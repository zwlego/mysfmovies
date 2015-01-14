package mysfmovies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.mysfmovies.Service;
//import com.mysfmovies.JsonSource;

public class ServiceTest {
    public final String getURL="https://data.sfgov.org/resource/yitu-d5am.json";
    
    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    
    private String readJsonFromUrl() throws IOException, JSONException {
        URL getUrl=new URL(getURL);
        HttpURLConnection connection = (HttpURLConnection)getUrl.openConnection();
        connection.connect();
        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String jsonText = readAll(rd);
        connection.disconnect();
        return jsonText;
    }
    
//    JsoSource.setDataSource(readJsonFromUrl());
    
    private Service service = new Service();
    
    public ServiceTest() {
    	service.dataArray = readJsonFromUrl();
    }
    
    @Test
    public void test_SearchLocations() {
        JSONArray filmingLocations = null;
        try{
            JSONObject userInput=new JSONObject();
            userInput.put("title", "the enforcer");
            JSONObject query=new JSONObject();
            query.put("searchType", "search");
            query.put("query", userInput);
            query.put("data", "nothing");
            JSONObject response = service.search(query.toString());
            filmingLocations = response.getJSONArray("array");
        }catch(JSONException e){
            e.printStackTrace();
        }
        Assert.assertTrue(filmingLocations.length()==9);
    }
    
    @Test
    public void test_filterLocations() {
        
        JSONArray filteredResults = null;
        try{
            JSONObject userInput=new JSONObject();
            userInput.put("title", "the enforcer");
            JSONObject query=new JSONObject();
            query.put("searchType", "search");
            query.put("query", userInput);
            JSONObject originalResults = service.search(query.toString());
            JSONObject data=new JSONObject();
            data.put("rawResult", originalResults.get("array"));
            
            
            userInput.put("location", "24th Street Mini Park");
            query.put("searchType", "filter");
            query.put("query", userInput);
            query.put("data", data.toString());
            JSONObject response = service.search(query.toString());
            filteredResults = response.getJSONArray("array");
        }catch(JSONException e){
            e.printStackTrace();
        }
        Assert.assertTrue(filteredResults.length()==1);
    }
    
    @Test
    public void test_invailidInput() {
        JSONObject filmingLocations = null;
        String success = null;
        try{
            JSONObject userInput=new JSONObject();
            userInput.put("title", "##$!@#!");
            JSONObject query=new JSONObject();
            query.put("searchType", "search");
            query.put("query", userInput);
            filmingLocations = service.search(query.toString());
            success = filmingLocations.getString("success");
        }catch(JSONException e){
            e.printStackTrace();
        }
        Assert.assertTrue(success.equals("false"));
    }
}
