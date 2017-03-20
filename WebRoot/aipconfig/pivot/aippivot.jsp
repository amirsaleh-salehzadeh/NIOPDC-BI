<%@page session="true" contentType="text/html; charset=UTF-8"%>
<%@page import="aip.util.NVL"%>
<%@page import="aip.xmla.AIPOlap"%>
<%@page import="aip.jpivot.AIPPivotParam"%>
<%@page import="aip.util.AIPErrorHandler"%>
<%@page import="com.tonbeller.jpivot.chart.ChartComponent"%>
<%@page import="aip.jpivot.ChartTypeEnum"%>
<%@page import="aip.tag.tabbed.AIPTabs"%>
<%@page import="aip.tag.tabbed.AIPTab"%>
<%@page import="com.tonbeller.jpivot.olap.model.Result"%>
<%@page import="aip.util.AIPUtil"%>
<%@page import="aip.util.UTF8"%>

<%@ taglib uri="/WEB-INF/jpivot/jpivot-tags.tld" prefix="jp"%>
<%@ taglib uri="/WEB-INF/wcf/wcf-tags.tld" prefix="wcf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld"%>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld"%>

<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%!public void refreshPivot(HttpSession session, String queryName){
		session.setAttribute( queryName 	,null);
		session.setAttribute( "tbl"+queryName 	,null);
		session.setAttribute( queryName+".drillthroughtable" 	,null);
		session.setAttribute( "chart"+queryName 	,null);
	}
	public String getAttributeOrParameter(HttpServletRequest request, String name){
		return getAttributeOrParameter(request,name,false);
	}
	public String getAttributeOrParameter(HttpServletRequest request, String name,boolean cnvParametr2Utf8){
		if(request.getAttribute(name)!=null){
			if (cnvParametr2Utf8 ){
				return UTF8.cnvUTF8(request.getAttribute(name).toString());
			}else{
				return request.getAttribute(name).toString();
			}
		}else if(request.getParameter(name)!=null){
			if (cnvParametr2Utf8 ){
				return UTF8.cnvUTF8(request.getParameter(name));
			}else{
				return request.getParameter(name);
			}
		}
		return null;
	}
%>


<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="jpivot/table/mdxtable.css">
	<link rel="stylesheet" type="text/css" href="jpivot/navi/mdxnavi.css">
	<link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
	<link rel="stylesheet" type="text/css" href="wcf/table/xtable.css">
	<link rel="stylesheet" type="text/css" href="wcf/tree/xtree.css">

	<link rel="stylesheet" type="text/css" href="images/skin/message/aipskin_message.css">

<%--		<style>--%>
<%--			.verticaltextRight {--%>
<%--				display: block;--%>
<%--				right: -5px;--%>
<%--				top: 15px;--%>
<%--				direction: rtl;--%>
<%--				-webkit-transform: rotate(90deg);--%>
<%--				-moz-transform: rotate(90deg);--%>
<%--			}--%>
<%--			--%>
<%--			.verticaltextLeft {--%>
<%--				display: block;--%>
<%--				right: -5px;--%>
<%--				top: 15px;--%>
<%--				direction: rtl;--%>
<%--				-webkit-transform: rotate(-90deg);--%>
<%--				-moz-transform: rotate(-90deg);--%>
<%--			}--%>
<%--		</style>--%>
	<script type="text/javascript">
			function dlgDashboard_click(){
				try{
				var options = {
				    success: function(req) {
				        alert("اطلاعات با موفقیت ذخیره شد.");
				    }
				    ,error: function(req){
				    	alert("ذخیره با خطا مواجه شد!" + req.responseText);
				    }
				   };
					$('#frmDashboardSave').ajaxSubmit(options);
				}catch(e){
					alert(e);
				}
			}
	</script>

</head>

<table class="clsAIPPivot" height="100%" align="center" cellpadding="0" cellspacing="0"><tr><td valign="top" style="border: 3px solid #044B74 ;background-color: #DCE8EF;" height="100%">



