

function setSeasonSelected(input){
	$(".seasonCombo").removeAttr("selected");
	$(input).attr("selected","selected");
}
function getSeasonSelected(){
	//alert($(".seasonCombo:selected").val());
	return $(".seasonCombo:selected").val();
}

function setYearSelected(input){
	$(".yearCombo").removeAttr("selected");
	$(input).attr("selected","selected");
}
function getYearSelected(){
	//alert($(".yearCombo:selected").val());
	return $(".yearCombo:selected").val();
}

function setMonthSelected(input){
	$(".monthCombo").removeAttr("selected");
	$(input).attr("selected","selected");
}
function getMonthSelected(){
	//alert($(".monthCombo:selected").val());
	return $(".monthCombo:selected").val();
}
