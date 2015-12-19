/**
 * 
 */
package com.biostime.report.service;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baiytfp.commonlib.base.service.CommonService;
import com.baiytfp.commonlib.util.StringUtils;
import com.biostime.magazine.common.DBManager;
import com.biostime.report.common.ExcelUtil;
import com.biostime.report.constant.SystemConstant;

/**
 * 所有Excel导入的统一入口service
 *	
 * @author wpl 
 * @date May 11, 2014 8:24:06 PM
 * @version 1.0
 */
public class ImportExcelService {
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private List<Hashtable<String, String>> list = new ArrayList<Hashtable<String,String>>();
	
	/**
	 * 
	 * 
	 * @param importType
	 * @param fileName
	 * @author wpl 
	 * @date ：May 13, 2014 8:39:56 PM
	 * @version 1.0
	 * @throws SQLException 
	 */
	public Map<String, Object> excute(String importType, String fileName) {
		Object object = importExcel(importType, fileName);
		Map<String, Object> result = new HashMap<String, Object>();
		if(object == null) {
			result.put("result", false);
			return result;
		}
		result = executeDB(importType);
		Boolean flag = (Boolean)result.get("result");
		if(flag) {
			System.out.println(importType +" load data success!");
			return result;
		}
		return result;
	}
	
	/**
	 * 
	 * 
	 * @param importType
	 * @param fileName
	 * @return
	 * @author wpl 
	 * @date ：May 13, 2014 8:39:53 PM
	 * @version 1.0
	 * @throws Exception 
	 */
	private Object importExcel(String importType, String fileName) {
		if(SystemConstant.CUSTOMERINFO.equals(importType)) {
			list = importCustomerExcel(fileName);
		} else if(SystemConstant.CONTRACT.equals(importType)) {
			list = importContractExcel(fileName);
		} else if(SystemConstant.ORDER.equals(importType)) {
			list = importOrderExcel(fileName);
		} else if(SystemConstant.PROCESS.equals(importType)) {
			list = importProcessExcel(fileName);
		} else if(SystemConstant.QUESSTION.equals(importType)) {
			list = importQuestionExcel(fileName);
		}  else if(SystemConstant.EMPLOYEE.equals(importType)) {
			list = importEmployeeExcel(fileName);
		} 
		return list;
	}
	
