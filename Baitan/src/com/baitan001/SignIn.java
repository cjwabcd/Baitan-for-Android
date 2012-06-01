package com.baitan001;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

				String request_URL = "http://www.baitan001.com/m/login/";

				String response = "";
				/** Retrieve default item list */
				try {
					response = connectionPool.connInit(request_URL);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				response = "{items:" + response + "}";
				if (true){
					// TODO return to previous activity
					UserInfo.setLogin_status(true);
					setResult(0,new Intent(SignIn.this,NewItemActivity.class));
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