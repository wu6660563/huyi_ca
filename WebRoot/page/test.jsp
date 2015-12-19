<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
	<head>
	<%@ include file="../commons/global.jsp"%>
	<title>车世界-添加文章</title>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2"></script>
<script src="${ctx}/script/jquery-1.9.1.min.js"></script>

</head>
<body>
<div style="margin:50px">请输入起点:<input type="text" name="eachMap" id="start" size="30" style="width:300px;" /></div>
<div style="margin:50px">请输入终点:<input type="text" name="eachMap" id="end" size="30" style="width:300px;" /></div>
<div style="margin:50px"><input type="button" value="测距" id="ceju"/></div>


<div id="container" style="display: none;"></div>

<script type="text/javascript">

var map = new BMap.Map("container");
var point = new BMap.Point(116.3964,39.9093);
map.centerAndZoom(point,13);
map.enableScrollWheelZoom();
var points=new Array(4);
var myValue;
$(function(){
     $("input[name='eachMap']").each(function(){
          var obj=$(this);
          var inpId= obj.attr("id");
          var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
          {"input" : inpId,"location" : map});
		   ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
			    var _value = e.fromitem.value;
			    var value = "";
			    if (e.fromitem.index > -1) {
			        value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			    }    
			    value = "";
			    if (e.toitem.index > -1) {
			        _value = e.toitem.value;
			        value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			    }    
			});
			
			ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
			var _value = e.item.value;
			    myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
			    setPlace(obj);
			});
     
     });
     
    $("#ceju").click(function(){
      
        if(points[0]==undefined || points[1]==undefined){
          
           alert("请选择起始地址！");
           return;
        }
        if(points[2]==undefined || points[3]==undefined){
          
           alert("请选择终点地址！");
           return;
        }
        
        var startMap = new BMap.Point(points[0], points[1]);
        var endMap = new BMap.Point(points[2], points[3]);
        var distance = map.getDistance(startMap, endMap);
        alert(returnFloat(distance/1000)+"公里");
        
    });
});

function returnFloat(value) {  
    value = Math.round(parseFloat(value) * 10) / 10;
    if (value.toString().indexOf(".") < 0)
     value = value.toString() + ".0";
    return value;
   }
function setPlace(obj){// 创建地址解析器实例
var myGeo = new BMap.Geocoder();// 将地址解析结果显示在地图上,并调整地图视野
myGeo.getPoint(myValue, function(point){
  if (point) {
	    if(obj.attr("id") == "start"){
	     points[0] = point.lng;
	     points[1] = point.lat;
	    }
	    if(obj.attr("id") == "end"){
	     points[2] = point.lng;
	     points[3] = point.lat;
	    }   
  }
}, "北京");
}
</script>
</body>
</html>

