package com.sanmi.adapter;
import java.util.ArrayList;

import com.sanmi.activity.R;
import com.sanmi.fengzhuang.FuJinDatas;
import com.sanmi.view.CircularImage;
import com.zuwo.data.JiaoYi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 
 * @author Administrator
 *
 */
public class JiaoYiAdapter extends BaseAdapter{
	private ArrayList<JiaoYi>  mList;
	private Context mContext;
	private LayoutInflater mInflater;
	public JiaoYiAdapter(ArrayList<JiaoYi> mList, Context mContext) {
		super();
		this.mList = mList;
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mList!=null){
			return mList.size();
		}
		return 0;
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
		ViewHorder mViewHorder;
		if (convertView==null) {
			mViewHorder=new ViewHorder();
			convertView=mInflater.inflate(R.layout.list_item_jiaoyi, null);
			mViewHorder.mMoney=(TextView) convertView.findViewById(R.id.item_jiaoyi_money);
			mViewHorder.mTime=(TextView) convertView.findViewById(R.id.item_jiaoyi_time);
			mViewHorder.mDesc=(TextView) convertView.findViewById(R.id.item_jiaoyi_desc);
			convertView.setTag(mViewHorder);
		}else {
			mViewHorder=(ViewHorder) convertView.getTag();
		}
		ImageView mImg= (ImageView) convertView.findViewById(R.id.item_jiaoyi_img);
		
		JiaoYi jiaoYi = mList.get(position);
		
		int type = jiaoYi.getType();
		switch (type) {
		case 1:
			mImg.setImageResource(R.drawable.jy_shou);
			break;
		case 2:
			mImg.setImageResource(R.drawable.jy_zhi);
			break;
		}
		
		mViewHorder.mMoney.setText(jiaoYi.getUser_money());
		mViewHorder.mTime.setText(jiaoYi.getChange_time());
		mViewHorder.mDesc.setText(jiaoYi.getChange_desc());
		
		return convertView;
	}
	class ViewHorder{
//		private ImageView mImg;
		private TextView mMoney;
		private TextView mTime;
		private TextView mDesc;
	}

}
