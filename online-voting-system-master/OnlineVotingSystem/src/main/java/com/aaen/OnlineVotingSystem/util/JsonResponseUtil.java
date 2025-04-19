package com.aaen.OnlineVotingSystem.util;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.aaen.OnlineVotingSystem.model.User;

/**
 * @author Mohammad Enayatullah
 *
 */
@Component
public class JsonResponseUtil {

	public static String getResponse(String status, String message, String name, List<User> privilegesList) {
		JSONObject permissions = null;
		if (privilegesList != null) {
			JSONArray jsonArray = new JSONArray(privilegesList);
			permissions = jsonArray.getJSONObject(0);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", status);
		jsonObject.put("message", message);
		jsonObject.put("name", name);
		jsonObject.put("permissions", permissions.get("permissions"));
		return jsonObject.toString();
	}
}
