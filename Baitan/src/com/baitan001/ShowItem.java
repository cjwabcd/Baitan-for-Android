package com.baitan001;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowItem extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showitem);
        Intent inte=getIntent();     
        String id=inte.getStringExtra("id");  
    
	    ItemInfo item = new ItemInfo();
	    LinearLayout layout = (LinearLayout) findViewById(R.id.showitem1);
		ImageView imageView = new ImageView(this);
		Bitmap bitmap = connectionPool.getHttpBitmap(item.doubansimg);
		imageView.setImageBitmap(bitmap);
		imageView.setPadding(2,6,2,0); // left, top, right, bottom
		layout.addView(imageView);//,layoutParams);
		TextView text=new TextView(this);
		TextView price = new TextView(this);
		text.setText(id);
		price.setText("￥" +item.baitanprice);
		text.setTypeface(Typeface.DEFAULT_BOLD);	
		layout.addView(text);
		layout.addView(price);
    }
}