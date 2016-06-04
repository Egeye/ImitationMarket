package com.or.googlemarket.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class RankingProtocol extends BaseProtocol<List<String>> {

	@Override
	public String getKey() {
		return "hot";
	}

	@Override
	public List<String> paserJson(String json) {
		List<String> datas = new ArrayList<String>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				String str = array.getString(i);
				datas.add(str);
			}
			return datas;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}

}