<%
   
	String reportType= NVL.getEmptyString( request.getParameter("reporttype") ,"table");
	String formAction ;
	   
	AIPPivotParam param = (AIPPivotParam)request.getAttribute("AIPPivotParam"); 
	 
	if(param==null){ 
		System.out.println(">>>>>>>>>>>>>>>>>param==null"); 
		formAction = getAttributeOrParameter(request,"pivotFormAction",true); 
		String queryName = getAttributeOrParameter(request,"pivotQueryName",true); 
		String mdxQuery = getAttributeOrParameter(request,"pivotMdxQuery",true); 
		String refresh = getAttributeOrParameter(request,"pivotRefresh",true); 
		if(formAction!=null && queryName!=null && mdxQuery!=null){ 
			param=new AIPPivotParam(); 
			param.setFormAction(formAction); 
			param.setQueryName(queryName); 
			param.setMdxQuery(mdxQuery); 
			param.setRefresh(NVL.getBool(refresh)); 
			System.out.println(">>>>>>>>>>>>>>>>>param create "); 
			AIPUtil.printObject(param); 
		} 
	} else{
		formAction=param.getFormAction();
	}

	
	    
   
	if(param!=null && param.getMdxQuery()!=null 
		&& ( param.getMdxQuery().toUpperCase().indexOf("SELECT")>=0 
			|| param.getMdxQuery().toUpperCase().indexOf("WITH")>=0)
		){    
try{
%>
<script type="text/javascript">
$(document).ready(function(){
$('#AipPivotMdx_reportName').val($('#frmAipBiMdx_reportName').val());
});

function saveDashboard(){
	var loader = "/AIPNIOPDCSellBI/biaddashboard.jsp?toMdx="+$('#pivotMdxQuery').val();
	loader = loader + "&reportName=" + $('#frmAipBiMdx_reportName').val();
	showDialog('dlgDashboard','center',loader);
}


</script>




<div dir="ltr" style="width: 1000px;overflow:auto;" >



<form action="<%=formAction%>" method="post" id="frmAippivot">
	<input type="hidden" name="pivotFormAction" value="<%=formAction%>"/>
	<input type="hidden" name="pivotQueryName"  value="<%=param.getQueryName()%>"/>
	<input type="hidden" name="pivotMdxQuery" id="pivotMdxQuery"  value="<%=param.getMdxQuery()%>"/>
	<input type="hidden" name="pivotreportName" id="AipPivotMdx_reportName" value="test" />
	
	<input type="hidden" name="pivotMdxQueryNC"  value="<%=UTF8.cnvUTF8( NVL.getString(request.getParameter("mdxquerync"),getAttributeOrParameter(request,"pivotMdxQueryNC")))%>"/>
	<input type="hidden" name="reqCode"  value="<%=request.getParameter("reqCode")%>"/>
	
	<input type="hidden" name="pivotIsFirstCall"  value="<%=NVL.getString(getAttributeOrParameter(request,"pivotIsFirstCall"))%>"/>

	<%
		if(session.getAttribute(param.getQueryName())==null || param.isRefresh() || request.getParameter("refreshPivot")!=null ){ 
		refreshPivot(session,param.getQueryName());
		try{
	%>
		<jp:xmlaQuery id="<%=param.getQueryName()%>" uri="<%=AIPOlap.getDataSourceURI()%>" catalog="<%=AIPOlap.getCatalog()%>">  
			<%= param.getMdxQuery().replaceAll("_amp;", "&") %>
		</jp:xmlaQuery>
		
	<%	}catch(Exception ex){
			//refreshPivot(session,param.getQueryName());
			 
			System.out.println("IllegalStateException@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			String error = AIPErrorHandler.handle(request, this, ex, "", "");
			request.setAttribute("errorMessage",error);
		}
	
		} 
	%>
	
	
	<%
		try{
			if(!"table".equalsIgnoreCase(reportType)){
				int i=ChartTypeEnum.valueOf(reportType).ordinal();// if not supported chart requested exception throwed and report type changed to table 
			}
		}catch(Exception ex){
			reportType="table";
		}
	
		System.out.println("))))))))))))))))))))))))))))))))))))))))))))))):reportType=" + reportType);
		if("table".equalsIgnoreCase(reportType)){
		
	%>
	<jp:table id="<%= "tbl"+param.getQueryName() %>" query="<%="#{"+param.getQueryName()+"}"%>" />
	<jp:navigator id="<%= "navi"+param.getQueryName() %>" query="<%="#{"+param.getQueryName()+"}"%>" visible="trye" />
	<wcf:form id="<%= "sortform"+param.getQueryName() %>" xmlUri="/WEB-INF/jpivot/table/sortform.xml" model="<%= "#{tbl"+param.getQueryName()+"}"%>" visible="false" />
	<wcf:form id="<%= "printform"+param.getQueryName() %>" xmlUri="/WEB-INF/jpivot/print/printpropertiesform.xml" model="<%= "#{print"+param.getQueryName()+"}"%>" visible="false" />
	<jp:print id="<%= "print"+param.getQueryName() %>" />
	<wcf:table id="<%= param.getQueryName()+".drillthroughtable" %>" visible="false" selmode="none" editable="true" />


	<wcf:toolbar id="<%= "toolbar"+param.getQueryName() %>" bundle="com.tonbeller.jpivot.toolbar.resources">
		<wcf:scriptbutton id="cubeNaviButton" tooltip="toolb.cube" img="cube" model="<%= "#{navi"+param.getQueryName()+".visible}"%>" />
		<wcf:scriptbutton id="sortConfigButton" tooltip="toolb.table.config" img="sort-asc" model="<%= "#{sortform"+param.getQueryName()+".visible}"%>" />
		<wcf:separator />
		<wcf:scriptbutton id="levelStyle" tooltip="toolb.level.style" img="level-style" model="<%= "#{tbl"+param.getQueryName()+".extensions.axisStyle.levelStyle}"%>" />
		<wcf:scriptbutton id="hideSpans" tooltip="toolb.hide.spans" img="hide-spans" model="<%= "#{tbl"+param.getQueryName()+".extensions.axisStyle.hideSpans}"%>" />
<%--		<wcf:scriptbutton id="propertiesButton" tooltip="toolb.properties" img="properties" model="<%= "#{tbl"+param.getQueryName()+".rowAxisBuilder.axisConfig.propertyConfig.showProperties}"%>" />--%>
		<wcf:scriptbutton id="nonEmpty" tooltip="toolb.non.empty" img="non-empty" model="<%= "#{tbl"+param.getQueryName()+".extensions.nonEmpty.buttonPressed}"%>" />
		<wcf:scriptbutton id="swapAxes" tooltip="toolb.swap.axes" img="swap-axes" model="<%= "#{tbl"+param.getQueryName()+".extensions.swapAxes.buttonPressed}"%>" />
		<wcf:separator />
		<wcf:scriptbutton model="<%= "#{tbl"+param.getQueryName()+".extensions.drillMember.enabled}"%>" tooltip="toolb.navi.member" radioGroup="navi" id="drillMember" img="navi-member" />
		<wcf:scriptbutton model="<%= "#{tbl"+param.getQueryName()+".extensions.drillPosition.enabled}"%>" tooltip="toolb.navi.position" radioGroup="navi" id="drillPosition" img="navi-position" />
		<wcf:scriptbutton model="<%= "#{tbl"+param.getQueryName()+".extensions.drillReplace.enabled}"%>" tooltip="toolb.navi.replace" radioGroup="navi" id="drillReplace" img="navi-replace" />
		<wcf:scriptbutton model="<%= "#{tbl"+param.getQueryName()+".extensions.drillThrough.enabled}"%>" tooltip="toolb.navi.drillthru" id="<%="drillThrough"+param.getQueryName() %>" img="navi-through" />
		<wcf:separator />
		<wcf:scriptbutton id="<%= "printPropertiesButton"+param.getQueryName() %>" tooltip="toolb.print.config" img="print-config" model="<%= "#{printform"+param.getQueryName()+".visible}"%>" />
		<wcf:imgbutton id="printpdf" tooltip="toolb.print" img="print" href="./Print?cube=11&type=1" />
		<wcf:imgbutton id="printxls" tooltip="toolb.excel" img="excel" href="./Print?cube=11&type=0" />
		<wcf:imgbutton id="refresh" tooltip="بازسازی" img="refresh" href="<%= formAction+"?reqCode='refresh'" %>" />
		<wcf:imgbutton id="saveDashboard" tooltip="ذخیره داشبورد" img="print" href=""  />
		
	</wcf:toolbar>
		<img src="images/icons/save.png" title="ذخیره داشبورد" onclick="saveDashboard()" width="35" height="35" style="position:relative; left: 520; top: 35; cursor: pointer;">		
		<a href="aipbimdx.jsp?reqCode=edit&reportId=<%=request.getParameter("reportId") %>"><img src="images/icons/save.png" title="ویرایش" width="35" height="35" style="position:relative; left: 520; top: 35; cursor: pointer;"></a>
	<wcf:render ref="<%= "toolbar"+param.getQueryName() %>" xslUri="/WEB-INF/jpivot/toolbar/htoolbar.xsl" xslCache="false" />

	<% 
	//if( NVL.getBool( NVL.getStringContextMethod(session.getAttribute(param.getQueryName()),"getResult.isOverflowOccured") )){
	Object xmlaQuery = session.getAttribute(param.getQueryName());
	Result result=null;
	if(xmlaQuery!=null){
		result=(Result)AIPUtil.invokeMethod(xmlaQuery,"getResult");
	}
	if(xmlaQuery==null || result==null || result.isOverflowOccured()){
		request.setAttribute("errorMessage","سربار اطلاعاتی رخ داده لطفا مجددا سعی کنید");
		refreshPivot(session,param.getQueryName()); 
	} %>

<%--	 render navigator --%>
	<wcf:render ref="<%= "navi"+param.getQueryName() %>" xslUri="/WEB-INF/jpivot/navi/navigator.xsl" xslCache="false" />

<%--	 sort properties --%>
	<wcf:render ref="<%= "sortform"+param.getQueryName() %>" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="false" />

<%--	 print properties --%>
	<wcf:render ref="<%= "printform"+param.getQueryName() %>" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="false" />

	<!-- render the table -->
	<p>
		<wcf:render ref="<%= "tbl"+param.getQueryName() %>" xslUri="/WEB-INF/jpivot/table/mdxtable.xsl" xslCache="false" />
	</p>
	
	شرط:
	<wcf:render ref="<%= "tbl"+param.getQueryName() %>" xslUri="/WEB-INF/jpivot/table/mdxslicer.xsl" xslCache="false" />
	<p>
		<!-- drill through table -->
		<wcf:render ref="<%= ""+param.getQueryName()+".drillthroughtable"%>" xslUri="/WEB-INF/wcf/wcf.xsl" xslCache="false" />

	</p>
	<br/>
	<br/>
	<% }else{ %>
		<jp:chart id="<%= "chart"+param.getQueryName() %>" query="<%="#{"+param.getQueryName()+"}"%>" visible="true" />
	<%	
	
		System.out.println("))))))))))))))))))))))))))))))))))))))))))))))):reportType=" + reportType); 
		
		ChartComponent _chart=(ChartComponent)session.getAttribute("chart"+param.getQueryName());
		if(_chart!=null)
		{
			try{
			System.out.println("aippivot.jsp-!!!!!!!!!!!!!!!!!!!!!!!!!!!!!:reportType="+reportType);
			 _chart.setChartType(ChartTypeEnum.valueOf(reportType).ordinal());
			 }catch(Exception ex){
				refreshPivot(session,param.getQueryName()); 
			 	ex.printStackTrace();
			 	request.setAttribute("errorMessage",ex.getMessage());
			 }
			try{			 
	%>
		<wcf:render ref="<%= "chart"+param.getQueryName() %>" xslUri="/WEB-INF/jpivot/chart/chart.xsl" xslCache="false" />
	<%  	}catch(Exception ex) {
				refreshPivot(session,param.getQueryName());
			 
				System.out.println("IllegalStateException@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				String error = AIPErrorHandler.handle(request, this, ex, "", "");
				request.setAttribute("errorMessage",error);
			}
		} 
		System.out.println("))))))))))))))))))))))))))))))))))))))))))))))):reportType=" + reportType); 
	
	}
	
}catch(IllegalStateException ex){
		refreshPivot(session,param.getQueryName()); 

	System.out.println("IllegalStateException@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	String error = AIPErrorHandler.handle(request, this, ex, "", "");
	request.setAttribute("errorMessage",error);
	
}catch(Exception ex){
	refreshPivot(session,param.getQueryName()); 

	System.out.println("Exception!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	String error = AIPErrorHandler.handle(request, this, ex, "", "");
	request.setAttribute("errorMessage",error);
}

	
	 %>

	
	<input type="hidden" id="txtReporttype" name="reporttype" value="<%= request.getParameter("reporttype") %>">
	<input type="hidden" id="txtReportname" name="reportname" value="<%= request.getParameter("reportname") %>">
	<input type="hidden" id="txtReportdim" name="reportdim" value="<%= request.getParameter("reportdim") %>">

</form>
</div>



</td><td valign="top" align="right" >

<%
	AIPTabs tabs = new AIPTabs("frmAippivot","txtReporttype");
	tabs.add(new AIPTab( "table","جدولی"));
	tabs.add(new AIPTab("VerticalLine","خطی"));
	tabs.add(new AIPTab("VerticalBar3D","میله"));
	tabs.add(new AIPTab("PieChart3DRow","دایره"));

	tabs.setSelectedName( NVL.getEmptyString( request.getParameter("reporttype") ,"table"));

	request.setAttribute("AIPTabs",tabs);

 %>
<jsp:include page="/aipconfig/tabbed/aiptabbed-left.jsp"></jsp:include>

</td></tr></table>


<%


if(request.getAttribute("errorMessage")!=null){
	refreshPivot(session,param.getQueryName()); 
}

}else{

	request.setAttribute("errorMessage","اشکال در پارامترهای ارسالی برای aippivot");

}//end if(param!=null && param.getMdxQuery()!=null && param.getMdxQuery().toUpperCase().indexOf("SELECT")>=0

%>

<aip:message errorMessage="<%= NVL.getString( request.getAttribute("errorMessage") ) %>" successMessage="<%=NVL.getString( request.getAttribute("successMessage"))%>"/>


<aip:dialog styleId="dlgDashboard" loader="" title="ذخیره گزارش در داشبورد" style="simple" screenPosition="center" onOKClick="dlgDashboard_click();"  img="" >
</aip:dialog>
