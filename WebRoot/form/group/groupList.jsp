<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld" %>
<html>
	<head>
		<title>گروه ها</title>
		<link rel="shortcut icon" href="aip-co.ico">
		<link rel="icon" type="image/ico" href="aip-co.ico">
		<link href="css/dialogStyles.css" type="text/css" rel="stylesheet" />
		<link href="css/niopdc.css" type="text/css" rel="stylesheet" />
        <link href="css/popupStyles.css" rel="stylesheet" type="text/css"/>
		<link href="css/navigation.css" rel="stylesheet" type="text/css" >
		<link href="css/menu.css" rel="stylesheet" type="text/css" >

		<script src="jquery/jquery.js" type="text/javascript" ></script>
		<script src="jquery/ui/ui.core.packed.js" type="text/javascript" ></script>
		<script src="jquery/ui/ui.draggable.packed.js" type="text/javascript" ></script>
		<script src="js/security/group.list.js" type="text/javascript"></script>
		<script src="js/common/aip.niopdc.common.js" type="text/javascript"></script>
		<script src="js/common/aip.niopdc.dialogWindow.js" type="text/javascript"></script>
		<script src="aipconfig/dialog/aipdialog.js" type="text/javascript" ></script>		

	  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<bean:define id="errorMessage" name="groupForm" property="errorMessage" type="java.lang.String"/>
	<bean:define id="successMessage" name="groupForm" property="successMessage" type="java.lang.String"/>		
			
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
							<form action="group.do" method="post" id="filterForm">
								<input type="hidden" value="list" name="reqCode" id="reqCode">
								فیلتر:<html:text property="filter" name="param"></html:text>
								<img src="images/buttons/search.png" onclick="$('#filterForm').submit();" alt="جستجو" class="getUsersList" />
								<input type="hidden" id="selectedRoles" name="deleteIds">
								<input type="hidden"  name="page">
							</form>
						</aip:skin>
						<aip:skin type="LIST">
						<aip:dialog styleId="dlgFilter" img="" toolbar="" style="simple" title="لیست کاربران گروه" screenPosition="center">						</aip:dialog>
							<bean:define id="totalRows" name="groupLST" property="totalItems" type="java.lang.Integer"></bean:define>
							<bean:define id="currentPage" name="groupLST" property="currentPage" type="java.lang.Integer"></bean:define>
							<bean:define id="pageSize" name="groupLST" property="pageSize" type="java.lang.Integer"></bean:define>
								
							<aip:paginate currentPage="<%=currentPage%>" pageSize="<%=pageSize%>" totalRows="<%=totalRows%>" align="center">
								<table class="dataList" align="center">
									<tr>
										<th>
											انتخاب
										</th>
										<th>
											ردیف
										</th>
										<th>
											نام گروه
										</th>
										<th>
											حذف
										</th>
										<th>
											ویرایش
										</th>
										<th>
											کاربران
										</th>
									</tr>
									<logic:iterate id="groupList" name="groupLST" property="dtos" type="aip.common.security.group.GroupDTO" indexId="indexId">									
										<tr>
											<td>
												<input type="checkbox" onclick="selectRoleIds(this);" id="<%=groupList.getEnt().getId()%>">
											</td>
											<td>
												<%= indexId+1%>
											</td>
											<td>
												<%=groupList.getEnt().getGroupName()%>
											</td>
											<td>
												<a href="group.do?reqCode=delete&deleteId=<%=groupList.getEnt().getId()%>"><img src="images/buttons/delete.png"></a>
											</td>
											<td>
												<a href="group.do?reqCode=edit&editId=<%=groupList.getEnt().getId()%>" ><img src="images/buttons/edit.png"></a>
											</td>
											<td>
												<img src="images/icons/group.png" title="انتخاب گروه برای کاربر" id="<%=groupList.getEnt().getId()%>" style="cursor: pointer;" onclick="showDialogF(this);"> 
											</td>
										</tr>
									</logic:iterate>
								</table>
							</aip:paginate>
							<aip:skin type="HEAD">
								<html:button property="" styleId="btnAddNew" value="جدید"></html:button>
								<html:button property="" styleId="btnDelete" styleClass="btnDeleteForGroups" value="حذف"></html:button>
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

