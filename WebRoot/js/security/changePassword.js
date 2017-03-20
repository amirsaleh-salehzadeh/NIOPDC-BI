$(document).ready(function () {
	$("#submitChangePassword").click(doValidate);	
});

function doValidate() {
	$("#oldPassTitle").removeClass("focusedElementOnError");
	$("#newPass1Title").removeClass("focusedElementOnError");
	$("#newPass2Title").removeClass("focusedElementOnError");
	if ($("#oldPass").val() === undefined) {
		$(this).getMessage( {	
			messageDesc: 'لطفا کلمه عبور خود را وارد نمایید' , 
			toBeHighlightedDOM: "#oldPassTitle",
			messageType: 'error'
		});
		return false;
	}
	if ($("#newPass1").val() === undefined) {
		$(this).getMessage( {	
			messageDesc: 'لطفا کلمه عبور جدید را وارد نمایید' , 
			toBeHighlightedDOM: "#newPass1Title",
			messageType: 'error'
		});
		return false;
	}
	if ($("#newPass2").val() === undefined) {
		$(this).getMessage( {	
			messageDesc: 'لطفا کلمه عبور جدید را مجددا وارد نمایید' , 
			toBeHighlightedDOM: "#newPass2Title",
			messageType: 'error'
		});
		return false;
	}
	$("form#changePasswordForm input[@name='reqCode']").val("save");
	$("form#changePasswordForm").submit();
}
