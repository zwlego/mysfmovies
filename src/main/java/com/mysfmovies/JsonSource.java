package com.mysfmovies;

import org.json.JSONArray;
import org.json.JSONException;

public final class JsonSource {
	private static JSONArray json;
    protected static void setDataSource(String jsonText) {
    	try {
			json = new JSONArray(jsonText);
		} catch (JSONException e) {
			System.err.println("JSONexception: " + e.getMessage());
		}
    }
    protected static JSONArray getDataSource(){
    	return json;
    }
}
