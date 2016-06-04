package com.or.googlemarket.fragment;

import java.util.List;

import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.or.googlemarket.R;
import com.or.googlemarket.adapter.ListBaseAdapter;
import com.or.googlemarket.domain.AppInfo;
import com.or.googlemarket.protocol.AppProtocol;
import com.or.googlemarket.protocol.GameProtocol;
import com.or.googlemarket.utils.BitmapHelper;
import com.or.googlemarket.utils.UiUtils;
import com.or.googlemarket.view.LoadingPage.LoadResult;

import android.view.View;
import android.widget.ListView;

public class GameFragment extends BaseFragment {
	private List<AppInfo> datas;

	@Override
	public View createSuccessView() {
		ListView listView = new ListView(UiUtils.getContext());
		listView.setDividerHeight(0);
		listView.setFastScrollEnabled(true);
		listView.setAdapter(new ListBaseAdapter(datas, listView){
			
			@Override
			protected List<AppInfo> onLoad() {
				GameProtocol protocol = new GameProtocol();
				List<AppInfo> load = protocol.load(datas.size());
				datas.addAll(load);
				return load;
			}
			
		});

		bitmapUtils = BitmapHelper.getBitmapUtils();

		bitmapUtils.configDefaultLoadingImage(R.drawable.logo_app_loading);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.logo_app_loading);

		// �ڶ���������������ʱ�Ƿ����ͼƬ��false������أ�
		// �������������ٻ���ʱ�Ƿ����ͼƬ��true��������
		listView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false, true));

		return listView;
	}

	@Override
	public LoadResult load() {
		GameProtocol protocol = new GameProtocol();
		datas = protocol.load(0);
		return checkData(datas);
	}

}
