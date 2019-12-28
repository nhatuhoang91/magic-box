package com.snorlaxgames.magicboxes;

import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Sound;
import com.badlogic.androidgames.framework.gl.Animation;
import com.badlogic.androidgames.framework.gl.Font;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGame;

public class Assets {
	public static Texture mainMenu;
	public static TextureRegion mainMenuRegion;
	public static Texture background;
	public static TextureRegion backgroundRegion;
	
	public static Texture items;
	
	
	public static TextureRegion start;
	public static TextureRegion reset;
	public static TextureRegion help;
	public static TextureRegion resume;
	public static TextureRegion quit;
	public static TextureRegion startBold;
	public static TextureRegion resetBold;
	public static TextureRegion helpBold;
	public static TextureRegion resumeBold;
	public static TextureRegion quitBold;
	
	public static TextureRegion ready;
	
	public static TextureRegion soundOff;
	public static TextureRegion soundOn;
	
	public static TextureRegion nextButton;
	public static TextureRegion nextButtonBold;
	
	public static TextureRegion pause;
	public static TextureRegion pauseBold;
	
	public static TextureRegion box;
	public static TextureRegion boxOk;
	public static TextureRegion hole;
	
	public static TextureRegion wallHorr;
	public static TextureRegion wallVert;
	public static TextureRegion subWall;
	
	public static TextureRegion rightButton;
	public static TextureRegion rightButtonBold;
	public static TextureRegion upButton;
	public static TextureRegion upButtonBold;
	
	public static TextureRegion personR;
	public static TextureRegion personU;
	
	public static TextureRegion floor;
	public static Font font;
	
	public static Music music;
	
	public static Sound soundHitBox;
	public static Sound soundClick;
	
	public  static void load(GLGame game){
		mainMenu = new Texture(game, "mainmenuscreen.png");
		mainMenuRegion = new TextureRegion(mainMenu, 0, 0, 480, 320);
	
		background = new Texture(game, "background.png");
		backgroundRegion = new TextureRegion(background, 0, 0, 480, 320);
		
		items = new Texture(game, "items.png");

		start = new TextureRegion(items, 0, 128, 128, 32);
		startBold = new TextureRegion(items, 0, 160, 128, 32);
		reset = new TextureRegion(items, 0, 192, 128, 32);
		resetBold = new TextureRegion(items, 0, 224, 128, 32);
		help = new TextureRegion(items, 0, 256, 128, 32);
		helpBold = new TextureRegion(items, 0, 288, 128, 32);
		resume = new TextureRegion(items, 0, 320, 160, 32);
		resumeBold = new TextureRegion(items, 0, 352, 160, 32);
		quit = new TextureRegion(items, 0, 384, 128, 32);
		quitBold = new TextureRegion(items, 0, 416, 128, 32);
		
		ready = new TextureRegion(items, 182, 384, 128, 32);
		
		soundOff = new TextureRegion(items, 0, 0, 32,48);
		soundOn = new TextureRegion(items, 32, 0, 44,48);
		
		pause = new TextureRegion(items, 128, 0, 50,50);
		pauseBold = new TextureRegion(items, 128, 64, 50,50);
		
		nextButton = new TextureRegion(items, 0, 64, 64, 46);
		nextButtonBold = new TextureRegion(items, 64, 64, 64, 46);
		
		box = new TextureRegion(items, 160, 128, 32, 32);
		boxOk = new TextureRegion(items, 160, 160, 32, 32);
		hole = new TextureRegion(items, 160, 192, 30, 30);
		
		wallHorr = new TextureRegion(items, 192, 480, 256, 32);
		wallVert = new TextureRegion(items, 480, 0, 32, 256);
		subWall = new TextureRegion(items, 446, 64, 32, 32);
		
		rightButton = new TextureRegion(items, 0, 448, 42, 48);
		rightButtonBold = new TextureRegion(items, 42, 448, 42, 48);
		upButton = new TextureRegion(items, 96, 448, 48, 42);
		upButtonBold = new TextureRegion(items, 144, 448, 48, 42);
		
		personR = new TextureRegion(items, 446, 0, 32, 32);
		personU = new TextureRegion(items, 446, 32, 32, 32);
		
		
		floor = new TextureRegion(items, 192, 0, 256, 256);
		font = new Font(items, 192, 256, 16, 16, 20);
		
		music = game.getAudio().newMusic("music_background.mp3");
		music.setLooping(true);
		music.setVolume(0.5f);
		if(Settings.soundEnabled)
			music.play();
		soundHitBox = game.getAudio().newSound("hit_box.ogg");
		soundClick = game.getAudio().newSound("click.ogg");
	}
	
	public static void reload(){
		mainMenu.reLoad();
		items.reLoad();
		if(Settings.soundEnabled)
			music.play();
	}
	public static void playSound(Sound sound){
		if(Settings.soundEnabled)
			sound.play(1);
	}
}
