package com.sanmi.business;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;

import com.sanmi.fengzhuang.GchuzuDatas;
import com.sanmi.fengzhuang.GxunqiuDatas;
/**
 * ŮѰϢ
 * @author Administrator
 *
 */
public class GrilData2 {
	private ArrayList<GxunqiuDatas> maArrayList=new ArrayList<GxunqiuDatas>();
	private GxunqiuDatas GxunqiuDatas;
	public void Gdatas2(final Handler handler){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					GxunqiuDatas=new GxunqiuDatas();
					GxunqiuDatas.setName("我叫"+i);
					maArrayList.add(GxunqiuDatas);
				}
				Message message=Message.obtain();
				message.what=2;
				message.obj=maArrayList;
				handler.sendMessage(message);
				
			}
		}).start();
	}

}
