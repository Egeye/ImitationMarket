package com.or.googlemarket.fragment;

import java.util.List;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.or.googlemarket.DetailActivity;
import com.or.googlemarket.R;
import com.or.googlemarket.adapter.ListBaseAdapter;
import com.or.googlemarket.domain.AppInfo;
import com.or.googlemarket.holder.HomePictureHolder;
import com.or.googlemarket.protocol.HomeProtocol;
import com.or.googlemarket.utils.UiUtils;
import com.or.googlemarket.view.BaseListView;
import com.or.googlemarket.view.LoadingPage.LoadResult;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeFragment extends BaseFragment {

	private List<AppInfo> datas;
	private List<String> pictures;

	/** ��Fragment���ص�Activity������ʱ����� */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		show();
	}

	// �����ɹ��Ľ���
	public View createSuccessView() {
		BaseListView listView = new BaseListView(UiUtils.getContext());

		HomePictureHolder holder = new HomePictureHolder();
		holder.setData(pictures);
		// �õ�holder��������View����
		View contentView = holder.getContentView();
		// contentView.setLayoutParams(new
		// AbsListView.LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.WRAP_CONTENT));
		listView.addHeaderView(contentView);
		

		bitmapUtils.configDefaultLoadingImage(R.drawable.logo_app_loading);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.logo_app_loading);

		listView.setAdapter(new ListBaseAdapter(datas, listView) {

			@Override
			protected List<AppInfo> onLoad() {
				HomeProtocol protool = new HomeProtocol();
				List<AppInfo> load = protool.load(datas.size());
				datas.addAll(load);
				return load;
			}

		

		});

		// �ڶ���������������ʱ�Ƿ����ͼƬ��false������أ�
		// �������������ٻ���ʱ�Ƿ����ͼƬ��true��������
		listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));

		return listView;
	}

	// ����������ķ�ʽ
	public LoadResult load() {
		HomeProtocol protocol = new HomeProtocol();
		datas = protocol.load(0);

		pictures = protocol.getPictures();

		return checkData(datas);
	}

}
