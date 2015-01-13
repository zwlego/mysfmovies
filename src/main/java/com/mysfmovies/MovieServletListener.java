package com.mysfmovies;

import java.util.Calendar;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.appengine.api.ThreadManager;



public class MovieServletListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent arg0) {

        ThreadManager.createThreadForCurrentRequest(new JsonSourceUpdate()).start();
        ThreadManager.createThreadForCurrentRequest(new GoogleMapUpdate()).start();
	}

	public void contextDestroyed(ServletContextEvent arg0) {

	}
}
