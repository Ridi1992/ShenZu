package com.sanmi.activity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bset.tool.Texttool;
import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;
import com.sanmi.adapter.MyCallAdapter;
import com.sanmi.http.Constants;
import com.sanmi.http.HttpURL;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.loader.ImageLoader;
import com.sanmi.view.CircularImage;
import com.zuwo.data.MeCallData;
import com.zuwo.userinfo.mUserInfo;

public class MeCallActivity extends BaseActivity {
	public static MeCallActivity activity;
	private RadioButton mButtonLeft;
	private RadioButton mButtonRight;
	private String selecttype = "1";// 1是出租预约 2是寻求预约
	private ListView mListView;
	private MyCallAdapter mCallAdapter;
	private ArrayList<MeCallData> mList = new ArrayList<MeCallData>();
	private LodingDialog mDialog;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.mycall);
		activity=this;
		mButtonLeft = (RadioButton) findViewById(R.id.rb_title2_01);
		mButtonRight = (RadioButton) findViewById(R.id.rb_title2_02);
		mListView = (ListView) findViewById(R.id.lv_mycall);

		mCallAdapter = new MyCallAdapter(this, null);
		mListView.setAdapter(mCallAdapter);
		gethttp(selecttype);
	}

	/**
	 * 刷新数据
	 */
	public void refresh(){
		mList = new ArrayList<MeCallData>();
		mCallAdapter.setmList(mList);
		mCallAdapter.notifyDataSetChanged();
		gethttp(selecttype);
	}
	
	private void gethttp(String type) {
		String user_id = mUserInfo.GetUserInfo(MeCallActivity.this).getId();
		mDialog = LodingDialog.DialogFactor(MeCallActivity.this, "正在加载", false);
		PublicRequest http = new PublicRequest(handler2);
		http.mecall(MeCallActivity.this, user_id, type);
	}
    /**
     * 
     */
	public void Pay(String id) {
		Intent intent=new Intent(MeCallActivity.this,ZhiFuActivity.class);
		intent.putExtra("appointment_id", id);
		startActivityForResult(intent, 10001);
	}
	public void ShowTel(MeCallData data){
		 	AlertDialog.Builder builder = new AlertDialog.Builder(
				 MeCallActivity.this);
	        final View view = LayoutInflater.from(MeCallActivity.this).inflate(
	                R.layout.dialog_showtel, null);
	        builder.setView(view);
	        final Dialog dialog = builder.create();
	        dialog.show();
	        dialog.getWindow().setLayout((int)( MeCallActivity.this.getWindowManager().getDefaultDisplay().getWidth()), WindowManager.LayoutParams.WRAP_CONTENT);
	        Texttool.setText(view, R.id.name, data.getNickname());
	       String tel="";
	        if(selecttype.equals("1")){
	        	tel=data.getPublisher_tel();
	       }else{
	    	   tel=data.getTel();
	       }
	        if(tel!=null && !tel.equals("")){
	        	 Texttool.setText(view, R.id.tel, tel);
	        }else{
	        	Texttool.setText(view, R.id.tel, "未知号码");
	        }
	        String imgurl=data.getFace();
	        if(imgurl!=null){
	        	CircularImage head=(CircularImage) view.findViewById(R.id.user_head);
	        	ImageLoader imageLoader=new ImageLoader(MeCallActivity.this);
	        	imageLoader.DisplayImage(HttpURL.IMGPATH+imgurl, head);
	        }
	        view.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
	        final String calltel=tel;
	        view.findViewById(R.id.call).setOnClickListener(new OnClickListener() {
	        	@Override
	        	public void onClick(View v) {
	        		dialog.dismiss();
	        		Intent intent2 = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+calltel));
					startActivity(intent2);
	        	}
	        });
	}
    /**
     * 接受
     * @param id
     */
	public void Accept(String appointment_id,String message_id) {
		mDialog = LodingDialog.DialogFactor(MeCallActivity.this, "正在接受", false);
		PublicRequest http = new PublicRequest(handler2);
		http.mecallaccept(MeCallActivity.this, appointment_id, message_id,selecttype,mUserInfo.GetUserInfo(MeCallActivity.this).getId());
	}
	/**
	 * 支付按钮
	 */
	public void Confirm(String appointment_id){ 
		mDialog = LodingDialog.DialogFactor(MeCallActivity.this, "正在确认支付", false);
		PublicRequest http = new PublicRequest(handler2);
		http.conform_pay(MeCallActivity.this, appointment_id,selecttype,mUserInfo.GetUserInfo(MeCallActivity.this).getId());
	}
	public void StartCommentActivity(String user_id,String appointment_id){
		Intent intent=new Intent(this,CommentActivity.class);
		intent.putExtra("appointment_id", appointment_id);
		intent.putExtra("user_id", user_id);
		startActivity(intent);
	}
	public void StartToIserinfoActivity(String user_id){
		Intent mIntent=new Intent(MeCallActivity.this,MySelfActivity.class);
		mIntent.putExtra("user_id",user_id);
		startActivity(mIntent);
	}
	private Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				if (mDialog != null) {
					mDialog.dismiss();
				}
				switch (msg.what) {
				case Constants.MECALL:
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if (jsonObj.getString("code").equals("1")) {
						String jsondata = jsonObj.getString("data");
						mList = JsonUtil.instance().fromJson(jsondata,
								new TypeToken<ArrayList<MeCallData>>() {
								}.getType());
						if (mList != null) {
							mCallAdapter.setmList(mList);
							mCallAdapter.notifyDataSetChanged();
						}
					}else{
						Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 0).show();
					}
					break;

				case Constants.MECALLACCEPT:
					JSONObject jsonObj1 = new JSONObject(msg.obj.toString());
					if (jsonObj1.getString("code").equals("1")) {
						String jsondata = jsonObj1.getString("data");
						mList = JsonUtil.instance().fromJson(jsondata,
								new TypeToken<ArrayList<MeCallData>>() {
								}.getType());
						if (mList != null) {
							
							mCallAdapter.setmList(mList);
							mCallAdapter.notifyDataSetChanged();
						}
						com.bset.tool.Toast.ToastMe(getApplicationContext(), "接受成功");
					}else{
						Toast.makeText(getApplicationContext(), jsonObj1.getString("message"), 0).show();
					}
					break;
				case Constants.CONFIRMPAY:
					JSONObject jsonObj2 = new JSONObject(msg.obj.toString());
					if (jsonObj2.getString("code").equals("1")) {
						String jsondata = jsonObj2.getString("data");
						mList = JsonUtil.instance().fromJson(jsondata,
								new TypeToken<ArrayList<MeCallData>>() {
						}.getType());
						if (mList != null) {
							mCallAdapter.setmList(mList);
							mCallAdapter.notifyDataSetChanged();
						}
						com.bset.tool.Toast.ToastMe(getApplicationContext(), "支付成功");
					}else{
						Toast.makeText(getApplicationContext(), jsonObj2.getString("message"), 0).show();
					}
					break;
				
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		};
	};

	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mCenter.setText("我的预约");
		mRight.setVisibility(View.GONE);
	}

	@Override
	protected void SetTitle2View(RadioGroup mGroup, RadioButton mButtonLeft,
			RadioButton mButtonRight) {
		super.SetTitle2View(mGroup, mButtonLeft, mButtonRight);
		mButtonLeft.setText("我约了谁");
		mButtonRight.setText("谁约了我");
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.rb_title2_01:
			if (selecttype.equals("1")) {
				return;
			}
			selecttype = "1";
			mList = new ArrayList<MeCallData>();
			mCallAdapter.setmList(mList);
			mCallAdapter.notifyDataSetChanged();
			gethttp(selecttype);
			break;
		case R.id.rb_title2_02:
			if (selecttype.equals("2")) {
				return;
			}
			selecttype = "2";
			mList = new ArrayList<MeCallData>();
			mCallAdapter.setmList(mList);
			mCallAdapter.notifyDataSetChanged();
			gethttp(selecttype);
			break;

		default:
			break;
		}
	}

}
