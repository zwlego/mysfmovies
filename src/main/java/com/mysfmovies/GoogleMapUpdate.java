package com.mysfmovies;

import com.google.appengine.api.ThreadManager;

public class GoogleMapUpdate implements Runnable {
    private final long PERIOD_DAY=24*60*60*1000;
    private final long MAP_UPDATE_INTERVAL = 30 * PERIOD_DAY;
	@Override
	public void run(){
        try{
            System.out.println("update google maps");
            LocSearch.geoMaps.clear();
            Thread.sleep(MAP_UPDATE_INTERVAL);
        }
        catch(InterruptedException e){
             System.err.println("IOException: " + e.getMessage());
        }
	}
}
 