/**
 * 
 */
package com.biostime.customer.model;

/**
 * 
 *	
 * @author wpl 
 * @date May 11, 2014 9:31:56 PM
 * @version 1.0
 */
public class WorkOrder {
	
	/**
	 * 工单id_src 
	 */
	private int work_order_id_src;
	
	/**
	 * 工单id 
	 */
	private int work_order_id;
	
	/**
	 * 合同ID 
	 */
	private int contract_id;
	
	/**
	 * 商品类型ID 
	 */
	private int product_type_id;
	
	/**
	 * 购买项目 
	 */
	private String buy_items;
	
	/**
	 * 年限 
	 */
	private String years;
	
	/**
	 * 金额 
	 */
	private String amount;
	
	/**
	 * 进度 
	 */
	private String schedule;
	
	/**
	 * 是否问单1:是,0:否 
	 */
	private int is_question_order;
	
	/**
	 * 到期时间 
	 */
	private String expiry_date;
	
	/**
	 * 创建时间 
	 */
	private String create_time;
	
	/**
	 * 修改时间 
	 */
	private String modify_time;
	private String itemName; //商品名称 ，临时属性 数据库没有改字段
	
	/**
	 * 返回成员属性 work_order_id_src
	 * @return work_order_id_src
	 */
	public int getWork_order_id_src() {
		return work_order_id_src;
	}

	/**
	 * 将传入参数work_order_id_src 赋给成员属性 work_order_id_src
	 * @param work_order_id_src
	 */
	public void setWork_order_id_src(int work_order_id_src) {
		this.work_order_id_src = work_order_id_src;
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
	 * 返回成员属性 contract_id
	 * @return contract_id
	 */
	public int getContract_id() {
		return contract_id;
	}

	/**
	 * 将传入参数contract_id 赋给成员属性 contract_id
	 * @param contract_id
	 */
	public void setContract_id(int contract_id) {
		this.contract_id = contract_id;
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
	 * 返回成员属性 buy_items
	 * @return buy_items
	 */
	public String getBuy_items() {
		return buy_items;
	}

	/**
	 * 将传入参数buy_items 赋给成员属性 buy_items
	 * @param buy_items
	 */
	public void setBuy_items(String buy_items) {
		this.buy_items = buy_items;
	}

	/**
	 * 返回成员属性 years
	 * @return years
	 */
	public String getYears() {
		return years;
	}

	/**
	 * 将传入参数years 赋给成员属性 years
	 * @param years
	 */
	public void setYears(String years) {
		this.years = years;
	}

	/**
	 * 返回成员属性 amount
	 * @return amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * 将传入参数amount 赋给成员属性 amount
	 * @param amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * 返回成员属性 schedule
	 * @return schedule
	 */
	public String getSchedule() {
		return schedule;
	}

	/**
	 * 将传入参数schedule 赋给成员属性 schedule
	 * @param schedule
	 */
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	/**
	 * 返回成员属性 is_question_order
	 * @return is_question_order
	 */
	public int getIs_question_order() {
		return is_question_order;
	}

	/**
	 * 将传入参数is_question_order 赋给成员属性 is_question_order
	 * @param is_question_order
	 */
	public void setIs_question_order(int is_question_order) {
		this.is_question_order = is_question_order;
	}

	/**
	 * 返回成员属性 expiry_date
	 * @return expiry_date
	 */
	public String getExpiry_date() {
		return expiry_date;
	}

	/**
	 * 将传入参数expiry_date 赋给成员属性 expiry_date
	 * @param expiry_date
	 */
	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
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
	 * 返回成员属性 itemName
	 * @return itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * 将传入参数itemName 赋给成员属性 itemName
	 * @param itemName
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	
	
}
