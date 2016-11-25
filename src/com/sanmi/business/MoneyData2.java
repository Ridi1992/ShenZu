package com.sanmi.business;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;

import com.sanmi.fengzhuang.MchuzuDatas;
import com.sanmi.fengzhuang.MxunqiuDatas;
/**
 * ѰϢ
 * @author Administrator
 *
 */
public class MoneyData2 {
	private ArrayList<MxunqiuDatas> mArrayList1=new ArrayList<MxunqiuDatas>();
	private MxunqiuDatas mxunqiuDatas;
	public void mxunqiu(final Handler handler){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					mxunqiuDatas=new MxunqiuDatas();
					mxunqiuDatas.setName("我叫:"+i);
					mArrayList1.add(mxunqiuDatas);
				}
				Message message=Message.obtain();
				message.obj=mArrayList1;
				message.what=2;
				handler.sendMessage(message);
				
			}
		}).start();
	}

}
