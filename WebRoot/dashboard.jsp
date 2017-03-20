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
	<td>kpi
		<jp:xmlaQuery id="query01"  uri="<%=AIPOlap.getDataSourceURI()%>" catalog="<%=AIPOlap.getCatalog()%>">
			select {KPIValue("مقایسه با دوره قبل"),KPIGoal("مقایسه با دوره قبل"), KPIStatus("مقایسه با دوره قبل")} on columns,
			[فرآورده].[گروه-نرخ].[همه ].children on rows
			from [حواله]
			where   ([زمان].[سال-فصل-ماه].[سال].&[1387].&[3]) 
		</jp:xmlaQuery>
		<form action="dashboard.jsp" method="post" id="form01">
			<jp:chart query="${query01}" id="chart01" visible="true" />
			<%
				_chart=(ChartComponent)session.getAttribute("chart01");
				  _chart.setChartWidth(300);
			%>
			<wcf:render ref="chart01" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="false"/>
		</form>
	</td>
	<td>آخرین فروش ریالی(روزانه)
		<jp:xmlaQuery id="query11"  uri="<%=AIPOlap.getDataSourceURI()%>" catalog="<%=AIPOlap.getCatalog()%>">
			WITH SET [LastWeek] as 
			'TAIL(FILTER([زمان].[سال-ماه-روز].[روز].members , [Measures].[مبلغ کل]
			                                            >0)
			            ,7)'
			select  [فرآورده].[گروه-نرخ].[همه ].children  on columns,
			[LastWeek] on rows
			from [حواله]
			where [Measures].[مبلغ فیش]
		</jp:xmlaQuery>
		<form action="dashboard.jsp" method="post" id="form11">
			<jp:chart query="${query11}" id="chart11" visible="true" />
			<%
				_chart=(ChartComponent)session.getAttribute("chart11");
				   _chart.setChartType(ChartTypeEnum.VerticalLine.ordinal()); 
				   _chart.setChartWidth(300);
			%>
			<wcf:render ref="chart11" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="false"/>
		</form>
	</td>
	<td>آخرین فروش حجمی(روزانه)
		<jp:xmlaQuery id="query12"  uri="<%=AIPOlap.getDataSourceURI()%>" catalog="<%=AIPOlap.getCatalog()%>">
			WITH SET [LastWeek] as 
			'TAIL(FILTER( [زمان].[سال-ماه-روز].[روز].members , [Measures].[مبلغ کل]
			                                            >0)
			            ,7)'
			select  [فرآورده].[گروه-نرخ].[همه ] on columns,
			        [LastWeek] on rows
			from [حواله]
			where [Measures].[مقدار]
		</jp:xmlaQuery>
		<form action="dashboard.jsp" method="post" id="form12">
			<jp:chart query="${query12}" id="chart12" visible="true" />
			<%
				_chart=(ChartComponent)session.getAttribute("chart12");
				   _chart.setChartType(ChartTypeEnum.VerticalLine.ordinal()); 
				   _chart.setChartWidth(300);
			%>
			<wcf:render ref="chart12" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="false"/>
		</form>
	</td>
</tr>
<tr>
</tr>
<tr>
	<td> سهم فروش فرآورده ها
		<jp:xmlaQuery id="query21"  uri="<%=AIPOlap.getDataSourceURI()%>" catalog="<%=AIPOlap.getCatalog()%>">
			with member [Measures].[سهم مشارکت  فرآورده] As
			'[Measures].[مبلغ فیش] /
			 ([فرآورده].[گروه - فرآورده- نرخ].CURRENTMEMBER.PARENT,[Measures].[مبلغ فیش] )' ,
			FORMAT_STRING='0.00'
			select [Measures].[سهم مشارکت  فرآورده] on columns,
			[فرآورده].[گروه-نرخ].[All].children on rows
			from [حواله]
			where ([زمان].[سال-فصل-ماه].[سال].&[1387])
		</jp:xmlaQuery>
		<form action="dashboard.jsp" method="post" id="form21">
			<jp:chart query="${query21}" id="chart21" visible="true" />
			<%
				_chart=(ChartComponent)session.getAttribute("chart21");
				   _chart.setChartType(ChartTypeEnum.PieChartsByRow.ordinal());
				   _chart.setChartWidth(300);
			%>
			<wcf:render ref="chart21" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="false"/>
		</form>
	</td>
	<td>سهم خرید مناطق
		<jp:xmlaQuery id="query22"  uri="<%=AIPOlap.getDataSourceURI()%>" catalog="<%=AIPOlap.getCatalog()%>">
			with member [Measures].[سهم فروش منطقه]
			as ' [Measures].[مبلغ فیش]/([Measures].[مبلغ فیش],
			[مناطق].[منطقه-ناحیه].CurrentMember.Parent )',
			
			FORMAT_STRING='0.00'
			select [Measures].[سهم فروش منطقه] on columns,
			[مناطق].[منطقه-ناحیه].[همه].children on rows
			from [حواله]
			where ([زمان].[سال-فصل-ماه].[سال].&[1388])
		</jp:xmlaQuery>
		<form action="dashboard.jsp" method="post" id="form22">
			<jp:chart query="${query22}" id="chart22" visible="true" />
			<%
				_chart=(ChartComponent)session.getAttribute("chart22");
				   _chart.setChartType(ChartTypeEnum.PieChartsByRow.ordinal());
				   _chart.setChartWidth(300);
			%>
			<wcf:render ref="chart22" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="false"/>
		</form>
	</td>
	
