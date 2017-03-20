<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	</head>
	<body dir="rtl" background="images/background/background.png" id="pageLayout">

		<table border="0" width="100%" cellspacing="0" align="center" cellpadding="0">
			<tbody>
				<tr align="center">
					<td colspan="2" >
						<c:import url="/layout/header.jsp"></c:import>						
					</td>
				</tr>
				<tr align="center"> 
					<td colspan="2" >
						<c:import url="/layout/menu.jsp"></c:import>						
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<c:import url="/layout/banner.jsp"></c:import>						
					</td>
				</tr>
				<tr align="center">
					<td valign="top" style="padding-right: 0px; " align="right">
						<c:import url="/t_navigation.do"></c:import>						
					</td>
					<td width="100%" valign="top">
						<table class="rr_Table" id="bodyTable" border="0" cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td class="rr_BASE_top_left">&nbsp;</td>
								<td class="rr_BASE_top_top">&nbsp;</td>
								<td class="rr_BASE_top_right">&nbsp;</td>
							</tr>
							<tr>
								<td class="rr_BASE_left_left">&nbsp;</td>
								<td class="rr_BASE_center">
									<div align="center" dir="rtl">
									    <div id="loginDiv" class="modulecontainer containing BLUE SMALL" align="center">
											<div>
												<br>
												<table align="center">
													<tr>
														<td align="center">
															<span style="font-size: 24px; color: red;">شما به این صفحه دسترسی ندارید</span>
															<a href="#" onClick="history.go(-1)">
																<input type="button" value="بازگشت" title="بازگشت" id="btnCancel" onclick="history.go(-1);">
															</a> 
														</td>
													</tr>
												</table>
												<br>
									        </div>
									    </div>
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
					<td colspan="2">
						<c:import url="/layout/footer.jsp"></c:import>						
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>

















