<%@ page session="true" contentType="text/html; charset=UTF-8" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link rel="shortcut icon" href="aip-co.ico">
		<link rel="icon" type="image/ico" href="aip-co.ico">
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body dir="rtl" background="images/background/background.png" id="pageLayout" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<table border="0" width="100%" cellspacing="0" align="center" cellpadding="0">
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
<%--				<jsp:include page="/layout/navigation.jsp"></jsp:include>--%>
			</td>
			<td width="100%" valign="top">





<jsp:include page="/bireport/aipbifolder.jsp"></jsp:include>





				</td>
			</tr>
			<tr align="center">
				<td colspan="2">
					<jsp:include page="/layout/footer.jsp"></jsp:include>
				</td>
			</tr>
		</table>
	</body>
</html>