	/**
	 * 
	 * 
	 * @param importType
	 * @return
	 * @author wpl 
	 * @date ：May 13, 2014 8:39:50 PM
	 * @version 1.0
	 * @throws Exception
	 */
	private Map<String, Object> executeDB(String importType) {
		Map<String, Object> result= new HashMap<String, Object>();
		if(SystemConstant.CUSTOMERINFO.equals(importType)) {
			result = excuteCustomersToDBByOwn();
		} else if(SystemConstant.CONTRACT.equals(importType)) {
			result = excuteContractToDBByOwn();
		} else if(SystemConstant.ORDER.equals(importType)) {
			result = excuteOrdersToDBByOwn();
		} else if(SystemConstant.PROCESS.equals(importType)) {
			result = excuteProcessToDBByOwn();
		} else if(SystemConstant.EMPLOYEE.equals(importType)) {
			result = excuteEmployeeToDBByOwn();
		}
		return result;
	}
	
	
	/**
	 * 批量执行客户数据，插入到数据库
	 * 
	 * @return
	 * @author wpl 
	 * @date ：May 13, 2014 9:14:59 PM
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public boolean excuteCustomersToDBByBatch() {
		boolean flag = false;
		int sqlNum = 0;
		
		Connection connection = DBManager.getConnection();
		Statement statement = null;
		StringBuffer stringBuffer = null;
		Date date = new Date();
		int[] results = null;	//处理结果
		int successNum = 0;	//成功执行数量
		
		Map<String, Object> existMap = new HashMap<String, Object>();
		try {
			statement = connection.createStatement();
			connection.setAutoCommit(false);	//设置事物手动提交，出错全部重新导入即可
			CommonService commonService = new CommonService();
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Hashtable<String, String> hashtable = list.get(i);
					existMap.put("customer" + i, list.get(i));
					
					//判断客户名，如果客户名存在，则不插入
					String sqlTemp = "SELECT * FROM customer_info WHERE customer_id = ? ";
					List<Map<String, Object>> customers = commonService.getItemMapList(DBManager.getConnection(), sqlTemp, new Object[]{hashtable.get("customer_id")});
					boolean haveCustomer = false;
					if(customers != null && customers.size() > 0) {
						haveCustomer = true;
					}
					
					boolean _isMobie = isMobile(String.valueOf(hashtable.get("phone")));
					//公司名称、联系人不能为空，手机号码必须为指定手机号格式
					if(!haveCustomer && StringUtils.isNotBlank(hashtable.get("customer_name")) && StringUtils.isNotBlank(hashtable.get("business_contacts")) && _isMobie) {
						//将客服ID判断员工编号是否存在并且登录角色是客服的，如果存在，就转成员工主键id，可为空，如果不存在，就为空
						String cust_service_id = null;
						String cust_service_name = "";
						if(StringUtils.isNotBlank(hashtable.get("cust_service_id")) && StringUtils.isNotBlank(hashtable.get("cust_service_name"))) {
							//说明存在客服ID、客服
							String sql1 = "SELECT * FROM employees WHERE work_no = ? AND login_role='2' ORDER BY employees_id DESC";
							List<Map<String, Object>> emps = commonService.getItemMapList(DBManager.getConnection(), sql1, new Object[]{hashtable.get("cust_service_id")});
							if(emps != null && emps.size() > 0) {
								cust_service_id = String.valueOf(emps.get(0).get("employees_id"));
								cust_service_name = String.valueOf(emps.get(0).get("employees_name"));
							}
						}
						
						//等级，如果在excel等级不填的话，默认是1，查询数据库，如果存在，就取得id，如果不存在，插入一条数据，取得主键id
						String customer_rating = "1";
						String customer_rating_id = null;
						if(StringUtils.isNotBlank(hashtable.get("customer_rating_id"))) {
							customer_rating = hashtable.get("customer_rating_id");	//1/2/3/4/5
						}
						String sql2 = "SELECT * FROM customer_rating WHERE level_name = ? ORDER BY customer_rating_id DESC";
						List<Map<String, Object>> ratings = commonService.getItemMapList(DBManager.getConnection(), sql2, new Object[]{customer_rating.trim()});
						if(ratings != null && ratings.size() > 0) {
							customer_rating_id = String.valueOf(ratings.get(0).get("customer_rating_id"));	//得到等级主键id
						} else {
							//没有查询到该等级，将该等级插入到数据库中
							Map<String, String> map = new HashMap<String, String>();
							map.put("level_name", customer_rating.trim());
							map.put("create_time", dateFormat.format(new Date()));
							map.put("modify_time", dateFormat.format(new Date()));
							commonService.itemMapAdd(DBManager.getConnection(), "customer_rating", map);
							
							List<Map<String, Object>> ratings2 = commonService.getItemMapList(DBManager.getConnection(), sql2, new Object[]{customer_rating.trim()});
							if(ratings2 != null && ratings2.size() > 0) {
								customer_rating_id = String.valueOf(ratings2.get(0).get("customer_rating_id"));	//得到刚刚插入数据库的主键ID
							}
						}
						
						stringBuffer = new StringBuffer();
						stringBuffer.append("insert into customer_info(customer_id, customer_name, customer_rating_id, company_name, business_contacts, phone, adress, main_products, introduction, cust_service_id, cust_service_name, total_num, several_projects, login_name, pswd, isdelete, create_time, modify_time) values (");
						stringBuffer.append(Long.parseLong(hashtable.get("customer_id")));
						stringBuffer.append(",'");
						stringBuffer.append(hashtable.get("customer_name"));
						stringBuffer.append("','");
						stringBuffer.append(customer_rating_id);
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("customer_name"));
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("business_contacts"));
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("phone"));
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("adress"));
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("main_products"));
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("introduction"));
						
						stringBuffer.append("',");
						stringBuffer.append(cust_service_id);
						stringBuffer.append(",'");
						stringBuffer.append(cust_service_name);
						stringBuffer.append("','");
						
						stringBuffer.append(0);	//total_num
						stringBuffer.append("','");
						stringBuffer.append(0);	//several_projects
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("customer_name"));	//login_name
						stringBuffer.append("','");
						stringBuffer.append("123456");	//pswd
						stringBuffer.append("','");
						stringBuffer.append("0");	//isdelete
						stringBuffer.append("','");
						stringBuffer.append(dateFormat.format(date));
						stringBuffer.append("','");
						stringBuffer.append(dateFormat.format(date));
						stringBuffer.append("')");
						
						statement.addBatch(stringBuffer.toString());
						sqlNum ++;
					}
				}
			}
			System.out.println("excuteCustomersToDB ---> sqlNum:" + sqlNum);
			results = statement.executeBatch();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			try {
				if(connection != null)connection.rollback();
				if(statement != null)statement.close();
				if(connection != null)connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			successNum = 0;
			if(results != null) {
				for (int i : results) {
					if(i >= 0) {
						successNum ++;
					}
				}
			}
			try {
				if(flag) {
					connection.commit();
					System.out.println("excuteCustomersToDB --> 成功执行条数：" + successNum);
				}
				if(statement != null)statement.close();
				if(connection != null)connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				statement = null;
				connection = null;
			}
		}
		return flag;
	}
	
	/**
	 * 单独一条数据执行客户数据，插入到数据库
	 * 
	 * @return
	 * @author wpl 
	 * @date ：May 13, 2014 9:14:59 PM
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> excuteCustomersToDBByOwn() {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean flag = false;
		int sqlNum = 0;
		
		Date date = new Date();
		try {
			CommonService commonService = new CommonService();
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Hashtable<String, String> hashtable = list.get(i);
					
					//判断客户名，如果客户名存在，则不插入
					String sqlTemp = "SELECT * FROM customer_info WHERE customer_id = ? ";
					List<Map<String, Object>> customers = commonService.getItemMapList(DBManager.getConnection(), sqlTemp, new Object[]{hashtable.get("customer_id")});
					boolean haveCustomer = false;
					if(customers != null && customers.size() > 0) {
						haveCustomer = true;
					}
					
					boolean _isMobie = isMobile(String.valueOf(hashtable.get("phone")));
					boolean haveThePhone = false;
					String phoneTemp = "SELECT * FROM customer_info WHERE phone = ? ";
					List<Map<String, Object>> customers_phone = commonService.getItemMapList(DBManager.getConnection(), phoneTemp, new Object[]{String.valueOf(hashtable.get("phone"))});
					if(customers_phone != null && customers_phone.size() > 0) {
						haveThePhone = true;
					}
					//公司名称、联系人不能为空，手机号码必须为指定手机号格式，号码必须不存在
					if(!haveCustomer && StringUtils.isNotBlank(hashtable.get("customer_name")) && StringUtils.isNotBlank(hashtable.get("business_contacts")) && _isMobie && !haveThePhone) {
						//将客服ID判断员工编号是否存在并且登录角色是客服的，如果存在，就转成员工主键id，可为空，如果不存在，就为空
						String cust_service_id = null;
						String cust_service_name = "";
						if(StringUtils.isNotBlank(hashtable.get("cust_service_id")) && StringUtils.isNotBlank(hashtable.get("cust_service_name"))) {
							//说明存在客服ID、客服
							String sql1 = "SELECT * FROM employees WHERE work_no = ? AND login_role='2' ORDER BY employees_id DESC";
							List<Map<String, Object>> emps = commonService.getItemMapList(DBManager.getConnection(), sql1, new Object[]{hashtable.get("cust_service_id")});
							if(emps != null && emps.size() > 0) {
								cust_service_id = String.valueOf(emps.get(0).get("employees_id"));
								cust_service_name = String.valueOf(emps.get(0).get("employees_name"));
							}
						}
						
						//等级，如果在excel等级不填的话，默认是1，查询数据库，如果存在，就取得id，如果不存在，插入一条数据，取得主键id
						String customer_rating = "1";
						String customer_rating_id = null;
						if(StringUtils.isNotBlank(hashtable.get("customer_rating_id"))) {
							customer_rating = hashtable.get("customer_rating_id");	//1/2/3/4/5
						}
						String sql2 = "SELECT * FROM customer_rating WHERE level_name = ? ORDER BY customer_rating_id DESC";
						List<Map<String, Object>> ratings = commonService.getItemMapList(DBManager.getConnection(), sql2, new Object[]{customer_rating.trim()});
						if(ratings != null && ratings.size() > 0) {
							customer_rating_id = String.valueOf(ratings.get(0).get("customer_rating_id"));	//得到等级主键id
						} else {
							//没有查询到该等级，将该等级插入到数据库中
							Map<String, String> map = new HashMap<String, String>();
							map.put("level_name", customer_rating.trim());
							map.put("create_time", dateFormat.format(new Date()));
							map.put("modify_time", dateFormat.format(new Date()));
							commonService.itemMapAdd(DBManager.getConnection(), "customer_rating", map);
							
							List<Map<String, Object>> ratings2 = commonService.getItemMapList(DBManager.getConnection(), sql2, new Object[]{customer_rating.trim()});
							if(ratings2 != null && ratings2.size() > 0) {
								customer_rating_id = String.valueOf(ratings2.get(0).get("customer_rating_id"));	//得到刚刚插入数据库的主键ID
							}
						}
						
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("customer_id", Long.parseLong(hashtable.get("customer_id")));
						map.put("customer_name", hashtable.get("customer_name"));
						map.put("customer_rating_id", customer_rating_id);
						map.put("company_name", hashtable.get("customer_name"));
						map.put("business_contacts", hashtable.get("business_contacts"));
						map.put("phone", hashtable.get("phone"));
						map.put("adress", hashtable.get("adress"));
						map.put("main_products", hashtable.get("main_products"));
						map.put("introduction", hashtable.get("introduction"));
						map.put("cust_service_id", cust_service_id);
						map.put("cust_service_name", cust_service_name);
						map.put("total_num", 0);
						map.put("several_projects", 0);
						map.put("login_name", hashtable.get("customer_name"));
						map.put("pswd", "123456");
						map.put("isdelete", 0);
						map.put("create_time", dateFormat.format(date));
						map.put("modify_time", dateFormat.format(date));
						Long num = commonService.itemMapAdd(DBManager.getConnection(), "customer_info", map);
						if(num > 0) {
							sqlNum ++;
						}
					}
				}
			}
			System.out.println("excuteCustomersToDB ---> sqlNum:" + sqlNum);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		result.put("result", flag);
		result.put("num", sqlNum);
		return result;
	}
	
	
	/**
	 * 批量执行合同数据，插入到数据库
	 * 
	 * @return
	 * @author wpl 
	 * @date ：May 15, 2014 8:38:08 PM
	 * @version 1.0
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public boolean excuteContractToDBByBacth() {
		boolean flag = false;
		int sqlNum = 0;
		int[] results = null;
		int successNum = 0;
		
		Connection connection = null;
		CommonService commonService = null;
		Statement statement = null;
		try {
			connection = DBManager.getConnection();
			connection.setAutoCommit(false);
			commonService = new CommonService();
			StringBuffer stringBuffer = null;
			Date date = new Date();
			statement = connection.createStatement();
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Hashtable<String, String> hashtable = list.get(i);
					
					String signing_time = hashtable.get("signing_time");
					
					//判断business_id、manager_id、director_id是否存在employees表中
					//只要有任何一个id不存在，就不插入数据库
					boolean businessId_right = false;
					String business_id = "0";
					if(hashtable.get("business_id") != null) {
						//不等于null或者空串，表示填写了，既然填写了，就必须是数字
						List<Map<String, Object>> emplist = commonService.getItemMapList(DBManager.getConnection(), "SELECT * FROM employees WHERE work_no = ?", new Object[]{hashtable.get("business_id")});
						if(emplist != null && emplist.size() > 0) {
							//该id在数据库中存在
							businessId_right = true;
							business_id = String.valueOf(emplist.get(0).get("employees_id"));
						}
					}
					boolean managerId_right = false;
					String manager_id = "0";
					if(hashtable.get("manager_id") != null) {
						//不等于null或者空串，表示填写了，既然填写了，就必须是数字
							List<Map<String, Object>> emplist = commonService.getItemMapList(DBManager.getConnection(), "SELECT * FROM employees WHERE work_no = ?", new Object[]{hashtable.get("manager_id")});
						if(emplist != null && emplist.size() > 0) {
							//该id在数据库中存在
							managerId_right = true;
							manager_id = String.valueOf(emplist.get(0).get("employees_id"));
						}
					} else if(StringUtils.isBlank(hashtable.get("manager_id"))) {
						//经理id可为null或者空串
						managerId_right = true;
					}
					boolean directorId_right = false;
					String director_id = "0";
					if(hashtable.get("director_id") != null) {
						//不等于null或者空串，表示填写了，既然填写了，就必须是数字
						List<Map<String, Object>> emplist = commonService.getItemMapList(DBManager.getConnection(), "SELECT * FROM employees WHERE work_no = ?", new Object[]{hashtable.get("director_id")});
						if(emplist != null && emplist.size() > 0) {
							//该id在数据库中存在
							directorId_right = true;
							director_id = String.valueOf(emplist.get(0).get("employees_id"));
						}
					} else if(StringUtils.isBlank(hashtable.get("director_id"))) {
						//总监id可为null或者空串
						directorId_right = true;
					}
					
					//判断客户ID，是否存在
					boolean customer_id_exist = false;
					String customer_info_id = null;
					if(StringUtils.isNotBlank(hashtable.get("customer_id"))) {
						String string = "SELECT * FROM customer_info WHERE customer_id = ?"; 
						List<Map<String, Object>> customers = commonService.getItemMapList(DBManager.getConnection(), string, new Object[]{hashtable.get("customer_id")});
						if(customers != null && customers.size() > 0) {
							customer_info_id = String.valueOf(customers.get(0).get("customer_info_id"));
							customer_id_exist = true;
						}
					}
					
					if(customer_id_exist && StringUtils.isNotBlank(customer_info_id) && businessId_right && managerId_right && directorId_right) {
						stringBuffer = new StringBuffer();
						stringBuffer.append("insert into contract_info(customer_info_id, contract_no, signing_time, business_id, manager_id, director_id, is_sure_contract, cooperation_num, create_time, modify_time) values (");
						stringBuffer.append(Integer.parseInt(customer_info_id));
						stringBuffer.append(",'");
						stringBuffer.append(hashtable.get("contract_no"));
						stringBuffer.append("','");
						stringBuffer.append(signing_time);
						stringBuffer.append("',");
						stringBuffer.append(business_id);
						stringBuffer.append(",");
						stringBuffer.append(manager_id);
						stringBuffer.append(",");
						stringBuffer.append(director_id);
						stringBuffer.append(",'");
						stringBuffer.append(0);	//默认是未确认
						stringBuffer.append("',");
						stringBuffer.append(Integer.parseInt(hashtable.get("cooperation_num")));
						stringBuffer.append(",'");
						stringBuffer.append(dateFormat.format(date));
						stringBuffer.append("','");
						stringBuffer.append(dateFormat.format(date));
						stringBuffer.append("')");
						
						statement.addBatch(stringBuffer.toString());
						sqlNum ++;
					}
				}
			}
			System.out.println("excuteContractToDB ---> sqlNum:" + sqlNum);
			results = statement.executeBatch();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			try {
				if(connection != null)connection.rollback();
				if(statement != null)statement.close();
				if(connection != null)connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			successNum = 0;
			if(results != null) {
				for (int i : results) {
					if(i >= 0) {
						successNum ++;
					}
				}
			}
			try {
				if(flag) {
					connection.commit();
					System.out.println("excuteContractToDB --> 成功执行条数：" + successNum);
				}
				if(statement != null)statement.close();
				if(connection != null)connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				statement = null;
				connection = null;
			}
		}
		return flag;
	}
	
	/**
	 * 单独数据执行合同数据，插入到数据库
	 * 
	 * @return
	 * @author wpl 
	 * @date ：May 15, 2014 8:38:08 PM
	 * @version 1.0
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> excuteContractToDBByOwn() {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean flag = false;
		int sqlNum = 0;
		
		CommonService commonService = null;
		try {
			commonService = new CommonService();
			Date date = new Date();
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Hashtable<String, String> hashtable = list.get(i);
					
					String signing_time = hashtable.get("signing_time");
					
					//判断business_id、manager_id、director_id是否存在employees表中
					//只要有任何一个id不存在，就不插入数据库
					boolean businessId_right = false;
					String business_id = "0";
					if(hashtable.get("business_id") != null) {
						//不等于null或者空串，表示填写了，既然填写了，就必须是数字
						List<Map<String, Object>> emplist = commonService.getItemMapList(DBManager.getConnection(), "SELECT * FROM employees WHERE work_no = ?", new Object[]{hashtable.get("business_id")});
						if(emplist != null && emplist.size() > 0) {
							//该id在数据库中存在
							businessId_right = true;
							business_id = String.valueOf(emplist.get(0).get("employees_id"));
						}
					}
					boolean managerId_right = false;
					String manager_id = "0";
					if(hashtable.get("manager_id") != null) {
						//不等于null或者空串，表示填写了，既然填写了，就必须是数字
							List<Map<String, Object>> emplist = commonService.getItemMapList(DBManager.getConnection(), "SELECT * FROM employees WHERE work_no = ?", new Object[]{hashtable.get("manager_id")});
						if(emplist != null && emplist.size() > 0) {
							//该id在数据库中存在
							managerId_right = true;
							manager_id = String.valueOf(emplist.get(0).get("employees_id"));
						}
					} else if(StringUtils.isBlank(hashtable.get("manager_id"))) {
						//经理id可为null或者空串
						managerId_right = true;
					}
					boolean directorId_right = false;
					String director_id = "0";
					if(hashtable.get("director_id") != null) {
						//不等于null或者空串，表示填写了，既然填写了，就必须是数字
						List<Map<String, Object>> emplist = commonService.getItemMapList(DBManager.getConnection(), "SELECT * FROM employees WHERE work_no = ?", new Object[]{hashtable.get("director_id")});
						if(emplist != null && emplist.size() > 0) {
							//该id在数据库中存在
							directorId_right = true;
							director_id = String.valueOf(emplist.get(0).get("employees_id"));
						}
					} else if(StringUtils.isBlank(hashtable.get("director_id"))) {
						//总监id可为null或者空串
						directorId_right = true;
					}
					
					//判断客户ID，是否存在
					boolean customer_id_exist = false;
					String customer_info_id = null;
					if(StringUtils.isNotBlank(hashtable.get("customer_id"))) {
						String string = "SELECT * FROM customer_info WHERE customer_id = ?"; 
						List<Map<String, Object>> customers = commonService.getItemMapList(DBManager.getConnection(), string, new Object[]{hashtable.get("customer_id")});
						if(customers != null && customers.size() > 0) {
							customer_info_id = String.valueOf(customers.get(0).get("customer_info_id"));
							customer_id_exist = true;
						}
					}
					
					if(customer_id_exist && StringUtils.isNotBlank(customer_info_id) && businessId_right && managerId_right && directorId_right) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("customer_info_id", Integer.parseInt(customer_info_id));
						map.put("contract_no", hashtable.get("contract_no"));
						map.put("signing_time", signing_time);
						map.put("business_id", business_id);
						map.put("manager_id", manager_id);
						map.put("director_id", director_id);
						map.put("is_sure_contract", 0);
						map.put("cooperation_num", Integer.parseInt(hashtable.get("cooperation_num")));
						map.put("create_time", dateFormat.format(date));
						map.put("modify_time", dateFormat.format(date));
						Long num = commonService.itemMapAdd(DBManager.getConnection(), "contract_info", map);
						if(num > 0) {
							sqlNum ++;
						}
					}
				}
			}
			System.out.println("excuteContractToDB ---> sqlNum:" + sqlNum);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		result.put("result", flag);
		result.put("num", sqlNum);
		return result;
	}
	
	
	/**
	 * 批量执行工单数据，插入到数据库
	 * 
	 * @return
	 * @author wpl 
	 * @date ：May 15, 2014 8:39:04 PM
	 * @version 1.0
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public boolean excuteOrdersToDBByBatch() {
		boolean flag = false;
		int sqlNum = 0;
		int[] results = null;
		int successNum = 0;
		
		Connection connection = null;
		Statement statement = null;
		try {
			connection = DBManager.getConnection();
			connection.setAutoCommit(false);
			StringBuffer stringBuffer = null;
			CommonService commonService = new CommonService();
			Date date = new Date();
			statement = connection.createStatement();
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Hashtable<String, String> hashtable = list.get(i);
					
					//work_order_id_src相同的，则不插入
					boolean haveOrderId = false;
					String sqlTemp = "SELECT * FROM work_order WHERE work_order_id_src = ?";
					List<Map<String, Object>> orders = commonService.getItemMapList(DBManager.getConnection(), sqlTemp, new Object[]{hashtable.get("work_order_id_src")});
					if(orders != null && orders.size() > 0) {
						haveOrderId = true;
					}
					
					//判断合同是否存在
					boolean contactIsExist = false;
					int contract_id = 0;
					if(StringUtils.isNotBlank(hashtable.get("contract_no"))) {
						String sql2 = "SELECT * FROM contract_info WHERE contract_no = ?";
						List<Map<String, Object>> contracts = commonService.getItemMapList(DBManager.getConnection(), sql2, new Object[]{hashtable.get("contract_no")});
						if(contracts != null && contracts.size() > 0) {
							contactIsExist = true;
							contract_id = Integer.parseInt(String.valueOf(contracts.get(0).get("contract_id")));
						}
					}
					
					if(!haveOrderId && contactIsExist) {
						//不存在orderid && 存在合同id ，类型不能为空
						String product_type = hashtable.get("product_type");
						String sql3 = "SELECT * FROM product_type WHERE type_name = ?";
						int product_type_id = 0;
						List<Map<String, Object>> products = commonService.getItemMapList(DBManager.getConnection(), sql3, new Object[]{product_type});
						//产品不为空，说明存在，如果产品为空，则插入一条数据到产品类型表
						if(products != null && products.size() > 0) {
							product_type_id = Integer.parseInt(String.valueOf(products.get(0).get("product_type_id")));
						} else {
							//不存在，插入一条新数据到product_type表
							Map<String, String> map = new HashMap<String, String>();
							map.put("type_name", product_type);
							map.put("create_time", dateFormat.format(new Date()));
							map.put("modify_time", dateFormat.format(new Date()));
							
							long num = commonService.itemMapAdd(DBManager.getConnection(), "product_type", map);
							if(num > 0) {
								List<Map<String, Object>> product_types = commonService.getItemMapList(DBManager.getConnection(), "SELECT * FROM product_type WHERE type_name = ?", new Object[]{product_type});
								if(product_types != null && product_types.size() > 0) {
									product_type_id = Integer.parseInt(String.valueOf(product_types.get(0).get("product_type_id")));
								}
							}
						}
						
						stringBuffer = new StringBuffer();
						stringBuffer.append("insert into work_order(work_order_id_src, contract_id, product_type_id, buy_items, years, amount, schedule, is_question_order, expiry_date, create_time, modify_time) values ('");
						stringBuffer.append(Integer.parseInt(hashtable.get("work_order_id_src")));
						stringBuffer.append("',");
						stringBuffer.append(contract_id);
						stringBuffer.append(",'");
						stringBuffer.append(product_type_id);
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("buy_items"));
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("years"));
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("amount"));
						stringBuffer.append("','");
						stringBuffer.append(0);
						stringBuffer.append("',");
						stringBuffer.append(0);
						stringBuffer.append(",NULL ,'");
						stringBuffer.append(dateFormat.format(date));
						stringBuffer.append("','");
						stringBuffer.append(dateFormat.format(date));
						stringBuffer.append("')");
						
						statement.addBatch(stringBuffer.toString());
						sqlNum ++;
					}
				}
			}
			System.out.println("excuteOrdersToDB ---> sqlNum:" + sqlNum);
			results = statement.executeBatch();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			try {
				if(connection != null)connection.rollback();
				if(statement != null)statement.close();
				if(connection != null)connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			successNum = 0;
			if(results != null) {
				for (int i : results) {
					if(i >= 0) {
						successNum ++;
					}
				}
			}
			try {
				if(flag) {
					connection.commit();
					System.out.println("excuteContractToDB --> 成功执行条数：" + successNum);
				}
				if(statement != null)statement.close();
				if(connection != null)connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				statement = null;
				connection = null;
			}
		}
		return flag;
	}
	
	/**
	 * 单独执行工单数据，插入到数据库
	 * 
	 * @return
	 * @author wpl 
	 * @date ：May 15, 2014 8:39:04 PM
	 * @version 1.0
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> excuteOrdersToDBByOwn() {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean flag = false;
		int sqlNum = 0;
		
		try {
			CommonService commonService = new CommonService();
			Date date = new Date();
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Hashtable<String, String> hashtable = list.get(i);
					
					//work_order_id_src相同的，则不插入
					boolean haveOrderId = false;
					String sqlTemp = "SELECT * FROM work_order WHERE work_order_id_src = ?";
					List<Map<String, Object>> orders = commonService.getItemMapList(DBManager.getConnection(), sqlTemp, new Object[]{hashtable.get("work_order_id_src")});
					if(orders != null && orders.size() > 0) {
						haveOrderId = true;
					}
					
					//判断合同是否存在
					boolean contactIsExist = false;
					int contract_id = 0;
					if(StringUtils.isNotBlank(hashtable.get("contract_no"))) {
						String sql2 = "SELECT * FROM contract_info WHERE contract_no = ?";
						List<Map<String, Object>> contracts = commonService.getItemMapList(DBManager.getConnection(), sql2, new Object[]{hashtable.get("contract_no")});
						if(contracts != null && contracts.size() > 0) {
							contactIsExist = true;
							contract_id = Integer.parseInt(String.valueOf(contracts.get(0).get("contract_id")));
						}
					}
					
					if(!haveOrderId && contactIsExist) {
						//不存在orderid && 存在合同id ，类型不能为空
						String product_type = hashtable.get("product_type");
						String sql3 = "SELECT * FROM product_type WHERE type_name = ?";
						int product_type_id = 0;
						List<Map<String, Object>> products = commonService.getItemMapList(DBManager.getConnection(), sql3, new Object[]{product_type});
						//产品不为空，说明存在，如果产品为空，则插入一条数据到产品类型表
						if(products != null && products.size() > 0) {
							product_type_id = Integer.parseInt(String.valueOf(products.get(0).get("product_type_id")));
						} else {
							//不存在，插入一条新数据到product_type表
							Map<String, String> map = new HashMap<String, String>();
							map.put("type_name", product_type);
							map.put("create_time", dateFormat.format(new Date()));
							map.put("modify_time", dateFormat.format(new Date()));
							
							long num = commonService.itemMapAdd(DBManager.getConnection(), "product_type", map);
							if(num > 0) {
								List<Map<String, Object>> product_types = commonService.getItemMapList(DBManager.getConnection(), "SELECT * FROM product_type WHERE type_name = ?", new Object[]{product_type});
								if(product_types != null && product_types.size() > 0) {
									product_type_id = Integer.parseInt(String.valueOf(product_types.get(0).get("product_type_id")));
								}
							}
						}
						
//						stringBuffer = new StringBuffer();
//						stringBuffer.append("insert into work_order(work_order_id_src, contract_id, product_type_id, buy_items, years, amount, schedule, is_question_order, expiry_date, create_time, modify_time) values ('");
//						stringBuffer.append(Integer.parseInt(hashtable.get("work_order_id_src")));
//						stringBuffer.append("',");
//						stringBuffer.append(contract_id);
//						stringBuffer.append(",'");
//						stringBuffer.append(product_type_id);
//						stringBuffer.append("','");
//						stringBuffer.append(hashtable.get("buy_items"));
//						stringBuffer.append("','");
//						stringBuffer.append(hashtable.get("years"));
//						stringBuffer.append("','");
//						stringBuffer.append(hashtable.get("amount"));
//						stringBuffer.append("','");
//						stringBuffer.append(0);
//						stringBuffer.append("',");
//						stringBuffer.append(0);
//						stringBuffer.append(",NULL ,'");
//						stringBuffer.append(dateFormat.format(date));
//						stringBuffer.append("','");
//						stringBuffer.append(dateFormat.format(date));
//						stringBuffer.append("')");
						
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("work_order_id_src", Integer.parseInt(hashtable.get("work_order_id_src")));
						map.put("contract_id", contract_id);
						map.put("product_type_id", product_type_id);
						map.put("buy_items", hashtable.get("buy_items"));
						map.put("years", hashtable.get("years"));
						map.put("amount", hashtable.get("amount"));
						map.put("schedule", 0);
						map.put("is_question_order", 0);
						map.put("expiry_date", null);
						map.put("create_time", dateFormat.format(date));
						map.put("modify_time", dateFormat.format(date));
						
						Long num = commonService.itemMapAdd(DBManager.getConnection(), "work_order", map);
						if(num > 0) {
							sqlNum ++;
						}
					}
				}
			}
			System.out.println("excuteOrdersToDB ---> sqlNum:" + sqlNum);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		result.put("result", flag);
		result.put("num", sqlNum);
		return result;
	}
	
	
	/**
	 * 批量执行服务进程数据，插入到数据库
	 * 
	 * @return
	 * @author wpl 
	 * @date ：May 15, 2014 8:45:12 PM
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> excuteProcessToDBByBacth() {
		Map<String, Object> result = new HashMap<String, Object>();
		
		boolean flag = false;
		int sqlNum = 0;
		int[] results = null;
		int successNum = 0;
		
		Connection connection = null;
		Statement statement = null;
		try {
			connection = DBManager.getConnection();
			connection.setAutoCommit(false);
			StringBuffer stringBuffer = null;
			CommonService commonService = new CommonService();
			statement = connection.createStatement();
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Hashtable<String, String> hashtable = list.get(i);
					
					boolean orderIdExist = false;
					int work_order_id = 0;
					if(StringUtils.isNotBlank(hashtable.get("work_order_id_src"))) {
						List<Map<String, Object>> workorders = commonService.getItemMapList(DBManager.getConnection(), "SELECT * FROM work_order WHERE work_order_id_src = ?", new Object[]{hashtable.get("work_order_id_src")});
						if(workorders != null && workorders.size() > 0) {
							orderIdExist = true;
							work_order_id = Integer.parseInt(String.valueOf(workorders.get(0).get("work_order_id")));
						}
					}
					
					if(orderIdExist) {
						//工单id，必须存在于工单表中
						stringBuffer = new StringBuffer();
						stringBuffer.append("insert into feedback(work_order_id, service_pro_name, context, create_time, modify_time) values (");
						stringBuffer.append(work_order_id);
						stringBuffer.append(",'");
						stringBuffer.append(hashtable.get("service_pro_name"));
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("context"));
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("create_time"));
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("create_time"));
						stringBuffer.append("')");
						
						statement.addBatch(stringBuffer.toString());
						sqlNum ++;
					}
				}
			}
			System.out.println("excuteProcessToDB ---> sqlNum:" + sqlNum);
			results = statement.executeBatch();
			flag = true;
		} catch (Exception e) {
			System.out.println(ImportExcelService.class.getName() + " --> excuteProcessToDB --> excute data error!");
			e.printStackTrace();
			flag = false;
			try {
				if(connection != null)connection.rollback();
				if(statement != null)statement.close();
				if(connection != null)connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			successNum = 0;
			if(results != null) {
				for (int i : results) {
					if(i >= 0) {
						successNum ++;
					}
				}
			}
			try {
				if(flag) {
					connection.commit();
					System.out.println("excuteContractToDB --> 成功执行条数：" + successNum);
				}
				if(statement != null)statement.close();
				if(connection != null)connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				statement = null;
				connection = null;
			}
		}
		result.put("result", flag);
		result.put("num", sqlNum);
		return result;
	}
	
	/**
	 * 单条数据执行服务进程数据，插入到数据库
	 * 
	 * @return
	 * @author wpl 
	 * @date ：May 15, 2014 8:45:12 PM
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> excuteProcessToDBByOwn() {
		Map<String, Object> result = new HashMap<String, Object>();
		
		boolean flag = false;
		int sqlNum = 0;
		
		try {
			CommonService commonService = new CommonService();
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Hashtable<String, String> hashtable = list.get(i);
					
					boolean orderIdExist = false;
					int work_order_id = 0;
					if(StringUtils.isNotBlank(hashtable.get("work_order_id_src"))) {
						List<Map<String, Object>> workorders = commonService.getItemMapList(DBManager.getConnection(), "SELECT * FROM work_order WHERE work_order_id_src = ?", new Object[]{hashtable.get("work_order_id_src")});
						if(workorders != null && workorders.size() > 0) {
							orderIdExist = true;
							work_order_id = Integer.parseInt(String.valueOf(workorders.get(0).get("work_order_id")));
						}
					}
					
					if(orderIdExist) {
						//工单id，必须存在于工单表中
//						stringBuffer = new StringBuffer();
//						stringBuffer.append("insert into feedback(work_order_id, service_pro_name, context, create_time, modify_time) values (");
//						stringBuffer.append(work_order_id);
//						stringBuffer.append(",'");
//						stringBuffer.append(hashtable.get("service_pro_name"));
//						stringBuffer.append("','");
//						stringBuffer.append(hashtable.get("context"));
//						stringBuffer.append("','");
//						stringBuffer.append(hashtable.get("create_time"));
//						stringBuffer.append("','");
//						stringBuffer.append(hashtable.get("create_time"));
//						stringBuffer.append("')");
						
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("work_order_id", work_order_id);
						map.put("service_pro_name", hashtable.get("service_pro_name"));
						map.put("context", hashtable.get("context"));
						map.put("create_time", hashtable.get("create_time"));
						map.put("modify_time", hashtable.get("create_time"));
						
						Long num = commonService.itemMapAdd(DBManager.getConnection(), "feedback", map);
						if(num > 0) {
							sqlNum ++;
						}
					}
				}
			}
			System.out.println("excuteProcessToDB ---> sqlNum:" + sqlNum);
			flag = true;
		} catch (Exception e) {
			System.out.println(ImportExcelService.class.getName() + " --> excuteProcessToDB --> excute data error!");
			e.printStackTrace();
			flag = false;
		}
		result.put("result", flag);
		result.put("num", sqlNum);
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean excuteQuesstionsToDBByBacth() {
		boolean flag = false;
		int sqlNum = 0;
		int[] results = null;
		int successNum = 0;
		
		Connection connection = null;
		Statement statement = null;
		StringBuffer stringBuffer = null;
		CommonService commonService = null;
		try {
			commonService = new CommonService();
			connection = DBManager.getConnection();
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Hashtable<String, String> hashtable = list.get(i);
					
					//工单id必须存在于工单表
					boolean orderIdExist = false;
					int work_order_id = 0;
					
					if(StringUtils.isNotBlank(hashtable.get("work_order_src_id"))) {
						String sql = "SELECT * FROM work_order WHERE work_order_id_src = ? ";
						List<String> params = new ArrayList<String>();
						params.add(hashtable.get("work_order_src_id"));
						List<Map<String, Object>> workorders = commonService.getItemMapList(DBManager.getConnection(), sql, params.toArray());
						if(workorders != null && workorders.size() > 0) {
							orderIdExist = true;
							work_order_id = Integer.parseInt(String.valueOf(workorders.get(0).get("work_order_id")));
						}
					}
					
//					boolean empExist = false;
//					int employee_id = 0;
//					if(StringUtils.isNotBlank(hashtable.get("single_person"))) {
//						List<Map<String, Object>> emps = commonService.getItemMapList(DBManager.getConnection(), "SELECT * FROM employees WHERE work_no = ?", new Object[]{hashtable.get("single_person")});
//						if(emps != null && emps.size() > 0) {
//							empExist = true;
//							employee_id = Integer.parseInt(String.valueOf(emps.get(0).get("employees_id")));
//						}
//					}
//					System.out.println(hashtable.get("work_order_src_id")+"----"+work_order_id);
					if(orderIdExist) {
						stringBuffer = new StringBuffer();
						stringBuffer.append("insert into question(question_type, work_order_id, single_person, department, context, create_time, modify_time) values ('");
						stringBuffer.append(hashtable.get("question_type"));
						stringBuffer.append("',");
						stringBuffer.append(work_order_id);
						stringBuffer.append(",'");
						stringBuffer.append(hashtable.get("single_person"));
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("department"));
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("context"));
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("create_time"));
						stringBuffer.append("','");
						stringBuffer.append(hashtable.get("create_time"));
						stringBuffer.append("')");
						
						statement.addBatch(stringBuffer.toString());
						sqlNum ++;
					}
				}
			}
			System.out.println("excuteQuesstionsToDB ---> sqlNum:" + sqlNum);
			results = statement.executeBatch();
			flag = true;
		} catch (Exception e) {
			System.out.println(ImportExcelService.class.getName() + " --> excuteQuesstionsToDB --> excute data error!");
			e.printStackTrace();
			flag = false;
			try {
				if(connection != null)connection.rollback();
				if(statement != null)statement.close();
				if(connection != null)connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			successNum = 0;
			if(results != null) {
				for (int i : results) {
					if(i >= 0) {
						successNum ++;
					}
				}
			}
			try {
				if(flag) {
					connection.commit();
					System.out.println("excuteContractToDB --> 成功执行条数：" + successNum);
				}
				if(statement != null)statement.close();
				if(connection != null)connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				statement = null;
				connection = null;
			}
		}
		return flag;
	}
	
	/**
	 * 批量执行员工数据，插入到数据库
	 * 
	 * @return
	 * @author wpl 
	 * @date ：May 15, 2014 8:52:34 PM
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public boolean excuteEmployeeToDBByBacth() {
		boolean flag = false;
		
		Connection connection = null;
		Statement statement = null;
		StringBuffer stringBuffer = null;
		Date date = new Date();
		int sqlNum = 0;
		int[] results = null; //执行结果
		int successNum = 0;	//执行条数
		try {
			CommonService commonService = new CommonService();
			connection = DBManager.getConnection();
			connection.setAutoCommit(false);	//设置事物手动提交，出错全部重新导入即可
			statement = connection.createStatement();
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Hashtable<String, String> hashtable = list.get(i);
					
					if(StringUtils.isNotBlank(hashtable.get("work_no"))) {
						//验证work_no是否存在
						StringBuffer checkworkNoSql = new StringBuffer("SELECT * FROM employees a WHERE a.work_no = ?");
						List<Map<String, String>> list = commonService.getItemMapList(DBManager.getConnection(), checkworkNoSql.toString(), new Object[]{hashtable.get("work_no")});
						boolean iscontain = false;
						if(list != null && list.size() > 0) {
							iscontain = true;
						}
						
						if(!iscontain && StringUtils.isNotBlank(hashtable.get("job_status")) && "1".equals(hashtable.get("job_status"))) {
							//如果不包含，则插入数据库
							stringBuffer = new StringBuffer();
							stringBuffer.append("insert into employees(work_no, employees_name, large_area, branch, department, job_title, mobile_phone, login_role, job_status, login_name, pswd, isdelete, create_time, modify_time) values ('");
							stringBuffer.append(hashtable.get("work_no"));
							stringBuffer.append("','");
							stringBuffer.append(hashtable.get("employees_name"));
							stringBuffer.append("','");
							stringBuffer.append(hashtable.get("large_area"));
							stringBuffer.append("','");
							stringBuffer.append(hashtable.get("branch"));
							stringBuffer.append("','");
							stringBuffer.append(hashtable.get("department"));
							stringBuffer.append("','");
							stringBuffer.append(hashtable.get("job_title"));
							stringBuffer.append("','");
							stringBuffer.append(hashtable.get("mobile_phone"));
							stringBuffer.append("','");
							stringBuffer.append(hashtable.get("login_role"));
							stringBuffer.append("','");
							stringBuffer.append(hashtable.get("job_status"));
							stringBuffer.append("','");
							stringBuffer.append(hashtable.get("work_no"));	//login_name
							stringBuffer.append("','");
							stringBuffer.append("123456");	//pswd
							stringBuffer.append("','");
							stringBuffer.append("0");	//默认未删除
							stringBuffer.append("','");
							stringBuffer.append(dateFormat.format(date));
							stringBuffer.append("','");
							stringBuffer.append(dateFormat.format(date));
							stringBuffer.append("')");
							
							statement.addBatch(stringBuffer.toString());
							sqlNum ++;
						}
					}
				}
			}
			System.out.println("excuteEmployeeToDB ---> sqlNum:" + sqlNum);
			results = statement.executeBatch();
			flag = true;
		} catch (Exception e) {
			System.out.println(ImportExcelService.class.getName() + " --> excuteEmployeeToDB --> excute data error!");
			e.printStackTrace();
			flag = false;
			try {
				if(connection != null)connection.rollback();
				if(statement != null)statement.close();
				if(connection != null)connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if(results != null) {
				for (int i : results) {
					if(i >= 0) {
						successNum ++;
					}
				}
			}
			try {
				if(flag) {
					connection.commit();
					System.out.println("excuteEmployeeToDB --> 成功执行条数：" + successNum);
				}
				if(statement != null)statement.close();
				if(connection != null)connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				statement = null;
				connection = null;
			}
		}
		return flag;
	}
	
	/**
	 * 单条数据执行员工数据，插入到数据库
	 * 
	 * @return
	 * @author wpl 
	 * @date ：May 15, 2014 8:52:34 PM
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> excuteEmployeeToDBByOwn() {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean flag = false;
		
		Date date = new Date();
		int sqlNum = 0;
		try {
			CommonService commonService = new CommonService();
			if(list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Hashtable<String, String> hashtable = list.get(i);
					
					if(StringUtils.isNotBlank(hashtable.get("work_no"))) {
						//验证work_no是否存在
						StringBuffer checkworkNoSql = new StringBuffer("SELECT * FROM employees a WHERE a.work_no = ?");
						List<Map<String, String>> list = commonService.getItemMapList(DBManager.getConnection(), checkworkNoSql.toString(), new Object[]{hashtable.get("work_no")});
						boolean iscontain = false;
						if(list != null && list.size() > 0) {
							iscontain = true;
						}
						
						if(!iscontain && StringUtils.isNotBlank(hashtable.get("job_status")) && "1".equals(hashtable.get("job_status"))) {
							//如果不包含，则插入数据库
//							stringBuffer = new StringBuffer();
//							stringBuffer.append("insert into employees(work_no, employees_name, large_area, branch, department, job_title, mobile_phone, login_role, job_status, login_name, pswd, isdelete, create_time, modify_time) values ('");
//							stringBuffer.append(hashtable.get("work_no"));
//							stringBuffer.append("','");
//							stringBuffer.append(hashtable.get("employees_name"));
//							stringBuffer.append("','");
//							stringBuffer.append(hashtable.get("large_area"));
//							stringBuffer.append("','");
//							stringBuffer.append(hashtable.get("branch"));
//							stringBuffer.append("','");
//							stringBuffer.append(hashtable.get("department"));
//							stringBuffer.append("','");
//							stringBuffer.append(hashtable.get("job_title"));
//							stringBuffer.append("','");
//							stringBuffer.append(hashtable.get("mobile_phone"));
//							stringBuffer.append("','");
//							stringBuffer.append(hashtable.get("login_role"));
//							stringBuffer.append("','");
//							stringBuffer.append(hashtable.get("job_status"));
//							stringBuffer.append("','");
//							stringBuffer.append(hashtable.get("work_no"));	//login_name
//							stringBuffer.append("','");
//							stringBuffer.append("123456");	//pswd
//							stringBuffer.append("','");
//							stringBuffer.append("0");	//默认未删除
//							stringBuffer.append("','");
//							stringBuffer.append(dateFormat.format(date));
//							stringBuffer.append("','");
//							stringBuffer.append(dateFormat.format(date));
//							stringBuffer.append("')");
							
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("work_no", hashtable.get("work_no"));
							map.put("employees_name", hashtable.get("employees_name"));
							map.put("large_area", hashtable.get("large_area"));
							map.put("branch", hashtable.get("branch"));
							map.put("department", hashtable.get("department"));
							map.put("job_title", hashtable.get("job_title"));
							map.put("mobile_phone", hashtable.get("mobile_phone"));
							map.put("login_role", hashtable.get("login_role"));
							map.put("job_status", hashtable.get("job_status"));
							map.put("login_name", hashtable.get("work_no"));
							map.put("pswd", hashtable.get("123456"));
							map.put("isdelete", 0);
							map.put("create_time", dateFormat.format(date));
							map.put("modify_time", dateFormat.format(date));
							
							Long num = commonService.itemMapAdd(DBManager.getConnection(), "employees", map);
							if(num > 0) {
								sqlNum ++;
							}
						}
					}
				}
			}
			System.out.println("excuteEmployeeToDB ---> sqlNum:" + sqlNum);
			flag = true;
		} catch (Exception e) {
			System.out.println(ImportExcelService.class.getName() + " --> excuteEmployeeToDB --> excute data error!");
			e.printStackTrace();
			flag = false;
		}
		result.put("result", flag);
		result.put("num", sqlNum);
		return result;
	}
	
	/**
	 * 解析客户Excel
	 * 
	 * @param fileName
	 * @return
	 * @author wpl 
	 * @date ：May 13, 2014 8:39:44 PM
	 * @version 1.0
	 */
	private List<Hashtable<String, String>> importCustomerExcel(String fileName) {
		ExcelUtil excelUtil = new ExcelUtil(fileName, 0);
		int rows = excelUtil.getSheet().getRows();
		List<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();
		try {
			for (int i = 1; i < rows; i++) {
				Hashtable<String, String> hashtable = new Hashtable<String, String>();
				
				String customer_id = excelUtil.getContentByCell(0, i);	//客户ID
				String customer_name = excelUtil.getContentByCell(1, i);	//公司名称(客户名)
				String customer_rating_id = excelUtil.getContentByCell(2, i);	//服务等级
				String business_contacts = excelUtil.getContentByCell(3, i);	//业务联系人
				String phone = excelUtil.getContentByCell(4, i);	//联系电话
				String adress = excelUtil.getContentByCell(5, i);	//公司地址
				String main_products = excelUtil.getContentByCell(6, i);	//主营产品
				String introduction = excelUtil.getContentByCell(7, i);	//简介
				String cust_service_id = excelUtil.getContentByCell(8, i);	//客服id
				String cust_service_name = excelUtil.getContentByCell(8, i);	//客服名称
				
				hashtable.put("customer_id", customer_id);
				hashtable.put("customer_name", customer_name);
				hashtable.put("customer_rating_id", customer_rating_id);
				hashtable.put("business_contacts", business_contacts);
				hashtable.put("phone", phone);
				hashtable.put("adress", adress);
				hashtable.put("main_products", main_products);
				hashtable.put("introduction", introduction);
				hashtable.put("cust_service_id", cust_service_id);
				hashtable.put("cust_service_name", cust_service_name);
				
				list.add(hashtable);
			}
		} catch (Exception e) {
			System.out.println(ImportExcelService.class.getName() + " --> importCustomerExcel --> parse data error!");
			e.printStackTrace();
		} finally {
			if(excelUtil != null) {
				excelUtil.close();
			}
			deleteFile(fileName);
		}
		
		return list;
	}
	
