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
	</head>
	<body>
<%--		<header id="header">--%>
<%--			<a href="javascript:window.history.back();" class="btn-back"></a>--%>
<%--			<h1 class="logo-title">杂志目录</h1>--%>
<%--		</header>--%>
		
		<section id="main">
			<div id="slider" class="owl-theme">
				<div class="item">
					<a href="javascript:void(0);"><img src="/mzimg/${miMap.magazine_path }" /></a>
				</div>
			</div>
		
			<div class="newsList">
				<c:forEach var="ca" items="${caList }" >
				<dl>
					<dt class="n-head"><span class="mulu"></span><div class="n-title">${ca.title }</div></dt>
					<c:forEach var="art" items="${ca.artList }">
					<dd>
						<h3><a href="${ctx }/clients?methType=content&articleId=${art.article_id }&magazineId=${miMap.magazine_id }">${art.title }</a></h3>
						<a class="news-c news-p autoclear" href="${ctx }/clients?methType=content&articleId=${art.article_id }&magazineId=${miMap.magazine_id }">
							<div class="left">${art.content }</div>
							<script type="text/javascript">
								$(".left img").hide();
							</script>
							<c:if test="${art.article_pic != null && art.article_pic != '' }">
							<div class="newsImg"><img src="/mzimg/${art.article_pic }" /></div>
							</c:if>
						</a>
					</dd>
					</c:forEach>
				</dl>
				</c:forEach>
			</div>
		<section><!--main-->
	</body>
</html>