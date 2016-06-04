package com.or.googlemarket.holder;

import com.or.googlemarket.R;
import com.or.googlemarket.domain.CategoryInfo;
import com.or.googlemarket.utils.UiUtils;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class CategoryTitleHolder extends BaseHolder<CategoryInfo> {
	private TextView tv;

	@Override
	public View initView() {
		tv = new TextView(UiUtils.getContext());
		tv.setBackgroundDrawable(UiUtils.getDrawalbe(R.drawable.grid_item_bg));
		tv.setGravity(Gravity.CENTER);
		tv.setTextColor(0xff7a7a7a);
		tv.setPadding(0, 4, 0, 4);
		tv.setTextSize(UiUtils.dip2px(12));
		return tv;
	}

	@Override
	public void refreshView(CategoryInfo data) {
		tv.setText(data.getTitle());
	}
}
