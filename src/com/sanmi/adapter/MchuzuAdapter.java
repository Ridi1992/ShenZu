package com.sanmi.adapter;

import java.util.ArrayList;

import com.bset.tool.Texttool;
import com.bset.tool.Timetool;
import com.sanmi.activity.R;
import com.sanmi.adapter.MianFeiAapter.ViewHorder;
import com.sanmi.fengzhuang.GchuzuDatas;
import com.sanmi.fengzhuang.MchuzuDatas;
import com.sanmi.http.HttpURL;
import com.sanmi.loader.ImageLoader;
import com.sanmi.view.CircularImage;
import com.zuwo.data.InfoList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 
 * @author Administrator
 *
 */
public class MchuzuAdapter extends BaseAdapter {
	private ArrayList<InfoList> mArrayList;
	private Context mContext;
	private LayoutInflater mInflater;
	

	public MchuzuAdapter(ArrayList<InfoList> mArrayList, Context mContext,
			LayoutInflater mInflater) {
		super();
		this.mArrayList = mArrayList;
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mArrayList!=null){
			return mArrayList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHorder mViewHorder;
		if (convertView==null) {
			mViewHorder=new ViewHorder();
			convertView=mInflater.inflate(R.layout.m_item_chuzu, null);
			mViewHorder.mTouXiang=(CircularImage) convertView.findViewById(R.id.iv_mc_touxiang);
			mViewHorder.mName=(TextView) convertView.findViewById(R.id.mc_hire_info);
			mViewHorder.mMsg=(TextView) convertView.findViewById(R.id.mc_tv_name);
			mViewHorder.mJiNeng=(TextView) convertView.findViewById(R.id.mc_serv_me_info);
			mViewHorder.mJuLi=(TextView) convertView.findViewById(R.id.mc_distance);
			mViewHorder.mTime=(TextView) convertView.findViewById(R.id.mc_tv_f_time);
			convertView.setTag(mViewHorder);
		}else {
			mViewHorder=(ViewHorder) convertView.getTag();
		}
		InfoList infoList = mArrayList.get(position);
		
		ImageLoader loader = new ImageLoader(mContext);
		loader.DisplayImage(HttpURL.IMGPATH+infoList.getFace(), mViewHorder.mTouXiang);
		Log.i("aaaaaa", HttpURL.IMGPATH + infoList.getFace());
		
		Texttool.setText(mViewHorder.mName, infoList.getNickname());
		Texttool.setText(mViewHorder.mMsg, infoList.getUsersign());
		Texttool.setText(mViewHorder.mJiNeng, infoList.getMyrange());
		Texttool.setText(mViewHorder.mJuLi, infoList.getDistance());
		String time = Timetool.getStandardDate(infoList.getAdd_time());
		Texttool.setText(mViewHorder.mTime, time);

		return convertView;
	}
	
	class ViewHorder{
		private CircularImage mTouXiang;
		private TextView mName;
		private TextView mMsg;
		private TextView mJiNeng;
		private TextView mJuLi;
		private TextView mTime;
	}
	public void cleandata(){
		this.mArrayList=null;
	}

}
