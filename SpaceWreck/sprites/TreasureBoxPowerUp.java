package com.SpaceWreck.sprites;

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

public class TreasureBoxPowerUp extends com.SpaceWreck.sprites.PowerUps {
    private TextureRegion treasureBox1;
    private TextureRegion treasureBox2;

    private Array<TextureRegion> treasureBoxSequence;
    private Animation<TextureRegion> animation;
    private float stateTime;
    public static int value=2000;


    private static Sound hitSound;

    public TreasureBoxPowerUp(PlayScreen playScreen, float x, float y) {
        super(playScreen, x, y);
        this.isHit=false;
        this.width=45.0f/GameConstants.ppm;
        this.height= 45.0f/GameConstants.ppm;
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

        treasureBox1= atlas.findRegion("treasureBox1");
        treasureBox2=atlas.findRegion("treasureBox2");
    }
    public void initializeAnimations(){
        treasureBoxSequence= new Array<TextureRegion>();
        treasureBoxSequence.addAll(treasureBox1, treasureBox2);
        animation= new Animation<TextureRegion>(0.50f, treasureBoxSequence, Animation.PlayMode.LOOP);
    }
    public void updateKeyFrame(float delta){
        stateTime+=delta;
        this.setRegion(animation.getKeyFrame(stateTime));
        this.setSize(width, height);
        this.setPosition(this.body.getPosition().x- this.getWidth()/2.0f, this.body.getPosition().y- this.getHeight()/2.0f);
    }

    public void loadSound(){


        hitSound=playScreen.getGame().getAssetManager().get("Sounds/cling.mp3", Sound.class);

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
        player.setScore(player.getScore()+TreasureBoxPowerUp.value);
        hitSound.play();
        hitSound.play();
        hitSound.play();
        hitSound.play();

    }
}
