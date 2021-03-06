<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="com.tonbeller.jpivot.chart.ChartComponent"%>
<%@page import="aip.jpivot.ChartTypeEnum"%>
<%@page import="aip.util.NVL"%>
<%@page import="aip.xmla.AIPOlap"%>
<%@page import="rex.DimensionMember"%>

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
int defaultTimeType = 2;
String whereClause="";
%>

<%
	String reqCode=request.getParameter("reqCode");
	if(reqCode!=null && "filter".equalsIgnoreCase(reqCode)){
		defaultLocation=NVL.getString(request.getParameter("location"),defaultLocation);
		defaultZone=NVL.getString(request.getParameter("zone"),defaultZone);
		defaultTimeType=NVL.getInt(request.getParameter("type"),defaultTimeType);
		session.setAttribute("qrySaleTrendInWarehouse",null);
		session.setAttribute("tblSaleTrendInWarehouse",null);

		if(!"".equals(defaultLocation) || !"".equals(defaultZone) ){
	whereClause =" where [مناطق].[منطقه-ناحیه]";
	if(!"".equals(defaultLocation))
		whereClause+=".&["+defaultLocation+"]";
	if(!"".equals(defaultZone))
		whereClause+=".&["+defaultZone+"]";
		}
		//out.println("whereClause="+whereClause);
	}
%>
<%
	DimensionMember[] locationCombos = AIPOlap.getDimTreeMembers("[مناطق]","[مناطق].[منطقه-ناحیه]","[مناطق].[منطقه-ناحیه].[Level 02]");
	DimensionMember[] zoneCombos = AIPOlap.getDimTreeMembers("[مناطق]","[مناطق].[منطقه-ناحیه]","[مناطق].[منطقه-ناحیه].[Level 03]");
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
<form action="sale_trend_in_warehouse.jsp" method="get">
	<input type="hidden" name="reqCode" value="filter">
		منطقه <select name="location">
			<option/>
		    <%
		    	for(int i=0;i<locationCombos.length;i++){
		    %>
		        <option class="locCombo" value="<%=locationCombos[i].getMemberNum()%>" 
		        		<% if(locationCombos[i].getMemberNum().equals(defaultLocation)){ out.print("selected='selected'"); } %> >
		        <%=locationCombos[i].getMemberCaption()%></option>
		    <%
		    	}
		    %>
		</select>
		ناحیه<select name="zone">
			<option/>
		    <%
		    	for(int i=0;i<zoneCombos.length;i++){
		    %>
		        <option class="zoneCombo" value="<%=zoneCombos[i].getMemberNum()%>" 
		        		<% if(zoneCombos[i].getMemberNum().equals(defaultZone)){ out.print("selected='selected'"); } %> >
		        <%=zoneCombos[i].getMemberCaption()%></option>
		    <%
		    	}
		    %>
		</select>

		سال<input type="radio" name="type" value="1" <% if(defaultTimeType==1){ out.print("checked='checked'"); } %> />
		فصل<input type="radio" name="type" value="2" <% if(defaultTimeType==2){ out.print("checked='checked'"); } %> />
		ماه<input type="radio" name="type" value="3" <% if(defaultTimeType==3){ out.print("checked='checked'"); } %> />
		روز<input type="radio" name="type" value="4" <% if(defaultTimeType==4){ out.print("checked='checked'"); } %> />
		
		<input type="submit" value="تایید">
</form>
</aip:skin>
<aip:skin type="BODY">					
<table align="center">
<tr>
	<td class="headerTitleDiv" align="center">روند فروش در انبارها</td>
</tr>
<tr>	
	<td align="left">
		<div dir="ltr" style="width: 1200px;overflow: auto;" >
		<form action="sale_trend_in_warehouse.jsp" method="post">
		
		<c:if test="${qrySaleTrendInWarehouse == null}">
		<jp:xmlaQuery id="qrySaleTrendInWarehouse" uri="<%=AIPOlap.getDataSourceURI()%>" catalog="<%=AIPOlap.getCatalog()%>">  
		SELECT  {[انبار].[انبار].[همه]}
			     on columns,        
		         {NONEMPTY( DESCENDANTS( [زمان].[سال-فصل-ماه2],
                  <%= defaultTimeType %>,SELF))}  

		       on rows
		from [حواله]
		<%=whereClause %>
