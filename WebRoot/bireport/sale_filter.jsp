<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@page import="aip.util.NVL"%>
<%@page import="aip.xmla.AIPOlap"%>
<%@page import="rex.DimensionMember"%>
<%@page import="aip.util.AIPErrorHandler"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>
<%
	String defaultLocation = NVL.getString(request.getParameter("location"),""); 
String defaultZone = NVL.getString(request.getParameter("zone"),"");
int defaultTimeType = NVL.getInt(request.getParameter("type"),2);
%>
<%
	DimensionMember[] locationCombos=null; 
	DimensionMember[] zoneCombos =null;
	try{
		locationCombos = AIPOlap.getDimTreeMembers("[مناطق]","[مناطق].[منطقه-ناحیه]","[مناطق].[منطقه-ناحیه].[Level 02]");
		zoneCombos = AIPOlap.getDimTreeMembers("[مناطق]","[مناطق].[منطقه-ناحیه]","[مناطق].[منطقه-ناحیه].[Level 03]");
	}catch(Exception ex){
		System.out.println("IllegalStateException@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		String error = AIPErrorHandler.handle(request, this, ex, "", "");
		request.setAttribute("errorMessage",error);
	}
%> 
	
	
<aip:skin type="HEAD">
<form action="sale.jsp" method="get">
	<input type="hidden" name="reqCode" value="filter">
		منطقه <select name="location">
			<option/>
		    <%for(int i=0;locationCombos!=null && i<locationCombos.length;i++){ %>
		        <option class="locCombo" value="<%=locationCombos[i].getMemberNum()%>" 
		        		<% if(locationCombos[i].getMemberNum().equals(defaultLocation)){ out.print("selected='selected'"); } %> >
		        <%=locationCombos[i].getMemberCaption() %></option>
		    <%} %>
		</select>
		ناحیه <select name="zone">
			<option/>
		    <%for(int i=0;zoneCombos!=null && i<zoneCombos.length;i++){ %>
		        <option class="zoneCombo" value="<%=zoneCombos[i].getMemberNum()%>" 
		        		<% if(zoneCombos[i].getMemberNum().equals(defaultZone)){ out.print("selected='selected'"); } %> >
		        <%=zoneCombos[i].getMemberCaption() %></option>
		    <%} %>
		</select>

		سال<input type="radio" name="type" value="1" <% if(defaultTimeType==1){ out.print("checked='checked'"); } %> />
		فصل<input type="radio" name="type" value="2" <% if(defaultTimeType==2){ out.print("checked='checked'"); } %> />
		ماه<input type="radio" name="type" value="3" <% if(defaultTimeType==3){ out.print("checked='checked'"); } %> />
		روز<input type="radio" name="type" value="4" <% if(defaultTimeType==4){ out.print("checked='checked'"); } %> />
		
		<input type="submit" value="تایید">
</form>
</aip:skin>