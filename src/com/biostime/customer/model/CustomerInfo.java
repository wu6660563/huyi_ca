/**
 * 
 */
package com.biostime.customer.model;

/**
 * 客户 customer_info
 * @author wpl 
 * @date May 11, 2014 9:08:13 PM
 * @version 1.0
 */
public class CustomerInfo {
	
	/**
	 * 客户资料ID 
	 */
	private int customer_info_id;
	
	/**
	 * 客户ID 
	 */
	private int customer_id;
	
	/**
	 * 客户名 
	 */
	private String customer_name;
	
	/**
	 * 客户等级id 
	 */
	private int customer_rating_id;
	
	/**
	 * 客服ID 
	 */
	private int cust_service_id;
	
	/**
	 * 客服名称 
	 */
	private String cust_service_name;
	
	/**
	 * 公司名称 
	 */
	private String company_name;
	
	/**
	 * 业务联系人 
	 */
	private String business_contacts;
	
	/**
	 * 联系电话 phone
	 */
	private String phone;
	
	/**
	 * 企业简介 introduction
	 */
	private String introduction;
	
	/**
	 * 公司地址 adress
	 */
	private String adress;
	
	/**
	 * 主营产品 
	 */
	private String main_products;
	
	/**
	 * 累积合作金额 
	 */
	private Double total_count;
	
	/**
	 * 累积合作次数 
	 */
	private int total_num;
	
	/**
	 * 手机设备号 
	 */
	private String mobile_no;
	
	/**
	 * 设备系统 
	 */
	private String mobile_os;
	
	/**
	 * 登录经度 
	 */
	private String longitude;
	
	/**
	 * 登录纬度 
	 */
	private String Latitude;
	
	/**
	 * 登录地址 
	 */
	private String login_adress;
	
	/**
	 * 最近一次登录时间 
	 */
	private String last_login_time;
	
	/**
	 * 最近一次合作时间 
	 */
	private String last_cooperation_time;
	
	/**
	 * 90内可持续项目数 
	 */
	private String several_projects;
	
	/**
	 * 到期时间 
	 */
	private String expiry_date;
	
	
	/**
	 * 登录账号 
	 */
	private String login_name;
	
	/**
	 * 登录密码 pswd
	 */
	private String pswd;
	
	/**
	 * 是否删除 
	 */
	private String isdelete;
	
	/**
	 * 创建时间 
	 */
	private String create_time;
	
	/**
	 * 修改时间 
	 */
	private String modify_time;
	
	private String kefuName; //专属客服
	private String kefuPhone; //客服电话

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
	 * 返回成员属性 customer_id
	 * @return customer_id
	 */
	public int getCustomer_id() {
		return customer_id;
	}

	/**
	 * 将传入参数customer_id 赋给成员属性 customer_id
	 * @param customer_id
	 */
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	/**
	 * 返回成员属性 customer_name
	 * @return customer_name
	 */
	public String getCustomer_name() {
		return customer_name;
	}

	/**
	 * 将传入参数customer_name 赋给成员属性 customer_name
	 * @param customer_name
	 */
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	/**
	 * 返回成员属性 customer_rating_id
	 * @return customer_rating_id
	 */
	public int getCustomer_rating_id() {
		return customer_rating_id;
	}

	/**
	 * 将传入参数customer_rating_id 赋给成员属性 customer_rating_id
	 * @param customer_rating_id
	 */
	public void setCustomer_rating_id(int customer_rating_id) {
		this.customer_rating_id = customer_rating_id;
	}

	/**
	 * 返回成员属性 cust_service_id
	 * @return cust_service_id
	 */
	public int getCust_service_id() {
		return cust_service_id;
	}

	/**
	 * 将传入参数cust_service_id 赋给成员属性 cust_service_id
	 * @param cust_service_id
	 */
	public void setCust_service_id(int cust_service_id) {
		this.cust_service_id = cust_service_id;
	}

	/**
	 * 返回成员属性 cust_service_name
	 * @return cust_service_name
	 */
	public String getCust_service_name() {
		return cust_service_name;
	}

	/**
	 * 将传入参数cust_service_name 赋给成员属性 cust_service_name
	 * @param cust_service_name
	 */
	public void setCust_service_name(String cust_service_name) {
		this.cust_service_name = cust_service_name;
	}

	/**
	 * 返回成员属性 company_name
	 * @return company_name
	 */
	public String getCompany_name() {
		return company_name;
	}

	/**
	 * 将传入参数company_name 赋给成员属性 company_name
	 * @param company_name
	 */
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	/**
	 * 返回成员属性 business_contacts
	 * @return business_contacts
	 */
	public String getBusiness_contacts() {
		return business_contacts;
	}

	/**
	 * 将传入参数business_contacts 赋给成员属性 business_contacts
	 * @param business_contacts
	 */
	public void setBusiness_contacts(String business_contacts) {
		this.business_contacts = business_contacts;
	}

	/**
	 * 返回成员属性 phone
	 * @return phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 将传入参数phone 赋给成员属性 phone
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 返回成员属性 introduction
	 * @return introduction
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * 将传入参数introduction 赋给成员属性 introduction
	 * @param introduction
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * 返回成员属性 adress
	 * @return adress
	 */
	public String getAdress() {
		return adress;
	}

