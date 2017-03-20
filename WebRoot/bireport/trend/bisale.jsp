<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="aip.tag.tabbed.AIPTabs"%>
<%@page import="aip.tag.tabbed.AIPTab"%>
<%@page import="aip.util.NVL"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>

<%--	گزارش روند فروش در کشور/منطقه--%>
<form id="frmSale" action="bisale.jsp" method="post">
	<input type="hidden" id="txtReportdim" name="reportdim">
</form>

		<%
		
			//AIPTabs tabs2 = new AIPTabs("frmAippivot","txtReportdim");
			AIPTabs tabs2 = new AIPTabs("frmSale","txtReportdim");
			tabs2.add(new AIPTab("all","کلی"));
			tabs2.add(new AIPTab("faravardeh","فرآورده"));
			tabs2.add(new AIPTab("moshtari","نوع مشتری"));
			tabs2.add(new AIPTab("noehazineh","نوع هزینه"));
			tabs2.add(new AIPTab("noekharid","نوع خرید"));
			tabs2.add(new AIPTab("noemasraf","نوع مصرف "));
			tabs2.add(new AIPTab("nahvehpardakht","نحوه پرداخت "));
		
			tabs2.setSelectedName( NVL.getString( request.getParameter("reportdim") ,"all"));
			request.setAttribute("AIPTabs",tabs2);
			
			int reportId=4;//all
			if("faravardeh".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=5;
			}else if("moshtari".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=6;
			}else if("noehazineh".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=7;
			}else if("noekharid".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=8;
			}else if("noemasraf".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=9;
			}else if("nahvehpardakht".equalsIgnoreCase(tabs2.getSelectedName())){
				reportId=10;
			}
			
			//out.println("sale.jsp:selectedTab=" + tabs2.getSelectedName() + ",reportId=" + reportId);

			String pivotIsFirstCall = NVL.getString( request.getParameter("pivotIsFirstCall") );
			if("".equals(pivotIsFirstCall)){
				request.setAttribute("pivotIsFirstCall",1);
				request.setAttribute("pivotFormAction","bisale.jsp");
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

