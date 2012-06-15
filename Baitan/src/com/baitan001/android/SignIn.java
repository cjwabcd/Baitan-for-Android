package com.baitan001.android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignIn extends Activity {
	
    EditText et_un,et_pw;
    Editable editable; 
    TextView prompt;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        if (UserInfo.isLogin())
        	finish();

        Button lgbtn = (Button) findViewById(R.id.login);
        lgbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				et_un = (EditText)findViewById(R.id.signin_username);
				et_pw = (EditText)findViewById(R.id.signin_password);
				editable = et_un.getText();
				String username = editable.toString();
				editable = et_pw.getText();
				String password = editable.toString(); 
				login(username, password);
			}

			private void login(String username, String password) {
				// TODO Auto-generated method stub
				//
				
				
				String request_URL = "http://www.baitan001.com/m/login/woailiuziwen@gmail.com/9aee390f19345028f03bb16c588550e1";
				String request = "{\"email\":\""+username+"\",\"password\":\""+password+"\"}";
				String response = "";
				/** Retrieve default item list */
				try {
					//connectionPool.setRequest(request);
					response = connectionPool.postConn(request_URL,request);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//	response = "{items:" + response + "}";
				Log.i("LoginResp",response);
				
				JSONObject jObj = null ;
				String nickname = null, sessionid = null;
		

				try {
					jObj = new JSONObject(response);
					nickname = jObj.getString("username");
					sessionid = jObj.getString("sessionid");
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			
				if (!sessionid.equals("")){
					UserInfo.setNickname(nickname);
					UserInfo.setSessionid(sessionid);
					// TODO return to previous activity
					UserInfo.setLogin_status(true);
				//	setResult(0,new Intent(SignIn.this,NewItemActivity.class));
					finish();
				} else{
					prompt = (TextView) findViewById(R.id.prompt); 
					prompt.setText("Error Message");
					prompt.invalidate();
				}
					
					
			}
        	
        });
    }
    
    
}