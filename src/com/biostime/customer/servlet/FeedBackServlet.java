/**
 * 
 */
package com.biostime.customer.servlet;

import java.io.PrintWriter;
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
import com.biostime.magazine.common.DBManager;
import com.biostime.magazine.common.Page;
import com.biostime.magazine.common.PageUtils;

/**
 * 
 *	
 * @author wpl 
 * @date May 18, 2014 11:12:01 PM
 * @version 1.0
 */
public class FeedBackServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void doHandle() {
		String operType = getParameter("operType");

		try {
			if (StringUtils.isNotBlank(operType)) {
				if("list".equals(operType)) {
					list();
				} else if("add".equals(operType)) {
					add();
				} else if("showDetail".equals(operType)) {
					showDetail();
				} else if("toAdd".equals(operType)) {
					toAdd();
				} else if("saveOrUpdate".equals(operType)) {
					saveOrUpdate();
				} else if("checkStatus".equals(operType)) {
					checkStatus();
				} else if("getStatusByType".equals(operType)) {
					getStatusByType();
				} else if("feedbackHaveStatus".equals(operType)) {
					feedbackHaveStatus();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void feedbackHaveStatus() throws Exception {
		CommonService commonService = new CommonService();
		
		String feedback_status_id = getParameter("feedback_status_id");
		
		StringBuffer buffer = new StringBuffer("SELECT * FROM feedback WHERE  STATUS = ? ");
		List<Map<String, Object>> list = commonService.getItemMapList(DBManager.getConnection(), buffer.toString(), new Object[]{feedback_status_id});
		String resultText = "false";
		if(list != null && list.size() > 0) {
			resultText = "true";
		}
		PrintWriter out = getResponse().getWriter();
		out.print(resultText);
		out.flush();
		out.close();
	}
	
	@SuppressWarnings("unchecked")
	private void checkStatus() throws Exception {
		String work_order_id = getParameter("work_order_id");
		String status = getParameter("status");
		
		CommonService commonService = new CommonService();
		
		StringBuffer buffer = new StringBuffer("SELECT * FROM feedback WHERE work_order_id = ? AND STATUS = ? ");
		List<Map<String, Object>> list = commonService.getItemMapList(DBManager.getConnection(), buffer.toString(), new Object[]{work_order_id, status});
		String resultText = "false";
		if(list != null && list.size() > 0) {
			//存在
			resultText = "true";
		}
		PrintWriter out = getResponse().getWriter();
		out.print(resultText);
		out.flush();
		out.close();
	}
	
	@SuppressWarnings("unchecked")
	private void getStatusByType() throws Exception {
		CommonService commonService = new CommonService();
		
		String product_type_id = getParameter("product_type_id");
		StringBuffer buffer = new StringBuffer("SELECT * FROM feedback_status WHERE product_type_id = ? ORDER BY status_num ASC");
		List<Map<String, Object>> statusList = commonService.getItemMapList(DBManager.getConnection(), buffer.toString(), new Object[]{product_type_id});
		ResponseUtil.sendJsonList(response, statusList);
	}
	
	private void saveOrUpdate() throws Exception {
		CommonService commonService = new CommonService();
		
		String actionType = getParameter("actionType");
		String work_order_id = getParameter("work_order_id");
		String service_pro_name = getParameter("service_pro_name");
		String create_time = getParameter("create_time");
		String status = getParameter("status");
		String context = getParameter("context");
		String feedback_id = getParameter("feedback_id");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("work_order_id", work_order_id);
		map.put("service_pro_name", service_pro_name);
		map.put("status", status);
		map.put("context", context);
		map.put("create_time", create_time);
		map.put("modify_time", sdf.format(new Date()));
		if(StringUtils.isNotBlank(actionType) && "update".equals(actionType) && StringUtils.isNotBlank(feedback_id)) {
			//修改
			commonService.itemMapEdit(DBManager.getConnection(), "feedback", "WHERE feedback_id = ?", feedback_id, map);
			sendRedirect("feedback.action?operType=list");
		} else {
			//新增
			commonService.itemMapAdd(DBManager.getConnection(), "feedback", map);
			sendRedirect("workorder.action?operType=showDetail&id="+work_order_id);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void toAdd() throws Exception {
		CommonService commonService = new CommonService();
		
		String work_order_id = getParameter("work_order_id");
		if(StringUtils.isNotBlank(work_order_id)) {
			StringBuffer sql = new StringBuffer("SELECT a.*,c.contract_id,c.contract_no,b.type_name,b.product_type_id,d.customer_name FROM work_order a ");
			sql.append("LEFT JOIN product_type b ON a.product_type_id=b.product_type_id ");
			sql.append("LEFT JOIN contract_info c ON a.contract_id=c.contract_id ");
			sql.append("LEFT JOIN customer_info d ON d.customer_info_id=c.customer_info_id ");
			sql.append("WHERE a.work_order_id = ?");
			List<Map<String, Object>> list = commonService.getItemMapList(DBManager.getConnection(), sql.toString(), new Object[]{work_order_id});
			if(list != null && list.size() > 0) {
				Map<String, Object> vo = list.get(0);
				setAttribute("vo", vo);
				String product_type_id = String.valueOf(vo.get("product_type_id"));
				if(vo != null && StringUtils.isNotBlank(product_type_id)) {
					StringBuffer buffer = new StringBuffer("SELECT * FROM feedback_status WHERE product_type_id = ? ORDER BY status_num ASC");
					List<Map<String, Object>> statusList = commonService.getItemMapList(DBManager.getConnection(), buffer.toString(), new Object[]{product_type_id});
					setAttribute("statusList", statusList);
				}
			}
		}
		requestForward("/reciprocity_admin/add-jin-cheng.jsp");
	}
	
	@SuppressWarnings("unchecked")
	private void showDetail() throws Exception {
		String feedback_id = getParameter("id");
		if(StringUtils.isNotBlank(feedback_id)) {
			CommonService cs = new CommonService();
			List<String> params = new ArrayList<String>();	//params
			StringBuffer sql = new StringBuffer("SELECT a.*,c.contract_id,c.contract_no,b.work_order_id_src,d.type_name,d.product_type_id,e.customer_name FROM feedback a  ");
			sql.append("LEFT JOIN work_order b ON a.work_order_id=b.work_order_id ");
			sql.append("LEFT JOIN contract_info c ON c.contract_id=b.contract_id ");
			sql.append("LEFT JOIN product_type d ON b.product_type_id=d.product_type_id ");
			sql.append("LEFT JOIN customer_info e ON e.customer_info_id=c.customer_info_id ");
			sql.append("WHERE a.feedback_id = ?");
			params.add(feedback_id);
			
			List<Map<String, Object>> list = cs.getItemMapList(DBManager.getConnection(), sql.toString(), params.toArray());
			if(list != null && list.size() > 0) {
				Map<String, Object> vo = list.get(0);
				setAttribute("vo", vo);
				String product_type_id = String.valueOf(vo.get("product_type_id"));
				if(vo != null && StringUtils.isNotBlank(product_type_id)) {
					StringBuffer buffer = new StringBuffer("SELECT * FROM feedback_status WHERE product_type_id = ? ORDER BY status_num ASC");
					List<Map<String, Object>> statusList = cs.getItemMapList(DBManager.getConnection(), buffer.toString(), new Object[]{product_type_id});
					setAttribute("statusList", statusList);
				}
			}
		}
		setAttribute("actionType", "update");
		requestForward("/reciprocity_admin/add-jin-cheng.jsp");
	}
	
	private void add() throws Exception {
		CommonService cs = new CommonService();
		String customer_info_id = getParameter("customer_info_id");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		String work_order_id = getParameter("work_order_id_temp");
		String service_pro_name = getParameter("service_pro_name");
		String context = getParameter("context");
		String create_time = getParameter("create_time");
		map.put("work_order_id", work_order_id);
		map.put("service_pro_name", service_pro_name);
		map.put("context", context);
		map.put("create_time", create_time);
		map.put("modify_time", create_time);
		Long num = cs.itemMapAdd(DBManager.getConnection(), "feedback", map);
		
		if(num > 0) {
			//插入到图片表
			String[] old_filePaths = getParameterValues("old_filePath");
			String[] upImgs = getParameterValues("upImg");
			HashMap<String, String> pictureHash = null;
			if(upImgs != null && upImgs.length > 0) {
				for (int i = 0; i < upImgs.length; i++) {
					pictureHash = new HashMap<String, String>();
					pictureHash.put("business_id", work_order_id);
					pictureHash.put("business_type", "2");	//服务进程
					pictureHash.put("path", upImgs[i]);
					pictureHash.put("or_path", old_filePaths[i]);
					pictureHash.put("sort", "0");
					pictureHash.put("isdelete", "0");
					pictureHash.put("create_time", create_time);
					pictureHash.put("modify_time", create_time);
					cs.itemMapAdd(DBManager.getConnection(), "picture", pictureHash);
				}
			}
			
			requestForward("/customer.action?operType=contractDetail&id="+customer_info_id);
		}
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
		
		StringBuffer sql = new StringBuffer("SELECT a.*,c.contract_id,c.contract_no,b.work_order_id_src,d.type_name,d.product_type_id,e.customer_name,f.feedback_status_id,f.status_num,f.status_name FROM feedback a  ");
		sql.append("LEFT JOIN work_order b ON a.work_order_id=b.work_order_id ");
		sql.append("LEFT JOIN contract_info c ON c.contract_id=b.contract_id ");
		sql.append("LEFT JOIN product_type d ON b.product_type_id=d.product_type_id ");
		sql.append("LEFT JOIN customer_info e ON e.customer_info_id=c.customer_info_id ");
		sql.append("LEFT JOIN feedback_status f ON f.feedback_status_id=a.status ");
		sql.append("WHERE 1=1 ");
		List<String> params = new ArrayList<String>();	//params
		
		String customer_name = getParameter("customer_name");
		if(StringUtils.isNotBlank(customer_name)) {
			sql.append(" AND e.customer_name like ?");
			params.add("%" + customer_name + "%");
			setAttribute("customer_name", customer_name);
		}
		String product_type_id = getParameter("product_type_id");
		if(StringUtils.isNotBlank(product_type_id)) {
			sql.append(" AND d.product_type_id = ?");
			params.add(product_type_id);
			setAttribute("product_type_id", product_type_id);
			StringBuffer buffer = new StringBuffer("SELECT * FROM feedback_status WHERE product_type_id = ? ORDER BY status_num ASC");
			List<Map<String, Object>> statusList = cs.getItemMapList(DBManager.getConnection(), buffer.toString(), new Object[]{product_type_id});
			setAttribute("statusList", statusList);
		}
		String work_order_id_src = getParameter("work_order_id_src");
		if(StringUtils.isNotBlank(work_order_id_src)) {
			sql.append(" AND b.work_order_id_src like ?");
			params.add("%" + work_order_id_src + "%");
			setAttribute("work_order_id_src", work_order_id_src);
		}
		String service_pro_name = getParameter("service_pro_name");
		if(StringUtils.isNotBlank(service_pro_name)) {
			sql.append(" AND a.service_pro_name like ?");
			params.add("%" + service_pro_name + "%");
			setAttribute("service_pro_name", service_pro_name);
		}
		String feedback_status_id = getParameter("status");
		if(StringUtils.isNotBlank(feedback_status_id)) {
			sql.append(" AND f.feedback_status_id = ?");
			params.add(feedback_status_id);
			setAttribute("feedback_status_id", feedback_status_id);
		}
		//此work_order_id是用来合同详情查看服务进程的
		String work_order_id = getParameter("work_order_id");
		if(StringUtils.isNotBlank(work_order_id)) {
			sql.append(" AND a.work_order_id = ?");
			params.add(work_order_id);
			setAttribute("work_order_id", work_order_id);
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
		StringBuffer productTypeSql = new StringBuffer("SELECT * FROM product_type ORDER BY product_type_id ASC");
		List<Map<String, Object>> productTypeList = cs.getItemMapList(DBManager.getConnection(), productTypeSql.toString(), new Object[]{});
		setAttribute("productTypeList", productTypeList);
		
		setAttribute("list", list);
		setAttribute("page", page);
		requestForward("/reciprocity_admin/fu-wu-jin-cheng-list.jsp");
	}

}
