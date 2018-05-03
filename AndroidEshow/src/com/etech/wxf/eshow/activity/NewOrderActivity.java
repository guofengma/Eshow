package com.etech.wxf.eshow.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.adapter.NewOrderAdapter;
import com.etech.wxf.eshow.adapter.OrderItemAdapter;
import com.etech.wxf.eshow.common.HttppostEntity;
import com.etech.wxf.eshow.common.StringToJSON;
import com.etech.wxf.eshow.entity.NewOrderEntity;
import com.etech.wxf.eshow.entity.OrderItemEntity;
import com.etech.wxf.eshow.global.AppConst;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class NewOrderActivity extends Activity{
	
	private TextView tv1, tv3, tvtitle, tvedit;
	private EditText et1;
	private ViewGroup tv_top;
	private RadioGroup rg;
	private RadioButton rb1, rb2;
	private Button btn1, btn2;
	private HttppostEntity postEntity;
	
	private ListView lst;
	private NewOrderAdapter adapter;
	private List<NewOrderEntity> entitys = new ArrayList<NewOrderEntity>();
	
	private String url_new_saleorder = "http://" 
			+ AppConst.sServerURL 
			+ "/wxf/saleorders/new_saleorder?Uid=";
	
	private String url_new_purchase = "http://" 
			+ AppConst.sServerURL 
			+ "/wxf/purchaseorders/new_purchaseorder?Uid=";
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getBd();
		setContentView(R.layout.activity_new_order);
		init();
	}
	
	private String Uid;
	private int index;
	private void getBd(){
		Intent intent = getIntent();
		try{
			Bundle bd = intent.getExtras();
			Uid = bd.getString("Uid");
			//Bid = bd.getString("Bid");
			index = bd.getInt("index");
		}catch(Exception e){
			e.printStackTrace();
			index = 1;
		}
	}
	
	void init(){
		tv_top = (ViewGroup)findViewById(R.id.tv_top);
		tvtitle = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_title);
		tvedit = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_edit);
		tv1 = (TextView)findViewById(R.id.tv_1);
		tv3 = (TextView)findViewById(R.id.tv_3);
		et1 = (EditText)findViewById(R.id.et_1);
		tv1.setText("请输入价格：");
		rg = (RadioGroup)findViewById(R.id.vg_1);
		rb1 = (RadioButton)findViewById(R.id.rb_1);
		rb2 = (RadioButton)findViewById(R.id.rb_2);
		btn1 = (Button)findViewById(R.id.btn_1);
		btn1.setOnClickListener(add_product);
		btn2 = (Button)findViewById(R.id.btn_2);
		btn2.setOnClickListener(new_order);
		
		if(index == 1){
			tvtitle.setText("订单详情");
		}else if(index == 2){
			tvtitle.setText("采购单详情");
			tv3.setVisibility(View.GONE);
			rg.setVisibility(View.GONE);
		}
		
		tvedit.setText("返回");
		tvedit.setOnClickListener(back);
		
		lst = (ListView)findViewById(R.id.lst);
		adapter = new NewOrderAdapter(this, entitys);
		lst.setAdapter(adapter);
		
	}
	
	String response = null;
	private OnClickListener new_order = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			JSONArray json_items = new JSONArray();
			JSONObject json_item = new JSONObject();
			final JSONObject json_data = new JSONObject();
			try {
				for(int i = 0; i < entitys.size(); i++){
					json_item.put("Pno", entitys.get(i).getPno());
					json_item.put("Pnum", entitys.get(i).getPnum());
					json_item.put("Pbrand", entitys.get(i).getPbrand());
					json_item.put("Psize", deal_size(entitys.get(i).getPsize()));
					json_items.put(json_item);
				}
				if(index == 1){
					json_data.put("Oprice", Double.parseDouble(et1.getText().toString()));
					if(rb1.isChecked()){
						json_data.put("Ostatus", 201);
					}else if(rb2.isChecked()){
						json_data.put("Ostatus", 202);
					}
					json_data.put("Order_items", json_items);
				}else if(index == 2){
					json_data.put("POprice", Double.parseDouble(et1.getText().toString()));
					json_data.put("POstatus", 301);
					json_data.put("POrder_items", json_items);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new Thread(){
				public void run(){
					try {
						if(index == 1){
							response = postEntity.doPost(json_data, url_new_saleorder + Uid);
						}else if(index == 2){
							response = postEntity.doPost(json_data, url_new_purchase + Uid); 
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			while(true){
				if(response != null){
					break;
				}
			}
			Intent intent = new Intent(NewOrderActivity.this, MainActivity.class);
			intent.putExtra("index", index);
			intent.putExtra("Uid", Uid);
			startActivity(intent);
			finish();
		}
		
	};
	
	private int deal_size(String size){
		int deal_size = 0;
		if(size.equals("XS")){
			deal_size = 1;
		}else if(size.equals("S")){
			deal_size = 2;
		}else if(size.equals("M")){
			deal_size = 3;
		}else if(size.equals("L")){
			deal_size = 4;
		}else if(size.equals("XL")){
			deal_size = 5;
		}else if(size.equals("XXL")){
			deal_size = 6;
		}else{
			deal_size = 0;
		}
		return deal_size;
	}
	
	private OnClickListener add_product = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			showDialog("请输入商品信息");
		}
		
	};
	
	private OnClickListener back = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(NewOrderActivity.this, MainActivity.class);
			intent.putExtra("Uid", Uid);
			intent.putExtra("index", index);
			startActivity(intent);
			finish();
		}
		
	};
	
	private void showDialog(String title){
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final LayoutInflater inflater = LayoutInflater.from(this);
		final View view = inflater.inflate(R.layout.layout_alert, null);
		builder.setTitle(title);
		final EditText et_name = (EditText)view.findViewById(R.id.alert_1);
		final EditText et_no = (EditText)view.findViewById(R.id.alert_2);
		final EditText et_3 = (EditText)view.findViewById(R.id.alert_3);
		final EditText et_4 = (EditText)view.findViewById(R.id.alert_4);
		et_name.setHint("请输入品牌");
		et_no.setHint("请输入货号");
		et_3.setHint("请输入尺码");
		et_4.setHint("请输入数量");
		builder.setView(view);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				NewOrderEntity entity = new NewOrderEntity();
				entity.setPno(et_no.getText().toString());
				Log.e("Pno", et_no.getText().toString());
				entity.setPnum(Integer.parseInt(et_4.getText().toString()));
				Log.e("Pnum", et_4.getText().toString());
				entity.setPbrand(et_name.getText().toString());
				Log.e("Pbrand", et_name.getText().toString());
				entity.setPsize(et_3.getText().toString());
				Log.e("Psize", et_3.getText().toString());
				entitys.add(entity);
				adapter.notifyDataSetChanged();
			}
		});
		builder.show();
	}

}
