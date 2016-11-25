package com.sanmi.view;
import java.util.List;

import com.sanmi.activity.R;
import com.zuwo.data.Regions;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
public class DialogUtil {
	private static Dialog dialog;
	public static void creatCityDialog(Activity context
			,List<Regions> text, OnItemClickListener listener){
		View inflater = LayoutInflater.from(context).inflate(
				R.layout.dialog_city, null);
		dialog = new Dialog(context, R.style.MyDialog);
		dialog.setContentView(inflater);
		dialog.show();
		// 点击屏幕外侧，dialog不消失
		dialog.setCanceledOnTouchOutside(false);
		ListView mListView = (ListView) inflater
				.findViewById(R.id.dialog_lv);
		
		mListView.setAdapter(new MyAdapter(context, text, null));
		
		mListView.setOnItemClickListener(listener);
		
	}
	public static void dismiss() {
		if (dialog!=null) {
			dialog.dismiss();
		}
}
	/**
	 * 内部类 List dialog  适配器
	 */
	static class MyAdapter extends BaseAdapter{
		private Activity activity;
		private LayoutInflater layout;
		private List<Regions> list;
		public MyAdapter(Activity activity,List<Regions> list, LayoutInflater layout) {
			this.activity = activity;
			this.list=list;
			this.layout = LayoutInflater.from(activity);
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder=null;
			if (convertView==null) {
				holder=new ViewHolder();
				convertView=layout.inflate(R.layout.activity_list_item, null);
				holder.text=(TextView) convertView.findViewById(R.id.listview_item);
				convertView.setTag(holder);
			}
			else{
				holder=(ViewHolder) convertView.getTag();
			}
			holder.text.setText(list.get(position).getName());
			return convertView;
		}
		class ViewHolder{
		private TextView text;	
		}
		
	}

}
