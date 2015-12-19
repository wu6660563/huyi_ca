/**
 * 
 */
package com.biostime.magazine.servlet;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.baiytfp.commonlib.base.BaseServlet;
import com.baiytfp.commonlib.base.service.CommonService;
import com.baiytfp.commonlib.commons.Configuration;
import com.baiytfp.commonlib.util.DateUtils;
import com.baiytfp.commonlib.util.FileUtils;
import com.baiytfp.commonlib.util.StringUtil;
import com.biostime.magazine.common.DBManager;

/**
 * 杂志编辑
 *	
 * @author cjl 新增日期：2014-3-7下午03:49:41
 * @version 1.0
 */
public class MagazineServlet extends BaseServlet {

	@Override
	public void doHandle() {
		String operType = getParameter("operType");

		try {
			if (operType.equals("index")) { // 访问首页
				index();
			} else if (operType.equals("list")) { // 所有杂志数据
				list();
			} else if (operType.equals("get")) { // 杂志数据
				get();
			} else if (operType.equals("edit")) { // 保存杂志数据
				editMagazine();
			} else if (operType.equals("del")) { // 删除杂志数据
				del();
			} else if (operType.equals("saveChannel")) { // 保存栏目数据
				saveChannel();
			} else if (operType.equals("sortChannel")) { // 栏目排序
				sortChannel();
			} else if (operType.equals("delChannel")) { // 删除栏目
				delChannel();
			}else if(operType.equals("initMagazine")){
				initMagazine();
			} else if(operType.equals("preview")){//杂志预览
				preview();
			} else if(operType.equals("release")){//发布杂志
				release();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除杂志数据
	 * 
	 * @author cjl 新增日期：2014-3-7下午03:59:54
	 * @version 1.0
	 * @throws Exception 
	 */
	private void del() throws Exception {
		String mId = getParameter("mId");
		long len = 0;
		if (StringUtils.isNotBlank(mId)) {
			CommonService cs = new CommonService();
			Map map = new HashMap();
			map.put("magazine_state", 1);
			len = cs.itemMapEdit(DBManager.getConnection(), "magazine_info", " WHERE magazine_id=? AND magazine_state!=1 ", mId, map);
		}
		renderText(len+"");
	}

	/**
	 * (此处写功能描述，换行用<b/>，换段用<p/>）
	 * 
	 * @author cjl 新增日期：2014-3-7下午03:59:36
	 * @version 1.0
	 */
	private void list() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * (此处写功能描述，换行用<b/>，换段用<p/>）
	 * 
	 * @author cjl 新增日期：2014-3-7下午03:58:59
	 * @version 1.0
	 * @throws Exception 
	 */
	private void delChannel() throws Exception {
		// TODO Auto-generated method stub
		CommonService cs = new CommonService();
		String channelId=getParameter("channelId");
		String modif_time =DateUtils.getString(new Date(), DateUtils.yyyy_MM_dd_HH_mm_ss);
		String mesg="";
		Map channelMap = new HashMap();
		channelMap.put("modif_time", modif_time);
		channelMap.put("is_delete",1);
		
	     long res =cs.itemMapEdit(DBManager.getConnection(), "channel_info", " WHERE channel_id = ?", channelId, channelMap );
		 PrintWriter out =getResponse().getWriter();
         out.print(res);
         out.flush();
         out.close();
	}

	/**
	 * (栏目置顶排序）
	 * 
	 * @author cjl 新增日期：2014-3-7下午03:58:57
	 * @version 1.0
	 * @throws Exception 
	 */
	private void sortChannel() throws Exception {
		// TODO Auto-generated method stub
		CommonService cs = new CommonService();
		String channelId= getParameter("channelId"); 
		String sortTime = DateUtils.getString(new Date(), DateUtils.yyyy_MM_dd_HH_mm_ss);
		  Map channelMap=new HashMap();
		  long len=0; 
		 channelMap.put("sort_time", sortTime);
		 
		 channelMap.put("modif_time", sortTime);
	     len= cs.itemMapEdit(DBManager.getConnection(), "channel_info", " WHERE channel_id = ?", channelId, channelMap );
	     PrintWriter out =getResponse().getWriter();
         out.print(len);
         out.flush();
         out.close();
		  
		  
		
	}

	/**
	 * (此处写功能描述，换行用<b/>，换段用<p/>）
	 * 
	 * @author cjl 新增日期：2014-3-7下午03:58:52
	 * @version 1.0
	 * @throws Exception 
	 */
	private void saveChannel() throws Exception {
		// TODO Auto-generated method stub
		CommonService cs = new CommonService();
		String magazineId=getParameter("magazineId");
		String channelId=getParameter("channelId");
		String title=getParameter("title");
		String createTime =DateUtils.getString(new Date(), DateUtils.yyyy_MM_dd_HH_mm_ss);
		String mesg="";
		Map channelMap = new HashMap();
		  channelMap.put("title", title);
		  if( channelId!=null && !"".equalsIgnoreCase(channelId) ){
			  
		      channelMap.put("modif_time", createTime);
		      cs.itemMapEdit(DBManager.getConnection(), "channel_info", " WHERE channel_id = ?", channelId, channelMap );
		  }else{
			  channelMap.put("create_time", createTime);
			  channelMap.put("sort_time", createTime);
			  channelMap.put("is_delete", 0);
			  channelMap.put("magazine_id",magazineId);
			  cs.itemMapAdd(DBManager.getConnection(), "channel_info", channelMap);
		      channelMap= cs.itemMapRead(DBManager.getConnection(), " SELECT * FROM channel_info WHERE create_time = ? and magazine_id=? ", new Object[]{createTime,magazineId});
		      channelId = channelMap.get("channel_id").toString();
		  }
		    
		     
	     PrintWriter out =getResponse().getWriter();
         out.print(channelId);
         out.flush();
         out.close();
	}

	/**
	 * (杂志编辑）
	 * 
	 * @author cjl 新增日期：2014-3-7下午03:58:51
	 * @version 1.0
	 * @throws Exception 
	 */
	private void editMagazine() throws Exception {
		// TODO Auto-generated method stub
		CommonService cs = new CommonService();
		String modifTime =DateUtils.getString(new Date(), DateUtils.yyyy_MM_dd_HH_mm_ss);
		String magazineId= getParameter("magazineId");
		String magazinePath= getParameter("magazinePath");
        String magazinePhase= getParameter("magazinePhase");
        String publishTime= getParameter("publishTime");
        String byType = getParameter("byType");
        long len=0; 
        Map magazineMap = new HashMap(); 
        if (StringUtils.isNotBlank(magazinePath)) {
			String srcPath = Configuration.read("fileupload.directory") + magazinePath;
			String descPath = Configuration.read("file.formal.directory") + magazinePath;
			File descDir = new File(descPath.substring(0, descPath.lastIndexOf("/")));
			if (!descDir.exists()) {
				descDir.mkdirs();
			}
			FileUtils.copyFile(srcPath, descPath);
			magazineMap.put("magazine_path", magazinePath);
		}
        magazineMap.put("magazine_phase", magazinePhase);
        magazineMap.put("publish_time", publishTime);
        magazineMap.put("modif_time", modifTime);
        len= cs.itemMapEdit(DBManager.getConnection(), "magazine_info", " WHERE magazine_id = ?", magazineId, magazineMap );
	     PrintWriter out =getResponse().getWriter();
         out.print(len);
         out.flush();
         out.close();
          
        
        
        
        
        
		
	}
	
	
	/**
	 * 
	 * 
	 * (初始化一条杂志数据）
	 * 
	 * @author bxz 新增日期：2014-3-17上午10:55:30
	 * @version 1.0
	 * @throws Exception 
	 */
	private void initMagazine() throws Exception{
		CommonService cs = new CommonService();
		long len =0;
		String createTime = DateUtils.getString(new Date(), DateUtils.yyyy_MM_dd_HH_mm_ss);
		  //杂志期数sql
		String phaseSql = "select magazine_phase from magazine_info where magazine_state=2 order by magazine_phase desc LIMIT 1";
		Map phaseMap = cs.itemMapRead( DBManager.getConnection(), phaseSql , new Object[]{} );
		int phase= new Integer(StringUtil.getMapString(phaseMap, "magazine_phase", "0")).intValue()+1;
		Map magazineMap = new HashMap();
		magazineMap.put("is_free",1);
		magazineMap.put("magazine_phase",phase);
		magazineMap.put("create_time", createTime);
		magazineMap.put("modif_time", createTime);
		magazineMap.put("magazine_state", 0);
	   
		cs.itemMapAdd(DBManager.getConnection(), "magazine_info", magazineMap);
	    
		response.sendRedirect("magazine.action?operType=get&phase="+phase+"&createTime="+createTime);  
		
	}
	

	/**
	 * (此处写功能描述，换行用<b/>，换段用<p/>）
	 * 
	 * @author cjl 新增日期：2014-3-7下午03:58:49
	 * @version 1.0
	 * @throws Exception 
	 */
	private void get() throws Exception {
		// TODO Auto-generated method stub
		CommonService cs = new CommonService();
		String createTime = getParameter("createTime");
		
		 String phase = getParameter("phase");
		 
		 String magazineId=getParameter("mId");
		 
	     Map magazineMap = new HashMap();
	        
	    	 if(magazineId!=null){
	    		 
	    		 magazineMap = cs.itemMapRead(DBManager.getConnection(), " SELECT * FROM magazine_info WHERE magazine_id = ? ", new Object[]{magazineId});
	    		 
	    	 } else {
	    		 
	    		 magazineMap = cs.itemMapRead(DBManager.getConnection(), " SELECT * FROM magazine_info WHERE create_time = ? and magazine_phase=? ", new Object[]{createTime,phase});
	    		 magazineId= magazineMap.get("magazine_id").toString();
	    		 
	    	 } 
	    	 
	     String channelSQL="select count(art.channel_id) as artcount,channel.title,channel.channel_id  from channel_info as channel left join (select * from  article where is_delete=0) as art on channel.channel_id=art.channel_id  where channel.is_delete=0 and channel.magazine_id=? GROUP BY channel.channel_id order by channel.sort_time desc";
	    	 
	     List channelList= cs.getItemMapList(DBManager.getConnection(),channelSQL, new Object[]{magazineId});
				
	     setAttribute("magazineMap",magazineMap);
	     setAttribute("channelList",channelList);
	     requestForward("/page/magazine_edit.jsp");
		  
	}
	/**
	 * 
	 * 
	 * (发布杂志）
	 * 
	 * @author bxz 新增日期：2014-3-18下午05:42:15
	 * @version 1.0
	 * @throws Exception 
	 */
	private void release() throws Exception{
		 
		CommonService cs = new CommonService();
		String modifTime =DateUtils.getString(new Date(), DateUtils.yyyy_MM_dd_HH_mm_ss);
		String magazineId= getParameter("magazineId");
		String magazineState= getParameter("magazineState");
		
		  Map magazineMap = new HashMap(); 
		  magazineMap.put("magazine_state", magazineState);
		  magazineMap.put("modif_time", modifTime);
		  
	       long len= cs.itemMapEdit(DBManager.getConnection(), "magazine_info", " WHERE magazine_id = ?", magazineId, magazineMap );
	       PrintWriter out =getResponse().getWriter();
	         out.print(len);
	         out.flush();
	         out.close();
		  
		
	}
	
	
    /**
     * 
     * 
     * 	
     * (杂志预览）
     * 
     * @author bxz 新增日期：2014-3-18下午02:51:40
     * @version 1.0
     * @throws Exception 
     */
	private void preview() throws Exception{
		 CommonService cs = new CommonService();
		 String magazineId =getParameter("magazineId");
		 setAttribute("magazineId", magazineId);
		 
		 Map magazineMap  = cs.itemMapRead(DBManager.getConnection(), " SELECT * FROM magazine_info WHERE magazine_id = ? ", new Object[]{magazineId});
 	     
		  setAttribute("magazineMap", magazineMap);
		   
		  String channelSql="select * from channel_info where magazine_id=? and is_delete=0 order by sort_time desc";
		  List channelList= cs.getItemMapList(DBManager.getConnection(),channelSql, new Object[]{magazineId});
		  List  channels=new ArrayList();
		        for (int i = 0; i < channelList.size(); i++) {
		        	
		        	    Map channelMap=(Map) channelList.get(i);
		        	      
		        	    String articleSql="select * from article where channel_id=? and is_delete=0 order by sort_time desc";
		        	   
		        	    List articleList= cs.getItemMapList(DBManager.getConnection(),articleSql, new Object[]{channelMap.get("channel_id")});
		        			 
		        	    channelMap.put("articleList", articleList);
		        	    
		        	    channels.add(channelMap);
		        	    
		        	
					
				}
		        
		  setAttribute("channels", channels);		
		  requestForward("/page/yulang.jsp");
		
	}
	
	

	/**
	 * (此处写功能描述，换行用<b/>，换段用<p/>）
	 * 
	 * @author cjl 新增日期：2014-3-7下午03:58:48
	 * @version 1.0
	 */
	private void index() {
		// TODO Auto-generated method stub
		
	}

}
