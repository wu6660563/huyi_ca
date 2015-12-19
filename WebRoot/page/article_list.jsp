<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/global.jsp"%>

		<title>编辑杂志</title>
		<meta charset=utf-8>
		<link rel="stylesheet" href="${ctx}/style/index.css" />
		<script src="${ctx}/script/jquery.min.js"></script>
		<script src="${ctx}/script/autoresize.js"></script>
	
	</head>
	<script type="text/javascript">

	    $(function(){
	        $("img[data-autoresize]").one("load",function(){$(this).autoresize({forceRun:true})});
	        //删除栏目里的文章
	           $(".custom-table tr").each(function(){
	               var param=$(this);
	               var articleId=param.attr("articleId");
	               param.find(".del-btn").click(function(){
		               $.post("${ctx}/article.action", 
						   {
								operType: "del",
								articleId: articleId
						  }, 
						  function (data) {
							 if(data>0){
							   window.location.href="${ctx}/article.action?operType=list&channelId=${channeMap.channel_id}";
							 }
				       });
	               });
	                param.find(".rank-btn").click(function(){
	                
	                 $.post("${ctx}/article.action", 
						   {
								operType: "sort",
								articleId: articleId
						  }, 
						  function (data) {
							 if(data>0){
							   window.location.href="${ctx}/article.action?operType=list&channelId=${channeMap.channel_id}";
							 }
				       });
	                  
	                
	                });
	           
	         });
	        
	            $(".back-btn").click(function(){
	              window.location.href="${ctx}/magazine.action?operType=get&mId=${magazineMap.magazine_id}";
	            
	            });
	        
	        
	        
			   
	    
	    });
	    
	    
	    function init(){
	        
	    
	    }
	  
	
	
	</script>

	<body>
		<%@ include file="./head.jsp"%>
		
<div class="position w"><a href="${ctx}/home.action">杂志列表</a> >><a href="${ctx}/magazine.action?operType=get&mId=${magazineMap.magazine_id}">杂志编辑</a> >>  <strong class="cw">文章列表</strong></div>

<div class="admin-content w">
	<div class="adminArea">
		
		<div class="head-tit clearfix">	
			<a class="right-btn back-btn" href="javascript:void(0);">返回</a>	
			<a class="right-btn" href="${ctx}/article.action?operType=get&channelId=${channeMap.channel_id}"> 添加文章</a>
			<span class="t-title">【栏目${channeMap.title }】的文章列表 <em class="text-red">(第${magazineMap.magazine_phase }期)</em></span>
		</div>
		<div class="listview1">
			<table width="100%" class="custom-table" >
			  <tr class="tr-title">
				<td><span>序号</span></td>
				<td width="250"><span>文章标题</span></td>
				<td><span>作者</span></td>
				<td><span>编辑时间</span></td>
				<td><span>阅读数</span></td>
				<td><span>评论数</span></td>
				<td width="200"><span>操作</span></td>
			  </tr>
			 
			   <c:forEach items="${articleList}" var="article" varStatus="index">
			   
				   <tr articleId="${article.article_id}">
					<td>${index.index+1 }</td>
					<td style="text-align:left">${article.title}</td>
					<td>${article.author}</td>
					<td><fmt:formatDate value="${article.edit_time}" type="date" pattern="yyyy-MM-dd"/></td>
					<td>${article.article_read}</td>
					<td>${article.comcount}</td>
					<td>
						<div class="editB">
							<span class="rank-btn"><img src="img/icon-rank.jpg"></span>
							<span class="icon-edit"><a href="${ctx}/article.action?operType=get&articleId=${article.article_id}"><img src="img/icon-edit.jpg"></a></span>
							<span class="icon-edit"><a href="${ctx}/article.action?operType=comments&articleId=${article.article_id}"><img src="img/icon-ping.jpg"></a></span>
							<span class="del-btn"><img src="img/icon-close.jpg"></span>
						</div>
					</td>
				  </tr>	
				  
			   </c:forEach>
			   
			  
			  
			  <tr>
				 <td colspan="7">
					<div class="pageList">
					   <c:choose>
						    <c:when test="${curPage>1}">
						      <a   href="${ctx}/article.action?operType=list&channelId=${channeMap.channel_id}&curPage=${pageNum-1}">上一页</a>
						    </c:when>
						    <c:otherwise>
						        <a class="on" href="javascript:void(0);">上一页</a>
						    </c:otherwise>
						</c:choose> 
						<c:forEach begin="1" end="${pageCount}" var="pageNum">
						        <a  <c:if test="${pageNum==curPage}">class="on" </c:if> href="${ctx}/article.action?operType=list&channelId=${channeMap.channel_id}&curPage=${pageNum}">${pageNum}</a>
						</c:forEach>
						<span>1/共 ${pageCount} 页</span>
						<c:choose>
						    <c:when test="${curPage<pageCount}">
						      <a href="${ctx}/article.action?operType=list&channelId=${channeMap.channel_id}&curPage=${pageNum+1}">下一页</a>
						    </c:when>
						    <c:otherwise>
						        <a href="javascript:void(0);" class="on">下一页</a>
						    </c:otherwise>
						</c:choose> 
					</div>
				 </td>
			 </tr>
			</table>
			
		</div>
	</div>
</div>

	</body>
</html>
