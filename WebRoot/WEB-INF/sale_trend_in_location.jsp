<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="com.tonbeller.jpivot.chart.ChartComponent"%>
<%@page import="aip.jpivot.ChartTypeEnum"%>
<%@page import="aip.util.NVL"%>
<%@page import="rex.AIPOlap"%>
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
		session.setAttribute("qrySaleTrendInLoc",null);
		session.setAttribute("tblSaleTrendInLoc",null);

		if(!"".equals(defaultLocation) || !"".equals(defaultZone) ){
			whereClause =" where [مناطق].[منطقه- ناحیه]";
			if(!"".equals(defaultLocation))
				whereClause+=".&["+defaultLocation+"]";
			if(!"".equals(defaultZone))
				whereClause+=".&["+defaultZone+"]";
		}
		//out.println("whereClause="+whereClause);
	}
 %>
<%
	//DimensionMember[] locationCombos = AIPOlap.getDimTreeMembers("[مناطق]","[مناطق].[منطقه- ناحیه]","[مناطق].[منطقه-ناحیه].[Level 02]");
//	"[مناطق]","[مناطق].[منطقه- ناحیه]","[مناطق].[منطقه- ناحیه].[Level 02]");
	//DimensionMember[] zoneCombos = AIPOlap.getDimTreeMembers("[مناطق]","[مناطق].[منطقه- ناحیه]","[مناطق].[منطقه- ناحیه].[Level 03]");
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
<form action="sale_trend_in_location.jsp" method="get">
	<input type="hidden" name="reqCode" value="filter">
		منطقه <select name="location">
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
	<td class="headerTitleDiv" align="center">گزارش روند فروش در کشور/منطقه</td>
</tr>
<tr>	
	<td align="left">
		<div dir="ltr" style="width: 1200px;overflow: auto;" >
		<form action="sale_trend_in_location.jsp" method="post">
		
		<c:if test="${qrySaleTrendInLoc == null}">
		<jp:xmlaQuery id="qrySaleTrendInLoc" uri="<%= AIPOlap.getDataSourceURI() %>" catalog="<%= AIPOlap.getCatalog() %>">  
		SELECT {[Measures].[مبلغ کل],[Measures].[مبلغ پایه]} on columns,
		       {DESCENDANTS( [زمان].[سال-فصل-ماه2],
		                    <%= defaultTimeType %>,SELF)}
			   on rows
		from [حواله]
		<%=whereClause %>
