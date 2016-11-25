package com.sanmi.activity;

import java.util.ArrayList;

import org.json.JSONObject;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;
import com.sanmi.adapter.GroupAdapter;
import com.sanmi.adapter.MianFeiAapter;
import com.sanmi.adapter.MianFeiXunQiuAdapter;
import com.sanmi.business.MxunqiuData;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.refresh.PullToRefreshBase.OnRefreshListener;
import com.sanmi.refresh.PullToRefreshListView;
import com.sanmi.view.MyApplication;
import com.sanmi.view.MyApplication.myLocation;
import com.sanmi.view.Title;
import com.zuwo.data.InfoList;

/**
 * ѽ
 * 
 * @author Administrator
 */
public class MianFeiActivity extends BaseActivity {
	private com.sanmi.refresh.PullToRefreshListView mPullToRefreshListView01;// listview
	private LodingDialog lls;// Dialog
	private ListView mListView;// listview
	private ArrayList<InfoList> mArrayList = new ArrayList<InfoList>();// ݼ
	private MianFeiAapter mMianFeiAapter;//

	private int type = 1;// 1是出租信息 2是寻求信息
	private String category = "1";
	private String bydistance = "0";
	private int page = 0;
	private int pagenum = 30;
	private String selecttype = "1";

	private RadioButton mButtonLeft;
	private RadioButton mButtonRight;
	private ImageView mLeft;
	private ImageView mRight;
	private Button mFaBu;
	private boolean difference = false;

	/**************** START PopWindow ******************/
	private PopupWindow popupWindow;
	private View view;
	private ListView lv_group;
	private ArrayList<String> groups;
	private GroupAdapter groupAdapter;

