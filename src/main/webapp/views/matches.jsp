<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

<link href="${pageContext.request.contextPath}/css/matches.css" rel="stylesheet" type="text/css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$(document).ready(function(){
        $("input[name='photo_filter']").change(function(){
            getMatches();
        });
       
    });
function getMatches() {
		var data = {}
		data["has_photo"] = $("input[name='photo_filter']:checked").val();
		
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/api/matches/1",
			data : JSON.stringify(data),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("SUCCESS: ", data);
				//alert(data);
			},
			error : function(e) {
				console.log("ERROR: ", e);
				//display(e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
	}
</script>
<!------ Include the above in your HEAD tag ---------->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
   <title>My Matches</title>
</head>
<body>
   <div  style="position : fixed;  height: 100%; width: 20%; float:left">FILTER MATCHES BY<br/><br/><br/>
   <!-- HTML SECTION  -->
 
        <b>HAS PHOTO:</b><br/>
		<label for="radio-one">
		<input type="radio" value="radio-one" name="photo_filter" id="radio-one"> <span>yes</span>
		</label>
		<br/>
		 
		<label for="radio-two">
		<input type="radio" value="radio-two" name="photo_filter" id="radio-two"> <span>no</span>
		</label>
		 <br/>
		<label for="radio-three">
		<input type="radio" value="radio-three"  name="photo_filter" id="radio-three" checked> <span>doesn't matter</span>
		</label>
   </div>
   <div class="filteredClass" style="  height: 100%; width: 80%; float:right">
   <c:forEach var="user" items="${users}">
      <link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" rel="stylesheet">
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