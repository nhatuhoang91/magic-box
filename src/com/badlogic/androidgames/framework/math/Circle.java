package com.badlogic.androidgames.framework.math;

public class Circle {
	public Vector2 center = new Vector2();
	public float radius;
	
	public Circle(float x, float y, float radius){
		center.set(x, y);
		this.radius = radius;
	}
}
