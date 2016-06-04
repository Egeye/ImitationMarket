package com.or.googlemarket.utils;

import java.io.File;

import android.os.Environment;

public class FileUtils {
	// ����json
	public static final String CACHE = "cache";
	public static final String ROOT = "AppMarket";
	// ����ͼƬ
	public static final String ICON = "icon";

	public static File getDir(String str) {
		StringBuilder path = new StringBuilder();

		if (isSDAvailable()) {
			path.append(Environment.getExternalStorageDirectory().getAbsolutePath());
			path.append(File.separator);// ����б��
			path.append(ROOT); // ·��/mnt/sdcard/Appmarket/cache
			path.append(File.separator);// ����б��
			path.append(str); // ·��/mnt/sdcard/Appmarket/cache/str
		} else {
			// �õ���ǰ�����cache·��
			File dir = UiUtils.getContext().getCacheDir();
			path.append(dir.getAbsolutePath()); // /data/data/packagename/cache
			path.append(File.separator);
			path.append(str);
		}

		File file = new File(path.toString());
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();// �����ļ���

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
	 * ��ȡjson����·��
	 * 
	 * @return
	 */
	public static File getCacheDir() {
		return getDir(CACHE);
	}

	/**
	 * ��ȡͼƬ�Ļ���·��
	 * 
	 * @return
	 */
	public static File getIconDir() {
		return getDir(ICON);

	}

}
