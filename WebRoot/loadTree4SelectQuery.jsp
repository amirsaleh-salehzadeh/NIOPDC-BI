<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>
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
</head>
	
	<aip:tree loader="/cubemeta.do?reqCode=dimensions" title="درخت ابعاد" id="treDimension"></aip:tree>
	
