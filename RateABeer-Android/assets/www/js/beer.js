$(function() {
	var beerLocations = null;
	$(document).bind("mobileinit", function() {
		// Make your jQuery Mobile framework configuration changes here!
		$.mobile.allowCrossDomainPages = true;
	});

	$.fn.getRecentBeers = function() {
		$.ajax({
			type : "GET",
			url : restUrl + "beer/last",
			dataType : "json",
			processData : false,
			success : function(data) {
				$.fn.parseBeers(data, $(".recent_beers"));
			},
			error : function(xhr, textStatus, errorThrown) {
				alert("Napaka: " + xhr + " " + xhr.status);
				alert(xhr.responseText);
			}
		});
	};

	$("#resetHref").click(function() {
		$("#commentInput").val('');
	});

	$("#beerLocations").click(function() {
		beerLocations = null;
		$('#map_canvas').gmap('destroy');
		$.ajax({
			type : "GET",
			url : restUrl + "location/beer/" + chosenBeer,
			dataType : "json",
			processData : false,
			success : function(data) {
				$.fn.parseBeerLocations(data);
			},
			error : function(xhr, textStatus, errorThrown) {
				alert("Napaka: " + xhr + " " + xhr.status);
				alert(xhr.responseText);
			}
		});
	});

	// event listener for search buttons
	$.fn.bindSearch = function(url, parseBeers) {
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
							parseBeers(data, $(".recent_beers"));
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

	$.fn.setButtonTheme = function(link, active) {
		var newTheme = (active ? "b" : "c");

		link.removeClass().addClass(
				"ui-btn ui-shadow ui-btn-corner-all ui-btn-hover-" + newTheme
						+ " ui-btn-up-" + newTheme + " ui-btn-up-" + newTheme);
		link.attr("data-theme", newTheme).trigger("mouseout");
	};

	$.fn.bindChoseBeer = function() {
		$(".choseBeer").click(function() {
			chosenBeer = $(this).attr("id");
			if (beerData == null || beerData.id != chosenBeer) {
				$.fn.bindLoadMore();
				commentsPage = 1;
				dialogMessage = false;
				$(".allcomments").empty();
				$.fn.getBeer(chosenBeer);
			} else {
				if (dialogMessage == false)
					$.fn.bindLoadMore();
				$.fn.parseBeerJson(beerData);
			}
		});
	};

	// click on desired location
	$.fn.bindChoseLocation = function() {
		$(".choseLocation").click(function() {
			chosenLocation = $(this).attr("id");

			// get beers on location
			$.ajax({
				type : "GET",
				url : restUrl + "location/beers/" + chosenLocation,
				dataType : "json",
				success : function(data) {
					$.fn.parseBeersOnSpecificLocations(data);
				},
				error : function(xhr, textStatus, errorThrown) {
					alert("Napaka: " + xhr + " " + xhr.status);
					alert(xhr.responseText);
				}
			});

		});
	};

	// parse locations (beers on location)
	$.fn.parseBeersOnSpecificLocations = function(data) {
		$(".beersOnLocation").empty();
		if (data == null)
			return;

		var jsonData = (data.location instanceof Array ? data.location : data);

		beerLocations = new Array();
		$.each(jsonData, function(i, item) {
			$("<div></div>").addClass("location " + item.lat + ";" + item.lon)
					.html(
							"<img src='img/web/beer-icon.png' /><div class='location-text'>"
									+ item.beer.name + " (Checked in by "
									+ item.user.username + ")</div>").appendTo(
							$(".beersOnLocation"));
			if (beerLocations.length == 0) {
				mapLocation = new Object();
				mapLocation.lat = item.lat;
				mapLocation.lon = item.lon;
				beerLocations.push(mapLocation);
			}
		});
	};

	// get beer by id
	$.fn.getBeer = function(beerId) {
		$.ajax({
			type : "GET",
			url : restUrl + "beer/" + beerId,
			dataType : "json",
			processData : false,
			success : function(data) {
				$.fn.parseBeerJson(data);
			},
			error : function(xhr, textStatus, errorThrown) {
				alert("Napaka: " + xhr + " " + xhr.status);
				alert(xhr.responseText);
			}
		});
	};

	// set click listener on "load more comments"
	$.fn.bindLoadMore = function() {
		$(".loadmore").click(function() {
			$.fn.getComments(chosenBeer);
		});
	};

	$("#commentshref").click(function() {
		$.fn.addCommentBind();
		$("#commentInput").val('');
		if (dialogMessage == false) {
			$.fn.getComments(chosenBeer);
		}
	});

	$.fn.getComments = function(beerId) {
		$.ajax({
			type : "GET",
			url : restUrl + "comment/beer/" + beerId,
			data : {
				page : commentsPage
			},
			dataType : "json",
			success : function(data) {
				commentsPage += 1;
				$.fn.parseCommentsJson(data);
			},
			error : function(xhr, textStatus, errorThrown) {
				alert("Napaka: " + xhr + " " + xhr.status);
				alert(xhr.responseText);
			}
		});
	};

	$.fn.parseCommentsJson = function(jsonData) {
		if (jsonData != null) {
			jsonData = (jsonData.comment instanceof Array ? jsonData.comment
					: jsonData);

			commentsData = jsonData;
			$.each(jsonData, function(i, item) {
				$.fn.returnCommentDiv(item).appendTo($(".allcomments")).hide()
						.slideDown();
			});
		} else {
			dialogMessage = true;
			$(".loadmore").unbind();
			$("#dialogText").html("No more comments to load");
			$("#dialogLink").trigger('click');
		}
	};

	$.fn.returnCommentDiv = function(item) {
		return $("<div></div>")
				.addClass("comment")
				.html(
						"<div class='commentimg'><img alt='' src='img/web/avatar_pic.png' /></div><div class='comment-datetime'>"
								+ item.author.username
								+ "<br> <span class='date'>"
								+ item.date
								+ "</span></div><div class='comment-content'>"
								+ item.comment + "</div>");
	};

	$.fn.parseBeerJson = function(jsonData) {
		beerData = jsonData;
		$(".title").text(beerData.name);
		$(".descText").text(beerData.description);
		var overallR = (parseInt(beerData.rateFlavour)
				+ parseInt(beerData.rateAroma) + parseInt(beerData.rateAlcoholContent)) / 3;
		$.fn.setRating($(".overallrating").children(":first"), Math
				.round(overallR));
		$.fn.setRating($("#aroma_rating"), jsonData.rateAroma);
		$.fn.setRating($("#flavour_rating"), jsonData.rateFlavour);
		$.fn.setRating($("#alcohol_content_rating"),
				jsonData.rateAlcoholContent);

	};

	$.fn.parseBeers = function(data, div) {
		div.empty();
		var jsonObj = null;
		if (data == null)
			return;
		jsonObj = (data.beer instanceof Array ? data.beer : data);

		$.each(jsonObj, function(i, item) {
			$("<div style='text-align: center'></div>").addClass("ui-block-a")
					.html("<img src='img/web/beer_pic_small.png' />").appendTo(
							div);
			$("<div></div>").addClass("ui-block-b people_name").html(
					"<a href='#beer' class='choseBeer' id=" + item.id + ">"
							+ item.name + "</a>").appendTo(div);
		});
		$.fn.bindChoseBeer();
	};

	// parse searched locations
	$.fn.parseLocations = function(data, div) {
		div.empty();
		var jsonObj = null;
		if (data == null)
			return;
		jsonObj = (data.location instanceof Array ? data.location : data);

		$.each(jsonObj,
				function(i, item) {
					$("<div style='text-align: center'></div>").addClass(
							"ui-block-a").html(
							"<img src='img/web/google-maps-icon.png' />")
							.appendTo(div);
					$("<div></div>").addClass("ui-block-b people_name").html(
							"<a href='#beersOnLocations' class='choseLocation' id="
									+ item.id + ">" + item.name + "</a>")
							.appendTo(div);
				});
		$.fn.bindChoseLocation();
	};

	$.fn.parseBeerLocations = function(data) {
		$(".allLocations").empty();
		if (data == null)
			return;

		var jsonData = (data.location instanceof Array ? data.location : data);

		beerLocations = new Array();
		$.each(jsonData, function(i, item) {
			$("<div></div>").addClass("location " + item.lat + ";" + item.lon)
					.html(
							"<img src='img/web/google-maps-icon.png' /><div class='location-text'>"
									+ item.name + " (Checked in by "
									+ item.user.username + ")</div>").appendTo(
							$(".allLocations"));

			mapLocation = new Object();
			mapLocation.lat = item.lat;
			mapLocation.lon = item.lon;
			mapLocation.info = item.user.username;
			beerLocations.push(mapLocation);
		});
	};

	// google maps + set markers
	$.fn.setMaps = function() {
		setTimeout(function() {
			$('#map_canvas').gmap().bind('init', function(ev, map) {
				if (beerLocations != null) {
					for ( var i = 0; i < beerLocations.length; i++) {
						locObj = beerLocations[i];

						$('#map_canvas').gmap('addMarker', {
							'position' : locObj.lat + "," + locObj.lon,
							'bounds' : true
						}).click(function() {
							$('#map_canvas').gmap('openInfoWindow', {
								'content' : locObj.info
							}, this);
						});
					}
					beerLocations = null;
				}
			});
		}, 200);
	};

	$("#mapsPage").on("pageshow", function(event, ui) {
		setTimeout(function() {
			$('#map_canvas').gmap().bind('init', function(ev, map) {
				if (beerLocations != null) {
					for ( var i = 0; i < beerLocations.length; i++) {
						locObj = beerLocations[i];

						$('#map_canvas').gmap('addMarker', {
							'position' : locObj.lat + "," + locObj.lon,
							'bounds' : true
						}).click(function() {
							$('#map_canvas').gmap('openInfoWindow', {
								'content' : locObj.user
							}, this);
						});
					}
				}
			});
		}, 200);
	});
	
	$(".locationMaps").click(function() {
		$('#map_canvas').gmap('destroy');
	});

	// add new comment
	$.fn.addCommentBind = function() {
		$("#sendCommentHref").unbind().click(
				function() {
					var val = $("#commentInput").val();
					if (val.length < 5) {
						$("#dialogText").html("Content is not long enough!");
						$("#dialogLink").trigger('click');
						return;
					}
					$(this).unbind();
					var comObj = new Object();
					var dateObj = new Date();
					var dateStr = dateObj.getFullYear() + "-"
							+ parseInt(dateObj.getMonth() + 1) + "-"
							+ dateObj.getDate() + " " + dateObj.getHours()
							+ ":" + dateObj.getMinutes() + ":"
							+ dateObj.getSeconds();
					comObj.author = new Object();
					comObj.author.username = "testni";
					comObj.author.id = "1";
					comObj.comment = val;
					comObj.date = dateStr;
					var jsonObj = JSON.stringify(comObj);
					var div = $.fn.returnCommentDiv(comObj);
					$(".allcomments").prepend(div);
					$.ajax({
						type : "POST",
						contentType : "application/json",
						url : restUrl + "comment/newcomment/" + chosenBeer,
						processData : false,
						data : jsonObj
					}).done(function() {
						$(".allcomments").prepend(div);
					});
				});
	};

	// change search buttons style
	$("#beersHref").click(function() {
		$.fn.setButtonTheme($(this), true);
		$.fn.setButtonTheme($("#locationsHref"), false);
		$.fn.setButtonTheme($("#recentBeersLink"), false);
		$(".search-input").unbind();
		$.fn.bindSearch("beer/search", $.fn.parseBeers);
	});
	$("#locationsHref").click(function() {
		$.fn.setButtonTheme($(this), true);
		$.fn.setButtonTheme($("#beersHref"), false);
		$.fn.setButtonTheme($("#recentBeersLink"), false);
		$(".search-input").unbind();
		$.fn.bindSearch("location/search/?limit=1", $.fn.parseLocations);
	});
	$("#recentBeersLink").click(function() {
		$.fn.setButtonTheme($(this), true);
		$.fn.setButtonTheme($("#locationsHref"), false);
		$.fn.setButtonTheme($("#beersHref"), false);
		$.fn.getRecentBeers();
	});

	// print rating stars
	$.fn.setRating = function(div, rating) {
		div.empty();
		for ( var i = 0; i < rating; i++) {
			div.append("<img src='img/web/star_blank.png' />");
		}
		for ( var j = 0; j < 5 - rating; j++) {
			div.append("<img src='img/web/star.png' />");
		}
	};

});