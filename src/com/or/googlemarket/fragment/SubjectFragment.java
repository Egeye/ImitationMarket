package com.or.googlemarket.fragment;

import java.util.List;
import com.or.googlemarket.R;
import com.or.googlemarket.adapter.DefaultAdapter;
import com.or.googlemarket.domain.SubjectInfo;
import com.or.googlemarket.holder.BaseHolder;
import com.or.googlemarket.http.HttpHelper;
import com.or.googlemarket.protocol.SubjectProtocol;
import com.or.googlemarket.utils.UiUtils;
import com.or.googlemarket.view.LoadingPage.LoadResult;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SubjectFragment extends BaseFragment {

	private List<SubjectInfo> datas;

	@Override
	public View createSuccessView() {
		// BaseListView blistView = new BaseListView(getActivity());
		ListView listView = new ListView(UiUtils.getContext());
		listView.setAdapter(new SubjectAdapter(datas, listView));
		listView.setFastScrollEnabled(true);
		listView.setDividerHeight(0);
		return listView;
	}

	@Override
	public LoadResult load() {
		SubjectProtocol protocol = new SubjectProtocol();
		datas = protocol.load(0);
		return checkData(datas);
	}

	/** 专题界面的数据填充适配器 */
	private class SubjectAdapter extends DefaultAdapter<SubjectInfo> {

		public SubjectAdapter(List<SubjectInfo> datas, ListView lv) {
			super(datas, lv);
		}

		@Override
		protected BaseHolder<SubjectInfo> getHolder() {
			return new SubjectViewHolder();
		}

		@Override
		protected List<SubjectInfo> onLoad() {
			SubjectProtocol protocol = new SubjectProtocol();
			List<SubjectInfo> load = protocol.load(datas.size());
			datas.addAll(load);
			return load;
		}

		@Override
		public void onInnerItemClick(int position) {
			super.onInnerItemClick(position);
			Toast.makeText(UiUtils.getContext(), datas.get(position).getDes(), Toast.LENGTH_SHORT).show();
		}

	}

	static class SubjectViewHolder extends BaseHolder<SubjectInfo> {
		ImageView ivIcon;
		TextView tvText;

		@Override
		public View initView() {
			View contentView = UiUtils.inflate(R.layout.item_subject);
			this.ivIcon = (ImageView) contentView.findViewById(R.id.item_icon);
			this.tvText = (TextView) contentView.findViewById(R.id.item_txt);
			return contentView;
		}

		public void refreshView(SubjectInfo data) {
			this.tvText.setText(data.getDes());
			bitmapUtils.display(this.ivIcon, HttpHelper.URL + "image?name=" + data.getUrl());

		}
	}
}
