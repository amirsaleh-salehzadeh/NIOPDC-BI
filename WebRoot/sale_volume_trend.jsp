<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="com.tonbeller.jpivot.chart.ChartComponent"%>
<%@page import="aip.jpivot.ChartTypeEnum"%>
<%@page import="aip.util.NVL"%>

<%@page import="rex.DimensionMember"%>
<%@page import="aip.jpivot.AIPPivotParam"%>
<%@page import="aip.xmla.AIPOlap"%>
<%@page import="aip.util.UTF8"%>


<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld" %>

<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>

		<link rel="shortcut icon" href="aip-co.ico">
		<link rel="icon" type="image/ico" href="aip-co.ico">
		<link href="css/niopdc.css" rel="stylesheet" type="text/css"/>
		<link href="css/dialogStyles.css" type="text/css" rel="stylesheet" />
        <link href="css/popupStyles.css" rel="stylesheet" type="text/css"/>
		<link href="css/navigation.css" rel="stylesheet" type="text/css" >
		<link href="css/menu.css" rel="stylesheet" type="text/css" >
		<link href="css/common.css" rel="stylesheet" type="text/css"/>
		
		<script src="js/common/jquery-1.2.6.pack.js" type="text/javascript"></script>
		<script src="js/common/aip.niopdc.common.js" type="text/javascript"></script>			
		<script src="js/common/aip.niopdc.menu.js" type="text/javascript"></script>
		
<%--  <title></title>--%>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" type="text/css" href="jpivot/table/mdxtable.css">
  <link rel="stylesheet" type="text/css" href="jpivot/navi/mdxnavi.css">
  <link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
  <link rel="stylesheet" type="text/css" href="wcf/table/xtable.css">
  <link rel="stylesheet" type="text/css" href="wcf/tree/xtree.css">

	</head>
<%
ChartComponent _chart=null;
 %>
<% 
	String defaultLocation = ""; 
	String defaultZone = "";
	String defaultProduct="";
	
	int defaultTimeType = 1;

	String whereClause="";
	String whereClause1="";
	String whereClause2="";
%>

