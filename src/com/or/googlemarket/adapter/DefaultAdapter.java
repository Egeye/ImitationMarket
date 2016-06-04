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

		// ListView ����¼�
		lv.setOnItemClickListener(this);
		this.lv = lv;
	}

	/**
	 * ListView��Ŀ����¼��ص�����
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// Toast.makeText(UiUtils.getContext(), "position " + position,
		// Toast.LENGTH_SHORT).show();
		// ��ȡ��������Ŀ������ - ��ҳListview����
		position -= lv.getHeaderViewsCount();
		onInnerItemClick(position);
	}

	/** ������Ŀ����¼� */
	public void onInnerItemClick(int position) {

	}

	// ��������
	public List<Data> getDatas() {
		return datas;
	}

	// ��������
	public void setDatas(List<Data> datas) {
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size() + 1;
		// +1�����ظ���
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/** ����λ��ȥ�жϵ�ǰ��Ŀ��ʲô���� */
	@Override
	public int getItemViewType(int position) {
		if (position == datas.size()) {
			// ���һ����Ŀ
			return MORE_ITEM;
		}
		return getInnerItemViewType(position);
	}

	// ����������һ����Ŀ,����Ĭ������
	protected int getInnerItemViewType(int position) {
		return DEFAULT_ITEM;
	}

	/** ��ǰListView�м��ֲ�ͬ����Ŀ���ͣ�Ĭ��1 */
	@Override
	public int getViewTypeCount() {
		// 2����Ŀ���ͣ�2���ڴ���ά��
		return super.getViewTypeCount() + 1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseHolder holder = null;

		/*
		 * // ���λ�������һλ������ʾMoreHolder if (position == datas.size()) { holder =
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
		// �����ǰholderǡ����MoreHolder��֤��MoreHolder�Ѿ���ʾ����ִ�м��ظ�������
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
	 * �Ƿ��ж�������
	 * @return
	 */
	protected boolean hasMore() {
		return true;
	}

	protected abstract BaseHolder<Data> getHolder();

	/**
	 * ���ظ�����Ŀ��ʾ��ʱ����ø÷���
	 */
	public void loadMore() {
		ThreadManager.getInstance().createLongPool().execute(new Runnable() {

			@Override
			public void run() {
				// �����߳��м��ظ���
				
				// ���ݲ�һ����������Ҫ�������ʵ��
				final List<Data> newData = onLoad();

				UiUtils.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (newData == null) {
							// ���ӷ�����ʧ��
							moreHolder.setData(MoreHolder.LOAD_ERROR);

						} else if (newData.size() == 0) {
							// �������Ѿ�û�ж���������
							moreHolder.setData(MoreHolder.HAS_NO_MORE);

						} else {
							// �ɹ���
							moreHolder.setData(MoreHolder.HAS_MORE);
							datas.addAll(newData);
							// ��ListView֮ǰ�ļ������һ���µļ���

							// �������߳�ˢ�½���
							notifyDataSetChanged();
						}
					}
				});
			}
		});
	}

	/** ���ظ������ݵľ����߼� */
	protected abstract List<Data> onLoad();

}
