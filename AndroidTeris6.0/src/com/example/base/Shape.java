package com.example.base;

import com.example.Listenner.ComeBackAdapter;
import com.example.androidteris.GameView;
import com.example.androidteris.R;
import com.example.constant.Constant;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;

public class Shape {
	
	public int[][][] body;
	
	public ComeBackAdapter listener;
	public int top=0;
	public int left=4;
	public int status=0;
	
	public static final int ROTATE = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;
	public Bitmap shapeImg;
	
	

	public void moveLeft(){
		
		Log.d("TAG===°∑", "moveLeft");
		left--;
	};
	
	public void moveRight(){
		Log.d("TAG===°∑", "moveRight");
		left++;
	};
	
	public void moveDown(){
		
		Log.d("TAG===°∑", "moveDown");
		
		top++;
	}
	
	public void moveUp(){
		Log.d("TAG===°∑", "moveUp");
		status = (status+1)%body.length;

	}
	
	public void drawMe(Canvas canvas,Resources res){
		
		Paint paint = new Paint();
		//paint.setColor(Color.BLUE);
		Paint paint2 = new Paint();
		paint2.setColor(Color.BLACK);
		paint2.setStyle(Style.STROKE);
		paint2.setStrokeWidth(8);
		
		shapeImg = BitmapFactory.decodeResource(res, R.drawable.star_b);
		
		for(int i=0;i<4;i++){
			
			for(int j=0 ;j<4;j++){
				if(body[status][i][j]==1){
					
				//	canvas.drawRect((left+j)*GameView.Cell_Size, (top+i)*GameView.Cell_Size, 
				//			(left+j+1)*GameView.Cell_Size, (top+i+1)*GameView.Cell_Size, paint2);
					
			/*	canvas.drawRect((left+j)*GameView.Cell_Size, (top+i)*GameView.Cell_Size, 
						(left+j+1)*GameView.Cell_Size, (top+i+1)*GameView.Cell_Size, paint);
			*/
			
					Rect src = new Rect();
					Rect dst = new Rect();
					src.left=0;
					src.top=0;
					src.right =shapeImg.getWidth();
					src.bottom = shapeImg.getHeight();
					
					dst.left=(left+j)*GameView.Cell_Size;
					dst.top=(top+i)*GameView.Cell_Size;
					dst.right =(left+j+1)*GameView.Cell_Size;
					dst.bottom = (top+i+1)*GameView.Cell_Size;
					
			canvas.drawBitmap(shapeImg, src, dst, null);
			
				}
				
			}
			
			
		}

	}
	
	
	public synchronized boolean isMemeber(int x,int y,int flag){
		Log.d("TAG===>", "x "+x+"y "+y+" "+flag+"" );
		return body[flag][x][y]==1;
	}
	
	
	public Shape(){
		
		startThread();

	}
	
   
	public void startThread(){
		
		
		new Thread(new ShapeMove()).start();
		Log.d("TAG===>","”Œœ∑÷ÿ∆Ù");
	}

	class ShapeMove implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			while(listener.isEnd(Shape.this)){
				
				moveDown();
				
				listener.refreshDisplay(Shape.this);
				try {
					Thread.sleep(Constant.SPEED);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		}
		
	}
	
	
	public void setAdapter(ComeBackAdapter listener){
		
		this.listener=listener;
		
	}
	
	
	public void setBody(int[][][] body){
		
		
		this.body = body;
		
	}
	
	public void setStatus(int status){
		this.status = status;
		
	}
	
	
	public int getTop(){
		
		return top;
	}
	public int getLeft(){
		
		return left;
	}
    public int[][][] getBody(){
		
		return body;
		
	}
	
	
	
	
}
