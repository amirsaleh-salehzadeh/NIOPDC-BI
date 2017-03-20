var userWrapper = "#addUserWrapper";
var isPermission = -1;
$(document).ready(function(){
	mainButtonActions();
});

function showAddUserDialog(userId,isPermission){
	$(this).createTransparentBg();
	var url= "/AIPNIOPDCSell/user.do?reqCode=" ;
	var titleHeader = '';
	var width = 800;
	if (userId === 0) {
		url += "new&isAgent="+ $("input[@name='param.isAgent']").val() +"&isPermission="+isPermission;
		titleHeader = 'کاربر جدید';
		if ( $("#isAgent").val() == 'true' ) {
			titleHeader = 'عامل جدید';	
		} 
		if (isPermission === 1) {
			titleHeader="صدور مجوز فروش مشتری";
			width = 1000;
		}
	} else {
		url += "edit&userId=" + userId + "&isAgent=" + $("#isAgent").val()  ;
		titleHeader = 'ویرایش کاربر';
		if ( $("#isAgent").val() == 'true' ) {
			titleHeader = 'ویرایش عامل';	
		} 
	}
	$.get(url,function(data){
		$(this).dialogWindow({
			prefix: 'addUser', 
			attached: '#baseDiv',
			title:  titleHeader,
			content: data,
			customWidth: width,
			resizable: false
		});
		bindUserButtons();
		$("#editableAgentProp").removeAttr('checked');
		$(this).removeTransparentBgChild();
	});
}


function bindUserButtons(){
	$(userWrapper+" #btnGet_customer").bind('click',btnGetCustomer_click);
	$(userWrapper+" #btnSave_User").bind('click',btnSaveUser_click);
	$(userWrapper+" #btnSaveAndNew_User").bind('click',btnSaveAndNewUser_click);
	$(userWrapper+" #btnCancel_User").bind('click',btnReturnUser_click);

	$("#isAgentParam").val($("#isAgent").val());
	$("#isPermissionParam").val($("#isPermission").val());
	$("#isInternetCustomerParam").val($("#isInternetCustomer").val());


}


function btnGetCustomer_click() {
	if ($(userWrapper+" #customerCode").val() === undefined) {
		$(this).getMessage( {	
			appendTo: "#ajaxMessageTable",
			messageDesc: "لطفا کد مشتری را وارد نمایید" , 
			toBeHighlightedDOM: "#customerCodeTitle",
			messageType: 'error'
		});
	} else {
		$("#newUserForm input[@name='reqCode']").val('getCInfo');
		submitAddUserDialog();	
	}
}

function btnSaveUser_click(){
	if (!userValidation()){
		return false;
	}
	$(userWrapper+" input[@name='reqCode']").val('save');
	submitAddUserDialog4Return();
}

function btnSaveAndNewUser_click(){
	if (!userValidation()){
		return false;
	}
	$(userWrapper+" input[@name='reqCode']").val('saveAndNew');
	submitAddUserDialog();	
}

function btnReturnUser_click(){
	$(userWrapper+" input[@name='reqCode']").val('return');
	submitAddUserDialog4Return();
//	closeUserDialog();
}

function submitAddUserDialog(){
	$(userWrapper+" #ajaxMessageTable").css("display","none");
	$('#newUserForm').css("display","none");
	$(userWrapper+" #content").append("<div align='center'> در حال برقراری ارتباط با سیستم فروش <img src='images/icons/loading.gif' width='36px' height='36px' /> </div>");
	$('#newUserForm').ajaxSubmit(function(data) {
		$(userWrapper+" #content").html(data);
		bindUserButtons();
	});
}

function submitAddUserDialog4Return(){
	$("#bgDiv").remove();
	$('#newUserForm').ajaxSubmit(function(data) {
		$("#baseDiv").html("<div id='baseDiv' />");
		$("#baseDiv").html(data);
		if ($("#errorMessage").val()!=="") {
			$(this).createTransparentBg4Message();
			$("#messageDiv").css('visibility','visible');	
			$("#messageDiv #messageTable #messageContainer #errorDescription").html($("#errorMessage").val());
		}
		$(this).beautification();
	});
}

function mainButtonActions() {
	var userId = 0;
	$(".edit").bind("click", function(){
		isPermission = false;
		userId = $(this).attr('id');
		showAddUserDialog(userId,isPermission);
	});
	$("#btnAddNew").bind('click', function(){
		isPermission = false;
		showAddUserDialog(0,isPermission);
	});
	$("#btnAddNewWithPermission").bind('click', function(){
		isPermission = true;
		showAddUserDialog(0,isPermission);
	});
}

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
		appendTo: "#ajaxMessageTable",
		messageDesc: msg , 
		toBeHighlightedDOM: dom,
		messageType: 'error'
	});
	return false;
}


