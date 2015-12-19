<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/cglobal.jsp"%>
		<title>会员店期刊</title>
		<link rel="stylesheet" href="${ctx}/client/style/normalize.css" />
		<link rel="stylesheet" href="${ctx}/client/style/zz.css" />
		<script type="text/javascript" src="${ctx}/client/js/jquery-1.11.0.min.js"></script>
		<script type="text/javascript">
			function subData(){
				// 获取评论者的基本信息
				var content = $.trim($("#content").val());
				if(content == "" || content.length < 5){
					alert("评论不能少于5个字符！");
					return;
				}
				if(content.length > 200){
					alert("评论不能超过200个字符！");
					return;
				}
				if(localStorage.getItem("provinceName") == null){
					localStorage.setItem("provinceName","");
				}
				if(localStorage.getItem("cityName") == null){
					localStorage.setItem("cityName","");
				}
				if(localStorage.getItem("regionName") == null){
					localStorage.setItem("regionName","");
				}
				// 提交评论信息
				$.post("${ctx}/ctwo", {
					methType: "other",
					articleId: "${param.articleId }",
					operType: 1,
					content: content,
					temn_code: localStorage.getItem("terminalCode"),
					temn_name: localStorage.getItem("terminalName"),
					temn_city: localStorage.getItem("provinceName")+localStorage.getItem("cityName")+localStorage.getItem("regionName"),
					comments_name: localStorage.getItem("userName"),
					position: localStorage.getItem("userJob") }, function(data){
					if (data.ok == true) {
						zdyAlert();
					}
				});
			}

			function zdyAlert(){
				$('.bg').remove();
				var bg="<div class='bg'></div>";
				$('body').append(bg);
				var tip=$('.show-tips');
				tip.show();
				var winW=$(window).width();
				var winH=$(window).height();
				var w=tip.outerWidth();
				var h=tip.outerHeight();
				tip.css({
					left:winW/2-w/2,
					top:winH/2-h/2
				});
				$('.bg').one('click',function(){
					$(this).hide();
					$('.show-tips').fadeOut(100);
				});
			}

			function qdClick(){
				window.history.back();
			}
		</script>
	</head>
	<body>
<%--		<header id="header">--%>
<%--			<a href="javascript:window.history.back();" class="btn-back"></a>--%>
<%--			<h1 class="logo-title">发表评论</h1>--%>
<%--		</header>--%>
		<section id="main">
			<div class="leaveArea">
				<h3>评论</h3>
				<div class="leave-c">
					<textarea class="leave-txt" placeholder="请文明发言(最大200个字符)" id="content" maxlength="200"></textarea>
				</div>
				<div class="center">
					<a href="javascript:subData();" class="load-more leave-btn">发表</a>
				</div>
			</div>
		</section><!--main-->
		
		<div class="show-tips">
			<p class="show-title">发表成功，感谢您的参与！</p>
			<div class="show-btn">
				<a href="javascript:qdClick();">确定</a>
			</div>
		</div>
	</body>
</html>