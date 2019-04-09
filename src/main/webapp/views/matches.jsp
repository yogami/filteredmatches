<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/matches.css" rel="stylesheet" type="text/css">
<!------ Include the above in your HEAD tag ---------->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
   <title>My Matches</title>
</head>
<body>
   <div style="position : fixed;  height: 100%; width: 20%; float:left">SOMETHING GOES HERE<br/></div>
   <div id="filteredResults" style="height : 100%; width: 80%; float:right;">
   <c:forEach var="user" items="${users}">
      <link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css" rel="stylesheet">
      <div class="container" style="width: 650px;">
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
                           <img src="${user.main_photo}" alt="" class="img-circle img-responsive">
                           
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