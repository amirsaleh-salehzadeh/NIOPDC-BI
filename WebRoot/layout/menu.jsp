<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="aip.util.SecurityUtil"%>
<%@ taglib prefix="aip" uri="/WEB-INF/AIPTag.tld" %>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="/AIPNIOPDCSellBI/css/pro_dropdown_2.css" />
		<script src="/AIPNIOPDCSellBI/js/stuHover.js" type="text/javascript"></script>
		<script type="text/javascript" src="jquery/jquery.js"></script>
		<script type="text/javascript" src="jquery/ui/ui.core.packed.js"></script>
		<script type="text/javascript" src="jquery/ui/ui.draggable.packed.js"></script>
		<script type="text/javascript">
		function showDialogForChangePassword(x){
			var loader = "/AIPNIOPDCSellBI/changePassword.jsp?userName=" + x;
			
			showDialog('dlgChangePassword','center',loader);
		}
		</script>
	</head>
	<body>
		<table align="center" width="" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center" valign="top">
					<img src="/AIPNIOPDCSellBI/images/menu2/right.png" />
				</td>
				<td>
					<ul id="nav">
						<li class="top" id="liFirstVisit">
							<a href="#" id="menu1" class="top_link"> <span class="down">فرآورده
									ها </span> </a>
							<ul class="big">
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=59">گزارش فروش فرآورده ها به تفکیک نوع مصرف در هر منطقه</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=60">گزارش فروش فرآورده ها به تفکیک مناطق به ازای هر نوع مصرف</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=61"> گزارش فروش فرآورده ها
										به تفکیک نوع مصرف</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=arash1">گزارش در صد نوع مصرف هر فرآورده نسبت به کلیه مصارف</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=arash2">گزارش در صد رشد  فرآورده ها نسبت به دوره قبل</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=77">گزارش روند فروش فرآورده ها- کلی</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=78">گزارش فروش فرآورده ها در یک دوره زمانی</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=63">گزارش فروش مشتریان به تفکیک منطقه </a>
								</li>
				
							
							</ul>
						</li>
						<li class="top" id="liFirstVisit">
							<a href="#" id="menu1" class="top_link"> <span class="down">
									 مناطق و نواحی </span> </a>
							<ul class="big">
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=84">گزارش فروش در مناطق</a>

								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=86">گزارش
										فروش مناطق به تفکیک فرآورده ها</a>

								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=87">گزارش
										فروش به تفکیک انواع مشتری</a>

								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=88">گزارش
										فروش مناطق به تفکیک محل و مشتری</a>

								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=89">گزارش فروش
										مناطق به تفکیک انواع خرید</a>

								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=90">گزارش
										فروش مناطق به تفکیک اقلام هزینه و نحوه پرداخت</a>

								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=91">گزارش فروش
										مناطق به تفکیک انواع مصرف</a>

								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=93">گزارش فروش مناطق در یک دوره زمانی</a>

								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=92">گزارش درصد فروش مناطق نسبت به دوره قبل</a>

								</li>
							</ul>
						</li>
						<li class="top" id="liFirstVisit">
							<a href="#" id="menu1" class="top_link"> <span class="down">
									مشتریان  </span> </a>
							<ul class="big">
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=62"> گزارش فروش مشتریان - کلی</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=65">
										گزارش فروش مشتریان به تفکیک انواع مشتری</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=63"> گزارش فروش
										مشتریان به تفکیک انواع مشتری و منطقه</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=64">  گزارش فروش مشتریان
										به تفکیک منطقه و انواع مشتری</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=94">گزارش روند فروش به تفکیک انواع مشتری</a>
								</li>
							
							</ul>
						</li>

						<li class="top" id="liFirstVisit">
							<a href="#" id="menu1" class="top_link"> <span class="down">
									بانک </span> </a>
							<ul class="big">
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=66">گزارش
										درصد نوع فیش به کل پرداخت ها</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=67">گزارش
										پرداخت به بانک به تفکیک انواع فیش</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=68">گزارش اجمالی
										پرداخت به بانک</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=69">گزارش روند
										    پرداخت به بانک به تفکیک انواع پرداخت </a>
								</li>
							</ul>
						</li>
						<li class="top" id="liFirstVisit">
							<a href="#" id="menu1" class="top_link"> <span class="down">
									روندها </span> </a>
							<ul class="big">
												<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=98">گزارش روند فروش در
										کشور/منطقه</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=99"> گزارش روند فروش در انبارها</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=100">گزارش
										روند فروش در مناطق به تفکیک فرآورده ها</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=84">گزارش روند فروش در مناطق</a>
								</li>
						
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=101"> گزارش روند فروش به
										تفکیک فرآورده و منطقه</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=97">گزارش روندانواع مصرف در یک دوره
										زمانی</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=77">گزارش روند فروش فرآورده ها- کلی</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=94">گزارش روند فروش به تفکیک انواع مشتری</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=102">گزارش روند فروش فروش  تفکیک محل و مشتری </a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=103">گزارش روند فروش به تفکیک انواع مصرف</a>
								</li>
								
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=104"> گزارش روند فروش در مناطق به تفکیک
										فرآورده ها</a>
								
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=104"> گزارش روند فروش  به تفکیک انواع هزینه</a>
								
								</li>
								
							</ul>
						</li>
							<li class="top" id="liFirstVisit">
							<a href="#" id="menu1" class="top_link"> <span class="down">
									سایر گزارش ها</span> </a>
							<ul class="sub">
									<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=97">گزارش انواع مصرف در یک دوره زمانی</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=95"> گزارش فروش در انبارها </a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reportId=96">گزارش فروش مجاری عرضه</a>
								</li>
							</ul>
						</li>
						<li class="top" id="liFirstVisit">
							<a href="#" id="menu1" class="top_link"> <span class="down">
									ساخت گزارش</span> </a>
							<ul class="sub">
								<li>
									<a href="/AIPNIOPDCSellBI/aipbimdx.jsp?reqCode=edit">ساخت گزارش MDX</a>

								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/dashboard.jsp">داشبورد</a>
								</li>
								<li>
									<a href="/AIPNIOPDCSellBI/bidashboard.jsp">داشبورد جدید</a>
								</li>
							</ul>
						</li>
						<li class="top" id="liFirstVisit">
						<% if(!session.isNew() && request.getRemoteUser()!=null){ %>
							<a href="#" id="menu1" class="top_link"> <span class="down">
									امنیت </span> </a>
							<ul class="sub">
								<li>
									<a href="#" onclick="showDialogForChangePassword('<%=request.getRemoteUser()%>');">تغییر رمز</a>
								</li>
								<li>
									<a href="logOut.do?reqCode=logOut" >خروج</a>
								</li>
								<%SecurityUtil util = new SecurityUtil();
								if(util.isUserInRoleByUN(request.getRemoteUser(),"admin,securityViewer")){ %>	
									<li>
										<a href="user.do" >مدیریت کاربر</a>
									</li>
									<li>
										<a href="group.do" >مدیریت گروه</a>
									</li>																		
								<%} %>						
							</ul>
						<%}else{ %>
							<a href="welcome.do" id="menu1" class="top_link"> <span class="down">
									ورود </span> </a>
						<%} %>
						</li>
					</ul>
				</td>

				<td align="center" valign="top">
					<img src="/AIPNIOPDCSellBI/images/menu2/left.png" />
				</td>
			</tr>
		</table>
	<aip:dialog styleId="dlgChangePassword" loader="" title="تغییر رمز کاربر" style="simple" screenPosition="center" onOKClick="dlgChangepassword_OK();"  img="" >
	</aip:dialog>
	</body>
</html>

