/**
 * 
 */
package com.biostime.customer.model;

/**
 * 员工表
 *	
 * @author lj add 2014-05-13
 * 
 */
public class Employees {
	
	private String employees_id;//员工ID
	private String work_no;//工号
	private String employees_name;//	员工名称
	private String phone;//电话
	
	/**
	 * 大区
	 */
	private String large_area;
	
	private String branch;//分公司
	private String department;//所属部门
	private String job_title;//职位名称
	private String mobile_phone;//手机号
	private String mobile_no;//手机设备号
	private String mobile_os;//手机设备系统
	private String login_role;//登录角色
	private String job_status;//离职状态
	private String login_name;//登录账号
	private String pswd;//登录密码
	private String isdelete;//是否删除
	private String create_time;//创建时间
	private String modify_time;//修改时间
	
	
	public String getEmployees_id() {
		return employees_id;
	}
	public void setEmployees_id(String employeesId) {
		employees_id = employeesId;
	}
	public String getWork_no() {
		return work_no;
	}
	public void setWork_no(String workNo) {
		work_no = workNo;
	}
	public String getEmployees_name() {
		return employees_name;
	}
	public void setEmployees_name(String employeesName) {
		employees_name = employeesName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getJob_title() {
		return job_title;
	}
	public void setJob_title(String jobTitle) {
		job_title = jobTitle;
	}
	public String getMobile_phone() {
		return mobile_phone;
	}
	public void setMobile_phone(String mobilePhone) {
		mobile_phone = mobilePhone;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobileNo) {
		mobile_no = mobileNo;
	}
	public String getMobile_os() {
		return mobile_os;
	}
	public void setMobile_os(String mobileOs) {
		mobile_os = mobileOs;
	}
	public String getLogin_role() {
		return login_role;
	}
	public void setLogin_role(String loginRole) {
		login_role = loginRole;
	}
	public String getJob_status() {
		return job_status;
	}
	public void setJob_status(String jobStatus) {
		job_status = jobStatus;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String loginName) {
		login_name = loginName;
	}
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	public String getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String createTime) {
		create_time = createTime;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modifyTime) {
		modify_time = modifyTime;
	}
	/**
	 * 返回成员属性 large_area
	 * @return large_area
	 */
	public String getLarge_area() {
		return large_area;
	}
	/**
	 * 将传入参数large_area 赋给成员属性 large_area
	 * @param large_area
	 */
	public void setLarge_area(String large_area) {
		this.large_area = large_area;
	}
	
	
}
