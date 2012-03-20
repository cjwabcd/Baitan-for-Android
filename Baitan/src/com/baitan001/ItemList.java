package com.baitan001;

import android.app.Activity;
import android.os.Bundle;

public class ItemList extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemlist);
        
    	String request_URL = "http://tiaosao.org/book.json"; 
        String response = "";                
        /* Retrieve default item list*/
        try {
			response = connectionPool.connInit(request_URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        
        
    }
    
    
    
}