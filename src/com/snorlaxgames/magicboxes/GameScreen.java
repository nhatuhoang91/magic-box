package com.snorlaxgames.magicboxes;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.MyTouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class GameScreen extends GLScreen{
	static final int GAME_RUNNING =0;
	static final int GAME_PAUSE =1;
	static final int GAME_WIN =2;
	static final int GAME_NEXT_LEVEL=3;
	
	int state;
	Camera2D guiCam;
	Vector2 touchPoint;
	SpriteBatcher batcher;
	World world;
	WorldRenderer renderer;
	Rectangle leftBound;
	Rectangle rightBound;
	Rectangle upBound;
	Rectangle downBound;
	
	Rectangle pauseBound;
	Rectangle resumeBound;
	Rectangle resetBound;
	Rectangle quitBound;
	Rectangle testBound;
	
	boolean isLeftPressed = false;
	boolean isRightPressed = false;
	boolean isUpPressed = false;
	boolean isDownPressed = false;
	
	boolean isPausedPressed = false;
	boolean isResumePressed = false;
	boolean isResetPressed = false;
	boolean isQuitpressed = false;
	
	public GameScreen(Game game){
		super(game);
		state = GAME_RUNNING;
		
		guiCam = new Camera2D(glGraphics, 480, 320);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics, 100);
		world = new World();
		renderer = new WorldRenderer(glGraphics,batcher,world);
	
		leftBound = new Rectangle(480-20-42-46-42, 20+42, 42, 46); //(480-20-42-46-42, 20+42, 42, 46);
		rightBound = new Rectangle(480-20-42, 20+42, 42, 46);
		upBound = new Rectangle(480-20-42-46, 20+42+46, 46, 42);
		downBound = new Rectangle(480-20-42-46,20, 46, 42);
		
		testBound = new Rectangle(480-20-42-46-42, 0, 42, 46);
		
		pauseBound = new Rectangle(480-50-10, 320-50-5, 50, 50);
		resumeBound = new Rectangle(240-160/2, 160+16+32, 160, 32);
		resetBound = new Rectangle(240-128/2, 160-16, 128, 32);
		quitBound = new Rectangle(240-128/2, 160-16-64, 128, 32);
		
	}
	@Override
	public void update(float deltaTime) {
		switch(state){
		case GAME_PAUSE:
			updatePaused();
			break;
		case GAME_RUNNING:
			updateRunning();
			break;
		case GAME_WIN:
			updateWin();
			break;
		case GAME_NEXT_LEVEL:
			updateNextLevel();
			break;
		}
	}
	
	private void updateRunning(){
		List<MyTouchEvent> touchEvents = game.getInput().getTouchEvent();
	
		int len = touchEvents.size();
		for(int i=0;i<len;i++){
			MyTouchEvent event = touchEvents.get(i);
			if(event.type == MyTouchEvent.TOUCH_DOWN){
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);
				if(OverlapTester.pointInRectangle(leftBound, touchPoint)){
					isLeftPressed = true;
					return;
				}
				if(OverlapTester.pointInRectangle(rightBound, touchPoint)){
					isRightPressed = true;
					return;
				}
				if(OverlapTester.pointInRectangle(upBound, touchPoint)){
					isUpPressed = true;
					return;
				}
				if(OverlapTester.pointInRectangle(downBound, touchPoint)){
					isDownPressed = true;
					return;
				}
				if(OverlapTester.pointInRectangle(pauseBound, touchPoint)){
					isPausedPressed = true;
					return;
				}
			}
			if(event.type == MyTouchEvent.TOUCH_UP){
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);
				if(OverlapTester.pointInRectangle(leftBound, touchPoint)){
					isLeftPressed = false;
					Assets.playSound(Assets.soundClick);
					world.goLeft();
					return;
				}
				if(OverlapTester.pointInRectangle(rightBound, touchPoint)){
					isRightPressed = false;
					Assets.playSound(Assets.soundClick);
					world.goRight();
					return;
				}
				if(OverlapTester.pointInRectangle(upBound, touchPoint)){
					isUpPressed = false;
					Assets.playSound(Assets.soundClick);
					world.goUp();
					return;
				}
				if(OverlapTester.pointInRectangle(downBound, touchPoint)){
					isDownPressed = false;
					Assets.playSound(Assets.soundClick);
					world.goDown();
					return;
				}
				if(OverlapTester.pointInRectangle(pauseBound, touchPoint)){
					isPausedPressed = false;
					Assets.playSound(Assets.soundClick);
					state =GAME_PAUSE;
					return;
				}
				
				if(OverlapTester.pointInRectangle(testBound, touchPoint)){
		
					Assets.playSound(Assets.soundClick);
					state =GAME_NEXT_LEVEL;
					return;
				}
			}
		}
		world.checkGameNextLevel();
		if(world.state == World.WORLD_STATE_RUNNING)
			state = GAME_RUNNING;
		if(world.state == World.WORLD_STATE_NEXT_LEVEL)
			state = GAME_WIN;
	}
	
	private void updatePaused(){
		List<MyTouchEvent> touchEvents = game.getInput().getTouchEvent();
		
		int len = touchEvents.size();
		for(int i=0;i<len;i++){
			MyTouchEvent event = touchEvents.get(i);
			if(event.type == MyTouchEvent.TOUCH_DOWN){
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);
				if(OverlapTester.pointInRectangle(resumeBound, touchPoint)){
					isResumePressed = true;
					return;
				}
				if(OverlapTester.pointInRectangle(resetBound, touchPoint)){
					isResetPressed = true;
					return;
				}
				if(OverlapTester.pointInRectangle(quitBound, touchPoint)){
					isQuitpressed = true;
					return;
				}
			}
			if(event.type == MyTouchEvent.TOUCH_UP){
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);
				if(OverlapTester.pointInRectangle(resumeBound, touchPoint)){
					isResumePressed = false;
					Assets.playSound(Assets.soundClick);
					state =GAME_RUNNING;
					return;
				}
				if(OverlapTester.pointInRectangle(resetBound, touchPoint)){
					isResetPressed = false;
					Assets.playSound(Assets.soundClick);
					Settings.load(game.getFileIO());
					world = new World();
					renderer = new WorldRenderer(glGraphics,batcher,world);
					state = GAME_RUNNING;
					return;
				}
				if(OverlapTester.pointInRectangle(quitBound, touchPoint)){
					isQuitpressed = false;
					Assets.playSound(Assets.soundClick);
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}
	}
	
	private void updateWin(){
		if(game.getInput().getTouchEvent().size()>0){
			state =GAME_NEXT_LEVEL;
		}
	}
	private void updateNextLevel(){
		Settings.level+=1;
		Settings.save(game.getFileIO());
		Settings.load(game.getFileIO());
		world = new World();
		renderer = new WorldRenderer(glGraphics, batcher, world);
		state = GAME_RUNNING;
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		guiCam.setViewportAndMatrices();
		batcher.beginBatch(Assets.background);
		batcher.drawSprite(240, 160, 480, 320, Assets.backgroundRegion);
		batcher.endBatch();
		
		renderer.render();
		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		
		pauseBound = new Rectangle(480-50-10, 320-50-5, 50, 50);
		batcher.drawSprite(480-25-10, 320-25-5, 50, 50,isPausedPressed?Assets.pauseBold:Assets.pause);
		
		batcher.drawSprite(480-20-42-46-21, 20+42+21, -42, 46, isLeftPressed?Assets.rightButtonBold:Assets.rightButton);
		batcher.drawSprite(480-20-21, 20+42+23, 42, 46, isRightPressed?Assets.rightButtonBold:Assets.rightButton);
		batcher.drawSprite(480-20-42-23, 20+42+46+23, 46, 42,isUpPressed?Assets.upButtonBold:Assets.upButton);
		batcher.drawSprite(480-20-42-23, 20+21, 46, -42, isDownPressed? Assets.upButtonBold:Assets.upButton);
		
		batcher.drawSprite(480-20-42-46-21, 23, -42, 46, Assets.rightButton);
		
		switch (state) {
		case GAME_PAUSE:
			presentPaused();
			break;
		case GAME_WIN:
			presentGameWin();
			break;
		}
		
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void presentPaused(){
		batcher.drawSprite(240, 160+16+32+16, 160, 32,isResumePressed?Assets.resumeBold:Assets.resume);
		batcher.drawSprite(240, 160, 128, 32, isResetPressed?Assets.resetBold:Assets.reset);
		batcher.drawSprite(240, 160-16-32-16, 128, 32, isQuitpressed? Assets.quitBold:Assets.quit);
	}
	private void presentGameWin(){
		String s1 = "CONGRATULATION!!!"+ "\n" +"TOUCH TO NEXT ROUND!!!";
		Assets.font.drawText(batcher, s1, 480/2 -s1.length()/2*16, 320/2+60);
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
