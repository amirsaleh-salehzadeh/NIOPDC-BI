<%@ page language="java" import="java.util.*" pageEncoding="UTF8"%>
<%@page import="aip.util.NVL"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip"%>

<%
	String reqCode=request.getParameter("reqCode");
	String id=request.getParameter("id");
	String nodeType= NVL.getString(request.getParameter("nodeType"));
%>
<%=  NVL.getString(request.getParameter("nodeType")) %>

<aip:tree id="treMembersLoader" loader="<%="/memberswithcheck.do?reqCode=hierarchy&id="+id +"&nodeType=CheckBox"%>" 
		  title=""
		  	selectedIds="<%=request.getParameter("selectedIds") %>" 
			selectedNames="<%=request.getParameter("selectedNames") %>" >
</aip:tree>

