package com.or.googlemarket.adapter;

import java.util.List;

import com.or.googlemarket.DetailActivity;
import com.or.googlemarket.domain.AppInfo;
import com.or.googlemarket.holder.BaseHolder;
import com.or.googlemarket.holder.ListBaseViewHolder;
import com.or.googlemarket.utils.UiUtils;

import android.content.Intent;
import android.widget.ListView;

public abstract class ListBaseAdapter extends DefaultAdapter<AppInfo> {

	public ListBaseAdapter(List<AppInfo> datas,ListView listView) {
		super(datas, listView);
	}

	@Override
	protected BaseHolder<AppInfo> getHolder() {
		return new ListBaseViewHolder();
	}

	// 再交给子类去实现F
	@Override
	protected abstract List<AppInfo> onLoad();
	
	@Override
	public void onInnerItemClick(int position) {
		super.onInnerItemClick(position);

		AppInfo appInfo = datas.get(position);
		// Toast.makeText(UiUtils.getContext(), "position " + position,
		// Toast.LENGTH_SHORT).show();
		Intent i = new Intent(UiUtils.getContext(), DetailActivity.class);
		i.putExtra("packageName", appInfo.getPackageName());
		UiUtils.startActivity(i);
	}
}
