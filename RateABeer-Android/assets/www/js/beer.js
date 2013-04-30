$(function() {
	$(document).bind("mobileinit", function() {
		// Make your jQuery Mobile framework configuration changes here!
		$.mobile.allowCrossDomainPages = true;
	});

	$(".choseBeer").click(function() {
		chosenBeer = $(this).attr("id");
		if (beerData == null || beerData.id != chosenBeer) {
			$.fn.getBeer(chosenBeer);
		} else {
			alert("isti je");
			$.fn.parseBeerJson(beerData);
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
	}
	
	$.fn.parseBeerJson = function(jsonData) {
		beerData = jsonData;
		$(".title").text(beerData.name);
		$(".descText").text(beerData.description);
		var overallR = (parseInt(beerData.rateFlavour) + parseInt(beerData.rateAroma) + parseInt(beerData.rateAlcoholContent))/3;
		$.fn.setRating($(".overallrating").children(":first"), Math.round(overallR));
		$.fn.setRating($("#aroma_rating"), jsonData.rateAroma);
		$.fn.setRating($("#flavour_rating"), jsonData.rateFlavour);
		$.fn.setRating($("#alcohol_content_rating"), jsonData.rateAlcoholContent);
		
	}
	
	$.fn.setRating = function(div,rating) {
		div.empty();
		for (var i = 0; i < rating; i++) {
			div.append("<img src='img/web/star_blank.png' />");
		}
		for (var j = 0; j < 5 - rating; j++) {
			div.append("<img src='img/web/star.png' />");
		}
	}

});