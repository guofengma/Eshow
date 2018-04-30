package com.etech.wxf.eshow.adapter;

import java.util.List;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.entity.PurchaseOrderEntity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PurchaseOrderAdapter extends BaseAdapter{
	
	private Context context;
	private List<PurchaseOrderEntity> entitys;
	
	public PurchaseOrderAdapter(Context context, List<PurchaseOrderEntity> entitys){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_purchaseorder, null);
			holder.POtime = (TextView)convertView.findViewById(R.id.tv_2);
			holder.POstatus = (TextView)convertView.findViewById(R.id.tv_4);
			holder.POprice = (TextView)convertView.findViewById(R.id.tv_6);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		PurchaseOrderEntity entity = entitys.get(position);
		holder.POtime.setText(entity.getPOtime());
		holder.POstatus.setText(entity.getPOstatus());
		holder.POprice.setText(entity.getPOprice());
		convertView.setTag(holder);
		return convertView;
	}
	
	private class ViewHolder{
		private TextView POtime, POprice, POstatus;
	}

}