	/**
	 * 将传入参数adress 赋给成员属性 adress
	 * @param adress
	 */
	public void setAdress(String adress) {
		this.adress = adress;
	}

	/**
	 * 返回成员属性 main_products
	 * @return main_products
	 */
	public String getMain_products() {
		return main_products;
	}

	/**
	 * 将传入参数main_products 赋给成员属性 main_products
	 * @param main_products
	 */
	public void setMain_products(String main_products) {
		this.main_products = main_products;
	}

	/**
	 * 返回成员属性 total_count
	 * @return total_count
	 */
	public Double getTotal_count() {
		return total_count;
	}

	/**
	 * 将传入参数total_count 赋给成员属性 total_count
	 * @param total_count
	 */
	public void setTotal_count(Double total_count) {
		this.total_count = total_count;
	}

	/**
	 * 返回成员属性 total_num
	 * @return total_num
	 */
	public int getTotal_num() {
		return total_num;
	}

	/**
	 * 将传入参数total_num 赋给成员属性 total_num
	 * @param total_num
	 */
	public void setTotal_num(int total_num) {
		this.total_num = total_num;
	}


	/**
	 * 返回成员属性 mobile_no
	 * @return mobile_no
	 */
	public String getMobile_no() {
		return mobile_no;
	}

	/**
	 * 将传入参数mobile_no 赋给成员属性 mobile_no
	 * @param mobile_no
	 */
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	/**
	 * 返回成员属性 mobile_os
	 * @return mobile_os
	 */
	public String getMobile_os() {
		return mobile_os;
	}

	/**
	 * 将传入参数mobile_os 赋给成员属性 mobile_os
	 * @param mobile_os
	 */
	public void setMobile_os(String mobile_os) {
		this.mobile_os = mobile_os;
	}

	/**
	 * 返回成员属性 longitude
	 * @return longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * 将传入参数longitude 赋给成员属性 longitude
	 * @param longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * 返回成员属性 latitude
	 * @return latitude
	 */
	public String getLatitude() {
		return Latitude;
	}

	/**
	 * 将传入参数latitude 赋给成员属性 latitude
	 * @param latitude
	 */
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	/**
	 * 返回成员属性 login_adress
	 * @return login_adress
	 */
	public String getLogin_adress() {
		return login_adress;
	}

	/**
	 * 将传入参数login_adress 赋给成员属性 login_adress
	 * @param login_adress
	 */
	public void setLogin_adress(String login_adress) {
		this.login_adress = login_adress;
	}

	/**
	 * 返回成员属性 last_login_time
	 * @return last_login_time
	 */
	public String getLast_login_time() {
		return last_login_time;
	}

	/**
	 * 将传入参数last_login_time 赋给成员属性 last_login_time
	 * @param last_login_time
	 */
	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
	}

	/**
	 * 返回成员属性 last_cooperation_time
	 * @return last_cooperation_time
	 */
	public String getLast_cooperation_time() {
		return last_cooperation_time;
	}

	/**
	 * 将传入参数last_cooperation_time 赋给成员属性 last_cooperation_time
	 * @param last_cooperation_time
	 */
	public void setLast_cooperation_time(String last_cooperation_time) {
		this.last_cooperation_time = last_cooperation_time;
	}

	/**
	 * 返回成员属性 login_name
	 * @return login_name
	 */
	public String getLogin_name() {
		return login_name;
	}

	/**
	 * 将传入参数login_name 赋给成员属性 login_name
	 * @param login_name
	 */
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	/**
	 * 返回成员属性 pswd
	 * @return pswd
	 */
	public String getPswd() {
		return pswd;
	}

	/**
	 * 将传入参数pswd 赋给成员属性 pswd
	 * @param pswd
	 */
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	/**
	 * 返回成员属性 isdelete
	 * @return isdelete
	 */
	public String getIsdelete() {
		return isdelete;
	}

	/**
	 * 将传入参数isdelete 赋给成员属性 isdelete
	 * @param isdelete
	 */
	public void setIsdelete(String isdelete) {
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

	/**
	 * 返回成员属性 several_projects
	 * @return several_projects
	 */
	public String getSeveral_projects() {
		return several_projects;
	}

	/**
	 * 将传入参数several_projects 赋给成员属性 several_projects
	 * @param several_projects
	 */
	public void setSeveral_projects(String several_projects) {
		this.several_projects = several_projects;
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
	 * 返回成员属性 kefuName
	 * @return kefuName
	 */
	public String getKefuName() {
		return kefuName;
	}

	/**
	 * 将传入参数kefuName 赋给成员属性 kefuName
	 * @param kefuName
	 */
	public void setKefuName(String kefuName) {
		this.kefuName = kefuName;
	}

	/**
	 * 返回成员属性 kefuPhone
	 * @return kefuPhone
	 */
	public String getKefuPhone() {
		return kefuPhone;
	}

	/**
	 * 将传入参数kefuPhone 赋给成员属性 kefuPhone
	 * @param kefuPhone
	 */
	public void setKefuPhone(String kefuPhone) {
		this.kefuPhone = kefuPhone;
	}


	
}
