<html>
	<head>
		<script type="text/javascript">
		$(document).ready(function(){
			$('#'+$('#bottom').val()).css('background','url(/AIPNIOPDCSellBI/images/links/bottom_selected.gif)');
			$('.links').click(function(){
				
				$('#bottom').val($(this).html());
				$('form#form').submit();
				
			});
		
		});
	
	</script>
		<style type="text/css">
.links {
	text-decoration: none;
	color: #fff;
	cursor: pointer;
}
</style>

	</head>
	<body>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<img src="/AIPNIOPDCSellBI/images/links/bottom_right.gif">
				</td>
				<td background="/AIPNIOPDCSellBI/images/links/bottom_middle.gif" class="links"
					id="test1">
					<span class="links">test1</span>
				</td>
				<td>
					<img src="/AIPNIOPDCSellBI/images/links/bottom_divider.gif">
				</td>
				<td background="/AIPNIOPDCSellBI/images/links/bottom_middle.gif" class="links"
					id="test2">
					<span class="links">test2</span>
				</td>
				<td>
					<img src="/AIPNIOPDCSellBI/images/links/bottom_divider.gif">
				</td>
				<td background="/AIPNIOPDCSellBI/images/links/bottom_middle.gif" class="links"
					id="test3">
					<span class="links">test3</span>
				</td>
				<td>
					<img src="/AIPNIOPDCSellBI/images/links/bottom_left.gif">
				</td>

			</tr>
		</table>
	</body>
</html>
