$(document).ready(function () {

//	$(".visualReportEdit").click(function(){
//	alert($(this).attr('id'));
//		var subsystem = $("#subSystem").val();
//		$('#reqCode').val('edit'); 
//		$('#reportId').val($(this).attr('id'));
//		alert($('#reportId').val()); 
//		$('form#visualReportForm').submit();
		//var loader = "/AIPNIOPDCSellBI/visualreport.do?reqCode=edit&reportId="+$(this).attr('id');
		//$('form#visualReportForm').submit();
//	});
});


function addVisualFormField(thisElement,name){
//alert(($("input[@type='radio'").attr('checked') === 'checked').val());
	var radio = $("input[@type='radio']");
	var addType;

	for (var i = 0; i < radio.length; i = i + 1) {
		if ($(radio[i]).attr('checked') == true) {
			addType=$(radio[i]).val();
		}
	}


	var id = $(thisElement).attr('id');
	//alert(id+"..thisElement"+thisElement+"....="+(id === "undefined"));
	if(id === undefined){
		id = thisElement;	
	}
	
	if(addType=='3'){
		var n=$(".filterAxis").length+1;
		var txtFilterValueName='filterAxis_'+n;
		$(".filterTD").append('<tr> <td class=\"headerTitleDiv\">'
	    						+'<input type="text" value="'+name+'" readonly="readonly" name="filters" class="filterAxis"/>'
	    						+"<img src=\"images/down.gif\" onclick=\"showFilterTreeDialog('"+id+"','"+txtFilterValueName+"');\"/>"
	    						+"<img src=\"images/delete_24.gif\" onclick=\"removeFormField(this);\"/>"
	    						+'<input type="hidden" value=",{'+id+'}," name="selectedFiltersIds" id="'+txtFilterValueName+'" readonly="readonly"/>');
	}else if(addType=='2'){
		var n=$(".columnAxis").length+1;
		var txtColumnValueName='columnAxis_'+n;
		$(".columnTD").append('<tr> <td class="headerTitleDiv">'
	    						+'<input type="text" value="'+name+'" readonly="readonly" name="columns" class="columnAxis"/>'
	    						+"<img src=\"images/down.gif\" onclick=\"showColumnTreeDialog('"+id+"','"+txtColumnValueName+"');\"/>"
	    						+"<img src=\"images/delete_24.gif\" onclick=\"removeFormField(this);\"/>"
	    						+'<input type="hidden" value=",{'+id+'}," name="selectedColumnsIds" id="'+txtColumnValueName+'" readonly="readonly"/>');
	}else if(addType=='1'){
		var n=$(".rowAxis").length+1;
		var txtValueName='rowAxis_'+n;
		$(".rowTD").append('<tr> <td class="headerTitleDiv">'
	    						+'<input type="text" value="'+name+'" readonly="readonly" name="rows" class="rowAxis"/>'
	    						+"<img src=\"images/down.gif\" onclick=\"showRowTreeDialog('"+id+"','"+txtValueName+"');\"/>"
	    						+"<img src=\"images/delete_24.gif\" onclick=\"removeFormField(this);\"/>"
	    						+'<input type="hidden" value=",{'+id+'}," name="selectedRowsIds" id="'+txtValueName+'" readonly="readonly"/>'
	    						+"پارامتری: <input type=\"checkbox\" name=\"checkParameter\" onclick=\"addToParameters('"+id+"');\" />");
	}else if(addType=='4'){
		var n=$(".measureAxis").length+1;
		var txtMeasureValueName='measureAxis_'+n;
		$(".measureTD").append('<tr> <td class="headerTitleDiv">'
	    						+'<input type="text" value="'+name+'" readonly="readonly" name="measures" class="measureAxis"/>'
	    						+"<img src=\"images/down.gif\" id=\""+id+"\" onclick=\"showMeasureTreeDialog('"+id+"','"+txtMeasureValueName+"');\"/>"
	    						+"<img src=\"images/delete_24.gif\" onclick=\"removeFormField(this);\"/>"
	    						+'<input type="hidden" value=",{'+id+'}," name="selectedMeasuresIds" id="'+txtMeasureValueName+'" readonly="readonly"/>');
	}    						
}   
     
function addToParameters(id){
	alert($("#parameters").val());
	$("#parameters").val($("#parameters").val()+","+id)
	alert($("#parameters").val());
}

function removeFormField(thisElement,id) {
	$(thisElement).parent().remove();
	
	//$(thisElement).remove(":contains('"+id+"')"); 
	//$('#row'+id1).remove();	
	//$("#"+id).remove();
	//$(thisElement).remove();
}

//function showTreeDialog(thisElement,uid){
//	var id = $(thisElement).attr('id');
// 	var loader="/AIPNIOPDCSellBI/jsp/treeloader.jsp?reqCode=hierarchy&id="+uid+"&nodeType=CheckBox";
//	showDialog('dlgFilter','center',loader);
//}    

