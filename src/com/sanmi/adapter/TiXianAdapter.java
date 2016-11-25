package com.sanmi.adapter;
import java.util.ArrayList;

import com.sanmi.activity.R;
import com.sanmi.fengzhuang.FuJinDatas;
import com.sanmi.view.CircularImage;
import com.zuwo.data.TiXian;

import android.content.Context;
import android.graphics.Color;
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
public class TiXianAdapter extends BaseAdapter{
	private ArrayList<TiXian>  mList;
	private Context mContext;
	private LayoutInflater mInflater;
	public TiXianAdapter(ArrayList<TiXian> mList, Context mContext) {
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
			convertView=mInflater.inflate(R.layout.list_item_tixian, null);
			mViewHorder.mMoney=(TextView) convertView.findViewById(R.id.item_tixian_money);
			mViewHorder.mDesc=(TextView) convertView.findViewById(R.id.item_tixian_desc);
			mViewHorder.mTime=(TextView) convertView.findViewById(R.id.item_tixian_time);
			mViewHorder.mStatus=(TextView) convertView.findViewById(R.id.item_tixian_status);
			convertView.setTag(mViewHorder);
		}else {
			mViewHorder=(ViewHorder) convertView.getTag();
		}
		
		TiXian tiXian = mList.get(position);
		mViewHorder.mMoney.setText(tiXian.getAmount());
		mViewHorder.mDesc.setText("往"+tiXian.getBank_number().toString()+"卡，提取"+tiXian.getAmount().toString()+"元");
		mViewHorder.mTime.setText(tiXian.getAdd_time());
		if (tiXian.getIs_paid().equals("0")) {
			mViewHorder.mStatus.setText("待审核");
			mViewHorder.mStatus.setTextColor(mContext.getResources().getColor(R.color.chengse));
		}else if (tiXian.getIs_paid().equals("1")){
			mViewHorder.mStatus.setText("提现成功");
			mViewHorder.mStatus.setTextColor(Color.GREEN);
		}else if (tiXian.getIs_paid().equals("2")){
			mViewHorder.mStatus.setText("提现失败，请联系客服");
			mViewHorder.mStatus.setTextColor(Color.RED);
			
		}
		
		return convertView;
	}
	class ViewHorder{
		private TextView mMoney;
		private TextView mDesc;
		private TextView mTime;
		private TextView mStatus;
	}

}
