<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>找回密码</title>
		<link href="${ctx}/reciprocity_admin/style/index.css" type="text/css"
			rel="stylesheet" />
		<script language="javascript" type="text/javascript"
			src="${ctx}/reciprocity_admin/js/jquery.js"></script>
		<script language="javascript" type="text/javascript"
			src="${ctx}/reciprocity_admin/js/my97/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="${ctx}/reciprocity_admin/js/popup.js"></script>
		<script type="text/javascript">
	var msg = "${error}";
	$(function() {
		if (msg == "true") {
			alert("客户名或者合同号不对！");
		}
	});

	function search() {
		$('#info_diss').submit();
	}
</script>
	</head>
	<body>
		<jsp:include page="header-include.jsp">
			<jsp:param name="currentClass" value="employee"/>
		</jsp:include>
		<div class="tablist">
			<a href="${ctx}/employee.action?operType=list">账户管理</a>
			<a href="${ctx}/home.action?operType=login_log">登录记录查看</a>
			<a href="${ctx}/reciprocity_admin/zhao-hui-mi-ma.jsp" class="on">找回密码</a>
		</div>
		<form action="${ctx}/home.action" id="info_diss" method="post">
			<input type="hidden" name="operType" value="retrievePwd">
			<div id="main">
				<div class="custom">
					<h4 class="hd">
						<span class="title">找回密码</span>
					</h4>
					<div class="hm">
						<div class="forgot">
							<h3>
								找回密码
							</h3>
							<div class="forgot-m">
								<ul class="clearfix">
									<li>
										<label>
											客户名：
										</label>
										<input type="text" name="customerName"
											value="${customerName }" />
									</li>
									<li>
										<label>
											合同号：
										</label>
										<input type="text" name="contract_no" value="${contract_no}" />
									</li>
								</ul>
								<div class="forgot-b">
									<a href="javascript:search();" class="forgot-btn">查看</a>
								</div>
								<ul class="clearfix">
									<li>
										<label>
											密码：
										</label>
										<input type="text" name="customer_info_pswd"
											value="${customer_info_pswd }" />
									</li>
								</ul>
							</div>

						</div>
					</div>
				</div>

			</div>
		</form>
	</body>
</html>
