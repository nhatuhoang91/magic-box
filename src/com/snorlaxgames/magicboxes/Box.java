package com.snorlaxgames.magicboxes;

import com.badlogic.androidgames.framework.GameObject;

public class Box extends GameObject{
	public static final int BOX_STATE_NONTICK = 0;
	public static final int BOX_STATE_TICK = 1;
	int state;
	public Box(float x, float y, float width, float height) {
		super(x, y, width, height);
		state =BOX_STATE_NONTICK;
	}
}
