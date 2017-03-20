<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="com.tonbeller.jpivot.chart.ChartComponent"%>
<%@page import="aip.jpivot.ChartTypeEnum"%>
<%@page import="rex.metadata.ServerMetadata"%>
<%@page import="rex.graphics.datasourcetree.elements.DataSourceTreeElement"%>
<%@page import="rex.xmla.XMLADiscoverProperties"%>
<%@page import="rex.xmla.XMLADiscoverRestrictions"%>
<%@page import="rex.xmla.XMLAObjectsFactory"%>
<%@page import="rex.graphics.dimensiontree.elements.DimensionTreeElement"%>
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
								<td class="rr_BASE_top_right_Box">&nbsp;</td>
								<td class="rr_BASE_top_top_Box">&nbsp;</td>
								<td class="rr_BASE_top_left_Box">&nbsp;</td>
							</tr>
							<tr>
								<td class="rr_BASE_right_right_Box">&nbsp;</td>
								<td class="rr_BASE_center_Box">
<%
	ChartComponent _chart=null;
DimensionTreeElement dimension[]=null;
%>

<%
	ServerMetadata smd = new ServerMetadata(AIPOlap.getDataSourceURI());
	       DataSourceTreeElement ds[] = smd.discoverDataSources();
	       
	       if (ds != null){
		       for (int i = 0; ds != null && i < ds.length; i++) {
			XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
			XMLADiscoverProperties   properties   = XMLAObjectsFactory.newXMLADiscoverProperties();
		      		properties.setDataSourceInfo("TAHERI");
			properties.setCatalog("NIOPDC_MRS_Ver1");		
			restrictions.setDimensionUniqueName("[زمان]");
			dimension = ds[i].getServerMetaData().getDimensionTreeMembersList(restrictions, properties);
			System.out.println(".......................getDimensionTreeMembersList........................ces4.size="+dimension.length);
		
			for (DimensionTreeElement ce4 : dimension) {
				//System.out.println("\t"+ce4.getCaption() );
			}

		}
	       
	       }
%> 


<table border="1" align="center">
<select size="1">
    <optgroup>
    <%for(int n=0;n<10 && dimension.length>=10;n++){ DimensionTreeElement ce4 = dimension[n]; %>
        <option ><%=ce4.getCaption() %></option>
    <%} %>
    </optgroup>    
    <optgroup label="Asia">
        <option>India</option>
        <option>Dubai</option>
    </optgroup>
</select>
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

