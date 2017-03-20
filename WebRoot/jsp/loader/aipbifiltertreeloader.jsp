<%@ page language="java" import="java.util.*" pageEncoding="UTF8"%>
<%@page import="aip.util.NVL"%>
<%@page import="aip.util.UTF8"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip"%>

<aip:tree id="treAIPBIFilterTree" loader="<%="/cubemeta.do?reqCode=filter&id="+ request.getParameter("id") %>"
	title="">
</aip:tree>
