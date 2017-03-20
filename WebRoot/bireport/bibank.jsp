<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="aip.tag.tabbed.AIPTabs"%>
<%@page import="aip.tag.tabbed.AIPTab"%>
<%@page import="aip.util.NVL"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>

<%--	گزارش روند فروش در کشور/منطقه--%>
<form id="frmbank" action="bibank.jsp" method="post">
	<input type="hidden" id="txtReportdim" name="reportdim">
</form>

		<%
		
			//AIPTabs tabs2 = new AIPTabs("frmAippivot","txtReportdim");
			AIPTabs tabs2 = new AIPTabs("frmbank","txtReportdim");
			tabs2.add(new AIPTab("payment_to_bank_summary_report","کلی"));
			tabs2.add(new AIPTab("payment_to_bank_summary_report_by_fish","نوع فیش"));
			tabs2.add(new AIPTab("fish_type_percent_per_total_payment_report","درصد نوع فیش"));
			tabs2.add(new AIPTab("bank_payment_trend_in_location"," روندمناطق "));
		
			tabs2.setSelectedName( NVL.getString( request.getParameter("reportdim") ,"payment_to_bank_summary_report"));
			request.setAttribute("AIPTabs",tabs2);

		
			tabs2.setSelectedName( NVL.getString( request.getParameter("reportdim") ,"all"));
			
			int reportId=43;//payment_to_bank_summary_report
			if("payment_to_bank_summary_report_by_fish".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=44;
			}else if("fish_type_percent_per_total_payment_report".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=45;
			}else if("bank_payment_trend_in_location".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=56;//18;
			}
			
			//out.println("bank.jsp:selectedTab=" + tabs2.getSelectedName() + ",reportId=" + reportId);
		
			String pivotIsFirstCall = NVL.getString( request.getParameter("pivotIsFirstCall") );
			if("".equals(pivotIsFirstCall)){
				request.setAttribute("pivotIsFirstCall",1);
				request.setAttribute("pivotFormAction","bibank.jsp");
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

