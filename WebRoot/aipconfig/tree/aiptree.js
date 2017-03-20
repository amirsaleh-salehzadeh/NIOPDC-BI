/*
 * these are older version of treeCheckChanged
 *
function treeCheckChanged(_this,idTarget){
	treeCheckChanged(_this,idTarget,null);
}
function treeCheckChanged(_this,idTarget,nameTarget){
	var target=$('#'+idTarget);
	var target2=$('#'+nameTarget);
	if(target!=null && target!=undefined){
		if( $(_this).attr("type")=="checkbox" )
		{
			if($(_this).attr("checked")==true){
				appendNodeValue2Target(target,$(_this).attr("value"));
				//appendNodeValue2Target(target2,$(_this).parent().text());
				appendNodeText2Target(target2,$(_this).parent().text());
			}else{
				removeNodeValue2Target(target,$(_this).attr("value"));
				removeNodeText2Target(target2,$(_this).parent().text());
			}
		}
	}
}
 */
 
 /*
  * first find tree contained this checkbox then add/remove it's id and name to/from related selectedIds,selectedNames
  */
function treeCheckChanged(checkbox){
	var treeId="";
	try{	treeId="#"+$(checkbox).parents(".tree").attr("id");  }catch(e){}
	var target=$(treeId+' #selectedIds');
	var target2=$(treeId+' #selectedNames');

	if(target!=null && target!=undefined){
		if( $(checkbox).attr("type")=="checkbox" )
		{
			if($(checkbox).attr("checked")==true){
				appendNodeValue2Target(target,$(checkbox).attr("value"));
				//appendNodeValue2Target(target2,$(checkbox).parent().text());
				appendNodeText2Target(target2,$(checkbox).parent().text());
			}else{
				removeNodeValue2Target(target,$(checkbox).attr("value"));
				removeNodeText2Target(target2,$(checkbox).parent().text());
			}
		}
	}
}
function appendNodeValue2Target(target,value,attr){
	if(target.attr("value")==null || target.attr("value")==""){
		target.val(",");
	}
	target.val(","+value+target.attr("value"));
}
function removeNodeValue2Target(target,value){
	var tmp=target.attr("value");
	var find=","+value+"," ;
	while(tmp.indexOf(find)>=0){
		tmp=tmp.replace(find, ",");
	}
	target.val(tmp);
}
function appendNodeText2Target(target,value){
	if(target.is("input")){
		appendNodeValue2Target(target,value);
	}else{
		if(target.text()==null || target.text()==""){
			target.text(",");
		}
		var append=" "+value+","
		target.text( target.text() + append );
	}
	
}
function removeNodeText2Target(target,value){
	if(target.is("input") || target.is("textarea") ){
		removeNodeValue2Target(target,value);
	}else{
		var tmp=target.text();
		var find=" "+value+"," ;
		while(tmp.indexOf(find)>=0){
			tmp=tmp.replace(find, "");
		}
		target.text(tmp);
	}
}

/*
 * unchecked all checked inputs and clear selectedIds and selectedNames inputs
 */
function clearSelectedIds(treeId){
	$("#"+treeId+" li span input[@type=checkbox]").attr("checked","");
	$("#"+treeId+" #selectedIds").val("");
	$("#"+treeId+" #selectedNames").val("");
}
/*
 * when tree load or every ajax nodes load it called and checked all checkboxes 
 * that there ids exists in selectedIds input 
 */
//function checkSelectedIds(treeId){
//alert($("#"+treeId+" li span input[@type=checkbox]").size());
//alert(treeId);
//$("#"+treeId+" li span input[@type=checkbox]").attr("checked","checked");
//
//	var ids = $("#"+treeId+" #selectedIds").val();
//	var ar=ids.split(",");
//	for(var i=0;i<ar.length;i++){
//		if( ar[i]!="" ){
//			//alert(ar[i]);
//			$("#"+treeId+" li span input[@value="+ar[i]+"]").attr("checked","checked");
//		}
//	}
//}


var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

function encode64(input) {
   var output = "";
   var chr1, chr2, chr3;
   var enc1, enc2, enc3, enc4;
   var i = 0;

   do {
      chr1 = input.charCodeAt(i++);
      chr2 = input.charCodeAt(i++);
      chr3 = input.charCodeAt(i++);

      enc1 = chr1 >> 2;
      enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
      enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
      enc4 = chr3 & 63;

      if (isNaN(chr2)) {
         enc3 = enc4 = 64;
      } else if (isNaN(chr3)) {
         enc4 = 64;
      }

      output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2) + 
         keyStr.charAt(enc3) + keyStr.charAt(enc4);
   } while (i < input.length);
   
   return output;
}

function replaceAll( str, from, to ) {
    var idx = str.indexOf( from );


    while ( idx > -1 ) {
        str = str.replace( from, to );
        idx = str.indexOf( from );
    }

    return str;
}



function checkSelectedIds(treeId){
	var ids = $("#"+treeId+" #selectedIds").val();
	var ar=ids.split(",");
	for(var i=0;i<ar.length;i++){
		if( ar[i]!="" ){
			$('#'+ treeId+ " input[@name='treeNode']").each(function(){
					var a = replaceAll(ar[i],"&", "%26");
					a = encode64(a);
					a=replaceAll(a,"=","_");
					a=replaceAll(a,"/","_");
	
					var el = replaceAll($(this).val(),"&", "%26"); 
					el = encode64(el);
					el=replaceAll(el,"=","_");
					el=replaceAll(el,"/","_");
					
					if(el === a){
						$(this).attr("checked","checked");
					}
				}
			);	
		}
	}
}



