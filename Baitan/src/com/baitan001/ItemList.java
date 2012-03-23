package com.baitan001;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
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
 
    	String request_URL = "http://tiaosao.org/book.json"; 
        String response = "";                
        /** Retrieve default item list*/
        try {
			response = connectionPool.connInit(request_URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        ItemInfo item = (ItemInfo)JSONParser(response);
        
        TableLayout layout = (TableLayout)findViewById(R.id.tableLayout1);
		for (int i = 0; i < 6; i++)
        		layout.addView(row(item));
        
        
               
    }
    
    
    
    private ItemInfo JSONParser(String response) {
		// TODO Read in a JSONString and generate an item object
		ItemInfo item = new ItemInfo();
		JSONObject jObj;
		try {
			jObj = new JSONObject(response);
			item.bookname = jObj.getString("bookname");
			item.baitanprice = Integer.parseInt(jObj.getString("baitanprice"));
			item.seller = jObj.getString("seller");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return item;
	}



	private TableRow row (final ItemInfo item){
		/*Retrieve info from Iteminfo object and generate an item row */
    	
		TableRow template=(TableRow)findViewById(R.id.itemlist_row);
		android.view.ViewGroup.LayoutParams layoutParams=template.getLayoutParams();
		final TableRow layout=new TableRow(this);
		layout.setLayoutParams(layoutParams);
		layout.setBackgroundColor(Color.WHITE);
		ImageView imageView=new ImageView(this);

		Bitmap bitmap = connectionPool.getHttpBitmap(item.doubansimg);
		imageView.setImageBitmap(bitmap);
		imageView.setPadding(2,6,2,0);
		layout.addView(imageView);
		TextView text=new TextView(this);
		TextView price = new TextView(this);
		text.setText("\n"+item.bookname + "\n卖家：" + item.seller);
		price.setText("\n\n\n         ￥" +item.baitanprice);
		text.setTypeface(Typeface.DEFAULT_BOLD);	
		layout.addView(text);
		layout.addView(price);
    	
    	
		OnTouchListener showItemListener = new OnTouchListener() {
			Intent i = new Intent();

			public boolean onTouch(View v, MotionEvent event) {
				// TODO Listen to the touch event on item row

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(Color.GRAY);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(Color.WHITE);
					i.putExtra("id", "book.json");
					i.setClassName("com.baitan001", "com.baitan001.ShowItem");
					startActivity(i);
					break;
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundColor(Color.WHITE);
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
    



    
}