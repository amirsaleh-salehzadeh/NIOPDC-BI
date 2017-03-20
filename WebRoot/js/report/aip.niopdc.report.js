var productFilterWrapper = "#productFilterWrapper";
var buyTypeFilterWrapper = "#buyTypeFilterWrapper";
var reportWrapper = "#reportWrapper";
var filterType = '';
var locationId = '';

$(document).ready(function () {

	$("#productFilter").click(function(){
		var newLocationId = $("#location option:selected").val();
		filterType = 'productFilter';
		$(this).createTransparentBg();
		$(this).removeTransparentBgChild();
		if ($(productFilterWrapper).html() === null || newLocationId !== locationId) {
			showFilter('products');
		} else {
			$(productFilterWrapper).show();
		}
	}); 
	$("#buyTypeFilter").click(function(){
		filterType = 'buyTypeFilter';
		$(this).createTransparentBg();
		$(this).removeTransparentBgChild();
		if ($(buyTypeFilterWrapper).html() === null) {
			showFilter('buyTypes');
		} else {
			$(buyTypeFilterWrapper).show();
		}
	}); 

	$("#getThisPageExcelReportHref").click(function(){
		$("#customerQuotaForm input[@name='reqCode']").val('thisPageExcelReport');
		$("form#customerQuotaForm").attr('action', "/AIPNIOPDCSell/customerQuota.do");
		$("form#customerQuotaForm").submit();
		$("#customerQuotaForm input[@name='reqCode']").val('view');
		$("form#customerQuotaForm").attr('action', "/AIPNIOPDCSell/t_customerQuota.do");
	});

	$("#getXMLReportHref").click(function(){
		ajaxGetReportXML();
	});
	
});

 
function showFilter(filterType) {
	var url = "";
	var title = "";
	var prefix = "";
	var width = "";
	locationId = $("#location option:selected").val();
	if (filterType === 'products') {
		prefix = 'productFilter';
		title = "فیلتر فرآورده ها";
		url="/AIPNIOPDCSell/productsFilter.do?reqCode=getProducts&locationId="+locationId;
		width = 400;
	} else if (filterType === 'buyTypes') {
		prefix = 'buyTypeFilter';
		title = "فیلتر نوع خرید";
		url="/AIPNIOPDCSell/productsFilter.do?reqCode=getBuyTypes&locationId="+locationId;
		width = 300;
	}
	$.get(url,function(theData){
		$(this).dialogWindow({
			prefix: prefix, 
			attached: '#filtersPage',
			title: title ,
			content: theData,
			closeMode: 'hide',
			customWidth:width,
			resizable: false
		});
		bindFilterDialogButtons();
		bindActionsForSelectingFilters();
	});
 


}
function bindFilterDialogButtons(){

	if (filterType === 'productFilter') {
		$(productFilterWrapper +" .products").quicksearch({
			stripeRowClass: ['odd', 'even'],
			position: 'before',
			attached: "#filter",
			labelText: 'فیلتر: '
		});
		$(productFilterWrapper + " #btnSubmitFilters").bind('click',btnSubmit_click);
		$(productFilterWrapper + " #btnCancel").bind('click',btnCancel_click);
		$("#newlySelectedRolesString").val($('#productsSelected').val());
	} else if (filterType === 'buyTypeFilter') {
		$(buyTypeFilterWrapper + " #btnSubmitFilters").bind('click',btnSubmit_click);
		$(buyTypeFilterWrapper + " #btnCancel").bind('click',btnCancel_click);
		$("#newlySelectedRolesString").val($('#buyTypesSelected').val());
	}
}

function btnSubmit_click() {
	if (filterType === 'productFilter') {
		$("#productsSelected").val($('#newlySelectedRolesString').val());
	} else if (filterType === 'buyTypeFilter') {
		$("#buyTypesSelected").val($('#newlySelectedRolesString').val());
	}
	closeFilterApproverDialog();	
}

function btnCancel_click(){
	closeFilterApproverDialog();	
}
function closeFilterApproverDialog(){
	if (filterType === 'productFilter') {
		$(productFilterWrapper).hide();
	} else if (filterType === 'buyTypeFilter') {
		$(buyTypeFilterWrapper).hide();
	}
	$("#bgDiv").remove();
}


function bindActionsForSelectingFilters() {
	var newlySed = $("#newlySelectedRolesString").val();
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
		$('#newlySelectedRolesString').attr('value',newlySelectedRoles);
		$("input[@type='checkbox']").bind('click',function(){
			if ($(this).attr('checked')) {
				newlySelectedRoles.push($(this).attr("id"));
				$('#newlySelectedRolesString').attr('value',newlySelectedRoles);
			}	else  {
				var indexOf = newlySelectedRoles.indexOf($(this).attr('id'));
				if (indexOf != '-1') {
					newlySelectedRoles.splice(newlySelectedRoles.indexOf($(this).attr("id")),1);
				}
				$('#newlySelectedRolesString').attr('value',newlySelectedRoles);
			} 
		});
	}
}


function ajaxGetReportXML() {
	var xmlHttp;
	try {
		// Firefox, Opera 8.0+, Safari
		xmlHttp = new XMLHttpRequest();
	} catch (e) {
		// Internet Explorer
		try {
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
		    try {
      			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
      		} catch (e) {
      			alert("Your browser does not support AJAX!"); 
      			return false;
			}
    	}
	}
	xmlHttp.onreadystatechange = function() {
	  	if(xmlHttp.readyState == 4) {
			window.open('/AIPNIOPDCSell/cache/report/report-'+$("#getReportXML").val()+'.xls');
		}
	}
	var url = '/AIPNIOPDCSell/customerQuota.do?reqCode=xmlReport&';
	url+="locationId="+ $("#getReportXML").val();
	xmlHttp.open("POST",url,true);
	xmlHttp.send(null);
}

function commoGenerator() {
	$("#lanAmount").val(addCommas($("#lanAmount").val()));
	$("#internetAmount").val(addCommas($("#internetAmount").val()));
}
