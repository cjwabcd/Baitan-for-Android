package com.baitan001;

import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

public class BaitanTab extends TabActivity {

	private TabHost mTabHost;
	private LocalActivityManager manager = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablayout);

		initTab(savedInstanceState);
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
		mTabHost.addTab(mTabHost.newTabSpec("lucky").setIndicator("逛逛")
				.setContent(new Intent(this, BaitanHomePager.class)));
		mTabHost.addTab(mTabHost.newTabSpec("search").setIndicator("搜索")
				.setContent(new Intent(this, SearchActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("newitem").setIndicator("摆")
				.setContent(new Intent(this, NewItemActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("favorite").setIndicator("收藏")
				.setContent(new Intent(this, FavoriteActivity.class)));
		mTabHost.addTab(mTabHost.newTabSpec("settings").setIndicator("设置")
				.setContent(new Intent(this, SettingsActivity.class)));
		mTabHost.setCurrentTab(0);

		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
	}

	private void startLogin() {
		// TODO Auto-generated method stub
		if (UserInfo.isLogin()) {
			Toast toast = Toast.makeText(BaitanTab.this, "已登陆",
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