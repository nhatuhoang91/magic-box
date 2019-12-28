package com.badlogic.androidgames.framework.impl;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import com.badlogic.androidgames.framework.Input.MyTouchEvent;
import com.badlogic.androidgames.framework.Pool;
import com.badlogic.androidgames.framework.Pool.PoolObjectFactory;

public class SingleTouchHandler implements TouchHandler{

	boolean isTouch;
	int touchX;
	int touchY;
	Pool<MyTouchEvent> touchEventPool;
	List<MyTouchEvent> touchEvents = new ArrayList<MyTouchEvent>();
	List<MyTouchEvent> touchEventsBuffer = new ArrayList<MyTouchEvent>();
	float scaleX;
	float scaleY;
	
	public SingleTouchHandler(View view, float ScaleX, float ScaleY)
	{
		PoolObjectFactory<MyTouchEvent> factory = new PoolObjectFactory<MyTouchEvent>() {

			@Override
			public MyTouchEvent createObject() {
				return new MyTouchEvent();
			}
		};
		
		touchEventPool = new Pool<MyTouchEvent>(factory, 100);
		view.setOnTouchListener(this);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		MyTouchEvent myTouchEvent = touchEventPool.newObject();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			myTouchEvent.type = MyTouchEvent.TOUCH_DOWN;
			isTouch = true;
			break;
		case MotionEvent.ACTION_MOVE:
			myTouchEvent.type = MyTouchEvent.TOUCH_DRAGGED;
			isTouch = true;
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			myTouchEvent.type = MyTouchEvent.TOUCH_UP;
			isTouch=false;
			break;
		}
		
		myTouchEvent.x = touchX = (int)(event.getX() * scaleX);
		myTouchEvent.y=touchY = (int)(event.getY() * scaleY);
		
		touchEventsBuffer.add(myTouchEvent);
		
		return true;
	}

	@Override
	public boolean isTouchDown(int pointer) {
		synchronized (this) {
			if(pointer == 0)
				return isTouch;
			else return false;
		}
	}

	@Override
	public int getTouchX(int pointer) {
		synchronized (this) {
			return touchX;
		}
	}

	@Override
	public int getTouchY(int pointer) {
		synchronized (this) {
			return touchY;
		}
	}

	@Override
	public List<MyTouchEvent> getTouchEvents() {
		synchronized (this) {
			int len = touchEvents.size();
			for(int i = 0; i< len;i++)
			{
				touchEventPool.free(touchEvents.get(i));
			}
			touchEvents.clear();
			touchEvents.addAll(touchEventsBuffer);
			touchEventsBuffer.clear();
			return touchEvents;
		}
	}
}

