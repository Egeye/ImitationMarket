package com.or.googlemarket.holder;

import com.or.googlemarket.R;
import com.or.googlemarket.adapter.DefaultAdapter;
import com.or.googlemarket.utils.UiUtils;

import android.view.View;
import android.widget.RelativeLayout;

public class MoreHolder extends BaseHolder<Integer> {
	// 没有额外数据了
	public static final int HAS_NO_MORE = 0;
	// 加载失败
	public static final int LOAD_ERROR = 1;
	// 有额外书
	public static final int HAS_MORE = 2;
	private boolean hasMore;

	private RelativeLayout rlMoreLoading;
	private RelativeLayout rlMoreError;

	/**
	 * 加载更多，根据数据判断做出修改
	 */
	@Override
	public void refreshView(Integer data) {
		rlMoreError.setVisibility(data == LOAD_ERROR ? View.VISIBLE : View.GONE);
		rlMoreLoading.setVisibility(data == HAS_MORE ? View.VISIBLE : View.GONE);
	}

	/**
	 * 当Holder显示的时候是什么样子的
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

	// 加载更多数据
	private void loadMore() {
		// 1.请求服务器，加载下一批数据
		// 2.将新的数据添加到集合中，并更新显示
		// 所以将此逻辑交给Adapter，让Adapter加载更多数据
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
