<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="com.tonbeller.jpivot.chart.ChartComponent"%>

<%@page import="aip.util.NVL"%>
<%@page import="aip.util.UTF8"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<link rel="shortcut icon" href="aip-co.ico">
		<link rel="icon" type="image/ico" href="aip-co.ico">
		<link href="css/niopdc.css" rel="stylesheet" type="text/css"/>
		<link href="css/dialogStyles.css" type="text/css" rel="stylesheet" />
        <link href="css/popupStyles.css" rel="stylesheet" type="text/css"/>
		<link href="css/navigation.css" rel="stylesheet" type="text/css" >
		<link href="css/menu.css" rel="stylesheet" type="text/css" >
		<link  href="css/commonList.css" rel="stylesheet" type="text/css" />
		<script src="js/common/aip.commonList.js" type="text/javascript"></script>
		<script src="js/common/jquery-1.2.6.pack.js" type="text/javascript"></script>
		<script src="js/common/aip.niopdc.common.js" type="text/javascript"></script>			
		<script src="js/common/aip.niopdc.menu.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/visualreport/aip.niopdcsellbi.visualreport.js"></script>

  <title>لیست گزارش ویژوال</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" type="text/css" href="jpivot/table/mdxtable.css">
  <link rel="stylesheet" type="text/css" href="jpivot/navi/mdxnavi.css">
  <link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
  <link rel="stylesheet" type="text/css" href="wcf/table/xtable.css">
  <link rel="stylesheet" type="text/css" href="wcf/tree/xtree.css">

  <script type="text/javascript">
	$(document).ready(function(){
		var selcetdIdsArray = new Array();
		selcetdIdsArray = $("#hiddenIDs").val().split(',');
		for (var i = 0 ; i < selcetdIdsArray.length ; i++) {
			$("input[id="+selcetdIdsArray[i]+"]").attr('checked', 'checked');
		}
	});
	function selectCheckBox(chk){
		var txtHiddenIDs = $("#hiddenIDs").val();
		if($(chk).attr("checked") === true) {
			$("#hiddenIDs").val( txtHiddenIDs + $(chk).val() + "," );
		} else {
			var tmp = txtHiddenIDs.replace($(chk).val()+"," , "");
			$("#hiddenIDs").val( tmp );
		}
	}
	</script>

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
					<td width="100%" valign="top">

	
<aip:skin type="BODY">
<%--<aip:skin type="ITEM">--%>
<%--	<aip:tree loader="/navigation.do" title="درخت فایل و گزارش" id="tree3"></aip:tree>--%>
<%--</aip:skin>--%>

	<form action="visualreport.do" method="POST"  id="visualReportForm">
		<input type="hidden" value="<%=NVL.getString(request.getAttribute("selectedIds"))%>" id="hiddenIDs" class="hiddenIDs" name="selectedIds"/>
		<input type="hidden" name="reqCode" id="reqCode"/>
		<div align="center" dir="rtl" id="listOfResult_Grid">
					<table class="forPrint">
						<tr class="forPrint">
							<th class="forPrint">انتخاب گزارش</th>
							<th class="forPrint">ردیف</th> 
							<th class="forPrint">نام گزارش</th>
							<th class="amrHidden forPrint">ویرایش</th>
							<th class="amrHidden forPrint">حذف</th>
						</tr>
						<logic:iterate  name="visualReportForm" id="dto" property="reportLST.dtos" type="aip.common.report.ReportDTO" indexId="rId">
							<tr class="forPrint">
								<td class="forPrint">
									<input type="checkbox" class="chkSelect" name="reportId" id="<%= dto.getId()%>" value="<%=dto.getId() %>" onclick="selectCheckBox(this);"/>
								</td>
								<td class="forPrint"><%=rId+1%></td>
								<td class="forPrint"><%=NVL.getString(dto.getQueryName())%></td>
								<td class="forPrint amrHidden">
									<a href="visualreport.do?reqCode=edit&reportId=<%=dto.getId()%>">
										<img src="images/edit_24.gif" alt="ویرایش" />
									</a>
<%--									<img src="images/edit_24.gif" class="visualReportEdit" title="ویرایش" id="<%=dto.getId()%>" style="cursor: pointer;" onclick="$('#reqCode').val('edit'); $('form#visualReportForm').submit();">--%>
								</td>
								<td class="forPrint amrHidden">
									<a href="visualreport.do?reqCode=delete&reportId=<%=dto.getId()%>">
										<img src= "images/delete_24.gif"  alt="حذف" />
									</a>
								</td>
								
							</tr>										
						</logic:iterate>					
					</table>
		</div>
		<div>
			<input type="submit" value="نمایش در صفحه"/>
			<input type="submit" value="نمایش در داشبورد"/>
			<input type="button" value="حذف"  onclick="$('#reqCode').val('deleteIds'); $('form#visualReportForm').submit();" />
			<input type="button" value="ویرایش" onclick="$('#reqCode').val('edit'); $('form#visualReportForm').submit();" />
			<input type="button" value="جدید" onclick="$('#reqCode').val('new');$('form#visualReportForm').submit();" />
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

