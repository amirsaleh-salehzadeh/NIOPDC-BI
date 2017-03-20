<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="aip.tag.tabbed.AIPTabs"%>
<%@page import="aip.tag.tabbed.AIPTab"%>
<%@page import="aip.jpivot.AIPPivotParam"%>
<%@page import="aip.util.UTF8"%>
<%@page import="aip.util.NVL"%>
<%@page import="aip.niopdc.sellbi.orm.SellDAOManager"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="aip.common.report.ReportENT"%>
<%@page import="aip.common.AIPDefaultParam"%>
<%@page import="aip.common.AIPWebUser"%>
<%@page import="aip.common.report.ReportCriteriaENT"%>
<%@page import="aip.common.folder.FolderENT"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%!
	public String getAttributeOrParameter1(HttpServletRequest request, String name){
		return getAttributeOrParameter1(request,name,false);
	}
	public String getAttributeOrParameter1(HttpServletRequest request, String name,boolean cnvParametr2Utf8){
		if(request.getAttribute(name)!=null){
			return request.getAttribute(name).toString();
		}else if(request.getParameter(name)!=null){
			return UTF8.cnvUTF8(request.getParameter(name));
		}
		return null;
	}
%>
<%
	String reqCode=NVL.getString( request.getParameter("reqCode") );
	//out.println("reqCode="+reqCode);
	int id = 0;
	id=NVL.getInt( getAttributeOrParameter1(request,"reportId") );
	//////////////////ADDED BY AMIRSALEH START
	if(id == 0){
		id = NVL.getInt(request.getParameter("id"));
	}
	//////////////////ADDED BY AMIRSALEH END
	String pivotFormAction=NVL.getEmptyString(getAttributeOrParameter1(request,"pivotFormAction"), "aipbimdx.jsp");
	
	ReportENT bimdxDTO=null;
	if(id>0)
	{
		try{
			bimdxDTO = SellDAOManager.getBIReport().getReportENT( new AIPDefaultParam( new AIPWebUser(request) , id ) );
			pageContext.setAttribute("bimdxDTO",bimdxDTO);
		}catch(Exception ex){
			ex.printStackTrace();
			request.setAttribute("errorMessage","اشکال در بارگذاری گزارش:<br>" + ex.getMessage());
		}
	}

 %>

