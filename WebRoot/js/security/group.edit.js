$(document).ready(function () {
	var str = $('#selectedRoleIds').val().split(',');
	$("#selectedRoles").val($('#selectedRoleIds').val() + ",");
	for (var i = 0 ; i < str.length ; i++){
		$('.role'+'#'+str[i]).attr('checked','checked');
	}
	$('#btnSave').click(function () {
		$('#form').submit();
	});
	$('#btnCancel').click(function () {
		$('#reqCode').val("list");
		$('#form').submit();
	} );
	$('#btnSaveAndNew').click(function () {
		$('#reqCode').val("saveAndNew");
		$('#form').submit();
		
	} );
	$('#btnAddNew').click(function () {
		$('#reqCode').val('edit');
		$('#filterForm').submit();
	} );

	$('#btnDelete').click(function () {
		$('#reqCode').val('edit');
		$('#filterForm').submit();
	} );
	$('.selectAll').click(function(){
			$('.role').attr('checked','checked');
			$("#selectedRoles").val("");
			$('.role').each( function (i) {
 						$("#selectedRoles").val($("#selectedRoles").val()+ $(this).attr('id') + ",");
			});
	});
	$('.deSelectAll').click(function(){
		if($(this).attr('id') === 'role'){
			$('.role').attr('checked','');
			$("#selectedRoles").val("");
		}
	});	


});

function selectRoleIds(x){
	var sr = $(x).attr('id');
	if($(x).attr("checked") === true) {
		$("#selectedRoles").val($("#selectedRoles").val() + sr + ",");
	} else {
		var tmp = $("#selectedRoles").val().replace(sr + "," , "");
		$("#selectedRoles").val(tmp);
	}
}