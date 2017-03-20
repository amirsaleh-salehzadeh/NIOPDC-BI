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

		<script src="js/common/jquery-1.2.6.pack.js" type="text/javascript"></script>
		<script src="js/common/aip.niopdc.common.js" type="text/javascript"></script>			
		<script src="js/common/aip.niopdc.menu.js" type="text/javascript"></script>

<%--  <title></title>--%>
<%--  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">--%>
  <link rel="stylesheet" type="text/css" href="jpivot/table/mdxtable.css">
  <link rel="stylesheet" type="text/css" href="jpivot/navi/mdxnavi.css">
  <link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
  <link rel="stylesheet" type="text/css" href="wcf/table/xtable.css">
  <link rel="stylesheet" type="text/css" href="wcf/tree/xtree.css">

	</head>
	
	
	<body dir="rtl" background="images/background/background.png" id="pageLayout" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<table border="0" width="100%" cellspacing="0" align="center" cellpadding="0">
			<tbody>
				<tr align="center">
					<td colspan="2" >
						<tiles:insert attribute="header" />
					</td>
				</tr>
				<tr align="center"> 
					<td colspan="2" >
						<tiles:insert attribute="menu" />
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<tiles:insert attribute="banner" />
					</td>
				</tr>
				<tr align="center">
					<td valign="top" style="padding-right: 0px; " align="right">
						<tiles:insert attribute="navigation"/>
					</td>
					<td width="100%" valign="top">
						<table class="rr_Table" id="bodyTable" border="0" cellspacing="0" cellpadding="0" width="99%">
							<tr>
								<td class="rr_BASE_top_left">&nbsp;</td>
								<td class="rr_BASE_top_top">&nbsp;</td>
								<td class="rr_BASE_top_right">&nbsp;</td>
							</tr>
							<tr>
								<td class="rr_BASE_left_left">&nbsp;</td>
								<td class="rr_BASE_center">
									<tiles:insert attribute="body"/>
								</td>
								<td class="rr_BASE_right_right">&nbsp;</td>
							</tr>
							<tr>
								<td class="rr_BASE_bottom_left">&nbsp;</td>
								<td class="rr_BASE_bottom_bottom">&nbsp;</td>
								<td class="rr_BASE_bottom_right">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<tiles:insert attribute="footer" />
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>