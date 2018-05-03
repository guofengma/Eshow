package com.etech.wxf.eshow.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.adapter.OrderItemAdapter;
import com.etech.wxf.eshow.common.HttpgetEntity;
import com.etech.wxf.eshow.common.StringToJSON;
import com.etech.wxf.eshow.entity.OrderItemEntity;
import com.etech.wxf.eshow.entity.SaleOrderEntity;
import com.etech.wxf.eshow.global.AppConst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SaleOrderActivity extends Activity{
	
	private HttpgetEntity getEntity;
	
	private String url_get_purchase = "http://" 
			+ AppConst.sServerURL 
			+ "/wxf/purchaseorders/get_abo?POid=";
	
	private String url_get_saleorder = "http://" 
			+ AppConst.sServerURL 
			+ "/wxf/saleorders/get_abo?Oid=";
	
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tvtitle, tvedit;
	private ViewGroup tv_top;
	
	private ListView lst;
	private OrderItemAdapter adapter;
	private List<OrderItemEntity> entitys = new ArrayList<OrderItemEntity>();
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getBd();
		setContentView(R.layout.activity_order);
		new Thread(){
			public void run(){
				getOrderabo();
			}
		}.start();
		while(true){
			if(order_abo != null){
				break;
			}
		}
		init();
	}
	
	private String Uid;
	private String POid;
	private String Oid;
	private int index;
	private void getBd(){
		Intent intent = getIntent();
		try{
			Bundle bd = intent.getExtras();
			Uid = bd.getString("Uid");
			//Bid = bd.getString("Bid");
			index = bd.getInt("index");
			if(index == 2){
				POid = bd.getString("POid");
			}else if(index == 1){
				Oid = bd.getString("Oid");
			}
		}catch(Exception e){
			e.printStackTrace();
			index = 1;
		}
	}
	
	private String order_abo;
	private void getOrderabo(){
		getEntity = new HttpgetEntity();
		try {
			if(index == 1){
				order_abo = getEntity.doGet(url_get_saleorder + Oid);
			}else if(index == 2){
				order_abo = getEntity.doGet(url_get_purchase + POid);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void init(){
		tv_top = (ViewGroup)findViewById(R.id.tv_top);
		tvtitle = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_title);
		tvedit = (TextView)findViewById(R.id.tv_top).findViewById(R.id.tv_edit);
		tv2 = (TextView)findViewById(R.id.tv_2);
		tv4 = (TextView)findViewById(R.id.tv_4);
		tv6 = (TextView)findViewById(R.id.tv_6);
		tv1 = (TextView)findViewById(R.id.tv_1);
		tv3 = (TextView)findViewById(R.id.tv_3);
		tv5 = (TextView)findViewById(R.id.tv_5);
		if(index == 1){
			tvtitle.setText("订单详情");
			tv1.setText("订单创建时间：");
			tv3.setText("订单类型：");
			tv5.setText("订单价格：");
		}else if(index == 2){
			tvtitle.setText("采购单详情");
			tv1.setText("采购单创建时间：");
			tv3.setVisibility(View.GONE);
			tv4.setVisibility(View.GONE);
			tv5.setText("采购单价格：");
		}
		
		tvedit.setText("返回");
		tvedit.setOnClickListener(back);
		
		lst = (ListView)findViewById(R.id.lst);
		adapter = new OrderItemAdapter(this, entitys);
		lst.setAdapter(adapter);
		
		setText(order_abo);
	}
	
	private OnClickListener back = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(SaleOrderActivity.this, MainActivity.class);
			intent.putExtra("Uid", Uid);
			intent.putExtra("index", index);
			startActivity(intent);
			finish();
		}
		
	};
	
	private void setText(String order_abo){
		if(order_abo != null){
			JSONObject obj = StringToJSON.toJSONObject(order_abo);
			String string_data = obj.optString("data");
			JSONObject json_data = StringToJSON.toJSONObject(string_data);
			String order_item = null;
			if(index == 1){
				tv2.setText(json_data.optString("Otime"));
				tv4.setText(deal_status(json_data.optInt("Ostatus")));
				tv6.setText(json_data.optDouble("Oprice") + "元");
				order_item = json_data.optString("Order_items");
			}else if(index == 2){
				tv2.setText(json_data.optString("POtime"));
				//tv4.setText(deal_status(json_data.optInt("POstatus")));
				tv6.setText(json_data.optDouble("POprice") + "元");
				order_item = json_data.optString("POrder_items");
			}
			if(order_item != null){
				JSONArray json_items = StringToJSON.toJSONArray(order_item);
				entitys.clear();
				for(int i = 0; i < json_items.length(); i++){
					JSONObject json_item;
					try {
						json_item = json_items.getJSONObject(i);
						OrderItemEntity entity = new OrderItemEntity();
						entity.setPno(json_item.optString("Pno"));
						entity.setPbrand(json_item.optString("Pbrand"));
						entity.setPname(json_item.optString("Pname"));
						entity.setPsize(json_item.optInt("Psize"));
						entity.setPnum(json_item.optInt("Pnum"));
						entitys.add(entity);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
			
		}
	}
	
	private String deal_status(int status){
		String deal_status = "";
		if(status == 201){
			deal_status = "销售单";
		}else if(status == 202){
			deal_status = "退货单";
		}else if(status == 301){
			deal_status = "完全入库";
		}else if(status == 302){
			deal_status = "部分入库";
		}else if(status == 303){
			deal_status = "未收货";
		}else{
			deal_status = "未知状态";
		}
		return deal_status;
	}
}
