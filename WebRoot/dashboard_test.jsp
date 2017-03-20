<%@ page session="true" contentType="text/html; charset=UTF-8"%>
<%@page import="com.tonbeller.jpivot.chart.ChartComponent"%>
<%@page import="aip.jpivot.ChartTypeEnum"%>
<%@page import="aip.xmla.AIPOlap"%>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp"%>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld"%>

<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<link rel="shortcut icon" href="aip-co.ico">
		<link rel="icon" type="image/ico" href="aip-co.ico">
		<link href="css/niopdc.css" rel="stylesheet" type="text/css" />
		<link href="css/dialogStyles.css" type="text/css" rel="stylesheet" />
		<link href="css/popupStyles.css" rel="stylesheet" type="text/css" />
		<link href="css/navigation.css" rel="stylesheet" type="text/css">
		<link href="css/menu.css" rel="stylesheet" type="text/css">

		<script src="js/common/jquery-1.2.6.pack.js" type="text/javascript"></script>
		<script src="js/common/aip.niopdc.common.js" type="text/javascript"></script>
		<script src="js/common/aip.niopdc.menu.js" type="text/javascript"></script>

		<%--  <title></title>--%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css"
			href="jpivot/table/mdxtable.css">
		<link rel="stylesheet" type="text/css" href="jpivot/navi/mdxnavi.css">
		<link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
		<link rel="stylesheet" type="text/css" href="wcf/table/xtable.css">
		<link rel="stylesheet" type="text/css" href="wcf/tree/xtree.css">

	</head>


	<body dir="rtl" background="images/background/background.png"
		id="pageLayout" leftmargin="0" topmargin="0" marginwidth="0"
		marginheight="0">
		<table border="0" width="100%" cellspacing="0" align="center"
			cellpadding="0">
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
					<td width="100%">
						<table cellpadding="0" cellspacing="0" border="0" width="68%"
							height="100%" align="center">
							<tr valign="bottom" height="100%">
								<td style="background-color: white;" align="center"
									width="100%%">
									<img src="/AIPNIOPDCSellBI/images/roundedBoxes/top.png"
										width="100%" align="middle">
								</td>
							</tr>
						</table>
						<table class="rr_Table" id="bodyTable" border="0" cellspacing="0"
							cellpadding="0" width="68%">
							<tr>
								<td class="rr_GREEN_top_left">
									&nbsp;
								</td>
								<td class="rr_GREEN_top_top">
									&nbsp;
								</td>
								<td class="rr_GREEN_top_right">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td class="rr_GREEN_left_left">
									&nbsp;
								</td>
								<td class="rr_GREEN_center">

									<table cellpadding="1" cellspacing="50" border="0" width="90%"
										height="90%" align="center">

										<tr align="center" valign="middle">
											<td width="50%" align="right">
												<table cellpadding="0" cellspacing="0" border="0">
													<tr>
														<td>
															asdsaasds
														</td>
													</tr>
													<tr>
														<td>
															asdsaasds
														</td>
													</tr>
													<tr>
														<td>
															asdsaasds
														</td>
													</tr>
												</table>
											</td>
											<td width="50%">
												<table cellpadding="0" cellspacing="0" border="0">
													<tr>
														<td>
															<img
																src="/AIPNIOPDCSellBI/images/roundedBoxes/DisplayChart.png">
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr align="center">
											<td colspan="4" align="center">
											<img src="/AIPNIOPDCSellBI/images/roundedBoxes/line.png" >
											</td>
										</tr>
									</table>
								</td>
								<td class="rr_GREEN_right_right">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td class="rr_GREEN_bottom_left">
									&nbsp;
								</td>
								<td class="rr_GREEN_bottom_bottom">
									&nbsp;
								</td>
								<td class="rr_GREEN_bottom_right">
									&nbsp;
								</td>
							</tr>

						</table>
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

