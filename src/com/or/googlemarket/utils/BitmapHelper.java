package com.or.googlemarket.utils;

import com.lidroid.xutils.BitmapUtils;

import android.content.Context;

public class BitmapHelper {
	private BitmapHelper() {
	}

	private static BitmapUtils bitmapUtils;

	/**
	 * BitmapUtils不是单例的 根据需要重载多个获取实例的方法
	 *
	 * @param appContext
	 *            application context
	 * @return
	 */
	public static BitmapUtils getBitmapUtils() {
		if (bitmapUtils == null) {
			// bitmapUtils = new BitmapUtils(UiUtils.getContext());

			bitmapUtils = new BitmapUtils(UiUtils.getContext(), // 上下文
					FileUtils.getIconDir().getAbsolutePath(), // 第二个参数，缓存图片路径
					0.3f// 第三个参数，最多能够去缓存多大的空间,加载图片最多消耗多少比例的内存，0.05~0.8f
			);
		}
		return bitmapUtils;
	}
}
