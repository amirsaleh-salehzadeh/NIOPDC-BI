jQuery.fn.createTreeDiv = function(options) {
	calculateDimensions();
	this.settings = $.extend({
		prefix: 'tree1',
		position: 'prepend',
		attached: 'body',
		title: null,
		treePath : null
	}, options || {});
	var popup1 = new jQuery._popup(this.settings);
}

jQuery._popup = function (settings) {
	var treeHtmlWrapper = '<div id="' + settings.prefix + 'Wrapper" class="orange">' +
								'<div id="' + settings.prefix + 'Collapse" class="popupDiv">' +
									'<form id="' + settings.prefix + 'PopupForm" class="popupForm" method="post">' +
										'<div id="' + settings.prefix + 'Tree" class="tree">' + 
											'<img src="images/icons/loading.gif" width="36px" height="36px" class="loading"/>' +
										'</div>' +		
									'</form>' +
								'</div>' +
							'</div>';

	$(settings.attached).prepend(treeHtmlWrapper);
	$('#' + settings.prefix + 'Collapse').css('position', 'static');
	createTreeHtmlBody(settings);
}

function createTreeHtmlBody(settings) {
	$.get(settings.treePath, function(data){
		$("#" + settings.prefix + "Tree").html(data);
		$("#" + settings.prefix + "Tree").SimpleTree({animate: true,autoclose:true});
		$("#" + settings.prefix + "Tree ul li").quicksearch({
			stripeRowClass: ['odd', 'even'],
			position: 'before',
			attached: "#" + settings.prefix + "PopupForm",
			labelText: 'فیلتر: '
		});
		$('div#' + settings.prefix + 'Tree').css('height','200px');
		$('.loading').remove();
		bindActionsForSelectingLocations();
	});	
	$("#" + settings.prefix + "Collapse").show();
}

function bindActionsForSelectingLocations() {
	var newlySed = $('#agentLocation').val();
	var checkBoxes = $("input[@type='checkbox']");
	var preselectedRoles = new Array();
	var newlySelectedRoles = new Array();
	if (newlySed !== null) {
		preselectedRoles = newlySed.split(',');
		for (var i = 0; i < checkBoxes.length; i = i + 1) {
			for (var j=0;j<preselectedRoles.length; j = j+1) {
				if ($(checkBoxes[i]).attr('id') == preselectedRoles[j]) {
					newlySelectedRoles.push(preselectedRoles[j]);
					$(checkBoxes[i]).attr("checked", "checked");
				}
			}
		}
		$('#agentLocation').attr('value',newlySelectedRoles);
		$("input[@type='checkbox']").bind('click',function(){
			if ($(this).attr('checked')) {
				newlySelectedRoles.push($(this).attr("id"));
				$('#agentLocation').attr('value',newlySelectedRoles);
			}	else  {
				var indexOf = newlySelectedRoles.indexOf($(this).attr('id'));
				if (indexOf != '-1') {
					newlySelectedRoles.splice(newlySelectedRoles.indexOf($(this).attr("id")),1);
				}
				$('#agentLocation').attr('value',newlySelectedRoles);
			} 
		});
	}
}