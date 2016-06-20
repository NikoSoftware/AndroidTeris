package com.example.androidteris;

import com.example.Listenner.ComeBackAdapter;
import com.example.androidteris.R;
import com.example.androidteris.BackService.LocalBinder;
import com.example.base.GameTime;
import com.example.base.Ground;
import com.example.base.Shape;
import com.example.base.ShapeFactory;
import com.example.constant.Constant;
import com.example.controller.Controller;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements ComeBackAdapter,OnTouchListener {

	public Shape shape;
	public Ground ground;
	public ShapeFactory shapeFactory;
	public View gameView;
	public Controller controller; 
	public int downX;
	public int downY;
	public int upX;
	public int upY; 
	public long Score=0;
	
	public ImageView pause;
	public TextView gameScore;
	public TextView gameTime;
	public TextView currentTime; 
	
	public BackService backPlay;
	public GameTime gaT;
	public GameTime gaT2;
	public int touchLength=Constant.TouchLength;
	
	Handler handler= new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			
			case  1:
				((GameView) gameView).display((Shape)msg.obj,ground);
				
				gameScore.setText(ground.getScore()+"");
				switch ((int)(ground.getScore()-Score)) {
				case 50:
					backPlay.playEight();
					Score = ground.getScore();
					break;
				case 100:
					backPlay.playNine();
					Score = ground.getScore();
					break;
				case 200:
					backPlay.playTen();
					Score = ground.getScore();
					break;
				case 400:
					backPlay.playEleven();
					Score = ground.getScore();
					break;
					

				default:
					break;
				}
				
				
				
				break;
			case  2:
				
				gameTime.setText(GameTime.getTime(gaT.hour-gaT2.hour, gaT.minute-gaT2.minute, gaT.sec-gaT2.sec));
			
				currentTime.setText(GameTime.getTime(gaT.hour,gaT.minute,gaT.sec));
				
				
				break;
			
			}
			
			

			
		}
		
		
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		LinearLayout panel = (LinearLayout) findViewById(R.id.panel);
		pause = (ImageView) findViewById(R.id.pause);
		gameScore = (TextView) findViewById(R.id.gameScore);
		gameTime = (TextView) findViewById(R.id.gameTime);
		currentTime = (TextView)findViewById(R.id.currentTime);
		gameView = findViewById(R.id.gameView);
		
		Log.d("TAG===>", ""+GameView.Cell_heightCount+"  "+GameView.Cell_widthCount);
		
	     shapeFactory = new ShapeFactory();
	     ground=new Ground();
		controller = new Controller(ground, shapeFactory,MainActivity.this);
		controller.startGame();
		panel.setOnTouchListener(this);
		 gaT = new GameTime();
		 gaT2= new GameTime();
		getInitTime();
		new Thread(new TimeThread()).start();
		
		Log.d("TAG===》", "shape,startGame");
		
		pause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!controller.isPause)
				{
					controller.isPause = true; 
					pause.setImageResource(R.drawable.pausebutton);
				}else{
					pause.setImageResource(R.drawable.playbutton);
					controller.beginThread();
				}

			}
		});
		
		Intent intent = new Intent(this,BackService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);


	}
	
	private ServiceConnection conn  =new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			
			LocalBinder binder =  (LocalBinder)service;		
			backPlay = binder.getService();
		//	backPlay.CreateBackSound();
			Log.d("TAg===>","backPlay ok");

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		
	};
	
	

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
	public void refreshDisplay(Shape shape) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshDisplay(Shape shape, Ground ground) {
		// TODO Auto-generated method stub
		this.shape = shape;
		Message message = new Message();
		message.what=1;
		message.obj =shape;
		
		handler.sendMessage(message);
		
		
		
	}
	
	
	

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			Log.d("TAG===>","onTouch");
			downX = (int) event.getX();
			downY = (int) event.getY();
			return true;
		}else if(event.getAction() == MotionEvent.ACTION_UP){
			
			upX=(int) event.getX();
			upY=(int) event.getY();
			
			if((Math.abs(upX-downX)-Math.abs(upY-downY))>0){
				
				if((upX-downX)>touchLength){
					
					if(ground.isMove(shape, Shape.RIGHT)){
					
					shape.moveRight();
					backPlay.playFour();
					refreshDisplay(shape,ground);
					return true;
					
					}
				}else if((upX-downX)<-touchLength){
					
					if(ground.isMove(shape, Shape.LEFT)){
					shape.moveLeft();
					backPlay.playFour();
					refreshDisplay(shape,ground);
					return true;
					}
				}
				
			}else if((Math.abs(upX-downX)-Math.abs(upY-downY))<0){
				
				if((upY-downY)>touchLength){
					if(ground.isMove(shape, Shape.DOWN)){
						backPlay.playSix();
						while(ground.isMove(shape, Shape.DOWN)){
							
					
					shape.moveDown();
					refreshDisplay(shape,ground);
					
						
						}
					return true;
					}
				}else if((upY-downY)<-touchLength){
					backPlay.playFour();
					
					if(ground.isMove(shape, Shape.ROTATE)){
					shape.moveUp();
					
					refreshDisplay(shape,ground);
					return true;
					}
				}
				
				
			}
			
			
		}
		
		
		return false;
	}

	@Override
	public boolean isEnd(Shape eshape) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void getInitTime(){
		
		Time t=new Time(); 
		t.setToNow(); 
		gaT2.hour = t.hour; 
		gaT2.minute = t.minute;      
		gaT2.sec = t.second; 
		
	}
	
	
	class TimeThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				
				Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
				t.setToNow(); // 取得系统时间。
				gaT.hour = t.hour; 
				gaT.minute = t.minute;      
				gaT.sec = t.second; 
				
		       Message message =new Message(); 
		       message.what=2;
			   handler.sendMessage(message);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		}
	
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		unbindService(conn);
		controller.isPause=true;
		finish();
	}
	


	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
		
		backPlay.playSeven();
		Intent intent = new Intent(this,GameOver.class);
		intent.putExtra("time", GameTime.getTime(gaT.hour-gaT2.hour, gaT.minute-gaT2.minute, gaT.sec-gaT2.sec));
		Log.d("TAG=>",intent.getStringExtra("time")+"");
		intent.putExtra("score", ground.getScore()+"");
		startActivity(intent);
		finish();
	}

	
	
	
	
}
