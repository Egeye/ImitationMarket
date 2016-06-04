package com.or.googlemarket.view;

import com.or.googlemarket.R;
import com.or.googlemarket.utils.UiUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class BaseListView extends ListView {

	public BaseListView(Context context) {
		super(context);
		init();
	}

	public BaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public BaseListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		// setSelector  点击显示的颜色
		// setCacheColorHint  拖拽的颜色
		// setDivider  每个条目的间隔	的分割线	
		this.setSelector(R.drawable.nothing);  // 什么都没有
		this.setCacheColorHint(R.drawable.nothing);
		this.setDivider(UiUtils.getDrawalbe(R.drawable.nothing));
		this.setFastScrollEnabled(true);
	}

	
}
