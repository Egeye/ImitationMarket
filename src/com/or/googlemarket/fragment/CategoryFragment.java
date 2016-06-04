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
	 * ����������
	 * 
	 * @author Octavio
	 *
	 */
	private class CategoryAdapter extends DefaultAdapter<CategoryInfo> {
		private int position;// ��¼��ǰ��Ŀλ��

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

		// ��ǰ�������Ϊfalse,onLoad�Ͳ��ᱻ����
		@Override
		protected boolean hasMore() {
			return false;
		}

		@Override
		protected List<CategoryInfo> onLoad() {
			return null;
		}

		/** �ֶ������һ������    ���Ϲ���3��convertview */
		@Override
		public int getViewTypeCount() {
			//������������Ŀ���ͣ�1���⣬2���ݣ�3���ظ���(û����ʾ)
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
