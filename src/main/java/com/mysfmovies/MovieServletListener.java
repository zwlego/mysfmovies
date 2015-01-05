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
	//private final long PERIOD_DAY=24*60*60*1000;
	//private final long JSON_UPDATE_INTERVAL = PERIOD_DAY;
	//private final long MAP_UPDATE_INTERVAL = 30 * PERIOD_DAY;
	//private Timer timer = new Timer();

	public void contextInitialized(ServletContextEvent arg0) {
		//timer.schedule(new JsonSourceUpdate(), Calendar.getInstance().getTime(), JSON_UPDATE_INTERVAL);
		//timer.schedule(new GoogleMapUpdate(), Calendar.getInstance().getTime(), MAP_UPDATE_INTERVAL);
        ThreadManager.createThreadForCurrentRequest(new JsonSourceUpdate()).start();
        ThreadManager.createThreadForCurrentRequest(new GoogleMapUpdate()).start();
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		//timer.cancel();
	}
}
