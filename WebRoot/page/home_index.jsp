<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>

<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="../commons/global.jsp"%>
		<title>杂志列表</title>
		<link rel="stylesheet" href="${ctx}/style/index.css" />
		<script src="${ctx}/script/jquery.min.js"></script>
		<script src="${ctx}/script/autoresize.js"></script>
	</head>
	<script type="text/javascript">
		var sort = 0;
		var list = null;
		$(function() {
			$("#keyword").val("");
			loadData();
		});
	
		function loadData() {
			if (sort == 0) {
				$('#asc').attr('class', 'sort_cate_on');
				$('#desc').attr('class', 'sort_cate_down');
			} else {
				$('#asc').attr('class', '');
				$('#desc').attr('class', 'sort_cate_down sort_cate_on');
			}
			var keyword = $("#keyword").val()||"";
			var isDate = 0;
			if (keyword !== "") {
				// 判断是否日期
				if (CheckDateTime(keyword)) {
					isDate = 1;
				} else {
					if (!/^[01-9]{1}\d*$/.test(keyword)) {
						alert("输入有误!");
						addData();
						return;
					} else {
						isDate = 0;
					}
				}
			}
			$.getJSON("${ctx}/home.action?operType=list", {keyword: keyword, isDate: isDate, sort: sort}, function(data) {
				list = data.list;
				addData();
			});
		}

		function addData() {
			for ( var i = 0; i < list.length; i++) {
				var obj = list[i];
				var v_pic = "${ctx}/images/1.jpg";
				var isFree = obj.is_free == 0 ? "charge-icon" : "free-icon";
				if (obj.magazine_path!= null && obj.magazine_path != "") {
					v_pic = "/mzimg/" + obj.magazine_path;
				}
				var trHtml = ''+
							'<li id="li_'+obj.magazine_id+'">'+
								'<h3>第 '+obj.magazine_phase+' 期</h3>'+
								'<div class="zz-img">'+
									'<a href="${ctx}/magazine.action?operType=get&mId='+obj.magazine_id+'"><img src="'+v_pic+'" /></a>'+
									'<span class="'+isFree+'">免费</span>'+
								'</div>'+
								'<p>出版日期：'+(obj.publish_time==null?"":obj.publish_time)+'</p>'+
								'<div class="zz-edit">'+
									'<a href="${ctx}/magazine.action?operType=get&mId='+obj.magazine_id+'">编辑</a>'+
									'<a href="javascript:del('+obj.magazine_id+');">删除</a>'+
								'</div>'+
							'</li>';
				$("#dataTable").append(trHtml);
			}
			if (list == null || list.length == 0) {
				$("#dataTable").append("<li>暂无数据</li>");
			}
			$("img[data-autoresize]").one("load",function(){$(this).autoresize({forceRun:true})});
		}
		
		function search() {
			$("#dataTable").html("");
			loadData();
		}

		function del(mId) {
			if (confirm("是否确认删除?")) {
				$.post("${ctx}/magazine.action", {operType: "del", mId: mId}, function(data) {
					if (data == "1") {
						$("#li_" + mId).remove();
						alert("操作成功!");
						if ($("#dataTable li").length == 0) {
							$("#dataTable").append("<li>暂无数据</li>");
						}
					} else {
						alert("操作成功!");
					}
				});
			}
		}
		
		//函数名：CheckDateTime  
		//功能介绍：检查是否为日期时间 
		function CheckDateTime(str){  
		    var reg = /^(\d+).(\d{1,2}).(\d{1,2})$/; 
		    var r = str.match(reg); 
		    if(r==null)return false; 
		    r[2]=r[2]-1; 
		    var d= new Date(r[1], r[2],r[3]); 
		    if(d.getFullYear()!=r[1])return false; 
		    if(d.getMonth()!=r[2])return false; 
		    if(d.getDate()!=r[3])return false; 
		    return true;
		} 
	</script>

	<body>
		<%@ include file="./head.jsp"%>
		
		<div class="position w"><strong class="cw">杂志列表</strong></div>
		
		<div class="admin-content w">
			<div class="adminArea">
		
				<div class="head-tit clearfix">		
					<a class="right-btn" href="${ctx}/magazine.action?operType=initMagazine">添加杂志</a>
					<div class="search-area">
						<span>排序</span>	
						<span class="sort_cate">
							<a id="asc" href="javascript:sort=0;search();" class="sort_cate_down"><i class="sort_arrow"></i></a>
							<a id="desc" href="javascript:sort=1;search();" class="sort_cate_on"><i class="sort_arrow"></i></a>
						</span>
						<span class="search-tit">搜索</span>	
						<form>
							<input id="keyword" type="text" placeholder="输入期数或出版日期(如:2014.01.01)" class="search-txt w300">
							<input type="button" onclick="search();" value="搜索" class="search-btn">
						</form>
					</div>
				</div>
		
				<div class="zz-list">
					<ul id="dataTable" class="clearfix">
					</ul>
				</div>
				
			</div>
		</div>
	</body>
</html>
