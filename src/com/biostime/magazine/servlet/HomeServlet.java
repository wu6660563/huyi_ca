/**
 * 
 */
package com.biostime.magazine.servlet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.dbutils.QueryRunner;
import com.baiytfp.commonlib.base.BaseServlet;
import com.baiytfp.commonlib.base.service.CommonService;
import com.baiytfp.commonlib.util.StringUtils;
import com.biostime.customer.model.Employees;
import com.biostime.customer.model.InfoDiss;
import com.biostime.customer.model.PushInfo;
import com.biostime.magazine.common.DBManager;
import com.biostime.magazine.common.Page;
import com.biostime.magazine.common.PageUtils;

/**
 * 后台首页
 * 
 * @author lj 新增日期：2014-05-17
 * @version 1.0
 */
public class HomeServlet extends BaseServlet {
	PageUtils pageUtils = new PageUtils();
	Page page = null;

	@Override
	public void doHandle() {
		String operType = getParameter("operType");
		if (org.apache.commons.lang.StringUtils.isBlank(operType)) {
			operType = "index";
		}

		try {
			if (operType.equals("index")) { // 访问首页
				index();
			} else if (operType.equals("list")) { // 列表数据
				list();
			} else if (operType.equals("accout")) {// 账户管理
				accout();
			} else if (operType.equals("login_log")) {// 登录记录
				login_log();
			} else if (operType.equals("updateAccout")) {// 离职
				updateAccout();
			} else if (operType.equals("retrievePwd")) {// 找回密码
				retrievePwd();
			} else if (operType.equals("clicent_PushInfo")) {// 客户推送信息
				clicent_PushInfo();
			} else if (operType.equals("delete_clicent_PushInfo")) {// 删除客户推送信息
				delete_clicent_PushInfo();
			} else if (operType.equals("business_PushInfo")) {// 商务推送信息
				business_PushInfo();
			} else if (operType.equals("info_diss")) {// 业务咨询信息
				info_diss();
			} else if (operType.equals("add_info_diss")) {// 业务咨询信息 add
				add_info_diss();
			} else if (operType.equals("find_info_diss")) {// 业务咨询信息 find
				find_info_diss();
			} else if (operType.equals("update_info_diss")) {// 业务咨询信息 update
				update_info_diss();
			} else if (operType.equals("del_info_diss")) {// 业务咨询信息 del
				del_info_diss();
			} else if (operType.equals("add_PushInfo")) {// 业绩播报
				add_PushInfo();
			} else {
				index();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 账户管理列表数据
	 * 
	 * @author lj 新增日期：2014-05-17
	 * @version 1.0
	 * @throws Exception
	 */
	private void accout() throws Exception {
		CommonService cs = new CommonService();
		List<Employees> employeesList = cs.getItemMapList(DBManager
				.getConnection(),
				"SELECT * FROM employees e ORDER BY e.employees_id ASC", null);
		setAttribute("employeesList", employeesList);
		requestForward("reciprocity_admin/zhang-hu-guan-li.jsp");
		// renderJson("list", employeesList);
	}

	/**
	 * 
	 * 找回密码
	 * 
	 * @author lj 新增日期：2014-05-17
	 * @version 1.0
	 * @throws Exception
	 */
	private void retrievePwd() throws Exception {
		CommonService cs = new CommonService();
		// 客户名
		String customerName = getParameter("customerName");
		// 合同号
		String contract_no = getParameter("contract_no");

		Map customer_info = cs
				.itemMapRead(
						DBManager.getConnection(),
						"SELECT *FROM customer_info cu LEFT JOIN contract_info co  ON co.customer_info_id=cu.customer_info_id WHERE cu.customer_name= ?AND co.contract_no=?",
						new Object[] { customerName, contract_no });

		if (customer_info != null) {
			setAttribute("customer_info_pswd", customer_info.get("pswd"));
		} else {
			setAttribute("error", true);
		}
		setAttribute("customerName", customerName);
		setAttribute("contract_no", contract_no);

		requestForward("reciprocity_admin/zhao-hui-mi-ma.jsp");
	}

	/**
	 * 离职
	 * 
	 * @author lj 新增日期：2014-05-17
	 * @version 1.0
	 * @throws Exception
	 */
	private void updateAccout() throws Exception {
		QueryRunner qr = new QueryRunner(true);
		String employees_id = getParameter("employees_id");
		qr
				.update(
						DBManager.getConnection(),
						"UPDATE  employees e SET e.job_status=0   WHERE e.employees_id=?",
						new Object[] { employees_id });
		sendRedirect("employee.action?operType=list");
	}

	/**
	 * 登录信息列表
	 * 
	 * @author lj 新增日期：2014-05-17
	 * @version 1.0
	 * @throws Exception
	 */
	private void login_log() throws Exception {
		CommonService cs = new CommonService();
		// 0-升序, 1-降序
		Integer sort = getIntegerParameter("sort");
		// 登录时间login_timeStart
		String login_timeStart = getParameter("login_timeStart");
		// 登录时间login_timeEnd
		String login_timeEnd = getParameter("login_timeEnd");
		// 同一设备不同账号登录次数 login_number
		String login_number = getParameter("login_number");
		// 用户名user_name
		String user_name = getParameter("user_name");
		List<Map> loginInfoList = null;
		List<Map> loginInfoList1 = null;
		List<Map> loginInfoList4 = null;
		List<Map> loginInfoList6 = null;
		StringBuffer sql = new StringBuffer(
				"SELECT * FROM login_info where 1=1");
		List params = new ArrayList();
		try {

			// 用户名user_name
			if (user_name != null && user_name != "") {
				sql.append(" AND user_name = ? ");
				params.add(user_name);
			}

			// 登录时间login_timeStart
			if (login_timeStart != "" && login_timeStart != null) {
				sql.append(" AND login_time >=? ");
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				params.add(sdf.parse(login_timeStart + " 00:00:00"));
			}
			// 登录时间login_timeEnd
			if (login_timeEnd != "" && login_timeEnd != null) {
				sql.append(" AND login_time <=? ");
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				params.add(sdf.parse(login_timeEnd + " 23:59:59"));
			}
			if (sort != null) {
				sql.append(" GROUP BY user_id ORDER BY login_info_id DESC ");
			} else {
				sql.append(" GROUP BY user_id ORDER BY login_info_id ASC ");
			}
			System.out.println(sql.toString());
			loginInfoList = cs.getItemMapList(DBManager.getConnection(), sql
					.toString(), params.toArray());
			List<Map> new_loginInfoList = new ArrayList();
			// 同一设备不同账号登录次数 login_number
			long new_login_number = 0;

			for (Map loginInfoMap : loginInfoList) {

				new_login_number = cs.getRowNum(DBManager.getConnection(),
						"SELECT * FROM login_info l WHERE l.login_dev_id=? ",
						new Object[] { loginInfoMap.get("login_dev_id"
								.toString()) });

				if (StringUtils.isNotBlank(login_number)
						&& new_login_number == 1) {// 同一设备不同账号登录次数 1
					loginInfoMap.put("login_count", "1");
					new_loginInfoList.add(loginInfoMap);
					loginInfoList1 = new_loginInfoList;
				} else if (StringUtils.isNotBlank(login_number)
						&& new_login_number <= 5) {// 同一设备不同账号登录次数 <5
					loginInfoMap.put("login_count", new_login_number);
					new_loginInfoList.add(loginInfoMap);
					loginInfoList4 = new_loginInfoList;
				} else if (StringUtils.isNotBlank(login_number)
						&& new_login_number >= 5) {// 同一设备不同账号登录次数 >5
					loginInfoMap.put("login_count", new_login_number);
					new_loginInfoList.add(loginInfoMap);
					loginInfoList6 = new_loginInfoList;
				} else {// 同一设备不同账号登录次数
					loginInfoMap.put("login_count", new_login_number);
					new_loginInfoList.add(loginInfoMap);
				}
			}
			if (login_number == "1" || "1".equals(login_number)) {
				loginInfoList = loginInfoList1;
			} else if (login_number == "2" || "2".equals(login_number)) {
				loginInfoList = loginInfoList4;
			} else if (login_number == "3" || "3".equals(login_number)) {
				loginInfoList = loginInfoList6;
			} else {
				loginInfoList = new_loginInfoList;
			}

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("查询登录信息列表出错"+e.printStackTrace());
		} finally {
			setAttribute("loginInfoList", loginInfoList);
			setAttribute("user_name", user_name);
			setAttribute("login_number", login_number);
			setAttribute("login_timeStart", login_timeStart);
			setAttribute("login_timeEnd", login_timeEnd);
			requestForward("reciprocity_admin/login-info.jsp");
		}
	}

	/**
	 * 客户信息推送列表
	 * 
	 * @author lj 新增日期：2014-05-17
	 * @version 1.0
	 * @throws Exception
	 */
	private void clicent_PushInfo() throws Exception {

		CommonService cs = new CommonService();
		// 这个前台页面给，当前页，从1开始
		Integer pageIndex = this.getIntegerParameter("pageIndex");
		if (pageIndex == null) {
			pageIndex = 1;
		}
		// 这个是每页多少数据 10条
		Integer pageSize = 10;
		// 信息
		String msg = getParameter("msg");
		// 0-升序, 1-降序
		Integer sort = getIntegerParameter("sort");
		// 客户名customerName
		String customerName = getParameter("customerName");
		// 合同号contract_no
		String contract_no = getParameter("contract_no");
		// 信息类别(业务类型)info_type
		String info_type = getParameter("info_type");
		// 推送状态push_status
		String push_status = getParameter("push_status");
		// 编辑时间edit_timeStart
		String edit_timeStart = getParameter("edit_timeStart");
		// 编辑时间edit_timeEnd
		String edit_timeEnd = getParameter("edit_timeEnd");

		StringBuffer sql = new StringBuffer(
				"SELECT * FROM push_info p LEFT JOIN contract_info c ON c.business_id=p.business_id left join customer_info u ON u.customer_info_id=c.customer_info_id WHERE p.recipient_type=1 ");
		List<PushInfo> pushInfoList = null;
		List params = new ArrayList();
		try {

			// 客户名customerName
			if (customerName != null && customerName != "") {
				sql.append(" AND u.customer_name = ? ");
				params.add(customerName);
			}
			// 合同号contract_no
			if (contract_no != null && contract_no != "") {
				sql.append(" AND c.contract_no = ? ");
				params.add(contract_no);
			}
			// 信息类别(业务类型)info_type
			if (info_type != null && info_type != "") {
				sql.append(" AND p.info_type = ? ");
				params.add(info_type);
			}
			// 推送状态push_status
			if (push_status != null && push_status != "") {
				sql.append(" AND push_status = ? ");
				params.add(push_status);
			}
			// 编辑时间edit_timeStart
			if (edit_timeStart != "" && edit_timeStart != null) {
				sql.append(" AND p.edit_time >=? ");
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				params.add(sdf.parse(edit_timeStart + " 00:00:00"));
			}
			// 编辑时间edit_timeEnd
			if (edit_timeEnd != "" && edit_timeEnd != null) {
				sql.append(" AND p.edit_time <=? ");
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				params.add(sdf.parse(edit_timeEnd + " 23:59:59"));
			}
			if (sort != null) {
				sql.append(" ORDER BY p.push_info_id ASC ");
			} else {
				sql.append(" ORDER BY p.push_info_id  DESC ");
			}
			System.out.println(sql.toString());
			pushInfoList = cs.getItemMapList(DBManager.getConnection(), sql
					.toString(), params.toArray());
			Long rowCount = 0l;
			if (pushInfoList != null) {
				rowCount = Long.valueOf(pushInfoList.size());
			}
			pushInfoList = pageUtils.getItemMapList(cs, sql.toString(), params
					.toArray(), pageIndex, pageSize);
			page = new Page(pageIndex, pageSize, rowCount.intValue(),
					pushInfoList);
		} catch (Exception e) {
			System.out.println("查询客户信息推送列表出错" + e.getMessage());
		} finally {
			setAttribute("page", page);
			setAttribute("msg", msg);
			setAttribute("customerName", customerName);
			setAttribute("contract_no", contract_no);
			setAttribute("info_type", info_type);
			setAttribute("edit_timeStart", edit_timeStart);
			setAttribute("edit_timeEnd", edit_timeEnd);
			setAttribute("pushInfoList", pushInfoList);
			requestForward("reciprocity_admin/ke-hu-xin-xi-tui-song.jsp");
		}
	}

	/**
	 * 删除客户信息推送列表
	 * 
	 * @author lj 新增日期：2014-05-17
	 * @version 1.0
	 * @throws Exception
	 */
	private void delete_clicent_PushInfo() throws Exception {
		String msg = "";
		// 信息
		String recipient_type = getParameter("recipient_type");
		try {
			long len = 0;
			CommonService cs = new CommonService();
			// 推送信息id push_info_id;
			String push_info_id = getParameter("push_info_id");
			len = cs.itemDel(DBManager.getConnection(), "push_info",
					"WHERE push_info_id=?", new Object[] { push_info_id });

			if (len > 0) {
				msg = "del_true";
			} else {
				msg = "del_false";
			}
		} catch (Exception e) {
			msg = "del_false";
		} finally {
			if ("1".equals(recipient_type)) {
				sendRedirect("home.action?operType=clicent_PushInfo&msg=" + msg);
			} else if ("2".equals(recipient_type)) {
				sendRedirect("home.action?operType=business_PushInfo&msg="
						+ msg);
			}

		}

	}

	/**
	 * 商务信息推送列表
	 * 
	 * @author lj 新增日期：2014-05-17
	 * @version 1.0
	 * @throws Exception
	 */
	private void business_PushInfo() throws Exception {
		CommonService cs = new CommonService();
		// 这个前台页面给，当前页，从1开始
		Integer pageIndex = this.getIntegerParameter("pageIndex");
		if (pageIndex == null) {
			pageIndex = 1;
		}
		// 这个是每页多少数据 10条
		Integer pageSize = 10;
		// 信息
		String msg = getParameter("msg");
		// 0-升序, 1-降序
		Integer sort = getIntegerParameter("sort");
		// 客户名customerName
		String customerName = getParameter("customerName");
		// 合同号contract_no
		String contract_no = getParameter("contract_no");
		// 信息类别(业务类型)info_type
		String info_type = getParameter("info_type");
		// 推送状态push_status
		String push_status = getParameter("push_status");
		// 编辑时间edit_timeStart
		String edit_timeStart = getParameter("edit_timeStart");
		// 编辑时间edit_timeEnd
		String edit_timeEnd = getParameter("edit_timeEnd");
		StringBuffer sql = new StringBuffer(
				"SELECT *,e.employees_name FROM push_info p LEFT JOIN contract_info c ON c.business_id=p.business_id LEFT JOIN employees e ON e.work_no=c.business_id left join customer_info u ON u.customer_info_id=c.customer_info_id WHERE p.recipient_type=2  ");
		List params = new ArrayList();
		List<PushInfo> pushInfoList = null;
		try {
			// 客户名customerName
			if (customerName != null && customerName != "") {
				sql.append(" AND u.customer_name = ? ");
				params.add(customerName);
			}
			// 合同号contract_no
			if (contract_no != null && contract_no != "") {
				sql.append(" AND c.contract_no = ? ");
				params.add(contract_no);
			}
			// 信息类别(业务类型)info_type
			if (info_type != null && info_type != "") {
				sql.append(" AND p.info_type = ? ");
				params.add(info_type);
			}
			// 推送状态push_status
			if (push_status != null && push_status != "") {
				sql.append(" AND push_status = ? ");
				params.add(push_status);
			}
			// 编辑时间edit_timeStart
			if (edit_timeStart != "" && edit_timeStart != null) {
				sql.append(" AND p.edit_time >=? ");
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				params.add(sdf.parse(edit_timeStart + " 00:00:00"));
			}
			// 编辑时间edit_timeEnd
			if (edit_timeEnd != "" && edit_timeEnd != null) {
				sql.append(" AND p.edit_time <=? ");
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				params.add(sdf.parse(edit_timeEnd + " 23:59:59"));
			}
			if (sort != null) {
				sql.append(" ORDER BY p.push_info_id ASC ");
			} else {
				sql.append(" ORDER BY p.push_info_id  DESC");
			}
			System.out.println(sql.toString());
			pushInfoList = cs.getItemMapList(DBManager.getConnection(), sql
					.toString(), params.toArray());
			Long rowCount = 0l;
			if (pushInfoList != null) {
				rowCount = Long.valueOf(pushInfoList.size());
			}
			pushInfoList = pageUtils.getItemMapList(cs, sql.toString(), params
					.toArray(), pageIndex, pageSize);
			page = new Page(pageIndex, pageSize, rowCount.intValue(),
					pushInfoList);
		} catch (Exception e) {
			System.out.println("查询商务信息推送列表出错" + e.getMessage());
		} finally {
			setAttribute("page", page);
			setAttribute("msg", msg);
			setAttribute("customerName", customerName);
			setAttribute("contract_no", contract_no);
			setAttribute("info_type", info_type);
			setAttribute("edit_timeStart", edit_timeStart);
			setAttribute("edit_timeEnd", edit_timeEnd);
			setAttribute("pushInfoList", pushInfoList);
			requestForward("reciprocity_admin/shang-wu-xin-xi-tui-song.jsp");
		}
	}

	/**
	 * 业务播报 add
	 * 
	 * @author lj 新增日期：2014-05-17
	 * @version 1.0
	 * @throws Exception
	 * @throws Exception
	 */
	private void add_PushInfo() throws Exception {
		long len = 0;
		long len_true = 0;// 成功
		long len_false = 0;// 失败
		String msg = "";
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		CommonService cs = new CommonService();
		// 产品
		String product = getParameter("product");
		// 到账金额
		String toBooK = getParameter("toBooK");
		// 签单人
		String sign_bill = getParameter("sign_bill");
		// 时间
		String sign_time = getParameter("sign_time");
		try {
			List<Map> employeesList = cs
					.getItemMapList(
							DBManager.getConnection(),
							"SELECT * FROM employees e WHERE e.login_role=1 AND e.job_status=1",
							null);
			for (Map employeesmap : employeesList) {

				try {
					Map map = new HashMap();
					// 使用json封装
					JSONObject returnjsonobject = new JSONObject();
					// 产品
					returnjsonobject.put("product", product);
					// 到账金额
					returnjsonobject.put("toBooK", toBooK);
					// 签单人
					returnjsonobject.put("sign_bill", sign_bill);
					// 时间
					returnjsonobject.put("sign_time", sign_time);
					// 分公司
					returnjsonobject.put("branch", employeesmap.get("branch"));
					// 部门名称
					returnjsonobject.put("department", employeesmap
							.get("department"));
					// 推送内容
					map.put("push_context", returnjsonobject.toString());
					// 信息类别(业务类型)
					map.put("info_type", "8");
					// 业务ID
					map.put("business_id", employeesmap.get("work_no"));
					// 推送状态
					map.put("push_status", "0");
					// 接收人类型
					map.put("recipient_type", "0");
					// 接收人
					map.put("recipient", employeesmap.get("employees_id"));
					// 接收人类型
					map.put("recipient_type", "2");
					// 手机设备号
					map.put("mobile_no", employeesmap.get("mobile_no"));
					// 手机设备系统
					map.put("mobile_os", employeesmap.get("mobile_os"));
					// 编辑时间
					map.put("edit_time", dateformat.format(date));
					if (employeesmap.get("mobile_no") != null
							&& !employeesmap.get("mobile_no").equals("")) {
						len = cs.itemMapAdd(DBManager.getConnection(),
								"push_info", map);
						if (len > 0) {
							len_true++;
						} else {
							len_false++;
						}
					}

				} catch (Exception e) {
					System.out.println("插入实时播报出错：" + e.getMessage());
					len_false++;
				}
			}
		} catch (Exception e) {
			System.out.println("查询员工表数据出错：" + e.getMessage());
		} finally {
			// 返回插入成功，失败多少条数据
			msg = len_true + ":" + len_false;
			sendRedirect("home.action?operType=business_PushInfo&msg=" + msg);
			System.out.println("插入实时播报成功,失败条数：" + msg);
		}
	}

	/**
	 * 业务咨询列表
	 * 
	 * @author lj 新增日期：2014-05-17
	 * @version 1.0
	 * @throws Exception
	 */
	private void info_diss() throws Exception {
		CommonService cs = new CommonService();
		String msg = getParameter("msg");
		// 0-升序, 1-降序
		Integer sort = getIntegerParameter("sort");
		// 类型ann_type
		String ann_type = getParameter("ann_type");
		// 推送状态push_status
		String push_status = getParameter("push_status");
		// 编辑时间modify_timeStart
		String modify_timeStart = getParameter("modify_timeStart");
		// 编辑时间modify_timeEnd
		String modify_timeEnd = getParameter("modify_timeEnd");

		StringBuffer sql = new StringBuffer("SELECT d.*,p.push_status FROM info_diss d LEFT JOIN push_info p ON p.business_id=d.info_id WHERE 1 = 1 ");
		List params = new ArrayList();
		// 信息类别(业务类型)info_type
		if (ann_type != null && ann_type != "") {
			sql.append(" AND d.ann_type = ? ");
			params.add(ann_type);
		}
		 //推送状态push_status
		 if (push_status!=null) {
		 sql.append(" AND p.push_status = ? ");
		 params.add(push_status);
		 }
		// 编辑时间modify_timeStart
		if (modify_timeStart != "" && modify_timeStart != null) {
			sql.append(" AND 'edit_time' >? ");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			params.add(sdf.parse(modify_timeStart));
		}
		// 编辑时间modify_timeEnd
		if (modify_timeEnd != "" && modify_timeEnd != null) {
			sql.append(" AND 'edit_time' >? ");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			params.add(sdf.parse(modify_timeEnd));
		}
		if (sort != null) {
			sql.append(" ORDER BY d.info_id ASC ");
		} else {
			sql.append(" ORDER BY d.info_id DESC ");
		}
		List<InfoDiss> infoDissList = cs.getItemMapList(DBManager
				.getConnection(), sql.toString(), params.toArray());
		setAttribute("msg", msg);
		setAttribute("ann_type", ann_type);
		setAttribute("push_status", push_status);
		setAttribute("modify_timeStart", modify_timeStart);
		setAttribute("modify_timeEnd", modify_timeEnd);
		setAttribute("infoDissList", infoDissList);
		requestForward("reciprocity_admin/ye-wu-zi-xun.jsp");
	}

	/**
	 * 业务咨询 add
	 * 
	 * @author lj 新增日期：2014-05-17
	 * @version 1.0
	 * @throws Exception
	 * @throws Exception
	 */
	private void add_info_diss() throws Exception {
		String msg = "";
		long len_true = 0;// 成功
		long len_false = 0;// 失败
		try {
			long len = 0;
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			CommonService cs = new CommonService();
			// 标题title
			String title = getParameter("title");
			// 类型
			String ann_type = getParameter("ann_type");
			// 摘要
			String summary = getParameter("summary");
			// 内容
			String context = getParameter("context");
			Map map = new HashMap();
			map.put("title", title);
			map.put("ann_type", ann_type);
			map.put("summary", summary);
			map.put("context", context);
			map.put("create_time", dateformat.format(date));
			map.put("modify_time", dateformat.format(date));
			len = cs.itemMapAdd(DBManager.getConnection(), "info_diss", map);
			if (len > 0) {
				// 更新图片表
				Map pictureMap = new HashMap();
				Map push_infoMap = cs
						.itemMapRead(
								DBManager.getConnection(),
								"SELECT  *FROM info_diss p ORDER BY p.info_id DESC  LIMIT 1",
								new Object[] {});
				// 插入到图片表
				String[] old_filePaths = getParameterValues("old_filePath");
				String[] upImgs = getParameterValues("upImg");
				HashMap<String, String> pictureHash = null;
				if (upImgs != null && upImgs.length > 0) {
					for (int i = 0; i < upImgs.length; i++) {
						pictureHash = new HashMap<String, String>();
						pictureHash.put("business_id", push_infoMap.get(
								"info_id").toString());
						pictureHash.put("business_type", "3"); // 业务咨询
						pictureHash.put("path", upImgs[i]);
						pictureHash.put("or_path", old_filePaths[i]);
						pictureHash.put("sort", "0");
						pictureHash.put("isdelete", "0");
						pictureHash.put("create_time", dateformat.format(date));
						pictureHash.put("modify_time", dateformat.format(date));
						cs.itemMapAdd(DBManager.getConnection(), "picture",
								pictureHash);
					}
				}
				//推送信息
				try {
					List<Map> employeesList = cs
							.getItemMapList(
									DBManager.getConnection(),
									"SELECT * FROM employees e WHERE e.login_role=1 AND e.job_status=0",
									null);
					for (Map employeesmap : employeesList) {

						try {
							Map newemployeesmap = new HashMap();
							// 使用json封装
							JSONObject returnjsonobject = new JSONObject();
							// 业务ID
							returnjsonobject.put("info_id", push_infoMap.get(
									"info_id").toString());
							// 类别
							returnjsonobject.put("ann_type", ann_type);
							// 摘要
							returnjsonobject.put("summary", summary);
							// 推送内容
							newemployeesmap.put("push_context",
									returnjsonobject.toString());
							// 信息类别(业务类型)
							if("1".equals(ann_type)){
								newemployeesmap.put("info_type", "5");
							}else if("2".equals(ann_type)){
								newemployeesmap.put("info_type", "2");
							}else if("3".equals(ann_type)){
								newemployeesmap.put("info_type", "6");
							}else if("4".equals(ann_type)){
								newemployeesmap.put("info_type", "7");
							}
							
							// 业务ID
							newemployeesmap.put("business_id", push_infoMap
									.get("info_id").toString());
							// 推送状态
							newemployeesmap.put("push_status", "0");
							// 接收人类型
							newemployeesmap.put("recipient_type", "0");
							// 接收人
							newemployeesmap.put("recipient", employeesmap
									.get("employees_id"));
							// 接收人类型
							newemployeesmap.put("recipient_type", "2");
							// 手机设备号
							newemployeesmap.put("mobile_no", employeesmap
									.get("mobile_no"));
							// 手机设备系统
							newemployeesmap.put("mobile_os", employeesmap
									.get("mobile_os"));
							// 编辑时间
							newemployeesmap.put("edit_time", dateformat
									.format(date));
							if (employeesmap.get("mobile_no") != null
									&& !employeesmap.get("mobile_no")
											.equals("")) {
								len = cs.itemMapAdd(DBManager.getConnection(),
										"push_info", newemployeesmap);
								if (len > 0) {
									len_true++;
								} else {
									len_false++;
								}
							}

						} catch (Exception e) {
							len_false++;
						}
					}
				} catch (Exception e) {
					System.out.println("查询员工表数据出错：" + e.getMessage());
				}
			} else {
				msg = "add_false";
			}

		} catch (Exception e) {
			msg = "add_false";
		} finally {
			msg = len_true + ":" + len_false;
			sendRedirect("home.action?operType=info_diss&msg=" + msg);
			// 返回插入成功，失败多少条数据
			System.out.println("插入业务资讯成功,失败条数：" + msg);
		}
	}

	/**
	 * 业务咨询 find
	 * 
	 * @author lj 新增日期：2014-05-17
	 * @version 1.0
	 * @throws Exception
	 */
	private void find_info_diss() throws Exception {
		CommonService cs = new CommonService();
		// info_id
		String info_id = getParameter("info_id");
		Map info_dissMap = cs
				.itemMapRead(
						DBManager.getConnection(),
						"SELECT  d.*,p.picture_id,p.or_path,p.path FROM info_diss d  LEFT JOIN picture p ON d.info_id=p.business_id WHERE d.info_id = ?",
						new Object[] { info_id });
		if (info_dissMap != null) {
			setAttribute("info_dissMap", info_dissMap);
			requestForward("reciprocity_admin/update-ye-wu-zi-xun.jsp");
		}
	}

	/**
	 * 业务咨询 update
	 * 
	 * @author lj 新增日期：2014-05-17
	 * @version 1.0
	 * @throws Exception
	 */
	private void update_info_diss() throws Exception {
		String msg = "";
		try {
			long len = 0;
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			CommonService cs = new CommonService();
			// info_id
			String info_id = getParameter("info_id");
			// 标题title
			String title = getParameter("title");
			// 类型
			String ann_type = getParameter("ann_type");
			// 摘要
			String summary = getParameter("summary");
			// 内容
			String context = getParameter("context");
			Map map = new HashMap();
			map.put("title", title);
			map.put("ann_type", ann_type);
			map.put("summary", summary);
			map.put("context", context);
			map.put("create_time", dateformat.format(date));
			map.put("modify_time", dateformat.format(date));
			map.put("info_id", info_id);
			len = cs.itemMapEdit(DBManager.getConnection(), "info_diss ",
					"WHERE info_id=?", info_id, map);
			if (len > 0) {
				cs.itemDel(DBManager.getConnection(), "picture",
						"where business_id=?", new Object[] { info_id });
				// 插入到图片表
				String[] old_filePaths = getParameterValues("old_filePath");
				String[] upImgs = getParameterValues("upImg");
				HashMap<String, String> pictureHash = null;
				if (upImgs != null && upImgs.length > 0) {
					for (int i = 0; i < upImgs.length; i++) {
						pictureHash = new HashMap<String, String>();
						pictureHash.put("business_id", info_id);
						pictureHash.put("business_type", "3"); // 业务咨询
						pictureHash.put("path", upImgs[i]);
						pictureHash.put("or_path", old_filePaths[i]);
						pictureHash.put("sort", "0");
						pictureHash.put("isdelete", "0");
						pictureHash.put("create_time", dateformat.format(date));
						pictureHash.put("modify_time", dateformat.format(date));
						long addlen = cs.itemMapAdd(DBManager.getConnection(),
								"picture", pictureHash);
						if (addlen > 0) {
							msg = "update_true";
						} else {
							msg = "update_false";
						}
					}
				}

			} else {
				msg = "update_false";
			}
		} catch (Exception e) {
			msg = "update_false";
		} finally {
			sendRedirect("home.action?operType=info_diss&msg=" + msg);
		}

	}

	/**
	 * 业务咨询 del
	 * 
	 * @author lj 新增日期：2014-05-17
	 * @version 1.0
	 * @throws Exception
	 */
	private void del_info_diss() throws Exception {
		String msg = "";
		try {
			long len = 0;
			CommonService cs = new CommonService();
			// info_id
			String info_id = getParameter("info_id");
			len = cs.itemDel(DBManager.getConnection(), "info_diss",
					"WHERE info_id=?", new Object[] { info_id });
			if (len > 0) {
				msg = "del_true";
			} else {
				msg = "del_false";
			}
		} catch (Exception e) {
			msg = "del_false";
		} finally {
			sendRedirect("home.action?operType=info_diss&msg=" + msg);
		}

	}

	/**
	 * 列表数据
	 * 
	 * @author cjl 新增日期：2014-3-7下午03:50:04
	 * @version 1.0
	 * @throws Exception
	 */
	private void list() throws Exception {
		CommonService cs = new CommonService();

		// 0-升序, 1-降序
		Integer sort = getIntegerParameter("sort");
		// 期数或日期
		String keyword = getParameter("keyword");
		// 0-期数, 1-日期
		Integer isDate = getIntegerParameter("isDate");

		StringBuffer sql = new StringBuffer(
				" SELECT magazine_id,magazine_phase,date_format(publish_time,'%Y.%m.%d') AS publish_time,is_free,magazine_path,create_time,modif_time,magazine_state FROM magazine_info WHERE magazine_state != 1 AND magazine_type = 0 ");
		List params = new ArrayList();

		if (StringUtils.isNotBlank(keyword)) {
			if (isDate.intValue() == 0) {
				sql.append(" AND magazine_phase = ? ");
				params.add(keyword);
			} else {
				sql.append(" AND publish_time = ? ");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
				params.add(sdf.parse(keyword));
			}
		}

		if (sort.intValue() == 0) {
			sql.append(" ORDER BY magazine_phase DESC ");
		} else {
			sql.append(" ORDER BY magazine_phase ASC ");
		}
		List list = cs.getItemMapList(DBManager.getConnection(),
				sql.toString(), params.toArray());
		renderJson("list", list);
	}

	/**
	 * 访问首页
	 * 
	 * @author lj 新增日期：2014-05-17
	 * @version 1.0
	 * @throws Exception
	 */
	private void index() throws Exception {
		// CommonService cs = new CommonService();
		//		
		// Long pagesize = new Long(Configuration.read("pagesize"));
		// Long rowCount = cs.getRowNum(DBManager.getConnection(),
		// " SELECT count(magazine_id) FROM magazine_info WHERE magazine_state !=1 AND magazine_type = 0 ",
		// new Integer[]{});
		// Long maxPage = rowCount % pagesize == 0 ? rowCount / pagesize :
		// rowCount / pagesize + 1;
		// maxPage = (maxPage == 0 ? 1 : maxPage);
		//		
		// setAttribute("maxPage", maxPage);
		// setAttribute("pagesize", pagesize);

		requestForward("/reciprocity_admin/index.jsp");
	}

}
