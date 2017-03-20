<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="aip.tag.tabbed.AIPTabs"%>
<%@page import="aip.tag.tabbed.AIPTab"%>
<%@page import="aip.util.NVL"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>

<%--	گزارش روند فروش در کشور/منطقه--%>
<form id="frmvolume" action="bivolume.jsp" method="post">
	<input type="hidden" id="txtReportdim" name="reportdim">
</form>

		<%
		
			//AIPTabs tabs2 = new AIPTabs("frmAippivot","txtReportdim");
			AIPTabs tabs2 = new AIPTabs("frmvolume","txtReportdim");
			tabs2.add(new AIPTab("all","کلی"));
			tabs2.add(new AIPTab("percent_increase","درصد افزایش"));
			tabs2.add(new AIPTab("rate","به تفکیک نرخ"));
			tabs2.add(new AIPTab("location","منطقه"));
			tabs2.add(new AIPTab("product_rate","نرخ فرآورده"));
			tabs2.add(new AIPTab("location_product","منطقه و فرآورده"));
			tabs2.add(new AIPTab("location_rate","منطقه و نرخ"));
		
			tabs2.setSelectedName( NVL.getString( request.getParameter("reportdim") ,"all"));
		
			request.setAttribute("AIPTabs",tabs2);
		
			int reportId=53;//all
			if("percent_increase".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=54;
			}else if("rate".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=52;
			}else if("location".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=46;
			}else if("product_rate".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=48;
			}else if("location_product".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=49;
			}else if("location_rate".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=48;
			}
			
			//out.println("sale.jsp:selectedTab=" + tabs2.getSelectedName() + ",reportId=" + reportId);

			String pivotIsFirstCall = NVL.getString( request.getParameter("pivotIsFirstCall") );
			if("".equals(pivotIsFirstCall)){
				request.setAttribute("pivotIsFirstCall",1);
				request.setAttribute("pivotFormAction","bivolume.jsp");
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

