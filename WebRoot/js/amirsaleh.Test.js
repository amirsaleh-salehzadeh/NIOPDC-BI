$(document).ready(function(){
});	
		var txtTemp4FilterValue;
		var txtTemp4FilterCaption;

		function filter_click(){
			generate_mdxquerycompleted();
			
			$('#txtMDXQueryNC_Filter').val( $('#txtMDXQueryNC').val() );
			$('#txtMDXQuery_Filter').val( $('#txtMDXQuery').val() );
			
			document.getElementById("frmAipBiFilter").submit();
		}

		function generate_mdxquerycompleted(){
			var mdx = $('#txtMDXQueryNC').val();
			//alert(mdx);
			var ar_mdxquery = mdx.split('?');
			
			var mdxquerycompleted = ar_mdxquery[0];
			//alert(mdxquerycompleted); 

			var curFilterItems = $('#tblFilter #row td');
			
			for(var i=0;i<curFilterItems.length;i++){
				mdxquerycompleted += $( curFilterItems[i] ).children('.clsFilterValue').val();
				if(ar_mdxquery.length>i+1){
					mdxquerycompleted += ar_mdxquery[i+1]; 
				}
				//alert(mdxquerycompleted);
			}
			$('#txtMDXQuery').val(mdxquerycompleted);
			
		}
		function filter_param_click(cur){
			txtTemp4FilterValue=$(cur).parent().children('.clsFilterValue');
			txtTemp4FilterCaption=$(cur).parent().children('.clsFilterCaption');
			var txtTemp=$(cur).parent().children(".clsFilterCondition");
			
			if($(txtTemp).val().charAt(0)=='{'){
				showDialogSet($(txtTemp).val() );
			}else{
				showDialog('dlgFilter','center','jsp/loader/aipbifiltertreeloader.jsp?id=' + $(txtTemp).val() );
			}
		}
		function showDialogSet(aSet){
			var set = aSet;
			set = set.replace('{','');
			set = set.replace('}','');
			var ar = set.split(',');
			
			showDialog('dlgSet','center');
			
			var treSetRootChildren = $('#treSet .root ul');
			for(var i=treSetRootChildren.length-1;i>=0;i--){
				$(treSetRootChildren[i]).remove();
			}
			
			var treSetRoot = $('#treSet .root');
			for(var i=0;i<ar.length;i++){
				var caption=ar[i],value=ar[i];
				var pair=ar[i].split('=');
				if(pair.length>=2){
					caption=pair[0];
					value=pair[1];
				}
				treSetRoot.append("<ul><li class='leaf-last'><span class='text' onclick='selectedNodeChangeSet(\""+caption+"\",\""+value+"\");'>"+caption+"</span></li></ul>");
			}
			$('#treSet').SimpleTree({animate: false,autoclose:true});
			
		}
		
		function dlgFilter_OK(){
			if(txtTemp4FilterValue!==null){
				$(txtTemp4FilterValue).val( $('#treAIPBIFilterTree #selectedIds').val() );
				$(txtTemp4FilterCaption).val( $('#treAIPBIFilterTree #selectedNames').val() );
			}
		}
		function dlgSet_OK(){
			if(txtTemp4FilterValue!==null){
				$(txtTemp4FilterValue).val( $('#treSet #selectedIds').val() );
				$(txtTemp4FilterCaption).val( $('#treSet #selectedNames').val() );
			}
		}

		function selectedNodeChange(aId){
			$('#treDimension #selectedIds').val( aId ); 
		}
		function selectedNodeChangeFilter(aCaption,aValue){
			$('#treAIPBIFilterTree #selectedIds').val( aValue ); 
			$('#treAIPBIFilterTree #selectedNames').val( aCaption ); 
		}
		function selectedNodeChangeSet(aCaption,aValue){
			$('#treSet #selectedNames').val( aCaption ); 
			$('#treSet #selectedIds').val( aValue ); 
		}
		function selectedNodeChangeFolderReport(aFolderId){
			//alert(aFolderId);
			('#frmAipBiMdx_folderId').val( aFolderId );
		}
	

		function showDialogReportFolder(type){
			showDialog('dlgReportFolder','center');
		}
		

	
	
		function new_click(){
			$('#txtMDXQueryNC').val("");
			$('#txtMDXQuery').val("");
			
			$("#frmAipBiMdx_id").val( "" );
			$("#frmAipBiMdx_folderId").val( "" );
			$("#frmAipBiMdx_reportName").val( "" );
			$("#frmAipBiMdx_isPublic").val( "" );
			
			validate_click();
		}
		function open_click(){
			showDialogReportFolder('open');
		}
		function save_click(){
			showDialogReportFolder('save');
		}
		

		function execute_click(){
			generate_params();
			generate_filter();
			if(validate_click()){
				
				generate_mdxquerycompleted();
				
				document.getElementById("frmAipBiMdx").submit();
			}
		}
		var txtTemp4Condition;
		function param_condition_click(cur){
			//alert( $(cur).val() ); 
			txtTemp4Condition=$(cur).parent().children('input');
			//alert( $(txtTemp4Condition).val() ); 
			showDialog('dlgDimension','center');
		}
		function dlgDimension_OK(){
			if(txtTemp4Condition!==null){
				$(txtTemp4Condition).val( $('#treDimension #selectedIds').val() );
			}
		}

		function param_default_click(cur){
			txtTemp4FilterValue=$(cur).parent().children("input[@name='param_default_value']");
			txtTemp4FilterCaption=$(cur).parent().children("input[@name='param_default_caption']");
			//alert( $(cur).parent().parent().children(".colCondition").html() ); 
			var txtTemp=$(cur).parent().parent().children(".colCondition").children("input[@name=param_condition]");
			
			if($(txtTemp).val().charAt(0)=='{'){
				showDialogSet($(txtTemp).val() );
			}else{
				showDialog('dlgFilter','center','jsp/loader/aipbifiltertreeloader.jsp?id=' + $(txtTemp).val() );
			}
		}