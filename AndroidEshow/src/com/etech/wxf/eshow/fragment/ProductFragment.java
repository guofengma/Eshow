package com.etech.wxf.eshow.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.activity.MainActivity;
import com.etech.wxf.eshow.activity.NewProductActivity;
import com.etech.wxf.eshow.activity.ProductActivity;
import com.etech.wxf.eshow.adapter.ProductAdapter;
import com.etech.wxf.eshow.common.HttpgetEntity;
import com.etech.wxf.eshow.common.StringToJSON;
import com.etech.wxf.eshow.entity.ProductEntity;
import com.etech.wxf.eshow.global.AppConst;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ProductFragment extends Fragment{
	
	private String Uid;
	private HttpgetEntity getEntity;
	
	private ListView lst;
	private ProductAdapter adapter;
	private List<ProductEntity> entitys = new ArrayList<ProductEntity>();
	
	private ViewGroup tv_top;
	private TextView tv1, tv2;
	
	private String url_get_productlist = "http://" 
			+ AppConst.sServerURL 
			+ "/wxf/products/get_all";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getBd();
		View view = inflater.inflate(R.layout.activity_list, container, false);
		new Thread(){
			public void run(){
				getProductList();
			}
		}.start();
		while(true){
			if(product_list != null){
				break;
			}
		}
		Log.e("product_list", product_list);
		init(view);
		return view;
	}
	
	private void init(View view){
		tv_top = (ViewGroup)view.findViewById(R.id.tv_top);
		lst = (ListView)view.findViewById(R.id.lst);
		adapter = new ProductAdapter(getActivity(), entitys);
		lst.setAdapter(adapter);
		lst.setOnItemClickListener(get_abo);
		tv1 = (TextView)view.findViewById(R.id.tv_top).findViewById(R.id.tv_title);
		tv2 = (TextView)view.findViewById(R.id.tv_top).findViewById(R.id.tv_edit);
		tv1.setText("商品");
		tv2.setText("发布");
		tv2.setOnClickListener(add);
		
		setText(product_list);
	}
	
	private OnClickListener add = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), NewProductActivity.class);
			intent.putExtra("Uid", Uid);
			intent.putExtra("index", 0);
			startActivity(intent);
			getActivity().finish();
		}
		
	};
	
	private OnItemClickListener get_abo = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), ProductActivity.class);
			intent.putExtra("Uid", Uid);
			intent.putExtra("Pid", entitys.get(position).getPid());
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
						ProductEntity entity = new ProductEntity();
						entity.setPid(json_item.optString("Pid"));
						entity.setPname(json_item.optString("Pname"));
						entity.setPbrand(json_item.optString("Pbrand"));
						entity.setPno(json_item.optString("Pno"));
						entity.setPsize(json_item.optInt("Psize") + "码");
						entity.setPnum(json_item.optInt("Pnum"));
						entity.setPstatus(deal_status(json_item.optInt("Pstatus")));
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
		if(status == 101){
			deal_status = "在售";
		}else if(status == 102){
			deal_status = "已下架";
		}else{
			deal_status = "未知状态";
		}
		return deal_status;
	}
	
	private void getBd(){
		Uid = ((MainActivity)getActivity()).getUid();
	}
	
	private String product_list;
	private void getProductList(){
		getEntity = new HttpgetEntity();
		try {
			product_list = getEntity.doGet(url_get_productlist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}