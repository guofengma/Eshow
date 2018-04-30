package com.etech.wxf.eshow.adapter;

import java.util.List;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.entity.ProductEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter{
	
	private Context context;
	private List<ProductEntity> entitys;
	
	public ProductAdapter(Context context, List<ProductEntity> entitys){
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_product, null);
			holder.Pname = (TextView)convertView.findViewById(R.id.tv_4);
			holder.Pno = (TextView)convertView.findViewById(R.id.tv_2);
			holder.Pbrand = (TextView)convertView.findViewById(R.id.tv_6);
			holder.Pstatus = (TextView)convertView.findViewById(R.id.tv_8);
			holder.Psize = (TextView)convertView.findViewById(R.id.tv_10);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		ProductEntity entity = entitys.get(position);
		holder.Pno.setText(entity.getPno());
		holder.Pname.setText(entity.getPname());
		holder.Pstatus.setText(entity.getPstatus());
		holder.Pbrand.setText(entity.getPbrand());
		holder.Psize.setText(entity.getPsize());
		convertView.setTag(holder);
		return convertView;
	}
	
	private class ViewHolder{
		private TextView Pname, Pbrand, Pno, Psize, Pstatus, Pnum;
	}

}
