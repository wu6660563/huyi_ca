/**
 * 
 */
package com.biostime.report.constant;

/**
 *	
 * @author wpl 
 * @date May 11, 2014 8:35:57 PM
 * @version 1.0
 */
public class SystemConstant {
	
	/**
	 * 客户
	 */
	public final static String CUSTOMERINFO = "CUSTOMERINFO";
	
	/**
	 * 合同
	 */
	public final static String CONTRACT = "CONTRACT";
	
	/**
	 * 工单
	 */
	public final static String ORDER = "ORDER";
	
	/**
	 * 服务进程
	 */
	public final static String PROCESS = "PROCESS";
	
	/**
	 * 问题
	 */
	public final static String QUESSTION = "QUESSTION";
	
	/**
	 * 员工
	 */
	public final static String EMPLOYEE = "EMPLOYEE";
	
	/**
	 * 业务资讯
	 */
	public final static String INFODISS = "INFODISS";
	
	/**
	 * 产品类型
	 */
	public final static String PRODUCTTYPE = "PRODUCTTYPE";
	
	/**
	 * 获取数据类型 ONLINE、在线 OFFLINE、离线
	 * 在线为未启动了task，离线为启动了task，直接查询数据库字段
	 * 调用地方为CustomerService类
	 */
	public final static String ONLINE = "ONLINE";
	
	/**
	 * 获取数据类型 ONLINE、在线 OFFLINE、离线
	 * 在线为未启动了task，离线为启动了task，直接查询数据库字段
	 * 调用地方为CustomerService类
	 */
	public final static String OFFLINE = "OFFLINE";
	
	/**
	 * CustomerService 需要判断 是否启动
	 */
	public final static String QUERYEXPIRYDATETASK = "QUERYEXPIRYDATETASK";
	
	/**
	 * CustomerService 需要判断 是否启动
	 */
	public final static String SUSTAINABLEPROJECTTASK = "SUSTAINABLEPROJECTTASK";
	
	/**
	 * CustomerService 需要判断 是否启动
	 */
	public final static String PUSHTASK = "PUSHTASK";
	
	public static String sysPath = "";
}
