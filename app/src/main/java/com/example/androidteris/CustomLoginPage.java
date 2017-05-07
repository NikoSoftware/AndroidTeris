package com.example.androidteris;

import java.util.List;

import org.json.JSONObject;

import com.example.Bmob.PersonDate;
import com.example.Listenner.HttpListenner;
import com.example.androidteris.R;
import com.example.constant.Constant;
import com.example.db.UserInfo;
import com.example.db.UserTool;
import com.example.httputil.HttpUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetCallback;
import cn.bmob.v3.listener.GetListener;

public class CustomLoginPage extends BaseActivity implements OnClickListener{

	public EditText name;
	public EditText pwd;
	public Button login;
	public Button register;
	public TextView warn;
	public Context mContext;

	
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				
				warn.setText(msg.obj.toString());

				break;

			default:
				break;
			}
			
		}

		
	};
	private String Cname;
	private String Cpasswords;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_login);
		this.mContext = this;
		name  = (EditText) findViewById(R.id.name);	
		pwd  = (EditText) findViewById(R.id.pwd);
		warn  = (TextView) findViewById(R.id.warn);
		
		login = (Button) findViewById(R.id.login);
		register = (Button) findViewById(R.id.register);
		login.setOnClickListener(this);
		register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.login:
			 Cname = name.getText().toString().trim();
			 Cpasswords = pwd.getText().toString().trim();

			if(TextUtils.isEmpty(Cname)){
				Toast.makeText(CustomLoginPage.this, "用户名不能为空",Toast.LENGTH_SHORT).show();
				break;
			}
			if(TextUtils.isEmpty(Cpasswords)){
				Toast.makeText(CustomLoginPage.this, "密码不能为空",Toast.LENGTH_SHORT).show();
				break;
			}

			BmobQuery<PersonDate> bmobQuery = new BmobQuery<PersonDate>();

			bmobQuery.addWhereEqualTo("name", Cname);
			bmobQuery.findObjects(this, new FindListener<PersonDate>() {

				@Override
				public void onError(int arg0, String arg1) {
					// TODO Auto-generated method stub
					Toast.makeText(CustomLoginPage.this, "没有发现账号",Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onSuccess(List<PersonDate> arg0) {
					if(Cpasswords.equals(arg0.get(0).getPassword())){
						Constant.CustomName=Cname;
						UserTool.saveAccount(mContext,Cname,Cpasswords);
						Intent intent = new Intent(CustomLoginPage.this,CustomRecord.class);
                       	startActivity(intent);
                       	finish();
					}else{
						pwd.setText(null);
						Toast.makeText(CustomLoginPage.this, "密码有误",Toast.LENGTH_SHORT).show();
					}
				}
			});

			break;
			
        case R.id.register:

        	Intent intent = new Intent(CustomLoginPage.this,CustomRegisterPage.class);
        	startActivity(intent);
			break;
		default:
			break;
		}
		
		
	}


	
}
