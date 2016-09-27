package org.flixel;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.flixel.screens.EarserTest;
import org.flixel.screens.MaskTest;

public class Flixel extends Game {

	@Override
	public void create () {
        //setScreen(new EarserTest());
        setScreen(new MaskTest());
	}

}
