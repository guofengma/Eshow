package com.etech.wxf.eshow.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.activity.MainActivity;
import com.etech.wxf.eshow.adapter.CountAdapter;
import com.etech.wxf.eshow.common.HttpgetEntity;
import com.etech.wxf.eshow.common.StringToJSON;
import com.etech.wxf.eshow.entity.CountEntity;
import com.etech.wxf.eshow.global.AppConst;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CountFragment extends Fragment{
	
	private String Uid;
	private HttpgetEntity getEntity;
	
	private ListView lst;
	private CountAdapter adapter;
	private List<CountEntity> entitys = new ArrayList<CountEntity>();
	
	private ViewGroup tv_top;
	private TextView tv1, tv2;
	private Button btn1;
	private EditText et1;
	
	private String url_get_brand = "http://" 
			+ AppConst.sServerURL 
			+ "/wxf/counts/get_all_by_time_brand";
	
	private String url_get_no = "http://" 
			+ AppConst.sServerURL 
			+ "/wxf/counts/get_all_by_time_no";
	
	private String url_get_week = "http://" 
			+ AppConst.sServerURL 
			+ "/wxf/counts/get_num_by_time";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getBd();
		View view = inflater.inflate(R.layout.fragment_count, container, false);
		new Thread(){
			public void run(){
				getText();
			}
		}.start();
		new Thread(){
			public void run(){
				getText2();
			}
		}.start();
		new Thread(){
			public void run(){
				getText3();
			}
		}.start();
		while(true){
			if(brand_text != null && no_text != null){
				break;
			}
		}
		deal_text();
		init(view);
		return view;
	}
	
	private void getBd(){
		Uid = ((MainActivity)getActivity()).getUid();
	}
	
	private void init(View view){
		tv_top = (ViewGroup)view.findViewById(R.id.tv_top);
		lst = (ListView)view.findViewById(R.id.lst);
		tv1 = (TextView)view.findViewById(R.id.tv_top).findViewById(R.id.tv_title);
		tv2 = (TextView)view.findViewById(R.id.tv_top).findViewById(R.id.tv_edit);
		tv1.setText("统计数据");
		//tv2.setVisibility(View.GONE);
		tv2.setText("周报");
		tv2.setOnClickListener(get_week);
		btn1 = (Button)view.findViewById(R.id.btn_1);
		et1 = (EditText)view.findViewById(R.id.et_1);
		btn1.setOnClickListener(select);
		adapter = new CountAdapter(getActivity(), entitys);
		lst.setAdapter(adapter);
	}
	
	private OnClickListener get_week = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.e("data_week", week_text);
			if(week_text != null){
				JSONObject json_week = StringToJSON.toJSONObject(week_text);
				String data_text = json_week.optString("data");
				JSONArray json_data = StringToJSON.toJSONArray(data_text);
				entitys.clear();
				for(int i = 0; i < json_data.length(); i++){
					try {
						JSONObject json_item = json_data.getJSONObject(i);
						CountEntity entity = new CountEntity();
						entity.setPmonth(json_item.optString("time"));
						entity.setPurchasenum(json_item.optInt("Purchasenum"));
						entity.setPsalenum(json_item.optInt("Salenum"));
						entity.setPresalenum(json_item.optInt("RSalenum"));
						entitys.add(entity);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Log.e("entitys.size", entitys.size() + " ");
				if(entitys.size() == 0){
					Toast.makeText(getActivity(), "系统异常", Toast.LENGTH_SHORT);
				}else{
					adapter.notifyDataSetChanged();
				}
			}
			
			
		}
		
	};
	
	private OnClickListener select = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Log.e("data+brand", data_brand);
			Log.e("data_no", data_no);
			JSONArray array_brand = StringToJSON.toJSONArray(data_brand);
			JSONArray array_no = StringToJSON.toJSONArray(data_no);
			entitys.clear();
			for(int i = 0; i < array_brand.length(); i++){
				try {
					JSONObject json_item = array_brand.getJSONObject(i);
					String Pbrand = json_item.optString("Pbrand");
					Log.e("Pbrand", Pbrand);
					if(Pbrand.equals(et1.getText().toString())){
						JSONArray array_sale = StringToJSON.toJSONArray(json_item.optString("Psalenum"));
						JSONArray array_resale = StringToJSON.toJSONArray(json_item.optString("Presalenum"));
						JSONArray array_purchase = StringToJSON.toJSONArray(json_item.optString("Purchasenum"));
						for(int j = 0; j < 12; j++){
							CountEntity entity = new CountEntity();
							JSONObject obj_sale = array_sale.getJSONObject(j);
							JSONObject obj_resale = array_resale.getJSONObject(j);
							JSONObject obj_purchase = array_purchase.getJSONObject(j);
							entity.setPmonth(j + 1 + "月");
							entity.setPsalenum(obj_sale.optInt("num"));
							entity.setPresalenum(obj_resale.optInt("num"));
							entity.setPurchasenum(obj_purchase.optInt("num"));
							entitys.add(entity);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Log.e("test", array_no.length() + " ");
			for(int i = 0; i < array_no.length(); i++){
				Log.e("test", "e");
				try {
					Log.e("test", "e");
					JSONObject json_item = array_no.getJSONObject(i);
					String Pno = json_item.optString("Pno");
					Log.e("Pno", Pno);
					if(Pno.equals(et1.getText().toString())){
						JSONArray array_sale = StringToJSON.toJSONArray(json_item.optString("Psalenum"));
						JSONArray array_resale = StringToJSON.toJSONArray(json_item.optString("Presalenum"));
						JSONArray array_purchase = StringToJSON.toJSONArray(json_item.optString("Purchasenum"));
						for(int j = 0; j < 12; j++){
							CountEntity entity = new CountEntity();
							JSONObject obj_sale = array_sale.getJSONObject(j);
							JSONObject obj_resale = array_resale.getJSONObject(j);
							JSONObject obj_purchase = array_purchase.getJSONObject(j);
							entity.setPsalenum(obj_sale.optInt("num"));
							entity.setPresalenum(obj_resale.optInt("num"));
							entity.setPmonth(j + 1 + "月");
							entity.setPurchasenum(obj_purchase.optInt("num"));
							entitys.add(entity);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Log.e("entitys.size", entitys.size() + " ");
			if(entitys.size() == 0){
				Toast.makeText(getActivity(), "系统异常", Toast.LENGTH_SHORT);
			}else{
				adapter.notifyDataSetChanged();
			}
		}
		
	};
	
	private String brand_text;
	private String no_text;
	private void getText(){
		getEntity = new HttpgetEntity();
		try {
			brand_text = getEntity.doGet(url_get_brand);
			//no_text = getEntity.doGet(url_get_no);
			//Log.e("brand_text", brand_text);
			//Log.e("no_text", no_text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getText2(){
		getEntity = new HttpgetEntity();
		try {
			//brand_text = getEntity.doGet(url_get_brand);
			no_text = getEntity.doGet(url_get_no);
			//Log.e("brand_text", brand_text);
			//Log.e("no_text", no_text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String week_text;
	private void getText3(){
		getEntity = new HttpgetEntity();
		try {
			//brand_text = getEntity.doGet(url_get_brand);
			week_text = getEntity.doGet(url_get_week);
			//Log.e("brand_text", brand_text);
			//Log.e("no_text", no_text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String data_brand;
	private String data_no;
	private void deal_text(){
		if(brand_text != null && no_text != null){
			JSONObject json_brand = StringToJSON.toJSONObject(brand_text);
			JSONObject json_no = StringToJSON.toJSONObject(no_text);
			data_brand = json_brand.optString("data");
			data_no = json_no.optString("data");
		}else{
			Toast.makeText(getActivity(), "系统异常", Toast.LENGTH_SHORT);
		}
	}
}
