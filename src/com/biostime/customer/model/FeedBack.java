/**
 * 
 */
package com.biostime.customer.model;

/**
 * 信息反馈/服务进程
 *	
 * @author wpl 
 * @date May 15, 2014 10:18:51 PM
 * @version 1.0
 */
public class FeedBack {
	
	/**
	 * 信息反馈id 
	 */
	private int feedback_id;
	
	/**
	 * 工单id 
	 */
	private int work_order_id;
	
	/**
	 * 服务进程名称 
	 */
	private String service_pro_name;
	
	/**
	 * 内容 
	 */
	private String context;
	
	/**
	 * 状态
	 */
	private int status;
	
	/**
	 * 创建时间 
	 */
	private String create_time;
	
	/**
	 * 修改时间 
	 */
	private String modify_time;

	/**
	 * 返回成员属性 feedback_id
	 * @return feedback_id
	 */
	public int getFeedback_id() {
		return feedback_id;
	}

	/**
	 * 将传入参数feedback_id 赋给成员属性 feedback_id
	 * @param feedback_id
	 */
	public void setFeedback_id(int feedback_id) {
		this.feedback_id = feedback_id;
	}

	/**
	 * 返回成员属性 work_order_id
	 * @return work_order_id
	 */
	public int getWork_order_id() {
		return work_order_id;
	}

	/**
	 * 将传入参数work_order_id 赋给成员属性 work_order_id
	 * @param work_order_id
	 */
	public void setWork_order_id(int work_order_id) {
		this.work_order_id = work_order_id;
	}

	/**
	 * 返回成员属性 service_pro_name
	 * @return service_pro_name
	 */
	public String getService_pro_name() {
		return service_pro_name;
	}

	/**
	 * 将传入参数service_pro_name 赋给成员属性 service_pro_name
	 * @param service_pro_name
	 */
	public void setService_pro_name(String service_pro_name) {
		this.service_pro_name = service_pro_name;
	}

	/**
	 * 返回成员属性 context
	 * @return context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * 将传入参数context 赋给成员属性 context
	 * @param context
	 */
	public void setContext(String context) {
		this.context = context;
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

	/**
	 * 返回成员属性 status
	 * @return status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * 将传入参数status 赋给成员属性 status
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

}
