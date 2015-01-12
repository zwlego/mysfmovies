package com.mysfmovies;

import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapCoord {
	LocSearch LOC=new LocSearch();
	MapCoord(){
		LOC.setCitySuffix("San Francisco", "CA");
	}
	public HashMap<String,float[] > getCoord(JSONArray json){
		HashMap<String,float[] > map=new HashMap<String,float[]>();
		boolean flag=true;
		System.out.println("update start");
		int k=50;
		for(int i=0;i<json.length();i++){
			try {
				if(i>k){
				    k+=30;
				}
			    JSONObject temp=json.getJSONObject(i);
				if(temp.has("locations")){
					String location=(String)temp.get("locations");
					if(!map.containsKey(location)){
						float[] cords=locate(location);
				        map.put(location, cords);
					}
				}
			} catch (JSONException je){
				je.printStackTrace();
			}
		}
		return map;
	}
	private float[] locate(String location){
		float[] cords=LOC.getGeoLoc(location);
		if(cords!=null){
	        return cords;
	    }
	    String parsedLocation=parseLocation(location);
	    cords=LOC.getGeoLoc(parsedLocation);
	    if(cords!=null){
	        return cords;
	    }
	    return null;
	}
	private String parseLocation(String location){
	    int left=-1;
	    int right=-1;
	    for(int i=0;i<location.length();i++){
	        if(location.charAt(i)=='('){
	            left=i;
	        }
	        if(location.charAt(i)==')'){
	            right=i;
	        }
	    }
	    if(left==-1||right==-1){
	        return location;
	    }
	    return location.substring(left,right+1);
	}
}
