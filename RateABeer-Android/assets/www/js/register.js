
function registerUser() {
	
	var givenName = $('#givenName').val();
	var familyName = $('#familyName').val();
	var email = $('#email').val();
	var userName = $('#userName').val();
	var password = $('#password').val();

	 var userData = JSON.stringify({
		 	id : -1,
		 	name : givenName,
		 	lastName : familyName,
	        username : userName,
	        password : password,
	        email : email,
	        fbid : 0,
	        comments: [],
	        ratings:[],
	        locations:[],
	        favouriteBeers:[],
	        evemts:[]
	    });

	 console.log('URL: ' + restUrl + "user/register");
	 console.log('Data: ' + userData);
	 
	 $.ajax({
			type : "POST",
			contentType : "application/json",
//			url : "http://192.168.1.2:8080/RateABeer_web/rest/user/login",
			url : restUrl + "user/register",
			processData : false,
			data : userData,
			success : function(data) { 

				if (data.username === userName) {				
				    console.log('Login successful.');
				    alert('Registracija je bila uspešna! Lahko se prijavite z vašim novim uporabniškim imenom in geslom.');
				    window.location.replace('login.html');
				} else {
					alert('Prišlo je do napake pri registraciji...prosimo, poizkusite ponovno kasneje.');
				}
				
				console.log('Klic uspesen.');
				console.log('ID: ' + data.id);
				console.log('Name: ' + data.name);

			  },
			  error       : function(xhr, textStatus, errorThrown){ 
				  console.log(textStatus);
				  console.log(JSON.stringify(xhr));
				  alert('Prišlo je do napake pri registraciji...prosimo, poizkusite ponovno kasneje.');
//				  alert("Napaka: " + xhr + " " + xhr.status);
//				  alert(JSON.stringify(xhr));
//				  alert(xhr.responseText);
			  } 
		});

};


function onDeviceReady() {	
	$('#registerButton').click(registerUser);
}

$(document).ready(function () {	
//	document.addEventListener("deviceready", onDeviceReady, false);
	$('#registerButton').click(registerUser);
});