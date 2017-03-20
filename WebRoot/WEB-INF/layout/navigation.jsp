<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<div id="navigationDIV">
<table border="0" cellspacing="0">
	<tr>
		<td>
			<table border="0" cellspacing="0">
				<tr>
					<td>
						<img src="./images/navigation/top.png" class="mainlevel" width="179px">
					</td>
				</tr>
				<tr>
					<td>
						<html:link action="welcome" styleClass="mainlevel">صفحه اصلی</html:link>
					</td>
				</tr>
				<tr>
					<td>
						<html:link action="t_orderList?reqCode=new" styleClass="mainlevel">حواله جدید</html:link>
					</td>
				</tr>
				<tr>
					<td>
						<html:link action="t_orderpursuit" styleClass="mainlevel">پیگیری حواله</html:link>
					</td>
				</tr>
				<tr>
					<td>
						<html:link action="t_orderList" styleClass="mainlevel">لیست حواله ها</html:link>
					</td>
				</tr>
				<tr>
					<td>
						<html:link action="t_orderList?reqCode=notComplete" styleClass="mainlevel">حواله های تکمیل نشده</html:link>
					</td>
				</tr>
				<tr>
					<td>
						<html:link action="t_orderfiche.do?reqCode=view" styleClass="mainlevel">استعلام فیش بانکی</html:link>
					</td>
				</tr>
				<tr>
					<td>
						<html:link action="t_customerList.do" styleClass="mainlevel">لیست مشتریان</html:link>
					</td>
				</tr>
				<tr>
					<td>
						<html:link action="t_user.do?reqCode=newNA&isPermission=true" styleClass="mainlevel">صدور مجوز فروش</html:link>
					</td>
				</tr>
				<tr>
					<td>
						<html:link action="t_user.do?reqCode=newNA&isAgent=true" styleClass="mainlevel">تعریف عامل فروش</html:link>
					</td>
				</tr>
				<tr>
					<td>
						<img src="./images/navigation/down.png" class="mainlevel" style="margin-top: -7px;" width="179px">
					</td>
				</tr>
			</table>
		</td>
		<td width="10px;">
			<img height="30px;" width="10px;" src="images/navigation/showNavigation.png" alt="show/hide" id="showhideNavigation">
		</td>
	</tr>
</table>
</div>