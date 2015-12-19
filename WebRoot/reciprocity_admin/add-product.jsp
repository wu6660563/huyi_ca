<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加产品类型</title>
<link href="${ctx}/reciprocity_admin/style/index.css" type="text/css" rel="stylesheet" />
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/jquery.js"></script>
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/popup.js"></script>
<script language="javascript" type="text/javascript"  src="${ctx}/script/ajaxupload.3.6.js"></script>
<script language="javascript" type="text/javascript">
	$(function() {
		$('#addStatus').click(function() {
			var size = $('.addList > div').size();
			//判断前一个不为空
			var prevStatus = $("#status_name" + size).val();
			if(prevStatus != null && $.trim(prevStatus) != '') {
				var flag = true;
				$('.addList > div').each(function(index) {
					if((index + 1) != size) {
						var status_name_str = $("#status_name" + (index + 1)).val();
						if(status_name_str == prevStatus) {
							alert("每个状态节点必须唯一！");
							flag = false;
							return false;
						}
					}
				});
				if(flag) {
					size ++;
					var content = '<div class="item" id="item'+size+'">' +
						'序号：<input type="text" name="status_id" value="'+size+'" id="status_id'+size+'" readonly="readonly"/>'+
						' 状态名称：<input type="text" name="status_name" id="status_name'+size+'"/>'+
						'<a class="del-jc" href="javascript:deleteItem('+size+');">删除</a></div>';
					$('.addList :first').append(content);
				}
			} else {
				alert("请先填写当前状态！");
				return;
			}
		});
		
		$('#saveBtn').click(function() {
			//验证不能为空
			var type_name = $("#type_name").val();
			if(type_name == null || $.trim(type_name) == '') {
				alert("产品类别名称不能为空！");
				return;
			}
			
			var upImg = $("#upImg").val();
			if(upImg == null || $.trim(upImg) == '') {
				alert("必须上传图片！");
				return;
			}
			
			var size = $('.addList > div').size();
			var statusNum = 100;	//正常代码
			$('.addList > div').each(function(index) {
				if(statusNum != 100) {
					return false;
				}
				
				var status_name_str1 = $("#status_name" + (index+1)).val();
				$('.addList > div').each(function(i) {
					var status_name_str2 = $("#status_name" + (i+1)).val();
					if(status_name_str1 == null || status_name_str2 == null || $.trim(status_name_str1) == '' || $.trim(status_name_str2) == '') {
						statusNum = 101;	//为空代码
						return false;
					}
					if(index != i && status_name_str1 == status_name_str2) {
						statusNum = 102;	//相同代码
						return false;
					}
				});
			});
			
			if(statusNum == 100) {
				if(size > 5) {
					alert("服务进程个数不能超过5！");
					return;
				} else if(size == 0) {
					alert("服务进程个数不能为0！");
					return;
				}
				$("#course_num").val(size);
				$("#form1").submit();
			} else if(statusNum == 101) {
				alert("状态不能为空！");
			} else if(statusNum == 102) {
				alert("每个状态节点必须唯一！");
			}
		});
		
		new AjaxUpload("#upload-btn", {
			action: '${ctx}/contract.action?operType=uploadImg',
			data:{
				importType:'PRODUCTTYPE'
			},
			name:'myFile',
			//选择后自动开始上传
			autoSubmit:true,
			//返回Text格式数据
			responseType: false,
			//上传的时候按钮不可用
			onSubmit : function(filename, ext) {
   				//设置允许上传的文件格式
   				if (!(ext && /^(jpg|png|jpeg)$/.test(ext))) {
    				alert('请选择静态图片文件!');
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
				}
			}
		});
		
		//取消添加
		$("#calnel-btn").click(function() {
			var data = "";
			var upImg = $("#upImg").val();
			var old_filePath = $("#old_filePath").val();
			if(upImg != "" &&　old_filePath != "") {
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
	   				url: "${ctx}/contract.action?operType=delImg&delType=PRODUCTTYPE",
	   				data: data,
	   				dataType: "text",
	   				success: function(msg){
	   					if(msg == 'success') {
	   						window.history.back();	//后退
	   					}
	   				}
				});
			} else {
				window.history.back();
			}
		});
		
	});
	
	function deleteItem(index) {
		var size = $('.addList > div').size();
		if(index == size) {
			var product_type_id = $("#product_type_id").val();
			var feedback_statusObj = $("#feedback_status" + index);
			if(product_type_id != null && product_type_id != '') {
				if(feedback_statusObj != null) {
					var feedback_status_id = feedback_statusObj.val();
					if(feedback_status_id != null && feedback_status_id != '') {
						var data = "feedback_status_id="+feedback_status_id;
						$.ajax({
			   				type: "POST",
			   				url: "${ctx}/feedback.action?operType=feedbackHaveStatus",
			   				data: data,
			   				dataType: "text",
			   				success: function(result){
			   					if(result == 'true') {
			   						alert("存在该状态的服务进程！");
			   					} else {
			   						$('#item' + index).remove();
			   					}
			   				}
						});
					}
				}
			} else {
				$('#item' + index).remove();
			}
		
		} else {
			alert("必须先从最后一个序号开始删除！");
		}
	}
