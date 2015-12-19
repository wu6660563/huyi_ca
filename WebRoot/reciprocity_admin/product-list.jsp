<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品类型列表</title>
<link href="${ctx}/reciprocity_admin/style/index.css" type="text/css" rel="stylesheet" />
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/jquery.js"></script>
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/my97/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/popup.js"></script>
<script type="text/javascript">
	function search(indexPage){
		$("#pageIndex").val(indexPage);
		$('#form1').submit();
	}
	
</script>
<style>
	.yem ul li a.last-li:hover {color:#454545;text-decoration:none;}
</style>

</head>
<body>
	<jsp:include page="header-include.jsp">
		<jsp:param name="currentClass" value="producttype"/>
	</jsp:include>

	<form action="${ctx}/producttype.action?operType=list" id="form1" name="form1" method="post">
	<div id="main">
		<div class="custom">
			<h4 class="hd"><span class="title">产品类型列表</span></h4>
			<div class="hm">
				<div class="search-box">
					<span>类型名称</span><input type="text" name="type_name" value="${type_name}"/>			
					<a href="javascript:search(1);" class="search-btn">搜索</a>
				</div>
				<table class="table-list" width="100%">
					<tr class="top-tr">
						<th>类型名称</th>
						<th>服务进程个数</th>
						<th width="40%">操作</th>
					</tr>
					<c:forEach items="${list}" var="vo">
					<tr>
						<td>${vo.type_name}</td>
						<td>${vo.course_num}</td>
						<td><div class="editBtn"><a href="${ctx}/producttype.action?operType=toUpdate&id=${vo.product_type_id}" class="edit-btn">修改</a><a href="${ctx}/reciprocity_admin/add-product.jsp" class="add-btn">添加</a><a href="${ctx}/producttype.action?operType=delete&id=${vo.product_type_id}" class="add-btn">删除</a></div></td>
					</tr>
					</c:forEach>
				</table>
				
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
				</div><!--fanye-->
			</div>
		</div>
	</div><!--main-->
	</form>
</body>
</html>
