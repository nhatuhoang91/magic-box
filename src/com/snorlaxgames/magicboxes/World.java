package com.snorlaxgames.magicboxes;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.androidgames.framework.GameObject;

public class World {
	public static final float WORLD_WIDTH = 20;
	public static final float WORLD_HEIGHT = 15;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	
	public  Person person;
	public  GameObject wall;
	public  List<GameObject> subWalls;
	public  List<Box> boxes;
	public  List<GameObject> holes;
	public int state;
	public World(){
		person = new Person(Settings.person.position.x, Settings.person.position.y);
		person.direction = Settings.person.direction;
		wall = new GameObject(Settings.wall.position.x, Settings.wall.position.y,
				Settings.wall.bounds.width, Settings.wall.bounds.height);
		//wall = new GameObject(3.5f, 4.5f, 7, 9);
		subWalls = new ArrayList<GameObject>();
		boxes = new ArrayList<Box>();
		holes = new ArrayList<GameObject>();
		subWalls = Settings.subWalls;
		boxes = Settings.boxes;
		holes = Settings.holes;
		
		state = WORLD_STATE_RUNNING;
	}
	public void goLeft(){
		if(person.position.x==1.0f){
			return;
		}
		int len = boxes.size();
		for(int i=0;i<len;i++){
			if(person.position.x < boxes.get(i).position.x 
					|| person.position.y != boxes.get(i).position.y)
				continue;
			if(person.position.x == boxes.get(i).position.x+1)
			{
				if(checkBoxLeft(boxes.get(i))){
					person.position.x-=1;
					person.direction = Person.LEFT;
					boxes.get(i).position.x-=1;
					if(checkBoxInHole(boxes.get(i)))
						boxes.get(i).state = Box.BOX_STATE_TICK;
					else
						boxes.get(i).state = Box.BOX_STATE_NONTICK;
					return;
				}else
				{
					return;
				}
			}
		}
		len = subWalls.size();
		for(int i=0;i<len;i++){
			if(person.position.x < subWalls.get(i).position.x
					|| person.position.y != subWalls.get(i).position.y)
				continue;
			if(person.position.x == subWalls.get(i).position.x+1)
			{
				return;
			}
		}
		person.position.x-=1;
		person.direction=Person.LEFT;
	}
	public void goRight(){
		if(person.position.x == wall.bounds.width -1.0f){
			return;
		}
		int len = boxes.size();
		for(int i=0;i<len;i++){
			if(person.position.x > boxes.get(i).position.x 
					|| person.position.y != boxes.get(i).position.y)
				continue;
			if(person.position.x == boxes.get(i).position.x-1)
			{
				if(checkBoxRight(boxes.get(i))){
					person.position.x+=1;
					person.direction = Person.RIGHT;
					boxes.get(i).position.x+=1;
					if(checkBoxInHole(boxes.get(i)))
						boxes.get(i).state = Box.BOX_STATE_TICK;
					else
						boxes.get(i).state = Box.BOX_STATE_NONTICK;
					return;
				}else{
					return;
				}
			}
		}
		
		len = subWalls.size();
		for(int i=0;i<len;i++){
			if(person.position.x > subWalls.get(i).position.x
					|| person.position.y != subWalls.get(i).position.y)
				continue;
			if(person.position.x == subWalls.get(i).position.x-1)
			{
				return;
			}
		}
		person.position.x+=1;
		person.direction=Person.RIGHT;
	}
	public void goUp(){
		if(person.position.y == wall.bounds.height -1.0f){
			return;
		}
		int len = boxes.size();
		for(int i=0;i<len;i++){
			if(person.position.y > boxes.get(i).position.y
					|| (person.position.x != boxes.get(i).position.x))
				continue;
			if(person.position.y == boxes.get(i).position.y-1)
			{
				if(checkBoxUp(boxes.get(i))){
					person.position.y+=1;
					person.direction = Person.UP;
					boxes.get(i).position.y+=1;
					if(checkBoxInHole(boxes.get(i)))
						boxes.get(i).state = Box.BOX_STATE_TICK;
					else
						boxes.get(i).state = Box.BOX_STATE_NONTICK;
					return;
				}else{
					return;
				}
			}
		}
		
		len = subWalls.size();
		for(int i=0;i<len;i++){
			if(person.position.y > subWalls.get(i).position.y
					|| person.position.x != subWalls.get(i).position.x)
				continue;
			if(person.position.y == subWalls.get(i).position.y-1)
			{
				return;
			}
		}
		person.position.y+=1;
		person.direction=Person.UP;
	}
	public void goDown(){
		if(person.position.y == 1.0f){
			return;
		}
		int len = boxes.size();
		for(int i=0;i<len;i++){
			if(person.position.y < boxes.get(i).position.y
					|| person.position.x != boxes.get(i).position.x)
				continue;
			if(person.position.y == boxes.get(i).position.y+1)
			{
				if(checkBoxDown(boxes.get(i))){
					person.position.y-=1;
					person.direction = Person.DOWN;
					boxes.get(i).position.y-=1;
					if(checkBoxInHole(boxes.get(i)))
						boxes.get(i).state = Box.BOX_STATE_TICK;
					else
						boxes.get(i).state = Box.BOX_STATE_NONTICK;
					return;
				}else{
					return;
				}
			}
		}
		len = subWalls.size();
		for(int i=0;i<len;i++){
			if(person.position.y < subWalls.get(i).position.y
					|| person.position.x != subWalls.get(i).position.x)
				continue;
			if(person.position.y == subWalls.get(i).position.y+1)
			{
				return;
			}
		}
		person.position.y-=1;
		person.direction=Person.DOWN;
	}
	//Ham kiem tra co day box di dc ko
	private boolean checkBoxLeft(Box box){
		if(box.position.x == 1.0f)//neu box o ke ben wall thi ko dc
			return false;
		int len = boxes.size();
		for(int i=0;i<len;i++){
			if(box.position.x <= boxes.get(i).position.x
					|| box.position.y != boxes.get(i).position.y)
				continue;
			if(box.position.x == boxes.get(i).position.x+1)
			{
				return false;
			}
		}
		len = subWalls.size();
		for(int i=0;i<len;i++){
			if(box.position.x < subWalls.get(i).position.x
					|| box.position.y != subWalls.get(i).position.y){
				continue;
			}
			if(box.position.x == subWalls.get(i).position.x+1){
				return false;
			}
		}
		return true;
	}
	private boolean checkBoxRight(Box box){
		if(box.position.x == wall.bounds.width-1.0f)//neu box o ke ben wall thi ko dc
			return false;
		int len = boxes.size();
		for(int i=0;i<len;i++){
			if(box.position.x >= boxes.get(i).position.x
					|| box.position.y != boxes.get(i).position.y)
				continue;
			if(box.position.x == boxes.get(i).position.x-1)
			{
				return false;
			}
		}
		len = subWalls.size();
		for(int i=0;i<len;i++){
			if(box.position.x > subWalls.get(i).position.x
					|| box.position.y != subWalls.get(i).position.y){
				continue;
			}
			if(box.position.x == subWalls.get(i).position.x-1){
				return false;
			}
		}
		return true;
	}
	private boolean checkBoxUp(Box box){
		if(box.position.y == wall.bounds.height-1.0f)//neu box o ke ben wall thi ko dc
			return false;
		int len = boxes.size();
		for(int i=0;i<len;i++){
			if(box.position.y >= boxes.get(i).position.y
					|| box.position.x != boxes.get(i).position.x)
				continue;
			if(box.position.y == boxes.get(i).position.y-1)
			{
				return false;
			}
		}
		len = subWalls.size();
		for(int i=0;i<len;i++){
			if(box.position.y > subWalls.get(i).position.y
					|| box.position.x != subWalls.get(i).position.x){
				continue;
			}
			if(box.position.y == subWalls.get(i).position.y-1){
				return false;
			}
		}
		return true;
	}
	private boolean checkBoxDown(Box box){
		if(box.position.y == 1.0f)//neu box o ke ben wall thi ko dc
			return false;
		int len = boxes.size();
		for(int i=0;i<len;i++){
			if(box.position.y <= boxes.get(i).position.y
					|| box.position.x != boxes.get(i).position.x)
				continue;
			if(box.position.y == boxes.get(i).position.y+1)
			{
				return false;
			}
		}
		len = subWalls.size();
		for(int i=0;i<len;i++){
			if(box.position.y < subWalls.get(i).position.y
					|| box.position.x != subWalls.get(i).position.x){
				continue;
			}
			if(box.position.y == subWalls.get(i).position.y+1){
				return false;
			}
		}
		return true;
	}
	private boolean checkBoxInHole(Box box){
		int len = holes.size();
		for(int i=0;i<len;i++){
			if(box.position.x == holes.get(i).position.x 
					&& box.position.y == holes.get(i).position.y)
				return true;
		}
		return false;
	}
	
	public void checkGameNextLevel(){
		int len = boxes.size();
		int lenHoles  = holes.size();
		boolean isOk = false;
		for(int i=0;i<lenHoles;i++){
			for(int j=0;j<len;j++){
				if(boxes.get(j).position.x==holes.get(i).position.x
						&& boxes.get(j).position.y==holes.get(i).position.y){
					isOk = true;
					break;
				}else
					isOk = false;
			}
			if(!isOk){
				state = WORLD_STATE_RUNNING;
				return;
			}
		}
		state= WORLD_STATE_NEXT_LEVEL;
	}
}
