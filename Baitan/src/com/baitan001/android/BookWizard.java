package com.baitan001.android;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.baitan001.android.zxing.CaptureActivity;
import com.baitan001.android.zxing.IntentIntegrator;
import com.baitan001.android.zxing.IntentResult;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BookWizard extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Bitmap photo = null;
	private String photoPath = null;
	Editable et;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookwizard);

		TextView tv_isbn = (TextView) findViewById(R.id.newitem_ISBN);
		et = tv_isbn.getEditableText();
		
		initBtnLsnr();
		

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		
		if (requestCode == IntentIntegrator.REQUEST_CODE){
			et.clear();
			et.append(data.getStringExtra("SCAN_RESULT"));
		}else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Bundle extras = data.getExtras();
				photoPath = data.getDataString();
				try {
					photo = MediaStore.Images.Media.getBitmap(
							this.getContentResolver(), data.getData());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// extras.get("data");
				ImageView img = (ImageView) findViewById(R.id.newitem_preview);
				img.setImageBitmap(photo);
				// setContentView(img);
				// Image captured and saved to fileUri specified in the Intent
				// android.widget.Toast.makeText(this, "Image saved to:\n" +
				// data.getData(), android.widget.Toast.LENGTH_LONG).show();
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		}

	}

	private void initBtnLsnr() {
		// TODO initiate view listener

		Button photoBtn = (Button) findViewById(R.id.newitem_photo);
		Button publishBtn = (Button) findViewById(R.id.newitem_publish);
		Button swipe = (Button) findViewById(R.id.newitem_swipe);
		photoBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new
				// File(
				// Environment.getExternalStorageDirectory(), "temp.jpg")));
				
				startActivityForResult(intent,
						CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
		});
		publishBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				publishItem();
			}

		});
		
		swipe.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BookWizard.this,CaptureActivity.class);
			      intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
			      intent.putExtra("SCAN_WIDTH", 800);
			      intent.putExtra("SCAN_HEIGHT", 200);
			      intent.putExtra("PROMPT_MESSAGE", "请将相机对准条形码，便于对焦，不要靠得太近");
				startActivityForResult(intent, IntentIntegrator.REQUEST_CODE);
			}
		});
		
	}

	protected void publishItem() {
		// TODO Auto-generated method stub

		//File aFile = new File("/sdcard/layout.png");
		File aFile = new File("/data/data/com.miui.camera/files/image-temp.jpg");
		HttpClient httpclient = new DefaultHttpClient();
		String urlStr = "http://airs2012.sinaapp.com/post2.php";
		HttpPost httppost = new HttpPost(urlStr);
		MultipartEntity mpEntity = new MultipartEntity();
		String requestParams = null;
		JSONObject jObj = new JSONObject();
		try {
			jObj.put("isbn13", "9787800805431");
			jObj.put("school", "北京邮电大学");
			jObj.put("username", UserInfo.getNickname());
			jObj.put("email", "woailiuziwen@gmail.com");
			jObj.put("sessionid", UserInfo.getSessionid());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		requestParams = jObj.toString();
		Log.i("reqParam", requestParams);
		
        FileBody fileBody = new FileBody(aFile);  
        StringBody stringBody = null;
		try {
			stringBody = new StringBody(requestParams);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		mpEntity.addPart("file", fileBody);  
        mpEntity.addPart("request", stringBody);

		
		httppost.setEntity(mpEntity);
	
		
		HttpResponse response = null;
		try {
			response = httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Header[] headers = response.getAllHeaders();
		int ia = response.getStatusLine().getStatusCode();
		Log.i("code",""+ia);

		for (int i = 0; i < headers.length; i++)
			Log.i(headers[i].getName(), headers[i].getValue());

		HttpEntity entity = response.getEntity();
		StringBuffer sb = new StringBuffer();
		InputStream is;
		BufferedReader br = null;
		try {
			is = entity.getContent();
			br = new BufferedReader(new InputStreamReader(is, "GB2312"));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String data = "";
		
		try {
			while ((data = br.readLine()) != null) {
				sb.append( data);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.v("test",sb.toString());
	}
	
	

	
}