var curDlgDim="";
function showRowTreeDialog(uid,txtValueName){
//alert(",selectedRowsIds="+$('#'+txtValueName).val());
	curDlgDim=txtValueName;
	var rowsSelected =  $('#'+curDlgDim).val();
	var txtRowsID = $('#'+txtValueName).val();
	//alert("txtRowsID 1===="+txtRowsID);
	//var txtRowsID = $('#txtRowsID').val();
	if(txtRowsID.indexOf('}')>0)
		txtRowsID = txtRowsID.substring(txtRowsID.indexOf('}')+1);
		
	//alert("txtRowsID===="+txtRowsID);
	
	txtRowsID = replaceAll(txtRowsID,"&", "%26");//txtRowsID.replace("&", "%26");
 	var loader="/AIPNIOPDCSellBI/jsp/loader/treerowloader.jsp?reqCode=hierarchy&id="+uid+"&nodeType=CheckBox"+"&selectedIds="+txtRowsID+"&selectedNames="+$('#txtRows').val();
	showDialog('dlgRow','center',loader);
}    

var curColumnDlgDim="";
function showColumnTreeDialog(uid,txtValueName){
	curColumnDlgDim=txtValueName;
	var columnsSelected =  $('#'+curColumnDlgDim).val();
	var txtColumnsID = $('#'+txtValueName).val();
	if(txtColumnsID.indexOf('}')>0)
		txtColumnsID = txtColumnsID.substring(txtColumnsID.indexOf('}')+1);
	
	txtColumnsID = replaceAll(txtColumnsID,"&", "%26");//txtRowsID.replace("&", "%26");

 	var loader="/AIPNIOPDCSellBI/jsp/loader/treecolumnloader.jsp?reqCode=hierarchy&id="+uid+"&nodeType=CheckBox"+"&selectedIds="+txtColumnsID+"&selectedNames="+$('#txtColumns').val();
	showDialog('dlgColumn','center',loader);
}    

var curFilterDlgDim=""
function showFilterTreeDialog(uid,txtValueName){
	curFilterDlgDim=txtValueName;
	var filtersSelected =  $('#'+curFilterDlgDim).val();
	var txtFiltersID = $('#'+txtValueName).val();
	if(txtFiltersID.indexOf('}')>0)
		txtFiltersID = txtFiltersID.substring(txtFiltersID.indexOf('}')+1);
	
	txtFiltersID = replaceAll(txtFiltersID,"&", "%26");
	
 	var loader="/AIPNIOPDCSellBI/jsp/loader/treefilterloader.jsp?reqCode=hierarchy&id="+uid+"&nodeType=CheckBox"+"&selectedIds="+txtFiltersID+"&selectedNames="+$('#txtFilters').val();
	showDialog('dlgFilter','center',loader);
}    

var curMeasureDlgDim="";
function showMeasureTreeDialog(uid,txtValueName){	
	curMeasureDlgDim=txtValueName;
	var measuresSelected =  $('#'+curFilterDlgDim).val();
	var txtMeasuresID = $('#'+txtValueName).val();
	if(txtMeasuresID.indexOf('}')>0)
		txtMeasuresID = txtMeasuresID.substring(txtMeasuresID.indexOf('}')+1);

	txtMeasuresID = replaceAll(txtMeasuresID,"&", "%26");
	
 	var loader="/AIPNIOPDCSellBI/jsp/loader/treemeasureloader.jsp?reqCode=hierarchy&id="+uid+"&nodeType=CheckBox"+"&selectedIds="+txtMeasuresID+"&selectedNames="+$('#txtMeasures').val();
	showDialog('dlgMeasure','center',loader);
}    



function replaceAll( str, from, to ) {
    var idx = str.indexOf( from );


    while ( idx > -1 ) {
        str = str.replace( from, to );
        idx = str.indexOf( from );
    }

    return str;
}

function dlgRow_OK(){
	var cur = $('#'+curDlgDim).val();
	//alert($('#'+curDlgDim).val());
	cur = cur.substring(cur.indexOf('{'), cur.indexOf('}')+1);
	//alert(cur+"...selected="+$('#treRowMembersLoader #selectedIds').val());
	$('#'+curDlgDim).val( ',' + cur + ',' +  $('#treRowMembersLoader #selectedIds').val() +',' );
	//alert("after OK="+$('#'+curDlgDim).val());

	$('#txtRowsID').val( $('#treRowMembersLoader #selectedIds').val() );
	$('#txtRows').val( $('#treRowMembersLoader #selectedNames').val() );
	$('#txtRowsSpan').html( $('#treRowMembersLoader #selectedNames').val() );
}


function dlgColumn_OK(){
	var cur = $('#'+curColumnDlgDim).val();
	//alert($('#'+curColumnDlgDim).val());
	cur = cur.substring(cur.indexOf('{'), cur.indexOf('}')+1);
	//alert(cur);

	$('#'+curColumnDlgDim).val( ',' + cur + ',' +  $('#treColumnMembersLoader #selectedIds').val() +',' );

	$('#txtColumnsID').val( $('#treColumnMembersLoader #selectedIds').val() );
	$('#txtColumns').val( $('#treColumnMembersLoader #selectedNames').val() );
	$('#txtColumnsSpan').html( $('#treColumnMembersLoader #selectedNames').val() );
}

