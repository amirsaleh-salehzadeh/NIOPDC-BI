<%@ page language="java" import="java.util.*" pageEncoding="UTF8"%>
<%@page import="aip.util.NVL"%>
<%@page import="aip.util.UTF8"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip"%>

<head>
</head>

<aip:tree id="treFolderLoader" loader="<%="/navigation.do" +"&nodeType=CheckBox"%>" 
		  title=""
		   >
</aip:tree>
