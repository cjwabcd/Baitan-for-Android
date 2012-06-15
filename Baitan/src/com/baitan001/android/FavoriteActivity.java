package com.baitan001.android;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baitan001.android.AsyncImageLoader.ImageCallback;
import com.baitan001.android.SearchActivity.ItemAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FavoriteActivity extends Activity {
   
	
	private List<ItemInfo> itemlist;
	private View booklayout,loadMore;
	private LinearLayout favorlayout;

	private ViewPager globalPager;// 页卡内容
	private List<View> buttonViews; // Tab页面列表
	private ListView itemlist_layout;
	private Editable et;

	private Button loadMoreBtn;
	private ItemAdapter adapter; 
	private int page_number;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoritelayout);
        
        initActionBar();
        initFavorPager();
        
        loadFavor();
		generListContent(false);
        
    }
    
    private void initActionBar() {
		// TODO Auto-generated method stub
		
	}
    
    private void initFavorPager() {
		LayoutInflater mInflater = getLayoutInflater();
		booklayout = mInflater.inflate(R.layout.booklayout, null);
		favorlayout = (LinearLayout) findViewById(R.id.favormain);
		favorlayout.addView(booklayout);

		
	}


	
	private void loadFavor(){
		
		String request_URL = "http://www.baitan001.com/m/favoritelist/383/book/1";
		itemlist = loadItemList(request_URL);
	}
	
	
	
	
	
	private List<ItemInfo> loadItemList(String request_URL) {
		// TODO load or refresh the item listview


		String response = "";
		/** Retrieve default item list */
		try {
			response = connectionPool.connInit(request_URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response = "{items:" + response + "}";
		
		List<ItemInfo> itemlist = new ArrayList<ItemInfo>();

		JSONObject jObj, jItem;
		try {

			jObj = new JSONObject(response);
			JSONArray items = jObj.getJSONArray("items");
			for (int i = 0; i < items.length(); i++) {
				jItem = items.getJSONObject(i);
				itemlist.add(new ItemInfo(jItem.getString("id"), 
						jItem.getString("bookname"), 
						jItem.getString("contactpeople"), 
						jItem.getString("baitanprice"), 
						jItem.getString("doubansimg"),
						jItem.getString("publisher"),
						jItem.getString("bookauthor")
						));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return itemlist;	
	}
	
	
	
	
	
	private void generListContent(boolean isLoadingMore){
		
		adapter = new ItemAdapter(itemlist);
			
			
			itemlist_layout = (ListView) booklayout.findViewById(R.id.itemlistview);
			itemlist_layout.setAdapter(adapter);
			itemlist_layout.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View view, int arg2,
						long arg3) {
					Object obj = view.getTag();
					if (obj != null) {
						String id = obj.toString();
						Intent intent = new Intent(FavoriteActivity.this,
								ShowItem.class);
						Bundle b = new Bundle();
						b.putString("id", id);
						intent.putExtras(b);
						startActivity(intent);
					}
				}

			});
			
		}
		

	   
		/**
		 * Adapter 
		 */
			
		public class ItemAdapter extends BaseAdapter {

			private AsyncImageLoader asyncImageLoader;
			List<ItemInfo> itemlist;
			
			
			
			public ItemAdapter(List<ItemInfo> itemlist){
				this.itemlist = itemlist;
			}
			
			
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

				ItemInfo item = itemlist.get(position);
				convertView = LayoutInflater.from(getApplicationContext()).inflate(
						R.layout.itemtempl, null);			
				asyncImageLoader = new AsyncImageLoader();

				TextView  bookname = (TextView) convertView.findViewById(R.id.bookname);
				TextView  price = (TextView) convertView.findViewById(R.id.price);
				TextView  contactpeople = (TextView) convertView.findViewById(R.id.contactpeople);
				ImageView image = (ImageView) convertView.findViewById(R.id.image);
				
				if (item != null) {
					convertView.setTag(item.id);
					bookname.setText(item.bookname);
					contactpeople.setText(item.contactpeople);
					price.setText(item.baitanprice);


								
					 if (true) {
						 image.setImageResource(R.drawable.logo91x29); 
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