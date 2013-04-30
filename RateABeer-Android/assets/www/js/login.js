// http://localhost:8080/RateABeer_web/rest/user/login/test/123465
var loginURI = "http://localhost:8080/RateABeer_web/rest/user/login/";

function loginUser() {
	
	var userName = $('#userName').val();
	var password = $('#password').val();

	 var userData = JSON.stringify({
	        username    : userName,
	        password  : password     
	    });
	
	 console.log('Username: ' + userName);
	 console.log('Password: ' + password);
	 console.log(loginURI);
	 console.log(userData);
	 
	$.ajax({
		  type        : "GET", 
		  contentType : "application/json",
		  dataType: 'jsonp',
		  jsonp: 'callback',
		  url         : loginURI,  
		  data        : userData, 
		  processData : false,    
		  success     : function(data){ 
			  console.log('Klic uspesen.');
		    alert("OK"); 
		  },
		  error       : function(xhr, textStatus, errorThrown){ 
			  console.log(textStatus);
			  console.log(xhr);
		    alert("Napaka: " + xhr + " " + xhr.status);
		    alert(xhr.responseText);
		  } 
	 });
	
//	$.ajax({
//		  type        : "POST", 
//		  contentType : "application/json",
//		  dataType: "json",
//		  url         : loginURI,  
//		  data        : userData, 
//		  processData : false,    
//		  success     : function(data){ 
//			  console.log('Klic uspesen.');
//		    alert("OK"); 
//		  },
//		  error       : function(xhr, textStatus, errorThrown){ 
//			  console.log(textStatus);
//			  console.log(xhr);
//		    alert("Napaka: " + xhr + " " + xhr.status);
//		    alert(xhr.responseText);
//		  } 
//	 });

};

$(document).ready(function () {
	
	$('#loginButton').click(loginUser);
	
//	$.ajax(GetAllContactsUri, {
//		beforeSend: function (xhr) {
//			$.mobile.showPageLoadingMsg();
//		},
//		complete: function () {
//			$.mobile.hidePageLoadingMsg();
//		},
//
//		contentType: 'application/json',
//		dataType: 'jsonp',
//		jsonp: 'callback',
//		type: 'GET',
//		error: function (xhr, ajaxOptions, thrownError) {
//			alert(xhr.status);
//			alert(xhr.responseText);			
//		},
//		
//		success: function (data) {
//			var result = data.GetAllContactsResult;
//
//			$.each(result, function (index, output) {
//				$('#contactsList').append('<li><h2>' + output.FirstName + ' ' + output.LastName + '</h2><p>' + output.PhoneNumber + '</p></li>');
//			});
//
//			$('#contactsList').listview('refresh');
//		}
//	});
});