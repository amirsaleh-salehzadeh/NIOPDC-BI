<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld" %>
<html>
	<head>
	<title>ویرایش گروه</title>
		<link rel="shortcut icon" href="aip-co.ico">
		
		<link rel="icon" type="image/ico" href="aip-co.ico">
		<link href="css/dialogStyles.css" type="text/css" rel="stylesheet" />
		<link href="css/niopdc.css" type="text/css" rel="stylesheet" />
        <link href="css/popupStyles.css" rel="stylesheet" type="text/css"/>
		<link href="css/navigation.css" rel="stylesheet" type="text/css" >
		<link href="css/menu.css" rel="stylesheet" type="text/css" >
	
		<script src="js/common/jquery-1.2.6.pack.js" type="text/javascript"></script>
		<script src="js/common/aip.niopdc.menu.js" type="text/javascript"></script>
		<script src="js/security/group.edit.js" type="text/javascript"></script>

	  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
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
					<td width="100%" valign="top" align="center">
					<form action="group.do" id="form">
						<aip:skin type="HEAD">
						<input type="hidden" value="save" name="reqCode" id="reqCode">
						<input type="hidden" name="selectedRoles" id="selectedRoles">
							نام گروه: <html:text property="ent.groupName" name="groupDTO" ></html:text><br/>
							نام گروه(WINDOWS): <html:text property="ent.winGroup" name="groupDTO" ></html:text><br/>
							<html:hidden property="selectedRoleIds" styleId="selectedRoleIds" name="groupDTO" ></html:hidden>
							<html:hidden  property="ent.id" name="groupDTO" ></html:hidden>
						</aip:skin>
						<aip:skin type="BODY">
							<div align="center">
								<img src="images/buttons/selectAll.png" title="انتخاب همه" style="cursor: pointer;" class="selectAll" id="role">
								<img src="images/buttons/deselectAll.png" title="حذف همه" style="cursor: pointer;" class="deSelectAll" id="role">
							</div>
							<table class="dataList" align="center">
								<tr>
									<th>
										انتخاب
									</th>
									<th>
										ردیف
									</th>
	<%--								<th>--%>
	<%--									سطح دسترسی--%>
	<%--								</th>--%>
									<th>
										سطح دسترسی
									</th>
								</tr>	
							
									<logic:iterate id="comboRole" name="comboLST" property="roleDTOs" type="aip.common.security.group.RoleDTO" indexId="indexID">
										<tr>
											<td>
												<input type="checkbox" id="<%=comboRole.getId()%>" class="role" onclick="selectRoleIds(this);">
											</td>
											<td>
												<%=indexID+1 %>
											</td>
		<%--									<td>--%>
		<%--										<%=comboRole.getRoleName() %>--%>
		<%--									</td>--%>
											<td>
												<%=comboRole.getGroupInherited() %>
											</td>
										</tr>	
									</logic:iterate>
							</table>
							<aip:skin type="HEAD">
								<html:button property="" styleId="btnSave" value="ثبت"></html:button>
								<html:button property="" styleId="btnSaveAndNew" value="ثبت و جدید"></html:button>
								<html:button property="" styleId="btnCancel" value="بازگشت"></html:button>
							</aip:skin>
						</aip:skin>						
					</form>
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

