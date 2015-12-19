/**
 * 
 */
package com.biostime.customer.model;

/**
 * 产品服务进程状态
 *	
 * @author zjl 新增日期：2014-7-4下午02:59:03
 * @version 1.0
 */
public class FeedBackStart {

	private int feedback_status_id;
	private int product_type_id; //服务类型id
	private int status_num;  //状态标识
	private String status_name; //状态名称
	private int isdelete;// 是否删除
	private String create_time;//创建时间
	private String modify_time;//修改时间
	/**
	 * 返回成员属性 feedback_status_id
	 * @return feedback_status_id
	 */
	public int getFeedback_status_id() {
		return feedback_status_id;
	}
	/**
	 * 将传入参数feedbackStatusId 赋给成员属性 feedback_status_id
	 * @param feedbackStatusId
	 */
	public void setFeedback_status_id(int feedbackStatusId) {
		feedback_status_id = feedbackStatusId;
	}
	/**
	 * 返回成员属性 product_type_id
	 * @return product_type_id
	 */
	public int getProduct_type_id() {
		return product_type_id;
	}
	/**
	 * 将传入参数productTypeId 赋给成员属性 product_type_id
	 * @param productTypeId
	 */
	public void setProduct_type_id(int productTypeId) {
		product_type_id = productTypeId;
	}
	/**
	 * 返回成员属性 status_num
	 * @return status_num
	 */
	public int getStatus_num() {
		return status_num;
	}
	/**
	 * 将传入参数statusNum 赋给成员属性 status_num
	 * @param statusNum
	 */
	public void setStatus_num(int statusNum) {
		status_num = statusNum;
	}
	/**
	 * 返回成员属性 status_name
	 * @return status_name
	 */
	public String getStatus_name() {
		return status_name;
	}
	/**
	 * 将传入参数statusName 赋给成员属性 status_name
	 * @param statusName
	 */
	public void setStatus_name(String statusName) {
		status_name = statusName;
	}
	/**
	 * 返回成员属性 isdelete
	 * @return isdelete
	 */
	public int getIsdelete() {
		return isdelete;
	}
	/**
	 * 将传入参数isdelete 赋给成员属性 isdelete
	 * @param isdelete
	 */
	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}
	/**
	 * 返回成员属性 create_time
	 * @return create_time
	 */
	public String getCreate_time() {
		return create_time;
	}
	/**
	 * 将传入参数createTime 赋给成员属性 create_time
	 * @param createTime
	 */
	public void setCreate_time(String createTime) {
		create_time = createTime;
	}
	/**
	 * 返回成员属性 modify_time
	 * @return modify_time
	 */
	public String getModify_time() {
		return modify_time;
	}
	/**
	 * 将传入参数modifyTime 赋给成员属性 modify_time
	 * @param modifyTime
	 */
	public void setModify_time(String modifyTime) {
		modify_time = modifyTime;
	}
	
	
	
}
