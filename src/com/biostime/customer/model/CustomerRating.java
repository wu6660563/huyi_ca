/**
 * 
 */
package com.biostime.customer.model;

/**
 * 客户等级表
 *	
 * @author lj  add 2014-05-13
 * 
 */
public class CustomerRating {
	

	private String  customer_rating_id;//	客户等级id
	private String  level_name;//等级名称
	private String  pic_path;//等级图标
	private String  create_time;//	创建时间
	private String  modify_time;//修改时间
	private String  levle_desc;//等级描述
	
	public String getCustomer_rating_id() {
		return customer_rating_id;
	}
	public void setCustomer_rating_id(String customerRatingId) {
		customer_rating_id = customerRatingId;
	}
	public String getLevel_name() {
		return level_name;
	}
	public void setLevel_name(String levelName) {
		level_name = levelName;
	}
	public String getPic_path() {
		return pic_path;
	}
	public void setPic_path(String picPath) {
		pic_path = picPath;
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
	 * 返回成员属性 levle_desc
	 * @return levle_desc
	 */
	public String getLevle_desc() {
		return levle_desc;
	}
	/**
	 * 将传入参数levleDesc 赋给成员属性 levle_desc
	 * @param levleDesc
	 */
	public void setLevle_desc(String levleDesc) {
		levle_desc = levleDesc;
	}
	
	

}
