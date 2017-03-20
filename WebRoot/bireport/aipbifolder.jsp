<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="aip.tag.tabbed.AIPTabs"%>
<%@page import="aip.tag.tabbed.AIPTab"%>
<%@page import="aip.util.NVL"%>
<%@page import="aip.niopdc.sellbi.orm.SellDAOManager"%>
<%@page import="aip.common.AIPDefaultParam"%>
<%@page import="aip.common.folder.FolderAndReportDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="aip.common.AIPWebUser"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>

<form id="frmfolder" action="aipbifolder.jsp" method="post">
	<input type="hidden" id="txtReportdim" name="reportdim">
	<input type="hidden" id="txtReportdim" name="folderId" value="<%=request.getParameter("folderId") %>">
	
</form>

		<%
			AIPDefaultParam param = new AIPDefaultParam();
			param.setId(NVL.getInt(request.getParameter("folderId")));
			param.setWebUser(new AIPWebUser(request));
			ArrayList<FolderAndReportDTO> reports = SellDAOManager.getFolder().getFolderAndReportTreeForSelectedNode(param);
			//AIPTabs tabs2 = new AIPTabs("frmAippivot","txtReportdim");
			AIPTabs tabs2 = new AIPTabs("frmfolder","txtReportdim");
			for(int i = 0 ; i < reports.size() ; i ++){
				tabs2.add(new AIPTab(NVL.getString(reports.get(i).getId()) ,reports.get(i).getCaption()));
			}
			
		
			tabs2.setSelectedName( NVL.getString( request.getParameter("reportdim") ));
			request.setAttribute("AIPTabs",tabs2);

		
			int reportId=reports.get(0).getId();
			for(int i = 0 ; i < reports.size() ; i ++){
				if(NVL.getString(reports.get(i).getId()).equalsIgnoreCase(tabs2.getSelectedName()) ){
					reportId = reports.get(i).getId(); 
				}
			}
			
		
			String pivotIsFirstCall = NVL.getString( request.getParameter("pivotIsFirstCall") );
			if("".equals(pivotIsFirstCall)){
				request.setAttribute("pivotIsFirstCall",1);
				request.setAttribute("pivotFormAction","aipbifolder.jsp");
				request.setAttribute("reportId",reportId);
			}

		
		 %>
		 
<table cellpadding="0" cellspacing="0">
<tr><td valign="top" style="font-weight: bold;">
		<br/>		 
		<br/>		 
		<jsp:include page="/aipconfig/tabbed/aiptabbed-right.jsp"></jsp:include>
</td><td valign="top">
		<jsp:include page="/bireport/bimdx/aipbimdx.jsp"></jsp:include>
</td></tr>
</table>

