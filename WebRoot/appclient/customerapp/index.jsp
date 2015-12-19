<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../commons/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<style type="text/css">
		.imgtest{
		    display: block;
		    padding: 10px;
		}
			</style>
	<title>首页</title>
	<%@ include file="../commons/global.jsp"%>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/> 
	<link rel="stylesheet" href="${ctx}/appclient/customerapp/style/kefu.css" />
	<link rel="stylesheet" href="${ctx}/appclient/customerapp/style/normalize.css" />
	<script type="text/javascript" src="${ctx}/appclient/customerapp/js/jquery.js"></script>
	<script>
		$(document).ready(function(){
			setInterval("showImg('.b-ad')",3000);
		});
		function changeStart(id){
			var contract_id = $("#contract_id").val();
			$.getJSON("${ctx}/customerapp.action?method=changeStart", {contract_id:id}, function(data) { 
							if(data == 1){  
								$("#start"+id).html('<font color="red" >已确认</font>');
							}else{
								alert("合同确认失败");
							}
				});
		}
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
				<table width="100%">
				    <tr>
				    	<td><br>
							<h3 class="c-tit">${custom.company_name }</h3>
							<p>${custom.business_contacts }</p>
							<p>${custom.phone}</p>
						</td>
						<c:forEach items="${custcomerrat}" var="custcomerrat">
				    	<td align="right" >
				    		<span class="card-img" >
								<img src="${ctx}/fileupload/${custcomerrat.pic_path}" style="width: 100px;height: 100px">
							</span>
				    	</td>
				    	</c:forEach>
				    </tr>
				</table><!--
				<br>
					<h3 class="c-tit">${custom.company_name }</h3>
					<p>${custom.business_contacts }</p>
					<p>${custom.phone}</p>
					<span class="card-img">
						<img src="${ctx}/customerapp/img/pai.png">
						<img src="${picPath}"/> 
					</span>
					
				--></div>
					 </c:forEach>
				<c:forEach items="${custcomerrat}" var="custcomerrat">
					<h2 class="hd"><span>${custcomerrat.level_name }</span></h2>
					<div class="hm">
						<p class="service-txt">${custcomerrat.level_desc }</p>
					</div>
				</c:forEach>
		<c:forEach items="${contract}" var="contract">
			<h2 class="hd"><span>你</span> 
			<fmt:formatDate value='${contract.signing_time }' pattern='yyyy-MM-dd' />
			 购买的服务</h2>
			<div class="hm">
			<input type="hidden" value="${contract.contract_id }" id="contract_id">
				<h3 class="c-tit">合同号：${contract.contract_no }</h3>
				<p>合作金额：${contract.cooperation_num }万</p>
				<p>责任商务代表：${contract.business_name }</p>
				<span class="confirm">
					<c:choose>
						<c:when test="${contract.is_sure_contract == 0}"> <div id="start${contract.contract_id }"><a href="#" onclick="changeStart(${contract.contract_id});" id="no">点此确认合同</a></div> </c:when>
						<c:when test="${contract.is_sure_contract == 1}"> <div id="start${contract.contract_id }"><font color="red" >已确认</font></div> </c:when>
					</c:choose>
				 </span>
			</div>
			<c:forEach items="${workOrder}" var="workOrder">
			<c:if test="${contract.contract_id == workOrder.contract_id }">
				<ul class="xm-list">
					<li>
						<a href="${ctx}/customerapp.action?method=details&workOrderId=${workOrder.work_order_id}&pncode=${phcode }&version=${version}">
							<h5>
								<span class="xm-sb left">${workOrder.product_type_id}</span><span class="xm-tit left">${workOrder.buy_items }</span>
								<span class="xm-jc right"> 查看进程   </span>
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
			<!--<li><img src="${ctx}/fileupload/3.png"  /></li>
			<li><img src="${ctx}/fileupload/ad.png"  /></li>-->
				<c:forEach items="${product}" var="product">
					<li><a href="#"><img src="${ctx}/fileupload/${product.path}"  /></a></li>
				</c:forEach>
			</ul>
		</div>
	</footer>
</body>
</html>
