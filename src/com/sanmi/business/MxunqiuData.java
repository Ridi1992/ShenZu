package com.sanmi.business;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.http.entity.SerializableEntity;

import android.os.Handler;
import android.os.Message;

import com.sanmi.fengzhuang.MxunqiuDatas;
/**
 * ѰϢ
 * @author Administrator
 *
 */
public class MxunqiuData implements Serializable{
	private MxunqiuDatas mDatas;
	private ArrayList<MxunqiuDatas>  mArrayList2=new ArrayList<MxunqiuDatas>();
	public void Mxunqiudata(final Handler handler){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					mDatas=new MxunqiuDatas();
					mDatas.setName("我叫"+i+"");
					mArrayList2.add(mDatas);
				}
				Message message=Message.obtain();
				message.what=2;
				message.obj=mArrayList2;
				handler.sendMessage(message);
			}
		}).start();
		
	}

}
