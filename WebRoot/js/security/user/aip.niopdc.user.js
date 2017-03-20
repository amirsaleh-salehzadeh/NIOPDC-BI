$(document).ready(function () {
	$(".delete").removeAttr("href");
	$(".edit").removeAttr("href");
	$(".delete").bind("click", function(){
		$(this).deleteUser(this);
	});
});

jQuery.fn.deleteUser = function(th) {
	var user = 'کاربر';
	if ($("#isAgent").val() === "true") {
		user = 'عامل فروش';		
	}
	$(this).makePopupDiv({
		showText: ' آیا با حذف این ' + user +' موافقید؟ ' ,
		buttons:2,
		windowLocation: "t_user.do?reqCode=delete&userId="+$(th).attr('id')+ "&isAgentProp=" + $("#isAgent").val()+ "&isIntenterCustomerProp=" + $("#isIntenterCustomer").val()
	});
};