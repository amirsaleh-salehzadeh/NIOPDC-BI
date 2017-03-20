<%@ page language="java" import="java.util.*" pageEncoding="UTF8"%>
<%@page import="aip.util.NVL"%>
<%@page import="aip.util.UTF8"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip"%>

<head>
	<script type="text/javascript">
	$(document).ready(function () {
		$("#selectedIds").val($("#txtColumnsID").val());
		$("#selectedNames").val($("#txtColumns").val());
		checkSelectedIds('treColumnMembersLoader');
	});
	
	</script>
</head>

<%
	String reqCode=request.getParameter("reqCode");
	String id=request.getParameter("id");
	String nodeType= NVL.getString(request.getParameter("nodeType"));
	String selectedIds= NVL.getString(request.getParameter("selectedIds"));
	selectedIds = UTF8.cnvUTF8(selectedIds);
	selectedIds = selectedIds.replaceAll("&", "%26");
	
	String selectedNames= NVL.getString(request.getParameter("selectedNames"));
	selectedNames = UTF8.cnvUTF8(selectedNames);
	
%>
<%--<%=  selectedIds %>--%>

<%--<%=  NVL.getString(request.getParameter("nodeType")) %>--%>

<aip:tree id="treColumnMembersLoader" loader="<%="/memberswithcheck.do?reqCode=hierarchy&id="+id +"&nodeType=CheckBox"%>" 
		  title=""
		  selectedIds="<%=selectedIds %>" 
		  selectedNames="<%=selectedNames %>" >

</aip:tree>
