<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工单详情</title>
<link href="${ctx}/reciprocity_admin/style/index.css" type="text/css" rel="stylesheet" />
<script language="javascript" type="text/javascript"  src="js/jquery.js"></script>
</head>
<body>
	<jsp:include page="header-include.jsp">
		<jsp:param name="currentClass" value="workorder"/>
	</jsp:include>

	<div id="main">
		<div class="custom">
			<h4 class="hd"><span class="title">工单详情</span><div class="hd-r"><a href="javascript:history.back();" class="back-btn">返回</a></div></h4>
			<div class="hm">
				<div class="hetong">
					<h3 class="ht-tit clearfix"><span class="ht-ph">合同编号：<em>${vo.contract_no}</em></span></h3>
					<div class="ht-info">
						<p><span><em class="list-tit">客户名称：</em>${vo.customer_name}</span><span><em class="list-tit">工单ID：</em>${vo.work_order_id_src}</span></p>
						<p><span><em class="list-tit">产品服务类别：</em>${vo.type_name}</span><span><em class="list-tit">金额：</em>${vo.amount} RMB</span></p>
						<p><span><em class="list-tit">服务年限：</em>${vo.years} 年</span><span><em class="list-tit">到期时间：</em>${fn:substring(vo.expiry_date, 0, 10)}</span></p>
						<p><em class="list-tit">购买项目内容：</em>${vo.buy_items}</p>
					</div>
					<div class="ht-table">
						<p class="editBtn">
							<a href="${ctx}/feedback.action?operType=toAdd&work_order_id=${vo.work_order_id}" class="add-btn">添加服务进程</a>
						</p>
					</div>
					<div class="ht-table">
						<table class="table-list" width="100%">
							<tr class="top-tr">
								<th>服务进程名称</th>
								<th>状态</th>
								<th>时间</th>
							</tr>
							<c:forEach items="${feedbackList}" var="feedback">
							<tr>
								<td>${feedback.service_pro_name}</td>
								<td>${feedback.status_name}</td>
								<td>${fn:substring(feedback.modify_time, 0, 10)}</td>
							</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div><!--main-->

</body>
</html>
