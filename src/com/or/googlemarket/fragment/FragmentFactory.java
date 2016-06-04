package com.or.googlemarket.fragment;

import java.util.HashMap;
import java.util.Map;

import android.support.v4.app.Fragment;

public class FragmentFactory {

	private static Map<Integer, BaseFragment> mFragments = new HashMap<Integer, BaseFragment>();

	public static BaseFragment createFragment(int position) {
		BaseFragment fragment = null;

		// 复用集合中缓存的Fragment
		fragment = mFragments.get(position);

		// 如果集合中没有缓存,需要创建
		if (fragment == null) {
			if (position == 0) {
				fragment = new HomeFragment();
			} else if (position == 1) {
				fragment = new AppFragment();
			} else if (position == 2) {
				fragment = new GameFragment();
			} else if (position == 3) {
				fragment = new SubjectFragment();
			} else if (position == 4) {
				fragment = new CategoryFragment();
			} else if (position == 5) {
				fragment = new RankingFragment();
			}

			if (fragment != null) {
				// 存放创建好的Fragment到集合中缓存起来
				mFragments.put(position, fragment);
			}
		}
		return fragment;

	}

}
