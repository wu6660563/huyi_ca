<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/global.jsp"%>
		<title>工单详情</title>
		<meta charset=utf-8>
		<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/> 
	<link rel="stylesheet" href="${ctx}/customerapp/style/kefu.css" />
	<link rel="stylesheet" href="${ctx}/customerapp/style/normalize.css" />
	<script type="text/javascript" src="${ctx}/customerapp/js/jquery.js"></script>
		<script>
		
			$(document).ready(function(){
			var span=$('.tab').children('span');
			span.on('click',function(){
				$(this).addClass('current').siblings().removeClass('current');
				var _index=$('.tab > span').index(this);
				$('.gongdan').find('.gd-c').eq(_index).show().siblings().hide();
			});
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
			<a href="javascript:history.back(-1);" class="back-btn">返回</a>
			<h1>工单详情</h1>
			<a href="tel:13888888889" class="tel">电话</a>
		</div>
	</header>
	<div id="main">
		<nav class="tab">
			<span class="current">服务进程信息反馈</span>
			<span>标准办事流程查看</span>
		</nav>
			<c:forEach items="${feedBack}" var="feedBack">
				<div class="gd-c" style="display:block">
					<dl class="gd-list">
						<dd>
							<h2>${feedBack.service_pro_name }</h2>
							<time>${feedBack.create_time }</time>
							<p class="gd-desc">
								${feedBack.context}
							</p>
						</dd>
					</dl>
				</div>
			</c:forEach>		
			<div class="gd-c">
				<div class="process" >
						<img src="${ctx}/customerapp/img/2.png" />
				</div>
			</div>
		</section>
	</div> 

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
