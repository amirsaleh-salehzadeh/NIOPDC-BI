function confirm(lawId, isLaw, confirmType, formId, doConfirm){
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
	  		if (xmlHttp.responseText === "done") {
	  			$("#showConfirmDiv").val("true");
				$("form#"+formId).submit();
			} else {
      			alert("There has been an error"); 
			}
		}
	}
	var url = "/LawWeb/confirm.do?lawId="+lawId+"&isLaw="+isLaw+"&confirmType="+confirmType+"&doConfirm="+doConfirm;
	xmlHttp.open("POST",url,true);
	xmlHttp.send(null);
}

