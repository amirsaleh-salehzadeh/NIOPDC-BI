		function changeSelectedTab(aForm,aInput,aValue){
			var txt=document.getElementById(aInput);
			txt.value=aValue;
			document.getElementById(aForm).submit();
		}
