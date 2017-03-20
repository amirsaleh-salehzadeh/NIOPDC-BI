jQuery.fn.quicksearch = function (options) {
	
	this.timeout = null;
	
	this.settings = $.extend({
		
		position: 'prepend',
		attached: 'body',
		formId: 'quicksearch',
		labelText: 'Quick Search',
		labelClass: 'qs_label',
		inputText: null,
		inputClass: 'qs_input',
		loaderId: 'loader',
		loaderClass: 'loader',
		loaderImg: 'images/icons/loading.gif',
		loaderText: 'در حال جستجو ...',
		stripeRowClass: null,
		hideElement: null,
		delay: 1000,
		focusOnLoad: false,
		randomElement: 'qs'+Math.floor(Math.random()*1000000),
		option: false, //{true, false}
		lableAll: 'همه',
		lableChecked: 'انتخاب شده ها',
		optionMode: 'all' //{all, checked}
	}, options || {});
	
	var el 		= this;
		
	var form1 	= new jQuery._form(this.settings);
	var key1	= new jQuery._key(this.settings);
	var loader	= new jQuery._loader(this.settings);
	var stripes = new jQuery._stripe(this.settings.stripeRowClass);	
	var option = this.settings.option;
	
	form1.initialize();
	loader.setTo('hide');	
	
	if(this.settings.stripeRowClass != null) {
		$(el).each(function () {			
			if(el.settings.hideElement == "grandparent") {
				stripes.go(this.parentNode.parentNode);
			} else if (el.settings.hideElement == "parent") {
				stripes.go(this.parentNode);
			} else {
				stripes.go(this);
			}
		})
		stripes.reset();
	}		
	
	$('form.quicksearch').submit( function () { return false; });
	
	$('input[@rel="'+this.settings.randomElement+'"]').keydown(function(e) {
		switch(e.keyCode) {
			case 9: 
			case 13:
			case 38: 
			case 40:
				e.preventDefault();
				break;
			default:
				clearTimeout(this.timeout);				
			
				this.timeout = setTimeout(function () {
						loader.setTo('show');
						setTimeout( function () {
						
							key1.setKey();
														
							$(el).each( function () {
								
								if(el.settings.hideElement == "grandparent") {
									var hide = $(this).parent().parent();
								} else if (el.settings.hideElement == "parent") {
									var hide = $(this).parent();
								} else {
									var hide = $(this)
								}
															
								if(!option){							
									if(key1.test(this)) {			
										$(hide).show();
										jQuery._openParents($(this));		
									} else {
										$(hide).hide();
									}
								} else if (option){
									if($(this).children("span").children("input").attr('checked') == true){
										if(key1.test(this)) {			
											$(hide).show();
											jQuery._openParents($(this));										
										} else {
											$(hide).hide();
										}
									}
								}
									
								if(el.settings.stripeRowClass != null) {									
									if(el.settings.hideElement == "grandparent") {
										stripes.go(this.parentNode.parentNode);
									} else if (el.settings.hideElement == "parent") {
										stripes.go(this.parentNode);
									} else {
										stripes.go(this);
									}
								}				
							});	
																						
							stripes.reset();
						}, el.settings.delay);						
						setTimeout( function () { 
							loader.setTo('hide');
						}, el.settings.delay);			
				}, el.settings.delay);
				break;
		}
	});

	if(this.settings.option){
		$("#filterOptionsGroup_all").focus(function (){
			//jQuery._closeAll(el);
			$(el).show();
		});
		$("#filterOptionsGroup_checked").focus(function (){
			//jQuery._closeAll(el);
			$(el).each(function(){
				if($(this).children("span").children("input").attr('checked') == true){
					$(this).show();
					$(this).parents("li").show();
					jQuery._openParents($(this));
				} else {
					$(this).hide();
				}
			});
		});
	}
}

