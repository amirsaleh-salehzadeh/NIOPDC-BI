<%@ page session="true" contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>

<%@ page language="java" pageEncoding="utf-8"%>
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
<%--  <title></title>--%>
<%--  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>
<%--  <link rel="stylesheet" type="text/css" href="jpivot/table/mdxtable.css">--%>
<%--  <link rel="stylesheet" type="text/css" href="jpivot/navi/mdxnavi.css">--%>
<%--  <link rel="stylesheet" type="text/css" href="wcf/form/xform.css">--%>
<%--  <link rel="stylesheet" type="text/css" href="wcf/table/xtable.css">--%>
<%--  <link rel="stylesheet" type="text/css" href="wcf/tree/xtree.css">--%>

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
<%--						<jsp:include page="/layout/menu.jsp"></jsp:include>--%>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
<%--						<jsp:include page="/layout/banner.jsp"></jsp:include>--%>
					</td>
				</tr>
				<tr align="center">
					<td valign="top" style="padding-right: 0px; " align="right">
						<jsp:include page="/layout/navigation.jsp"></jsp:include>
					</td>
					<td width="100%" valign="top">


<aip:skin type="HEAD">
<form action="saletrendinlocation1.do" method="get">
	<input type="hidden" name="reqCode" value="filter"/>
	<html:hidden name="saletrendinlocation1Form" property="defaultMeasureInt"/>
	<html:select name="saletrendinlocation1Form" property="defaultLocation" >
		<option/>
		<html:optionsCollection name="saletrendinlocation1Form" property="locationCombos" label="memberCaption" value="memberNum" />
	</html:select>
	
	سال<html:radio name="saletrendinlocation1Form" property="defaultType" value="1"/>
	فصل<html:radio name="saletrendinlocation1Form" property="defaultType" value="2"/>
	ماه<html:radio name="saletrendinlocation1Form" property="defaultType" value="3"/>
	روز<html:radio name="saletrendinlocation1Form" property="defaultType" value="4"/>
		
  		<input type="submit" value="تایید">
</form>
</aip:skin>
					
					
<aip:skin type="BODY">
	<div align="center" class="headerTitleDiv">گزارش فروش فرآورده ها در یک دوره زمانی</div>


 <jsp:include page="/layout/pivot.jsp"></jsp:include>

 
</aip:skin>


					</td>
				</tr>
				<tr align="center">
					<td colspan="2"><br><%--						<tiles:insert attribute="footer" />--%>
						<jsp:include page="/layout/footer.jsp"></jsp:include>
					
					<br></td>
				</tr>
			</tbody>
		</table>
	</body>
</html>

