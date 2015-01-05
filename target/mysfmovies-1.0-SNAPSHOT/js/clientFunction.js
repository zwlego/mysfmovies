function clientFunction(){

	var query = getInput();

	/*
	$.ajax(
		{
		url:"search/movieServlet",
		type: 'POST',
		dataType: 'json',
		data:JSON.stringfy(query),
		contentType:'application/json',
		mimeType:'application/json',
		success: function(data){
			alert("sucess");
		},
		error: function(data,status,er){
			alert("error");
		}	
	});
	*/
	var ValidJSON = JSON.stringify(query); 
	$.post("sfmovies",
			ValidJSON,
			function(obj){
				if(obj.success=="true"){
					var jsonArray=obj.array;
					alert("going well!");
					postResult(jsonArray);
				}
				else{
					alert("no results");
				}
			},
			"json"
			);
	
	
//	$.post("movieServlet",
//			{"year_to":"1",
//			  "year_from":"2",
//			  "director":"3",
//			  "title":"4"
//			},
//	function(data){},
//	"json");

}

function getInput(){
	var form
	var input={};
	
	form=document.getElementById("searchform");
	for(var i=0;i<form.elements.length;i++){
		var key=form.elements[i].name;
		input[key] = form.elements[i].value;
	}
	
	return input;	
}

//function postMarkers(){
//	var cors=[[-122.4316177368164,37.786293029785156],
//	 [-122.40371704101562,37.77547073364258]
//	 ];
//	alert("inside");
//	var myLatlng = new google.maps.LatLng(37.786293029785156, -122.4316177368164);
//	var mapOptions = {
//  		center: myLatlng,
//  		zoom: 12,
//	};
//	var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
//	
//	var contentString = "This is !!!!!";
//	
//	var marker = new google.maps.Marker({
//		position: new google.maps.LatLng(37.786293029785156, -122.4316177368164),
//		map: map,
//		title:"first"
//	});
//	var marker_2=new google.maps.Marker({
//		position: new google.maps.LatLng(locations[1][1], locations[1][0]),
//		map: map,
//		title:"second"
//	});
//	var infowindow = new google.maps.InfoWindow({
//			content: contentString
//	});
//	google.maps.event.addListener(marker_2, 'click', function() {
//		infowindow.open(map,marker_2);
//		
//	});
//}

function postMarkers(){
	var cors=[[-122.4316177368164,37.786293029785156],
	     	 [-122.40371704101562,37.77547073364258]
	     	 ];
	
	var myLatlng = new google.maps.LatLng(37.786293029785156, -122.4316177368164);
	var mapOptions = {
  		center: myLatlng,
  		zoom: 12,
	};
	var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
	
	var infowindow = new google.maps.InfoWindow();
	var marker,i;
	for(i=0;i<cors.length;i++){
		marker=new google.maps.Marker({
			position: new google.maps.LatLng(cors[i][1],cors[i][0]),
			map: map,
			title: "num:"+i
		});
		google.maps.event.addListener(marker, 'click', (function(marker,i) {
			return function(){
				infowindow.setContent("place:"+i);
				infowindow.open(map,marker);
			}	
		})(marker,i));
	}
	
}
function postResult(objects){
	 var searchForm=document.getElementById("searchform");
	 var myElem = document.getElementById("resultDiv");
	 if (myElem != null) {
		 alert('does exist!');
		 myElem.parentNode.removeChild(Elem);
	 }
	 var firstbutton=document.getElementById("firstbutton");
	 firstbutton.innerHTML="Filter";
	 var items=createResult(objects);
	 var p=document.getElementById("panel");
	 p.style.overflow="auto";
	 p.appendChild(items);
	 postMarkers();
}

function createEntry(json){
	var entry = document.createElement("div");
	entry.style.position="relative";
	entry.style.left="10px";
	entry.style.width = "290px";
	entry.style.height = "100px";
	entry.style.color = "white";
	entry.style.borderBottom="1px solid white";
	
	entry.innerHTML = json.title;
	if(json.hasOwnProperty('release_year')){
		entry.innerHTML+=json.release_year;
	}
//	if(json.hasOwnProperty('latitude')){
//		postMarkers(json.latitude,json.lantitude);
//	}
	return entry;
}

function createResult(arrays){
		var result = document.createElement("div");
		result.setAttribute("id", "resultDiv");
		result.style.width="310px";
		result.style.height="450px";
//		result.style.overflow="auto";
		
		for(var j=0;j<arrays.length;j++){
			var objects=arrays[j];
			for(var i=0;i<objects.length;i++){
				var json=objects[i];
				var entry = createEntry(json);
				result.appendChild(entry);	
			}
		}
		return result;
}