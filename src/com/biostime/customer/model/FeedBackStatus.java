/**
 * 
 */
package com.biostime.customer.model;

/**
 * 
 *	
 * @author wpl 
 * @date Jun 30, 2014 3:37:13 PM
 * @version 1.0
 */
public class FeedBackStatus {
	
	/**
	 * id
	 */
	private int feedback_status_id;
	
	/**
	 * 类型ID
	 */
	private int product_type_id;
	
	/**
	 * 状态序号
	 */
	private int status_num;
	
	/**
	 * 状态名称
	 */
	private String status_name;
	
	/**
	 * 是否删除
	 */
	private int isdelete;
	
	/**
	 * 创建时间
	 */
	private String create_time;
	
	/**
	 * 修改时间
	 */
	private String modify_time;

	/**
	 * 返回成员属性 feedback_status_id
	 * @return feedback_status_id
	 */
	public int getFeedback_status_id() {
		return feedback_status_id;
	}

	/**
	 * 将传入参数feedback_status_id 赋给成员属性 feedback_status_id
	 * @param feedback_status_id
	 */
	public void setFeedback_status_id(int feedback_status_id) {
		this.feedback_status_id = feedback_status_id;
	}

	/**
	 * 返回成员属性 product_type_id
	 * @return product_type_id
	 */
	public int getProduct_type_id() {
		return product_type_id;
	}

	/**
	 * 将传入参数product_type_id 赋给成员属性 product_type_id
	 * @param product_type_id
	 */
	public void setProduct_type_id(int product_type_id) {
		this.product_type_id = product_type_id;
	}

	/**
	 * 返回成员属性 status_num
	 * @return status_num
	 */
	public int getStatus_num() {
		return status_num;
	}

	/**
	 * 将传入参数status_num 赋给成员属性 status_num
	 * @param status_num
	 */
	public void setStatus_num(int status_num) {
		this.status_num = status_num;
	}

	/**
	 * 返回成员属性 status_name
	 * @return status_name
	 */
	public String getStatus_name() {
		return status_name;
	}

	/**
	 * 将传入参数status_name 赋给成员属性 status_name
	 * @param status_name
	 */
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
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
	 * 将传入参数create_time 赋给成员属性 create_time
	 * @param create_time
	 */
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	/**
	 * 返回成员属性 modify_time
	 * @return modify_time
	 */
	public String getModify_time() {
		return modify_time;
	}

	/**
	 * 将传入参数modify_time 赋给成员属性 modify_time
	 * @param modify_time
	 */
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	
	
}
