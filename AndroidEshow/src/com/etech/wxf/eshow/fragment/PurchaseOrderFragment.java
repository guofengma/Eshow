package com.etech.wxf.eshow.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.activity.MainActivity;
import com.etech.wxf.eshow.activity.NewOrderActivity;
import com.etech.wxf.eshow.activity.SaleOrderActivity;
import com.etech.wxf.eshow.adapter.PurchaseOrderAdapter;
import com.etech.wxf.eshow.common.HttpgetEntity;
import com.etech.wxf.eshow.common.StringToJSON;
import com.etech.wxf.eshow.entity.PurchaseOrderEntity;
import com.etech.wxf.eshow.global.AppConst;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PurchaseOrderFragment extends Fragment{
	
	private String Uid;
	private HttpgetEntity getEntity;
	
	private ListView lst;
	private PurchaseOrderAdapter adapter;
	private List<PurchaseOrderEntity> entitys = new ArrayList<PurchaseOrderEntity>();
	
	private ViewGroup tv_top;
	private TextView tv1, tv2;
	
	private String url_get_Purchaseorderlist = "http://" 
			+ AppConst.sServerURL 
			+ "/wxf/purchaseorders/get_all";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getBd();
		View view = inflater.inflate(R.layout.activity_list, container, false);
		new Thread(){
			public void run(){
				getPurchaseOrderList();
			}
		}.start();
		while(true){
			if(order_list != null){
				Log.e("order_list", order_list);
				break;
			}
		}
		init(view);
		return view;
	}
	
	private void init(View view){
		tv_top = (ViewGroup)view.findViewById(R.id.tv_top);
		lst = (ListView)view.findViewById(R.id.lst);
		adapter = new PurchaseOrderAdapter(getActivity(), entitys);
		lst.setAdapter(adapter);
		lst.setOnItemClickListener(get_abo);
		tv1 = (TextView)view.findViewById(R.id.tv_top).findViewById(R.id.tv_title);
		tv2 = (TextView)view.findViewById(R.id.tv_top).findViewById(R.id.tv_edit);
		tv1.setText("采购单");
		tv2.setText("+");
		tv2.setOnClickListener(add);
		
		setText(order_list);
	}
	
	private OnClickListener add = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), NewOrderActivity.class);
			intent.putExtra("Uid", Uid);
			intent.putExtra("index", 2);
			startActivity(intent);
			getActivity().finish();
		}
		
	};
	
	private OnItemClickListener get_abo = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), SaleOrderActivity.class);
			intent.putExtra("Uid", Uid);
			intent.putExtra("POid", entitys.get(position).getPOid());
			intent.putExtra("index", 2);
			startActivity(intent);
			getActivity().finish();
		}
		
	};
	
	private void setText(String task_list){
		Log.e("list", task_list);
		if(task_list == null){
			Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT);
		}else{
			JSONObject json_response = StringToJSON.toJSONObject(task_list);
			if(json_response.optInt("status") == 200){
				try{
					String string_data = json_response.optString("data");
					JSONArray json_data = StringToJSON.toJSONArray(string_data);
					entitys.clear();
					for(int i = 0; i < json_data.length(); i++){
						JSONObject json_item = json_data.getJSONObject(i);
						PurchaseOrderEntity entity = new PurchaseOrderEntity();
						entity.setPOid(json_item.optString("POid"));
						entity.setPOprice(json_item.optDouble("POprice") + "元");
						entity.setPOstatus(deal_status(json_item.optInt("POstatus")));
						entity.setPOtime(json_item.optString("POtime"));
						entitys.add(entity);
					}
				}catch(Exception e){
					
				}
			}else{
				Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT);
			}
		}
	}
	
	private String deal_status(int status){
		String deal_status = "";
		if(status == 301){
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
	
	private void getBd(){
		Uid = ((MainActivity)getActivity()).getUid();
	}
	
	private String order_list;
	private void getPurchaseOrderList(){
		getEntity = new HttpgetEntity();
		try {
			order_list = getEntity.doGet(url_get_Purchaseorderlist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
