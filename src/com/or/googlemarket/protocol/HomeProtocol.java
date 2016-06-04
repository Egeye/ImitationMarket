package com.or.googlemarket.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.or.googlemarket.domain.AppInfo;

public class HomeProtocol extends BaseProtocol<List<AppInfo>> {
	private List<String> pictures;

	/**
	 * ½âÎöjsonÊý¾Ý
	 * 
	 * @param json
	 */
	public List<AppInfo> paserJson(String json) {
		List<AppInfo> appInfos = new ArrayList<AppInfo>();
		pictures = new ArrayList<String>();

		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonArray = jsonObject.getJSONArray("list");

			JSONArray pictureArray = jsonObject.getJSONArray("picture");
			for (int i = 0; i < pictureArray.length(); i++) {
				String str = pictureArray.getString(i);
				pictures.add(str);
			}

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jobject = jsonArray.getJSONObject(i);
				long id = jobject.getLong("id");
				String name = jobject.getString("name");
				String packageName = jobject.getString("packageName");
				String iconUrl = jobject.getString("iconUrl");
				float stars = Float.parseFloat(jobject.getString("stars"));
				long size = jobject.getLong("size");
				String downloadUrl = jobject.getString("downloadUrl");
				String describe = jobject.getString("des");

				AppInfo info = new AppInfo(id, name, packageName, iconUrl, stars, size, downloadUrl, describe);
				appInfos.add(info);
			}
			return appInfos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> getPictures() {
		return pictures;
	}

	@Override
	public String getKey() {
		return "home";
	}

}
