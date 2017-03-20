<%@page session="true" contentType="text/html; charset=UTF-8"%>
<%@page import="aip.util.NVL"%>
<%@page import="aip.xmla.AIPOlap"%>
<%@page import="aip.jpivot.AIPPivotParam"%>

<%@ taglib uri="/WEB-INF/jpivot/jpivot-tags.tld" prefix="jp"%>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld"%>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld"%>

<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<head>
<%--	<link rel="shortcut icon" href="aip-co.ico">--%>
<%--	<link rel="icon" type="image/ico" href="aip-co.ico">--%>
<%--	<link href="css/niopdc.css" rel="stylesheet" type="text/css" />--%>
<%--	<link href="css/dialogStyles.css" type="text/css" rel="stylesheet" />--%>
<%--	<link href="css/popupStyles.css" rel="stylesheet" type="text/css" />--%>
<%--	<link href="css/navigation.css" rel="stylesheet" type="text/css">--%>
<%--	<link href="css/menu.css" rel="stylesheet" type="text/css">--%>
<%--	<link href="css/common.css" rel="stylesheet" type="text/css" />--%>

<%--	<script src="js/common/jquery-1.2.6.pack.js" type="text/javascript"></script>--%>
<%--	<script src="js/common/aip.niopdc.common.js" type="text/javascript"></script>--%>
<%--	<script src="js/common/aip.niopdc.menu.js" type="text/javascript"></script>--%>
<%--	<script src="js/common/aip.niopdcsell.common.js" type="text/javascript"></script>--%>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="jpivot/table/mdxtable.css">
	<link rel="stylesheet" type="text/css" href="jpivot/navi/mdxnavi.css">
	<link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
	<link rel="stylesheet" type="text/css" href="wcf/table/xtable.css">
	<link rel="stylesheet" type="text/css" href="wcf/tree/xtree.css">
