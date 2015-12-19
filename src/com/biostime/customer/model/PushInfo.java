/**
 * 
 */
package com.biostime.customer.model;

/**
 *  推送信息
 *	
 * @author lj  add 2014-05-13
 * 
 */
public class PushInfo {
	
	
	private String push_info_id;//推送信息id
	private String push_context;//推送内容
	private String info_type;//信息类别(业务类型)
	private String business_id;//业务ID
	private String push_status;//推送状态
	private String recipient;//	接收人
	private String mobile_no;//手机设备号
	private String mobile_os;//手机设备系统
	private String push_time;//信息推送时间
	private String edit_time;//	信息编辑时间
	private String employees_name;//商务名称
	private String recipient_type;//接收人类型
	
	
	public String getRecipient_type() {
		return recipient_type;
	}
	public void setRecipient_type(String recipientType) {
		recipient_type = recipientType;
	}
	public String getEmployees_name() {
		return employees_name;
	}
	public void setEmployees_name(String employeesName) {
		employees_name = employeesName;
	}
	public String getPush_info_id() {
		return push_info_id;
	}
	public void setPush_info_id(String pushInfoId) {
		push_info_id = pushInfoId;
	}
	public String getPush_context() {
		return push_context;
	}
	public void setPush_context(String pushContext) {
		push_context = pushContext;
	}
	public String getInfo_type() {
		return info_type;
	}
	public void setInfo_type(String infoType) {
		info_type = infoType;
	}
	public String getBusiness_id() {
		return business_id;
	}
	public void setBusiness_id(String businessId) {
		business_id = businessId;
	}
	public String getPush_status() {
		return push_status;
	}
	public void setPush_status(String pushStatus) {
		push_status = pushStatus;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
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
	public String getPush_time() {
		return push_time;
	}
	public void setPush_time(String pushTime) {
		push_time = pushTime;
	}
	public String getEdit_time() {
		return edit_time;
	}
	public void setEdit_time(String editTime) {
		edit_time = editTime;
	}
	
	
	
	
}
	
