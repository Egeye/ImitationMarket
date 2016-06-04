package com.or.googlemarket.utils;

import com.or.googlemarket.BaseActivity;
import com.or.googlemarket.BaseApplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;

public class UiUtils {

	/**
	 * ��ȡ�ַ���
	 * 
	 * @param tabNames
	 *            �ַ����id
	 */
	public static String[] getStringArray(int tabNames) {
		return getResource().getStringArray(tabNames);
	}

	public static Resources getResource() {
		return BaseApplication.getApplication().getResources();
	}

	public static Context getContext() {
		return BaseApplication.getApplication();
	}

	/** dipת��px */
	public static int dip2px(int dip) {
		final float scale = getResource().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/** pxת��dip */
	public static int px2dip(int px) {
		final float scale = getResource().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	// ��ȡdimens�ļ�����_R.dimen.xxx
	public static int getDimens(int homePictureHeight) {
		return (int) getResource().getDimension(homePictureHeight);
	}

	/**
	 * ��Runnable�����ύ�����߳�������
	 * 
	 * @param runnable
	 */
	public static void runOnUiThread(Runnable runnable) {
		// �жϵ��������߳������е�
		if (android.os.Process.myTid() == BaseApplication.getMainTid()) {
			runnable.run();
		} else {
			// �õ����̵߳�handler
			BaseApplication.getHandler().post(runnable);
		}
	}

	/**
	 * listView�Ĳ��ּ��ط���
	 */
	public static View inflate(int layoutId) {
		return View.inflate(getContext(), layoutId, null);
	}

	public static Drawable getDrawalbe(int drawableId) {

		return getResource().getDrawable(drawableId);
	}

	/**
	 * �ӳ�ִ������
	 * 
	 * @param run
	 * @param time
	 */
	public static void postDelayed(Runnable task, int time) {
		BaseApplication.getHandler().postDelayed(task, time);
	}

	/**
	 * ȡ������
	 * 
	 * @param task
	 */
	public static void cancelTask(Runnable task) {
		BaseApplication.getHandler().removeCallbacks(task);
	}

	/**
	 * ���µ�Activity
	 * 
	 * @param i
	 */
	public static void startActivity(Intent i) {
		// �������Activity��ȥ��Activity����Ҫָ������ջ,��Ҫ���ñ�ǩ
		// ���û��Activity
		if (BaseActivity.activity == null) {
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getContext().startActivity(i);
		} else {
			BaseActivity.activity.startActivity(i);
		}
	}
}