<%--where  ([مناطق].[منطقه- ناحیه], [Measures].[مبلغ کل])--%>
		
		</jp:xmlaQuery>
		</c:if>
			 
			 
		<%-- define table, navigator and forms --%>
		<jp:table id="tblSaleTrendInLoc" query="#{qrySaleTrendInLoc}"/>
		<jp:navigator id="naviSaleTrendInLoc" query="#{qrySaleTrendInLoc}" visible="false"/>
		<wcf:form id="mdxeditSaleTrendInLoc" xmlUri="/WEB-INF/jpivot/table/mdxedit.xml" model="#{qrySaleTrendInLoc}" visible="false"/>
		<wcf:form id="sortformSaleTrendInLoc" xmlUri="/WEB-INF/jpivot/table/sortform.xml" model="#{tblSaleTrendInLoc}" visible="false"/>
		
		<jp:print id="printSaleTrendInLoc"/>
		<wcf:form id="printformSaleTrendInLoc" xmlUri="/WEB-INF/jpivot/print/printpropertiesform.xml" model="#{printSaleTrendInLoc}" visible="false"/>
		
		<jp:chart id="chartSaleTrendInLoc" query="#{qrySaleTrendInLoc}" visible="false"/>
			<% _chart=(ChartComponent)session.getAttribute("chartSaleTrendInLoc");
			   _chart.setChartType(ChartTypeEnum.VerticalLine.ordinal());				
  	    	   _chart.setChartWidth(2500); 
			 %>
		
		<wcf:form id="chartformSaleTrendInLoc" xmlUri="/WEB-INF/jpivot/chart/chartpropertiesform.xml" model="#{chartSaleTrendInLoc}" visible="false"/>
		<wcf:table id="qrySaleTrendInLoc.drillthroughtable" visible="false" selmode="none" editable="true"/>
		
		<jp:selectproperties id="selectpropSaleTrendInLoc" table="#{tblSaleTrendInLoc}" visible="false"/>
		<h2>
		<c:out value="${titleSaleTrendInLoc}"/>
		</h2>
		
		<%-- define a toolbar --%>
		<wcf:toolbar id="toolbarSaleTrendInLoc" bundle="com.tonbeller.jpivot.toolbar.resources">
		  <wcf:scriptbutton id="cubeNaviButton" tooltip="toolb.cube" img="cube" model="#{naviSaleTrendInLoc.visible}"/>
		  <wcf:scriptbutton id="mdxEditButton" tooltip="toolb.mdx.edit" img="mdx-edit" model="#{mdxeditSaleTrendInLoc.visible}"/>
		  <wcf:scriptbutton id="sortConfigButton" tooltip="toolb.table.config" img="sort-asc" model="#{sortformSaleTrendInLoc.visible}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="levelStyle" tooltip="toolb.level.style" img="level-style" model="#{tblSaleTrendInLoc.extensions.axisStyle.levelStyle}"/>
		  <wcf:scriptbutton id="hideSpans" tooltip="toolb.hide.spans" img="hide-spans" model="#{tblSaleTrendInLoc.extensions.axisStyle.hideSpans}"/>
		  <wcf:scriptbutton id="propertiesButton" tooltip="toolb.properties"  img="properties" model="#{tblSaleTrendInLoc.rowAxisBuilder.axisConfig.propertyConfig.showProperties}"/>
		  <wcf:scriptbutton id="selectPropertiesButton" tooltip="toolb.properties"  img="properties-config" model="#{selectpropSaleTrendInLoc.visible}"/>
		  <wcf:scriptbutton id="nonEmpty" tooltip="toolb.non.empty" img="non-empty" model="#{tblSaleTrendInLoc.extensions.nonEmpty.buttonPressed}"/>
		  <wcf:scriptbutton id="swapAxes" tooltip="toolb.swap.axes"  img="swap-axes" model="#{tblSaleTrendInLoc.extensions.swapAxes.buttonPressed}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton model="#{tblSaleTrendInLoc.extensions.drillMember.enabled}"	 tooltip="toolb.navi.member" radioGroup="navi" id="drillMember"   img="navi-member"/>
		  <wcf:scriptbutton model="#{tblSaleTrendInLoc.extensions.drillPosition.enabled}" tooltip="toolb.navi.position" radioGroup="navi" id="drillPosition" img="navi-position"/>
		  <wcf:scriptbutton model="#{tblSaleTrendInLoc.extensions.drillReplace.enabled}"	 tooltip="toolb.navi.replace" radioGroup="navi" id="drillReplace"  img="navi-replace"/>
		  <wcf:scriptbutton model="#{tblSaleTrendInLoc.extensions.drillThrough.enabled}"  tooltip="toolb.navi.drillthru" id="drillThroughSaleTrendInLoc"  img="navi-through"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="chartButtonSaleTrendInLoc" tooltip="toolb.chart" img="chart" model="#{chartSaleTrendInLoc.visible}"/>
		  <wcf:scriptbutton id="chartPropertiesButtonSaleTrendInLoc" tooltip="toolb.chart.config" img="chart-config" model="#{chartformSaleTrendInLoc.visible}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="printPropertiesButtonSaleTrendInLoc" tooltip="toolb.print.config" img="print-config" model="#{printformSaleTrendInLoc.visible}"/>
		  <wcf:imgbutton id="printpdf" tooltip="toolb.print" img="print" href="./Print?cube=11&type=1"/>
		  <wcf:imgbutton id="printxls" tooltip="toolb.excel" img="excel" href="./Print?cube=11&type=0"/>
		</wcf:toolbar>
		
		<%-- render toolbar --%>
		<wcf:render ref="toolbarSaleTrendInLoc" xslUri="/WEB-INF/jpivot/toolbar/htoolbar.xsl" xslCache="true"/>
		<p>
		
		<%-- if there was an overflow, show error message --%>
		
		<c:if test="${qrySaleTrendInLoc.result.overflowOccured}">
		  <p>
		  <strong style="color:red">Resultset overflow occured</strong>
		  <p>
		</c:if>
		 
		<%-- render navigator --%>
		<wcf:render ref="naviSaleTrendInLoc" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true"/>
		<wcf:render ref="selectpropSaleTrendInLoc" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true"/>
		
		<%-- edit mdx --%>
		
		<c:if test="${mdxeditSaleTrendInLoc.visible}">
		  <h3>MDX Query Editor</h3>
		  <wcf:render ref="mdxeditSaleTrendInLoc" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		</c:if>
		
		 
		<%-- sort properties --%>
		<wcf:render ref="sortformSaleTrendInLoc" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<%-- chart properties --%>
		<wcf:render ref="chartformSaleTrendInLoc" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<%-- print properties --%>
		<wcf:render ref="printformSaleTrendInLoc" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<!-- render the table -->
		<p>
		<wcf:render ref="tblSaleTrendInLoc" xslUri="/WEB-INF/jpivot/table/mdxtable.xsl" xslCache="true"/>
		</p>
		Slicer:
		<wcf:render ref="tblSaleTrendInLoc" xslUri="/WEB-INF/jpivot/table/mdxslicer.xsl" xslCache="true"/>
		
		<p>
		<!-- drill through table -->
		<wcf:render ref="qrySaleTrendInLoc.drillthroughtable" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		</p>
		<!-- render chart -->
		<wcf:render ref="chartSaleTrendInLoc" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="true"/>
		
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

