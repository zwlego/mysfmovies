function clientFunction(){

	var query = getInput();
	
	var ValidJSON = JSON.stringify(query); 
	$.post("sfmovies",
			ValidJSON,
			function(obj){
				if(obj.success=="true"){
					var jsonArray=obj.array;
					postResult(jsonArray);
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
	form=document.getElementById("searchform");
	for(var i=0;i<form.elements.length;i++){
		var key=form.elements[i].name;
		input[key] = form.elements[i].value;
	}
	return input;	
}



function postMarkers(arrays){
	for(var j=0;j<arrays.length;j++){
		var objects=arrays[j];
		for(var i=0;i<objects.length;i++){
			var json=objects[i];	
		}
	}
	var myLatlng = new google.maps.LatLng(37.786293029785156, -122.4316177368164);
	var mapOptions = {
  		center: myLatlng,
  		zoom: 12,
	};
	var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);
	var infowindow = new google.maps.InfoWindow();
	var marker,i;
	for(var j=0;j<arrays.length;j++){
		var objects=arrays[j];
		for(var i=0;i<objects.length;i++){
			var json=objects[i];	
			if(json.hasOwnProperty("latitude")){
				marker=new google.maps.Marker({
					position: new google.maps.LatLng(json.latitude,json.lantitude),
					map: map,
					title: "num:"+i
				});
				google.maps.event.addListener(marker, 'click', (function(marker,i) {
					return function(){
						var content="";
						if(json.hasOwnProperty("locations")){
							content+=json.locations;
						}
						infowindow.setContent(json.title+": "+content);
						infowindow.open(map,marker);
					}	
				})(marker,i));
				
			}
			
		}
	}
}
function postResult(objects){
	 var searchForm=document.getElementById("searchform");
	 var myElem = document.getElementById("resultDiv");
	 if (myElem != null) {
		 alert('does exist!');
		 myElem.parentNode.removeChild(Elem);
	 }
	 var items=createResult(objects);
	 var p=document.getElementById("panel");
	 p.style.overflow="auto";
	 p.appendChild(items);
	 postMarkers(objects);
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
	var content=""
	if(json.hasOwnProperty('release_year')){
		content+=" ,"+ json.release_year;
	}
	if(json.hasOwnProperty('actor_1')){
		content+="<br/>"+"Actors:"+json.actor_1;
	}
	if(json.hasOwnProperty('actor_2')){
		content+=","+json.actor_2;
	}
	if(json.hasOwnProperty('director')){
		content+="<br/>"+"Director:"+json.director;
	}
	if(json.hasOwnProperty('locations')){
		content+="<br/>"+"Locations:"+json.locations;
	}
	entry.innerHTML+=content;
	return entry;
}

function createResult(arrays){
		var result = document.createElement("div");
		result.setAttribute("id", "resultDiv");
		result.style.width="310px";
		result.style.height="450px";
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