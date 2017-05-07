package com.example.base;


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

public class Ground {

	private long score=0;
	public Bitmap groundImg;
	private int status;
	private int[] delete={-1,-1,-1,-1};
	public int[][] obstacles = new int[GameView.Cell_heightCount][GameView.Cell_widthCount];

	public boolean accept(Shape shape){
		System.out.println("Ground");
		
		for(int i=0;i<4;i++){
			
			for(int j=0;j<4;j++){
				
				if(shape.isMemeber(i, j, shape.status)){
					
					obstacles[shape.top+i][shape.left+j]=1;

				}
			}
			
		}
		isDelete();
		boolean flag=true;
		for(int i=0;i<GameView.Cell_widthCount;i++){
			
			if(obstacles[0][i]==1){
				flag=false;
			}
			
		}
		return flag;
		
	}
	
	public void doDelete(){
		int count=0;
		for(int k=0;k<delete.length;k++){
			if(delete[k]!=-1){
				for(int i=delete[k];i>0;i--){
					

					for(int j=0;j<GameView.Cell_widthCount;j++){
					
						obstacles[i][j]=obstacles[i-1][j];
					}
					
				}
				
				
				count++;
				
			}
			
		}
		
		
		
		for(int i=0;i<GameView.Cell_widthCount;i++){
			obstacles[0][i]=0;
		}
		score+=50*(Math.pow(2, count-1));
	}
	
	public void isDelete(){
		
		for(int i=0;i<4;i++){
			delete[i]=-1;
		}
		int count=0;
		for(int i=0;i<obstacles.length;i++){
			boolean flage=true;
			for(int j=0;j<obstacles[i].length;j++){
				
				if(obstacles[i][j]==0){
					
					flage=false;
				}
			}
			
			if(flage){
				delete[count++]=i;
				
			}

		}
		if(count>0){
			doDelete();
		}
	}
	
	
	
	
	
	
	public void drawMe(Canvas canvas,Resources res){
		Paint paint = new Paint();
		paint.setColor(Color.CYAN);
		
		Paint paint2 = new Paint();
		paint2.setColor(Color.BLACK);
		paint2.setStyle(Style.STROKE);
		paint2.setStrokeWidth(1);
		groundImg = BitmapFactory.decodeResource(res, R.drawable.star_y);
		
		
		for(int i=0;i<GameView.Cell_heightCount;i++){
			
			for(int j=0;j<GameView.Cell_widthCount;j++){
				if(Constant.GridPaint){
				canvas.drawRect(j*GameView.Cell_Size, i*GameView.Cell_Size, 
						(j+1)*GameView.Cell_Size, (i+1)*GameView.Cell_Size, paint2);
				}
				if(obstacles[i][j]==1)	{	
					
			//		canvas.drawRect(j*GameView.Cell_Size, i*GameView.Cell_Size, (j+1)*GameView.Cell_Size, (i+1)*GameView.Cell_Size, paint2);
			
				
					Rect src = new Rect();
					Rect dst = new Rect();
					src.left=0;
					src.top=0;
					src.right =groundImg.getWidth();
					src.bottom = groundImg.getHeight();
					
					dst.left=j*GameView.Cell_Size;
					dst.top=i*GameView.Cell_Size;
					dst.right =(j+1)*GameView.Cell_Size;
					dst.bottom = (i+1)*GameView.Cell_Size;
					
			canvas.drawBitmap(groundImg, src, dst, null);
					
					
					
				}
			}
			
		}
		
	}
	
	

	public synchronized boolean isMove(Shape shape,int action){
	
		int top = shape.getTop();
		int left = shape.getLeft();
		status = shape.status;
		switch(action){
		
		case Shape.LEFT:
			left--;
			
			break;
			
		case Shape.RIGHT:
			left++;
			
			break;
			
		case Shape.DOWN:
			top++;
			
			break;
			
		case Shape.ROTATE:
			
			this.status = (status+1)%shape.getBody().length;
		
		}
		
		for(int i=0;i<4;i++){
			
			for(int j=0;j<4;j++){
				
				if(shape.isMemeber(i,j,status)){
					
					if(left+j<=-1||left+j>=GameView.Cell_widthCount||top+i>=GameView.Cell_heightCount||obstacles[top+i][left+j]==1){
						
				
						return false;
					}
					
				}
				
			}
		}
		
		return true;
	}
	
	
	public long getScore() {
		return score;
	}
	
	
	
}
