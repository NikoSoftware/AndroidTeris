package com.example.androidteris;

import com.example.androidteris.BackService.LocalBinder;
import com.example.constant.Constant;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import cn.bmob.v3.Bmob;



public class HomePage extends Activity implements OnClickListener{

	public ImageView startGame;
	public ImageView setting;
	public ImageView music;
	public ImageView about;
	public ImageView custom;
	
	public BackService backPlay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_page);
		
		 Bmob.initialize(this, "66a8b513ff7c1b9e116d2c34dfbda63b");
		
		getGameSharedPreference();
		
		startGame = (ImageView) findViewById(R.id.startGame);
		
		setting = (ImageView) findViewById(R.id.setUp);
		music = (ImageView) findViewById(R.id.music);
		about = (ImageView) findViewById(R.id.aboutMe);
		custom = (ImageView) findViewById(R.id.custom);
		
		startGame.setOnClickListener(this);
		setting.setOnClickListener(this);
		music.setOnClickListener(this);
		about.setOnClickListener(this);
		custom.setOnClickListener(this);
		
		Intent intent = new Intent(this,BackService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
		
		
		Log.d("TAg===>","backPlay0.5 ok");
	}
	
	
	private ServiceConnection conn  =new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			
			LocalBinder binder =  (LocalBinder)service;		
			backPlay = binder.getService();
			backPlay.CreateBackSound();
			Log.d("TAg===>","backPlay ok");

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		
	};
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.startGame:
			
			backPlay.playFour();
			Intent intent = new Intent(this,MainActivity.class);
			
			startActivity(intent);
			backPlay.playFive();
			
			break;
		
		
		case R.id.setUp:
			backPlay.playOne();
			
           Intent intent1 = new Intent(this,SettingPage.class);
			
			startActivity(intent1);
			break;
			
		case R.id.music:
			backPlay.playOne();
			
			Intent intent2 = new Intent(this,MusicPage.class);
		    startActivity(intent2);
	
			break;
			
		case R.id.aboutMe:
			backPlay.playFour();
			backPlay.playFive();
			
            Intent intent3 = new Intent(this,AboutActivity.class);
			startActivity(intent3);
			
			//Toast.makeText(this, "setup", 0).show();
			break;
			
		case R.id.custom:
			backPlay.playFour();
			backPlay.playFive();
			
			if(Constant.CustomName!=null){
				
				Intent intent5 = new Intent(HomePage.this,CustomRecord.class);
				startActivity(intent5);
				
			}else{
			
            Intent intent4 = new Intent(this,CustomLoginPage.class);
			startActivity(intent4);
			
			}
			//Toast.makeText(this, "setup", 0).show();
			break;

		default:
			break;
		}
		
		
		
	}
	

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		unbindService(conn);
	}
	
    public void getGameSharedPreference(){
		
		SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		Constant.SPEED = sp.getInt("speed", 800);
		 Constant.Cell_widthCount = sp.getInt("Cell_widthCount", 10);
		 Constant.Cell_heightCount = sp.getInt("Cell_heightCount", 14);
		 Constant.TouchLength = sp.getInt("touchLength", 100);
		 
		 SharedPreferences mp = getSharedPreferences("music_config", Context.MODE_PRIVATE);
		 Log.d("TAG===>", mp.getBoolean("isButtonGridLine", true)+"  getSharedPreferences");
		 Constant.GridPaint = mp.getBoolean("isButtonGridLine", true);
	}
	
	
}
