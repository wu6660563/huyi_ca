/**
 * 
 */
package com.biostime.customer.model;

/**
 * 
 *	
 * @author wpl 
 * @date May 11, 2014 9:40:39 PM
 * @version 1.0
 */
public class Question {

	/**
	 * 问题ID 
	 */
	private int question_id;
	
	/**
	 * 工单id
	 */
	private int work_order_id;
	
	/**
	 * 问题类型1 
	 */
	private int question_type;
	
	/**
	 * 弹单人 
	 */
	private String single_person;
	
	/**
	 * 部门 
	 */
	private String department;
	
	/**
	 * 内容 
	 */
	private String context;
	
	/**
	 * 创建时间 
	 */
	private String create_time;
	
	/**
	 * 修改时间 
	 */
	private String modify_time;

	/**
	 * 返回成员属性 question_id
	 * @return question_id
	 */
	public int getQuestion_id() {
		return question_id;
	}

	/**
	 * 将传入参数question_id 赋给成员属性 question_id
	 * @param question_id
	 */
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}

	/**
	 * 返回成员属性 question_type
	 * @return question_type
	 */
	public int getQuestion_type() {
		return question_type;
	}

	/**
	 * 将传入参数question_type 赋给成员属性 question_type
	 * @param question_type
	 */
	public void setQuestion_type(int question_type) {
		this.question_type = question_type;
	}

	/**
	 * 返回成员属性 single_person
	 * @return single_person
	 */
	public String getSingle_person() {
		return single_person;
	}

	/**
	 * 将传入参数single_person 赋给成员属性 single_person
	 * @param single_person
	 */
	public void setSingle_person(String single_person) {
		this.single_person = single_person;
	}

	/**
	 * 返回成员属性 department
	 * @return department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * 将传入参数department 赋给成员属性 department
	 * @param department
	 */
	public void setDepartment(String department) {
		this.department = department;
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

	
}
