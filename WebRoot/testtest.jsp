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
	String defaultYear=""; 
String defaultSeason = "";
String defaultMonth = "";
String whereClause="";
%>

<%
	String reqCode=request.getParameter("reqCode");
	if(reqCode!=null && "filter".equalsIgnoreCase(reqCode)){
		defaultYear=NVL.getString(request.getParameter("location"),defaultYear);
		defaultSeason=NVL.getString(request.getParameter("zone"),defaultSeason);
		defaultMonth=NVL.getString(request.getParameter("type"),defaultMonth);
		session.setAttribute("qrySalAllCustTypeAllLoc1111",null);
		session.setAttribute("tblSalAllCustTypeAllLoc1111",null);

		if(!"".equals(defaultYear) || !"".equals(defaultSeason) || !"".equals(defaultMonth)){
	whereClause =" where [زمان].[سال-فصل-ماه].[سال]";
	if(!"".equals(defaultYear))
		whereClause+=".&["+defaultYear+"]";
	if(!"".equals(defaultSeason))
		whereClause+=".&["+defaultSeason+"]";
	if(!"".equals(defaultMonth))
		whereClause+=".&["+defaultMonth+"]";

		}
		out.println("whereClause="+whereClause);
	}
%>
<%
	DimensionMember[] yearCombos = AIPOlap.getDimTreeMembers("[زمان]","[زمان].[سال-فصل-ماه]","[زمان].[سال-فصل-ماه].[سال]");
	DimensionMember[] monthCombos = AIPOlap.getDimTreeMembers("[زمان]","[زمان].[سال-فصل-ماه]","[زمان].[سال-فصل-ماه].[ماه]");
	DimensionMember[] seasonCombos = AIPOlap.getDimTreeMembers("[زمان]","[زمان].[سال-فصل-ماه]","[زمان].[سال-فصل-ماه].[فصل]");
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
<form action="testtest.jsp" method="get">
	<input type="hidden" name="reqCode" value="filter">
		<select name="year">
			<option/>
		    <%
		    	for(int i=0;i<yearCombos.length;i++){
		    %>
		        <option class="yearCombo" value="<%=yearCombos[i].getMemberNum()%>" 
		        		<% if(yearCombos[i].getMemberNum().equals(defaultYear)){ out.print("selected='selected'"); } %> >
		        <%=yearCombos[i].getMemberCaption()%></option>
		    <%
		    	}
		    %>
		</select>
		<select name="season">
			<option/>
		    <%
		    	for(int i=0;i<seasonCombos.length;i++){
		    %>
		        <option class="seasonCombo" value="<%=seasonCombos[i].getMemberNum()%>" 
		        		<% if(seasonCombos[i].getMemberNum().equals(defaultSeason)){ out.print("selected='selected'"); } %> >
		        <%=seasonCombos[i].getMemberCaption()%></option>
		    <%
		    	}
		    %>
		</select>
		<select name="month">
			<option/>
		    <%
		    	for(int i=0;i<seasonCombos.length;i++){
		    %>
		        <option class="monthCombo" value="<%=monthCombos[i].getMemberNum()%>" 
		        		<% if(monthCombos[i].getMemberNum().equals(defaultMonth)){ out.print("selected='selected'"); } %> >
		        <%=monthCombos[i].getMemberCaption()%></option>
		    <%
		    	}
		    %>
		</select>

		
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
		<form action="testtest.jsp" method="post">
		
		<c:if test="${qrySalAllCustTypeAllLoc1111 == null}">
		<jp:xmlaQuery id="qrySalAllCustTypeAllLoc1111" uri="<%=AIPOlap.getDataSourceURI()%>" catalog="<%=AIPOlap.getCatalog()%>">  
		select  {[Measures].[تعداد حواله],[Measures].[مبلغ پایه],[Measures].[مبلغ فیش],[Measures].[مبلغ کل],[Measures].[مقدار]} on columns,
		    {
		        [مناطق].[منطقه-ناحیه].[همه]
		       } *  
		     {{
		          [مشتری].[نوع و محدوده].[همه]} 
		        }   on rows
		  
		from [حواله]
		<%=whereClause %>
		</jp:xmlaQuery>
		</c:if>
			 
			 
		<%-- define table, navigator and forms --%>
		<jp:table id="tblSalAllCustTypeAllLoc1111" query="#{qrySalAllCustTypeAllLoc1111}"/>
		<jp:navigator id="naviSalAllCustTypeAllLoc1111" query="#{qrySalAllCustTypeAllLoc1111}" visible="false"/>
		<wcf:form id="mdxeditSalAllCustTypeAllLoc1111" xmlUri="/WEB-INF/jpivot/table/mdxedit.xml" model="#{qrySalAllCustTypeAllLoc1111}" visible="false"/>
		<wcf:form id="sortformSalAllCustTypeAllLoc1111" xmlUri="/WEB-INF/jpivot/table/sortform.xml" model="#{tblSalAllCustTypeAllLoc1111}" visible="false"/>
		
		<jp:print id="printSalAllCustTypeAllLoc1111"/>
		<wcf:form id="printformSalAllCustTypeAllLoc1111" xmlUri="/WEB-INF/jpivot/print/printpropertiesform.xml" model="#{printSalAllCustTypeAllLoc1111}" visible="false"/>
		
		<jp:chart id="chartSalAllCustTypeAllLoc1111" query="#{qrySalAllCustTypeAllLoc1111}" visible="false"/>
			<% _chart=(ChartComponent)session.getAttribute("chartSalAllCustTypeAllLoc1111");
			   _chart.setChartType(ChartTypeEnum.VerticalLine.ordinal());				
  	    	   _chart.setChartWidth(2500); 
			 %>
		
		<wcf:form id="chartformSalAllCustTypeAllLoc1111" xmlUri="/WEB-INF/jpivot/chart/chartpropertiesform.xml" model="#{chartSalAllCustTypeAllLoc1111}" visible="false"/>
		<wcf:table id="qrySalAllCustTypeAllLoc1111.drillthroughtable" visible="false" selmode="none" editable="true"/>
		
		<jp:selectproperties id="selectpropSalAllCustTypeAllLoc1111" table="#{tblSalAllCustTypeAllLoc1111}" visible="false"/>
		<h2>
		<c:out value="${titleSalAllCustTypeAllLoc1111}"/>
		</h2>
		
		<%-- define a toolbar --%>
		<wcf:toolbar id="toolbarSalAllCustTypeAllLoc1111" bundle="com.tonbeller.jpivot.toolbar.resources">
		  <wcf:scriptbutton id="cubeNaviButton" tooltip="toolb.cube" img="cube" model="#{naviSalAllCustTypeAllLoc1111.visible}"/>
		  <wcf:scriptbutton id="mdxEditButton" tooltip="toolb.mdx.edit" img="mdx-edit" model="#{mdxeditSalAllCustTypeAllLoc1111.visible}"/>
		  <wcf:scriptbutton id="sortConfigButton" tooltip="toolb.table.config" img="sort-asc" model="#{sortformSalAllCustTypeAllLoc1111.visible}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="levelStyle" tooltip="toolb.level.style" img="level-style" model="#{tblSalAllCustTypeAllLoc1111.extensions.axisStyle.levelStyle}"/>
		  <wcf:scriptbutton id="hideSpans" tooltip="toolb.hide.spans" img="hide-spans" model="#{tblSalAllCustTypeAllLoc1111.extensions.axisStyle.hideSpans}"/>
		  <wcf:scriptbutton id="propertiesButton" tooltip="toolb.properties"  img="properties" model="#{tblSalAllCustTypeAllLoc1111.rowAxisBuilder.axisConfig.propertyConfig.showProperties}"/>
		  <wcf:scriptbutton id="selectPropertiesButton" tooltip="toolb.properties"  img="properties-config" model="#{selectpropSalAllCustTypeAllLoc1111.visible}"/>
		  <wcf:scriptbutton id="nonEmpty" tooltip="toolb.non.empty" img="non-empty" model="#{tblSalAllCustTypeAllLoc1111.extensions.nonEmpty.buttonPressed}"/>
		  <wcf:scriptbutton id="swapAxes" tooltip="toolb.swap.axes"  img="swap-axes" model="#{tblSalAllCustTypeAllLoc1111.extensions.swapAxes.buttonPressed}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton model="#{tblSalAllCustTypeAllLoc1111.extensions.drillMember.enabled}"	 tooltip="toolb.navi.member" radioGroup="navi" id="drillMember"   img="navi-member"/>
		  <wcf:scriptbutton model="#{tblSalAllCustTypeAllLoc1111.extensions.drillPosition.enabled}" tooltip="toolb.navi.position" radioGroup="navi" id="drillPosition" img="navi-position"/>
		  <wcf:scriptbutton model="#{tblSalAllCustTypeAllLoc1111.extensions.drillReplace.enabled}"	 tooltip="toolb.navi.replace" radioGroup="navi" id="drillReplace"  img="navi-replace"/>
		  <wcf:scriptbutton model="#{tblSalAllCustTypeAllLoc1111.extensions.drillThrough.enabled}"  tooltip="toolb.navi.drillthru" id="drillThroughSalAllCustTypeAllLoc1111"  img="navi-through"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="chartButtonSalAllCustTypeAllLoc1111" tooltip="toolb.chart" img="chart" model="#{chartSalAllCustTypeAllLoc1111.visible}"/>
		  <wcf:scriptbutton id="chartPropertiesButtonSalAllCustTypeAllLoc1111" tooltip="toolb.chart.config" img="chart-config" model="#{chartformSalAllCustTypeAllLoc1111.visible}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="printPropertiesButtonSalAllCustTypeAllLoc1111" tooltip="toolb.print.config" img="print-config" model="#{printformSalAllCustTypeAllLoc1111.visible}"/>
		  <wcf:imgbutton id="printpdf" tooltip="toolb.print" img="print" href="./Print?cube=11&type=1"/>
		  <wcf:imgbutton id="printxls" tooltip="toolb.excel" img="excel" href="./Print?cube=11&type=0"/>
		</wcf:toolbar>
		
		<%-- render toolbar --%>
		<wcf:render ref="toolbarSalAllCustTypeAllLoc1111" xslUri="/WEB-INF/jpivot/toolbar/htoolbar.xsl" xslCache="true"/>
		<p>
		
		<%-- if there was an overflow, show error message --%>
		
		<c:if test="${qrySalAllCustTypeAllLoc1111.result.overflowOccured}">
		  <p>
		  <strong style="color:red">Resultset overflow occured</strong>
		  <p>
		</c:if>
		 
		<%-- render navigator --%>
		<wcf:render ref="naviSalAllCustTypeAllLoc1111" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true"/>
		<wcf:render ref="selectpropSalAllCustTypeAllLoc1111" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true"/>
		
		<%-- edit mdx --%>
		
		<c:if test="${mdxeditSalAllCustTypeAllLoc1111.visible}">
		  <h3>MDX Query Editor</h3>
		  <wcf:render ref="mdxeditSalAllCustTypeAllLoc1111" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		</c:if>
		
		 
		<%-- sort properties --%>
		<wcf:render ref="sortformSalAllCustTypeAllLoc1111" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<%-- chart properties --%>
		<wcf:render ref="chartformSalAllCustTypeAllLoc1111" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<%-- print properties --%>
		<wcf:render ref="printformSalAllCustTypeAllLoc1111" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<!-- render the table -->
		<p>
		<wcf:render ref="tblSalAllCustTypeAllLoc1111" xslUri="/WEB-INF/jpivot/table/mdxtable.xsl" xslCache="true"/>
		</p>
		Slicer:
		<wcf:render ref="tblSalAllCustTypeAllLoc1111" xslUri="/WEB-INF/jpivot/table/mdxslicer.xsl" xslCache="true"/>
		
		<p>
		<!-- drill through table -->
		<wcf:render ref="qrySalAllCustTypeAllLoc1111.drillthroughtable" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		</p>
		<!-- render chart -->
		<wcf:render ref="chartSalAllCustTypeAllLoc1111" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="true"/>
		
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

