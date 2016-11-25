package com.sanmi.adapter;
import com.bset.tool.Texttool;
import com.sanmi.activity.AcceptActivity;
import com.sanmi.activity.R;
import com.sanmi.loader.ImageLoader;
import com.sanmi.view.CircularImage;
import com.zuwo.data.Accept;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class AcceptAdapter extends BaseAdapter {
	private Accept  mList;
	private AcceptActivity mContext;
	private LayoutInflater mInflater;
	ViewHorder mViewHorder=null;
	private Boolean canAccept=true;
	private String mId;
	private ImageLoader imageLoader;

	
	public void setdata(Accept mList) {
		this.mList = mList;
	}

	public AcceptAdapter( AcceptActivity mContext,
			LayoutInflater mInflater,String mId) {
		super();
		this.mId=mId;
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		imageLoader=new ImageLoader(mContext);
	}

	@Override
	public int getCount() {
		if(mList!=null && mList.getMessagedata()!=null){
			this.canAccept=mList.getCanAccept();
			return mList.getMessagedata().size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			mViewHorder=new ViewHorder();
			convertView=mInflater.inflate(R.layout.activity_accept_item, null);
			mViewHorder.mName=(TextView) convertView.findViewById(R.id.tv_accept_item_name);
			mViewHorder.head=(CircularImage) convertView.findViewById(R.id.iv_me_touxiang);
//			mViewHorder.mJiaG=(TextView) convertView.findViewById(R.id.tv_accept_item_jiage);
			mViewHorder.mYes=(TextView) convertView.findViewById(R.id.tv_accept_item_yes);
//			mViewHorder.mNo=(TextView) convertView.findViewById(R.id.tv_accept_item_no);
			convertView.setTag(mViewHorder);
		}
		else {
			mViewHorder=(ViewHorder) convertView.getTag();
		}
		Texttool.setText(mViewHorder.mName,mList.getMessagedata().get(position).getNickname());
		String face=mList.getMessagedata().get(position).getFace();
		if(face!=null){
			//设置头像
			imageLoader.DisplayImage(face, mViewHorder.head);
		}
		
		if(canAccept){
			Texttool.setText(mViewHorder.mYes,"接受");
			mViewHorder.mYes.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {//接受预约
					mContext.accept(mList.getMessagedata().get(position).getId(),mId);
				}
			});
		}else{
			if(mList.getMessagedata().get(position).getIsAccept()==1){//已接收的预约则显示支付按钮
				if(mList.getMessagedata().get(position).getHasPay()==0){
					if(mList.getMessagedata().get(position).getType()==1){
						Texttool.setText(mViewHorder.mYes,"等待支付");
					}else{
						Texttool.setText(mViewHorder.mYes,"支付工资");
						mViewHorder.mYes.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								mContext.pay(mList.getMessagedata().get(position).getId());
							}
						});
					}
				}else{
					Texttool.setText(mViewHorder.mYes,"交易完成");
				}
			}else{
				mViewHorder.mYes.setVisibility(View.GONE);
			}
		}
		return convertView;
	}

	class ViewHorder{
		private TextView mName;
		private CircularImage head;
//		private TextView mJiaG;
		private TextView mYes;
//		private TextView mNo;
		
	}

}
