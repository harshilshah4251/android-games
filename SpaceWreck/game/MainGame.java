package com.SpaceWreck.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.SpaceWreck.ads.AdHandler;
import com.SpaceWreck.playServices.PlayServices;

public class MainGame extends Game {
	public SpriteBatch batch;
	private AssetManager assetManager;
	public static PlayServices playServices;
	public static AdHandler handler;
	public boolean toggle;

	public MainGame (PlayServices playServices, AdHandler handler){
		this.playServices= playServices;
		this.handler= handler;

	}
	public MainGame(){

    }



	@Override
	public void create () {


		batch = new SpriteBatch();
		assetManager= new AssetManager();
		this.setScreen(new com.SpaceWreck.screens.LoadingScreen(this));

	}

	@Override
	public void render () {
		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();

	}
	public AssetManager getAssetManager(){
		return  this.assetManager;
	}

}
