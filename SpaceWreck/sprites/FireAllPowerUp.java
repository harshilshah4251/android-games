package com.SpaceWreck.sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.SpaceWreck.game.GameConstants;
import com.SpaceWreck.screens.PlayScreen;

/**
 * Created by harsh on 06/29/17.
 */

public class FireAllPowerUp extends com.SpaceWreck.sprites.PowerUps {
    private TextureRegion fireAll1;
    private TextureRegion fireAll2;

    private Array<TextureRegion> fireAllSequence;
    private Animation<TextureRegion> animation;
    private float stateTime;


    private static Music funkySound;
    private static Sound hitSound;

    public FireAllPowerUp(PlayScreen playScreen, float x, float y) {
        super(playScreen, x, y);
        this.isHit=false;
        this.width=44.0f/GameConstants.ppm;
        this.height= 40.0f/GameConstants.ppm;
        defineBody();
        this.setPosition(this.body.getPosition().x-this.getWidth()/2.0f, this.body.getPosition().y-this.getHeight()/2.0f);
        initializeTextures();
        initializeAnimations();
        loadSound();
        stateTime=0;
    }
    public void defineBody(){
        BodyDef bodyDef= new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type= BodyDef.BodyType.StaticBody;
        body= world.createBody(bodyDef);

        FixtureDef fixtureDef= new FixtureDef();
        PolygonShape shape= new PolygonShape();
        shape.setAsBox(width/2.0f, height/2.0f);

        fixtureDef.shape= shape;
        fixtureDef.filter.categoryBits= GameConstants.POWERUP_BIT;
        fixtureDef.filter.maskBits= GameConstants.WALLHORIZONTAL_BIT |
                GameConstants.WALLVERTICAL_BIT |
                GameConstants.PLAYER_BIT |
                GameConstants.WALL_BIT;
        fixtureDef.isSensor=true;
        body.createFixture(fixtureDef).setUserData(this);
    }
    public void initializeTextures(){

        fireAll1= atlas.findRegion("fireAll1");
        fireAll2=atlas.findRegion("fireAll2");
    }
    public void initializeAnimations(){
        fireAllSequence= new Array<TextureRegion>();
        fireAllSequence.addAll(fireAll1, fireAll2);
        animation= new Animation<TextureRegion>(0.50f, fireAllSequence, Animation.PlayMode.LOOP);
    }
    public void updateKeyFrame(float delta){
        stateTime+=delta;
        this.setRegion(animation.getKeyFrame(stateTime));
        this.setSize(width, height);
        this.setPosition(this.body.getPosition().x- this.getWidth()/2.0f, this.body.getPosition().y- this.getHeight()/2.0f);
    }

    public void loadSound(){
        funkySound= playScreen.getGame().getAssetManager().get("Sounds/arcadeFunk.mp3", Music.class);
        funkySound.setLooping(true);

        hitSound=playScreen.getGame().getAssetManager().get("Sounds/fireAll.mp3", Sound.class);

    }
    public static void playSound(boolean flag){
        if(flag){
            funkySound.play();
        }
        else if(!flag){
            funkySound.stop();
            //Gdx.app.log("FunkySOund", "Stop");
        }
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {
        updateKeyFrame(delta);
        this.draw(batch);
        //Gdx.app.log("2xpowerUp", "render");
    }
    @Override
    public void setHit(boolean hit) {
        super.setHit(hit);
        hitSound.play();

    }
}
