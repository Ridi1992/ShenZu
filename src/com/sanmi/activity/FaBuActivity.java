package com.sanmi.activity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.yanzi.ui.HorizontalListView;
import org.yanzi.ui.HorizontalListViewAdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bset.tool.Inputtool;
import com.bset.tool.Texttool;
import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;
import com.lester.school.CalendarView.CalendarView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sanmi.adapter.GvAdapter;
import com.sanmi.fengzhuang.GridViewInfo;
import com.sanmi.http.Constants;
import com.sanmi.http.HttpFileUpTool;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.http.HttpFileUpTool.Myreturn;
import com.sanmi.view.DialogUtil;
import com.teacher.datedialog.DateTimePickerDialog;
import com.teacher.datedialog.DateTimePickerDialog.OnDateTimeSetListener;
import com.zuwo.data.MeCallData;
import com.zuwo.data.Regions;
import com.zuwo.userinfo.mUserInfo;
import com.lester.school.CalendarView.CalendarView.myOnItemClickListener;

/**
 * 发布界面
 * 
 * @author Administrator
 * 
 */
public class FaBuActivity extends BaseActivity {
	private LodingDialog mDialog;
	public static FaBuActivity mcontent;
	private TextView mStartTime, mEndTime, mStartTime2, mEndTime2;
	private TextView xStartTime, xEndTime, xStartTime2, xEndTime2;
	private TextView mShen, mShi, mQu;
	private TextView mShen1, mShi1, mQu1;
	private String id2,id3;
	private ScrollView mRleft, mRright;
	private RadioButton mButtonLeft;
	private RadioButton mButtonRight;
	private Button mFaBu, mXunQiu;
	private LinearLayout mXiangCe;
	private HorizontalListView hListView, hListView2;// 水平滑动view
	private HorizontalListViewAdapter hListViewAdapter;// 水平滑动view的适配器
	private ArrayList<GridViewInfo> mGridViewInfos = new ArrayList<GridViewInfo>();// 出租范围
																					// GridView集合数据
	private GridViewInfo mGridViewInfo;// 数据封装类
	private LinearLayout mImageView;
	private LinearLayout mNeiRong;
	private View CustomView;// 弹出框

	private ArrayList<Regions> mList = new ArrayList<Regions>();

	private String intent2;
	
	private int check;
	/**************** START PopWindow ******************/
	private PopupWindow popupWindow;
	private View view;
	private GridView lv_group;
	private List<String> groups;
	private GvAdapter groupAdapter;
	private Button mQueRen;// 布局添加的****
	private int count;

	/*************** END PopWindow *********************/
	/*************** 相册开始 ************************/
	private static final int REQUEST_PICK = 0;
	private GridAdapter adapter;
	public ArrayList<String> selectedPicture = new ArrayList<String>();
	private HorizontalListView mHorizontalScrollView2;
	/*************** 相册结束 ************************/

	private LodingDialog lldialog;
	private String category;// category（分类1免费，2女神，3男神，4土豪）
	private EditText mCphone, mXphone;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mcontent = this;
		// 设置软键盘
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.activity_fabu);

		intent2 = getIntent().getStringExtra("0000");
		category = getIntent().getStringExtra("category");
		init();// 初始化控件
		isShowPicture();// 是否显示发布图片的按钮
		/*************** 相册开始 ************************/
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(100 * 1024 * 1024).diskCacheFileCount(300)
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
		adapter = new GridAdapter();
		mHorizontalScrollView2.setAdapter(adapter);
		mHorizontalScrollView2
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						StratToImageViewActivity(arg2);
					}
				});
