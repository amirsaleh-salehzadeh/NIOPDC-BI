<%@ page session="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld"%>
<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link rel="shortcut icon" href="aip-co.ico">
		<link rel="icon" type="image/ico" href="/aip-co.ico">
		<link href="/AIPNIOPDCSellBI//css/niopdc.css" rel="stylesheet" type="text/css" />
		<link href="/AIPNIOPDCSellBI/css/dialogStyles.css" type="text/css" rel="stylesheet" />
		<link href="/AIPNIOPDCSellBI/css/popupStyles.css" rel="stylesheet" type="text/css" />
		<link href="/AIPNIOPDCSellBI/css/navigation.css" rel="stylesheet" type="text/css">
		<link href="/AIPNIOPDCSellBI/css/menu.css" rel="stylesheet" type="text/css">
		<link href="/AIPNIOPDCSellBI/css/common.css" rel="stylesheet" type="text/css" />

		<script src="/AIPNIOPDCSellBI/js/common/jquery-1.2.6.pack.js" type="text/javascript"></script>
		<script src="/AIPNIOPDCSellBI/js/common/aip.niopdc.common.js" type="text/javascript"></script>
		<script src="/AIPNIOPDCSellBI/js/common/aip.niopdc.menu.js" type="text/javascript"></script>

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%--		<style>--%>
<%--			.verticaltextRight {--%>
<%--				display: block;--%>
<%--				right: -5px;--%>
<%--				top: 15px;--%>
<%--				direction: rtl;--%>
<%--				-webkit-transform: rotate(90deg);--%>
<%--				-moz-transform: rotate(90deg);--%>
<%--			}--%>
<%--			--%>
<%--			.verticaltextLeft {--%>
<%--				display: block;--%>
<%--				right: -5px;--%>
<%--				top: 15px;--%>
<%--				direction: rtl;--%>
<%--				-webkit-transform: rotate(-90deg);--%>
<%--				-moz-transform: rotate(-90deg);--%>
<%--			}--%>
<%--		</style>--%>
	</head>
	<body dir="rtl" leftmargin="0" style="background: URL(/AIPNIOPDCSellBI/images/background/background.png);" topmargin="0" marginwidth="0"	marginheight="0">
		<table border="0" width="100%" cellspacing="0" align="center" cellpadding="0">
			<tbody>
				<tr align="center">
					<td colspan="2">
						<jsp:include page="/layout/header.jsp"></jsp:include>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<jsp:include page="/layout/menu.jsp"></jsp:include>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<jsp:include page="/layout/banner.jsp"></jsp:include>
					</td>
				</tr>
				<tr align="center">
					<td valign="top" style="padding-right: 0px;" align="right">
						<jsp:include page="/layout/navigation.jsp"></jsp:include>
					</td>
					<td width="100%" valign="top">
						<aip:skin type="BODY">
							<form action="linkTemplates.jsp" method="get" id="form">
								<table border="0" width="800" align="center">
									<tr>
										<td align="right" valign="top">
											<jsp:include page="rightLinks.jsp"></jsp:include>
											<input type="hidden" name="right" value="<%=request.getParameter("right") %>" id="right">
										</td>
										<td background="/AIPNIOPDCSellBI/images/links/bg.gif" width="100%" valign="top" align="center">
											<jsp:include page="/layout/pivot.jsp"></jsp:include>
										</td>
										<td align="left" valign="top">
											<jsp:include page="/tabbedLink/leftLinks.jsp"></jsp:include>
											<input value="<%=request.getParameter("left") %>" type="hidden" name="left" id="left">
										</td>
									</tr>
									<tr>
										<td colspan="3" align="center" valign="top">
											<jsp:include page="/tabbedLink/bottomLinks.jsp"></jsp:include>
											<input type="hidden" name="bottom" id="bottom" value="<%=request.getParameter("bottom") %>">
										</td>
									</tr>
								</table>
							</form>
						</aip:skin>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<%--						<tiles:insert attribute="footer" />--%>
						<jsp:include page="/layout/footer.jsp"></jsp:include>

					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>

