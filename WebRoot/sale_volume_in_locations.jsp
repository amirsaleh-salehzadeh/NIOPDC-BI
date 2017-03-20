<%@ page session="true" contentType="text/html; charset=UTF-8"%>
<%@page import="com.tonbeller.jpivot.chart.ChartComponent"%>
<%@page import="aip.jpivot.ChartTypeEnum"%>
<%@page import="aip.util.NVL"%>
<%@page import="aip.xmla.AIPOlap"%>
<%@page import="rex.DimensionMember"%>
<%@page import="aip.jpivot.AIPPivotParam"%>

<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp"%>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld"%>

<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<link rel="shortcut icon" href="aip-co.ico">
		<link rel="icon" type="image/ico" href="aip-co.ico">
		<link href="css/niopdc.css" rel="stylesheet" type="text/css" />
		<link href="css/dialogStyles.css" type="text/css" rel="stylesheet" />
		<link href="css/popupStyles.css" rel="stylesheet" type="text/css" />
		<link href="css/navigation.css" rel="stylesheet" type="text/css">
		<link href="css/menu.css" rel="stylesheet" type="text/css">

		<script src="js/common/jquery-1.2.6.pack.js" type="text/javascript"></script>
		<script src="js/common/aip.niopdc.common.js" type="text/javascript"></script>
		<script src="js/common/aip.niopdc.menu.js" type="text/javascript"></script>

		<%--  <title></title>--%>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css"
			href="jpivot/table/mdxtable.css">
		<link rel="stylesheet" type="text/css" href="jpivot/navi/mdxnavi.css">
		<link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
		<link rel="stylesheet" type="text/css" href="wcf/table/xtable.css">
		<link rel="stylesheet" type="text/css" href="wcf/tree/xtree.css">

	</head>
	<%
		ChartComponent _chart=null;
	%>
	<%
		String defaultYear = "1388";
			String defaultSeason = "1";
			String defaultMonth = "1";
			
			String defaultLocation = ""; 
			String defaultZone = "";
			
			String whereClause1 = "";
			String whereClause2 = "";
	%>

	<%
		String reqCode = request.getParameter("reqCode");
			if (reqCode != null && "filter".equalsIgnoreCase(reqCode)) {
		
		defaultLocation=NVL.getString(request.getParameter("location"),defaultLocation);
		defaultZone=NVL.getString(request.getParameter("zone"),defaultZone);
		
		defaultYear = NVL.getString(request.getParameter("year"),
				defaultYear);
		defaultMonth = NVL.getString(request.getParameter("month"),
				defaultMonth);
		defaultSeason = NVL.getString(request.getParameter("season"),
				defaultSeason);
		
		session.setAttribute("qrySaleVolumeInLocations", null);
		session.setAttribute("tblqrySaleVolumeInLocations", null);

		whereClause1 ="[مناطق].[منطقه-ناحیه].[همه] ";
		if(!"".equals(defaultLocation) || !"".equals(defaultZone) ){
		whereClause1 ="[مناطق].[منطقه-ناحیه].[همه] ";
		if(!"".equals(defaultLocation))
			whereClause1+=".&["+defaultLocation+"]";
		if(!"".equals(defaultZone))
			whereClause1+=".&["+defaultZone+"]";
		}
		
		whereClause2 = "[زمان].[سال-فصل-ماه].[سال]";
		if (!"".equals(defaultYear) || !"".equals(defaultSeason)
				|| !"".equals(defaultMonth)) {
			whereClause2 = "[زمان].[سال-فصل-ماه].[سال]";
			if (!"".equals(defaultYear))
				whereClause2 += ".&[" + defaultYear + "]";
			if (!"".equals(defaultSeason))
				whereClause2 += ".&[" + defaultSeason + "]";
			if (!"".equals(defaultMonth))
				whereClause2 += ".&[" + defaultMonth + "]";

		}
			
			
		System.out.println("whereClause="+ whereClause1 + "_________________" + whereClause2);
			}
	%>
	<%
		DimensionMember[] locationCombos = AIPOlap.getDimTreeMembers("[مناطق]","[مناطق].[منطقه-ناحیه]","[مناطق].[منطقه-ناحیه].[Level 02]");
		
			DimensionMember[] zoneCombos = AIPOlap.getDimTreeMembers("[مناطق]","[مناطق].[منطقه-ناحیه]","[مناطق].[منطقه-ناحیه].[Level 03]");
			
			DimensionMember[] yearCombos = AIPOlap.getDimTreeMembers("[زمان]",
			"[زمان].[سال-فصل-ماه]", "[زمان].[سال-فصل-ماه].[سال]");
			DimensionMember[] monthCombos = AIPOlap.getDimTreeMembers("[زمان]",
			"[زمان].[سال-فصل-ماه]", "[زمان].[سال-فصل-ماه].[ماه]");
			DimensionMember[] seasonCombos = AIPOlap.getDimTreeMembers(
			"[زمان]", "[زمان].[سال-فصل-ماه]",
			"[زمان].[سال-فصل-ماه].[فصل]");
	%>
	<body dir="rtl" background="images/background/background.png"
		id="pageLayout" leftmargin="0" topmargin="0" marginwidth="0"
		marginheight="0">
		<table border="0" width="100%" cellspacing="0" align="center"
			cellpadding="0">
			<tbody>
				<tr align="center">
					<td colspan="2">
						<jsp:include page="/layout/header.jsp"></jsp:include>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<jsp:include page="/layout/menu.jsp"></jsp:include>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<jsp:include page="/layout/banner.jsp"></jsp:include>
					</td>
				</tr>
				<tr align="center">
					<td valign="top" style="padding-right: 0px;" align="right">
						<jsp:include page="/layout/navigation.jsp"></jsp:include>
					</td>
					<td width="100%" valign="top">
						<aip:skin type="HEAD">
							<form action="sale_volume_in_locations.jsp" method="get">
								<input type="hidden" name="reqCode" value="filter">
								<table align="right">
									<tr>
										<td align="right">
										<td align="right">
											منطقه<select name="location">
											<option/>
										    <%for(int i=0;i<locationCombos.length;i++){ %>
										        <option class="locCombo" value="<%=locationCombos[i].getMemberNum()%>" 
										        		<% if(locationCombos[i].getMemberNum().equals(defaultLocation)){ out.print("selected='selected'"); } %> >
										        <%=locationCombos[i].getMemberCaption() %></option>
										    <%} %>
											</select>
											ناحیه <select name="zone">
											<option/>
										    <%for(int i=0;i<zoneCombos.length;i++){ %>
										        <option class="zoneCombo" value="<%=zoneCombos[i].getMemberNum()%>" 
										        		<% if(zoneCombos[i].getMemberNum().equals(defaultZone)){ out.print("selected='selected'"); } %> >
										        <%=zoneCombos[i].getMemberCaption() %></option>
										    <%} %>
											</select>
										
										
											<select name="year">
												<%
													for (int i = 0; i < yearCombos.length; i++) {
												%>
												<%
													if (yearCombos[i].getMemberNum().equals(defaultYear)) {
												%>
												<option selected class="yearCombo"
													value="<%=yearCombos[i].getMemberNum()%>"><%=yearCombos[i].getMemberCaption()%></option>
												<%
													} else {
												%>
												<option class="yearCombo"
													value="<%=yearCombos[i].getMemberNum()%>"><%=yearCombos[i].getMemberCaption()%></option>
												<%
													}
												%>
												<%
													}
												%>
											</select>
										</td>
										<td align="right">
											<select name="season">
												<option></option>
												<%
													for (int i = 0; i < seasonCombos.length; i++) {
												%>
												<%
													if (seasonCombos[i].getMemberNum().equals(defaultSeason)) {
												%>
												<option selected class="seasonCombo"
													value="<%=seasonCombos[i].getMemberNum()%>"><%=seasonCombos[i].getMemberCaption()%></option>
												<%
													} else {
												%>
												<option class="seasonCombo"
													value="<%=seasonCombos[i].getMemberNum()%>"><%=seasonCombos[i].getMemberCaption()%></option>
												<%
													}
												%>
												<%
													}
												%>
											</select>
										</td>
										<td align="right">
											<select name="month">
												<option></option>
												<%
													for (int i = 0; i < monthCombos.length; i++) {
												%>
												<%
													if (monthCombos[i].getMemberNum().equals(defaultMonth)) {
												%>
												<option selected class="monthCombo"
													value="<%=monthCombos[i].getMemberNum()%>"><%=monthCombos[i].getMemberCaption()%></option>
												<%
													} else {
												%>
												<option class="monthCombo"
													value="<%=monthCombos[i].getMemberNum()%>"><%=monthCombos[i].getMemberCaption()%></option>
												<%
													}
												%>
												<%
													}
												%>
											</select>
										</td>
										<td align="right">
											<input type="submit" value="تایید" />
										</td>
									</tr>
								</table>
							</form>
						</aip:skin>
						<aip:skin type="BODY">
							<table align="center">
								<tr>
									<td class="headerTitleDiv" align="center">
										گزارش فروش مقداری فرآورده هادر مناطق
									</td>
								</tr>
								<tr>
									<td align="left">

										<%
											AIPPivotParam param = new AIPPivotParam();
					param.setFormAction("sale_volume_in_locations.jsp");
					param.setMdxQuery("with member Measures.[واحد] as " +
					"Generate({[فرآورده].[فرآورده-نرخ].CurrentMember.FIRSTCHILD},[فرآورده].[واحد-فرآورده].CurrentMember.Name)" +
					"select  {[Measures].[مقدار],[Measures].[درصد افزایش مقدار],Measures.[واحد]} on columns," +
					"crossjoin ( FILTER([فرآورده].[فرآورده-نرخ].[همه].children,[Measures].[مقدار]" +
					"           >0 )," +
					whereClause1 +
					"   )" +					
					"   on rows " +
					"from [حواله بافیش]" +
					"where ("  +
					whereClause2 +
					")");

												param.setQueryName("qrySaleVolumeInLocations");
												//param.setRefresh(true);
												request.setAttribute("AIPPivotParam", param);
										%>

										<jsp:include page="/layout/pivot.jsp"></jsp:include>


									</td>
								</tr>
							</table>
						</aip:skin>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<%--						<tiles:insert attribute="footer" />--%>
						<jsp:include page="/layout/footer.jsp"></jsp:include>

					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>

