package com.badlogic.androidgames.framework;

import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Sound;

public interface Audio {
	public Music newMusic(String fileName);
	public Sound newSound(String fileName);
}
