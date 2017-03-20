//buttons --> 1=yes 2=yesNo
jQuery.fn.makePopupDiv = function(options) {
	calculateDimensions();
	this.settings = $.extend({
		prefix: 'divPopUp',
		position: 'prepend',
		attached: 'body',
		title: null,
		showText: null ,
		showIcon: 'pationet.png',
		iconAlt: 'caution',
		theme: 'YELLOW',
		viewId: null,
		buttons: null,
		buttonsDiv: 'buttonsDiv',
		reqCode: null,
		draggable: false,
		resizable: false,
		formName: '',
		windowLocation: '',
		width: '300'			
	}, options || {});

	var popup = new jQuery.confirmationPopup(this.settings);
	popup.popupPosition();	
	popup.popupActions();
};

jQuery.confirmationPopup = function (set) {
	$(this).createTransparentBg();
	var settings = set;
	$("#" + settings.prefix + 'Base').attr('width',"800px");
	this.popupPosition = function () {
		var domPosition 	= settings.position;
		var domAttached 	= settings.attached;
		if(domPosition == 'before') {
			$(domAttached).before( jQuery.createPopup(settings) );
		} else if(domPosition == 'prepend') {
			$(domAttached).prepend( jQuery.createPopup(settings) );
		} else if(domPosition == 'append') {
			$(domAttached).append( jQuery.createPopup(settings) );
		} else {
			$(domAttached).after( jQuery.createPopup(settings) );
		}
	};
	this.popupActions = function() {
		if(settings.draggable) {
			jQuery.makeDraggable(settings);
		}
		if(settings.resizable) {
			jQuery.makeResizable(settings);
		}
		jQuery.clickActions(settings);
	};
	$(this).removeTransparentBgChild();
}

jQuery.createPopup = function (set) {
	var settings = set;
	this.fullDiv = function () {
		return '<div id="' + settings.prefix + 'Base" class="basePopupDiv" align="center">'+ 
				 '<div id="' + settings.prefix + 'Collapse" class="popupDiv">'+
				 '<div  id="' + settings.prefix + 'DragHandle" class="a">'+
					'<table class="rr_Table" id="'+settings.prefix+'Id" border="0" cellspacing="0" cellpadding="0" width="' + settings.width + 'px">'+
						'<tr>'+
							'<td class="rr_'+ settings.theme +'_top_left">&nbsp;</td>'+
							'<td class="rr_'+ settings.theme +'_top_top">&nbsp;</td>'+
							'<td class="rr_'+ settings.theme +'_top_right">&nbsp;</td>'+
						'</tr>'+
						'<tr>'+
							'<td class="rr_'+ settings.theme +'_left_left">&nbsp;</td>'+
							'<td class="rr_'+ settings.theme +'_center">'+
			
								'<div id="buttonsDiv">'+
									'<img width="36" height="36" src="images/icons/' + settings.showIcon + '"' + 'alt="'+ settings.iconAlt +'" />'+
									'<h3 class="header" id="'+settings.prefix+'H3">' + settings.showText + '</h3>' +
									this.getButtons()+
							    '</div>'+ 
							'</td>'+
						    '<td class="rr_'+ settings.theme +'_right_right">&nbsp;</td>'+
						  '</tr>'+
						  '<tr>'+
						    '<td class="rr_'+ settings.theme +'_bottom_left">&nbsp;</td>'+
						    '<td class="rr_'+ settings.theme +'_bottom_bottom">&nbsp;</td>'+
						    '<td class="rr_'+ settings.theme +'_bottom_right">&nbsp;</td>'+
						  '</tr>'+
						'</table>'+
 				 '</div>'+		
					'</div>'+		
				'</div>';		
	};

	this.getButtons = function() {
		switch (settings.buttons) {
			case 1:
				return '<input type="button" class="btnOK" value="تائید" id="ok" />' ;

			case 2:
				return '<input type="button" class="btnOK" value="تائید" id="commit" />' +
					   '<input type="button" class="btnReset" value="انصراف" id="cancel" />';
			default:
				return '';		   
		}
	};	
	return this.fullDiv();
};

jQuery.makeDraggable = function (set) {
	var settings = set;
	var handleName = "#" + settings.prefix + "DragHandle";
	$("#" + settings.prefix + "Collapse").Draggable({zIndex:1000, ghosting:true, opacity:0.1, handle:handleName});
}


jQuery.makeResizable = function (set) {
	var settings = set;
	var handleName = "#" + settings.prefix + "DragHandle";
	$("#" + settings.prefix + "Collapse").Resizable(
	{
		minWidth: 450,
		minHeight: 150,
		maxWidth: 1000,
		maxHeight: 5000,
		dragHandle: handleName,
		handlers: {}
	});
}


jQuery.clickActions = function (set) {

	var settings = set;
	var bDiv = "#" + settings.buttonsDiv ;
	var popUpDiv = "#" + settings.prefix + 'Base' ;
	var formName = "#" + settings.formName ;
	var windowLocation = settings.windowLocation;

	$(bDiv + " #commit").click(function () {
		if (settings.formName === "printOrder") {
			popUp(settings.reqCode);
			$(popUpDiv).hide();		
			$("#bgDiv").remove();
		} else {
			// Meaning:  the viewed order is going to be deleted
			if (settings.viewId != null) {
				window.location = windowLocation+settings.viewId;		
			} else if (windowLocation !== '') {
				window.location = windowLocation;
			}
	 		// end
			$( formName + " input[@name='reqCode']").attr('value',settings.reqCode);
			$(formName).submit();
		}
	});

	$(  bDiv + " #cancel ," + bDiv + " #ok"  ).click(function () {
		if ( settings.formName === "printOrder" || settings.viewId != null) {
			$(popUpDiv).hide();		
			$("#bgDiv").remove();
		} else {
			if (formName !== '#') {
				flushData();
				$(formName).submit();
			} else {
				$(".delete").removeAttr("href");
				$(popUpDiv).hide();
				$("#bgDiv").remove();
			}
		}		
	});
}


function popUp(URL) {
	var id = 'Print';
	ajaxSetCount($("#orderIds").val());
	eval("window.open(encodeUrl(URL));");
}


function encodeUrl(url) {  
	return encodeURI(url);  
}  



function encodeUrl(url) {  
      if (url.indexOf("?")>0)  
      {  
          encodedParams = "?";  
          parts = url.split("?");  
          params = parts[1].split("&");  
          for(i = 0; i < params.length; i=i+1)  
           {  
               if (i > 0)  
               {  
                   encodedParams += "&";  
               }  
               if (params[i].indexOf("=")>0) //Avoid null values  
               {  
                   p = params[i].split("=");  
                   encodedParams += (p[0] + "=" + escape(encodeURI(p[1])));  
               }  
               else  
               {  
                   encodedParams += params[i];  
               }  
           }  
           url = parts[0] + encodedParams;  
       }  
       return url;  
  }  
