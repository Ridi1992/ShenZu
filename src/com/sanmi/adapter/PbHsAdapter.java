package com.sanmi.adapter;

import java.util.ArrayList;

import com.sanmi.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PbHsAdapter extends BaseAdapter {
	private ArrayList<String> mArrayList;
	private Context context;
	private LayoutInflater inflater;
	

	public PbHsAdapter(ArrayList<String> mArrayList, Context context,
			LayoutInflater inflater) {
		super();
		this.mArrayList = mArrayList;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList!=null?mArrayList.size():0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mArrayList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHorder horder=null;
		if (convertView==null) {
			horder=new ViewHorder();
			convertView=inflater.inflate(R.layout.activity_hs_pb_item, null);
			horder.mTextView=(TextView) convertView.findViewById(R.id.tv_pb_item);
			convertView.setTag(horder);
		}
		else {
			horder=(ViewHorder) convertView.getTag();
		}
		if (mArrayList!=null&&mArrayList.size()>0) {
			horder.mTextView.setText(mArrayList.get(position));
		}
		switch (position) {
		case 0:
			horder.mTextView.setBackgroundResource(R.drawable.free_green);
			break;
		case 1:
			horder.mTextView.setBackgroundResource(R.drawable.free_blue);
			break;
		case 2:
			horder.mTextView.setBackgroundResource(R.drawable.free_rose);
			break;
		case 3:
			horder.mTextView.setBackgroundResource(R.drawable.free_purple);
			break;
		case 4:
			horder.mTextView.setBackgroundResource(R.drawable.free_yellow);
			break;
		case 5:
			horder.mTextView.setBackgroundResource(R.drawable.free_green);
			break;
		case 6:
			horder.mTextView.setBackgroundResource(R.drawable.free_blue);
			break;
		case 7:
			horder.mTextView.setBackgroundResource(R.drawable.free_rose);
			break;
		case 8:
			horder.mTextView.setBackgroundResource(R.drawable.free_purple);
			break;
		case 9:
			horder.mTextView.setBackgroundResource(R.drawable.free_yellow);
			break;
		case 10:
			horder.mTextView.setBackgroundResource(R.drawable.free_green);
			break;
		case 11:
			horder.mTextView.setBackgroundResource(R.drawable.free_blue);
			break;
		case 12:
			horder.mTextView.setBackgroundResource(R.drawable.free_rose);
			break;
		case 13:
			horder.mTextView.setBackgroundResource(R.drawable.free_purple);
			break;
		case 14:
			horder.mTextView.setBackgroundResource(R.drawable.free_yellow);
			break;
		}
		return convertView;
	
	}
	class ViewHorder{
		private TextView mTextView;
	}

}
