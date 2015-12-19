<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/global.jsp"%>
		<title>信息详情</title>
		<meta charset=utf-8>
		<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/> 
		<link rel="stylesheet" href="${ctx}/businessclient/style/sw.css" />
		<link rel="stylesheet" href="${ctx}/businessclient/style/normalize.css" />
	</head>
	<body>
		<header id="header">
			<div class="header-m">
				<a href="javascript:history.back(-1);" class="back-btn">返回</a>
				<h1>信息详情</h1>
			</div>
		</header>
			<div class="gd-c" style="display:block">
				<dl class="gd-list">
					<dd>
							<h2>标题:${infoMap.title}</h2>
							<time>修改时间:${infoMap.modify_time}</time>
							<p class="gd-desc">
								摘要:${infoMap.summary}
							</p>
							<p class="gd-desc">
								内容:${infoMap.context}
							</p>
							<time>创建时间:${infoMap.create_time}</time>
					</dd>
				</dl>
			</div>
	</body>
</html>
