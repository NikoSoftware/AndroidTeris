package com.example.androidteris;

import com.example.androidteris.R;
import com.example.androidteris.BackService.LocalBinder;
import com.example.constant.Constant;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MusicPage extends BaseActivity implements OnClickListener {

	public CheckBox backMusic;
	public CheckBox buttonMusic;
	public CheckBox buttonGridLine;
	public Button setting;
	public Button cancel;
	
	public Boolean isBackMusic;
	public Boolean isButtonMusic;
	public Boolean isButtonGridLine;
	
	public BackService backPlay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.music_page);
		
		backMusic = (CheckBox) findViewById(R.id.backmusic);
		buttonMusic =  (CheckBox) findViewById(R.id.buttonmusic);
		buttonGridLine = (CheckBox) findViewById(R.id.buttonGridLine);
		setting = (Button)findViewById(R.id.setting);
		cancel = (Button) findViewById(R.id.cancel);
		getGameSharedPreference();
		
		backMusic.setChecked(isBackMusic);
		buttonMusic.setChecked(isButtonMusic);
		buttonGridLine.setChecked(isButtonGridLine);
		
		Intent intent = new Intent(this,BackService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
		
		setting.setOnClickListener(this);
		cancel.setOnClickListener(this);
		
	}
	
	
	private ServiceConnection conn  =new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			
			LocalBinder binder =  (LocalBinder)service;		
			backPlay = binder.getService();
			Log.d("TAg===>",(backPlay==null)+ "ok");

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
	
			
        case R.id.setting:
			
        	backPlay.playTwo();
        	isButtonMusic = buttonMusic.isChecked();
        	isBackMusic = backMusic.isChecked();
        	isButtonGridLine = buttonGridLine.isChecked();
			Constant.GridPaint = isButtonGridLine;
			backPlay.isMusic= isButtonMusic;
			backPlay.isBackMusic = isBackMusic;

			if(!isBackMusic){
				backPlay.pauseBackMusic();
			}else{
				backPlay.startBackMusic();
			}

			setGameSharedPreference();
			finish();
			break;
			
			
			case R.id.cancel:
				backPlay.playTwo();
				finish();
				break;

		default:
			break;
		}
		
		
		
	}
	
	
	
	public void setGameSharedPreference(){
		
		SharedPreferences sp = getSharedPreferences("music_config", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("isBackMusic", isBackMusic);
		editor.putBoolean("isButtonMusic", isButtonMusic);
		editor.putBoolean("isButtonGridLine", isButtonGridLine);
		editor.commit();
	}
	
    public void getGameSharedPreference(){
		
		SharedPreferences sp = getSharedPreferences("music_config", Context.MODE_PRIVATE);
		isBackMusic = sp.getBoolean("isBackMusic", true);
		isButtonMusic = sp.getBoolean("isButtonMusic", true);
		isButtonGridLine = sp.getBoolean("isButtonGridLine", true);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		unbindService(conn);
		finish();
	}
	

	
	
	
}
