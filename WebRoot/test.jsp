<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>
<head>
	
	<script type="text/javascript" src="jquery/jquery.js"></script>
	<script type="text/javascript" src="jquery/jquery.form.js"></script>
	<script type="text/javascript" src="jquery/ui/ui.core.packed.js"></script>
	<script type="text/javascript" src="jquery/ui/ui.draggable.packed.js"></script>
	<script type="text/javascript" src="jquery/jquery.tree.js"></script>
</head>
<body>

<%--<aip:tree loader="/cubemeta.do?reqCode=dimensions" title="درخت ابعاد" id="treDimension"></aip:tree>--%>

<br>

<aip:tree loader="/aipbimdx.do?reqCode=folder_report" title="درخت " id="tre2"></aip:tree>



</body>