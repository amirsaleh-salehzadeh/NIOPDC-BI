<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="com.tonbeller.jpivot.chart.ChartComponent"%>
<%@page import="aip.jpivot.ChartTypeEnum"%>
<%@page import="aip.xmla.AIPOlap"%>
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
						<table class="rr_Table" id="bodyTable" border="0" cellspacing="0" cellpadding="0" width="99%">
							<tr>
								<td class="rr_BASE_top_left">&nbsp;</td>
								<td class="rr_BASE_top_top">&nbsp;</td>
								<td class="rr_BASE_top_right">&nbsp;</td>
							</tr>
							<tr>
								<td class="rr_BASE_left_left">&nbsp;</td>
								<td class="rr_BASE_center">

		

<%
			ChartComponent _chart=null;
		%>

<table border="1" align="center">
<%--<tr>--%>
<%--	<td>گزارش فروش مناطق در یک دوره زمانی--%>
<%--		<jp:xmlaQuery id="query12"  uri="http://192.168.0.52:80/olap/msmdpump.dll" catalog="NIOPDC_MRS_Ver1">--%>
<%--		SELECT {[Measures].[تعداد حواله],[Measures].[مبلغ پایه],[Measures].[مبلغ فیش],[Measures].[مبلغ کل],[Measures].[مقدار]--%>
<%--		        ,[Measures].[نرخ فرآورده]} on columns,--%>
<%--		       {[مناطق].[منطقه- ناحیه].children,--%>
<%--		       [مناطق].[منطقه-ناحیه].[همه]--%>
<%--		        } on rows--%>
<%--		       from [حواله]--%>
<%--		where  [زمان].[سال-فصل-ماه].[سال].&[1387]--%>
<%--		</jp:xmlaQuery>--%>
<%--		<form action="saleByLocations.jsp" method="post" id="form12">--%>
<%--			<jp:chart query="${query12}" id="chart12" visible="true" />--%>
<%--			<% _chart=(ChartComponent)session.getAttribute("chart12");--%>
<%--  			   _chart.setChartType(ChartTypeEnum.VerticalBar3D.ordinal());--%>
<%--  	    	   _chart.setChartWidth(300); --%>
<%--			 %>--%>
<%--			<wcf:render ref="chart12" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="false"/>--%>
<%--		</form>--%>
<%--	</td>--%>
<%--</tr>--%>


