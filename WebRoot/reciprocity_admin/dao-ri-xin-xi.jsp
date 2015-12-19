<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>导入详细信息结果</title>
<link href="${ctx}/reciprocity_admin/style/index.css" type="text/css" rel="stylesheet" />
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/jquery.js"></script>
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/my97/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/popup.js"></script>

</head>
<body>
	<jsp:include page="header-include.jsp">
		<jsp:param name="currentClass" value="index"/>
	</jsp:include>

	<div id="main">
		<div class="custom">
			<h4 class="hd"><span class="title">导入详细信息结果</span><div class="hd-r"><a href="javascript:history.back();" class="back-btn">返回</a></div></h4>
			<div class="hm">
				<table class="table-list" width="100%">
					<tr class="top-tr">
						<th>客户ID</th>
						<th>客户名</th>
						<th>服务等级</th>
						<th>业务联系人</th>
						<th>联系电话</th>
						<th>公司地址</th>
						<th>主营产品</th>
						<th>简介</th>
						<th>专属客服</th>
					</tr>
					<c:forEach var="vo" items="${list}">
					<tr>
						<td>${vo.customer_id}</td>
						<td>${vo.customer_name}</td>
						<td>${vo.customer_rating_id}</td>
						<td>${vo.business_custacts}</td>
						<td>${vo.phone}</td>
						<td>${vo.adress}</td>
						<td>${vo.main_products}</td>
						<td>${vo.introduction}</td>
						<td>${vo.cust_service_name}</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div><!--main-->

</body>
</html>
