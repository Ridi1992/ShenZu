package com.sanmi.adapter;

import java.util.ArrayList;

import com.best.mesage.MessageList;
import com.bset.tool.Texttool;
import com.sanmi.activity.R;
import com.sanmi.fengzhuang.MsgDatas;
import com.sanmi.http.HttpURL;
import com.sanmi.loader.ImageLoader;
import com.sanmi.view.CircularImage;

import android.content.Context;
import android.graphics.Paint.Join;
import android.location.Criteria;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Ϣ
 * @author Administrator
 *
 */
public class MsgAdapter extends BaseAdapter {
	private MessageList mList;
	private Context mContext;
	private LayoutInflater mInflater;
	private ImageLoader imageLoader;
	public MsgAdapter( Context mContext,
			LayoutInflater mInflater) {
		super();
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		imageLoader=new ImageLoader(mContext);
	}

	public void clearn(){
		this.mList = null;
	}
	
	public void setmList(MessageList mList) {
		this.mList = mList;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mList!=null && mList.getData()!=null){
			return  mList.getData().size();
		}
		return 0;
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHorder horder=null;
		if (convertView==null) {
			horder=new ViewHorder();
			convertView=mInflater.inflate(R.layout.system_info, null);
			horder.mTouXiang=(CircularImage) convertView.findViewById(R.id.iv_m_touxiang);
			horder.mText=(TextView) convertView.findViewById(R.id.tv_m01);
			horder.mTime=(TextView) convertView.findViewById(R.id.tv_m02);
			convertView.setTag(horder);
		}else {
			horder=(ViewHorder) convertView.getTag();
		}
		
		Texttool.setText(horder.mText, mList.getData().get(position).getMessage());
		Texttool.setText(horder.mTime, mList.getData().get(position).getTime());
		String imgurl=mList.getData().get(position).getFace();
		if(imgurl!=null){
			imageLoader.DisplayImage(HttpURL.IMGPATH+imgurl, horder.mTouXiang);
		}else{
			horder.mTouXiang.setImageResource(R.drawable.morentu);
		}
		return convertView;
	}
	class ViewHorder{
		private CircularImage mTouXiang;
		private TextView mText;
		private TextView mTime;
	}
	

}
