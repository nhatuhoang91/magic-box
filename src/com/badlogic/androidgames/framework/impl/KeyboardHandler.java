package com.badlogic.androidgames.framework.impl;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.androidgames.framework.Input.MyKeyEvent;
import com.badlogic.androidgames.framework.Pool;
import com.badlogic.androidgames.framework.Pool.PoolObjectFactory;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

public class KeyboardHandler implements OnKeyListener{

	boolean[] pressedKeys = new boolean[128];
	Pool<MyKeyEvent> keyEventPool;
	List<MyKeyEvent> keyEventsBuffer = new ArrayList<MyKeyEvent>();
	List<MyKeyEvent> keyEvents = new ArrayList<MyKeyEvent>();
	
	public KeyboardHandler(View view)
	{
		PoolObjectFactory<MyKeyEvent> factory = new PoolObjectFactory<MyKeyEvent>() {

			@Override
			public MyKeyEvent createObject() {
				// TODO Auto-generated method stub
				return new MyKeyEvent();
			}
		};
		
		keyEventPool = new Pool<MyKeyEvent>(factory, 100);
		view.setOnKeyListener(this);
		view.setFocusableInTouchMode(true);
		view.requestFocus();
	}
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(event.getAction() == KeyEvent.ACTION_MULTIPLE)
			return false;
		if(keyCode == KeyEvent.KEYCODE_BACK ||
				keyCode == KeyEvent.KEYCODE_VOLUME_DOWN|| 
				keyCode == KeyEvent.KEYCODE_VOLUME_UP) //note: Back button !!!
			return false;
		
		synchronized (this) {
			MyKeyEvent myKeyEvent = keyEventPool.newObject();
			myKeyEvent.keycode = keyCode;
			myKeyEvent.keycode = (char)event.getUnicodeChar();
			
			if(event.getAction() == KeyEvent.ACTION_DOWN)
			{
				myKeyEvent.type = MyKeyEvent.KEY_DOWN;
				if(keyCode >= 0 && keyCode <= 127)
					pressedKeys[keyCode] = true;
			}
			
			if(event.getAction() == KeyEvent.ACTION_UP)
			{
				myKeyEvent.type = MyKeyEvent.KEY_UP;
				if(keyCode >= 0 && keyCode <= 127)
					pressedKeys[keyCode] = false;
			}
			
			keyEventsBuffer.add(myKeyEvent);
		}
		return true;
	}
	
	public boolean isKeyPressed(int keyCode)
	{
		if(keyCode < 0 || keyCode > 127)
		{
			return false;
		}
		return pressedKeys[keyCode];
	}
	
	public List<MyKeyEvent> getKeyEvents()
	{
		synchronized (this) {
			int len = keyEvents.size();
			for(int i =0;i<len;i++)
			{
				keyEventPool.free(keyEvents.get(i));
			}
			keyEvents.clear();
			keyEvents.addAll(keyEventsBuffer);
			keyEventsBuffer.clear();
			return keyEvents;
		}
	}
}

