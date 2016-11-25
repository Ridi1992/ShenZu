package com.sanmi.activity;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bset.tool.Texttool;
import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.zuwo.data.Accept;
import com.zuwo.userinfo.mUserInfo;

public class CommentActivity extends BaseActivity {

	private LodingDialog dialog;
	private double comment_rank=0;
	private String message_userid,appointment_id;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.comment);
		initview();
		message_userid=getIntent().getStringExtra("user_id");
		appointment_id=getIntent().getStringExtra("appointment_id");
	}

	private void initview(){
		findViewById(R.id.send).setOnClickListener(this);
		RatingBar rank=(RatingBar) findViewById(R.id.start_pharmacy);
		rank.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				comment_rank=rating;
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.send:
			sendcomment(Texttool.Gettext(CommentActivity.this, R.id.content));
			break;
		}
	}
	/**
	 * 提交评论
	 * @param content
	 */
	private void sendcomment(String content) {
		dialog = LodingDialog.DialogFactor(CommentActivity.this, "正在提交评论",
				false);
		PublicRequest http = new PublicRequest(handler2);
		String user_id=mUserInfo.GetUserInfo(CommentActivity.this).getId();
		http.comment(getApplicationContext(), appointment_id, user_id, content, comment_rank+"", message_userid);
	}

	private Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				if (dialog != null) {
					dialog.dismiss();
				}
				switch (msg.what) {
				case Constants.SENDCOMMENT:
					JSONObject jsonObj1 = new JSONObject(msg.obj.toString());
					if (jsonObj1.getString("code").equals("1")) {
//						String jsonData = jsonObj1.getString("data");
//						Accept mList = JsonUtil.instance().fromJson(jsonData,
//								new TypeToken<Accept>() {
//								}.getType());
						Toast.makeText(getApplicationContext(),
								"评论成功", 0).show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(),
								"请填写评价内容", 0).show();
					}
					break;
				case 404:
					Toast.makeText(getApplicationContext(), msg.obj.toString(),
							0).show();
					break;
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		};
	};

	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mCenter.setText("评价");
		mRight.setVisibility(View.GONE);
		mLeft.setVisibility(View.VISIBLE);
	}
}
