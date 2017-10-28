package com.SpaceWreck.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

/**
 * Created by harsh on 06/29/17.
 */

public class DiePowerUp extends PowerUps {
    private TextureRegion die1;
    private TextureRegion die2;

    private Array<TextureRegion> dieSequence;
    private Animation<TextureRegion> animation;
    private float stateTime;

    public DiePowerUp(com.SpaceWreck.screens.PlayScreen playScreen, float x, float y) {
        super(playScreen, x, y);
        this.isHit=false;
        this.width=20.0f/ com.SpaceWreck.game.GameConstants.ppm;
        this.height=24.0f/ com.SpaceWreck.game.GameConstants.ppm;
        defineBody();
        this.setPosition(this.body.getPosition().x-this.getWidth()/2.0f, this.body.getPosition().y-this.getHeight()/2.0f);
        initializeTextures();
        initializeAnimations();
        stateTime=0.0f;
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
        fixtureDef.filter.categoryBits= com.SpaceWreck.game.GameConstants.POWERUP_BIT;
        fixtureDef.filter.maskBits= com.SpaceWreck.game.GameConstants.WALLHORIZONTAL_BIT |
                com.SpaceWreck.game.GameConstants.WALLVERTICAL_BIT |
                com.SpaceWreck.game.GameConstants.PLAYER_BIT |
                com.SpaceWreck.game.GameConstants.WALL_BIT;
        fixtureDef.isSensor=true;
        body.createFixture(fixtureDef).setUserData(this);
    }
    public void initializeTextures(){

        die1= atlas.findRegion("die1");
        die2=atlas.findRegion("die2");
    }
    public void initializeAnimations(){
        dieSequence= new Array<TextureRegion>();
        dieSequence.addAll(die1, die2);
        animation= new Animation<TextureRegion>(0.50f, dieSequence, Animation.PlayMode.LOOP);
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
        player.setHealth(0);
        Gdx.app.log("DiePowerUp", "Hit");

    }
}
