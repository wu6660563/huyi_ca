<%@ page contentType="text/html;charset=UTF-8"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
%>
<script type="text/javascript" language="javascript">
	var ctx = "${ctx}"; //全局上下文路径JS变量
	var pageSize=6; //全局每页记录数(Global Page Size)JS变量
	var serverDomain = "http://113.108.232.135:36963";
</script>