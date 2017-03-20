$(document).ready(function () {
$('#groupSelect option[id='+$('#groupComboId').val()+']').attr('selected', 'selected');
	$('#btnAddNew').click(function () {
		$('#reqCode').val('new');
		$('#filterForm').submit();
	} );
	$('#btnDelete').click(function () {
		$('#reqCode').val('delete');
		$('#filterForm').submit();
	} );

});
function showDialogF(x){
	var loader = "/AIPNIOPDCSellBI/user.do?reqCode=grouplist&group=true&editUserName="+$(x).attr('id');
	showDialog('dlgFilter','center',loader);
}
function showDialogForRoles(x){
	var loader = "/AIPNIOPDCSellBI/user.do?reqCode=grouplist&group=false&editUserName=" + $(x).attr('id');
	showDialog('dlgFilter','center',loader);
}
function showDialogForSSASRoles(x){
	var loader = "/AIPNIOPDCSellBI/user.do?reqCode=ssasroles&editUserName=" + $(x).attr('id');
	showDialog('dlgFilterSSAS','center',loader);
}
function selectRoleIds(x){
	var sr = $(x).attr('id');
	if($(x).attr("checked") === true) {
		$("#selectedRoles").val($("#selectedRoles").val() + sr + ",");
	} else {
		var tmp = $("#selectedRoles").val().replace(sr + "," , "");
		$("#selectedRoles").val(tmp);
	}
}
function selectCombo(x){
	$('#groupComboId').val($(x).attr('id'));
}
function dlgRolesAndGroups_OK(){
	try{
	var options = {
	    success: function(req) {
	        alert("اطلاعات با موفقیت ذخیره شد.");
	    }
	    ,error: function(req){
	    	alert("ذخیره اطلاعات با مشکل مواجه شده است" + req.responseText);
	    }
	   };
		$('#rolesAndGroupsform').ajaxSubmit(options);
	}catch(e){
		alert(e);
	}
}
