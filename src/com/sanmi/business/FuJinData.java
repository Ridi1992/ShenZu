package com.sanmi.business;

import java.util.ArrayList;

import com.sanmi.adapter.FuJinAdapter;
import com.sanmi.fengzhuang.FuJinDatas;
import com.sanmi.refresh.PullToRefreshBase;
import com.sanmi.refresh.PullToRefreshListView;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.widget.BaseAdapter;
import android.widget.ListView;
/**
 * ݷ
 * @author Administrator
 *
 */
public class FuJinData {
	private String s="123";
	private FuJinDatas mDatas;
	private ArrayList<FuJinDatas>  mList=new ArrayList<FuJinDatas>();
	public void fujindata(final Handler handler){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				mDatas=new FuJinDatas();
				mDatas.setName(s);
				mList.add(mDatas);
				Message mMessage=Message.obtain();
				mMessage.obj=mList;
				handler.sendMessage(mMessage);
			}
		}).start();
	}

}
