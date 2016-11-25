package com.sanmi.business;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;

import com.sanmi.fengzhuang.MchuzuDatas;
/**
 * Ϣ
 * @author Administrator
 *
 */
public class MoneyData {
	private ArrayList<MchuzuDatas> mArrayList1=new ArrayList<MchuzuDatas>();
	private MchuzuDatas mchuzuDatas;
	public void mchuzu(final Handler handler){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					mchuzuDatas=new MchuzuDatas();
					mchuzuDatas.setName("我叫:"+i);
					mArrayList1.add(mchuzuDatas);
				}
				Message message=Message.obtain();
				message.obj=mArrayList1;
				message.what=1;
				handler.sendMessage(message);
				
			}
		}).start();
	}

}
