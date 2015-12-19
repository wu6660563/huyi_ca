<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML>
<html>
	<head>
	<%@ include file="../commons/global.jsp"%>
	<title>客户中心</title>
	<link rel="stylesheet" href="${ctx}/style/huyi.css" />
	<script src="${ctx}/script/zepto.min.js"></script>
	<script src="${ctx}/script/jquery.min.js"></script>
</head>
<script type="text/javascript"> 
	setInterval("showImg('.b-ad')",3000);
	function showImg(obj) {
		var _ul=$(obj).find('.ad-list');
		var _height=$(obj).height();
		_ul.animate({'margin-top':-_height},300,function(){
			$(this).css({'margin-top':0}).find('li:first').appendTo(_ul);
		})
	}
</script>
<body>
		<header id="header">
			<a href="javascript:history.back(-1);" class="backBtn"></a>
			<h2 class="title">商品</h2>
			<a class="phone" href="tel:123456789"></a>
		</header>
		<div class="dlmain"><!--开发注意  current 是指新(new)右上角的标志  已经处理的删掉-->
			<c:forEach items="${workOrder}" var="workOrder">
				<c:choose >
					<c:when test="${workOrder.is_search == 1}"><dl class="dlList "></c:when>
					<c:otherwise><dl class="dlList current"></c:otherwise>
				</c:choose>
					<dt><a href="${ctx }/application?method=feedbackList&wordId=${workOrder.work_order_id}&productTypeId=${workOrder.product_type_id}">${workOrder.typeName} ${workOrder.buy_items}</a></dt>
					<dd>
						<a href="${ctx }/application?method=feedbackList&wordId=${workOrder.work_order_id}&productTypeId=${workOrder.product_type_id}">
							<div class="dl-desc">
								<div class="dl-time"><p class="left"><span>签约时间：</span><fmt:formatDate value='${workOrder.modify_time }' pattern='yyyy-MM-dd' /></p><p class="right"><span>到期时间：</span>${workOrder.expiry_date }</p></div>
								<p><span>服务年限：</span>${workOrder.years } 年</p>
								<p><span>金额：</span>${workOrder.amount }万</p>
							</div>
						</a>
						<span class="status">${workOrder.sname}</span>
					</dd>
				</dl>
			</c:forEach>
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
</body>
</html>
