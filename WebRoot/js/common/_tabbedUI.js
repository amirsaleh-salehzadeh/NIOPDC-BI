var currentPanel = '';
var currentTab = '';
$(document).ready(function(){
});

function showPanel(tab,panel){
	hidePanel(currentPanel);
	$("#"+panel).css('visibility','visible');
	currentPanel = panel;
	setState(tab);
	currentTab = tab;
}
function hidePanel(panel){
	if(currentPanel!=='') {
		$("#"+panel).css('visibility','hidden');
	}
}
function hilite(tab){
	tab.style.backgroundColor = '#ddddff';
}
function unHilite(tab){
	tab.style.backgroundColor = '#ffffff';
}
function setState(tab){
	if(currentTab !== '') {
		$("#"+currentTab).css('color','navy');
	}
	$("#"+tab).css('color','red');
}
//tab constructor
function tab(id, text,top,left,width){
	this.id = id;
	this.text = text;
	this.top = top;
	this.left = left;
	this.width = width;
}
//panel constructor
function panel(id, src){
	this.id = id;
	this.src = src;
}
//tabPanel constructor
function tabPanel(tab, panel){
	this.tab = tab;
	this.panel = panel;
	this.writeTabPanel = writeTabPanel;
}
//method that writes tabPanel implementation to page
function writeTabPanel(){
	var tpText = '';
	tpText += '<div id="'+ this.tab.id +'" class="tab" style="top:'+ this.tab.top +';left:' + this.tab.left  + ';width:' + this.tab.width + '" onClick="showPanel(\'';
	tpText += this.tab.id + '\',\'' + this.panel.id;
	tpText += '\')" onMouseOver="hilite(this)" onMouseOut="unHilite(this)">';
	tpText += this.tab.text;
	tpText += '</div>';
	tpText += '<iframe id="';
	tpText += this.panel.id;
	tpText += '" class="panel" src="';
	tpText += this.panel.src;
	tpText += '"><button onclick="window.print();" value="asdad"></button></iframe>';
	document.write(tpText);
}