</tr>
<tr>
	<td>مقایسه با سال قبل
		<jp:xmlaQuery id="query31"  uri="<%=AIPOlap.getDataSourceURI()%>" catalog="<%=AIPOlap.getCatalog()%>">
			select  {[زمان].[سال-فصل-ماه].[سال].&[1387],
			[زمان].[سال-فصل-ماه].[سال].&[1386]}  on columns,
			[فرآورده].[گروه-نرخ].[گروه فرآورده].members  on rows
			from [حواله]
		</jp:xmlaQuery>
		<form action="dashboard.jsp" method="post" id="form31">
			<jp:chart query="${query31}" id="chart31" visible="true" />
			<%
				_chart=(ChartComponent)session.getAttribute("chart31");
				   _chart.setChartType(ChartTypeEnum.VerticalBar3D.ordinal());
				   _chart.setChartWidth(300);
			%>
			<wcf:render ref="chart31" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="false"/>
		</form>
	</td>
	

<%--	<td>مقایسه با سال قبل دوره--%>
<%--		<jp:xmlaQuery id="query32"  uri="<%= AIPOlap.getDataSourceURI() %>" catalog="<%= AIPOlap.getCatalog() %>">--%>
<%--			with Member [Measures].[ماه مشابه سال قبل]--%>
<%--			as ' ([Measures].[مبلغ فیش],--%>
<%--			     PARALLELPERIOD( [زمان].[سال-فصل-ماه].[ماه],--%>
<%--			                     12,--%>
<%--			                     [زمان].[سال-فصل-ماه].CURRENTMEMBER))'--%>
<%--			select  {[Measures].[مبلغ فیش] ,[Measures].[ماه مشابه سال قبل]}  on 0,--%>
<%--			{[فرآورده].[گروه-نرخ].children * DESCENDANTS([زمان].[سال-فصل-ماه].[سال].&[1387],--%>
<%--			                                 [زمان].[سال-فصل-ماه].[ماه],SELF)} on 1--%>
<%--			from [حواله]--%>
<%--		</jp:xmlaQuery>--%>
<%--		<form action="dashboard.jsp" method="post" id="form32">--%>
<%--			<jp:chart query="${query32}" id="chart32" visible="true" />--%>
<%--			<wcf:render ref="chart32" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="false"/>--%>
<%--		</form>--%>
<%--	</td>--%>
	<td>در صد رشد فروش مناطق نسبت به دوره قبل 
<%--		<jp:xmlaQuery id="query32"  uri="<%= AIPOlap.getDataSourceURI() %>" catalog="<%= AIPOlap.getCatalog() %>">--%>
<%--		SELECT {[Measures].[در صد رشد نسبت به دوره قبل]--%>
<%--		       }  on columns,--%>
<%--		{ [مناطق].[منطقه- ناحیه].children ,--%>
<%--		   [مناطق].[منطقه- ناحیه] } on rows--%>
<%--		from [حواله]--%>
<%--		where  [زمان].[سال-فصل-ماه].[سال].&[1387].&[4].&[10]--%>
<%--		</jp:xmlaQuery>--%>
<%--		<form action="dashboard.jsp" method="post" id="form32">--%>
<%--			<jp:chart query="${query32}" id="chart32" visible="true" />--%>
<%--			<% _chart=(ChartComponent)session.getAttribute("chart32");--%>
<%--			   _chart.setChartType(ChartTypeEnum.VerticalBar3D.ordinal());--%>
<%--			   _chart.setChartWidth(300); --%>
<%--			   %>--%>
<%--			<wcf:render ref="chart32" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="false"/>--%>
<%--		</form>--%>
	</td>

	<td>در صد رشد  فرآورده ها نسبت به دوره قبل 
		<jp:xmlaQuery id="query33"  uri="<%=AIPOlap.getDataSourceURI()%>" catalog="<%=AIPOlap.getCatalog()%>">
		 SELECT {[Measures].[ در صد افزایش قیمت]
		       }  on columns,
		 {[فرآورده].[گروه-نرخ].[همه ].children }
		  on rows
		 from [حواله]
		 where  [زمان].[سال-فصل-ماه].[سال].&[1387].&[4].&[10]
		</jp:xmlaQuery>
		<form action="dashboard.jsp" method="post" id="form33">
			<jp:chart query="${query33}" id="chart33" visible="true" />
			<% _chart=(ChartComponent)session.getAttribute("chart33");
			   _chart.setChartType(ChartTypeEnum.VerticalBar3D.ordinal());
			   _chart.setChartWidth(300); 
			   %>
			<wcf:render ref="chart33" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="false"/>
		</form>
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

