<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML>
<html>
	<head>
	<%@ include file="../commons/global.jsp"%>
	<title>服务进程</title>
	<link rel="stylesheet" href="${ctx}/style/huyi.css" />
</head>
<script type="text/javascript"> 
	setInterval("showImg('.b-ad')",3000);
	function showImg(obj) {
		var _ul=$(obj).find('.ad-list');
		var _height=$(obj).height();
		_ul.animate({'margin-top':-_height},300,function(){
			$(this).css({'margin-top':0}).find('li:first').appendTo(_ul);
		})
	}
</script>
<body class="ariBody">
		<header id="header">
		<a href="javascript:history.back(-1);" class="backBtn"></a>
		<h2 class="title">促销详情</h2>
		<a class="phone" href="tel:123456789"></a>
	</header>

	<div id="main">
		<section class="aritle">
			<c:forEach items="${picture}" var="picture">
				<h2 class="ari-title">${picture.title }</h2>
				<div class="ari-info"><span>${picture.publisher }</span><span><fmt:formatDate value='${picture.create_time }' pattern='yyyy-MM-dd' /></span></div>
				<div class="aritle-content">
					${picture.context}
				</div>
			</c:forEach>
			
		</section>
	</div>
</body>
</html>
