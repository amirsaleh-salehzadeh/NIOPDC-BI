var locationWrapper = "#addLocationWrapper";
$(document).ready(function(){
	$("#btnAddLocation").bind('click', function(){
		if ($("#agentId").val() === "") {
			$(this).getMessage( {	
				messageDesc: "هیچ کاربری انتخاب نشده است" , 
				toBeHighlightedDOM: ".locationCodeData span:first",
				messageType: 'error'
			});
			return false;		
		}
		showAddLocationDialog();
	});
});

function showAddLocationDialog(){
	$(this).createTransparentBg();

	var url= "/AIPNIOPDCSell/agentLocation.do?reqCode=new&agentLocationENT.userId=" + $("#agentId").val();
	url += "&agentLST.agentDTO.code="+$("#agentCode").val();
	var titleHeader = 'تعریف ناحیه کاربر';
	$.get(url,function(data){
		$(this).dialogWindow({
			prefix: 'addLocation', 
			attached: '#baseDiv',
			title:  titleHeader,
			content: data,
			customWidth:400,
			resizable: false
		});
		bindLocationButtons();
		$(this).removeTransparentBgChild();
	});
}

function bindLocationButtons(){
	var treePath = '/AIPNIOPDCSell/cache/tree/'+$("#customerParentId").val()+'.tree';
	$(this).createTreeDiv({
		prefix: 'regions',
		position: 'append',
		attached: "#agentTree",
		title: 'نواحی',
		treePath: treePath
	});	
	if ($("#ajaxMessageTable").html() !== ""){
		$(".locationCodeData span:first").removeClass("focusedElementOnError");
		$("#ajaxMessageTable").empty();
	}
	$(locationWrapper+" #btnSave_Location").bind('click',btnSaveLocation_click);
	$(locationWrapper+" #btnReturn_Location").bind('click',btnReturnLocation_click);
}

function btnSaveLocation_click(){
	$(locationWrapper+" input[@name='reqCode']").val('save');
	submitAddLocationDialog4Return();
}

function btnReturnLocation_click(){
	$(locationWrapper+" input[@name='reqCode']").val('return');
	submitAddLocationDialog4Return();
}

function submitAddLocationDialog(){
	$('#newLocationForm').css("display","none");
	$(locationWrapper+" #content").append("<div align='center'> در حال برقراری ارتباط با وب سرویس <img src='images/icons/loading.gif' width='36px' height='36px' /> </div>");

	$('#newLocationForm').ajaxSubmit(function(data) {
		$(locationWrapper+" #content").html(data);
		bindLocationButtons();
	});
}

function submitAddLocationDialog4Return(){
	$('#newLocationForm').ajaxSubmit(function(data) {
		$("#baseDiv").html("<div id='baseDiv'/>");
		$("#baseDiv").html(data);
		rebindActions();
		$("#bgDiv").remove();
		if ($("#errorMessage").val() !== "") {
			$(this).getMessage( {	
				messageDesc: $("#errorMessage").val() , 
				toBeHighlightedDOM: ".agentData span:first",
				messageType: 'error'
			});
		} else if ($("#successMessage").val() !== "") {
			$(this).getMessage( {	
				messageDesc: $("#successMessage").val() , 
				messageType: 'success'
			});
		}
	});
}

function closeLocationDialog(){
	$("#bgDiv").remove();
	$("#baseDiv").html("");
}

function rebindActions() {
	$(this).beautification();
	$("#btnAddLocation").bind('click', function(){
		if ($("#agentId").val() === "") {
			$(this).getMessage( {	
				messageDesc: "هیچ کاربری انتخاب نشده است" , 
				toBeHighlightedDOM: ".locationCodeData span:first",
				messageType: 'error'
			});
			return false;		
		}
		showAddLocationDialog();
	});
}

