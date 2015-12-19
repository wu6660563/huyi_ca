/**
 * 
 */
package com.biostime.customer.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.baiytfp.commonlib.util.StringUtils;

/**
 * 创建Task的工厂类
 *	
 * @author wpl 
 * @date May 24, 2014 11:59:17 AM
 * @version 1.0
 */
public class TaskFactory {
	
	private Timer timer = new Timer();
	
	/**
	 * 默认时间间隔
	 */
	private long scheTime = 5;	//默认5分钟
	/**
	 * 默认延时 单位 毫秒
	 */
	private long delay = 100;
	
	private String taskXml = "task.xml";
	
	private static List<Hashtable<String,String>> taskList = new ArrayList<Hashtable<String,String>>();	//配置文件的hash
	
	/**
	 * 
	 *
	 * @author wpl 
	 * @date May 24, 2014 2:31:05 PM
	 * @version 1.0
	 */
	public TaskFactory() {
		init();
		createTask();
	}
	
	private void init() {
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document document = null;
		try {
			String path = this.getClass().getClassLoader().getResource("").getPath();
			String filePath = path + taskXml;
			
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			document = builder.parse(filePath);
			
			NodeList tasks = document.getElementsByTagName("task");
			for (int i = 0; i < tasks.getLength(); i++) {
				Element node = (Element) tasks.item(i);
				
				Hashtable<String, String> hash = new Hashtable<String, String>();
				hash.put("id", node.getElementsByTagName("id").item(0).getTextContent());
				hash.put("task-class", node.getElementsByTagName("task-class").item(0).getTextContent());
				hash.put("describle", node.getElementsByTagName("describle").item(0).getTextContent());
				hash.put("start", node.getElementsByTagName("start").item(0).getTextContent());
				hash.put("delay", node.getElementsByTagName("delay").item(0).getTextContent());
				hash.put("scheTime", node.getElementsByTagName("scheTime").item(0).getTextContent());
				taskList.add(hash);
			}
			System.out.println(filePath + "---------文件加载成功！----------");
		} catch (Exception e) {
			System.out.println("初始化加载XML出错！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建任务
	 * 
	 * @author wpl 
	 * @date ：May 25, 2014 9:03:35 AM
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	private void createTask() {
		Integer num = 0;
		if(taskList != null && taskList.size() > 0) {
			try {
				for (Hashtable<String, String> hash : taskList) {
					String className = hash.get("task-class");
					Boolean isStart = Boolean.valueOf(hash.get("start"));
					Long thedelay = Long.valueOf(hash.get("delay"));
					Long thescheTime = Long.valueOf(hash.get("scheTime"));
					
					if(thescheTime == 0) {
						thescheTime = scheTime;
						
					}
					if(thedelay == 0) {
						thedelay = delay;
					}
					if(!StringUtils.isBlank(className) && isStart) {
						TimerTask clazz = (TimerTask) Class.forName(className).newInstance();
						timer.schedule(clazz, thedelay, thescheTime * 60 * 1000);
						num ++;
					}
				}
			} catch (Exception e) {
				System.out.println("创建任务异常！");
				e.printStackTrace();
			}
		}
		System.out.println("成功创建定时任务数："+num);
	}

	/**
	 * 返回成员属性 taskList
	 * @return taskList
	 */
	public static List<Hashtable<String, String>> getTaskList() {
		return taskList;
	}
	
}
