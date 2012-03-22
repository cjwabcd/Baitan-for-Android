package com.baitan001;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Dashboard extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*try {
			connectionPool.connInit("http://tiaosao.org/book.json");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        Button showListBtn = (Button)findViewById(R.id.showListBtn);
       
        
        OnClickListener showListLsnr = new OnClickListener() {
            public void onClick(View v) {
              // do something when the button is clicked
            	Intent i = new Intent();
                i.setClassName("com.baitan001",
                               "com.baitan001.ItemList");
                startActivity(i);
            }
        };
        
        showListBtn.setOnClickListener(showListLsnr);
        
        
    }
    
    
    /* Initialize the connection with server, retrieve Logo*/
    
    
    
    
    private void dispList(String list_Json) {
		// TODO generate item list
		
	}



    private boolean dispLogo(String logoURI){
    	// TODO displogo
    	return true;
    }
    

}