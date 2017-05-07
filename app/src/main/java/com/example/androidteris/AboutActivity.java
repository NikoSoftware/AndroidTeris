package com.example.androidteris;

import com.example.about.Findfgm;
import com.example.about.Frdfgm;
import com.example.about.Myfgm;
import com.example.about.WeiXinfgm;
import com.example.androidteris.R;
import com.example.androidteris.BackService.LocalBinder;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends BaseActivity implements OnClickListener {

	public ImageView button1;
	public ImageView button2;
	public ImageView button3;
	public ImageView button4;
	
	public TextView text1;
	public TextView text2;
	public TextView text3;
	public TextView text4;
	
	public WeiXinfgm mWeiXin;
	public Fragment mFrd;
	public Fragment mFind;
	public Fragment mMyself;
	
	public BackService backPlay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about_activity);
		
		Intent intent = new Intent(this,BackService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
		Log.d("TAg===>","intent ok");
		InitView();
	}
	
	
	private ServiceConnection conn  =new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			
			LocalBinder binder =  (LocalBinder)service;		
			backPlay = binder.getService();
			Log.d("TAg===>","backPlay12 ok");

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		
	};

	private void InitView() {
		// TODO Auto-generated method stub
		button1 =(ImageView) findViewById(R.id.img1);
		button2 =(ImageView) findViewById(R.id.img2);
		button3 =(ImageView) findViewById(R.id.img3);
		button4 =(ImageView) findViewById(R.id.img4);
		
		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);
		text3 = (TextView) findViewById(R.id.text3);
		text4 = (TextView) findViewById(R.id.text4);
		

		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		
		
		setSelet(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
		resetIcon();
		
		switch(v.getId()){
		
		case R.id.img1:
			Log.d("TAG===>",(backPlay==null)+"");
			backPlay.playTwo();
			setSelet(1);
			break;
		
		case R.id.img2:
			backPlay.playTwo();
			setSelet(2);
			break;
		case R.id.img3:
			backPlay.playTwo();
			setSelet(3);
			break;
		case R.id.img4:
			backPlay.playTwo();
			setSelet(4);
			break;
		
		
		}
		
		
	}

	private void setSelet(int i) {
		// TODO Auto-generated method stub
		FragmentManager fm = getFragmentManager();
		FragmentTransaction  ft =  fm.beginTransaction();
		
		hideTransaction(ft);
		
		switch (i) {
		case 1:
			
			if(mWeiXin==null){
				
				mWeiXin = new WeiXinfgm();
				ft.add(R.id.frameLayout, mWeiXin);
				
			}else{
				ft.show(mWeiXin);
				
			}
			
			button1.setImageResource(R.drawable.weixin_press);
			text1.setTextColor(Color.RED);
			break;
			
		case 2:
		
			if(mFrd==null){
				
				mFrd = new Frdfgm();
				ft.add(R.id.frameLayout, mFrd);
			}else{
				ft.show(mFrd);
				
			}
			
			button2.setImageResource(R.drawable.friends_press);
			text2.setTextColor(Color.RED);
			break;
			
		case 3:
			
			if(mFind==null){
				mFind = new Findfgm();
				ft.add(R.id.frameLayout, mFind);
			}else{
				ft.show(mFind);
				
			}
			
			button3.setImageResource(R.drawable.find_press);
			text3.setTextColor(Color.RED);
			break;
			
		case 4:
	
			if(mMyself==null){
				
				mMyself = new Myfgm();
				ft.add(R.id.frameLayout, mMyself);
			}else{
				ft.show(mMyself);
				
			}
			
			button4.setImageResource(R.drawable.myself_press);
			text4.setTextColor(Color.RED);
			break;
			
		}
		
		
		
		ft.commit();
		
	}

	private void hideTransaction(FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
		if(mWeiXin!=null){
			
			ft.hide(mWeiXin);
		}
		
		if(mFrd!=null){
			
			ft.hide(mFrd);
		}
		if(mFind!=null){
			
			ft.hide(mFind);
		}
		if(mMyself!=null){
			
			ft.hide(mMyself);
		}
		
		
		
	}

	private void resetIcon() {
		// TODO Auto-generated method stub
		
		button1.setImageResource(R.drawable.weixin_normal);
		text1.setTextColor(Color.GRAY);
		button2.setImageResource(R.drawable.friends_normal);
		text2.setTextColor(Color.GRAY);
		button3.setImageResource(R.drawable.find_normal);
		text3.setTextColor(Color.GRAY);
		button4.setImageResource(R.drawable.myself_normal);
		text4.setTextColor(Color.GRAY);
		
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		unbindService(conn);
		finish();
	}
	

	
	
}
