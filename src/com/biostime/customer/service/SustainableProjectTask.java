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
import java.util.TimerTask;

import com.baiytfp.commonlib.base.service.CommonService;
import com.biostime.magazine.common.DBManager;

/**
 *  定时轮训 90内可持续项目数
 *	
 * @author wpl 
 * @date May 24, 2014 12:03:51 PM
 * @version 1.0
 */
public class SustainableProjectTask extends TimerTask {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//90天可持续项目，key为客户id
	private static Hashtable<String, List<Map<String, Object>>> sustainableHash = new Hashtable<String, List<Map<String, Object>>>();
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		System.out.println("启动SustainableProjectTask------start-----"+dateFormat.format(new Date()));
		StringBuffer buffer = new StringBuffer("SELECT * FROM customer_info a");
		sustainableHash.clear();	//获取数据前清空
		CommonService commonService = null;
		try {
			List<String> params = new ArrayList<String>();
			commonService = new CommonService();
			List<Map<String, Object>> list = commonService.getItemMapList(DBManager.getConnection(), buffer.toString(), params.toArray());
			if(list != null && list.size() > 0) {
				for (Map<String, Object> map : list) {
					//循环所有的客户
					StringBuffer buffer2 = new StringBuffer("select * from contract_info where customer_info_id = ? and signing_time <= ? and signing_time >= ?");
					List<String> list2 = new ArrayList<String>();
					list2.add(String.valueOf(map.get("customer_info_id")));
					
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
								several_projects = work_orders.size();
								//修改该客户的90天可持续项目
								Map<String, Integer> map3 = new HashMap<String, Integer>();
								map3.put("several_projects", work_orders.size());
								commonService.itemMapEdit(DBManager.getConnection(), "customer_info", "where customer_info_id = ?", String.valueOf(map.get("customer_info_id")), map3);
								sustainableHash.put(String.valueOf(map.get("customer_info_id")), work_orders);
							} else {
								several_projects = 0;
							}
						}
					} else {
						several_projects = 0;
					}
					
					//根据several_projects 判断，如果是0，则修改customer_info中的several_projects字段为0
					if(several_projects == 0) {
						Map<String, Integer> map3 = new HashMap<String, Integer>();
						map3.put("several_projects", 0);
						commonService.itemMapEdit(DBManager.getConnection(), "customer_info", "where customer_info_id = ?", String.valueOf(map.get("customer_info_id")), map3);
						sustainableHash.put(String.valueOf(map.get("customer_info_id")), new ArrayList<Map<String,Object>>());
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 返回成员属性 sustainableHash
	 * @return sustainableHash
	 */
	public static Hashtable<String, List<Map<String, Object>>> getSustainableHash() {
		return sustainableHash;
	}
	
}
