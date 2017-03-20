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


<table class="clsAIPTabbed clsAIPTabbedBottom" dir="rtl" cellpadding="0" cellspacing="0" style="background-image: url('images/tabbed/bottom/background.png');" width="100%">
	<tr>
<%
	if(tabs.size()>0 && tabs.get(0).isSelected()){
 %>
		<td>
			<img src="images/tabbed/bottom/first-hover.png" alt="" border="0">
		</td>
<%
	}else{
 %>
		<td>
			<img src="images/tabbed/bottom/right.png" alt="" border="0">
		</td>
<%	}
	while(counter<tabs.size() && !tabs.get(counter).isSelected()){ 
%>
		<td align="center" style="background-image: url('images/tabbed/bottom/between2.png');cursor: pointer;" onclick="<%= tabs.getOnClick(counter) %>">
			<%= tabs.get(counter).getValue() %>
		</td>
<%	if( counter+1<tabs.size() ){
		if(!tabs.get(counter+1).isSelected()){
%>
		<td>
			<img src="images/tabbed/bottom/between-right.png" alt="" border="0">
		</td>
<%
		}else{
%>
		<td>
			<img src="images/tabbed/bottom/right-hover.png" alt="" border="0">
		</td>
<%
		}
	}   
%>
<%	counter++;
	}
%>
<%	if(counter<tabs.size() && tabs.get(counter).isSelected()){ %>
		<td align="center" class="selectedTab" style="background-image: url('images/tabbed/bottom/between-hover.png');cursor: pointer;"  onclick="<%= tabs.getOnClick(counter) %>">
			<%= tabs.get(counter).getValue() %>
		</td>
		<td>
			<img src="images/tabbed/bottom/left-hover.png" alt="" border="0">
		</td>
<%	counter++;
	}
%>


<%	while(counter<tabs.size() ){ %>

		<td align="center" style="background-image: url('images/tabbed/bottom/between2.png');cursor: pointer;" onclick="<%= tabs.getOnClick(counter) %>">
			<%= tabs.get(counter).getValue() %>
		</td>
		<td>
			<img src="images/tabbed/bottom/between-left.png" alt="" border="0">
		</td>
<%
	counter++;
	}
 %>
		<td width="100%">
			
		</td>
		<td>
			<img src="images/tabbed/bottom/left.png" alt="" border="0">
		</td>
	</tr>
</table>
