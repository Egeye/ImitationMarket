package com.or.googlemarket.holder;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.or.googlemarket.R;
import com.or.googlemarket.domain.AppInfo;
import com.or.googlemarket.http.HttpHelper;
import com.or.googlemarket.utils.UiUtils;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailInfoHolder extends BaseHolder<AppInfo> {
	@ViewInject(R.id.item_icon)
	private ImageView ivIcon;

	@ViewInject(R.id.item_title)
	private TextView tvTitle;

	@ViewInject(R.id.item_rating)
	private RatingBar rbStars;

	@ViewInject(R.id.item_download)
	private TextView tvDownload;

	@ViewInject(R.id.item_version)
	private TextView tvVersion;

	@ViewInject(R.id.item_size)
	private TextView tvSize;

	@ViewInject(R.id.item_date)
	private TextView tvDate;

	// ʵ�����ؼ��Ͳ���
	@Override
	public View initView() {
		View view = UiUtils.inflate(R.layout.detail_app_info);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void refreshView(AppInfo data) {
		tvTitle.setText(data.getName());
		bitmapUtils.display(ivIcon, HttpHelper.URL + "image?name=" + data.getIconUrl());
		rbStars.setRating(data.getStars());
		tvDate.setText("ʱ�䣺" + data.getDate());
		tvDownload.setText("���أ�" + data.getDownloadNum());
		tvVersion.setText("�汾��" + data.getVersion());
		tvSize.setText("��С��" + Formatter.formatFileSize(UiUtils.getContext(), data.getSize()));
	}

}
