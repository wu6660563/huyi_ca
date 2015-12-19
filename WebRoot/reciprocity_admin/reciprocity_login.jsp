<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8"/>
<title>后台登陆</title>
<meta name="viewport" content="width=device-width"/>
<link href="style/index.css" rel="stylesheet" type="text/css"/>
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
<form  method="post" id="form" action="${ctx}/admin_login.action">
	<div class="logo"></div>
    <div class="login_form">
    	<div class="user clearfix">
        	<p><label>用户名：</label><input class="text_value" value="" name="username" type="text" id="username"></p>
            <p><label> 密  码：</label><input class="text_value" value="" name="password" type="password" id="password"></p>
        </div>
        <button class="button" id="submit" type="submit">登录</button>
    </div>
    </form>
</div>

</body>
</html>