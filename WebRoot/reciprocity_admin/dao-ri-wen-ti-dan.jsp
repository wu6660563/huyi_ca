<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>导入问题单</title>
<link href="${ctx}/reciprocity_admin/style/index.css" type="text/css" rel="stylesheet" />
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/jquery.js"></script>
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/my97/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/popup.js"></script>
<script type="text/javascript">
	$(function() {
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
		<jsp:param name="currentClass" value="index"/>
	</jsp:include>

	<form action="${ctx}/question.action?operType=list" method="post" name="form1" id="form1">
	<div id="main">
		<div class="custom">
			<h4 class="hd"><span class="title">导入问题单</span><div class="hd-r"><a href="javascript:history.back();" class="back-btn">返回</a></div></h4>
			<div class="hm">
				<table class="table-list" width="100%">            
					<tr class="top-tr">                     
						<th>合同号</th>
						<th>工单细则id</th>
						<th>问题类型</th>
						<th>描述</th>
						<th>弹单人</th>
						<th>部门</th>
						<th>时间</th>
					</tr>
					<c:forEach var="vo" items="${list}">
					<tr>
						<td>${vo.contract_no}</td>
						<td>${vo.work_order_id_src}</td>
						<td>${vo.question_type}</td>
						<td>${vo.context}</td>
						<td>${vo.single_person}</td>
						<td>${vo.department}</td>
						<td>${vo.create_time}</td>
					</tr>
					</c:forEach>
				</table>
			</div>
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
	</div><!--main-->
	</form>
</body>
</html>
