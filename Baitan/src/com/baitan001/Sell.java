package com.baitan001;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Sell extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell);
        
        Button verifyBtn = (Button)findViewById(R.id.up_ver_btn); 
        final TextView up_isbn = (TextView)findViewById(R.id.up_isbn);
        
       
        OnClickListener buttonClickListener = new OnClickListener() {
            public void onClick(View v) {
              // do something when the button is clicked
            	getBookInfo(up_isbn.getText().toString());
            }


        };
        
        
        verifyBtn.setOnClickListener(buttonClickListener); 
        
        
    }
  
    private ItemInfo getBookInfo(String isbn){
    	ItemInfo item = null;
    	String request_URL = "http://www.baitan001.com/m/isbn/" + isbn; 
        String response = "";                
        /** Retrieve default item list*/
        try {
			response = connectionPool.connInit(request_URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	item = JSONParser(response);    	
    	return item;
    	
    }
    
    private ItemInfo JSONParser(String response) {
		// TODO Read in a JSONString and generate an item object
		ItemInfo item = new ItemInfo();
		JSONObject jObj;
		try {
			jObj = new JSONObject(response);
			item.bookname = jObj.getString("bookname");
			item.baitanprice = jObj.getString("baitanprice");
			item.contactpeople = jObj.getString("contactpeople");
			item.tel = jObj.getString("tel");
			item.doubansimg = jObj.getString("doubansimg");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}
    
    
    

}