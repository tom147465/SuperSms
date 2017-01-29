package com.supinfo.supsms;

import com.supinfo.service.LoginService;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private EditText usernameText;
	private EditText passwordText;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		usernameText = (EditText) this.findViewById(R.id.InputUsername);
		passwordText = (EditText) this.findViewById(R.id.InputPassWord);
		System.out.println("a1111");
	}


	public void Login(View v){
		String username = usernameText.getText().toString();
		String password = passwordText.getText().toString();
		boolean result = LoginService.cert(username , password );
		System.out.println("a1111");
		if(result){
			Toast.makeText(getApplicationContext(),"success", 1).show();
			Intent intent = new Intent(MainActivity.this,
					BackupActivity.class); //Ìø×ªÒ³Ãæ
			intent.putExtra("username", username);
			intent.putExtra("password", password);
			MainActivity.this.startActivity(intent);
			
		}else{
			Toast.makeText(getApplicationContext(),"error", 1).show();
		}
		
	}
	
}
