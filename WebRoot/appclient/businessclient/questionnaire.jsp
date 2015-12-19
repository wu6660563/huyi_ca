<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/global.jsp"%>
		<title>我的问题单</title>
		<meta charset=utf-8>
		<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/> 
		<link rel="stylesheet" href="${ctx}/appclient/businessclient/style/sw.css" />
		<link rel="stylesheet" href="${ctx}/appclient/businessclient/style/normalize.css" />
		<script type="text/javascript" src="${ctx}/appclient/businessclient/js/jquery.js"></script>
		<script>
		$(document).ready(function(){		
			setInterval("showImg('.b-ad')",3000);			
		});

		function showImg(obj) {
				var _ul=$(obj).find('.ad-list');
				var _height=$(obj).height();
				_ul.animate({'margin-top':-_height},300,function(){
					$(this).css({'margin-top':0}).find('li:first').appendTo(_ul);
				})
			}
	</script>
	</head>
	<body>
		<header id="header">
		<div class="header-m">
			<a href="${ctx}/goback.do" class="back-btn">返回</a>
			<h1>我的问题单</h1>
		</div>
	</header>
	<div id="main">
		<dl class="gd-list wt-list">
			<c:forEach items="${questions}" var="questions">
				  <c:forEach items="${questions.questionList }" var="questionList">
				  	 <c:forEach items="${questions.customerInfoMap }" var="customerInfoMap">
						<dd>
							<h2>${questionList.question_type}<time class="right"><fmt:formatDate value='${questionList.modify_time}' pattern='yyyy-MM-dd' /></time></h2>
							<ul class="ms-list">
							<li>
								<span>问题描述：</span>
								<p>${questionList.context}</p>
							</li>
							<li ><span>合同号：</span><p>${questions.contract_no}</p></li>
							<li><span>弹单人：</span><p>${questionList.department}&nbsp;&nbsp;${questionList.single_person}</p></li>				
							<li><span>客户：</span><p>${customerInfoMap.customer_name}</p></li>
							<li><span>签单时间：</span><p><fmt:formatDate value='${questions.signing_time}' pattern='yyyy-MM-dd' /></p></li>	
						</dd>
					</c:forEach>
				 </c:forEach>
			</c:forEach>
		</dl>
	</div><!--main-->
	</body>
</html>
