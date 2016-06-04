package com.or.googlemarket.utils;

import java.io.File;

import android.os.Environment;

public class FileUtils {
	// 缓存json
	public static final String CACHE = "cache";
	public static final String ROOT = "AppMarket";
	// 缓存图片
	public static final String ICON = "icon";

	public static File getDir(String str) {
		StringBuilder path = new StringBuilder();

		if (isSDAvailable()) {
			path.append(Environment.getExternalStorageDirectory().getAbsolutePath());
			path.append(File.separator);// 加上斜线
			path.append(ROOT); // 路径/mnt/sdcard/Appmarket/cache
			path.append(File.separator);// 加上斜线
			path.append(str); // 路径/mnt/sdcard/Appmarket/cache/str
		} else {
			// 得到当前程序的cache路径
			File dir = UiUtils.getContext().getCacheDir();
			path.append(dir.getAbsolutePath()); // /data/data/packagename/cache
			path.append(File.separator);
			path.append(str);
		}

		File file = new File(path.toString());
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();// 创建文件夹

		}
		return file;
	}

	private static boolean isSDAvailable() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取json缓存路径
	 * 
	 * @return
	 */
	public static File getCacheDir() {
		return getDir(CACHE);
	}

	/**
	 * 获取图片的缓存路径
	 * 
	 * @return
	 */
	public static File getIconDir() {
		return getDir(ICON);

	}

}