<head>
	<link href="css/navigation.css" rel="stylesheet" type="text/css" >
	<link rel="stylesheet" type="text/css" href="aipconfig/tabbed/aiptabbed.css">
	<script type="text/javascript" src="jquery/jquery.js"></script>
	<script type="text/javascript" src="jquery/jquery.form.js"></script>
	<script type="text/javascript" src="jquery/ui/ui.core.packed.js"></script>
	<script type="text/javascript" src="jquery/ui/ui.draggable.packed.js"></script>
	<script type="text/javascript" src="jquery/jquery.tree.js"></script>
	<script src="js/common/aip.niopdc.common.js" type="text/javascript"></script>			


	<script type="text/javascript">
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
			$('#frmAipBiMdx_folderId').val( aFolderId );
			$('#frmFolder_folderId').val( aFolderId );
		}
	

		function showDialogReportFolder(type){
			showDialog('dlgReportFolder','center');
		}
		function dlgReportFolder_OK(){
			generate_params();
			generate_filter();
			if(validate_click()){
				
				generate_mdxquerycompleted();
				
				var actionOld = $("#frmAipBiMdx").attr("action"); 
				var reqCodeOld = $("#frmAipBiMdx_reqCode").val();
				 
				$("#frmAipBiMdx").attr("action","aipbimdx.do");
<%--				$("#frmAipBiMdx_reqCode").val('saveReport');--%>
				$("#frmAipBiMdx_reqCode").val($('#folderReqCode').val());
				$("#frmAipBiMdx_reportName").val( $('#dlgReportFolder_reportName').val() );
				$("#frmAipBiMdx_isPublic").val( $('#dlgReportFolder_isPublic').val() );
				
				var options = {
				    success: function(responseText) {
				        alert("اطلاعات با موفقیت ذخیره شد.");
						$('#reportTitle').html($('#dlgReportFolder_reportName').val());				        
				    }
				    ,error: function(req){
<%--				    	alert("ذخیره با خطا مواجه شد!" + req.responseText);--%>
				    	alert("ذخیره با خطا مواجه شد!");
				    }
				};
				$('#frmAipBiMdx').ajaxSubmit(options);				

				$("#frmAipBiMdx").attr("action",actionOld);
				$("#frmAipBiMdx_reqCode").val(reqCodeOld);

			}
		}

	
	<% if("edit".equalsIgnoreCase(reqCode)){ %>
	
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
			var x = $('#frmFolder_isPublic').val();
			var y = $('#frmFolder_reportName').val();
			var z = $('#frmFolder_reportId').val();	
			var w = $('#frmFolder_reportFolderId').val();
			var loader= "jsp/loader/treeLoaderForFolders.jsp?reportName="+y;
			loader = loader + "&isPublic="+x;
			loader = loader + "&reportId="+z;
			loader = loader + "&folderId="+w;
			showDialog('dlgReportFolder','center',loader);
<%--			showDialogReportFolder('save');--%>
		}
		function save_as_click(){
			var x = $('#frmFolder_isPublic').val();
			var y = $('#frmFolder_reportName').val();
			var z = $('#frmFolder_reportId').val();	
			var w = $('#frmFolder_reportFolderId').val();					
			var loader= "jsp/loader/treeLoaderForFolders.jsp?forSaveAs=true&reportName="+y;
			loader = loader + "&isPublic="+x;
			loader = loader + "&reportId="+z;
			loader = loader + "&folderId="+w;			
			showDialog('dlgReportFolder','center',loader);
<%--			showDialogReportFolder('save');--%>
		}		
		function validate_click(){
			generate_params();
			generate_filter();
			var curParameters = $('#tblParameters tr.rowParameters');
			for(var i=1;i<curParameters.length;i++){
				var txt = $( curParameters[i] ).children(".colCondition").children("input");
				if(txt.val()===''){
					alert("سطر شماره " + (i-1) + " شرط لیست انتخابی ندارد! لطفا برای این سطر از لیست انتخاب کنید.")
					
					// بررسی خود mdx باقی مانده
					
					return false;
				}
			}
			return true;
		}
		function generate_params(){
			//count ?
			var mdx = $('#txtMDXQueryNC').val();
			var ques_count=0,i=0;
			for(i=0;i<mdx.length;i++){
				if(mdx.charAt(i)=="?")ques_count++;
			}
			
			//alert(ques_count);
			
			var curParameters = $('#tblParameters tr.rowParameters');
			
			//alert(curParameters.length);
			
			if((curParameters.length-2)>ques_count){
				for(i=curParameters.length-1;i>ques_count+1 && i>1;i--){
					$( curParameters[i] ).remove();
				}
			}else if((curParameters.length-2)<ques_count){
				for(i=curParameters.length-1;i<=ques_count;i++){
					$('#tblParameters').append(
						"<tr class='rowParameters'><td class='colRadif'>"+(i+1)+"</td>"
							+"<td class='colName'>"
							+"	<input name='param_name' type='text' style='vertical-align: top;' value='پارامتر"+(i+1)+"'>"
							+"</td>"
							+"<td width='65%' class='colCondition'  nowrap='nowrap'>"
							+"	<input name='param_condition' type='text' style='vertical-align: top;width: 95%'>"
							+"	<img src='bireport/bimdx/list.png' title='لیست' width='20px' height='20px' style='cursor: pointer;' onclick='param_condition_click(this)' />"
							+"</td>"
							+"<td class='colDefault'  nowrap='nowrap'>"
							+"	<input id='txtParamDefaultValue' name='param_default_value' type='hidden' style='vertical-align: top;'>"
							+"	<input id='txtParamDefaultCaption' name='param_default_caption' type='text' style='vertical-align: top;'>"
							+"	<img src='bireport/bimdx/list.png' title='لیست' width='20px' height='20px' style='cursor: pointer;'  onclick='param_default_click(this)' />"
							+"</td>"
						+"</tr>"						
					);
				}
			}
			
			
			return true;
		}
		function generate_filter(){
			var curParameters = $('#tblParameters tr.rowParameters');

			var curFilterItems = $('#tblFilter #row td');
			
			for(i=curFilterItems.length-1;i>=0;i--){
				$( curFilterItems[i] ).remove();
			}
			
			var curFilter = $('#tblFilter #row');
			for(i=curParameters.length-1;i>=2;i--){
				$('#tblFilter #row').prepend(""
					+"<td nowrap='nowrap'>"
					+"	<input type='text' name='filter_name' class='headerText'  value='"+ $(curParameters[i]).children(".colName").children("input").val() +"' style='text-decoration: none;border: none;background: transparent;width: 20%;text-align:left;'>"
					+"	<input type='hidden' name='filter_mdx'  class='clsFilterCondition' value='"+ $(curParameters[i]).children(".colCondition").children("input").val() +"'>"
					+"	<input type='hidden' name='filter_value' class='clsFilterValue' value='"+ $(curParameters[i]).children(".colDefault").children("input[@name='param_default_value']").val() +"'>"
					+"	<input type='text' readonly name='filter_caption' class='clsFilterCaption' value='"+ $(curParameters[i]).children(".colDefault").children("input[@name='param_default_caption']").val() +"'>"
					+"	<img src='bireport/bimdx/list.png' title='لیست' width='20px' height='20px' style='cursor: pointer;' onclick='filter_param_click(this)' />"
					+"</td>"
				);
			}
			return true;
		}
		function execute_click(){
			generate_params();
			generate_filter();
			if(validate_click()){
				
				generate_mdxquerycompleted();
				
				document.getElementById("frmAipBiMdx").submit();
			}
		}
		function sample_click(){
			var sample ="SELECT {[Measures].[مبلغ کل],[Measures].[مبلغ پایه]} on columns,"
		         +" {NONEMPTY( DESCENDANTS( [زمان].[سال-فصل-ماه2],"
		               + "?" +" ,SELF))}"
		               +" on rows"
		               +" from [حواله] where "+ "?";
			$('#txtMDXQueryNC').val(sample);
			generate_params();
			
			$($('#tblParameters tr.rowParameters')[2]).children(".colName").children("input").val('زمان');
			$($('#tblParameters tr.rowParameters')[2]).children(".colCondition").children("input").val('{سال=1,ماه=2,روز=3}');
			$($('#tblParameters tr.rowParameters')[2]).children(".colDefault").children("input[@name='param_default_value']").val('1');
			$($('#tblParameters tr.rowParameters')[2]).children(".colDefault").children("input[@name='param_default_caption']").val('سال');
			
			$($('#tblParameters tr.rowParameters')[3]).children(".colName").children("input").val('منطقه/ناحیه');
			$($('#tblParameters tr.rowParameters')[3]).children(".colCondition").children("input").val('[مشتری].[منطقه- ناحیه]');
			$($('#tblParameters tr.rowParameters')[3]).children(".colDefault").children("input[@name='param_default_value']").val('[مشتری].[منطقه- ناحیه].[همه]');
			$($('#tblParameters tr.rowParameters')[3]).children(".colDefault").children("input[@name='param_default_caption']").val('همه');
			
			generate_filter();
			
			generate_mdxquerycompleted();
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
		
		function selectedNodeChange4Query(x){
			insertAtCaret('txtMDXQueryNC',x);
		}
		function selectedNodeChangeFunctions(x){
			insertAtCaret('txtMDXQueryNC',$(x).attr('name'));
		}
		function insertAtCaret(areaId,text) { 
			var txtarea = document.getElementById(areaId); 
			var scrollPos = txtarea.scrollTop; 
			var strPos = 0; 
			var br = ((txtarea.selectionStart || txtarea.selectionStart == '0') ? "ff" : (document.selection ? "ie" : false ) ); 
			if (br == "ie") { 
				txtarea.focus(); 
				var range = document.selection.createRange(); 
				range.moveStart ('character', -txtarea.value.length); 
				strPos = range.text.length; 
			} else if (br == "ff") strPos = txtarea.selectionStart; 
			var front = (txtarea.value).substring(0,strPos); 
			var back = (txtarea.value).substring(strPos,txtarea.value.length); 
			txtarea.value=front+text+back; 
			strPos = strPos + text.length; 
			if (br == "ie") { 
				txtarea.focus(); 
				var range = document.selection.createRange(); 
				range.moveStart ('character', -txtarea.value.length); 
				range.moveStart ('character', strPos); 
				range.moveEnd ('character', 0); range.select(); 
			} else if (br == "ff") { 
				txtarea.selectionStart = strPos; 
				txtarea.selectionEnd = strPos; 
				txtarea.focus(); 
			} 
			txtarea.scrollTop = scrollPos; 
		} 

		function topTabbed(x){
			if($(x).attr('id')==='right' || $(x).attr('id')==='betweenr' ){
				$('#betweenr').css('color','yellow');
				$('#betweenl').css('color','black');
				$('#right').attr('src','images/tabbed/top/right-hover.png');
				$('#betweenr').css("background-image","url('images/tabbed/top/between-hover.png')");
				$('#betweenl').css("background-image","url('images/tabbed/top/between2.png')");
				$('#betweenright').attr('src','images/tabbed/top/left-hover.png');
				$('#left').attr('src','images/tabbed/top/left.png');
				$('#emptyBetween').attr('src','images/tabbed/top/between-right.png');
				$('#loadTree4SelectQueryFunctions').css('display','none');
				$('#loadTree4SelectQueryCubes').css('display','inline');
			} else {
				$('#betweenr').css('color','black');
				$('#betweenl').css('color','yellow');
				$('#right').attr('src','images/tabbed/top/right.png');
				$('#betweenr').css("background-image","url('images/tabbed/top/between2.png')");
				$('#betweenright').attr('src','images/tabbed/top/right-hover.png');
				$('#betweenl').css("background-image","url('images/tabbed/top/between-hover.png')");
				$('#betweenr').attr('src','images/tabbed/top/left-hover.png');
				$('#emptyBetween').attr('src','images/tabbed/top/left-hover.png');
				$('#loadTree4SelectQueryFunctions').css('display','inline');
				$('#loadTree4SelectQueryCubes').css('display','none');
			}
		} 		
	<%}// if("edit".equals(reqCode))  %>
	</script>
