$(function() {
	$(document).bind("mobileinit", function() {
		// Make your jQuery Mobile framework configuration changes here!
		$.mobile.allowCrossDomainPages = true;
	});

	$("#recentBeersLink").click(function() {
		$.fn.getRecentBeers();
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
					url : restUrl + "beer/search/",
					dataType : "json",
					data : {
						param : val
					},
					success : function(data) {
						$.fn.parseBeers(data,$(".recent_beers"));
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

	$.fn.bindLoadMore = function() {
		$(".loadmore").click(function() {
			$.fn.getComments(chosenBeer);
		});
	};

	$("#commentshref").click(function() {
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
			commentsData = jsonData;
			$
					.each(
							jsonData.comment,
							function(i, item) {
								$("<div></div>")
										.addClass("comment")
										.html(
												"<div class='commentimg'><img alt='' src='img/web/avatar_pic.png' /></div><div class='comment-datetime'>"
														+ item.author.username
														+ "<br> <span class='date'>"
														+ item.date
														+ "</span></div><div class='comment-content'>"
														+ item.comment
														+ "</div>").hide()
										.appendTo($(".allcomments"))
										.slideDown();
							});
		} else {
			dialogMessage = true;
			$("#dialogText").html("Ni vec komentarjev za prikaz");
			$("#dialogLink").click();
			$(".loadmore").unbind();
		}
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
		if (data == null) return;
		if (data.beer.length > 0) {
			jsonObj = data.beer;
		} else if (data.beer.length == undefined) {
			jsonObj = data;
		}
	
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