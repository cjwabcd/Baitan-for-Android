package com.baitan001;

import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class BaitanTab extends TabActivity {
	
	private TabHost mTabHost;
	private LocalActivityManager manager = null;
	private final static String TAG = "BaitanHomePager";
	private final Context context = BaitanTab.this;
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout);

		initTab(savedInstanceState);
        
    }
    
	/**
	 * Initiate tab, aka bottom button in this app
	 */

	private void initTab(Bundle savedInstanceState){
		mTabHost = getTabHost(); 
        mTabHost.addTab(mTabHost.newTabSpec("lucky").setIndicator( 
                "逛逛").setContent( 
                new Intent(this, BaitanHomePager.class))); 
        mTabHost.addTab(mTabHost.newTabSpec("search").setIndicator( 
                "搜索").setContent( 
                new Intent(this, SearchActivity.class)));
        mTabHost.addTab(mTabHost.newTabSpec("newitem").setIndicator( 
                "摆").setContent( 
               new Intent(this, SignIn.class))); 
        mTabHost.addTab(mTabHost.newTabSpec("favorite").setIndicator( 
                "收藏").setContent( 
               new Intent(this, FavoriteActivity.class))); 
        mTabHost.addTab(mTabHost.newTabSpec("settings").setIndicator( 
                "设置").setContent( 
               new Intent(this, SettingsActivity.class))); 
        mTabHost.setCurrentTab(0); 
       
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);
	}
    
    
    
}    