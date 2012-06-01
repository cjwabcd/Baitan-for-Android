package com.baitan001;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baitan001.AsyncImageLoader.ImageCallback;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends Activity {
	
	private List<ItemInfo> itemlist;
	private View booklayout;
	private ViewPager SearchPager;
	private List<View> SearchPageViews;
	private LinearLayout searchlayout;
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlayout);
        
        initSearchPager();        
        initSearchButton();
        

        
    }
    
	private void initSearchButton() {
		// TODO Auto-generated method stub
		Button searchBtn = (Button) findViewById(R.id.searchBtn);
		searchBtn.setOnClickListener(new OnClickListener(){
			private int index = 0;
		
			public void onClick(View v) {
				refreshList(index);
			}
		});
	}

	

	/**
	 * 初始化ViewPager
	 */
	private void initSearchPager() {
		LayoutInflater mInflater = getLayoutInflater();
		booklayout = mInflater.inflate(R.layout.booklayout, null);
		searchlayout = (LinearLayout) findViewById(R.id.searchmain);
		searchlayout.addView(booklayout);
		//View mainlayout = findViewById(R.id.searchmain);
		
		/*
		SearchPager = (ViewPager) findViewById(R.id.luckyPager);
		SearchPageViews = new ArrayList<View>();
		
		SearchPageViews.add(booklayout);
		// listViews.add(mInflater.inflate(R.layout.lay3, null));
		SearchPager.setAdapter(new MyPagerAdapter(SearchPageViews));
		SearchPager.setCurrentItem(0);
		SearchPager.setOnPageChangeListener((OnPageChangeListener) new MyOnPageChangeListener());*/
	}
   
    
    
    
    
    
    private void refreshList(int currIndex) {
		// TODO load or refresh the item listview

		long sys_time = System.currentTimeMillis();
		sys_time = sys_time / 1000;
		String cur_time = String.valueOf(sys_time);

		String location_ID = "27";

		String request_URL = "http://www.baitan001.com/m/itemlist/" + cur_time
				+ "/" + location_ID + "/1";

		// String request_URL =
		// "http://www.baitan001.com/m/itemlist/1332485319/27";
		String response = "";
		/** Retrieve default item list */
		try {
			response = connectionPool.connInit(request_URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response = "{items:" + response + "}";

		itemlist = (List<ItemInfo>) JSONParser(response);
		ListView itemlist_layout = (ListView) booklayout
				.findViewById(R.id.itemlistview);
		ItemAdapter adapter = new ItemAdapter();

		itemlist_layout.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				Object obj = view.getTag();
				if (obj != null) {
					String id = obj.toString();
					Intent intent = new Intent(SearchActivity.this,
							ShowItem.class);
					Bundle b = new Bundle();
					b.putString("id", id);
					intent.putExtras(b);
					startActivity(intent);
				}
			}

		});
		itemlist_layout.setAdapter(adapter);
	}
    
    
    
    private List<ItemInfo> JSONParser(String response) {
		// TODO Read in a JSONString and generate an item object
		List<ItemInfo> itemlist = new ArrayList<ItemInfo>();

		JSONObject jObj, jItem;
		try {

			jObj = new JSONObject(response);
			JSONArray items = jObj.getJSONArray("items");
			for (int i = 0; i < items.length(); i++) {
				jItem = items.getJSONObject(i);
				itemlist.add(new ItemInfo(jItem.getString("id"), jItem
						.getString("bookname"), jItem
						.getString("contactpeople"), jItem
						.getString("baitanprice"), jItem
						.getString("doubansimg")));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return itemlist;
	}
    
    
    /**
     * Click item to show product details
     */
    
    
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
				i.putExtra("id", "" + v.getId());
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
	

	/**
	 * Adapter 
	 */
		
	public class ItemAdapter extends BaseAdapter {

		private AsyncImageLoader asyncImageLoader;

		public int getCount() {
			return itemlist.size();
		}

		public Object getItem(int position) {
			return itemlist.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			asyncImageLoader = new AsyncImageLoader();

			convertView = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.itemtempl, null);

			//ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
			TextView  bookname = (TextView) convertView.findViewById(R.id.bookname);
			TextView  price = (TextView) convertView.findViewById(R.id.price);
			TextView  contactpeople = (TextView) convertView.findViewById(R.id.contactpeople);
			ImageView image = (ImageView) convertView.findViewById(R.id.image);
			ItemInfo item = itemlist.get(position);
			if (item != null) {
				convertView.setTag(item.id);
				bookname.setText(item.bookname);
				contactpeople.setText(item.contactpeople);
				price.setText(item.baitanprice);

				
				 if (true) {
					 image.setImageResource(R.drawable.ic_launcher); 
				 } 
				 
				 Drawable cachedImage = asyncImageLoader.loadDrawable(
				 item.doubansimg , image, new ImageCallback() {
					 public void imageLoaded(Drawable imageDrawable, 
							 ImageView imageView, String imageUrl) {
						 	imageView.setImageDrawable(imageDrawable); }
				 
				 }); 
				 
				 /*
				 if (cachedImage == null) {
					 icon.setImageResource(R.drawable.ic_launcher); 
				 } else {
					 icon.setImageDrawable(cachedImage);
				 }*/
				
			}

			return convertView;
		}
	}

    
}