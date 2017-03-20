<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="aip.util.NVL"%>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld" %>
<html>
	<head>
		<title>ویرایش اطلاعات کاربر
		</title>
		<link rel="shortcut icon" href="aip-co.ico">
		<link rel="icon" type="image/ico" href="aip-co.ico">
		<link href="css/dialogStyles.css" type="text/css" rel="stylesheet" />
		<link href="css/niopdc.css" type="text/css" rel="stylesheet" />
        <link href="css/popupStyles.css" rel="stylesheet" type="text/css"/>
		<link href="css/navigation.css" rel="stylesheet" type="text/css" >
		<link href="css/menu.css" rel="stylesheet" type="text/css" >
		<link href="css/calendar.css" rel="stylesheet" type="text/css"/>
		
		<script type="text/javascript" src="jquery/jquery.js"></script>
		<script type="text/javascript" src="js/common/aip.niopdc.dialogWindow.js" ></script>
		<script type="text/javascript" src="js/common/aip.niopdc.menu.js" ></script>
		

		<script type="text/javascript" src="aipconfig/dialog/aipdialog.js"></script>
		<script type="text/javascript" src="js/common/JsFarsiCalendar.js" ></script>				
		<script type="text/javascript" src="js/security/user/user.edit.js" ></script>
					
	  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<bean:define id="errorMessage" name="userForm" property="errorMessage" type="java.lang.String"/>
	<bean:define id="successMessage" name="userForm" property="successMessage" type="java.lang.String"/>				
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
					<td width="100%" valign="middle" align="center">
						<aip:message errorMessage="<%=errorMessage%>" successMessage="<%=successMessage%>"/>
						<aip:skin type="ITEM">
							<span style="font-size: 17px; color: #444;">اطلاعات کاربری</span> 
						</aip:skin>
						<aip:dialog styleId="dlgFilter" loader="" title="گروه های کاربر" style="simple" screenPosition="center" onOKClick="dlgFilter_OK();"  img="">
						</aip:dialog>
						<aip:skin type="DIALOGVIEW">
							<form action="user.do" method="post" id="editForm">
							<html:hidden property="userRoleIds" styleId="selectedRoleIds" name="saveENT" ></html:hidden>
							<html:hidden property="userGroupIds" styleId="selectedGroupIds" name="saveENT"></html:hidden>
							<html:hidden property="userENT.id" name="saveENT"></html:hidden>
							<input name="reqCode" id="reqCode" type="hidden">
							<aip:dialog styleId="dlgChangePassword" loader=""  title="تغییر رمز کاربر" style="simple" screenPosition="center" onOKClick="dlgChangepassword_OK();"  img="" >
							</aip:dialog>
								<table cellpadding="1" cellspacing="1" border="0">
								<tr>
									<td>
										نام کاربری:
									</td>
									<td>
									<%if("new".equalsIgnoreCase(request.getParameter("reqCode"))){ %>
										<html:text property="userENT.userName" name="saveENT" styleId="username" onchange="insertToGroup();"></html:text>
									<%}else{ %>
										<html:text property="userENT.userName" name="saveENT" styleId="username" readonly="true" onchange="insertToGroup();"></html:text>
									<%} %>
									</td>
								</tr>
								<tr>
									<td>
										نام کاربری(WINDOWS):
									</td>
									<td>
										<html:text property="userENT.winUser" name="saveENT" styleId="username" onchange=""></html:text>
									</td>
									<td>
										کلمه عبور(WINDOWS):
									</td>
									<td>
										<html:password property="userENT.winPassword" name="saveENT" styleId="username" onchange=""></html:password>
									</td>
								</tr>								
								<tr>
									<td>
										نام :
									</td>
									<td>
										<html:text property="userENT.firstName" name="saveENT"></html:text>
									</td>
									<td>
										نام خانوادگی:
									</td>
									<td>
										<html:text property="userENT.lastName" name="saveENT"></html:text>
									</td>

								</tr>
								<tr>
									<td>
										شماره شناسنامه: 
									</td>
									<td>
										<html:text property="userENT.identityNo" name="saveENT"></html:text>
									</td>
									<td>
										کد ملی: 
									</td>
									<td>
										<html:text property="userENT.nationalCode" name="saveENT"></html:text>
									</td>
								</tr>
								<tr>
									<td>
										تایید شده: 
									</td>
									<td>
										<html:checkbox property="userENT.isApproved" name="saveENT"></html:checkbox>
									</td>
									<td></td><td></td>
								<tr>
