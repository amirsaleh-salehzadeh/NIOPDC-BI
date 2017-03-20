<%@ page session="true" contentType="text/html; charset=UTF-8" %>
		<script type="text/javascript" src="jquery/jquery.form.js"></script>
		<script type="text/javascript">
		function dlgChangepassword_OK(){
			try{
			var options = {
			    success: function(req) {
			        alert("تغییر رمز با موفقیت انجام شد");
			    }
			    ,error: function(req){
			    	alert("فرآیند تغییر رمز موفقیت آمیز نبود. کلمه رمز قبلی اشتباه می باشد.");
					
			    }
			   };
			   if($('#newpass').val()===$('#renewpass').val()){
				$('#CHPform').ajaxSubmit(options);
				}else{
					alert('کلمه رمز درست تکرار نشده است. مجددا سعی کنید')
				}
			}catch(e){
				alert(e);
			}
		}
		</script>

<form action="user.do?reqCode=changepasword&userName=<%= "true".equalsIgnoreCase(request.getParameter("isInUserEdit"))? request.getParameter("userName") : request.getRemoteUser() %>" id="CHPform">
	رمز قدیم:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="password" name="oldPassword"><br/>
	رمز جدید:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="password" name="newPassword" id="newpass"><br/>
	تکرار رمز جدید:<input type="password" name="newPassword" id="renewpass">
</form>