	/**
	 * 解析合同Excel
	 * 
	 * @param fileName
	 * @return
	 * @author wpl 
	 * @date ：May 13, 2014 8:40:11 PM
	 * @version 1.0
	 */
	private List<Hashtable<String, String>> importContractExcel(String fileName) {
		ExcelUtil excelUtil = new ExcelUtil(fileName, 0);
		int rows = excelUtil.getSheet().getRows();
		try {
			for (int i = 1; i < rows; i++) {
				Hashtable<String, String> hashtable = new Hashtable<String, String>();
				
				String customer_id = excelUtil.getContentByCell(0, i);	//客户工号ID
				String customer_name = excelUtil.getContentByCell(1, i);	//公司名称(客户名)
				String contract_no = excelUtil.getContentByCell(2, i);	//合同编号
				String signing_time = excelUtil.getContentByCell(3, i);	//签约时间
				String cooperation_num = excelUtil.getContentByCell(4, i);	//合作金额
				String branch = excelUtil.getContentByCell(5, i);	//分公司
				String department = excelUtil.getContentByCell(6, i);	//所属部门
				String business_id = excelUtil.getContentByCell(7, i);	//商务代表工号
				String manager_id = excelUtil.getContentByCell(8, i);	//经理工号
				String director_id = excelUtil.getContentByCell(9, i);	//分公司总监工号
				System.out.println(customer_id);
				
				hashtable.put("customer_id", customer_id);
				hashtable.put("customer_name", customer_name);
				hashtable.put("contract_no", contract_no);
				hashtable.put("signing_time", signing_time);
				hashtable.put("cooperation_num", cooperation_num);
				hashtable.put("branch", branch);
				hashtable.put("department", department);
				hashtable.put("business_id", business_id);
				hashtable.put("manager_id", manager_id);
				hashtable.put("director_id", director_id);
				
				list.add(hashtable);
			}
		} catch (Exception e) {
			System.out.println(ImportExcelService.class.getName() + " --> importContractExcel --> parse data error!");
			e.printStackTrace();
		} finally {
			if(excelUtil != null) {
				excelUtil.close();
			}
			deleteFile(fileName);
		}
		return list;
	}
	
