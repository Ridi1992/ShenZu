package com.sanmi.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.sanmi.http.HttpURL;
import com.sanmi.loader.ImageLoader;

public class PagerActivity extends Activity {
	/**************** START VIEWPAGER ******************/
	public static final int STATE_1 = 0;
	public static final int STATE_2 = 1;
	public static final int STATE_3 = 2;
	// ʼviewpagerؼ
	private ViewPager mPager;
	private ViewGroup mGroup;
	private ImageView[] imageViews;// ʾҪСԲ
	private int currentItem = 0;// ǰֲҳ
	private ScheduledExecutorService scheduledExecutorService;// Թ̶Ƶִ
	private Timer mTimer;
	private TimerTask mTask;
	private List<View> imageViewsList;// ʾͼƬĿ
	// õǰֲҳ
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			mPager.setCurrentItem(currentItem);
		};
	};
	private String[] mypciture;
	private boolean isTaskRun;
	
	private int position;//选中的图片
	/******************** END VIEWPAGER ****************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		mypciture=getIntent().getStringArrayExtra("picture");
		position=getIntent().getIntExtra("position", 0);
		
		initView();
		mVierAdapter();
		setselcet();//设置圆点
		mPager.setCurrentItem(position);
	}

	/**************** START VIEWPAGER ******************/
	private void initView() {
		mPager = (ViewPager) findViewById(R.id.guidePages);
		mGroup = (ViewGroup) findViewById(R.id.viewGroup);
		
	}

	private void setselcet(){
		for (int i = 0; i < imageViewsList.size(); i++) {
			ImageView imageView = new ImageView(this);
			imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(20, 20));
			imageView.setPadding(5, 0, 5, 0);
			imageViews[i] = imageView;
			if (i == 0)
				imageViews[i].setImageDrawable(getResources().getDrawable(
						R.drawable.page_focused));
			else
				imageViews[i].setImageDrawable(getResources().getDrawable(
						R.drawable.page_unfocused));

			mGroup.addView(imageViews[i]);
		}
	}
	/**
	 * 
	 */
	private void mVierAdapter() {
		    mPager.setFocusable(true);
		    
		    if(mypciture==null||mypciture.length==0){
				finish();
				return;
			}
		    
			imageViewsList = new ArrayList<View>();
			imageViews = new ImageView[mypciture.length];
			
			for (int j = 0; j < mypciture.length; j++) {
				View view2 = getLayoutInflater().inflate(R.layout.showpicture_item, null);
				ImageView img=(ImageView) view2.findViewById(R.id.img);
				if(mypciture[j]!=null){
					ImageLoader loader = new ImageLoader(PagerActivity.this);
					loader.DisplayImage(HttpURL.IMGPATH+mypciture[j], img);
				}
				view2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});
				imageViewsList.add(view2);
			}
			mPager.setAdapter(new MyPagerAdapter());
			mPager.setOnPageChangeListener(new MyPageChangeListener());
			
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
				// ı䵱ǰҳ
				synchronized (mPager) {
					currentItem = (currentItem + 1) % imageViewsList.size();
					handler.obtainMessage().sendToTarget();
				}
			}
		};
		mTimer.schedule(mTask, 3 * 1000, 3 * 1000);// Զлʱ䣬λǺ룬2*1000ʾ2
	}

	/**
	 * ֲֹͣͼл
	 */
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
		 * Ƴ˼еĳһ
		 */
		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			// ((ViewPager) container).removeView(imageViewsList.get(position));
			// ȡڼλ.
			int newPosition = position % imageViewsList.size();
			mPager.removeView(imageViewsList.get(newPosition)); // viewpagerƳǰĶ
		}

		/**
		 * ȡеĳһ
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
		 * жViewǷͬһ
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * ViewPagerļ ViewPagerҳ״̬ıʱ
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		boolean isAutoPlay = false;

		// ֻ˻ļ
		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			switch (state) {
			case STATE_2:// ƻ,ûл
				isAutoPlay = false;
				break;
			case STATE_3:// лУڻ
				isAutoPlay = true;
				break;
			case STATE_1:// лϻ߼
				/*if (mPager.getCurrentItem()==mPager.getAdapter().getCount()-1) {
					//mButton.setVisibility(View.VISIBLE);
				}
				if (mPager.getCurrentItem() == mPager.getAdapter().getCount() - 1
						&& !isAutoPlay) {
					mPager.setCurrentItem(STATE_1);
				}
				else if (mPager.getCurrentItem() == STATE_1 && !isAutoPlay) {
					mPager.setCurrentItem(mPager.getAdapter().getCount() - 1);
				}*/
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}

		/**
		 * ѡʱ򱻵 viewpager¼ СԲ
		 */
		@Override
		public void onPageSelected(int pos) {

			for (int i = 0; i < imageViews.length; i++) {
				if (i == pos) {
					imageViews[i].setImageDrawable(getResources().getDrawable(
							R.drawable.page_focused));
					currentItem = pos;
				} else {
					imageViews[i].setImageDrawable(getResources().getDrawable(
							R.drawable.page_unfocused));
				}
			}
		}
	}
	/******************** END VIEWPAGER ****************************/

}
