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
			// ���������
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
	 * ���������
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

	/** �����������������Ҫ��ʵ�� - ��ʾ�� */
	protected String getParams() {
		return "";
	}

	/**
	 * �����ݱ��浽���� ,��������������ص�json�ļ��У��������й���ʱ��Ӷ����ж�
	 * ���Ա����ʱ������ڵ�һ��дһ������ʱ�䣬Ȼ���ٱ��棬��������ļ�����ʱ��С�ڵ�ǰʱ�䣬�Ͳ�Ҫ�ٸ��û����ˡ�
	 * 1.������json����д�������ļ��С� 2.��ÿ������ժ�������浽���ݿ�
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
			buffer.newLine();// ����
			buffer.write(json);// д������json�ļ�

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(buffer);
		}
	}

	/**
	 * ���ر��ػ�������
	 * 
	 * @param index
	 * @return
	 */
	private String loadLocal(int index) {
		// ��ȡ���������ļ���λ��
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
				StringWriter sw = new StringWriter();// д���ڴ���
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

	/** ˵���ؼ��� */
	public abstract String getKey();

	/** ����json���� */
	public abstract T paserJson(String json);
}
