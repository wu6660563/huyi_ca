<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../commons/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>首页</title>
	<%@ include file="../commons/global.jsp"%>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/> 
	<link rel="stylesheet" href="${ctx}/customerapp/style/kefu.css" />
	<link rel="stylesheet" href="${ctx}/customerapp/style/normalize.css" />
	<script type="text/javascript" src="${ctx}/customerapp/js/jquery.js"></script>
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

	<div id="main">
			<c:forEach items="${custom}" var="custom">
				<div class="card-left">
				<br>
					<h3 class="c-tit">${custom.cust_service_name }</h3>
					<p>${custom.business_contacts }</p>
					<p>${custom.phone}</p>
				</div>
			
				
					<span class="card-img">
					<img src="${ctx}/customerapp/img/pai.png">
					<!--<img src="${picPath}"/>
				--></span>
					</c:forEach>
				<c:forEach items="${custcomerrat}" var="custcomerrat">
					<h2 class="hd"><span>${custcomerrat.level_name }</span></h2>
					<div class="hm">
						<p class="service-txt">${custcomerrat.level_name }</p>
					</div>
				</c:forEach>
		<c:forEach items="${contract}" var="contract">
			<h2 class="hd"><span>你</span> ${contract.signing_time } 购买的服务</h2>
			<div class="hm">
				<h3 class="c-tit">合同号：${contract.contract_no }</h3>
				<p>合作金额：${contract.cooperation_num }万</p>
				<p>责任商务代表：${contract.manager_id }</p>
				<span class="confirm">
				<c:choose>
					<c:when test="${contract.is_sure_contract == 0}">待确认</c:when>
					<c:when test="${contract.is_sure_contract == 1}"> <font color="red">已确认</font> </c:when>
				</c:choose>
				 </span>
			</div>
			<c:forEach items="${workOrder}" var="workOrder">
			<c:if test="${contract.contract_id == workOrder.contract_id }">
				<ul class="xm-list">
					<li>
						<a href="${ctx}/customer.action?method=details&workOrderId=${workOrder.work_order_id}">
							<h5>
								<span class="xm-sb left">[商标]</span><span class="xm-tit left">${workOrder.buy_items }</span>
								<span class="xm-jc right"> 查看进程</span>
							</h5>
							<p><span>单价:  ${workOrder.amount }万</span>
							<span> 服务年限:  ${workOrder.years }年</span></p>
							<p><span>于 ${workOrder.expiry_date } 到期</span></p>
						</a>
					</li>
				</ul>
			</c:if>
			</c:forEach>
		</c:forEach>
		
	</div><!--main-->

	<footer id="footer">
		<div class="b-ad">
			<ul class="ad-list">
				<li><a href="#"><img src="${ctx}/customerapp/img/ad.png" /></a></li>
				<li><a href="#"><img src="${ctx}/customerapp/img/3.png" /></a></li>
				<li><a href="#"><img src="${ctx}/customerapp/img/1.jpg" /></a></li>
				<li><a href="#"><img src="${ctx}/customerapp/img/3.png" /></a></li>
			</ul>
		</div>
	</footer>
</body>
</html>
