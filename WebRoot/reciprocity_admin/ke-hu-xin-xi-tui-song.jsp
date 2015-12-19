<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>客户信息推送列表</title>
		<link href="${ctx}/reciprocity_admin/style/index.css" type="text/css"
			rel="stylesheet" />
		<script language="javascript" type="text/javascript"
			src="${ctx}/reciprocity_admin/js/jquery.js"></script>
		<script language="javascript" type="text/javascript"
			src="${ctx}/reciprocity_admin/js/my97/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="${ctx}/reciprocity_admin/js/popup.js"></script>
		<script type="text/javascript">
	function search(indexPage){
		//alert(indexPage);
		//var pageCount=$("#pageCount").val();
		//alert(pageCount);
		//if(indexPage>pageCount){
		//	alert("已是尾页!")
		//}else if(indexPage<1||indexPage==null){
		///	alert("已是首页!")
		//}else{
			 $("#pageIndex").val(indexPage);
			 $('#info_diss').submit();
		//}
	}
	var msg = "${msg}";
	$(function() {
		if(msg!=""){
			    if(msg=="add_true"){
				     alert("添加成功!");
				}else if(msg=="add_false"){
					 alert("添加失败!");
				}else if(msg=="update_true"){
					 alert("修改成功!");
				}else if(msg=="update_false"){
					 alert("修改失败!");
				}else if(msg=="del_true"){
					 alert("删除成功!");
				}else if(msg=="del_false"){
					 alert("删除失败!");
				}
		}
	});
</script>

	</head>
	<body>
		<jsp:include page="header-include.jsp">
			<jsp:param name="currentClass" value="push_info"/>
		</jsp:include>

		<div class="tablist">
			<a href="${ctx}/home.action?operType=clicent_PushInfo" class="on">客户信息推送审核</a>
			<a href="${ctx}/home.action?operType=business_PushInfo">商务信息推送审核</a>
		</div>
		<form action="${ctx}/home.action" id="info_diss" method="post">
			<input type="hidden" name="operType" value="clicent_PushInfo">
			<div id="main">
				<div class="custom">
					<h4 class="hd">
						<span class="title">客户信息推送列表</span>
					</h4>
					<div class="hm">
						<div class="search-box">
							<span>客户名：</span>
							<input type="text" name="customerName" value="${customerName }" />
							<span>合同号：</span>
							<input type="text" name="contract_no" value="${contract_no }" />
							<span>信息类别：</span>
							<select name="info_type">
								<option value="" ${info_type==""?"selected":""}>
									请选择
								</option>
								<option value="1" ${info_type==1?"selected":""}>
									服务进程
								</option>
								<option value="2" ${info_type==2?"selected":""}>
									促销政策
								</option>
								<option value="3" ${info_type==3?"selected":""}>
									创建合同
								</option>
								<option value="4" ${info_type==4?"selected":""}>
									问题单
								</option>
								<option value="5" ${info_type==5?"selected":""}>
									奖励政策
								</option>
								<option value="6" ${info_type==6?"selected":""}>
									社区公告
								</option>
								<option value="7" ${info_type==7?"selected":""}>
									重要通知
								</option>
								<option value="8" ${info_type==8?"selected":""}>
									业绩播报
								</option>
								<option value="9" ${info_type==9?"selected":""}>
									续费提醒
								</option>
							</select>
							<span>编辑时间：</span>
							<input type="text" onclick="WdatePicker()" name="edit_timeStart"
								value="${edit_timeStart }" />
							至
							<input type="text" onclick="WdatePicker()" name="edit_timeEnd"
								value="${edit_timeEnd }" />
							<br />
							<br />
							<span>推送状态：</span>
							<select name="push_status">
								<option value="" ${push_status==""?"selected":""}>
									请选择
								</option>
								<option value="0" ${push_status==0?"selected":""}>
									未推送
								</option>
								<option value="1" ${push_status==1?"selected":""}>
									已推送
								</option>
								<option value="2" ${push_status==2?"selected":""}>
									推送不成功
								</option>
							</select>
							<a href="javascript:search(1);" class="search-btn">搜索</a>
						</div>
					</div>

					<table class="table-list" width="100%">
						<tr class="top-tr">
							<th>
								推送内容
							</th>
							<th>
								信息类别
							</th>
							<th>
								编辑时间
							</th>
							<th>
								推送状态
							</th>
							<th>
								操作
							</th>
						</tr>
						<c:forEach items="${pushInfoList}" var="pushInfo">
							<tr>
								<td>
									${pushInfo.push_context}
								</td>
								<td>
									<c:if test="${pushInfo.info_type==1}">
						      服务进程
						</c:if>
									<c:if test="${pushInfo.info_type==2}">
						      促销政策
						</c:if>
									<c:if test="${pushInfo.info_type==3}">
						      创建合同
						</c:if>
									<c:if test="${pushInfo.info_type==4}">
						     问题单
						</c:if>
									<c:if test="${pushInfo.info_type==5}">
						     奖励政策
						</c:if>
									<c:if test="${pushInfo.info_type==6}">
						   社区公告
						</c:if>
									<c:if test="${pushInfo.info_type==7}">
						重要通知
						</c:if>
									<c:if test="${pushInfo.info_type==8}">
						  业绩播报
						</c:if>
									<c:if test="${pushInfo.info_type==9}">
						 续费提醒
						</c:if>
								</td>
								<td>
									${pushInfo.edit_time}
								</td>
								<td>
									<c:if test="${pushInfo.push_status==0}">
						  未推送
						</c:if>
									<c:if test="${pushInfo.push_status==1}">
						   已推送
						</c:if>
									<c:if test="${pushInfo.push_status==2}">
						   推送不成功
						</c:if>
								</td>
								<td>
									<a
										href="${ctx}/home.action?operType=delete_clicent_PushInfo&recipient_type=1&push_info_id=${pushInfo.push_info_id}"
										class="text-blue">删除</a>
									<a href="#" class="text-blue">重新推送</a>
									<a href="#" class="text-blue">预览</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="fanye">
				<div class="yem">
					<input type="hidden" name="pageIndex" id="pageIndex" value="">
				  	<ul>
						<li><a href="javascript:search(1);">首页</a></li>
						<li><a href="javascript:search(${page.previousPage == 0 ? page.startIndex : page.previousPage});">上一页</a></li>
						<c:forEach var="i" begin="1" end="${page.pageCount}">
							<c:choose>
								<c:when test="${page.currentPage == i}">
									<li class="sellify"><a href="javascript:search(${i});" id="currentPageButton">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="javascript:search(${i});">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<li><a href="javascript:search(${page.currentPage == page.endIndex ? page.endIndex : page.nextPage});">下一页</a></li>
						<li><a href="javascript:search(${page.pageCount});">尾页</a></li>
				  	</ul>
				</div>
		 	</div>
		</div>
		</form>
	</body>
</html>
