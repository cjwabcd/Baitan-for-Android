package com.baitan001;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baitan001.AsyncImageLoader.ImageCallback;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class BaitanHomePager extends Activity {

	private List<ItemInfo> itemlist;
	private View booklayout,luckylayout;
	private ViewPager luckyPager,globalPager;// 页卡内容
	private List<View> buttonViews, luckyPageViews; // Tab页面列表
	private ImageView cursor;// 动画图片
	private TextView lb1, lb2, bb1, bb2, bb3, bb4, bb5;// 页卡头标
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private int page_number;
	private Button loadMoreBtn;

	



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.luckylayout);
		initImageView();
		initLuckyPager();
		initLuckyBar();
		initLoadMore();
		initRefreshButton();
		refreshList();
	}
		
	
	
	
	private void initLoadMore() {
		// TODO Auto-generated method stub
		//LayoutInflater lmInflator = getLayoutInflator(R.layout.loadmore);
		//Button loadMore = (Button) lmInflator.findViewById(R.id.loadMoreButton);
		
	}




	private void initRefreshButton() {
		// TODO Auto-generated method stub
		TextView refrBtn = (TextView) findViewById(R.id.refresh);
		refrBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				//page_number=0;
				//itemlist.add(new ItemInfo("-1","-1","-1","-1","-1"));
				refreshList();
			}
			
		});
		
		/*
		refrBtn.setOnTouchListener(new OnTouchListener(){

			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(Color.GRAY);
					break;
				case MotionEvent.ACTION_UP:
					refreshList(currIndex);
					break;
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundColor(Color.WHITE);
					break;
				default:
					break;
				}
				
				return false;
			}
			
		});*/
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
		// listViews.add(mInflater.inflate(R.layout.lay3, null));
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
	 * 底部监听
	 */
	
	public class BottomButtonListener implements View.OnClickListener {
		private int index = 0;

		public BottomButtonListener(int i) {
			index = i;
		}

		public void onClick(View v) {
				globalPager.setCurrentItem(index);
		}
	};



	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW; // 页卡1 -> 页卡2 偏移量

		public void onPageSelected(int arg0) {
			//Log.e("Page","NO"+arg0);
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

	private void refreshList() {
		// TODO load or refresh the item listview

		long sys_time = System.currentTimeMillis();
		sys_time = sys_time / 1000;
		String cur_time = String.valueOf(sys_time);

		String location_ID = "27";
		page_number++;
		

		String request_URL = "http://www.baitan001.com/m/itemlist/" + cur_time
				+ "/" + location_ID + "/" + page_number;

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
		
		ListView itemlist_layout = (ListView) booklayout
				.findViewById(R.id.itemlistview);
		ItemAdapter adapter = new ItemAdapter();
		View loadMore = getLayoutInflater().inflate(R.layout.loadmore, null);
		loadMoreBtn = (Button) loadMore.findViewById(R.id.loadMoreButton);
		loadMoreBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadMoreBtn.setText("loading..");
				refreshList();
			}});
		
		if (page_number != 1) {
		//	itemlist.remove(itemlist.size()-1);
			itemlist.addAll((List<ItemInfo>) JSONParser(response));
		//	itemlist.add(new ItemInfo("-1","-1","-1","-1","-1"));
			adapter.notifyDataSetChanged();
			return;
		}
		
		itemlist_layout.addFooterView(loadMore); 
		itemlist = (List<ItemInfo>) JSONParser(response);
		//itemlist.add(new ItemInfo("-1","-1","-1","-1","-1"));


		itemlist_layout.setAdapter(adapter);
		itemlist_layout.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				Object obj = view.getTag();
				if (obj != null) {
					String id = obj.toString();
					Log.e("test","more button begin");
					if (id.equals("-1")){
						Log.e("test","more button");
						refreshList();
						return;
					}	
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
	
	
/*
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
*/
	
	/**
	 * JSONParser
	 * repsonse: http response wrapping JSON 
	 */
	
	
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

			ItemInfo item = itemlist.get(position);
			convertView = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.itemtempl, null);
			if (item.id.equals("-1")){
				convertView.setTag(item.id);
				TextView  bookname = (TextView) convertView.findViewById(R.id.bookname);
				bookname.setText("更多...");
 				return convertView;
			}			
			
			asyncImageLoader = new AsyncImageLoader();

			

			//ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
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