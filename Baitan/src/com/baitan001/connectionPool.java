package com.baitan001;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class connectionPool extends Application{
		private String connection;
		
		public String getConnection(){
			return connection;
		}
		
		static String connInit(String request_url) throws Exception{
	    	//final String ITEM_LIST_URL = "http://www.tiaosao.org/book.json";
	    	//final String ITEM_INFO_URL_PREFIX = "http://www.tiaosao.org/book.json";
	    	
	    	
	    	BufferedReader in = null;
	    	String page;
	    	try {
	    		HttpClient client = new DefaultHttpClient();
	    		HttpGet request = new HttpGet();
	    		request.setURI(new URI(request_url));
	    		HttpResponse response = client.execute(request);
	    		in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	    		StringBuffer sb = new StringBuffer("");
	    		String line = "";
	    		String NL = System.getProperty("line.separator");
	    		while ((line =in.readLine()) != null){
	    			sb.append(line + NL);
	    		} 
	    		in.close();
	    		page = sb.toString();
	    		System.out.println(page);
	    	}finally{
	    		if (in != null){
	    			try {
	    				in.close();
	    			}catch (IOException e){
	    				e.printStackTrace();
	    			}
	    		}
				
			}
	    	
	    	return page;
	    }
		
	    public static Bitmap getLocalBitmap(String url) {
	    	try {
	    		FileInputStream fis = new FileInputStream(url);
	    		return BitmapFactory.decodeStream(fis);
	    	}catch (FileNotFoundException e) {
	    		e.printStackTrace();
	    		return null;
	    		}
	    }
	    
		public static Bitmap getHttpBitmap(String url) {
			URL myFileUrl = null;
			Bitmap bitmap = null;
			try {
				// Log.d(TAG, url);
				myFileUrl = new URL(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			try {
				HttpURLConnection conn = (HttpURLConnection) myFileUrl
						.openConnection();
				conn.setConnectTimeout(0);
				conn.setDoInput(true);
				conn.connect();
				InputStream is = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(is);
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bitmap;
		}
			
}