<%--								<%if("new".equalsIgnoreCase(request.getParameter("reqCode"))){ %>--%>
									<tr>
										<td>
											رمز عبور: 
										</td>
										<td>
											<input type="password" name="userENT.userPassword" id="pass">
<%--											<html:password property="userENT.userPassword" name="saveENT" styleId="pass"></html:password>--%>
										</td>
										<td>
											تکرار رمز عبور: 
										</td>
										<td>
<%--											<html:password property="userENT.userPassword" name="saveENT" styleId="rePass"></html:password>--%>
											<input type="password"  id="rePass">
											
										</td>
									</tr>
<%--								<%} %>--%>
								<tr>
									<td>
										شماره تلفن: 
									</td>
									<td>
										<html:text property="userENT.phone" name="saveENT"></html:text>
									</td>
									<td>
							 		  	شماره تلفن همراه:
						   	 		</td>
						   	 		<td>
						   	 			<html:text property="userENT.cellPhone" name="saveENT"></html:text>
									</td>
								</tr>
								<tr>
									<td>
									تاریخ آغاز: 
									</td>
									<td>
										<html:text property="userENT.startDate" name="saveENT" styleId="fromDate"></html:text><img src="images/buttons/calendarFromDate.gif"  onclick="displayDatePicker('userENT.startDate',this)">
									</td>
									<td>
									 تاریخ پایان:
							  	    </td>
							    	<td>
							    		<html:text property="userENT.endDate" name="saveENT" ></html:text><img src="images/buttons/calendarToDate.gif" onclick="displayDatePicker('userENT.endDate',this)">
									</td>
								</tr>
								<tr>
									<td>
										ایمیل: 
									</td>
									<td>	
										<html:text property="userENT.mailAddress" name="saveENT" size="50"></html:text>
									</td>
								</tr>
								<tr>
									<td>
										آدرس:
									</td>
									<td>
										<html:text property="userENT.address" name="saveENT" size="50"></html:text>							
									</td>
								</tr>
								<tr>
									<td>
										توضیحات:
									</td>
									<td>
										<html:textarea property="userENT.description" name="saveENT" cols="38"></html:textarea>
									</td>
								</tr>						
								</table>
							</form>
						</aip:skin>
						<aip:skin type="HEAD" >
							<%if(!"new".equalsIgnoreCase(request.getParameter("reqCode"))){ %>
								<bean:define id="userId" name="saveENT" property="userENT.userName"></bean:define>
								<html:button property="" styleId="btnGroups" alt="<%=NVL.getString(userId)%>"  value="گروه ها" onclick="showDialogF(this)"></html:button>
								<html:button property="" styleId="btnRoles" alt="<%=NVL.getString(userId)%>"  value="دسترسی ها" onclick="showDialogForRoles(this)"></html:button>
<%--								<html:button property="" styleId="btnChangePassword" value="تغییر رمز" alt="<%=NVL.getString(userId)%>" onclick="showDialogForChangePasswordUser(this)"></html:button>--%>
							<%} %>
							<html:button property="" styleId="btnSave" value="ثبت"></html:button>
							<html:button property="" styleId="btnSaveAndNew" value="ثبت و جدید"></html:button>
							<html:button property="" styleId="btnCancel" styleClass="returnToList" value="بازگشت"></html:button>
						</aip:skin>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<jsp:include page="/layout/footer.jsp"></jsp:include>
					
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>

