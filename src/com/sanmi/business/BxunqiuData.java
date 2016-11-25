package com.sanmi.business;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;

import com.sanmi.fengzhuang.BchuzuDatas;
import com.sanmi.fengzhuang.BxunqiuDatas;
/**
 * ѰϢ
 * @author Administrator
 *
 */
public class BxunqiuData {
	private ArrayList<BxunqiuDatas> mArrayList=new ArrayList<BxunqiuDatas>();
	private BxunqiuDatas mBxunqiuDatas;
	public void bxunqiu(final Handler handler){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					mBxunqiuDatas=new BxunqiuDatas();
					mBxunqiuDatas.setName("我叫"+i);
					mArrayList.add(mBxunqiuDatas);
					
				}
				Message message=Message.obtain();
				message.obj=mArrayList;
				message.what=2;
				handler.sendMessage(message);
			}
		}).start();
	}

}
