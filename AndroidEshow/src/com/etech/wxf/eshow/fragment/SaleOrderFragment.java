package com.etech.wxf.eshow.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.activity.MainActivity;
import com.etech.wxf.eshow.activity.NewOrderActivity;
import com.etech.wxf.eshow.activity.SaleOrderActivity;
import com.etech.wxf.eshow.adapter.SaleOrderAdapter;
import com.etech.wxf.eshow.common.HttpgetEntity;
import com.etech.wxf.eshow.common.StringToJSON;
import com.etech.wxf.eshow.entity.SaleOrderEntity;
import com.etech.wxf.eshow.global.AppConst;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SaleOrderFragment extends Fragment{

	private String Uid;
	private HttpgetEntity getEntity;
	
	private ListView lst;
	private SaleOrderAdapter adapter;
	private List<SaleOrderEntity> entitys = new ArrayList<SaleOrderEntity>();
	
	private ViewGroup tv_top;
	private TextView tv1, tv2;
	
	private String url_get_saleorderlist = "http://" 
			+ AppConst.sServerURL 
			+ "/wxf/saleorders/get_all";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getBd();
		View view = inflater.inflate(R.layout.activity_list, container, false);
		new Thread(){
			public void run(){
				getSaleOrderList();
			}
		}.start();
		while(true){
			if(order_list != null){
				break;
			}
		}
		init(view);
		return view;
	}
	
	private void init(View view){
		tv_top = (ViewGroup)view.findViewById(R.id.tv_top);
		lst = (ListView)view.findViewById(R.id.lst);
		adapter = new SaleOrderAdapter(getActivity(), entitys);
		lst.setAdapter(adapter);
		lst.setOnItemClickListener(get_abo);
		tv1 = (TextView)view.findViewById(R.id.tv_top).findViewById(R.id.tv_title);
		tv2 = (TextView)view.findViewById(R.id.tv_top).findViewById(R.id.tv_edit);
		tv1.setText("销售单");
		tv2.setText("新增");
		tv2.setOnClickListener(add);
		
		setText(order_list);
	}
	
	private OnClickListener add = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), NewOrderActivity.class);
			intent.putExtra("Uid", Uid);
			intent.putExtra("index", 1);
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
			intent.putExtra("Oid", entitys.get(position).getOid());
			intent.putExtra("index", 0);
			startActivity(intent);
			getActivity().finish();
		}
		
	};
	
	private void setText(String task_list){
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
						SaleOrderEntity entity = new SaleOrderEntity();
						entity.setOid(json_item.optString("Oid"));
						entity.setOprice(json_item.optDouble("Oprice") + "元");
						entity.setOstatus(deal_status(json_item.optInt("Ostatus")));
						entity.setOtime(json_item.optString("Otime"));
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
		if(status == 201){
			deal_status = "销售单";
		}else if(status == 202){
			deal_status = "退货单";
		}else{
			deal_status = "未知状态";
		}
		return deal_status;
	}
	
	private void getBd(){
		Uid = ((MainActivity)getActivity()).getUid();
	}
	
	private String order_list;
	private void getSaleOrderList(){
		getEntity = new HttpgetEntity();
		try {
			order_list = getEntity.doGet(url_get_saleorderlist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
