package com.sanmi.activity;

import java.util.ArrayList;

import org.json.JSONObject;
import org.yanzi.ui.HorizontalListView;

import com.bset.tool.Timetool;
import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;
import com.sanmi.adapter.CommentAdapter;
import com.sanmi.adapter.PBhSAdapter2;
import com.sanmi.http.Constants;
import com.sanmi.http.HttpURL;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.loader.ImageLoader;
import com.sanmi.refresh.PullToRefreshListView;
import com.sanmi.refresh.PullToRefreshBase.OnRefreshListener;
import com.sanmi.view.CircularImage;
import com.sanmi.view.Title;
import com.zuwo.data.Comment;
import com.zuwo.data.InfoDetail;
import com.zuwo.userinfo.UserInfo;
import com.zuwo.userinfo.mUserInfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Ϣ
 * @author Administrator
 *
 */
public class MsgBchuzu extends BaseActivity {
	private PullToRefreshListView mRefreshListView;
	private ListView mListView;
	private CommentAdapter mAdapter;
	private ArrayList<Comment> mList = new ArrayList<Comment>();
	private LodingDialog lls;//Dialog
	
	private CircularImage mFace;
	private TextView mNickName;
	private ImageView mSex;
	private TextView mAge;
	private TextView mAddress;
	private TextView mShowSkill;
	private TextView mRent;
	private TextView mWork;
	private TextView mHeight;
	private TextView mUserSign;
	private TextView mTime;
	private TextView mAdd;
	private Button mSubmit;
	private PBhSAdapter2 mAdapter2;
	private HorizontalListView mHorizontalScrollView2;
	private String[] mypciture=null;
	private String id;
	private boolean category;
	private TextView mFanWei;
	
	private String user_id;
	
