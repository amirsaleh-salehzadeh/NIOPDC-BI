<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="aip.jpivot.AIPPivotParam"%>
<%@page import="aip.util.NVL"%>
<%@page import="java.util.List"%>
<%@page import="aip.common.visualreport.VisualReportDetailDTO"%>
<%@page import="aip.xmla.AIPOlap"%>
<%@page import="aip.common.report.ReportDetailENT"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<jsp:useBean id="visualReportForm" type="aip.niopdc.sellbi.struts.form.visual.VisualReportForm" scope="request" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="shortcut icon" href="aip-co.ico">
		<link rel="icon" type="image/ico" href="aip-co.ico">
		<link rel="stylesheet" type="text/css" href="css/niopdc.css"/>
		<link rel="stylesheet" type="text/css" href="css/dialogStyles.css"/>
        <link rel="stylesheet" type="text/css" href="css/popupStyles.css"/>
		<link rel="stylesheet" type="text/css" href="css/navigation.css" >
		<link rel="stylesheet" type="text/css" href="css/menu.css">
		<link rel="stylesheet" type="text/css" href="css/common.css"/>
		<link rel="stylesheet" type="text/css" href="css/visual/visual.css"/>


		<link rel='stylesheet' type='text/css' href='css/aiptree-simple.css' />
		<link rel='stylesheet' type='text/css' href='css/tree_xp_style_rtl.css' />
		<link rel="stylesheet" type="text/css" href="css/commonList.css"/>
		<link rel="stylesheet" type="text/css" href="jpivot/table/mdxtable.css">
		<link rel="stylesheet" type="text/css" href="jpivot/navi/mdxnavi.css">
		<link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
		<link rel="stylesheet" type="text/css" href="wcf/table/xtable.css">
		<link rel="stylesheet" type="text/css" href="wcf/tree/xtree.css">

		<script type="text/javascript" src="jquery/jquery.js"></script>
		<script type="text/javascript" src="jquery/jquery.form.js"></script>
		<script type="text/javascript" src="jquery/ui/ui.core.packed.js"></script>
		<script type="text/javascript" src="jquery/ui/ui.draggable.packed.js"></script>
		<script type="text/javascript" src="jquery/jquery.tree.js"></script>
	
	
	
	
	<script type="text/javascript">
	function saveFunction(){ 
			$('#reqCode').val('save');
		if($('#reportName').val() === null || $('#reportName').val() === ""){
			alert("گزینه نام گزارش حاوی اطلاعات نمی باشد")
		} else {
			$('form#visualReportForm').submit();
		}
	}
	</script>
		<script type="text/javascript" src="js/common/aip.niopdc.common.js"></script>			
		<script type="text/javascript" src="js/common/aip.niopdc.menu.js"></script>
		<script type="text/javascript" src="js/visualreport/aip.niopdcsellbi.visualreport.js"></script>
		<script type="text/javascript" src="js/aiptree.js"></script>

		  <title>ساخت گزارش MDX</title>
<%--		<script type="text/javascript">--%>
<%--		</script>--%>
	</head>
	
	<body dir="rtl" background="images/background/background.png" id="pageLayout" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<table border="0" width="100%" cellspacing="0" align="center" cellpadding="0">
			<tbody>
				<tr align="center">
					<td colspan="2" >
						<jsp:include page="/layout/header.jsp"></jsp:include>
					</td>
				</tr>
				<tr align="center"> 
					<td colspan="2" >
						<jsp:include page="/layout/menu.jsp"></jsp:include>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<jsp:include page="/layout/banner.jsp"></jsp:include>
					</td>
				</tr>
				<tr align="center">
					<td valign="top" style="padding-right: 0px; " align="right">
						<jsp:include page="/layout/navigation.jsp"></jsp:include>
					</td>
					<td width="" valign="top">

<aip:skin type="BODY">
	<form action="visualreport.do" method="get"  id="visualReportForm" name="visualReportForm">



		<input type="hidden" name="reqCode" id="reqCode"/>
		<html:hidden name="visualReportForm" property="reportENT.id"/>

		<bean:define id="form" name="visualReportForm" type="aip.niopdc.sellbi.struts.form.visual.VisualReportForm"/>
       
        
<div>

