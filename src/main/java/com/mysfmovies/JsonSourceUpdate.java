package com.mysfmovies;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;







public class JsonSourceUpdate implements Runnable{
	public final String getURL="https://data.sfgov.org/resource/yitu-d5am.json";
    private final long PERIOD_DAY=24*60*60*1000;
    private final long JSON_UPDATE_INTERVAL = PERIOD_DAY;
	@Override
	public void run() {
		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		try{
	       URL getUrl=new URL(getURL);
	       HttpURLConnection connection = (HttpURLConnection)getUrl.openConnection();
	       connection.connect();
	       BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	       String jsonText = this.readAll(rd);
	       System.out.print("update json file @");
	       System.out.println(df.format(Calendar.getInstance().getTime()));
	       JsonSource.setDataSource(jsonText);
	       connection.disconnect();
           Thread.sleep(JSON_UPDATE_INTERVAL);
		}catch(IOException e){
			System.err.println("IOException: " + e.getMessage());
        }catch(InterruptedException e){
            System.err.println("InterrupttedException: " + e.getMessage());
        }
	}
	
    private String readAll(Reader rd) throws IOException {
    	StringBuilder sb = new StringBuilder();
    	int cp;
    	while ((cp = rd.read()) != -1) {
    	sb.append((char) cp);
    	}
    	return sb.toString();
    }
    
}