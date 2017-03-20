<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="com.tonbeller.jpivot.chart.ChartComponent"%>
<%@page import="aip.jpivot.ChartTypeEnum"%>
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
								<td class="rr_BASE_top_right_Box">&nbsp;</td>
								<td class="rr_BASE_top_top_Box">&nbsp;</td>
								<td class="rr_BASE_top_left_Box">&nbsp;</td>
							</tr>
							<tr>
								<td class="rr_BASE_right_right_Box">&nbsp;</td>
								<td class="rr_BASE_center_Box">

		

<%
ChartComponent _chart=null;
 %>

<table border="1" align="center">

<tr>
	<td>	
		<div dir="ltr" style="width: 1200px;overflow: auto;" >
		<form action="salesreport.jsp" method="post">
		
		<%-- include query and title, so this jsp may be used with different queries --%>
<%--		<wcf:include id="include31" httpParam="query" prefix="/WEB-INF/queries/" suffix=".jsp"/>--%>
		
		<jp:xmlaQuery id="query31" uri="<%=AIPOlap.getDataSourceURI()%>" catalog="NIOPDC_MRS_Ver1"> 
				SELECT  {[Measures].[تعداد حواله],[Measures].[مبلغ پایه],[Measures].[مبلغ فیش],[Measures].[مبلغ کل],[Measures].[مقدار]
				        ,[Measures].[نرخ فرآورده]} on columns,
				        crossjoin ( { [مناطق].[منطقه-ناحیه].[همه]
				                    }  ,
				                    { [فرآورده].[گروه-نرخ].[All]
				                      
				                    }
				                  ) on rows 
				from [حواله]
				where  [زمان].[سال-فصل-ماه].[سال].&[1387]
		
		</jp:xmlaQuery>
			 
		<%-- define table, navigator and forms --%>
		<jp:table id="table31" query="#{query31}"/>
		<jp:navigator id="navi31" query="#{query31}" visible="false"/>
		<wcf:form id="mdxedit31" xmlUri="/WEB-INF/jpivot/table/mdxedit.xml" model="#{query31}" visible="false"/>
		<wcf:form id="sortform31" xmlUri="/WEB-INF/jpivot/table/sortform.xml" model="#{table31}" visible="false"/>
		
		<jp:print id="print31"/>
		<wcf:form id="printform31" xmlUri="/WEB-INF/jpivot/print/printpropertiesform.xml" model="#{print31}" visible="false"/>
		
		<jp:chart id="chart31" query="#{query31}" visible="false"/>
		<wcf:form id="chartform31" xmlUri="/WEB-INF/jpivot/chart/chartpropertiesform.xml" model="#{chart31}" visible="false"/>
		<wcf:table id="query31.drillthroughtable" visible="false" selmode="none" editable="true"/>
		
		<jp:selectproperties id="selectprop31" table="#{table31}" visible="false"/>
		<h2>
		<c:out value="${title31}"/>
		</h2>
		
		<%-- define a toolbar --%>
		<wcf:toolbar id="toolbar31" bundle="com.tonbeller.jpivot.toolbar.resources">
		  <wcf:scriptbutton id="cubeNaviButton" tooltip="toolb.cube" img="cube" model="#{navi31.visible}"/>
		  <wcf:scriptbutton id="mdxEditButton" tooltip="toolb.mdx.edit" img="mdx-edit" model="#{mdxedit31.visible}"/>
		  <wcf:scriptbutton id="sortConfigButton" tooltip="toolb.table.config" img="sort-asc" model="#{sortform31.visible}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="levelStyle" tooltip="toolb.level.style" img="level-style" model="#{table31.extensions.axisStyle.levelStyle}"/>
		  <wcf:scriptbutton id="hideSpans" tooltip="toolb.hide.spans" img="hide-spans" model="#{table31.extensions.axisStyle.hideSpans}"/>
		  <wcf:scriptbutton id="propertiesButton" tooltip="toolb.properties"  img="properties" model="#{table31.rowAxisBuilder.axisConfig.propertyConfig.showProperties}"/>
		  <wcf:scriptbutton id="selectPropertiesButton" tooltip="toolb.properties"  img="properties-config" model="#{selectprop31.visible}"/>
		  <wcf:scriptbutton id="nonEmpty" tooltip="toolb.non.empty" img="non-empty" model="#{table31.extensions.nonEmpty.buttonPressed}"/>
		  <wcf:scriptbutton id="swapAxes" tooltip="toolb.swap.axes"  img="swap-axes" model="#{table31.extensions.swapAxes.buttonPressed}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton model="#{table31.extensions.drillMember.enabled}"	 tooltip="toolb.navi.member" radioGroup="navi" id="drillMember"   img="navi-member"/>
		  <wcf:scriptbutton model="#{table31.extensions.drillPosition.enabled}" tooltip="toolb.navi.position" radioGroup="navi" id="drillPosition" img="navi-position"/>
		  <wcf:scriptbutton model="#{table31.extensions.drillReplace.enabled}"	 tooltip="toolb.navi.replace" radioGroup="navi" id="drillReplace"  img="navi-replace"/>
		  <wcf:scriptbutton model="#{table31.extensions.drillThrough.enabled}"  tooltip="toolb.navi.drillthru" id="drillThrough31"  img="navi-through"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="chartButton31" tooltip="toolb.chart" img="chart" model="#{chart31.visible}"/>
		  <wcf:scriptbutton id="chartPropertiesButton31" tooltip="toolb.chart.config" img="chart-config" model="#{chartform31.visible}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="printPropertiesButton31" tooltip="toolb.print.config" img="print-config" model="#{printform31.visible}"/>
		  <wcf:imgbutton id="printpdf" tooltip="toolb.print" img="print" href="./Print?cube=31&type=1"/>
		  <wcf:imgbutton id="printxls" tooltip="toolb.excel" img="excel" href="./Print?cube=31&type=0"/>
		</wcf:toolbar>
		
		<%-- render toolbar --%>
		<wcf:render ref="toolbar31" xslUri="/WEB-INF/jpivot/toolbar/htoolbar.xsl" xslCache="true"/>
		<p>
		
		<%-- if there was an overflow, show error message --%>
		
		<c:if test="${query31.result.overflowOccured}">
		  <p>
		  <strong style="color:red">Resultset overflow occured</strong>
		  <p>
		</c:if>
		 
		<%-- render navigator --%>
		<wcf:render ref="navi31" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true"/>
		<wcf:render ref="selectprop31" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true"/>
		
		<%-- edit mdx --%>
		
		<c:if test="${mdxedit31.visible}">
		  <h3>MDX Query Editor</h3>
		  <wcf:render ref="mdxedit31" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		</c:if>
		
		 
		<%-- sort properties --%>
		<wcf:render ref="sortform31" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<%-- chart properties --%>
		<wcf:render ref="chartform31" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<%-- print properties --%>
		<wcf:render ref="printform31" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<!-- render the table -->
		<p>
		<wcf:render ref="table31" xslUri="/WEB-INF/jpivot/table/mdxtable.xsl" xslCache="true"/>
		</p>
		Slicer:
		<wcf:render ref="table31" xslUri="/WEB-INF/jpivot/table/mdxslicer.xsl" xslCache="true"/>
		
		<p>
		<!-- drill through table -->
		<wcf:render ref="query31.drillthroughtable" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		</p>
		<!-- render chart -->
		<wcf:render ref="chart31" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="true"/>
		
		<p>
		<a href=".">back to index</a>
		
		</form>
		</div>
	</td>
</tr>


</table>







































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

