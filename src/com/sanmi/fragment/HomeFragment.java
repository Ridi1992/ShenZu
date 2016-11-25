package com.sanmi.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;
import com.sanmi.activity.BoyActivity;
import com.sanmi.activity.GirlActivity;
import com.sanmi.activity.MianFeiActivity;
import com.sanmi.activity.MoneyActivity;
import com.sanmi.activity.PayRankActivity;
import com.sanmi.activity.R;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.loader.ImageLoader;
import com.zuwo.data.Banner;
import com.zuwo.userinfo.UserInfo;
import com.zuwo.userinfo.mUserInfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

/**
 * ҳFragment
 * 
 * @author Administrator
 * 
 */
public class HomeFragment extends Fragment implements OnClickListener,OnKeyListener {
	/**************** START VIEWPAGER ******************/
	public static final int STATE_1 = 0;
	public static final int STATE_2 = 1;
	public static final int STATE_3 = 2;
	// ʼviewpagerؼ
	private ViewPager mPager;
	private ViewGroup mGroup;
	private ImageView[] imageViews;// ʾҪСԲ
	private int currentItem;// ǰֲҳ

	private Timer mTimer;
	private TimerTask mTask;
	private Boolean isTaskRun = false;
	private ArrayList<Banner> data = new ArrayList<Banner>();
	private List<ImageView> imageViewsList = new ArrayList<ImageView>();// ؼ
	private ArrayList<View> bar = new ArrayList<View>();
	private Handler Allhandler = new Handler() {
		public void handleMessage(Message msg) {
			if (mPager.getCurrentItem() == mPager.getAdapter().getCount() - 1) {
				mPager.setCurrentItem(STATE_1,false);
			}else{
				mPager.setCurrentItem(currentItem);
			}
		};
	};

