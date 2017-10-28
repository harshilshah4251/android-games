package com.SpaceWreck.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.SpaceWreck.game.GameConstants;
import com.SpaceWreck.screens.PlayScreen;


/**
 * Created by harsh on 06/20/17.
 */

public class PlayerBullet extends Bullet {
    private static final float velocity=20.0f;
    private TextureRegion blueBullet1;
    private TextureRegion blueBullet2;
    private TextureRegion blueBullet3;

    private Array<TextureRegion> bulletSequence;
    private Animation<TextureRegion> animation;
    private float stateTime=0;


    public PlayerBullet(PlayScreen playScreen, float x, float y){
        super(playScreen, x, y);
        this.width= 10.0f/GameConstants.ppm;
        this.height= 5.0f/GameConstants.ppm;
        setDirection();
        defineBody();
        initializeTextures();
        initializeAnimations();
    }
    public PlayerBullet(PlayScreen playScreen, float x, float y, float velX, float velY){
        super(playScreen, x, y);
        this.width= 10.0f/GameConstants.ppm;
        this.height= 5.0f/GameConstants.ppm;
        this.velX=velX;
        this.velY=velY;
        defineBody();
        initializeTextures();
        initializeAnimations();
    }

    public void defineBody(){
        BodyDef bodyDef= new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        body= world.createBody(bodyDef);

        FixtureDef fixtureDef= new FixtureDef();
        PolygonShape shape= new PolygonShape();
        shape.setAsBox(width/2.0f, height/2.0f);

        fixtureDef.shape= shape;
        fixtureDef.filter.categoryBits= GameConstants.PLAYERBULLET_BIT;
        fixtureDef.filter.maskBits= GameConstants.WALLHORIZONTAL_BIT |
                GameConstants.WALLVERTICAL_BIT |
                GameConstants.ENEMY_BIT |
                GameConstants.PLAYERBULLET_BIT |
                GameConstants.WALL_BIT |
        GameConstants.BOX_BIT ;
        fixtureDef.restitution=0.5f;
        body.setBullet(true);
        body.createFixture(fixtureDef).setUserData(this);

        body.setLinearVelocity(velX, velY);

    }
    public void initializeTextures(){
        blueBullet1= atlas.findRegion("BlueBullet1");
        blueBullet2= atlas.findRegion("BlueBullet2");
        blueBullet3= atlas.findRegion("BlueBullet3");


    }
    public void initializeAnimations(){
        bulletSequence= new Array<TextureRegion>();
        bulletSequence.addAll(blueBullet1, blueBullet2, blueBullet3);
        animation= new Animation<TextureRegion>(0.166f, bulletSequence, Animation.PlayMode.LOOP_PINGPONG);
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
    public void setDirection(){

        if(playScreen.getPlayer().isRightMove() || playScreen.getPlayer().isStatic()){
            this.velX= velocity;
            this.velY= 0;
        }
        else if(playScreen.getPlayer().isLeftMove()){
            this.velX= -velocity;
            this.velY= 0;
        }
        else if(playScreen.getPlayer().isUpMove()){
            this.velX= 0;
            this.velY= velocity;
        }
        else if(playScreen.getPlayer().isDownMove()){
            this.velX= 0;
            this.velY= -velocity;
        }

    }
}
