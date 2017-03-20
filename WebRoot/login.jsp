<%@ page session="true" contentType="text/html; charset=UTF-8" %>

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
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" type="text/css" href="jpivot/table/mdxtable.css">
  <link rel="stylesheet" type="text/css" href="jpivot/navi/mdxnavi.css">
  <link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
  <link rel="stylesheet" type="text/css" href="wcf/table/xtable.css">
  <link rel="stylesheet" type="text/css" href="wcf/tree/xtree.css">

	<style>
	<!--
	.clsBoldText{
		font-weight: bold;
		color: blue;
	}
	.clsHeadText{
		font-family: Tahoma, Arial, Helvetica, sans-serif;
		font-size: 20;
		color: maroon;
	}
	-->
	</style>

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


					
						<table class="rr_Table" id="bodyTable" border="0" cellspacing="0" cellpadding="0" >
							<tr>
								<td class="rr_BASE_top_left">&nbsp;</td>
								<td class="rr_BASE_top_top">&nbsp;</td>
								<td class="rr_BASE_top_right">&nbsp;</td>
							</tr>
							<tr>
								<td class="rr_BASE_left_left">&nbsp;</td>
								<td class="rr_BASE_center">




<%--	<aip:headerTitle title="صفحه ورود کاربر"></aip:headerTitle>--%>

<div align="center">
	<div align="center" >
		<span style="color: red;" id="messageSpan"></span>
	</div>
	<form method="POST" action="j_security_check" dir="rtl" id="loginForm">
		<br>
		<aip:skin type="ITEM">
			<table>
				<tr>
					<td id="userNameText">نام کاربری</td>
					<td><input type="text" name="j_username" dir="ltr" class="defaultFocus" id="userName" tabindex="1"></td>
				</tr>
				<tr>
					<td id="passwordText">کلمه عبور</td>
					<td><input type="password" name="j_password" dir="ltr" id="password" tabindex="2"></td>
				</tr>
				<tr>
					<td></td>	
					<td><input type="submit" value="ورود" id="login"></td>
				</tr>
			</table>
		</aip:skin>
	</form> 
	
</div>



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
					<td colspan="2"><%--						<tiles:insert attribute="footer" />--%>

<%--						<jsp:include page="/layout/footer.jsp"></jsp:include>--%>
					<a href="http://www.aip-co.com" target="_blank">© درگاه هوش مصنوعی ۱۳۸۷</a>
					
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>

