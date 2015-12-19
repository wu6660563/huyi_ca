/**
 * 
 */
package com.biostime.customer.model;

/**
 * 合同
 *	
 * @author wpl 
 * @date May 12, 2014 9:56:16 PM
 * @version 1.0
 */
public class ContractInfo {
	
	/**
	 * 合同ID 
	 */
	private int contract_id;
	
	/**
	 * 客户资料ID 
	 */
	private int customer_info_id;
	
	/**
	 * 合同编号 
	 */
	private String contract_no;
	
	/**
	 * 签约时间 
	 */
	private String signing_time;
	
	/**
	 * 商务ID 
	 */
	private int business_id;
	
	/**
	 * 经理ID 
	 */
	private int manager_id;
	
	/**
	 * 分公司总监ID 
	 */
	private int director_id;
	
	/**
	 * 是否确认合同1-已经确认 
	 */
	private int is_sure_contract;
	
	/**
	 * 合作金额 
	 */
	private Double cooperation_num;
	
	/**
	 * 创建时间 
	 */
	private String create_time;
	
	/**
	 * 修改时间 
	 */
	private String modify_time;

	/**
	 * 返回成员属性 contract_id
	 * @return contract_id
	 */
	public int getContract_id() {
		return contract_id;
	}
	/**
	 * 责任商务代表名称（数据库没有该字段，临时取值用的）
	 */
	private String business_name;

	/**
	 * 将传入参数contract_id 赋给成员属性 contract_id
	 * @param contract_id
	 */
	public void setContract_id(int contract_id) {
		this.contract_id = contract_id;
	}

	/**
	 * 返回成员属性 customer_info_id
	 * @return customer_info_id
	 */
	public int getCustomer_info_id() {
		return customer_info_id;
	}

	/**
	 * 将传入参数customer_info_id 赋给成员属性 customer_info_id
	 * @param customer_info_id
	 */
	public void setCustomer_info_id(int customer_info_id) {
		this.customer_info_id = customer_info_id;
	}

	/**
	 * 返回成员属性 contract_no
	 * @return contract_no
	 */
	public String getContract_no() {
		return contract_no;
	}

	/**
	 * 将传入参数contract_no 赋给成员属性 contract_no
	 * @param contract_no
	 */
	public void setContract_no(String contract_no) {
		this.contract_no = contract_no;
	}

	/**
	 * 返回成员属性 signing_time
	 * @return signing_time
	 */
	public String getSigning_time() {
		return signing_time;
	}

	/**
	 * 将传入参数signing_time 赋给成员属性 signing_time
	 * @param signing_time
	 */
	public void setSigning_time(String signing_time) {
		this.signing_time = signing_time;
	}

	/**
	 * 返回成员属性 business_id
	 * @return business_id
	 */
	public int getBusiness_id() {
		return business_id;
	}

	/**
	 * 将传入参数business_id 赋给成员属性 business_id
	 * @param business_id
	 */
	public void setBusiness_id(int business_id) {
		this.business_id = business_id;
	}

	/**
	 * 返回成员属性 manager_id
	 * @return manager_id
	 */
	public int getManager_id() {
		return manager_id;
	}

	/**
	 * 将传入参数manager_id 赋给成员属性 manager_id
	 * @param manager_id
	 */
	public void setManager_id(int manager_id) {
		this.manager_id = manager_id;
	}

	/**
	 * 返回成员属性 director_id
	 * @return director_id
	 */
	public int getDirector_id() {
		return director_id;
	}

	/**
	 * 将传入参数director_id 赋给成员属性 director_id
	 * @param director_id
	 */
	public void setDirector_id(int director_id) {
		this.director_id = director_id;
	}

	/**
	 * 返回成员属性 is_sure_contract
	 * @return is_sure_contract
	 */
	public int getIs_sure_contract() {
		return is_sure_contract;
	}

	/**
	 * 将传入参数is_sure_contract 赋给成员属性 is_sure_contract
	 * @param is_sure_contract
	 */
	public void setIs_sure_contract(int is_sure_contract) {
		this.is_sure_contract = is_sure_contract;
	}

	/**
	 * 返回成员属性 cooperation_num
	 * @return cooperation_num
	 */
	public Double getCooperation_num() {
		return cooperation_num;
	}

	/**
	 * 将传入参数cooperation_num 赋给成员属性 cooperation_num
	 * @param cooperation_num
	 */
	public void setCooperation_num(Double cooperation_num) {
		this.cooperation_num = cooperation_num;
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
	 * 返回成员属性 business_name
	 * @return business_name
	 */
	public String getBusiness_name() {
		return business_name;
	}

	/**
	 * 将传入参数businessName 赋给成员属性 business_name
	 * @param businessName
	 */
	public void setBusiness_name(String businessName) {
		business_name = businessName;
	}

	
}
