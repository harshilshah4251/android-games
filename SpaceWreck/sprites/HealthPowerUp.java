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

public class HealthPowerUp extends com.SpaceWreck.sprites.PowerUps {

    private TextureRegion chargeUp1;
    private TextureRegion chargeUp2;
    private TextureRegion chargeUp3;

    private Array<TextureRegion> chargeUpSequence;
    private Animation<TextureRegion> animation;
    private float stateTime;

    private float healthIncrement;
    private Sound chargeUpSound;
    public HealthPowerUp(PlayScreen playScreen, float x, float y) {
        super(playScreen, x, y);
        this.healthIncrement=60.0f;
        this.isHit=false;
        this.width=10.0f/ GameConstants.ppm;
        this.height=20.0f/GameConstants.ppm;
        defineBody();
        this.setPosition(this.body.getPosition().x-this.getWidth()/2.0f, this.body.getPosition().y-this.getHeight()/2.0f);
        initializeTextures();
        initializeAnimations();
        initializeSounds();
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

        chargeUp1= atlas.findRegion("chargeUp1");
        chargeUp2= atlas.findRegion("chargeUp2");
        chargeUp3= atlas.findRegion("chargeUp3");
    }
    public void initializeAnimations(){
        chargeUpSequence= new Array<TextureRegion>();
        chargeUpSequence.addAll(chargeUp1, chargeUp2, chargeUp3);
        animation= new Animation<TextureRegion>(0.333f, chargeUpSequence, Animation.PlayMode.LOOP);
    }
    public void initializeSounds(){
        chargeUpSound= playScreen.getGame().getAssetManager().get("Sounds/chargeUp.mp3", Sound.class);
    }
    public void updateKeyFrame(float delta){
        stateTime+=delta;
        this.setRegion(animation.getKeyFrame(stateTime));
        this.setSize(width, height);
        this.setPosition(this.body.getPosition().x- this.getWidth()/2.0f, this.body.getPosition().y- this.getHeight()/2.0f);
    }
    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {
        updateKeyFrame(delta);
        this.draw(batch);
    }

    @Override
    public void setHit(boolean hit) {
        super.setHit(hit);
        chargeUpSound.play();
        player.setHealth(player.getHealth()+this.healthIncrement);
        if(player.getHealth()>100){
            player.setHealth(100.0f);

        }
    }
}
