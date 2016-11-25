package com.sanmi.activity;

import java.util.ArrayList;
import org.json.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.sanmi.adapter.PbHsAdapter;
import com.sanmi.http.Constants;
import com.sanmi.http.HttpURL;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.loader.ImageLoader;
import com.sanmi.view.CircularImage;
import com.zuwo.data.InfoDetail;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * ѳϢ
 * @author Administrator
 *
 */
public class MsgPMchuzu extends BaseActivity {
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
	private Button mSubmit;
	private TextView mFanWei;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.pingming_b);
		init();//ʼ
		String id = getIntent().getStringExtra("id");
		
		PublicRequest http = new PublicRequest(mHandler);
		http.InfoDetail(MsgPMchuzu.this, id);

	}
     /**
      * ʼ
      */
	private void init() {
		mFanWei=(TextView)findViewById(R.id.hs_pb);
		mFace = (CircularImage) findViewById(R.id.detail_face);
		mNickName = (TextView) findViewById(R.id.tv_pm_b_name);
		mSex = (ImageView) findViewById(R.id.iv_pm_b_sex);
		mAge = (TextView) findViewById(R.id.tv_pm_b_age);
		mAddress = (TextView) findViewById(R.id.tv_pm_b_address);
		mShowSkill = (TextView) findViewById(R.id.tv_pm_b_skill);
		mRent = (TextView) findViewById(R.id.tv_pm_b_rent);
		mWork = (TextView) findViewById(R.id.tv_pm_b_work);
		mHeight = (TextView) findViewById(R.id.tv_pm_b_height);
		mUserSign = (TextView) findViewById(R.id.tv_pm_b_sign);
		mTime = (TextView) findViewById(R.id.tv_pm_b_time);
		mSubmit = (Button) findViewById(R.id.tv_b_b_submit22);
		mSubmit.setOnClickListener(this);
	}

	/**
	 * 设置标题
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
	 * onclick监听
	 */
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_b_b_submit22:
			Intent intent=new Intent(MsgPMchuzu.this,XiaDanActivity.class);
			
			System.out.println(getIntent().getStringExtra("id")+"*************");
			intent.putExtra("id2",getIntent().getStringExtra("id"));
			startActivity(intent);
			break;
		}
	}
	
	//回调handler
	private Handler mHandler = new Handler() {
		private PbHsAdapter mHsAdapter;
		private ArrayList<String> mArrayList;

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Constants.INFODETAIL:
				try {
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if(jsonObj.getString("code").equals("1")) {
						String jsonData = jsonObj.getString("data");
						InfoDetail data=JsonUtil.instance().fromJson(jsonData, new TypeToken<InfoDetail>(){}.getType());
						if(data!=null){
							ImageLoader loader = new ImageLoader(MsgPMchuzu.this);
							loader.DisplayImage(HttpURL.IMGPATH+data.getFace(), mFace);
							mNickName.setText(data.getNickname());
							if (data.getSex().equals("1")) {
								mSex.setImageResource(R.drawable.iconfont_nan);
							}  else if(data.getSex().equals("2")){
								mSex.setImageResource(R.drawable.iconfont_nv);
							}else {
								mSex.setImageBitmap(null);
							}
							mFanWei.setText(data.getMyrange());
							mAge.setText(data.getAge());
							mAddress.setText(data.getAddress());
							mShowSkill.setText(data.getSkills());
							mRent.setText(data.getRent()+"");
							mWork.setText(data.getWork());
							mHeight.setText(data.getHeight());
							mUserSign.setText(data.getUsersign());
							mTime.setText(data.getSchedule_start()+"--"+data.getSchedule_end());
						}
					}else{
						Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 0).show();
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
