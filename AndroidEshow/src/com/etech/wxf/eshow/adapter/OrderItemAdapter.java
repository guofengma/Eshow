package com.etech.wxf.eshow.adapter;

import java.util.List;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.entity.OrderItemEntity;
import com.etech.wxf.eshow.entity.SaleOrderEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderItemAdapter extends BaseAdapter{

	private Context context;
	private List<OrderItemEntity> entitys;
	
	public OrderItemAdapter(Context context, List<OrderItemEntity> entitys){
		this.context = context;
		this.entitys = entitys;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entitys.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return entitys.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_product_order, null);
			holder.Pno = (TextView)convertView.findViewById(R.id.tv_4);
			holder.Pbrand = (TextView)convertView.findViewById(R.id.tv_2);
			holder.Psize = (TextView)convertView.findViewById(R.id.tv_6);
			holder.Pnum = (TextView)convertView.findViewById(R.id.tv_8);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		OrderItemEntity entity = entitys.get(position);
		holder.Pno.setText(entity.getPno());
		holder.Pbrand.setText(entity.getPbrand());
		holder.Psize.setText(deal_size(entity.getPsize()));
		holder.Pnum.setText(entity.getPnum() + "¼þ");
		convertView.setTag(holder);
		return convertView;
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
			deal_size = "Î´Öª";
		}
		return deal_size;
	}
	
	private class ViewHolder{
		private TextView Pno, Pbrand, Pname, Psize, Pnum;
	}

}
