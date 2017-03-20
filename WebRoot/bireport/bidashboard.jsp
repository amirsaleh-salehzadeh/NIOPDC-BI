<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="com.tonbeller.jpivot.chart.ChartComponent"%>
<%@page import="aip.niopdc.sellbi.orm.SellDAOManager"%>
<%@page import="aip.common.AIPWebUser"%>
<%@page import="aip.common.dashboard.DashboardLST"%>
<%@page import="aip.common.dashboard.DashboardENT"%>
<%@page import="aip.xmla.AIPXmla"%>
<%@page import="aip.util.NVL"%>
<%@page import="com.tonbeller.jpivot.xmla.XMLA_Model"%>
<%@page import="com.tonbeller.jpivot.olap.model.OlapModel"%>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf" %>
<head>
		<link href="css/common.css" rel="stylesheet" type="text/css" />
		<link href="css/niopdc.css" rel="stylesheet" type="text/css" />
</head>
<table cellpadding="0" cellspacing="0" border="0" width="68%"
	height="100%" align="center">
	<tr valign="bottom" height="100%">
		<td style="background-color: white;" align="center"
			width="100%%">
			<img src="/AIPNIOPDCSellBI/images/roundedBoxes/top.png"
				width="100%" align="middle">
		</td>
	</tr>
</table>
<table class="rr_Table" id="bodyTable" border="0" cellspacing="0"
	cellpadding="0" width="68%">
	<tr>
		<td class="rr_GREEN_top_left">
			&nbsp;
		</td>
		<td class="rr_GREEN_top_top">
			&nbsp;
		</td>
		<td class="rr_GREEN_top_right">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td class="rr_GREEN_left_left">
			&nbsp;
		</td>
		<td class="rr_GREEN_center" align="center">


<%

	DashboardLST lst = SellDAOManager.getDashboard().getDashboardLST( new AIPWebUser(request) );
	AIPXmla xmla = SellDAOManager.getXmla( request.getRemoteUser() );
	ChartComponent chart=null;
	
	for(int i=0;i<lst.getDashboardENTs().size();i++){
		DashboardENT ent = lst.getDashboardENTs().get(i);
		
		if(ent!=null){
		String qryName = "qryDashboard" + i;
		
		%>
			<jp:xmlaQuery id="<%= qryName %>"  uri="<%= xmla.getDataSource() %>" catalog="<%=xmla.getCatalog()%>">
			select {KPIValue("مقایسه با دوره قبل"),KPIGoal("مقایسه با دوره قبل"), KPIStatus("مقایسه با دوره قبل")} on columns,
			[فرآورده].[گروه-نرخ].[همه ].children on rows
			from [حواله]
			where   ([زمان].[سال-فصل-ماه].[سال].&[1387].&[3]) 
			</jp:xmlaQuery>


			<table>
				<tr>
					<td colspan="2" align="center" class="headerText">
						<%= NVL.getString( ent.getCaption() ) %>
					</td>
				</tr>
				<tr>
					<td>
						<jp:chart id="<%= "chart"+i %>" query="<%="#{"+qryName+"}"%>"  visible="true" />
						<%
							if( session.getAttribute("chart"+i)!=null && session.getAttribute("chart"+i) instanceof ChartComponent ){
								chart=(ChartComponent)session.getAttribute("chart"+i);
								int chartType=NVL.getInt( ent.getDiagramType() , 1 );
								chart.setChartType( chartType );
							}
							
							if( session.getAttribute("chart"+i)==null ){
								out.print("chart is null<br>");
							}
							if( !( session.getAttribute("chart"+i) instanceof ChartComponent) ){
								out.print("chart is  not from type of ChartComponent <br>");
							}
						%>
						<wcf:render ref="<%= "chart"+i %>" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="false"/>
					</td>
					<td>
						<%
							if( session.getAttribute(qryName)!=null && session.getAttribute(qryName) instanceof OlapModel )
							{
								OlapModel model = (OlapModel)session.getAttribute(qryName);
								%>
<%--									شرط : <%= model.getResult().getSlicer() %>--%>
								
								<%
							}
						 %>
					</td>
				</tr>
			</table>	
			<br>
			<img src="/AIPNIOPDCSellBI/images/roundedBoxes/line.png" >
			<br>

		
		<%
		}
	}
	
	

%>
		</td>
		<td class="rr_GREEN_right_right">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td class="rr_GREEN_bottom_left">
			&nbsp;
		</td>
		<td class="rr_GREEN_bottom_bottom">
			&nbsp;
		</td>
		<td class="rr_GREEN_bottom_right">
			&nbsp;
		</td>
	</tr>

</table>