	private int page = 0;
	private int pagenum = 30;
	private ImageView mHuiYuan;
	private TextView mTime2;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.huiyuan_b_b);
		new Title(this);
	    id=getIntent().getStringExtra("id");
	    user_id=getIntent().getStringExtra("user_id");
	    category = getIntent().getBooleanExtra("category", false);
		init();
		
		PublicRequest http = new PublicRequest(mHandler);
		http.InfoDetail(MsgBchuzu.this, id);
		
		PublicRequest http2 = new PublicRequest(mHandler);
		http2.Comment(MsgBchuzu.this, user_id);
		
	}
	/**
	 * ʼؼ
	 */
	private void init() {
		mRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_detail);
		mListView = mRefreshListView.getRefreshableView();
		mAdapter = new CommentAdapter(mList, MsgBchuzu.this);
		mListView.setAdapter(mAdapter);
		View v = LayoutInflater.from(MsgBchuzu.this).inflate(R.layout.detail_hand, null);
		mListView.addHeaderView(v);
		listViewRefresh();
		
		mFanWei = (TextView) v.findViewById(R.id.hs_pb);
		mFace = (CircularImage) v.findViewById(R.id.detail_face);
		mNickName = (TextView) v.findViewById(R.id.tv_b_b_name);
		mSex = (ImageView) v.findViewById(R.id.iv_b_b_sex);
		mHuiYuan = (ImageView) v.findViewById(R.id.iv_b_b_huiyuan);
		mAge = (TextView) v.findViewById(R.id.tv_b_b_age);
		mAddress = (TextView) v.findViewById(R.id.tv_b_b_address);
		mShowSkill = (TextView) v.findViewById(R.id.tv_b_b_skill);
		mRent = (TextView) v.findViewById(R.id.tv_b_b_rent);
		mWork = (TextView) v.findViewById(R.id.tv_b_b_work);
		mHeight = (TextView) v.findViewById(R.id.tv_b_b_height);
		mUserSign = (TextView) v.findViewById(R.id.tv_b_b_sign);
		mTime = (TextView) v.findViewById(R.id.tv_b_b_time);
		mTime2 = (TextView) v.findViewById(R.id.tv_b_b_time2);
		mAdd = (TextView) v.findViewById(R.id.tv_b_b_add);
		mSubmit = (Button) findViewById(R.id.tv_b_b_submit);
		mSubmit.setOnClickListener(this);
		mHorizontalScrollView2=(HorizontalListView) v.findViewById(R.id.hs_tu);
		if (category) {
			mHorizontalScrollView2.setVisibility(View.GONE);
		}
	}

	/**
	 * 
	 */
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mRight.setVisibility(View.GONE);
		mCenter.setText("个人详情");
	}
	/**
	 * onClick
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if (v==mSubmit) {
             Intent intent=new Intent(MsgBchuzu.this,XiaDanActivity.class);
			System.out.println(getIntent().getStringExtra("id")+"*************");
			intent.putExtra("id2",getIntent().getStringExtra("id"));
			startActivity(intent);
		}
	}
	/**
	 * 设置水平图片,转换成数组
	 * @param picture
	 */
	private void  setPicture(String picture){
		if(picture!=null && !picture.trim().equals("")){
			
			mypciture=picture.split(";");
			mAdapter2=new PBhSAdapter2(mypciture, MsgBchuzu.this,null);
			mHorizontalScrollView2.setAdapter(mAdapter2);
			mHorizontalScrollView2.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					showpicture(arg2);
				}
			});
		}else{
			
		}
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
						lls=LodingDialog.DialogFactor(MsgBchuzu.this, "正在加载...", false);
						if (mList.size()/pagenum == page+1) {
							page = mList.size()/30;
							Log.i("aaaaa", "aaaa=-"+page);
							PublicRequest http2 = new PublicRequest(mHandler);
							http2.Comment(MsgBchuzu.this, user_id);
						}else {
						lls.dismiss();
						com.bset.tool.Toast.ToastMe(MsgBchuzu.this, "没有更多");
						mRefreshListView.onRefreshComplete();
						}
					}
				}
			}
		});
	}
	
	
	private void showpicture(int position){
		Intent intent=new Intent(MsgBchuzu.this,PagerActivity.class);
		intent.putExtra("picture", mypciture);
		intent.putExtra("position", position);
		startActivity(intent);
	}
	//回调handler
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (lls != null) {
				lls.dismiss();
			}
			switch (msg.what) {
			case Constants.INFODETAIL:
				try {
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if(jsonObj.getString("code").equals("1")) {
						String jsonData = jsonObj.getString("data");
						InfoDetail data=JsonUtil.instance().fromJson(jsonData, new TypeToken<InfoDetail>(){}.getType());
						if(data!=null){
							ImageLoader loader = new ImageLoader(MsgBchuzu.this);
							loader.DisplayImage(HttpURL.IMGPATH+data.getFace(), mFace);
							mNickName.setText(data.getNickname());
							if (data.getSex().equals("1")) {
								mSex.setImageResource(R.drawable.iconfont_nan);
							}  else if(data.getSex().equals("2")){
								mSex.setImageResource(R.drawable.iconfont_nv);
							}else {
								mSex.setImageBitmap(null);
							}
							
							String i=data.getRank_id();
							if (i.equals("1")) {
								
							} else {
								mHuiYuan.setImageResource(R.drawable.huiyuanbg);
							} 
							
							
							
							if(data.getAge()==null||data.getAge().trim().equals("")){
								mAge.setText("未知");
							}else{
								mAge.setText(data.getAge()+"岁");
							}
							mFanWei.setText(data.getMyrange());
							mAddress.setText(data.getAddress());
							mShowSkill.setText(data.getSkills());
							mRent.setText(data.getRent()+"元");
							mWork.setText(data.getWork());
							mHeight.setText(data.getHeight());
							mUserSign.setText(data.getUsersign());
							mTime.setText(data.getSchedule_start()+"--"+data.getSchedule_end());
							if ((data.getStartdate()==null||data.getStartdate().equals(""))&&(data.getEnddate()==null||data.getEnddate().equals(""))) {
									mTime2.setText("没有发布档期时间");
							}else{
								mTime2.setText(data.getStartdate()+"--"+data.getEnddate());
							}
							
							String s = Timetool.trans_time(data.getAdd_time());
							mAdd.setText(s);
							setPicture(data.getPicture());
						}
					}else{
						Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 0).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case Constants.COMMENT:
				try {
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if(jsonObj.getString("code").equals("1")) {
						String jsonData = jsonObj.getString("data");
						ArrayList<Comment> data=JsonUtil.instance().fromJson(jsonData, new TypeToken<ArrayList<Comment>>(){}.getType());
						if(data != null){
							mList.addAll(data);
							mAdapter = new CommentAdapter(mList, MsgBchuzu.this);
							mListView.setAdapter(mAdapter);
						}
					}else{
//						Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 0).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case Constants.RESERVATION:
				try {
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if(jsonObj.getString("code").equals("1")) {
						Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 0).show();
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
	
}
