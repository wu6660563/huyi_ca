<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户列表</title>
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
	
	function query() {
		var cust_service_name = $("#cust_service_name").val();
		var start_create_time = $("#start_create_time").val();
		var end_create_time = $("#end_create_time").val();
		var customer_name = $("#customer_name").val();
		if(cust_service_name == "" && start_create_time == "" && end_create_time == "" && customer_name == "") {
			alert("您未选择搜索条件！");
			return;
		}
		var startdate = new Date(($("#start_create_time").val()).replace(/-/g,"/"));
		var enddate = new Date(($("#end_create_time").val()).replace(/-/g,"/"));
		if(startdate > enddate) {
			alert("开始日期不能大于结束日期");
			return;
		}
		$("#form1").submit();
	}
	
	//弹出对话窗口(msgID-要显示的div的id) wupinlong modify
	function toUdateCustService(msgID, customer_info_id, customer_rating_id, cust_service_id, cust_service_name) {  
	    //创建大大的背景框  
	    var bgObj=document.createElement("div");  
	    bgObj.setAttribute('id','EV_bgModeAlertDiv');  
	   	document.body.appendChild(bgObj);  
	    //背景框满窗口显示  
	    EV_Show_bgDiv();  
	   	//把要显示的div居中显示  
	   	EV_MsgBox_ID=msgID;  
	   	EV_Show_msgDiv();
	   	
	   	//设置值
	   	$('#customer_info_id_temp').val(customer_info_id);
	   	if(customer_rating_id != null && customer_rating_id != '') {
	   		$('#customer_rating_id').val(customer_rating_id);
	   	}
	   	if(cust_service_id != null && cust_service_id != '') {
	   		var cust_service = cust_service_id + "," + cust_service_name;
	   		$('#cust_service_id_temp').val(cust_service);
	   	}
	}
	
	function updateCustService() {
		$("#editEmpForm").submit();
	}
	
	function search(indexPage){
		$("#pageIndex").val(indexPage);
		$('#form1').submit();
	}
	
	function del(customer_info_id) {
		if(confirm("你确定要删除吗？")) {
			window.location.href = "${ctx}/customer.action?operType=delete&id="+customer_info_id;
		}
	}

</script>
<style>
	/** wupinlong add for show page **/
	.yem ul li a.last-li:hover {color:#454545;text-decoration:none;}
</style>
</head>
<body>
	<jsp:include page="header-include.jsp">
		<jsp:param name="currentClass" value="customer"/>
	</jsp:include>
	
	<form action="${ctx}/customer.action?operType=list" id="form1" name="form1" method="post">
	<div id="main">
		<div class="custom">
			<h4 class="hd"><span class="title">客户列表</span><div class="hd-r"><a href="javascript:void(0);" onClick="(EV_modeAlert1('uploadshowbox','CONTRACT'))" class="">导入合同</a><a href="javascript:void(0);" onClick="(EV_modeAlert1('uploadshowbox','ORDER'))" class="">导入工单</a><a href="javascript:void(0);" onClick="(EV_modeAlert1('uploadshowbox','PROCESS'))" class="">导入服务进程</a></div></h4>
			<div class="hm">
				<div class="search-box">
					<!-- 
					<span>合同编号：</span><input type="text" id="contract_no" name="contract_no"/> -->
					<span>客户名：</span><input type="text" name="customer_name" id="customer_name" value="${customer_name}"/>
					<span>跟进客服：</span><input type="text" id="cust_service_name" name="cust_service_name" value="${cust_service_name}"/>
					<span>导入时间 开始时间：</span><input type="text"  onclick="WdatePicker()" name="start_create_time" id="start_create_time" value="${fn:substring(start_create_time, 0, 10)}"/>
					<span>结束时间：</span><input type="text"  onclick="WdatePicker()" name="end_create_time" id="end_create_time" value="${fn:substring(end_create_time, 0, 10)}"/>
					<a href="javascript:void(0);" onclick="query()" class="search-btn">搜索</a>
				</div>
				<table class="table-list" width="100%">
					<tr class="top-tr">
						<th>导入时间</th>
						<th>客户名</th>
						<!-- 
						<th>服务等级</th> -->
						<th>业务联系人</th>
						<th>联系电话</th>
						<th>公司地址</th>
						<th>主营产品</th>
						<!-- 
						<th>简介</th> -->
						<th>专属客服</th>
						<th>操作</th>
					</tr>
					<c:forEach var="vo" items="${list}">
					<tr>
						<td>${fn:substring(vo.create_time, 0, 10)}</td>
						<td>${vo.customer_name}</td>
						<!-- 
						<td>${vo.level_name}</td> -->
						<td>${vo.business_contacts}</td>
						<td>${vo.phone}</td>
						<td>${vo.adress}</td>
						<td>${vo.main_products}</td>
						<!-- 
						<td>${vo.introduction}</td> -->
						<td>${vo.cust_service_name}</td>
						<td><a href="javascript:void(0);" onclick="(del('${vo.customer_info_id}'))" class="text-blue">删除</a> <a href="javascript:void(0);" class="text-blue" onClick="(toUdateCustService('showbox','${vo.customer_info_id}','${vo.customer_rating_id}','${vo.cust_service_id}','${vo.cust_service_name}'))">修改客服</a> <a href="${ctx}/customer.action?operType=contractDetail&id=${vo.customer_info_id}" class="text-blue">详情</a></td>
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
	
	<form action="${ctx}/customer.action?operType=editCustService" method="post" name="editEmpForm" id="editEmpForm">
		<div id="showbox">
			<input type="hidden" name="customer_info_id_temp" id="customer_info_id_temp"/>
			<h4>修改客服<a class="close-btn" href="javascript:void(EV_closeAlert('showbox'))"></a></h4>
			<div class="showbox-m">
				<ul class="upload">
					<li><span class="upload-l">服务等级</span>
						<select name="customer_rating_id" id="customer_rating_id">
							<c:forEach items="${ratings}" var="rating">
								<option value="${rating.customer_rating_id}">${rating.level_name}</option>
							</c:forEach>
						</select>
					</li>
					<li><span class="upload-l">跟进客服</span>
						<!-- <input type="text" id="cust_service_name_temp" name="cust_service_name_temp" value=""/><input type="hidden" id="cust_service_id_temp" name="cust_service_id_temp" value=""/> -->
						<select name="cust_service_id_temp" id="cust_service_id_temp">
							<c:forEach items="${employees}" var="emp">
								<option value="${emp.employees_id},${emp.employees_name}">${emp.employees_name}</option>
							</c:forEach>
						</select>
					</li>
				</ul>
			</div>
			<div class="show-b"><a href="javascript:void(EV_closeAlert('showbox'))" class="cannel-btn">取消</a><a href="javascript:void(0);" onclick="updateCustService();" class="save-btn">保存</a></div>
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
