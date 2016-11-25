package com.sanmi.activity;

import java.util.ArrayList;

import com.dream.framework.utils.dialog.LodingDialog;
import com.sanmi.adapter.MeDingAdapter;
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
 * ҵĶ
 * @author Administrator
 *
 */
public class MeDingDanActivity extends BaseActivity {
	private com.sanmi.refresh.PullToRefreshListView mPullToRefreshListView;//ˢlistview
	private LodingDialog lls;//ˢDialog
	private ListView mListView;//ˢlistview
	private ArrayList<String> mList=new ArrayList<String>();
	private MeDingAdapter mDingAdapter;
	private ImageView mLeft;
	private Intent intent;//Intentֵ
	private int num;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_medingdan);
		new Title(this);//״̬ɫ
		init();//ʼؼ
		listViewRefresh();//ˢ
		intent=getIntent();
		num=intent.getIntExtra("num",0);
	}
	/**
	 * ݲ
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mList.add("Լ");
		mDingAdapter=new MeDingAdapter(mList, getApplication(), null);
		mListView.setAdapter(mDingAdapter);
	}
	/**
	 * ʼؼ
	 */
	private void init() {
		mPullToRefreshListView=(PullToRefreshListView) findViewById(R.id.lv_meding);
		mLeft=(ImageView) findViewById(R.id.iv_left);
		mLeft.setOnClickListener(this);
		mListView=mPullToRefreshListView.getRefreshableView();
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
					 lls=LodingDialog.DialogFactor(MeDingDanActivity.this, "...", false);
					
					
					 mPullToRefreshListView.onRefreshComplete();
					 lls.dismiss();
				} else{
					
					if (mPullToRefreshListView.getRefreshType() == PullToRefreshListView.MODE_PULL_UP_TO_REFRESH) {// 
					
						lls=LodingDialog.DialogFactor(MeDingDanActivity.this, "...", false);
						
						mPullToRefreshListView.onRefreshComplete();
						lls.dismiss();
					}
				}
					
					
					
			}
			
		});
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
		mCenter.setText("我的订单");
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
			if (num==1) {
				this.finish();
			}
			else
			{
			/*Intent intent = new Intent(MeDingDanActivity.this, MainActivity.class);
			intent.putExtra("type", 1);
			startActivity(intent);*/
			this.finish();
			}
			break;
		default:
			break;
		}
	}
	/**
	 * ׽ؼonBackPressed()
	 */
	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
        	Intent intent = new Intent(MeDingDanActivity.this, MainActivity.class);
			intent.putExtra("type", 1);
			startActivity(intent);
			this.finish();
			return true;
		}    
		return super.onKeyDown(keyCode, event);
	}
*/

}
