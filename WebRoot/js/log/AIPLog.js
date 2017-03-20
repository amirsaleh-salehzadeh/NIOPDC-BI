$(document).ready(function () {
	$(".theMessageImg").click(function (){
		$(this).getMessage( {	
			messageDesc: $(this).attr('alt')  
		});
	});
	$(".theDescriptionImg").click(function (){
		$(this).getMessage( {	
			messageDesc: $(this).attr('alt')  
		});
	});
	$(".selectedForFullData").click(function (){
		var url = '/AIPNIOPDCSell/aipLog.do?reqCode=getFullData&id=' + $(this).attr('id');
		ajaxGetFullData(url);
	});
});


function ajaxGetFullData(url) {
	var xmlHttp;
	try {
		// Firefox, Opera 8.0+, Safari
		xmlHttp = new XMLHttpRequest();
	} catch (e) {
		// Internet Explorer
		try {
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
		    try {
      			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
      		} catch (e) {
      			alert("Your browser does not support AJAX!"); 
      			return false;
			}
    	}
	}
	xmlHttp.onreadystatechange = function() {
	  	if(xmlHttp.readyState == 4) {
			var msg = "<div dir='ltr' style='overflow: auto;width: 500px;height: 300px;'>";
			msg += xmlHttp.responseText;
			msg += "</div>";
			$(this).getMessage( {	
				messageDesc: msg  
			});
		}
	}
	xmlHttp.open("POST",url,true);
	xmlHttp.send(null);
}
