jQuery.fn.makeTree = function(options) {
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

$(document).ready(function(){
	getReady();
});

function getReady() {
	var handleName = "#PREFIXDragHandle";
	var resizeName = "#PREFIXWindowResize";
	var treeName = "#PREFIXTree";
	var collapseName = "#PREFIXCollapse";
	var formName = "#PREFIXPopupForm";
	$("#PREFIXCollapse").Draggable({zIndex:1000, ghosting:true, opacity:'0.3', handle:"#PREFIXDragHandle"});
	$(collapseName + " .close").click(function () {
		$(collapseName).hide();
	});
	$("#PREFIXCollapse").Resizable(
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
	$("#PREFIXTree tr td").quicksearch({
		stripeRowClass: ['odd', 'even'],
		position: 'before',
		attached: formName,
		labelText: 'فیلتر: '
	});
}