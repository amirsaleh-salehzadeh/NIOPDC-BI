function addNote(refNo,fieldName,refId,noteDialogId,extraParams){
	//alert('addNote clicked; refNo='+refNo+',fieldName='+fieldName+',refId='+refId+',noteDialogId='+noteDialogId);
	var loader = "note.do?reqCode=add";
	loader += "&refNo="+refNo;
	loader += "&fieldName="+fieldName;
	loader += "&refId="+refId;
	loader += "&noteDialogId="+noteDialogId;
	loader += "&"+extraParams;
	showDialog(noteDialogId,"center",loader);
}
function showNoteList(refNo,fieldName,refId,noteDialogId,extraParams){
	//alert("showNoteList clicked; refNo="+refNo+",fieldName="+fieldName+",refId="+refId+",noteDialogId="+noteDialogId);
	var loader = "note.do?reqCode=list";
	loader += "&refNo="+refNo;
	loader += "&fieldName="+fieldName;
	loader += "&refId="+refId;
	loader += "&noteDialogId="+noteDialogId;
	loader += "&"+extraParams;
	showDialog(noteDialogId,"center",loader);
}
