<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/global.jsp"%>
		<title>我的客户</title>
		<meta charset=utf-8>
		<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/> 
		<link rel="stylesheet" href="${ctx}/businessclient/style/sw.css" />
		<link rel="stylesheet" href="${ctx}/businessclient/style/normalize.css" />
		<script type="text/javascript" src="${ctx}/businessclient/js/jquery.js"></script>
	<script>
		$(document).ready(function(){
			var span = $('.tab').children('span');
			span.on('click',function(){
				$(this).addClass('current').siblings().removeClass('current'); 
				var _index = $('.tab > span').index(this); 
				var flag = _index;
				var customerName = $(".search-txt").val(); 
				var o = $("#div"+_index);
				$.getJSON("${ctx}/business.action?methType=index", {flag:flag, customerName:customerName}, function(data) {
							$('.gongdan').find('.gd-c').eq(_index).show().siblings().hide();
							o.empty();//清空原来列表
							if(data != ""){   
								for(var i = 0; i<data.length;i++){ 
									var question = "";
									if (data[i].question >0){ 
										question = "有问题单";
									}
									$("<li>" 
											+"<a href='${ctx}/business.action?methType=clientInfo&flag=0&customerInfoId="+data[i].customer_info_id+"'>"     
												+"<h2 class='hd'><span class='title'>"+data[i].customer_name+"</span></h2>" 
												+"<p><span>累计合作次数："+data[i].total_num+"</span><span>累计合作金额："+data[i].total_count+"</span></p>" 
												+"<p><span>90天内可续费项目个数："+data[i].several_projects+"</span></p>"
												+"<p><span>到期时间："+data[i].expiry_date+"</span></p>"
												+"<p><span>最近一次合作时间："+data[i].last_cooperation_time+"</span></p>" 
												+"<i>"
													+question
												+"</i>"
											+"</a>"
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
			<a href="javascript:history.back(-1);" class="back-btn">返回</a>
			<h1>我的客户</h1>
		</div>
		<div class="search-box">
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
					<c:if test="${customerInfoList != null}"> 
						<c:forEach items="${customerInfoList}" var="customerInfo">
							<li>
								<a href="${ctx}/business.action?methType=clientInfo&flag=0&customerInfoId=${customerInfo.customer_info_id}">       
									<h2 class="hd"><span class="title">${customerInfo.customer_name}</span></h2>
									<p><span>累计合作次数：${customerInfo.total_num}</span><span>累计合作金额：${customerInfo.total_count}</span></p>
									<p><span>90天内可续费项目个数：${customerInfo.several_projects}</span></p>
									<p><span>到期时间：${customerInfo.expiry_date}</span></p>
									<p><span>最近一次合作时间：${customerInfo.last_cooperation_time}</span></p>
									<i><c:if test="${customerInfo.question >0}">
										有问题单
										</c:if>
									</i>
								</a>
							</li>	
						</c:forEach>
					</c:if>
					<c:if test="${customerInfoList == null}">
						<div id="div0"></div>
					</c:if>
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
