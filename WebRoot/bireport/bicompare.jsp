<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="aip.tag.tabbed.AIPTabs"%>
<%@page import="aip.tag.tabbed.AIPTab"%>
<%@page import="aip.util.NVL"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>

<%--	گزارش روند فروش در کشور/منطقه--%>
<form id="frmcompare" action="bicompare.jsp" method="post">
	<input type="hidden" id="txtReportdim" name="reportdim">
</form>

		<%
		
			//AIPTabs tabs2 = new AIPTabs("frmAippivot","txtReportdim");
			AIPTabs tabs2 = new AIPTabs("frmcompare","txtReportdim");
			//tabs2.add(new AIPTab("sale_amount_compare_with_last_year","مقدار"));
			//tabs2.add(new AIPTab("sale__price_compare_with_last_year","مبلغ"));
			tabs2.add(new AIPTab("all_amount","حجم-کلی"));
			tabs2.add(new AIPTab("locations_amount","مبلغ-کلی"));
			tabs2.add(new AIPTab("all_price","حجم-منطقه"));
			tabs2.add(new AIPTab("locations_price","مبلغ-منطقه"));
		
			tabs2.setSelectedName( NVL.getString( request.getParameter("reportdim") ,"all_amount"));
		
			request.setAttribute("AIPTabs",tabs2);
			
			int reportId=55;//all-amount
			if("locations_amount".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=51;
			}else if("all_price".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=56;
			}else if("locations_price".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=57;//23;
			}
			
			//out.println("sale.jsp:selectedTab=" + tabs2.getSelectedName() + ",reportId=" + reportId);

			String pivotIsFirstCall = NVL.getString( request.getParameter("pivotIsFirstCall") );
			if("".equals(pivotIsFirstCall)){
				request.setAttribute("pivotIsFirstCall",1);
				request.setAttribute("pivotFormAction","bicompare.jsp");
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

