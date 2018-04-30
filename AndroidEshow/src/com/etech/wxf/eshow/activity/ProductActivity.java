package com.etech.wxf.eshow.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.common.HttpgetEntity;
import com.etech.wxf.eshow.common.HttppostEntity;
import com.etech.wxf.eshow.common.StringToJSON;
import com.etech.wxf.eshow.global.AppConst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ProductActivity extends Activity{
	
	private HttpgetEntity getEntity;
	private HttppostEntity postEntity;
	
	private String url_product_abo = "http://" 
			+ AppConst.sServerURL 
			+ "/wxf/products/get_abo?Pid=";
	
	private String url_update_productstatus = "http://" 
			+ AppConst.sServerURL 
			+ "/wxf/products/delete_product";
	
	private TextView tv1, tv2, tv3, tv4, tv6, tv8, tv10, tv12;
	private ViewGroup tv_top;
	private Button btn1, btn2, btn3;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getBd();
		setContentView(R.layout.activity_product);
		new Thread(){
			public void run(){
				getProductabo();
			}
		}.start();
		while(true){
			if(product_abo != null){
				break;
			}
		}
		init();
	}
	
	private String Uid;
	private String Pid;
	private int index;
	private void getBd(){
		Intent intent = getIntent();
		try{
			Bundle bd = intent.getExtras();
			Uid = bd.getString("Uid");
			Pid = bd.getString("Pid");
			index = bd.getInt("index");
		}catch(Exception e){
			e.printStackTrace();
			index = 1;
		}
	}
	
	private String product_abo;
	private void getProductabo(){
		getEntity = new HttpgetEntity();
		try {
			product_abo = getEntity.doGet(url_product_abo + Pid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void init(){
		tv_top = (ViewGroup)findViewById(R.id.tv_top);
		tv1 = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_title);
		tv2 = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_edit);
		tv3 = (TextView)findViewById(R.id.tv_2);
		tv4 = (TextView)findViewById(R.id.tv_4);
		tv6 = (TextView)findViewById(R.id.tv_6);
		tv8 = (TextView)findViewById(R.id.tv_8);
		tv10 = (TextView)findViewById(R.id.tv_10);
		tv12 = (TextView)findViewById(R.id.tv_12);
		btn1 = (Button)findViewById(R.id.btn_1);
		btn1.setOnClickListener(update_status);
		btn2 = (Button)findViewById(R.id.btn_2);
		btn2.setOnClickListener(create_order);
		btn3 = (Button)findViewById(R.id.btn_3);
		btn3.setOnClickListener(create_purchase);
		tv1.setText("商品详情");
		tv2.setText("返回");
		tv2.setOnClickListener(back);
		
		setText(product_abo);
	}
	
	private OnClickListener back = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ProductActivity.this, MainActivity.class);
			intent.putExtra("Uid", Uid);
			intent.putExtra("index", 0);
			startActivity(intent);
			finish();
		}
		
	};
	
	private void setText(String product_abo){
		if(product_abo != null){
			JSONObject obj = StringToJSON.toJSONObject(product_abo);
			String string_data = obj.optString("data");
			JSONObject json_data = StringToJSON.toJSONObject(string_data);
			tv3.setText(json_data.optString("Pno"));
			tv4.setText(json_data.optString("Pname"));
			tv6.setText(json_data.optString("Pbrand"));
			tv8.setText(deal_status(json_data.optInt("Pstatus")));
			tv10.setText(json_data.optInt("Psize") + "码");
			tv12.setText(json_data.optInt("Pnum") + "件");
		}
	}
	
	private String deal_status(int status){
		String deal_status = "";
		if(status == 101){
			deal_status = "在售";
		}else if(status == 102){
			deal_status = "已下架";
		}else{
			deal_status = "未知状态";
		}
		return deal_status;
	}
	
	private OnClickListener update_status = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			new Thread(){
				public void run(){
					postStatus();
				}
			}.start();
			while(true){
				if(response != null){
					break;
				}
			}
			Log.e("response", response);
			new Thread(){
				public void run(){
					getProductabo();
				}
			}.start();
			while(true){
				if(product_abo != null){
					break;
				}
			}
			init();
		}
		
	};
	
	private String response;
	void postStatus(){
		postEntity = new HttppostEntity();
		JSONObject obj = new JSONObject();
		try {
			obj.put("Pid", Pid);
			response = postEntity.doPost(obj, url_update_productstatus);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private OnClickListener create_order = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ProductActivity.this, NewOrderActivity.class);
			intent.putExtra("index", 1);
			intent.putExtra("Uid", Uid);
			//intent.putExtra("Pno", )
			startActivity(intent);
			finish();
		}
		
	};
	
	private OnClickListener create_purchase = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ProductActivity.this, NewOrderActivity.class);
			intent.putExtra("index", 2);
			intent.putExtra("Uid", Uid);
			//intent.putExtra("Pno", )
			startActivity(intent);
			finish();
		}
		
	};
	

}
