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

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		   throws ServletException, IOException{	
    	
    	System.out.println("post");
		
    	BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String query = "";		 
		if(br != null){
			query = br.readLine();
		}
		Service ser=new Service();
		JSONObject result=ser.search(query);
		resp.setContentType("application/json");
		resp.getWriter().write(result.toString());
	}

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException
    {
    	PrintWriter out = response.getWriter();
    	out.println("<html><body>'get'</body></html>");
    	System.out.println("get");
    }

}