</head>

<%--<aip:skin type="ITEM" >--%>

					
<table align="center" cellpadding="0" cellspacing="0" width="1000">
<tr>
	<td class="headerTitleDiv"  align="center" colspan="2" valign="top" style="font-weight: bold;color: navy;">
	
	<% 
	//out.println("reqCode="+reqCode);
	if("edit".equalsIgnoreCase(reqCode)){ %>
	ساخت گزارش MDX
	<%} %>
		<br/>
		نام گزارش :<span id="reportTitle"> <%= (bimdxDTO==null||request.getParameter("pivotreportName")!=null)?UTF8.cnvUTF8(request.getParameter("pivotreportName")):bimdxDTO.getReportName() %></span> 
		<br/>
	</td>
</tr>
	<% if("edit".equalsIgnoreCase(reqCode)){ %>
<tr>
	<td align="left"  nowrap="nowrap">
		<div style="border:1px activeborder;border-style:outset;background: menu;" dir="ltr">
		<img src="bireport/bimdx/new.png" title="جدید"  style="cursor: pointer;" onclick="new_click();" />
		<img src="bireport/bimdx/open.png" title="بازکردن"  style="cursor: pointer;" onclick="open_click();" />
		<img src="bireport/bimdx/save.png" title="ذخیره"  style="cursor: pointer;" onclick="save_click();" />
		<img src="bireport/bimdx/saveAs.png" title="ذخیره به عنوان"  style="cursor: pointer;" onclick="save_as_click();" />
		<img src="bireport/bimdx/validate.png" title="بازبینی"  style="cursor: pointer;" onclick="validate_click()" />
		<img src="bireport/bimdx/execute.png" title="اجرا"  style="cursor: pointer;" onclick="execute_click()" />
		&nbsp;
		<img src="bireport/bimdx/sample.png" title="مثال" style="cursor: pointer;" onclick="sample_click()" />
		</div>
	</td>	
</tr>
	<%}// if("edit".equals(reqCode))  %>
