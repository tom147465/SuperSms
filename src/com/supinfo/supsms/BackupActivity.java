package com.supinfo.supsms;


import org.json.JSONException;

import com.supinfo.service.BackupService;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Toast;

public class BackupActivity extends ActionBarActivity {

	private String username;
	private String password;
	private Button SMS_Backup;
	private Button Contacts_Backup;
	private CheckedTextView Log_Out;
	private CheckedTextView ABOUT;
	Context mContext = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_backup);
		SMS_Backup = (Button) findViewById(R.id.button_backupsms);
		Contacts_Backup = (Button) findViewById(R.id.button_backupcontacts);
		Log_Out = (CheckedTextView)findViewById(R.id.Logout);
		ABOUT = (CheckedTextView)findViewById(R.id.button_ABOUT);
		
		Bundle extras = getIntent().getExtras();
		username = extras.getString("username");
		password = extras.getString("password");
		System.out.println(username + "---" + password);
		
		SMS_Backup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean result;
				try {
					result = BackupService.BackupSMS(mContext, username, password);
					if(result){
						Toast.makeText(getApplicationContext(),"Backup success", 1).show();
					}else{
						Toast.makeText(getApplicationContext(),"Backup error", 1).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
		
		Contacts_Backup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean result;
				try {
					result = BackupService.BackupContacts(mContext, username, password);
					if(result){
						Toast.makeText(getApplicationContext(),"Backup success", 1).show();
					}else{
						Toast.makeText(getApplicationContext(),"Backup error", 1).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Log_Out.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BackupActivity.this,
						MainActivity.class);
				BackupActivity.this.startActivity(intent);
			}
		});
		
		ABOUT.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BackupActivity.this,
						AboutActivity.class);
				BackupActivity.this.startActivity(intent);
			}
		});
	
	}
	
}
