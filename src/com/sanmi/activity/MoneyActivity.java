package com.sanmi.activity;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONObject;

import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;
import com.sanmi.adapter.GchuzuAdapter;
import com.sanmi.adapter.GroupAdapter;
import com.sanmi.adapter.GxunqiuAdapter;
import com.sanmi.adapter.MchuzuAdapter;
import com.sanmi.adapter.MianFeiAapter;
import com.sanmi.adapter.MxunqiuAdapter;
import com.sanmi.business.GrilData;
import com.sanmi.business.GrilData2;
import com.sanmi.business.MoneyData;
import com.sanmi.business.MoneyData2;
import com.sanmi.fengzhuang.GchuzuDatas;
import com.sanmi.fengzhuang.GxunqiuDatas;
import com.sanmi.fengzhuang.MchuzuDatas;
import com.sanmi.fengzhuang.MxunqiuDatas;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.refresh.PullToRefreshListView;
import com.sanmi.refresh.PullToRefreshBase.OnRefreshListener;
import com.sanmi.view.MyApplication;
import com.sanmi.view.Title;
import com.sanmi.view.MyApplication.myLocation;
import com.zuwo.data.InfoList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 土豪
 * @author Administrator
 *
 */
public class MoneyActivity extends BaseActivity{
	private com.sanmi.refresh.PullToRefreshListView mPullToRefreshListView;//ˢlistview
	private LodingDialog lls;//Dialog
	private ListView mListView;//listview
	private ArrayList<InfoList> mArrayList=new ArrayList<InfoList>();//
	private MchuzuAdapter mMchuzuAdapter;//
	
	private int type = 1;//1是出租信息 2是寻求信息
	private String category = "4";
	private String bydistance = "0";
	private int page = 0;
	private int pagenum = 30;
	
	//ݽ
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (lls != null) {
				lls.dismiss();
			}
			switch (msg.what) {
			case Constants.INFOLIST:
				mPullToRefreshListView.onRefreshComplete();
				try {
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if(jsonObj.getString("code").equals("1")) {
						String jsonData = jsonObj.getString("data");
						ArrayList<InfoList> list = JsonUtil.instance().fromJson(jsonData, new TypeToken<ArrayList<InfoList>>(){}.getType());
						if(list!=null){
							mArrayList.addAll(list);
							mMchuzuAdapter = new MchuzuAdapter(mArrayList, MoneyActivity.this, null);
							mListView.setAdapter(mMchuzuAdapter);
							mListView();
						}
					}else{
						Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 0).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
			
		};
		
	};
	
	private RadioButton mButtonLeft;
	private RadioButton mButtonRight;
	private ImageView mLeft;
	private ImageView mRight;
	private Button mFaBu;
    /****************START PopWindow ******************/
	
	private PopupWindow popupWindow;
	private View view;
	private ListView lv_group;
	private ArrayList<String> groups;
	private GroupAdapter groupAdapter;
	
