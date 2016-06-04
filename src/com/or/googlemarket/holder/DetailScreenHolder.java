package com.or.googlemarket.holder;

import java.util.List;

import com.or.googlemarket.R;
import com.or.googlemarket.domain.AppInfo;
import com.or.googlemarket.http.HttpHelper;
import com.or.googlemarket.utils.UiUtils;

import android.view.View;
import android.widget.ImageView;

public class DetailScreenHolder extends BaseHolder<AppInfo> {
	private ImageView[] ivs;

	@Override
	public View initView() {
		View view = UiUtils.inflate(R.layout.detail_screen);
		ivs = new ImageView[5];
		ivs[0] = (ImageView) view.findViewById(R.id.screen_1);
		ivs[1] = (ImageView) view.findViewById(R.id.screen_2);
		ivs[2] = (ImageView) view.findViewById(R.id.screen_3);
		ivs[3] = (ImageView) view.findViewById(R.id.screen_4);
		ivs[4] = (ImageView) view.findViewById(R.id.screen_5);
		return view;
	}

	@Override
	public void refreshView(AppInfo data) {
		List<String> screen = data.getScreen(); // 集合的大小有可能小于5
		for (int i = 0; i < 5; i++) {
			if (i < screen.size()) {
				ivs[i].setVisibility(View.VISIBLE);
				bitmapUtils.display(ivs[i], HttpHelper.URL + "image?name=" + screen.get(i));
			} else {
				ivs[i].setVisibility(View.GONE);
			}

		}
	}
}
