package com.etech.wxf.eshow.activity;

import com.etech.wxf.eshow.R;
import com.etech.wxf.eshow.fragment.CountFragment;
import com.etech.wxf.eshow.fragment.ProductFragment;
import com.etech.wxf.eshow.fragment.PurchaseOrderFragment;
import com.etech.wxf.eshow.fragment.SaleOrderFragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity{
	
	private TextView tv1,tv2,tv3,tv4;
	private int index = 0;
	public String Uid;
	public int Utype;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getBd();
		setContentView(R.layout.activity_main);
		
		tv1 = (TextView)findViewById(R.id.bar_text1);
		tv1.setOnClickListener(tabClickListener);
		tv2 = (TextView)findViewById(R.id.bar_text2);
		tv2.setOnClickListener(tabClickListener);
		tv3 = (TextView)findViewById(R.id.bar_text3);
		findViewById(R.id.bar_text3).setOnClickListener(tabClickListener);
		//tv3.setVisibility(View.GONE);
		tv4 = (TextView)findViewById(R.id.bar_text4);
		tv4.setOnClickListener(tabClickListener);
		Intent intent = getIntent();
		try{
			Bundle bd = intent.getExtras();
			int n = bd.getInt("index");
			if (n == 0 || n == 1 || n == 2 || n == 3 || n == 4 || n == 5) {
				index = n;
			}
		}catch (Exception e) {
			e.printStackTrace();
			Log.e("changeError", "false");
			index = 0;
		}
		SwitchFragment(index);
	}
	
	private void getBd(){
		Intent intent = getIntent();
		try{
			Bundle bd = intent.getExtras();
			int n = bd.getInt("index");
			if (n == 0 || n == 1 || n == 2) {
				index = n;
			}
			Uid = bd.getString("Uid");
			Utype = bd.getInt("Utype");
		}catch (Exception e) {
			e.printStackTrace();
			Log.e("changeError", "false");
			index = 2;
		}
	}
	
	public String getUid(){
		return Uid;
	}
	public int getUtype(){
		return Utype;
	}
	public int getIndex(){
		return index;
	}
	
	private OnClickListener tabClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.bar_text1:
				setTextColorChecked(tv1, 0);
				setTextColorUnChecked(tv2, 1);
				setTextColorUnChecked(tv3, 2);
				setTextColorUnChecked(tv4, 3);
				SwitchFragment(0);
				break;
			case R.id.bar_text2:
				setTextColorUnChecked(tv1, 0);
				setTextColorChecked(tv2, 1);
				setTextColorUnChecked(tv3, 2);
				setTextColorUnChecked(tv4, 3);
				SwitchFragment(1);
				break;
			case R.id.bar_text3:
				setTextColorUnChecked(tv1, 0);
				setTextColorUnChecked(tv2, 1);
				setTextColorChecked(tv3, 2);
				setTextColorUnChecked(tv4, 3);
				SwitchFragment(2);
				break;
			case R.id.bar_text4:
				setTextColorUnChecked(tv1, 0);
				setTextColorUnChecked(tv2, 1);
				setTextColorUnChecked(tv3, 2);
				setTextColorChecked(tv4, 3);
				SwitchFragment(3);
				break;
			}
		}
		
	};
	
	private void setTextColorChecked(TextView tv, int index){
		tv.setTextColor(getResources().getColor(R.color.cBlue));
		if(index == 0){
			tv.setCompoundDrawablesWithIntrinsicBounds(null,getResources()
					.getDrawable(R.drawable.ic_main_tab_1_p), null, null);
		}else if(index == 1){
			tv.setCompoundDrawablesWithIntrinsicBounds(null,getResources()
					.getDrawable(R.drawable.ic_main_tab_2_p), null, null);
		}else if(index == 2){
			tv.setCompoundDrawablesWithIntrinsicBounds(null,getResources()
					.getDrawable(R.drawable.ic_main_tab_3_p), null, null);
		}else if(index == 3){
			tv.setCompoundDrawablesWithIntrinsicBounds(null,getResources()
					.getDrawable(R.drawable.ic_main_tab_4_p), null, null);
		}
	}
	
	private void setTextColorUnChecked(TextView tv, int index){
		tv.setTextColor(getResources().getColor(R.color.cText));
		if(index == 0){
			tv.setCompoundDrawablesWithIntrinsicBounds(null,getResources()
					.getDrawable(R.drawable.ic_main_tab_1), null, null);
		}else if(index == 1){
			tv.setCompoundDrawablesWithIntrinsicBounds(null,getResources()
					.getDrawable(R.drawable.ic_main_tab_2), null, null);
		}else if(index == 2){
			tv.setCompoundDrawablesWithIntrinsicBounds(null,getResources()
					.getDrawable(R.drawable.ic_main_tab_3), null, null);
		}else if(index == 3){
			tv.setCompoundDrawablesWithIntrinsicBounds(null,getResources()
					.getDrawable(R.drawable.ic_main_tab_4), null, null);
		}
	}
	
	ProductFragment f1;
	SaleOrderFragment f2;
	PurchaseOrderFragment f3;
	CountFragment f4;
	
	private void SwitchFragment(int id){
		FragmentTransaction transaction = this.getFragmentManager()
				.beginTransaction();
		switch(id){
		case 0:
			hideFragment(transaction);
			if(f1 == null){
				f1 = new ProductFragment();
				transaction.add(R.id.fl_fragment_layout, f1);
			}
			transaction.show(f1);
			//transaction.replace(R.id.fl_fragment_layout, new InforFragment());
			break;
		case 1:
			hideFragment(transaction);
			if(f2 == null){
				f2 = new SaleOrderFragment();
				transaction.add(R.id.fl_fragment_layout, f2);
			}
			transaction.show(f2);
			//transaction.replace(R.id.fl_fragment_layout, new DIYFragment());
			break;
		case 2:
			hideFragment(transaction);
			if(f3 == null){
				f3 = new PurchaseOrderFragment();
				transaction.add(R.id.fl_fragment_layout, f3);
			}
			transaction.show(f3);
			//transaction.replace(R.id.fl_fragment_layout, new MssFragment());
			break;
		case 3:
			hideFragment(transaction);
			if(f4 == null){
				f4 = new CountFragment();
				transaction.add(R.id.fl_fragment_layout, f4);
			}
			transaction.show(f4);
			//transaction.replace(R.id.fl_fragment_layout, new MeFragment());
			break;
		}
		transaction.commitAllowingStateLoss();
	}
	protected void onResume(){
		super.onResume();
	}

	private void hideFragment(FragmentTransaction transaction){
		if(f1 != null){
			transaction.hide(f1);
		}if(f2 != null){
			transaction.hide(f2);
		}if(f3 != null){
			transaction.hide(f3);
		}if(f4 != null){
			transaction.hide(f4);
		}
	}


}
