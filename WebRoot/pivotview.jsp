<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="aip.jpivot.AIPPivotParam"%>
<%@page import="aip.util.AIPUtil"%>

<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld" %>

<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>
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
		<link href="css/common.css" rel="stylesheet" type="text/css"/>

		<script src="js/common/jquery-1.2.6.pack.js" type="text/javascript"></script>
		<script src="js/common/aip.niopdc.common.js" type="text/javascript"></script>			
		<script src="js/common/aip.niopdc.menu.js" type="text/javascript"></script>
		<script src="js/common/aip.niopdcsell.common.js" type="text/javascript"></script>
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


<%
	if(request.getAttribute("AIPPivotParam")==null){
		if(session.getAttribute("AIPPivotParam")!=null){
			request.setAttribute("AIPPivotParam",session.getAttribute("AIPPivotParam"));
		}else{
			out.print("اشکال");
		}
	}
	AIPPivotParam param = (AIPPivotParam )request.getAttribute("AIPPivotParam");
	param.setMdxQuery(param.getMdxQuery().replaceAll("%26","&"));
	AIPUtil.printObject(param);

 %>
					
					
<aip:skin type="BODY">
	<div align="center" class="headerTitleDiv"><%= param.getQueryName() %></div>

 	<jsp:include page="/layout/pivot.jsp"></jsp:include>
 	<div>
	 	<input type="button" value="بازگشت" align="left" onclick="window.location = '<%=param.getLoader() %>'" />
 	</div>

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

