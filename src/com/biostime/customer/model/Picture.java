/**
 * 
 */
package com.biostime.customer.model;

/**
 * 图片表
 *	
 * @author lj  add 2014-05-13
 * 
 */
public class Picture {
	

	private String  picture_id;//图片ID
	private String  business_id;//业务ID
	private String  business_type;//业务类型	1.合同、2.服务进程
	private String path;//图片路径
	private String or_path;//原图路径
	private String sort;//	序号
	private String isdelete;//是否删除
	private String create_time;//创建时间
	private String modify_time;//修改时间
	public String getPicture_id() {
		return picture_id;
	}
	public void setPicture_id(String pictureId) {
		picture_id = pictureId;
	}
	public String getBusiness_id() {
		return business_id;
	}
	public void setBusiness_id(String businessId) {
		business_id = businessId;
	}
	public String getBusiness_type() {
		return business_type;
	}
	public void setBusiness_type(String businessType) {
		business_type = businessType;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getOr_path() {
		return or_path;
	}
	public void setOr_path(String orPath) {
		or_path = orPath;
	}
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
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
