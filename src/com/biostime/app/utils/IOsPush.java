package com.biostime.app.utils;
import java.io.File;

import com.biostime.report.constant.SystemConstant;

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

public class IOsPush {
	
	public boolean IOsPushSend(String message,String mobile_no) {
		try {
			// 从客户端获取的deviceToken，在此为了测试简单，写固定的一个测试设备标识。
			String deviceToken = mobile_no;
			System.out.println(deviceToken.length());
			// 定义消息模式
			PushNotificationPayload payLoad = PushNotificationPayload.complex();
			payLoad.addAlert(message);//内容
			payLoad.addBadge(1);// 消息推送标记数，小红圈中显示的数字。
			payLoad.addSound("default");
			payLoad.addCustomDictionary("type", "1");// 添加字典--描述
//			payLoad.setExpiry(5);
			// 注册deviceToken
			PushNotificationManager pushManager = new PushNotificationManager();
			// 连接APNS
//			String host = "gateway.sandbox.push.apple.com";
			//String host = "gateway.push.apple.com";
			int port = 2195;
			// 前面生成的用于JAVA后台连接APNS服务的*.p12文件位置
//			File directory = new File("");
//			String courseFile = directory.getCanonicalPath();
//			String keyPath = courseFile + File.separator + "p12" + File.separator + "hybusiness.p12";
//			System.out.println("keyPath:"+keyPath);
			String keyPath = SystemConstant.sysPath + File.separator + "WebRoot/p12" + File.separator + "aps_hypush.p12";
			//String keystore = "D:/WorkMyEclipse 8.5/Develop/Module1/hyca/WebRoot/p12/hybusiness.p12";
			// p12文件密码
			String password = "123456";
			// true：表示产品推送服务器  false：表示测试的服务器
			AppleNotificationServerBasicImpl ansbi = new AppleNotificationServerBasicImpl(keyPath, password, true);
			pushManager.initializeConnection(ansbi);
			Device device = new BasicDevice();
			device.setToken(deviceToken);
			PushedNotification notification = pushManager.sendNotification(device, payLoad, true); 
			// 发送推送
			System.out.println("推送消息: " + device.getToken() + "\n" + payLoad.toString() + " ");
			pushManager.sendNotification(device, payLoad);
//			 停止连接APNS
			pushManager.stopConnection();
//			 删除deviceToken
//			pushManager.removeDevice(deviceToken);
			System.out.println("苹果系统消息推送成功!");
			return true;
		} catch (Exception ex) {
			System.out.println("苹果系统消息推送失败："+ex.getMessage());
			return false;
		}
	}
}