<table border="0" width="100%">
	<tr align="center">
		
		<td colspan="4" dir="rtl" nowrap="nowrap" align="right">
		
			نام گزارش:<html:text name="visualReportForm" styleId="reportName" property="reportENT.queryName" size="70"/>
			<html:hidden name="visualReportForm" property="reportENT.query" style="width: 100%;" />
			نوع گزارش:  عمومی:<html:checkbox name="visualReportForm" property="reportENT.isPublic" styleId="isPublic"></html:checkbox>
			<html:hidden name="visualReportForm" property="reportENT.isVisual" styleId="isVisual" />
			<html:hidden name="visualReportForm" property="parameters" styleId="parameters" />
		</td>
	</tr>

	<tr class="viewTR">
		<td>
		
			<table align="right" border="0">
				<tr>
				<td class="AddToType">
				<aip:skin type="ITEM">
					افزودن به:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					سطر<input type="radio" name="addType" value="1" <% if(NVL.getInt(request.getParameter("addType"),1)==1){ out.print("checked='checked'"); } %>>
					ستون<input type="radio" name="addType" value="2" <% if(NVL.getInt(request.getParameter("addType"))==2){ out.print("checked='checked'"); } %>>
					فیتر<input type="radio" name="addType" value="3" <% if(NVL.getInt(request.getParameter("addType"))==3){ out.print("checked='checked'"); } %>>
					داده<input type="radio" name="addType" value="4" <% if(NVL.getInt(request.getParameter("addType"))==4){ out.print("checked='checked'"); } %>>
				</aip:skin>
				</td>
				</tr>				
				<tr>
				<td>
				<aip:skin type="ITEM">
					<aip:tree loader="/memberswithcheck.do?reqCode=dimensions" title="درخت اطلاعات" id="tree2"></aip:tree>
				</aip:skin>
				</td></tr>
			</table>
		</td>

		<td valign="top">
		<aip:skin type="ITEM1">
		<table width="100%" border="0" align="center">
			<tr>
				<td class="rowTD" align="center" style="font-family: Tahoma, Verdana, Arial, Helvetica, sans-serif; font-size: 12px;font-weight: bold;">سطر
					<%
					if(visualReportForm.getReportENT().getReportDetailENTs().size() != 0){
				%>
						<%
							List<ReportDetailENT> list = visualReportForm.getReportENT().getReportDetailENTs();
						%>
						<%
							for(int i=0;i<list.size();i++){
						%>
						<%
							if(list.get(i).getType().equalsIgnoreCase("Row")) {
						%>
							<%
								String name = AIPOlap.getHierarchyName(AIPOlap.getCubeName(),list.get(i).getDimension()); 
												   String id = list.get(i).getDimension();
							%>
								<head>
									<script type="text/javascript">
										$(document).ready(function () {
											
											//alert(<%=id%>);
											addVisualFormField4Jsp('<%=id%>','<%=name%>','1','<%=list.get(i).getSelectedMembers()%>');
										});
									</script>
									
								</head>
							<%
								} }
							%>
						<%
							}
						%>
			<aip:dialog styleId="dlgRow" loader="" img="" title="" style="simple" 
				 toolbarExtra="<input type='button' value='حذف انتخابها' onclick='clearSelectedIds(\"treRowMembersLoader\");'>"
				 onOKClick="dlgRow_OK();">
			</aip:dialog>
			<div id="txtRowsSpan" style="width:200px;">
				<%=NVL.getString( request.getAttribute("selectedRowNames") )%>
			</div>
			
		</td></tr></table>
		</aip:skin>
		</td>		
		
		<td valign="top">
		<aip:skin type="ITEM1">
		<table width="100%" border="0" align="center"><tr>
		<td class="columnTD" align="center" style="font-family: Tahoma, Verdana, Arial, Helvetica, sans-serif; font-size: 12px;font-weight: bold;">ستون
			<%
			if(visualReportForm.getReportENT().getReportDetailENTs().size() != 0){
		%>
				<%
					List<ReportDetailENT> list = visualReportForm.getReportENT().getReportDetailENTs();
				%>
				<%
					for(int i=0;i<list.size();i++){
				%>
				<%
					if(list.get(i).getType().equalsIgnoreCase("Column")) {
				%>
					<%
						String name = AIPOlap.getHierarchyName(AIPOlap.getCubeName(),list.get(i).getDimension()); 
								   String id = list.get(i).getDimension();
					%>
						<head>
							<script type="text/javascript">
								$(document).ready(function () {
									
									//alert(<%=id%>);
									addVisualFormField4Jsp('<%=id%>','<%=name%>','2','<%=list.get(i).getSelectedMembers()%>');
								});
							</script>
						</head>
						
				<%
											} }
										%>
			<%
				}
			%>
			
			
			<aip:dialog styleId="dlgColumn" loader="" img="" title="" style="simple" 
				 toolbarExtra="<input type='button' value='حذف انتخابها' onclick='clearSelectedIds(\"treColumnMembersLoader\");'>"
				 onOKClick="dlgColumn_OK();">
			</aip:dialog>
			<div id="txtColumnsSpan" style="width:200px;">
				<%=NVL.getString( request.getAttribute("selectedColumnNames") )%>
			</div>
		</td></tr></table>
		</aip:skin>
		</td>
		<td valign="top">
		<aip:skin type="ITEM1">
		<table width="100%" border="0"><tr> 
		<td class="filterTD" align="center" style="font-family: Tahoma, Verdana, Arial, Helvetica, sans-serif; font-size: 12px;font-weight: bold;">فیلتر

			<%
 			if(visualReportForm.getReportENT().getReportDetailENTs().size() != 0){
 		%>
				<%
					List<ReportDetailENT> list = visualReportForm.getReportENT().getReportDetailENTs();
				%>
				<%
					for(int i=0;i<list.size();i++){
				%>
				<%
					if(list.get(i).getType().equalsIgnoreCase("Filter")) {
				%>
					<%
						String name = AIPOlap.getHierarchyName(AIPOlap.getCubeName(),list.get(i).getDimension()); 
								   String id = list.get(i).getDimension();
					%>
						
							<script type="text/javascript">
								$(document).ready(function () {
									
									//alert(<%=id%>);
									addVisualFormField4Jsp('<%=id%>','<%=name%>','3','<%=list.get(i).getSelectedMembers()%>');
								});
							</script>
				<%
					} }
				%>
			<%
				}
			%>

			<aip:dialog styleId="dlgFilter" loader="" img="" title="" style="simple" 
				 toolbarExtra="<input type='button' value='حذف انتخابها' onclick='clearSelectedIds(\"treFilterMembersLoader\");'>"
				 onOKClick="dlgFilter_OK();">
			</aip:dialog>
			<div id="txtFiltersSpan" style="width:200px;">
				<%=NVL.getString( request.getAttribute("selectedFilterNames") )%>
			</div>
		</td></tr>

		</table>		
		</aip:skin>
		</td>

		<td valign="top">		<aip:skin type="ITEM1">
		<table width="100%" border="0"><tr> 
		<td class="measureTD" align="center" style="font-family: Tahoma, Verdana, Arial, Helvetica, sans-serif; font-size: 12px;font-weight: bold;">داده
			
			<%
 			if(visualReportForm.getReportENT().getReportDetailENTs().size() != 0){
 		%>
				<%
					List<ReportDetailENT> list = visualReportForm.getReportENT().getReportDetailENTs();
				%>
				<%
					for(int i=0;i<list.size();i++){
				%>
				<%
					if(list.get(i).getType().equalsIgnoreCase("Measure")) {
				%>
					<%
						String name = AIPOlap.getHierarchyName(AIPOlap.getCubeName(),list.get(i).getDimension()); 
								   String id = list.get(i).getDimension();
					%>
						<head>
							<script type="text/javascript">
								$(document).ready(function () {
									
									//alert(<%=id%>);
									addVisualFormField4Jsp('<%=id%>','<%=name%>','4','<%= list.get(i).getSelectedMembers() %>');
								});
							</script>
						
				<%} } %>
			<%} %>
			
			<aip:dialog styleId="dlgMeasure" loader="" img="" title="" style="simple" 
				 toolbarExtra="<input type='button' value='حذف انتخابها' onclick='clearSelectedIds(\"treMeasureMembersLoader\");'>"
				 onOKClick="dlgMeasure_OK();">
			</aip:dialog>
			<div id="txtMeasuresSpan" style="width:200px;">
				<%= NVL.getString( request.getAttribute("selectedMeasureNames") ) %>
			</div>
		</td></tr></table></aip:skin>
		</td>


	</tr>
