<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
	<head>
	<%@ include file="../commons/global.jsp"%>
	<title>客户中心</title>
	<link rel="stylesheet" href="${ctx}/style/huyi.css" />
	<script src="${ctx}/script/zepto.min.js"></script>
	<script src="${ctx}/script/jquery.min.js"></script>
	<script src="${ctx}/script/comm.js"></script>
</head>

<body>
	<header id="header">
		<h2 class="title">客服中心</h2>
		<span class="setting"></span>
		<nav class="menu">
			<ul>
				<li><a href="edit-pass.html?t=fdsaf">修改密码</a></li>
				<li><a href="#">解除账号</a></li>
				<li><a href="#">退出账号</a></li>
			</ul>
		</nav>
	</header>
		<div id="main">
			<section class="info-head">
				<c:forEach items="${custom}" var="custom">
				<input type="hidden" value="${custom.customer_info_id}" id="customerInfoId"/>
					<div class="company">
						<span class="cop-logo"><img src="img/logo.png"></span>
						<div class="company-c">
							<h5>${custom.login_name}</h5>
							<div class="company-desc">
								联系人：${custom.business_contacts}<br />
								联系电话：${custom.phone}<br />
								合作次数：<em class="text-red">${custom.several_projects} </em>次
							</div>
						</div>
					</div>
					<div class="kefu"><span>您的专属客服</span><span class="kf-name">${custom.kefuName}${custom.kefuPhone}<span></div>
				</c:forEach>
			</section>
			<section class="list">
				<ul>
					<c:forEach items="${workOrder}" var="workOrder">
						<li><a href="${ctx }/application?method=searchWord&contractId=${workOrder.contract_id}&productTypeId=${workOrder.product_type_id}">
						<i><img src="${ctx }/fileupload/${workOrder.path }" /></i><h4>${workOrder.itemName}</h4><span class="num">${workOrder.amount}</span></a></li>
						
						
					</c:forEach>
				</ul>
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
	<script type="text/javascript"> 

	var flag=false;
	 var menu=$('.menu');
	 $('.setting').on('tap',function(){		
		 if (menu.is(':visible')) {
			$('.menu').fadeOut(200);
			flag=false;
		 }else{
			$('.menu').fadeIn(200);
			flag=true;
		 }
		  return false;
	 });
	 $('.menu a').on('tap',function(){flag=false; })
	  $(window).on('tap',function(){
		  if (flag){
			  menu.fadeOut(200);
		  }	
		  flag=false;
	 });
</script>
</body>

</html>
