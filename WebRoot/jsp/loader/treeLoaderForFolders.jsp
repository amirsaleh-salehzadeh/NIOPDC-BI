<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF8"%>
<%@page import="aip.util.NVL"%>
<%@page import="aip.util.UTF8"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<script type="text/javascript">
	$(document).ready(function(){
		var a = $('#reportId').val();
		var b = $('#folderId').val();
		$('#'+a).removeClass('text');
		$('#'+a).addClass('active');
		
		$('#'+b).removeClass('text');
		$('#'+b).addClass('active');
	});
</script>
<%--	<%if("newFolder".equalsIgnoreCase(request.getParameter("reqCode"))){ %>--%>
	<%String reqCode = request.getParameter("reqCode");	
	if(request.getAttribute("ent")!=null){  %>
		<bean:define id="ent" property="id" name="ent"></bean:define>
		<input type="text" id="newFolderId" value="<%=ent %>">
	<%} %>
	<div align="left" style="border:1px activeborder;border-style:outset;background: menu;">
	<img src="images/icons/add1.png" width="30" height="30" title="پوشه جدید" style="cursor: pointer;" onclick="$('#frmFolder_reqCode').val('newFolder'); showDialog('dlgNewFolder','center');"/>
	<img src="images/icons/delet.png" width="30" height="30" title="حذف پوشه" style="cursor: pointer;" onclick="$('#frmFolder_reqCode').val('delFolder'); dlgFolder_OK();"/>
	<img src="images/icons/fish.png" width="30" height="30" title="تغییر نام پوشه" style="cursor: pointer;" onclick="editFolder()"/>
	</div>
		<aip:tree loader="/aipbimdx.do?reqCode=folder_report" title="درخت گزارشات" id="treReportFolder" ></aip:tree>
	<br>
	نام : <input type="text" name="reportName" id="dlgReportFolder_reportName" value="<%= UTF8.cnvUTF8(NVL.getString(request.getParameter("reportName"))) %>" /><br>
	عمومی <input type="checkbox" name="isPublic"  id="dlgReportFolder_isPublic" <%= "true".equals(request.getParameter("isPublic"))?"checked=checked":"" %> /><br>
	<input type="hidden" name="folderReqCode" id="folderReqCode" value="<%= "true".equals(request.getParameter("forSaveAs"))?"saveAsReport":"saveReport" %>">
	<input type="hidden" name="folderReqCode" id="folderId" value="<%= request.getParameter("folderId")==null?"":UTF8.cnvUTF8(request.getParameter("folderId")) %>">
	<input type="hidden" name="folderReqCode" id="reportId" value="<%= request.getParameter("reportId")==null?"":UTF8.cnvUTF8(request.getParameter("reportId")) %>">
