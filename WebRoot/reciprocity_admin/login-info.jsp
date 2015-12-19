<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>登录记录查看</title>
		<link href="${ctx}/reciprocity_admin/style/index.css" type="text/css"
			rel="stylesheet" />
		<script language="javascript" type="text/javascript"
			src="${ctx}/reciprocity_admin/js/jquery.js"></script>
		<script language="javascript" type="text/javascript"
			src="${ctx}/reciprocity_admin/js/my97/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="${ctx}/reciprocity_admin/js/popup.js"></script>
		<script type="text/javascript">
	function search(){
		 $('#info_diss').submit();
		}
</script>

	</head>
	<body>
		<jsp:include page="header-include.jsp">
			<jsp:param name="currentClass" value="index"/>
		</jsp:include>

		<div class="tablist">
			<a href="${ctx}/employee.action?operType=list">账户管理</a>
			<a href="${ctx}/home.action?operType=login_log" class="on">登录记录查看</a>
			<a href="${ctx}/reciprocity_admin/zhao-hui-mi-ma.jsp">找回密码</a>
		</div>
		<form action="${ctx}/home.action" id="info_diss" method="post">
			<input type="hidden" name="operType" value="login_log">
			<div id="main">
				<div class="custom">
					<h4 class="hd">
						<span class="title">登录信息列表</span>
					</h4>
					<div class="hm">
						<div class="search-box">
							<span>最后一次登录时间：</span>
							<input type="text" onclick="WdatePicker()" name="login_timeStart"
								value="${login_timeStart }" />
							至
							<input type="text" onclick="WdatePicker()"
								value="${login_timeEnd }" name="login_timeEnd" />
							<span>至同一不同账号登录次数：</span>
							<select name="login_number">
								<option value="1" ${login_number==1?"selected":""}>
									1次
								</option>
								<option value="2" ${login_number==2?"selected":""}>
									5次以下
								</option>
								<option value="3" ${login_number==3?"selected":""}>
									5次以上
								</option>
							</select>
							<span>最后一次登录用户：</span>
							<input type="text" name="user_name" value="${user_name }" />
							<a href="javascript:search();" class="search-btn">搜索</a>
						</div>

						<table class="table-list" width="100%">
							<tr class="top-tr">
								<th>
									设备号
								</th>
								<th>
									用户类别
								</th>
								<th>
									最后一次登录用户
								</th>
								<th>
									登录者ID号
								</th>
								<th>
									同一设备不同账号登录次数
								</th>
								
							</tr>
							<c:forEach items="${loginInfoList}" var="loginInfo">
								<tr>
									<td>
										${loginInfo.login_dev_id}
									</td>
									<td>
										<c:if test="${loginInfo.user_type==1}">
											客户
											</c:if>
										<c:if test="${loginInfo.user_type==2}">
											商务
											</c:if>
									</td>
									<td>
										${loginInfo.user_name}
									</td>
									<td>
										${loginInfo.user_id}
									</td>
									<td>
										${loginInfo.login_count}
									</td>
									
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
