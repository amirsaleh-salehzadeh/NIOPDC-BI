<html>
	<head>
		<script type="text/javascript">
		$(document).ready(function(){
			$('#'+$('#right').val()).css('background','url(/AIPNIOPDCSellBI/images/links/right_selected.gif)');			
			$('.linksRight').click(function(){
				$('#right').val($(this).html());
				$('form#form').submit();
			});
		});
	</script>
		<style type="text/css">
.linksRight {
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
					<img src="/AIPNIOPDCSellBI/images/links/right_top.gif">
				</td>
			</tr>
			<tr>
				<td background="/AIPNIOPDCSellBI/images/links/right_middle.gif" id="right1">
					<div class="verticaltextRight">
						<span class="linksRight">right1</span>
					</div>
					<br />
				</td>
			</tr>
			<tr>
				<td>
					<img src="/AIPNIOPDCSellBI/images/links/right_divider.gif">
				</td>
			</tr>
			<tr>
				<td background="/AIPNIOPDCSellBI/images/links/right_middle.gif" id="right2">
					<br />
					<div class="verticaltextRight">
						<span class="linksRight">right2</span>
					</div>
					<br />
				</td>
			</tr>
			<tr>
				<td>
					<img src="/AIPNIOPDCSellBI/images/links/right_divider.gif">
				</td>
			</tr>
			<tr>
				<td background="/AIPNIOPDCSellBI/images/links/right_middle.gif" id="right3">
					<br />
					<div class="verticaltextRight">
						<span class="linksRight">right3</span>
					</div>
					<br />
				</td>
			</tr>
			<tr>
				<td>
					<img src="/AIPNIOPDCSellBI/images/links/right_divider.gif">
				</td>
			</tr>
			<tr>
				<td background="/AIPNIOPDCSellBI/images/links/right_middle.gif" nowrap="nowrap"
					id="right4">
					<br />
					<span class="verticaltextRight"><span class="linksRight">right4</span>
					</span>
					<br />
				</td>
			</tr>
			<tr>
				<td>
					<img src="/AIPNIOPDCSellBI/images/links/right_divider.gif">
				</td>
			</tr>
			<tr>
				<td background="/AIPNIOPDCSellBI/images/links/right_middle.gif" nowrap="nowrap"
					id="right5">
					<br />
					<span class="verticaltextRight"><span class="linksRight">right5</span>
					</span>
				</td>
			</tr>

			<tr>
				<td>
					<img src="/AIPNIOPDCSellBI/images/links/right_bottom.gif">
				</td>
			</tr>
		</table>
	</body>
</html>
