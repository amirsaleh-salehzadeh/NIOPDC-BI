<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<head>
	<title>صفحه اصلی</title>
	<link rel="shortcut icon" href="images/aip.ico">	
	<style>
	<!--
	.clsBoldText{
		font-weight: bold;
		color: blue;
	}
	.clsHeadText{
		font-family: Tahoma, Arial, Helvetica, sans-serif;
		font-size: 20;
		color: maroon;
	}
	-->
	</style>
</head>


<% if (request.getAttribute("errr") != null && !"".equals(request.getAttribute("errr"))) { %>
<p align="center" style="color: red; font-size: 20px;">
<% out.println(request.getAttribute("errr")); %>
</p>
<% } %>



<p align="center" class="clsHeadText">
به سيستم فروش اینترنتی شرکت ملی پخش فرآورده های نفتی جمهوری اسلامی ایران خوش آمدید.
</p>
<%--<img src="./images/background/mainbg.png" width="100%" height="100%">--%>
<table>
	<tr>
		<td>
		خدماتی که از طریق این سایت در اختیار مشتریان نواحی قرار می گیرد
		</td>
	</tr>
	<tr>
		<td>
			<ol>
				<li>
					<span class="clsBoldText">
					حواله جدید:
					</span>
					برای استفاده از این قسمت مشتری باید مجوز کاربری اینترنتی را از ناحیه مربوطه دریافت کرده باشد
					همچنین می تواند با مراجعه به عاملین فروش اینترنتی ناحیه مربوطه درخواست های خود را از طریق اینترنت ارسال کند
				</li>
				<li>
					<span class="clsBoldText">
						پیگیری حواله :
					</span>
					 از طریق این قسمت مشتری در جریان آخرین وضعیت حواله خود قرار می گیرد.
				</li>
				<li>
					<span class="clsBoldText">
						لیست حواله ها :
					</span>
					در این بخش لیست تمام حواله های ثبت شده مربوط به یک مشتری یا ناحیه نمایش داده می شود
				</li>
				<li>
					<span class="clsBoldText">
						حواله های تکمیل نشده :
					</span>
					در این بخش لیست تمام حواله های تکمیل نشده (حواله هایی که فرایند پرداخت الکترونیکی آنها تکمیل نشده اند) مربوط به یک مشتری یا عامل فروش نمایش داده می شود
				</li>
			</ol>
		</td>
	</tr>
</table>
