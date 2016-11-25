package com.sanmi.activity;

import java.util.ArrayList;

import com.dream.framework.utils.dialog.LodingDialog;
import com.sanmi.adapter.QueRenAdapter;
import com.sanmi.refresh.PullToRefreshListView;
import com.sanmi.refresh.PullToRefreshBase.OnRefreshListener;
import com.sanmi.view.Title;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * ҵԤԼ
 * 
 * @author Administrator
 * 
 */
public class MeYueActivity extends BaseActivity {
	private com.sanmi.refresh.PullToRefreshListView mPullToRefreshListView;// ˢlistview
	private LodingDialog lls;// ˢDialog
	private ArrayList<String> mArrayList = new ArrayList<String>();
	private QueRenAdapter mQueRenAdapter;
	private ListView mListView;// ˢlistview
	private ImageView mLeft;
	private Intent intent;
	private int num;// Intent

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_meyue);
		new Title(this);//״̬ɫ
		init();// ʼؼ
		listViewRefresh();// ˢ

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mArrayList.add("1215");
		mQueRenAdapter = new QueRenAdapter(mArrayList, getApplication(), null);
		mListView.setAdapter(mQueRenAdapter);
	}
	/**
	 * ˢ
	 */

	private void listViewRefresh() {
		// ˢ·
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// ˢ
				if (mPullToRefreshListView.getRefreshType() == PullToRefreshListView.MODE_PULL_DOWN_TO_REFRESH) {// ˢ
					lls = LodingDialog.DialogFactor(MeYueActivity.this,
							"正在加载...", false);

					mPullToRefreshListView.onRefreshComplete();
					lls.dismiss();
				} else {

					if (mPullToRefreshListView.getRefreshType() == PullToRefreshListView.MODE_PULL_UP_TO_REFRESH) {// 

						lls = LodingDialog.DialogFactor(MeYueActivity.this,
								"正在加载...", false);

						mPullToRefreshListView.onRefreshComplete();
						lls.dismiss();
					}
				}

			}

		});
	}
	/**
	 * ʼؼ
	 */

	private void init() {
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_meyue);
		intent = getIntent();
		num = intent.getIntExtra("num", 0);
		mLeft = (ImageView) findViewById(R.id.iv_left);
		mLeft.setOnClickListener(this);
		mListView = mPullToRefreshListView.getRefreshableView();
	}
	/**
	 * ñ״̬
	 */

	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mRight.setVisibility(View.GONE);
		mCenter.setText("我要约谁");
	}
	/**
	 * onClick¼
	 */

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_left:
			if (num == 1) {
				this.finish();
			} else {

				/*Intent intent = new Intent(MeYueActivity.this,
						MainActivity.class);
				intent.putExtra("type", 1);
				startActivity(intent);*/
				this.finish();
			}
			break;

		default:
			break;
		}
	}
/*	*//**
	 * ׽ؼonBackPressed()
	 *//*
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
        	Intent intent = new Intent(MeYueActivity.this, MainActivity.class);
			intent.putExtra("type", 1);
			startActivity(intent);
			this.finish();
			return true;
		}    
		return super.onKeyDown(keyCode, event);
	}*/
	

}
