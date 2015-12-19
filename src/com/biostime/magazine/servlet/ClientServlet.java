package com.biostime.magazine.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.baiytfp.commonlib.base.BaseServlet;
import com.baiytfp.commonlib.base.Scope;
import com.baiytfp.commonlib.base.service.CommonService;
import com.baiytfp.commonlib.util.ResponseUtil;
import com.biostime.magazine.common.DBManager;
import com.biostime.magazine.common.Page;
import com.biostime.magazine.common.PageUtils;

/**
 * 前端业务处理Servlet
 * 
 * @author xc 新增日期：2014-2-14上午11:18:04
 * @version 1.0
 */
public class ClientServlet extends BaseServlet {
	
	/**
	 * 说明清楚此属性的业务含义
	 */
	private static final long serialVersionUID = 1L;
	
	private CommonService cs;
	private String onlyKey;
	private String magazineType;

	@Override
	public void doHandle() {
		try {
			cs = new CommonService();
			
			onlyKey = this.getParameter("onlyKey");
			if(StringUtils.isNotBlank(onlyKey)){
				setAttribute("onlyKey", onlyKey, Scope.SESSION);
			}
			
			magazineType = this.getParameter("magazineType");
			if(StringUtils.isNotBlank(magazineType)){
				setAttribute("magazineType", magazineType, Scope.SESSION);
			}
			
			String methType = this.getParameter("methType");
			if (methType.equalsIgnoreCase("index")) {// 首页
				index();
			}else if (methType.equalsIgnoreCase("info")) {// 杂志详情
				info();
			}else if (methType.equalsIgnoreCase("content")) {// 文章详情
				content();
			}else if (methType.equalsIgnoreCase("cl")) {// 文章评论信息 
				cLeave();
			}
		} catch (Exception e) {
			if(!e.getMessage().equals("java.lang.IllegalStateException: Cannot forward after response has been committed")){
				System.out.println(e.getMessage());
			}
			//System.out.println(e.getMessage().equals("java.lang.IllegalStateException: Cannot forward after response has been committed")?this.getAttribute("onlyKey", Scope.SESSION).toString()+"前端用户刷新过于频繁！":e.getMessage());
		}
	}
	
	/**
	 * 首页
	 * 
	 * @throws Exception
	 * @author db 新增日期：2014-3-15下午05:47:18
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public void index() throws Exception {
		// 获取杂志信息
		String sql = "SELECT m.`magazine_id`,m.`magazine_path` FROM magazine_info m WHERE m.magazine_type = ? AND m.`magazine_state` = 2 ORDER BY magazine_phase DESC";
		List miList = cs.getItemMapList(DBManager.getConnection(), sql, new Object[]{ getAttribute("magazineType", Scope.SESSION) });
		this.setAttribute("miList", miList);
		List fillList = new ArrayList();
		int size = 13 - miList.size();
		if(size > 0){
			for (int i = 0; i < size; i++) {
				fillList.add(i);
			}
		}
		this.setAttribute("fillList", fillList);
		this.requestForward("/client/index.jsp");
	}
	
	/**
	 * 杂志详情
	 * 
	 * @throws Exception
	 * @author db 新增日期：2014-3-15下午05:47:18
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public void info() throws Exception {
		String magazineId = this.getParameter("magazineId");
		
		// 获取杂志信息
		String sql = "SELECT m.`magazine_id`, m.`magazine_path` FROM magazine_info m WHERE m.`magazine_id` = ?";
		Map miMap = cs.itemMapRead(DBManager.getConnection(), sql, new Object[]{ magazineId });
		this.setAttribute("miMap", miMap);
		// 获取杂志目录
		String channelSql = "SELECT channel_id, title FROM channel_info WHERE is_delete = 0 AND magazine_id = ? ORDER BY sort_time DESC";
		List channelList = cs.getItemMapList(DBManager.getConnection(), channelSql, new Object[]{ magazineId });
		List caList = new ArrayList();
		for (int i = 0; i < channelList.size(); i++) {
			Map map = (Map) channelList.get(i);
			// 获取目录下的文章
			String articleSql = "SELECT article_id, title, content, article_pic FROM article WHERE is_delete = 0 AND channel_id = ? ORDER BY sort_time DESC";
			List artList = cs.getItemMapList(DBManager.getConnection(), articleSql, new Object[]{ map.get("channel_id") });
			map.put("artList", artList);
			caList.add(map);
		}
		this.setAttribute("caList", caList);
		this.requestForward("/client/zz_info.jsp");
	}
	
	/**
	 * 文章详情
	 * 
	 * @throws Exception
	 * @author db 新增日期：2014-3-15下午05:47:18
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public void content() throws Exception {
		String magazineId = this.getParameter("magazineId");
		this.setAttribute("magazineId", magazineId);
		String articleId = this.getParameter("articleId");
		String sql = "SELECT article_id, title, DATE_FORMAT(edit_time,'%Y-%m-%d  %H:%i') AS edit_time, content, article_pic, article_total, author FROM article WHERE is_delete = 0 AND article_id = ?";
		Map artMap = cs.itemMapRead(DBManager.getConnection(), sql, new Object[]{ articleId });
		this.setAttribute("artMap", artMap);
		this.requestForward("/client/zz_content.jsp");
	}
	
	/**
	 * 文章评论信息
	 * 
	 * @author xc 新增日期：2014-2-14上午11:29:28
	 * @version 1.0
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void cLeave() throws Exception {
		String articleId = this.getParameter("articleId");
		Integer pageIndex = this.getIntegerParameter("pageIndex");
		Integer pageSize = this.getIntegerParameter("pageSize");
		// 获取文章评论总数
		String rowCountSql = "SELECT article_comment_id FROM article_comment WHERE is_delete = 0 AND article_id = ?";
		Long rowCount = cs.getRowNum(DBManager.getConnection(), rowCountSql, new Object[]{ articleId });
		// 获取文章评论信息
		String sql = "SELECT article_comment_id, content, DATE_FORMAT(create_time,'%m-%d  %H:%i') AS create_time, temn_name, temn_city, comments_name, article_comment.`position` FROM article_comment WHERE is_delete = 0 AND article_id = ? ORDER BY create_time DESC,article_comment_id DESC";
		List artComList = PageUtils.getItemMapList(cs, sql, new Object[]{ articleId }, pageIndex, pageSize);
		Page page = new Page(pageIndex, pageSize, rowCount.intValue(), artComList);
		ResponseUtil.sendJsonObj(response, page);
	}
}
