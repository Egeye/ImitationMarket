package com.or.googlemarket.holder;

import com.lidroid.xutils.BitmapUtils;
import com.or.googlemarket.utils.BitmapHelper;

import android.view.View;

public abstract class BaseHolder<Data> {
	protected BitmapUtils bitmapUtils;
	private View contentView;
	private Data data;

	public View getContentView() {
		return contentView;
	}

	public void setData(Data data) {
		this.data = data;
		refreshView(data);
	}

	public abstract void refreshView(Data data);

	public BaseHolder() {
		bitmapUtils = BitmapHelper.getBitmapUtils();
		contentView = initView();
		contentView.setTag(this);
	}

	public abstract View initView();
}
