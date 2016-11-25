package com.sanmi.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bset.tool.Texttool;
import com.sanmi.activity.R;

public class DialoglistAadapter extends BaseAdapter{

	private Context mcontent;
	private ArrayList<String> arry;
	public DialoglistAadapter(Context mcontent, ArrayList<String> arry) {
		this.mcontent=mcontent;
		this.arry=arry;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView= LayoutInflater.from(mcontent).inflate(R.layout.dialogitem, null);
		Texttool.setText(convertView,R.id.text ,arry.get(position));
		return convertView;
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(arry!=null){
			return arry.size();
		}
		return 0;
	}


}
