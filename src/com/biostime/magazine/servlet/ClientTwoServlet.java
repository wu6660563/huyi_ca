package com.biostime.magazine.servlet;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import com.baiytfp.commonlib.base.BaseServlet;
import com.baiytfp.commonlib.util.ResponseUtil;
import com.baiytfp.webbase.util.RequestUtil;
import com.biostime.magazine.common.DBManager;

/**
 * 前端业务处理ServletTwo
 * 
 * @author xc 新增日期：2014-2-14上午11:18:04
 * @version 1.0
 */
public class ClientTwoServlet extends BaseServlet {
	
	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doHandle() {
		try {
			String methType = this.getParameter("methType");
			if (methType.equalsIgnoreCase("other")) {// 客户评论1 客户赞2
				other();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 客户评论1 客户赞2 客户浏览数3
	 * 
	 * @author xc 新增日期：2014-2-14上午11:29:28
	 * @version 1.0
	 * @throws Exception
	 */
	public void other() throws Exception {
		String operType = this.getParameter("operType");
		String articleId = this.getParameter("articleId");
		QueryRunner qr = new QueryRunner(true);
		Connection connection = DBManager.getConnection();
		if(operType.equals("1")){
			List<Object> params = new ArrayList<Object>();
			params.add(articleId);
			String content = this.getParameter("content");
			params.add(content);
			String temn_code = this.getParameter("temn_code");
			params.add(temn_code);
			String temn_name = this.getParameter("temn_name");
			params.add(temn_name);
			String temn_city = this.getParameter("temn_city");
			params.add(temn_city);
			String comments_name = this.getParameter("comments_name");
			params.add(comments_name);
			String position = this.getParameter("position");
			params.add(position);
			qr.update(connection, "INSERT INTO article_comment ( article_id, content, temn_code, temn_name, temn_city, comments_name, position, is_delete, create_time ) VALUES (?, ?, ?, ?, ?, ?, ?, 0, NOW())", params.toArray());
		}else if(operType.equals("2")){
			qr.update(connection, "INSERT INTO read_history ( article_id, ip_address, praise, create_time ) VALUES (?, ?, 1, NOW())", new Object[] { articleId,RequestUtil.getClientIpAddr(this.getRequest()) });
			qr.update(connection, "UPDATE article SET article_total = article_total+1 WHERE article_id = ?", new Object[] { articleId });
		}else if(operType.equals("3")){
			qr.update(connection, "INSERT INTO read_history ( article_id, ip_address, praise, create_time ) VALUES (?, ?, 0, NOW())", new Object[] { articleId,RequestUtil.getClientIpAddr(this.getRequest()) });
			qr.update(connection, "UPDATE article SET article_read = article_read+1 WHERE article_id = ?", new Object[] { articleId });
		}
		DbUtils.closeQuietly(connection);
		ResponseUtil.sendJsonOK(this.getResponse());
	}

}
