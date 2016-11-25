package com.sanmi.adapter;

import java.util.List;

import com.sanmi.activity.R;
import com.sanmi.adapter.GroupAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * griview
 * @author Administrator
 *
 */
public class GvAdapter extends BaseAdapter {

	private Context context;

	private List<String> list;

	public GvAdapter(Context context, List<String> list) {

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
			convertView=LayoutInflater.from(context).inflate(R.layout.gv_item_view, null);
			holder=new ViewHolder();
			
			convertView.setTag(holder);
			
			holder.groupItem=(TextView) convertView.findViewById(R.id.tv_gv02);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.groupItem.setText(list.get(position));
		switch (position) {
		case 0:
			holder.groupItem.setBackgroundResource(R.drawable.free_green);
			break;
		case 1:
			holder.groupItem.setBackgroundResource(R.drawable.free_blue);
			break;
		case 2:
			holder.groupItem.setBackgroundResource(R.drawable.free_rose);
			break;
		case 3:
			holder.groupItem.setBackgroundResource(R.drawable.free_purple);
			break;
		case 4:
			holder.groupItem.setBackgroundResource(R.drawable.free_yellow);
			break;
		case 5:
			holder.groupItem.setBackgroundResource(R.drawable.free_green);
			break;
		case 6:
			holder.groupItem.setBackgroundResource(R.drawable.free_blue);
			break;
		case 7:
			holder.groupItem.setBackgroundResource(R.drawable.free_rose);
			break;
		case 8:
			holder.groupItem.setBackgroundResource(R.drawable.free_purple);
			break;
		case 9:
			holder.groupItem.setBackgroundResource(R.drawable.free_yellow);
			break;
		case 10:
			holder.groupItem.setBackgroundResource(R.drawable.free_green);
			break;
		case 11:
			holder.groupItem.setBackgroundResource(R.drawable.free_blue);
			break;
		case 12:
			holder.groupItem.setBackgroundResource(R.drawable.free_rose);
			break;
		case 13:
			holder.groupItem.setBackgroundResource(R.drawable.free_purple);
			break;
		case 14:
			holder.groupItem.setBackgroundResource(R.drawable.free_yellow);
			break;
	

		default:
			break;
		}
		return convertView;
	}

	static class ViewHolder {
		TextView groupItem;
		
	}

}
