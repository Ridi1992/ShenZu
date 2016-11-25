package com.sanmi.adapter;
import java.util.ArrayList;

import com.sanmi.activity.R;
import com.sanmi.fengzhuang.FuJinDatas;
import com.sanmi.view.CircularImage;
import com.zuwo.data.ChongZhi;
import com.zuwo.data.TiXian;

import android.content.Context;
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
public class ChongZhiAdapter extends BaseAdapter{
	private ArrayList<ChongZhi>  mList;
	private Context mContext;
	private LayoutInflater mInflater;
	public ChongZhiAdapter(ArrayList<ChongZhi> mList, Context mContext) {
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
			convertView=mInflater.inflate(R.layout.list_item_chongzhi, null);
			mViewHorder.mMoney=(TextView) convertView.findViewById(R.id.item_chongzhi_money);
			mViewHorder.mDesc=(TextView) convertView.findViewById(R.id.item_chongzhi_desc);
			mViewHorder.mTime=(TextView) convertView.findViewById(R.id.item_chongzhi_time);
			convertView.setTag(mViewHorder);
		}else {
			mViewHorder=(ViewHorder) convertView.getTag();
		}
		
		ChongZhi chongZhi = mList.get(position);
		mViewHorder.mMoney.setText(chongZhi.getUser_money());
		mViewHorder.mDesc.setText(chongZhi.getChange_desc());
		mViewHorder.mTime.setText(chongZhi.getChange_time());
		
		return convertView;
	}
	class ViewHorder{
		private TextView mMoney;
		private TextView mDesc;
		private TextView mTime;
	}

}
