/**
 * 
 */
package com.biostime.customer.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.baiytfp.commonlib.base.service.CommonService;
import com.baiytfp.commonlib.util.StringUtils;
import com.biostime.magazine.common.DBManager;
import com.biostime.report.constant.SystemConstant;

/**
 * CustomerService 用于远程app调用数据服务类
 *	
 * @author wpl 
 * @date May 25, 2014 9:06:42 AM
 * @version 1.0
 */
public class CustomerService {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 
	 * 
	 * @param customer_info_id 客户资料id
	 * @param getDataType ONLINE or not
	 * @return 90天内所有的list
	 * @author wpl 
	 * @date ：May 25, 2014 9:55:28 AM
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getSustProData(String customer_info_id, String getDataType) {
		CommonService commonService = null;
		List<Map<String, Object>> retunList = null;
		try {
			commonService = new CommonService();
			
			Boolean task_is_start = false;
			if(TaskFactory.getTaskList() != null && TaskFactory.getTaskList().size() > 0) {
				for (Hashtable<String,String> hash : TaskFactory.getTaskList()) {
					String id = hash.get("id");
					Boolean isStart = Boolean.valueOf(hash.get("start"));
					if(SystemConstant.SUSTAINABLEPROJECTTASK.equals(id) && isStart) {
						task_is_start = true;
						break;
					}
				}
			}
			if(!StringUtils.isBlank(getDataType) && task_is_start) {
				getDataType = SystemConstant.OFFLINE;
			} else {
				getDataType = SystemConstant.ONLINE;
			}
			if(SystemConstant.ONLINE.equalsIgnoreCase(getDataType)) {
				//在线获取
				StringBuffer buffer2 = new StringBuffer("select * from contract_info where customer_info_id = ? and signing_time <= ? and signing_time >= ?");
				List<String> list2 = new ArrayList<String>();
				list2.add(customer_info_id);
				
				Calendar calendar = Calendar.getInstance();
				list2.add(dateFormat.format(calendar.getTime()));
				calendar.add(Calendar.DATE, -90);
				list2.add(dateFormat.format(calendar.getTime()));
				List<Map<String, Object>> contracts = commonService.getItemMapList(DBManager.getConnection(), buffer2.toString(), list2.toArray());
				
				//项目总数
				Integer several_projects = 0;
				if(contracts != null && contracts.size() > 0) {
					for (Map<String, Object> map2 : contracts) {
						StringBuffer buffer3 = new StringBuffer("select * from work_order where contract_id = ? ");
						List<String> list3 = new ArrayList<String>();
						list3.add(String.valueOf(map2.get("contract_id")));
						List<Map<String, Object>> work_orders = commonService.getItemMapList(DBManager.getConnection(), buffer3.toString(), list3.toArray());
						//找到该客户90天内所有的合同，并得到所有合同下面的所有项目数
						if(work_orders != null && work_orders.size() > 0) {
							retunList = work_orders;
						} else {
							several_projects = 0;
						}
					}
				} else {
					several_projects = 0;
				}
				
				//根据several_projects 判断，如果是0，则修改customer_info中的several_projects字段为0
				if(several_projects == 0) {
					retunList = new ArrayList<Map<String, Object>>();
				}
				
			} else {
				//离线获取，直接数据库中取
				if(SustainableProjectTask.getSustainableHash() != null && SustainableProjectTask.getSustainableHash().size() > 0) {
					retunList = SustainableProjectTask.getSustainableHash().get(customer_info_id);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retunList;
	}
	
	/**
	 * 
	 * 
	 * @param customer_info_id 客户资料id
	 * @param getDataType ONLINE or OFFLINE
	 * @return 日期
	 * @author wpl 
	 * @date ：May 25, 2014 9:54:43 AM
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public String getExiryDateData(String customer_info_id, String getDataType) {
		CommonService commonService = null;
		String returnData = null;
		try {
			commonService = new CommonService();
			
			Boolean task_is_start = false;
			if(TaskFactory.getTaskList() != null && TaskFactory.getTaskList().size() > 0) {
				for (Hashtable<String,String> hash : TaskFactory.getTaskList()) {
					String id = hash.get("id");
					Boolean isStart = Boolean.valueOf(hash.get("start"));
					if(SystemConstant.QUERYEXPIRYDATETASK.equals(id) && isStart) {
						task_is_start = true;
						break;
					}
				}
			}
			if(!StringUtils.isBlank(getDataType) && task_is_start) {
				getDataType = SystemConstant.OFFLINE;
			} else {
				getDataType = SystemConstant.ONLINE;
			}
			if(SystemConstant.ONLINE.equalsIgnoreCase(getDataType)) {
				//在线获取
				StringBuffer buffer2 = new StringBuffer("SELECT a.* FROM contract_info a LEFT JOIN customer_info b ON a.customer_info_id = b.customer_info_id WHERE b.customer_info_id = ?");
				List<String> list2 = new ArrayList<String>();
				list2.add(customer_info_id);
				List<Map<String, Object>> workorders = commonService.getItemMapList(DBManager.getConnection(), buffer2.toString(), list2.toArray());
				
				long expiry_date_temp = Calendar.getInstance().getTime().getTime();	//得到最新的到期时间
				Double total_count = 0D;	//累计合作总金额
				long last_cooperation_time = 0;	//得到最新的合作时间（签约时间）
				if(workorders != null && workorders.size() > 0) {
					for (Map<String, Object> map2 : workorders) {
						java.sql.Date sql_expiry_date = (java.sql.Date) map2.get("expiry_date");
						
						long expiry_date_times = sql_expiry_date.getTime();
						if(expiry_date_times >= expiry_date_temp) {
							expiry_date_temp = expiry_date_times;	//得到最新的到期时间
						}
						
						//计算累计合作金额
						total_count = total_count + Double.parseDouble(String.valueOf(map2.get("amount")));
					}
				}
				
				//得到上一次合作时间
				StringBuffer buffer3 = new StringBuffer("SELECT * FROM contract_info a LEFT JOIN customer_info b ON a.customer_info_id = b.customer_info_id ");
				buffer3.append("WHERE b.customer_info_id = ? ORDER BY signing_time DESC LIMIT 0,1");
				List<String> list3 = new ArrayList<String>();
				list3.add(customer_info_id);
				List<Map<String, Object>> contracts = commonService.getItemMapList(DBManager.getConnection(), buffer3.toString(), list3.toArray());
				if(contracts != null && contracts.size() > 0) {
					Map<String, Object> map2 = contracts.get(0);
					java.sql.Date signing_time = (java.sql.Date) map2.get("signing_time");
					last_cooperation_time = signing_time.getTime();
				}
				
				String expiry_date_str = null;
				String last_cooperation_time_str = null;
				expiry_date_str = dateFormat.format(new Date(expiry_date_temp));
				//修改数据
				Map<String, Object> map3 = new HashMap<String, Object>();
				map3.put("expiry_date", expiry_date_str);	//最新到期时间
				map3.put("total_count", total_count);	//合作总金额
				if(last_cooperation_time != 0){
					last_cooperation_time_str = dateFormat.format(new Date(last_cooperation_time));
					map3.put("last_cooperation_time", last_cooperation_time_str);	//最近一次合作时间
				}
				returnData = JSONObject.fromObject(map3).toString();
			} else {
				//离线获取
				StringBuffer buffer = new StringBuffer("SELECT * FROM customer_info WHERE customer_info_id = ? ");
				List<String> params = new ArrayList<String>();
				params.add(customer_info_id);
				List<Map<String, Object>> customers = commonService.getItemMapList(DBManager.getConnection(), buffer.toString(), params.toArray());
				if(customers != null && customers.size() > 0) {
					Map<String, Object> map = customers.get(0);
					java.sql.Date sql_expiry_date = (java.sql.Date) map.get("expiry_date");
					returnData =  dateFormat.format(new Date(sql_expiry_date.getTime()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
}