	/***************END PopWindow*********************/
	private String selecttype = "1";
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_money);
		new Title(this);//
		init();//ʼؼ
		lls=LodingDialog.DialogFactor(MoneyActivity.this, "正在加载...", false);
		sendHttp();
		listViewRefresh();//ˢ
	}
	/**
	 *上拉下拉刷新
	 */
	private void listViewRefresh() {
		// ˢ·
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (mPullToRefreshListView.getRefreshType() == PullToRefreshListView.MODE_PULL_DOWN_TO_REFRESH) {
					lls=LodingDialog.DialogFactor(MoneyActivity.this, "正在刷新...", false);
					page = 0;
					mArrayList=new ArrayList<InfoList>();
					mMchuzuAdapter.notifyDataSetChanged();
					if (selecttype.equals("1")) {
						type=1;
						sendHttp();
					}
					if (selecttype.equals("2")) {
						type=2;
						sendHttp();
					}
//					mPullToRefreshListView01.onRefreshComplete();  
				} else{
					if (mPullToRefreshListView.getRefreshType() == PullToRefreshListView.MODE_PULL_UP_TO_REFRESH) {
						lls=LodingDialog.DialogFactor(MoneyActivity.this, "正在加载...", false);
						if (mArrayList.size()/30 == page+1) {
							page = mArrayList.size()/30;
							Log.i("aaaaa", "aaaa=-"+page);
							if (selecttype.equals("1")) {
								type=1;
								sendHttp();
							}
							if (selecttype.equals("2")) {
								type=2;
								sendHttp();
							}
						}else {
							lls.dismiss();
							com.bset.tool.Toast.ToastMe(MoneyActivity.this, "没有更多");
							mPullToRefreshListView.onRefreshComplete();
						}
					}
				}
			}
		});
	}
	/**
	 * ʼ
	 */
	private void init() {
		mPullToRefreshListView=(PullToRefreshListView) findViewById(R.id.lv_money);
		mListView=mPullToRefreshListView.getRefreshableView();
		mButtonLeft = (RadioButton) findViewById(R.id.rb_title2_01);
		mButtonRight = (RadioButton) findViewById(R.id.rb_title2_02);
		mLeft = (ImageView) findViewById(R.id.iv_left);
		mRight=(ImageView) findViewById(R.id.iv_right);
		mFaBu=(Button) findViewById(R.id.btn_fabu);
		mFaBu.setOnClickListener(this);
		mListView=mPullToRefreshListView.getRefreshableView();
	}
	/**
	 * ݣ
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mListView();
//		mMoneyData.mchuzu(handler);
		mMchuzuAdapter=new MchuzuAdapter(mArrayList, MoneyActivity.this, null);
		mListView.setAdapter(mMchuzuAdapter);
	}
	/**
	 * ñ
	 */
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mLeft.setVisibility(View.VISIBLE);
		mRight.setVisibility(View.VISIBLE);
		mCenter.setText("土豪");
	}
	/**
	 * ListViewÿitemļ
	 */
	public void mListView(){
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(MoneyActivity.this,MsgBchuzu.class);
				intent.putExtra("id", mArrayList.get(arg2-1).getId());
				intent.putExtra("user_id", mArrayList.get(arg2 - 1).getUser_id());
				startActivity(intent);
			}
		});
	}
	
	/**
	 *onClick 
	 */
	@Override
	public void onClick(View v) {
		Intent intent=null;
		super.onClick(v);
		if (v == mButtonLeft) {
			if (selecttype.equals("1")) {
				return;
			}
			selecttype = "1";
			type = 1;
			mArrayList=new ArrayList<InfoList>();
			mMchuzuAdapter.cleandata();
			mMchuzuAdapter.notifyDataSetChanged();
			lls=LodingDialog.DialogFactor(MoneyActivity.this, "正在加载...", false);
			sendHttp();
		}
		if (v == mButtonRight) {
			if (selecttype.equals("2")) {
				return;
			}
			    selecttype = "2";
			type = 2;
			mArrayList=new ArrayList<InfoList>();
			mMchuzuAdapter.cleandata();
			mMchuzuAdapter.notifyDataSetChanged();
			lls=LodingDialog.DialogFactor(MoneyActivity.this, "正在加载...", false);
			sendHttp();
		}
		if (v==mRight) {
			/****************START PopWindow ******************/
			showWindow(v);
		}
		if (v==mFaBu) {
			intent = new Intent(MoneyActivity.this, FaBuActivity.class);
			intent.putExtra("category", category);
			startActivity(intent);
		}
	
		
	}
	
	private void sendHttp(){
		((MyApplication)getApplication()).getmyLocation(new myLocation() {
			
			@Override
			public void result(double nlatitude, double nlontitude, boolean success) {
				if (success) {
					PublicRequest http = new PublicRequest(handler);
					http.InfoList(MoneyActivity.this, category, type+"",page, pagenum,nlontitude+"",nlatitude+"",bydistance);
				}else {
					if (lls != null) {
						lls.dismiss();
					}
					com.bset.tool.Toast.ToastMe(MoneyActivity.this, "获取地理位置失败");
				}
				
			}
		});
	}
	
	/****************START PopWindow ******************/
	public void showWindow(View parent) {

		if (popupWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			view = layoutInflater.inflate(R.layout.group_list, null);
			// ViewUtils.inject(this,view);
			lv_group = (ListView) view.findViewById(R.id.lvGroup);
			//
			groups = new ArrayList<String>();
			groups.add("离我最近");
			groups.add("最新发布");
			groups.add("租金最高");

			

			groupAdapter = new GroupAdapter(getApplication(), groups);
			lv_group.setAdapter(groupAdapter);
			popupWindow = new PopupWindow(view,getWindowManager().getDefaultDisplay().getWidth()/4,LayoutParams.WRAP_CONTENT);
		}

		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);

		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		int xPos = windowManager.getDefaultDisplay().getWidth() / 2
				- popupWindow.getWidth() / 2;
		Log.i("coder", "xPos:33333" + xPos);

		popupWindow.showAsDropDown(parent, xPos, 0);

		lv_group.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {

				switch (position) {
				case 0:
					page = 0;
					bydistance = "1";
					mArrayList=new ArrayList<InfoList>();
					mMchuzuAdapter.cleandata();
					mMchuzuAdapter.notifyDataSetChanged();
					if (selecttype.equals("1")) {
						type=1;
						sendHttp();
					}
					if (selecttype.equals("2")) {
						type=2;
						sendHttp();
					}
					break;
				case 1:
					page = 0;
					bydistance = "0";
					mArrayList=new ArrayList<InfoList>();
					mMchuzuAdapter.cleandata();
					mMchuzuAdapter.notifyDataSetChanged();
					if (selecttype.equals("1")) {
						type=1;
						sendHttp();
					}
					if (selecttype.equals("2")) {
						type=2;
						sendHttp();
					}
					break;
				case 2:
					page = 0;
					bydistance = "2";
					mArrayList = new ArrayList<InfoList>();
					mMchuzuAdapter.cleandata();
					mMchuzuAdapter.notifyDataSetChanged();
					if (selecttype.equals("1")) {
						type=1;
						sendHttp();
					}
					if (selecttype.equals("2")) {
						type=2;
						sendHttp();
					}
					break;
				}

				if (popupWindow != null) {
					popupWindow.dismiss();
				}
			}
		});
	}
	/****************START PopWindow ******************/
	/**
	 * ׽ؼonBackPressed()
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
        	Intent intent = new Intent(MoneyActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			return true;
		}    
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mButtonLeft.setChecked(true);
		mButtonRight.setChecked(false);
	}

}
