<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>
<head>
	<script type="text/javascript" src="aipconfig/tree/aiptree.js"></script>
	<script type="text/javascript" src="jquery/jquery.tree.js"></script>
	<link href="css/navigation.css" rel="stylesheet" type="text/css" >
	<link rel="stylesheet" type="text/css" href="aipconfig/tree/simple/aiptree-simple.css">
	<link rel="stylesheet" type="text/css" href="aipconfig/tree/simple/tree_xp_style_rtl.css">
	<style type="text/css">
		#navigationTree{
			width: 170px;
			overflow: auto;
			height: 100%;
			background-color: white;
			z-index: 100;
		}
	</style>

</head>
<div id="navigationDIV" >
	<table border="0" cellspacing="0">
		<tr>
			<td>
				<table border="0" cellspacing="0">
					<tr>
						<td>
							<img src="/AIPNIOPDCSellBI/images/navigation/top.png" class="mainlevel" width="179px">
						</td>
					</tr>
					<tr>
						<td class="mainlevel" style="width: 170;">
							<aip:tree loader="/navigation.do" title="درخت اطلاعات" id="navigationTree" ></aip:tree>
						</td>
					</tr>
					<tr>
						<td >
							<img src="/AIPNIOPDCSellBI/images/navigation/down.png" class="mainlevel" style="margin-top: -7px;" width="179px">
						</td>
					</tr>
				</table>
			</td>
			<td width="10px;">
				<img height="30px;" width="10px;" style="cursor: pointer;" src="/AIPNIOPDCSellBI/images/navigation/showNavigation.png" alt="show/hide" id="showhideNavigation" >
			</td>
		</tr>
	</table>
</div>