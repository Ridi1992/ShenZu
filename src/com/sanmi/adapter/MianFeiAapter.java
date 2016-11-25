package com.sanmi.adapter;

import java.util.ArrayList;

import com.bset.tool.Texttool;
import com.bset.tool.Timetool;
import com.sanmi.activity.R;
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
 * ѳ
 * @author Administrator
 *
 */
public class MianFeiAapter extends BaseAdapter {
	private ArrayList<InfoList> mArrayList;
	private Context mContext;
	private LayoutInflater mInflater;
	

	public MianFeiAapter(ArrayList<InfoList> mArrayList, Context mContext,
			LayoutInflater mInflater) {
		super();
		this.mArrayList = mArrayList;
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList!=null?mArrayList.size():0;
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
		ViewHorder mViewHorder=null;
		if (convertView==null) {
			mViewHorder=new ViewHorder();
			convertView=mInflater.inflate(R.layout.mianfei_item_chuzu, null);
			mViewHorder.mTouXiang=(CircularImage) convertView.findViewById(R.id.iv_mf_touxiang);
			mViewHorder.mName=(TextView) convertView.findViewById(R.id.mf_hire_info);
			mViewHorder.mMsg=(TextView) convertView.findViewById(R.id.mf_tv_name);
			mViewHorder.mJiNeng=(TextView) convertView.findViewById(R.id.mf_serv_me_info);
			mViewHorder.mJiNeng1=(TextView) convertView.findViewById(R.id.mf_serv_me_info1);
			mViewHorder.mJuLi=(TextView) convertView.findViewById(R.id.mf_distance);
			mViewHorder.mTime=(TextView) convertView.findViewById(R.id.mf_tv_f_time);
			convertView.setTag(mViewHorder);
		}
		else {
			mViewHorder=(ViewHorder) convertView.getTag();
		}
//		if (mArrayList!=null&&mArrayList.size()>0) {
//			mViewHorder.mName.setText(mArrayList.get(position).getName());
//		}
		
		InfoList infoList = mArrayList.get(position);
		
		ImageLoader loader = new ImageLoader(mContext);
		loader.DisplayImage(HttpURL.IMGPATH + infoList.getFace(), mViewHorder.mTouXiang);
		Log.i("aaaaaa", "aaaa="+infoList.getNickname());
		Texttool.setText(mViewHorder.mName, infoList.getNickname());
		Texttool.setText(mViewHorder.mMsg, infoList.getUsersign());
		Texttool.setText(mViewHorder.mJiNeng, infoList.getMyrange());
		Texttool.setText(mViewHorder.mJiNeng1, infoList.getAddress_city());
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
		private TextView mJiNeng1;
		private TextView mJuLi;
		private TextView mTime;
	}
	
	public void cleandata(){
		this.mArrayList=null;
	}

}
