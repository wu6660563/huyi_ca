/**
 * 
 */
package com.biostime.customer.servlet;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.baiytfp.commonlib.base.BaseServlet;
import com.baiytfp.commonlib.base.service.CommonService;
import com.baiytfp.commonlib.util.StringUtils;
import com.biostime.magazine.common.DBManager;
import com.biostime.magazine.common.Page;
import com.biostime.magazine.common.PageUtils;
import com.biostime.report.constant.SystemConstant;

/**
 * 
 *	
 * @author wpl 
 * @date May 18, 2014 9:46:40 PM
 * @version 1.0
 */
public class ContractServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void doHandle() {
		String operType = getParameter("operType");

		try {
			if (StringUtils.isNotBlank(operType)) {
				if("list".equals(operType)) {
					list();
				} else if("uploadImg".equals(operType)) {
					uploadImg();
				} else if("delImg".equals(operType)) {
					delImg();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	private String uploadImg() throws Exception {
		CommonService cs = new CommonService();

		String importType = null;
		boolean success = false;
		String realFilePath = null;
		HashMap<String, String> hashMap = new HashMap<String, String>();
		
		//得到文件上传工厂
		FileItemFactory factory = new DiskFileItemFactory();
		//处理文件上传核心类
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		//设置文件上传类的编码格式
		fileUpload.setHeaderEncoding("utf-8");
		//每一个表单域，对应一个FileItem对象
		List<FileItem> fileItemList = null;
		try {
			fileItemList = fileUpload.parseRequest(request);
			if(fileItemList != null) {
				for (FileItem fileItem : fileItemList) {
					if(!fileItem.isFormField()) {	//文件类型的表单
						String name = fileItem.getName();
						String fileName = name.substring(name.lastIndexOf("\\")+1);
						String rootPath = request.getRealPath("/");
						String realPath = rootPath + "fileupload";
						File file = new File(realPath);
						if(!file.isDirectory()) {
							file.mkdirs();
						}
						//将中文名字改成uuid
						UUID uuid = UUID.randomUUID();
						//取到原来的文件名称的后缀名
						String postfix = fileName.substring(fileName.indexOf("."));	//.jpg .gif 后缀
						realFilePath =  realPath + File.separator + uuid.toString() + postfix;
						File newFile = new File(realFilePath);
						fileItem.write(newFile);
						hashMap.put("path",  uuid.toString() + postfix);	//新的文件名
						hashMap.put("or_path", fileName);	//改成只保存旧文件名
						success = true;
					} else {
						//非文件类型的表单
						String filedName = fileItem.getFieldName();
						String filedValue = fileItem.getString();
						System.out.println("type====" + filedName + "===" +filedValue);
						hashMap.put(filedName, filedValue);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		String resultText = "";
		if(success) {
			importType = hashMap.get("importType");
			System.out.println("importType:"+importType);
			if(SystemConstant.CONTRACT.equalsIgnoreCase(importType)) {
				//合同
				resultText = "success,"+hashMap.get("id")+","+hashMap.get("path");
				hashMap.put("business_type", "1");	//合同
				String business_id = hashMap.get("id").substring(10);	//selectFile9
				hashMap.put("business_id", business_id);
				
				//数据库
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("business_id", Integer.parseInt(hashMap.get("business_id")));
				map.put("business_type", String.valueOf(hashMap.get("business_type")));
				map.put("path", String.valueOf(hashMap.get("path")));
				map.put("or_path", String.valueOf(hashMap.get("or_path")));
				map.put("sort", "0");	//序号
				map.put("isdelete", "0");
				map.put("create_time", dateFormat.format(new Date()));
				map.put("modify_time", dateFormat.format(new Date()));
				cs.itemMapAdd(DBManager.getConnection(), "picture", map);
			} else if(SystemConstant.PROCESS.equalsIgnoreCase(importType)) {
				//服务进程
				resultText = "success,"+hashMap.get("path")+","+hashMap.get("or_path");
			} else if(SystemConstant.INFODISS.equalsIgnoreCase(importType)) {
				//业务资讯
				resultText = "success,"+hashMap.get("path")+","+hashMap.get("or_path");
			} else if(SystemConstant.PRODUCTTYPE.equalsIgnoreCase(importType)) {
				//产品类别
				resultText = "success,"+hashMap.get("path")+","+hashMap.get("or_path");
			}
		} else {
			resultText = "faild,0,0";
		}
		PrintWriter out = getResponse().getWriter();
		out.print(resultText);
		out.flush();
		out.close();
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private void delImg() throws Exception {
		String[] fileNames = getParameterValues("fileName");
		String delType = getParameter("delType");
		Object[] objects = null;
		if(fileNames != null && fileNames.length != 0) {
			objects = new Object[fileNames.length];
			for (int i = 0; i < objects.length; i++) {
				objects[i] = fileNames[i];
			}
		} else {
			objects = new Object[0];
		}
		String resultText = "faild";
		if(StringUtils.isNotBlank(delType) && SystemConstant.CONTRACT.equals(delType)) {
			String sqlWhere = " WHERE path in (";
			CommonService cs = new CommonService();
			for (int i = 0; i < objects.length; i++) {
				if(objects.length == (i+1)) {
					sqlWhere +=  "?)";
				} else {
					sqlWhere +=  "?,";
				}
			}
			long num = cs.itemDel(DBManager.getConnection(), "picture", sqlWhere, objects);
			if(num > 0) {
				resultText = "success";
			}
		}
		//不管哪种方式，都要删除服务器Tomcat图片，防止服务器文件积累
		String realPath = request.getRealPath("/") + "fileupload";
		for (int i = 0; i < objects.length; i++) {
			String path = realPath + File.separator + objects[i];
			File file = new File(path);
			if(file.isFile() && file.exists()) {
				file.delete();
			}
		}
		
		resultText = "success";
		PrintWriter out = getResponse().getWriter();
		out.print(resultText);
		out.flush();
		out.close();
	}
	
	@SuppressWarnings("unchecked")
	private void list() throws Exception {
		CommonService cs = new CommonService();
		
		//第几个页面
		Integer pageIndex = getIntegerParameter("pageIndex");
		if(pageIndex == null || pageIndex == 0) {
			pageIndex = 1;
		}
		//页面大小
		Integer pageSize = 10;
		Page page = null;
		
		StringBuffer sql = new StringBuffer("SELECT * FROM contract_info a LEFT JOIN customer_info b ON a.customer_info_id = b.customer_info_id WHERE 1=1 ");
		List<String> params = new ArrayList<String>();	//params
		
		String contract_no = getParameter("contract_no");
		if(StringUtils.isNotBlank(contract_no)) {
			sql.append(" AND a.contract_no like ?");
			params.add("%" + contract_no + "%");
			setAttribute("contract_no", contract_no);
		}
		String customer_name = getParameter("customer_name");
		if(StringUtils.isNotBlank(customer_name)) {
			sql.append(" AND b.customer_name like ?");
			params.add("%" + customer_name + "%");
			setAttribute("customer_name", customer_name);
		}
		
		List list = cs.getItemMapList(DBManager.getConnection(), sql.toString(), params.toArray());
		int rowCount = 0;
		if(list != null && list.size() > 0) {
			//得到list总条数
			rowCount = list.size();
		}
		//分页
		list = PageUtils.getItemMapList(cs, sql.toString(),  params.toArray(), pageIndex, pageSize);
		if(list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map custractMap = (Map) list.get(i);
				String sqlTemp1 = "select * from employees e where e.employees_id = ?";
				List businessList = cs.getItemMapList(DBManager.getConnection(), sqlTemp1, new Object[]{custractMap.get("business_id")});
				String business_name = "";
				String branch = "";
				String department = "";
				if(businessList != null && businessList.size() > 0) {
					Map<String, String> tempMap = (Map<String, String>) businessList.get(0);
					business_name = (tempMap.get("employees_name"));
					branch = (tempMap.get("branch"));
					department = (tempMap.get("department"));
				}
				custractMap.put("business_name", business_name);
				custractMap.put("branch", branch);
				custractMap.put("department", department);
				
				String sqlTemp2 = "select * from employees e where e.employees_id = ?";
				List managerList = cs.getItemMapList(DBManager.getConnection(), sqlTemp2, new Object[]{custractMap.get("manager_id")});
				String manager_name = "";
				if(managerList != null && managerList.size() > 0) {
					Map<String, String> tempMap = (Map<String, String>) managerList.get(0);
					manager_name = (tempMap.get("employees_name"));
				}
				custractMap.put("manager_name", manager_name);
				
				String sqlTemp3 = "select * from employees e where e.employees_id = ?";
				List directorList = cs.getItemMapList(DBManager.getConnection(), sqlTemp3, new Object[]{custractMap.get("director_id")});
				String director_name = "";
				if(directorList != null && directorList.size() > 0) {
					Map<String, String> tempMap = (Map<String, String>) directorList.get(0);
					director_name = (tempMap.get("employees_name"));
				}
				custractMap.put("director_name", director_name);
			}
		}
		page = new Page(pageIndex, pageSize, rowCount, list);
		setAttribute("list", list);
		setAttribute("page", page);
		requestForward("/reciprocity_admin/dao-ri-he-tong.jsp");
	}

}
