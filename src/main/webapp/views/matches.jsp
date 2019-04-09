<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
 
<link href="${pageContext.request.contextPath}/css/matches.css" rel="stylesheet" type="text/css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$(document).ready(function(){

        var data = {};
        getMatches(data);
        
        $("input[name='photo_filter']").change(function(){
           var data = {};
		   data["hasPhoto"] = $("input[name='photo_filter']:checked").val();
		   
		   console.log("data[\"hasPhoto\"] = "+data["hasPhoto"]);
		   console.log("data="+data);
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
	     resultsHtml += "<div class=\"container\">"+
         "<div class=\"row\">"+
            "<div class=\"col-md-offset-2 col-md-8 col-lg-offset-3 col-lg-6\">"+
               "<div class=\"well profile\">"+
                  "<div class=\"col-sm-12\">"+
                     "<div class=\"col-xs-12 col-sm-8\">"+
                        "<h2>"+this.display_name+"</h2>"+
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
                     "<div class=\"col-xs-12 col-sm-4 text-center\">"+
                        "<figure>"+mainPhoto+
                          
                        
                        "</figure>"+
                     "</div>"+
                  "</div>"+
                  "<div class=\"col-xs-12 divider text-center\">"+
                    
                  "</div>"+
               "</div>"+
            "</div>"+
         "</div>"+
      "</div>";
      
	     
	     });
	     
	     $("#filteredResults").html(resultsHtml);
	     
	}
</script>
<!------ Include the above in your HEAD tag ---------->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
   <title>My Matches</title>
</head>
<body>
<input type="hidden"id="id" name="id" value="${id}"/>
   <div id="filter" style="position : fixed;  height: 100%; width: 20%; float:left">FILTER MATCHES BY<br/><br/><br/>
   <!-- HTML SECTION  -->
 
        <b>HAS PHOTO:</b><br/>
		<label for="radio-one">
		<input type="radio" value="yes" name="photo_filter" id="radio-one"> <span>yes</span>
		</label>
		<br/>
		 
		<label for="radio-two">
		<input type="radio" value="no" name="photo_filter" id="radio-two"> <span>no</span>
		</label>
		 <br/>
		<label for="radio-three">
		<input type="radio" value=""  name="photo_filter" id="radio-three" checked> <span>doesn't matter</span>
		</label>
   </div>
   <div id="filteredResults" class="container" style="  height: 100%; width: 80%; float:right">
   
   <c:forEach var="user" items="${users}">
     
      <div class="container">
         <div class="row">
            <div class="col-md-offset-2 col-md-8 col-lg-offset-3 col-lg-6">
               <div class="well profile">
                  <div class="col-sm-12">
                     <div class="col-xs-12 col-sm-8">
                        <h2>${user.display_name}</h2>
                        <p><strong>Job Title: </strong> ${user.job_title} </p>
                        <p><strong>Age: </strong> ${user.age} years </p>
                        <p><strong>Height: </strong> ${user.height_in_cm} cm </p>
                        <p><strong>Distance: </strong> ${user.distanceInKms} km </p>
                        <p><strong>City: </strong> ${user.city.name}  </p>
                        <p><strong>Compatibility: </strong> ${user.compatibilityPercentage} % </p>
                        <p><strong>Messages Exchanged: </strong> ${user.contacts_exchanged}  </p>
                        <p><strong>Favorite: </strong> ${user.favourite}  </p>
                        <p><strong>Religion: </strong> ${user.religion}  </p>
                       
                     </div>
                     <div class="col-xs-12 col-sm-4 text-center">
                        <figure>
                        <c:choose>
                           <c:when test="${ empty user.main_photo}">
						      <img src="${pageContext.request.contextPath}/images/no_photo_available.png" alt="" class="img-circle img-responsive">
						   </c:when>
						   <c:otherwise>
                              <img src="${user.main_photo}" alt="" class="img-circle img-responsive">
                           </c:otherwise>
                        </c:choose>   
                        </figure>
                     </div>
                  </div>
                  <div class="col-xs-12 divider text-center">
                    
                  </div>
               </div>
            </div>
         </div>
      </div>
   </c:forEach>
   </div>
</body>
</html>