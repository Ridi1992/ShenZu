package com.sanmi.business;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;

import com.sanmi.fengzhuang.GchuzuDatas;
/**
 * ŮϢ
 * @author Administrator
 *
 */
public class GrilData {
	private ArrayList<GchuzuDatas> maArrayList=new ArrayList<GchuzuDatas>();
	private GchuzuDatas mGchuzuDatas;
	public void Gdatas(final Handler handler){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					mGchuzuDatas=new GchuzuDatas();
					mGchuzuDatas.setName("我叫"+i);
					maArrayList.add(mGchuzuDatas);
				}
				Message message=Message.obtain();
				message.what=1;
				message.obj=maArrayList;
				handler.sendMessage(message);
				
			}
		}).start();
	}

}
