package mysfmovies;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.mysfmovies.Service;

public class ServiceTest {

		private Service service = new Service();
		
		public ServiceTest() {
		}
		
		@Test
		public void test_SearchLocations() {
			JSONObject filmingLocations = null;
			try{
				JSONObject userInput=new JSONObject();
				userInput.put("title", "the enforcer");
				JSONObject query=new JSONObject();
				query.put("searchType", "search");
				query.put("query", userInput);
				JSONObject response = service.search(query.toString());
				filmingLocations = response.getJSONObject("array");
			}catch(JSONException e){
				
			}
			Assert.assertTrue(filmingLocations.length()==9);
		}
		
		@Test
		public void test_filterLocations() {
			JSONObject filteredResults = null;
			try{				
				JSONObject userInput=new JSONObject();
				userInput.put("title", "the enforcer");
				JSONObject query=new JSONObject();
				query.put("searchType", "search");
				query.put("query", userInput);
				JSONObject originalResults = service.search(query.toString());
				
				userInput.put("location", "24th Street Mini Park");
				query.put("searchType", "filter");
				query.put("query", userInput);
				query.put("rawResult", originalResults);
				JSONObject response = service.search(query.toString());
				filteredResults = response.getJSONObject("array");
			}catch(JSONException e){
				
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
				
			}
			Assert.assertTrue(success.equals("false"));
		}
		
}
