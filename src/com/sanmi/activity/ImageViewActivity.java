package com.sanmi.activity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.polites.android.GestureImageView;
import com.sanmi.view.Title;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
public class ImageViewActivity extends Activity {
	private String intent;
	private com.polites.android.GestureImageView mImageView;
	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imageview);
		new Title(this);
		mImageView=(GestureImageView) findViewById(R.id.image);
		mTextView=(TextView) findViewById(R.id.tv_remove);
		intent = getIntent().getStringExtra("00000");
		final int position=getIntent().getIntExtra("position", 0);
		mTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FaBuActivity.mcontent.deletepciture(position);
				finish();
			}
		});
		ImageLoader.getInstance().displayImage("file://" + intent, mImageView);

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.finish();
	}
}
