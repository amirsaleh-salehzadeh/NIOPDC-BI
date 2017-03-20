$(document).ready(function () {
	$('#btnAddNew').click(function () {
		$('#reqCode').val('edit');
		$('#filterForm').submit();
	} );
	$('#btnAddNewList').click(function () {
		$('#reqCode').val('new');
		$('#filterForm').attr('action','user.do');
		$('#filterForm').submit();
	} );
	$('.btnDeleteForGroups').click(function () {
		$('#reqCode').val('deletes');
		$('#filterForm').submit();
	} );
	$('.btnDeleteForUser').click(function () {
		if($("#selectedRoles").val()!==""){
			$('#form').submit();	
		}
		$('#dlgFilter').hide();
		
	} );
	
	$('#btnCancel').click(function(){
		$('#dlgFilter').hide();
	});
	$('.close').click(function(){
		$('#dlgFilter').hide();
	});
	$('.selectAll').click(function(){
		$('.group').attr('checked','checked');
		$("#selectedRoles").val("");
		$('.group').each( function (i) {
			$("#selectedRoles").val($("#selectedRoles").val()+ $(this).val() + ",");
		});
	});
	$('.deSelectAll').click(function(){
		$('.group').attr('checked','');
		$("#selectedRoles").val("");
	});	
});
function showDialogF(x){
var loader = "/AIPNIOPDCSellBI/group.do?reqCode=userList&groupId=" + $(x).attr('id');
showDialog('dlgFilter','center',loader);
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