<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/global.jsp"%>
		<title>客户详情</title>
		<meta charset=utf-8>
		<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/> 
		<link rel="stylesheet" href="${ctx}/businessclient/style/sw.css" />
		<link rel="stylesheet" href="${ctx}/businessclient/style/normalize.css" />
		<script type="text/javascript" src="${ctx}/businessclient/js/jquery.js"></script>
		<script type="text/javascript" src="${ctx}/businessclient/js/zoom.js"></script>
		<script type="text/javascript" src="${ctx}/businessclient/js/zepto.js"></script>
	    <script type="text/javascript" src="${ctx}/businessclient/js/touch.js"></script>
	</head>
	<body>
		<header id="header">
		<div class="header-m">
			<a href="javascript:history.back(-1);" class="back-btn">返回</a>
			<h1>客户详情</h1>
		</div>
	</header>
	<div id="main">
		<section class="gongdan">
			<ul class="kh-list">
				<li>
					<h2 class="hd"><span class="title">${customerInfoMap.customer_name}</span></h2>
					<p><span>主营产品：${customerInfoMap.main_products}</span></p>
					<p><span>公司地址：${customerInfoMap.adress}</span></p>
					<p><span>业务联系人：${customerInfoMap.adress}</span></p>
					<p><span>联系电话：${customerInfoMap.phone}</span></p>
					<p class="mt10"><span>累计合作次数：${customerInfoMap.total_num}</span></p>
					<p><span>累计合作金额：${customerInfoMap.total_count}</span></p>
					<p><span>90天内可续费项目个数：${customerInfoMap.several_projects}</span></p>
					<p><span>到期时间：${customerInfoMap.expiry_date}</span></p>
				</li>
			</ul>
		</section>
		<c:forEach items="${contracts}" var="contracts">
		<section class="box">
			<h2 class="hd"><span>合作</span>详情</h2>
			<div class="hm">
				<h3 class="c-tit">合同号：${contracts.contract_no}</h3>				
				<p>签约时间：${contracts.signing_time}</p>
				<p>合作金额：${contracts.cooperation_num}</p>
				<div class="ht-img">
					<c:forEach items="${contracts.pictureList}" var="picture">
					<span><img src="${picture.path}" /><em>${picture.sort}</em></span>
					</c:forEach>
				</div>
				<span class="confirm">${contracts.is_sure_contract}</span>
			</div>
			
			<c:forEach items="${contracts.workOrderList}" var="workOrder">
				<ul class="xm-list">
					<li>
						<a href="${ctx}/business.action?methType=workOrder&flag=0&workOrderId=${workOrder.work_order_id}">
							<h5><span class="xm-sb left">[商标]</span><span class="xm-tit left">${workOrder.buy_items}</span><span class="xm-jc right">查看进程</span></h5>
							<p><span>单价 ${workOrder.amount}元</span><span>服务年限 ${workOrder.years}年</span></p>
							<p><span>于${workOrder.expiry_date}到期</span></p>
						</a>
					</li>
				</ul>
			</c:forEach>
		</section>
		</c:forEach>
	</div><!--main-->
	
	<section id="wrapper" class="wrapper">
		<div id="scroller">
			<img class="imgShow" src=""  />
		</div>
	</section>
	<script>
			var myScroll;
			function loaded() {
				myScroll = new IScroll('#scroller', { 
					zoom: true,
					scrollX: true,
					scrollY: true,
					mouseWheel: true,
					zoomMin:1,
					wheelAction: 'zoom'
				});
			}
			
			document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 10); }, false);
	
	
	
		$(function(){
			$('.ht-img span').find('img').on('click',function(e){	
				//document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
				$('#wrapper').show();
				var _src=$(this).attr('src');
				$('#wrapper').find('img').attr('src',_src).css({'width':100+'%'});	
			//	resize();
			});
			$('#wrapper').bind('touchmove',function(){
			//	resize();
				
			});
			
			$('.wrapper ').on('tap',function(e){	
				$(this).hide();	
			});
			
		});
	
	function resize(){
				var H=$('#wrapper').height();
				var W=$('#wrapper').width();
				$('#scroller').height(H);
				$('#scroller').width(W);
				$('#scroller').css({
					'margin':-H/2+'px 0 0 '+(-W/2)+'px'
				});
	}
	
	</script>
	</body>
</html>
