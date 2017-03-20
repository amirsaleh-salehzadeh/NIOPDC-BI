$(document).ready(function () {
	$(".theMessageImg").click(function (){
		$(this).getMessage( {	
			messageDesc: $(this).attr('alt')  
		});
	});
	$(".theDescriptionImg").click(function (){
		$(this).getMessage( {	
			messageDesc: $(this).attr('alt') ,
			height: 'height: 200px;' 
		});
		$("#messageContainer").css('direction','ltr');
	});
});