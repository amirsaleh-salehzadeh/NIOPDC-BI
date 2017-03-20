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
		$(document).ready(function(){
			$('#role 10').attr('checked','checked');
			var allUserRoles = $('#userSSASRoles').val().split(",");
			
			$('.SSAS').each(function(){
				for(var i = 0 ; i < allUserRoles.length ; i ++){
					if(allUserRoles[i].toLowerCase() === $(this).val()){
						$(this).attr('checked','checked');
					}
				}
			});
		});
		function dlgFilterSSAS_OK(){
			try{
			var options = {
			    success: function(req) {
			        alert("عملیات ثبت با موفقیت انجام گردید");
			    }
			    ,error: function(req){
			    	alert("ثبت عملیات با مشکل مواجه گردیده" + req.responseText);
			    }
			   };
				$('#ssasForm').ajaxSubmit(options);
			}catch(e){
				alert(e);
			}
		}
		function selectRoles(x){
			var sr = $(x).val();
			if($(x).attr("checked") === true) {
				$("#userSSASRoles").val($("#userSSASRoles").val() + sr + ",");
			} else {
				var tmp = $("#userSSASRoles").val().replace(sr + "," , "");
				$("#userSSASRoles").val(tmp);
			}
		}		
		</script>
	</head>
	<body dir="rtl" background="images/background/background.png" id="pageLayout" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<div style="max-height: 300px; overflow: scroll;">
		<form action="user.do?reqCode=ssasrolesSave&userName=<%=request.getParameter("editUserName") %>" id="ssasForm" method="get">
			<table class="dataList" align="center">
				<tr>
					<th>
						انتخاب
					</th>
					<th>
						ردیف
					</th>
					<th>
						دسترسی
					</th>
				</tr>
				<logic:iterate id="allRoles" name="all" indexId="indexID">
					<tr>
						<td>
							<input type="checkbox" class="SSAS" name="ssasRoles" value="<%=allRoles%>" onclick="selectRoles(this);">
						</td>
						<td>
							<%=indexID+1 %>		
						</td>	
						<td>
							<%=allRoles %>		
						</td>	
					</tr>				
				</logic:iterate>
			</table>
		</form>
		</div>
		<bean:define id="userRoles" name="user"></bean:define>
		
		<input type="hidden" id="userSSASRoles" value="<%=userRoles %>" >
	</body>
</html>

