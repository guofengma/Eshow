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
			holder.Pno = (TextView)convertView.findViewById(R.id.tv_2);
			holder.Pname = (TextView)convertView.findViewById(R.id.tv_4);
			holder.Pbrand = (TextView)convertView.findViewById(R.id.tv_6);
			holder.Psize = (TextView)convertView.findViewById(R.id.tv_8);
			holder.Pnum = (TextView)convertView.findViewById(R.id.tv_10);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		OrderItemEntity entity = entitys.get(position);
		holder.Pno.setText(entity.getPno());
		holder.Pbrand.setText(entity.getPbrand());
		holder.Pname.setText(entity.getPname());
		holder.Psize.setText(entity.getPsize() + "ย๋");
		holder.Pnum.setText(entity.getPnum() + "ิช");
		convertView.setTag(holder);
		return convertView;
	}
	
	private class ViewHolder{
		private TextView Pno, Pbrand, Pname, Psize, Pnum;
	}

}
