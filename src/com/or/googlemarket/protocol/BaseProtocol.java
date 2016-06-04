package com.or.googlemarket.protocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringWriter;

import com.lidroid.xutils.util.IOUtils;
import com.or.googlemarket.http.HttpHelper;
import com.or.googlemarket.http.HttpHelper.HttpResult;
import com.or.googlemarket.utils.FileUtils;

import android.os.SystemClock;

public abstract class BaseProtocol<T> {

	public T load(int index) {
		SystemClock.sleep(500);

		String json = loadLocal(index);

		if (json == null) {
			// 请求服务器
			json = loadServer(index);
			if (json != null) {
				saveLocal(json, index);
			}
		}

		if (json != null) {
			return paserJson(json);
		} else {
			return null;
		}
	}

	/**
	 * 请求服务器
	 * 
	 * @param index
	 * @return
	 */
	private String loadServer(int index) {
		// "http://127.0.0.1:8090/home?index=index"
		HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey() + "?index=" + index + getParams());

		if (httpResult != null) {
			String json = httpResult.getString();
			return json;
		} else {
			return null;
		}

	}

	/** 额外请求参数，有需要就实现 - 标示符 */
	protected String getParams() {
		return "";
	}

	/**
	 * 把数据保存到本地 ,请求服务器所返回的json文件中，让它带有过期时间从而好判断
	 * 所以保存的时候可以在第一行写一个过期时间，然后再保存，如果发现文件过期时间小于当前时间，就不要再复用缓存了。
	 * 1.把整个json数据写到本地文件中。 2.把每条数据摘出来保存到数据库
	 * 
	 * @param json
	 * @param index
	 */
	private void saveLocal(String json, int index) {
		BufferedWriter buffer = null;
		try {
			File dir = FileUtils.getCacheDir();
			File file = new File(dir, getKey() + "_" + index+getParams());
			FileWriter writer = new FileWriter(file);
			buffer = new BufferedWriter(writer);
			buffer.write(System.currentTimeMillis() + 1000 * 100 + "");
			buffer.newLine();// 换行
			buffer.write(json);// 写入整个json文件

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(buffer);
		}
	}

	/**
	 * 加载本地缓存数据
	 * 
	 * @param index
	 * @return
	 */
	private String loadLocal(int index) {
		// 获取缓存所在文件夹位置
		File dir = FileUtils.getCacheDir();
		File file = new File(dir, getKey() + "_" + index+getParams());
		BufferedReader buffer = null;
		try {
			FileReader reader = new FileReader(file);
			buffer = new BufferedReader(reader);
			long outOfDate = Long.parseLong(buffer.readLine());
			if (System.currentTimeMillis() > outOfDate) {
				return null;
			} else {
				String str = null;
				StringWriter sw = new StringWriter();// 写入内存中
				while ((str = buffer.readLine()) != null) {
					sw.write(str);
				}
				return sw.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			IOUtils.closeQuietly(buffer);
		}
	}

	/** 说明关键字 */
	public abstract String getKey();

	/** 解析json数据 */
	public abstract T paserJson(String json);
}
