package com.bset.tool;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class Toast {
	public static void ToastMe(Context context,String text) {
		 android.widget.Toast.makeText(context, text, 0).show();
	}
	public static void ToastMe(Activity context,String text,int time) {
		 android.widget.Toast.makeText(context, text, time).show();
	}
	public static void ToastMe(Application application, String text, int time) {
		 android.widget.Toast.makeText(application, text, time).show();
		
	}
}
