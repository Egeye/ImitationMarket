package com.or.googlemarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_start);

		AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
		aa.setDuration(1500);
		findViewById(R.id.rl_start).startAnimation(aa);

		new Thread() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					enterMain();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}.start();
	}

	/**
	 * 进入主界面
	 */
	private void enterMain() {
		Intent i = new Intent(this, MainActivity.class);
		finish();
		startActivity(i);
	}

}
