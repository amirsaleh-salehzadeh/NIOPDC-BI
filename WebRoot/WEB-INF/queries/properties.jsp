<%@ page session="true" contentType="text/html; charset=ISO-8859-1" %>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

	<jp:xmlaQuery id="query01"  uri="http://192.168.0.52:80/olap/msmdpump.dll" catalog="Waremart 2005">
		select 
		{[Customer].[Customer].[State].[OH].children}
		DIMENSION PROPERTIES [Customer].[Customer].[Education],
		[Customer].[Customer].[Num Children]
		on columns,
		[Product].[ByCategory].[Category].MEMBERS on rows
		from [Sales]
		where ([Measures].[Unit Sales])
	</jp:xmlaQuery>

<c:set var="title01" scope="session">Dim Properties</c:set>