	/**
	 * 解析工单Excel
	 * 
	 * @param fileName
	 * @return
	 * @author wpl 
	 * @date ：May 13, 2014 8:40:13 PM
	 * @version 1.0
	 */
	private List<Hashtable<String, String>> importOrderExcel(String fileName) {
		ExcelUtil excelUtil = new ExcelUtil(fileName, 0);
		int rows = excelUtil.getSheet().getRows();
		List<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();
		try {
			for (int i = 1; i < rows; i++) {
				Hashtable<String, String> hashtable = new Hashtable<String, String>();
				
				String contract_no = excelUtil.getContentByCell(0, i);	//合同编号
				String work_order_id_src = excelUtil.getContentByCell(1, i);	//工单细则ID
				String product_type = excelUtil.getContentByCell(2, i);	//产品服务类别
				String buy_items = excelUtil.getContentByCell(3, i);	//购买项目内容
				String years = excelUtil.getContentByCell(4, i);	//服务年限
				String amount = excelUtil.getContentByCell(5, i);	//总金额
				String expiry_date = excelUtil.getContentByCell(6, i);	//到期时间
				
				hashtable.put("contract_no", contract_no);
				hashtable.put("work_order_id_src", work_order_id_src);
				hashtable.put("product_type", product_type);
				hashtable.put("buy_items", buy_items);
				hashtable.put("years", years);
				hashtable.put("amount", amount);
				hashtable.put("expiry_date", expiry_date);
				
				list.add(hashtable);
			}
		} catch (Exception e) {
			System.out.println(ImportExcelService.class.getName() + " --> importOrderExcel --> parse data error!");
			e.printStackTrace();
		} finally {
			if(excelUtil != null) {
				excelUtil.close();
			}
			deleteFile(fileName);
		}
		
		return list;
	}
	
