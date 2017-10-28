package com.SpaceWreck.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.SpaceWreck.game.MainGame;


/**
 * Created by harsh on 06/20/17.
 */

public class LoadingScreen implements Screen {
    private MainGame game;
    private float progress;
    private  AssetManager assetManager;
    private ShapeRenderer shapeRenderer;

    public LoadingScreen(MainGame game){
        this.game= game;
        this.assetManager= game.getAssetManager();
        shapeRenderer= new ShapeRenderer();
        this.progress= 0.0f;
        queueAssets();
        //Gdx.app.log("called", "constructer");

    }
    public void queueAssets(){
        assetManager.load("Images/Player/PlayerAtlas.txt", TextureAtlas.class);
        assetManager.load("Images/Player/BoxAtlas.txt", TextureAtlas.class);

        assetManager.load("Images/Player/Earth.png", Texture.class);
        assetManager.load("Images/Player/Mars.png", Texture.class);
        assetManager.load("Images/Player/Jupiter.png", Texture.class);
        assetManager.load("Images/Player/Uranus.png", Texture.class);
        assetManager.load("Images/Player/AlienPlanet.png", Texture.class);
        assetManager.load("Images/Player/marsLocked.png", Texture.class);
        assetManager.load("Images/Player/jupiterLocked.png", Texture.class);
        assetManager.load("Images/Player/uranusLocked.png", Texture.class);
        assetManager.load("Images/Player/alienPlanetLocked.png", Texture.class);


        assetManager.load("Sounds/cling.mp3", Sound.class);
        assetManager.load("Sounds/laserShot.wav", Sound.class);
        assetManager.load("Sounds/wickedwitchlaugh.wav", Sound.class);
        assetManager.load("Sounds/chargeUp.mp3", Sound.class);
        assetManager.load("Sounds/fireAll.mp3", Sound.class);

        assetManager.load("Sounds/spaceAlarm.mp3", Music.class);
        assetManager.load("Sounds/inGameMusic.mp3", Music.class);
        assetManager.load("Sounds/outGameMusic.mp3", Music.class);
        assetManager.load("Sounds/enemiesShot.mp3", Music.class);
        assetManager.load("Sounds/arcadeFunk.mp3", Music.class);






    }

    public void update(float delta){
        progress= MathUtils.lerp(progress, this.assetManager.getProgress(), 0.1f);
       // Gdx.app.log("Progress", progress+"");
        if(this.assetManager.update() && progress >=0.99){
            game.setScreen(new MenuScreen(game));

            //game.setScreen(new ContextScreen(game));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.BLACK);
        shapeRenderer.rect(Gdx.graphics.getWidth()/20.0f, Gdx.graphics.getHeight()/2.0f - Gdx.graphics.getHeight()/40.0f,
                Gdx.graphics.getWidth() -  Gdx.graphics.getWidth()/10.0f, Gdx.graphics.getHeight()/20.0f );
        shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.GREEN);
        shapeRenderer.rect(Gdx.graphics.getWidth()/20.0f, Gdx.graphics.getHeight()/2.0f - Gdx.graphics.getHeight()/40.0f,
                (Gdx.graphics.getWidth() -  Gdx.graphics.getWidth()/10.0f)* this.progress, Gdx.graphics.getHeight()/20.0f );

        shapeRenderer.end();

        //Gdx.app.log("called", "render");
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();

    }
}
