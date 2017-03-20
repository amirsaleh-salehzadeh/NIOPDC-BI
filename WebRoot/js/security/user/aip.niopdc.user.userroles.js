var userRolesWrapper = "#userRolesWrapper";
var locationWrapper = "#addLocationWrapper";
$(document).ready(function(){
	$(".userRoles").bind('click',showUserRoles);
	$(".locations").bind('click',showAddLocations);
});

function showUserRoles(){
	$(this).createTransparentBg();
	var uName = $(this).attr('id');
	var url="/AIPNIOPDCSell/userrole.do";
	url+="?username=" + uName;
	$.get(url,function(roleData){
		$(this).dialogWindow({
			prefix: 'userRoles', 
			attached: '#baseDiv',
			title: 'گروه های کاربر : '+ uName ,
			content: roleData,
			customWidth:300,
			resizable: false
		});
		bindUserRolesDialogButtons();
		bindActionsForSelectingRoles();
		$(this).removeTransparentBgChild();
	});
}

function bindUserRolesDialogButtons(){
	$(userRolesWrapper+" #btnSubmitUserRoles").bind('click',btnSubmit_click);
	$(userRolesWrapper+" #btnCancel").bind('click',btnCancel_click);
}


function bindActionsForSelectingRoles() {
	var newlySed = $("#newlySelectedRolesString").val();
	var checkBoxes = $("input[@type='checkbox']");
	var preselectedRoles = new Array();
	var newlySelectedRoles = new Array();
	if (newlySed !== null) {
		preselectedRoles = newlySed.split(',');
//		for (var i = 0; i < checkBoxes.length; i = i + 1) {
//			for (var j=0;j<preselectedRoles.length; j = j+1) {
//				if ($(checkBoxes[i]).attr('id') == preselectedRoles[j]) {
//					newlySelectedRoles.push(preselectedRoles[j]);
//					$(checkBoxes[i]).attr("checked", "checked");
//				}
//			}
//		}
//

		for (var j=0;j<preselectedRoles.length; j = j+1) {
			var flagPuched = false;
			for (var i = 0; i < checkBoxes.length; i = i + 1) {
				if ($(checkBoxes[i]).attr('id') == preselectedRoles[j]) {
					newlySelectedRoles.push(preselectedRoles[j]);
					flagPuched = true;
					$(checkBoxes[i]).attr("checked", "checked");
				}
			}
			if (!flagPuched) {
				newlySelectedRoles.push(preselectedRoles[j]);				
			}
		}

		
		
		$('#newlySelectedRolesString').attr('value',newlySelectedRoles);
		$("input[@type='checkbox']").bind('click',function(){
			if ($(this).attr('checked')) {
				newlySelectedRoles.push($(this).attr("id"));
				$('#newlySelectedRolesString').attr('value',newlySelectedRoles);
			}	else  {
				var indexOf = newlySelectedRoles.indexOf($(this).attr('id'));
				if (indexOf != '-1') {
					newlySelectedRoles.splice(newlySelectedRoles.indexOf($(this).attr("id")),1);
				}
				$('#newlySelectedRolesString').attr('value',newlySelectedRoles);
			} 
		});
	}
}

function btnSubmit_click(){
	$(userRolesWrapper+" input[@name='reqCode']").val('save');
	submitUserRoleDialog();	
}

function submitUserRoleDialog(){
	$('#userroleForm').ajaxSubmit(function() {
		closeUserRolesApproverDialog();	
	});
}

function btnCancel_click(){
	closeUserRolesApproverDialog();	
}

function closeUserRolesApproverDialog(){
	$("#userRolesWrapper").html("");
	$("#bgDiv").remove();
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function showAddLocations(){
	$(this).createTransparentBg();
	var url = "/AIPNIOPDCSell/agentLocation.do?reqCode=new&agentLocationENT.userId=" + $(this).attr('id');
	var titleHeader = 'تعریف ناحیه کاربر';
	$.get(url,function(data){
		$(this).dialogWindow({
			prefix: 'addLocation',
			attached: '#baseDiv',
			title:  titleHeader,
			content: data,
			closeMode: 'remove',
			customWidth:350,
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
	$('#newLocationForm').ajaxSubmit(function() {
		closeLocationApproverDialog();
	});
}

function btnReturnLocation_click(){
	closeLocationApproverDialog();	
}

function closeLocationApproverDialog(){
	$("#addLocationWrapper").html("");
	$("#bgDiv").remove();
}