<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
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
	
	
	<body dir="rtl" background="images/background/background.png" id="pageLayout" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<table border="0" width="100%" cellspacing="0" align="center" cellpadding="0">
			<tbody>
				<tr align="center">
					<td colspan="2" >
<%--						<tiles:insert attribute="header" />--%>
<jsp:include page="/layout/header.jsp"></jsp:include>

					</td>
				</tr>
				<tr align="center"> 
					<td colspan="2" >
					
<%--						<tiles:insert attribute="menu" />--%>

<jsp:include page="/layout/menu.jsp"></jsp:include>
						
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
<%--						<tiles:insert attribute="banner" />--%>
					</td>
				</tr>
				<tr align="center">
					<td valign="top" style="padding-right: 0px; " align="right">
<%--						<tiles:insert attribute="navigation"/>--%>
<jsp:include page="/layout/navigation.jsp"></jsp:include>

					</td>
					<td width="100%" valign="top">
						<table class="rr_Table" id="bodyTable" border="0" cellspacing="0" cellpadding="0" width="99%">
							<tr>
								<td class="rr_BASE_top_right_Box">&nbsp;</td>
								<td class="rr_BASE_top_top_Box">&nbsp;</td>
								<td class="rr_BASE_top_left_Box">&nbsp;</td>
							</tr>
							<tr>
								<td class="rr_BASE_right_right_Box">&nbsp;</td>
								<td class="rr_BASE_center_Box">
<%--									<tiles:insert attribute="body"/>--%>
		








<div dir="ltr" style="width: 1200px;overflow: auto;" >
<form action="customer.jsp" method="post">

<%-- include query and title, so this jsp may be used with different queries --%>
<%--<wcf:include id="include01" httpParam="query" prefix="/WEB-INF/queries/" suffix=".jsp"/>--%>

<c:if test="${qryCustomer == null}">

<jp:xmlaQuery id="qryCustomer" uri="<%=AIPOlap.getDataSourceURI()%>" catalog="Waremart 2005"> 
SELECT NON EMPTY { Measures.AllMembers } ON COLUMNS,
	NON EMPTY { [Product].Family.Members } ON ROWS
FROM Sales
</jp:xmlaQuery>
	 
</c:if>

<%-- define table, navigator and forms --%>
<jp:table id="tblCustomer" query="#{qryCustomer}"/>
<jp:navigator id="navi01" query="#{qryCustomer}" visible="false"/>
<wcf:form id="mdxedit01" xmlUri="/WEB-INF/jpivot/table/mdxedit.xml" model="#{qryCustomer}" visible="false"/>
<wcf:form id="sortform01" xmlUri="/WEB-INF/jpivot/table/sortform.xml" model="#{tblCustomer}" visible="false"/>

<jp:print id="print01"/>
<wcf:form id="printform01" xmlUri="/WEB-INF/jpivot/print/printpropertiesform.xml" model="#{print01}" visible="false"/>

<jp:chart id="chart01" query="#{qryCustomer}" visible="false"/>
<wcf:form id="chartform01" xmlUri="/WEB-INF/jpivot/chart/chartpropertiesform.xml" model="#{chart01}" visible="false"/>
<wcf:table id="qryCustomer.drillthroughtable" visible="false" selmode="none" editable="true"/>

<jp:selectproperties id="selectprop01" table="#{tblCustomer}" visible="false"/>
<h2>
<c:out value="${title01}"/>
</h2>

