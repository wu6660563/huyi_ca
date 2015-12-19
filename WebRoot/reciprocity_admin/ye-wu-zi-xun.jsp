<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>业务咨询</title>
		<link href="${ctx}/reciprocity_admin/style/index.css" type="text/css"
			rel="stylesheet" />
		<script language="javascript" type="text/javascript"
			src="${ctx}/reciprocity_admin/js/jquery.js"></script>
		<script language="javascript" type="text/javascript"
			src="${ctx}/reciprocity_admin/js/my97/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="${ctx}/reciprocity_admin/js/popup.js"></script>
		<script type="text/javascript">
	function search(){
		 $('#info_diss').submit();
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
				}else{
					//strs=msg.toString().split(":");
					alert("业务资讯【(成功:失败) "+msg+"】条");
				}
		}
	});
	
</script>

	</head>
	<body>
		<jsp:include page="header-include.jsp">
			<jsp:param name="currentClass" value="info_diss"/>
		</jsp:include>
		<form action="${ctx}/home.action" id="info_diss" method="post">
			<input type="hidden" name="operType" value="info_diss">
			<div id="main">
				<div class="custom">
					<h4 class="hd">
						<span class="title">业务资讯列表</span>
						<div class="hd-r">
							<a href="${ctx}/reciprocity_admin/add-ye-wu-zi-xun.jsp" class="">发布新资讯</a>
						</div>
					</h4>
					<div class="hm">
						<div class="search-box">
							<span>资讯类别：</span>
							<select name="ann_type">
								<option value="" ${ann_type ==""?"selected":""}>
									全部
								</option>
								<option value="1" ${ann_type ==1 ?"selected":""}>
									奖励政策
								</option>
								<option value="2" ${ann_type ==2 ?"selected":""}>
									促销政策
								</option>
								<option value="3" ${ann_type ==3 ?"selected":""}>
									社区公告
								</option>
								<option value="4" ${ann_type ==4 ?"selected":""}>
									重要通知
								</option>
							</select>
							<span>编辑时间：</span>
							<input type="text" onclick="WdatePicker()"
								name="modify_timeStart" value="${modify_timeStart }" />
							至
							<input type="text" onclick="WdatePicker()" name="modify_timeEnd"
								value="${modify_timeEnd}" />
							<span>推送状态：</span>
							<select name="push_status" >
								<option value="0" ${push_status ==0 ?"selected":""}>
									未推送
								</option>
								<option value="1" ${push_status ==1 ?"selected":""}>
									已推送
								</option>
								<option value="2" ${push_status ==2 ?"selected":""}>
									推送不成功
								</option>
							</select>
							<a href="javascript:search();" class="search-btn">搜索</a>
						</div>
					</div>
				<table class="table-list" width="100%">
			<tr class="top-tr">
				<th>
					序号
				</th>
				<th>
					标题
				</th>
				<th>
					摘要
				</th>
				<th>
					资讯类别
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
			<c:forEach items="${infoDissList}" var="infoDiss">
				<tr>
					<td>
						${infoDiss.info_id}
					</td>
					<td>
						${infoDiss.title}
					</td>
					<td>
						${infoDiss.summary}
					</td>
					<td>
						<c:if test="${infoDiss.ann_type==1}">
							奖励政策
						</c:if>
						<c:if test="${infoDiss.ann_type==2}">
							促销政策
						</c:if>
						<c:if test="${infoDiss.ann_type==3}">
							社区公告
						</c:if>
						<c:if test="${infoDiss.ann_type==4}">
							重要通知
						</c:if>
					</td>
					<td>
						${fn:substring(infoDiss.modify_time, 0, 10)}
					</td>
					<td>
						<c:if test="${infoDiss.push_status==0}">
									未推送
						</c:if>	
						<c:if test="${infoDiss.push_status==1}">
									已推送
						</c:if>	
						<c:if test="${infoDiss.push_status==2}">
									推送不成功
						</c:if>			
					</td>
					<td>
						<a
							href="${ctx}/home.action?operType=del_info_diss&info_id=${infoDiss.info_id}"
							class="text-blue">删除</a>
						<a
							href="${ctx}/home.action?operType=find_info_diss&info_id=${infoDiss.info_id}"
							class="text-blue">修改</a>
						<a href="#" class="text-blue">推送</a>
<!--						<a href="#" class="text-blue">预览</a>-->
					</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		</div>
			
		</form>
		

	</body>
</html>
