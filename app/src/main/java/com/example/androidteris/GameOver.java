package com.example.androidteris;

import com.example.Bmob.PlayerData;
import com.example.Listenner.HttpListenner;
import com.example.androidteris.R;
import com.example.constant.Constant;
import com.example.httputil.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;

public class GameOver extends BaseActivity{

	public TextView textViewTime;
	public TextView textViewScore;
	public Button button1;
	public Button button2;
	public int score;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.game_over);
		
		textViewTime = (TextView) findViewById(R.id.TextView1);
		textViewScore = (TextView) findViewById(R.id.TextView2);
		
		
		LinearLayout linear1 = (LinearLayout) findViewById(R.id.linear1);
		AnimationSet set =new AnimationSet(false);
		
		Animation animation1 = new ScaleAnimation(0, 1, 0, 1);
		animation1.setDuration(2000);
		set.addAnimation(animation1);
			
		linear1.setAnimation(set);
		
		
		
		button1 = (Button) findViewById(R.id.Button1);
		button2 = (Button) findViewById(R.id.Button2);
		Intent intent = getIntent();
		
		final String str =intent.getStringExtra("time");
		Log.d("TAG===>",str);
		textViewTime.setText(str);
		score = Integer.parseInt(intent.getStringExtra("score"));
		textViewScore.setText(intent.getStringExtra("score"));
		
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(GameOver.this,MainActivity.class);
				startActivity(intent);
				finish();
				
			}
		});
		
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Constant.CustomName==null){
					
					Toast.makeText(GameOver.this, "请登录", 1).show();
					Intent intent = new Intent(GameOver.this,CustomLoginPage.class);
					startActivity(intent);
					
					
				}else{
					PlayerData player = new PlayerData();
					player.setName(Constant.CustomName);
					player.setScore(score);
					player.setTime(str);
					
					player.save(GameOver.this,new SaveListener() {
						
						
						@Override
						public void onSuccess() {
							Intent intent5 = new Intent(GameOver.this,CustomRecord.class);
							startActivity(intent5);
							Toast.makeText(GameOver.this, "数据上传成功", 1).show();
						}
						
						@Override
						public void onFailure(int arg0, String arg1) {
							
							Toast.makeText(GameOver.this, "数据上传失败", 1).show();
						}
					});
					
					
				
				}
			}
		});
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
}
