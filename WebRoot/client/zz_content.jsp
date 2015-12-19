<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/cglobal.jsp"%>
		<title>会员店期刊</title>
		<link rel="stylesheet" href="${ctx}/client/style/normalize.css" />
		<link rel="stylesheet" href="${ctx}/client/style/zz.css" />
		<script type="text/javascript" src="${ctx}/client/js/jquery-1.11.0.min.js"></script>
		<script type="text/javascript">
			$(function(){
				// 统计阅读
				others('${artMap.article_id }',3);
				// 异步获取评论
				loadDatas(1);
			});
	
			function loadDatas(pageIndex){
				$.post("${ctx}/clients", {
					methType: "cl",
					articleId: "${artMap.article_id }",
					pageIndex: pageIndex,
					pageSize: pageSize }, function(page){
					if (page != null) {
						var list = page.result;
						var ulHtml = '';
						for ( var i = 0; i < list.length; i++) {
							var map = list[i];
							ulHtml+= 
								'<li>'+
									'<div class="comm-li">'+
										'<div class="comm-txt">'+
											'<h3>'+
												'<span class="comm-time">'+ map.create_time +'</span>'+
												'<span class="rember-name">'+ map.comments_name +'</span>'+
												'<span class="rember-zw">'+ map.position +'</span>'+
											'</h3>'+
											'<p class="comm-dre">'+
												'<span>'+ map.temn_city +'</span>'+
												'<span>'+ map.temn_name +'</span>'+
											'</p>'+
											'<div class="comm-desc">'+ map.content +'</div>'+
										'</div>'+
									'</div>'+
								'</li>';
						}
						$(".comment-list").append(ulHtml);
						$(".comm-nums").html(page.rowCount);
						if(page.currentPage != page.endIndex){
							$(".load-more").attr("href","javascript:loadDatas("+ (page.nextPage == null ? page.endIndex : page.nextPage) +");");
						}else{
							$(".load-more").hide();
						}
					}
				});
			}

			// 阅读统计oper=3 赞oper=2
			function others(id,oper){
				$.post("${ctx}/ctwo", { methType: "other", articleId: id, operType: oper }, function(data){
					if (data.ok == true && oper == 2) {
						$(".art_op_ico").removeAttr("onclick");
						$(".art_op_ico").addClass("on");
						$(".art_op_ico").html(parseInt($(".art_op_ico").html())+1);
					}
				});
			}
		</script>
	</head>
	<body>
<%--		<header id="header">--%>
<%--			<a href="window.history.back()" class="btn-back"></a>--%>
<%--			<a href="${ctx }/clients?methType=info&magazineId=${magazineId }" class="btn-back"></a>--%>
<%--			<h1 class="logo-title">文章详情</h1>	--%>
<%--		</header>--%>
		
		<section id="main">
			<div class="aritle-head">
				<h1 class="title">${artMap.title }</h1>
			</div>
			<div class="aritle-head" style="border-left: none; margin: 0px;">
				<p class="aritle-time clearfix">
					<time>作者：${artMap.author }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${artMap.edit_time }</time>
				</p>
			</div>
			<div class="aritle-content">
				<c:if test="${artMap.article_pic != null && artMap.article_pic != '' }">
					<div class="aritle-img"><img src="/mzimg/${artMap.article_pic }" /></div>
				</c:if>
				<div id="artContent">${artMap.content }</div>
				<script type="text/javascript">
					$("#artContent img").parent().each(function(){
						$(this).replaceWith('<div style="text-align: center;">'+ $(this).html() +'</div>');
					});
					$("#artContent img").attr("style","");
				</script>
				<div class="zan">
					<span class="art_op_ico ons" onclick="javascript:others(${artMap.article_id },2);" >${artMap.article_total }</span>
				</div>
			</div>
			<div class="comment">
				<div class="comment-head clearfix">
					<span class="comment-title">阅读评论</span>
				</div>
				<div class="com-c">
					<ul class="comment-list">
					</ul>
					<a class="load-more" href="javascript:loadDatas(1);">查看更多评论</a>
				</div>
			</div>
			<div class="comm-text">
				<a href="${ctx }/client/leave_comment.jsp?articleId=${artMap.article_id }" class="comm-a">我来说两句</a>
				<span class="comm-nums">0</span>
			</div>
		</section><!--main-->
	</body>
</html>