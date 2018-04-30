package com.etech.wxf.eshow.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.common.HttppostEntity;
import com.etech.wxf.eshow.common.StringToJSON;
import com.etech.wxf.eshow.global.AppConst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity{
	
	private EditText et1, et2;
	private TextView tv1, tv2;
	private Button btn2;
	
	private String login_url = "http://" 
			+ AppConst.sServerURL 
			+ "/wxf/users/login";

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
	}
	
	private void init(){
		et1 = (EditText)findViewById(R.id.et_1);
		et2 = (EditText)findViewById(R.id.et_2);
		tv1 = (TextView)findViewById(R.id.tv_1);
		tv2 = (TextView)findViewById(R.id.tv_2);
		btn2 = (Button)findViewById(R.id.btn_2);
		
		tv1.setText("用户名：");
		tv2.setText("密码：");
		et1.setHint("请输入用户名");
		et2.setHint("请输入密码");
		btn2.setText("登录");
		
		btn2.setOnClickListener(login_listener);
	};
	
	private OnClickListener login_listener = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(!isNull()){
				getText();
				new Thread(){
					public void run(){
						postText();
					}
				}.start();
				while(true){
					if(result_login != null){
						break;
					}
				}
				JSONObject json_obj = StringToJSON.toJSONObject(result_login);
				if(json_obj.optInt("status") == 200){
					String data = json_obj.optString("data");
					JSONObject json_data = StringToJSON.toJSONObject(data);
					String Uid = json_data.optString("Uid");
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					intent.putExtra("Uid", Uid);
					intent.putExtra("index", 0);
					startActivity(intent);
					finish();
				}else{
					Toast.makeText(LoginActivity.this, "系统异常", Toast.LENGTH_SHORT);
				}
			}
		}
		
	};
	
	private String Uname;
	private String Upwd;
	private void getText(){
		Uname = et1.getText().toString();
		Log.e("Uname", Uname);
		Upwd = et2.getText().toString();
		Log.e("Upwd", Upwd);
	}
	
	private boolean isNull(){
		if(et1.getText().toString().length() == 0){
			return true;
		}
		if(et2.getText().toString().length() == 0){
			return true;
		}
		return false;
	}
	
	private String result_login;
	private void postText(){
		JSONObject obj = new JSONObject();
		try {
			obj.put("Uname", Uname);
			obj.put("Upwd", Upwd);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("url", login_url);
		HttppostEntity httppost = new HttppostEntity();
		try {
			result_login = httppost.doPost(obj, login_url);
			Log.e("result", result_login);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("post", "error");
			e.printStackTrace();
		}
	}
}
