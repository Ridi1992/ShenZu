package com.sanmi.activity;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.sanmi.http.Constants;
import com.sanmi.http.PublicRequest;
import com.sanmi.view.Title;

public class AboutUsActivity extends BaseActivity {
	
	private TextView mContent;

	protected void onCreate(android.os.Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.about_us);
		new Title(this);
		init();// 
		PublicRequest http = new PublicRequest(mHandler);
		http.AboutUs(AboutUsActivity.this);
	};

	/**
	 * ؼ
	 */
	private void init() {
		mContent = (TextView) findViewById(R.id.about_content);
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
		mLeft.setVisibility(View.VISIBLE);
		mCenter.setText("关于我们");
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
//				if(dialog!=null){
//					dialog.dismiss();
//				}
			switch (msg.what) {
			case Constants.ABOUT:
				JSONObject jsonObj = new JSONObject(msg.obj.toString());
				if(jsonObj.getString("code").equals("1")) {
					mContent.setText("    "+jsonObj.getString("data"));
				}else{
					Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 0).show();
				}
				break;
			case 404:
				Toast.makeText(getApplicationContext(), msg.obj.toString(), 0).show();
				break;
			}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		};
	};
	
}
