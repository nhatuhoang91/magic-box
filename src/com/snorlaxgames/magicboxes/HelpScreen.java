package com.snorlaxgames.magicboxes;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.MyTouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class HelpScreen extends GLScreen{
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle backBound;
	Vector2 touchPoint;
	Texture helpImage;
	TextureRegion helpRegion;
	boolean isBackPress = false;
	public HelpScreen(Game game){
		super(game);
		guiCam = new Camera2D(glGraphics, 480, 320);
		batcher = new SpriteBatcher(glGraphics, 2);
		backBound = new Rectangle(0, 0, 64, 64);
		touchPoint = new Vector2();
	}
	@Override
	public void update(float deltaTime) {
		List<MyTouchEvent> touchEvents = game.getInput().getTouchEvent();
		game.getInput().getKeyEvent();
		int len = touchEvents.size();
		for(int i=0;i<len;i++){
			MyTouchEvent event = touchEvents.get(i);
			touchPoint.set(event.x, event.y);
			guiCam.touchToWorld(touchPoint);
			if(event.type == MyTouchEvent.TOUCH_DOWN){
				isBackPress = true;
				return;
			}
			if(event.type == MyTouchEvent.TOUCH_UP){
				if(OverlapTester.pointInRectangle(backBound, touchPoint)){
					isBackPress= false;
					Assets.playSound(Assets.soundClick);
					game.setScreen(new MainMenuScreen(game));
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		batcher.beginBatch(helpImage);
		batcher.drawSprite(240, 160, 480, 320, helpRegion);
		batcher.endBatch();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(32, 32, -64, 64, isBackPress?Assets.nextButtonBold:Assets.nextButton);
		
		batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
	}

	@Override
	public void pause() {
		helpImage.dispose();
	}

	@Override
	public void resume() {
		helpImage = new Texture(glGame, "help.png");
		helpRegion = new TextureRegion(helpImage, 0, 0, 480, 320);
		
		guiCam.setViewportAndMatrices();
	}

	@Override
	public void dispose() {
	}
	
}
