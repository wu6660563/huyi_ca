<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/global.jsp"%>
		<title>工单详情</title>
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
					var flag = _index; 
					var o = $("#div"+_index);
					$.getJSON("${ctx}/business.action?methType=workOrder", {workOrderflag:"workOrderflag",flag:flag,workOrderId:${workOrderId}}, function(data) {  
							$('.gongdan').find('.gd-c').eq(_index).show().siblings().hide();
							o.empty();//清空原来列表
							if(flag == 1){
								if(data != ""){ 
									$("<div class='process'>"
										+"<img src='"+data.path+"' />" 
									+"</div>").appendTo(o);
								}
							}else if(flag == 0){
								if(data != ""){    
									for(var i = 0; i<data.length;i++){ 
										var picture = "";
										var list = data[i].pictureList;
										for(var j = 0; j<list.length;j++){ 
											picture = picture + "<div class='gd-img'>"
												+ "<span><img src="+list[j].path+" /></span>"  
											+ "</div>";
										}
										$("<dd>"   
											+"<h2>"+data[i].service_pro_name+"<i class='star'></i></h2>"       
												+"<time>"+data[i].modify_time+"</time>" 
												+"<p class='gd-desc'>"  
													+data[i].context
												+"</p>"
													+picture 
										+"</dd>").appendTo(o);     
									}
								}	
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
			<h1>工单详情</h1>
		</div>
	</header>
	<div id="main">
		<nav class="tab">
			<span class="current">服务进程信息反馈</span>
			<span>标准办事流程查看</span>
		</nav>
		<section class="gongdan">
			<div class="gd-c" style="display:block">
				<dl class="gd-list">
					<c:if test="${feedBacks != null}"> 
						<c:forEach items="${feedBacks}" var="feed">
							<dd>
						 		<h2>${feed.service_pro_name}<i class="star"></i></h2>
								<time>${feed.modify_time}</time>
								<p class="gd-desc">
									${feed.context}
								</p>
						    <c:forEach items="${feed.pictureList }" var="picture">
								<div class="gd-img">
									<span><img src="${ctx}/businessclient/${picture.path}" /></span>
								</div>
							</c:forEach>
						</dd>
						</c:forEach>
					</c:if>	
					<c:if test="${feedBacks == null}">
						<div id="div0"></div>
					</c:if>
				</dl>
			</div>
			<div class="gd-c">
				<div id="div1"></div>
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
