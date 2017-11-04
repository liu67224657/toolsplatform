$(function(){
	var oClose = $('.close-icon'),
		oXq = $('.xq-cont'),
		oMask = $('.mask-box'),
		oDhbox = $('.dh-box');
	var oBtn = $('.sub-form a');
	$('.btn-xq').click(function(){
		oXq.show();
		oMask.show();
	});
	$('.btn-dh').click(function(){
		if($(this).hasClass('un-dh')){
			return false;
		}else{
			oDhbox.show();
			oMask.show();
		}
	});
	$('.close-icon,.btn-sure').click(function(){
		oXq.hide();
		oMask.hide();
		oDhbox.hide();
	});
	oMask.click(function(e){
		e.stopPropagation();
	});
	$('.sub-form input,.sub-form textarea').bind('input', function() {
    	var inpVal =$('.sub-form input').val()+$('.sub-form textarea').val();
    	if(inpVal !== ''){
    		oBtn.addClass('on');
    	}else{
    		oBtn.removeClass('on');
    	}
	});
});