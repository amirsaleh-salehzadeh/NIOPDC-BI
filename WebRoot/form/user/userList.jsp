 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="aip.xmla.AIPXmla"%>
<%@page import="aip.util.NVL"%>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld" %>
<html>
	<head>
		<title>کاربران</title>
		<link rel="shortcut icon" href="aip-co.ico">
		<link rel="icon" type="image/ico" href="aip-co.ico">
		<link href="css/dialogStyles.css" type="text/css" rel="stylesheet" />
		<link href="css/niopdc.css" type="text/css" rel="stylesheet" />
        <link href="css/popupStyles.css" rel="stylesheet" type="text/css"/>
		<link href="css/menu.css" rel="stylesheet" type="text/css" >

		<script type="text/javascript" src="jquery/jquery.js"></script>
		<script type="text/javascript" src="jquery/ui/ui.core.packed.js"></script>
		<script type="text/javascript" src="js/common/aip.niopdc.dialogWindow.js" ></script>
		<script type="text/javascript" src="js/common/aip.niopdc.menu.js"></script>
		<script type="text/javascript" src="jquery/ui/ui.draggable.packed.js"></script>
		<script type="text/javascript" src="aipconfig/dialog/aipdialog.js"></script>
		<script type="text/javascript" src="js/security/user/user.list.js"></script>
					
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
						<aip:skin type="HEAD">
							<form action="user.do" method="post" id="filterForm">
								<input type="hidden" value="list" name="reqCode" id="reqCode">
								<select name="groupCombo" id="groupSelect">
									<option id="0" onclick="selectCombo(this)"></option>
									<logic:iterate id="combogroup" name="comboLST" property="groupDTOs" type="aip.common.security.group.GroupENT">
										<option onclick="selectCombo(this)" id="<%=combogroup.getId()%>"><%=combogroup.getGroupName()%></option>						
									</logic:iterate>
								</select>
								<html:hidden styleId="groupComboId" property="groupId" name="param"></html:hidden>
								فیلتر:<html:text property="filter" name="param"></html:text>
								<img src="images/buttons/search.png" onclick="$('#filterForm').submit();" alt="جستجو" class="getUsersList" />
								<input type="hidden" id="selectedRoles" name="deleteIds">
								<input type="hidden" value="" name="page">
							</form>
						</aip:skin>
						<aip:dialog styleId="dlgFilter" loader="" title="" style="simple" screenPosition="center" onOKClick="dlgRolesAndGroups_OK()" img="">
						</aip:dialog>
						<aip:dialog styleId="dlgFilterSSAS" loader="" title="" style="simple" screenPosition="center" onOKClick="dlgFilterSSAS_OK()" img="">
						</aip:dialog>
						<aip:skin type="LIST">
							<bean:define id="totalRows" name="userList" property="totalItems" type="java.lang.Integer"></bean:define>
							<bean:define id="currentPage" name="userList" property="currentPage" type="java.lang.Integer"></bean:define>
							<bean:define id="pageSize" name="userList" property="pageSize" type="java.lang.Integer"></bean:define>
							
								
							<aip:paginate currentPage="<%=currentPage%>" pageSize="<%=pageSize%>" totalRows="<%=totalRows%>" align="center">
							<div id="baseDiv" align="center">
								<table class="dataList">
									<tr>
										<th>
											انتخاب
										</th>
										<th>
											ردیف
										</th>								
										<th>
											نام 
										</th>
										<th>
											نام خانوادگی  
										</th>
										<th>
											تاریخ شروع 
										</th>
										<th>
											تاریخ پ‍ایان  
										</th>
										<th>
											نام کاربری  
										</th>
										<th>
											تایید شده  
										</th>
										<th>
												کد ملی  
										</th>										
										<th>
											شماره شناسنامه  
										</th>
										<th>
											حذف
										</th>
										<th>
											ویرایش
										</th>
										<th>
											گروه ها
										</th>
										<th>
											دسترسی
										</th>
										<th>
											SSAS
										</th>
									</tr>								
								<logic:iterate id="userLST" property="dto" name="userList" type="aip.common.security.user.UserDTO" indexId="index">
									<tr>
										<td>
											<input type="checkbox" onclick="selectRoleIds(this);" id="<%=userLST.getId()%>" name="">
										</td>
										<td>
											<%=index+1 %>
										</td>
										<td>
											<%=userLST.getFirstName()%>&nbsp
										</td>
									
										<td>
											<%=userLST.getLastName() %>&nbsp
										</td>
										<td>
											<%=userLST.getStartDate() %>&nbsp
										</td>
										<td>
											<%=userLST.getEndDate() %>&nbsp
										</td>
										<td>
											<%=userLST.getUserName() %>&nbsp
										</td>
										<td align="center">
											<%if(NVL.getBln(userLST.getIsApproved())){ %>
												<img src="images/icons/confirm.png">
											<%} else{%>
												<img src="images/icons/nconfirm.png">
											<%} %>
										</td>
										<td>
											<%=userLST.getNationalCode() %>&nbsp
										</td>										
										<td>
											<%=userLST.getIdentityNo() %>&nbsp
										</td>
										<td>
											<a href="user.do?reqCode=delete&deleteUserId=<%=userLST.getId()%>&deleteUserName=<%=userLST.getUserName()%>"><img title="حذف کاربر" src="images/buttons/delete.png" ></a>
										</td>
										<td>
											<a href="user.do?reqCode=edit&editUserName=<%=userLST.getUserName()%>" ><img title="ویرایش کاربر" src="images/buttons/edit.png"  ></a>
										</td>									
										<td>
											<img src="images/icons/group.png"  Class="userGroup" title="انتخاب گروه برای کاربر" id="<%=userLST.getUserName()%>" style="cursor: pointer;" onclick="showDialogF(this);"> 
										</td>
										<td>
											<img src="images/icons/customers.png"  Class="userRoles" title="انتخاب دسترسی برای کاربر" id="<%=userLST.getUserName()%>" style="cursor: pointer;" onclick="showDialogForRoles(this)">
										</td>
										<td>
											<img src="images/icons/customers.png"  Class="userRoles" title="انتخاب دسترسی برای کاربر" id="<%=userLST.getUserName()%>" style="cursor: pointer;" onclick="showDialogForSSASRoles(this)">
										</td>
									</tr>							
								</logic:iterate>
								</table>
							</div>
							</aip:paginate>
							<aip:skin type="HEAD">
								<html:button property="" styleId="btnAddNew" value="جدید"></html:button>
								<html:button property="" styleId="btnDelete" value="حذف"></html:button>
							</aip:skin>
							
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

