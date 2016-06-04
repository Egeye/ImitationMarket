package com.or.googlemarket;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 代表当前应用程序
 * 
 * @author Octavio
 *
 */
public class BaseApplication extends Application {

	private static BaseApplication application;
	private static int mainTid;
	private static Handler handler;

	// 在主线程中运行的
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;

		mainTid = android.os.Process.myTid();

		handler = new Handler();
	}

	public static int getMainTid() {
		return mainTid;
	}

	public static Handler getHandler() {
		return handler;
	}

	public static Context getApplication() {
		return application;
	}
}
