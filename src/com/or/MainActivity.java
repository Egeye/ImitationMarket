package com.or.googlemarket;

import com.or.googlemarket.fragment.BaseFragment;
import com.or.googlemarket.fragment.FragmentFactory;
import com.or.googlemarket.holder.MenuHolder;
import com.or.googlemarket.utils.UiUtils;

import android.annotation.SuppressLint;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements OnQueryTextListener {

	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	private ViewPager myViewPager;
	private PagerTabStrip pagerTabStrip;
	private String[] tabNames;// 标签名
	private FrameLayout fl;// 菜单根布局

	@Override
	protected void init() {
		tabNames = UiUtils.getStringArray(R.array.tab_names);
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_main);
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);

		drawerLayout = (DrawerLayout) findViewById(R.id.dl);
		myViewPager = (ViewPager) findViewById(R.id.vp);
		pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
		fl = (FrameLayout) findViewById(R.id.fl);

		// pagerTabStrip.setTabIndicatorColor(Color.WHITE);
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.main_color));
		myViewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
		myViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				BaseFragment cFragment = FragmentFactory.createFragment(position);

				// 当切换界面的时候，重新请求服务器
				cFragment.show();
			}

		});
		
		MenuHolder holder = new MenuHolder();
		
		//之前已经登陆过了
//		holder.setData(data);
		fl.addView(holder.getContentView());
		
	}

	@Override
	protected void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		actionBarDrawerToggle = new ActionBarDrawerToggle(this, // 参数1当前ActionBar所在的ACtivity
				drawerLayout, // 参数2.所控制的抽屉,
				R.drawable.ic_drawer_am, // 参数3，抽屉按钮的图片,
				R.string.open_draw, // 参数4，抽屉打开的描述
				R.string.close_draw// 参数5，抽屉关闭的描述
		) {

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				// Toast.makeText(getApplicationContext(), "close",
				// Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				// Toast.makeText(getApplicationContext(), "open",
				// Toast.LENGTH_SHORT).show();
			}
		};

		drawerLayout.setDrawerListener(actionBarDrawerToggle);
		actionBarDrawerToggle.syncState();
	}

	/**
	 * 处理ActionBar菜单选项点击事件
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_search) {

			Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
			return true;
		}
		return actionBarDrawerToggle.onOptionsItemSelected(item) | super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		Toast.makeText(this, "onQueryTextSubmit", Toast.LENGTH_SHORT).show();
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		Toast.makeText(this, newText, Toast.LENGTH_SHORT).show();
		return true;
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);

		if (android.os.Build.VERSION.SDK_INT > 11) {
			SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
			searchView.setOnQueryTextListener(this);
		}
		return true;
	}

	private class MainAdapter extends FragmentStatePagerAdapter {

		public MainAdapter(FragmentManager fm) {
			super(fm);
		}

		// 返回每个条目的标题
		@Override
		public CharSequence getPageTitle(int position) {
			return tabNames[position];
		}

		// 每个条目返回的Fragment
		@Override
		public Fragment getItem(int position) {
			return FragmentFactory.createFragment(position);
		}

		@Override
		public int getCount() {
			return tabNames.length;
		}

	}

}
