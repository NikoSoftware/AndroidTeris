package com.example.controller;



import android.util.Log;
import android.view.View;

import com.example.Listenner.ComeBackAdapter;
import com.example.base.Ground;
import com.example.base.Shape;
import com.example.base.ShapeFactory;

public class Controller implements ComeBackAdapter {

	  public Shape shape;
	  public Ground ground;
	  public ShapeFactory shapeFactory;
	  public View gameView;
	  
	  
	  public ComeBackAdapter mainActivity;
	  public boolean isPause=false;

	
	@Override
	public void refreshDisplay(Shape shape) {
		// TODO Auto-generated method stub
		
		mainActivity.refreshDisplay(shape,ground);
		
	}
	
	public void startGame(){
		
		shape=shapeFactory.getShape(Controller.this);
		
	}
	
	
	public Controller(Ground ground,ShapeFactory shapeFactory,ComeBackAdapter mainActivity){
		
		this.ground = ground;
		this.shapeFactory = shapeFactory;
		this.mainActivity = mainActivity;
	
	}

	@Override
	public void refreshDisplay(Shape shape, Ground ground) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEnd(Shape eshape) {
		// TODO Auto-generated method stub
		Log.d("TAG===》", "ground.isMove?");
		if(ground.isMove(eshape, Shape.DOWN)&&!isPause){
			
			return true;
		}
		
		if(!isPause){
			if(ground.accept(eshape)){
				
			this.shape = shapeFactory.getShape(Controller.this);
			
			}else{
				//JOptionPane.showConfirmDialog(null, "游戏结束", "", JOptionPane.YES_NO_OPTION);
				mainActivity.refreshDisplay(shape,ground);
				mainActivity.gameOver();
				
				//Log.d("TAG===>", "游戏结束");
			}
		}
		
		return false;
	}
	
	public void beginThread(){
		isPause=false;
		shape.startThread();
		
		
	}

	@Override
	public void gameOver() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	

}
