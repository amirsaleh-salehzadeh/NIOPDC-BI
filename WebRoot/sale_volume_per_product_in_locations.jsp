<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="com.tonbeller.jpivot.chart.ChartComponent"%>
<%@page import="aip.jpivot.ChartTypeEnum"%>
<%@page import="aip.util.NVL"%>

<%@page import="rex.DimensionMember"%>
<%@page import="aip.jpivot.AIPPivotParam"%>
<%@page import="aip.xmla.AIPOlap"%>


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
	String defaultProduct="";
	int defaultTimeType = 1;

	String whereClause="";
%>

<%
	String reqCode=request.getParameter("reqCode");
	if(reqCode!=null && "filter".equalsIgnoreCase(reqCode)){
		
		defaultProduct=NVL.getString(request.getParameter("product"),defaultProduct);
		defaultTimeType=NVL.getInt(request.getParameter("type"),defaultTimeType);
		
		session.setAttribute("qrySaleVolumePerProductInLocations",null);
		session.setAttribute("tblqrySaleVolumePerProductInLocations",null);
		
		if(!"".equals(defaultProduct) || !"".equals(defaultProduct) ){
		
			whereClause ="[فرآورده].[فرآورده-نرخ].[نام فرآورده]";
			if(!"".equals(defaultProduct))
			whereClause+=".&["+defaultProduct+"]";
																	}
		System.out.println("**********whereClause=" + whereClause);
		//System.out.flush();
		
	}
 %>
<%
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
<form action="sale_volume_per_product_in_locations.jsp" method="get">
	<input type="hidden" name="reqCode" value="filter">
		فرآورده: <select selected name="product">
			<option/>
		    <%for(int i=0;i<productCombos.length;i++){ %>
		        <option class="prodCombo" value="<%=productCombos[i].getMemberNum()%>" 
		        		<% if(productCombos[i].getMemberNum().equals(productCombos)){ out.print("selected='selected'"); } %> >
		        <%=productCombos[i].getMemberCaption() %></option>
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
									 گزارش روند فروش حجمی  نرخ های یک فراورده در هر منطقه 
									</td>
								</tr>
								<tr>
									<td align="left">
										<%
								AIPPivotParam param = new AIPPivotParam();
								param.setFormAction("sale_volume_per_product_in_locations.jsp");
								param.setMdxQuery("select  {"+ whereClause +".children} on columns," +
												"		{ FILTER(DESCENDANTS([زمان].[سال-فصل-ماه2], " +
												defaultTimeType +
												",SELF ),[Measures].[مقدار]" +
												"		                  >0)}" +
												"		     ON ROWS" +
												"		from [حواله بافیش]" +
												"		where ([مناطق].[منطقه-ناحیه].[همه],[Measures].[مقدار])");
																																		
								param.setQueryName("qrySaleVolumePerProductInLocations");
								request.setAttribute("AIPPivotParam", param);
							//	System.out.print(defaultProduct);
								System.out.println(param.getMdxQuery());
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

