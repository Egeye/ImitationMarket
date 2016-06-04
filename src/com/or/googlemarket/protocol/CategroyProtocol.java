package com.or.googlemarket.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.or.googlemarket.domain.CategoryInfo;

public class CategroyProtocol extends BaseProtocol<List<CategoryInfo>> {

	@Override
	public String getKey() {
		return "category";
	}

	@Override
	public List<CategoryInfo> paserJson(String json) {
		List<CategoryInfo> infos = new ArrayList<CategoryInfo>();
		try {
			JSONArray jarray = new JSONArray(json);
			for (int i = 0; i < jarray.length(); i++) {
				JSONObject jobject = jarray.getJSONObject(i);
				String title = jobject.getString("title");
				
				CategoryInfo category = new CategoryInfo();
				category.setTitle(title);
				category.setIsTitle(true);
				infos.add(category);

				JSONArray array = jobject.getJSONArray("infos");
				for (int i1 = 0; i1 < array.length(); i1++) {
					JSONObject object = array.getJSONObject(i1);
					String url1 = object.getString("url1");
					String url2 = object.getString("url2");
					String url3 = object.getString("url3");

					String name1 = object.getString("name1");
					String name2 = object.getString("name2");
					String name3 = object.getString("name3");

					CategoryInfo info = new CategoryInfo(title, url1, url2, url3, name1, name2, name3,false);
					infos.add(info);
				}
			}
			return infos;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}

}
