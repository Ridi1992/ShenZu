package com.sanmi.adapter;

import java.util.ArrayList;

import com.sanmi.activity.R;
import com.sanmi.adapter.BchuzuAdapter.ViewHorder;
import com.sanmi.http.HttpURL;
import com.sanmi.loader.ImageLoader;
import com.sanmi.view.CircularImage;
import com.zuwo.data.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter{
	
	private ArrayList<Comment> mList;
	private LayoutInflater mInflater;
	private ImageLoader loader;
	
	public CommentAdapter(ArrayList<Comment> list,Context c){
		mList = list;
		mInflater = LayoutInflater.from(c);
		loader = new ImageLoader(c);
	}

	@Override
	public int getCount() {
		if (mList != null) {
			return mList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (mList != null) {
			return mList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHorder viewHorder = null;
		if (convertView == null) {
			viewHorder=new ViewHorder();
			convertView=mInflater.inflate(R.layout.list_item_comment, null);
			viewHorder.name = (TextView) convertView.findViewById(R.id.comment_nickname);
			viewHorder.time = (TextView) convertView.findViewById(R.id.comment_time);
			viewHorder.content = (TextView) convertView.findViewById(R.id.comment_content);
			convertView.setTag(viewHorder);
		}else {
			viewHorder=(ViewHorder) convertView.getTag();
		}
		
		CircularImage face = (CircularImage) convertView.findViewById(R.id.comment_face);
		
		Comment comment = mList.get(position);
		
		loader.DisplayImage(HttpURL.IMGPATH+comment.getFace(), face);
		viewHorder.name.setText(comment.getNickname());
		viewHorder.time.setText(comment.getAdd_time());
		viewHorder.content.setText(comment.getContent());
		
		return convertView;
	}
	
	class ViewHorder{
		
		private TextView name;
		private TextView time;
		private TextView content;
		
	}
}
