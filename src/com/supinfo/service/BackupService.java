package com.supinfo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.android.phonne.Date;
//import com.android.phonne.SimpleDateFormat;








import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.util.Base64;

public class BackupService {
	private static final int PHONES_CONTACT_ID_INDEX = 0;
    private static final int PHONES_DISPLAY_NAME_INDEX = 1;
    private static final int PHONES_NUMBER_INDEX = 2;
    private static final int  PHONES_ID_INDEX= 3;
    
    private static Uri SMS_INBOX = Uri.parse("content://sms/");
    
	public static boolean BackupContacts(Context mContext, String username, String password) throws JSONException {
		JSONArray jsonArray = new JSONArray(); 
		final String[] PHONES_PROJECTION = new String[] {
				Phone.CONTACT_ID,Phone.DISPLAY_NAME, Phone.NUMBER, Phone._ID};
		ContentResolver resolver = mContext.getContentResolver();
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);
		if (phoneCursor != null) {		
			int loop = 0;
		    while (phoneCursor.moveToNext()) {
			String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
			if (TextUtils.isEmpty(phoneNumber))
			    continue;
			Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
			String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
			Long phoneID = phoneCursor.getLong(PHONES_ID_INDEX);
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Contact_ID", contactid);  
            jsonObject.put("_ID", phoneID);  
            jsonObject.put("DNAME", contactName);
            jsonObject.put("PNUM", phoneNumber);
	
            jsonArray.put(loop, jsonObject);
			loop++;
		    }

		    phoneCursor.close();
		}
		String path = "http://91.121.105.200/API/"; //http://192.168.1.103:8080/webserver/TestServlet
		Map<String , String> params = new HashMap<String, String>();
		params.put("action", "backupcontacts");
		params.put("login", username);
		params.put("password", password);
		try {
			System.out.println( "a2222");
			return sendPostquest(path, params, jsonArray, "contacts", "UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	public static boolean BackupSMS(Context mContext, String username, String password) throws JSONException {
		JSONArray jsonArray = new JSONArray(); 
		
		
		//final String[] PHONES_PROJECTION = new String[] {
		//		Phone.CONTACT_ID,Phone.DISPLAY_NAME, Phone.NUMBER, Phone._ID };
		
		ContentResolver resolver = mContext.getContentResolver();
		Cursor SmsCursor = resolver.query(SMS_INBOX, null, null, null,"date desc");
		if (SmsCursor != null) {
			String receiveTime="";
			int loop = 0;
		    while (SmsCursor.moveToNext()) {

			String Send_Num = SmsCursor.getString(SmsCursor.getColumnIndex("address"));
			String type = SmsCursor.getString(SmsCursor.getColumnIndex("type"));
			String date = SmsCursor.getString(SmsCursor.getColumnIndex("date"));
			if(date!=null && date!=""){
				Date datetime = new Date(Long.valueOf(date));
				SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				receiveTime = Dateformat.format(datetime);
            } 
			String body = SmsCursor.getString(SmsCursor.getColumnIndex("body"));
			//String body_Base64 = new String(Base64.encode(body.getBytes(), Base64.DEFAULT));
			String body_Base64 = Base64.encodeToString(body.getBytes(), Base64.DEFAULT);
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Send_Num", Send_Num);
            jsonObject.put("type", type);  
            jsonObject.put("date", receiveTime);
            jsonObject.put("body", body_Base64);
            
			
            jsonArray.put(loop, jsonObject);
			
			loop++;
			
		    }

		    SmsCursor.close();
		}
		
		String path = "http://91.121.105.200/API/"; //http://192.168.1.103:8080/webserver/TestServlet
		Map<String , String> params = new HashMap<String, String>();
		params.put("action", "backupsms");
		params.put("login", username);
		params.put("password", password);
		params.put("box", "inbox");
		try {
			System.out.println( "a2222");
			return sendPostquest(path, params, jsonArray, "sms", "UTF-8");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	
	

	private static boolean sendPostquest(String path,
			Map<String, String> params, JSONArray jsonArray, String back_type, String encoding) {
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
			
			pairs.add(new BasicNameValuePair(back_type, jsonArray.toString()));
			
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
