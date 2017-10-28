package com.SpaceWreck.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.SpaceWreck.screens.PlayScreen;


/**
 * Created by harsh on 06/20/17.
 */

public class WhiteEnemyBullet extends Bullet {
    private static final float velocity=8.0f;
    private TextureRegion greenBullet1;
    private TextureRegion greenBullet2;
    private TextureRegion greenBullet3;

    private Array<TextureRegion> bulletSequence;
    private Animation<TextureRegion> animation;
    private float stateTime=0;  //for animation


    public WhiteEnemyBullet(PlayScreen playScreen, float x, float y , float velX, float velY){
        super(playScreen, x, y);
        this.width= 10.0f/ com.SpaceWreck.game.GameConstants.ppm;
        this.height= 5.0f/ com.SpaceWreck.game.GameConstants.ppm;
        this.velX= velX;
        this.velY=velY;
        defineBody();
        setDirection();
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
        mass.mass=0.001f;
        fixtureDef.friction=0.0f;
        fixtureDef.filter.categoryBits= com.SpaceWreck.game.GameConstants.ENEMYBULLET_BIT;
        fixtureDef.filter.maskBits= com.SpaceWreck.game.GameConstants.WALLHORIZONTAL_BIT |
                com.SpaceWreck.game.GameConstants.WALLVERTICAL_BIT|
                com.SpaceWreck.game.GameConstants.WALL_BIT |
                com.SpaceWreck.game.GameConstants.BOX_BIT |
                com.SpaceWreck.game.GameConstants.PLAYER_BIT;
        fixtureDef.restitution=0.5f;
        body.setBullet(true);
        body.createFixture(fixtureDef).setUserData(this);


    }
    public void initializeTextures(){
        greenBullet1= atlas.findRegion("GreenBullet1");
        greenBullet2= atlas.findRegion("GreenBullet2");
        greenBullet3= atlas.findRegion("GreenBullet3");


    }
    public void initializeAnimations(){
        bulletSequence= new Array<TextureRegion>();
        bulletSequence.addAll(greenBullet1, greenBullet2, greenBullet3);
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
        this.body.setLinearVelocity(velX, velY);
    }


}
