package com.or.googlemarket.view;

import com.or.googlemarket.R;
import com.or.googlemarket.manager.ThreadManager;
import com.or.googlemarket.utils.UiUtils;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * 创建自定义帧布局，把BaseFragment的一部分代码抽取到这里
 * 
 * @author Octavio
 *
 */
public abstract class LoadingPage extends FrameLayout {
	public static final int STATE_UNKNOWN = 0;
	public static final int STATE_LOADING = 1;
	public static final int STATE_ERROR = 2;
	public static final int STATE_EMPTY = 3;
	public static final int STATE_SUCCESS = 4;
	public int state = STATE_UNKNOWN;

	// 加载中的界面
	private View loadingView;

	// 错误界面
	private View errorView;

	// 空的界面
	private View emptyView;

	// 成功的界面
	private View successView;

	public enum LoadResult {
		error(2), empty(3), success(4);

		int value;

		LoadResult(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	public LoadingPage(Context context) {
		super(context);
		init();
	}

	public LoadingPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	/** 在FrameLayout中添加4中不同的界面 */
	private void init() {
		// 加载中的界面
		loadingView = createLodingView();
		if (loadingView != null) {
			this.addView(loadingView,
					new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		// 加载错误的界面
		errorView = createErrorView();
		if (errorView != null) {
			this.addView(errorView, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		// 加载空的界面
		emptyView = createEmptyView();
		if (emptyView != null) {
			this.addView(emptyView, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		showPage();
	}

	/** 根据不同的状态显示不同的界面 */
	private void showPage() {
		if (loadingView != null) {
			loadingView.setVisibility(state == STATE_UNKNOWN || state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
		}
		if (errorView != null) {
			errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
		}
		if (emptyView != null) {
			emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
		}

		if (state == STATE_SUCCESS) {
			if (successView == null) {
				successView = createSuccessView();
				this.addView(successView,
						new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			}
			successView.setVisibility(View.VISIBLE);
		} else {
			if (successView != null) {
				successView.setVisibility(View.INVISIBLE);
			}
		}
	}

	/** 根据服务器的数据切换状态 */
	public void show() {
		if (state == STATE_ERROR || state == STATE_EMPTY) {
			state = STATE_LOADING;
		}

		ThreadManager.getInstance().createLongPool().execute(new Runnable() {

			@Override
			public void run() {
				SystemClock.sleep(200);

				// 请求服务器，获取服务器上的数据，进行判断，返回一个结果
				final LoadResult result = load();

				UiUtils.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (result != null) {
							state = result.getValue();

							// 状态改变了，重新判断当前所要显示的界面
							showPage();
						}
					}
				});
			}
		});

		showPage();
	}

	// 创建加载中的界面
	private View createLodingView() {
		View view = View.inflate(UiUtils.getContext(), R.layout.loadpage_loding, null);
		return view;
	}

	// 创建加载错误的界面
	private View createErrorView() {
		View view = View.inflate(UiUtils.getContext(), R.layout.loadpage_error, null);
		Button btnRetry = (Button) view.findViewById(R.id.btn_page_error);
		btnRetry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				show();
			}
		});
		return view;
	}

	// 创建加载空的界面
	private View createEmptyView() {
		View view = View.inflate(UiUtils.getContext(), R.layout.loadpage_empty, null);
		return view;
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

}
