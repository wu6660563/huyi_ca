<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>导入工单</title>
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
	.yem ul li a.last-li:hover {color:#454545;text-decoration:none;}
</style>
</head>
<body>
	<jsp:include page="header-include.jsp">
		<jsp:param name="currentClass" value="workorder"/>
	</jsp:include>
	
	<form action="${ctx}/workorder.action?operType=list" method="post" name="form1" id="form1">
	<div id="main">
		<div class="custom">
			<h4 class="hd"><span class="title">工单列表</span><div class="hd-r"><a href="javascript:history.back();" class="back-btn">返回</a></div></h4>
			<div class="hm">
				<table class="table-list" width="100%">
					<div class="search-box">
						<span>客户名：</span><input type="text" name="customer_name" value="${customer_name}"/>
						<span>产品服务类别：</span>
						<select name="product_type_id">
							<option value="">所有</option>
							<c:forEach items="${productTypeList}" var="type">
							<option value="${type.product_type_id}">${type.type_name}</option>
							</c:forEach>
						</select>
						<span>工单ID：</span><input type="text" name="work_order_id_src" value="${work_order_id_src}"/>
						<a href="javascript:search(1);" class="search-btn">搜索</a>
					</div>      
					<tr class="top-tr">
						<th>客户名</th>          
						<th>合同号</th>
						<th>工单ID</th>
						<th>产品服务类别</th>
						<th>购买项目内容</th>
						<th>服务年限</th>
						<th>总金额</th>
						<th>到期时间</th>
						<th>操作</th>
					</tr>
					<c:forEach var="vo" items="${list}">
					<tr>
						<td>${vo.customer_name}</td>
						<td>${vo.contract_no}</td>
						<td>${vo.work_order_id_src}</td>
						<td>${vo.type_name}</td>
						<td>${vo.buy_items}</td>
						<td>${vo.years} 年</td>
						<td>${vo.amount} RMB</td>
						<td>${fn:substring(vo.expiry_date, 0, 10)}</td>
						<td><div class="editBtn"><a href="${ctx}/workorder.action?operType=showDetail&id=${vo.work_order_id}" class="look-btn">查看</a></div></td>
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
