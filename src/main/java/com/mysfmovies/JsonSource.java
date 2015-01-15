package com.mysfmovies;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsonSource {
	private static JSONArray json;
	private static HashMap<String,float[] > map;
	
	/**
	 *  update static variable json
	 */
    protected static void setDataSource(String jsonText) {
    	try {
			json = new JSONArray(jsonText);
		} catch (JSONException e) {
			System.err.println("JSONexception: " + e.getMessage());
		}
    }

    protected static boolean isMapInit(){
    	return map!=null;
    }
    
    protected static void setCoordsMap() {
    	map=new HashMap<String,float[] >();
    }
    /**
     *  return a JSONArray contains up-to-date source information.
     */
    protected static JSONArray getDataSource(){
    	return json;
    }
    protected static void updateMap(String location, float[] coords){
    	map.put(location, coords);
    }
    protected static float[] getCoords(String location){
    	if(map.containsKey(location)){
    		return map.get(location);
    	}
    	return null;
    }

}
