package com.or.googlemarket.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewUtils {
	
	public static void removeParent(View v){
		//先找到父控件，再通过父控件去移除孩子
		ViewParent parent =v.getParent();
		
		//所有的控件都有父控件，一般情况下就是ViewGroup
		if(parent instanceof ViewGroup){
			ViewGroup group = (ViewGroup) parent;
			group.removeView(v);
		}
	}

}
