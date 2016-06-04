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
 * �����Զ���֡���֣���BaseFragment��һ���ִ����ȡ������
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

	// �����еĽ���
	private View loadingView;

	// �������
	private View errorView;

	// �յĽ���
	private View emptyView;

	// �ɹ��Ľ���
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

	/** ��FrameLayout�����4�в�ͬ�Ľ��� */
	private void init() {
		// �����еĽ���
		loadingView = createLodingView();
		if (loadingView != null) {
			this.addView(loadingView,
					new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		// ���ش���Ľ���
		errorView = createErrorView();
		if (errorView != null) {
			this.addView(errorView, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}

		// ���ؿյĽ���
		emptyView = createEmptyView();
		if (emptyView != null) {
			this.addView(emptyView, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		showPage();
	}

	/** ���ݲ�ͬ��״̬��ʾ��ͬ�Ľ��� */
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

	/** ���ݷ������������л�״̬ */
	public void show() {
		if (state == STATE_ERROR || state == STATE_EMPTY) {
			state = STATE_LOADING;
		}

		ThreadManager.getInstance().createLongPool().execute(new Runnable() {

			@Override
			public void run() {
				SystemClock.sleep(200);

				// �������������ȡ�������ϵ����ݣ������жϣ�����һ�����
				final LoadResult result = load();

				UiUtils.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (result != null) {
							state = result.getValue();

							// ״̬�ı��ˣ������жϵ�ǰ��Ҫ��ʾ�Ľ���
							showPage();
						}
					}
				});
			}
		});

		showPage();
	}

	// ���������еĽ���
	private View createLodingView() {
		View view = View.inflate(UiUtils.getContext(), R.layout.loadpage_loding, null);
		return view;
	}

	// �������ش���Ľ���
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

	// �������ؿյĽ���
	private View createEmptyView() {
		View view = View.inflate(UiUtils.getContext(), R.layout.loadpage_empty, null);
		return view;
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

}
