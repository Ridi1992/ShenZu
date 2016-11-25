package com.sanmi.business;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;

import com.sanmi.fengzhuang.BchuzuDatas;
/**
 * Ϣ
 * @author Administrator
 *
 */
public class BchuzuData {
	private ArrayList<BchuzuDatas> mArrayList=new ArrayList<BchuzuDatas>();
	private BchuzuDatas mBchuzuDatas;
	public void bchuzu(final Handler handler){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					mBchuzuDatas=new BchuzuDatas();
					mBchuzuDatas.setName("我叫"+i);
					mArrayList.add(mBchuzuDatas);
					
				}
				Message message=Message.obtain();
				message.obj=mArrayList;
				message.what=1;
				handler.sendMessage(message);
			}
		}).start();
	}

}
