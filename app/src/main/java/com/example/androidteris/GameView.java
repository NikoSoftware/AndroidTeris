package com.example.androidteris;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


import android.view.View;
import android.view.View.OnTouchListener;

import com.example.base.Ground;
import com.example.base.Shape;
import com.example.constant.Constant;

public class GameView extends View{

    public Shape shape;
    public Ground ground;
	public static int Cell_Size;
	public static int Cell_widthCount;
	public static int Cell_heightCount;

	
	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		Cell_widthCount=Constant.Cell_widthCount;
		Cell_heightCount=Constant.Cell_heightCount;
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Cell_widthCount=Constant.Cell_widthCount;
		Cell_heightCount=Constant.Cell_heightCount;
	}

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Cell_widthCount=Constant.Cell_widthCount;
		Cell_heightCount=Constant.Cell_heightCount;
	}
	
	
	public void display(Shape shape,Ground ground){
		
		this.shape = shape;	
		this.ground = ground;
		invalidate();
	}
	
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//if(Cell_Size==0){
			Init();
			
//		}
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(8);
		canvas.drawRect(0, 0, getWidth(), Cell_heightCount*Cell_Size+2, paint);
		
		Resources res = getResources();
		
		
		if(shape!=null&&ground!=null){
		shape.drawMe(canvas,res);
		ground.drawMe(canvas,res);
		}
		
	}
	
	
	public void Init(){
		int Width =getWidth();
		int Height = getHeight();
		Cell_Size =Width/Cell_widthCount;
		
	//	 Cell_heightCount=Height/Cell_Size;
	}

	 

	

	
}
