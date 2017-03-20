$(document).ready(function () {
	$(".noHref").removeAttr("href");
	$(".paginate a[@id^='hrefed']").bind("click", function(){
		var forms = $("form");
		for (var i = 0 ; i < forms.length ; i = i + 1) {
			if ($(forms[i]).attr('id') !== 'ajaxForm') {
				var page = $(this).attr('id');
				page = page.substring(6);
				$(forms[i]).prepend('<input name="page" dir="ltr" type="hidden" id="hiddenPage" value="" />');
				$("input#hiddenPage").attr("value",page);
				if ($(document).getUrlParam("reqCode") == "delete") {
					$("input[@name='reqCode']").val("list");
				} 
				$(forms[i]).submit();
			}
		}
		return false;
 	});	
});