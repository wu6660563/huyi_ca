<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加业务资讯</title>
<link href="${ctx}/reciprocity_admin/style/index.css" type="text/css" rel="stylesheet" />
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/jquery.js"></script>
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/my97/WdatePicker.js"></script>
<script language="javascript" type="text/javascript"  src="${ctx}/reciprocity_admin/js/popup.js"></script>
<script src="${ctx}/script/jquery.min.js"></script>
<script src="${ctx}/script/autoresize.js"></script> 
<script src="${ctx}/script/ajaxupload.3.6.js"></script>
<script src="${ctx}/ckeditor/ckeditor.js"></script>
<script src="${ctx}/ckeditor/adapters/jquery.js"></script>
<script src="${ctx}/script/lhgcore.min.js"></script>
<script src="${ctx}/script/lhgcalendar.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript">
       //实例化编辑器
	    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	    UE.getEditor('editor');
	    $(function(){
		    //图文编辑初始化

			 new AjaxUpload("#upload-btn", {
					action: '${ctx}/contract.action?operType=uploadImg',
					data:{
						importType:'INFODISS'
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
						}
					}
		        });
		});
	 
			
		
	   
	      //保存
		  function add_info_diss(){
		 
		  $("#context").val(UE.getEditor('editor').getContent());
			   $('#add_info_diss').submit();
			   
			  };
		  //取消
		 function back(){
			 //取消之前添加的图片
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
			//返回主页面
		      window.location.href="${ctx}/home.action?operType=info_diss";
		   };
		   
			 
				
			
</script>
</head>
<body>
	<form action="${ctx}/home.action" id="add_info_diss" method="post">
	<input type="hidden" name="operType" value="add_info_diss"/>
	<jsp:include page="header-include.jsp">
		<jsp:param name="currentClass" value="info_diss"/>
	</jsp:include>

	<div id="main">
		<div class="custom">
			<h4 class="hd"><span class="title">添加业务资讯</span><div class="hd-r"><a href="javascript:void(0);" onclick="back();" class="back-btn">返回</a></div></h4>
			<div class="hm">
				 <div class="addYw">
					 <p><label>标题：</label><input type="text" name="title" value="${title }"/></p>
					 <p><label>资讯类别：</label>
						<select name="ann_type" >
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
					</p>
					<p class="addImg">
						<label>添加图片：</label>
						<span class="uploadImg" id="showImgDiv">
								<img src="${ctx}/reciprocity_admin/images/add_pic.jpg" id="addImg" />
								<input type='hidden' value='' name='upImg' id='upImg'/>
								<input type='hidden' value='' name='old_filePath' id='old_filePath'/>
						</span>
						<a href="javascript:void(0);" class="loadBtn" id="upload-btn" >上传图片</a>
					</p>
					 <p><label>摘要：</label><textarea class="zy-txt" name="summary" ></textarea></p>
				 </div>
			 <div class="editorArea">
			<h4>图文编辑</h4>
		     	 <textarea cols="80" id="editor" name="editor" rows="10" style="height: 300px;"></textarea>
		     	 <input type="hidden" id="context" name="context"/>

		   </div>
				 <div class="textcenter f-btn"><a href="javascript:void(0);" onclick="back();" class="b-cannel">取消</a><a onclick="add_info_diss();" href="javascript:void(0);" class="b-save">添加</a></div>
			</div>
		</div>
	</div><!--main-->

</form>
</body>
</html>
