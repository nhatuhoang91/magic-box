package com.snorlaxgames.magicboxes;

import com.badlogic.androidgames.framework.GameObject;

public class Person extends GameObject{
	public static final float PERSON_WIDTH = 1;
	public static final float PERSON_HEIGHT = 1;
	//public static final int PERSON_STATE_NONMOVE = 0;
	//public static final int PERSON_STATE_MOVE = 1;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	
	//int state;
	int direction;
	//float stateTime;
	public Person(float x, float y) {
		super(x, y, PERSON_WIDTH, PERSON_HEIGHT);
		direction=LEFT;
		//state=PERSON_STATE_NONMOVE;
		//stateTime=0;
	}
	/*
	public void move(float deltaTime){
		if(state!=PERSON_STATE_MOVE)
			state = PERSON_STATE_MOVE;
		stateTime += deltaTime;
	}
	public void notMove(){
		if(state!=PERSON_STATE_NONMOVE)
			state = PERSON_STATE_NONMOVE;
		stateTime=0;
	}*/
}
