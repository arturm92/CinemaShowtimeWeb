$(document).ready(function() {
	$("#mainDataTable tr").click(function() {
		$(this).addClass('selected').siblings().removeClass('selected');
		var value = $(this).find('td:first').html();
		
		var x = document.getElementById("selectedFrom").elements.length; 
		
		var det = document.getElementById('selectedFrom:selectedItem');
		det.value = value;
	});
});