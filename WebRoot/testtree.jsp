<%@ page session="true" contentType="text/html; charset=utf8" %>
<%@taglib prefix="aip" uri="/WEB-INF/AIPTag.tld" %>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="jquery/jquery.js"></script>
		<script type="text/javascript" src="jquery/jquery.form.js"></script>
		<script type="text/javascript" src="jquery/ui/ui.core.packed.js"></script>
		<script type="text/javascript" src="jquery/ui/ui.draggable.packed.js"></script>
		<script type="text/javascript" src="jquery/jquery.tree.js"></script>
			<link rel="stylesheet" type="text/css" href="aipconfig/tree/simple/aiptree-simple.css">
	<link rel="stylesheet" type="text/css" href="aipconfig/tree/simple/tree_xp_style_rtl.css">

	
	<script type="text/javascript" src="aipconfig/tree/aiptree.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$('#treDimension').SimpleTree({animate: false,autoclose:true
				,success:function(){checkSelectedIds("treDimension");}
			});
			checkSelectedIds("treDimension");
		});
	</script>
</head>

<table align="center"><tr><td>

<aip:skin type="ITEM">
<aip:tree loader="/navigation.do" title="درخت ابعاد" id="tree1"></aip:tree>
</aip:skin>
</td><td>

<%--<aip:skin type="ITEM">--%>
<%--<aip:tree loader="/cubemeta.do?reqCode=dimensions" title="درخت ابعاد" id="treDimension"></aip:tree>--%>
<%--</aip:skin>--%>

</td></tr></table>


