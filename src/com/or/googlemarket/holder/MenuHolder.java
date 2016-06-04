package com.or.googlemarket.holder;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.or.googlemarket.R;
import com.or.googlemarket.domain.UserInfo;
import com.or.googlemarket.http.HttpHelper;
import com.or.googlemarket.manager.ThreadManager;
import com.or.googlemarket.protocol.UserProtocol;
import com.or.googlemarket.utils.UiUtils;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MenuHolder extends BaseHolder<UserInfo>implements OnClickListener {
	@ViewInject(R.id.photo_layout)
	private RelativeLayout photoLayout;

	@ViewInject(R.id.image_photo)
	private ImageView ivPhoto;

	@ViewInject(R.id.user_name)
	private TextView tvUsername;

	@ViewInject(R.id.user_email)
	private TextView tvEmail;

	@ViewInject(R.id.home_layout)
	private RelativeLayout itemHome;

	@ViewInject(R.id.setting_layout)
	private RelativeLayout itemSetting;

	@ViewInject(R.id.theme_layout)
	private RelativeLayout itemTheme;

	@ViewInject(R.id.scans_layout)
	private RelativeLayout itemPackageManager;

	@ViewInject(R.id.feedback_layout)
	private RelativeLayout itemFeedBack;

	@ViewInject(R.id.updates_layout)
	private RelativeLayout itemUpdates;

	@ViewInject(R.id.about_layout)
	private RelativeLayout itemAbout;

	@ViewInject(R.id.exit_layout)
	private RelativeLayout itemExit;

	@Override
	public View initView() {
		View view = View.inflate(UiUtils.getContext(), R.layout.menu_holder, null);
		ViewUtils.inject(this, view);
		photoLayout.setOnClickListener(this);
		itemHome.setOnClickListener(this);
		itemAbout.setOnClickListener(this);
		itemExit.setOnClickListener(this);
		itemFeedBack.setOnClickListener(this);
		itemPackageManager.setOnClickListener(this);
		itemSetting.setOnClickListener(this);
		itemTheme.setOnClickListener(this);
		itemUpdates.setOnClickListener(this);

		return view;
	}

	@Override
	public void refreshView(UserInfo data) {
		tvUsername.setText(data.getName());
		tvEmail.setText(data.getEmail());
		String url = data.getUrl();// image/user.png
		bitmapUtils.display(ivPhoto, HttpHelper.URL + "image?name=" + url);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 连接服务器 登陆
		case R.id.photo_layout:
			ThreadManager.getInstance().createLongPool().execute(new Runnable() {

				@Override
				public void run() {
					UserProtocol protocol = new UserProtocol();
					final UserInfo load = protocol.load(0);

					UiUtils.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							if (load != null) {

								setData(load);// 调用该方法就会调用RefreshView
							} else {
								Toast.makeText(UiUtils.getContext(), "登陆失败", Toast.LENGTH_SHORT).show();
							}
						}
					});
				}
			});
			break;

		// 菜单选项
		case R.id.home_layout:
			Toast.makeText(UiUtils.getContext(), "home", Toast.LENGTH_SHORT).show();
			break;

		case R.id.setting_layout:
			Toast.makeText(UiUtils.getContext(), "set", Toast.LENGTH_SHORT).show();
			break;

		case R.id.about_layout:
			Toast.makeText(UiUtils.getContext(), "about", Toast.LENGTH_SHORT).show();
			break;

		case R.id.scans_layout:
			Toast.makeText(UiUtils.getContext(), "app", Toast.LENGTH_SHORT).show();
			break;

		case R.id.feedback_layout:
			Toast.makeText(UiUtils.getContext(), "back", Toast.LENGTH_SHORT).show();
			break;

		case R.id.updates_layout:
			Toast.makeText(UiUtils.getContext(), "update", Toast.LENGTH_SHORT).show();
			break;

		case R.id.theme_layout:
			Toast.makeText(UiUtils.getContext(), "theme", Toast.LENGTH_SHORT).show();
			break;

		case R.id.exit_layout:
			UiUtils.getContext().sendBroadcast(new Intent("com.or.googlemarket.killall"));

			break;

		default:
			break;
		}
	}

}
