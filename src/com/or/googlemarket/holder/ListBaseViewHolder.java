package com.or.googlemarket.holder;

import com.or.googlemarket.R;
import com.or.googlemarket.domain.AppInfo;
import com.or.googlemarket.http.HttpHelper;
import com.or.googlemarket.utils.UiUtils;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ListBaseViewHolder extends BaseHolder<AppInfo> {

	ImageView ivIcon;
	TextView tvName;
	TextView tvDes;
	TextView tvSize;
	RatingBar rbStars;

	@Override
	public View initView() {
		View contentView = View.inflate(UiUtils.getContext(), R.layout.item_app, null);
		this.ivIcon = (ImageView) contentView.findViewById(R.id.item_icon);
		this.tvDes = (TextView) contentView.findViewById(R.id.item_bottom);
		this.tvName = (TextView) contentView.findViewById(R.id.item_title);
		this.rbStars = (RatingBar) contentView.findViewById(R.id.item_rating);
		this.tvSize = (TextView) contentView.findViewById(R.id.item_size);
		return contentView;
	}

	public void refreshView(AppInfo data) {
		this.tvName.setText(data.getName());
		this.tvSize.setText(Formatter.formatFileSize(UiUtils.getContext(), data.getSize()));
		this.tvDes.setText(data.getDescribe());
		this.rbStars.setRating(data.getStars());

		// http://127.0.0.1:8090/image?name=
		// 不完整app/com.youyuan.yyhl/icon.jpg
		String iconUrl = data.getIconUrl();

		// 显示图片的控件
		bitmapUtils.display(this.ivIcon, HttpHelper.URL + "image?name=" + iconUrl);
	}

}
