<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/global.jsp"%>
		<title>社区公告</title>
		<meta charset=utf-8>
		<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/> 
		<link rel="stylesheet" href="${ctx}/businessclient/style/sw.css" />
		<link rel="stylesheet" href="${ctx}/businessclient/style/normalize.css" />
		<script type="text/javascript" src="${ctx}/businessclient/js/jquery.js"></script>
		<script>
			$(document).ready(function(){
				var span=$('.tab').children('span');
				span.on('click',function(){
					$(this).addClass('current').siblings().removeClass('current');
					var _index=$('.tab > span').index(this);
					$('.gongdan').find('.gd-c').eq(_index).show().siblings().hide();
					var flag = _index;
					var o = $("#div"+_index);
					$.getJSON("${ctx}/business.action?methType=community", {flag:flag}, function(data) { 
							$('.gongdan').find('.gd-c').eq(_index).show().siblings().hide();
							o.empty();//清空原来列表
							if(data != ""){   
								for(var i = 0; i<data.length;i++){ 
									$("<dd>" 
											+"<h2>"+data[i].title+"<i class='star'></i></h2>"      
												+"<time>"+data[i].modify_time+"</time>" 
												+"<p class='gd-desc'>"  
												+data[i].context
												+"</p>"
												+"<p class='b-more'><a href='${ctx}/business.action?methType=infoDiss&infoId="+data[i].info_id+"'>了解详情</a></p>"    
											+"</a>"
										+"</dd>").appendTo(o);    
								}
							}else{
								$("没有数据显示").appendTo(o); 
							}
					});
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
			<h1>社区公告</h1>
		</div>
	</header>
	<div id="main">
		<nav class="tab tab1">
			<span class="current">全部</span>
			<span>重要通知</span>
		</nav>
		<section class="gongdan">
			<div class="gd-c" style="display:block">
				<dl class="gd-list">
					<c:if test="${infoDissList != null}">
						<c:forEach items="${infoDissList}" var="infoDiss">
							<dd>
						 		<h2>${infoDiss.title}<i class="star"></i></h2>
								<time>${infoDiss.modify_time}</time>
								<p class="gd-desc">
									${infoDiss.context}
								</p>
								<p class="b-more"><a href="${ctx}/business.action?methType=infoDiss&infoId=${infoDiss.info_id}" >了解详情</a></p>
							</dd>
						</c:forEach>
					</c:if>
					<c:if test="${infoDissList == null}">
						<div id="div0"></div>
					</c:if>
				</dl>
			</div>
			<div class="gd-c">
				<dl class="gd-list">
						<div id="div1"></div> 
				</dl>
			</div>
		</section>
	</div><!--main-->

	<footer id="footer">
		<div class="b-ad">
			<ul class="ad-list">
				<li><a href="#"><img src="${ctx}/businessclient/img/ad.png" /></a></li>
				<li><a href="#"><img src="${ctx}/businessclient/img/3.png" /></a></li>
				<li><a href="#"><img src="${ctx}/businessclient/img/1.jpg" /></a></li>
				<li><a href="#"><img src="${ctx}/businessclient/img/3.png" /></a></li>
			</ul>
		</div>
	</footer>
	</body>
</html>
