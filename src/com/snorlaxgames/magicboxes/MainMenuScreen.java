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
public class MainMenuScreen extends GLScreen{

	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle soundBounds;
	Rectangle startBounds;
	Rectangle resetBounds;
	Rectangle helpBounds;
	Rectangle leftBound;
	Rectangle rightBound;
	Vector2 touchPoint;
	String stringLevel = "level: "; 

	Boolean isStartPressed = false;
	Boolean isResetPressed = false;
	Boolean isHelpPressed = false;
	
	public MainMenuScreen(Game game){
		super(game);
		guiCam = new Camera2D(glGraphics, 480, 320);
		batcher = new SpriteBatcher(glGraphics, 100);
		soundBounds = new Rectangle(0, 0, 64, 64);
		startBounds = new Rectangle(300, 224, 128, 32);
		resetBounds = new Rectangle(300, 224-64, 128, 32);
		helpBounds = new Rectangle(300, 224-128, 128, 32);
		touchPoint = new Vector2();
	}
	@Override
	public void update(float deltaTime) {
		List<MyTouchEvent> touchEvents = game.getInput().getTouchEvent();
		game.getInput().getKeyEvent();
		
		int len = touchEvents.size();
		for(int i=0;i<len;i++){
			MyTouchEvent event = touchEvents.get(i);
			if(event.type == MyTouchEvent.TOUCH_DOWN){
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);
				if(OverlapTester.pointInRectangle(startBounds, touchPoint)){
					isStartPressed = true;
					return;
				}
				if(OverlapTester.pointInRectangle(resetBounds, touchPoint)){
					isResetPressed = true;
					return;
				}
				if(OverlapTester.pointInRectangle(helpBounds, touchPoint)){
					isHelpPressed = true;
					return;
				}
			}
			if(event.type == MyTouchEvent.TOUCH_UP){
				touchPoint.set(event.x, event.y);
				guiCam.touchToWorld(touchPoint);
				if(OverlapTester.pointInRectangle(soundBounds, touchPoint)){
					Assets.playSound(Assets.soundClick);
					Settings.soundEnabled = !Settings.soundEnabled;
					if(Settings.soundEnabled)
						Assets.music.play();
					else
						Assets.music.pause();
				}
				if(OverlapTester.pointInRectangle(startBounds, touchPoint)){
					isStartPressed = false;
					Assets.playSound(Assets.soundClick);
					game.setScreen(new GameScreen(game));
					return;
				}
				if(OverlapTester.pointInRectangle(resetBounds, touchPoint)){
					Assets.playSound(Assets.soundClick);
					Settings.level=1;
					Settings.save(game.getFileIO());
					Settings.load(game.getFileIO());
					isResetPressed = false;
				}
				if(OverlapTester.pointInRectangle(helpBounds, touchPoint)){
					isHelpPressed = false;
					Assets.playSound(Assets.soundClick);
					game.setScreen(new HelpScreen(game));
					return;
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		batcher.beginBatch(Assets.mainMenu);
		batcher.drawSprite(240, 160, 480, 320, Assets.mainMenuRegion);
		batcher.endBatch();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(300+128/2, 224+32/2, 128, 32,isStartPressed?Assets.startBold:Assets.start);
		batcher.drawSprite(300+128/2, 224-64+32/2, 128, 32, isResetPressed?Assets.resetBold:Assets.reset);
		batcher.drawSprite(300+128/2, 224-128+32/2, 128, 32, isHelpPressed?Assets.helpBold:Assets.help);
		if(Settings.soundEnabled){
			batcher.drawSprite(22, 24, 44, 48,Assets.soundOn);
		}
		else
		{
			batcher.drawSprite(22, 24, 32, 48,Assets.soundOff);
		}
		Assets.font.drawText(batcher, "level: "+Settings.level , 480-10*16 , 20);
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {
		Settings.save(game.getFileIO());
	}

	@Override
	public void resume() {
		guiCam.setViewportAndMatrices();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
