package com.baitan001.android;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baitan001.android.AsyncImageLoader.ImageCallback;

public class BaitanHomePager extends Activity {

	private List<ItemInfo> itemlist;
	private View booklayout,luckylayout,loadMore;
	private ViewPager luckyPager,globalPager;// 页卡内容
	private List<View> buttonViews, luckyPageViews; // Tab页面列表
	private ListView itemlist_layout;
	private ImageView cursor;// 动画图片
	private TextView lb1, lb2, bb1, bb2, bb3, bb4, bb5;// 页卡头标
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private int page_number;
	private TextView loadMoreBtn;
	private String cur_time;
	private ItemAdapter adapter; 




	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.luckylayout);
		initActionBar();
		initViewPager();
	}
		

	private void initActionBar() {
		// TODO Auto-generated method stub
		initRefreshButton();
	}


	private void initViewPager() {
		// TODO Auto-generated method stub
		initImageView();
		initLuckyPager();
		initLuckyBar();
		refrList();
		generListContent(false);
	}


	private void initRefreshButton() {
		// TODO Auto-generated method stub
		ImageView refrBtn = (ImageView) findViewById(R.id.refresh);
		refrBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				//itemlist_layout.removeFooterView(loadMore);
				refrList();
				generListContent(true);
				//adapter = new ItemAdapter(itemlist);

			}
			
		});
	}
	
	

	/**
	 * 初始化ViewPager
	 */
	private void initLuckyPager() {
		luckyPager = (ViewPager) findViewById(R.id.luckyPager);
		luckyPageViews = new ArrayList<View>();
		LayoutInflater mInflater = getLayoutInflater();
		booklayout = mInflater.inflate(R.layout.booklayout, null);
		luckyPageViews.add(booklayout);
		luckyPageViews.add(mInflater.inflate(R.layout.goodlayout, null));
		luckyPager.setAdapter(new MyPagerAdapter(luckyPageViews));
		luckyPager.setCurrentItem(0);
		luckyPager.setOnPageChangeListener((OnPageChangeListener) new MyOnPageChangeListener());
	}
	
	

	/**
	 * 初始化头标
	 */
	private void initLuckyBar() {
		lb1 = (TextView) findViewById(R.id.bookslist);
		lb2 = (TextView) findViewById(R.id.goodslist);

		lb1.setOnClickListener(new LuckyBarListener(0));
		lb2.setOnClickListener(new LuckyBarListener(1));

	}
	
	/**
	 * 初始化动画
	 */
	private void initImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.cursorbar).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 2 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}
	
	

	/**
	 * 头标点击监听
	 */
	public class LuckyBarListener implements View.OnClickListener {
		private int index = 0;

		public LuckyBarListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			luckyPager.setCurrentItem(index);
		}
	};
	
	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW; 

		public void onPageSelected(int arg0) {

			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(0, one, 0, 0);
				} 
				break;

			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {
		}
	}



	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
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
			TextView  publisher = (TextView) convertView.findViewById(R.id.publisher);
			TextView  contactpeople = (TextView) convertView.findViewById(R.id.contactpeople);
			ImageView image = (ImageView) convertView.findViewById(R.id.image);
			
			if (item != null) {
				convertView.setTag(item.id);
				bookname.setText(item.bookname);
				publisher.setText(item.bookauthor+"  "+item.publisher);
				//contactpeople.setText(item.contactpeople);
				price.setText(item.baitanprice + "元");


				/*	
				 if (true) {
					 image.setImageResource(R.drawable.logo91x29); 
				 } 
				 */
				 Drawable cachedImage = asyncImageLoader.loadDrawable(
				 item.doubansimg , image, new ImageCallback() {
					 public void imageLoaded(Drawable imageDrawable, 
							 ImageView imageView, String imageUrl) {
						 	imageView.setImageDrawable(imageDrawable); }
				 
				 }); 
				 
				 
				 if (cachedImage == null) {
					 image.setImageResource(R.drawable.loading); 
				 } else {
					 image.setImageDrawable(cachedImage);
				 }
				
			}
			return convertView;
		}

	}
	
	
	
	/*
	 * 刷新全部列表
	 */
	private void refrList(){
		
		long sys_time = System.currentTimeMillis();
		sys_time = sys_time / 1000;
		cur_time = String.valueOf(sys_time);
		page_number = 1;
		String location_ID = "27";
		
		String request_URL = "http://www.baitan001.com/m/itemlist/" + cur_time
				+ "/" + location_ID + "/" + page_number;
		itemlist = loadItemList(request_URL);
		
		//loadMoreBtn.setText("查看更多");
		//

	}
	
	
	/*
	 * 取得'更多'内容
	 */
	private void loadMore(){
		loadMoreBtn.setText("loading..");
		long sys_time = System.currentTimeMillis();
		sys_time = sys_time / 1000;

		String location_ID = "27";
		page_number++;
		
		String request_URL = "http://www.baitan001.com/m/itemlist/" + cur_time
				+ "/" + location_ID + "/" + page_number;
		
		itemlist.addAll(loadItemList(request_URL));
		loadMoreBtn.setText("查看更多");
		adapter.notifyDataSetChanged();
		//generListContent(true);
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

		
		//adapter.notifyDataSetChanged();
		
		adapter = new ItemAdapter(itemlist);
		
		
		itemlist_layout = (ListView) booklayout.findViewById(R.id.itemlistview);
		if (!isLoadingMore){	loadMore = getLayoutInflater().inflate(R.layout.loadmore, null);
		loadMoreBtn = (TextView) loadMore.findViewById(R.id.loadMoreButton);
		loadMoreBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadMore();
			}
		});
	
		itemlist_layout.addFooterView(loadMore); }
		
		itemlist_layout.setAdapter(adapter);
		itemlist_layout.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				Object obj = view.getTag();
				if (obj != null) {
					String id = obj.toString();
					Intent intent = new Intent(BaitanHomePager.this,
							ShowItem.class);
					Bundle b = new Bundle();
					b.putString("id", id);
					intent.putExtras(b);
					startActivity(intent);
				}
			}

		});
	
	}

	

}