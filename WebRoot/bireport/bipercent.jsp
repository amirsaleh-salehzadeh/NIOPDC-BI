<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="aip.tag.tabbed.AIPTabs"%>
<%@page import="aip.tag.tabbed.AIPTab"%>
<%@page import="aip.util.NVL"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>

<%--	گزارش روند فروش در کشور/منطقه--%>
<form id="frmpercent" action="bipercent.jsp" method="post">
	<input type="hidden" id="txtReportdim" name="reportdim">
</form>

		<%
		
			AIPTabs tabs2 = new AIPTabs("frmpercent","txtReportdim");
			//tabs2.add(new AIPTab("all","کلی"));
			tabs2.add(new AIPTab("percent_of_web_use_in_locations","مناطق"));
			tabs2.add(new AIPTab("percent_of_web_use_in_sub_locations","نواحی"));
			tabs2.add(new AIPTab("percent_of_web_use_in_a_location_sub_locations","نواحی منطقه "));
			tabs2.add(new AIPTab("trend_of_percent_of_web_use_in_locations","روند"));
		
			tabs2.setSelectedName( NVL.getString( request.getParameter("reportdim") ,"percent_of_web_use_in_locations"));
		
			request.setAttribute("AIPTabs",tabs2);
			
			int reportId=11;//percent_of_web_use_in_locations
			if("percent_of_web_use_in_sub_locations".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=40;
			}else if("percent_of_web_use_in_a_location_sub_locations".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=41;
			}else if("trend_of_percent_of_web_use_in_locations".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=42;
			}
			
			//out.println("percent.jsp:selectedTab=" + tabs2.getSelectedName() + ",reportId=" + reportId);

			String pivotIsFirstCall = NVL.getString( request.getParameter("pivotIsFirstCall") );
			if("".equals(pivotIsFirstCall)){
				request.setAttribute("pivotIsFirstCall",1);
				request.setAttribute("pivotFormAction","bipercent.jsp");
				request.setAttribute("reportId",reportId);
			}

		
		 %>
		 
<table cellpadding="0" cellspacing="0">
<tr><td valign="top" style="font-weight: bold;">
		<br/>		 
		<br/>		 
		<jsp:include page="/aipconfig/tabbed/aiptabbed-right.jsp"></jsp:include>
</td><td>
		<jsp:include page="/bireport/bimdx/aipbimdx.jsp"></jsp:include>
</td></tr>
</table>

