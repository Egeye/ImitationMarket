package com.or.googlemarket.utils;

import com.lidroid.xutils.BitmapUtils;

import android.content.Context;

public class BitmapHelper {
	private BitmapHelper() {
	}

	private static BitmapUtils bitmapUtils;

	/**
	 * BitmapUtils���ǵ����� ������Ҫ���ض����ȡʵ���ķ���
	 *
	 * @param appContext
	 *            application context
	 * @return
	 */
	public static BitmapUtils getBitmapUtils() {
		if (bitmapUtils == null) {
			// bitmapUtils = new BitmapUtils(UiUtils.getContext());

			bitmapUtils = new BitmapUtils(UiUtils.getContext(), // ������
					FileUtils.getIconDir().getAbsolutePath(), // �ڶ�������������ͼƬ·��
					0.3f// ����������������ܹ�ȥ������Ŀռ�,����ͼƬ������Ķ��ٱ������ڴ棬0.05~0.8f
			);
		}
		return bitmapUtils;
	}
}
