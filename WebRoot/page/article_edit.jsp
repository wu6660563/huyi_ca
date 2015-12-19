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
	    <script src="${ctx}/script/ajaxupload.3.6.js"></script>
		<script src="${ctx}/ckeditor/ckeditor.js"></script>
		<script src="${ctx}/ckeditor/adapters/jquery.js"></script>
		<script src="${ctx}/script/lhgcore.min.js"></script>
		<script src="${ctx}/script/lhgcalendar.min.js"></script>

	</head>
	<script type="text/javascript">
		CKEDITOR.disableAutoInline = true;
        
	    $(function(){
	    
	        $("img[data-autoresize]").one("load",function(){$(this).autoresize({forceRun:true})});
			$( '#editor1' ).ckeditor(); 
	        init();
	        //删除文章封面
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
					$("input[name='articlePic']").val(msg); 
					$("img[data-autoresize]").one("load",function(){$(this).autoresize({forceRun:true})});
				}
				
			});
			  
			  
			  //编辑文章
			  $(".b-save").click(function(){
			  
			       var articleId= $("input[name='articleId']").val();
			       var channelId= $("input[name='channelId']").val();
			       var title= $("input[name='title']").val();
			       var author= $("input[name='author']").val(); 
			       var editTime= $("input[name='editTime']").val(); 
			       var articlePic= $("input[name='articlePic']").val(); 
			       var content= $("textarea[name='editor1']").val(); 
                      if(title==""){
                          alert("请输入文章标题！");
                          return;
                      }
                      if(author==""){
                          alert("请输入文章作者名称！");
                          return;
                      }

                      if(content.length>64040){
 			             alert("图文字符长度超长！");
 			             return;
 			         }
                     
			       var keyWords= ""; 
			         $(".fq-list div").each(function(){
			         var obj=$(this);
			         keyWords=keyWords+obj.find(".kh-txt").text()+",";
			       });
			        keyWords=keyWords.substring(0,(keyWords.length-1));
			        $.post("${ctx}/article.action", 
					{
						operType: "editArticle",
						title: title,
						channelId: channelId,
						articleId: articleId,
						author: author,
						editTime: editTime,
						articlePic: articlePic,
						content: content,
						keyWords: keyWords
					}, 
					function (data) {
						 if(data=="修改成功" || data=="新增成功"){
						       var magazineId= $("input[name='magazineId']").val();
						       if(magazineId!=""){
						    	    window.location.href="${ctx}/magazine.action?operType=get&mId="+magazineId;
							    }else{
							    	window.location.href="${ctx}/article.action?operType=list&channelId="+channelId;
								}
						 }
			      });
			        
			        
			  });
			  
			 
			
			  //添加关键字
			  $(".video-w-list").find(".xx-btn").click(function(){
			         if($(".fq-list div").length>5){
			             alert("最多可以添加6个关键字！");
			             $(".video-w-list").find("input[name='keyVal']").val("");
			             return;
			         }else{
			          var keyVal= $(".video-w-list").find("input[name='keyVal']").val();
			            if(keyVal==""){
			             alert("请输入关键字！");
			            }else{
					          var html=$('<div class="kh-c" stauts="0"><span class="kh-txt" >'+keyVal+'</span><a href="javascript:void(0);" class="del-btn">删除</a></div>');
		  				      html.appendTo($(".fq-list"));
		  				      $(".video-w-list").find("input[name='keyVal']").val("");
		  				      init();
  				          }
			          }
				 });
				 //返回到文章列表
				   $(".right-btn").click(function(){
				      var channelId= $("input[name='channelId']").val();
				      var magazineId= $("input[name='magazineId']").val();
				       if(magazineId!=""){
				    	    window.location.href="${ctx}/magazine.action?operType=get&mId="+magazineId;
					    }else{
					    	window.location.href="${ctx}/article.action?operType=list&channelId="+channelId;
						}
				      
				   });
				   
				   $(".b-cannel").click(function(){
				      var channelId= $("input[name='channelId']").val();
				      window.location.href="${ctx}/article.action?operType=list&channelId="+channelId;
				   });
				   
				   
				  
			   
			   
	    
	    });
	    
	    
	    function init(){
	       
	         //删除关键字
			   $(".fq-list div[stauts='0']").each(function(){
			             var obj=$(this);
			             obj.attr("stauts","1");
			             obj.find(".del-btn").click(function(){
			                   obj.remove(); 
			             });
			   });
			
	        
	    
	    }
	    
	    
	    
	    function start(){
			J.calendar.Show();
		}
		
	
	</script>

	<body>
		<%@ include file="./head.jsp"%>
		
