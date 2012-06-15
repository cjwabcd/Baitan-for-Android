package com.baitan001.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class NewItemActivity extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
	TextView prompt;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newitem);

		initBtnLsnr();
	}
	
	private void initBtnLsnr() {
		// TODO initiate view listener

		TextView bookBtn = (TextView) findViewById(R.id.booklauncher);
		TextView goodBtn = (TextView) findViewById(R.id.goodlauncher);
		bookBtn.setOnClickListener(new WizardListener(0));
		goodBtn.setOnClickListener(new WizardListener(1));	
				
				
				
		
	}
	
	class WizardListener implements View.OnClickListener {
		
		int idx;
		
		public WizardListener(int idx){
			this.idx = idx;
		}
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = null;
			switch (idx) 
			{
				case 0: intent = new Intent(NewItemActivity.this, BookWizard.class);break;
				case 1: intent = new Intent(NewItemActivity.this, GoodWizard.class);
			}
			Bundle b = new Bundle();
			b.putString("id", "Default");
			intent.putExtras(b);
			startActivity(intent);
		}
	}
}