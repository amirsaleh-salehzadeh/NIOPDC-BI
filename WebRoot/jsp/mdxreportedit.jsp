<%@ page session="true" contentType="text/html; charset=UTF-8"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="aip.jpivot.AIPPivotParam"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip"%>
<%@ taglib uri="http://www.tonbeller.com/jpivot" prefix="jp"%>
<%@ taglib uri="http://www.tonbeller.com/wcf" prefix="wcf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="shortcut icon" href="aip-co.ico">
		<link rel="icon" type="image/ico" href="aip-co.ico">
		<link rel="stylesheet" type="text/css" href="css/niopdc.css" />
		<link rel="stylesheet" type="text/css" href="css/dialogStyles.css" />
		<link rel="stylesheet" type="text/css" href="css/popupStyles.css" />
		<link rel="stylesheet" type="text/css" href="css/navigation.css">
		<link rel="stylesheet" type="text/css" href="css/menu.css">
		<link rel="stylesheet" type="text/css" href="css/common.css" />

		<link rel='stylesheet' type='text/css' href='css/aiptree-simple.css' />
		<link rel='stylesheet' type='text/css'
			href='css/tree_xp_style_rtl.css' />
		<link rel="stylesheet" type="text/css" href="css/commonList.css" />
		<link rel="stylesheet" type="text/css"
			href="jpivot/table/mdxtable.css">
		<link rel="stylesheet" type="text/css" href="jpivot/navi/mdxnavi.css">
		<link rel="stylesheet" type="text/css" href="wcf/form/xform.css">
		<link rel="stylesheet" type="text/css" href="wcf/table/xtable.css">
		<link rel="stylesheet" type="text/css" href="wcf/tree/xtree.css">

		<script type="text/javascript" src="jquery/jquery.js"></script>
		<script type="text/javascript" src="jquery/jquery.form.js"></script>
		<script type="text/javascript" src="jquery/ui/ui.core.packed.js"></script>
		<script type="text/javascript" src="jquery/ui/ui.draggable.packed.js"></script>
		<script type="text/javascript" src="jquery/jquery.tree.js"></script>

		<script type="text/javascript" src="js/common/aip.niopdc.common.js"></script>
		<script type="text/javascript" src="js/common/aip.niopdc.menu.js"></script>
		<script type="text/javascript"
			src="js/mdxreport/aip.niopdcsellbi.mdxreport.js"></script>
		<script type="text/javascript" src="js/aiptree.js"></script>
<script type="text/javascript">
		$(document).ready(function(){
			$('.trees').css('display','none');
			$('#informationTree').css('display','inline');
		});		
		function showSample(x){
			$('.trees').css('display','none');
			$('#'+$(x).attr('class')).css('display','inline');				
		}
