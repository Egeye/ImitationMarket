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
			// 移除frameLayout之前的父控件
			ViewUtils.removeParent(loadingPage);
		}

		/** 根据不同的状态显示不同的界面 */
		// showPage();

		/** 根据服务器的数据切换状态 */
		// show();

		return loadingPage;

	}

	public void show() {
		if (loadingPage != null) {
			loadingPage.show();
		}
	}

	/**
	 * 创建成功的界面，抽象方法，由各自子类具体实现
	 * 
	 * @return
	 */
	public abstract View createSuccessView();

	/**
	 * 请求服务器的方法
	 * 
	 * @return
	 */
	public abstract LoadResult load();

	/**
	 * 校验服务器返回的数据
	 * 
	 * @param load
	 * @return
	 */
	public LoadResult checkData(List datas) {
		if (datas == null) {
			return LoadResult.error;// 请求服务器失败
		} else {
			if (datas.size() == 0) {
				return LoadResult.empty;
			} else {
				return LoadResult.success;
			}
		}
	}
}
