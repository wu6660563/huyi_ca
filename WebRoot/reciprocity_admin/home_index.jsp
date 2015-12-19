<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="../commons/global.jsp"%>
<title>互易客服中心信息发布系统</title>
<link href="style/index.css" type="text/css" rel="stylesheet" />
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/jquery.js"></script>
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/my97/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/popup.js"></script>
</head>
<body>
	<jsp:include page="header-include.jsp">
		<jsp:param name="currentClass" value="index"/>
	</jsp:include>

	<div id="main">
		<dl class="box">
			<dt class="icon_1"><span>合同信息推送</span></dt>
			<dd>				
				<a href="ke-hu-list.html">客户信息列表</a>
				<a href="dao-ri-xin-xi.html">导入客户</a>
				<a href="dao-ri-he-tong.html">导入合同</a>
				<a href="dao-ri-gong-dan.html">导入工单</a>
				<a href="dao-ri-fu-wu-jin-cheng.html">导入服务进程</a>
				<a href="dao-ri-wen-ti-dan.html">导入问题单</a>
			</dd>
		</dl>

		<dl class="box">
			<dt class="icon_2"><span>商务信息推送</span></dt>
			<dd>
				<a href="ye-wu-zi-xun.html">业务资讯推送列表</a>
				<a href="javascript:void(0);"  onClick="(EV_modeAlert('showbox'))">发送业绩播报</a>				
			</dd>
		</dl>

		<dl class="box">
			<dt class="icon_3"><span>推送管理</span></dt>
			<dd>
				<a href="ke-hu-xin-xi-tui-song.html">客户信息推送审核</a>
				<a href="shang-wu-xin-xi-tui-song.html">商务信息推送审核</a>
			</dd>
		</dl>

		<dl class="box">
			<dt class="icon_4"><span>系统管理</span></dt>
			<dd>
				<a href="zhang-hu-guan-li.html">账户管理</a>
				<a href="login-info.html">查看登录记录</a>
				<a href="zhao-hui-mi-ma.html">找回密码</a>
			</dd>
		</dl>

	</div><!--main-->

<div id="showbox">
	<h4>实时业绩播报<a class="close-btn" href="javascript:void(EV_closeAlert('showbox'))"></a></h4>
	<div class="showbox-m">
		<ul class="upload">
			<li><span class="upload-l">产品：</span><input type="text" /></li>
			<li><span class="upload-l">到账金额：</span><input type="text" value="" /></li>
			<li><span class="upload-l">签单人：</span><input type="text" value="广州分公司  商务1部  张三" /></li>
			<li><span class="upload-l">时间：</span><input type="text"  onclick="WdatePicker()" /></li>
		</ul>
	</div>
	<div class="show-b"><a href="#" class="save-btn">实时广播</a></div>
</div>

</body>
</html>
