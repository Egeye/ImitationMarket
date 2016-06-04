package com.or.googlemarket;

import com.or.googlemarket.domain.AppInfo;
import com.or.googlemarket.holder.DetailBottomHolder;
import com.or.googlemarket.holder.DetailDesHolder;
import com.or.googlemarket.holder.DetailInfoHolder;
import com.or.googlemarket.holder.DetailSafeHolder;
import com.or.googlemarket.holder.DetailScreenHolder;
import com.or.googlemarket.protocol.DetailProtocol;
import com.or.googlemarket.utils.UiUtils;
import com.or.googlemarket.view.LoadingPage;
import com.or.googlemarket.view.LoadingPage.LoadResult;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

public class DetailActivity extends BaseActivity {
	private String packageName;
	private AppInfo data;

	@Override
	protected void init() {
		super.init();
		// 获取到打开当前Activity的意图对象
		Intent i = getIntent();
		packageName = i.getStringExtra("packageName");
	}

	@Override
	protected void initView() {
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);

		LoadingPage loadingPage = new LoadingPage(this) {

			@Override
			public View createSuccessView() {
				return DetailActivity.this.createSuccessView();
			}

			@Override
			public LoadResult load() {
				return DetailActivity.this.load();
			}

		};
		// 必须调用show方法才会请求服务器加载新的界面
		loadingPage.show();
		setContentView(loadingPage);

	}

	/**
	 * 请求服务器
	 * 
	 * @return
	 */
	protected LoadResult load() {
		DetailProtocol protocol = new DetailProtocol(packageName);
		data = protocol.load(0);
		if (data == null) {
			return LoadResult.error;
		} else {
			return LoadResult.success;
		}
	}

	private FrameLayout bottom_layout, detail_info, detail_safe, detail_des;
	private HorizontalScrollView detail_screen;
	private DetailInfoHolder detailInfoHolder;
	private DetailScreenHolder screenHolder;
	private DetailSafeHolder safeHolder;
	private DetailDesHolder desHolder;
	private DetailBottomHolder bottomHolder;

	/**
	 * 加载成功的界面
	 * 
	 * @return
	 */
	protected View createSuccessView() {
		View view = UiUtils.inflate(R.layout.activity_detail);

		bottom_layout = (FrameLayout) view.findViewById(R.id.bottom_layout);
		detail_info = (FrameLayout) view.findViewById(R.id.detail_info);
		detail_safe = (FrameLayout) view.findViewById(R.id.detail_safe);
		detail_des = (FrameLayout) view.findViewById(R.id.detail_des);
		detail_screen = (HorizontalScrollView) view.findViewById(R.id.detail_screen);

		// 展示应用程序信息
		detailInfoHolder = new DetailInfoHolder();
		detailInfoHolder.setData(data);
		detail_info.addView(detailInfoHolder.getContentView());

		// 应用图片展示
		screenHolder = new DetailScreenHolder();
		screenHolder.setData(data);
		detail_screen.addView(screenHolder.getContentView());

		// 安全标记
		safeHolder = new DetailSafeHolder();
		safeHolder.setData(data);
		detail_safe.addView(safeHolder.getContentView());

		desHolder = new DetailDesHolder();
		desHolder.setData(data);
		detail_des.addView(desHolder.getContentView());
		
		//按钮
		bottomHolder = new DetailBottomHolder();
		bottomHolder.setData(data);
		bottom_layout.addView(bottomHolder.getContentView());
		
		return view;
	}

	/**
	 * 初始化ActionBar
	 */
	@Override
	protected void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}

}
