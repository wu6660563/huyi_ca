/**
 * 
 */
package com.biostime.customer.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baiytfp.commonlib.base.BaseServlet;
import com.baiytfp.commonlib.base.service.CommonService;
import com.baiytfp.commonlib.util.StringUtils;
import com.biostime.customer.model.WorkOrder;
import com.biostime.magazine.common.DBManager;
import com.biostime.magazine.common.Page;
import com.biostime.magazine.common.PageUtils;

/**
 * 
 *	
 * @author wpl 
 * @date May 18, 2014 11:03:26 PM
 * @version 1.0
 */
public class WorkOrderServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doHandle() {
		String operType = getParameter("operType");

		try {
			if (StringUtils.isNotBlank(operType)) {
				if("list".equals(operType)) {
					list();
				} else if("showDetail".equals(operType)) {
					showDetail();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void showDetail() throws Exception {
		String work_order_id = getParameter("id");
		if(StringUtils.isNotBlank(work_order_id)) {
			CommonService cs = new CommonService();
			List<String> params = new ArrayList<String>();	//params
			StringBuffer sql = new StringBuffer("SELECT a.*,c.contract_id,c.contract_no,b.type_name,d.customer_name FROM work_order a ");
			sql.append("LEFT JOIN product_type b ON a.product_type_id=b.product_type_id ");
			sql.append("LEFT JOIN contract_info c ON a.contract_id=c.contract_id ");
			sql.append("LEFT JOIN customer_info d ON d.customer_info_id=c.customer_info_id ");
			sql.append("WHERE a.work_order_id = ?");
			params.add(work_order_id);
			
			List<Map<String, Object>> list = cs.getItemMapList(DBManager.getConnection(), sql.toString(), params.toArray());
			if(list != null && list.size() > 0) {
				Map<String, Object> vo = list.get(0);
				setAttribute("vo", vo);
				if(vo != null && vo.size() > 0) {
					StringBuffer stringBuffer = new StringBuffer();
					stringBuffer.append("SELECT a.*,b.work_order_id_src,c.status_num,c.status_name FROM feedback a ");
					stringBuffer.append("LEFT JOIN work_order b ON a.work_order_id=b.work_order_id ");
					stringBuffer.append("LEFT JOIN feedback_status c ON c.feedback_status_id=a.status ");
					stringBuffer.append("WHERE a.work_order_id = ? ORDER BY a.feedback_id DESC ");
					List<Map<String, Object>> feedbackList = cs.getItemMapList(DBManager.getConnection(), stringBuffer.toString(), new Object[]{work_order_id});
					setAttribute("feedbackList", feedbackList);
				}
			}
		}
		requestForward("/reciprocity_admin/gong-dan-xiang-qing.jsp");
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
		
		StringBuffer sql = new StringBuffer("SELECT a.*,c.contract_id,c.contract_no,b.product_type_id,b.type_name,d.customer_name FROM work_order a ");
		sql.append("LEFT JOIN product_type b ON a.product_type_id=b.product_type_id ");
		sql.append("LEFT JOIN contract_info c ON a.contract_id=c.contract_id ");
		sql.append("LEFT JOIN customer_info d ON d.customer_info_id=c.customer_info_id ");
		sql.append("WHERE 1=1 ");
		List<String> params = new ArrayList<String>();	//params
		
		String customer_name = getParameter("customer_name");
		if(StringUtils.isNotBlank(customer_name)) {
			sql.append(" AND d.customer_name like ?");
			params.add("%" + customer_name + "%");
			setAttribute("customer_name", customer_name);
		}
		String product_type_id = getParameter("product_type_id");
		if(StringUtils.isNotBlank(product_type_id)) {
			sql.append(" AND b.product_type_id = ?");
			params.add(product_type_id);
			setAttribute("product_type_id", product_type_id);
		}
		String work_order_id_src = getParameter("work_order_id_src");
		if(StringUtils.isNotBlank(work_order_id_src)) {
			sql.append(" AND a.work_order_id_src like ?");
			params.add("%" + work_order_id_src + "%");
			setAttribute("work_order_id_src", work_order_id_src);
		}
		
		String work_order_id = getParameter("work_order_id");
		if(StringUtils.isNotBlank(work_order_id_src)) {
			sql.append(" AND a.work_order_id = ?");
			params.add(work_order_id);
			setAttribute("work_order_id", work_order_id);
		}
		
		List<WorkOrder> list = cs.getItemMapList(DBManager.getConnection(), sql.toString(), params.toArray());
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
		requestForward("/reciprocity_admin/gong-dan-list.jsp");
	}

}
