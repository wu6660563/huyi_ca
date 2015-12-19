<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加服务进程</title>
<link href="${ctx}/reciprocity_admin/style/index.css" type="text/css"
			rel="stylesheet" />
<script language="javascript" type="text/javascript"
	src="${ctx}/reciprocity_admin/js/jquery.js"></script>
<script language="javascript" type="text/javascript"
	src="${ctx}/reciprocity_admin/js/my97/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"
	src="${ctx}/reciprocity_admin/js/popup.js"></script>
<script language="javascript" type="text/javascript">
	$(function() {
		$("#saveBtn").click(function() {
			
			//保存之前先验证
			var service_pro_name = $("#service_pro_name").val();
			var status = $("#status").val();
			
			if(service_pro_name == null || service_pro_name == '') {
				alert("服务进程标题不能为空");
				return;
			}
			if(status == null || status == '') {
				alert("请先去完善产品类别状态！");
				return;
			}
			//验证状态是否存在
			var work_order_id = $("#work_order_id").val();
			var data = "work_order_id="+work_order_id+"&status="+status;
			$.ajax({
   				type: "POST",
   				url: "${ctx}/feedback.action?operType=checkStatus",
   				data: data,
   				dataType: "text",
   				success: function(msg){
   					if(msg == 'true') {
   						//存在
   						alert("该工单已经存在这个服务进程！");
   					} else {
   						$("#form1").submit();
   					}
   				}
			});
			
			
		});
	});
</script>
</head>
<body>
	<jsp:include page="header-include.jsp">
		<jsp:param name="currentClass" value="workorder"/>
	</jsp:include>

	<form action="${ctx}/feedback.action?operType=saveOrUpdate" method="post" name="form1" id="form1">
	<input type="hidden"" name="work_order_id" id="work_order_id" value="${vo.work_order_id}"/>
	<input type="hidden"" name="feedback_id" id="feedback_id" value="${vo.feedback_id}"/>
	<input type="hidden"" name="actionType" id="actionType" value="${actionType}"/>
	<div id="main">
		<div class="custom">
			<h4 class="hd"><span class="title">服务进程</span><div class="hd-r"><a href="javascript:history.back();" class="back-btn">返回</a></div></h4>
			<div class="hm">
				<div class="hetong">
					<h3 class="ht-tit clearfix"><span class="ht-ph">合同编号：<em>${vo.contract_no}</em></span></h3>
					<div class="ht-info">	
						<p><span><em class="list-tit">客户名称：</em>${vo.customer_name}</span><span><em class="list-tit">工单ID：</em>${vo.work_order_id_src}</span></p>
						<p><span><em class="list-tit">产品服务类别：</em>${vo.type_name}</span><span><em class="list-tit">时间：</em><input type="text" class="ht-date" name="create_time" onclick="WdatePicker()" value="${fn:substring(vo.create_time, 0, 10)}"/></span></p>
						<p><em class="list-tit">服务进程标题：</em><input type="text" class="ht-date" id="service_pro_name" name="service_pro_name" value="${vo.service_pro_name}"/></p>
						<p>
							<em class="list-tit">状态：</em>
							<select name="status" id="status">
								<c:forEach var="status" items="${statusList}">
								<c:choose>
									<c:when test="${status.feedback_status_id eq vo.feedback_status_id}">
									<option value="${status.feedback_status_id}" selected="selected">${status.status_name}</option>
									</c:when>
									<c:otherwise>
									<option value="${status.feedback_status_id}">${status.status_name}</option>
									</c:otherwise>
								</c:choose>
								</c:forEach>
							</select>
						</p>
						<p>
							<em class="list-tit">服务进程内容：</em>
							<textarea class="jc-txt" placeholder="请输入内容..." name="context" id="context">${vo.context}</textarea>
						</p>
					</div>
					<div class="textcenter f-btn"><a class="b-cannel" href="javascript:history.back();">取消</a><a class="b-save" id="saveBtn">保存</a></div>					
				</div>
			</div>
		</div>
	</div><!--main-->
	</form>
</body>
</html>