	/**
	 * 解析服务进程Excel
	 * 
	 * @param fileName
	 * @return
	 * @author wpl 
	 * @date ：May 13, 2014 8:40:17 PM
	 * @version 1.0
	 */
	private List<Hashtable<String, String>> importProcessExcel(String fileName) {
		ExcelUtil excelUtil = new ExcelUtil(fileName, 0);
		int rows = excelUtil.getSheet().getRows();
		try {
			for (int i = 1; i < rows; i++) {
				Hashtable<String, String> hashtable = new Hashtable<String, String>();
				
				String contract_no = excelUtil.getContentByCell(0, i);	//合同号
				String work_order_id_src = excelUtil.getContentByCell(1, i);	//工单细则id
				String service_pro_name = excelUtil.getContentByCell(2, i);	//服务进程名称
				String context = excelUtil.getContentByCell(3, i);	//描述
				String create_time = excelUtil.getContentByCell(4, i);	//到期时间
				
				hashtable.put("contract_no", contract_no);
				hashtable.put("work_order_id_src", work_order_id_src);
				hashtable.put("service_pro_name", service_pro_name);
				hashtable.put("context", context);
				hashtable.put("create_time", create_time);
				
				list.add(hashtable);
			}
		} catch (Exception e) {
			System.out.println(ImportExcelService.class.getName() + " --> importProcessExcel --> parse data error!");
			e.printStackTrace();
		} finally {
			if(excelUtil != null) {
				excelUtil.close();
			}
			deleteFile(fileName);
		}
		return list;
	}
	