</script>
		<title>ساخت گزارش MDX</title>
		<%--		<script type="text/javascript">--%>
		<%--		$(document).ready(function(){--%>
		<%--			$('#dimTree').SimpleTree({animate: false,autoclose:true});--%>
		<%--		});--%>
		<%--		</script>--%>
	</head>

	<body dir="rtl" background="images/background/background.png"
		id="pageLayout" leftmargin="0" topmargin="0" marginwidth="0"
		marginheight="0">
		
		
		<table border="0" width="100%" cellspacing="0" align="center"
			cellpadding="0">
			<tbody>
				<tr align="center">
					<td colspan="2">
						<jsp:include page="/layout/header.jsp"></jsp:include>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<jsp:include page="/layout/menu.jsp"></jsp:include>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<jsp:include page="/layout/banner.jsp"></jsp:include>
					</td>
				</tr>
				<tr align="center"><%--
					bade tbody				--%>
					<td valign="top" style="padding-right: 0px;" align="right">
						<jsp:include page="/layout/navigation.jsp"></jsp:include>
					</td>
				
					<td width="100%" valign="top">
						<aip:skin type="BODY">
							<form action="mdxreport.do" method="POST" id="mdxReportForm"
								name="mdxReportForm">
								<input type="hidden" name="reqCode" id="reqCode" />
								<html:hidden name="mdxReportForm" property="mdxReportDTO.id" />
								<bean:define id="form" name="mdxReportForm"
									type="aip.niopdc.sellbi.struts.form.MDXReportForm" />
								<%--		<% --%>
								<%--//			DimensionTreeElement t[] = form.getDimensionTreeLST().getTree();--%>
								<%--//			AIPAjaxTreeUtil.createTree(out,"Dimensions",t);--%>
								<%----%>
								<%--		%>--%>
								<%--		<div>--%>
								<table align="center" >
									<tr>
										<td colspan="2">
											<a style="color: #000; background-image: images/button.png;" > نمایش درخت اطلاعات<img src="images/button.png" class="informationTree" onclick="showSample(this);" style="cursor: pointer; " ></a> 
											<a style="color: #000; background-image: images/button.png;" > نمایش درخت ابعاد<img src="images/button.png" class="dimensionTree" onclick="showSample(this);" style="cursor: pointer;"></a> 
											<a style="color: #000; background-image: images/button.png;" > نمایش مثال<img src="images/button.png" class="sampleShow" onclick="showSample(this);" style="cursor: pointer;"></a> 
										</td>
									</tr>
									<tr>
										<td id="sampleShow" class="trees" >
											<aip:skin type="ITEM" >
													<span style="font-weight: bold;" >نمایش مثال</span>
											</aip:skin>
										</td>
										<td id="informationTree" class="trees">
											<aip:skin type="ITEM">
													<span style="font-weight: bold;" >درخت اطلاعات</span>
												<aip:tree loader="/members2.do?reqCode=dimensions" title="" id="informationTree" ></aip:tree>
											</aip:skin>
										</td>
										<td id="dimensionTree" class="trees"> 	
											<aip:skin type="ITEM">
												<span style="font-weight: bold;">درخت ابعاد</span>
												<br />
												<aip:tree loader="/dimensions.do?reqCode=dimensions" title="" id="dimensionTree"></aip:tree>
											</aip:skin>
										</td>

										<td>
											<aip:skin type="ITEM">
												<aip:tree loader="/navigation.do" title="درخت فایل و گزارش" id="tree3"></aip:tree>
											</aip:skin>
									
										</td>
<%--		<aip:skin type="BODY">--%>
<%--			<aip:tree loader="/navigation.do"--%>
<%--					title="درخت فایل و گزارش"--%>
<%--				id="treContent">--%>
<%--			</aip:tree>--%>
<%--		</aip:skin>--%>
										

										
										<td>
											<table align="center" width="100%" >
												<tr align="center">
													<td class="headerTitleDiv">
														نام گزارش: &nbsp;&nbsp;&nbsp;
													</td>
													<td>
														<html:text name="mdxReportForm"
															property="mdxReportDTO.queryName" style="width: 700px;"  />
													</td>
												</tr>
												<tr>
													<td class="headerTitleDiv">
														گزارش&nbsp;: &nbsp;&nbsp;
													</td>
													<td dir="ltr">
														<html:textarea name="mdxReportForm"
															property="mdxReportDTO.query" styleId="reportQuery" style="width: 700px; height: 300px;"/>
													</td>
												</tr>
											</table>
											<%--		</div>--%>
										</td>
									</tr>
								</table>

								<div align="left">

									<br />
									<input type="submit" value="ذخیره"
										onclick="$('#reqCode').val('save'); $('form#mdxReportForm').submit();" />
									<input type="button" value="اجرا"
										onclick="$('#reqCode').val('execute');$('#reportQuery').val($('#reportQuery').val().replace('&','%26')); $('form#mdxReportForm').submit();" />
									<input type="button" value="بازگشت"
										onclick="$('form#mdxReportForm').submit();" />
								</div>


							</form>
						</aip:skin>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<%--						<tiles:insert attribute="footer" />--%>
						<jsp:include page="/layout/footer.jsp"></jsp:include>

					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>

