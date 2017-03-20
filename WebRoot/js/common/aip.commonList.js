$(document).ready(function(){
	try {
		$(this).doBeautify();
	} catch(e) { }
	if ($("#isDescending").val() === 'false') {
		$("th#"+$("#sortedByField").val()).append('<img src="images/amrIcons/down_20.gif" alt="Desc" width="16" height="16" />');
		$("th#"+$("#sortedByField").val()).addClass('isAsc');
	} else {
		$("th#"+$("#sortedByField").val()).append('<img src="images/amrIcons/up_20.gif" alt="Asc" width="16" height="16" />');
		$("th#"+$("#sortedByField").val()).addClass('isDesc');
	}
});

jQuery.fn.doBeautify = function() {
	$("table.forPrint tr:not(.collectionItemTR):not(.forPrintSub):odd").addClass("oddRows");
	$("table.forPrint tr:not(.collectionItemTR):not(.forPrintSub):even").addClass("evenRows");
	$("table.forPrintSub tr:odd").addClass("evenRows");
	$("table.forPrintSub tr:even").addClass("oddRows");
	$("table.forPrint tr:not(.collectionItemTR)").hover(
		function(){$(this).addClass("highlightYellow");},
		function(){$(this).removeClass("highlightYellow");}
	);
	$("table.forPrint .collectionItemTR").hide();
	$(".openSubList").toggle(function () {
		$(this).parent().parent().next().filter(".collectionItemTR").show();
		$(this).attr("src", "images/up.gif");
	}, function () {
		$(this).parent().parent().next().filter(".collectionItemTR").hide();
		$(this).attr("src", "images/down.gif");
	});
};

function sortBy(th,field,formName, reqCode) {
	$("#sortedByField").val(field);
	if (reqCode !== '') {
		$("#reqCode").val(reqCode);
	}
	if ($("#isDescending").val() === 'true' && $(th).hasClass("isDesc") ) {
		$("#isDescending").val('false');
	} else {
		$("#isDescending").val('true');
	}
	$(this).submitForm(formName);
}			