</script>
</head>
<body>
	<jsp:include page="header-include.jsp">
		<jsp:param name="currentClass" value="producttype"/>
	</jsp:include><!--header-->

	<form action="${ctx}/producttype.action?operType=saveOrUpdate" method="post" name="form1" id="form1">
		<div id="main">
			<div class="custom">
				<h4 class="hd"><span class="title">添加产品类型</span><div class="hd-r"><a href="javascript:history.back();" class="back-btn">返回</a></div></h4>
				<div class="hm">
					<div class="hetong">
						<h3 class="ht-tit clearfix">
						<input type="hidden" name="product_type_id" id="product_type_id" value="${vo.product_type_id}"/>
						<span class="ht-ph">产品类别名称：<input type="text" name="type_name" id="type_name" value="${vo.type_name}"/></span></h3>
						<div class="ht-info">
							<p><!-- <em class="list-tit">服务进程个数：</em><input type="text" /> 个 -->
								<input type="hidden" name="course_num" id="course_num" value="${vo.course_num}"/>
								<a class="add-jc" id="addStatus" href="javascript:void(0);">添加进程状态</a>
							</p>
						</div>
						<div class="ht-info">
							<ul class="upload">
								<li>
									<span class="upload-l">添加图片:</span>
									<div class="upload-img" id="showImgDiv">
										<c:choose>
											<c:when test="${vo.pic_path == null || vo.pic_path eq ''}">
											<img src="${ctx}/reciprocity_admin/images/add_pic.jpg" id="addImg"/>
											</c:when>
											<c:otherwise>
											<img src="${ctx}/fileupload/${vo.pic_path}" id="addImg"/>
											</c:otherwise>
										</c:choose>
										<input type='hidden'  name='upImg' id='upImg' value='${vo.pic_path}'/>
										<input type='hidden'  name='old_filePath' id='old_filePath' value=''/>
										<a href="javascript:void(0);" id="upload-btn" class="upload-btn">上传图片</a>
									</div>
								</li>
							</ul>
						</div>
						<div class="addList">
							<c:if test="${statusList == null || fn:length(statusList) == 0}">
							<div class="item" id="item1">
								序号：<input type="text" name="status_id" value="1" id="status_id1" readonly="readonly"/> 状态名称：<input type="text" name="status_name" id="status_name1"/><a class="del-jc" href="javascript:deleteItem(1);">删除</a>
							</div>
							</c:if>
							<c:if test="${fn:length(statusList) > 0}">
							<c:forEach items="${statusList}" var="status">
							<div class="item" id="item${status.status_num}">
								<input type="hidden" name="feedback_status" value="${status.feedback_status_id}" id="feedback_status${status.status_num}" readonly="readonly"/>
								序号：<input type="text" name="status_id" value="${status.status_num}" id="status_id${status.status_num}" readonly="readonly"/> 状态名称：<input type="text" name="status_name" id="status_name${status.status_num}" value="${status.status_name}"/><a class="del-jc" href="javascript:deleteItem(${status.status_num});">删除</a>
							</div>
							</c:forEach>
							</c:if>
						</div>
						<div class="textcenter f-btn"><a href="javascript:void(0);" class="b-cannel" id="calnel-btn" >取消</a><a href="javascript:void(0);" class="b-save" id="saveBtn">保存</a></div>
					</div>				
				</div>
			</div>
		</div><!--main-->
	</form>
</body>
</html>
