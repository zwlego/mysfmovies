<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>

<link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
<meta charset="US-ASCII">
<title>SF Moives</title>
<!-- third party code -->
<title>Movie Search San Francisco</title>
<script src="js/jquery.1.9.1.min.js"></script>

<!-- own code here -->
<script src="js/clientFunction.js"></script>


<script type="text/javascript">
/* <input id="year_from" name="year_from" type="text" />
<input id="year_to" name="year_to" type="text" />
<input id="director" name="director" type="text" />
<input id="title" name="title" type="text" />
<button type="button" onclick="clientFunction()">Search</button>
 */
</script>

<!-- my page code -->

	
	<style type="text/css">
		html,body,#map-canvas { height: 100%; width: 100%;
						float:left;padding:0 px; margin:0px;
						}


	#panel{ 
			background: #808080;
			color:white;
			font-family: Arial;
			border: 1px solid balck;
			position: absolute;
			right:100px; 
			top: 10%;
			float:right;	
			width: 310px;
			height:500px;
	
	}
	#panel:hover{
	}
	#welcome{
	border-bottom: 1px white solid;
	font-family: "Lane";
	font-size: 40px;
	position: relative ;
	text-indent:65px;
	}
	#searchform{
		position: relative;
		top:6px;
	}
	label{
		float:left;
		width:90px;
		text-align:right;
		padding-right:8px;
	}
	#searchform>input{
		width:200px;
	}
   </style>
	
	<script type="text/javascript"
      src="http://maps.google.com/maps/api/js?sensor=false">
    </script>
	
	<script type="text/javascript"
      src="http://maps.google.com/maps/api/js?sensor=false">
    </script>
    
    

    <script type="text/javascript">
      function initialize() {
        var mapOptions = {
          center: { lat: 37.786293029785156, lng: -122.4316177368164},
          zoom: 12
        };
        var map = new google.maps.Map(document.getElementById('map-canvas'),
            mapOptions);
      }
      google.maps.event.addDomListener(window, 'load', initialize);    
	</script>
	
	<script type="text/javascript">
		
		
	</script>	
	
</head>


<body>
	<div id="map-canvas"></div>
	<div id="panel">
		<div id="welcome">SF Movies</div>
		
		<form id="searchform">
		
		<label for="title">Title:</label> 
		<input type="text" name="title" /> </br>
			
		<label for="year">Year</label> 
		<input type="text" name="year" /> </br>
			
		<label for="location">Location:</label> 
		<input type="text" name="location" /> </br>
		
		<label for="production">Production:</label> 
		<input type="text" name="production" /> </br>
		
		<label for="distributor">Distributor:</label> 
		<input type="text" name="distributor" /> </br>
		
		<label for="director">Director:</label> 
		<input type="text" name="director" /> </br>
			
		<label for="writer">Writer:</label> 
		<input type="text" name="writer" /> </br>
			
		<label for="actor">Actor:</label> 
		<input type="text" name="actor" /> </br>
		</form>
		
		<button id="firstbutton" onclick="clientFunction()">Search</button>
		
	</div>
</body>
</html>