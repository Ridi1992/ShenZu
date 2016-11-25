package com.sanmi.view;

import android.app.Activity;
import android.content.Context;

import com.sanmi.activity.R;
/**
 *״̬ɫ 
 */
public class Title {
	
	public Title(Activity context) {
		SystemBarTintManager mTintManager = new SystemBarTintManager(context);
		mTintManager.setStatusBarTintEnabled(true);
		mTintManager.setNavigationBarTintEnabled(true);
		mTintManager.setTintColor(context.getResources().getColor(R.color.font_ck));
	}
}
