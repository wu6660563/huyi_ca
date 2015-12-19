package com.biostime.magazine.servlet;

/**
 * 
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.baiytfp.commonlib.base.BaseServlet;
import com.baiytfp.commonlib.base.Scope;
import com.baiytfp.commonlib.base.service.CommonService;
import com.baiytfp.commonlib.util.ResponseUtil;
import com.baiytfp.commonlib.util.StringUtil;
import com.baiytfp.commonlib.util.StringUtils;
import com.biostime.magazine.common.DBManager;
import com.biostime.magazine.utils.ToolUtils;
import com.biostime.report.constant.SystemConstant;

/**
 * 登陆
 * 
 * @author lj 新增日期：2014-05-17
 * @version 1.0
 */
public class LoginServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public void doHandle() {
		String operType = getParameter("operType");

		try {
			if (StringUtils.isNotBlank(operType) && operType.equals("logout")) { // 访问首页
				logout();
			} else if (StringUtils.isNotBlank(operType) && operType.equals("clientLogin")) { // 客户端登陆
				clientLogin();
			} else if (StringUtils.isNotBlank(operType) && operType.equals("updatePassword")) { // 修改密码
				updatePassword();
			} else { // 排序
				login();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 登出
	 * 
	 * @author lj 新增日期：2014-05-17
	 * @version 1.0
	 */
	private void logout() {
		getSession().invalidate();
		requestForward("page/login.jsp");
	}

	/**
	 * 登陆
	 * 
	 * @author lj 新增日期：2014-05-17
	 * @version 1.0
	 */
	private void login() {
		String username = getParameter("username");
		String password = getParameter("password");
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			requestForward("page/login.jsp");
			return;
		}
		CommonService cs;
		try {
			boolean loginFlag = false;
			cs = new CommonService();
			Map userinfo = cs.itemMapRead(DBManager.getConnection(), " SELECT * FROM employees WHERE login_name=? AND pswd=? AND isdelete=0 ", new Object[]{username, password});
			if (userinfo != null) {
				Integer userId = StringUtils.toInteger(StringUtil.getMapString(userinfo, "employees_id", "0"));
				if (userId != null && userId.intValue() > 0) {
					loginFlag = true;
				}
			}

			if (!loginFlag) { // 登陆不成功
				setAttribute("error", "true", Scope.REQUEST);
				requestForward("page/login.jsp");
				return;
			} else { // 登陆成功
				setAttribute("userinfo", userinfo, Scope.SESSION);
				requestForward("reciprocity_admin/index.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * app端登陆接口
	 * 
	 * @author zjl 新增日期：2014-05-21
	 * 
	 * 需要参数 ：
	 * username：登陆名称  
	 * password：登陆密码 
	 * type：登陆类型（客户app：CUSTOMERINFO ，商务app：EMPLOYEE）
	 * version：手机设备系统版本号
	 * serialNumber：手机设备号
	 * 
	 * 返回参数：
	 * status：登陆状态
	 * loginName：登陆名称
	 * cId：（信息id，客户app为：customer_id  商务app为：employees_id）
	 */
	@SuppressWarnings("unchecked")
	public void clientLogin() throws Exception {
		// 定义返回数据存储对象map
		CommonService cs = new CommonService();
		Map<String, Object> map = new HashMap<String, Object>();
		String requestParam = ToolUtils.getStringByInputData(request);
		Map userMap = new HashMap();
		String type = null;
		try {
			if(StringUtils.isNotBlank(requestParam)){// 数据不为空，进行登陆流程
				JSONObject jsonObject = JSONObject.fromObject(requestParam);
				String name = jsonObject.getString("userName");
				String pwd =  jsonObject.getString("password");
				 type = jsonObject.getString("type");
				String userSql = null;
				if(null != type && !"".equalsIgnoreCase(type) && type.equalsIgnoreCase(SystemConstant.CUSTOMERINFO)){//客户app登陆
					userSql = "SELECT * FROM customer_info WHERE login_name = ? AND pswd = ? AND isdelete=0"; 
				}else if(null != type && !"".equalsIgnoreCase(type) && type.equalsIgnoreCase(SystemConstant.EMPLOYEE)){ //商务app登陆
					userSql = "SELECT * FROM employees WHERE login_name=? AND pswd=? AND isdelete=0 ";
				}
				 userMap = cs.itemMapRead(DBManager.getConnection(), userSql, new Object[]{name,pwd});
				//获取当前时间
			    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				if (userMap == null) {
					map.put("status", false);
					map.put("result", "用户不存在或密码不正确!");
				} else {
					//如果登陆成功，则需要修改登陆信息，并将登陆结果返回给app端
					if(null != type && !"".equalsIgnoreCase(type) && type.equalsIgnoreCase(SystemConstant.CUSTOMERINFO)){  //客户app
						String version = jsonObject.getString("version"); //系统版本
						String serialNumber = jsonObject.getString("serialNumber");//手机设备号
						String ip = jsonObject.getString("ip");//ip
						if(version.contains("iPhone")){
							serialNumber = serialNumber.replace(" ", "");
							serialNumber = serialNumber.substring(1,serialNumber.length()-1);
						}
						Map articleMap= new HashMap();
						Map loginInfoMap= new HashMap();
				        articleMap.put("mobile_no", serialNumber);
				        articleMap.put("mobile_os", version);  
				        articleMap.put("last_login_time", df.format(new Date()));
						cs.itemMapEdit(DBManager.getConnection(), "customer_info", " WHERE customer_id = "+userMap.get("customer_id")+" AND isdelete = 0  ", articleMap);
						map.put("cId", userMap.get("customer_info_id"));
						loginInfoMap.put("user_name", name);
						loginInfoMap.put("login_time", df.format(new Date()));
						loginInfoMap.put("login_ip", ip);
						loginInfoMap.put("login_dev_id", serialNumber);
						loginInfoMap.put("user_type", 1);
						loginInfoMap.put("user_id", userMap.get("customer_info_id"));
						cs.itemMapAdd(DBManager.getConnection(), "login_info", loginInfoMap);
					}else if(null != type && !"".equalsIgnoreCase(type) && type.equalsIgnoreCase(SystemConstant.EMPLOYEE)){ //商务app
						map.put("cId", userMap.get("employees_id"));
//						String longitude = jsonObject.getString("longitude");//经度
//						String latitude = jsonObject.getString("latitude");//纬度
//						String loginAdress = jsonObject.getString("loginAdress"); //登陆地址
						String version = jsonObject.getString("version"); //系统版本
						String serialNumber = jsonObject.getString("serialNumber");//手机设备号
						String ip = jsonObject.getString("ip");//ip
						if(version.contains("iPhone")){
							serialNumber = serialNumber.replace(" ", "");
							serialNumber = serialNumber.substring(1,serialNumber.length()-1);
						}
						Map articleMap= new HashMap();
						Map loginInfoMap= new HashMap();
				        articleMap.put("mobile_no", serialNumber);
				        articleMap.put("mobile_os", version);  
//				        articleMap.put("longitude", longitude);
//				        articleMap.put("latitude", latitude);
//				        articleMap.put("loginAdress", loginAdress);
						cs.itemMapEdit(DBManager.getConnection(), "employees", " WHERE employees_id = "+userMap.get("employees_id")+" AND isdelete = 0  ", articleMap);
						map.put("cId", userMap.get("employees_id"));
						loginInfoMap.put("user_name", name);
						loginInfoMap.put("login_time", df.format(new Date()));
						loginInfoMap.put("login_ip", ip);
						loginInfoMap.put("login_dev_id", serialNumber);
						loginInfoMap.put("user_type", 2);
						loginInfoMap.put("user_id", userMap.get("employees_id"));
						cs.itemMapAdd(DBManager.getConnection(), "login_info", loginInfoMap);
					}
					map.put("status", true);
					map.put("loginName", name);
					map.put("loginType",type );
					System.out.println("-----------------登陆成功--------------------");
				}
			} else {// 数据为空，则抛出提示信息
				map.put("status", false);
				map.put("result", "非法请求!");
			}
		} catch (Exception e) {
			map.put("status", false);
			map.put("result", "请求参数错误!!");
		} finally {
			try {
				ResponseUtil.sendJsonObj(response, map);
			} catch (Exception e) {
				System.err.println("返回JSON数据出错");
			}
		}
	}
	
	private void updatePassword() throws Exception {
		CommonService cs = new CommonService();
		String requestParam = ToolUtils.getStringByInputData(request);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(requestParam)){// 数据不为空，进行登陆流程
			JSONObject jsonObject = JSONObject.fromObject(requestParam);
			String userId = jsonObject.getString("userId");
			String oldPassword = jsonObject.getString("oldPassword");
			String newPswd =  jsonObject.getString("newPswd");
			String type = jsonObject.getString("type");
			Map<String, String> articleMap= new HashMap<String, String>();
			long resultNum = 0;
			try {
				if(null != type && !"".equalsIgnoreCase(type) && type.equalsIgnoreCase(SystemConstant.CUSTOMERINFO)) {//客户app登陆
//					userSql = "update customer_info set pswd = ? WHERE login_name = ? and pswd = ?"; 
					articleMap.put("pswd", newPswd);
					resultNum = cs.itemMapEdit(DBManager.getConnection(), "customer_info", " WHERE customer_info_id = '" + userId + "' AND pswd = '" + oldPassword + "'", articleMap);
				} else if(null != type && !"".equalsIgnoreCase(type) && type.equalsIgnoreCase(SystemConstant.EMPLOYEE)) { //商务app登陆
//					userSql = "update employees set pswd = ? WHERE login_name = ? and pswd = ?";
					articleMap.put("pswd", newPswd);
					resultNum = cs.itemMapEdit(DBManager.getConnection(), "employees", " WHERE employees_id = '" + userId + "' AND pswd = '" + oldPassword + "'", articleMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
				resultNum = 0;
			} finally {
				if(resultNum == 0) {
					resultMap.put("status", false);
					resultMap.put("result", "原密码错误，修改失败！");
				} else if(resultNum > 0) {
					resultMap.put("cId",userId);
					resultMap.put("status", true);
					resultMap.put("result", "修改成功！");
					System.out.println("-----修改密码成功------");
				}
				try {
					ResponseUtil.sendJsonObj(response, resultMap);
				} catch (Exception e) {
					System.err.println("返回JSON数据出错");
				}
			}
			
			
		}
		
	}
	
}
