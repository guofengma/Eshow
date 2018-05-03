package com.etech.wxf.eshow.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.activity.MainActivity;
import com.etech.wxf.eshow.activity.NewProductActivity;
import com.etech.wxf.eshow.activity.ProductActivity;
import com.etech.wxf.eshow.adapter.ProductAdapter;
import com.etech.wxf.eshow.common.HttpgetEntity;
import com.etech.wxf.eshow.common.HttppostEntity;
import com.etech.wxf.eshow.common.StringToJSON;
import com.etech.wxf.eshow.entity.NewOrderEntity;
import com.etech.wxf.eshow.entity.ProductEntity;
import com.etech.wxf.eshow.global.AppConst;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ProductFragment extends Fragment{
	
	private String Uid;
	private HttpgetEntity getEntity;
	private HttppostEntity postEntity;
	
	private ListView lst;
	private ProductAdapter adapter;
	private List<ProductEntity> entitys = new ArrayList<ProductEntity>();
	
	private ViewGroup tv_top;
	private TextView tv1, tv2;
	
	private String url_get_productlist = "http://" 
			+ AppConst.sServerURL 
			+ "/wxf/products/get_all";
	
	private String url_update_productstatus = "http://" 
			+ AppConst.sServerURL 
			+ "/wxf/products/delete_product";

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
		tv1.setText("库存表");
		tv2.setText("+");
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
	
	private String Pid;
	private OnItemClickListener get_abo = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Pid = entitys.get(position).getPid();
			showDialog("下架", Pid);
		}
		
	};
	
	private void showDialog(String title, String Pid){
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View view = inflater.inflate(R.layout.layout_alert, null);
		builder.setTitle(title);
		final EditText et_name = (EditText)view.findViewById(R.id.alert_1);
		et_name.setVisibility(View.GONE);
		final EditText et_no = (EditText)view.findViewById(R.id.alert_2);
		et_no.setVisibility(View.GONE);
		final EditText et_3 = (EditText)view.findViewById(R.id.alert_3);
		et_3.setVisibility(View.GONE);
		final EditText et_4 = (EditText)view.findViewById(R.id.alert_4);
		et_4.setVisibility(View.GONE);
		et_name.setHint("请输入商品编号");
		et_no.setHint("请输入采购数量");
		builder.setView(view);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
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
			}
		});
		builder.show();
	}
	
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
						entity.setPsize(deal_size(json_item.optInt("Psize")));
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
	
	private String deal_size(int size){
		String deal_size = "";
		if(size == 1){
			deal_size = "XS";
		}else if(size == 2){
			deal_size = "S";
		}else if(size == 3){
			deal_size = "M";
		}else if(size == 4){
			deal_size = "L";
		}else if(size == 5){
			deal_size = "XL";
		}else if(size == 6){
			deal_size = "XXL";
		}else{
			deal_size = "未知";
		}
		return deal_size;
	}
	
	private OnClickListener update_status = new OnClickListener(){

		@Override
		public void onClick(View view) {
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
					getProductList();
				}
			}.start();
			while(true){
				if(product_list != null){
					break;
				}
			}
			init(view);
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
}