package com.baitan001;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
 
    	String request_URL = "http://www.baitan001.com/m/itemlist/1332485319/27"; 
        String response = "";                
        /** Retrieve default item list*/
        try {
			response = connectionPool.connInit(request_URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        response = "{items:" +  response +"}";
        
        ArrayList<ItemInfo> itemlist = (ArrayList<ItemInfo>)JSONParser(response);
        
        TableLayout layout = (TableLayout)findViewById(R.id.tableLayout1);
		for (int i = 0; i < itemlist.size(); i++)
        		layout.addView(row(itemlist.get(i)));
		/*for (int i = 0; i < itemlist.size(); i++){
			TableRow row = (TableRow) layout.findViewById(Integer.parseInt(itemlist.get(i).id));
			Bitmap bitmap = connectionPool.getHttpBitmap(itemlist.get(i).doubansimg);
				//Bitmap bitmap =null;
			row.findViewById(Integer.parseInt(itemlist.get(i).id)).setImageBitmap(bitmap);
		}*/
        
               
    }
    
    
    
    private ArrayList<ItemInfo> JSONParser(String response) {
		// TODO Read in a JSONString and generate an item object
		ArrayList<ItemInfo> itemlist = new ArrayList<ItemInfo>();

		JSONObject jObj,jItem;
		try {
			
			jObj = new JSONObject(response);
			JSONArray items = jObj.getJSONArray("items"); 
			for(int i = 0; i < items.length(); i++){
				jItem = items.getJSONObject(i);
				itemlist.add(new ItemInfo(
						jItem.getString("id"),
						jItem.getString("bookname"),
						jItem.getString("contactpeople"),
						jItem.getString("baitanprice"), 
						jItem.getString("doubansimg")));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return itemlist;
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
		//Bitmap bitmap =null;
		imageView.setImageBitmap(bitmap);
		imageView.setPadding(2,6,2,0);
		imageView.setId(Integer.parseInt(item.id));
		layout.addView(imageView);
		TextView text=new TextView(this);
		TextView price = new TextView(this);
		text.setText("\n"+item.bookname + "\n卖家：" + item.contactpeople);
		price.setText("\n\n\n￥" +item.baitanprice);
		text.setTypeface(Typeface.DEFAULT_BOLD);	
		layout.addView(text);
		layout.addView(price);
		layout.setId(Integer.parseInt(item.id));
        layout.setOnTouchListener((OnTouchListener) showItemListener);
        
        return layout;
    }
    
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
				i.putExtra("id", ""+v.getId());
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



    
}