<tr>
	<td align="left">
		<form id="frmAipBiMdx" action="<%= pivotFormAction %>" method="post" style="margin-bottom:0;">
			<input type="hidden" name="reqCode" id="frmAipBiMdx_reqCode" value="<%=request.getParameter("reqCode")%>"/>


			<input type="hidden" name="id" id="frmAipBiMdx_id" value="<%= bimdxDTO==null? request.getParameter("id") :bimdxDTO.getId() %>" />
			<input type="hidden" name="folderId" id="frmAipBiMdx_folderId" value="<%= bimdxDTO==null?"":bimdxDTO.getFolderId() %>" />
			<input type="hidden" name="reportName" id="frmAipBiMdx_reportName" value="<%= bimdxDTO==null?"":bimdxDTO.getReportName() %>" />
			<input type="hidden" name="isPublic"  id="frmAipBiMdx_isPublic" value="<%= (bimdxDTO==null || !bimdxDTO.getIsPublic())?"":"1" %>" />
			<input type="hidden" name="renameFolder"  id="renameFolder" value="" />
			
		<table id="tblParameters" width="100%"  cellpadding="0" cellspacing="0" border="1" dir="ltr" style="<%= ("edit".equalsIgnoreCase(reqCode))?"display:table;":"display:none;" %>">
			<tr class="rowParameters">
				<td colspan="3" width="100%" height="100%" valign="top">
					<%
						String mdxQueryNC = 	request.getParameter("mdxquerync");
						String mdxQuery = 	request.getParameter("mdxquery");
						if(mdxQuery==null){
							mdxQuery = request.getParameter("pivotMdxQuery");
							mdxQueryNC = 	request.getParameter("pivotMdxQueryNC");
						}
						mdxQueryNC = UTF8.cnvUTF8( NVL.getString( mdxQueryNC ) );
						mdxQuery = UTF8.cnvUTF8( NVL.getString( mdxQuery ) );
						
						if(bimdxDTO!=null){
							mdxQueryNC = bimdxDTO.getMdxQueryNC(); 
							mdxQuery = bimdxDTO.getMdxQuery(); 
						}
					 %>
					<textarea id="txtMDXQueryNC" name="mdxquerync" rows="*" cols="*" dir="ltr" style="width: 100%; height:100%;"><%= mdxQueryNC %></textarea>
					<textarea id="txtMDXQuery" name="mdxquery" rows="*" cols="*" dir="ltr" style="width: 100%;display: none;"><%= mdxQuery %></textarea>
				</td>
				<td width="160px" align="right" valign="top">
					<table dir="rtl" cellspacing="0" cellpadding="0" class="clsAIPTabbedTop" style="cursor: pointer;" >
						<tr>
							<td>
								<img src="images/tabbed/top/right.png" alt="" border="0" id="right" onclick="topTabbed(this);">
							</td>
							<td nowrap="nowrap" style="background-image: url('images/tabbed/top/between2.png');" id="betweenr" onclick="topTabbed(this);">
								نمودار مقادیر
							</td>
							<td>
								<img src="images/tabbed/top/between-right.png" alt="" border="0" id="betweenright" onclick="topTabbed(this);">
							</td>
							<td nowrap="nowrap" style="background-image: url('images/tabbed/top/between2.png');" id="betweenl" onclick="topTabbed(this);">
								نمودار توابع
							</td>

							<td>
								<img src="images/tabbed/top/between-right.png" alt="" border="0" id="emptyBetween" onclick="">
							</td>	
							<td>
								<img src="images/tabbed/top/left.png" alt="" border="0" id="emptyLeft" onclick="">
							</td>
						</tr>
					</table>
