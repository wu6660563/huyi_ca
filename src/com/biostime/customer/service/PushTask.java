/**
 * 
 */
package com.biostime.customer.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import com.baiytfp.commonlib.base.service.CommonService;
import com.biostime.app.utils.AndroidPush;
import com.biostime.app.utils.IOsPush;
import com.biostime.magazine.common.DBManager;

/**
 * 线程，推送信息
 * 
 * @author lj 新增日期：2014-5-22
 * @version 1.0
 */
public class PushTask extends TimerTask {

	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = new Date();
	AndroidPush androidPush=new AndroidPush();
	IOsPush iOsPush=new IOsPush();

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		System.out.println("启动PushTask------start-----" + dateformat.format(new Date()));
		long len = 0;
		int mm = 0;
		int ss=0;
		boolean push=false;
		try {
		
			CommonService cs = new CommonService();
			// 查询出所有的推送消息 0未推送
			List<Map> pushInfoList = cs.getItemMapList(DBManager
					.getConnection(),
					"SELECT p.push_info_id, p.recipient, p.push_context,e.mobile_no, e.mobile_os FROM push_info p  LEFT JOIN employees e  ON e.work_no = p.business_id WHERE p.push_status = 0 ",
					new Object[] {});
			if (pushInfoList != null) {
			for (Map mapPushInfo: pushInfoList) {
				
					try {
						// 调用推送接口 ：由钟建良，廖康利提供
						if(mapPushInfo.get("push_context")!=null&&mapPushInfo.get("mobile_no")!=null&&mapPushInfo.get("mobile_os")!=null){
							
							if(mapPushInfo.get("mobile_os").toString().indexOf("Android")>-1){//安卓推送
								push=androidPush.AndroidPushSend(mapPushInfo.get("push_context").toString(), mapPushInfo.get("mobile_no").toString());
								
							}else if(mapPushInfo.get("mobile_os").toString().indexOf("iPhone")>-1){//苹果推送
								push=iOsPush.IOsPushSend(mapPushInfo.get("push_context").toString(), mapPushInfo.get("mobile_no").toString());
							}
						
						if(push){
							// 修改推送状态 ：1已推送，2 推送 不成功 和推送时间
							Map map = new HashMap();
							// 推送时间
							map.put("push_time", dateformat.format(date));
							// 推送状态 成功
							map.put("push_status", "1");
							len = cs.itemMapEdit(DBManager.getConnection(),
									"push_info", "WHERE push_info_id=?", mapPushInfo.get("push_info_id").toString()
											, map);
							if (len > 0) {
								 mm++;
								System.out.println("员工ID"+mapPushInfo.get("recipient").toString()
										+ "信息推送成功!");
							} else {
								System.out.println("员工ID"+mapPushInfo.get("recipient").toString()
										+ "信息推送失败!");
								ss++;
							}
						}else{
							// 修改推送状态 ：1已推送，2 推送 不成功 和推送时间
							Map map = new HashMap();
							// 推送时间
							map.put("push_time", dateformat.format(date));
							// 推送状态 失败
							map.put("push_status", "2");
							len = cs.itemMapEdit(DBManager.getConnection(),
									"push_info", "WHERE push_info_id=?", mapPushInfo.get("push_info_id").toString(), map);
							System.out.println("员工ID"+mapPushInfo.get("recipient").toString() + "信息推送失败!");
							ss++;
						}
					}
					System.out.println("无法进行推送可能是内容(push_context)为空,设备号(mobile_no)为空,设备系统(mobile_os)为空"+mapPushInfo.get("push_info_id").toString());
					} catch (Exception e) {
						// 修改推送状态 ：1已推送，2 推送 不成功 和推送时间
						Map map = new HashMap();
						// 推送时间
						map.put("push_time", dateformat.format(date));
						// 推送状态 失败
						map.put("push_status", "2");
						len = cs.itemMapEdit(DBManager.getConnection(),
								"push_info", "WHERE push_info_id=?", mapPushInfo.get("push_info_id").toString(), map);
						System.out.println("员工ID"+mapPushInfo.get("recipient").toString() + "信息推送失败!"+e.getMessage());
						ss++;
					}
				}
			}

		} catch (Exception e1) {
			System.out.println("系统数据出错"+e1.getMessage());
		}
		System.out.println("商务信息推送成功" + mm+"条 失败"+ss+"条");
	}

}
