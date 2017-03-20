var browser = navigator.appName;
var ExtraSpace     = 10;
var WindowLeftEdge = 0 ; 
var WindowTopEdge = 0 ;
var WindowWidth = 0 ;
var WindowHeight = 0 ;
var WindowRightEdge = 0 ;
var WindowBottomEdge = 0 ; 

$(document).ready(function(){
	$(this).beautification();
	if ( isMessageAvailabe() ) {
		$(this).createTransparentBg4Message();
		$("#messageDiv").css('visibility','visible');	
	}
	$("#showhideNavigation").click(function(){
		if ($("#navigationDIV").css('margin-right') === '-185px') {
			$("#navigationDIV").css('margin-right','0px');
			$("#showhideNavigation").attr('src','images/navigation/hideNavigation.png');
		} else {
			$("#navigationDIV").css('margin-right','-185px');
			$("#showhideNavigation").attr('src','images/navigation/showNavigation.png');
		}
	});
	$("#bodyTable").click(function(){
		$("#navigationDIV").css('margin-right','-185px');
		$("#showhideNavigation").attr('src','images/navigation/showNavigation.png');
	});
	$(".defaultFocus").focus();
	$('.dontSubmitForm').keypress(function(e){
		if(e.which == 13){
			return false;
		}
	});
//	$('.canInvokeSubmit').keypress(function(e){
//		if(e.which == 13){
//		}
//	});
});


function isMessageAvailabe(){
	var err = false;
	if ($("#messageDiv #messageTable #messageContainer #errorDescription").html() === null) {
		err = false;		
	} else if ($("#messageDiv #messageTable #messageContainer #errorDescription").html() === 'null') {
		err = false;		
	} else if ($("#messageDiv #messageTable #messageContainer #errorDescription").html() === "") {
		err = false;		
	} else {
		err = true;
	}
	 
	var success = false;
	if ($("#messageDiv #messageTable #messageContainer #successDescription").html() === null) {
		success = false;
	} else if ($("#messageDiv #messageTable #messageContainer #successDescription").html() === 'null') {
		success = false;
	} else if ($("#messageDiv #messageTable #messageContainer #successDescription").html() === "") {
		success = false;
	} else {
		success = true;
	}

	return success || err;
}


jQuery.fn.beautification = function(){
	$(".dataList table tr").hover(
		function(){$(this).addClass("highlightBlue");	},
		function(){$(this).removeClass("highlightBlue");}
	);
	$("form  table table table tr").filter(":first").remove();
	$("form  table table table tr").filter(":last").remove();
};

jQuery.fn.isInteger = function(s){ 
	var i;
	for (i = 0; i < s.length; i=i+1){
		// Check that current character is number.
		var c = s.charAt(i);
		if (((c < "0") || (c > "9"))) { 
			return false; 
		}
	}
	// All characters are numbers.
	return true;
};



function calculateDate(strInput){
	strInput = strInput.split("/");
	var day = strInput[2];
	if (day*1 < 10) {
		day = '0'+day*1;
	}
	var month = strInput[1];
	if (month*1 < 10) {
		month = '0'+month*1;
	}
	var year = strInput[0];
	var fullDate =  year + month + day + ""; 
	return fullDate;
}

function removeHighlightedDOMs(){
	$(".chooseDate span:eq(0)").removeClass('focusedElementOnError');
	$(".chooseDate span:eq(1)").removeClass('focusedElementOnError');
	$(".chooseDate span").removeClass('focusedElementOnError');
}

function calculateDimensions() {
	if (browser.indexOf('Microsoft') != -1 ) {	
		WindowLeftEdge = document.body.scrollLeft;
		WindowTopEdge  = document.body.scrollTop;
		WindowWidth    = document.body.clientWidth;
		WindowHeight   = document.body.clientHeight;
		WindowRightEdge  = (WindowLeftEdge + WindowWidth) - ExtraSpace;
		WindowBottomEdge = (WindowTopEdge + WindowHeight) - ExtraSpace;
	} else if (browser.indexOf('Netscape') != -1) {
		WindowLeftEdge = document.body.scrollLeft;
		WindowTopEdge  = document.body.scrollTop;
		WindowWidth    = document.body.clientWidth;
		WindowHeight   = document.body.clientHeight;
		WindowRightEdge  = (WindowLeftEdge + WindowWidth) - ExtraSpace;
		WindowBottomEdge = (WindowTopEdge + WindowHeight) - ExtraSpace;
	}
}

