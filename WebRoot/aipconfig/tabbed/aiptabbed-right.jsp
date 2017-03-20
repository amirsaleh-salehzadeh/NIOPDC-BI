<%@page session="true" contentType="text/html; charset=UTF-8"%>
<%@page import="aip.tag.tabbed.AIPTabs"%>
<%@page import="aip.tag.tabbed.AIPTab"%>
<%
	AIPTabs tabs=(AIPTabs)request.getAttribute("AIPTabs");
	int counter=0;

	if(tabs==null)tabs=new AIPTabs();

%>

<head>
	<script type="text/javascript" src="aipconfig/tabbed/aiptabbed.js"></script>
	<link rel="stylesheet" type="text/css" href="aipconfig/tabbed/aiptabbed.css">
</head>

<table class="clsAIPTabbed clsAIPTabbedRight" cellpadding="0" cellspacing="0" style="background-image: url('images/tabbed/right/background.png');" height="100%">
<%
	if(tabs.size()>0 && tabs.get(0).isSelected()){
 %>
	<tr>
		<td>
			<img src="images/tabbed/right/first-hover.png" alt="" border="0">
		</td>
	</tr>
<%
	}else{
 %>
	<tr>
		<td>
			<img src="images/tabbed/right/top.png" alt="" border="0">
		</td>
	</tr>
<%	}
	while(counter<tabs.size() && !tabs.get(counter).isSelected()){ 
%>
	<tr>
		<td width="40" title="<%= tabs.get(counter).getValue() %>" align="center" style="background-image: url('images/tabbed/right/between2.png');cursor: pointer;" onclick="<%= tabs.getOnClick(counter) %>">
			<% 
			if(tabs.get(counter).getValue().length()>=9){
				out.print(tabs.get(counter).getValue().substring(0,9)+"...");
			}else{
				out.print(tabs.get(counter).getValue());
			}
			
			 %> 
		</td>
	</tr>
<%	if( counter+1<tabs.size() ){
		if(!tabs.get(counter+1).isSelected()){
%>
	<tr>
		<td>
			<img src="images/tabbed/right/between-top.png" alt="" border="0">
		</td>
	</tr>
<%
		}else{
%>
	<tr>
		<td>
			<img src="images/tabbed/right/top-hover.png" alt="" border="0">
		</td>
	</tr>
<%
		}
	}   
%>
<%	counter++;
	}
%>
<%	if(counter<tabs.size() && tabs.get(counter).isSelected()){ %>
	<tr>
		<td align="center" class="selectedTab" title="<%= tabs.get(counter).getValue() %>" style="background-image: url('images/tabbed/right/between-hover.png');cursor: pointer;"  onclick="<%= tabs.getOnClick(counter) %>">
			<% 
			if(tabs.get(counter).getValue().length()>=9){
				out.print(tabs.get(counter).getValue().substring(0,9)+"...");
			}else{
				out.print(tabs.get(counter).getValue());
			}
			
			 %>
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/tabbed/right/down-hover.png" alt="" border="0">
		</td>
	</tr>
<%	counter++;
	}
%>


<%	while(counter<tabs.size() ){ %>

	<tr>
		<td align="center" title="<%= tabs.get(counter).getValue() %>" style="background-image: url('images/tabbed/right/between2.png');cursor: pointer;" onclick="<%= tabs.getOnClick(counter) %>">
			<% 
			if(tabs.get(counter).getValue().length()>=9){
				out.print(tabs.get(counter).getValue().substring(0,9)+"...");
			}else{
				out.print(tabs.get(counter).getValue());
			}
			
			 %> 
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/tabbed/right/between-down.png" alt="" border="0">
		</td>
	</tr>
<%
	counter++;
	}
 %>
	<tr>
		<td height="100%">
			
		</td>
	</tr>
	<tr>
		<td>
			<img src="images/tabbed/right/down.png" alt="" border="0">
		</td>
	</tr>
</table>
