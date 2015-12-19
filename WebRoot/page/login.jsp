<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/global.jsp"%>
<meta charset="utf-8">
<title>后台登陆</title>
<meta name="viewport" content="width=device-width">
<link href="${ctx}/reciprocity_admin/style/index.css" rel="stylesheet" type="text/css">
<script src="${ctx}/script/jquery.min.js"></script>
	<script type="text/javascript">
		var msg = "${error}";
		$(function() {
			if (msg == "true") {
				alert("用户名或密码错误!");
			}
		});
	</script>
</head>
<body class="login-bg">

<div class="login">
<form  method="post" id="form" action="${ctx}/login.action">
	<div class="logo"></div>
    <div class="login_form">
    	<div class="user clearfix">
        	<p><label>用户名：</label><input class="text_value"  name="username" type="text" id="username"></p>
            <p><label> 密  码：</label><input class="text_value" name="password" type="password" id="password"></p>
        </div>
        <button class="button" id="submit" type="submit">登录</button>
    </div>
    </form>
</div>

</body>
</html>