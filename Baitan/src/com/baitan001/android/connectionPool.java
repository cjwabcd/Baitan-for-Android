package com.baitan001.android;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class connectionPool extends Application{
		private String connection;
		private static String httpRequest;
		
		public String getConnection(){
			return connection;
		}
		
		
		
		static String postConn(String request_url, String requestParams) throws Exception{
			
			BufferedReader in = null;
	    	String page;
	    	try {
	    		HttpClient client = new DefaultHttpClient();
	    		HttpPost httpPost = new HttpPost();
	    		httpPost.setURI(new URI(request_url));
	    		
	    		
	    		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); 
	    		nameValuePairs.add(new BasicNameValuePair("request", requestParams));
	    		try {
	    			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    		} catch (UnsupportedEncodingException e1) {
	    			// TODO Auto-generated catch block
	    			e1.printStackTrace();
	    		}  
	    		HttpResponse response = client.execute(httpPost);
	    		in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	    		StringBuffer sb = new StringBuffer("");
	    		String line = "";
	    		String NL = System.getProperty("line.separator");
	    		while ((line =in.readLine()) != null){
	    			sb.append(line + NL);
	    		} 
	    		in.close();
	    		page = sb.toString();
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
		
		
		static String connInit(String request_url) throws Exception{
	    	//final String ITEM_LIST_URL = "http://www.tiaosao.org/book.json";
	    	//final String ITEM_INFO_URL_PREFIX = "http://www.tiaosao.org/book.json";
	    	
	    	
	    	BufferedReader in = null;
	    	String page;
	    	try {
	    		HttpClient client = new DefaultHttpClient();
	    		HttpGet request = new HttpGet();
	    		request.setURI(new URI(request_url));
	    		/*
	    		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); 
	    		nameValuePairs.add(new BasicNameValuePair("request", httpRequest));
	    		try {
	    			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    		} catch (UnsupportedEncodingException e1) {
	    			// TODO Auto-generated catch block
	    			e1.printStackTrace();
	    		} */  
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
	    		//System.out.println(page);
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

		public static void setRequest(String httprequest) {
			// TODO Auto-generated method stub
			httpRequest = httprequest;
		}
			
}