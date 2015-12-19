<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>账户管理</title>
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
		
		//导入数据条数
		var resultNum = "";
		resultNum = "${resultNum}";
		if(resultNum != null && resultNum != "") {
			alert("成功导入" + resultNum + "条数据");
		}
	});

	
	function search(indexPage){
		$("#pageIndex").val(indexPage);
		$('#form1').submit();
	}
	
</script>
<style>
	/** wupinlong add for show page **/
	.yem ul li a.last-li:hover {color:#454545;text-decoration:none;}
</style>
</head>
<body>
	<jsp:include page="header-include.jsp">
		<jsp:param name="currentClass" value="employee"/>
	</jsp:include>

	<div class="tablist">
		<a href="${ctx}/employee.action?operType=list" class="on">账户管理</a>
		<a href="${ctx}/home.action?operType=login_log">登录记录查看</a>
		<a href="${ctx}/reciprocity_admin/zhao-hui-mi-ma.jsp">找回密码</a>
	</div>

	<form action="${ctx}/employee.action?operType=list" id="form1" name="form1" method="post">
	<div id="main">
		<div class="custom">
			<h4 class="hd"><span class="title">账户管理列表</span><div class="hd-r"><a href="javascript:void(0);" onClick="(EV_modeAlert1('uploadshowbox','EMPLOYEE'))">导入用户信息</a></div></h4>
			<div class="hm">
				<table class="table-list" width="100%">
					<tr class="top-tr">
						<th>创建时间</th>
						<th>工号</th>
						<th>姓名</th>
						<th>分公司</th>
						<th>部门</th>
						<th>职位</th>
						<th>手机号</th>
						<th>角色权限</th>
						<th>入离职状态</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${list}" var="employees">
					<tr>
						<td>${fn:substring(employees.create_time, 0, 10)}</td>
						<td>${employees.work_no}</td>
						<td>${employees.employees_name}</td>
						<td>${employees.branch}</td>
						<td>${employees.department}</td>
						<td>${employees.job_title}</td>
						<td>${employees.mobile_phone}</td>
						<td>
						<c:if test="${employees.login_role==1}">
						  商务
						</c:if>
						<c:if test="${employees.login_role==2}">
						  客服
						</c:if>
						</td>
						<td>
						<c:if test="${employees.job_status==1}">
						 在职
						</c:if>
						<c:if test="${employees.job_status==0}">
						 离职
						</c:if>
						</td>
						<td>
						<c:if test="${employees.job_status==1}">
							<a href="${ctx}/home.action?operType=updateAccout&employees_id=${employees.employees_id}" class="text-blue">离职，关闭权限</a>
						</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
			</div>
			<div class="fanye">
				<div class="yem">
					<input type="hidden" name="pageIndex" id="pageIndex" value=""/>
				  	<ul>
						<li><a href="javascript:search(1);">首页</a></li>
						<li><a href="javascript:search(${page.previousPage == 0 ? page.startIndex : page.previousPage});">上一页</a></li>
						<c:if test="${page.pageCount <= 3}">
							<c:forEach var="i" begin="${page.startIndex}" end="${page.pageCount}">
								<c:choose>
									<c:when test="${page.currentPage == i}">
										<li class="sellify"><a href="javascript:search(${i});" id="currentPageButton">${i}</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="javascript:search(${i});">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
						<c:if test="${page.pageCount > 3}">
							<c:choose>
								<c:when test="${page.currentPage - 2 <= page.startIndex && page.currentPage + 2 <= page.pageCount}">
									<c:forEach var="i" begin="${page.startIndex}" end="${page.currentPage + 2}">
										<c:choose>
											<c:when test="${page.currentPage == i}">
												<li class="sellify"><a href="javascript:search(${i});" id="currentPageButton">${i}</a></li>
											</c:when>
											<c:otherwise>
												<li><a href="javascript:search(${i});">${i}</a></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<li>...</li>
								</c:when>
								<c:when test="${page.currentPage - 2 > page.startIndex && page.currentPage + 2 >= page.pageCount}">
									<li>...</li>
									<c:forEach var="i" begin="${page.currentPage - 2}" end="${page.pageCount}">
										<c:choose>
											<c:when test="${page.currentPage == i}">
												<li class="sellify"><a href="javascript:search(${i});" id="currentPageButton">${i}</a></li>
											</c:when>
											<c:otherwise>
												<li><a href="javascript:search(${i});">${i}</a></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
								<c:when test="${page.currentPage - 2 <= page.startIndex && page.currentPage + 2 >= page.pageCount}">
									<c:forEach var="i" begin="${page.startIndex}" end="${page.pageCount}">
										<c:choose>
											<c:when test="${page.currentPage == i}">
												<li class="sellify"><a href="javascript:search(${i});" id="currentPageButton">${i}</a></li>
											</c:when>
											<c:otherwise>
												<li><a href="javascript:search(${i});">${i}</a></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
								<c:when test="${page.currentPage - 2 > page.startIndex && page.currentPage + 2 <= page.pageCount}">
									<li>...</li>
									<c:forEach var="i" begin="${page.currentPage - 2}" end="${page.currentPage + 2}">
										<c:choose>
											<c:when test="${page.currentPage == i}">
												<li class="sellify"><a href="javascript:search(${i});" id="currentPageButton">${i}</a></li>
											</c:when>
											<c:otherwise>
												<li><a href="javascript:search(${i});">${i}</a></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<li>...</li>
								</c:when>
							</c:choose>
						</c:if>
						<li><a href="javascript:search(${page.currentPage == page.endIndex ? page.endIndex : page.nextPage});">下一页</a></li>
						<li><a href="javascript:search(${page.pageCount});">尾页</a></li>
						<li><a class="last-li">共${page.pageCount}页</a></li>
						<li><a class="last-li">共${page.rowCount}条</a></li>
				  	</ul>
				</div>
		 	</div>
		</div>
	</div><!--main-->
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
