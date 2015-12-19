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
			function localStorageMng(){
				if (localStorage.getItem("onlyKey") != "") {
					if(localStorage.getItem("onlyKey") != '${onlyKey }'){
						localStorage.clear();
						localStorage.setItem("onlyKey","${onlyKey }");
						getServerData();
					}
<%--					$(".loading-bg,.loading").hide();--%>
				} else {
					localStorage.setItem("onlyKey","${onlyKey }");
					getServerData();
				}
			}
			
			function getServerData() {
				var serverUrl = serverDomain+"/mobile/business/magazineCallBackqueryAccountInfo.action?tsno=uuid&token="+localStorage.getItem("onlyKey");
					$.ajax({
					    async: false,
					    url: serverUrl,
					    type: "GET",
					    dataType: 'jsonp',
					    //jsonp的值自定义,如果使用jsoncallback,那么服务器端,要返回一个jsoncallback的值对应的对象.
					    jsonp: 'jsoncallback',
					    //要传递的参数,没有传参时，也一定要写上
						data: null,
					    timeout: 3000,
					    //返回Json类型
						contentType: "application/json;utf-8",
					    //服务器段返回的对象包含name,data属性.
					    success: function (json) {
							if(json != ""){
								var obj = eval(json);
								localStorage.setItem("userName",obj.mobResponse.userName);
								localStorage.setItem("userJob",obj.mobResponse.userJob);
								localStorage.setItem("terminalCode",obj.mobResponse.terminalCode);
								localStorage.setItem("terminalName",obj.mobResponse.terminalName);
								localStorage.setItem("provinceName",obj.mobResponse.provinceName);
								localStorage.setItem("cityName",obj.mobResponse.cityName);
								localStorage.setItem("regionName",obj.mobResponse.regionName);
<%--								$(".loading-bg,.loading").hide();--%>
							}else{
								$(".show-title").html("接口服务器异常,请稍后再试！");
								zdyAlert();
							}
					    },
					    error: function (jqXHR, textStatus, errorThrown) {
						    $(".show-title").html("请求接口服务器异常，异常代码："+errorThrown);
					        zdyAlert();
<%--					        $(".loading-bg,.loading").hide();--%>
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
				$('.show-tips').hide();
			}
		
			function clearLocalStorage() {
				localStorage.clear();
				alert("清除");
			}
		</script>
	</head>
	<body style="background:#863c2b;" onload="javascript:localStorageMng();">
<%--		<a href="javascript:clearLocalStorage();">清空缓存</a>--%>
		<section class="bookArea">
			<ul class="book-list clearfix">
				<c:forEach var="mi" items="${miList }">
					<li>
						<a href="${ctx }/clients?methType=info&magazineId=${mi.magazine_id }">
							<span><img src="/mzimg/${mi.magazine_path }" /></span>
						</a>
					</li>
				</c:forEach>
				<c:forEach var="fl" items="${fillList }">
					<li>
						<a href="javascript:void(0);">
							<span><img src="${ctx}/client/images/nopic.png" /></span>
						</a>
					</li>
				</c:forEach>
			</ul>
		</section>
		<div class="show-tips">
			<p class="show-title">接口服务器异常,请稍后再试！</p>
			<div class="show-btn">
				<a href="javascript:qdClick();">确定</a>
			</div>
		</div>
<%--		<div class="loading-bg"></div>--%>
<%--		<div class="loading">--%>
<%--			<span class="loading-img"><img src="${ctx}/client/images/loading3.gif" /></span>--%>
<%--			<p>正在处理...</p>--%>
<%--		</div>--%>
	</body>
</html>