package com.supinfo.service;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckReturnJosnService {

	public static boolean CheckSuccess(String result) throws JSONException {
		
		JSONObject object = new JSONObject(result);
		boolean decide = object.getBoolean("success");
		if(decide){
			return true;
		}else
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
		return false;
	}

}
