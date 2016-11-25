package com.sanmi.adapter;

import java.util.ArrayList;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bset.tool.Timetool;
import com.sanmi.activity.MeCallActivity;
import com.sanmi.activity.R;
import com.sanmi.http.HttpURL;
import com.sanmi.loader.ImageLoader;
import com.sanmi.view.CircularImage;
import com.zuwo.data.MeCallData;

public class MyCallAdapter extends BaseAdapter {
	private MeCallActivity mContext;
	private ArrayList<MeCallData> mList;
	private LayoutInflater mInflater;
	private ImageLoader imageLoader;

	public MyCallAdapter(MeCallActivity mContext, LayoutInflater mInflater) {
		super();
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		imageLoader = new ImageLoader(mContext);
	}

	public void setmList(ArrayList<MeCallData> mList) {
		this.mList = mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList != null ? mList.size() : 0;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHorder mHorder = null;
		if (convertView == null) {
			mHorder = new ViewHorder();
			convertView = mInflater.inflate(R.layout.mycall_item, null);
			mHorder.mhead = (CircularImage) convertView
					.findViewById(R.id.iv_mycall_head);
			mHorder.mName = (TextView) convertView
					.findViewById(R.id.tv_mycall_item_name);
			mHorder.mSex = (ImageView) convertView
					.findViewById(R.id.tv_mycall_item_sex);
			mHorder.hint = (ImageView) convertView.findViewById(R.id.hint);
			mHorder.mAge = (TextView) convertView
					.findViewById(R.id.tv_mycall_item_age);
			mHorder.mTime = (TextView) convertView
					.findViewById(R.id.tv_mycall_item_time);
			mHorder.mSkill = (TextView) convertView
					.findViewById(R.id.tv_mycall_item_skill);
			mHorder.mMake = (TextView) convertView
					.findViewById(R.id.tv_mycall_item_make);
			mHorder.mMoney = (TextView) convertView
					.findViewById(R.id.tv_mycall_item_money);
			mHorder.mState = (TextView) convertView
					.findViewById(R.id.tv_mycall_item_state);
			mHorder.mPay = (TextView) convertView
					.findViewById(R.id.tv_mycall_item_pay);
			mHorder.needtime = (TextView) convertView
					.findViewById(R.id.tv_mycall_item_needtime);
			mHorder.needtime2 = (TextView) convertView
					.findViewById(R.id.tv_mycall_item_needtime2);
			mHorder.pay = (TextView) convertView.findViewById(R.id.pay);
			convertView.setTag(mHorder);
		} else {
			mHorder = (ViewHorder) convertView.getTag();
		}
		String face = mList.get(position).getFace();
		if (face != null) {
			// 设置头像
			imageLoader.DisplayImage(HttpURL.IMGPATH + face, mHorder.mhead);
		}
		mHorder.mName.setText(mList.get(position).getNickname());
		String sex = mList.get(position).getSex();
		if (sex.equals("1")) {
			mHorder.mSex.setImageResource(R.drawable.nan);
		} else if (sex.equals("2")) {
			mHorder.mSex.setImageResource(R.drawable.nv);
		}
		String age = mList.get(position).getAge();
		if (age != null && !age.equals("")) {
			age += "岁";
		} else {
			age = "未知";
		}
		if (mList.get(position).getMessage().getType() == 1) {
			mHorder.hint.setImageResource(R.drawable.zuimg);
		} else {
			mHorder.hint.setImageResource(R.drawable.xunimg);
		}
		mHorder.mAge.setText(age);
		mHorder.mTime.setText(Timetool.trans_time(mList.get(position)
				.getMessage().getAdd_time()));
		mHorder.mSkill.setText(mList.get(position).getMessage().getMyrange()
				.replaceAll(";", "、"));
		mHorder.mMake.setText(mList.get(position).getMessage().getAddress());
		mHorder.mMoney
				.setText(mList.get(position).getMessage().getRent() + "元");
		mHorder.needtime.setText(mList.get(position).getMessage()
				.getSchedule_start()
				+ "至" + mList.get(position).getMessage().getSchedule_end());
		if (mList.get(position).getMessage().getStartdate() == null
				|| mList.get(position).getMessage().getStartdate().equals("0")
				&& mList.get(position).getMessage().getEnddate() == null
				|| mList.get(position).getMessage().getEnddate().equals("0")) {
			mHorder.needtime2.setText("没有发布档期时间");
		} else {
			mHorder.needtime2.setText(mList.get(position).getMessage()
					.getStartdate()
					+ "至" + mList.get(position).getMessage().getEnddate());
		}

		setviewAndbt(mList.get(position).getMessage().getType(),
				mList.get(position).getIsAccept(), mList.get(position)
						.getHasPay(), mList.get(position).getIsMy(),
				mList.get(position).getAppointment_id(), mList.get(position)
						.getMessage().getId(), mHorder.mState, mHorder.mPay,
				mHorder.pay, mList.get(position).getHasConfirm(),
				mList.get(position));
		mHorder.mhead.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mList.get(position).getIsMy()) {
					mContext.StartToIserinfoActivity(mList.get(position)
							.getMessage_userid());
				} else {
					mContext.StartToIserinfoActivity(mList.get(position)
							.getUser_id());
				}

			}
		});
		return convertView;
	}

	private void setviewAndbt(final int type, int isAccept, int hasPay,
			Boolean isMy, final String appointment_id, final String message_id,
			TextView Stateview, final TextView actionview, final TextView pay,
			int hasConfirm, final MeCallData meCallData) {
		actionview.setVisibility(View.VISIBLE);// 默认是显示的
		pay.setVisibility(View.VISIBLE);
		Stateview.setVisibility(View.VISIBLE);
		if (isMy) {// 我租了谁
			if (type == 1) {
				if (isAccept == 1) {
					actionview.setText("查看联系方式");
					if (hasPay == 1) {
						actionview.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								mContext.ShowTel(meCallData);
							}
						});
						if (hasConfirm == 0) {
							pay.setText("确认支付");
							Stateview.setText("等待确认支付");
							pay.setTextColor(Color.RED);
							pay.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									mContext.Confirm(appointment_id);
								}
							});
						} else {
							pay.setText("评价");
							Stateview.setText("已支付");
							pay.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									mContext.StartCommentActivity(
											meCallData.getMessage_userid(),
											appointment_id);
								}
							});
						}
					} else {
						pay.setVisibility(View.GONE);
						Stateview.setText("等待支付");
						actionview.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								android.widget.Toast.makeText(mContext,
										"查看联系方式前请先支付工资", Toast.LENGTH_SHORT).show();
								mContext.Pay(appointment_id);
							}
						});
					}
				} else {
					Stateview.setText("待接受");
					pay.setVisibility(View.GONE);
					actionview.setVisibility(View.GONE);
				}
			} else {
				if (isAccept == 1) {
					actionview.setText("查看联系方式");
					if (hasPay == 1) {
						actionview.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								mContext.ShowTel(meCallData);
							}
						});
						if (hasConfirm == 0) {
							pay.setVisibility(View.GONE);
							Stateview.setText("等待确认支付");
							
						} else {
							pay.setText("评价");
							Stateview.setText("已支付");
							pay.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									mContext.StartCommentActivity(
											meCallData.getMessage_userid(),
											appointment_id);
								}
							});
						}
					} else {
						pay.setVisibility(View.GONE);
						Stateview.setText("待支付");
						actionview.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								android.widget.Toast.makeText(mContext,
										"对方还未支付，无能查看联系方式", Toast.LENGTH_SHORT).show();
								;
							}
						});
					}
				} else {
					Stateview.setText("待接受");
					actionview.setVisibility(View.GONE);
					pay.setVisibility(View.GONE);
				}
			}

		} else {// 谁租了我
			if (type == 1) {
				if (isAccept == 0) {
					Stateview.setText("待接受");
					actionview.setText("接受");
					pay.setVisibility(View.GONE);
					actionview.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							mContext.Accept(appointment_id, message_id);
							
						}
					});
				} else {
					actionview.setText("查看联系方式");
					if (hasPay == 1) {
						Stateview.setText("已支付");
						actionview.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								mContext.ShowTel(meCallData);
							}
						});
						if (hasConfirm == 0) {
							Stateview.setText("等待确认支付");
							pay.setVisibility(View.GONE);
						} else {
							pay.setText("评价");
							Stateview.setText("已支付");
							pay.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									mContext.StartCommentActivity(
											meCallData.getUser_id(),
											appointment_id);
								}
							});
						}
					} else {
						Stateview.setText("等待支付");
						pay.setVisibility(View.GONE);
						actionview.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								android.widget.Toast.makeText(mContext,
										"对方还未支付，不能查看联系方式", Toast.LENGTH_SHORT).show();
								;
							}
						});
					}
				}
			} else {
				if (isAccept == 0) {
					Stateview.setText("待接受");
					actionview.setText("接受");
					pay.setVisibility(View.GONE);
					actionview.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							mContext.Accept(appointment_id, message_id);
						}
					});
				} else {
					actionview.setText("查看联系方式");
					if (hasPay == 1) {
						actionview.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								mContext.ShowTel(meCallData);
							}
						});
						if (hasConfirm == 0) {
							Stateview.setText("等待确认支付");
							pay.setText("确认支付");
							pay.setTextColor(Color.RED);
							pay.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									mContext.Confirm(appointment_id);
								}
							});
						} else {
							Stateview.setText("已支付");
							pay.setText("评价");
							pay.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									mContext.StartCommentActivity(
											meCallData.getUser_id(),
											appointment_id);
								}
							});
						}
					} else {
						Stateview.setText("等待支付");
						pay.setVisibility(View.GONE);
						actionview.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								android.widget.Toast.makeText(mContext,
										"查看联系方式前请先支付工资", Toast.LENGTH_SHORT).show();
								mContext.Pay(appointment_id);
							}
						});

					}
				}
			}
		}
	}

	class ViewHorder {
		private com.sanmi.view.CircularImage mhead;
		private TextView mName;
		private ImageView mSex;
		private ImageView hint;// 左上角标签
		private TextView mAge;
		private TextView mTime;
		private TextView mSkill;
		private TextView mMake;
		private TextView mMoney;
		private TextView mState;
		private TextView mPay;
		private TextView needtime;// 档期
		private TextView needtime2;// 档期
		private TextView pay;
	}

}
