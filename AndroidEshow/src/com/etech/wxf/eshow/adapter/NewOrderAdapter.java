package com.etech.wxf.eshow.adapter;

import java.util.List;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.entity.NewOrderEntity;
import com.etech.wxf.eshow.entity.ProductEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewOrderAdapter extends BaseAdapter{

	private Context context;
	private List<NewOrderEntity> entitys;
	
	public NewOrderAdapter(Context context, List<NewOrderEntity> entitys){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_new_order, null);
			holder.Pnum = (TextView)convertView.findViewById(R.id.tv_4);
			holder.Pno = (TextView)convertView.findViewById(R.id.tv_2);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		NewOrderEntity entity = entitys.get(position);
		holder.Pno.setText(entity.getPno());
		holder.Pnum.setText(entity.getPnum() + "¼þ");
		convertView.setTag(holder);
		return convertView;
	}
	
	private class ViewHolder{
		private TextView Pno, Pnum;
	}

}
