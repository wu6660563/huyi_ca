/**
 * 
 */
package com.biostime.magazine.common;

import java.sql.Connection;
import java.sql.DriverManager;

import com.baiytfp.commonlib.commons.Configuration;

/**
 * 数据连接管理类
 */
public class DBManager {

	public static Connection conn;

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 * @throws Exception
	 * @version cibms 1.0
	 */
	public static Connection getConnection() {
		String mysqlurl = Configuration.read("mysqlurl");
		String username = Configuration.read("username");
		String password = Configuration.read("password");
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(mysqlurl, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void main(String[] args) {
		DBManager d = new DBManager();
		d.getConnection();
	}
}
