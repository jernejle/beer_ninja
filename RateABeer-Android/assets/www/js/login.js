// http://localhost:8080/RateABeer_web/rest/user/login/test/123465
var loginURI = restUrl + "user/login/";

function loginUser() {
	
	var baza = new DBStorage();
	baza.setup(null);
	
	var userName = $('#userName').val();
	var password = $('#password').val();

	 var userData = JSON.stringify({
		 	id : -1,
		 	name : '',
		 	lastName : '',
	        username : userName,
	        password : password,
	        email : '',
	        fbid : 0,
	        comments: [],
	        ratings:[],
	        locations:[],
	        favouriteBeers:[],
	        evemts:[]
	    });

	 loginURI = loginURI + userName+"/"+password;
	 
	 console.log('Username: ' + userName);
	 console.log('Password: ' + password);
	 console.log('URL: ' + loginURI);
	 console.log('Data: ' + userData);
	 
	 $.ajax({
			type : "POST",
			contentType : "application/json",
			url : "http://192.168.1.2:8080/RateABeer_web/rest/user/login",
			processData : false,
			data : userData,
			success : function(data) { 

				if (data.username === userName) {				
					db = new DBStorage();
				    db.setup();				    
				    db.saveEntry(data, loginSuccessful());
				}
				
				console.log('Klic uspesen.');
				console.log('ID: ' + data.id);
				console.log('Name: ' + data.name);

			  },
			  error       : function(xhr, textStatus, errorThrown){ 
				  console.log(textStatus);
				  console.log(xhr);
				  alert("Napaka: " + xhr + " " + xhr.status);
				  alert(JSON.stringify(xhr));
				  alert(xhr.responseText);
			  } 
		});
	 
//	$.ajax({
//		  type        : "POST", 
//		  contentType : "application/json",
//		  dataType: "jsonp",
//		  jsonp: 'callback',
//		  url         : loginURI,  
//		  //data        : userData, 
//		  processData : false,    
//		  success     : function(data){ 
//			  console.log('Klic uspesen.');
//		    alert("OK"); 
//		    console.log(data);
//		  },
//		  error       : function(xhr, textStatus, errorThrown){ 
//			  console.log(textStatus);
//			  console.log(xhr);
//			  alert("Napaka: " + xhr + " " + xhr.status);
//			  alert(xhr.responseText);
//		  } 
//	 });

};

function loginSuccessful() {
	window.location.replace('index.html');
};

function onDeviceReady() {	
	$('#loginButton').click(loginUser);
}

$(document).ready(function () {	
	document.addEventListener("deviceready", onDeviceReady, false);
});