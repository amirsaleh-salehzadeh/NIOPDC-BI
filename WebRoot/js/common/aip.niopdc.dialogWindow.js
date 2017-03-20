jQuery.fn.dialogWindow = function(options) {
		this.settings = $.extend({
			prefix: 'dialog1',
			position: 'prepend',
			attached: 'body',
			title: null,
			theme: 'green',
			draggable: true,
			resizable: false,
			content: null,
			closeMode: 'remove',	 //{hide,remove}
			customWidth: null, 	 // 500
			closeBtn: true,
			top: 500,
			left: 500,
			bgShow : 'true'	
		}, options || {});
		var dialog1 = new jQuery._dialog(this.settings);
		dialog1.placedialog();	
		dialog1.prepareActions();
}

jQuery._dialog = function (set) {
	var settings = set;
	this.placedialog = function () {
		var domPosition 	= settings.position;
		var domAttached 	= settings.attached;
		if(domPosition == 'before') {
			$(domAttached).before( jQuery._createDialog(settings) );
		} else if(domPosition == 'prepend') {
			$(domAttached).prepend( jQuery._createDialog(settings) );
		} else if(domPosition == 'append') {
			$(domAttached).append( jQuery._createDialog(settings) );
		} else {
			$(domAttached).after( jQuery._createDialog(settings) );
		}
	};
	this.prepareActions = function() {
		if(settings.customWidth != null) {
			$('#' + settings.prefix + 'Collapse').css('width', settings.customWidth + 'px');
			$('#' + settings.prefix + 'Collapse').css('margin-left', '-' + Number(settings.customWidth)/2 + 'px');
			var dlgHeight = $('#' + settings.prefix + 'Collapse').css('height');
			//FIXME 
				dlgHeight = "500px";
			//
			var realH = new Array();  			
			realH = dlgHeight.split("px");
			dlgHeight = realH[0];

			var dlgWidth = $('#' + settings.prefix + 'Collapse').css('width');
			//FIXME 
				dlgWidth = "500px";
			//
			var realW = new Array();  			
			realW = dlgWidth.split("px");
			dlgWidth = realW[0];

			$('#' + settings.prefix + 'Collapse').css('top', WindowTopEdge + (WindowHeight-dlgHeight)/2  + 'px');
			$('#' + settings.prefix + 'Collapse').css('left', WindowLeftEdge + WindowWidth/2 + 'px');
		}
		if(settings.draggable) {
			jQuery._makeDraggableDialog(settings);
		}
		if(settings.resizable) {
			jQuery._makeResizableDialog(settings);
		}	
		var collapseName = "#" + settings.prefix + "Collapse";
		var wrapperName = "#" + settings.prefix + "Wrapper";
		$(collapseName + " .close").click(function () {
			if(settings.closeMode === 'hide'){
				$("#bgDiv").remove();
				$(wrapperName).hide();
			}
			if(settings.closeMode === 'remove'){
				$("#bgDiv").remove();
				$(wrapperName).remove();
			}
		});
	};
}

jQuery._createDialog = function (set) {
	var settings = set;
	this.collapseDiv = function () {
		return '<div id="' + settings.prefix + 'Collapse" class="dialogDiv">' +
					'<table id="' + settings.prefix + 'DragHandle" class="dragHandle">' +
						'<tr>' +
							'<td>' +
								placeCloseBtn(settings)+
							'</td>' +
							'<td>' +
								'<span id="dialogText">'   +  settings.title +  '   </span>' +
							'</td>' +
						'</tr>' +
					'</table>' +
					'<div id="content">' + 
						settings.content +
					'</div>' +
					'<img src="images/icons/window_resize.gif" id="' + settings.prefix + 'WindowResize" class="windowResize" />' +
				'</div>' ;
	};
	this.wrapUp = function () {
		return '<div id="' + settings.prefix + 'Wrapper" class="' + settings.theme + '"> ' +  
					this.collapseDiv() + 
				'</div>';
	};
	return this.wrapUp();



}

function placeCloseBtn(settings){
		if (settings.closeBtn) {
			return '<span class="close"></span>' ;
		}
}

jQuery._makeDraggableDialog = function (set) {
	var settings = set;
	var handleName = "#" + settings.prefix + "DragHandle";
	$("#" + settings.prefix + "Collapse").Draggable({zIndex:1000, ghosting:true, opacity:0.5, handle:handleName});
}

jQuery._makeResizableDialog = function (set) {
	var settings = set;
	var handleName = "#" + settings.prefix + "DragHandle";
	var resizeName = "#" + settings.prefix + "WindowResize";
	var contentDiv = "#" + settings.prefix + " #content";
	var collapse = "#" + settings.prefix + "Collapse";
	$("#" + settings.prefix + "Collapse").Resizable(
	{
		minWidth: $(collapse).css('width').replace("px",""),
		minHeight: $(collapse).css('height').replace("px",""),
		maxWidth: 1000,
		maxHeight: 5000,
		dragHandle: handleName,
		handlers: {
			se: resizeName
		},
		onResize : function(size, position) {
			$(contentDiv).css('height', size.height - 110 + 'px');
			$(contentDiv).css('width', size.width - 15 + 'px');
		}
	});
}