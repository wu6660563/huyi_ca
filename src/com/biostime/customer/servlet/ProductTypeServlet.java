/**
 * 
 */
package com.biostime.customer.servlet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
 * @date Jun 30, 2014 3:46:07 PM
 * @version 1.0
 */
public class ProductTypeServlet extends BaseServlet {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
				} else if("saveOrUpdate".equals(operType)) {
					saveOrUpdate();
				} else if("toUpdate".equals(operType)) {
					toUpdate();
				} else if("delete".equals(operType)) {
					delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void delete() throws Exception {
		String id = getParameter("id");
		if(StringUtils.isNotBlank(id)) {
			CommonService commonService = new CommonService();
			System.out.println("delete:"+id);
			commonService.itemDel(DBManager.getConnection(), "product_type", "WHERE product_type_id = ?", new Object[]{id});
			
			commonService.itemDel(DBManager.getConnection(), "feedback_status", "WHERE product_type_id = ?", new Object[]{id});
		}
		response.sendRedirect("producttype.action?operType=list");
	}
	
	@SuppressWarnings("unchecked")
	private void toUpdate() throws Exception {
		String id = getParameter("id");
		CommonService commonService = new CommonService();
		StringBuffer buffer = new StringBuffer("SELECT * FROM product_type WHERE product_type_id = ?");
		List<Map<String, Object>> list = commonService.getItemMapList(DBManager.getConnection(), buffer.toString(), new Object[]{id});
		if(list != null && list.size() > 0) {
			Map<String, Object> vo = list.get(0);
			setAttribute("vo", vo);
			if(vo != null) {
				StringBuffer buffer2 = new StringBuffer("SELECT * FROM feedback_status WHERE product_type_id = ? ORDER BY status_num ASC");
				List<Map<String, Object>> statusList = commonService.getItemMapList(DBManager.getConnection(), buffer2.toString(), new Object[]{id});
				setAttribute("statusList", statusList);
			}
		}
		requestForward("/reciprocity_admin/add-product.jsp");
	}
	
	//因为要添加product_type，考虑到线程问题，同步线程，故将改方法加锁
	@SuppressWarnings("unchecked")
	private synchronized void saveOrUpdate() throws Exception {
		String product_type_id = getParameter("product_type_id");
		String type_name = getParameter("type_name");
		String course_num = getParameter("course_num");
		String upImg = getParameter("upImg");
		
		String[] status_id = getParameterValues("status_id");
		String[] status_name = getParameterValues("status_name");
		
		if(StringUtils.isNotBlank(product_type_id)) {
			//product_type_id不为空，修改
			Map<String, String> map = new HashMap<String, String>();
			map.put("type_name", type_name);
			map.put("pic_path", upImg);
			map.put("course_num", course_num);
			map.put("modify_time", sdf.format(new Date()));
			
			CommonService commonService = new CommonService();
			commonService.itemMapEdit(DBManager.getConnection(), "product_type", "WHERE product_type_id = ?", product_type_id, map);
			
			//删除feedback_status表里面product_type_id相同的数据，再插入新的数据
			commonService.itemDel(DBManager.getConnection(), "feedback_status", "WHERE product_type_id = ?", new Object[]{product_type_id});
			if(status_id != null && status_name != null && status_id.length > 0 && status_name.length > 0) {
				for (int i = 0; i < status_name.length; i++) {
					Map<String, String> statusMap = new HashMap<String, String>();
					statusMap.put("product_type_id", String.valueOf(product_type_id));
					statusMap.put("status_num", status_id[i]);
					statusMap.put("status_name", status_name[i]);
					statusMap.put("isdelete", "0");
					statusMap.put("create_time", sdf.format(new Date()));
					statusMap.put("modify_time", sdf.format(new Date()));
					commonService.itemMapAdd(DBManager.getConnection(), "feedback_status", statusMap);
				}
			}
		} else {
			////product_type_id为空,新增
			Map<String, String> map = new HashMap<String, String>();
			map.put("type_name", type_name);
			map.put("pic_path", upImg);
			map.put("course_num", course_num);
			map.put("isdelete", "0");
			map.put("create_time", sdf.format(new Date()));
			map.put("modify_time", sdf.format(new Date()));
			
			CommonService commonService = new CommonService();
			commonService.itemMapAdd(DBManager.getConnection(), "product_type", map);
			
			//查询刚刚插入的ID
			StringBuffer buffer = new StringBuffer("SELECT * FROM product_type ORDER BY product_type_id DESC");
			List<Map<String, String>> list = commonService.getItemMapList(DBManager.getConnection(), buffer.toString(), new Object[]{});
			int id = 0;
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					id = Integer.parseInt(String.valueOf(list.get(0).get("product_type_id")));
				}
			}
			if(id != 0) {
				if(status_id != null && status_name != null && status_id.length > 0 && status_name.length > 0) {
					for (int i = 0; i < status_name.length; i++) {
						Map<String, String> statusMap = new HashMap<String, String>();
						statusMap.put("product_type_id", String.valueOf(id));
						statusMap.put("status_num", status_id[i]);
						statusMap.put("status_name", status_name[i]);
						statusMap.put("isdelete", "0");
						statusMap.put("create_time", sdf.format(new Date()));
						statusMap.put("modify_time", sdf.format(new Date()));
						commonService.itemMapAdd(DBManager.getConnection(), "feedback_status", statusMap);
					}
				}
			}
		}
		response.sendRedirect("producttype.action?operType=list");
	}
	
	@SuppressWarnings("unchecked")
	private void list() throws Exception {
		CommonService commonService = new CommonService();
		
		//第几个页面
		Integer pageIndex = getIntegerParameter("pageIndex");
		if(pageIndex == null || pageIndex == 0) {
			pageIndex = 1;
		}
		//页面大小
		Integer pageSize = 10;
		Page page = null;
		
		StringBuffer buffer = new StringBuffer("SELECT * FROM product_type a WHERE 1=1 ");
		List<String> params = new ArrayList<String>();
		
		String type_name = getParameter("type_name");
		if(StringUtils.isNotBlank(type_name)) {
			buffer.append(" AND type_name like ?");
			params.add("%" + type_name + "%");
			setAttribute("type_name", type_name);
		}
		
		List<WorkOrder> list = commonService.getItemMapList(DBManager.getConnection(), buffer.toString(), params.toArray());
		int rowCount = 0;
		if(list != null && list.size() > 0) {
			//得到list总条数
			rowCount = list.size();
		}
		//分页
		list = PageUtils.getItemMapList(commonService, buffer.toString(),  params.toArray(), pageIndex, pageSize);
		page = new Page(pageIndex, pageSize, rowCount, list);
		
		setAttribute("list", list);
		setAttribute("page", page);
		requestForward("/reciprocity_admin/product-list.jsp");
	}

}
