<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/global.jsp"%>
		<title>杂志列表</title>
		<link rel="stylesheet" href="${ctx}/style/index.css" />
		<script type="text/javascript" src="${ctx}/script/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/script/jquery-jtemplates.js"></script>
		<script type="text/javascript">
			$(function() {
				searchData(1);
			});
			// 评论列表
			function searchData(indexPage) {
				var params = {};
				params.operType = 'commentDatas';
				params.articleId = '${artMap.article_id }';
				params.rowCount = '${rowCount }';
				params.pageIndex = indexPage;
				
				$.getJSON("${ctx}/article.action", params, function(result) {
					$("#dataDiv").setTemplateElement("dataTemplate");
					$("#dataDiv").processTemplate(result);
				});
			}

			function delComment(id){
				if(confirm("您确定要删除吗？")){
					var params = {};
					params.operType = 'delComment';
					params.id = id;
					params.articleId = '${artMap.article_id }';
					$.post("${ctx}/article.action", params, function(data){
						if (data.ok == true) {
							$("#"+id).html("已删除");
							$("#"+id).attr("class","del-com");
						}
					});
				}
			}
		</script>
	</head>
	<body>
		<%@ include file="./head.jsp"%>
		<div class="position w">
			<a href="${ctx}/home.action">杂志列表</a> >><a href="${ctx}/magazine.action?operType=get&mId=${channelMap.magazine_id}">杂志编辑</a> >>
			<a href="${ctx}/article.action?operType=list&channelId=${artMap.channel_id}">文章列表</a> >><strong class="cw">管理文章评论</strong>
		</div>
		<div class="admin-content w">
			<div class="adminArea">
				<div class="head-tit clearfix">		
					<a class="right-btn back-btn" href="javascript:window.history.back();">返回</a>
					<span class="t-title">【${artMap.title }】<em class="pin-num">评论：<i>${rowCount }</i></em><em class="pin-num">阅读：<i>${artMap.article_read }</i></em></span>
				</div>
				<div class="pinList" id="dataDiv"></div>
			</div>
		</div>
<!------------------ 模板开始 --------------------------->
<textarea style="display: none;" id="dataTemplate">
<!-- 
<ul>
{#foreach $T.result as rs}
	<li>
		<h3>
			<span class="comm-time">{$T.rs.create_time}</span>
			<span class="rember-name">{$T.rs.comments_name}</span>
			<span class="rember-zw">{$T.rs.position}</span>
			<span class="comm-dre">{$T.rs.temn_city}</span>
			<span>{$T.rs.temn_name}</span>
		</h3>
		<div class="comm-desc">
			{$T.rs.content}
		</div>
		{#if $T.rs.is_delete == 0}
			<span id="{$T.rs.article_comment_id}" class="com-del" onclick="javascript:delComment({$T.rs.article_comment_id});"></span>
		{#else}
			<span class="del-com">已删除</span>
		{#/if}
	</li>
{#else}
	<li>
		<h4><span class="pin-time"></span><span class="pin-title">没有评论信息！</span></h4>
	</li>
{#/for}
<div class="pageList">
	<a href="javascript:searchData({$T.previousPage == 0 ? $T.startIndex : $T.previousPage})">上一页</a>
	{#for index = $T.startIndex to $T.endIndex}
		{#if $T.currentPage == $T.index}
			<a class="on" href="javascript:searchData({$T.index})">{$T.index}</a>
		{#else}
			<a href="javascript:searchData({$T.index})">{$T.index}</a>
		{#/if}
	{#/for}
	<span>{$T.currentPage}/共{$T.pageCount} 页</span>
	<a href="javascript:searchData({$T.nextPage == 0 ? $T.endIndex : $T.nextPage})">下一页</a>
</div>
</ul>
 -->
</textarea>
<!------------------ 模板结束 ---------------------->
	</body>
</html>
