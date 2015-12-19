<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML>
<html>
	<head>
	<%@ include file="../commons/global.jsp"%>
	<title>用户注册</title>
	<link rel="stylesheet" href="${ctx}/style/huyi.css" />
	<script src="${ctx}/script/zepto.min.js"></script>
	<script src="${ctx}/script/jquery.min.js"></script>
</head>
<script type="text/javascript"> 
		$(function(){
			var start = "${start}";
			var size = "${size}";
			var style = "step-bg step-b"+start;
			$("#startStyle").attr("class",style);
			for(var i=1; i<=parseInt(start); i++){
				$("#"+i).attr("class","current");
				if(start == parseInt(size)){
					$("#"+size).attr("class","current last-step");
				}else{
					$("#"+size).attr("class","last-step");
				}
			}
		});
	setInterval("showImg('.b-ad')",3000);
	function showImg(obj) {
		var _ul=$(obj).find('.ad-list');
		var _height=$(obj).height();
		_ul.animate({'margin-top':-_height},300,function(){
			$(this).css({'margin-top':0}).find('li:first').appendTo(_ul);
		})
	}
</script>
<body>
		<header id="header">
			<a href="javascript:history.back(-1);" class="backBtn"></a>
			<h2 class="title">用户注册</h2>
			<a class="phone" href="tel:123456789"></a>
		</header>
		<div id="main">
			<section class="step-wrap">
					<div class="step">
						<div class="step-list clearfix">
						<c:forEach items="${feedbackStart}" var="feedbackStart">
								<span id="${feedbackStart.status_num }" ><em>${feedbackStart.status_num }</em><i>${feedbackStart.status_name }</i></span>
						</c:forEach>
						<div id="startStyle" class="step-bg step-b${start}"></div>
							<!--开发注册   step-bg step-b4 这个是指进度到哪里,  current 比如step-b2 那是到了第二步了  <span>标签的current也得同时加上-->
						</div>
					</div>
			</section>
			<section class="stepBox">
				<c:forEach items="${feedBack}" var="feedBack">
					<ul class="processList">
					<li>
						<h3>${feedBack.service_pro_name }</h3>
						<p class="prc-time"><fmt:formatDate value='${feedBack.create_time }' pattern='yyyy-MM-dd hh:mm:ss' /></p>
						<div class="prc-desc">
							${feedBack.context }
						</div>
						<span class="pro-status">${feedBack.stautsName }</span>
					</li>
				</ul>
				</c:forEach>
				
			</section>
		</div>
		<footer id="footer">
			<div class="b-ad">
				<ul class="ad-list">
					<c:forEach items="${product}" var="product">
						<li><a href="${ctx}/application?method=getInfoDiss&info_id=${product.info_id}"><img src="${ctx}/fileupload/${product.path}" /></a></li>
					</c:forEach>
				</ul>
			</div>
		</footer>
	</div>
</body>
</html>
