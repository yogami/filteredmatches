<!DOCTYPE html>
<html lang="en">
   <head>
      <title>Matches</title>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
      <script>
$(document).ready(function(){

        var data = {};
        getMatches(data);
        
        $("#filter_button").click(function(){
           var data = {};
		   data["hasPhoto"] = $("input[name='photo_filter']:checked").val();
		   data["isFavourite"] = $("input[name='favourite_filter']:checked").val();
		   data["hasContactsExchanged"] = $("input[name='contacts_exchanged_filter']:checked").val();
		   data["distanceLimit"] = $("input[name='distance_range_filter']").val();
		   
		   
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
			console.log($("#id").val());
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
</script>
   </head>
   <body>
   <input type="hidden"id="id" name="id" value="${id}"/>
      <div class="jumbotron  sticky-top" >
         <div class="container">
               <div class="row">
                      <div class="col-sm-4">
                          
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
	                   <div class="col-sm-4">
                          
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
	                    <div class="col-sm-4">
                          
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
	           </div>
	           <div class="row"> 
	                   <div class="col-sm-12">
                          
	                      
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