package com.mysfmovies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
	
    public final String getURL="https://data.sfgov.org/resource/yitu-d5am.json";
    


    
    // used to check if it is a valid JSON string
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
   
    private SearchPair parser(Type type, String input){
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
    
    private SearchPair[] parser(List<SearchPair> pairs){
        int lens=pairs.size();
        SearchPair result[]=new SearchPair[lens];
        int emptyNum=0;
        for(int i=0;i<lens;i++){
        	SearchPair temp=(SearchPair)pairs.get(i);
            result[i]=parser(temp.type_enum,temp.val);
            if(result[i].val.equals("")||result[i].val==null){
            	emptyNum++;
            }
        }
        if(emptyNum==lens){
        	return null;
        }
        return result;
    }
    
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
                    if(!temp.equals(pair.val)){
                        return false;
                    }
            }
        return true;
    }
    
    private boolean isTarget(SearchPair[] pairs, JSONObject ob) throws JSONException{
        for(int i=0;i<pairs.length;i++){
            if(!compare(pairs[i],ob)){
                return false;
            }
        }
        return true;
    }
    
    private List<List<JSONObject>> search (JSONArray data, SearchPair[] pairs) throws JSONException{
    	List<List<JSONObject>> result=new ArrayList<List<JSONObject>>();
        HashMap<String,List<JSONObject>> map=new HashMap<String,List<JSONObject>>();
            for(int i=0;i<data.length();i++){
                JSONObject temp=data.getJSONObject(i);
                if(isTarget(pairs,temp)){
                	if(temp.has("locations")){
                		String location=(String)temp.get("locations");
                		float cords[]=LOC.getGeoLoc(location);
                		if(cords!=null){
                			temp.put("latitude", cords[0]);
                    		temp.put("lantitude", cords[1]);
                		}
                		if(map.containsKey(location)){
                			map.get(location).add(temp);
                		}
                		else{
                			List<JSONObject> newList=new ArrayList<JSONObject>();
                			result.add(newList);
                			map.put(location, newList);
                			newList.add(temp);
                		}
                	}
                }
            }
        return result;
    }
    
    public JSONObject search(String ob){
    	Gson gson = new Gson();
    	List<List<JSONObject>> result=new ArrayList<List<JSONObject>>();
    	JSONObject json= new JSONObject();
    	try{
    		json.put("success","false" );
    		if(isJSONValid(ob)){
        		Query query = gson.fromJson(ob, Query.class);
        		List<SearchPair> pairs=query.toSearchPairs();
        		// if the user inputs nothing, return 
        		if(pairs.size()==0)
        			return json;
        		SearchPair[] parsedPairs=parser(pairs);
        		// if the user inputs only spaces, return 
        		if(parsedPairs==null)
        			return json;
        		result=search(dataArray,parsedPairs);
        		if(result.size()==0)
        			return json;
        		JSONArray jResult=new JSONArray(result);
        		json.put("array", jResult);
        		json.put("success", "true");
        	}
		}catch(JSONException je){
			System.out.print(je.toString());
		}
    	return json;
    }

}
