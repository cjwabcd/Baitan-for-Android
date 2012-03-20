package com.baitan001;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Application;

class connectionPool extends Application{
		private String connection;
		
		public String getConnection(){
			return connection;
		}
		
		static String connInit(String request_url) throws Exception{
	    	//final String defaultLogo = "res/drawble-ldpi/logo91x29.png"; 
	    	//dispLogo(defaultLogo);
	    	final String ITEM_LIST_URL = "http://www.tiaosao.org/book.json";
	    	final String ITEM_INFO_URL_PREFIX = "http://www.tiaosao.org/book.json";
	    	
	    	
	    	BufferedReader in = null;
	    	String page;
	    	try {
	    		HttpClient client = new DefaultHttpClient();
	    		HttpGet request = new HttpGet();
	    		request.setURI(new URI(ITEM_LIST_URL));
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
			
}