<tr>
	<td>گزارش فروش مناطق در یک دوره زمانی	
		<div dir="ltr" style="width: 1200px;overflow: auto;" >
		<form action="testquery.jsp" method="post">
		
		<c:if test="${qrySalLoc == null}">
		<jp:xmlaQuery id="qrySalLoc" uri="<%=AIPOlap.getDataSourceURI()%>" catalog="<%=AIPOlap.getCatalog()%>"> 
		SELECT {[Measures].[تعداد حواله],[Measures].[مبلغ پایه],[Measures].[مبلغ فیش],[Measures].[مبلغ کل],[Measures].[مقدار]
		        ,[Measures].[نرخ فرآورده]} on columns,
		       {[مناطق].[منطقه- ناحیه].children,
		       [مناطق].[منطقه-ناحیه].[همه]
		        } on rows
		       from [حواله]
		where  [زمان].[سال-فصل-ماه].[سال].&[1387]
		
		</jp:xmlaQuery>
		</c:if>
			 
			 
		<%-- define table, navigator and forms --%>
		<jp:table id="table11" query="#{qrySalLoc}"/>
		<jp:navigator id="navi11" query="#{qrySalLoc}" visible="false"/>
		<wcf:form id="mdxedit11" xmlUri="/WEB-INF/jpivot/table/mdxedit.xml" model="#{qrySalLoc}" visible="false"/>
		<wcf:form id="sortform11" xmlUri="/WEB-INF/jpivot/table/sortform.xml" model="#{table11}" visible="false"/>
		
		<jp:print id="print11"/>
		<wcf:form id="printform11" xmlUri="/WEB-INF/jpivot/print/printpropertiesform.xml" model="#{print11}" visible="false"/>
		
		<jp:chart id="chart11" query="#{qrySalLoc}" visible="false"/>
		<wcf:form id="chartform11" xmlUri="/WEB-INF/jpivot/chart/chartpropertiesform.xml" model="#{chart11}" visible="false"/>
		<wcf:table id="qrySalLoc.drillthroughtable" visible="false" selmode="none" editable="true"/>
		
		<jp:selectproperties id="selectprop11" table="#{table11}" visible="false"/>
		<h2>
		<c:out value="${title11}"/>
		</h2>
		
		<%-- define a toolbar --%>
		<wcf:toolbar id="toolbar11" bundle="com.tonbeller.jpivot.toolbar.resources">
		  <wcf:scriptbutton id="cubeNaviButton" tooltip="toolb.cube" img="cube" model="#{navi11.visible}"/>
		  <wcf:scriptbutton id="mdxEditButton" tooltip="toolb.mdx.edit" img="mdx-edit" model="#{mdxedit11.visible}"/>
		  <wcf:scriptbutton id="sortConfigButton" tooltip="toolb.table.config" img="sort-asc" model="#{sortform11.visible}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="levelStyle" tooltip="toolb.level.style" img="level-style" model="#{table11.extensions.axisStyle.levelStyle}"/>
		  <wcf:scriptbutton id="hideSpans" tooltip="toolb.hide.spans" img="hide-spans" model="#{table11.extensions.axisStyle.hideSpans}"/>
		  <wcf:scriptbutton id="propertiesButton" tooltip="toolb.properties"  img="properties" model="#{table11.rowAxisBuilder.axisConfig.propertyConfig.showProperties}"/>
		  <wcf:scriptbutton id="selectPropertiesButton" tooltip="toolb.properties"  img="properties-config" model="#{selectprop11.visible}"/>
		  <wcf:scriptbutton id="nonEmpty" tooltip="toolb.non.empty" img="non-empty" model="#{table11.extensions.nonEmpty.buttonPressed}"/>
		  <wcf:scriptbutton id="swapAxes" tooltip="toolb.swap.axes"  img="swap-axes" model="#{table11.extensions.swapAxes.buttonPressed}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton model="#{table11.extensions.drillMember.enabled}"	 tooltip="toolb.navi.member" radioGroup="navi" id="drillMember"   img="navi-member"/>
		  <wcf:scriptbutton model="#{table11.extensions.drillPosition.enabled}" tooltip="toolb.navi.position" radioGroup="navi" id="drillPosition" img="navi-position"/>
		  <wcf:scriptbutton model="#{table11.extensions.drillReplace.enabled}"	 tooltip="toolb.navi.replace" radioGroup="navi" id="drillReplace"  img="navi-replace"/>
		  <wcf:scriptbutton model="#{table11.extensions.drillThrough.enabled}"  tooltip="toolb.navi.drillthru" id="drillThrough11"  img="navi-through"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="chartButton11" tooltip="toolb.chart" img="chart" model="#{chart11.visible}"/>
		  <wcf:scriptbutton id="chartPropertiesButton11" tooltip="toolb.chart.config" img="chart-config" model="#{chartform11.visible}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="printPropertiesButton11" tooltip="toolb.print.config" img="print-config" model="#{printform11.visible}"/>
		  <wcf:imgbutton id="printpdf" tooltip="toolb.print" img="print" href="./Print?cube=11&type=1"/>
		  <wcf:imgbutton id="printxls" tooltip="toolb.excel" img="excel" href="./Print?cube=11&type=0"/>
		</wcf:toolbar>
		
		<%-- render toolbar --%>
		<wcf:render ref="toolbar11" xslUri="/WEB-INF/jpivot/toolbar/htoolbar.xsl" xslCache="true"/>
		<p>
		
		<%-- if there was an overflow, show error message --%>
		
		<c:if test="${qrySalLoc.result.overflowOccured}">
		  <p>
		  <strong style="color:red">Resultset overflow occured</strong>
		  <p>
		</c:if>
		 
		<%-- render navigator --%>
		<wcf:render ref="navi11" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true"/>
		<wcf:render ref="selectprop11" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true"/>
		
		<%-- edit mdx --%>
		
		<c:if test="${mdxedit11.visible}">
		  <h3>MDX Query Editor</h3>
		  <wcf:render ref="mdxedit11" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		</c:if>
		
		 
		<%-- sort properties --%>
		<wcf:render ref="sortform11" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<%-- chart properties --%>
		<wcf:render ref="chartform11" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<%-- print properties --%>
		<wcf:render ref="printform11" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<!-- render the table -->
		<p>
		<wcf:render ref="table11" xslUri="/WEB-INF/jpivot/table/mdxtable.xsl" xslCache="true"/>
		</p>
		Slicer:
		<wcf:render ref="table11" xslUri="/WEB-INF/jpivot/table/mdxslicer.xsl" xslCache="true"/>
		
		<p>
		<!-- drill through table -->
		<wcf:render ref="qrySalLoc.drillthroughtable" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		</p>
		<!-- render chart -->
		<wcf:render ref="chart11" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="true"/>
		
		<p>
		<a href=".">back to index</a>
		
		</form>
		</div>
	</td>
</tr>

</table>







































						</td>
								<td class="rr_BASE_right_right">&nbsp;</td>
							</tr>
							<tr>
								<td class="rr_BASE_bottom_left">&nbsp;</td>
								<td class="rr_BASE_bottom_bottom">&nbsp;</td>
								<td class="rr_BASE_bottom_right">&nbsp;</td>
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

