package com.sanmi.business;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;

import com.sanmi.fengzhuang.MeDatas;
import com.sanmi.fengzhuang.MsgDatas;
/**
 * Ϣ
 * @author Administrator
 *
 */
public class MsgData {

	private ArrayList<MsgDatas> mList=new ArrayList<MsgDatas>();
	private MsgDatas mMeDatas;
	public void msgData(final Handler handler){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 5; i++) {
					mMeDatas=new MsgDatas();
					mMeDatas.setTishi("我叫"+i);
					mList.add(mMeDatas);
				}
				Message mMessage=Message.obtain();
				mMessage.obj=mList;
				handler.sendMessage(mMessage);
			}
		}).start();
	}
}
