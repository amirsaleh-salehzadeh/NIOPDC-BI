<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="com.tonbeller.jpivot.chart.ChartComponent"%>
<%@page import="aip.jpivot.ChartTypeEnum"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="aip.common.JDBCConectionManager"%>
<%@page import="java.util.ArrayList"%>
<%@page import="aip.common.mdxreport.MDXReportDTO"%>
<%@page import="aip.util.NVL"%>
<%@page import="aip.util.UTF8"%>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld" %>

<%@ page language="java" pageEncoding="utf-8"%>
<%--<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>--%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

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

  <script type="text/javascript">
	$(document).ready(function(){
		var selcetdIdsArray = new Array();
		selcetdIdsArray = $("#hiddenIDs").val().split(',');
		for (var i = 0 ; i < selcetdIdsArray.length ; i++) {
			$("input[id="+selcetdIdsArray[i]+"]").attr('checked', 'checked');
		}
	});
	function selectCheckBox(chk){
		var txtHiddenIDs = $("#hiddenIDs").val();
		if($(chk).attr("checked") === true) {
			$("#hiddenIDs").val( txtHiddenIDs + $(chk).val() + "," );
		} else {
			var tmp = txtHiddenIDs.replace($(chk).val()+"," , "");
			$("#hiddenIDs").val( tmp );
		}
	}
	</script>

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
<%
ChartComponent _chart=null;
ArrayList<MDXReportDTO> list = new ArrayList<MDXReportDTO>();
int id=NVL.getInt(request.getParameter("queryId"));
String selectBtn = NVL.getString(UTF8.cnvUTF8(request.getParameter("btnDelete")));
System.out.println("id="+id);
%>
 
<%	
		try{
			Statement stmt = JDBCConectionManager.getAipCN().createStatement();
			ResultSet rs = stmt.executeQuery("select * from mrsmdxreport");
			MDXReportDTO dto = null;
			while(rs.next()){
				dto = new MDXReportDTO();
				dto.setId(NVL.getInt(rs.getInt("ID")));
				dto.setQueryName(NVL.getString(rs.getString("QueryName")));
				list.add(dto);
			}
			System.out.println("mdxList...........list.size="+list.size());			 				 
		}catch(SQLException ex) {
			ex.printStackTrace();
			System.err.print("SQLException: ");
			System.err.println(ex.getMessage());
		}
%>
<%
String selectedIds = request.getParameter("selectedIds");
System.out.println("....btnDelete="+selectBtn+"...selectedIds="+selectedIds);
request.setAttribute("selectedIds",selectedIds);

if(selectBtn.equals("حذف")){
	System.out.print("delte from table....................");
	//Statement stmt = JDBCConectionManager.getAipCN().createStatement();
	//stmt.executeUpdate("delete from mrsmdxreport where ID="+id);
	
}

%>

<aip:skin type="BODY">
	<form action="mdxlist.jsp" method="post">
		<input type='text' value="<%=NVL.getString(request.getAttribute("selectedIds"))%>" id="hiddenIDs" class="hiddenIDs" name="selectedIds"/>
		<table align="center" width="100%">
			<tr>
				<td>
					<table align="center" border="1" width="100%">
						<tr style="text-align: right;">
							<th/>
							<th>ردیف</th>
							<th>نام گزارش</th> 
						</tr>
						<%for(int i=0;i<list.size();i++){ %>
							<tr>
								<td>
									<input type="checkbox" class="chkSelect" name="queryId" id="<%=list.get(i).getId()%>" value="<%=list.get(i).getId() %>" onclick="selectCheckBox(this);"/>
								</td>
								<td align="right"><%= i+1 %></td>
								<td align="right"><%= list.get(i).getQueryName()%></td>
<%--								<input type="hidden" name="id" value="<%= list.get(i).getId() %>"/>--%>
							</tr>
						<%} %>
			
					</table>
		
<%--					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
				</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="نمایش در صفحه"/>
				</td>
				<td>
					<input type="submit" value="نمایش در داشبورد"/>
				</td>
				<td>
					<input type="submit" value="حذف"  name="btnDelete" />
				</td>
				<td>
					<input type="submit" value="ویرایش"/>
				</td>
				<td>
					<input type="submit" value="جدید"/>
				</td>

			</tr>
		</table>
	</form>
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

