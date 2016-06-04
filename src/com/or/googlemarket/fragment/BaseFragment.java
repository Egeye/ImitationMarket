package com.or.googlemarket.fragment;

import java.util.List;

import com.lidroid.xutils.BitmapUtils;
import com.or.googlemarket.domain.AppInfo;
import com.or.googlemarket.utils.BitmapHelper;
import com.or.googlemarket.utils.ViewUtils;
import com.or.googlemarket.view.LoadingPage;
import com.or.googlemarket.view.LoadingPage.LoadResult;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	private LoadingPage loadingPage;
	protected BitmapUtils bitmapUtils;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		bitmapUtils = BitmapHelper.getBitmapUtils();
		
		if (loadingPage == null) {
			loadingPage = new LoadingPage(getActivity()) {

				@Override
				public View createSuccessView() {
					return BaseFragment.this.createSuccessView();
				}

				@Override
				public LoadResult load() {
					return BaseFragment.this.load();
				}

			};

		} else {
			// �Ƴ�frameLayout֮ǰ�ĸ��ؼ�
			ViewUtils.removeParent(loadingPage);
		}

		/** ���ݲ�ͬ��״̬��ʾ��ͬ�Ľ��� */
		// showPage();

		/** ���ݷ������������л�״̬ */
		// show();

		return loadingPage;

	}

	public void show() {
		if (loadingPage != null) {
			loadingPage.show();
		}
	}

	/**
	 * �����ɹ��Ľ��棬���󷽷����ɸ����������ʵ��
	 * 
	 * @return
	 */
	public abstract View createSuccessView();

	/**
	 * ����������ķ���
	 * 
	 * @return
	 */
	public abstract LoadResult load();

	/**
	 * У����������ص�����
	 * 
	 * @param load
	 * @return
	 */
	public LoadResult checkData(List datas) {
		if (datas == null) {
			return LoadResult.error;// ���������ʧ��
		} else {
			if (datas.size() == 0) {
				return LoadResult.empty;
			} else {
				return LoadResult.success;
			}
		}
	}
}
