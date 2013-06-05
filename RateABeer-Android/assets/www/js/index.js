/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
//var app = {
//    // Application Constructor
//    initialize: function() {
//        this.bindEvents();
//    },
//    // Bind Event Listeners
//    //
//    // Bind any events that are required on startup. Common events are:
//    // 'load', 'deviceready', 'offline', and 'online'.
//    bindEvents: function() {
//        document.addEventListener('deviceready', this.onDeviceReady, false);
//    },
//    // deviceready Event Handler
//    //
//    // The scope of 'this' is the event. In order to call the 'receivedEvent'
//    // function, we must explicity call 'app.receivedEvent(...);'
//    onDeviceReady: function() {
//        app.receivedEvent('deviceready');
//        
//        console.log("DEVICE IS READY");
//        
//        
//    },
//    // Update DOM on a Received Event
//    receivedEvent: function(id) {
//        var parentElement = document.getElementById(id);
//        var listeningElement = parentElement.querySelector('.listening');
//        var receivedElement = parentElement.querySelector('.received');
//
//        listeningElement.setAttribute('style', 'display:none;');
//        receivedElement.setAttribute('style', 'display:block;');
//
//        console.log('Received Event: ' + id);
//    }
//};

function browserLogin() {
	console.log('Browser login method...');
	if (typeof (Storage) !== 'undefined') {
		if (localStorage.user != null) {
			console.log('User is defined in storage');
			if (localStorage.user.id !== 'undefined' && localStorage.user.id > -1) {
				var logoutButton = "<a href='#' data-role='button' data-mini='true' id='logoutButton'>Log out</a>";
	
	    		$('#footerContent').html("Logged in as " + localStorage.user.username);
	    		$('#userStatusButton').html('<a id="logoutButton" href="#" data-role="button" data-mini="true" data-corners="true" data-shadow="true" data-iconshadow="true" data-wrapperels="span" data-theme="a" class="ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-hover-a ui-btn-up-a"><span class="ui-btn-inner"><span class="ui-btn-text">Log out</span></span></a>');
	    
	    		// Uporabnika se shrani v sejo
	    		window.sessionStorage.setItem("userId", localStorage.user.id + "");
	
	    		$('#logoutButton').click(function() {
	    			localStorage.user.id = -1;
	    			alert('User logged out.');
					window.location.replace('index.html');
	    			
	    		});
			} else {
//				$('#footerContent').html("");
//	    		$('#userStatusButton').html('<a id="loginButton" href="#" data-role="button" data-mini="true" data-corners="true" data-shadow="true" data-iconshadow="true" data-wrapperels="span" data-theme="a" class="ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-hover-a ui-btn-up-a"><span class="ui-btn-inner"><span class="ui-btn-text">Prijava</span></span></a>');
//	    	
//	    		$('#loginButton').click(function() {
//	    			window.location.replace('login.html');
//	    		});
			}
			
		} else {
			console.log('User IS NOT defined');
			$('#footerContent').html("");
    		$('#userStatusButton').html('<a id="loginButton" href="#" data-role="button" data-mini="true" data-corners="true" data-shadow="true" data-iconshadow="true" data-wrapperels="span" data-theme="a" class="ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-hover-a ui-btn-up-a"><span class="ui-btn-inner"><span class="ui-btn-text">Log in</span></span></a>');
    	
    		$('#loginButton').click(function() {
    			window.location.replace('login.html');
    		});
		}
		
	} else {
		console.log('Storage not supported!');
	}
}
function onDeviceReady() {	
	
	// Network state
	var networkState = navigator.connection.type;	
	console.log("Network state: " + networkState);
	if (networkState == Connection.NONE) {
		navigator.notification.alert(
			    'Internet connection is required for the application to work properly. Since you have no internet connection, the application might not work at all.',  // message
			    function(){},         		// callback
			    'No internet connection',	// title
			    'I understand'              // buttonName
			);
	}
	
	db = new DBStorage();
    db.setup();				    
    db.getEntries(function(results) {
    	console.log('Index entries: ' + JSON.stringify(results));
    	if (results.length > 0) {
    		
    		var logoutButton = "<a href='#' data-role='button' data-mini='true' id='logoutButton'>Log out</a>";

    		$('#footerContent').html("Logged in as " + results[0].username);
    		$('#userStatusButton').html('<a id="logoutButton" href="#" data-role="button" data-mini="true" data-corners="true" data-shadow="true" data-iconshadow="true" data-wrapperels="span" data-theme="a" class="ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-hover-a ui-btn-up-a"><span class="ui-btn-inner"><span class="ui-btn-text">Log out</span></span></a>');
    
    		// Uporabnika se shrani v sejo
    		window.sessionStorage.setItem("userId", results[0].id + "");

    		$('#logoutButton').click(function() {
    			db.deleteEntry({id:results[0].id}, function() {
    				console.log('Izbrisano');
    				alert('User logged out.');
    				window.location.replace('index.html');
    			})
    		});
    	
    	
    	} else {
    		$('#footerContent').html("");
    		$('#userStatusButton').html('<a id="loginButton" href="#" data-role="button" data-mini="true" data-corners="true" data-shadow="true" data-iconshadow="true" data-wrapperels="span" data-theme="a" class="ui-btn ui-shadow ui-btn-corner-all ui-mini ui-btn-hover-a ui-btn-up-a"><span class="ui-btn-inner"><span class="ui-btn-text">Log in</span></span></a>');
    	
    		$('#loginButton').click(function() {
    			window.location.replace('login.html');
    		});
    	}

    });
}

$(document).ready(function () {	
	//$('#userStatusButton').html("<a data-role='button' data-mini='true' id='logoutButton' class='ui-btn' href='#'>Odjava</a>");
	if (document.location.protocol == "file:") {
		console.log('Using PhoneGap...');
		document.addEventListener("deviceready", onDeviceReady, false);
	} else {
		console.log('Using browser...');
		browserLogin();
	}
});