function dlgFilter_OK(){
	var cur = $('#'+curFilterDlgDim).val();
	//alert($('#'+curFilterDlgDim).val());
	cur = cur.substring(cur.indexOf('{'), cur.indexOf('}')+1);
	//alert(cur);

	$('#'+curFilterDlgDim).val( ',' + cur + ',' +  $('#treFilterMembersLoader #selectedIds').val() +',' );
	$('#txtFiltersID').val( $('#treFilterMembersLoader #selectedIds').val());// + $('#txtFiltersID').val() );
	$('#txtFilters').val( $('#treFilterMembersLoader #selectedNames').val());// + $('#txtFilters').val());
	$('#txtFiltersSpan').html( $('#treFilterMembersLoader #selectedNames').val() );

	//$('#txtFiltersSpan').html( $('#txtFilters').val());
}

function dlgMeasure_OK(){
	var cur = $('#'+curMeasureDlgDim).val();
	//alert($('#'+curMeasureDlgDim).val());
	cur = cur.substring(cur.indexOf('{'), cur.indexOf('}')+1);
	//alert(cur);

	$('#'+curMeasureDlgDim).val( ',' + cur + ',' +  $('#treMeasureMembersLoader #selectedIds').val() +',' );
//
	$('#txtMeasuresID').val( $('#treMeasureMembersLoader #selectedIds').val() );
	$('#txtMeasures').val( $('#treMeasureMembersLoader #selectedNames').val() );
	$('#txtMeasuresSpan').html( $('#treMeasureMembersLoader #selectedNames').val() );
}






function addVisualFormField4Jsp(thisElement,name,addType,selectedMembers){
//alert(thisElement);
//alert(name);
//alert(($("input[@type='radio'").attr('checked') === 'checked').val());

	var id = thisElement;
	
	if(addType=='3'){
		var n=$(".filterAxis").length+1;
		var txtFilterValueName='filterAxis_'+n;
		$(".filterTD").append('<tr> <td class=\"headerTitleDiv\">'
	    						+'<input type="text" value="'+name+'" readonly="readonly" name="filters" class="filterAxis"/>'
	    						+"<img src=\"images/down.gif\" onclick=\"showFilterTreeDialog('"+id+"','"+txtFilterValueName+"');\"/>"
	    						+"<img src=\"images/delete_24.gif\" onclick=\"removeFormField(this);\"/>"
	    						+'<input type="hidden" value=",{'+id+'},"'+selectedMembers+'" name="selectedFilters" id="'+txtFilterValueName+'" readonly="readonly"/>');
	}else if(addType=='2'){
		var n=$(".columnAxis").length+1;
		var txtColumnValueName='columnAxis_'+n;
		$(".columnTD").append('<tr> <td class="headerTitleDiv">'
	    						+'<input type="text" value="'+name+'" readonly="readonly" name="columns" class="columnAxis"/>'
	    						+"<img src=\"images/down.gif\" onclick=\"showColumnTreeDialog('"+id+"','"+txtColumnValueName+"');\"/>"
	    						+"<img src=\"images/delete_24.gif\" onclick=\"removeFormField(this);\"/>"
	    						+'<input type="hidden" value=",{'+id+'},'+selectedMembers+'" name="selectedColumnsIds" id="'+txtColumnValueName+'" readonly="readonly"/>');
	}else if(addType=='1'){
		var n=$(".rowAxis").length+1;
		var txtValueName='rowAxis_'+n;
		$(".rowTD").append('<tr> <td class="headerTitleDiv">'
	    						+'<input type="text" value="'+name+'" readonly="readonly" name="rows" class="rowAxis"/>'
	    						+"<img src=\"images/down.gif\" onclick=\"showRowTreeDialog('"+id+"','"+txtValueName+"');\"/>"
	    						+"<img src=\"images/delete_24.gif\" onclick=\"removeFormField(this);\"/>"
	    						+'<input type="hidden" value=",{'+id+'},'+selectedMembers+'" name="selectedRowsIds" id="'+txtValueName+'" readonly="readonly"/>');
	}else if(addType=='4'){
		var n=$(".measureAxis").length+1;
		var txtMeasureValueName='measureAxis_'+n;
		$(".measureTD").append('<tr> <td class="headerTitleDiv">'
	    						+'<input type="text" value="'+name+'" readonly="readonly" name="measures" class="measureAxis"/>'
	    						+"<img src=\"images/down.gif\" id=\""+id+"\" onclick=\"showMeasureTreeDialog('"+id+"','"+txtMeasureValueName+"');\"/>"
	    						+"<img src=\"images/delete_24.gif\" onclick=\"removeFormField(this);\"/>"
	    						+'<input type="hidden" value=",{'+id+'},'+selectedMembers+'" name="selectedMeasuresIds" id="'+txtMeasureValueName+'" readonly="readonly"/>');
	}    						
}   



