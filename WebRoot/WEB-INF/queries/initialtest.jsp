<%@ page session="true" contentType="text/html; charset=ISO-8859-1" %> 
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %> 


<jp:xmlaQuery id="query01" uri="http://localhost:81/olap/msmdpump.dll" catalog="Waremart 2005"> 

SELECT 
NON EMPTY
{
   Measures.AllMembers
}
 ON COLUMNS,
NON EMPTY
{
   [Product].Family.Members
}
ON ROWS
FROM Sales
</jp:xmlaQuery> 


<jp:xmlaQuery id="query05" uri="http://localhost/olap/msmdpump.dll" catalog="NIOPDC_MRS"> 
select 
from [havaleh with detail]

</jp:xmlaQuery> 

<jp:xmlaQuery id="query06" uri="http://localhost:81/olap/msmdpump.dll" catalog="NIOPDC_MRS"> 
select 
	{ [Measures].members } on columns
	,{ [“„«‰].[Hierarchy].[Year No].members } ON ROWS
from [havaleh with detail]

</jp:xmlaQuery> 

<jp:xmlaQuery id="query03" uri="http://localhost/olap/msmdpump.dll" catalog="NIOPDC_MRS"> 
select 
	{ [Measures].members } on columns
from [havaleh with detail]
	
</jp:xmlaQuery> 



<jp:xmlaQuery id="query02" uri="http://localhost/olap/msmdpump.dll" catalog="Waremart 2005"> 
	select 
	{[Measures].[Dollar Sales]} on columns
	,{[Customer].[Customer].Region.Members} on rows 
	from [Sales]
	
</jp:xmlaQuery> 
	


<c:set var="title01" scope="session">Test Query using XML/A</c:set> 
			
			