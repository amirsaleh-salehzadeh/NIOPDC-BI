jQuery.fn.makePopupWindow = function(options) {
	calculateDimensions();
	this.settings = $.extend({
		prefix: 'tree1',
		position: 'prepend',
		attached: 'body',
		title: null,
		theme: 'green',
		treePath : null,
		toBeFilledDom1: null,
		toBeFilledDom2: null,
		inputId : null,
		draggable: true,
		resizable: true			
	}, options || {});
	var popup1 = new jQuery._popup(this.settings);
	popup1.placePopup();	
	popup1.prepareActions();
}

jQuery._popup = function (set) {

	var settings = set;
	this.placePopup = function () {
		var domPosition 	= settings.position;
		var domAttached 	= settings.attached;
		if(domPosition == 'before') {
			$(domAttached).before( jQuery._createPopup(settings) );
		} else if(domPosition == 'prepend') {
			$(domAttached).prepend( jQuery._createPopup(settings) );
		} else if(domPosition == 'append') {
			$(domAttached).append( jQuery._createPopup(settings) );
		} else {
			$(domAttached).after( jQuery._createPopup(settings) );
		}
	};
	this.prepareActions = function() {
		if(settings.draggable) {
			jQuery._makeDraggable(settings);
		}
		if(settings.resizable) {
			jQuery._makeResizable(settings);
		}
		$('#' + settings.prefix + 'Collapse').css('top', WindowTopEdge + WindowHeight/2  + 'px');
		$('#' + settings.prefix + 'Collapse').css('left', WindowLeftEdge + WindowWidth/2 + 'px');
		jQuery._bindClick(settings);
	};
}

jQuery._createPopup = function (set) {
	var settings = set;
	this.collapseDiv = function () {
		return '<div id="' + settings.prefix + 'Collapse" class="popupDiv">' +
					'<table id="' + settings.prefix + 'DragHandle" class="dragHandle">' +
						'<tr>' +
							'<td>' +
								'<span class="close"></span>' +
							'</td>' +
							'<td>' +
								'<span id="popupText">' + settings.title + ' </span>' +
							'</td>' +
						'</tr>' +
					'</table>' +
					'<form id="' + settings.prefix + 'PopupForm" class="popupForm" method="post">' +
						'<div id="' + settings.prefix + 'Tree" class="tree">' + 
							'<img src="images/icons/loading.gif" width="36px" height="36px" class="loading"/>' +
						'</div>' +		
						'<div id="buttonsDiv">' +
							'<input type="button" value="تائید" id="commit"  class="btnOk"/>' +
							'<input type="button" value="انصراف" id="cancel" class="btnReset" />' +
						'</div>' +
					'</form>' +
					'<img src="images/icons/window_resize.gif" id="' + settings.prefix + 'WindowResize" class="windowResize" />' +
				'</div>' ;
	};
	this.viewDiv = function () {
		return '<div id="' + settings.prefix + 'TitlesView" class="popupTitlesView"></div>';
		
	};
	this.wrapUp = function () {
		return '<div id="' + settings.prefix + 'Wrapper" class="' + settings.theme + '">' + 
				this.collapseDiv() + 
				this.viewDiv()+ 
				'</div>';
	};
	if ($("#"+settings.prefix+"Tree").css('height') === undefined) {
		return this.wrapUp();
	}
}

jQuery._makeDraggable = function (set) {
	var settings = set;
	var handleName = "#" + settings.prefix + "DragHandle";
	$("#" + settings.prefix + "Collapse").Draggable({zIndex:1000, ghosting:true, opacity:0.3, handle:handleName});
}

jQuery._makeResizable = function (set) {
	var settings = set;
	var handleName = "#" + settings.prefix + "DragHandle";
	var resizeName = "#" + settings.prefix + "WindowResize";
	var treeName = "#" + settings.prefix + "Tree";
	$("#" + settings.prefix + "Collapse").Resizable(
	{
		minWidth: 300,
		minHeight: 150,
		maxWidth: 1000,
		maxHeight: 5000,
		dragHandle: handleName,
		handlers: {
			se: resizeName
		},
		onResize : function(size, position) {
			$(treeName).css('height', size.height - 110 + 'px');
			$(treeName).css('width', size.width - 15 + 'px');
		}
	});
}

jQuery._bindClick = function (set) {
	var settings = set;
	var formName = "#" + settings.prefix + "PopupForm";
	var titlesView = "#" + settings.prefix + "TitlesView";
	var collapseName = "#" + settings.prefix + "Collapse";

	createHtml(settings);

	$(collapseName + " .close").click(function () {
		$(collapseName).hide();
	});
	//
	
	
	$(formName + " #commit").click(function () {
		$(collapseName).hide();
		$(settings.inputId).val($(formName + " input:checked").val());
		var j = 0;
		var txts = "";
		var ids = "";
		jQuery.each($(formName + " input:checked"), function () {
			if (j !== 0) {
				txts += ",";
				ids += ",";
			}
			j = j + 1;
			txts += $(this).attr("title");
			ids += $(this).attr("id");
		});
		$(settings.toBeFilledDom1).val(ids);
		if($(settings.toBeFilledDom1).val() !== undefined && $(settings.toBeFilledDom1).val() !== "") {
			$(settings.toBeFilledDom2).val($(formName + " span[@id='"+ $(settings.toBeFilledDom1).val() +"']").text());
		}
	});
	$(formName + " #cancel").click(function () {
		$(collapseName).hide();
	});
}


function createHtml(settings) {
	var formName = "#" + settings.prefix + "PopupForm";
	var titlesView = "#" + settings.prefix + "TitlesView";
	var collapseName = "#" + settings.prefix + "Collapse";
	$.getIfModified(settings.treePath, function(data){
		$("#" + settings.prefix + "Tree").html(data);
		$("#" + settings.prefix + "Tree").SimpleTree({animate: true,autoclose:false});
		$("#" + settings.prefix + "Tree ul li").quicksearch({
			stripeRowClass: ['odd', 'even'],
			position: 'before',
			attached: formName,
			labelText: 'فیلتر: '
		});
		$('div#' + settings.prefix + 'Tree').css('height','100%');
		$('.loading').remove();
		$('div#' + settings.prefix + 'Tree').css('height',$('div#' + settings.prefix + 'Tree').height());
	});	
	$(collapseName).show();
}