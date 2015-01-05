package com.mysfmovies;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;
import com.google.code.geocoder.model.LatLng;





import java.io.IOException;
import java.util.HashMap;


public class LocSearch{
	protected static HashMap<String, float[]> geoMaps = new HashMap<String,float[]>();
	private String citySuffix = "";
	
	
	
	//set suffix
	public void setCitySuffix(String city, String state){
		this.citySuffix = " " + city + state;
	}
	// get coordates
	public float[] getGeoLoc(String addr){
		addr += citySuffix;
		if(!geoMaps.containsKey(addr)){
			geoMaps.put(addr,getCoord(addr));
		}
		return geoMaps.get(addr);
	}
        
        
	public float[] getCoord(String addr){
            
		Geocoder geocoder = new Geocoder();		
	    float[] coords = new float[2];
		if (addr  == null)  
			     return null;
			       
		GeocoderRequest geocoderReq  = new GeocoderRequestBuilder()
			       .setAddress(addr).setLanguage("en").getGeocoderRequest();
		GeocodeResponse geocoderResp;
			    geocoderResp = geocoder.geocode(geocoderReq);
			    if (geocoderResp.getStatus() == GeocoderStatus.OK
			      & !geocoderResp.getResults().isEmpty()) 
                {
			      GeocoderResult geocoderResult =  
			    		  geocoderResp.getResults().iterator().next();
			      LatLng loc = geocoderResult.getGeometry().getLocation();
			      coords[0] = loc.getLat().floatValue();
			      coords[1] = loc.getLng().floatValue();
			      return coords;
                }
    

                     
			  return null;
	}
        
	public float getLatitude(float[] coord ){
		return coord[0];
	}
	public float getLantitude(float[] coord){
		return coord[1];
	}
}
