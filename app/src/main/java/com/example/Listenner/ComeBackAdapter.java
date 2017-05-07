package com.example.Listenner;

import com.example.base.Ground;
import com.example.base.Shape;

public interface ComeBackAdapter {

	public void refreshDisplay(Shape shape);
	public void refreshDisplay(Shape shape,Ground ground);
	public boolean isEnd(Shape eshape);
	public void gameOver();
}
