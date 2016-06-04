package com.or.googlemarket.holder;

import com.or.googlemarket.R;
import com.or.googlemarket.adapter.DefaultAdapter;
import com.or.googlemarket.utils.UiUtils;

import android.view.View;
import android.widget.RelativeLayout;

public class MoreHolder extends BaseHolder<Integer> {
	// û�ж���������
	public static final int HAS_NO_MORE = 0;
	// ����ʧ��
	public static final int LOAD_ERROR = 1;
	// �ж�����
	public static final int HAS_MORE = 2;
	private boolean hasMore;

	private RelativeLayout rlMoreLoading;
	private RelativeLayout rlMoreError;

	/**
	 * ���ظ��࣬���������ж������޸�
	 */
	@Override
	public void refreshView(Integer data) {
		rlMoreError.setVisibility(data == LOAD_ERROR ? View.VISIBLE : View.GONE);
		rlMoreLoading.setVisibility(data == HAS_MORE ? View.VISIBLE : View.GONE);
	}

	/**
	 * ��Holder��ʾ��ʱ����ʲô���ӵ�
	 */
	@Override
	public View initView() {
		View view = UiUtils.inflate(R.layout.item_load_more);
		rlMoreLoading = (RelativeLayout) view.findViewById(R.id.rl_more_loading);
		rlMoreError = (RelativeLayout) view.findViewById(R.id.rl_more_error);
		return view;
	}

	@Override
	public View getContentView() {
		if (hasMore) {
			loadMore();
		}
		return super.getContentView();
	}

	// ���ظ�������
	private void loadMore() {
		// 1.�����������������һ������
		// 2.���µ�������ӵ������У���������ʾ
		// ���Խ����߼�����Adapter����Adapter���ظ�������
		adapter.loadMore();
	}

	private DefaultAdapter adapter;

	public MoreHolder(DefaultAdapter adapter, boolean hasMore) {
		super();
		this.adapter = adapter;
		this.hasMore = hasMore;
		if(!hasMore){
			setData(0);
		}
	}

}
