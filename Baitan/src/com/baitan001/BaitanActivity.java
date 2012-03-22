package com.baitan001;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BaitanActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        try {
			connectionPool.connInit("http://tiaosao.org/book.json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    
    /* Initialize the connection with server, retrieve Logo*/
    
    
    
    private void dispList(String list_Json) {
		// TODO generate item list
		
	}



    private boolean dispLogo(String logoURI){
    	// TODO displogo
    	return true;
    }
    
    private void clickListener(){    	
    	 Intent i = new Intent();
         i.setClassName("com.baitan001.BaitanActivity","com.baitan001.ItemListActivity");
         startActivity(i); 	   	
    }
}