<%--					<% if("edit".equalsIgnoreCase(reqCode)){ %>--%>
								<div id="loadTree4SelectQueryCubes" align="center" style="overflow: hidden; display: none;">
									<aip:tree loader="/cubemeta.do?reqCode=dimensions&isForQuery=true" title="درخت ابعاد" id="treDimension4query"></aip:tree>
								</div>
								<div id="loadTree4SelectQueryFunctions" align="center" style="overflow: hidden; display: none;">
									<aip:tree loader="/mdxFunctions.do" title="درخت توابع" id="treDimension4Functions"></aip:tree>
								</div>	
<%--						<%} %>--%>
				</td>
			</tr>			
	<% 
			List<ReportCriteriaENT> criterias=null;
				try{

				if(bimdxDTO!=null){
					criterias=bimdxDTO.getCriterias();
				}else if(request.getParameterValues("param_name")!=null){
					String criteria_names[]=request.getParameterValues("param_name");
					String criteria_mdxs[]=request.getParameterValues("param_condition");
					String criteria_default_values[]=request.getParameterValues("param_default_value");
					String criteria_default_captions[]=request.getParameterValues("param_default_caption");
					
					if(criteria_names!=null && criteria_names.length>0){
						criterias = new ArrayList<ReportCriteriaENT>();
						for(int i=0;i<criteria_names.length;i++){
							ReportCriteriaENT criteria = new ReportCriteriaENT();

							criteria.setName( UTF8.cnvUTF8( criteria_names[i] ) );
							criteria.setCriteriaMdx( UTF8.cnvUTF8( criteria_mdxs[i] ) );
							criteria.setDefaultValue( UTF8.cnvUTF8( criteria_default_values[i] ) );
							criteria.setDefaultCaption( UTF8.cnvUTF8( criteria_default_captions[i] ) );
							
							criterias.add(criteria);
						}
						
					}
					
				}else if(session.getAttribute("aipbimdx_criterias")!=null){
					criterias=(List<ReportCriteriaENT>)session.getAttribute("aipbimdx_criterias");
				}
				session.setAttribute("aipbimdx_criterias",criterias);
				}catch(Exception ex){
					ex.printStackTrace();
				}

		if("edit".equalsIgnoreCase(reqCode)){ 
		%>
			<tr class="rowParameters">
				<th>ردیف</th>
				<th>نام</th>
				<th  nowrap="nowrap">شرط لیست انتخابی</th>
				<th  nowrap="nowrap">&lt;پیش فرض لیست</th>
			</tr>
			<logic:notEmpty  name="aipbimdx_criterias">
				<logic:iterate id="criteria" name="aipbimdx_criterias" indexId="indexId" type="aip.common.report.ReportCriteriaENT">
					<tr class="rowParameters"><td class='colRadif'><%= indexId+1 %></td>
						<td class='colName'>
						 	<input name='param_name' type='text' style='vertical-align: top;' value='<%= criteria.getName() %>'>
						</td>
						<td width='65%' class='colCondition'  nowrap='nowrap'>
							<input name='param_condition' type='text' style='vertical-align: top;width: 95%' value="<%= criteria.getCriteriaMdx() %>" >
							<img src='bireport/bimdx/list.png' title='لیست' width='20px' height='20px' style='cursor: pointer;' onclick='param_condition_click(this)' />
						</td>
						<td class='colDefault'  nowrap='nowrap'>
							<input id='txtParamDefaultValue' name='param_default_value' type='hidden' style='vertical-align: top;' value="<%= criteria.getDefaultValue() %>">
							<input id='txtParamDefaultCaption' name='param_default_caption' type='text' style='vertical-align: top;' value="<%= criteria.getDefaultCaption() %>" >
							<img src='bireport/bimdx/list.png' title='لیست' width='20px' height='20px' style='cursor: pointer;'  onclick='param_default_click(this)' />
						</td>
					</tr>						
				</logic:iterate>
			</logic:notEmpty>
	<%}  %>
		</table>
		
		</form>
	</td>
