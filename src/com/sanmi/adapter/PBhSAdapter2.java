package com.sanmi.adapter;

import java.util.ArrayList;

import com.sanmi.activity.R;
import com.sanmi.fengzhuang.Infor;
import com.sanmi.http.HttpURL;
import com.sanmi.loader.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PBhSAdapter2 extends BaseAdapter{
	private String[] mArrayList;
	private Activity context;
	private LayoutInflater inflater;
	private ImageLoader mImageLoader;
	public PBhSAdapter2(String[] mArrayList, Activity context,
			LayoutInflater inflater) {
		super();
		this.mArrayList = mArrayList;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.mImageLoader=new ImageLoader(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList!=null?mArrayList.length:0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mArrayList.length;
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
			convertView=inflater.inflate(R.layout.activity_hs_pb_item2, null);
			horder.mImageView=(ImageView) convertView.findViewById(R.id.iv_tu);
			convertView.setTag(horder);
		}
		else {
			horder=(ViewHorder) convertView.getTag();
		}
		horder.mImageView.setTag(mArrayList[position]);
		mImageLoader.DisplayImage(HttpURL.IMGPATH+mArrayList[position], horder.mImageView);
	
		return convertView;
	
	}
	class ViewHorder{
		private ImageView mImageView;
	}

}
