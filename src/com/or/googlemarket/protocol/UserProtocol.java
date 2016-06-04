package com.or.googlemarket.protocol;

import org.json.JSONException;
import org.json.JSONObject;

import com.or.googlemarket.domain.UserInfo;

public class UserProtocol extends BaseProtocol<UserInfo> {

	@Override
	public String getKey() {
		return "user";
	}

	@Override
	public UserInfo paserJson(String json) {

		try {
			JSONObject jsonobject = new JSONObject(json);
			String name = jsonobject.getString("name");
			String url = jsonobject.getString("url");
			String email = jsonobject.getString("email");
			UserInfo info = new UserInfo(name, url, email);
			return info;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
