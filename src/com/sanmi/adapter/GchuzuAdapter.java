package com.sanmi.adapter;

import java.util.ArrayList;

import com.bset.tool.Texttool;
import com.bset.tool.Timetool;
import com.sanmi.activity.R;
import com.sanmi.adapter.MianFeiAapter.ViewHorder;
import com.sanmi.fengzhuang.GchuzuDatas;
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
 * Ů
 * @author Administrator
 *
 */
public class GchuzuAdapter extends BaseAdapter {
	private ArrayList<InfoList> mArrayList1;
	private Context mContext;
	private LayoutInflater mInflater;
	

	public GchuzuAdapter(ArrayList<InfoList> mArrayList1, Context mContext,
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
			convertView=mInflater.inflate(R.layout.g_item_chuzu, null);
			mViewHorder.mTouXiang=(CircularImage) convertView.findViewById(R.id.iv_gc_touxiang);
			mViewHorder.mName=(TextView) convertView.findViewById(R.id.gc_hire_info);
			mViewHorder.mMsg=(TextView) convertView.findViewById(R.id.gc_tv_name);
			mViewHorder.mJiNeng=(TextView) convertView.findViewById(R.id.gc_serv_me_info);
			mViewHorder.mJuLi=(TextView) convertView.findViewById(R.id.gc_distance);
			mViewHorder.mTime=(TextView) convertView.findViewById(R.id.gc_tv_f_time);
			convertView.setTag(mViewHorder);
		}
		else {
			mViewHorder=(ViewHorder) convertView.getTag();
		}
//		if (mArrayList1!=null&&mArrayList1.size()>0) {
//			mViewHorder.mName.setText(mArrayList1.get(position).getName());
//		}
		InfoList infoList = mArrayList1.get(position);
		
		ImageLoader loader = new ImageLoader(mContext);
		loader.DisplayImage(HttpURL.IMGPATH + infoList.getFace(), mViewHorder.mTouXiang);
		Log.i("aaaaaa", "aaaa="+infoList.getNickname());
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
		this.mArrayList1=null;
	}

}
