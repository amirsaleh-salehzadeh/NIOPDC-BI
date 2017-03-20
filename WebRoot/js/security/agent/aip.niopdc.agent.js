$(document).ready(function () {
	$(".btnGoTo").bind('click',function(){
		if ($("#agentCode").val() === undefined || $("#agentCode").val() === "") {
			$(this).getMessage( {	
				messageDesc: "لطفا کد مشتری را وارد نمایید" , 
				toBeHighlightedDOM: "#customerInfo",
				messageType: 'error'
			});
			return false;
		}
		$("form#agentLocationsForm input[@name='reqCode']").val("view");
		$("form#agentLocationsForm").submit();
	});
});
