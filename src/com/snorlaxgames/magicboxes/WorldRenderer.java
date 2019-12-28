package com.snorlaxgames.magicboxes;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.GameObject;
import com.badlogic.androidgames.framework.gl.Animation;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGraphics;

public class WorldRenderer {
	static final float FRUSTUM_WIDTH = 17;
	static final float FRUSTUM_HEIGHT = 11.3f;
	GLGraphics glGraphics;
	World world;
	Camera2D cam;
	SpriteBatcher batcher;
	
	float offsetHorr;
	float offsetVert;
	public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world){
		this.glGraphics = glGraphics;
		this.world = world;
		this.batcher =batcher;
		cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		
		offsetHorr = FRUSTUM_WIDTH/7;
		offsetVert = FRUSTUM_HEIGHT/2 - world.wall.bounds.height/2;
	}
	public void render(){
		cam.setViewportAndMatrices();
		renderObjects();
	}
	public void renderObjects(){
		GL10 gl = glGraphics.getGL();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		renderWall();
		renderHoles();
		renderPerson();
		renderBoxes();
		renderSubwalls();
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}
	private void renderWall(){
		//left,right, y
		float xLeft = offsetHorr;
		float xRight = xLeft + world.wall.bounds.width;
		float yVer = FRUSTUM_HEIGHT/2;
		
		
		float yBottom = offsetVert;
		float yTop = yBottom + world.wall.bounds.height;
		float xHorr = offsetHorr + world.wall.bounds.width/2;
		
		batcher.drawSprite(xHorr, yVer,world.wall.bounds.width, world.wall.bounds.height, Assets.floor);
		batcher.drawSprite(xLeft, yVer, 1, world.wall.bounds.height, Assets.wallVert);
		batcher.drawSprite(xRight, yVer, 1, world.wall.bounds.height, Assets.wallVert);
		batcher.drawSprite(xHorr, yTop, world.wall.bounds.width, 1, Assets.wallHorr);
		batcher.drawSprite(xHorr, yBottom, world.wall.bounds.width, 1, Assets.wallHorr);
	}
	private void renderHoles(){
		int len = world.holes.size();
		for(int i=0;i<len;i++){
			GameObject hole = world.holes.get(i);
			batcher.drawSprite(hole.position.x + offsetHorr, hole.position.y + offsetVert,
					1, 1, Assets.hole);
		}
	}
	private void renderPerson(){
		TextureRegion keyFrame;
		if(world.person.direction == Person.LEFT || world.person.direction == Person.RIGHT)
			keyFrame = Assets.personR;
		else
			keyFrame = Assets.personU;
		
		float sideHorr = (world.person.direction==Person.LEFT) ? -1:1;
		float sideVert = (world.person.direction == Person.DOWN) ? -1:1;
		batcher.drawSprite(world.person.position.x + offsetHorr, world.person.position.y + offsetVert,
				sideHorr*1, sideVert*1, keyFrame);
	}
	private void renderBoxes(){
		int len = world.boxes.size();
		for(int i=0;i<len;i++){
			Box box = world.boxes.get(i);
			TextureRegion keyFrame = (box.state == Box.BOX_STATE_NONTICK)?Assets.box:Assets.boxOk;
			batcher.drawSprite(box.position.x + offsetHorr, box.position.y + offsetVert,
					1, 1, keyFrame);
		}
	}
	private void renderSubwalls(){
		int len = world.subWalls.size();
		for(int i=0;i<len;i++){
			GameObject subWall = world.subWalls.get(i);
			batcher.drawSprite(subWall.position.x + offsetHorr, subWall.position.y + offsetVert,
					1, 1, Assets.wallHorr);
		}
	}
}












