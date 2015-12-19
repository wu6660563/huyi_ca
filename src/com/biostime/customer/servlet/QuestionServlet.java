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
import com.biostime.magazine.common.DBManager;
import com.biostime.magazine.common.Page;
import com.biostime.magazine.common.PageUtils;

/**
 * 
 *	
 * @author wpl 
 * @date May 18, 2014 11:26:05 PM
 * @version 1.0
 */
public class QuestionServlet extends BaseServlet {

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
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		
		StringBuffer sql = new StringBuffer("SELECT a.*,b.work_order_id_src,b.contract_id,c.contract_no FROM question a  ");
		sql.append("LEFT JOIN work_order b ON a.work_order_id=b.work_order_id ");
		sql.append("left join contract_info c on b.contract_id=c.contract_id ");
		List<String> params = new ArrayList<String>();	//params
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
		requestForward("/reciprocity_admin/dao-ri-wen-ti-dan.jsp");
	}

}
