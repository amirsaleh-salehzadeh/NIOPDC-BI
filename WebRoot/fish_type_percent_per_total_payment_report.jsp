<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="com.tonbeller.jpivot.chart.ChartComponent"%>
<%@page import="aip.jpivot.ChartTypeEnum"%>
<%@page import="aip.util.NVL"%>
<%@page import="aip.xmla.AIPOlap"%>
<%@page import="rex.DimensionMember"%>

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
<%
	ChartComponent _chart=null;
%>
<%
	String defaultYear="1388"; 
String defaultSeason = "1";
String defaultMonth = "1";
String whereClause="";
%>

<%
	String reqCode=request.getParameter("reqCode");
	if(reqCode!=null && "filter".equalsIgnoreCase(reqCode)){
		defaultYear=NVL.getString(request.getParameter("year"),defaultYear);
		defaultMonth=NVL.getString(request.getParameter("month"),defaultMonth);
		defaultSeason=NVL.getString(request.getParameter("season"),defaultSeason);
		session.setAttribute("qryFshTypeTotalPym",null);
		session.setAttribute("tblFshTypeTotalPym",null);
        whereClause =" [زمان].[سال-فصل-ماه]";
		if(!"".equals(defaultYear) || !"".equals(defaultSeason) || !"".equals(defaultMonth)){
	whereClause =" [زمان].[سال-فصل-ماه].[سال]";
	if(!"".equals(defaultYear))
		whereClause+=".&["+defaultYear+"]";
	if(!"".equals(defaultSeason))
		whereClause+=".&["+defaultSeason+"]";
	if(!"".equals(defaultMonth))
		whereClause+=".&["+defaultMonth+"]";
		
		}
		//out.println("whereClause="+whereClause);
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
<form action="fish_type_percent_per_total_payment_report.jsp" method="get">
	<input type="hidden" name="reqCode" value="filter">
	<table align="right">
		<tr>
			<td align="right">
				<select name="year">
				    <%
				    	for(int i=0;i<yearCombos.length;i++){
				    %>
				    	<%
				    		if(yearCombos[i].getMemberNum().equals(defaultYear)){
				    	%>
				    		<option selected class="yearCombo" value="<%=yearCombos[i].getMemberNum()%>"><%=yearCombos[i].getMemberCaption()%></option>
				    	<%
				    		}else{
				    	%>
				        	<option class="yearCombo" value="<%=yearCombos[i].getMemberNum()%>"><%=yearCombos[i].getMemberCaption()%></option>
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
				    	for(int i=0;i<seasonCombos.length;i++){
				    %>
				    	<%
				    		if(seasonCombos[i].getMemberNum().equals(defaultSeason)){
				    	%>
				    		<option selected class="seasonCombo" value="<%=seasonCombos[i].getMemberNum()%>"><%=seasonCombos[i].getMemberCaption()%></option>
				        <%
				        	}else{
				        %>
				        	<option class="seasonCombo" value="<%=seasonCombos[i].getMemberNum()%>"><%=seasonCombos[i].getMemberCaption()%></option>
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
				    	for(int i=0;i<monthCombos.length;i++){
				    %>
				    	<%
				    		if(monthCombos[i].getMemberNum().equals(defaultMonth)){
				    	%>
				    		<option selected class="monthCombo" value="<%=monthCombos[i].getMemberNum()%>"><%=monthCombos[i].getMemberCaption()%></option>
				        <%
				        	}else{
				        %>
				        	<option class="monthCombo" value="<%=monthCombos[i].getMemberNum()%>"><%=monthCombos[i].getMemberCaption()%></option>
				        <%
				        	}
				        %>	
				    <%
					    	}
					    %>
				</select>
			</td>
			<td align="right">
		  		<input type="submit" value="تایید"/>													
			</td>
		</tr>
	</table>
</form>
</aip:skin>
<aip:skin type="BODY">					
<table align="center">
<tr>
	<td class="headerTitleDiv" align="center">گزارش درصد نوع فیش به کل پرداخت ها</td>
</tr>	
<tr>	
	<td align="left">
		<div dir="ltr" style="width: 1200px;overflow: auto;" >
		<form action="fish_type_percent_per_total_payment_report.jsp" method="post">
		
		<c:if test="${qryFshTypeTotalPym == null}">
		<jp:xmlaQuery id="qryFshTypeTotalPym" uri="<%=AIPOlap.getDataSourceURI()%>" catalog="<%=AIPOlap.getCatalog()%>">  
		with member [Measures].[درصد]
		as 'iif( ( [نوع فیش].[نوع].[همه]     
		          ,[Measures].[مبلغ فیش پرداخت شده])
		            =0 ,null,
		           [Measures].[مبلغ فیش پرداخت شده]/
		             ([Measures].[مبلغ فیش پرداخت شده], 
		               [نوع فیش].[نوع].[همه] ) )',
		FORMAT_STRING='Percent'         
		select  {[نوع فیش].children } on columns,
		[بانک].[بانک - شعبه] on rows
		from [حواله بافیش]
		where ( <%=whereClause %> , [Measures].[درصد]  )
<%--where [زمان].[سال-فصل-ماه]--%>
		
		</jp:xmlaQuery>
		</c:if>
			 
			 
		<%-- define table, navigator and forms --%>
		<jp:table id="tblFshTypeTotalPym" query="#{qryFshTypeTotalPym}"/>
		<jp:navigator id="naviFshTypeTotalPym" query="#{qryFshTypeTotalPym}" visible="false"/>
		<wcf:form id="mdxeditFshTypeTotalPym" xmlUri="/WEB-INF/jpivot/table/mdxedit.xml" model="#{qryFshTypeTotalPym}" visible="false"/>
		<wcf:form id="sortformFshTypeTotalPym" xmlUri="/WEB-INF/jpivot/table/sortform.xml" model="#{tblFshTypeTotalPym}" visible="false"/>
		
		<jp:print id="printFshTypeTotalPym"/>
		<wcf:form id="printformFshTypeTotalPym" xmlUri="/WEB-INF/jpivot/print/printpropertiesform.xml" model="#{printFshTypeTotalPym}" visible="false"/>
		
		<jp:chart id="chartFshTypeTotalPym" query="#{qryFshTypeTotalPym}" visible="false"/>
			<% _chart=(ChartComponent)session.getAttribute("chartFshTypeTotalPym");
			   _chart.setChartType(ChartTypeEnum.VerticalBar.ordinal());	
  	    	   _chart.setChartWidth(2500); 
			 %>
		
		<wcf:form id="chartformFshTypeTotalPym" xmlUri="/WEB-INF/jpivot/chart/chartpropertiesform.xml" model="#{chartFshTypeTotalPym}" visible="false"/>
		<wcf:table id="qryFshTypeTotalPym.drillthroughtable" visible="false" selmode="none" editable="true"/>
		
		<jp:selectproperties id="selectpropFshTypeTotalPym" table="#{tblFshTypeTotalPym}" visible="false"/>
		<h2>
		<c:out value="${titleFshTypeTotalPym}"/>
		</h2>
		
		<%-- define a toolbar --%>
		<wcf:toolbar id="toolbarFshTypeTotalPym" bundle="com.tonbeller.jpivot.toolbar.resources">
		  <wcf:scriptbutton id="cubeNaviButton" tooltip="toolb.cube" img="cube" model="#{naviFshTypeTotalPym.visible}"/>
		  <wcf:scriptbutton id="mdxEditButton" tooltip="toolb.mdx.edit" img="mdx-edit" model="#{mdxeditFshTypeTotalPym.visible}"/>
		  <wcf:scriptbutton id="sortConfigButton" tooltip="toolb.table.config" img="sort-asc" model="#{sortformFshTypeTotalPym.visible}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="levelStyle" tooltip="toolb.level.style" img="level-style" model="#{tblFshTypeTotalPym.extensions.axisStyle.levelStyle}"/>
		  <wcf:scriptbutton id="hideSpans" tooltip="toolb.hide.spans" img="hide-spans" model="#{tblFshTypeTotalPym.extensions.axisStyle.hideSpans}"/>
		  <wcf:scriptbutton id="propertiesButton" tooltip="toolb.properties"  img="properties" model="#{tblFshTypeTotalPym.rowAxisBuilder.axisConfig.propertyConfig.showProperties}"/>
		  <wcf:scriptbutton id="selectPropertiesButton" tooltip="toolb.properties"  img="properties-config" model="#{selectpropFshTypeTotalPym.visible}"/>
		  <wcf:scriptbutton id="nonEmpty" tooltip="toolb.non.empty" img="non-empty" model="#{tblFshTypeTotalPym.extensions.nonEmpty.buttonPressed}"/>
		  <wcf:scriptbutton id="swapAxes" tooltip="toolb.swap.axes"  img="swap-axes" model="#{tblFshTypeTotalPym.extensions.swapAxes.buttonPressed}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton model="#{tblFshTypeTotalPym.extensions.drillMember.enabled}"	 tooltip="toolb.navi.member" radioGroup="navi" id="drillMember"   img="navi-member"/>
		  <wcf:scriptbutton model="#{tblFshTypeTotalPym.extensions.drillPosition.enabled}" tooltip="toolb.navi.position" radioGroup="navi" id="drillPosition" img="navi-position"/>
		  <wcf:scriptbutton model="#{tblFshTypeTotalPym.extensions.drillReplace.enabled}"	 tooltip="toolb.navi.replace" radioGroup="navi" id="drillReplace"  img="navi-replace"/>
		  <wcf:scriptbutton model="#{tblFshTypeTotalPym.extensions.drillThrough.enabled}"  tooltip="toolb.navi.drillthru" id="drillThroughFshTypeTotalPym"  img="navi-through"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="chartButtonFshTypeTotalPym" tooltip="toolb.chart" img="chart" model="#{chartFshTypeTotalPym.visible}"/>
		  <wcf:scriptbutton id="chartPropertiesButtonFshTypeTotalPym" tooltip="toolb.chart.config" img="chart-config" model="#{chartformFshTypeTotalPym.visible}"/>
		  <wcf:separator/>
		  <wcf:scriptbutton id="printPropertiesButtonFshTypeTotalPym" tooltip="toolb.print.config" img="print-config" model="#{printformFshTypeTotalPym.visible}"/>
		  <wcf:imgbutton id="printpdf" tooltip="toolb.print" img="print" href="./Print?cube=11&type=1"/>
		  <wcf:imgbutton id="printxls" tooltip="toolb.excel" img="excel" href="./Print?cube=11&type=0"/>
		</wcf:toolbar>
		
		<%-- render toolbar --%>
		<wcf:render ref="toolbarFshTypeTotalPym" xslUri="/WEB-INF/jpivot/toolbar/htoolbar.xsl" xslCache="true"/>
		<p>
		
		<%-- if there was an overflow, show error message --%>
		
		<c:if test="${qryFshTypeTotalPym.result.overflowOccured}">
		  <p>
		  <strong style="color:red">Resultset overflow occured</strong>
		  <p>
		</c:if>
		 
		<%-- render navigator --%>
		<wcf:render ref="naviFshTypeTotalPym" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true"/>
		<wcf:render ref="selectpropFshTypeTotalPym" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="true"/>
		
		<%-- edit mdx --%>
		
		<c:if test="${mdxeditFshTypeTotalPym.visible}">
		  <h3>MDX Query Editor</h3>
		  <wcf:render ref="mdxeditFshTypeTotalPym" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		</c:if>
		
		 
		<%-- sort properties --%>
		<wcf:render ref="sortformFshTypeTotalPym" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<%-- chart properties --%>
		<wcf:render ref="chartformFshTypeTotalPym" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<%-- print properties --%>
		<wcf:render ref="printformFshTypeTotalPym" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		<!-- render the table -->
		<p>
		<wcf:render ref="tblFshTypeTotalPym" xslUri="/WEB-INF/jpivot/table/mdxtable.xsl" xslCache="true"/>
		</p>
		Slicer:
		<wcf:render ref="tblFshTypeTotalPym" xslUri="/WEB-INF/jpivot/table/mdxslicer.xsl" xslCache="true"/>
		
		<p>
		<!-- drill through table -->
		<wcf:render ref="qryFshTypeTotalPym.drillthroughtable" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="true"/>
		
		</p>
		<!-- render chart -->
		<wcf:render ref="chartFshTypeTotalPym" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="true"/>
		
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

