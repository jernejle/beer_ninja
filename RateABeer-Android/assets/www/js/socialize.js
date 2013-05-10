function initNativeDatePicker() {
	$('.nativedatepicker').focus(function(event) {

		var currentField = $(this);
		var myNewDate = Date.parse(currentField.val()) || new Date();

		// Same handling for iPhone and Android
		window.plugins.datePicker.show({
			date : myNewDate,
			mode : 'date', // date or time or blank for both
			allowOldDates : true
		}, function(returnDate) {
			var newDate = new Date(returnDate);
			currentField.val(newDate.toString("dd/MMM/yyyy"));

			// This fixes the problem you mention at the bottom of this script
			// with it not working a second/third time around, because it is in
			// focus.
			currentField.blur();
		});
	});

	$('.nativetimepicker').focus(function(event) {
		var currentField = $(this);
		var time = currentField.val();
		var myNewTime = new Date();

		myNewTime.setHours(time.substr(0, 2));
		myNewTime.setMinutes(time.substr(3, 2));

		// Same handling for iPhone and Android
		plugins.datePicker.show({
			date : myNewTime,
			mode : 'time', // date or time or blank for both
			allowOldDates : true
		}, function(returnDate) {
			// returnDate is generated by .toLocaleString() in Java so it will
			// be relative to the current time zone
			var newDate = new Date(returnDate);
			currentField.val(newDate.toString("HH:mm"));

			currentField.blur();
		});
	});
};

$(document).ready(function() {

	initNativeDatePicker();
	
	$('#flipCurrentLocation').change(function() {
		var sliderDateVal = $('#flipCurrentLocation').val();
	
		if (sliderDateVal == 'yes') {
			$('#location').prop('disabled', true);
			$('#location').css({
				'background-color' : 'grey',
			});
		} else {
			$('#location').prop('disabled', false);
			$('#location').css({
				'background-color' : 'white',
			});
		}
	
	});
	
	$('#createEventButton').click(function() {
		
		console.log("SOCIALIZE - ZACETEK");
		
		var userId = sessionStorage.userId;
		
		console.log('Trenutno prijavljen uporabnik: ' + sessionStorage.userId);
		
		console.log("SOCIALIZE - KONEC");
	});

});