jQuery._key = function (set) {
	
	this.key = "";
	this.settings = set;
	this.getKey = function ()  {
		return this.key;
	};
	this.setKey = function () {
		var input = $('input[@rel="'+this.settings.randomElement+'"]').val();
		var string = input.replace(/\s{2,}/g, " ").toLowerCase();
		var arr = string.split(" ");
		for(var i in arr) {
			var regexp = new RegExp(/([^A-Za-z0-9])/gi);
			if(arr[i] == "") {
				arr.splice(i,1);
			}
		}
		this.key = arr;
	};
	this.test = function (el) {
		if( this.getKey() == '' ) {
			return true;
		} else {			
			var searchStrings = this.getKey();
			var text = $._stripHtml( $(el).html() );	
			for (var i = 0; i < searchStrings.length; i+=1) {
				var test = text.indexOf(searchStrings[i]);	
				if (test == -1) {	
					return false;
				}
			}			
			return true;
		}
	}	
}

jQuery._form = function (set) {	
	
	this.settings = set;
	
	this.initialize = function () {
		this.placeForm();
		if(this.settings.focusOnLoad) {
			this.focusOnLoad();
		}
		if(this.settings.inputText != "" && this.settings.inputText != null) {
			this.toggleText();
		}
	};
	this.placeForm = function () {
		var formPosition 	= this.settings.position;
		var formAttached 	= this.settings.attached;
					
		if(formPosition == 'before') {
			$(formAttached).before( jQuery._makeForm(this.settings) );
		} else if(formPosition == 'prepend') {
			$(formAttached).prepend( jQuery._makeForm(this.settings) );
		} else if(formPosition == 'append') {
			$(formAttached).append( jQuery._makeForm(this.settings) );
		} else {
			$(formAttached).after( jQuery._makeForm(this.settings) );
		}
	};
	this.focusOnLoad = function () {
		$('input[@rel="'+this.settings.randomElement+'"]').get(0).focus();
	};
	this.toggleText = function () {
		var el = this;
		$('input[@rel="'+this.settings.randomElement+'"]').focus(function () {
			if($(this).val() == el.settings.inputText) {
				$(this).val('');
			}
		}),
		$('input[@rel="'+this.settings.randomElement+'"]').blur(function () {
			if($(this).val() == "") {
				$(this).val(el.settings.inputText);
			}
		})
	};	
};

jQuery._loader = function (set) {
	this.settings = set;
	this.setTo = function (to) {
		if(this.settings.loaderId) {
			if(to == 'hide') {
				$('input[@rel="'+this.settings.randomElement+'"]').parent().find('.loader').hide();
			} else {
				$('input[@rel="'+this.settings.randomElement+'"]').parent().find('.loader').show();
			}			
		}
	}
}

