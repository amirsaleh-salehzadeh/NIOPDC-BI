$(document).ready(function () {
	$("#submitCustomerTurnoverInfo").click(function(){
		if ($("#customerCodeHead").val() === null || $("#customerCodeHead").val() === undefined || $("#customerCodeHead").val() === "" ){
			$(this).getMessage( {	
				messageDesc: "لطفا کد مشتری را وارد نمایید" , 
				toBeHighlightedDOM: "#customerCodeTitle"
			});
			return false;
		}
	});
});