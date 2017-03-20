$(document).ready(function () {
	//showDialog('dlgFilter','center',loader);
	//alert($("input[@type='radio'").attr('checked') === 'checked');
});

function dbclickCreateQuery(myField,thisElement){
	var query = $(thisElement).attr('id');
	var mainQuery = $("#reportQuery").val();
	//$("#reportQuery").val(mainQuery+" "+query);
	
	if (document.selection) {
		myField.focus();
		sel = document.selection.createRange();
		sel.text = query;
	}else if (myField.selectionStart || myField.selectionStart == '0') {//MOZILLA/NETSCAPE support
		var startPos = myField.selectionStart;
		var endPos = myField.selectionEnd;
		myField.value = myField.value.substring(0, startPos)
		+ query
		+ myField.value.substring(endPos, myField.value.length);
	} else {
		myField.value += query;
	}
}




/*function removeTree(thisElement,id){
	alert(id);
	//alert($(thisElement).html());
	$(id).remove();
}*/		
		
		
	
	
	
		
//	var url = '/AIPNIOPDCSell/visualreport.do';
//	window.location = url;
	
	
	/*var ajaxDataPosition = $(".viewTR td[@id='"+q+"']");
	var url = '/AIPNIOPDCSellBI/visualreport.do?reqCode=edit';
	alert(ajaxDataPosition);
	$(ajaxDataPosition).html("<td>"+q+"</td>");
		$.post(url,function(data){
			$(ajaxDataPosition).parent().show();
			$(ajaxDataPosition).html(data);
		});*/
		
		
/*	$(".filterTable")
    .append($('<tr>')
        .append($('<td class="headerTitleDiv">'+q)
            .append($('<img>')
                .attr('src', 'images/edit_24.gif')
                .text('Image cell')
            )
        )
    );*/


//    $(".filterTD")
//    .append($('<tr>')
//        .append($('<td class="headerTitleDiv">'+id)
//            .append($('<img>')
//                .attr('src', 'images/down.gif')
//                .text(id)
//            )
//        )
//    );





//function insertAtCursor(myField, myValue) {
//alert($("#txta").val());
//alert(myField);
//alert(myValue);
//IE support
//if (document.selection) {
//myField.focus();
//sel = document.selection.createRange();
//sel.text = myValue;
//}
//MOZILLA/NETSCAPE support
//else if (myField.selectionStart || myField.selectionStart == '0') {
//var startPos = myField.selectionStart;
//var endPos = myField.selectionEnd;
//myField.value = myField.value.substring(0, startPos)
//+ myValue
//+ myField.value.substring(endPos, myField.value.length);
//} else {
//myField.value += myValue;
//}
//}
//
