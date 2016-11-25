package com.sanmi.activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * appЭ
 * @author Administrator
 *
 */
public class XieYiActivity extends BaseActivity{
	private MyTextView textView;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_xieyi);
		textView=(MyTextView) findViewById(R.id.textview);
		String str=textView.getText().toString();
/*//		int bstart=str.indexOf("（注：协议请您仔细阅读特别是红色部分，如有不理解地方请参阅官网客服页面，或与客服联系：电话 13359251686）");
//        int bend=bstart+"（注：协议请您仔细阅读特别是红色部分，如有不理解地方请参阅官网客服页面，或与客服联系：电话 13359251686）".length();
        int fstart=str.indexOf("前景");
        int fend=fstart+"前景".length();
        SpannableStringBuilder style=new SpannableStringBuilder(str); 
//        style.setSpan(new BackgroundColorSpan(Color.RED),bstart,bend,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);   
        style.setSpan(new ForegroundColorSpan(Color.RED),fstart,fend,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(style);*/
		
		textView.setSpecifiedTextsColor(str, "（注：协议请您仔细阅读特别是红色部分，如有不理解地方请参阅官网客服页面，或与客服联系：电话 13359251686）",Color.parseColor("#FF0000"));
	}
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mLeft.setVisibility(View.VISIBLE);
		mRight.setVisibility(View.GONE);
		mCenter.setText("协议");
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.finish();
	}

}
