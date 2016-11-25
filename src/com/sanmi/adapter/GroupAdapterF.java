package com.sanmi.adapter;
import java.util.List;

import com.sanmi.activity.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
/**
 * GroupAdapter
 * @author Administrator
 *
 */
public class GroupAdapterF extends BaseAdapter {

	private Context context;

	private List<String> list;

	public GroupAdapterF(Context context, List<String> list) {

		this.context = context;
		this.list = list;

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
	public View getView(int position, View convertView, ViewGroup viewGroup) {

		
		ViewHolder holder;
		if (convertView==null) {
			convertView=LayoutInflater.from(context).inflate(R.layout.group_item_view2, null);
			holder=new ViewHolder();
			
			convertView.setTag(holder);
			
			holder.groupItem=(TextView) convertView.findViewById(R.id.groupItem2);
//			holder.mImageView=(ImageView) convertView.findViewById(R.id.iv_popnannv);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
//		holder.groupItem.setTextColor(Color.BLACK);
		holder.groupItem.setText(list.get(position));
//		if (position==0) {
//			holder.mImageView.setBackgroundResource(R.drawable.iconfont_nan);
//		}
//		if (position==1) {
//			holder.mImageView.setBackgroundResource(R.drawable.iconfont_nv);
//		}
		
		return convertView;
	}

	static class ViewHolder {
		private TextView groupItem;
//		private ImageView mImageView;
	}

}