<div class="position w">
 <a href="${ctx}/home.action">杂志列表</a> >><a href="${ctx}/magazine.action?operType=get&mId=${magazineId}">杂志编辑</a> >> 
  <c:if test="${souType !='magazine'}"><a href="${ctx}/article.action?operType=list&channelId=${articleMap.channel_id}">文章列表</a> >></c:if> 
 
   <strong class="cw">
       <c:choose>
		   <c:when test="${articleMap.article_id!=null}">编辑文章</c:when>
		   <c:otherwise>添加文章</c:otherwise>
		</c:choose>
 </strong></div>

<div class="admin-content w">
	<div class="adminArea">
		<div class="head-tit clearfix">		
			<a class="right-btn back-btn" href="javascript:void(0);">返回</a>
			<span class="t-title">
			   <c:choose>
				   <c:when test="${articleMap.article_id!=null}">编辑文章</c:when>
				   <c:otherwise>添加文章</c:otherwise>
				</c:choose>
		   </span>
		</div>

		<div class="video-c clearfix">
		
		    <input type="hidden" value="${articleMap.article_id }" name="articleId"/>
		    <input type="hidden" value="${articleMap.channel_id }" name="channelId"/>
		    <input type="hidden" value="${magazineId }" name="magazineId"/>
		    
		    
			<div class="news-wrap left">
			    <input type="hidden" name="articlePic"/>
				<div class="news-img" id="uploadBtn">
				<c:choose>
				  <c:when test="${articleMap.article_pic!=null}">
				    	<img src="/mzimg/${articleMap.article_pic}" id="showImg1" data-autoresize="2" />
				  </c:when>
				  <c:otherwise>
				  	<div class="news-img"><img src="img/nopic4.jpg" id="showImg1"  data-autoresize="2" /></div>
				  </c:otherwise>
				
				</c:choose>
			
				
				</div>
				<p class="pic-tip">建议上传图片尺寸<span class="text-red">(360*200)</span></p>
				<p class="textcenter"><span>阅读：</span><b class="text-ylw">${articleMap.article_read}</b><span style="margin-left:15px;">评论：</span><b class="text-ylw">${commentNum }</b></p>
			</div>
			<div class="video-info">
				<p><span>文章标题</span><input class="video-title w400" type="text" value="${articleMap.title }" name="title"/></p>
				<p><span>文章作者</span><input class="video-title w400" type="text"  value="${articleMap.author }" name="author"/></p>
				<fmt:formatDate value="${articleMap.edit_time}" type="date" pattern="yyyy-MM-dd" var="editTime"/>
				<p><span>编辑日期</span><input class="video-title w200" type="text" readonly="readonly"  value="${editTime }" onclick="start();" name="editTime"/></p>
				<p>
					<span>文章归属</span>
					<input class="video-title w400" type="text" disabled="disabled" value="${vesting}"/>
				</p>
				<div class="video-word clearfix">
					<span>关键字：</span>
					<div class="video-w-list clearfix">
						<input type="text" placeholder="请输入关键字" name="keyVal"/>
						<input type="button" class="xx-btn" value="添加">
					</div>
				</div>
				<div class="video-word clearfix">
					<span>已添加：</span>
					<div class="fq-list clearfix">
					  
                             <c:if test="${articleMap.key_words!=null}">
                               <c:set value="${ fn:split(articleMap.key_words, ',') }" var="key_words" />
                               <c:forEach items="${key_words}" var="key">
                                 <div stauts="0" class="kh-c"><span class="kh-txt">${key}</span><a class="del-btn">删除</a></div>
                               </c:forEach>
                            </c:if>
		                    
					  
						
					</div>
				</div>
			</div>	
		</div>

		<div class="editorArea">
			<h4>图文编辑</h4>
			<textarea cols="80" id="editor1" name="editor1" rows="10">${articleMap.content}</textarea>
		</div>
		
	    <div class="textcenter f-btn"><a href="javascript:void(0);" class="b-save">保存</a><a href="javascript:void(0);" class="b-cannel">取消</a></div>
		
	</div>
</div>
	</body>
</html>
