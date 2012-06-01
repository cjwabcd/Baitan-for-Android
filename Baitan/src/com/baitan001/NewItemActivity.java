package com.baitan001;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NewItemActivity extends Activity {
	
	TextView prompt;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newitem);
      
        if (!UserInfo.isLogin()){
			Intent intent = new Intent(NewItemActivity.this,
					SignIn.class);
			Bundle b = new Bundle();
			String loginCode = null;
			//b.putString("id", id);
			intent.putExtra("listenLogin",loginCode);
			startActivityForResult(intent,0);
        }else
        	initLayout();
    
        	
    }

	private void initLayout() {
		// TODO Auto-generated method stub
		prompt = (TextView) findViewById(R.id.prompt); 
		prompt.setText("Log in successful");
		prompt.invalidate();
	}
}