$(document).ready(function() {
	$("#table tr").click(function() {
		$(this).addClass('selected').siblings().removeClass('selected');
		var value = $(this).find('td:first').html();
		
		var det = document.getElementById('selectedItem');
		det.value = value;
	});
});