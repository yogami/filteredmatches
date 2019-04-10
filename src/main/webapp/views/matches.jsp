<!DOCTYPE html>
<html lang="en">
   <head>
      <title>Matches</title>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" href="/css/matches.css">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
      <link rel="stylesheet" href="https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css"  media="screen">
      <script src="https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
      <script>
      $(document).ready(function(){

        var data = {};
        getMatches(data);
        
        $("#filter_button").click(function(){
           var data = {};
		   data["hasPhoto"] = $("input[name='photo_filter']:checked").val();
		   data["isFavourite"] = $("input[name='favourite_filter']:checked").val();
		   data["hasContactsExchanged"] = $("input[name='contacts_exchanged_filter']:checked").val();
		   data["distanceLimit"] = $("input[name='distance_filter']").val();
		   var lowerLimitCompatibility = $("#compatibility_filter option:selected").val();
		   
		   if(lowerLimitCompatibility && lowerLimitCompatibility != '-1'){
			   data["lowerLimitCompatibility"] =lowerLimitCompatibility;
			   var upperLimitCompatibility = parseFloat(lowerLimitCompatibility) + 0.09;
			   data["upperLimitCompatibility"] = upperLimitCompatibility;
		   }
		   
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
		   
		   console.log("JSON.stringify(data)="+JSON.stringify(data));
           getMatches(data);
        });
       
  });
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
	     $("#filteredResults").empty();
	     var resultsHtml = '';
	     $.each(data,function(){
	     var mainPhoto = (this.main_photo == '')? "<img src=\"/images/no_photo_available.png\" alt=\"\" class=\"img-circle img-responsive\">" : "<img src=\""+this.main_photo+"\" alt=\"\" class=\"img-circle img-responsive\">";
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
</script>
   </head>
   <body>
   <input type="hidden"id="id" name="id" value="${id}"/>
      <div class="jumbotron  sticky-top" >
         <div class="container">
               <div class="row">
                      <div class="col-sm-3">
                          
	                      <div class="radio">
								  <label><input type="radio" name="photo_filter" value="yes"> Has photo</label>
						  </div>
						  <div class="radio">
								  <label><input type="radio" name="photo_filter" value="no"> Has no photo</label>
						  </div>
						  <div class="radio">
								  <label><input type="radio" name="photo_filter" value="" checked> Doesn't Matter</label>
						  </div>
	                   </div>
	                   <div class="col-sm-3">
                          
	                      <div class="radio">
								  <label><input type="radio" name="favourite_filter" value="yes"> Favourite</label>
						  </div>
						  <div class="radio">
								  <label><input type="radio" name="favourite_filter" value="no"> Not Favourite</label>
						  </div>
						  <div class="radio">
								  <label><input type="radio" name="favourite_filter" value="" checked> Doesn't Matter</label>
						  </div>
	                   </div>
	                    <div class="col-sm-3">
                          
	                      <div class="radio">
								  <label><input type="radio" name="contacts_exchanged_filter" value="yes"> In Contact</label>
						  </div>
						  <div class="radio">
								  <label><input type="radio" name="contacts_exchanged_filter" value="no"> Not In Contact</label>
						  </div>
						  <div class="radio">
								  <label><input type="radio" name="contacts_exchanged_filter" value="" checked> Doesn't Matter</label>
						  </div>
	                   </div>
	                   <div class="col-lg-3">
	                      <div class"container">
	                             <div class="row">
	                                 <div class="col-sm-12 text-left">
	                                  Distance within
	                                 </div>
	                             </div>
		                         <div class="row">
		                            <div class="col-sm-12">
				                       <div class="d-flex justify-content-left my-4">
										 
										    <span class="font-weight-bold indigo-text mr-2 mt-1">30 km</span>
										
										    <input name="distance_filter" class="border-0" type="range" min="30" max="300" data-show-value="true" 
										    onchange="updateDistanceText(this.value)" > 
										   
										  
										  <span class="font-weight-bold indigo-text ml-2 mt-1">300 km</span>
										   
										</div>
								    </div>
								 </div>
								 <div class="row">
	                                 <div class="col-sm-12 text-left">
	                                      <input type="text" id="distanceTextInput" value="" readonly size="6">
	                                 </div>
	                             </div>
						  </div>
	                   </div>
	           </div>
	           <div class="row">
	                   <div class="col-sm-12">
	                   <br/>
	                   </div>
	           </div>
	           <div class="row"> 
	                   
	                   <div class="col-sm-4">
	                      <label>Compatibility:</label>
							<select id="compatibility_filter"class="form-control" name="compatibility_filter">
							    <option value="-1">doesn't matter</option>
							    <option value="0.01">1 to 9%</option>
							    <option value="0.1">10 to 29%</option>
							    <option value="0.2">20 to 30%</option>
							    <option value="0.3">30 to 39%</option>
							    <option value="0.4">40 to 49%</option>
							    <option value="0.5">50 to 59%</option>
							    <option value="0.6">60 to 69%</option>
							    <option value="0.7">70 to 79%</option>
							    <option value="0.8">80 to 89%</option>
							    <option value="0.9">90 to 99%</option>
							   
							</select> 
	                   </div>
	                   <div class="col-sm-4">
	                      <label>Age:</label>
							<select id="age_filter"class="form-control" name="age_filter">
							    <option value="-1">doesn't matter</option>
							    <option value="18">18 to 25 years</option>
							    <option value="26">26 to 33 years</option>
							    <option value="34">34 to 41 years</option>
							    <option value="42">42 to 49 years</option>
							    <option value="50">50 to 57 years</option>
							    <option value="58">58 to 65 years</option>
							    <option value="66">66 to 73 years</option>
							    <option value="74">74 to 81 years</option>
							    <option value="82">82 to 89 years</option>
							    <option value="90">90 to 95 years</option>
							    
							   
							</select> 
	                   </div>
	                    <div class="col-sm-4">
	                      <label>Height:</label>
							<select id="height_filter"class="form-control" name="height_filter">
							    <option value="-1">doesn't matter</option>
							    <option value="135">135 to 144 cm</option>
							    <option value="145">145 to 154 cm</option>
							    <option value="155">155 to 164 cm</option>
							    <option value="165">165 to 174 cm</option>
							    <option value="175">175 to 184 cm</option>
							    <option value="185">185 to 194 cm</option>
							    <option value="195">195 to 204 cm</option>
							    <option value="205">205 to 210 cm</option>
							    
							    
							   
							</select> 
	                   </div>
	                   
	                   
	                 
	           </div>
	            <div class="row">
	                   <div class="col-sm-12">
	                   <br/>
	                   </div>
	           </div>
               <div class="row">
                       <div class="col-sm-5">
                       </div>
                       <div id="filter_button" class="col-sm-2">
                           <button name="filter" type="button" class="btn btn-primary btn-lg">Search</button>
                       </div>
                       <div class="col-sm-5">
                       </div>
               </div>
         </div>
      </div>
      
      <div id="matchResults">
	     
      </div>
      
   </body>
</html>