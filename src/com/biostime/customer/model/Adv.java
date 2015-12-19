/**
 * 
 */
package com.biostime.customer.model;

/**
 * 广告
 *	
 * @author lj  add 2014-05-13
 * 
 */
public class Adv {
	

	private String adv_id;//广告ID
	private String customer_rating_id;//客户等级id
	private String context;//广告内容
	private String adv_pic;//广告图
	private String url;//url
	private String create_time;//	创建时间
	private String modify_time;//	修改时间
	public String getAdv_id() {
		return adv_id;
	}
	public void setAdv_id(String advId) {
		adv_id = advId;
	}
	public String getCustomer_rating_id() {
		return customer_rating_id;
	}
	public void setCustomer_rating_id(String customerRatingId) {
		customer_rating_id = customerRatingId;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getAdv_pic() {
		return adv_pic;
	}
	public void setAdv_pic(String advPic) {
		adv_pic = advPic;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	
	
}
	
