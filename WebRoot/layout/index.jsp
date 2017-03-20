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
به سامانه اینترنتی تحلیل و آمار و گزارشگیری مدیریتی فروش شرکت ملی پخش فرآورده های نفتی جمهوری اسلامی ایران خوش آمدید.
</p>
<table>
	<tr>
		<td>
		خدماتی که از طریق این سایت در اختیار مدیران ارشد و مدیران میانی و مدیران مناطق و نواحی قرار می گیرد
		</td>
	</tr>
	<tr>
		<td>
			<ol>
				<li><a href="bisale.jsp">
					<span class="clsBoldText">
					روند فروش:
					</span></a>
				</li>
				<li>
					<span class="clsBoldText">
					درصد روند فروش :
					</span>
				</li>
				<li><a href="bivolume.jsp">
					<span class="clsBoldText">
					حجم فروش محصولات :
					</span></a>
				</li>
				<li><a href="bibank.jsp">
					<span class="clsBoldText">
					تراکنش بانکها :
					</span></a>
				</li>
				<li>
					<span class="clsBoldText">
					آمار فرآورده ها:
					</span>
				</li>
				<li>
					<span class="clsBoldText">
					آمار مشتریان :
					</span>
				</li>
				<li>
					<span class="clsBoldText">
					آمار مناطق :
					</span>
				</li>
				<li>
					<span class="clsBoldText">
					آمار مصرف :
					</span>
				</li>
				<li>
					<span class="clsBoldText">
					آمار خرید :
					</span>
				</li>
				<li>
					<span class="clsBoldText">
					آمار هزینه ها :
					</span>
				</li>
				<li><a href="visualreport.do">
					<span class="clsBoldText">
					ساخت گزارشات مدیریتی - سازنده :
					</span></a>
				</li>
				<li><a href="aipbimdx.jsp">
					<span class="clsBoldText">
					ساخت گزارشات مدیریتی - MDX :
					</span></a>
				</li>
				<li>
					<span class="clsBoldText">
					امنیت:
					</span>
				</li>
			</ol>
		</td>
	</tr>
</table>
