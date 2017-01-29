package com.supinfo.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class LoginService {

	public static boolean cert(String username, String password) {
		String path = "http://91.121.105.200/API/"; //http://192.168.1.103:8080/webserver/TestServlet
		Map<String , String> params = new HashMap<String, String>();
		params.put("action", "login");
		params.put("login", username);
		params.put("password", password);
		try {
			System.out.println( "a2222");
			return sendLoginPostquest(path, params , "UTF-8");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	private static boolean sendLoginPostquest(String path,
			Map<String, String> params, String encoding) {
		String result = null;
		try{
			System.out.println( "a3333");
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost(path);
			
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			if(params!=null && !params.isEmpty()){
				for(Map.Entry<String, String> entry : params.entrySet()){
					pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, encoding);
			
			post.setEntity(entity);
			HttpResponse res = httpClient.execute(post);
			System.out.println( "a4444");
			if(res.getStatusLine().getStatusCode() == 200){
				HttpEntity Hentity = res.getEntity();
				result = EntityUtils.toString(Hentity);

				System.out.println(res.getStatusLine());
				System.out.println(Hentity.getContentLength());

				return CheckReturnJosnService.CheckSuccess(result);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return false;
	}

}
