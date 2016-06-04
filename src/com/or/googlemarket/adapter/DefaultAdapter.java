package com.or.googlemarket.adapter;

import java.util.List;

import com.or.googlemarket.holder.BaseHolder;
import com.or.googlemarket.holder.MoreHolder;
import com.or.googlemarket.manager.ThreadManager;
import com.or.googlemarket.utils.UiUtils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class DefaultAdapter<Data> extends BaseAdapter implements OnItemClickListener {

	protected List<Data> datas;
	private static final int DEFAULT_ITEM = 0;
	private static final int MORE_ITEM = 1;
	private ListView lv;

	public DefaultAdapter(List<Data> datas, ListView lv) {
		this.datas = datas;

		// ListView 点击事件
		lv.setOnItemClickListener(this);
		this.lv = lv;
	}

	/**
	 * ListView条目点击事件回调方法
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// Toast.makeText(UiUtils.getContext(), "position " + position,
		// Toast.LENGTH_SHORT).show();
		// 获取到顶部条目的数量 - 首页Listview修正
		position -= lv.getHeaderViewsCount();
		onInnerItemClick(position);
	}

	/** 处理条目点击事件 */
	public void onInnerItemClick(int position) {

	}

	// 返回数据
	public List<Data> getDatas() {
		return datas;
	}

	// 设置数据
	public void setDatas(List<Data> datas) {
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size() + 1;
		// +1：加载更多
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/** 根据位置去判断当前条目是什么类型 */
	@Override
	public int getItemViewType(int position) {
		if (position == datas.size()) {
			// 最后一个条目
			return MORE_ITEM;
		}
		return getInnerItemViewType(position);
	}

	// 如果不是最后一个条目,返回默认类型
	protected int getInnerItemViewType(int position) {
		return DEFAULT_ITEM;
	}

	/** 当前ListView有几种不同的条目类型，默认1 */
	@Override
	public int getViewTypeCount() {
		// 2中条目类型，2块内存来维护
		return super.getViewTypeCount() + 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseHolder holder = null;

		/*
		 * // 如果位置是最后一位，就显示MoreHolder if (position == datas.size()) { holder =
		 * getMoreHolder(); return holder.getContentView(); }
		 */
		/*
		 * if (convertView == null) { if (getItemViewType(position) ==
		 * MORE_ITEM) { holder = getMoreHolder(); } else { holder = getHolder();
		 * } } else { if (getItemViewType(position) == DEFAULT_ITEM) { holder =
		 * (BaseHolder) convertView.getTag(); } else { holder = getMoreHolder();
		 * } }
		 * 
		 * if (getItemViewType(position) == DEFAULT_ITEM) { Data info =
		 * datas.get(position); holder.setData(info); }
		 */
		switch (getItemViewType(position)) {
		case MORE_ITEM:
			if (convertView == null) {
				holder = getMoreHolder();
			} else {
				holder = (BaseHolder) convertView.getTag();
			}
			break;
//		case DEFAULT_ITEM:
			default:
			if (convertView == null) {
				holder = getHolder();
			} else {
				holder = (BaseHolder) convertView.getTag();
			}
			holder.setData(datas.get(position));
			break;
		}

		return holder.getContentView();
		// 如果当前holder恰好是MoreHolder，证明MoreHolder已经显示，并执行加载更多数据
	}

	private MoreHolder moreHolder;

	private BaseHolder getMoreHolder() {
		if (moreHolder != null) {
			return moreHolder;
		} else {
			moreHolder = new MoreHolder(this,hasMore());
			return moreHolder;
		}
	}

	/**
	 * 是否有额外数据
	 * @return
	 */
	protected boolean hasMore() {
		return true;
	}

	protected abstract BaseHolder<Data> getHolder();

	/**
	 * 加载更多条目显示的时候调用该方法
	 */
	public void loadMore() {
		ThreadManager.getInstance().createLongPool().execute(new Runnable() {

			@Override
			public void run() {
				// 在子线程中加载更多
				
				// 数据不一样，所以需要子类具体实现
				final List<Data> newData = onLoad();

				UiUtils.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (newData == null) {
							// 连接服务器失败
							moreHolder.setData(MoreHolder.LOAD_ERROR);

						} else if (newData.size() == 0) {
							// 服务器已经没有额外数据了
							moreHolder.setData(MoreHolder.HAS_NO_MORE);

						} else {
							// 成功了
							moreHolder.setData(MoreHolder.HAS_MORE);
							datas.addAll(newData);
							// 给ListView之前的集合添加一个新的集合

							// 返回主线程刷新界面
							notifyDataSetChanged();
						}
					}
				});
			}
		});
	}

	/** 加载更多数据的具体逻辑 */
	protected abstract List<Data> onLoad();

}
