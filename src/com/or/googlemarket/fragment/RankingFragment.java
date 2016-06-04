package com.or.googlemarket.fragment;

import java.util.List;
import java.util.Random;

import com.or.googlemarket.protocol.RankingProtocol;
import com.or.googlemarket.utils.DrawableTools;
import com.or.googlemarket.utils.UiUtils;
import com.or.googlemarket.view.FlowLayout;
import com.or.googlemarket.view.LoadingPage.LoadResult;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class RankingFragment extends BaseFragment {

	private List<String> datas;

	@Override
	public View createSuccessView() {
		ScrollView view = new ScrollView(UiUtils.getContext());

		FlowLayout layout = new FlowLayout(UiUtils.getContext());
//		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundColor(0xffffffff);
		
		int padding = UiUtils.dip2px(13);
		layout.setPadding(padding, padding, padding, padding);

		// 按下显示的图片
		Drawable press = DrawableTools.createShape(0xff3693F8);
		for (int i = 0; i < datas.size(); i++) {
			final String str = datas.get(i);

			Random random = new Random();
			int red = random.nextInt(200) + 20;
			int green = random.nextInt(200) + 20;
			int blue = random.nextInt(200) + 20;

			int color = Color.rgb(red, green, blue);// 0-255

			TextView tv = new TextView(UiUtils.getContext());
			tv.setText(datas.get(i));

			// 默认显示的图片
			GradientDrawable createShape = DrawableTools.createShape(color);
			StateListDrawable selector = DrawableTools.createSelectDrawable(press, createShape);

			tv.setBackgroundDrawable(selector);
			tv.setTextColor(Color.WHITE);
			tv.setPadding(UiUtils.dip2px(7), UiUtils.dip2px(4), UiUtils.dip2px(7), UiUtils.dip2px(4));
			tv.setTextSize(UiUtils.dip2px(12));

			tv.setClickable(true);
			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(UiUtils.getContext(), str, 0).show();
				}
			});
			layout.addView(tv, new LinearLayout.LayoutParams(-2, -2));
		}
		view.addView(layout);

		return view;
	}

	@Override
	public LoadResult load() {
		RankingProtocol protocol = new RankingProtocol();
		datas = protocol.load(0);
		return checkData(datas);
	}

}
