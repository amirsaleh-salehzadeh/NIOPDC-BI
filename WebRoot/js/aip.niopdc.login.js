$(document).ready(function () {
	$("#login").click(doValidate);
	var a = window.location.href;
	if (a.indexOf("/t_login.do") > -1 ) {
		$("span#messageSpan").html("نام کاربری و یا کلمه عبور صحیح نیست. لطفا در ورود اطلاعات دقت کنید");
	}

});



function doValidate() {
	$("#passwordText").removeClass('focusedElementOnError');
	$("#userNameText").removeClass('focusedElementOnError');
	if ($("#userName").val() === "") {
		$("span#messageSpan").html("لطفانام کاربری را وارد نمایید");
		$("#userNameText").addClass('focusedElementOnError');
		return false;
	}
	if ($("#password").val() === "") {
		$("span#messageSpan").html("لطفاکلمه عبور را وارد نمایید");
		$("#passwordText").addClass('focusedElementOnError');
		return false;
	}
	$("form#loginForm").submit();
}