<%
	String reqCode=request.getParameter("reqCode");
	if(reqCode!=null && "filter".equalsIgnoreCase(reqCode)){
		
		defaultProduct=NVL.getString(request.getParameter("product"),defaultProduct) ;
		defaultLocation=NVL.getString(request.getParameter("location"),defaultLocation);
		defaultZone=NVL.getString(request.getParameter("zone"),defaultZone);
		defaultTimeType=NVL.getInt(request.getParameter("type"),defaultTimeType);
		
		session.setAttribute("qrySaleVolumeTrend",null);
		session.setAttribute("tblqrySaleVolumeTrend",null);

		if(!"".equals(defaultLocation) || !"".equals(defaultZone) ){
			whereClause1 ="[مناطق].[منطقه-ناحیه]";
			if(!"".equals(defaultLocation))
				whereClause1+=".&["+defaultLocation+"]";
			if(!"".equals(defaultZone))
				whereClause1+=".&["+defaultZone+"]";
		}
		
		if(!"".equals(defaultProduct) || !"".equals(defaultProduct) ){
		whereClause2 ="[فرآورده].[فرآورده-نرخ].[نام فرآورده]";
			if(!"".equals(defaultProduct))
			whereClause2+=".&["+defaultProduct+"]";
		//whereClause2 =defaultProduct;
		
		if ( "".equals(whereClause1) && "".equals(whereClause2) )
		{
		whereClause="";
		}
		else if ( !"".equals(whereClause1) && !"".equals(whereClause2)) 
		{
		whereClause = " where ( " + whereClause1 + " , " + whereClause2 + " )";
		}
		else if ( "".equals(whereClause1) || "".equals(whereClause2))
		{
		whereClause =  " where " + whereClause1 + whereClause2;
		}
		System.out.println("**********whereClause=" + whereClause);
		//System.out.flush();
		//System.out.print("defaultProduct: "+defaultProduct);
	}
	
 %>
<%
	DimensionMember[] locationCombos = AIPOlap.getDimTreeMembers("[مناطق]","[مناطق].[منطقه-ناحیه]","[مناطق].[منطقه-ناحیه].[Level 02]");
	DimensionMember[] zoneCombos = AIPOlap.getDimTreeMembers("[مناطق]","[مناطق].[منطقه-ناحیه]","[مناطق].[منطقه-ناحیه].[Level 03]");
	DimensionMember[] productCombos = AIPOlap.getDimTreeMembers("[فرآورده]","[فرآورده].[فرآورده-نرخ]","[فرآورده].[فرآورده-نرخ].[نام فرآورده]");
	
%> 
	
	<body dir="rtl" background="images/background/background.png" id="pageLayout" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<table border="0" width="100%" cellspacing="0" align="center" cellpadding="0">
			<tbody>
				<tr align="center">
					<td colspan="2" >
						<jsp:include page="/layout/header.jsp"></jsp:include>
					</td>
				</tr>
				<tr align="center"> 
					<td colspan="2" >
						<jsp:include page="/layout/menu.jsp"></jsp:include>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<jsp:include page="/layout/banner.jsp"></jsp:include>
					</td>
				</tr>
				<tr align="center">
					<td valign="top" style="padding-right: 0px; " align="right">
						<jsp:include page="/layout/navigation.jsp"></jsp:include>
					</td>
					<td width="100%" valign="top">
<aip:skin type="HEAD">
<form action="sale_volume_trend.jsp" method="get">
	<input type="hidden" name="reqCode" value="filter">
		فرآورده: <select name="product">
			<option/>
		    <%for(int i=0;i<productCombos.length;i++){ %>
		        <option class="prodCombo" value="<%=productCombos[i].getUniqueName()%>" 
		        		<% if(productCombos[i].getMemberNum().equals(productCombos)){ out.print("selected='selected'"); } %> >
		        <%=productCombos[i].getMemberCaption() %></option>
		    <%} %>
		</select>
		منطقه: <select name="location">
			<option/>
		    <%for(int i=0;i<locationCombos.length;i++){ %>
		        <option class="locCombo" value="<%=locationCombos[i].getMemberNum()%>" 
		        		<% if(locationCombos[i].getMemberNum().equals(defaultLocation)){ out.print("selected='selected'"); } %> >
		        <%=locationCombos[i].getMemberCaption() %></option>
		    <%} %>
		</select>
		ناحیه: <select name="zone">
			<option/>
		    <%for(int i=0;i<zoneCombos.length;i++){ %>
		        <option class="zoneCombo" value="<%=zoneCombos[i].getMemberNum()%>" 
		        		<% if(zoneCombos[i].getMemberNum().equals(defaultZone)){ out.print("selected='selected'"); } %> >
		        <%=zoneCombos[i].getMemberCaption() %></option>
		    <%} %>
		</select>

		سال<input type="radio" name="type" value="1" <% if(defaultTimeType==1){ out.print("checked='checked'"); } %> />
		فصل<input type="radio" name="type" value="2" <% if(defaultTimeType==2){ out.print("checked='checked'"); } %> />
		ماه<input type="radio" name="type" value="3" <% if(defaultTimeType==3){ out.print("checked='checked'"); } %> />
	
		<input type="submit" value="تایید">
</form>
</aip:skin>
	<aip:skin type="BODY">
							<table align="center">
								<tr>
									<td class="headerTitleDiv" align="center">
									 گزارش  روند حجم قروش هر فرآورده در طول زمان 
									</td>
								</tr>
								<tr>
									<td align="left">
										<%
											AIPPivotParam param = new AIPPivotParam();
												param.setFormAction("sale_volume_trend.jsp");
												param.setMdxQuery("select {[Measures].[مقدار],[Measures].[درصد افزایش مقدار] }" +
												" on columns," +
												" { FILTER(DESCENDANTS([زمان].[سال-فصل-ماه2]," +
												+ defaultTimeType +
												",SELF ),[Measures].[مقدار]" +
												" >0)}" +
												" ON ROWS" +
												" from [حواله بافیش]" + whereClause );	
									//			" where ([مناطق].[منطقه-ناحیه].&[5.194])");		
												System.out.print("DEFAULT :"+defaultProduct);																	
												param.setQueryName("qrySaleVolumeTrend");
												request.setAttribute("AIPPivotParam", param);
											//	System.out.println(param.getMdxQuery());
											//	System.out.println("__________________________________" + whereClause);
												//System.out.println(whereClause);
												
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

