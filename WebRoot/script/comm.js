/********/
$(function(){
	var html='';
	html='<section id="preloader">'+
			'<div id="status">'+
				'<p class="center-text">'+
					'亲，正在加载数据...'+
					'<em><img src="img/status.gif" /></em>'+
				'</p>'+
			'</div>'+
		'</section>';

	$('body').append(html);
	$(window).load(function() { 
		$("#status").delay(350).fadeOut("slow"); 
		$("#preloader").delay(450).fadeOut("slow"); 
	}) 
})

window.addEventListener('load',function(){
	var header=document.getElementById('header');
	var myLinks=header.getElementsByTagName('a');
	for (var i=0;i<myLinks.length ;i++ ) {
		myLinks[i].addEventListener('touchstart',function(){
			var className=this.className;
			if (className=='setting'){
				this.className='setting current';
				this.addEventListener('touchend',function(){this.className='setting'});
			}else if (className=='phone'){
				this.className='phone current';
				this.addEventListener('touchend',function(){this.className='phone'});
			}else if (className=='backBtn'){
				this.className='backBtn current';
				this.addEventListener('touchend',function(){this.className='backBtn'});
			}
		})
	}

	/***********/
	var ad=document.getElementsByTagName('footer');
	if (ad.length>0){
		setInterval(function(){showImg()},3000);		
	}

	
});

function showImg(obj) {
	var _ul=$('.b-ad').find('.ad-list');
	var _height=$('.b-ad').height();
	_ul.animate({'margin-top':-_height+'px'},300,function(){
		$(this).css({'margin-top':0}).find('li:first').appendTo(this);
	})
 }

