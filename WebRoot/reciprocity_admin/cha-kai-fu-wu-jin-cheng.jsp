<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>导入问题单</title>
<link href="${ctx}/reciprocity_admin/style/index.css" type="text/css" rel="stylesheet" />
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/jquery.js"></script>
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/my97/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/popup.js"></script>

</head>
<body>
	<jsp:include page="header-include.jsp">
		<jsp:param name="currentClass" value="customer"/>
	</jsp:include>

	<div id="main">
		<div class="custom">
			<h4 class="hd"><span class="title">查看服务进程</span><div class="hd-r"><a href="javascript:history.back();" class="back-btn">返回</a></div></h4>
			<div class="hm">
				<table class="table-list" width="100%">            
					<tr class="top-tr">                     
						<th>合同号</th>
						<th>工单细则id</th>
						<th>服务进程名称</th>
						<th>描述</th>
						<th>时间</th>
					</tr>
					<c:forEach var="vo" items="${list}">
					<tr>
						<td>${vo.contract_no}</td>
						<td>${vo.work_order_id}</td>
						<td>${vo.service_pro_name}</td>
						<td>${vo.context}</td>
						<td>${vo.modify_time}</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div><!--main-->
</body>
</html>
