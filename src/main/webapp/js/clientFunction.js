var markers = [];
function clientFunction(){
	var query = getInput();
	var clientFunction={};
	clientFunction["query"]=query;
	clientFunction["data"]="";
	clientFunction["searchType"]="search";
 
	var ValidJSON = JSON.stringify(clientFunction); 

	$.post("sfmovies",
			ValidJSON,
			function(obj){
				if(obj.success=="true"){
					postResult(obj.array);
				}
				else{
					alert("sorry,no match");
				}
			},
			"json"
			);
}

function filterFunction(array){
	var query = getInput();
	var data = {};
	data["rawResult"]=array;
	var clientFunction={};
	clientFunction["query"]=query;
	clientFunction["data"]=JSON.stringify(data);
	clientFunction["searchType"]="filter";
	
	
	var ValidJSON = JSON.stringify(clientFunction); 

	$.post("sfmovies",
			ValidJSON,
			function(obj){
				if(obj.success=="true"){
					postResult(obj.array);
				}
				else{
					alert("sorry,no match");
				}
			},
			"json"
			);
}

function getInput(){
	var form
	var input={};
	form=document.getElementById("searchForm");
	for(var i=0;i<form.elements.length;i++){
		var key=form.elements[i].name;
		input[key] = form.elements[i].value;
	}
	return input;	
}

function postMarkers(cinemas){
	var myLatlng = new google.maps.LatLng(37.786293029785156, -122.4316177368164);
	var mapOptions = {
  		center: myLatlng,
  		zoom: 12,
	};
	var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
	var infowindow = new google.maps.InfoWindow();
    var marker;
      for (var i = 0; i < cinemas.length; i++) {  
        marker = new google.maps.Marker({
          position: new google.maps.LatLng(cinemas[i].latitude, cinemas[i].lantitude),
          map: map,
        });
        google.maps.event.addListener(marker, 'click', (function(marker, i) {
          return function() {
            infowindow.setContent(cinemas[i].title+": "+cinemas[i].locations);
            infowindow.open(map, marker);
          }
        })(marker, i));
        markers.push(marker);
      }
}

function postResult(objects){
	 var parent=document.getElementById("panel");
	 var result=document.getElementById("resultDiv");
	 var filter=document.getElementById("filterButton");
	 var searchButton=document.getElementById("searchButton");
	 if (result != null) {
		 markers=[];
		 parent.removeChild(result);
		 parent.removeChild(filter);
	 }
	 searchButton.innerHTML="Search Again";
	 var newButton = document.createElement("button");
	 newButton.setAttribute("id", "filterButton");
	 newButton.innerHTML = "Search in Results";
	 newButton.onclick=function(){
		 return filterFunction(objects)
		 };
	 newButton.style.position="relative";
	 newButton.style.top="10px";
	 var items=createResult(objects);
	 parent.style.overflow="auto";
	 parent.appendChild(newButton);
	 parent.appendChild(items);
	 postMarkers(objects);
}

function createEntry(json,order){
	var entry = document.createElement("div");
	entry.title=order;
	entry.style.position="relative";
	entry.style.left="10px";
	entry.style.width = "290px";
	entry.style.overflow = "hidden" 
	entry.style.color = "white";
	entry.style.borderBottom="1px solid white";
	entry.innerHTML = json.title;
	var content="";
	if(json.hasOwnProperty('release_year')){
		content+=" ,"+ json.release_year;
	}
	if(json.hasOwnProperty('actor_1')){
		content+="<br/>"+"Actors:"+json.actor_1;
	}
	if(json.hasOwnProperty('actor_2')){
		content+=","+json.actor_2;
	}
	if(json.hasOwnProperty('actor_3')){
		content+=","+json.actor_3;
	}
	if(json.hasOwnProperty('director')){
		content+="<br/>"+"Director:"+json.director;
	}
	if(json.hasOwnProperty('locations')){
		content+="<br/>"+"Location:"+json.locations;
	}
	if(json.hasOwnProperty('production_company')){
			content+="<br/>"+"Production:"+json.production_company;
	}
	if(json.hasOwnProperty('writer')){
			content+="<br/>"+"Writer:"+json.writer;
	}
	if(json.hasOwnProperty('distributor')){
		content+="<br/>"+"Distributor:"+json.distributor;
	}
	entry.innerHTML+=content;
	return entry;
}

function myClick(id){
    google.maps.event.trigger(markers[id], 'click');
}


function createResult(arrays){
		var result = document.createElement("div");
		result.setAttribute("id", "resultDiv");
		result.style.width="310px";
		result.style.height="250px";
		result.style.position="relative";
		result.style.top="16px";
		for(var i=0;i<arrays.length;i++){
			var json=arrays[i];
			var entry = createEntry(json,i);
			entry.onclick=function(){
			 return myClick(this.title);	
			}	
			result.appendChild(entry);	
		}
		return result;
}