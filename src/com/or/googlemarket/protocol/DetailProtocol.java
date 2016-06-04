package com.or.googlemarket.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.or.googlemarket.domain.AppInfo;

public class DetailProtocol extends BaseProtocol<AppInfo> {
	String packageName;

	public DetailProtocol(String packageName) {
		super();
		this.packageName = packageName;
	}

	@Override
	public String getKey() {
		return "detail";
	}

	@Override
	public AppInfo paserJson(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);

			long id = jsonObject.getLong("id");
			String name = jsonObject.getString("name");
			String packageName = jsonObject.getString("packageName");
			String iconUrl = jsonObject.getString("iconUrl");
			float stars = Float.parseFloat(jsonObject.getString("stars"));
			long size = jsonObject.getLong("size");
			String downloadUrl = jsonObject.getString("downloadUrl");
			String describe = jsonObject.getString("des");

			String downloadNum = jsonObject.getString("downloadNum");
			String version = jsonObject.getString("version");
			String date = jsonObject.getString("date");
			String author = jsonObject.getString("author");

			List<String> screen = new ArrayList<String>();
			JSONArray screenArray = jsonObject.getJSONArray("screen");
			for (int i = 0; i < screenArray.length(); i++) {
				screen.add(screenArray.getString(i));
			}

			List<String> safeUrl = new ArrayList<String>();
			List<String> safeDesUrl = new ArrayList<String>();
			List<String> safeDes = new ArrayList<String>();
			List<Integer> safeDesColor = new ArrayList<Integer>();

			JSONArray safeArray = jsonObject.getJSONArray("safe");
			for (int i = 0; i < safeArray.length(); i++) {
				JSONObject safeObject = safeArray.getJSONObject(i);
				safeUrl.add(safeObject.getString("safeUrl"));
				safeDesUrl.add(safeObject.getString("safeDesUrl"));
				safeDes.add(safeObject.getString("safeDes"));
				safeDesColor.add(safeObject.getInt("safeDesColor"));
			}
			AppInfo appInfo = new AppInfo(id, name, packageName, iconUrl, stars, size, downloadUrl, describe,
					downloadNum, version, date, author, screen, safeUrl, safeDesUrl, safeDes, safeDesColor);
			return appInfo;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected String getParams() {
		return "&packageName=" + packageName;
	}

}
