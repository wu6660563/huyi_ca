<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/global.jsp"%>
		<title>编辑预览</title>
		<meta charset=utf-8>
		<link rel="stylesheet" href="${ctx}/style/index.css" />
		<script src="${ctx}/script/jquery.min.js"></script>
		<script src="${ctx}/script/autoresize.js"></script>
	</head>
	<script type="text/javascript">
	   
	     $(function(){
	     
	          $(".zzhome").click(function(){
	                 clear();
	                 if($(this).parents(".tb").attr("class")!="tb cur"){
	                    $(".zzhome").parents(".tb").attr("class","tb cur");
	                 }
	                $(".phone-c iframe").attr("src","${ctx}/clients?methType=info&magazineId=${magazineId}");
	          });
	          
	          $(".sort-wrap a[name='article']").each(function(){
	                 var obj=$(this);
	                    obj.click(function(){
	                     var artid=obj.attr("artid");
	                      clear();
	                     $(".phone-c iframe").attr("src","${ctx}/clients?methType=content&articleId="+artid);
	                     obj.attr("class","on");
	                     $(".zzhome").parents(".tb").attr("class","tb");
	                    });
	           
	          });
	         
	          $(".back-btn").click(function(){
	              window.location.href="${ctx}/magazine.action?operType=get&mId=${magazineId}";
	          });
	          $("a[name='b-save']").click(function(){
	                $.post("${ctx}/magazine.action", 
								{
									operType: "release",
									magazineState: 2,
									magazineId: ${magazineId}
								}, 
								function (data) {
								     if(data!=null && data>0){
								     
								       window.location.href="${ctx}/magazine.action?operType=get&mId=${magazineId}";
								     
								     }
				      });
	              
	          
	          });
	     
	     });
	
	    function clear(){
	     $(".sort-wrap a[class='on']").each(function(){
	                    var obj=$(this);
	                    obj.removeAttr("class");
	                });
	    
	    }
	</script>
	<body>
		<%@ include file="./head.jsp"%>
<div class="position w">
<span class="rights-btn">		<c:if test="${magazineMap.magazine_state!=2}">
	                               <a href="javascript:void(0);" name="b-save">发布杂志</a>
                                </c:if>
                                <a href="javascript:void(0);" class="back-btn">返回编辑</a></span>
    <a href="${ctx}/home.action">杂志列表</a> >><a href="${ctx}/magazine.action?operType=get&mId=${magazineId}">杂志编辑</a> >>  <strong class="cw">杂志预览</strong></div>

<div class="admin-content w">
	<div class="adminArea">
		<div class="tip">如果预览效果看不到想要的效果，请用火狐或谷歌或IE10+ 浏览器浏览</div>
		<div class="main-yl clearfix">
			<div class="main-left">
				<div class="p-title">
					<h2>手机前台预览效果</h2>
					<p>拖动手机内图片可上下移动手机内图片</p>
				</div>
				<div class="phone">					
					<div class="phone-c">
					<iframe src="${ctx}/clients?methType=info&magazineId=${magazineId}" frameBorder="0" width="100%" scrolling="no" height="100%"></iframe>
					  <div class="mask"></div>
					</div>
				</div>
			</div>
			<div class="main-right">
				<div class="p-title">
					<h3>您可以任意点击以下页面链接，快速查看您刚才编辑的内容在手机前台的效果：</h3>
				</div>
				<div class="r-content">
					<div class="tb cur"><a href="javascript:void(0);" class="zzhome">杂志介绍(目录)页</a></div>					
					<div class="sort-wrap">
						<div class="tb">文章列表 ( 共21篇 )</div>
						<c:forEach items="${channels}" var="channel">
						
						<h3 class="sort-head">栏目：${channel.title }</h3>
						<div class="listC">
						    <c:forEach items="${channel.articleList}" var="article">
						         <a href="javascript:void(0);" name="article" artid="${article.article_id}">${article.title}</a>
						    </c:forEach>
						</div>
						
						</c:forEach>
						
					</div>
				</div><!--r-content-->
				<div class="glay f14">
					如果对以上页面效果不满意，可以点击返回编辑的按钮；<br />
					如果对效果满意则点击【完成并发布】
				</div>
			</div>
		</div>

		<div class="textcenter f-btn">
		
		   
		
		</div>

	</div>
</div>
	</body>
</html>