</table>
<div id="optionsDiv" align="center" class="inputText">
	
	<html:hidden name="visualReportForm" property="selectedRows" styleId="txtRowsID" />
	<input type="hidden" id="txtRows" name="selectedRowNames" value='<%= NVL.getString( request.getAttribute("selectedRowNames") ) %>' />	

	<html:hidden name="visualReportForm" property="selectedColumns" styleId="txtColumnsID" />
	<input type="hidden" id="txtColumns" name="selectedColumnNames" value='<%= NVL.getString( request.getAttribute("selectedColumnNames") ) %>'  />	

	<html:hidden name="visualReportForm" property="selectedFilters" styleId="txtFiltersID" />
	<input type="hidden" id="txtFilters" name="selectedFilterNames" value='<%= NVL.getString( request.getAttribute("selectedFilterNames") ) %>'  />

	<html:hidden name="visualReportForm" property="selectedMeasures" styleId="txtMeasuresID" />
	<input type="hidden" id="txtMeasures" name="selectedMeasureNames" value='<%= NVL.getString( request.getAttribute("selectedMeasureNames") ) %>'  />	

</div>	

</div>

		<div align="left">

			<br/>
			<input type="button" value="ذخیره" onclick="saveFunction();" />
			<input type="button" value="اجرا" onclick="$('#reqCode').val('execute'); $('form#visualReportForm').submit();" />
			<input type="button" value="بازگشت" onclick="$('form#visualReportForm').submit();" />
		</div>		
		
		
	</form>
</aip:skin>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2"><%--						<tiles:insert attribute="footer" />--%>
						<jsp:include page="/layout/footer.jsp"></jsp:include>
					
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>

