package com.baitan001;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.R.color;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ItemList extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.itemlist);
 
    	//String request_URL = "http://tiaosao.org/book.json"; 
        //String response = "";                
        /** Retrieve default item list*/
        /*try {
			response = connectionPool.connInit(request_URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

        
        TableLayout layout = (TableLayout)findViewById(R.id.tableLayout1);
		for (int i = 0; i < 6; i++)
        		layout.addView(row(new ItemInfo()));
        
        
               
    }
    
    
    
    private TableRow row (final ItemInfo item){
		/*Retrieve info from Iteminfo object and generate an item row */
    	
		TableRow template=(TableRow)findViewById(R.id.itemlist_row);
		android.view.ViewGroup.LayoutParams layoutParams=template.getLayoutParams();
		final TableRow layout=new TableRow(this);
		layout.setLayoutParams(layoutParams);
		layout.setBackgroundColor(Color.WHITE);
		ImageView imageView=new ImageView(this);
		//Bitmap bitmap = connectionPool.getLocalBitmap("res/drawable-mdpi/s1483422.jpg");
		Bitmap bitmap = connectionPool.getHttpBitmap(item.doubansimg);
		imageView.setImageBitmap(bitmap);
		//imageView.setImageResource("res/s1483422.jpg");
		//imageView.setImageURI(item.doubansimg);
		
		imageView.setPadding(2,6,2,0); // left, top, right, bottom
		layout.addView(imageView);//,layoutParams);
		TextView text=new TextView(this);
		TextView price = new TextView(this);
		text.setText(item.bookname);
		price.setText("￥" +item.baitanprice);
		text.setTypeface(Typeface.DEFAULT_BOLD);	
		layout.addView(text);
		layout.addView(price);
    	
    	
        OnTouchListener showItemListener = new OnTouchListener() {
    		Intent i = new Intent();
            public boolean onTouch(View v, MotionEvent event) {
  	    	  // TODO Auto-generated method stub
  	    	//scheduler.getGestureDetector().onTouchEvent(event);	
  	    	  switch (event.getAction()) { 	  
  	    	        case MotionEvent.ACTION_DOWN:v.setBackgroundColor(Color.GRAY);	//按下
  	    	            break;
  	    	        case MotionEvent.ACTION_UP:v.setBackgroundColor(Color.WHITE); i.setClassName("com.baitan001",
  	                        "com.baitan001.SignIn");
  	                startActivity(i);//抬起
  	    	            break;
  	    	        default:
  	    	            break;
  	    	  }
  	    	  return true;
            }
        };
        

        
        layout.setOnTouchListener((OnTouchListener) showItemListener);
        return layout;
    }
    
    public static Bitmap getHttpBitmap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			//conn.setConnectTimeout(0);
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