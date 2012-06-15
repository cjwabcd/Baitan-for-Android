package com.baitan001.android;

import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class BaitanTab extends TabActivity {

	private TabHost mTabHost;
	private TabSpec centerTab;
	private LocalActivityManager manager = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablayout);

		initTab2(savedInstanceState);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.baitan_menu, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.login:
			startLogin();
			break;
		case R.id.exit:
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(startMain);
			System.exit(0);
			break;
		}
		return true;
	}

	/**
	 * Initiate tab, aka bottom button in this app
	 */

	private void initTab(Bundle savedInstanceState) {
		mTabHost = getTabHost();
		centerTab = mTabHost.newTabSpec("signin").setIndicator("摆").setContent(new Intent(this, NewItemActivity.class));
				//mTabHost.newTabSpec("newitem").setIndicator("摆").setContent(new Intent(this, NewItemActivity.class))
				
		mTabHost.addTab(mTabHost.newTabSpec("lucky").setIndicator("逛逛")
				.setContent(new Intent(this, BaitanHomePager.class)));
		mTabHost.addTab(mTabHost.newTabSpec("search").setIndicator("搜索")
				.setContent(new Intent(this, SearchActivity.class)));
		mTabHost.addTab(centerTab);
		mTabHost.addTab(mTabHost.newTabSpec("favorite").setIndicator("收藏")
				.setContent(new Intent(this, FavoriteActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("settings").setIndicator("设置")
				.setContent(new Intent(this, SettingsActivity.class)));
		mTabHost.setCurrentTab(0);

		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
	}
	
	/**
	 * Initiate tab, aka bottom button in this app
	 */

	private void initTab2(Bundle savedInstanceState) {
		
		LinearLayout tab1=(LinearLayout)LayoutInflater.from(this).inflate(R.layout.tablet, null);  
        ImageView icon1=(ImageView)tab1.findViewById(R.id.icon);  
        icon1.setBackgroundResource(R.drawable.lucky_icon);  
        TextView title1=(TextView)tab1.findViewById(R.id.title);  
        title1.setText("逛逛");  
          
        LinearLayout tab2=(LinearLayout)LayoutInflater.from(this).inflate(R.layout.tablet, null);  
        ImageView icon2=(ImageView)tab2.findViewById(R.id.icon);  
        icon2.setBackgroundResource(R.drawable.search_icon);  
        TextView title2=(TextView)tab2.findViewById(R.id.title);  
        title2.setText("搜索");  
        
        LinearLayout tab3=(LinearLayout)LayoutInflater.from(this).inflate(R.layout.tablet, null);
        tab3.setBackgroundColor(Color.parseColor("#1180bb"));
        ImageView icon3=(ImageView)tab3.findViewById(R.id.icon);  
        icon3.setBackgroundResource(R.drawable.bai_icon); 
        TextView title3=(TextView)tab3.findViewById(R.id.title);  
        title3.setText("发布");  
        
        LinearLayout tab4=(LinearLayout)LayoutInflater.from(this).inflate(R.layout.tablet, null);  
        ImageView icon4=(ImageView)tab4.findViewById(R.id.icon);  
        icon4.setBackgroundResource(R.drawable.favor_icon);  
        TextView title4=(TextView)tab4.findViewById(R.id.title);  
        title4.setText("收藏");  
        
        LinearLayout tab5=(LinearLayout)LayoutInflater.from(this).inflate(R.layout.tablet, null);  
        ImageView icon5=(ImageView)tab5.findViewById(R.id.icon);  
        icon5.setBackgroundResource(R.drawable.settings_icon);  
        TextView title5=(TextView)tab5.findViewById(R.id.title);  
        title5.setText("设置");  
		
		
		
		
		
		mTabHost = getTabHost();
		centerTab = mTabHost.newTabSpec("signin").setIndicator(tab3).setContent(new Intent(this, NewItemActivity.class));
				//mTabHost.newTabSpec("newitem").setIndicator("摆").setContent(new Intent(this, NewItemActivity.class))
				
		mTabHost.addTab(mTabHost.newTabSpec("lucky").setIndicator(tab1)
				.setContent(new Intent(this, BaitanHomePager.class)));
		mTabHost.addTab(mTabHost.newTabSpec("search").setIndicator(tab2)
				.setContent(new Intent(this, SearchActivity.class)));
		mTabHost.addTab(centerTab);
		mTabHost.addTab(mTabHost.newTabSpec("favorite").setIndicator(tab4)
				.setContent(new Intent(this, FavoriteActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("settings").setIndicator(tab5)
				.setContent(new Intent(this, SettingsActivity.class)));
		mTabHost.setCurrentTab(0);

		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
	}
	
	
	
	

	private void startLogin() {
		// TODO Auto-generated method stub
		if (UserInfo.isLogin()) {
			Toast toast = Toast.makeText(BaitanTab.this, UserInfo.getNickname()+"已登陆",
					Toast.LENGTH_SHORT);
			toast.show();
		} else {
			Intent intent = new Intent(BaitanTab.this, SignIn.class);
			Bundle b = new Bundle();
			b.putString("id", "Default");
			intent.putExtras(b);
			startActivity(intent);
		}
	}
	
	

}