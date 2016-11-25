package com.sanmi.business;

import android.os.Handler;
import android.os.Message;
/**
 * ҳݷ
 * @author Administrator
 *
 */
public class HomeDate {
	private String s="我叫";
	public void homedata(final Handler handler){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Message mMessage=Message.obtain();
			    
				mMessage.obj=s;
				handler.sendMessage(mMessage);
			}
		}).start();
	}
}
