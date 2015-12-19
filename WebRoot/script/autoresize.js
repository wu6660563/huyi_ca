/*
this function will resize images with its parent element,
add "data-autoresize" to those img tag which you want to resize
remember to set height and width and overfloe:hidden,display:block to its parent element
author by Noah~~
last edit:
"Sun Sep 29 2013 11:49:54 GMT+0800 (CST)"
fix images resize incorrect when parent element has a style of text-align=center
change the images onload event
disable the autoupdate function which I still have no idea
more performance 
======
log:
"Sun Sep 22 2013 16:25:51 GMT+0800 (CST)" 
*/
(function(a){if(!jQuery)throw Error("autoresize requires jQuery");a.fn.resetcss=function(){return a(this).css({height:"auto",width:"auto","margin-left":0,"margin-top":0})};a.fn.subsize=function(a,c){return Math.floor(0.5*c*Math.abs(this[a]()-this.parent()[a]()))};a.fn.isImg=function(){return!0};a.fn.imgIsLoaded=function(){return 0!=a(this).width()&&0!=a(this).height()};a.fn.autoUpdate=function(){if(!a(this).data("resizeoptions").autoUpdate||a(this).attr("src")==a(this).data("updateSrc"))return a(this); a(this).data("updateSrc",a(this).attr("src"));a(this).autoresize({forceRun:!0});return a(this)};a.fn.autoresize=function(d){a(this).each(function(){var c=a.extend({},{debug:!1,mode:"not specific",forceRun:!1,isDone:!1,autoUpdate:!1},{mode:a(this).data("autoresize"),isDone:a(this).data("autoresizecomplete")},d);if(!c.isDone||c.forceRun)if(a(this).isImg()&&a(this).resetcss().imgIsLoaded())switch(c.mode){case 1:a(this).resetcss().height(a(this).parent().height()).width()>=a(this).parent().width()?a(this).css("margin-left", a(this).subsize("width",-1)):a(this).resetcss().width(a(this).parent().width()).css("margin-top",a(this).subsize("height",-1));a(this).parent().css("text-align","left");a(this).data("autoresizecomplete",!0);break;case 2:a(this).resetcss().height(a(this).parent().height()).width()<=a(this).parent().width()?a(this).css("margin-left",a(this).subsize("width",1)):a(this).resetcss().width(a(this).parent().width()).css("margin-top",a(this).subsize("height",1));a(this).parent().css("text-align","left");a(this).data("autoresizecomplete", !0);break;case 3:a(this).resetcss().width()<=a(this).parent().width()&&a(this).height()<=a(this).parent().height()?(a(this).css({"margin-left":a(this).subsize("width",1),"margin-top":a(this).subsize("height",1)}),a(this).parent().css("text-align","left"),a(this).data("autoresizecomplete",!0)):a(this).autoresize({mode:2,force:1});break;default:console.warn("Invalid operation number: "+b.mode)}else a(this).on("load",function(){a(this).autoresize()})});return a(this)}})(window.jQuery);$(function(){$("img[data-autoresize]").autoresize()});