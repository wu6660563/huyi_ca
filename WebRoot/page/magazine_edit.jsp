<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/global.jsp"%>

		<title>编辑杂志</title>
		<meta charset=utf-8>
		<link rel="stylesheet" href="${ctx}/style/index.css" />
		<script src="${ctx}/script/jquery.min.js"></script>
		<script src="${ctx}/script/autoresize.js"></script>
			<script src="${ctx}/script/autoresize.js"></script>
		<script src="${ctx}/script/lhgcore.min.js"></script>
		<script src="${ctx}/script/lhgcalendar.min.js"></script>
	    <script src="${ctx}/script/ajaxupload.3.6.js"></script>
	</head>
	<script type="text/javascript">
	    $(function(){
	       init();
	       var uploadBtn = $('#uploadBtn');
			uploadBtnObj = new AjaxUpload(uploadBtn, {
				action : "${ctx}/fileupload.action",
				name : 'fileName',
				onSubmit : function(file, ext) {
					if (!(ext && /^(gif|jpg|png)$/i.test(ext))) {
						alert("提示: 只能上传gif,jpg,png文件格式");
					}
				},
				onComplete : function(file, response) {
					var msg = response.replace(/__tag_36$32_].*?>/g, "");
					$("#showImg1").attr("src", "/mzimg/tempdir/" + msg);
					$("input[name='magazinePath']").val(msg);
					$("img[data-autoresize]").one("load",function(){$(this).autoresize({forceRun:true})});
				}
			});
		 $("#addColumn").click(function(){
			            var html=$('<tr stauts="0">'+
							'<td width="370"><input class="xx-txt" type="text" placeholder="栏目标题" /><input type="button" value="保存" class="xx-btn" /></td>'+
							'<td align="center" >此类目前暂无文章</td>'+
							'<td>'+
								'<div class="manage-btn">'+
									'<a href="javascript:void(0);" name="addArticle">添加文章</a>'+
									'<a href="javascript:void(0);" class="btn1">文章列表</a>'+
								'</div>'+
							'</td>'+
							'<td width="80"><div class="editC"><span class="rank-btn"><img src="img/icon-rank.jpg" /></span><span class="del-btn" count="0"><img src="img/icon-close.jpg" /></span></div></td>'+
						  '</tr>');
			             html.prependTo($(".ad-table"));
			             init();
		 });
			   
		 $("a[name='yulang']").click(function(){
		             if(!checkChannel()){
		               alert("预览前请先添加栏目及文章！");
		               return;
		             }
			         var magazineId=$("#magazineId").val();
			         var magazinePath=$("input[name='magazinePath']").val();
			         var magazinePhase=$("input[name='magazinePhase']").val();
			         var publishTime=$("input[name='publishTime']").val();
		             if(publishTime==""){
		                alert("请选择发布时间！");
		                return;
		             }
			         if (magazinePath=="") {
			            alert("请上传杂志封面");
			            return;
			         }   
				     var   r   =   /^[0-9]*[1-9][0-9]*$/ ;　　//正整数      
			         if(magazinePhase!="" && !r.test(magazinePhase)){
			        	   alert("赞数必须为正整数！");
			        	   $("input[name='magazinePhase']").focus();
			        	   return;
				     }
			         $.post("${ctx}/magazine.action", 
								{
									operType: "edit",
									magazineId: magazineId,
									magazinePath: magazinePath,
									magazinePhase: magazinePhase,
									publishTime: publishTime
								}, 
					               function (data) {
								     if(data!=null && data>0){
								        window.location.href="${ctx}/magazine.action?operType=preview&magazineId=${magazineMap.magazine_id}"; 
								     }
				          });
			   
	     });
			   
		 $("a[name='b-save']").click(function(){
              
	         var magazineId=$("#magazineId").val();
	         var magazinePath=$("input[name='magazinePath']").val();
	         var magazinePhase=$("input[name='magazinePhase']").val();
	         var publishTime=$("input[name='publishTime']").val();
	          if(publishTime==""){
	             alert("请选择出版日期！");
	             return;
	          }
	          if (magazinePath==null || magazinePath=="") {
			      alert("请上传杂志封面");
			      return;
			  } 
	          var   r   =   /^[0-9]*[1-9][0-9]*$/ ;　　//正整数      
		         if(magazinePhase!="" && !r.test(magazinePhase)){
		        	   alert("赞数必须为正整数！");
		        	   $("input[name='magazinePhase']").focus();
		        	   return;
			     }
		         $.post("${ctx}/magazine.action", 
					{
						operType: "edit",
						magazineId: magazineId,
						byType: 1,
						magazinePath: magazinePath,
						magazinePhase: magazinePhase,
						publishTime: publishTime
					}, 
					function (data) {
					     if(data!=null && data>0){
					        alert("杂志创建成功");
					     }
			     });
			         
			    
			   
		 });
			   
			   
	    
	     });
	     function init(){
	      $(".ad-table tr[stauts='0']").each(function(){
                 var param=$(this);
                 param.attr("stauts","1");
                 param.find(".del-btn").click(function(){
                     
                     var count = param.find(".del-btn").attr("count");
                      if(parseInt(count) > 0){
                          alert("该栏目里存在文章，不能删除！");
                          return;
                      }
                      
	                  if(confirm("是否将该栏目删除?")){
	                   if(param.attr("channelId")!=null && param.attr("channelId")!=undefined){
	                        var channelId=param.attr("channelId");
		                    $.post("${ctx}/magazine.action", 
								{
									operType: "delChannel",
									channelId: channelId
								}, 
								function (data) {
								     if(data!=null && data>0){
								         param.remove();
								     }
				               });
	                   }else{
	                                    param.remove();
	                    }
                     }
                 });  
                 param.find(".xx-btn").click(function(){
                 
                       if(param.find(".xx-btn").attr("value")=='编辑'){
                           param.find(".xx-btn").attr("value","保存");
                           param.find(".xx-txt").attr("class","xx-txt");
                           return;
                       }else{
                          param.find(".xx-btn").attr("disabled",true);
                       }
	                   var magazineId=$("#magazineId").val();
	                   var title=param.find(".xx-txt").val();
	                   var channelId=param.attr("channelId");
	                     $.post("${ctx}/magazine.action", 
							{
								operType: "saveChannel",
								magazineId: magazineId,
								channelId: channelId,
								title: title
							}, 
							function (data) {
							     if(data!=null){
							      param.attr("channelId",data);
							      setTimeout(function(){
							    		 param.find(".xx-btn").attr("value","编辑");
	                                     param.find(".xx-txt").attr("class","xx-txt gray");
	                                     param.find(".xx-btn").removeAttr("disabled");
							             return;
							      },600); 
							      alert("编辑栏目成功");
							     }
			               });
	                
                 });  
                 
                 param.find("a[name='addArticle']").click(function(){
                     var magazineId=$("#magazineId").val();
                     if( param.attr("channelId")=="" || param.attr("channelId")==undefined){
                         alert("请先保存栏目！");
                         return;
                     
                     }
                     window.location.href="${ctx}/article.action?operType=get&souType=magazine&magazineId="+magazineId+"&channelId="+param.attr("channelid");
                 });
                 
                 
                  param.find(".btn1").click(function(){
                     
                     if( param.attr("channelId")=="" || param.attr("channelId")==undefined){
                         alert("请先保存栏目！");
                         return;
                     }
                      window.location.href="${ctx}/article.action?operType=list&channelId="+param.attr("channelId");
                 });
                 
                  param.find(".rank-btn").click(function(){
                      if( param.attr("channelId")!="" && param.attr("channelId")!=undefined){
                          $.post("${ctx}/magazine.action", 
								{
									operType: "sortChannel",
									channelId: param.attr("channelId")
								}, 
								function (data) {
								     if(data!=null){
								      param.attr("channelId",data);
								     }
			               });
			               
			           }  
			             
			           param.insertBefore($(".ad-table tr:eq(0)"));
			             
                    
                 });
                 
                 param.find(".xx-txt").click(function(){
                           param.find(".xx-btn").attr("value","保存");  
                           param.find(".xx-txt").attr("class","xx-txt"); 
                 });
                 
                 
                 
                 
		  });
	    }
	    function start(){
			J.calendar.Show();
		}
		
		function checkChannel(){
		   var f=false;
		   $(".ad-table tr").each(function(){
		         if($(this).attr("channelId")!=null && $(this).attr("channelId")!=undefined){
		             f=true;
		             return f;
		         }
		   });
		             return f;
		
		}
		
		
		function cancleFaBu(sta){
		     var magazineId=$("#magazineId").val();
		     $.post("${ctx}/magazine.action", 
				{
					operType: "release",
					magazineState: sta,
					magazineId: magazineId
				}, 
				function (data) {
				     if(data!=null && data>0){
				     
				       window.location.href="${ctx}/home.action"; 
				     
				     }
		    });
		
		}
		

	
	</script>

	<body>
		<%@ include file="./head.jsp"%>
		
		<div class="position w"><a href="${ctx}/home.action">杂志列表</a> >> <strong class="cw">编辑杂志</strong></div>
		
		<div class="admin-content w">
			<div class="adminArea">
				<div class="head-tit clearfix">		
				
					<a class="right-btn back-btn" href="${ctx }/home.action">返回</a>
					<c:choose>
				  <c:when test="${magazineMap.magazine_state!=2}">
				<a class="right-btn back-btn" href="javascript:cancleFaBu(2);">发布杂志</a>
				  </c:when>
				  <c:otherwise>
				 <a class="right-btn back-btn" href="javascript:cancleFaBu(0);">取消发布</a>
				  </c:otherwise>
				</c:choose>
					
					 <a class="right-btn back-btn" name="yulang" href="javascript:void(0);">预览杂志</a>
					
					<span class="t-title">编辑杂志</span>
					<input type="hidden" value="${magazineMap.magazine_id}" id="magazineId"/>
				</div>
		
				<div class="video-c clearfix" style="padding:20px; border:1px solid #d8d8d8;">
					<div class="news-wrap left">
					    <input type="hidden" name="magazinePath" value="${magazineMap.magazine_path}"/>
						<div class="zz-imgs" id="uploadBtn">
						<c:choose>
						   
						     <c:when test="${magazineMap.magazine_path != null}">
						     
						     <img src="/mzimg/${magazineMap.magazine_path}"  data-autoresize="2" id="showImg1"/>
						     
						     </c:when>
						     
						      <c:otherwise>
						       
						        <img src="img/nopic3.jpg"  data-autoresize="2" id="showImg1"/>
						      
						      </c:otherwise>
						</c:choose>
						
						
						</div>
						<p class="pic-tip">建议上传图片尺寸<span class="text-red">720*800</span></p>
					</div>
					<div class="video-info">
						<p><span>杂志期数</span><strong class="qi-title">第<input type="text"  name="magazinePhase" value="${magazineMap.magazine_phase}" style="width:40px;margin: 0 4px" />期</strong></p>
						<p><span>出版日期</span>
						 <fmt:formatDate value="${magazineMap.publish_time}" type="date" pattern="yyyy-MM-dd" var="publishTime"/>
						<input class="video-title w200" type="text" value="${publishTime}" onclick="start();" readonly="readonly" name="publishTime"/>
						</p>
						<div class="msg">
					
					<strong>杂志发布规则：</strong><br />
					您编辑好一本杂志后，通过右上角的发布杂志按钮，可以发布杂志，点击发布后，系统将会根据出版日期所设定的时间，自动将杂志内容发布到商家中心APP。<br />
					若您觉得内容有误需要下架杂志，可以取消发布。
				</div>
					<div style="margin-top:15px; text-align:center;"> 
					
				  	<a class="right-btn" href="javascript:void(0);"  name="b-save" style="float:none; display:inline-block; padding:0 40px;">保存</a>
			
					</div>
						<div class="video-word clearfix" style="display:none">
							<span>是否免费：</span>
							<div><label>免费 <input type="radio" /></label> <label style="margin-left:15px;">付费 <input type="radio" /></label></div>
						</div>
					</div>	
				</div>
		
				<div class="box-wrap">
					<div class="head-tit clearfix">		
						<a class="right-btn" href="javascript:void(0);" id="addColumn">添加栏目</a>
						<span class="t-title">编辑本杂志的内容</span>
					</div>
		
					<div class="listview1">
						<table width="100%" class="ad-table">
						
						 <c:forEach items="${channelList}" var="channel">
						 <tr stauts="0" channelId="${channel.channel_id }">
							<td width="370" ><input class="xx-txt gray" type="text" value="${channel.title}" /><input type="submit" value="编辑" class="xx-btn" /></td>
							<td align="center" >此栏目目前<br />已有 <em class="text-red">${channel.artcount}</em> 篇文章</td>
							<td>
								<div class="manage-btn">
									<a href="javascript:void(0);"  name="addArticle">添加文章</a>
									<a href="javascript:void(0);" class="btn1">文章列表</a>
								</div>
							</td>
							<td width="80"><div class="editC"><span class="rank-btn"><img src="img/icon-rank.jpg" /></span><span class="del-btn" count="${channel.artcount}"><img src="img/icon-close.jpg" /></span></div></td>
						  </tr>
						 </c:forEach>
						
						
						   
						  
						</table>
					</div>
		
				</div>
		
			
				
			</div>
		</div>
	</body>
</html>
