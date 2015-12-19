/**
 * 
 */
package com.biostime.customer.model;

/**
 * 登录信息表
 *	
 * @author lj  add 2014-05-13
 * 
 */
public class loginInfo {
	
	

	private String login_info_id;//登录信息ID
	private String user_name;//用户名
	private String login_time;//登录时间
	private String login_ip;//登录IP
	private String login_dev_id;//登录设备号
	private String user_type;//登录类型
	private String user_id;//用户ID号
	private String login_count;//登录次数
	
	
	
	public String getLogin_count() {
		return login_count;
	}
	public void setLogin_count(String loginCount) {
		login_count = loginCount;
	}
	public String getLogin_info_id() {
		return login_info_id;
	}
	public void setLogin_info_id(String loginInfoId) {
		login_info_id = loginInfoId;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public String getLogin_time() {
		return login_time;
	}
	public void setLogin_time(String loginTime) {
		login_time = loginTime;
	}
	public String getLogin_ip() {
		return login_ip;
	}
	public void setLogin_ip(String loginIp) {
		login_ip = loginIp;
	}
	public String getLogin_dev_id() {
		return login_dev_id;
	}
	public void setLogin_dev_id(String loginDevId) {
		login_dev_id = loginDevId;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String userType) {
		user_type = userType;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	
	
	
}
