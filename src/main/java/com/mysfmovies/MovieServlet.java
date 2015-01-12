package com.mysfmovies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class MovieServlet  extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private List<Cinema> cinemas = new ArrayList<Cinema>(); 
	private List<Query> querys = new ArrayList<Query>();

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		   throws ServletException, IOException{	
    	
    	System.out.println("post");
		
    	BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String json = "";		 
		if(br != null){
			json = br.readLine();
		}
		System.out.println("Query: "+json);
		Service ser=new Service();
		JSONObject result=ser.search(json);
//		System.out.println(result.toString());
		
		resp.setContentType("application/json");
		resp.getWriter().write(result.toString());
		
//		
		
		 
//		ObjectMapper mapper = new ObjectMapper();
//		Query query = mapper.readValue(json, Query.class);
//		System.out.print(query.title);
		 
/*
		ObjectMapper mapper = new ObjectMapper();
		Query query = mapper.readValue(json, Query.class);
		
		
			//function

		Cinema cinema = mapper.readValue(json, Cinema.class);
		cinemas.add(cinema);
		
//		 resp.setContentType("Application/json");
//		 querys.add(query);
//		 mapper.writeValue(resp.getOutputStream(), querys);		
*/
	}

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException
    {
    	PrintWriter out = response.getWriter();
    	out.println("<html><body>'get'</body></html>");
    	System.out.println("get");
    }

}



