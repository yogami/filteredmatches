
      $(document).ready(function(){

        var data = {};
        getMatches(data);
        
        $("#filter_button").click(function(){
           var data = {};
           renderHasPhotoFilter(data);
		   renderIsFavouriteFilter(data);
		   renderHasContactsExchangedFilter(data);
		   renderDistanceFilter(data);
		   renderCompatibilityFilter(data); 
		   renderAgeFilter(data);
		   renderHeightFilter(data);
		   console.log("JSON.stringify(data)="+JSON.stringify(data));
           getMatches(data);
        });
       
  });
  
  function renderHasPhotoFilter(data){
           var hasPhoto = $("input[name='photo_filter']:checked").val();
		   if(hasPhoto && hasPhoto != '')data["hasPhoto"] = hasPhoto
  }
  
  function renderIsFavouriteFilter(data){
  
           var isFavourite = $("input[name='favourite_filter']:checked").val();
		   if(isFavourite && isFavourite != '')data["isFavourite"] = isFavourite;
  }
  
  function renderHasContactsExchangedFilter(data){
           var hasContactsExchanged = $("input[name='contacts_exchanged_filter']:checked").val();
		   if(hasContactsExchanged && hasContactsExchanged != '')data["hasContactsExchanged"] = hasContactsExchanged;
		   
  }
  
  function renderCompatibilityFilter(data){
           var lowerLimitCompatibility = $("#compatibility_filter option:selected").val();
		   
		   if(lowerLimitCompatibility && lowerLimitCompatibility != '-1'){
			   data["lowerLimitCompatibility"] =lowerLimitCompatibility;
			   var upperLimitCompatibility = parseFloat(lowerLimitCompatibility) + 0.09;
			   data["upperLimitCompatibility"] = upperLimitCompatibility;
		   }
  }
  
  function renderDistanceFilter(data){
  
           data["distanceLimit"] = $("input[name='distance_filter']").val();
  }
  
  function renderAgeFilter(data){
           var lowerLimitAge = $("#age_filter option:selected").val();
		   
		   if(lowerLimitAge && lowerLimitAge != '-1'){
			   data["lowerLimitAge"] =lowerLimitAge;
			   var intValueOfLowerLimitAge = parseInt(lowerLimitAge);
			   var upperLimitAge = 0;
			   if(intValueOfLowerLimitAge != 90 ){
			       upperLimitAge = intValueOfLowerLimitAge + 7;
			   }
			   else{
			      upperLimitAge = intValueOfLowerLimitAge + 5;
			   }
			 
			   data["upperLimitAge"] = upperLimitAge;
		   }
		   
  }
  
  function renderHeightFilter(data){
           var lowerLimitHeight = $("#height_filter option:selected").val();
		   
		   if(lowerLimitHeight && lowerLimitHeight != '-1'){
			   data["lowerLimitHeight"] =lowerLimitHeight;
			   var intValueOfLowerLimitHeight = parseInt(lowerLimitHeight);
			   var upperLimitHeight = 0;
			   if(intValueOfLowerLimitHeight != 205 ){
			       upperLimitHeight = intValueOfLowerLimitHeight + 9;
			   }
			   else{
			      upperLimitHeight = intValueOfLowerLimitHeight + 5;
			   }
			 
			   data["upperLimitHeight"] = upperLimitHeight;
		   }
		   
  }
  
  function getMatches(data) {
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/api/matches/"+$("#id").val(),
			data : JSON.stringify(data),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
			
				console.log("SUCCESS: ", data);
				renderPage(data);
			},
			error : function(e) {
				console.log("ERROR: ", e);
				
			},
			done : function(e) {
				console.log("DONE");
			}
		});
	}
	function renderPage(data){
	     $("#matchResults").empty();
	     var resultsHtml = '';
	     $.each(data,function(){
	     //if(!this.main_photo || this.main_photo == ''){ this.main_photo="/images/no_photo_available.png";}
	     resultsHtml +=  "<div class=\"container\">"+
	      "<div class=\"row\">"+
	         
	         "<div class=\"col-sm-7\">"+
	            "<div class=\"card\">"+
	               
	               "<div class=\"card-body\">"+
	                 "<h4>"+this.display_name+"</h4>"+
                        "<p><strong>Job Title: </strong> "+this.job_title+ "</p>"+
                        "<p><strong>Age: </strong>"+ this.age +" years </p>"+
                        "<p><strong>Height: </strong>"+ this.height_in_cm+" cm </p>"+
                        "<p><strong>Distance: </strong>"+ this.distanceInKms +" km </p>"+
                        "<p><strong>City: </strong>"+ this.city_name +"  </p>"+
                        "<p><strong>Compatibility: </strong>"+ this.compatibility_score+" % </p>"+
                        "<p><strong>Messages Exchanged: </strong>"+ this.contacts_exchanged+ "  </p>"+
                        "<p><strong>Favorite: </strong>"+ this.favourite +" </p>"+
                        "<p><strong>Religion: </strong>"+ this.religion +" </p>"+
	               "</div>"+
	            "</div>"+
	         "</div>"+
	         "<div class=\"col-sm-5\">"+
	            "<div class=\"card\">"+
	               "<img src=\""+this.main_photo+"\" alt=\"\">"+
	               
	            "</div>"+
	         "</div>"+
	      "</div>"+
      "</div>"
      
      
	     
	     });
	     
	     $("#matchResults").html(resultsHtml);
	     
	}
	
	function updateDistanceText(val) {
          $("#distanceTextInput").val(val+" km"); 
    }

