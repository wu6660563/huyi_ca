<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
	<head>
			<%@ include file="../commons/global.jsp"%>
		<title>工单详情</title>
		<meta charset=utf-8>
		<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/> 
	<link rel="stylesheet" href="${ctx}/appclient/customerapp/style/kefu.css" />
	<link rel="stylesheet" href="${ctx}/appclient/customerapp/style/normalize.css" />
	<script type="text/javascript" src="${ctx}/appclient/customerapp/js/jquery.js"></script>
        
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
		//控制选项卡
		function changeOnclick(id){
			if(id == 1){
				$("#orderDetail").removeAttr("style");
				$("#orderDetail").attr("style","display:block");
				$("#images").removeAttr("style");
				$("#images").attr("style","display:none");
			}else{
				$("#images").removeAttr("style");
				$("#images").attr("style","display:block");
				$("#orderDetail").removeAttr("style");
				$("#orderDetail").attr("style","display:none");
			}
		}
</script>
		
	</head>
	<body>
		<header id="header">
		<div class="header-m">
			<a href="javascript:history.back(-1);" class="back-btn">返回</a>
			<h1>工单详情 </h1>
			<c:if test="${version == 0 }"><a href="javascript:myObject.call('${phcode}');" class="tel" ></a></c:if>
			<c:if test="${version == 1 }"><a href="tel:${phcode}" class="tel" ></a></c:if>
		</div>
	</header>
	<div id="main">
		<nav class="tab">
			<span class="current" onclick="changeOnclick(1)">服务进程信息反馈</span>
			<span onclick="changeOnclick(2)">标准办事流程查看</span>
		</nav>
		<div class="gd-c" style="display:block" id="orderDetail">
			<c:forEach items="${feedBack}" var="feedBack">
					<dl class="gd-list">
						<dd>
							<h2>${feedBack.service_pro_name }</h2>
							<time>
								<fmt:formatDate value='${feedBack.create_time }' pattern='yyyy-MM-dd hh:mm:ss' />
							</time>
							<p class="gd-desc">
								${feedBack.context}
							</p>
						</dd>
					</dl>
			</c:forEach>	
			</div>	
			<div class="gd-c" id="images" style="display:none">
				<img src="${ctx}/fileupload/2.png" />
				<!--<div class="process" >
					 <c:forEach items="${productType}" var="productType">
						 <img src="${ctx}/fileupload/${productType.pic_path}" />
					</c:forEach>
				 </div>
			--></div>
		</section>
	</div> 

	<footer id="footer">
		<div class="b-ad">
			<ul class="ad-list">
				<!--<li><img src="${ctx}/fileupload/3.png"  /></li>
				<li><img src="${ctx}/fileupload/ad.png"  /></li>
				-->
				<c:forEach items="${product}" var="product">
					<li><a href="#"><img src="${ctx}/fileupload/${product.path}"  /></a></li>
				</c:forEach>
				
			</ul>
		</div>
	</footer>

	</body>
</html>
