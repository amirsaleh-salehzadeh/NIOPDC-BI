$(document).ready(function () {
	$("#locationId option").click(function(){
		$("#bankTitle").removeClass("focusedElementOnError");
		ajaxSetBankAccount(this);
	});
	$("#getBankTransactions").click(function(){
		if ($("#bankAccounts option").length > 0) {
			$("form#bankTransactionForm").submit();
		} else {
			$(this).getMessage( {	
				messageDesc: "برای این ناحیه بانکی جهت پرداخت الکترونیکی وجود ندارد" , 
				toBeHighlightedDOM: "#bankTitle"
			});
		}
	});
});


function  getThisPageExcelReportHref () {
	$("#bankTransactionsForm input[@name='reqCode']").val('thisPageExcelReport');
	$("form#bankTransactionsForm").attr('action', "/AIPNIOPDCSell/bankTransactions.do");
	$("form#bankTransactionsForm").submit();
	$("#bankTransactionsForm input[@name='reqCode']").val('');
	$("form#bankTransactionsForm").attr('action', "/AIPNIOPDCSell/t_bankTransactions.do");
}
function ajaxSetBankAccount(oElem) {
	$(this).createTransparentBg();
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
			$("#bankAccounts").html(xmlHttp.responseText);
			$("#bgDiv").remove();
		}
	}
	var url = "/AIPNIOPDCSell/bankTransactions.do?reqCode=getBankAccounts&locationId="+$(oElem).val();
	xmlHttp.open("POST",url,true);
	xmlHttp.send(null);
}