<%-- define a toolbar --%>
<wcf:toolbar id="toolbar01" bundle="com.tonbeller.jpivot.toolbar.resources">
  <wcf:scriptbutton id="cubeNaviButton" tooltip="toolb.cube" img="cube" model="#{navi01.visible}"/>
  <wcf:scriptbutton id="mdxEditButton" tooltip="toolb.mdx.edit" img="mdx-edit" model="#{mdxedit01.visible}"/>
  <wcf:scriptbutton id="sortConfigButton" tooltip="toolb.table.config" img="sort-asc" model="#{sortform01.visible}"/>
  <wcf:separator/>
  <wcf:scriptbutton id="levelStyle" tooltip="toolb.level.style" img="level-style" model="#{tblCustomer.extensions.axisStyle.levelStyle}"/>
  <wcf:scriptbutton id="hideSpans" tooltip="toolb.hide.spans" img="hide-spans" model="#{tblCustomer.extensions.axisStyle.hideSpans}"/>
  <wcf:scriptbutton id="propertiesButton" tooltip="toolb.properties"  img="properties" model="#{tblCustomer.rowAxisBuilder.axisConfig.propertyConfig.showProperties}"/>
  <wcf:scriptbutton id="selectPropertiesButton" tooltip="toolb.properties"  img="properties-config" model="#{selectprop01.visible}"/>
  <wcf:scriptbutton id="nonEmpty" tooltip="toolb.non.empty" img="non-empty" model="#{tblCustomer.extensions.nonEmpty.buttonPressed}"/>
  <wcf:scriptbutton id="swapAxes" tooltip="toolb.swap.axes"  img="swap-axes" model="#{tblCustomer.extensions.swapAxes.buttonPressed}"/>
  <wcf:separator/>
  <wcf:scriptbutton model="#{tblCustomer.extensions.drillMember.enabled}"	 tooltip="toolb.navi.member" radioGroup="navi" id="drillMember"   img="navi-member"/>
  <wcf:scriptbutton model="#{tblCustomer.extensions.drillPosition.enabled}" tooltip="toolb.navi.position" radioGroup="navi" id="drillPosition" img="navi-position"/>
  <wcf:scriptbutton model="#{tblCustomer.extensions.drillReplace.enabled}"	 tooltip="toolb.navi.replace" radioGroup="navi" id="drillReplace"  img="navi-replace"/>
  <wcf:scriptbutton model="#{tblCustomer.extensions.drillThrough.enabled}"  tooltip="toolb.navi.drillthru" id="drillThrough01"  img="navi-through"/>
  <wcf:separator/>
  <wcf:scriptbutton id="chartButton01" tooltip="toolb.chart" img="chart" model="#{chart01.visible}"/>
  <wcf:scriptbutton id="chartPropertiesButton01" tooltip="toolb.chart.config" img="chart-config" model="#{chartform01.visible}"/>
  <wcf:separator/>
  <wcf:scriptbutton id="printPropertiesButton01" tooltip="toolb.print.config" img="print-config" model="#{printform01.visible}"/>
  <wcf:imgbutton id="printpdf" tooltip="toolb.print" img="print" href="./Print?cube=01&type=1"/>
  <wcf:imgbutton id="printxls" tooltip="toolb.excel" img="excel" href="./Print?cube=01&type=0"/>
</wcf:toolbar>

<%-- render toolbar --%>
<wcf:render ref="toolbar01" xslUri="/WEB-INF/jpivot/toolbar/htoolbar.xsl" xslCache="true"/>
<p>

<%-- if there was an overflow, show error message --%>

<c:if test="${qryCustomer.result.overflowOccured}">
  <p>
  <strong style="color:red">Resultset overflow occured</strong>
  <p>
</c:if>
 
<%-- render navigator --%>
<wcf:render ref="navi01" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true"/>
<wcf:render ref="selectprop01" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true"/>

<%-- edit mdx --%>

<c:if test="${mdxedit01.visible}">
  <h3>ویرایشگر</h3>
  <wcf:render ref="mdxedit01" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
</c:if>

 
<%-- sort properties --%>
<wcf:render ref="sortform01" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

<%-- chart properties --%>
<wcf:render ref="chartform01" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

<%-- print properties --%>
<wcf:render ref="printform01" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

<!-- render the table -->
<p>
<wcf:render ref="tblCustomer" xslUri="/WEB-INF/jpivot/table/mdxtable.xsl" xslCache="true"/>
</p>
شرط:
<wcf:render ref="tblCustomer" xslUri="/WEB-INF/jpivot/table/mdxslicer.xsl" xslCache="true"/>

<p>
<!-- drill through table -->
<wcf:render ref="qryCustomer.drillthroughtable" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>

</p>
<!-- render chart -->
<wcf:render ref="chart01" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="true"/>

<p>

</form>
</div>






						</td>
								<td class="rr_BASE_left_left_Box">&nbsp;</td>
							</tr>
							<tr>
								<td class="rr_BASE_bottom_right_Box">&nbsp;</td>
								<td class="rr_BASE_bottom_bottom_Box">&nbsp;</td>
								<td class="rr_BASE_bottom_left_Box">&nbsp;</td>
							</tr>
						</table>
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

