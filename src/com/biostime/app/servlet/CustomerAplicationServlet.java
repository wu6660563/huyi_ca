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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.baiytfp.commonlib.base.BaseServlet;
import com.baiytfp.commonlib.base.service.CommonService;
import com.baiytfp.commonlib.util.ResponseUtil;
import com.biostime.customer.model.ContractInfo;
import com.biostime.customer.model.CustomerInfo;
import com.biostime.customer.model.FeedBack;
import com.biostime.customer.model.FeedBackStart;
import com.biostime.customer.model.InfoDiss;
import com.biostime.customer.model.WorkOrder;
import com.biostime.magazine.common.DBManager;

/**
 * 获取客户APP数据servlet
 *	
 * @author zjl 新增日期：2014-6-27 下午06:29:52
 * @version 1.0
 */
public class CustomerAplicationServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private CommonService cs;
	@Override
	public void doHandle() {
		try {
			cs = new CommonService();
			String method = this.getParameter("method");
			if(null != method && !"".equals(method) && method.equalsIgnoreCase("index")){//进入首页,得到当前登录人信息
				getIndexList();
			}else if(null != method && !"".equals(method) && method.equalsIgnoreCase("searchWord")){ //查看所有商品信息
				searchWord();
			}else if(null != method && !"".equals(method) && method.equalsIgnoreCase("feedbackList")){ //查看服务进程
				feedbackList();
			} else if(null != method && !"".equals(method) && method.equalsIgnoreCase("getInfoDiss")){ //查看促销详情
				getInfoDiss();
			} else if(null != method && !"".equals(method) && method.equalsIgnoreCase("registerUser")){ //注册用户
				registerUser();
			}else if(null != method && !"".equals(method) && method.equalsIgnoreCase("updatePwd")){  //修改密码
				updatePwd();
			}else if(null != method && !"".equals(method) && method.equalsIgnoreCase("resetPwd")){  //找回密码
				resetPwd();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 找回密码
	 * 
	 * @author zjl 新增日期：2014-6-27下午09:35:19
	 * @version 1.0
	 */
	private void resetPwd() {
		String userId = this.getParameter("userId");
		String newPwd = this.getParameter("newPwd");
		String phone = this.getParameter("phone");
		Map<String, String> articleMap= new HashMap<String, String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		long resultNum = 0;
		try {
			articleMap.put("pswd", newPwd);
			resultNum = cs.itemMapEdit(DBManager.getConnection(), "customer_info", " WHERE customer_info_id = '" + userId + "' and phone = '"+phone+"'", articleMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultNum = 0;
		} finally {
			if(resultNum == 0) {
				resultMap.put("status", false);
				resultMap.put("result", "修改失败！");
			} else if(resultNum > 0) {
				resultMap.put("cId",userId);
				resultMap.put("status", true);
				resultMap.put("result", "修改成功！");
				System.out.println("-----修改密码成功------");
			}
			try {
				ResponseUtil.sendJsonObj(response, resultMap);
			} catch (Exception e) {
				System.err.println("返回JSON数据出错");
			}
		}
		
	}
	
	/**
	 * 修改密码
	 * 
	 * @author zjl 新增日期：2014-6-27下午09:35:15
	 * @version 1.0
	 */
	private void updatePwd() {
		String userId = this.getParameter("userId");
		String newPwd = this.getParameter("newPwd");
		String pwd = this.getParameter("pwd");
		Map<String, String> articleMap= new HashMap<String, String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		long resultNum = 0;
		try {
			articleMap.put("pswd", newPwd);
			resultNum = cs.itemMapEdit(DBManager.getConnection(), "customer_info", " WHERE customer_info_id = '" + userId + "' AND pswd = '" + pwd + "'", articleMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultNum = 0;
		} finally {
			if(resultNum == 0) {
				resultMap.put("status", false);
				resultMap.put("result", "原密码错误，修改失败！");
			} else if(resultNum > 0) {
				resultMap.put("cId",userId);
				resultMap.put("status", true);
				resultMap.put("result", "修改成功！");
				System.out.println("-----修改密码成功------");
			}
			try {
				ResponseUtil.sendJsonObj(response, resultMap);
			} catch (Exception e) {
				System.err.println("返回JSON数据出错");
			}
		}
	}
	/**
	 * 注册用户
	 * 
	 * @author zjl 新增日期：2014-6-27下午09:35:13
	 * @version 1.0
	 */
	private void registerUser() {
		String companyName = this.getParameter("companyName");
		String businessContacts = this.getParameter("businessContacts");
		String phone = this.getParameter("phone");
		String password = this.getParameter("password");
		Map<String, String> updateMap= new HashMap<String, String>();
		Map<String, String> articleMap= new HashMap<String, String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		long resultNum = 0;
		String userId = "0";
		try {
		 	articleMap.put("isdelete", "0");
			articleMap.put("pswd", password);
			resultNum = cs.itemMapEdit(DBManager.getConnection(), "customer_info", " WHERE company_name = '" + companyName + "' AND business_contacts = '" + businessContacts + "' and phone = '"+phone+"'", articleMap);
			String sql = "select * from customer_info where company_name = ? AND business_contacts = ? and phone = ?  ";
			Map map = cs.itemMapRead(DBManager.getConnection(), sql, new Object[]{ companyName,businessContacts,phone });
			userId = map.get("customer_info_id").toString();
		} catch (Exception e) {
			e.printStackTrace();
			resultNum = 0;
		} finally {
			try {
				PrintWriter out = getResponse().getWriter();
			    out.print(userId);
		        out.flush();
		        out.close();
			} catch (Exception e) {
				System.err.println("返回JSON数据出错");
			}
		}
		
	}
	/**
	 * 
	 * 获取首页数据
	 * 
	 * @author zjl 新增日期：2014-6-26下午04:38:49
	 * @version 1.0
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void getIndexList() throws Exception{
		//客户信息
		String userId = request.getParameter("userId");
		String customerListSql = "select cs.customer_info_id," + //信息id
				"cs.customer_id,"+ //客户id
				"cs.cust_service_id,"+ //客服id
				"cs.cust_service,"+ //专属客服
				"cs.business_contacts,"+//业务联系人
				"cs.phone,"+ //联系电话
				"cs.login_name,"+ //登录名称（公司名称）
				"(select employees_name from employees ey where ey.employees_id = cs.cust_service_id) kefuName,"+  //专属客服
				"(select mobile_phone from employees ey where ey.employees_id = cs.cust_service_id) kefuPhone,"+//客服电话
				"(select count(*) from contract_info ci where ci.customer_info_id = cs.customer_info_id) several_projects "+  //合作次数
				" from customer_info cs where cs.customer_info_id = ? ";
		 List<CustomerInfo>  custom = new ArrayList<CustomerInfo>();
		 custom = cs.getItemMapList(DBManager.getConnection(), customerListSql, new Object[]{userId});
		 //客户所购买的商品,先获取客户签约的合同
		 String contratSql = "select ci.contract_id  from contract_info ci where customer_info_id in (select customer_info_id from customer_info cs where cs.customer_info_id = ?)";
		 List<ContractInfo> contract = new ArrayList<ContractInfo>();
		 contract = cs.getItemMapList(DBManager.getConnection(), contratSql, new Object[]{userId});
		 String ratinId = "";
		 if(contract.size() != 0){
			for (int i = 0; i < contract.size(); i++ ) {
				Map contractInfoMap = (Map) contract.get(i);
				ratinId +=String.valueOf(contractInfoMap.get("contract_id"))+",";
			}
		}
		 ratinId = ratinId.substring(0,ratinId.length()-1);
		//客户有签约的合同 ，获取合同所购买的产品
		 List<WorkOrder> workOrder = new ArrayList<WorkOrder>();
		if(ratinId.length()>0){
			String orderSql = "select (select pt.type_name from product_type pt where pt.product_type_id = wo.product_type_id)  itemName ," +//商品名称
					"(select pt.pic_path from product_type pt where pt.product_type_id = wo.product_type_id)  path ," + 
					"count(*) amount, " + //数量
					"wo.product_type_id," +  //类型id
					"wo.contract_id," + //合同id
					"(select count(*)issearch from work_order wr where wr.is_search = 0) is_question_order " + 
					"from work_order wo where wo.contract_id in (?) group by itemName";
			workOrder = cs.getItemMapList(DBManager.getConnection(), 
					orderSql, new Object[]{ratinId});
		}
		//促销广告
		List<InfoDiss> product  = getPicture();
		this.setAttribute("product",product);
		this.setAttribute("custom",custom);
		this.setAttribute("workOrder",workOrder);
		this.requestForward("/appclient/application/index.jsp");
	}
	
	/**
	 * 
	 * 查看不同类型下面的商品信息
	 * 
	 * @author zjl 新增日期：2014-6-26下午05:50:23
	 * @version 1.0
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public  void searchWord() throws Exception{
		List<WorkOrder> workOrder = new ArrayList<WorkOrder>();
		String contractId = this.getParameter("contractId");
		String productTypeId = this.getParameter("productTypeId");
		String orderSql = "select  (select pt.type_name from product_type pt where pt.product_type_id = wo.product_type_id)  typeName," + //商品类型
				"(select ci.signing_time from contract_info ci where ci.contract_id = wo.contract_id ) modify_time," +   //合同签约时间
				"(select status_name from feedback_status fs where fs.feedback_status_id =(select  fb.feedback_status from feedback fb where fb.work_order_id = wo.work_order_id order by fb.feedback_status desc limit 1 )) sname," +   //状态名称
				"wo.buy_items,wo.product_type_id,wo.amount,wo.years,wo.work_order_id,wo.expiry_date,is_search " +
				"from work_order wo where wo.contract_id=?  and wo.product_type_id= ?  order by is_search asc";
		workOrder = cs.getItemMapList(DBManager.getConnection(), orderSql, new Object[]{contractId,productTypeId});
		//促销广告
		List<InfoDiss> product  = getPicture();
		this.setAttribute("product",product);
		this.setAttribute("workOrder", workOrder);
		this.requestForward("/appclient/application/workList.jsp");
	}
	
	/**
	 * 查看商品的服务进程
	 * 
	 * 
	 * @author zjl 新增日期：2014-6-26下午06:14:39
	 * @version 1.0
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void feedbackList() throws Exception{
		String orderId = this.getParameter("wordId");
		String productTypeId = this.getParameter("productTypeId");
		List<FeedBack> feedBack = new ArrayList<FeedBack>();
		String feedBackSql = "select f.feedback_id,f.work_order_id,f.service_pro_name,f.context,f.create_time,f.feedback_status, " +
				"(select status_name from feedback_status fs where fs.feedback_status_id =  f.feedback_status) stautsName" +
				" from feedback f where f.work_order_id = ? order by feedback_status desc ";
		feedBack = cs.getItemMapList(DBManager.getConnection(), feedBackSql, new Object[]{orderId});
		//单独获取产品的流程状态
		List<FeedBack> feedBackStart = new ArrayList<FeedBack>();
		String  startOrderSql = " select feedback_status from feedback where work_order_id = ? order by feedback_status desc limit 1";
		feedBackStart = cs.getItemMapList(DBManager.getConnection(), startOrderSql, new Object[]{orderId});
		String start = "";
		 if(feedBackStart.size() != 0){
			for (int i = 0; i < feedBackStart.size(); i++ ) {
				Map contractInfoMap = (Map) feedBackStart.get(i);
				start +=String.valueOf(contractInfoMap.get("feedback_status"));
			}
		}
		 //查看该类产品的审批状态
		 String feedbackStart = "select fs.status_num,fs.status_name from feedback_status fs where product_type_id = ? and isdelete = 0 order by status_num asc";
		 List<FeedBackStart> fbs = new ArrayList<FeedBackStart>();
		 fbs = cs.getItemMapList(DBManager.getConnection(), feedbackStart, new Object[]{productTypeId});
		 JSONArray returnjsonarray = new JSONArray();
		 for (int i = 0; i < fbs.size(); i++) {
	        	Map feedBackMap = (Map) fbs.get(i);
	        	JSONObject returnjsonobject = new JSONObject();
	        	returnjsonobject.put("status_num", feedBackMap.get("status_num"));
	        	returnjsonobject.put("status_name", feedBackMap.get("status_name"));
	     	    returnjsonarray.add(returnjsonobject);
	        }
		 //修改此产品已经查看
	 	Map articleMap= new HashMap();
        //获取当前时间
        articleMap.put("is_search", 1);
		cs.itemMapEdit(DBManager.getConnection(), "work_order", " WHERE work_order_id = '"+orderId+"'  ", articleMap);
		//促销广告
		List<InfoDiss> product  = getPicture();
		this.setAttribute("product",product);
		this.setAttribute("feedBack",feedBack);
		this.setAttribute("feedbackStart", fbs);
		this.setAttribute("start",start);
		this.setAttribute("size", fbs.size());
		this.requestForward("/appclient/application/feedbackStart.jsp");
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
	public List<InfoDiss> getPicture() throws Exception {
		List<InfoDiss> picture = new ArrayList<InfoDiss>();
		String sql = "select info_id,path from info_diss where ann_type = 4 and is_delete = 0 order by info_id desc limit 4";
		picture = cs.getItemMapList(DBManager.getConnection(), sql, new Object[]{});
		return picture;
	}
	/**
	 * 
	 * 查看促销详情
	 * 
	 * @author zjl 新增日期：2014-6-27上午12:35:33
	 * @version 1.0
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void getInfoDiss() throws Exception{
		String id = this.getParameter("info_id");
		List<InfoDiss> picture = new ArrayList<InfoDiss>();
		String sql = " select * from info_diss where info_id = ? ";
		picture = cs.getItemMapList(DBManager.getConnection(), sql, new Object[]{id});
		this.setAttribute("picture", picture);
		this.requestForward("/appclient/application/aritle.jsp");
	}
	
	
}
