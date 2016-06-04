package com.or.googlemarket.holder;

import java.util.LinkedList;
import java.util.List;

import com.or.googlemarket.DetailActivity;
import com.or.googlemarket.http.HttpHelper;
import com.or.googlemarket.utils.UiUtils;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

public class HomePictureHolder extends BaseHolder<List<String>> {
	private ViewPager viewPager;
	private List<String> datas;

	// 当new HomePictureHolder()就会调用该方法
	@Override
	public View initView() {
		viewPager = new ViewPager(UiUtils.getContext());
		viewPager.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, UiUtils.dip2px(120)));
		return viewPager;
	}

	// 当holder.setData时调用
	@Override
	public void refreshView(List<String> datas) {
		this.datas = datas;
		viewPager.setAdapter(new HomeAdapter());
		viewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					runTask.stop();
					break;
				case MotionEvent.ACTION_CANCEL:// 事件的取消
				case MotionEvent.ACTION_UP:
					runTask.start();
					break;
				}
				return false;// ViewPager 这里不要TRUE，否则无法左右滑动
			}
		});
		runTask = new AutoRunTask();
		runTask.start();
	}

	/**
	 * 图片数据适配器
	 * 
	 * @author Octavio
	 *
	 */
	class HomeAdapter extends PagerAdapter {
		LinkedList<ImageView> convertView = new LinkedList<ImageView>();

		// 当前ViewPager里面有多少个条目
		@Override
		public int getCount() {
			return datas.size();
			// return Integer.MAX_VALUE;
		}

		// 判断返回的对象和加载的界面View对象的关系
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			ImageView view = (ImageView) object;

			// 把移除的对象添加到缓存集合中
			convertView.add(view);
			// super.destroyItem(container, position, object);
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			// int index = position % datas.size();
			ImageView iv;

			if (convertView.size() > 0) {
				iv = convertView.remove(0);
			} else {
				iv = new ImageView(UiUtils.getContext());
			}

//			iv.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					Toast.makeText(UiUtils.getContext(), datas.get(position), Toast.LENGTH_SHORT).show();
//					Intent i = new Intent(UiUtils.getContext(),DetailActivity.class);
////					i.putExtra("picture", datas.get(position));
//					UiUtils.getContext().startActivity(i);
//				}
//			});

			// bitmapUtils.display(iv, HttpHelper.URL + "image?name=" +
			// datas.get(index));
			bitmapUtils.display(iv, HttpHelper.URL + "image?name=" + datas.get(position));
			container.addView(iv);// 加载的View对象
			return iv;// 返回的View对象
		}
	}

	boolean flag;
	private AutoRunTask runTask;

	public class AutoRunTask implements Runnable {

		@Override
		public void run() {
			if (flag) {
				UiUtils.cancelTask(this);
				int currentItem = viewPager.getCurrentItem();

				if (currentItem == datas.size() - 1) {
					currentItem -= datas.size();
					currentItem++;
				} else {
					currentItem++;
				}
				viewPager.setCurrentItem(currentItem);
				// Handler handler = new Handler();
				// handler.postDelayed(r, delayMillis)
				// 延迟执行当前的任务 - 递归调用
				UiUtils.postDelayed(this, 2000);
			}
		}

		public void start() {
			if (!flag) {
				UiUtils.cancelTask(this);
				flag = true;
				UiUtils.postDelayed(this, 2000);
			}
		}

		public void stop() {
			if (flag) {
				flag = false;
				UiUtils.cancelTask(this);
			}
		}
	}

}
