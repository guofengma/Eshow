package com.etech.wxf.eshow.adapter;

import java.util.List;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.entity.SaleOrderEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SaleOrderAdapter extends BaseAdapter{

	private Context context;
	private List<SaleOrderEntity> entitys;
	
	public SaleOrderAdapter(Context context, List<SaleOrderEntity> entitys){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_saleorder, null);
			holder.Otime = (TextView)convertView.findViewById(R.id.tv_2);
			holder.Ostatus = (TextView)convertView.findViewById(R.id.tv_4);
			holder.Oprice = (TextView)convertView.findViewById(R.id.tv_6);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		SaleOrderEntity entity = entitys.get(position);
		holder.Otime.setText(entity.getOtime());
		holder.Ostatus.setText(entity.getOstatus());
		holder.Oprice.setText(entity.getOprice());
		convertView.setTag(holder);
		return convertView;
	}
	
	private class ViewHolder{
		private TextView Otime, Ostatus, Oprice;
	}

}
