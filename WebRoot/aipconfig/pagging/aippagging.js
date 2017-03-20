function paggingClick(thisElement,pageNo,inputId,formId,ajaxEnabledAndLocationIs){
	var form = document.getElementById(formId);
	var input = document.getElementById(inputId);
	if(input!==null && input!==undefined){
		input.value=pageNo;
	}

	var isAjax  = (ajaxEnabledAndLocationIs!=null && ajaxEnabledAndLocationIs!==undefined && ""!==ajaxEnabledAndLocationIs );
	var ajaxLocation;
	if(isAjax){
		 ajaxLocation =document.getElementById(ajaxEnabledAndLocationIs);
		 if(ajaxLocation==null || ajaxLocation==undefined){
		 	isAjax = false;
		 	
		 	alert('ajax enabled in paggingClick but couldnnott find location in document ');
		 } 
	}
	
	if(form!==null && form!==undefined){

		if(isAjax){
			$(form).ajaxSubmit( function(data){
				alert("response received"+data);
				$("#"+ajaxLocation).html(data);
				alert($("#"+ajaxEnabledAndLocationIs).html());
			}
		  );
		}else{
			form.submit();
		}
	}
}
