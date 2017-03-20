var isIsAgent = '';


$(document).ready(function(){
/*
	$("#menuCustomers").hover(
		function(){
			$('#popCustomers').css('visibility','visible');
	  		$('#popCustomers').show();
		},
		function(){
	  		$('#popCustomers').hide();
		}
	);

	$("#menuBank").hover(
		function(){
				
			$('#popBank').css('visibility','visible');
			$('#popBank').show();
		},
		function(){
	  		$('#popBank').hide();
		}
	);
	
	$("#menuReports").hover(
		function(){
				
			$('#popReports').css('visibility','visible');
			$('#popReports').show();
		},
		function(){
	  		$('#popReports').hide();
		}
	);
	$("#menuAdministration").hover(
		function(){
				
			$('#popupAdministration').css('visibility','visible');
			$('#popupAdministration').show();
		},
		function(){
	  		$('#popupAdministration').hide();
		}
	);
	$("#menuInfo").hover(
		function(){
				
			$('#popInfo').css('visibility','visible');
			$('#popInfo').show();
		},
		function(){
	  		$('#popInfo').hide();
		}
	);
*/	
	
//	$(".clsMenuRightTD").hover(
//		function(){
//			$('.clsMenuList').css('visibility','visible');
//	  		$('.clsMenuList').show();
//		},
//		function(){
//	  		$('.clsMenuList').hide();
//		}
//	);


	$(".clsMenu").each(function(i){
		$(this).hover(
			function(){
				$(this).children(".clsMenuList").show();
		},
		function(){
	  		$(this).children(".clsMenuList").hide();
		}
	);
	});
			
		
});