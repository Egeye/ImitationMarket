package com.or.googlemarket.fragment;

import java.util.List;

import com.or.googlemarket.adapter.DefaultAdapter;
import com.or.googlemarket.domain.CategoryInfo;
import com.or.googlemarket.holder.BaseHolder;
import com.or.googlemarket.holder.CategoryContentHolder;
import com.or.googlemarket.holder.CategoryTitleHolder;
import com.or.googlemarket.protocol.CategroyProtocol;
import com.or.googlemarket.utils.UiUtils;
import com.or.googlemarket.view.BaseListView;
import com.or.googlemarket.view.LoadingPage.LoadResult;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CategoryFragment extends BaseFragment {

	private List<CategoryInfo> datas;
	private static int ITEM_TITLE = 2;

	@Override
	public View createSuccessView() {
		BaseListView listView = new BaseListView(UiUtils.getContext());
		listView.setAdapter(new CategoryAdapter(datas, listView));

		return listView;
	}

	@Override
	public LoadResult load() {
		CategroyProtocol protocol = new CategroyProtocol();
		datas = protocol.load(0);
		return checkData(datas);
	}

	/**
	 * 数据适配器
	 * 
	 * @author Octavio
	 *
	 */
	private class CategoryAdapter extends DefaultAdapter<CategoryInfo> {
		private int position;// 记录当前条目位置

		public CategoryAdapter(List<CategoryInfo> datas, ListView lv) {
			super(datas, lv);
		}

		@Override
		protected BaseHolder<CategoryInfo> getHolder() {
			if(!datas.get(position).isTitle()){
				return new CategoryContentHolder();
			}else{
				return new CategoryTitleHolder();
			}
		}

		// 当前方法如果为false,onLoad就不会被调用
		@Override
		protected boolean hasMore() {
			return false;
		}

		@Override
		protected List<CategoryInfo> onLoad() {
			return null;
		}

		/** 又额外多了一种类型    集合管理3个convertview */
		@Override
		public int getViewTypeCount() {
			//现在有三种条目类型，1标题，2内容，3加载更多(没有显示)
			return super.getViewTypeCount() + 1;
		}

		@Override
		protected int getInnerItemViewType(int position) {
			if (datas.get(position).isTitle()) {
				return ITEM_TITLE;
			} else {

				return super.getInnerItemViewType(position);
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			this.position = position;
			return super.getView(position, convertView, parent);
		}

	}

}
