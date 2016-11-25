package com.sanmi.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bset.tool.Texttool;
import com.sanmi.activity.MeCallActivity;
import com.sanmi.activity.MeFaBuActivity;
import com.sanmi.activity.MeNunberActivity;
import com.sanmi.activity.MySelfActivity;
import com.sanmi.activity.R;
import com.sanmi.activity.RenZhengActivity;
import com.sanmi.activity.SheZhiActivity;
import com.sanmi.activity.WanShanActivity;
import com.sanmi.http.HttpURL;
import com.sanmi.loader.ImageLoader;
import com.sanmi.view.CircularImage;
import com.zuwo.userinfo.UserInfo;
import com.zuwo.userinfo.mUserInfo;

/**
 * ҵFragment
 * 
 * @author Administrator
 * 
 */
public class MeFragment extends Fragment implements OnClickListener {
	private LinearLayout mLayout01, mLayout02, mLayout03, mLayout04, mLayout05,
			mLayout06, mLayout07, mLayout10;
	private com.sanmi.view.CircularImage mHead;
	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.mefragment01, null);
		init(mView);//
		setUserinfo();
		return mView;
	}
	/**
	 * 设置姓名和性别
	 */
	public void setUserinfo() {
		UserInfo info = mUserInfo.GetUserInfo(getActivity());
		Texttool.setText(mView, R.id.name, info.getNickname());
		ImageView sex = (ImageView) mView.findViewById(R.id.sex);
		String i=info.getRank_id();
		System.out.println(i+"%%%%%%%%%%%%%%%%%%");
		if (i.equals("1")) {
			sex.setVisibility(View.GONE);
		} else {
			sex.setImageResource(R.drawable.huiyuanbg);
		} 
		String imgurl = info.getFace();
		CircularImage head = (CircularImage) mView
				.findViewById(R.id.iv_mefragment_head);
		if (imgurl != null) {
			ImageLoader imageLoader = new ImageLoader(getActivity());
			imageLoader.DisplayImage(HttpURL.IMGPATH + imgurl, head);
		} else {
			head.setImageResource(R.drawable.none_face);
		}
	}

	/**
	 * ʼ
	 * 
	 * @param mView
	 */
	private void init(View mView) {
		mHead = (CircularImage) mView.findViewById(R.id.iv_mefragment_head);
		mLayout01 = (LinearLayout) mView.findViewById(R.id.R_01);
		mLayout02 = (LinearLayout) mView.findViewById(R.id.R_02);
		mLayout04 = (LinearLayout) mView.findViewById(R.id.R_04);
		mLayout06 = (LinearLayout) mView.findViewById(R.id.R_06);
		mLayout07 = (LinearLayout) mView.findViewById(R.id.R_07);
		mLayout10 = (LinearLayout) mView.findViewById(R.id.R_10);
		mLayout01.setOnClickListener(this);
		mLayout02.setOnClickListener(this);
		mLayout04.setOnClickListener(this);
		mLayout06.setOnClickListener(this);
		mLayout07.setOnClickListener(this);
		mLayout10.setOnClickListener(this);
		mHead.setOnClickListener(this);
	}

	/**
	 * onClick
	 */
	@Override
	public void onClick(View v) {
		Intent intent = null;
		if (v == mLayout01) {
			intent = new Intent(getActivity(), WanShanActivity.class);
		}
		if (v == mHead) {
			intent = new Intent(getActivity(), MySelfActivity.class);
			intent.putExtra("user_id", mUserInfo.GetUserInfo(getActivity()).getId());
		}
		if (v == mLayout02) {
			intent = new Intent(getActivity(), MeNunberActivity.class);
		}
//		if (v == mLayout03) {//
//			intent = new Intent(getActivity(), MeYueActivity.class);
//		}
		if (v == mLayout04) {
			intent = new Intent(getActivity(), RenZhengActivity.class);
		}
//		if (v == mLayout05) {//
//			intent = new Intent(getActivity(), WhoMeYueActivity.class);
//		}
		if (v == mLayout06) {
			intent = new Intent(getActivity(), MeFaBuActivity.class);
		}
		if (v == mLayout07) {
			intent = new Intent(getActivity(), SheZhiActivity.class);
		}
		if (v == mLayout10) {
			intent = new Intent(getActivity(), MeCallActivity.class);
		}

		getActivity().startActivity(intent);
	}
}
