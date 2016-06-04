package com.or.googlemarket;

import java.util.LinkedList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class BaseActivity extends ActionBarActivity {
	private KillAllReceiver receiver;
	public static BaseActivity activity;

	// 管理运行的所有的Activity 必须静态，final的保障对象唯一存在
	final static List<BaseActivity> mActivities = new LinkedList<BaseActivity>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		receiver = new KillAllReceiver();
		registerReceiver(receiver, new IntentFilter("com.or.googlemarket.killall"));

		synchronized (mActivities) {
			mActivities.add(this);
		}

		init();
		initView();
		initActionBar();
	}

	protected void init() {
	}

	protected void initView() {
	}

	protected void initActionBar() {
	}

	@Override
	protected void onResume() {
		super.onResume();
		activity = this;
	}

	@Override
	protected void onPause() {
		super.onPause();
		activity = null;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		synchronized (mActivities) {
			mActivities.remove(this);
		}

		if (receiver != null) {
			unregisterReceiver(receiver);
			receiver = null;
		}
	}

	public void killAll() {
		List<BaseActivity> copy;
		synchronized (mActivities) {
			copy = new LinkedList<BaseActivity>(mActivities);
		}

		for (BaseActivity activity : copy) {
			activity.finish();
		}
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 管理关闭Activity的广播接受者
	 * 
	 * @author Octavio
	 *
	 */
	private class KillAllReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}

	}
}
