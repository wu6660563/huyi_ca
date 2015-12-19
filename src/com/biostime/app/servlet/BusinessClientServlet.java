package com.biostime.app.servlet;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.baiytfp.commonlib.base.BaseServlet;
import com.baiytfp.commonlib.base.service.CommonService;
import com.biostime.magazine.common.DBManager;

/**
 * 前台商务调用的Servlet
 * 
 * @author liaokangli	时间：2014-5-14 11:18:04
 * @version 1.0
 */
public class BusinessClientServlet extends BaseServlet {
	
	
	private static final long serialVersionUID = 1L;
	
	private CommonService cs;
	private String onlyKey;
	private String magazineType;

	@Override
	public void doHandle() {
		try {
			cs = new CommonService();
			
			String methType = this.getParameter("methType");
			if (methType.equalsIgnoreCase("index")) {// 我的客户
				index();
			}else if (methType.equalsIgnoreCase("clientInfo")) {// 客户详情
				clientInfo();
			}else if (methType.equalsIgnoreCase("questionnaire")) {// 问题单
				questionnaire();
			}else if (methType.equalsIgnoreCase("workOrder")) {// 工单详情
				workOrder();
			}else if (methType.equalsIgnoreCase("salesPromotionPolicy")) {// 促销政策
				salesPromotionPolicy();
			}else if (methType.equalsIgnoreCase("community")) {// 社区公告 
				community();
			}else if (methType.equalsIgnoreCase("infoDiss")) {// 信息详情
				infoDiss();
			}else if (methType.equalsIgnoreCase("advertising")) {// 广告详情
				advertising();
			}
			
		} catch (Exception e) {
			if(!e.getMessage().equals("java.lang.IllegalStateException: Cannot forward after response has been committed")){
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * 我的客户
	 * 
	 * @throws Exception
	 * @author 时间：2014-05-14
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public void index() throws Exception {
		// 标识代码
		String flag = request.getParameter("flag");//全部客户：0,本月合作：1,90天续费：2,问题单：3
		String userId = request.getParameter("userId");
		List  customerInfoList = new ArrayList();
		JSONArray returnjsonarray = new JSONArray();
		String sql = "SELECT m.customer_info_id,m.customer_name,m.expiry_date,m.several_projects,m.total_num,m.total_count,m.last_cooperation_time  FROM customer_info m,employees e,contract_info c WHERE  m.isdelete=0 and e.isdelete=0 and  e.work_no = c.business_id and c.customer_info_id = m.customer_info_id and e.employees_id = ?";
		String customerName =  request.getParameter("customerName");
		if(customerName!=null){
			if(!"".equals(customerName)){
				sql = sql +" and m.customer_name like ?   ";
			}
		}
		
		if("0".equals(flag)){
			sql = sql +"   ORDER BY m.modify_time DESC";
		}else if("1".equals(flag)){
			sql = sql +"   and m.last_cooperation_time <  last_day(curdate()) and  m.last_cooperation_time > DATE_ADD(curdate(),interval -day(curdate())+1 day)  ORDER BY m.modify_time DESC";
		}else if("2".equals(flag)){
			sql = sql +"   and m.several_projects>0  ORDER BY m.modify_time DESC";
		}else if("3".equals(flag)){
			sql = sql +"   and m.customer_info_id in (select c.customer_info_id from contract_info c where c.contract_id in (select w.contract_id from  work_order w where w.is_question_order = 1  and  w.work_order_id in(select q.work_order_id from question q)))  ORDER BY m.modify_time DESC";
		}
		
		if(customerName!= null){
			if(!"".equals(customerName)){
				customerInfoList = cs.getItemMapList(DBManager.getConnection(),sql, new Object[]{"'%"+customerName+"%'",userId});
			}else{
				customerInfoList = cs.getItemMapList(DBManager.getConnection(),sql, new Object[]{userId});
			}
		}else{
			customerInfoList = cs.getItemMapList(DBManager.getConnection(),sql, new Object[]{userId});
		}
		
		List customerInfos = new ArrayList();
		if(customerInfoList!=null){
			for (int i = 0; i < customerInfoList.size(); i++) {
				Map customerInfoMap = (Map) customerInfoList.get(i);
				String workOrderSql="select * from work_order w where w.contract_id in(select c.contract_id from contract_info c where c.customer_info_id=? ) and w.is_question_order = 1 ";//合同信息
	     	    List workOrderList = cs.getItemMapList(DBManager.getConnection(),workOrderSql, new Object[]{customerInfoMap.get("customer_info_id")});
	     	    customerInfoMap.put("question", workOrderList.size());
	     	    customerInfos.add(customerInfoMap);
	     	    JSONObject returnjsonobject = new JSONObject();
	     	   
	     	    returnjsonobject.put("customer_info_id", customerInfoMap.get("customer_info_id"));
	     	    returnjsonobject.put("customer_name", customerInfoMap.get("customer_name"));
	     	    returnjsonobject.put("expiry_date", customerInfoMap.get("expiry_date").toString());
	     	    returnjsonobject.put("several_projects", customerInfoMap.get("several_projects"));
	     	    returnjsonobject.put("customer_info_id", customerInfoMap.get("customer_info_id"));
	     	    returnjsonobject.put("total_num", customerInfoMap.get("total_num"));
	     	    returnjsonobject.put("total_count", customerInfoMap.get("total_count"));
	     	    returnjsonobject.put("last_cooperation_time", customerInfoMap.get("last_cooperation_time").toString());
	     	    returnjsonobject.put("question", customerInfoMap.get("question"));
	     	    returnjsonarray.add(returnjsonobject);
			}
		}
		this.setAttribute("flag", flag);
		if(customerName!=null){
			 PrintWriter out = getResponse().getWriter();
			 out.print(returnjsonarray.toString());
	         out.flush();
	         out.close();
		}else{
			this.setAttribute("userId", userId);
			this.setAttribute("customerInfoList", customerInfos);
			this.requestForward("/appclient/businessclient/index.jsp");
		}
	}
	
	/**
	 * 客户详情
	 * 
	 * @throws Exception
	 * @author db 时间：2014-5-15
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public void clientInfo() throws Exception {
	   CommonService cs = new CommonService();
	   String version = request.getParameter("version");//手机版本识别
	   String customerInfoId = request.getParameter("customerInfoId");//客户详情ID
		//客户信息
	   Map customerInfoMap  = cs.itemMapRead(DBManager.getConnection(), "SELECT m.customer_name,m.main_products,m.adress,m.business_contacts,m.phone,m.total_count,m.total_num,m.expiry_date,m.several_projects  FROM customer_info m WHERE m.isdelete=0 and m.customer_info_id = ? ", new Object[]{customerInfoId});
	   String contractSql = "select * from contract_info where customer_info_id=? order by modify_time desc";//合作信息
	   List  contractList = cs.getItemMapList(DBManager.getConnection(),contractSql, new Object[]{customerInfoId});
	   List  contracts = new ArrayList();
	        for (int i = 0; i < contractList.size(); i++) {
	        	
	        	    Map contractMap = (Map) contractList.get(i);
	        	      
	        	    String pictureSql="select * from picture where isdelete = 0 and business_id=? and business_type = 1 order by modify_time desc";//图片信息
	        	   
	        	    List pictureList= cs.getItemMapList(DBManager.getConnection(),pictureSql, new Object[]{contractMap.get("contract_id")});
	        			 
	        	    contractMap.put("pictureList", pictureList);
	        	    
	        	    String workOrderSql="select product_type_id,work_order_id,buy_items,amount,expiry_date,is_question_order,schedule from work_order where contract_id=? order by modify_time desc";//工单信息
		        	   
	        	    List workOrderList= cs.getItemMapList(DBManager.getConnection(),workOrderSql, new Object[]{contractMap.get("contract_id")});
	        		for (int j = 0; j < workOrderList.size(); j++) {
	        			Map workOrderMap = (Map) workOrderList.get(i);
	        			String productTypeSql="select type_name from product_type where product_type_id=? order by modify_time desc";//工单信息
	        			Map productTypeMap = cs.itemMapRead(DBManager.getConnection(),productTypeSql, new Object[]{workOrderMap.get("product_type_id")});
	        			workOrderMap.put("typeName", productTypeMap.get("type_name"));
					}
	        	    contractMap.put("workOrderList", workOrderList);
	        	    contracts.add(contractMap);
	        	    
		  }
	      setAttribute("version", version);  
	      setAttribute("customerInfoId", customerInfoId);   
	      setAttribute("customerInfoMap", customerInfoMap);//瀹㈡埛淇℃伅
		  setAttribute("contracts", contracts);		
		  requestForward("/appclient/businessclient/customerinfo.jsp");
	}
	
	
	/**
	 * 问题单
	 * 
	 * @throws Exception
	 * @author db 时间：2014-5-15
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public void questionnaire() throws Exception {
		CommonService cs = new CommonService();
		String version = request.getParameter("version");//手机版本识别
		String customerInfoId =getParameter("customerInfoId");//客户资料ID
		String userId = request.getParameter("userId");
		//客户信息
		if(customerInfoId!=null){//单个客户问题单
			Map customerInfoMap  = cs.itemMapRead(DBManager.getConnection(), "SELECT m.customer_info_id,m.customer_name,m.phone  FROM customer_info m where m.isdelete=0 and m.customer_info_id = ?  ", new Object[]{customerInfoId});
			//合作信息
			String contractSql="select signing_time,contract_no,contract_id from contract_info where customer_info_id=? order by modify_time desc";//
			List  contractList= cs.getItemMapList(DBManager.getConnection(),contractSql, new Object[]{customerInfoId});
			List  questions = new ArrayList();
	        for (int i = 0; i < contractList.size(); i++) {
	        	    Map contractMap = (Map) contractList.get(i);
	        	    String questionSql="select * from question q  where  q.work_order_id in (select w.work_order_id from work_order w where w.contract_id=? and w.is_question_order = 1 order by w.modify_time desc)";//工单详情
	        	    List questionList = cs.getItemMapList(DBManager.getConnection(),questionSql, new Object[]{contractMap.get("contract_id")});
	        	    contractMap.put("questionList", questionList);
	        	    questions.add(contractMap);
	        }
	        setAttribute("version", version);
	        setAttribute("customerInfoMap", customerInfoMap);
		    setAttribute("questions", questions);
			this.requestForward("/appclient/businessclient/question.jsp");
		}else{
			List customerInfoLIst = cs.getItemMapList(DBManager.getConnection(), "SELECT m.customer_info_id,m.customer_name  FROM customer_info m,employees e,contract_info c WHERE m.isdelete=0  and  e.isdelete=0 and e.work_no = c.business_id and c.customer_info_id = m.customer_info_id and  e.employees_id = ?  ", new Object[]{userId});
			//合作信息
			List  questions = new ArrayList();
			for (int i = 0; i < customerInfoLIst.size(); i++) {
				 Map customerInfoMap = (Map) customerInfoLIst.get(i);
				 String contractSql="select signing_time,contract_no,contract_id from contract_info where customer_info_id=? order by modify_time desc";//鍚堝悓淇℃伅
				 List  contractList= cs.getItemMapList(DBManager.getConnection(),contractSql, new Object[]{customerInfoMap.get("customer_info_id")});
				 for (int j = 0; j < contractList.size(); j++) {
					 Map contractMap = (Map) contractList.get(i);
					 String questionSql="select * from question q  where  q.work_order_id in (select w.work_order_id from work_order w where w.contract_id=? and w.is_question_order = 1 order by w.modify_time desc)";//工单详情
		        	 List questionList = cs.getItemMapList(DBManager.getConnection(),questionSql, new Object[]{contractMap.get("contract_id")});
		        	 contractMap.put("questionList", questionList);
		        	 questions.add(contractMap);
				 }
				 questions.add(customerInfoMap);
			}
		    setAttribute("questions", questions);
		    this.requestForward("/appclient/businessclient/questionnaire.jsp");
		}
	}
	
	
	
	/**
	 * 工单详情
	 * 
	 * @throws Exception
	 * @author db 时间：2014-5-15
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public void workOrder() throws Exception {
		String version = request.getParameter("version");//手机版本识别
		String workOrderId = getParameter("workOrderId");//工单ID
		String flag = getParameter("flag");//标识：0,服务进程 1,商务流程图
		String workOrderflag = getParameter("workOrderflag");
		setAttribute("workOrderId", workOrderId);
		JSONArray returnjsonarray = new JSONArray();
		if(workOrderflag!=null){
			if("0".equals(flag)){
				String feedBackSql ="SELECT * FROM feedback f WHERE f.work_order_id = ?  order by f.modify_time desc";//服务进程信息
				List  feedBackList = cs.getItemMapList(DBManager.getConnection(),feedBackSql, new Object[]{workOrderId});
		        for (int i = 0; i < feedBackList.size(); i++) {
		        	Map feedBackMap = (Map) feedBackList.get(i);
		        	String pictureSql ="select path from picture where  isdelete = 0 and business_id=? and business_type = 2 order by modify_time desc";//图片信息
		        	List pictureList = cs.getItemMapList(DBManager.getConnection(),pictureSql, new Object[]{feedBackMap.get("feedback_id")});
		        	JSONObject returnjsonobject = new JSONObject();
		        	returnjsonobject.put("feedback_id", feedBackMap.get("feedback_id"));
		        	returnjsonobject.put("work_order_id", feedBackMap.get("work_order_id"));
		     	    returnjsonobject.put("service_pro_name", feedBackMap.get("service_pro_name"));
		     	    returnjsonobject.put("modify_time", feedBackMap.get("modify_time").toString());
		     	    returnjsonobject.put("context", feedBackMap.get("context"));
		     	    returnjsonobject.put("pictureList", pictureList);
		     	    returnjsonarray.add(returnjsonobject);
		        }
			    PrintWriter out = getResponse().getWriter();
			    out.print(returnjsonarray.toString());
		        out.flush();
		        out.close();
			}else  if("1".equals(flag)){
				String productTypeSql ="SELECT * FROM product_type p WHERE p.product_type_id = (SELECT w.product_type_id FROM work_order w WHERE w.work_order_id = ?) ";//商务类型信息
				Map productTypeMap  = cs.itemMapRead(DBManager.getConnection(), productTypeSql, new Object[]{workOrderId});
			    JSONObject returnjsonobject = new JSONObject();
			    returnjsonobject.put("path", productTypeMap.get("pic_path"));
			    PrintWriter out = getResponse().getWriter();
				out.print(returnjsonobject.toString());
		        out.flush();
		        out.close();
			}
		}else{
			if("0".equals(flag)){
				String feedBackSql ="SELECT * FROM feedback f WHERE f.work_order_id = ?  order by f.modify_time desc";//服务进程信息
				List  feedBackList = cs.getItemMapList(DBManager.getConnection(),feedBackSql, new Object[]{workOrderId});
				List  feedBacks = new ArrayList();
		        for (int i = 0; i < feedBackList.size(); i++) {
		        	    Map feedBackMap = (Map) feedBackList.get(i);
		        	    String pictureSql ="select * from picture where  isdelete = 0 and business_id=? and business_type = 2 order by modify_time desc";//图片信息
		        	    List pictureList = cs.getItemMapList(DBManager.getConnection(),pictureSql, new Object[]{feedBackMap.get("feedback_id")});
			        	feedBackMap.put("pictureList",pictureList);
		        	    feedBacks.add(feedBackMap);
		        }
		    	Map customerInfoMap  = cs.itemMapRead(DBManager.getConnection(), "SELECT  m.customer_info_id,m.customer_name,m.phone  FROM customer_info m where m.isdelete=0 and m.customer_info_id in (SELECT c.customer_info_id  FROM contract_info c where  c.contract_id in (SELECT w.contract_id FROM work_order w WHERE w.work_order_id = ?))  ", new Object[]{workOrderId});
		    	
		    	setAttribute("version", version);
		    	setAttribute("customerInfoMap", customerInfoMap);
                setAttribute("feedBacks", feedBacks);	
				this.requestForward("/appclient/businessclient/feedback.jsp");
			}
		}
	}
	
	/**
	 * 信息详情
	 * 
	 * @author xc 时间：2014-5-15
	 * @version 1.0
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void infoDiss() throws Exception {
		String infoId = this.getParameter("infoId");//淇℃伅ID
		String infoSql ="SELECT title, DATE_FORMAT(modify_time,'%Y-%m-%d  %H:%i') AS modify_time, summary ,context ,DATE_FORMAT(create_time,'%Y-%m-%d  %H:%i') AS create_time   FROM info_diss  WHERE info_id = ? ";//信息详情
		Map infoMap  = cs.itemMapRead(DBManager.getConnection(), infoSql, new Object[]{infoId});
		this.setAttribute("infoId", infoId);
		this.setAttribute("infoMap", infoMap);
	    this.requestForward("/appclient/businessclient/dissinfo.jsp");
	}
	
	
	/**
	 * 促销政策:(奖励，促销)
	 * 
	 * @author xc 时间：2014-5-14 11:29:28
	 * @version 1.0
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void salesPromotionPolicy() throws Exception {
		String annType = "";//信息类型
		String flag = this.getParameter("flag");//信息标识
		JSONArray returnjsonarray = new JSONArray();
		String infoDissSql="select * from info_diss where ann_type=? order by modify_time desc";//信息详情
		if(flag!=null){
			if("0".equals(flag)){
				annType = "3";
			}else if("1".equals(flag)){
				annType = "4";
			}
			List  infoDissList= cs.getItemMapList(DBManager.getConnection(),infoDissSql, new Object[]{annType});
			 for (int i = 0; i < infoDissList.size(); i++) {
		        	Map infoDissMap = (Map) infoDissList.get(i);
		        	JSONObject returnjsonobject = new JSONObject();
		        	returnjsonobject.put("title", infoDissMap.get("title"));
		     	    returnjsonobject.put("info_id", infoDissMap.get("info_id"));
		     	    returnjsonobject.put("modify_time", infoDissMap.get("modify_time").toString());
		     	    returnjsonobject.put("context", infoDissMap.get("context"));
		     	    returnjsonarray.add(returnjsonobject);
		     }
			PrintWriter out = getResponse().getWriter();
			out.print(returnjsonarray.toString());
	        out.flush();
	        out.close();
		}else{
			annType = this.getParameter("annType");
			List  infoDissList= cs.getItemMapList(DBManager.getConnection(),infoDissSql, new Object[]{annType});
		    setAttribute("infoDissList", infoDissList);
		    setAttribute("annType", annType);
		    this.requestForward("/appclient/businessclient/salespromotionpolicy.jsp");
		}
		
	}
	
	
	
	/**
	 * 社区公告
	 * 
	 * @author xc 时间：2014-5-14 11:29:28
	 * @version 1.0
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void community() throws Exception {
		String annType = this.getParameter("annType");//信息类型
		String flag = this.getParameter("flag");
		JSONArray returnjsonarray = new JSONArray();
		String infoDissSql="select * from info_diss where ann_type=? order by modify_time desc";//信息详情
		if(flag!=null){
			if("0".equals(flag)){
				annType = "1";
			}else if("1".equals(flag)){
				annType = "2";
			}
			List  infoDissList= cs.getItemMapList(DBManager.getConnection(),infoDissSql, new Object[]{annType});
			 for (int i = 0; i < infoDissList.size(); i++) {
		        	Map infoDissMap = (Map) infoDissList.get(i);
		        	JSONObject returnjsonobject = new JSONObject();
		        	returnjsonobject.put("title", infoDissMap.get("title"));
		     	    returnjsonobject.put("info_id", infoDissMap.get("info_id"));
		     	    returnjsonobject.put("modify_time", infoDissMap.get("modify_time").toString());
		     	    returnjsonobject.put("context", infoDissMap.get("context"));
		     	    returnjsonarray.add(returnjsonobject);
		     }
			PrintWriter out = getResponse().getWriter();
			out.print(returnjsonarray.toString());
	        out.flush();
	        out.close();
		}else{
			List  infoDissList= cs.getItemMapList(DBManager.getConnection(),infoDissSql, new Object[]{annType});
		    setAttribute("infoDissList", infoDissList);
		    setAttribute("annType", annType);
		    this.requestForward("/appclient/businessclient/community.jsp");
		}
	}
	
	
	
	/**
	 * 广告详情
	 * 
	 * @author xc 时间：2014-5-14 11:29:28
	 * @version 1.0
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void advertising() throws Exception {
		String advId = this.getParameter("advId");
		String advSql ="SELECT * FROM adv a WHERE a.adv_id = ? ";//广告详情
		Map advMap = cs.itemMapRead(DBManager.getConnection(), advSql, new Object[]{advId});
		setAttribute("advId", advId);
	    setAttribute("advMap", advMap);
	    this.requestForward("/appclient/businessclient/advertising.jsp");
	}
	
	
}
