/**
 * 
 */
package com.biostime.customer.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baiytfp.commonlib.base.BaseServlet;
import com.baiytfp.commonlib.base.service.CommonService;
import com.baiytfp.commonlib.util.ResponseUtil;
import com.baiytfp.commonlib.util.StringUtils;
import com.biostime.customer.model.CustomerInfo;
import com.biostime.customer.model.CustomerRating;
import com.biostime.customer.model.Employees;
import com.biostime.magazine.common.DBManager;
import com.biostime.magazine.common.Page;
import com.biostime.magazine.common.PageUtils;

/**
 * 
 *	
 * @author wpl 
 * @date May 17, 2014 9:00:09 PM
 * @version 1.0
 */
public class CustomerServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private String templateFileName = "template.rar";

	/**
	 *
	 */
	@Override
	public void doHandle() {
		String operType = getParameter("operType");

		try {
			if (StringUtils.isNotBlank(operType)) {
				if("list".equals(operType)) {
					list();
				} else if("showList".equals(operType)) {
					showList();
				} else if("delete".equals(operType)) {
					delete();
				} else if("getEmployeeByAjax".equals(operType)) {
					getEmployeeByAjax();
				} else if("editCustService".equals(operType)) {
					editCustService();
				} else if("contractDetail".equals(operType)) {
					contractDetail();
				} else if("downloadTemplate".equals(operType)) {
					downloadTemplate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 模板下载
	 * 
	 * @throws Exception
	 * @author wpl 
	 * @date ：Jun 6, 2014 9:34:55 AM
	 * @version 1.0
	 */
	@SuppressWarnings("deprecation")
	private void downloadTemplate() throws Exception {
		// path是指欲下载的文件的路径。
		String realPath = request.getRealPath("template");
        File file = new File(realPath + File.separator + templateFileName);
//        // 取得文件名。
//        String filename = file.getName();
//        // 取得文件的后缀名。
//        String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

        // 以流的形式下载文件
        
        InputStream fis = null;
        byte[] buffer = null;
		try {
			fis = new BufferedInputStream(new FileInputStream(file));
			buffer = new byte[fis.available()];
	        fis.read(buffer);
	        
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fis != null)fis.close();
		}
        
        // 清空response
        response.reset();
        // 设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(templateFileName.getBytes()));
        response.addHeader("Content-Length", "" + file.length());
        OutputStream toClient = null;
		try {
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
	        toClient.write(buffer);
	        toClient.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(toClient != null)toClient.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void contractDetail() throws Exception {
		CommonService cs = new CommonService();
		String id = getParameter("id");
		
		StringBuffer sql = new StringBuffer("SELECT a.*,b.customer_id,b.customer_rating_id,b.cust_service_id,");
		sql.append("b.customer_name,b.cust_service_name,b.business_contacts,b.phone,b.introduction,");
		sql.append("b.adress,b.main_products,b.total_count,b.total_num,c.level_name FROM contract_info a ");
		sql.append("LEFT JOIN customer_info b ON a.customer_info_id=b.customer_info_id ");
		sql.append("LEFT JOIN customer_rating c ON b.customer_rating_id=c.customer_rating_id WHERE a.customer_info_id=?");
		List<String> params = new ArrayList<String>();	//params
		params.add(id);
		List<Map<String, Object>> list = cs.getItemMapList(DBManager.getConnection(), sql.toString(), params.toArray());
		if(list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				//根据合同id查找图片
				String findpictureSql = "SELECT * FROM picture WHERE business_type = 1 AND business_id = ?";
				List pictures = cs.getItemMapList(DBManager.getConnection(), findpictureSql, new Object[]{map.get("contract_id")});
				map.put("pictures", pictures);
				
				//查询商务id对应的名称、经理id对应的名称、总监id对应的名称
				String sqlTemp1 = "select * from employees e where e.employees_id = ?";
				List businessList = cs.getItemMapList(DBManager.getConnection(), sqlTemp1, new Object[]{map.get("business_id")});
				String name = "";
				String branch = "";
				String department = "";
				String work_no = "";
				if(businessList != null && businessList.size() > 0) {
					Map<String, String> tempMap = (Map<String, String>) businessList.get(0);
					work_no = (tempMap.get("work_no"));
					name = (tempMap.get("employees_name"));
					branch = (tempMap.get("branch"));
					department = (tempMap.get("department"));
				}
				map.put("business_work_no", work_no);
				map.put("business_name", name);
				map.put("business_branch", branch);
				map.put("business_department", department);
				
				name = "";
				branch = "";
				department = "";
				work_no = "";
				String sqlTemp2 = "select * from employees e where e.employees_id = ?";
				List managerList = cs.getItemMapList(DBManager.getConnection(), sqlTemp2, new Object[]{map.get("manager_id")});
				if(managerList != null && managerList.size() > 0) {
					Map<String, String> tempMap = (Map<String, String>) managerList.get(0);
					work_no = (tempMap.get("work_no"));
					name = (tempMap.get("employees_name"));
					branch = (tempMap.get("branch"));
					department = (tempMap.get("department"));
				}
				map.put("manager_work_no", work_no);
				map.put("manager_name", name);
				map.put("manager_branch", branch);
				map.put("manager_department", department);
				
				name = "";
				branch = "";
				department = "";
				work_no = "";
				String sqlTemp3 = "select * from employees e where e.employees_id = ?";
				List directorList = cs.getItemMapList(DBManager.getConnection(), sqlTemp3, new Object[]{map.get("director_id")});
				String director_name = "";
				if(directorList != null && directorList.size() > 0) {
					Map<String, String> tempMap = (Map<String, String>) directorList.get(0);
					name = (tempMap.get("employees_name"));
					branch = (tempMap.get("branch"));
					department = (tempMap.get("department"));
					work_no = (tempMap.get("work_no"));
				}
				map.put("director_work_no", work_no);
				map.put("director_name", director_name);
				map.put("director_branch", branch);
				map.put("director_department", department);
				
				StringBuffer sql2 = new StringBuffer("SELECT a.*,b.contract_id,c.type_name,c.pic_path FROM work_order a ");
				sql2.append("LEFT JOIN contract_info b ON a.contract_id = b.contract_id ");
				sql2.append("LEFT JOIN product_type c ON a.product_type_id = c.product_type_id ");
				sql2.append("WHERE b.contract_id = ?");
				List<String> params2 = new ArrayList<String>();	//params
				params2.add(String.valueOf(map.get("contract_id")));
				List<Map<String, Object>> list2 = cs.getItemMapList(DBManager.getConnection(), sql2.toString(), params2.toArray());
				
				//查询出最新的服务进程
				if(list2 != null && list2.size() > 0) {
					for (Map<String, Object> map2 : list2) {
						StringBuffer sql3 = new StringBuffer("SELECT * FROM feedback WHERE work_order_id = ? ORDER BY feedback_id DESC");
						List<String> params3 = new ArrayList<String>();	//params
						params3.add(String.valueOf(map2.get("work_order_id")));
						List<Map<String, Object>> list3 = cs.getItemMapList(DBManager.getConnection(), sql3.toString(), params3.toArray());
						if(list3 != null && list3.size() > 0) {
							Map<String, Object> processvo = list3.get(0);	//最新一条，按id排序的最上面的一条
							map2.put("feedback_feedback_id", processvo.get("feedback_id"));
							map2.put("feedback_service_pro_name", processvo.get("service_pro_name"));
							map2.put("feedback_context", processvo.get("service_pro_name"));
						}
					}
				}
				
				map.put("workorders", list2);
			}
		}
		setAttribute("list", list);
		setAttribute("customer_info_id", id);
		requestForward("/reciprocity_admin/he-tong-xiang-qing.jsp");
	}
	
	private void editCustService() throws Exception {
		CommonService cs = new CommonService();
		String customer_info_id = getParameter("customer_info_id_temp");
		String customer_rating_id = getParameter("customer_rating_id");
		String cust_service_id_temp = getParameter("cust_service_id_temp");
		String cust_service_id = "0";
		String cust_service_name = "";
		if(!StringUtils.isBlank(cust_service_id_temp)) {
			String[] temps = cust_service_id_temp.split(",");
			if(temps != null && temps.length == 2) {
				cust_service_id = temps[0];
				cust_service_name = temps[1];
			}
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("cust_service_id", cust_service_id);
		map.put("customer_rating_id", customer_rating_id);
		map.put("cust_service_name", cust_service_name);
		map.put("modify_time", dateFormat.format(new Date()));
		cs.itemMapEdit(DBManager.getConnection(), "customer_info", "where customer_info_id = ?", customer_info_id, map);
		requestForward("/customer.action?operType=list");
	}
	
	@SuppressWarnings("unchecked")
	private void getEmployeeByAjax() throws Exception {
		CommonService cs = new CommonService();
		StringBuffer sql = new StringBuffer("select * from employees");
		List<String> params = new ArrayList<String>();	//params
		List<Employees> list = cs.getItemMapList(DBManager.getConnection(), sql.toString(), params.toArray());
		ResponseUtil.sendJsonList(response, list);
	}
	
	/**
	 * 
	 * 
	 * @throws Exception
	 * @author wpl 
	 * @date ：May 24, 2014 10:44:35 AM
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	private void showList() throws Exception {
		CommonService cs = new CommonService();
		
		StringBuffer sql = new StringBuffer("select * from customer_info");
		List<String> params = new ArrayList<String>();	//params
		List<CustomerInfo> list = cs.getItemMapList(DBManager.getConnection(), sql.toString(), params.toArray());
		setAttribute("list", list);
		requestForward("/reciprocity_admin/dao-ri-xin-xi.jsp");
	}
	
	
	/**
	 * 
	 * 
	 * @throws Exception
	 * @author wpl 
	 * @date ：May 24, 2014 10:44:45 AM
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	private void list() throws Exception {
		//第几个页面
		Integer pageIndex = getIntegerParameter("pageIndex");
		if(pageIndex == null || pageIndex == 0) {
			pageIndex = 1;
		}
		//页面大小
		Integer pageSize = 10;
		Page page = null;
		
		CommonService cs = new CommonService();
		
		String cust_service_name = getParameter("cust_service_name");
		String start_create_time = getParameter("start_create_time");
		String end_create_time = getParameter("end_create_time");
		String customer_name = getParameter("customer_name");
		String contract_no = getParameter("contract_no");
		StringBuffer sql = null;
		List<String> params = new ArrayList<String>();	//params
		if(StringUtils.isNotBlank(contract_no)) {
			//合同不为空
			sql = new StringBuffer("SELECT DISTINCT a.*,b.customer_rating_id,b.level_name FROM customer_info a ");
			sql.append("LEFT JOIN customer_rating b ON a.customer_rating_id  =b.customer_rating_id ");
			sql.append("LEFT JOIN contract_info c ON a.customer_info_id = c.customer_info_id ");
			sql.append("WHERE a.isdelete='0' AND c.contract_no like ? ");
			params.add("%" + contract_no + "%");
			if(StringUtils.isNotBlank(cust_service_name)) {
				sql.append(" and a.cust_service_name like ? ");
				params.add("%" + cust_service_name + "%");
				setAttribute("cust_service_name", cust_service_name);
			}
			if(StringUtils.isNotBlank(start_create_time) || StringUtils.isNotBlank(end_create_time)) {
				if(StringUtils.isNotBlank(start_create_time)) {
					String start_crreate_time = start_create_time + " 00:00:00";
					sql.append(" and a.create_time >= ? ");
					params.add(start_crreate_time);
					setAttribute("start_create_time", start_create_time);
				}
				
				if(StringUtils.isNotBlank(end_create_time)) {
					String end_crreate_time = end_create_time + " 23:59:59";
					sql.append(" and a.create_time <= ? ");
					params.add(end_crreate_time);
					setAttribute("end_create_time", end_create_time);
				}
			}
			if(StringUtils.isNotBlank(customer_name)) {
				sql.append(" and a.customer_name like ? ");
				params.add("%" + customer_name + "%");
				setAttribute("customer_name", customer_name);
			}
			//去重复
		} else {
			sql = new StringBuffer("SELECT a.*,b.customer_rating_id,b.level_name FROM customer_info a LEFT JOIN customer_rating b ON a.customer_rating_id=b.customer_rating_id WHERE a.isdelete='0'");
			if(StringUtils.isNotBlank(cust_service_name)) {
				sql.append(" and a.cust_service_name like ? ");
				params.add("%" + cust_service_name + "%");
				setAttribute("cust_service_name", cust_service_name);
			}
			if(StringUtils.isNotBlank(start_create_time) || StringUtils.isNotBlank(end_create_time)) {
				if(StringUtils.isNotBlank(start_create_time)) {
					String start_crreate_time = start_create_time + " 00:00:00";
					sql.append(" and a.create_time >= ? ");
					params.add(start_crreate_time);
					setAttribute("start_create_time", start_create_time);
				}
				
				if(StringUtils.isNotBlank(end_create_time)) {
					String end_crreate_time = end_create_time + " 23:59:59";
					sql.append(" and a.create_time <= ? ");
					params.add(end_crreate_time);
					setAttribute("end_create_time", end_create_time);
				}
			}
			if(StringUtils.isNotBlank(customer_name)) {
				sql.append(" and a.customer_name like ? ");
				params.add("%" + customer_name + "%");
				setAttribute("customer_name", customer_name);
			}
		}
		
		List<Map<String, Object>> list = cs.getItemMapList(DBManager.getConnection(), sql.toString(), params.toArray());
		int rowCount = 0;
		if(list != null && list.size() > 0) {
			//得到list总条数
			rowCount = list.size();
		}
		//分页
		list = PageUtils.getItemMapList(cs, sql.toString(),  params.toArray(), pageIndex, pageSize);
		page = new Page(pageIndex, pageSize, rowCount, list);
		setAttribute("list", list);
		setAttribute("page", page);
		
		StringBuffer sql2 = new StringBuffer("SELECT * FROM employees WHERE login_role = '2'");
		List<String> params2 = new ArrayList<String>();	//params
		List<Employees> employees = cs.getItemMapList(DBManager.getConnection(), sql2.toString(), params2.toArray());
		setAttribute("employees", employees);
		
		StringBuffer sql3 = new StringBuffer("select * from customer_rating");
		List<String> params3 = new ArrayList<String>();	//params
		List<CustomerRating> customer_ratings = cs.getItemMapList(DBManager.getConnection(), sql3.toString(), params3.toArray());
		setAttribute("ratings", customer_ratings);
		
		requestForward("/reciprocity_admin/ke-hu-list.jsp");
	}
	
	private void delete() throws Exception {
		CommonService cs = new CommonService();
		String id = getParameter("id");
		Map<String, String> map = new HashMap<String, String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("isdelete", "1");
		map.put("modify_time", dateFormat.format(new Date()));
		cs.itemMapEdit(DBManager.getConnection(), "customer_info", "where customer_info_id = ?", id, map);
		requestForward("/customer.action?operType=list");
	}

}
