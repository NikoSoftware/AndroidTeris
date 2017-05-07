package com.example.androidteris;

import com.example.Bmob.PersonDate;
import com.example.androidteris.R;
import com.example.constant.Constant;
import com.example.db.UserTool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

public class CustomRegisterPage extends BaseActivity{

	public Button back;
	private Button register;
	private EditText name;
	private EditText pwd1;
	private EditText pwd2;
	private TextView warn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_register);
		
		init();
		
		
	}

	private void init() {
		// TODO Auto-generated method stub
		name = (EditText) findViewById(R.id.name);
		pwd1 = (EditText) findViewById(R.id.pwd);
		pwd2 = (EditText) findViewById(R.id.pwd2);
		warn = (TextView) findViewById(R.id.warn);
		back = (Button) findViewById(R.id.back);
		register = (Button) findViewById(R.id.register);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			final String personName =  name.getEditableText().toString().trim();
			final String personPwd = pwd1.getEditableText().toString().trim();
			String personPwd2 = pwd2.getEditableText().toString().trim();
			if(personName.equals("")){
				Toast.makeText(CustomRegisterPage.this, "用户名不能为空",Toast.LENGTH_SHORT).show();
				return;
			}

			if(personPwd.equals("")||personPwd2.equals("")){
				Toast.makeText(CustomRegisterPage.this, "密码不能为空",Toast.LENGTH_SHORT).show();
				return;
			}

			if((!personPwd.equals(personPwd2))){
				Toast.makeText(CustomRegisterPage.this, "两次密码不一致",Toast.LENGTH_SHORT).show();
				return;
			}
			
			
				PersonDate person = new PersonDate();
				person.setName(personName);
				person.setPassword(personPwd);
				person.save(CustomRegisterPage.this, new SaveListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						Constant.CustomName = personName;
						Constant.PassWords = personPwd;
						UserTool.saveAccount(CustomRegisterPage.this,personName,personPwd);
						Intent intent = new Intent(CustomRegisterPage.this, HomePage.class);
						startActivity(intent);
						Toast.makeText(CustomRegisterPage.this, "注册成功",Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						name.setText(null);
						pwd1.setText(null);
						pwd2.setText(null);
						warn.setText("用户名已存在");
					}
				});
				
			}
		});
		
		
		name.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				warn.setText(null);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	
	
	
	
}
