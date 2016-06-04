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
	 * 获取字符组
	 * 
	 * @param tabNames
	 *            字符组的id
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

	/** dip转换px */
	public static int dip2px(int dip) {
		final float scale = getResource().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/** px转换dip */
	public static int px2dip(int px) {
		final float scale = getResource().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	// 获取dimens文件数据_R.dimen.xxx
	public static int getDimens(int homePictureHeight) {
		return (int) getResource().getDimension(homePictureHeight);
	}

	/**
	 * 把Runnable方法提交到主线程中运行
	 * 
	 * @param runnable
	 */
	public static void runOnUiThread(Runnable runnable) {
		// 判断当是在主线程中运行的
		if (android.os.Process.myTid() == BaseApplication.getMainTid()) {
			runnable.run();
		} else {
			// 拿到主线程的handler
			BaseApplication.getHandler().post(runnable);
		}
	}

	/**
	 * listView的布局加载方法
	 */
	public static View inflate(int layoutId) {
		return View.inflate(getContext(), layoutId, null);
	}

	public static Drawable getDrawalbe(int drawableId) {

		return getResource().getDrawable(drawableId);
	}

	/**
	 * 延迟执行任务
	 * 
	 * @param run
	 * @param time
	 */
	public static void postDelayed(Runnable task, int time) {
		BaseApplication.getHandler().postDelayed(task, time);
	}

	/**
	 * 取消任务
	 * 
	 * @param task
	 */
	public static void cancelTask(Runnable task) {
		BaseApplication.getHandler().removeCallbacks(task);
	}

	/**
	 * 打开新的Activity
	 * 
	 * @param i
	 */
	public static void startActivity(Intent i) {
		// 如果不在Activity里去打开Activity，需要指定任务栈,需要设置标签
		// 如果没有Activity
		if (BaseActivity.activity == null) {
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getContext().startActivity(i);
		} else {
			BaseActivity.activity.startActivity(i);
		}
	}
}
