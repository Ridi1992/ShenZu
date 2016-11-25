package com.sanmi.adapter;

import java.util.ArrayList;

import com.bset.tool.Timetool;
import com.sanmi.activity.R;
import com.sanmi.fengzhuang.MeDatas;
import com.sanmi.view.CircularImage;
import com.zuwo.data.MeFaBu;

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
public class MeAdapter extends BaseAdapter {
	private ArrayList<MeFaBu> mList;
	private Context mContext;
	private LayoutInflater mInflater;
	public MeAdapter(ArrayList<MeFaBu> mList, Context mContext,
			LayoutInflater mInflater) {
		super();
		this.mList = mList;
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList!=null?mList.size():0;
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
		ViewHorder mViewHorder=null;
		if (convertView==null) {
			mViewHorder=new ViewHorder();
			convertView=mInflater.inflate(R.layout.mefabu_itim, null);
			mViewHorder.mF=(TextView) convertView.findViewById(R.id.tv_mefabu_fanwei);
			mViewHorder.mQ=(TextView) convertView.findViewById(R.id.tv_mefabu_qubie);
			mViewHorder.mM=(TextView) convertView.findViewById(R.id.tv_mefabu_zujin);
			mViewHorder.mT=(TextView) convertView.findViewById(R.id.tv_mefabu_shijian);
			mViewHorder.mD=(TextView) convertView.findViewById(R.id.tv_mefabu_daizhi);
			convertView.setTag(mViewHorder);
		}
		else {
			mViewHorder=(ViewHorder) convertView.getTag();
		}
		if (mList!=null&&mList.size()>0) {
			mViewHorder.mF.setText(mList.get(position).getMyrange());
			mViewHorder.mM.setText(mList.get(position).getRent()+"元");
			mViewHorder.mT.setText(Timetool.trans_time(mList.get(position).getAdd_time()));
			mViewHorder.mD.setText(mList.get(position).getAddress());
			String i=mList.get(position).getType();
			if (i.equals("1")) {
			mViewHorder.mQ.setText("出租");
			}
			if (i.equals("2") ){
		    mViewHorder.mQ.setText("寻租");
			}
		}
		return convertView;
	}
	class ViewHorder{
		private TextView mF;
		private TextView mQ;
		private TextView mM;
		private TextView mD;
		private TextView mT;
	}

}
