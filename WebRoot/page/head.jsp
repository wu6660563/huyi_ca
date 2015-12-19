<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="admin-header">
	<div class="header clearfix w">
		<div class="logo left"><img src="img/logo.png" /></div>
		<div class="nav right">
			<span class="edit-user">${userinfo.login_name }</span>
			<a href="${ctx}/login.action?operType=logout" class="exit-btn">退出</a>
		</div>
	</div>
</div><!--admin-header-->