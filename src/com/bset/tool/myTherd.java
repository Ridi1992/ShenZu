package com.bset.tool;
import android.os.Handler;
import android.widget.TextView;
public class myTherd{
	private int count=60;
	private Handler handler=new Handler();
	public void Countdown(final TextView v){
		new Thread(new Runnable() {
			@Override
			public void run() {
				count=count-1;
				while (count>0) {
					System.out.println(count--+"@@@@@@@@@@");
					handler.post(new Runnable() {
						@Override
						public void run() {
							v.setText("获取验证码" + count + "" + "秒");
							v.setClickable(false);
						}
					});
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				while (count==0) {
					count=60;
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							v.setClickable(true);
							v.setText("获取验证码");
						}
					});
				}
				
			}
		}).start();

		
		
	}

}
