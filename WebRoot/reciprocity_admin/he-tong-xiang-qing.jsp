<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>合同明细</title>
		<link href="${ctx}/reciprocity_admin/style/index.css" type="text/css"
			rel="stylesheet" />
		<script language="javascript" type="text/javascript"
			src="${ctx}/reciprocity_admin/js/jquery.js"></script>
		<script language="javascript" type="text/javascript"
			src="${ctx}/reciprocity_admin/js/my97/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="${ctx}/reciprocity_admin/js/popup.js"></script>
		<script language="javascript" type="text/javascript"
			src="${ctx}/script/ajaxupload.3.6.js"></script>
		<script type="text/javascript">
		
	$(function() {
	
		//删除图片，需要用到live
		$(".del-btn").live("click", function() {
			var div = $(this).parent();
			var img = $(this).prev().prev();
			var fileName = img.attr("src").substring(img.attr("src").lastIndexOf("/") + 1);
			$.ajax({
   				type: "POST",
   				url: "${ctx}/contract.action?operType=delImg&delType=CONTRACT",
   				data: "fileName=" + fileName,
   				dataType: "text",
   				success: function(msg) {
   					if(msg == 'success') {
   						div.remove();
   					}
   				}
			});
		});
	
		//文件ajax上传
		$(".selectFile").each(function(i){
			new AjaxUpload("#" + this.id, {
				action: '${ctx}/contract.action?operType=uploadImg',
				data:{
					id:this.id,
					importType:'CONTRACT'
				},
				name:'myFile',
				//选择后自动开始上传
				autoSubmit:true,
				//返回Text格式数据
				responseType: false,
				//上传的时候按钮不可用
				onSubmit : function(filename, ext) {
	   				//设置允许上传的文件格式
	   				if (!(ext && /^(jpg|png|jpeg|gif)$/.test(ext))) {
	    				alert('请选择图片文件!');
	    				// cancel upload
	    				return false;
	    			}
	    			this.disable();
	   			},
				//上传完成后取得文件名filename为本地取得的文件名，msg为服务器返回的信息
				onComplete: function(filename, msg) {
					this.enable();
					var results = msg.split(",");
					if(results[0] == "faild") {
						alert("上传失败！");
					} else {
						//location.reload();
						if(results[2].indexOf("</pre>") > 0) {
							//处理火狐浏览器，多出</pre>
							results[2] = results[2].substring(0, results[2].indexOf("</pre>"));
						}
						var fileTitle = filename.substring(0, filename.indexOf("."));
						$("#" + results[1]).parent().before("<li><img src='${ctx}/fileupload/"+results[2]+"'  width='100' height='100'/><span>"+fileTitle+"</span><i class='del-btn'></i></li>");
					}
				}
			});
		});
		
		
		new AjaxUpload("#upload-btn", {
			action: '${ctx}/contract.action?operType=uploadImg',
			data:{
				importType:'PROCESS'
			},
			name:'myFile',
			//选择后自动开始上传
			autoSubmit:true,
			//返回Text格式数据
			responseType: false,
			//上传的时候按钮不可用
			onSubmit : function(filename, ext) {
   				//设置允许上传的文件格式
   				if (!(ext && /^(jpg|png|jpeg|gif)$/.test(ext))) {
    				alert('请选择图片文件!');
    				// cancel upload
    				return false;
    			}
    			this.disable();
   			},
			//上传完成后取得文件名filename为本地取得的文件名，这里的文件名已经被后台修改，不能使用该文件名，msg为服务器返回的信息
			onComplete: function(filename, msg) {
				this.enable();
				var results = msg.split(",");
				if(results[0] == "faild") {
					alert("上传失败！");
				} else {
					$("#addImg").attr("src", "${ctx}/fileupload/"+results[1]);
					$("#upImg").val(results[1]);
					$("#old_filePath").val(results[2]);
					//$(".upload-img ").prepend("<img src='${ctx}/fileupload/"+results[1]+"' width='40' height='30'/><input type='hidden' value='"+results[1]+"' name='upImg'><input type='hidden' value='"+results[2]+"' name='old_filePath'>");			
				}
			}
		});
		
		//取消添加
		$("#calnel-btn").click(function() {
			var data = "";
			$("input[name=upImg]").each(function(i) {
				//i为下标索引
				if($("input[name=upImg]").size() == (i+1)) {
					//说明是最后一个，不需要拼接&，拼接fileName的目的在于，直接该字符串作为ajax提交到后台
					data += "fileName=" + $(this).val();
				} else {
					data += "fileName=" + $(this).val() + "&";
				}
			});
			
			$.ajax({
   				type: "POST",
   				url: "${ctx}/contract.action?operType=delImg",
   				data: data,
   				dataType: "text",
   				success: function(msg){
   					if(msg == 'success') {
   						EV_closeAlert('showbox');
   					}
   				}
			});
		});
		
		$("#feedbackSubmit").click(function() {
			var pro_name = $("#service_pro_name").val();
			if(pro_name == '') {
				alert("服务工单标题不能为空");
				return;
			}
			$("#feedbackForm").submit();
		});
		
	});
	
	function addFeedback(msgID, work_order_id) {  
	    //创建大大的背景框  
	    var bgObj = document.createElement("div");  
	    bgObj.setAttribute('id','EV_bgModeAlertDiv');  
	   	document.body.appendChild(bgObj);  
	    //背景框满窗口显示
	    EV_Show_bgDiv();  
	   	//把要显示的div居中显示  
	   	EV_MsgBox_ID = msgID;
	   	EV_Show_msgDiv();
	   	
	   	$("#work_order_id_temp").val(work_order_id);
	}
	
	</script>

	</head>
	<body>
		<jsp:include page="header-include.jsp">
			<jsp:param name="currentClass" value="customer"/>
		</jsp:include>
		
			<div id="main">
				<div class="custom">
					<h4 class="hd">
						<span class="title">客户明细</span>
						<div class="hd-r">
							<a href="javascript:history.back();" class="back-btn">返回</a>
						</div>
					</h4>
					<c:forEach items="${list}" var="contract">
					<div class="hm">
						<div class="hetong">
							<h3 class="ht-tit clearfix">
								<span class="ht-ph">合同编号：<em>${contract.contract_no}</em>
								</span>
								<c:choose>
									<c:when test="${contract.is_sure_contract == 0}">
										<span class="ht-status status-on">本合同信息客户未确认</span>
									</c:when>
									<c:when test="${contract.is_sure_contract == 1}">
										<span class="ht-status">本合同信息客户已确认</span>
									</c:when>
								</c:choose>
							</h3>
							<div class="ht-info">
								<p>
									<span>业务联系人：${contract.business_contacts}</span><span>联系电话：${contract.phone}</span>
								</p>
								<p>
									<span>签约时间：${fn:substring(contract.signing_time, 0, 10)}</span><span>合作金额：${contract.cooperation_num} RMB</span>
								</p>
								<p>
									<span>责任商务代表： ${contract.business_branch} ${contract.business_department} ${contract.business_name}</span><span>工号：${contract.business_work_no}</span>
								</p>
								<p>
									<!-- <span>服务等级：${contract.level_name}</span> --><span>跟进客服：${contract.cust_service_name}</span>
								</p>
							</div>
							<div class="ht-img">
								<ul class="clearfix" id="imgshow${contract.contract_id}">
									<!-- 
									<li>
										<img src="${ctx}/fileupload/a.png" width="100" height="100"/>
										<span>合同扫描件</span>
									</li>
									 -->
									<c:forEach items="${contract.pictures}" var="picture">
									<li>
										<img src="${ctx}/fileupload/${picture.path}" width="100" height="100"/>
										<span>${fn:substring(picture.or_path, 0, fn:indexOf(picture.or_path, '.'))}</span>
										<i class='del-btn'></i>
									</li>
									</c:forEach>
									<li class="add-img">
										<div id="selectFile${contract.contract_id}" class="selectFile">
											<img src="${ctx}/reciprocity_admin/images/add_pic.jpg"/>
										</div>
									</li>
								</ul>
							</div>
							<div class="ht-table">
								<table class="table-list" width="100%">
									<tr class="top-tr">
										<th>
											类别
										</th>
										<th>
											购买项目
										</th>
										<th>
											年限
										</th>
										<th>
											金额
										</th>
										<th>
											进度
										</th>
										<th>
											到期时间
										</th>
										<th>
											操作
										</th>
									</tr>
									<c:forEach items="${contract.workorders}" var="workorder">
										<tr>
											<td>
												${workorder.type_name}
											</td>
											<td>
												${workorder.buy_items}
											</td>
											<td>
												${workorder.years} 年
											</td>
											<td>
												${workorder.amount}
											</td>
											<td>
												${workorder.feedback_service_pro_name}
											</td>
											<td>
												${fn:substring(workorder.expiry_date, 0, 10)}
											</td>
											<td>
												<!-- 
												<a href="javascript:void(0);" class="text-blue"
													onClick="(addFeedback('showbox','${workorder.work_order_id}'))">添加服务进程</a> -->
												&nbsp;&nbsp;&nbsp;
												<a href="${ctx}/feedback.action?operType=list&work_order_id=${workorder.work_order_id}">查看服务进程</a>
											</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
					</c:forEach>
				</div>
			</div>
			<!--main-->

		<form action="${ctx}/feedback.action?operType=add" method="post" id="feedbackForm" name="feedbackForm">
			<div id="showbox">
				<h4>
					添加服务进程
					<a class="close-btn"
						href="javascript:void(EV_closeAlert('showbox'))"></a>
				</h4>
				<div class="showbox-m">
					<ul class="upload">
						<li>
							<span class="upload-l">服务工单标题</span>
							<input type="hidden" name="customer_info_id" id="customer_info_id" value="${customer_info_id}"/>
							<input type="hidden" name="work_order_id_temp" id="work_order_id_temp"/>
							<input type="text" name="service_pro_name" id="service_pro_name"/>
						</li>
						<li>
							<span class="upload-l">描述</span>
							<input type="text" name="context" id="context"/>
						</li>
						<li>
							<span class="upload-l">时间</span>
							<input type="text" onclick="WdatePicker()" name="create_time" id="create_time"/>
						</li>
						<li>
							<span class="upload-l">添加图片</span>
							<div class="upload-img" id="showImgDiv">
								<img src="${ctx}/reciprocity_admin/images/add_pic.jpg" id="addImg"/>
								<input type='hidden' value='' name='upImg' id='upImg'/>
								<input type='hidden' value='' name='old_filePath' id='old_filePath'/>
							</div>
							<a href="javascript:void(0);" id="upload-btn" class="upload-btn">上传图片</a>
						</li>
					</ul>
				</div>
				<div class="show-b">
					<a href="javascript:void(0);" id="calnel-btn"
						class="cannel-btn">取消</a><a href="#" id="feedbackSubmit" class="save-btn">保存</a>
				</div>
			</div>
		</form>
	
	
	</body>
</html>