</head>
<%
	AIPPivotParam param = (AIPPivotParam)request.getAttribute("AIPPivotParam");
	if(param!=null){
%>

<div dir="ltr" style="width: 1200px;overflow: auto;" >
<form action="<%=param.getFormAction()%>" method="post">

	<%
		System.out.println("pivot.jsp:refresh="+param.isRefresh());
			if(session.getAttribute(param.getQueryName())==null || param.isRefresh() || "refresh".equalsIgnoreCase(request.getParameter("reqCode"))){ 
		System.out.println("refresh xmla query!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.flush();
	%>
		<jp:xmlaQuery id="<%=param.getQueryName()%>" uri="<%=AIPOlap.getDataSourceURI()%>" catalog="<%=AIPOlap.getCatalog()%>">  
			<%= param.getMdxQuery() %>
		</jp:xmlaQuery>
		
	<%} %>
	<%-- define table, navigator and forms --%>
	<jp:table id="<%= "tbl"+param.getQueryName() %>" query="<%="#{"+param.getQueryName()+"}"%>" />
	<jp:navigator id="<%= "navi"+param.getQueryName() %>" query="<%="#{"+param.getQueryName()+"}"%>" visible="false" />
	<jp:chart id="<%= "chart"+param.getQueryName() %>" query="<%="#{"+param.getQueryName()+"}"%>" visible="false" />

	<wcf:form id="<%= "mdxedit"+param.getQueryName() %>" xmlUri="/WEB-INF/jpivot/table/mdxedit.xml" model="<%= "#{"+param.getQueryName()+"}"%>" visible="false" />
	<wcf:form id="<%= "sortform"+param.getQueryName() %>" xmlUri="/WEB-INF/jpivot/table/sortform.xml" model="<%= "#{tbl"+param.getQueryName()+"}"%>" visible="false" />
	<wcf:form id="<%= "printform"+param.getQueryName() %>" xmlUri="/WEB-INF/jpivot/print/printpropertiesform.xml" model="<%= "#{print"+param.getQueryName()+"}"%>" visible="false" />
	<wcf:form id="<%= "chartform"+param.getQueryName() %>" xmlUri="/WEB-INF/jpivot/chart/chartpropertiesform.xml" model="<%= "#{chart"+param.getQueryName()+"}"%>" visible="false" />

	<jp:print id="<%= "print"+param.getQueryName() %>" />
	
	<wcf:table id="<%= param.getQueryName()+".drillthroughtable" %>" visible="false" selmode="none" editable="true" />

	<jp:selectproperties id="<%= "selectprop"+param.getQueryName() %>" table="<%= "#{tbl"+param.getQueryName()+"}" %>" visible="false" />
	<h2>
<%--		<c:out value="<%="${title"+param.getQueryName()+"}"%>" />--%>
	</h2>

<%--	 define a toolbar --%>
	<wcf:toolbar id="<%= "toolbar"+param.getQueryName() %>" bundle="com.tonbeller.jpivot.toolbar.resources">
		<wcf:scriptbutton id="cubeNaviButton" tooltip="toolb.cube" img="cube" model="<%= "#{navi"+param.getQueryName()+".visible}"%>" />
		<wcf:scriptbutton id="mdxEditButton" tooltip="toolb.mdx.edit" img="mdx-edit" model="<%= "#{mdxedit"+param.getQueryName()+".visible}"%>" />
		<wcf:scriptbutton id="sortConfigButton" tooltip="toolb.table.config" img="sort-asc" model="<%= "#{sortform"+param.getQueryName()+".visible}"%>" />
		<wcf:separator />
		<wcf:scriptbutton id="levelStyle" tooltip="toolb.level.style" img="level-style" model="<%= "#{tbl"+param.getQueryName()+".extensions.axisStyle.levelStyle}"%>" />
		<wcf:scriptbutton id="hideSpans" tooltip="toolb.hide.spans" img="hide-spans" model="<%= "#{tbl"+param.getQueryName()+".extensions.axisStyle.hideSpans}"%>" />
		<wcf:scriptbutton id="propertiesButton" tooltip="toolb.properties" img="properties" model="<%= "#{tbl"+param.getQueryName()+".rowAxisBuilder.axisConfig.propertyConfig.showProperties}"%>" />
		<wcf:scriptbutton id="selectPropertiesButton" tooltip="toolb.properties" img="properties-config" model="<%= "#{selectprop"+param.getQueryName()+".visible}"%>" />
		<wcf:scriptbutton id="nonEmpty" tooltip="toolb.non.empty" img="non-empty" model="<%= "#{tbl"+param.getQueryName()+".extensions.nonEmpty.buttonPressed}"%>" />
		<wcf:scriptbutton id="swapAxes" tooltip="toolb.swap.axes" img="swap-axes" model="<%= "#{tbl"+param.getQueryName()+".extensions.swapAxes.buttonPressed}"%>" />
		<wcf:separator />
		<wcf:scriptbutton model="<%= "#{tbl"+param.getQueryName()+".extensions.drillMember.enabled}"%>" tooltip="toolb.navi.member" radioGroup="navi" id="drillMember" img="navi-member" />
		<wcf:scriptbutton model="<%= "#{tbl"+param.getQueryName()+".extensions.drillPosition.enabled}"%>" tooltip="toolb.navi.position" radioGroup="navi" id="drillPosition" img="navi-position" />
		<wcf:scriptbutton model="<%= "#{tbl"+param.getQueryName()+".extensions.drillReplace.enabled}"%>" tooltip="toolb.navi.replace" radioGroup="navi" id="drillReplace" img="navi-replace" />
		<wcf:scriptbutton model="<%= "#{tbl"+param.getQueryName()+".extensions.drillThrough.enabled}"%>" tooltip="toolb.navi.drillthru" id="<%="drillThrough"+param.getQueryName() %>" img="navi-through" />
		<wcf:separator />
		<wcf:scriptbutton id="<%= "chartButton"+param.getQueryName() %>" tooltip="toolb.chart" img="chart" model="<%= "#{chart"+param.getQueryName()+".visible}"%>" />
		<wcf:scriptbutton id="<%= "chartPropertiesButton"+param.getQueryName() %>" tooltip="toolb.chart.config" img="chart-config" model="<%= "#{chartform"+param.getQueryName()+".visible}"%>" />
		<wcf:separator />
		<wcf:scriptbutton id="<%= "printPropertiesButton"+param.getQueryName() %>" tooltip="toolb.print.config" img="print-config" model="<%= "#{printform"+param.getQueryName()+".visible}"%>" />
		<wcf:imgbutton id="printpdf" tooltip="toolb.print" img="print" href="./Print?cube=11&type=1" />
		<wcf:imgbutton id="printxls" tooltip="toolb.excel" img="excel" href="./Print?cube=11&type=0" />
		<wcf:imgbutton id="refresh" tooltip="بازسازی" img="refresh"  href="#"  />
		<img src="images/buttons/refresh.png" alt="" onclick=""/>
	</wcf:toolbar>

<%--	 render toolbar --%>
	<wcf:render ref="<%= "toolbar"+param.getQueryName() %>" xslUri="/WEB-INF/jpivot/toolbar/htoolbar.xsl" xslCache="true" />


<%--	 if there was an overflow, show error message --%>

<%--	<c:if test="<%="${"+param.getQueryName()+".result.overflowOccured}"%>">--%>
	<% if( NVL.getBool( NVL.getStringContext(session.getAttribute(param.getQueryName()),"result.overflowOccured") )){ %>
		<p>
			<strong style="color: red">Resultset overflow occured</strong>
		<p>
	<%} %>
<%--	</c:if>--%>

<%--	 render navigator --%>
	<wcf:render ref="<%= "navi"+param.getQueryName() %>" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true" />
	<wcf:render ref="<%= "selectprop"+param.getQueryName() %>" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true" />

<%--	 edit mdx --%>

<%--	<c:if test="${<%="mdxedit"+param.getQueryName()+".visible"%>}">--%>
	<% if( NVL.getBool( NVL.getStringContext(session.getAttribute("mdxedit"+param.getQueryName()),"visible") )){ %>
		<h3>
			MDX Query Editor
		</h3>
		<wcf:render ref="<%= "mdxedit"+param.getQueryName() %>" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true" />
	<%} %>
<%--	</c:if>--%>


<%--	 sort properties --%>
	<wcf:render ref="<%= "sortform"+param.getQueryName() %>" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true" />

<%--	 chart properties --%>
	<wcf:render ref="<%= "chartform"+param.getQueryName() %>" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true" />

<%--	 print properties --%>
	<wcf:render ref="<%= "printform"+param.getQueryName() %>" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true" />

	<!-- render the table -->
	<p>
		<wcf:render ref="<%= "tbl"+param.getQueryName() %>" xslUri="/WEB-INF/jpivot/table/mdxtable.xsl" xslCache="true" />
	</p>
	شرط:
	<wcf:render ref="<%= "tbl"+param.getQueryName() %>" xslUri="/WEB-INF/jpivot/table/mdxslicer.xsl" xslCache="true" />

	<p>
		<!-- drill through table -->
		<wcf:render ref="<%= ""+param.getQueryName()+".drillthroughtable"%>" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true" />

	</p>
	<!-- render chart -->
	<wcf:render ref="<%= "chart"+param.getQueryName() %>" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="true" />



</form>
</div>

<%
}
%>
