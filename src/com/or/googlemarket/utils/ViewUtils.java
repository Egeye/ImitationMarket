package com.or.googlemarket.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class ViewUtils {
	
	public static void removeParent(View v){
		//���ҵ����ؼ�����ͨ�����ؼ�ȥ�Ƴ�����
		ViewParent parent =v.getParent();
		
		//���еĿؼ����и��ؼ���һ������¾���ViewGroup
		if(parent instanceof ViewGroup){
			ViewGroup group = (ViewGroup) parent;
			group.removeView(v);
		}
	}

}
