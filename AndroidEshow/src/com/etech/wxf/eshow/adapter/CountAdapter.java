package com.etech.wxf.eshow.adapter;

import java.util.List;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.entity.CountEntity;
import com.etech.wxf.eshow.entity.NewOrderEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CountAdapter extends BaseAdapter{
	
	private Context context;
	private List<CountEntity> entitys;
	
	public CountAdapter(Context context, List<CountEntity> entitys){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_count, null);
			holder.tvmonth = (TextView)convertView.findViewById(R.id.tv_1);
			holder.tvsaleorder = (TextView)convertView.findViewById(R.id.tv_3);
			holder.tvresaleorder = (TextView)convertView.findViewById(R.id.tv_5);
			holder.tvpurchase = (TextView)convertView.findViewById(R.id.tv_7);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		CountEntity entity = entitys.get(position);
		holder.tvmonth.setText(entity.getPmonth());
		holder.tvsaleorder.setText(entity.getPsalenum() + "¼þ");
		holder.tvresaleorder.setText(entity.getPresalenum() + "¼þ");
		holder.tvpurchase.setText(entity.getPurchasenum() + "¼þ");
		convertView.setTag(holder);
		return convertView;
	}
	
	private class ViewHolder{
		private TextView tvsaleorder, tvresaleorder, tvpurchase, tvmonth;
	}

}
