package com.sanmi.adapter;

import java.util.ArrayList;

import com.sanmi.activity.R;
import com.sanmi.adapter.MianFeiAapter.ViewHorder;
import com.sanmi.fengzhuang.GchuzuDatas;
import com.sanmi.fengzhuang.GxunqiuDatas;
import com.sanmi.view.CircularImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * ŮѰ
 * @author Administrator
 *
 */
public class GxunqiuAdapter extends BaseAdapter {
	private ArrayList<GxunqiuDatas> mArrayList1;
	private Context mContext;
	private LayoutInflater mInflater;
	

	public GxunqiuAdapter(ArrayList<GxunqiuDatas> mArrayList1, Context mContext,
			LayoutInflater mInflater) {
		super();
		this.mArrayList1 = mArrayList1;
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList1!=null?mArrayList1.size():0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mArrayList1.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHorder mViewHorder=null;
		if (convertView==null) {
			mViewHorder=new ViewHorder();
			convertView=mInflater.inflate(R.layout.g_item_xunqiu, null);
			mViewHorder.mTouXiang=(CircularImage) convertView.findViewById(R.id.iv_gx_touxiang);
			mViewHorder.mName=(TextView) convertView.findViewById(R.id.gx_hire_info);
			mViewHorder.mMsg=(TextView) convertView.findViewById(R.id.gx_tv_name);
			mViewHorder.mYaoQiu=(TextView) convertView.findViewById(R.id.gx_serv_me_info);
			mViewHorder.mJuLi=(TextView) convertView.findViewById(R.id.gx_distance);
			mViewHorder.mTime=(TextView) convertView.findViewById(R.id.gx_tv_f_time);
			convertView.setTag(mViewHorder);
		}
		else {
			mViewHorder=(ViewHorder) convertView.getTag();
		}
		if (mArrayList1!=null&&mArrayList1.size()>0) {
			mViewHorder.mName.setText(mArrayList1.get(position).getName());
		}


		return convertView;
	}
	
	class ViewHorder{
		private com.sanmi.view.CircularImage mTouXiang;
		private TextView mName;
		private TextView mMsg;
		private TextView mYaoQiu;
		private TextView mJuLi;
		private TextView mTime;
		
	}

}
