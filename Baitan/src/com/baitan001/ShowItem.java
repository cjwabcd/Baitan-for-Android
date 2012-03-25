package com.baitan001;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowItem extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showitem);
        Intent i = getIntent();     
        String id = i.getStringExtra("id");  
        
    	String request_URL = "http://www.baitan001.com/m/itemid/" + id; 
        String response = "";                
        /** Retrieve default item list*/
        try {
			response = connectionPool.connInit(request_URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
        ItemInfo item = (ItemInfo)JSONParser(response);
	    
	    LinearLayout layout = (LinearLayout) findViewById(R.id.showitem1);
		ImageView imageView = new ImageView(this);
		Bitmap bitmap = connectionPool.getHttpBitmap(item.doubansimg);
		imageView.setImageBitmap(bitmap);
		imageView.setPadding(2,6,2,0);
		layout.addView(imageView);
		TextView text=new TextView(this);
		TextView price = new TextView(this);
		text.setText("\n"+item.bookname 
				+ "\n卖家：" + item.contactpeople 
				+ "\n电话：" + item.tel);
		price.setText("￥" + item.baitanprice);
		text.setTypeface(Typeface.DEFAULT_BOLD);	
		layout.addView(text);
		layout.addView(price);
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