package com.sanmi.activity;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;
import com.sanmi.adapter.CommentAdapter;
import com.sanmi.http.Constants;
import com.sanmi.http.HttpURL;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.loader.ImageLoader;
import com.sanmi.refresh.PullToRefreshListView;
import com.sanmi.refresh.PullToRefreshBase.OnRefreshListener;
import com.sanmi.view.CircularImage;
import com.zuwo.data.Comment;
import com.zuwo.data.UserInfor;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MySelfActivity extends BaseActivity {
	private LodingDialog mDialog;
	
	private PullToRefreshListView mRefreshListView;
	private ListView mListView;
	private CommentAdapter mAdapter;
	private ArrayList<Comment> mList = new ArrayList<Comment>();
	
	private com.sanmi.view.CircularImage mHead;
	private ImageView mSex;
	private ImageView mHuiYuan;
	private TextView mName;
	private TextView mage;
	private TextView mHeight;
	private TextView mAddress;
	private TextView mProfession;
	private ImageLoader mImageLoader;
	private String user_id ;
	private int page = 0;
	private int pagenum = 30;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.call_details);
		
		initView();
		user_id= getIntent().getStringExtra("user_id");
		mImageLoader = new ImageLoader(MySelfActivity.this);
		gethttp();// 网络请求
		
		PublicRequest http2 = new PublicRequest(handler2);
		http2.Comment(MySelfActivity.this, user_id);
	}

	private void initView() {
		mRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_my);
		mListView = mRefreshListView.getRefreshableView();
		mAdapter = new CommentAdapter(mList, MySelfActivity.this);
		mListView.setAdapter(mAdapter);
		View v = LayoutInflater.from(MySelfActivity.this).inflate(R.layout.my_hand, null);
		mListView.addHeaderView(v);
		listViewRefresh();
		
		mHead = (CircularImage) v.findViewById(R.id.iv_call_details_head);
		mSex = (ImageView) v.findViewById(R.id.iv_call_details_sex);
		mHuiYuan= (ImageView) v.findViewById(R.id.iv_call_details_huiyuan);
		mName = (TextView) v.findViewById(R.id.tv_call_details_name);
		mage = (TextView) v.findViewById(R.id.tv_call_details_age);
		mHeight = (TextView) v.findViewById(R.id.tv_call_details_height);
		mAddress = (TextView) v.findViewById(R.id.tv_call_details_addrss);
		mProfession = (TextView) v.findViewById(R.id.tv_call_details_profession);
		
	}

	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mCenter.setText("个人详细信息");
		mRight.setVisibility(View.GONE);

	}

	/**
	 * 网络请求
	 */
	private void gethttp() {
		if(user_id==null){
			return;
		}
		mDialog = LodingDialog.DialogFactor(MySelfActivity.this, "正在加载", false);
		PublicRequest http = new PublicRequest(handler2);
		http.myself(MySelfActivity.this, user_id);
	}
	
	/**
	 *上拉下拉刷新
	 */
	private void listViewRefresh() {
		// ˢ·
		mRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (mRefreshListView.getRefreshType() == PullToRefreshListView.MODE_PULL_DOWN_TO_REFRESH) {
					
					mRefreshListView.onRefreshComplete();  
				} else{
					if (mRefreshListView.getRefreshType() == PullToRefreshListView.MODE_PULL_UP_TO_REFRESH) {
						mDialog=LodingDialog.DialogFactor(MySelfActivity.this, "正在加载...", false);
						if (mList.size()/pagenum == page+1) {
							page = mList.size()/30;
							Log.i("aaaaa", "aaaa=-"+page);
							PublicRequest http2 = new PublicRequest(handler2);
							http2.Comment(MySelfActivity.this, user_id);
						}else {
							mDialog.dismiss();
						com.bset.tool.Toast.ToastMe(MySelfActivity.this, "没有更多");
						mRefreshListView.onRefreshComplete();
						}
					}
				}
			}
		});
	}

	@SuppressLint("HandlerLeak")
	private Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				if (mDialog != null) {
					mDialog.dismiss();
				}
				switch (msg.what) {
				case Constants.MYSELF:
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if (jsonObj.getString("code").equals("1")) {
						String jsondata = jsonObj.getString("data");
						UserInfor userinfor = JsonUtil.instance().fromJson(
								jsondata, new TypeToken<UserInfor>() {
								}.getType());
						if (userinfor != null) {
							String face = userinfor.getFace();
							if (face != null) {
								mImageLoader.DisplayImage(HttpURL.IMGPATH
										+ userinfor.getFace(), mHead);
							}
							if (userinfor.getSex().equals("0")) {
								mSex.setImageDrawable(null);
							}
							if (userinfor.getSex().equals("1")) {
								mSex.setImageResource(R.drawable.nan);
							}
							if (userinfor.getSex().equals("2")) {
								mSex.setImageResource(R.drawable.nv);
							}
							String name = userinfor.getNickname();
							if (name != null && !name.equals("")) {
								mName.setText(userinfor.getNickname());
							}
							String age = userinfor.getAge() + "";
							if (age != null && !age.equals("")) {
								mage.setText(userinfor.getAge() + "岁");
							} else {
								mage.setText("未知");
							}
							String i=userinfor.getRank_id();
							System.out.println(i+"%%%%%%%%%%%%%%%%%%");
							if (i.equals("1")) {
								mHuiYuan.setVisibility(View.GONE);
							} else {
								mHuiYuan.setImageResource(R.drawable.huiyuanbg);
							} 
							
							
							
							
							mHeight.setText(userinfor.getHeight() + "cm");
							mAddress.setText(userinfor.getAddress());
							mProfession.setText(userinfor.getWork());

						}
					} else {
						Toast.makeText(getApplicationContext(),
								jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
					}
					break;
				case Constants.COMMENT:
					try {
						JSONObject jsonObj2 = new JSONObject(msg.obj.toString());
						if(jsonObj2.getString("code").equals("1")) {
							String jsonData = jsonObj2.getString("data");
							ArrayList<Comment> data=JsonUtil.instance().fromJson(jsonData, new TypeToken<ArrayList<Comment>>(){}.getType());
							if(data != null){
								mList.addAll(data);
								mAdapter = new CommentAdapter(mList, MySelfActivity.this);
								mListView.setAdapter(mAdapter);
							}
						}else{
//							Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 0).show();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		};
	};

}
