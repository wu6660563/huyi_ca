/**
 * 
 */
package com.biostime.app.servlet;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baiytfp.commonlib.base.BaseServlet;
import com.baiytfp.commonlib.base.service.CommonService;
import com.biostime.customer.model.ContractInfo;
import com.biostime.customer.model.CustomerInfo;
import com.biostime.customer.model.CustomerRating;
import com.biostime.customer.model.FeedBack;
import com.biostime.customer.model.Picture;
import com.biostime.customer.model.ProductType;
import com.biostime.customer.model.WorkOrder;
import com.biostime.magazine.common.DBManager;

/**
 * 获取客户APP数据servlet
 *	
 * @author zjl 新增日期：2013-9-13 下午06:29:52
 * @version 1.0
 */
public class CustomerAppServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private CommonService cs;
	@Override
	public void doHandle() {
		try {
			cs = new CommonService();
			String method = this.getParameter("method");
			if(null != method && !"".equals(method) && method.equalsIgnoreCase("index")){//进入首页,得到当前登录人信息
				getIndex();
			}else if(null != method && !"".equals(method) && method.equalsIgnoreCase("details")){ //查看工单详细信息
				getWorkOrderDetails();
			}else if(null != method && !"".equals(method) && method.equalsIgnoreCase("changeStart")){ //确认合同
				changeStart();
			} 
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 获取首页数据（客户信息，合同，工单）
	 * 
	 * @author zjl 新增日期：2014-5-15下午04:13:19
	 * @version 1.0
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	 public void getIndex() throws Exception{
		 List<CustomerInfo>  custom = new ArrayList<CustomerInfo>();
		 //登录用户名
		 String userId = request.getParameter("userId");
		 String version = request.getParameter("version");
		 String phcode = null;
		 String sql = "select c.customer_info_id,c.customer_id,c.customer_rating_id,c.cust_service_id,c.customer_name , c.cust_service_name,c.company_name,c.business_contacts,c.phone,c.introduction,c.adress,c.cust_service from customer_info c where c.isdelete = 0 ";
		 if(null != userId && !"".equals(userId)){
			 sql +=" and c.customer_info_id = ?"; 
			 custom = cs.getItemMapList(DBManager.getConnection(),sql, new Object[]{userId});
		 }
//		if(null != userId){
//			custom = cs.getItemMapList(DBManager.getConnection(),sql, new Object[]{userId});
//		}else{
//			custom = cs.getItemMapList(DBManager.getConnection(),sql, new Object[]{});
//		}
		//如果存在客户信息 根据客户资料id获取客户对于的客户等级图标,合同信息
		List<ContractInfo> contract = new ArrayList<ContractInfo>();
		List<WorkOrder> workOrder = new ArrayList<WorkOrder>();
		List<CustomerRating> custcomerrat = new ArrayList<CustomerRating>();
		if(custom.size() != 0){ 
			//获取合同信息,客户等级
			for (int i = 0; i < custom.size(); i++) {
				Map customInfoMap = (Map) custom.get(i);
				phcode = String.valueOf(customInfoMap.get("phone"));
				String contratSql = "select ci.contract_id,ci.customer_info_id,ci.contract_no,ci.signing_time,ci.business_id,ci.is_sure_contract,ci.cooperation_num,(select en.employees_name from  employees en where en.employees_id = ci.business_id )as business_name  from contract_info ci where customer_info_id = ?";
				String custcomerratSql = "select cr.pic_path,cr.level_name,cr.level_desc  from customer_rating cr where cr.customer_rating_id = ?";
				contract = cs.getItemMapList(DBManager.getConnection(), contratSql, new Object[]{customInfoMap.get("customer_info_id")});
				custcomerrat = cs.getItemMapList(DBManager.getConnection(), custcomerratSql, new Object[]{customInfoMap.get("customer_rating_id")});
			}
			//如果该客户有签约的合同，则根据合同id获取合同对应的工单
			if(contract.size() != 0){
				for (int i = 0; i < contract.size(); i++ ) {
					Map contractInfoMap = (Map) contract.get(i);
					String workOrderSql = "select wo.work_order_id,wo.work_order_id_src,wo.contract_id,wo.buy_items,wo.years,wo.amount,wo.expiry_date ,(select type_name from product_type pt where pt.product_type_id = wo.product_type_id )as product_type_id from work_order wo where contract_id =  ?";
					workOrder = cs.getItemMapList(DBManager.getConnection(), workOrderSql, new Object[]{contractInfoMap.get("contract_id")});
				}
			}
		}
		//获取页面下方广告
		List<Picture> product  = getPicture();
		this.setAttribute("product",product);
		this.setAttribute("custcomerrat", custcomerrat);
		this.setAttribute("phcode", phcode);
		this.setAttribute("custom", custom);
		this.setAttribute("contract", contract);
		this.setAttribute("workOrder", workOrder);
		this.setAttribute("version", version);
		this.requestForward("/appclient/customerapp/index.jsp");
	 } 
	/**
	 * 
	 * 查看工单详细信息（进程）
	 * 
	 * @author zjl 新增日期：2014-5-16下午04:08:51
	 * @version 1.0
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void getWorkOrderDetails() throws Exception{
		int workOrderId = new Integer(request.getParameter("workOrderId"));
		String phcode = request.getParameter("pncode");
		String version = request.getParameter("version");
		String feedbackSql = "select f.feedback_id,f.work_order_id,f.service_pro_name,f.context,f.create_time from feedback f where 1=1 ";
		List<FeedBack> feedBack = new ArrayList<FeedBack>();
		List<ProductType> productType = new ArrayList<ProductType>();
		
//		product_type
		if(workOrderId != 0){
			feedbackSql += " and f.work_order_id = ? ";
			feedBack = cs.getItemMapList(DBManager.getConnection(), feedbackSql, new Object[]{workOrderId});
		}
		if(null != feedBack){  //查询工单的标准办事流程图
			String sql = "select * from product_type pt where  pt.product_type_id in(select product_type_id from work_order where work_order_id = ?)";
			productType = cs.getItemMapList(DBManager.getConnection(), sql, new Object[]{workOrderId});
		}
		//获取页面下方广告
		List<Picture> product  = getPicture();
		this.setAttribute("product",product);
		this.setAttribute("phcode", phcode);
		this.setAttribute("productType", productType);
		this.setAttribute("feedBack", feedBack);
		this.setAttribute("version", version);
		this.requestForward("/appclient/customerapp/feedback.jsp");
	}
	/**
	 * 
	 * 修改合同状态（确认合同）
	 * 
	 * @throws Exception
	 * @author zjl  2014-5-27下午04:43:57
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public void changeStart(){
		String contractId = request.getParameter("contract_id");
		Map articleMap= new HashMap();
        articleMap.put("contract_id", contractId);
        //获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        articleMap.put("modify_time", df.format(new Date()));
        articleMap.put("is_sure_contract", 1);
		try {
			cs.itemMapEdit(DBManager.getConnection(), "contract_info", " WHERE contract_id = '"+contractId+"'  ", articleMap);
			PrintWriter out = getResponse().getWriter();
			 out.print(1);
	         out.flush();
	         out.close();
	         System.out.println("合同修改成功");
		} catch (Exception e) {
			System.out.println("确认合同失败");
		}
	
	}
	
	/**
	 * 
	 * 获取页面下方的广告图片
	 * 
	 * @return
	 * @author zjl  2014-5-28 下午10:02:17
	 * @version 1.0
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public  static List<Picture> getPicture() throws Exception {
		CommonService cs = new CommonService();
		List<Picture> picture = new ArrayList<Picture>();
		String sql = "select * from picture where business_type = 3 and isdelete = 0";
		picture = cs.getItemMapList(DBManager.getConnection(), sql, new Object[]{});
		return picture;
	}
}
