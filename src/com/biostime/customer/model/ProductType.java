/**
 * 
 */
package com.biostime.customer.model;

/**
 * 商品类型表
 *	
 * @author lj  add 2014-05-13
 * 
 */
public class ProductType {
	

	private String  product_type_id;//商品类型ID
	private String  type_name;//类型名称
	private String  pic_path;//商品办事流程图
	private int course_num;	//服务进程个数
	private String  create_time;//创建时间
	private String  modify_time;//修改时间
	
	public String getProduct_type_id() {
		return product_type_id;
	}
	public void setProduct_type_id(String productTypeId) {
		product_type_id = productTypeId;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String typeName) {
		type_name = typeName;
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
	 * 返回成员属性 course_num
	 * @return course_num
	 */
	public int getCourse_num() {
		return course_num;
	}
	/**
	 * 将传入参数course_num 赋给成员属性 course_num
	 * @param course_num
	 */
	public void setCourse_num(int course_num) {
		this.course_num = course_num;
	}
	
	
}
