package com.sanmi.adapter;

import java.util.ArrayList;

import com.sanmi.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * ҵ
 * @author Administrator
 *
 */
public class MeAdapter2 extends BaseAdapter {
	private ArrayList<String> mList;
	private Context context;
	private LayoutInflater inflater;
	

	public MeAdapter2(ArrayList<String> mList, Context context,
			LayoutInflater inflater) {
		super();
		this.mList = mList;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
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
			convertView=inflater.inflate(R.layout.me_listview02, null);
			convertView.setTag(mHorder);
		}
		else{
			mHorder=(ViewHorder) convertView.getTag();
		}
		if (mList.size()>0&&mList!=null) {
			mHorder.mName.setText(mList.get(position));
		}
		return convertView;
	}
	class ViewHorder{
		private TextView mName;
		
	}

}
