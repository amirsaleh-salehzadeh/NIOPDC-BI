<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="bean" uri="/WEB-INF/struts-bean.tld" %>
<%@ taglib prefix="html" uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld" %>
<html>
	<head>
		<script type="text/javascript" src="jquery/jquery.form.js"></script>
		<script type="text/javascript">
		$(document).ready(function () {
			$('.selectAll').click(function(){
				if($(this).attr('id') === 'role'){
					$('.role').attr('checked','checked');
					$("#selectedRoleIds").val("");
					$('.role').each( function (i) {
   						$("#selectedRoleIds").val($("#selectedRoleIds").val()+ $(this).attr('id') + ",");
					});
				} else if ($(this).attr('id') === 'group'){
					$('.group').attr('checked','checked');
					$("#selectedGroupIds").val("");
					$('.group').each( function (i) {
   						$("#selectedGroupIds").val($("#selectedGroupIds").val()+ $(this).attr('id') + ",");
					});
				}
				
			});
			$('.deSelectAll').click(function(){
				if($(this).attr('id') === 'role'){
					$('.role').attr('checked','');
					$("#selectedRoleIds").val("");
				} else if ($(this).attr('id') === 'group'){
					$('.group').attr('checked','');
					$("#selectedGroupIds").val("");
				}
			});			
			var str = $('#selectedRoleIds').val().split(',');
			$("#selectedRoleIds").val($('#selectedRoleIds').val() + ",");
			for (var i = 0 ; i < str.length ; i++){
				$('.role' +'#'+str[i]).attr('checked','checked');
			}
			var str2 = $('#selectedGroupIds').val().split(',');

			$("#selectedGroupIds").val($('#selectedGroupIds').val() + ",");
			for (var i = 0 ; i < str2.length ; i++){
				$('.group' +'#'+str2[i]).attr('checked','checked');
			}	
	
	});
			function selectRoleIds(x){
				var sr = $(x).attr('id');
				if($(x).attr("checked") === true) {
					$("#selectedRoleIds").val($("#selectedRoleIds").val() + sr + ",");
				} else {
					var tmp = $("#selectedRoleIds").val().replace(sr + "," , "");
					$("#selectedRoleIds").val(tmp);
				}
			}
			function selectGroupIds(x){
				var sr = $(x).attr('id');
				if($(x).attr("checked") === true) {
					$("#selectedGroupIds").val($("#selectedGroupIds").val() + sr + ",");
				} else {
					var tmp = $("#selectedGroupIds").val().replace(sr + "," , "");
					$("#selectedGroupIds").val(tmp);
				}
			}				
		</script>
	</head>
	<body dir="rtl" background="images/background/background.png" id="pageLayout" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<form action="user.do" method="get" id="rolesAndGroupsform">
			<html:hidden property="userGroupIds" styleId="selectedGroupIds" name="userInfo"></html:hidden>
			<html:hidden property="userRoleIds" styleId="selectedRoleIds" name="userInfo"></html:hidden>
			<input type="hidden" name="editUserName" value="<%=request.getParameter("editUserName") %>">	
			<input type="hidden" name="reqCode" value="saveDeatils">
		</form>
		<%if("true".equalsIgnoreCase(request.getParameter("group"))){ %>
			<div align="center">
				<img src="images/buttons/selectAll.png" title="انتخاب همه" style="cursor: pointer;" class="selectAll" id="group">
				<img src="images/buttons/deselectAll.png" title="حذف همه" style="cursor: pointer;" class="deSelectAll" id="group">
			</div><br/>
			<div align="center">
				<span style="color: #ab1322; font-size: 18px; font-weight: bold ">گروه های کاربر</span>
			</div>
		
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
				</tr>	
					<logic:iterate id="combogroup" name="comboLST" property="groupDTOs" type="aip.common.security.group.GroupENT" indexId="indexID">
						<tr>
							<td>
								<input type="checkbox" class="group" id="<%=combogroup.getId()%>" onchange="selectGroupIds(this);">
							</td>
							<td>
								<%=indexID+1 %>
							</td>
							<td>
								<%=combogroup.getGroupName() %>
							</td>
						</tr>	
					</logic:iterate>
					
			</table>
						<%}else{ %>
			<div align="center">
				<img src="images/buttons/selectAll.png" title="انتخاب همه" style="cursor: pointer;" class="selectAll" id="role">
				<img src="images/buttons/deselectAll.png" title="حذف همه" style="cursor: pointer;" class="deSelectAll" id="role">			
			</div><br/>
			<div align="center">
				<span style="color: #ab1322; font-size: 18px; font-weight: bold ">دسترسی های کاربر</span>
			</div>
			<table class="dataList" align="center">
				<tr>
					<th>
						انتخاب
					</th>
					<th>
						ردیف
					</th>
					<th>
						سطح دسترسی
					</th>
				</tr>
					<logic:iterate id="comboRole" name="comboLST" property="roleDTOs" type="aip.common.security.group.RoleDTO" indexId="indexID">
						<tr>
							<td>
								<input type="checkbox" class="role" id="<%=comboRole.getId()%>" onchange="selectRoleIds(this);">
							</td>
							<td>
								<%=indexID+1 %>
							</td>
							<td>
								<%=comboRole.getGroupInherited() %>
							</td>
						</tr>	
					</logic:iterate>
			</table>
			<%} %>
<%--		<table align="center">--%>
<%--			<tr>--%>
<%--				<td colspan="3" nowrap="nowrap">--%>
<%--					<html:button property="" value="تایید" styleId="btnSubmitUserRoles"></html:button>--%>
<%--					<html:button property="" value="انصراف" styleId="btnCancel"></html:button>--%>
<%--				</td>--%>
<%--			</tr>--%>
<%--		</table>	--%>
	</body>
</html>

