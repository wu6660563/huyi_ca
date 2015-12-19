<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>互易客服中心信息发布系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/reciprocity_admin/style/index.css" type="text/css" rel="stylesheet" />
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/jquery.js"></script>
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/my97/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/popup.js"></script>
<script type="text/javascript">
	$(function() {
		$("#importFileButton").click(function() {
			var fileName = $("#file").val();
			var importType = $("#importType").val();
			if(fileName == null || fileName == "") {
				 alert("你还没有选择文件!");
				 return;
			}
			var fileTemp = fileName.substring(fileName.lastIndexOf("."));
			if(fileTemp != ".xls" && fileTemp != ".xlsx") {
				alert("请选择EXCEL文件!");
				return;
			}
			$("#importFileButton").attr("disabled", "disabled");
			$("#fileUpload").submit();
		});
		
		//返回导入结果
		var result = "";
		result = "${result}";
		if(result != null && result != "") {
			alert(result);
		}
	});
	
	
	function search(){
		 $('#info_diss').submit();
	}
</script>
</head>

<body>
	<jsp:include page="header-include.jsp">
		<jsp:param name="currentClass" value="index"/>
	</jsp:include>

	<div id="main">
		
		<dl class="box">
			<dt class="icon_1"><span>信息导入</span></dt>
			<dd>
				<a href="javascript:void(0);" onclick="(EV_modeAlert1('uploadshowbox','EMPLOYEE'))">导入员工</a>
				<a href="javascript:void(0);" onclick="(EV_modeAlert1('uploadshowbox','CUSTOMERINFO'))">导入客户</a>
				<a href="javascript:void(0);" onclick="(EV_modeAlert1('uploadshowbox','CONTRACT'))">导入合同</a>
				<a href="javascript:void(0);" onclick="(EV_modeAlert1('uploadshowbox','ORDER'))">导入工单</a>
				<a href="javascript:void(0);" onclick="(EV_modeAlert1('uploadshowbox','PROCESS'))">导入服务进程</a>
				<!-- 
				<a href="javascript:void(0);" onclick="(EV_modeAlert1('uploadshowbox','QUESSTION'))">导入问题单</a>
				 -->
			</dd>
		</dl>

		<dl class="box">
			<dt class="icon_2"><span>信息列表</span></dt>
			<dd>
				<a href="${ctx}/customer.action?operType=list">客户信息列表</a>
				<a href="${ctx}/feedback.action?operType=list">服务进程信息列表</a>
				<a href="${ctx}/contract.action?operType=list">合同信息列表</a>
				<a href="${ctx}/workorder.action?operType=list">工单信息列表</a>
				<a href="${ctx}/producttype.action?operType=list">产品类型列表</a>
				<a href="${ctx}/home.action?operType=info_diss">业务资讯列表</a>
			</dd>
		</dl>
		
		<!-- 
		<dl class="box">
			<dt class="icon_2"><span>商务信息推送</span></dt>
			<dd>
				<a href="${ctx}/home.action?operType=info_diss">业务资讯推送列表</a>
				<a href="javascript:void(0);"  onclick="(EV_modeAlert('showbox'))">发送业绩播报</a>
			</dd>
		</dl> -->
		
		<!-- 
		<dl class="box">
			<dt class="icon_3"><span>推送管理</span></dt>
			<dd>
				<a href="${ctx}/home.action?operType=clicent_PushInfo">客户信息推送审核</a>
				<a href="${ctx}/home.action?operType=business_PushInfo">商务信息推送审核</a>
			</dd>
		</dl>
		 -->

		<dl class="box">
			<dt class="icon_4"><span>系统管理</span></dt>
			<dd>
				<a href="${ctx}/employee.action?operType=list">账户管理</a>
				<a href="${ctx}/home.action?operType=login_log">查看登录记录</a>
				<a href="${ctx}/reciprocity_admin/zhao-hui-mi-ma.jsp">找回密码</a>
				<a href="${ctx}/customer.action?operType=downloadTemplate">模板下载</a>
			</dd>
		</dl>

	</div><!--main-->
<form action="${ctx}/home.action" id="info_diss" method="post">
<input type="hidden" name="operType" value="add_PushInfo"/>
<div id="showbox">
	<h4>实时业绩播报<a class="close-btn" href="javascript:void(EV_closeAlert('showbox'))"></a></h4>
	<div class="showbox-m">
		<ul class="upload">
			<li><span class="upload-l">产品：</span><input type="text" name="product"/></li>
			<li><span class="upload-l">到账金额：</span><input type="text" name="toBooK" /></li>
			<li><span class="upload-l">签单人：</span><input type="text" name="sign_bill" /></li>
			<li><span class="upload-l">时间：</span><input type="text" name="sign_time"  onclick="WdatePicker()" /></li>
		</ul>
	</div>
	<div class="show-b"><a href="javascript:search();"  class="save-btn">实时广播</a></div>
</div>
</form>
<form action="${ctx}/fileupload.action" method="post" enctype="multipart/form-data" id="fileUpload" name="fileUpload">
	<div id="uploadshowbox" class="uploadshowbox">
		<h4><font id="importTitle">文件上传</font><a class="close-btn" href="javascript:void(EV_closeAlert('uploadshowbox'))"></a></h4>
		<div class="showbox-m">
			<ul class="upload">
				<li>
					<span class="upload-l">文件：</span><input type="file" id="file" name="file"/>
					<input type="hidden" id="importType" value="" name="importType"/>
				</li>
			</ul>
		</div>
		<div class="show-b"><a href="#" class="save-btn" id="importFileButton">上传</a></div>
	</div>
</form>
</body>
</html>