jQuery._makeForm = function (set) {
	this.settings = set;
	this.mform = function () {
		return 	'<br>'+
				'<form action="#" ' + 
					'id="'+ this.settings.formId + '" ' +
					'class="quicksearch">' +
					this.mlabel() +
					this.minput() +
					this.mloader() +	
					this.moption() +	
				'</form>';
	};
	this.mlabel = function () {
		if(!this.isEmpty(this.settings.labelText)) {
			return '<label for="' + this.settings.inputId + '" '+
						'class="' + this.settings.labelClass + '">'
						+ this.settings.labelText
						+ '</label> ';	
		}
		return '';
	};
	this.minput = function () {			
		var val = (!this.isEmpty(this.settings.inputText)) ? this.settings.inputText : ""
		return '<input type="text" ' +
					'value="' + val + '" ' + 
					'rel="' + this.settings.randomElement  + '" ' +
					'class="' + this.settings.inputClass + '" ' +
					'/> ';	
	};
	this.mloader = function () {
		if(!this.isEmpty(this.settings.loaderImg)) {
			return '<img src="' + this.settings.loaderImg + '" alt="Loading" id="' + this.settings.loaderId + '" class="' + this.settings.loaderClass + '"  width="16px" height="16px"/>';
		} else {
			return '<span id="' + this.settings.loaderId + '" class="' + this.settings.loaderClass + '">' + this.settings.loaderText + '</span>';
		}
	};
	this.moption = function () {
		if(this.settings.option){	
			var opt1 = '';
			var opt2 = '';	
			if(this.settings.optionMode == 'all')
				opt1 = 'checked="checked"';
			else if(this.settings.optionMode == 'checked')
				opt2 = 'checked="checked"';			
			return 	'&nbsp;&nbsp;&nbsp;' +
					'<label>' +
					  '<input type="radio" name="filterOptionsGroup" value="radio" id="filterOptionsGroup_all"' +
					  opt1 +
					  '/> ' +  
					   this.settings.lableAll +
					'</label>' +
					'&nbsp;&nbsp;&nbsp;' +
					'<label>' +
					  '<input type="radio" name="filterOptionsGroup" value="radio" id="filterOptionsGroup_checked"' +
					  opt2 +
					  '/> ' + 
					   this.settings.lableChecked +
					'</label>';	
		}
		return '';
	};	
	this.isEmpty = function (input) {
		return (input == null || input == undefined || input == '' || input == 0) ? true: false;
	};
	return this.mform();
}

jQuery._stripHtml = function (input) {
	var regexp = new RegExp( /\<[^\<]+\>/g );
	var output = input.replace(regexp, "");
	output = output.toLowerCase();
	return output;
}

jQuery._stripe = function (set) {
	this.i = 0;
	this.set = set;
	this.go = function (el) {
		this.removeClasses(el);
		if(el.getAttribute('style') != "display: none;") {				
			$(el).addClass(this.set[this.i%this.set.length]);
			this.i += 1;
		}
	}; 
	this.removeClasses = function (el) {
		for(var j = 0; j < this.set.length; j+=1) {
			if(this.i%this.set.length != j) {
				$(el).removeClass(this.set[j]);
			}
		}
	};
	this.reset = function () {
		this.i = 0;
	}
}

jQuery._showChecked = function (set, el) {
	this.settings = set;
	var el = el;
	$(el).each(function(){
		
	});	
}

jQuery._showAll = function (set, el) {
	this.settings = set;
		
}

jQuery._openParents = function (el) {
	if(String($(el).parents("ul").eq(0).parents("li").eq(0).attr('class')).substring(0,12) == "folder-close")
	{
		var target = $(el).parents("ul").eq(0).parents("li").eq(0).children("span[@class='toggler']").eq(0).click();
		while (target.parents("ul").eq(0).parents("li").eq(0).attr('class') != 'root odd')
		{
			if(String($(target).parents("ul").eq(0).parents("li").eq(0).attr('class')).substring(0,12) == "folder-close")
			{
				target = target.parents("ul").eq(0).parents("li").eq(0).children("span[@class='toggler']").eq(0);
				target.click();
			}else
			{
				target = target.parents("ul").eq(0).parents("li").eq(0).children("span[@class='toggler']").eq(0);
			}
		}
	}
} 

jQuery._closeAll = function (el) {
	$(el).filter().each(function(){
		if(String($(this).parents("ul").eq(0).parents("li").eq(0).attr('class')).substring(0,12) != "folder-close")
		{
			var target = $(this).parents("ul").eq(0).parents("li").eq(0).children("span[@class='toggler']").eq(0).click();
			while (target.parents("ul").eq(0).parents("li").eq(0).attr('class') != 'root odd')
			{		
				if(String($(target).parents("ul").eq(0).parents("li").eq(0).attr('class')).substring(0,12) != "folder-close")
				{
					target = target.parents("ul").eq(0).parents("li").eq(0).children("span[@class='toggler']").eq(0);
					target.click();
				}else
				{
					target = target.parents("ul").eq(0).parents("li").eq(0).children("span[@class='toggler']").eq(0);
				}
			}
		}
	});
}