var dogodek = {pos : 'testing position'};

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

function onDeviceReady() {	
	
	$('#flipCurrentLocation').bind("change", function() {
		var sliderLocationVal = $('#flipCurrentLocation').val();

		if (sliderLocationVal == 'yes') {
			$('#location').prop('disabled', true);
			$('#location').css({
				'background-color' : 'grey',
			});

			navigator.geolocation.getCurrentPosition(onSuccess, onError, { enableHighAccuracy: true });
		} else {
			$('#location').prop('disabled', false);
			$('#location').css({
				'background-color' : 'white',
			});
		}
	});

	$('#createEventButton').click(function() {
		
		console.log("SOCIALIZE - ZACETEK");

		console.log('Trenutno prijavljen uporabnik: ' + window.sessionStorage.getItem("userId"));
		
		console.log('Pozicija: ' + JSON.stringify(dogodek.pos));
		
		var des = $('eventDescription').val();
		var isPublicEvent = ($('#flipPublicEvent').val() === 'yes') ? true : false;
		
		var data = JSON.stringify({
			id : -1,
			date : new Date(),
			description : des,
			lat :  dogodek.pos.lat,
			lon :  dogodek.pos.lon,
			publicEvent : isPublicEvent,
			user : {id : window.sessionStorage.getItem("userId")},
		});
		
		console.log("Poslani podatki: "  + data);
		
		$.ajax({
			type : "POST",
			contentType : "application/json",
//			url : "http://192.168.1.2:8080/RateABeer_web/rest/user/login",
			url : restUrl + "event/create/",
			processData : false,
			data : data,
			success : function(data) { 

				console.log('Klic uspesen.');
				console.log(JSON.stringify(data));
				
				if (data.result === 'true') {
					console.log("REZULTAT USPESNO");
					navigator.notification.alert(
						    'Dogodek je bil uspešno dodan.',  // message
						    function() {window.location.replace('socialize.html');},         // callback
						    'Dogodek dodan',            // title
						    'Zapri'                  // buttonName
						);
				} else {
					console.log("REZULTAT NEUSPESNO");
				}	

				
			  },
			  error       : function(xhr, textStatus, errorThrown){ 
				  console.log(textStatus);
				  console.log(JSON.stringify(xhr));
				  alert("Napaka: " + xhr + " " + xhr.status);
				  alert(JSON.stringify(xhr));
				  alert(xhr.responseText);
			  } 
		});
		
		console.log("SOCIALIZE - KONEC");
	});
}

function onSuccess(position) {
    // Shranimo pozicijo
    dogodek.pos = {lon : position.coords.longitude, lat : position.coords.latitude};
    
    console.log("Pridobljena pozicija: " + JSON.stringify(dogodek.pos));
}

function onError(error) {
	var errorMessage = 'code: '    + error.code    + '\n' +
    					'message: ' + error.message + '\n';
	
    console.log(errorMessage);
	alert(errorMessage);
}

$(document).ready(function() {

	initNativeDatePicker();

	document.addEventListener("deviceready", onDeviceReady, false);

});