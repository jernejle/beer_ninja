$(document).ready(function() {
	
	var events, chosenEvent, eventData;
	var displayedEventData;
	//document.addEventListener("deviceready", onDeviceReady, false);

	// change search buttons style
	$("#allEventsLink").click(function() {
		$.fn.setButtonTheme($(this), true);
		$.fn.setButtonTheme($("#invitedEventsLink"), false);
		$.fn.setButtonTheme($("#goingEventsLink"), false);
		$(".search-input").unbind();
//		$.fn.getAllEvents();
		$.fn.getEvents("event/events");
		$.fn.bindSearch("event/search", $.fn.parseEvents);
	});
	$("#invitedEventsLink").click(function() {
		$.fn.setButtonTheme($(this), true);
		$.fn.setButtonTheme($("#allEventsLink"), false);
		$.fn.setButtonTheme($("#goingEventsLink"), false);
		$(".search-input").unbind();
		$.fn.getEvents("event/invited/"+window.sessionStorage.getItem("userId"));
		$.fn.bindSearch("event/search", $.fn.parseEvents);
	});
	$("#goingEventsLink").click(function() {
		$.fn.setButtonTheme($(this), true);
		$.fn.setButtonTheme($("#allEventsLink"), false);
		$.fn.setButtonTheme($("#invitedEventsLink"), false);
		$.fn.getEvents("event/going/"+window.sessionStorage.getItem("userId"));
		$.fn.bindSearch("event/search", $.fn.parseEvents);
	});

	$.fn.setButtonTheme = function(link, active) {
		var newTheme = (active ? "b" : "c");

		link.removeClass().addClass(
				"ui-btn ui-shadow ui-btn-corner-all ui-btn-hover-" + newTheme
						+ " ui-btn-up-" + newTheme + " ui-btn-up-" + newTheme);
		link.attr("data-theme", newTheme).trigger("mouseout");
	};

	// event listener for search buttons
	$.fn.bindSearch = function(url, parseEvents) {
		var work = false;
		$(".search-input").click(function() {
			$(this).val('');
		});
		$(".search-input").keypress(function() {
			var val = $(this).val();
			if (val.length > 2 && !work) {
				work = true;
				setTimeout(function() {
					$.ajax({
						type : "GET",
						url : restUrl + url,
						dataType : "json",
						data : {
							param : val
						},
						success : function(data) {
							parseEvents(data, $(".events_ul"));
							work = false;
						},
						error : function(xhr, textStatus, errorThrown) {
							alert("Napaka: " + xhr + " " + xhr.status);
							alert(xhr.responseText);
						}
					});
				}, 1000);
			}
		});
	};
	
	$.fn.getAllEvents = function() {
		$.ajax({
			type : "GET",
			url : restUrl + "event/events",
			dataType : "json",
			processData : false,
			success : function(data) {
				console.log("Pridobljeni dogodki: " + JSON.stringify(data));
				$.fn.parseEvents(data, $(".events_ul"));
			},
			error : function(xhr, textStatus, errorThrown) {
				alert("Napaka: " + JSON.stringify(xhr) + " " + xhr.status);
				alert(xhr.responseText);
			}
		});
	};
	
	$.fn.getEvents = function(url) {
		$.ajax({
			type : "GET",
			url : restUrl + url,
			dataType : "json",
			processData : false,
			success : function(data) {
				console.log("Pridobljeni dogodki: " + JSON.stringify(data));
				$.fn.parseEvents(data, $(".events_ul"));
			},
			error : function(xhr, textStatus, errorThrown) {
				alert("Napaka: " + JSON.stringify(xhr) + " " + xhr.status);
				alert(xhr.responseText);
			}
		});
	};
	
	$.fn.parseEvents = function(data, div) {
		div.empty();
		var jsonObj = null;
		if (data == null)
			return;
		jsonObj = (data.event instanceof Array ? data.event : data);
		events = jsonObj;
		$.each(jsonObj, function(i, item) {
			console.log("Element: " + JSON.stringify(item));
			$(div).prepend("<li><a href='#event' class='choseEvent' data-transition='slide' id='"+item.id+"'>"+item.description+"</a></li>");
//			$(div).append($('<li/>').append($('<a/>', {
//				'href' : '#beer',
//				'class' : 'choseBeer',
//				'id' : item.id,
//				'data-transition' : 'slide',
//				'text' : item.description
//			})));
		});
		$('.events_ul').trigger('create');
		$(".events_ul").listview('refresh');
		$.fn.bindChoseEvent();
	};
	
	// click listener when user chooses one event
	$.fn.bindChoseEvent = function() {
		$(".choseEvent").click(function() {
			chosenEvent = $(this).attr("id");
			if (eventData == null || eventData.id != chosenEvent) {
//				$.fn.bindLoadMore();
//				commentsPage = 1;
//				dialogMessage = false;
//				$(".allcomments").empty();
				$.fn.getEvent(chosenEvent);
			} else {
//				if (dialogMessage == false)
//					$.fn.bindLoadMore();
				$.fn.parseEventJson(eventData);
			}
		});
	};
	
	// get event by id
	$.fn.getEvent = function(eventId) {
		console.log("FETCHING EVENT: " + eventId);
		$.ajax({
			type : "GET",
			url : restUrl + "event/" + eventId,
			dataType : "json",
			processData : false,
			success : function(data) {
				displayedEventData = data;
				// Invited
				$.ajax({
					type : "GET",
					url : restUrl + "event/" + eventId + "/invited",
					dataType : "json",
					processData : false,
					success : function(dataInvited) {
						if (dataInvited != undefined) {
							displayedEventData.invited = dataInvited.user;
							console.log('Invited users: ' + JSON.stringify(dataInvited.user));
							$.fn.parseEventJsonInvited(dataInvited, $(".eventInvited"));
						} else {
							$.fn.parseEventJsonInvited(undefined, $(".eventInvited"));
						}
					},
					error : function(xhr, textStatus, errorThrown) {
						alert("Napaka: " + xhr + " " + xhr.status);
						alert(xhr.responseText);
					}
				});
				// Going
				$.ajax({
					type : "GET",
					url : restUrl + "event/" + eventId + "/going",
					dataType : "json",
					processData : false,
					success : function(dataGoing) {
						if (dataGoing != undefined) {
							displayedEventData.going = dataGoing.user;
							console.log('Goiung users: ' + JSON.stringify(dataGoing));
							$.fn.parseEventJsonGoing(dataGoing, $(".eventGoing"));
						} else {
							$.fn.parseEventJsonGoing(undefined, $(".eventGoing"));
						}
						
					},
					error : function(xhr, textStatus, errorThrown) {
						alert("Napaka: " + xhr + " " + xhr.status);
						alert(xhr.responseText);
					}
				});
				
				$.fn.parseEventJson(displayedEventData);
			},
			error : function(xhr, textStatus, errorThrown) {
				alert("Napaka: " + xhr + " " + xhr.status);
				alert(xhr.responseText);
			}
		});
	};
	
	// parse event
	$.fn.parseEventJson = function(jsonData) {
		eventData = jsonData;

		$(".eventDescription").text(eventData.description);
		$(".eventDescription").html(eventData.description);
		$(".eventHost").text(eventData.user.username + ' (' + eventData.user.name + ' ' + eventData.user.lastname + ')');
		console.log("Displaying event: " + JSON.stringify(displayedEventData));

//		var overallR = (parseInt(beerData.rateFlavour)
//				+ parseInt(beerData.rateAroma) + parseInt(beerData.rateAlcoholContent)) / 3;
//		$.fn.setRating($(".overallrating").children(":first"), Math
//				.round(overallR));
//		$.fn.setRating($("#aroma_rating"), jsonData.rateAroma);
//		$.fn.setRating($("#flavour_rating"), jsonData.rateFlavour);
//		$.fn.setRating($("#alcohol_content_rating"),
//				jsonData.rateAlcoholContent);

	};
	
	$.fn.parseEventJsonInvited = function(users, div) {
		var currentUser = window.sessionStorage.getItem("userId"), currentUserInvited = false;

		var data;		
		if (users.user instanceof Array) {
			data = users.user;
		} else {
			data = [];
			data.push(users.user);
		}

		
		div.empty();
		
		if (data != undefined) {
			$.each(data, function(i, item) {
				div.append("<li>"+item.username+"</li>");
				if (currentUser === item.id)
					currentUserInvited = true;
			});
		}
		
		if (currentUserInvited === true) {
			
		}
	};
	
	$.fn.parseEventJsonGoing = function(users, div) {

		var currentUser = window.sessionStorage.getItem("userId"), currentUserGoing = false;
		
		var data;		
		if (users.user instanceof Array) {
			data = users.user;
		} else {
			data = [];
			data.push(users.user);
		}

		div.empty();

		if (data != undefined) {
			$.each(data, function(i, item) {
				div.append("<li>"+item.username+"</li>");
				if (currentUser === item.id)
					currentUserGoing = true;
			});
		}

		if (currentUserGoing === 'false') {
			$('.eventParticipation').html("<a href=\"\" data-role=\"button\" data-mini=\"true\" id=\"buttonParticipate\">Participate</a>");
			$('.eventParticipation').trigger('create');
			
			$('#buttonParticipate').bind('click', $.fn.buttonClickParticipate);
		} else {
			$('.eventParticipation').html("<a href=\"\" data-role=\"button\" data-mini=\"true\" id=\"buttonParticipate\">Cancel participation</a>");
			$('.eventParticipation').trigger('create');
			
			$('#buttonParticipate').bind('click', $.fn.buttonClickCancelParticipate);
		}
		
	};
	
	$.fn.buttonClickParticipate = function() { 
		var currentUser = window.sessionStorage.getItem("userId");
		
		$.ajax({
			type : "POST",
			url : restUrl + "event/" + eventData.id + "/go",
			contentType : "application/json",
			processData : false,
			data : JSON.stringify({id : currentUser}),
			success : function(data) {

				if (data.result === 'true') {
					console.log("REQUEST SUCCESSFUL");
					$('#buttonParticipate').text('Cancel participation');
					$('.eventParticipation').trigger('create');
					
					$('#buttonParticipate').unbind();
					
					$('#buttonParticipate').bind('click', $.fn.buttonClickCancelParticipate);
				}

				
			},
			error : function(xhr, textStatus, errorThrown) {
				alert("Napaka: " + xhr + " " + xhr.status);
				alert(xhr.responseText);
			}
		});
	};
	
	$.fn.buttonClickCancelParticipate = function() { 
		var currentUser = window.sessionStorage.getItem("userId");
		
		$.ajax({
			type : "POST",
			url : restUrl + "event/" + eventData.id + "/go_cancel",
			contentType : "application/json",
			processData : false,
			data : JSON.stringify({id : currentUser}),
			success : function(data) {

				if (data.result === 'true') {
					$('#buttonParticipate').text('Participate');
					$('.eventParticipation').trigger('create');
					
					$('#buttonParticipate').unbind();
					
					$('#buttonParticipate').bind('click', $.fn.buttonClickParticipate);
				}

				
			},
			error : function(xhr, textStatus, errorThrown) {
				alert("Napaka: " + xhr + " " + xhr.status);
				alert(xhr.responseText);
			}
		});
		
	};
	
	$("#event").on("pageshow", function(event, ui) {
		$('#map_canvas').gmap('destroy');
		setTimeout(function() {
			$('#map_canvas').gmap().bind('init', function(ev, map) {
//				if (beerLocations != null) {
//					for ( var i = 0; i < beerLocations.length; i++) {
						locObj = displayedEventData;
						console.log("Lon: " + locObj.lon + " Lat: " + locObj.lat);
						$('#map_canvas').gmap('addMarker', {
							'position' : locObj.lat + "," + locObj.lon,
							'bounds' : true
						}).click(function() {
							$('#map_canvas').gmap('openInfoWindow', {
								'content' : locObj.description
							}, this);
						});
//					}
//				}
			});
		}, 200);
	});
	
});