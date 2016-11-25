package com.sanmi.adapter;

import java.util.ArrayList;

import com.sanmi.activity.R;
import com.sanmi.fengzhuang.MxunqiuDatas;
import com.sanmi.view.CircularImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * Ѱ
 * @author Administrator
 *
 */
public class MianFeiXunQiuAdapter extends BaseAdapter{
	private ArrayList<MxunqiuDatas>  mArrayList2;
	private Context mContext;
	private LayoutInflater mInflater;
	

	public MianFeiXunQiuAdapter(ArrayList<MxunqiuDatas> mArrayList2,
			Context mContext, LayoutInflater mInflater) {
		super();
		this.mArrayList2 = mArrayList2;
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList2!=null?mArrayList2.size():0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mArrayList2.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHorder mViewHorder=null;
		if (convertView==null) {
			mViewHorder=new ViewHorder();
			convertView=mInflater.inflate(R.layout.mianfei_item_xunqiu, null);
			mViewHorder.mTouXiang=(CircularImage) convertView.findViewById(R.id.iv_mf_touxiang_xunqiu);
			mViewHorder.mName=(TextView) convertView.findViewById(R.id.mf_hire_info_xunqiu);
			mViewHorder.mMsg=(TextView) convertView.findViewById(R.id.mf_tv_name_xunqiu);
			mViewHorder.mYaoQiu=(TextView) convertView.findViewById(R.id.mf_serv_me_info_xunqiu);
			mViewHorder.mJuLi=(TextView) convertView.findViewById(R.id.mf_distance_xunqiu);
			mViewHorder.mTime=(TextView) convertView.findViewById(R.id.mf_tv_f_time_xunqiu);
			convertView.setTag(mViewHorder);
		}
		else {
			mViewHorder=(ViewHorder) convertView.getTag();
		}
		if (mArrayList2!=null&&mArrayList2.size()>0) {
			mViewHorder.mName.setText(mArrayList2.get(position).getName());
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
