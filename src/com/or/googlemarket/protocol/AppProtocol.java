package com.or.googlemarket.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.or.googlemarket.domain.AppInfo;

public class AppProtocol extends BaseProtocol<List<AppInfo>> {

	public String getKey() {
		return "app";
	}

	public List<AppInfo> paserJson(String json) {
		JSONArray jsonArray;
		List<AppInfo> appInfos = new ArrayList<AppInfo>();
		try {
			jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				long id = jsonObject.getLong("id");
				String name = jsonObject.getString("name");
				String packageName = jsonObject.getString("packageName");
				String iconUrl = jsonObject.getString("iconUrl");
				float stars = Float.parseFloat(jsonObject.getString("stars"));
				long size = jsonObject.getLong("size");
				String downloadUrl = jsonObject.getString("downloadUrl");
				String describe = jsonObject.getString("des");
				AppInfo info = new AppInfo(id, name, packageName, iconUrl, stars, size, downloadUrl, describe);
				appInfos.add(info);
			}

			return appInfos;
		} catch (JSONException e) {

			e.printStackTrace();
			return null;
		}

	}

}
