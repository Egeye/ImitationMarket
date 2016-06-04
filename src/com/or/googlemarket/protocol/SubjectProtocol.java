package com.or.googlemarket.protocol;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.or.googlemarket.domain.SubjectInfo;

public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>> {

	@Override
	public String getKey() {
		return "subject";
	}

	@Override
	public List<SubjectInfo> paserJson(String json) {
		List<SubjectInfo> subjectInfos = new ArrayList<SubjectInfo>();
		try {
			JSONArray jsonArray = new JSONArray(json);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String des = jsonObject.getString("des");
				String url = jsonObject.getString("url");
				SubjectInfo info = new SubjectInfo(des, url);
				subjectInfos.add(info);
			}
			return subjectInfos;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
