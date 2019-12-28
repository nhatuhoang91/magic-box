package com.badlogic.androidgames.framework.impl;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.view.MotionEvent;
import android.view.View;

import com.badlogic.androidgames.framework.Input.MyTouchEvent;
import com.badlogic.androidgames.framework.Pool;
import com.badlogic.androidgames.framework.Pool.PoolObjectFactory;

//@TargetApi(5)
public class MultiTouchHandler implements TouchHandler{

	private static final int MAX_TOUCHPOINTS = 10;
	boolean[] isTouch = new boolean[MAX_TOUCHPOINTS];
	int[] touchX = new int[MAX_TOUCHPOINTS];
	int[] touchY = new int[MAX_TOUCHPOINTS];
	int[] id = new int[MAX_TOUCHPOINTS];
	
	Pool<MyTouchEvent> touchEventPool;
	List<MyTouchEvent> touchEvent = new ArrayList<MyTouchEvent>();
	List<MyTouchEvent> touchEventBuffer = new ArrayList<MyTouchEvent>();
	
	float scaleX;
	float scaleY;
	
	public MultiTouchHandler(View view, float scaleX, float scaleY)
	{
		PoolObjectFactory<MyTouchEvent> factory = new PoolObjectFactory<MyTouchEvent>() {

			@Override
			public MyTouchEvent createObject() {
				// TODO Auto-generated method stub
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
		synchronized (this) {
			int action = event.getAction() & MotionEvent.ACTION_MASK;
			int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) 
					>> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
			int pointerCount = event.getPointerCount();
			MyTouchEvent myTouchEvent;
			for(int i=0 ; i < MAX_TOUCHPOINTS ; i++)
			{
				if(i >= pointerCount)
				{
					isTouch[i]=false;
					id[i] =-1;
					
					continue;
				}
				int pointerId = event.getPointerId(i);
				
				if(event.getAction() != MotionEvent.ACTION_MOVE && i!=pointerIndex)
				{
					continue;
				}
				
				switch (action) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:
					myTouchEvent = touchEventPool.newObject();
					myTouchEvent.type = MyTouchEvent.TOUCH_DOWN;
					myTouchEvent.pointer = pointerId;
					myTouchEvent.x=touchX[i]= (int)(event.getX(i)*scaleX);
					myTouchEvent.y=touchY[i]=(int)(event.getY(i)*scaleY);
					isTouch[i] = true;
					id[i] = pointerId;
					touchEventBuffer.add(myTouchEvent);
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
				case MotionEvent.ACTION_CANCEL:
					myTouchEvent = touchEventPool.newObject();
					myTouchEvent.type = MyTouchEvent.TOUCH_UP;
					myTouchEvent.pointer = pointerId;
					myTouchEvent.x = touchX[i]=(int)(event.getX() * scaleX);
					myTouchEvent.y = touchY[i]= (int)(event.getY()*scaleY);
					isTouch[i] = false;
					id[i] =-1;
					
					touchEventBuffer.add(myTouchEvent);
					break;
				case MotionEvent.ACTION_MOVE:
					myTouchEvent = touchEventPool.newObject();
					myTouchEvent.type = MyTouchEvent.TOUCH_DRAGGED;
					myTouchEvent.pointer = pointerId;
					myTouchEvent.x = touchX[i] = (int)(event.getX() * scaleX);
					myTouchEvent.y = touchY[i]= (int)(event.getY()*scaleY);
					isTouch[i] = true;
					id[i] = pointerId;
					touchEventBuffer.add(myTouchEvent);
					break;
				}
			}
			return true;
		}
	}

	@Override
	public boolean isTouchDown(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if(index <0 || index >=MAX_TOUCHPOINTS)
				return false;
			else return isTouch[index];
		}
	}

	@Override
	public int getTouchX(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if(index <0 || index >=MAX_TOUCHPOINTS)
				return 0;
			else return touchX[index];
		}
	}

	@Override
	public int getTouchY(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if(index <0 || index >=MAX_TOUCHPOINTS)
				return 0;
			else return touchY[index];
		}
	}

	@Override
	public List<MyTouchEvent> getTouchEvents() {
		synchronized (this) {
		 int len = touchEvent.size();
		 for(int i=0;i<len;i++)
		 {
			 touchEventPool.free(touchEvent.get(i));
		 }
		 touchEvent.clear();
		 touchEvent.addAll(touchEventBuffer);
		 touchEventBuffer.clear();
		  return touchEvent;
		}
	}
	
	private int getIndex(int pointerId)
	{
		for(int i=0;i<MAX_TOUCHPOINTS;i++)
		{
			if(id[i] ==pointerId)
			{
				return i;
			}
		}
		return -1;
	}
}

