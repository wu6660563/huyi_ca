<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/global.jsp"%>
		<title>促销广告详情</title>
		<meta charset=utf-8>
		<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/> 
		<link rel="stylesheet" href="${ctx}/businessclient/style/sw.css" />
		<link rel="stylesheet" href="${ctx}/businessclient/style/normalize.css" />
	</head>
	<body>
		<header id="header">
			<div class="header-m">
				<a href="javascript:history.back(-1);" class="back-btn">返回</a>
				<h1>促销广告详情</h1>
			</div>
		</header>
		<div id="main"><!--main-->
				<p>${advMap.context}</p>
				<p><img src="${advMap.uri}" /></p>
		</div>
	</body>
</html>