jQuery.fn.removeTransparentBgChild = function(){
	$("#bgDiv").html("");
};

jQuery.fn.createTransparentBg = function(){
	calculateDimensions();
	var bgObj = document.createElement("div"); 
	bgObj.setAttribute('id','bgDiv'); 
	bgObj.style.position="fixed"; 
	bgObj.style.top="0"; 
	bgObj.style.background="#cccccc"; 
	bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75"; 
	bgObj.style.opacity="0.6"; 
	bgObj.style.left="0"; 
	bgObj.style.width= (WindowRightEdge + 250) + "px"; 
	bgObj.style.height= (WindowBottomEdge + 250) + "px"; 
	bgObj.style.zIndex = "999";
	
	var bgChild = document.createElement("img");
	bgChild.setAttribute('src','images/icons/loading.gif');
	bgChild.setAttribute('id','imgLoading');
	bgChild.setAttribute('width','75px');
	bgChild.setAttribute('height','75px');
	bgChild.style.position="fixed";
	bgChild.style.top = "50%";
	bgChild.style.left = WindowLeftEdge + (WindowWidth-150)/2 +"px";
	$(bgObj).append(bgChild);
	document.body.appendChild(bgObj); 
};


jQuery.fn.validateDate = function (options) {
	this.settings = $.extend({
			fromDateDOM: "",
			fromDateLabel:"",
			toDateDOM: "",
			toDateLabel:""
	}, options || {});	
	
	var set = this.settings;
	var fromDate = "";
	var toDate = "";

	$(set.fromDateLabel).removeClass('focusedElementOnError');
	$(set.toDateLabel).removeClass('focusedElementOnError');
	// computing the fromDate field for its value 
	// If it is undefined showing error else receiving its value		
	if ($(set.fromDateDOM).val() !== undefined || $(set.fromDateDOM).val() !== undefined) {
		fromDate = calculateDate($(set.fromDateDOM).val());
		if (fromDate == 'NaN') {
			$(this).getMessage( {	
				messageDesc: "مقدار از تاریخ صحیح نیست" , 
				toBeHighlightedDOM: set.fromDateLabel,
				messageType: 'error'
			});
			return false;
		}
	}
	// computing the toDate field for its value 
	// If it is undefined showing error else receiving its value		
	if ($(set.toDateDOM).val() !== undefined || $(set.toDateDOM).val() !== "") {
		toDate =  calculateDate($(set.toDateDOM).val());
		if (toDate == 'NaN') {
			$(this).getMessage( {	
				messageDesc: "مقدار تا تاریخ صحیح نیست" , 
				toBeHighlightedDOM: set.toDateLabel,
				messageType: 'error'
			});
			return false;
		}
	}
	// Checking if fromDate is greater than toDate
	if ( (fromDate > toDate) && (fromDate !== "" && toDate !== "")  ) {
			$(this).getMessage( {	
				messageDesc: "از تاریخ نمی تواند بعد از تا تاریخ باشد" , 
				toBeHighlightedDOM: set.fromDateLabel + "," + set.toDateLabel,
				messageType: 'error'
			});
		return false;
	} 
};


jQuery.fn.getMessage = function(options) {
	this.settings = $.extend({
		messageDesc: '',
		toBeHighlightedDOM: '',
		color: 'red',
		height: ''
	}, options || {});
	createMessage(this.settings);	
};


function createMessage(settings) {
	$(".viewTitles").removeClass('focusedElementOnError');
	$(this).createTransparentBg4Message();
	$("#messageContainer").empty();
	$("#messageContainer").append('<div id="mDiv" style="'+settings.height+'overflow: auto;"><label id="errorDescription" style="color: ' + settings.color + ';">'+ settings.messageDesc +'</label></div>');
	$("#messageDiv").css('visibility','visible');
	if (settings.toBeHighlightedDOM !== '') {
		var doms = 	settings.toBeHighlightedDOM.split(",");
		for (var i = 0 ; i < doms.length ; i = i+1) {
			$(doms[i]).addClass('focusedElementOnError');
		}
	}
}