</tr>
<tr>	
	<td align="left"  valign="top" >
		<% 
			String mdxquery =  request.getParameter("mdxquery") ;
			mdxquery=UTF8.cnvUTF8(mdxquery);
			String mdxquerync =  request.getParameter("mdxquerync") ;
			mdxquerync=UTF8.cnvUTF8(mdxquerync);
			
			if(bimdxDTO!=null){
				mdxquery=bimdxDTO.getMdxQuery();
				mdxquerync=bimdxDTO.getMdxQueryNC();
			}

			if(mdxquery!=null && !"".equals(mdxquery))
			{
				System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

				AIPPivotParam param = new  AIPPivotParam();
				param.setFormAction( pivotFormAction );
				param.setMdxQuery( mdxquery );
				param.setMdxQueryNC( mdxquerync );


				param.setQueryName("qryAIPBIMDX");
				param.setRefresh(true);
				request.setAttribute("AIPPivotParam",param);
			}
			
			
		
		 %>
		<jsp:include page="/aipconfig/pivot/aippivot.jsp"></jsp:include>
	</td>
</tr>

<tr><td >	

<div >
<aip:skin type="HEAD">
	<form id="frmAipBiFilter" action="<%= pivotFormAction %>" method="post">
			<input type="hidden" name="reqCode"  value="<%=request.getParameter("reqCode")%>"/>
			<input type="hidden" name="pivotReportId" id="frmAippivot_reportId" value="<%= request.getParameter("reportId")==null? request.getParameter("pivotReportId") :request.getParameter("reportId") %>" />
			
 		<textarea id="txtMDXQueryNC_Filter" name="mdxquerync" rows="*" cols="*" dir="ltr" style="display: none;"><%= mdxQueryNC %></textarea>
		<textarea id="txtMDXQuery_Filter" name="mdxquery" rows="*" cols="*" dir="ltr" style="display: none;"><%= mdxQuery %></textarea>
		<table id="tblFilter" border="0" dir="rtl">
			<tr id="row">

			<%
				List<ReportCriteriaENT> filters=null;

				try{

				if(bimdxDTO!=null){
					filters=bimdxDTO.getCriterias();
					//out.print(">>>>>>>>>>>>>>>>:bimdxDTO");
				}else if(request.getParameterValues("filter_name")!=null && request.getParameterValues("filter_name").length>0){
					String criteria_names[]=request.getParameterValues("filter_name");
					String criteria_mdxs[]=request.getParameterValues("filter_mdx");
					String criteria_default_values[]=request.getParameterValues("filter_value");
					String criteria_default_captions[]=request.getParameterValues("filter_caption");
					
					if(criteria_names!=null && criteria_names.length>0){
						filters = new ArrayList<ReportCriteriaENT>();
						for(int i=0;i<criteria_names.length;i++){
							ReportCriteriaENT criteria = new ReportCriteriaENT();

							criteria.setName( UTF8.cnvUTF8( criteria_names[i] ) );
							criteria.setCriteriaMdx( UTF8.cnvUTF8( criteria_mdxs[i] ) );
							criteria.setDefaultValue( UTF8.cnvUTF8( criteria_default_values[i] ) );
							criteria.setDefaultCaption( UTF8.cnvUTF8( criteria_default_captions[i] ) );
							
							filters.add(criteria);
						}
						
					}
					
					//out.print(">>>>>>>>>>>>>>>>:filter_names");
				}else if( session.getAttribute("aipbimdx_filters")!=null ){
					filters=(List<ReportCriteriaENT>)session.getAttribute("aipbimdx_filters");
				}else if(criterias!=null && criterias.size()>0){
					filters=criterias;
					//out.print(">>>>>>>>>>>>>>>>:criterias");
				}
				session.setAttribute("aipbimdx_filters",filters);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			
			 %>

				<logic:notEmpty name="aipbimdx_filters">
					<logic:iterate id="criteria" name="aipbimdx_filters" indexId="indexId" type="aip.common.report.ReportCriteriaENT">
						<td nowrap='nowrap'>
							<input type="text" name='filter_name' class='headerText' value='<%= criteria.getName() %>' style='text-decoration: none;border: none;background: transparent;width: 20%;text-align:left;'>
							<input type='hidden' name='filter_mdx'  class='clsFilterCondition' value='<%= criteria.getCriteriaMdx() %>'>
							<input type='hidden' name='filter_value' class='clsFilterValue' value='<%= criteria.getDefaultValue() %>'>
							<input type='text' readonly name='filter_caption' class='clsFilterCaption' value='<%= criteria.getDefaultCaption() %>'>
					
	<input type="hidden" name="pivotIsFirstCall"  value="<%=NVL.getString(getAttributeOrParameter1(request,"pivotIsFirstCall"))%>"/>
					
							<img src='bireport/bimdx/list.png' title='لیست' width='20px' height='20px' style='cursor: pointer;' onclick='filter_param_click(this)' />
						</td>
					</logic:iterate>
				</logic:notEmpty>
				<th>
					<input type="image" value="تایید" onclick="filter_click();" style=" background-image: url('bireport/bimdx/filter.png');width: 108px;height: 24px;border-style: none;cursor: pointer;text-align: center; "  > 
				</th>
			</tr>
		</table>
	
	</form>
</aip:skin>
</div>

<%--	<jsp:include page="sale_filter.jsp"></jsp:include>--%>
</td></tr>

</table>

<%--</aip:skin>--%>

<% if("edit".equalsIgnoreCase(reqCode)){ %>
<aip:dialog styleId="dlgDimension" loader="" title="" style="simple" screenPosition="center" onOKClick="dlgDimension_OK();" img="">
	<div style="height: 200px;width:200px; overflow: auto;">
	<aip:tree loader="/cubemeta.do?reqCode=dimensions" title="درخت ابعاد" id="treDimension"></aip:tree>
	</div>
</aip:dialog>
<% }// if("edit".equalsIgnoreCase(reqCode)){ %>

<aip:dialog styleId="dlgFilter" loader="" title="" style="simple" screenPosition="center" onOKClick="dlgFilter_OK();"  img="">
</aip:dialog>
<aip:dialog styleId="dlgSet" loader="" title="" style="simple" screenPosition="center" onOKClick="dlgSet_OK();"  img="">
	<aip:tree loader="" title="درخت مجموعه" id="treSet"></aip:tree>
</aip:dialog>
<aip:dialog styleId="dlgReportFolder" loader="jsp/loader/treeLoaderForFolders.jsp?reportName=<%=bimdxDTO.getReportName() %>&isPublic=<%=bimdxDTO.getIsPublic() %>" title="" style="simple" screenPosition="center" onOKClick="dlgReportFolder_OK();"  img="">
</aip:dialog>
<input id="frmFolder_reportName" type="hidden" value="<%=bimdxDTO==null?"":bimdxDTO.getReportName()%>">
<input id="frmFolder_reportFolderId" type="hidden" value="<%=bimdxDTO==null?"":bimdxDTO.getFolderId()%>">
<input id="frmFolder_reportId" name="frmFolder_reportId" type="hidden" value="<%=bimdxDTO==null?"":bimdxDTO.getId()%>">
<input id="frmFolder_isPublic" type="hidden" value="<%=(bimdxDTO==null || !bimdxDTO.getIsPublic())?"":"true" %>">
<aip:dialog styleId="dlgNewFolder" loader="" title="مدیریت پوشه" style="simple" screenPosition="center" onOKClick="dlgFolder_OK();"  img="">
	<form action="aipbimdx.do" id="newFolderForm">
		<input id="frmFolder_reqCode" type="hidden" name="reqCode" value="newFolder">
		<input id="frmFolder_folderId" type="hidden" name="folderId" >
		نام جدید:<input id="frmFolder_name" type="text" name="newFolderInDialog"  /><br/>
		عمومی:<input id="frmFolder_isPublic" type="radio" name="isPublic" value="true" checked="checked"/>
		شخصی:<input id="frmFolder_isPrivate" type="radio" name="isPublic"  value="false"  />
	</form>
</aip:dialog>
<!-- salehzadeh start -->
<script type="text/javascript">
function editFolder(){
	if($('#frmFolder_folderId').val() === ""){
		alert('هیچ پوشه ای انتخاب نشده');
	}else{
		$('#frmFolder_reqCode').val('editFolder'); 
		showDialog('dlgNewFolder','center');
	}
}
function dlgFolder_OK(){
	try{
		var options = {
		    success: function(req) {
		        alert("عملیات با موفقیت انجام گردید");
		    }
		    ,error: function(req){
		    	alert("اشکال در انجام عملیات");
		    }
		   };
			$('#newFolderForm').ajaxSubmit(options);
		}catch(e){
			alert(e);
		}
		$('#dlgReportFolder').hide();
		save_click();
		
}
</script>

<div style="position: absolute;top:100px;right:0px;z-index: 1;">
	<jsp:include page="/layout/navigation.jsp"></jsp:include>
</div>