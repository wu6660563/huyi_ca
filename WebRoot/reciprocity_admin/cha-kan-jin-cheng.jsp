<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查看服务进程</title>
<link href="${ctx}/reciprocity_admin/style/index.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<jsp:include page="header-include.jsp">
		<jsp:param name="currentClass" value="feedback"/>
	</jsp:include>

	<div id="main">
		<div class="custom">
			<h4 class="hd"><span class="title">查看服务进程</span><div class="hd-r"><a href="javascript:history.back();" class="back-btn">返回</a></div></h4>
			<div class="hm">
				<div class="hetong">
					<h3 class="ht-tit clearfix"><span class="ht-ph">合同编号：<em>${vo.contract_no}</em></span></h3>
					<div class="ht-info">
						<p><span><em class="list-tit">客户名称：</em>${vo.customer_name}</span><span><em class="list-tit">工单ID：</em>${vo.work_order_id_src}</span></p>
						<p><span><em class="list-tit">产品服务类别：</em>${vo.type_name}</span><span><em class="list-tit">时间：</em>${fn:substring(vo.modify_time, 0, 10)}</span></p>
						<p><em class="list-tit">服务进程标题：</em>${vo.service_pro_name}</p>
						<p><em class="list-tit">状态：</em>${vo.status}</p>
						<p><em class="list-tit">服务进程内容：</em>${vo.context}</p>
					</div>					
				</div>
			</div>
		</div>
	</div><!--main-->
</body>
</html>
