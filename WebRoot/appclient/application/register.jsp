<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE HTML>
<html>
	<head>
	<%@ include file="../commons/global.jsp"%>
	<title>用户注册</title>
	<link rel="stylesheet" href="${ctx}/style/huyi.css" />
	<script src="${ctx}/script/zepto.min.js"></script>
</head>
<script type="text/javascript"> 
		$('.codeBtn').on('tap',function(){
			$(this).addClass('onBtn');
		});

		 $('.backBtn').on({
			touchstart:function(){
				$(this).addClass('current');
			},
			touchend:function(){
				$(this).removeClass('current');
			}
		 })
		function savead(){
			var company_name = $.trim($("#company_name").val());
			var business_contacts = $.trim($("#business_contacts").val());
			var phone = $.trim($("#phone").val());
			var password = $.trim($("#password").val());
			var passwordagn = $.trim($("#passwordagn").val());
			var regPartton=/1[3-8]+\d{9}/;
			if(company_name.length < 1){
				alert("请输入公司名称！");
				$("#company_name").focus();
				return ;  
			}
			if(company_name.length > 100){
				alert("字数过长，请重新输入公司名称！");
				$("#company_name").focus();
				return ;  
			}
			if(business_contacts.length < 1){
				alert("请输入联系人！");
				$("#business_contacts").focus();
				return;
			}
			if(company_name.length > 30){
				alert("字数过长，请重新输入联系人！");
				$("#business_contacts").focus();
				return ;  
			}
			if(phone.length < 1){
				alert("请输入电话！");
				$("#phone").focus();
				return;
			}
			if(phone.length > 20){
				alert("字数过长，请重新输入电话！");
				$("#phone").focus(); 
				return ;   
			}
			if(!regPartton.test(phone)){
				alert("输入格式不正确,请重新输入手机号！");
				$("#phone").focus();
				return ;
			}
			var chinese = /[\u4E00-\u9FA5]/g;
			if(password.length < 1){
				alert("请输入密码 ！");
				$("#password").focus();
				return;
			}else {
				if(password.length <8 || password.length>16){
					alert("密码长度必须是8-16位");
					$("#password").focus();
					return;
				}
				if(chinese.test(password) ){
					alert("密码不能含有中文");
					$("#password").focus(); 
					return;
				}
			}
			if(passwordagn.length < 1){
				alert("请输入确认密码 ！");
				$("#passwordagn").focus();
				return;
			}else {
				if(passwordagn.length <8 || passwordagn.length>16){
					alert("确认密码长度必须是8-16位");
					$("#passwordagn").focus(); 
					return;
				}
				if(chinese.test(password) ){
					alert("确认密码不能含有中文");
					$("#passwordagn").focus(); 
					return;
				}
			}
			if(password != passwordagn){
				alert("两次输入密码不一致，请重新输入密码！");
				$("#password").focus(); 
				return;
			}
			
			$.post("${ctx}/application", {
			method : "registerUser", 
			companyName : company_name,
			businessContacts : business_contacts,
			phone : phone,
			password : password
			},function (data) {
				if(data > 0){
					alert("注册成功！");
					 window.location.href = "${ctx}/application?method=index&userId="+data;  
				}else{
					alert("注册失败！");
					$("#company_name").focus(); 
				}
			});
		}
</script>
<body>
		<header id="header">
		<a class="backBtn" href=""></a>
		<h2 class="title">用户注册</h2>
	</header>
	<div id="main">
		<section class="edit-list">
			<ul>
				<li><label>公司名</label><div class="right-ipnut"><input type="text" placeholder="请输入公司名"  id="company_name"/></div></li>
				<li><label>联系人</label><div class="right-ipnut"><input type="text" placeholder="请输入联系人" id="business_contacts"/></div></li>
				<li><label>手机号</label><div class="right-ipnut"><input type="tel" placeholder="请输入手机号码" id="phone"/></div></li>
				<li><label>密码</label><div class="right-ipnut"><input type="password" placeholder="请输入密码" id="password"/></div></li>
				<li><label>确认密码</label><div class="right-ipnut"><input type="password" placeholder="请输入确认密码" id="passwordagn"/></div></li>
			</ul>			
		</section>
		<input type="button" class="saveBtn regBtn" onclick="savead()" value="注册"></input>
	</div>
</body>
</html>
