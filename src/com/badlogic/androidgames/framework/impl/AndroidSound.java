package com.badlogic.androidgames.framework.impl;

import android.media.SoundPool;

import com.badlogic.androidgames.framework.Sound;

public class AndroidSound implements Sound{
	
	int soundId;
	SoundPool soundPool;
	public AndroidSound(SoundPool s , int id)
	{
		soundId = id;
		soundPool = s;
	}
	@Override
	public void play(float volume) {
		soundPool.play(soundId, volume, volume, 0, 0, 1);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		soundPool.unload(soundId);
	}

}
