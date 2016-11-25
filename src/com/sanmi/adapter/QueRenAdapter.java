package com.sanmi.adapter;

import java.util.ArrayList;

import com.sanmi.activity.R;
import com.sanmi.view.CircularImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * ȷ
 * @author Administrator
 *
 */
public class QueRenAdapter extends BaseAdapter {
	private ArrayList<String>  mArrayList;
	private Context mContext;
	private LayoutInflater inflater;
	

	public QueRenAdapter(ArrayList<String> mArrayList, Context mContext,
			LayoutInflater inflater) {
		super();
		this.mArrayList = mArrayList;
		this.mContext = mContext;
		this.inflater = LayoutInflater.from(mContext);
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
		ViewHorder mHorder=null;
		if (convertView==null) {
			mHorder=new ViewHorder();
			convertView=inflater.inflate(R.layout.activity_meyue_item, null);
			mHorder.mTou=(CircularImage) convertView.findViewById(R.id.meyue_touxiang);
			mHorder.mName=(TextView) convertView.findViewById(R.id.meyue_name);
			mHorder.mTime=(TextView) convertView.findViewById(R.id.meyue_time);
			mHorder.mPnum=(TextView) convertView.findViewById(R.id.meyue_pnum);
			mHorder.mText=(TextView) convertView.findViewById(R.id.meyue_text);
			convertView.setTag(mHorder);
			
		}
		else {
			mHorder=(ViewHorder) convertView.getTag();
		}
		
		if (mArrayList!=null&&mArrayList.size()>0) {
			mHorder.mName.setText(mArrayList.get(position));
		}
		
		return convertView;
	}
	class ViewHorder{
		private com.sanmi.view.CircularImage mTou;
		private TextView  mName;
		private TextView  mTime;
		private TextView  mPnum;
		private TextView  mText;
	}

}