	/******************** END VIEWPAGER ****************************/
	private ImageView mMianFei, mMirl, mBoy, mTuHao;
	private boolean isYesOrNo;
	private LodingDialog dialog;
	private View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.homefragment, container, false);

		init(mView);// ʼ
		initView(mView);
		if (imageViewsList == null || imageViewsList.size() <= 0) {
			getBanner();
			// startPlay();
		} else {
			mVierAdapter(data);
		}
		return mView;
	}

	private void getBanner() {
		PublicRequest http = new PublicRequest(handler);
		http.Banner(getActivity());
	}

	/**
	 * ʼ
	 * 
	 * @param mView
	 */
	private void init(View mView) {
		mMianFei = (ImageView) mView.findViewById(R.id.iv_h_f01);
		mMirl = (ImageView) mView.findViewById(R.id.iv_h_f02);
		mBoy = (ImageView) mView.findViewById(R.id.iv_h_f03);
		mTuHao = (ImageView) mView.findViewById(R.id.iv_h_f04);

		mMianFei.setOnClickListener(this);
		mMirl.setOnClickListener(this);
		mBoy.setOnClickListener(this);
		mTuHao.setOnClickListener(this);

		/********************* жǷǵһν ***************************/
		SharedPreferences pref = getActivity().getSharedPreferences("login", 0);
		// ȡӦֵûиֵ˵δд룬trueΪĬֵ
		isYesOrNo = pref.getBoolean("isYesOrNo", true);
		/********************* жǷǵһν ***************************/
	}
	/**
	 * onClick
	 */
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.iv_h_f01:
			intent.setClass(getActivity(), MianFeiActivity.class);
			break;
		case R.id.iv_h_f02:
			Log.i("aaaaaa", "iiiii="+mUserInfo.GetUserInfo(getActivity()).getRank_id());
			if (mUserInfo.GetUserInfo(getActivity()).getRank_id().equals("1")) {
				intent.setClass(getActivity(), PayRankActivity.class);
				com.bset.tool.Toast.ToastMe(getActivity(), "你还是不是会员");
			} else {
				intent.setClass(getActivity(), GirlActivity.class);
			}
			break;
		case R.id.iv_h_f03:
			if (mUserInfo.GetUserInfo(getActivity()).getRank_id().equals("1")) {
				intent.setClass(getActivity(), PayRankActivity.class);
				com.bset.tool.Toast.ToastMe(getActivity(), "你还是不是会员");
			}else
				intent.setClass(getActivity(), BoyActivity.class);
			break;
		case R.id.iv_h_f04:
			if (mUserInfo.GetUserInfo(getActivity()).getRank_id().equals("1")) {
				intent.setClass(getActivity(), PayRankActivity.class);
				com.bset.tool.Toast.ToastMe(getActivity(), "你还是不是会员");
			}else
				intent.setClass(getActivity(), MoneyActivity.class);
			break;
		default:
			break;
		}
		getActivity().startActivity(intent);

	}

	/**************** START VIEWPAGER ******************/
	private void initView(View mView) {
		mPager = (ViewPager) mView.findViewById(R.id.guidePages);
		mGroup = (ViewGroup) mView.findViewById(R.id.viewGroup);
	
		// imageViews = new ImageView[imagesResIds.length];
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				if (dialog != null) {
					dialog.dismiss();
				}
				switch (msg.what) {
				case Constants.BANNER:
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if (jsonObj.getString("code").equals("1")) {
						String jsonData = jsonObj.getString("data");
						data = JsonUtil.instance().fromJson(jsonData,
								new TypeToken<ArrayList<Banner>>() {
								}.getType());
						if (data != null) {
							mVierAdapter(data);
						}
					} else {
						Toast.makeText(getActivity(),
								jsonObj.getString("message"), 0).show();
					}
					break;
				case 404:
					Toast.makeText(getActivity(), msg.obj.toString(), 0).show();
					break;
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		};
	};

	/**
	 * ͼƬСԲ
	 */
	private void mVierAdapter(ArrayList<Banner> data) {
		mPager.setFocusable(true);// н
		imageViewsList.clear();
		for (Banner banner : data) {
			ImageView view2 = new ImageView(getActivity());
			ImageLoader loader = new ImageLoader(getActivity());
			loader.DisplayImage(banner.getItem_url(), view2);// 通过地址放图片
			// view2.setImageResource(imageID);
			view2.setScaleType(ScaleType.FIT_XY);
			imageViewsList.add(view2);
		}
		imageViews = new ImageView[imageViewsList.size()];

		for (int i = 0; i < imageViewsList.size(); i++) {
			ImageView imageView = new ImageView(getActivity());
			imageView = new ImageView(getActivity());
			imageView.setLayoutParams(new LayoutParams(20, 20));
			imageView.setPadding(5, 0, 5, 0);
			imageViews[i] = imageView;
			if (i == 0) {
				imageViews[i].setImageDrawable(getResources().getDrawable(
						R.drawable.page_focused));
			} else {
				imageViews[i].setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));
			}

			mGroup.addView(imageViews[i]);
		}
		// һ
		mPager.setAdapter(new MyPagerAdapter());
		// һ1.ͼƬĻ 2.ѡеʱСԲ
		mPager.setOnPageChangeListener(new MyPageChangeListener());
		mPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		if (imageViewsList != null && imageViewsList.size() > 0) {
			startPlay();
		}
	}

	/**
	 * ʼֲͼл
	 */
	private void startPlay() {
		isTaskRun = true;
		if (mTimer != null) {
			mTimer.cancel();
		}
		mTimer = new Timer();

		mTask = new TimerTask() {
			@Override
			public void run() {
				//
				synchronized (mPager) {
					currentItem = (currentItem + 1) % imageViewsList.size();
					Allhandler.obtainMessage().sendToTarget();
				}
			}
		};
		mTimer.schedule(mTask, 3 * 1000, 3 * 1000);// Զлʱ䣬λǺ룬2*1000ʾ2
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (imageViewsList != null && imageViewsList.size() > 0) {
			stopTask();
		}

	}

	private void stopTask() {
		isTaskRun = false;
		mTimer.cancel();
	}

	/**
	 * ڲViewPagerҳ
	 * 
	 */
	private class MyPagerAdapter extends PagerAdapter {
		/**
		 * 
		 */
		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(imageViewsList.get(position));
		}

		/**
		 * 
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(imageViewsList.get(position));
			return imageViewsList.get(position);
		}

		@Override
		public int getCount() {
			return imageViewsList.size();
		}

		/**
		 * View
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

		@Override
		public void finishUpdate(View arg0) {
		}

	}

	/**
	 * ViewPager
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		boolean isAutoPlay = false;

		@Override
		public void onPageScrollStateChanged(int state) {
			switch (state) {
			case STATE_2://
				isAutoPlay = false;
				break;
			case STATE_3:
				isAutoPlay = true;
				break;
			case STATE_1:
				if (mPager.getCurrentItem() == mPager.getAdapter().getCount() - 1
						&& !isAutoPlay) {
					mPager.setCurrentItem(STATE_1,false);
				}
				else if (mPager.getCurrentItem() == STATE_1 && !isAutoPlay) {
					mPager.setCurrentItem(mPager.getAdapter().getCount() - 1,false);
				}
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		/**
		 * 
		 */
		@Override
		public void onPageSelected(int pos) {
			for (int i = 0; i < imageViews.length; i++) {
				currentItem = pos;
				if (i == pos) {
					imageViews[i].setImageDrawable(getResources().getDrawable(
							R.drawable.page_focused));

				} else {
					imageViews[i].setImageDrawable(getResources().getDrawable(
							R.drawable.page_unfocused));
				}
			}
		}
	}

	/******************** END VIEWPAGER ****************************/

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
