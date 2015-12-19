<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/global.jsp"%>
		<title>我的客户</title>
		<meta charset=utf-8>
		<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/> 
		<link rel="stylesheet" href="${ctx}/appclient/businessclient/style/sw.css" />
		<link rel="stylesheet" href="${ctx}/appclient/businessclient/style/normalize.css" />
		<script type="text/javascript" src="${ctx}/appclient/businessclient/js/jquery.js"></script>
	<script>
		$(document).ready(function(){
			var span = $('.tab').children('span');
			span.on('click',function(){
				$(this).addClass('current').siblings().removeClass('current'); 
				var _index = $('.tab > span').index(this); 
				var flag = _index;
				var customerName = $(".search-txt").val(); 
				var userId = $("#userId").val(); 
				var o = $("#div"+_index);
				$.getJSON("${ctx}/business.action?methType=index", {flag:flag, customerName:customerName,userId:userId}, function(data) { 
							$('.gongdan').find('.gd-c').eq(_index).show().siblings().hide();
							o.empty();//清空原来列表
							if(data != ""){   
								for(var i = 0; i<data.length;i++){ 
									var question = "";
									if (data[i].question >0){ 
										question = "有问题单";
									}
									$("<li>" 
										+"<h2 class='hd'><a href='${ctx}/business.action?methType=clientInfo&flag=0&version=1&customerInfoId="+data[i].customer_info_id+"'><span class='title'>"+data[i].customer_name+"</span></a></h2>"  
										+"<p><span>累计合作次数："+data[i].total_num+"</span><span>累计合作金额："+data[i].total_count+"</span></p>" 
										+"<p><span>90天内可续费项目个数："+data[i].several_projects+"</span></p>"
										+"<p><span>到期时间：<fmt:formatDate value='"+data[i].expiry_date+"' pattern='yyyy-MM-dd' /></span></p>" 
										+"<p><span>最近一次合作时间：<fmt:formatDate value='"+data[i].last_cooperation_time+"' pattern='yyyy-MM-dd' /></span></p>" 
										+"<a href='${ctx}/business.action?methType=questionnaire&version=1&customerInfoId="+data[i].customer_info_id+"'><i>" 
											+question
										+"</i></a>"
										+"</li>").appendTo(o);    
								}
							}
				});
			})
		});
	</script>
	</head>
<body>	
	<form id="indexForm" method="post" action="${ctx}/business.action?methType=index">
	<header class="header1">
		<div class="header1-m">
			<a href="${ctx}/goback.do" class="back-btn">返回</a>
			<h1>我的客户</h1>
		</div>
		<div class="search-box">
			<input type="hidden" class="userId" value="${userId}"/>
			<input type="search" class="search-txt" placeholder="请输入公司名" />
		</div>
	</header>
	<div id="main">
		<nav class="tab">
			<span class="current">全部客户</span>
			<span>本月合作</span>
			<span>90天内续费</span>
			<span>问题单</span>
		</nav>
		<div class="gongdan">
			<div class="gd-c" style="display:block;">
				<ul class="kh-list">
					<div id="div0">
						<c:forEach items="${customerInfoList}" var="customerInfo">
							<li>
								<h2 class="hd"><a href="${ctx}/business.action?methType=clientInfo&flag=0&version=1&customerInfoId=${customerInfo.customer_info_id}"><span class="title">${customerInfo.customer_name}</span></a></h2>
								<p><span>累计合作次数：${customerInfo.total_num}</span><span>累计合作金额：${customerInfo.total_count}</span></p>
								<p><span>90天内可续费项目个数：${customerInfo.several_projects}</span></p>
								<p><span>到期时间：<fmt:formatDate value='${customerInfo.expiry_date}' pattern='yyyy-MM-dd' /></span></p>
								<p><span>最近一次合作时间：<fmt:formatDate value='${customerInfo.last_cooperation_time}' pattern='yyyy-MM-dd' /></span></p>
								<c:if test="${customerInfo.question >0}">
									<a href="${ctx}/business.action?methType=questionnaire&version=1&customerInfoId=${customerInfo.customer_info_id}"><i>
											有问题单
										</i></a>
								</c:if>
							</li>	
						</c:forEach>
					</div>
				</ul>
			</div>
			<div class="gd-c">
				<ul class="kh-list">
					<div id="div1"></div>
				</ul>
			</div>
			<div class="gd-c">
				<ul class="kh-list">
					<div id="div2"></div>
				</ul>
			</div>
			<div class="gd-c">
				<ul class="kh-list">
					<div id="div3"></div>
				</ul>
			</div>
		</div>
	</div><!--main-->
	</form>
	</body>
</html>
