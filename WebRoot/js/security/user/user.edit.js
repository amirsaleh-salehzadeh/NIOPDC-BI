$(document).ready(function () {
	$('.returnToList').click(function(){
		$('#reqCode').val('list');
		$('#editForm').submit();
	});

	$('#btnSave').click(function(){
		$('#reqCode').val('save');
		
		if($('#pass').val()!==$('#rePass').val()){
			alert('کلمه رمز یکسان نمی باشد');
		}else{
			$('#editForm').submit();
		}
	});
	$('.selectAll').click(function(){
		if($(this).attr('id') === 'role'){
			$('.role').attr('checked','checked');
			$("#selectedRoleIds").val("");
			$('.role').each( function (i) {
 						$("#selectedRoleIds").val($("#selectedRoleIds").val()+ $(this).attr('id') + ",");
			});
		} else if ($(this).attr('id') === 'group'){
			$('.group').attr('checked','checked');
			$("#selectedGroupIds").val("");
			$('.group').each( function (i) {
 						$("#selectedGroupIds").val($("#selectedGroupIds").val()+ $(this).attr('id') + ",");
			});
		}
		
	});
	$('.deSelectAll').click(function(){
		if($(this).attr('id') === 'role'){
			$('.role').attr('checked','');
			$("#selectedRoleIds").val("");
		} else if ($(this).attr('id') === 'group'){
			$('.group').attr('checked','');
			$("#selectedGroupIds").val("");
		}
	});			
	var str = $('#selectedRoleIds').val().split(',');
	$("#selectedRoleIds").val($('#selectedRoleIds').val() + ",");
	for (var i = 0 ; i < str.length ; i++){
		$('.role' +'#'+str[i]).attr('checked','checked');
	}
	var str2 = $('#selectedGroupIds').val().split(',');

	$("#selectedGroupIds").val($('#selectedGroupIds').val() + ",");
	for (var i = 0 ; i < str2.length ; i++){
		$('.group' +'#'+str2[i]).attr('checked','checked');
	}		

});



function showDialogF(x){
	var loader = "/AIPNIOPDCSellBI/user.do?reqCode=grouplist&group=true&editUserName="+$(x).attr('alt');
	showDialog('dlgFilter','center',loader);
}
function showDialogForRoles(x){
	var loader = "/AIPNIOPDCSellBI/user.do?reqCode=grouplist&group=false&editUserName=" + $(x).attr('alt');
	showDialog('dlgFilter','center',loader);
}
//function showDialogForChangePasswordUser(x){
//	var loader = "/AIPNIOPDCSellBI/changePassword.jsp?isInUserEdit=true&userName=" + $(x).attr('alt');
//	showDialog('dlgChangePassword','center',loader);
//}
function insertToGroup(){
	$(".userGroups").attr('id',$('#username').val());
	$(".userRoles").attr('id',$('#username').val());
}
function selectRoleIds(x){
	var sr = $(x).attr('id');
	if($(x).attr("checked") === true) {
		$("#selectedRoleIds").val($("#selectedRoleIds").val() + sr + ",");
	} else {
		var tmp = $("#selectedRoleIds").val().replace(sr + "," , "");
		$("#selectedRoleIds").val(tmp);
	}
}
function selectGroupIds(x){
	var sr = $(x).attr('id');
	if($(x).attr("checked") === true) {
		$("#selectedGroupIds").val($("#selectedGroupIds").val() + sr + ",");
	} else {
		var tmp = $("#selectedGroupIds").val().replace(sr + "," , "");
		$("#selectedGroupIds").val(tmp);
	}
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
function dlgFilter_OK(){
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