	/*************** END PopWindow *********************/

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_mianfei);
		new Title(this);//
		init();//
		lls = LodingDialog.DialogFactor(MianFeiActivity.this, "正在加载...", false);
		sendHttp();
		listViewRefresh();//
	}

	private void init() {
		mPullToRefreshListView01 = (PullToRefreshListView) findViewById(R.id.lv_mianfei);
		mButtonLeft = (RadioButton) findViewById(R.id.rb_title2_01);
		mButtonRight = (RadioButton) findViewById(R.id.rb_title2_02);
		mLeft = (ImageView) findViewById(R.id.iv_left);
		mRight = (ImageView) findViewById(R.id.iv_right);
		mFaBu = (Button) findViewById(R.id.btn_fabu);
		mListView = mPullToRefreshListView01.getRefreshableView();
		mFaBu.setOnClickListener(this);
		mButtonLeft.setOnClickListener(this);
		mButtonRight.setOnClickListener(this);
	}

	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mLeft.setVisibility(View.VISIBLE);
		mRight.setVisibility(View.VISIBLE);
		mCenter.setText("免费");
	}

	public void mListView() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(MianFeiActivity.this,
						MsgBchuzu.class);
				intent.putExtra("id", mArrayList.get(arg2 - 1).getId());
				intent.putExtra("user_id", mArrayList.get(arg2 - 1).getUser_id());
				intent.putExtra("category", true);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		super.onClick(v);
		if (v == mButtonLeft) {
			if (selecttype.equals("1")) {
				return;
			}
			selecttype = "1";
			type = 1;
			mArrayList = new ArrayList<InfoList>();
			mMianFeiAapter.cleandata();
			mMianFeiAapter.notifyDataSetChanged();
			lls = LodingDialog.DialogFactor(MianFeiActivity.this, "正在加载...", false);
			sendHttp();

		}
		if (v == mButtonRight) {
			if (selecttype.equals("2")) {
				return;
			}
			    selecttype = "2";
				type = 2;
				mArrayList = new ArrayList<InfoList>();
				mMianFeiAapter.cleandata();
				mMianFeiAapter.notifyDataSetChanged();
				lls = LodingDialog.DialogFactor(MianFeiActivity.this, "正在加载...", false);
				sendHttp();
		}

		if (v == mRight) {
			/**************** START PopWindow ******************/
			showWindow(v);
		}
		if (v == mFaBu) {
			intent = new Intent(MianFeiActivity.this, FaBuActivity.class);
			intent.putExtra("category", category);
			startActivity(intent);
		}
	}

	/**
	 * 获取列表请求
	 */
	public void sendHttp() {
		((MyApplication) getApplication()).getmyLocation(new myLocation() {

			@Override
			public void result(double nlatitude, double nlontitude,
					boolean success) {
				if (success) {
					
					PublicRequest http = new PublicRequest(mHandler);
					http.InfoList(MianFeiActivity.this, category, type + "",
							page, pagenum, nlontitude + "", nlatitude + "",
							bydistance);
				} else {
					if (lls != null) {
						lls.dismiss();
					}
					com.bset.tool.Toast.ToastMe(MianFeiActivity.this,
							"获取地理位置失败");
				}
			}
		});
	}

	/**
	 * 上拉下拉刷新
	 */
	private void listViewRefresh() {
		//
		mPullToRefreshListView01.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (mPullToRefreshListView01.getRefreshType() == PullToRefreshListView.MODE_PULL_DOWN_TO_REFRESH) {
					lls = LodingDialog.DialogFactor(MianFeiActivity.this,
							"正在刷新...", false);
					page = 0;
					mArrayList = new ArrayList<InfoList>();
					mMianFeiAapter.notifyDataSetChanged();
					if (selecttype.equals("1")) {
						type=1;
						sendHttp();
					}
					if (selecttype.equals("2")) {
						type=2;
						sendHttp();
					}
					
					// mPullToRefreshListView01.onRefreshComplete();
				} else {
					if (mPullToRefreshListView01.getRefreshType() == PullToRefreshListView.MODE_PULL_UP_TO_REFRESH) {
						lls = LodingDialog.DialogFactor(MianFeiActivity.this,
								"正在加载...", false);
						if (mArrayList.size() / 30 == page + 1) {
							page = mArrayList.size() / 30;
							Log.i("aaaaa", "aaaa=-" + page);
							if (selecttype.equals("1")) {
								type=1;
								sendHttp();
							}
							if (selecttype.equals("2")) {
								type=2;
								sendHttp();
							}
						} else {
							lls.dismiss();
							com.bset.tool.Toast.ToastMe(MianFeiActivity.this,
									"没有更多");
							mPullToRefreshListView01.onRefreshComplete();
						}
					}
				}
			}
		});
	}

	// 请求回调
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (lls != null) {
				lls.dismiss();
			}
			switch (msg.what) {
			case Constants.INFOLIST:
				mPullToRefreshListView01.onRefreshComplete();
				try {
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if (jsonObj.getString("code").equals("1")) {
						String jsonData = jsonObj.getString("data");
						ArrayList<InfoList> list = JsonUtil.instance()
								.fromJson(jsonData,
										new TypeToken<ArrayList<InfoList>>() {
										}.getType());
						if (list != null) {
							mArrayList.addAll(list);
							mMianFeiAapter = new MianFeiAapter(mArrayList,
									MianFeiActivity.this, null);
							mListView.setAdapter(mMianFeiAapter);
							mListView();
						}
					} else {
						Toast.makeText(getApplicationContext(),
								jsonObj.getString("message"), 0).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		};
	};

	/**************** START PopWindow ******************/
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
			// һPopuWidow
			popupWindow = new PopupWindow(view, getWindowManager().getDefaultDisplay().getWidth()/4, LayoutParams.WRAP_CONTENT);
		}

		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);

		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		int xPos = windowManager.getDefaultDisplay().getWidth() / 2
				- popupWindow.getWidth() / 2;
		Log.i("coder", "xPos:4444." + xPos);

		popupWindow.showAsDropDown(parent, xPos, 0);
		lv_group.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {

				switch (position) {
				case 0:
					page = 0;
					bydistance = "1";
					mArrayList = new ArrayList<InfoList>();
					mMianFeiAapter.cleandata();
					mMianFeiAapter.notifyDataSetChanged();
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
					mArrayList = new ArrayList<InfoList>();
					mMianFeiAapter.cleandata();
					mMianFeiAapter.notifyDataSetChanged();
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
					mMianFeiAapter.cleandata();
					mMianFeiAapter.notifyDataSetChanged();
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

	/**************** START PopWindow ******************/
	/**
	 * ׽ؼonBackPressed()
	 */

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mButtonLeft.setChecked(true);
		mButtonRight.setChecked(false);
	}

}
