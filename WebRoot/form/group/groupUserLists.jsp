<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld" %>
<html>
	<head>
		<link href="css/niopdc.css" type="text/css" rel="stylesheet" />	
		<script type="text/javascript" src="jquery/jquery.form.js"></script>
		<script src="js/security/group.list.js" type="text/javascript"></script>	

	</head>
	<body dir="rtl" background="images/background/background.png" id="pageLayout" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<div align="center">
			<img src="images/buttons/selectAll.png" title="انتخاب همه" style="cursor: pointer;" class="selectAll" id="group">
			<img src="images/buttons/deselectAll.png" title="حذف همه" style="cursor: pointer;" class="deSelectAll" id="group">
		</div>
		<form action="group.do" id="form" method="post">
			<input type="hidden" value="deleteGroupUsers" name="reqCode">
			<input type="hidden" value="<%=request.getParameter("groupId")%>" name="groupIdForDelete">
			<table class="dataList" align="center">
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
						نام کاربری  
					</th>
					<th>
						تایید شده  
					</th>
					<th>
						حذف
					</th>
				</tr>								
			<logic:iterate id="userList" property="dto" name="userList" type="aip.common.security.user.UserDTO" indexId="index">
				<tr>
					<td>
						<input type="checkbox" onclick="selectRoleIds(this);" value="<%=userList.getUserName()%>" name="usernames" class="group">
					</td>
					<td>
						<%=index+1 %>
					</td>
					<td>
						<%=userList.getFirstName()%>&nbsp
					</td>
				
					<td>
						<%=userList.getLastName() %>&nbsp
					</td>
					<td>
						<%=userList.getUserName() %>&nbsp
					</td>
					<td>
						<%if(userList.getIsApproved()==true){ %>
							<img src="images/icons/confirm.png">
						<%} else{%>
							<img src="images/icons/nconfirm.png">
						<%} %>
					</td>
					<td>
						<a href="group.do?reqCode=deleteGroupUsers&usernames=<%=userList.getUserName()%>&groupIdForDelete=<%=request.getParameter("groupId") %>"><img title="حذف کاربر" src="images/buttons/delete.png" ></a>
					</td>
				</tr>							
			</logic:iterate>
			</table>	
		</form>	
		<aip:skin type="HEAD">
			<html:button property="" styleId="btnDelete" style="" styleClass="btnDeleteForUser" value="حذف"></html:button>
			<html:button property="" styleId="btnCancel"  value="بازگشت"></html:button>			
		</aip:skin>
	</body>
</html>