jQuery.fn.createTransparentBg4Message = function(){
	calculateDimensions();
	var bgObj = document.createElement("div"); 
	bgObj.setAttribute('id','bgDiv4Message'); 
	bgObj.style.position="fixed"; 
	bgObj.style.top="0"; 
	bgObj.style.background="#cccccc"; 
	bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75"; 
	bgObj.style.opacity="0.6"; 
	bgObj.style.left="0"; 
	bgObj.style.width= (WindowRightEdge + 250) + "px"; 
	bgObj.style.height= (WindowBottomEdge + 250) + "px"; 
	bgObj.style.zIndex = "999";
	document.body.appendChild(bgObj); 
	removeMessage();
};

function removeMessage(){
	$("#bgDiv4Message").click(function(){
		$("#bgDiv4Message").remove();
		$("#messageDiv").css('visibility','hidden');
	});
	$("#messageDiv").click(function(){
		$("#bgDiv4Message").remove();
		$("#messageDiv").css('visibility','hidden');
	});
}


function removeCommas(nStr) {
	try {
		return nStr.replace(/,/g,"");
	} catch(e) {
		return "";
	}	
}

function addCommas(nStr) {
	nStr += '';
	var x = nStr.split('.');
	var x1 = x[0];
	var x2 = x.length > 1 ? '.' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + ',' + '$2');
	}
	return x1 + x2;
}

function removeAllCommas() {
	$(".numberFormat").each(function(i){
		$(this).val(removeCommas($(this).val()));
	});
}

function addAllCommas() {
	$(".numberFormat").each(function(i){
		$(this).val(addCommas($(this).val()));
	});
}









		function showhideNavigation(){
		if ($("#navigationDIV").css('margin-right') === '-185px') {
			$("#navigationDIV").css('margin-right','0px');
			$("#showhideNavigation").attr('src','images/navigation/hideNavigation.png');
		} else {
			$("#navigationDIV").css('margin-right','-185px');
			$("#showhideNavigation").attr('src','images/navigation/showNavigation.png');
		}
		}













//jQuery.fn.jPrintArea = function(pageHtml){
//	var iframe = document.createElement('IFRAME');
//	var doc = null;
//	$(iframe).attr('style','position:absolute;width:0px;height:0px;left:-500px;top:-500px;');
//	document.body.appendChild(iframe);
//	doc = iframe.contentWindow.document;
//	doc.write(pageHtml);
//	doc.close();
//	iframe.contentWindow.focus();
//	iframe.contentWindow.print();
//	wait(1);
//	document.body.removeChild(iframe);
//};



//function format4print(htmElem) {
//
//	var htm = htmElem.html();
//	var subStr = "";
//	var replacement = "";
//	var value = "";
//	var theNode = "";
//	var theNodeParent = "";
//	
//	var selects = $( "#"+$(htmElem).attr('id') + " select");
//	for (var i = 0 ; i < selects.length ; i = i+1) {
//		subStr = htm.substring(htm.indexOf("<select"),htm.indexOf("</select>")+9);
//		value = $("select[@name="+ $(selects[i]).attr('name') +"] option:selected").text();
//		if (value === "") {
//			value = "-";
//		}
//		replacement = "<span>"+value+"</span>&nbsp;&nbsp;&nbsp;&nbsp;";
//		htm = htm.replace(subStr,replacement);
//	}
//
//	var inputs = $( "#"+$(htmElem).attr('id') + " input[@type='text']:visible");
//	for (i = 0 ; i < inputs.length ; i = i+1) {
//		if ($(inputs[i]).attr('name') === undefined || $(inputs[i]).attr('name') === 'undefined') {
//			theNode = window.document.getElementById($(inputs[i]).attr('id'));
//			theNodeParent = theNode.parentNode;
//			subStr = $(theNodeParent).html();
//			value = $(inputs[i]).val();
//			if (value === "") {
//				value = "-";
//			}
//			replacement = "<span>"+value+"</span>&nbsp;&nbsp;&nbsp;&nbsp;";
//		} else {
//			theNode = window.document.getElementsByName($(inputs[i]).attr('name'));
//			theNodeParent = theNode[0].parentNode;
//			subStr = $(theNodeParent).html();
//			value = $(inputs[i]).val();
//			if (value === "") {
//				value = "-";
//			}
//			replacement = "<span>"+value+"</span>&nbsp;&nbsp;&nbsp;&nbsp;";
//		}
//		htm = htm.replace(subStr,replacement);
//	}
//
//	if (htm === null || htm === 'null') {
//		htm = " ";
//	}
//	return htm;
//}






