/**
 * 
 */
package com.biostime.customer.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import com.baiytfp.commonlib.base.service.CommonService;
import com.biostime.magazine.common.DBManager;

/**
 * 
 *	
 * @author wpl 新增日期：May 24, 20148:41:01 PM
 * @version 1.0
 */
public class QueryExpiryDateTask extends TimerTask {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private Hashtable<String, String> expiryDateHash = new Hashtable<String, String>();

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		System.out.println("启动QueryExpiryDateTask------start-----"+dateFormat.format(new Date()));
		StringBuffer buffer = new StringBuffer("SELECT * FROM customer_info a");
		expiryDateHash.clear();	//获取数据前先清空
		CommonService commonService = null;
		try {
			List<String> params = new ArrayList<String>();
			commonService = new CommonService();
			List<Map<String, String>> list = commonService.getItemMapList(DBManager.getConnection(), buffer.toString(), params.toArray());
			if(list != null && list.size() > 0) {
				for (Map<String, String> map : list) {
					//循环所有的客户
					
					StringBuffer buffer2 = new StringBuffer("SELECT a.* FROM contract_info a LEFT JOIN customer_info b ON a.customer_info_id = b.customer_info_id WHERE b.customer_info_id = ?");
					List<String> list2 = new ArrayList<String>();
					list2.add(String.valueOf(map.get("customer_info_id")));
					List<Map<String, Object>> contracts = commonService.getItemMapList(DBManager.getConnection(), buffer2.toString(), list2.toArray());
					
					Double maxexiryDate = 0D;	//该客户的到期时间
					Double customer_total_count = 0D;	//该客户的累计金额数
					int  customer_total_num = 0;	//该客户的累计合作次数
					long last_cooperation_time = 0;	//该客户最近一次合作时间
					if(contracts != null && contracts.size() > 0) {
						for (Map<String, Object> map2 : contracts) {
							//循环该客户下面所有的合同
							java.sql.Timestamp signing_time = (java.sql.Timestamp) map2.get("signing_time");	//某个客户，一张合同里面的签单时间
							String sql3 = "SELECT * FROM work_order WHERE contract_id = ?";
							List<String> list3 = new ArrayList<String>();
							list3.add(String.valueOf(map2.get("contract_id")));
							List<Map<String, Object>> workorders = commonService.getItemMapList(DBManager.getConnection(), sql3, list3.toArray());
							if(signing_time.getTime() > last_cooperation_time) {
								last_cooperation_time = signing_time.getTime();
							}
							
							Double contract_total_count = 0D;	//合同累计合作总金额
							
							if(workorders != null && workorders.size() > 0) {
								for (Map<String, Object> map3 : workorders) {
									//****************到期时间*********start*****************
									Double years = Double.parseDouble(String.valueOf(map3.get("years")));	//得到该工单的到期时间
									Double expiry_date = signing_time.getTime() + years * 365 * 24 * 60 * 60 * 1000;	//一年以365天计算
									
									if(expiry_date > maxexiryDate) {
										maxexiryDate = expiry_date;	//得到最新的到期时间
									}
									long long_expiry_date = Math.round(expiry_date);
									if(long_expiry_date == 0){
										long_expiry_date = new Date().getTime();
									}
									Map<String, String> editExiryDateMap = new HashMap<String, String>();
									String expiry_date_str = dateFormat.format(new Date(long_expiry_date));
									editExiryDateMap.put("expiry_date", expiry_date_str);
									commonService.itemMapEdit(DBManager.getConnection(), "work_order", " WHERE work_order_id = ? ", String.valueOf(map3.get("work_order_id")), editExiryDateMap);
									//****************到期时间*********end*****************
								}
							}
							customer_total_num ++;	//客户累计数
							contract_total_count = Double.parseDouble(String.valueOf(map2.get("cooperation_num")));
							customer_total_count = customer_total_count + contract_total_count;	//计算该客户的累计金额数
						}
					}
					long long_maxexiryDate = Math.round(maxexiryDate);
					if(long_maxexiryDate == 0){
						long_maxexiryDate = new Date().getTime();
					}
					if(last_cooperation_time == 0){
						last_cooperation_time = new Date().getTime();
					}
					Map<String, String> editCustomerMap = new HashMap<String, String>();
					String expiry_date_str = dateFormat.format(new Date(long_maxexiryDate));
					String last_cooperation_time_str = dateFormat.format(new Date(last_cooperation_time));
					editCustomerMap.put("expiry_date", expiry_date_str);
					editCustomerMap.put("total_count", String.valueOf(customer_total_count));
					editCustomerMap.put("total_num", String.valueOf(customer_total_num));
					editCustomerMap.put("last_cooperation_time", last_cooperation_time_str);
					long num = commonService.itemMapEdit(DBManager.getConnection(), "customer_info", " WHERE customer_info_id = ? ", String.valueOf(map.get("customer_info_id")), editCustomerMap);
					
					if(num >= 0) {
						System.out.println("定时轮询修改---最新到期时间："+expiry_date_str+"---合作总金额:"+customer_total_count+"--最近一次合作时间:"+last_cooperation_time_str+"--客户id:"+String.valueOf(map.get("customer_info_id"))+":"+String.valueOf(map.get("customer_name")));
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
