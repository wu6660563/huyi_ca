/**
 * 
 */
package com.biostime.magazine.servlet;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang.StringUtils;

import com.baiytfp.commonlib.base.BaseServlet;
import com.baiytfp.commonlib.base.service.CommonService;
import com.baiytfp.commonlib.commons.Configuration;
import com.baiytfp.commonlib.util.DateUtils;
import com.baiytfp.commonlib.util.FileUtils;
import com.baiytfp.commonlib.util.ResponseUtil;
import com.biostime.magazine.common.DBManager;
import com.biostime.magazine.common.Page;
import com.biostime.magazine.common.PageUtils;

/**
 * 文章
 *	
 * @author cjl 新增日期：2014-3-7下午03:55:17
 * @version 1.0
 */
public class ArticleServlet extends BaseServlet {

	@Override
	public void doHandle() {
		String operType = getParameter("operType");

		try {
			if (operType.equals("index")) { // 访问首页
				index();
			} else if (operType.equals("list")) { // 文章数据
				list();
			} else if (operType.equals("get")) { // 文章数据
				get();
			} else if (operType.equals("editArticle")) { // 保存文章数据
				editArticle();
			} else if (operType.equals("sort")) { // 文章排序
				sort();
			} else if (operType.equals("del")) { // 删除文章
				del();
			} else if (operType.equals("comments")) { // 管理文章评论
				comments();
			} else if (operType.equals("commentDatas")) { // 文章评论数据获取
				commentDatas();
			} else if (operType.equals("delComment")) { // 删除文章评论
				delComment();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * (此处写功能描述，换行用<b/>，换段用<p/>）
	 * 
	 * @author cjl 新增日期：2014-3-7下午03:58:37
	 * @version 1.0
	 * @throws Exception 
	 */
	private void del() throws Exception {
		// TODO Auto-generated method stub
		
		  CommonService cs = new CommonService();
		  String createTime= DateUtils.getString(new Date(), DateUtils.yyyy_MM_dd_HH_mm_ss);
		  String articleId= getParameter("articleId");
		  Map articleMap= new HashMap();
		  articleMap.put("modify_time", createTime);
		  articleMap.put("is_delete", 1);
		  long len = cs.itemMapEdit(DBManager.getConnection(), "article", " WHERE article_id = ? AND is_delete = 0 ", articleId.toString(), articleMap);
          PrintWriter out =getResponse().getWriter();
          out.print(len);
          out.flush();
          out.close();
		    
		  
	}

	/**
	 * (此处写功能描述，换行用<b/>，换段用<p/>）
	 * 
	 * @author cjl 新增日期：2014-3-7下午03:58:35
	 * @version 1.0
	 * @throws Exception 
	 */
	private void sort() throws Exception {
		// TODO Auto-generated method stub
		     long len=0;
		     CommonService cs = new CommonService();
		     String sortTime= DateUtils.getString(new Date(), DateUtils.yyyy_MM_dd_HH_mm_ss);
		     String articleId=getParameter("articleId");
		     Map articleMap= new HashMap();
		     articleMap.put("sort_time", sortTime);
		     len=cs.itemMapEdit(DBManager.getConnection(), "article", " WHERE article_id = ? AND is_delete = 0 ", articleId, articleMap);
	          PrintWriter out =getResponse().getWriter();
	          out.print(len);
	          out.flush();
	          out.close();
		  
		
	}

	/**
	 * (文章编辑）
	 * 
	 * @author cjl 新增日期：2014-3-7下午03:58:00
	 * @version 1.0
	 * @throws Exception 
	 */
	private void editArticle() throws Exception {
		// TODO Auto-generated method stub
  	      String mesg="";
  	      CommonService cs = new CommonService();
		  String title = getParameter("title");
		  String author = getParameter("author");
		  String editTime= getParameter("editTime");
		  String articlePic= getParameter("articlePic");
		  String keyWords= getParameter("keyWords");
		  String content = getParameter("content");
		  content=replaceString(content);
		  String articleId= getParameter("articleId");
		  String channelId= getParameter("channelId");
		  String createTime= DateUtils.getString(new Date(), DateUtils.yyyy_MM_dd_HH_mm_ss);
		    if(editTime==null || "".equalsIgnoreCase(editTime)){
		    	
		    	editTime= DateUtils.getString(new Date(), DateUtils.yyyy_MM_dd_HH_mm_ss);
		    	
		    }
		  long len=0;
		    Map articleMap= new HashMap();
		        articleMap.put("title", title);
		        articleMap.put("author", author);  
		        articleMap.put("modify_time", createTime);
		        articleMap.put("edit_time", editTime);
		        articleMap.put("content", content);
		        articleMap.put("key_words", keyWords);
			    if (StringUtils.isNotBlank(articlePic)) {
	    			String srcPath = Configuration.read("fileupload.directory") + articlePic;
	    			String descPath = Configuration.read("file.formal.directory") + articlePic;
	    			File descDir = new File(descPath.substring(0, descPath.lastIndexOf("/")));
	    			if (!descDir.exists()) {
	    				descDir.mkdirs();
	    			}
	    			
	    			FileUtils.copyFile(srcPath, descPath);
	    		    articleMap.put("article_pic", articlePic);
	    		}
		        if( articleId!=null && !"".equalsIgnoreCase(articleId)){
		        	
		  		  len = cs.itemMapEdit(DBManager.getConnection(), "article", " WHERE article_id = ? AND is_delete = 0 ", articleId.toString(), articleMap);
	    		  if(len>0){
	    			  mesg="修改成功";
	    		  }
		        	
		        }else{
		        	  articleMap.put("is_delete", 0);
		        	  articleMap.put("channel_id", channelId);
		        	  articleMap.put("sort_time", createTime);
		        	  articleMap.put("create_time", createTime);
		        	  len=cs.itemMapAdd(DBManager.getConnection(), "article", articleMap);
		 	          if(len>0){
		     			  mesg="新增成功";
		     		  }
		        }
		          PrintWriter out =getResponse().getWriter();
		          out.print(mesg);
		          out.flush();
		          out.close();
			  
		    
		    
		
		
		
		
	}

	/**
	 * (获取文章数据，进入到编辑页面）
	 * 
	 * @author cjl 新增日期：2014-3-7下午03:57:58
	 * @version 1.0
	 * @throws Exception 
	 */
	private void get() throws Exception {
		// TODO Auto-generated method stub
		
        CommonService cs = new CommonService();
		String articleId =getParameter("articleId");
		String channelId =getParameter("channelId");
		String magazineId =getParameter("magazineId");
		String souType =getParameter("souType");
		String vesting="";
		long commentNum =0;  
		  Map articleMap = new HashMap();
		  
		    String commentSql="select * from article_comment where article_id=? ";
		    if( articleId!=null){
		    	commentNum= cs.getRowNum(DBManager.getConnection(), commentSql, new Object[]{articleId});
		    	articleMap = cs.itemMapRead(DBManager.getConnection(), " SELECT * FROM article WHERE article_id=? ", new Object[]{articleId});
		    }
		        if(channelId!=null){
		        	articleMap.put("channel_id",channelId);	 
		        }
		          Map channeMap= cs.itemMapRead(DBManager.getConnection(), " SELECT * FROM channel_info WHERE channel_id=? ", new Object[]{articleMap.get("channel_id")});
		          if(magazineId==null || "".equalsIgnoreCase(magazineId)){
		        	  magazineId = channeMap.get("magazine_id").toString();
		          }
		          
		          Map magazineMap= cs.itemMapRead(DBManager.getConnection(), " SELECT * FROM magazine_info WHERE magazine_id=? ", new Object[]{channeMap.get("magazine_id")});
		            
		          
		          vesting="第"+magazineMap.get("magazine_phase")+"期"+channeMap.get("title");
		         setAttribute("articleMap", articleMap);
		         setAttribute("vesting", vesting);
		         setAttribute("commentNum",commentNum);
		         setAttribute("magazineId",magazineId);
		         setAttribute("souType", souType);
		         requestForward("/page/article_edit.jsp");
		        
	}

	/**
	 * (文章列表）
	 * 
	 * @author cjl 新增日期：2014-3-7下午03:57:56
	 * @version 1.0
	 * @throws Exception 
	 */
	private void list() throws Exception {
		// TODO Auto-generated method stub
		Integer pageSize=Integer.parseInt(Configuration.read("pagesize"));
		String channelId= getParameter("channelId");
		 CommonService cs = new CommonService();
		String curPage=getParameter("curPage");
		Integer pageNum;
		int count=0;
		Integer pageCount=0;
		   if(curPage==null){
			   pageNum=1;
		   }else{
			   pageNum=Integer.parseInt(curPage);
			   
		   }
		   
		  String countSql="select * from article where channel_id=? and is_delete=0";
		  String articleSql="select mm.*,count(comment.article_id) as comcount from (select * from article where channel_id=? and is_delete=0 order by sort_time desc limit "+(pageNum-1)+","+pageSize+") as mm left join article_comment as comment on mm.article_id=comment.article_id  group by comment.article_id";
		  List articleList= cs.getItemMapList(DBManager.getConnection(),articleSql, new Object[]{channelId});
		  count=cs.getItemMapList(DBManager.getConnection(),countSql, new Object[]{channelId}).size();
		  
		  Map channeMap= cs.itemMapRead(DBManager.getConnection(), " SELECT * FROM channel_info WHERE channel_id=? ", new Object[]{channelId});
	        
		  Map magazineMap= cs.itemMapRead(DBManager.getConnection(), " SELECT * FROM magazine_info WHERE magazine_id=? ", new Object[]{channeMap.get("magazine_id")});
	         
		  pageCount=count/pageSize;
    	  if(count%pageSize>0){
    		  pageCount++;
    	  }
    	  setAttribute("curPage",pageNum);
    	  setAttribute("pageCount",pageCount);
		  setAttribute("articleList",articleList);
		  setAttribute("magazineMap", magazineMap);
		  setAttribute("channeMap", channeMap);
		  requestForward("/page/article_list.jsp");
		  
		  
		
	}

	/**
	 * (此处写功能描述，换行用<b/>，换段用<p/>）
	 * 
	 * @author cjl 新增日期：2014-3-7下午03:57:54
	 * @version 1.0
	 */
	private void index() {
		// TODO Auto-generated method stub
		
	}
	 /**
	  * 
	  * 
	  * 
	  * (图文详情处理）
	  * 
	  * @param ptv
	  * @return
	  * @author bxz 新增日期：2014-3-17下午09:01:08
	  * @version 1.0
	  */
	 public static String replaceString(String ptv) {
		   
		   while( ptv.indexOf("src=\"/mzimg/tempdir/") > 0 ){
				int stateIndexStart = ptv.indexOf("src=\"/mzimg/tempdir/")+
				  "src=\"/mzimg/tempdir/".length();
		        int stateIndexEnd = ptv.indexOf("\"", stateIndexStart);	
		        
		        String state = ptv.substring(stateIndexStart, stateIndexEnd);
		        ptv = ptv.replaceFirst("/mzimg/tempdir/","/mzimg/");
			      
		        String srcPath = Configuration.read("fileupload.directory") + state;
				String descPath = Configuration.read("file.formal.directory") + state;
				FileUtils.copyFile(srcPath, descPath);
		    }
		        return ptv;
	   }
	 
	/**
	 * 文章评论管理
	 * 
	 * @throws Exception
	 * @author xc 新增日期：2014-3-18下午03:09:46
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	private void comments() throws Exception {
		String articleId = this.getParameter("articleId");
		CommonService cs = new CommonService();
		
		// 获取文章评论总数
		String rowCountSql = "SELECT article_comment_id FROM article_comment WHERE article_id = ?";
		Long rowCount = cs.getRowNum(DBManager.getConnection(), rowCountSql, new Object[]{ articleId });
		this.setAttribute("rowCount", rowCount);
		
		// 获取文章信息
		String sql = "SELECT article_id, title, article_read,channel_id FROM article WHERE is_delete = 0 AND article_id = ?";
		Map artMap = cs.itemMapRead(DBManager.getConnection(), sql, new Object[]{ articleId });
		this.setAttribute("artMap", artMap);
		
		// 根据文章栏目ID，获取栏目所在杂志
		String channelSql = "SELECT magazine_id FROM channel_info WHERE channel_id = ?";
		Map channelMap = cs.itemMapRead(DBManager.getConnection(), channelSql, new Object[]{ artMap.get("channel_id") });
		this.setAttribute("channelMap", channelMap);
		
		requestForward("/page/comments.jsp");
	}
	
	/**
	 * 文章评论管理数据
	 * 
	 * @throws Exception
	 * @author xc 新增日期：2014-3-18下午03:09:46
	 * @version 1.0
	 */
	@SuppressWarnings({"unchecked" })
	public void commentDatas() throws Exception {
		String articleId = this.getParameter("articleId");
		String pageIndex = this.getParameter("pageIndex");
		Integer pageSize=Integer.parseInt(Configuration.read("pagesize"));
		String rowCount = this.getParameter("rowCount");
		CommonService cs = new CommonService();
		// 获取文章评论信息
		String sql = "SELECT article_comment_id, content, DATE_FORMAT(create_time,'%Y-%m-%d  %H:%i') AS create_time, temn_name, temn_city, comments_name, article_comment.`position`, is_delete FROM article_comment WHERE article_id = ? ORDER BY create_time DESC ";
		List artComList = PageUtils.getItemMapList(cs, sql, new Object[]{ articleId }, Integer.valueOf(pageIndex), pageSize);
		Page page = new Page(Integer.valueOf(pageIndex), Integer.valueOf(pageSize), Integer.valueOf(rowCount), artComList);
		ResponseUtil.sendJsonObj(response, page);
	}
	
	/**
	 * 删除评论信息
	 * 
	 * @throws Exception
	 * @author xc 新增日期：2014-3-18下午03:09:46
	 * @version 1.0
	 */
	@SuppressWarnings({"unchecked" })
	public void delComment() throws Exception {
		String id = this.getParameter("id");
		QueryRunner qr = new QueryRunner();
		Connection connection = DBManager.getConnection();
		qr.update(connection, "UPDATE article_comment SET is_delete = 1 WHERE article_comment_id = ?", new Object[] { id });
		DbUtils.closeQuietly(connection);
		ResponseUtil.sendJsonOK(this.getResponse());
	}
}
