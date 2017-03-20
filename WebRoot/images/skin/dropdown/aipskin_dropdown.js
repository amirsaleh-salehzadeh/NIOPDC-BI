$(document).ready(function () {
});
function enableDropDown(id){
	var div = "div#"+id+"DIV";
	var sub = "#"+id+"SUB";
	$(div).slideToggle(500);
	$(sub + " .barTable td:first-child").toggleClass("bar_BLUE_right_toggle");
	$(sub + " .barTable td:last-child").toggleClass("bar_BLUE_left_toggle");
}