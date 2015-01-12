package com.mysfmovies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.validation.*;
import com.google.gson.Gson;

public class Service {
	JSONArray dataArray;
	LocSearch LOC=new LocSearch();
	Service(){
			dataArray=JsonSource.getDataSource();
			LOC.setCitySuffix("San Francisco", "CA");
    }
	
    //public final String getURL="https://data.sfgov.org/resource/yitu-d5am.json";
    
    /**
     *  check whether a string is valid json text.
     *  
     *  @param  test  a String to be checked
     *  @return 	  true if test argument is a valid json text, otherwise return false.
     */
    
    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
    
    private String words(String s) {
        StringBuffer words= new StringBuffer();
        int j=-1;
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)==' '){
                j=i;
            }
            else if(i==s.length()-1||s.charAt(i+1)==' '){
                String add=s.substring(j+1, i+1);
                if(words.length()!=0&&words.charAt(words.length()-1)!='('&&(!add.equals(")"))){
                    words.append(" ");
                }
                words.append(add);
            }
        }
        return words.toString();
    }
    
    /**
     *  Parse the String in the input argument with respect to the type argument.
     *  
     *  @param	input  a String to be parsed
     *  @return 	   a parsed String
     */
    
    private SearchPair parse(Type type, String input){
        SearchPair result=new SearchPair(type);
        String words=words(input);
        switch(type){
            case RELEASE_YEAR:
                //what if 2014---201,5
                String years="";
                for(int i=0;i<words.length();i++){
                    if(words.charAt(i)>='0'&&words.charAt(i)<='9'){
                        years=words.substring(i, i+4);
                        break;
                    }
                }
                for(int i=words.length()-1;i>=3;i--){
                    if(words.charAt(i)>='0'&&words.charAt(i)<='9'){
                        years+=words.substring(i-3, i+1);
                        break;
                    }
                }
                result.val=years;
                break;
            default:
                result.val=words;
                break;
        }
        return result;
    }
    
    /**
     *  Parse all the SearchPair in the pairs argument
     *  
     *  @param	pairs  a List contains all SearchPair to be parsed
     *  @return 	   an array contains all the parsed searchPairs
     *  
     */
    
    private SearchPair[] parse(List<SearchPair> pairs){
        int lens=pairs.size();
        SearchPair result[]=new SearchPair[lens];
        int emptyNum=0;
        for(int i=0;i<lens;i++){
            SearchPair temp=(SearchPair)pairs.get(i);
            result[i]=parse(temp.type_enum,temp.val);
            if(result[i].val.equals("")||result[i].val==null){
                emptyNum++;
            }
        }
        if(emptyNum==lens){
            return null;
        }
        return result;
    }
    
    /**
     *  Returns the address within parentheses if any.
     *  
     *  @param  location  a String represents the address.
     *  @return			  a String contains parsed address.
     *  
     */
    
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
    
    /**
     *  Returns a float array contains the coordinates of the given location argument. If 
     *  no result found, return null.
     *  
     *  @param	location  a String represents the address
     *  @return			  return a float array contains two float coordinates, latitude and 
     *  				  lantitude.
     */
    
    private float[] locate(String location){
        float[] cords=LOC.getGeoLoc(location);
        if(cords!=null){
            return cords;
        }
        String parsedLocations=parseLocation(location);
        cords = LOC.getGeoLoc(parsedLocations);
        return cords;
    }
    
    /**
     *  Returns a boolean value that indicates whether ob argment is a match with 
     *  the information contained in the pair argument. 
     *  
     *  @param	pair   a SearchPair to be compared
     * 	@param	ob	   a JSONObject to be compared
     * 	@return		   return ture if pair and ob considered matched.
     * 
     */
    
    private boolean compare(SearchPair pair, JSONObject ob) throws JSONException{
    	if(pair.val.equals("")||pair.val==null){
    		return true;
    	}
    	pair.val=pair.val.toLowerCase();
            switch(pair.type_enum){
            case RELEASE_YEAR:
                if(ob.has("release_year")){
                    String year=(String)ob.get("release_year");
                    int val= Integer.parseInt(year);
                    int min=Integer.parseInt(pair.val.substring(0,4));
                    int max=Integer.parseInt(pair.val.substring(4,8));
                    if(val<=max&&val>=min){
                        return true;
                    }
                }
                return false;
            case ACTOR:
                String input=pair.val;
                String[] names=input.split(" ");
                StringBuffer bufferedActors=new StringBuffer();
                if(ob.has("actor_1")){
                    bufferedActors.append((String)ob.get("actor_1"));  
                }
                if(ob.has("actor_2")){
                    bufferedActors.append((String)ob.get("actor_2"));
                }
                if(ob.has("actor_3")){
                    bufferedActors.append((String)ob.get("actor_3"));
                }
                String actors=bufferedActors.toString().toLowerCase();
                for(int i=0;i<names.length;i++){
                    if(!actors.contains(names[i])){
                        return false;
                    }
                }
                return true;
            case WRITER:
                String input2=pair.val;
                String[] names2=input2.split(" ");
                String writers;
                if(!ob.has("writer")){
                    return false;
                }
                writers=(String)ob.get("writer");
                writers=writers.toLowerCase();
                for(int i=0;i<names2.length;i++){
                    if(!writers.contains(names2[i])){
                        return false;
                    }
                }
                return true;
            case PRODUCATION_COMPANY:
                String input3=pair.val;
                String[] names3=input3.split(" ");
                String companys;
                if(!ob.has("production_company")){
                    return false;
                }
                companys=(String)ob.get("production_company");
                companys=companys.toLowerCase();
                for(int i=0;i<names3.length;i++){
                    if(!companys.contains(names3[i])){
                        return false;
                    }
                }
                return true;
            default:
                if(!ob.has(pair.type)){
                    return false;
                }
                String temp=(String)ob.get(pair.type);
                temp=temp.toLowerCase();
                    if(!temp.contains(pair.val)){
                        return false;
                    }
            }
        return true;
    }
    
   
    /**
     *  Returns a boolean value that indicates whether ob argment is a match with 
     *  the information contained in pairs argument.
     *  @param	pairs  a array SearchPair to be compared
     * 	@param	ob	   a JSONObject to be compared
     * 	@return		   return ture if every element in pairs argument considered 
     * 				   matched with ob argument, otherwise return false. They are 
     * 				   considered matched if the method compare() returns true with
     * 				   them as arguments;
     * 
     */
    
    private boolean isTarget(SearchPair[] pairs, JSONObject ob) throws JSONException{
        for(int i=0;i<pairs.length;i++){
            if(!compare(pairs[i],ob)){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns a List of JSONObject which contains all the matched results by going 
     * through data argument to match the query infomation in the pairs argument.
     * This privated function is called by search() to implement "search" and "search in result" function. 
     * 					
     * 	@param  data  a JSONArray contains provided source data to be looked through.
     * 	@param	pairs an array of SearchPairs contains all query information. 
     * 	@return		  a List of JSONObject contains information of all the matched data. If no matched reuslts
     * 				  found, return an empty List.
     * 					
     * 
     */
    
    
    private List<JSONObject> findLocations (JSONArray data, SearchPair[] pairs) throws JSONException{		
        List<JSONObject> result=new ArrayList<JSONObject>();
        HashMap<String,List<JSONObject>> map=new HashMap<String,List<JSONObject>>();
        for(int i=0;i<data.length();i++){
            JSONObject temp=data.getJSONObject(i);
            if(isTarget(pairs,temp)){
                if(temp.has("locations")){
                    String location=(String)temp.get("locations");
                    if(!temp.has("latitude")){
                        float cords[]=JsonSource.getCoords(location);
                        if(cords==null){
                        	cords=locate(location);
                        	JsonSource.updateMap(location,cords);
                        }
                        if(cords!=null){
                            temp.put("latitude", cords[0]);
                            temp.put("lantitude", cords[1]);
                        }
                    }
                    if(map.containsKey(location)){
                        map.get(location).add(temp);
                    }
                    else{
                        List<JSONObject> sameLocation=new ArrayList<JSONObject>();
                        map.put(location, sameLocation);
                        sameLocation.add(temp);
                    }
                }
            }
        }
        Iterator<List<JSONObject>> listIterator= map.values().iterator();
        while(listIterator.hasNext()){
            Iterator<JSONObject> objectIterator=listIterator.next().iterator();
            while(objectIterator.hasNext()){
                result.add(objectIterator.next());
            }
        }
        return result;
    }
    
    
    /**
     * Returns a JSONObject which contains all the matched results. jsonText contains the user input.
     * It should be valid jsontext format and contains three name/value pairs. The value paired with 
     * name "query" is a JSONObject contains all user inputs. The value associated with the name "searchType" 
     * indicates it is a new search,"search", or search in results,"filter", manner. If it is a "filter", 
     * the value associated wiht the name "data" contains the unfiltered results in a JsonObject.
     * The method will return immediately, whether or not there is a matched result. 
     * 
     * 
     *
     * @param  jsonText a string contains the user query inputs in JSON text format; 
     * @return 			a JSONObject contains all the mathced results. 
     * 					There are two name/value pairs. The value associated
     * 					with the name "success" indicates whether any result has been
     * 					found. "false" indicates no reuslt; "true" indicates reuslts 
     * 					found. The value associated with the name "array" is a JSONArray
     * 					which contains several JSONOBjects. Each JSONObject contains all the 
     * 					information of a single result. 
     * 				 
     */
    
    public JSONObject search(String ob){
        Gson gson = new Gson();
        List<JSONObject> result=new ArrayList<JSONObject>();
        JSONObject json= new JSONObject();
        try{
            json.put("success","false" );
            if(isJSONValid(ob)){
            	searchRequest pack= gson.fromJson(ob, searchRequest.class);
                Query query=pack.query;
                String searchType=pack.searchType;
                List<SearchPair> pairs=query.toSearchPairs();
                if(pairs.size()==0)
                    return json;
                SearchPair[] parsedPairs=parse(pairs);
                if(parsedPairs==null)
                    return json;
                if(searchType.equals("search")){
                	if(dataArray==null){
                		return json;
                	}
                    result=findLocations(dataArray,parsedPairs);
                }
                else if(searchType.equals("filter")){
                    JSONObject rawData=new JSONObject(pack.data);
                    result=findLocations(rawData.getJSONArray("rawResult"),parsedPairs);
                }
                if(result.size()==0)
                    return json;
                JSONArray jsonArray=new JSONArray(result);
                json.put("array", jsonArray);
                json.put("success", "true");
            }
        }catch(JSONException je){
            System.out.print(je.toString());
        }
        return json;
    }
    
}