//		gethttp();
		
		/****************** 相册结束 ************************/
	}

	private void StratToImageViewActivity(int arg2) {
		Intent data = new Intent(FaBuActivity.this, ImageViewActivity.class);
		data.putExtra("00000", selectedPicture.get(arg2));
		data.putExtra("position", arg2);
		startActivityForResult(data, 6);
	}

	/**
	 * 是否可以发送图片
	 */
	private void isShowPicture() {// 免费不可以发送图片
		if (category.equals("1")) {
			findViewById(R.id.ll_xiangce).setVisibility(View.GONE);
		} else {
			findViewById(R.id.ll_xiangce).setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		mStartTime = (TextView) findViewById(R.id.starttime);
		mShen = (TextView) findViewById(R.id.address1);
		mShi = (TextView) findViewById(R.id.address2);
		mQu = (TextView) findViewById(R.id.address3);
		mShen1 = (TextView) findViewById(R.id.et_dizhi1);
		mShi1 = (TextView) findViewById(R.id.et_dizhi2);
		mQu1 = (TextView) findViewById(R.id.et_dizhi3);
		mEndTime = (TextView) findViewById(R.id.endtime);
		mStartTime2 = (TextView) findViewById(R.id.starttime2);
		mEndTime2 = (TextView) findViewById(R.id.endtime2);
		xStartTime = (TextView) findViewById(R.id.et_kaishijian);
		xEndTime = (TextView) findViewById(R.id.jieshijian);
		xStartTime2 = (TextView) findViewById(R.id.et_kaishijian2);
		xEndTime2 = (TextView) findViewById(R.id.jieshijian2);
		mRleft = (ScrollView) findViewById(R.id.sl1);
		mCphone = (EditText) findViewById(R.id.et_phonechuzu);
		mXphone = (EditText) findViewById(R.id.et_phone);
		mRright = (ScrollView) findViewById(R.id.sl2);
		mButtonLeft = (RadioButton) findViewById(R.id.rb_title2_01);
		mButtonRight = (RadioButton) findViewById(R.id.rb_title2_02);
		mFaBu = (Button) findViewById(R.id.bt_fabume);
		mXunQiu = (Button) findViewById(R.id.bt_fabumexunqiu);
		hListView = (HorizontalListView) findViewById(R.id.horizon_listview);
		hListView2 = (HorizontalListView) findViewById(R.id.horizon_listview2);
		mImageView = (LinearLayout) findViewById(R.id.ll_fb01);
		mNeiRong = (LinearLayout) findViewById(R.id.rl_neirong);
		mXiangCe = (LinearLayout) findViewById(R.id.ll_xiangce);
		mHorizontalScrollView2 = (HorizontalListView) findViewById(R.id.hs_tu);
		mFaBu.setOnClickListener(this);
		mXunQiu.setOnClickListener(this);
		mImageView.setOnClickListener(this);
		mNeiRong.setOnClickListener(this);
		mXiangCe.setOnClickListener(this);
		mStartTime.setOnClickListener(this);
		mEndTime.setOnClickListener(this);
		mStartTime2.setOnClickListener(this);
		mEndTime2.setOnClickListener(this);
		xStartTime.setOnClickListener(this);
		xEndTime.setOnClickListener(this);
		xStartTime2.setOnClickListener(this);
		xEndTime2.setOnClickListener(this);
		mShen.setOnClickListener(this);
		mShi.setOnClickListener(this);
		mQu.setOnClickListener(this);
		mShen1.setOnClickListener(this);
		mShi1.setOnClickListener(this);
		mQu1.setOnClickListener(this);

	}

	/**
	 * 设置标题状态
	 */
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mCenter.setText("发布");
		mRight.setVisibility(View.GONE);
	}

	/**
	 * onClick事件监听
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		Inputtool.HideKeyboard(v);
		if (v == mShen) {
			gethttp();
		}
		if (v == mShi) {
				gethttp2(id2);
			
		}
		if (v == mQu) {
				gethttp3(id3);
		}
		if (v == mShen1) {
			
			gethttp();
		}
		if (v == mShi1) {
				gethttp2(id2);
		}
		if (v == mQu1) {
				gethttp3(id3);
		}
		if (v == mButtonLeft) {
			mRleft.setVisibility(View.VISIBLE);
			mRright.setVisibility(View.GONE);
		}
		if (v == mButtonRight) {
			mRleft.setVisibility(View.GONE);
			mRright.setVisibility(View.VISIBLE);
		}
		if (v == mFaBu) {
			if (cansend()) {
				String skills, starttime, endtime, rent, address, address1, address2, address3, phonechuzu, starttime2, endtime2;
				skills = Texttool.Gettext(FaBuActivity.this, R.id.skills);
				starttime = Texttool.Gettext(FaBuActivity.this, R.id.starttime);
				endtime = Texttool.Gettext(FaBuActivity.this, R.id.endtime);
				starttime2 = Texttool.Gettext(FaBuActivity.this,
						R.id.starttime2);
				endtime2 = Texttool.Gettext(FaBuActivity.this, R.id.endtime2);
				rent = Texttool.Gettext(FaBuActivity.this, R.id.et_zujin);
				address = Texttool.Gettext(FaBuActivity.this, R.id.address);
				address1 = Texttool.Gettext(FaBuActivity.this, R.id.address1);
				address2 = Texttool.Gettext(FaBuActivity.this, R.id.address2);
				address3 = Texttool.Gettext(FaBuActivity.this, R.id.address3);
				phonechuzu = Texttool.Gettext(FaBuActivity.this,
						R.id.et_phonechuzu);
				String range = "";
				for (int i = 0; i < mGridViewInfos.size(); i++) {
					if (i == 0) {
						range = mGridViewInfos.get(i).getmText();
					} else {
						range += ";" + mGridViewInfos.get(i).getmText();
					}
				}
				JSONObject data = new JSONObject();
				try {
					data.put("user_id", mUserInfo
							.GetUserInfo(FaBuActivity.this).getId());
					data.put("category", category);
					data.put("category", category);
					data.put("skills", skills);
					data.put("tel", phonechuzu);
					data.put("schedule_start", starttime);
					data.put("schedule_end", endtime);
					data.put("startdata", starttime2);
					data.put("enddata", endtime2);
					data.put("rent", rent);
					data.put("addresscity", address1+address2+address3);
					data.put("address", address);
					data.put("range", range);
					publish(data.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				return;
			}
		}
		if (v == mXunQiu) {
			if (xunqiu()) {
				String mYaoQiu,  mDiZhi1, mDiZhi2, mDiZhi3,mDiZhi, mShiJian, mShiJian2, mZuJin, mXphone, mShiJian3, mShiJian4;
				String range2 = "";
				mYaoQiu = Texttool.Gettext(FaBuActivity.this, R.id.et_yaoqiu);
				mDiZhi = Texttool.Gettext(FaBuActivity.this, R.id.et_dizhi);
				mDiZhi1 = Texttool.Gettext(FaBuActivity.this, R.id.et_dizhi1);
				mDiZhi2 = Texttool.Gettext(FaBuActivity.this, R.id.et_dizhi2);
				mDiZhi3 = Texttool.Gettext(FaBuActivity.this, R.id.et_dizhi3);
				mShiJian = Texttool.Gettext(FaBuActivity.this,
						R.id.et_kaishijian);
				mShiJian2 = Texttool
						.Gettext(FaBuActivity.this, R.id.jieshijian);
				mShiJian3 = Texttool.Gettext(FaBuActivity.this,
						R.id.et_kaishijian2);
				mShiJian4 = Texttool.Gettext(FaBuActivity.this,
						R.id.jieshijian2);
				mZuJin = Texttool.Gettext(FaBuActivity.this, R.id.et_money);
				mXphone = Texttool.Gettext(FaBuActivity.this, R.id.et_phone);

				for (int i = 0; i < mGridViewInfos.size(); i++) {
					if (i == 0) {
						range2 = mGridViewInfos.get(i).getmText();
					} else {
						range2 += "," + mGridViewInfos.get(i).getmText();
					}
				}
				String range = "";
				for (int i = 0; i < mGridViewInfos.size(); i++) {
					if (i == 0) {
						range = mGridViewInfos.get(i).getmText();
					} else {
						range += ";" + mGridViewInfos.get(i).getmText();
					}
				}
				JSONObject data = new JSONObject();
				try {
					data.put("user_id", mUserInfo
							.GetUserInfo(FaBuActivity.this).getId());
					data.put("category", category);
					data.put("skills", mYaoQiu);
					data.put("tel", mXphone);
					data.put("schedule_start", mShiJian);
					data.put("schedule_end", mShiJian2);
					data.put("startdate", mShiJian3);
					data.put("enddate", mShiJian4);
					data.put("rent", mZuJin);
					data.put("addresscity", mDiZhi1+mDiZhi2+mDiZhi3);
					data.put("address", mDiZhi);
					data.put("range", range2);
					xunqiufabu(data.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				return;
			}
		}

		if (v == mImageView) {
			showWindow(v);
		}
		if (v == mNeiRong) {
			showWindow2(v);
		}
		if (v == mXiangCe) {
			if (selectedPicture.size() < 6) {
				startActivityForResult(new Intent(this,
						SelectPictureActivity.class), REQUEST_PICK);
			} else {
				com.bset.tool.Toast.ToastMe(getApplicationContext(),
						"最多只能选择六张图片");
			}

		}
		if (v == mStartTime) {
			showSelectdataPop(v.getId());

		}
		if (v == mStartTime2) {
			time(v.getId());
		}
		if (v == mEndTime) {
			showSelectdataPop(v.getId());
		}
		if (v == mEndTime2) {
			time(v.getId());
		}
		if (v == xStartTime) {
			showSelectdataPop(v.getId());
		}
		if (v == xEndTime) {
			showSelectdataPop(v.getId());
		}
		if (v == xStartTime2) {
			time(v.getId());
		}
		if (v == xEndTime2) {
			time(v.getId());
		}
	}

	private void gethttp3(String id) {
		mDialog = LodingDialog.DialogFactor(FaBuActivity.this, "正在查找", false);
		PublicRequest http = new PublicRequest(handler2);
		http.mecity3(FaBuActivity.this, id);
	}

	private void gethttp2(String id) {
		mDialog = LodingDialog.DialogFactor(FaBuActivity.this, "正在查找", false);
		PublicRequest http = new PublicRequest(handler2);
		http.mecity2(FaBuActivity.this, id);
	}

	private void gethttp() {
		mDialog = LodingDialog.DialogFactor(FaBuActivity.this, "正在查找", false);
		PublicRequest http = new PublicRequest(handler2);
		http.mecity(FaBuActivity.this, "1");
	}

	private Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				if (mDialog!=null) {
					mDialog.dismiss();
				}
				switch (msg.what) {
				case Constants.MCITY:
					JSONObject jsonObj = new JSONObject(msg.obj.toString());

					String jsondata = jsonObj.getJSONObject("data").getString(
							"regions");
					mList = JsonUtil.instance().fromJson(jsondata,
							new TypeToken<ArrayList<Regions>>() {
							}.getType());
					if (mList != null) {
						DialogUtil.creatCityDialog(FaBuActivity.this, mList,
								new OnItemClickListener() {
									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										System.out.println(mList.get(position)
												.getName());
										mShen.setText(mList.get(position)
												.getName());
										
										mShen1.setText(mList.get(position)
												.getName());
										id2=mList.get(position).getId();
										DialogUtil.dismiss();
										gethttp2(id2);
									}
								});
					}
					break;
				case Constants.MCITY2:
					JSONObject jsonObj2 = new JSONObject(msg.obj.toString());
					
					String jsondata2 = jsonObj2.getJSONObject("data").getString(
							"regions");
					mList = JsonUtil.instance().fromJson(jsondata2,
							new TypeToken<ArrayList<Regions>>() {
					}.getType());
					if (mList != null) {
						DialogUtil.creatCityDialog(FaBuActivity.this, mList,
								new OnItemClickListener() {
							@Override
							public void onItemClick(
									AdapterView<?> parent, View view,
									int position, long id) {
								System.out.println(mList.get(position)
										.getName());
								
								mShi.setText(mList.get(position)
										.getName());
								mShi1.setText(mList.get(position)
										.getName());
								id3=mList.get(position).getId();
								DialogUtil.dismiss();
								gethttp3(id3);
							}
						});
					}
					break;
				case Constants.MCITY3:
					JSONObject jsonObj3 = new JSONObject(msg.obj.toString());
					
					String jsondata3 = jsonObj3.getJSONObject("data").getString(
							"regions");
					mList = JsonUtil.instance().fromJson(jsondata3,
							new TypeToken<ArrayList<Regions>>() {
					}.getType());
					if (mList != null) {
						DialogUtil.creatCityDialog(FaBuActivity.this, mList,
								new OnItemClickListener() {
							@Override
							public void onItemClick(
									AdapterView<?> parent, View view,
									int position, long id) {
								System.out.println(mList.get(position)
										.getName());
						
								mQu.setText(mList.get(position)
										.getName());
								mQu1.setText(mList.get(position)
										.getName());
								DialogUtil.dismiss();
							}
						});
					}
					break;

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		};
	};

	/****************** TIME *************************/
	private void time(final int id) {
		DateTimePickerDialog dialog = new DateTimePickerDialog(
				FaBuActivity.this, System.currentTimeMillis());
		dialog.setOnDateTimeSetListener(new OnDateTimeSetListener() {
			public void OnDateTimeSet(AlertDialog dialog, long date) {
				String s = getStringDate(date).subSequence(11, 16).toString();
				// subSequence==subString 区别subSequence需要转换成字符串，subString不需要
				Texttool.setText(FaBuActivity.this, id, s);
			}
		});
		dialog.show();
	}

	@SuppressLint("SimpleDateFormat")
	public static String getStringDate(Long date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}
	/****************** TIME *************************/
	/**
	 * 寻求发布判断
	 * 
	 * @return
	 */
	private boolean xunqiu() {
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.et_yaoqiu)) {
			com.bset.tool.Toast.ToastMe(this, "请输入技能");
			return false;
		}
		if (mGridViewInfos == null || mGridViewInfos.size() == 0) {
			com.bset.tool.Toast.ToastMe(this, "请选择寻求范围");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.et_dizhi1)) {
			com.bset.tool.Toast.ToastMe(this, "请选择寻求省份");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.et_dizhi2)) {
			com.bset.tool.Toast.ToastMe(this, "请选择寻求城市");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.et_dizhi3)) {
			com.bset.tool.Toast.ToastMe(this, "请选择寻求城区");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.et_dizhi)) {
			com.bset.tool.Toast.ToastMe(this, "请输入地址");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.et_kaishijian)) {
			com.bset.tool.Toast.ToastMe(this, "请输入开始日期");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.jieshijian)) {
			com.bset.tool.Toast.ToastMe(this, "请输入结束日期");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.et_kaishijian2)) {
			com.bset.tool.Toast.ToastMe(this, "请输入开始时间");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.jieshijian2)) {
			com.bset.tool.Toast.ToastMe(this, "请输入结束时间");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.et_phone)) {
			com.bset.tool.Toast.ToastMe(this, "请输入联系电话");
			return false;
		}
		if (!Texttool.Pattern_phone(Texttool.Gettext(FaBuActivity.this,
				R.id.et_phone))) {
			com.bset.tool.Toast.ToastMe(this, "请输入真实联系电话");
			return false;
		}

		if (!Texttool.Havecontent(FaBuActivity.this, R.id.et_money)) {
			com.bset.tool.Toast.ToastMe(this, "请输入金额");
			return false;
		}
		return true;

	}

	/**
	 * 出租发布判断
	 * 
	 * @return
	 */
	private boolean cansend() {
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.skills)) {
			com.bset.tool.Toast.ToastMe(this, "请输入技能");
			return false;
		}
		if (mGridViewInfos == null || mGridViewInfos.size() == 0) {
			com.bset.tool.Toast.ToastMe(this, "请选择出租范围");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.et_zujin)) {
			com.bset.tool.Toast.ToastMe(this, "请输入金额");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.starttime)) {
			com.bset.tool.Toast.ToastMe(this, "请选择开始日期");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.endtime)) {
			com.bset.tool.Toast.ToastMe(this, "请选择结束日期");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.starttime2)) {
			com.bset.tool.Toast.ToastMe(this, "请选择开始时间");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.endtime2)) {
			com.bset.tool.Toast.ToastMe(this, "请选择结束时间");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.et_phonechuzu)) {
			com.bset.tool.Toast.ToastMe(this, "请输入联系电话");
			return false;
		}
		if (!Texttool.Pattern_phone(Texttool.Gettext(FaBuActivity.this,
				R.id.et_phonechuzu))) {
			com.bset.tool.Toast.ToastMe(this, "请输入真实联系电话");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this,
				R.id.address1)) {
			com.bset.tool.Toast.ToastMe(this, "请输入省份");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this,
				R.id.address2)) {
			com.bset.tool.Toast.ToastMe(this, "请输入城市");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this,
				R.id.address3)) {
			com.bset.tool.Toast.ToastMe(this, "请输入城区");
			return false;
		}
		if (!Texttool.Havecontent(FaBuActivity.this, R.id.address)) {
			com.bset.tool.Toast.ToastMe(this, "请输入服务地址");
			return false;
		}

		return true;
	}

	/**************** START PopWindow ******************/
	public void showWindow(View parent) {

		if (popupWindow == null) {
			view = LayoutInflater.from(FaBuActivity.this).inflate(
					R.layout.gv_list, null);
			WindowManager manger = (WindowManager) getSystemService(FaBuActivity.WINDOW_SERVICE);
			@SuppressWarnings("deprecation")
			int width = (int) (manger.getDefaultDisplay().getWidth() / 1.5);
			// view = layoutInflater.inflate(R.layout.gv_list, null);
			mQueRen = (Button) view.findViewById(R.id.bt_popqueding);
			lv_group = (GridView) view.findViewById(R.id.gv_02);
			// 加载数据
			groups = new ArrayList<String>();
			String[] s1 = new String[] { "约饭", "看电影", "约会", "旅游", "聚会", "一起运动",
					"陪喝酒", "陪唱歌", "压马路", "购物", "代驾", "拍照", "充当女友", "充当男友",
					"心事倾听者" };
			int j = s1.length;
			for (int i = 0; i < j; i++) {
				groups.add(s1[i]);// 讲数组添加到集合
			}
			groupAdapter = new GvAdapter(getApplication(), groups);
			lv_group.setAdapter(groupAdapter);

			// 创建一个PopuWidow对象
			popupWindow = new PopupWindow(view, width,
					LayoutParams.WRAP_CONTENT);
		}
		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		// 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
		int xPos = windowManager.getDefaultDisplay().getWidth() / 2
				- popupWindow.getWidth() / 2;
		Log.i("coder", "xPos:" + xPos);

		popupWindow.showAsDropDown(parent, xPos, 0);
		lv_group.setOnItemClickListener(new OnItemClickListener() {
			/**
			 * 所选择的item
			 */
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				mGridViewInfo = new GridViewInfo();
				mGridViewInfo.setmText(groups.get(position));
				mGridViewInfos.add(mGridViewInfo);
				hListViewAdapter = new HorizontalListViewAdapter(
						getApplication(), mGridViewInfos, null);
				hListView.setAdapter(hListViewAdapter);
				/**
				 * 点击提醒
				 */
				hListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							final int arg2, long arg3) {
						final Dialog dialog = new Dialog(FaBuActivity.this,
								R.style.MyDialog);
						dialog.setContentView(myBuilder(FaBuActivity.this));
						dialog.show();
						// 点击屏幕外侧，dialog不消失
						dialog.setCanceledOnTouchOutside(false);
						// 监听dialog里的button
						/*
						 * 监听btn发邮件
						 */
						Button ortherbtnemil = (Button) CustomView
								.findViewById(R.id.ortherbtnemil);
						ortherbtnemil.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								mGridViewInfos.remove(mGridViewInfos.get(arg2));
								hListViewAdapter.notifyDataSetChanged();
								hListView.setAdapter(hListViewAdapter);

								dialog.dismiss();
							}
						});
						/*
						 * 监听btn访问博客
						 */
						Button ortherbtnweb = (Button) CustomView
								.findViewById(R.id.ortherbtnweb);
						ortherbtnweb.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								Toast.makeText(FaBuActivity.this, "你选择了取消按钮！",
										Toast.LENGTH_SHORT).show();
								dialog.dismiss();
							}
						});

					}

					protected View myBuilder(FaBuActivity faBuActivity) {

						final LayoutInflater inflater = LayoutInflater
								.from(getApplication());

						CustomView = inflater
								.inflate(R.layout.customview, null);

						return CustomView;
					}
				});
				/**
				 * 按钮监听
				 */
				mQueRen.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						popupWindow.dismiss();
					}
				});
			}
		});
	}

	/**************** START PopWindow ******************/
	public void showWindow2(View parent) {

		if (popupWindow == null) {
			view = LayoutInflater.from(FaBuActivity.this).inflate(
					R.layout.gv_list, null);
			WindowManager manger = (WindowManager) getSystemService(FaBuActivity.WINDOW_SERVICE);
			@SuppressWarnings("deprecation")
			int width = (int) (manger.getDefaultDisplay().getWidth() / 1.5);
			// view = layoutInflater.inflate(R.layout.gv_list, null);
			mQueRen = (Button) view.findViewById(R.id.bt_popqueding);
			lv_group = (GridView) view.findViewById(R.id.gv_02);
			// 加载数据
			groups = new ArrayList<String>();
			String[] s1 = new String[] { "约饭", "看电影", "约会", "旅游", "聚会", "一起运动",
					"陪喝酒", "陪唱歌", "压马路", "购物", "代驾", "拍照", "充当女友", "充当男友",
					"心事倾听者" };
			int j = s1.length;
			for (int i = 0; i < j; i++) {
				groups.add(s1[i]);// 讲数组添加到集合
			}
			groupAdapter = new GvAdapter(getApplication(), groups);
			lv_group.setAdapter(groupAdapter);
			// 创建一个PopuWidow对象
			popupWindow = new PopupWindow(view, width,
					LayoutParams.WRAP_CONTENT);
		}
		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		// 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
		int xPos = windowManager.getDefaultDisplay().getWidth() / 2
				- popupWindow.getWidth() / 2;
		Log.i("coder", "xPos:" + xPos);

		popupWindow.showAsDropDown(parent, xPos, 0);
		lv_group.setOnItemClickListener(new OnItemClickListener() {
			/**
			 * 所选择的item
			 */
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				mGridViewInfo = new GridViewInfo();
				mGridViewInfo.setmText(groups.get(position));
				mGridViewInfos.add(mGridViewInfo);
				hListViewAdapter = new HorizontalListViewAdapter(
						getApplication(), mGridViewInfos, null);
				hListView2.setAdapter(hListViewAdapter);
				/**
				 * 点击提醒
				 */
				hListView2.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							final int arg2, long arg3) {

						final Dialog dialog = new Dialog(FaBuActivity.this,
								R.style.MyDialog);
						dialog.setContentView(myBuilder(FaBuActivity.this));
						dialog.show();
						// 点击屏幕外侧，dialog不消失
						dialog.setCanceledOnTouchOutside(false);
						// 监听dialog里的button

						Button ortherbtnemil = (Button) CustomView
								.findViewById(R.id.ortherbtnemil);
						ortherbtnemil.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								mGridViewInfos.remove(mGridViewInfos.get(arg2));
								hListViewAdapter.notifyDataSetChanged();
								hListView2.setAdapter(hListViewAdapter);

								dialog.dismiss();
							}
						});
						/*
						 * 监听btn访问博客
						 */
						Button ortherbtnweb = (Button) CustomView
								.findViewById(R.id.ortherbtnweb);
						ortherbtnweb.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								Toast.makeText(FaBuActivity.this, "你选择了取消按钮！",
										Toast.LENGTH_SHORT).show();
								dialog.dismiss();
							}
						});

					}

					protected View myBuilder(FaBuActivity faBuActivity) {
						final LayoutInflater inflater = LayoutInflater
								.from(getApplication());

						CustomView = inflater
								.inflate(R.layout.customview, null);

						return CustomView;
					}
				});
				/**
				 * 按钮监听
				 */
				mQueRen.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						popupWindow.dismiss();
					}
				});

				// 初始使用
				/*
				 * if (popupWindow != null) { popupWindow.dismiss(); }
				 */
			}
		});
	}

	/**************** START PopWindow ******************/
	/*********************** 相片选择开始 *******************************/
	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			/*********** 追加上去 *********************/
			ArrayList<String> myselectedPicture = (ArrayList<String>) data
					.getSerializableExtra(SelectPictureActivity.INTENT_SELECTED_PICTURE);
			selectedPicture.addAll(myselectedPicture);
			adapter.intBit();
			adapter.notifyDataSetChanged();
		}

		/* 删除 */
		if (requestCode == 6) {
			if (resultCode == RESULT_OK) {
				int position = data.getIntExtra("position", 0);
				System.out.println("-----position------------" + position);
				selectedPicture.remove(position);
				adapter.notifyDataSetChanged();
			}
		}

	}

	public void deletepciture(int position) {
		selectedPicture.remove(position);
		adapter.intBit();
		adapter.notifyDataSetChanged();
	}

	/**
	 * 内部类图片显示GridAdapter
	 * 
	 * @author Administrator
	 * 
	 */
	class GridAdapter extends BaseAdapter {
		private ArrayList<Bitmap> bit;

		public void intBit() {
			bit = new ArrayList<Bitmap>();
			if (selectedPicture != null) {
				for (String str : selectedPicture) {
					bit.add(fitBitmap(str));
				}
			}
		}

		@Override
		public int getCount() {
			if (bit != null) {
				return bit.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return bit.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHorder horder = null;
			if (convertView == null) {
				horder = new ViewHorder();
				convertView = LayoutInflater.from(getApplication()).inflate(
						R.layout.imageview, null);
				horder.imageView = (ImageView) convertView
						.findViewById(R.id.iv_picture);
				convertView.setTag(horder);
			} else {
				horder = (ViewHorder) convertView.getTag();
			}
			if (bit.get(position) != null) {
				horder.imageView.setImageBitmap(bit.get(position));
			}
			return convertView;
		}

		class ViewHorder {
			private ImageView imageView;
		}
	}

	/**
	 * 改变图片分辨率,使图片刚好和手机屏幕适配
	 */
	private Bitmap fitBitmap(String path) {

		if (path == null) {
			return null;
		}

		BitmapFactory.Options opts = new Options();
		// 不读取像素数组到内存中，仅读取图片的信息                
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);
		// 从Options中获取图片的分辨率                
		int imageHeight = opts.outHeight;
		int imageWidth = opts.outWidth;
		// 获取Android屏幕的服务                
		// 应该使用getSize()，但是这里为了向下兼容所以依然使用它们                
		int windowHeight = 500;
		int windowWidth = 500;
		// 计算采样率                
		int scaleX = imageWidth / windowWidth;
		Log.i("压缩图片", "imageWidth=" + imageWidth);
		Log.i("压缩图片", "windowWidth=" + windowWidth);
		int scaleY = imageHeight / windowHeight;
		int scale = 1;
		// 采样率依照最大的方向为准                
		if (scaleX > scaleY && scaleY >= 1) {
			scale = scaleX;
			Log.i("压缩图片,采码率", "scale=" + scale);
		}
		if (scaleX < scaleY && scaleX >= 1) {
			scale = scaleY;
			Log.i("压缩图片,采码率", "scale=" + scale);
		}
		// false表示读取图片像素数组到内存中，依照设定的采样率                
		opts.inJustDecodeBounds = false;
		// 采样率                
		opts.inSampleSize = scale;
		Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
		// setPicToView(bitmap);
		return bitmap;
	}

	/*********************** 相片选则结束 *******************************/
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.finish();
	}

	/**
	 * 寻求发布
	 * 
	 * @param data
	 */
	private void xunqiufabu(final String data) {
		final HttpFileUpTool upload = new HttpFileUpTool();
		lldialog = LodingDialog.DialogFactor(FaBuActivity.this, "正在发布", true);
		final Map<String, Bitmap> files = new HashMap<String, Bitmap>();// 寻租暂时没有图片
		// for(int i=0;i<selectedPicture.size();i++){
		// files.put("picture"+i, fitBitmap(selectedPicture.get(i)));
		// }
		new Thread(new Runnable() {
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try {
					upload.seeking(FaBuActivity.this, data, files,
							new Myreturn() {
								@Override
								public void result(final int i,
										final String string) {
									FaBuActivity.this
											.runOnUiThread(new Runnable() {
												@Override
												public void run() {
													if (lldialog != null) {
														lldialog.dismiss();
													}
													if (i == 1) {
														System.out
																.println("成功");
														finish();
														Toast.makeText(
																getApplicationContext(),
																string, 0)
																.show();
													} else {
														System.out
																.println("失败");
														Toast.makeText(
																getApplicationContext(),
																string, 0)
																.show();
													}
												}
											});

								}
							});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 出租发布
	 * 
	 * @param data
	 */
	private void publish(final String data) {
		final HttpFileUpTool upload = new HttpFileUpTool();
		lldialog = LodingDialog.DialogFactor(FaBuActivity.this, "正在发布", true);
		final Map<String, Bitmap> files = new HashMap<String, Bitmap>();
		for (int i = 0; i < selectedPicture.size(); i++) {
			files.put("picture" + i, fitBitmap(selectedPicture.get(i)));
			System.out.println("selectedPicture.get(i)"
					+ selectedPicture.get(i));
		}
		new Thread(new Runnable() {
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try {
					upload.postsms(FaBuActivity.this, data, files,
							new Myreturn() {
								@Override
								public void result(final int i,
										final String string) {
									FaBuActivity.this
											.runOnUiThread(new Runnable() {
												@Override
												public void run() {
													if (lldialog != null) {
														lldialog.dismiss();
													}
													if (i == 1) {
														System.out
																.println("成功");
														finish();
														Toast.makeText(
																getApplicationContext(),
																string, 0)
																.show();
													} else {
														System.out
																.println("失败");
														Toast.makeText(
																getApplicationContext(),
																string, 0)
																.show();
													}
												}
											});
								}
							});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void showSelectdataPop(final int id) {
		final PopupWindow mPopup;
		final CalendarView calendar;
		ImageButton calendarLeft;
		final TextView calendarCenter;
		ImageButton calendarRight;
		final SimpleDateFormat format;
		// popwindow用
		LayoutInflater inflater = (LayoutInflater) getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
		View popView = inflater.inflate(R.layout.pop_window, null);
		// 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()
		mPopup = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		// 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
		mPopup.setFocusable(true);
		mPopup.setOutsideTouchable(true);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		mPopup.setBackgroundDrawable(dw);
		// 设置popWindow的显示和消失动画
		// mPopup.setAnimationStyle(R.style.popwindow_anim_style);
		// 在底部显示
		mPopup.showAtLocation(this.findViewById(id), Gravity.BOTTOM, 0, 0);

		format = new SimpleDateFormat("yyyy-MM-dd");
		// 获取日历控件对象
		calendar = (CalendarView) popView.findViewById(R.id.calendar);
		calendar.setSelectMore(false); // 单选

		calendarLeft = (ImageButton) popView.findViewById(R.id.calendarLeft);
		calendarCenter = (TextView) popView.findViewById(R.id.calendarCenter);
		calendarRight = (ImageButton) popView.findViewById(R.id.calendarRight);
		try {
			// 设置日历日期
			Date date = format.parse("2015-01-01");
			calendar.setCalendarData(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 获取日历中年月 ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改）
		String[] ya = calendar.getYearAndmonth().split("-");
		calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
		calendarLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击上一月 同样返回年月
				String leftYearAndmonth = calendar.clickLeftMonth();
				String[] ya = leftYearAndmonth.split("-");
				calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
			}
		});
		calendarRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击下一月
				String rightYearAndmonth = calendar.clickRightMonth();
				String[] ya = rightYearAndmonth.split("-");
				calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
			}
		});
		// 设置控件监听，可以监听到点击的每一天（大家也可以在控件中根据需求设定）
		calendar.setOnItemClickListener(new myOnItemClickListener() {

			@Override
			public void OnItemClick(Date selectedStartDate,
					Date selectedEndDate, Date downDate) {
				if (calendar.isSelectMore()) {// 多选模式，返回一个日期区间
				} else {// 单选模式
					Log.i("选择日期", format.format(downDate));
					Texttool.setText(FaBuActivity.this, id,
							format.format(downDate));
					mPopup.dismiss();
				}

			}
		});
	}
}
