<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page language="java"%>
<% if(!session.isNew() && request.getRemoteUser()!=null){ %>
	<div style="color: white; font-weight: bold; font-size:10px; position: absolute;">
		&nbsp;آقا/خانم&nbsp;
		&nbsp;<%= request.getRemoteUser() %>&nbsp;
		خوش آمدید
		&nbsp;
	</div>
<%} %>
<img src="/AIPNIOPDCSellBI/images/header/header.gif" alt="header logo" width="100%" height="120px" onclick="window.location = '/AIPNIOPDCSellBI/welcome.do'" style="cursor: pointer;"/>

