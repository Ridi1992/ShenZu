package com.sanmi.business;

import java.util.ArrayList;

import com.sanmi.fengzhuang.MeDatas;

import android.os.Handler;
import android.os.Message;
/**
 * ҵϢݷ
 * @author Administrator
 *
 */
public class MeData {
	private ArrayList<MeDatas> mList=new ArrayList<MeDatas>();
	private MeDatas mMeDatas;
	public void meData(final Handler handler){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 5; i++) {
					mMeDatas=new MeDatas();
					mMeDatas.setText("我叫"+i);
					mList.add(mMeDatas);
				}
				Message mMessage=Message.obtain();
				mMessage.obj=mList;
				handler.sendMessage(mMessage);
			}
		}).start();
	}

}
