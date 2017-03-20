<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="aip.util.SecurityUtil"%>
<%@page import="com.tonbeller.jpivot.chart.ChartComponent"%>
<%@page import="aip.jpivot.ChartTypeEnum"%>
<%@page import="aip.xmla.AIPOlap"%>
<%@page import="aip.common.dashboard.DashboardENT"%>
<%@page import="aip.util.NVL"%>
<%@page import="aip.niopdc.sellbi.orm.SellDAOManager"%>
<%@page import="aip.util.UTF8"%>
<%@page import="aip.common.AIPWebUser"%>
<%@page import="aip.common.dashboard.DashboardLST"%>
<%@page import="aip.common.dashboard.DashboardComboLST"%>
<%@page import="aip.common.security.group.GroupLST"%>
<%@page import="aip.common.security.group.GroupDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld" %>

<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
	
	<%			
	GroupLST dashboardComboLST = new GroupLST();
	AIPWebUser user = new AIPWebUser();
	if("".equalsIgnoreCase(request.getParameter("reqCode")) || request.getParameter("reqCode") == null){
		user.setRemoteUser(request.getRemoteUser());
		dashboardComboLST = SellDAOManager.getDashboard().getUserDashboardComboLST(user);
	}else if("save".equalsIgnoreCase(request.getParameter("reqCode"))){
		DashboardENT dashboardENT = new DashboardENT();
		String saveType = request.getParameter("radio");
		if("private".equalsIgnoreCase(saveType)){
			dashboardENT.setUserName(request.getParameter("username"));
			dashboardENT.setGroupId(null);	
		}else if ("public".equalsIgnoreCase(saveType)){
			dashboardENT.setUserName(null);
			dashboardENT.setGroupId(null);	
		}else if("group".equalsIgnoreCase(saveType)){
			dashboardENT.setUserName(null);
			dashboardENT.setGroupId(NVL.getInt(request.getParameter("groupId")));
		}
			dashboardENT.setCaption(request.getParameter("name"));
			dashboardENT.setDashboardNo(NVL.getInteger(request.getParameter("id")));
			dashboardENT.setDiagramType(NVL.getInteger(request.getParameter("diagram")));
			dashboardENT.setDashboardQuery(UTF8.cnvUTF8(request.getParameter("mdx")));
		try{
			
			SellDAOManager.getDashboard().saveDashboard(dashboardENT);
			} catch(Exception e){
				e.printStackTrace();
			} 
	}
	 %>
<form id="frmDashboardSave" action="biaddashboard.jsp?reqCode=save" method="post">
<table cellpadding="5" cellspacing="5" border="0">
	<tr>
		<td>نام گزارش:</td>
		<td><input type="text" name="name" value="<%=request.getParameter("reportName")==null?"":UTF8.cnvUTF8(request.getParameter("reportName")) %>" ></td>
		<td><input type="hidden" name="username" value="<%=user.getRemoteUser() %>"> </td>
	</tr>
	<tr>
		<td>ردیف :</td>
		<td><input type="hidden" name="mdx" value="<%=UTF8.cnvUTF8(request.getParameter("toMdx")) %>">
		<input type="text" name="id"></td>
	</tr>
	<tr>
		<td>نوع دیاگرام :</td>
		<td>
			<select name="diagram">
				<option value="1" >نمودار میله ای عمودی</option>
				<option value="2" >نمودار میله ای عمودی سه بعدی</option>
				<option value="3" >نمودار میله ای افقی</option>
				<option value="4" >نمودار میله ای افقی سه بعدی</option>
				<option value="5" >نمودار پشته میله ای عمودی</option>
				<option value="6" >نمودار پشته میله ای عمودی سه بعدی</option>
				<option value="7" >نمودار پشته میله ای افقی</option>
				<option value="8" >نمودار پشته میله ای افقی سه بعدی</option>
				<option value="9" >نمدار خطی عمودی</option>
				<option value="10" >نمودار خطی افقی</option>
				<option value="11" >نمودار مساحت عمودی</option>
				<option value="12" >نمودار مساحت افقی</option>
				<option value="13" >نمودار پشته مساحت عمودی</option>
				<option value="14" >نمودار پشته مساحت افقی</option>
				<option value="15" >نمودار دایره ایی بر اساس ستون</option>
				<option value="16" >نمودار دایره ایی بر اساس سطر</option>
			</select>
		
		</td>
		<td></td>
	</tr>
	<tr>
		<td> 
			<%SecurityUtil util = new SecurityUtil();
				if(util.isUserInRoleByUN(request.getRemoteUser(),"definePersonalDashboard,admin")){ %>
		          شخصی:        
		          
		          
           <input type="radio" name="radio" value="private" checked="checked"> </td>
		<%} %>
		<td>
		<%if(util.isUserInRoleByUN(request.getRemoteUser(),"definePublicDashboard,admin")){ %>
		عمومی:
		
		<input type="radio" name="radio" value="public" checked="checked"><%} %>
		<%if(util.isUserInRoleByUN(request.getRemoteUser(),"defineGroupDashboard,admin")){ %>
		 گروه:<input type="radio" name="radio" value="group" checked="checked"> 
		<select name="groupId">
			<%
			ArrayList<GroupDTO> dto = new ArrayList<GroupDTO>();
			dto = dashboardComboLST.getDtos();
			for(int i = 0 ; i < dto.size() ; i++){%>
			<option value="<%=dto.get(i).getEnt().getId()%>"><%=dto.get(i).getEnt().getGroupName() %></option>
			<%}%>
		</select>
		<%} %>
		<td></td>
	</tr>	
</table>
</form>
						