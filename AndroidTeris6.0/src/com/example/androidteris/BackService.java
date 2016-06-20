package com.example.androidteris;

import java.io.IOException;
import java.util.HashMap;

import com.example.androidteris.R;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BackService extends Service {

	private final IBinder binder = new LocalBinder();
	public SoundPool soundPool;
	public MediaPlayer  backPlay;
	public HashMap<Integer, Integer> soundPoolMap;
	public Boolean isMusic=true;
	public Boolean isBackMusic=true;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return binder;
	}
	
	class LocalBinder extends Binder{
		
		BackService getService(){
			Log.d("TAg===>","backPlay1 ok");
			return BackService.this;
		}
		
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		 soundPool =new SoundPool(11, AudioManager.STREAM_SYSTEM, 2);
		 soundPoolMap = new HashMap<Integer, Integer>();   
		 soundPoolMap.put(1, soundPool.load(this, R.raw.ka, 1)); 
		 soundPoolMap.put(2, soundPool.load(this, R.raw.sa, 1)); 
		 soundPoolMap.put(3, soundPool.load(this, R.raw.seekbar, 1));
		 soundPoolMap.put(4, soundPool.load(this, R.raw.rotation, 1));
		 soundPoolMap.put(5, soundPool.load(this, R.raw.down, 1));
		 soundPoolMap.put(6, soundPool.load(this, R.raw.fastdown, 1));
		 soundPoolMap.put(7, soundPool.load(this, R.raw.lost, 1));
		 soundPoolMap.put(8, soundPool.load(this, R.raw.delete1, 1));
		 soundPoolMap.put(9, soundPool.load(this, R.raw.delete2, 1));
		 soundPoolMap.put(10, soundPool.load(this, R.raw.delete3, 1));
		 soundPoolMap.put(11, soundPool.load(this, R.raw.delete4, 1));
		 getGameSharedPreference();
		 
		
	
		   Log.d("TAg===>","backPlay11 ok");	
	}
	
	
	public void playOne(){
		if(isMusic)
		 soundPool.play(soundPoolMap.get(1), 1, 1,0, 0, 1); 
		
	}
	
	public void playTwo(){
		if(isMusic){
		 Log.d("TAG","playTwo");
			
			soundPool.setVolume(soundPool.play(soundPoolMap.get(2), 1, 1,0, 0, 1), 1f, 1f);
		}
	}
	public void playThree(){
		if(isMusic)
		 soundPool.play(soundPoolMap.get(3), 1, 1,0, 0, 1); 
		
	}
	
	public void playFour(){
		if(isMusic)
		 soundPool.play(soundPoolMap.get(4), 1, 1,0, 0, 1); 
		
	}
	public void playFive(){
		if(isMusic)
		 soundPool.play(soundPoolMap.get(5), 1, 1,0, 0, 1); 
		
	}
	public void playSix(){
		if(isMusic)
		 soundPool.play(soundPoolMap.get(6), 1, 1,0, 0, 1); 
		
	}
	public void playSeven(){
		if(isMusic)
		 soundPool.play(soundPoolMap.get(7), 1, 1,0, 0, 1); 
		
	}
	public void playEight(){
		if(isMusic)
		 soundPool.play(soundPoolMap.get(8), 1, 1,0, 0, 1); 
		
	}
	public void playNine(){
		if(isMusic)
		 soundPool.play(soundPoolMap.get(9), 1, 1,0, 0, 1); 
		
	}
	public void playTen(){
		if(isMusic)
		 soundPool.play(soundPoolMap.get(10), 1, 1,0, 0, 1); 
		
	}
	public void playEleven(){
		if(isMusic)
		 soundPool.play(soundPoolMap.get(11), 1, 1,0, 0, 1); 
		
	}
	
	
	public void CreateBackSound(){
		
		backPlay = new MediaPlayer();
		backPlay.setVolume(0.4f, 0.4f);
		playBackSound();
		}
		
	
	
	
	public void playBackSound(){
		
		
		Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.menubg);
			try {
				
		    backPlay.setDataSource(this,uri);
			
			backPlay.prepare();
			backPlay.start();
			//÷ÿ∏¥≤•∑≈
			backPlay.setLooping(true);
			
			
			if(!isBackMusic){
				backPlay.pause();
			}
			
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	
	public void pauseBackMusic(){
		

		if(backPlay.isPlaying()){
			backPlay.pause();
			
		}
	}
	
    public void startBackMusic(){
		

    if(backPlay.getDuration()==0){
    	CreateBackSound();
    }else{
			backPlay.start();
    }	
		
	}
	
	public void endBackSound(){
		
		if(backPlay.isPlaying()){
			backPlay.stop();
			
		}
		backPlay.release();

		
	}
	

	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		endBackSound();
		soundPool.release();
		
	}
    public void getGameSharedPreference(){
		
		SharedPreferences sp = getSharedPreferences("music_config", Context.MODE_PRIVATE);	
		isBackMusic = sp.getBoolean("isBackMusic", true);
		isMusic=sp.getBoolean("isButtonMusic", true);
		
	}
	
	

}