<%--where  ([مناطق].[منطقه- ناحیه],[Measures].[مبلغ کل])--%>
		
		</jp:xmlaQuery>
		</c:if>
			 
			 
		<%-- define table, navigator and forms --%>
		<jp:table id="tblSaleTrendInWarehouse" query="#{qrySaleTrendInWarehouse}"/>
		<jp:navigator id="naviSaleTrendInWarehouse" query="#{qrySaleTrendInWarehouse}" visible="false"/>
		<wcf:form id="mdxeditSaleTrendInWarehouse" xmlUri="/WEB-INF/jpivot/table/mdxedit.xml" model="#{qrySaleTrendInWarehouse}" visible="false"/>
		<wcf:form id="sortformSaleTrendInWarehouse" xmlUri="/WEB-INF/jpivot/table/sortform.xml" model="#{tblSaleTrendInWarehouse}" visible="false"/>
		
		<jp:print id="printSaleTrendInWarehouse"/>
		<wcf:form id="printformSaleTrendInWarehouse" xmlUri="/WEB-INF/jpivot/print/printpropertiesform.xml" model="#{printSaleTrendInWarehouse}" visible="false"/>
		
		<jp:chart id="chartSaleTrendInWarehouse" query="#{qrySaleTrendInWarehouse}" visible="false"/>
			<% _chart=(ChartComponent)session.getAttribute("chartSaleTrendInWarehouse");
			   _chart.setChartType(ChartTypeEnum.VerticalLine.ordinal());				
  	    	   _chart.setChartWidth(2500); 
			 %>
		
		<wcf:form id="chartformSaleTrendInWarehouse" xmlUri="/WEB-INF/jpivot/chart/chartpropertiesform.xml" model="#{chartSaleTrendInWarehouse}" visible="false"/>
		<wcf:table id="qrySaleTrendInWarehouse.drillthroughtable" visible="false" selmode="none" editable="true"/>
		
		<jp:selectproperties id="selectpropSaleTrendInWarehouse" table="#{tblSaleTrendInWarehouse}" visible="false"/>
		<h2>
		<c:out value="${titleSaleTrendInWarehouse}"/>
		</h2>
		
		<%-- define a toolbar --%>
		<wcf:toolbar id="toolbarSaleTrendInWarehouse" bundle="com.tonbeller.jpivot.toolbar.resources">
		  <wcf:scriptbutton id="cubeNaviButton" tooltip="toolb.cube" img="cube" model="#{naviSaleTrendInWarehouse.visible}"/>
		  <wcf:scriptbutton id="mdxEditButton" tooltip="toolb.mdx.edit" img="mdx-edit" model="#{mdxeditSaleTrendInWarehouse.visible}"/>
		  <wcf:scriptbutton id="sortConfigButton" tooltip="toolb.table.config" img="sort-asc" model="#{sortformSaleTrendInWarehouse.visible}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="levelStyle" tooltip="toolb.level.style" img="level-style" model="#{tblSaleTrendInWarehouse.extensions.axisStyle.levelStyle}"/>
		  <wcf:scriptbutton id="hideSpans" tooltip="toolb.hide.spans" img="hide-spans" model="#{tblSaleTrendInWarehouse.extensions.axisStyle.hideSpans}"/>
		  <wcf:scriptbutton id="propertiesButton" tooltip="toolb.properties"  img="properties" model="#{tblSaleTrendInWarehouse.rowAxisBuilder.axisConfig.propertyConfig.showProperties}"/>
		  <wcf:scriptbutton id="selectPropertiesButton" tooltip="toolb.properties"  img="properties-config" model="#{selectpropSaleTrendInWarehouse.visible}"/>
		  <wcf:scriptbutton id="nonEmpty" tooltip="toolb.non.empty" img="non-empty" model="#{tblSaleTrendInWarehouse.extensions.nonEmpty.buttonPressed}"/>
		  <wcf:scriptbutton id="swapAxes" tooltip="toolb.swap.axes"  img="swap-axes" model="#{tblSaleTrendInWarehouse.extensions.swapAxes.buttonPressed}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton model="#{tblSaleTrendInWarehouse.extensions.drillMember.enabled}"	 tooltip="toolb.navi.member" radioGroup="navi" id="drillMember"   img="navi-member"/>
		  <wcf:scriptbutton model="#{tblSaleTrendInWarehouse.extensions.drillPosition.enabled}" tooltip="toolb.navi.position" radioGroup="navi" id="drillPosition" img="navi-position"/>
		  <wcf:scriptbutton model="#{tblSaleTrendInWarehouse.extensions.drillReplace.enabled}"	 tooltip="toolb.navi.replace" radioGroup="navi" id="drillReplace"  img="navi-replace"/>
		  <wcf:scriptbutton model="#{tblSaleTrendInWarehouse.extensions.drillThrough.enabled}"  tooltip="toolb.navi.drillthru" id="drillThroughSaleTrendInWarehouse"  img="navi-through"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="chartButtonSaleTrendInWarehouse" tooltip="toolb.chart" img="chart" model="#{chartSaleTrendInWarehouse.visible}"/>
		  <wcf:scriptbutton id="chartPropertiesButtonSaleTrendInWarehouse" tooltip="toolb.chart.config" img="chart-config" model="#{chartformSaleTrendInWarehouse.visible}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="printPropertiesButtonSaleTrendInWarehouse" tooltip="toolb.print.config" img="print-config" model="#{printformSaleTrendInWarehouse.visible}"/>
		  <wcf:imgbutton id="printpdf" tooltip="toolb.print" img="print" href="./Print?cube=11&type=1"/>
		  <wcf:imgbutton id="printxls" tooltip="toolb.excel" img="excel" href="./Print?cube=11&type=0"/>
		</wcf:toolbar>
		
		<%-- render toolbar --%>
		<wcf:render ref="toolbarSaleTrendInWarehouse" xslUri="/WEB-INF/jpivot/toolbar/htoolbar.xsl" xslCache="true"/>
		<p>
		
		<%-- if there was an overflow, show error message --%>
		
		<c:if test="${qrySaleTrendInWarehouse.result.overflowOccured}">
		  <p>
		  <strong style="color:red">Resultset overflow occured</strong>
		  <p>
		</c:if>
		 
		<%-- render navigator --%>
		<wcf:render ref="naviSaleTrendInWarehouse" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true"/>
		<wcf:render ref="selectpropSaleTrendInWarehouse" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true"/>
		
		<%-- edit mdx --%>
		
		<c:if test="${mdxeditSaleTrendInWarehouse.visible}">
		  <h3>MDX Query Editor</h3>
		  <wcf:render ref="mdxeditSaleTrendInWarehouse" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		</c:if>
		
		 
		<%-- sort properties --%>
		<wcf:render ref="sortformSaleTrendInWarehouse" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<%-- chart properties --%>
		<wcf:render ref="chartformSaleTrendInWarehouse" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<%-- print properties --%>
		<wcf:render ref="printformSaleTrendInWarehouse" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<!-- render the table -->
		<p>
		<wcf:render ref="tblSaleTrendInWarehouse" xslUri="/WEB-INF/jpivot/table/mdxtable.xsl" xslCache="true"/>
		</p>
		Slicer:
		<wcf:render ref="tblSaleTrendInWarehouse" xslUri="/WEB-INF/jpivot/table/mdxslicer.xsl" xslCache="true"/>
		
		<p>
		<!-- drill through table -->
		<wcf:render ref="qrySaleTrendInWarehouse.drillthroughtable" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		</p>
		<!-- render chart -->
		<wcf:render ref="chartSaleTrendInWarehouse" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="true"/>
		
		<p>
		<a href=".">back to index</a>
		
		</form>
		</div>
	</td>
</tr>
</table>
</aip:skin>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2"><%--						<tiles:insert attribute="footer" />--%>
						<jsp:include page="/layout/footer.jsp"></jsp:include>
					
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>

