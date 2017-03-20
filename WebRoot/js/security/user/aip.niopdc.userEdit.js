var isPermission = -1;
$(document).ready(function () {

	if ( $(document).getUrlParam("isAgent") === 'true' ) {
		isPermission = 0;	
	}

	if ( $(document).getUrlParam("isPermission") === 'true' ) {
		isPermission = 1;	
	}
	$("#newUserForm").attr('action' , "/AIPNIOPDCSell/t_user.do");
	$("#btnCancel_User").bind("click",function(){
		$("#newUserForm").submit();
	});
	$("#btnSave_User").bind("click",function(){
		if (!userValidation()){
			return false;
		}
		$("input[@name='reqCode']").val('saveNA');
		$("#newUserForm").submit();
	});
	$("#btnSaveAndNew_User").bind("click",function(){
		if (!userValidation()){
			return false;
		}
		$("input[@name='reqCode']").val('saveAndNewNA');
		$("#newUserForm").submit();
	});
	$("#btnGet_customer").bind("click",function(){
		if ($("#customerCode").val() === undefined || $("#customerCode").val() === "") {
			$(this).getMessage( {	
				messageDesc: "لطفا کد مشتری را وارد نمایید" , 
				toBeHighlightedDOM: "#customerCodeTitle",
				messageType: 'error'
			});
			return false;
		}
		$("input[@name='reqCode']").val('getCInfoNA');
		$("#newUserForm").submit();
	});
});




function userValidation() {
	$("td").removeClass('focusedElementOnError');
	if (isPermission === 1) {
		if ($("#customerId").val() === null || $("#customerId").val() === undefined || $("#customerId").val() === '0.0' || $("#customerId").val() === "") {
			return userAddValidationMessage("اطلاعات مشتری کامل نیست" , "#customerCodeTitle");
		}
	}
	if ($("#userName").val() === null || $("#userName").val() === undefined || $("#userName").val() === '0.0' || $("#userName").val() === "") {
		return userAddValidationMessage("لطفا نام کاربری را وارد نمایید" , "#userNameTitle");
	} 

	if ($("div #isEdit").html() === null) {
		if ($("#userPassword").val() === null || $("#userPassword").val() === undefined || $("#userPassword").val() === '0.0' || $("#userPassword").val() === "") {
			return userAddValidationMessage("لطفا کلمه عبور را وارد نمایید" , "#userPassTitle");
		}
	}	
	 
	if (isPermission === 0) {
		if ($("#fName").val() === null || $("#fName").val() === undefined || $("#fName").val() === '0.0' || $("#fName").val() === "") {
			return userAddValidationMessage("لطفا نام کاربر را وارد نمایید" , "#fNameTitle");
		} 
		if ($("#userCode").val() === null || $("#userCode").val() === undefined || $("#userCode").val() === '0.0' || $("#userCode").val() === "") {
			return userAddValidationMessage("لطفا کد را وارد نمایید" , "#userCodeTitle");
		} 
	}
	return true;
}

function userAddValidationMessage(msg , dom) {
	$(this).getMessage( {	
		messageDesc: msg , 
		toBeHighlightedDOM: dom,
		messageType: 'error'
	});
	return false;
}

