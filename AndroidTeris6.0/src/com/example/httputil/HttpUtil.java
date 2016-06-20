package com.example.httputil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

import com.example.Listenner.HttpListenner;

public class HttpUtil {
	
	public static void getResponse(final String address,final HttpListenner listenner){
		
		new Thread(new Runnable() {
			
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				URL url;
				try {
					url = new URL(address);
				
				HttpURLConnection connection= (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setConnectTimeout(8000); 
				connection.setReadTimeout(8000);
				connection.setDoInput(true);
				connection.setDoOutput(true);
				InputStream in= connection.getInputStream();
				
				BufferedReader bin = new BufferedReader(new InputStreamReader(in));
				StringBuilder response = new StringBuilder();
				String line = null;
				
				while( (line=bin.readLine())!=null ){
					
					response.append(line);
				}
				
				if(response!=null){
					
					listenner.onFinish(response);
				}
				
				
				
				
				
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		}).start();;
		
		
		

		
	}
	
	
	
	

}
