/**
 * 
 */
package com.biostime.customer.model;

/**
 * 信息发布
 *	
 * @author lj  add 2014-05-13
 * 
 */
public class InfoDiss {
	
	private String info_id;//信息ID
	private String title;//标题
	private String ann_type;//类型
	private String summary;//摘要
	private String context;//内容
	private String create_time;//创建时间
	private String modify_time;//修改时间
	
	private String picture_id;// 图片表主键ID
	private String or_path;// 图片久路径
	private String path;//图片新路径 
	
	public String getPicture_id() {
		return picture_id;
	}
	public void setPicture_id(String pictureId) {
		picture_id = pictureId;
	}
	public String getOr_path() {
		return or_path;
	}
	public void setOr_path(String orPath) {
		or_path = orPath;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getInfo_id() {
		return info_id;
	}
	public void setInfo_id(String infoId) {
		info_id = infoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAnn_type() {
		return ann_type;
	}
	public void setAnn_type(String annType) {
		ann_type = annType;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
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
