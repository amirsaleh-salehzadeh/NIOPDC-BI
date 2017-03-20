<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/AIPTag.tld" prefix="aip" %>

	
		<script type="text/javascript">
			function selectedNodeChange(aId){
				$('#treDimension #selectedIds').val( aId ); 
			}
			function aaa(){
			alert(1);
				$('#treDimension').SimpleTree({animate: false,autoclose:true
				});
			alert(2);
			}
		</script>
	
		<aip:tree loader="/aipbimdx.do?reqCode=report_folder" title="درخت گزارشات" id="treDimension"  isJQueryIncluded="false"></aip:tree>
		
		<span onclick="aaa();">click</span>