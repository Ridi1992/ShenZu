package org.yanzi.ui;

import java.util.ArrayList;
import com.sanmi.activity.R;
import com.sanmi.fengzhuang.GridViewInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HorizontalListViewAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<GridViewInfo> mList;
	private LayoutInflater mInflater;
	public HorizontalListViewAdapter(Context mContext, ArrayList<GridViewInfo> mList,
			LayoutInflater mInflater) {
		super();
		this.mContext = mContext;
		this.mList = mList;
		this.mInflater = LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList!=null?mList.size():0;
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
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
			convertView=mInflater.inflate(R.layout.activity_gv_item01, null);
			mHorder.mtTextView=(TextView) convertView.findViewById(R.id.tv_gv01);
			convertView.setTag(mHorder);
		}
		else{
			mHorder=(ViewHorder) convertView.getTag();
		}
		if (mList!=null&&mList.size()>0) {
			mHorder.mtTextView.setText(mList.get(position).getmText());
		}
		switch (position) {
		case 0:
			mHorder.mtTextView.setBackgroundResource(R.drawable.free_green);
			break;
		case 1:
			mHorder.mtTextView.setBackgroundResource(R.drawable.free_blue);
			break;
		case 2:
			mHorder.mtTextView.setBackgroundResource(R.drawable.free_rose);
			break;
		case 3:
			mHorder.mtTextView.setBackgroundResource(R.drawable.free_purple);
			break;
		case 4:
			mHorder.mtTextView.setBackgroundResource(R.drawable.free_yellow);
			break;
		case 5:
			mHorder.mtTextView.setBackgroundResource(R.drawable.free_green);
			break;
		case 6:
			mHorder.mtTextView.setBackgroundResource(R.drawable.free_blue);
			break;
		case 7:
			mHorder.mtTextView.setBackgroundResource(R.drawable.free_rose);
			break;
		case 8:
			mHorder.mtTextView.setBackgroundResource(R.drawable.free_purple);
			break;
		case 9:
			mHorder.mtTextView.setBackgroundResource(R.drawable.free_yellow);
			break;
		case 10:
			mHorder.mtTextView.setBackgroundResource(R.drawable.free_green);
			break;
		case 11:
			mHorder.mtTextView.setBackgroundResource(R.drawable.free_blue);
			break;
		case 12:
			mHorder.mtTextView.setBackgroundResource(R.drawable.free_rose);
			break;
		case 13:
			mHorder.mtTextView.setBackgroundResource(R.drawable.free_purple);
			break;
		case 14:
			mHorder.mtTextView.setBackgroundResource(R.drawable.free_yellow);
			break;
	

		default:
			break;
		}
		return convertView;
	}
	class ViewHorder{
		private TextView mtTextView;
	}
}