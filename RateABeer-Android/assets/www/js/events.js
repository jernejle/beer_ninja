$(document).ready(function() {
	
	var chosenEvent, eventData;
	
	//document.addEventListener("deviceready", onDeviceReady, false);

	// change search buttons style
	$("#allEventsLink").click(function() {
		$.fn.setButtonTheme($(this), true);
		$.fn.setButtonTheme($("#invitedEventsLink"), false);
		$.fn.setButtonTheme($("#goingEventsLink"), false);
		$(".search-input").unbind();
		$.fn.getAllEvents();
		//$.fn.bindSearch("event/search", $.fn.parseEvents);
	});
	$("#invitedEventsLink").click(function() {
		$.fn.setButtonTheme($(this), true);
		$.fn.setButtonTheme($("#allEventsLink"), false);
		$.fn.setButtonTheme($("#goingEventsLink"), false);
		$(".search-input").unbind();
		//$.fn.bindSearch("location/search/", $.fn.parseLocations);
	});
	$("#goingEventsLink").click(function() {
		$.fn.setButtonTheme($(this), true);
		$.fn.setButtonTheme($("#allEventsLink"), false);
		$.fn.setButtonTheme($("#invitedEventsLink"), false);
		
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
							parseEvents(data, $("..events_ul"));
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
	
	$.fn.parseEvents = function(data, div) {
		div.empty();
		var jsonObj = null;
		if (data == null)
			return;
		jsonObj = (data.event instanceof Array ? data.event : data);

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
				$.fn.parseBeerJson(eventData);
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
				$.fn.parseEventJson(data);
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
		console.log("EVENT DESCRIPTION: " + eventData.description);
		$(".eventDescription").text(eventData.description);
		$(".eventDescription").html(eventData.description);
		$(".eventHost").text(eventData.user.username + ' (' + eventData.user.name + ' ' + eventData.user.lastname + ')');

//		var overallR = (parseInt(beerData.rateFlavour)
//				+ parseInt(beerData.rateAroma) + parseInt(beerData.rateAlcoholContent)) / 3;
//		$.fn.setRating($(".overallrating").children(":first"), Math
//				.round(overallR));
//		$.fn.setRating($("#aroma_rating"), jsonData.rateAroma);
//		$.fn.setRating($("#flavour_rating"), jsonData.rateFlavour);
//		$.fn.setRating($("#alcohol_content_rating"),
//				jsonData.rateAlcoholContent);

	};
	
});