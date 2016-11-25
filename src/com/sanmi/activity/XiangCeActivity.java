package com.sanmi.activity;

import java.util.ArrayList;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sanmi.view.Title;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * ѡ
 * 
 * @author Administrator
 * 
 */
public class XiangCeActivity extends BaseActivity {
	private static final int REQUEST_PICK = 0;
	private GridView gridview;
	private ImageView mImageView;
	private GridAdapter adapter;
	private ArrayList<String> selectedPicture = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_xiangce);
		new Title(this);//״̬ɫ
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(100 * 1024 * 1024).diskCacheFileCount(300)
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
		gridview = (GridView) findViewById(R.id.gridview);
		mImageView=(ImageView) findViewById(R.id.iv_suofang);
		
		adapter = new GridAdapter();
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent data = new Intent(XiangCeActivity.this,ImageViewActivity.class);
		        data.putExtra("00000", selectedPicture.get(arg2));
		  
				startActivity(data);
			}
		});

	}

	/**
	 * ѡƬť
	 * 
	 * @param view
	 */
	public void selectPicture(View view) {
		startActivityForResult(new Intent(this, SelectPictureActivity.class),
				REQUEST_PICK);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			selectedPicture = (ArrayList<String>) data
					.getSerializableExtra(SelectPictureActivity.INTENT_SELECTED_PICTURE);
			adapter.notifyDataSetChanged();
			if (requestCode==20) {
				
			}
		}
	}

	/**
	 * ڲͼƬʾGridAdapter
	 * 
	 * @author Administrator
	 * 
	 */
	class GridAdapter extends BaseAdapter {
		LayoutParams params = new AbsListView.LayoutParams(200, 200);

		@Override
		public int getCount() {
			return selectedPicture.size();
		}

		@Override
		public Object getItem(int position) {
			return selectedPicture.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = new ImageView(XiangCeActivity.this);
				((ImageView) convertView).setScaleType(ScaleType.CENTER_CROP);
				convertView.setLayoutParams(params);
			}
			ImageLoader.getInstance().displayImage(
					"file://" + selectedPicture.get(position),
					(ImageView) convertView);
			return convertView;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.finish();
	}
}