	/**
	 * 解析问题Excel
	 * 
	 * @param fileName
	 * @return
	 * @author wpl 
	 * @date ：May 13, 2014 8:40:20 PM
	 * @version 1.0
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private List<Hashtable<String, String>> importQuestionExcel(String fileName) {
		ExcelUtil excelUtil = new ExcelUtil(fileName, 0);
		int rows = excelUtil.getSheet().getRows();
		try {
			for (int i = 1; i < rows; i++) {
				Hashtable<String, String> hashtable = new Hashtable<String, String>();
				
				String contract_no = excelUtil.getContentByCell(0, i);	//合同号
				String work_order_src_id = excelUtil.getContentByCell(1, i);	//工单细则id
				String question_type = excelUtil.getContentByCell(2, i);	//问题类型
				String context = excelUtil.getContentByCell(3, i);	//描述
				String single_person = excelUtil.getContentByCell(4, i);	//弹单人
				String department = excelUtil.getContentByCell(5, i);	//部门
				String create_time = excelUtil.getContentByCell(6, i);	//时间
				
				hashtable.put("contract_no", contract_no);
				hashtable.put("work_order_src_id", work_order_src_id);
				hashtable.put("question_type", question_type);
				hashtable.put("context", context);
				hashtable.put("single_person", single_person);
				hashtable.put("department", department);
				hashtable.put("create_time", create_time);
				
				list.add(hashtable);
			}
		} catch (Exception e) {
			System.out.println(ImportExcelService.class.getName() + " --> importQuestionExcel --> parse data error!");
			e.printStackTrace();
		} finally {
			if(excelUtil != null) {
				excelUtil.close();
			}
			deleteFile(fileName);
		}
		return list;
	}
	
	/**
	 * 解析员工Excel
	 * 
	 * @param fileName
	 * @return
	 * @author wpl 
	 * @date ：May 13, 2014 8:40:20 PM
	 * @version 1.0
	 */
	private List<Hashtable<String, String>> importEmployeeExcel(String fileName) {
		ExcelUtil excelUtil = new ExcelUtil(fileName, 0);
		int rows = excelUtil.getSheet().getRows();
		try {
			for (int i = 1; i < rows; i++) {
				Hashtable<String, String> hashtable = new Hashtable<String, String>();
				
				String work_no = excelUtil.getContentByCell(0, i);	//工号
				String employees_name = excelUtil.getContentByCell(1, i);	//姓名
				String large_area = excelUtil.getContentByCell(2, i);	//姓名
				String branch = excelUtil.getContentByCell(3, i);	//分公司
				String department = excelUtil.getContentByCell(4, i);	//所属部门
				String job_title = excelUtil.getContentByCell(5, i);	//职位名称
				String mobile_phone = excelUtil.getContentByCell(6, i);	//手机号
				String login_role = excelUtil.getContentByCell(7, i);	//登录角色1,商务,2客服
				String job_status = excelUtil.getContentByCell(8, i);	//离职状态:1在职,0离职
				
				hashtable.put("work_no", work_no);
				hashtable.put("employees_name", employees_name);
				hashtable.put("large_area", large_area);
				hashtable.put("branch", branch);
				hashtable.put("department", department);
				hashtable.put("job_title", job_title);
				hashtable.put("mobile_phone", mobile_phone);
				hashtable.put("login_role", login_role);
				hashtable.put("job_status", job_status);
				
				list.add(hashtable);
			}
		} catch (Exception e) {
			System.out.println(ImportExcelService.class.getName() + " --> importEmployeeExcel --> parse data error!");
			e.printStackTrace();
		} finally {
			if(excelUtil != null) {
				excelUtil.close();
			}
			deleteFile(fileName);
		}
		return list;
	}
	
	private boolean deleteFile(String fileName) {
		boolean bln = false;
		//删除文件
		File file = new File(fileName);
		if(file.exists() && file.isFile()) {
			file.delete();
			bln = true;
		}
		return bln;
	}
	
	/** 
     * 手机号验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3-8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }  

	
}
