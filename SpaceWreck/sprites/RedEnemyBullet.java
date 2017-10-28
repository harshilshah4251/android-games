package com.SpaceWreck.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;


/**
 * Created by harsh on 06/20/17.
 */

public class RedEnemyBullet extends Bullet {
    private static final float velocity=8.0f;
    private TextureRegion redBullet1;
    private TextureRegion redBullet2;
    private TextureRegion redBullet3;

    private Array<TextureRegion> bulletSequence;
    private Animation<TextureRegion> animation;
    private float stateTime=0;  //for animation


    public RedEnemyBullet(com.SpaceWreck.screens.PlayScreen playScreen, float x, float y){
        super(playScreen, x, y);
        this.width= 10.0f/ com.SpaceWreck.game.GameConstants.ppm;
        this.height= 5.0f/ com.SpaceWreck.game.GameConstants.ppm;
        this.velX= 10.0f;
        this.velY=10.0f;
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
                /*GameConstants.PLAYER_BIT*/;
        fixtureDef.restitution=0.5f;
        body.setBullet(true);
        body.createFixture(fixtureDef).setUserData(this);


    }
    public void initializeTextures(){
        redBullet1= atlas.findRegion("RedBullet1");
        redBullet2= atlas.findRegion("RedBullet2");
        redBullet3= atlas.findRegion("RedBullet3");


    }
    public void initializeAnimations(){
        bulletSequence= new Array<TextureRegion>();
        bulletSequence.addAll(redBullet1, redBullet2, redBullet3);
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
        float xDistance= (float) ((float)(player.getBody().getPosition().x - this.body.getPosition().x));
        float yDistance= (float) ((float)(player.getBody().getPosition().y - this.body.getPosition().y));
        float distance= (float) (Math.sqrt(Math.pow(xDistance, 2.0)+Math.pow(yDistance, 2.0)));

        float costheta= xDistance/distance;
        float sintheta= yDistance/distance;

        float velXRequired= costheta*velX;
        float velYRequired= sintheta*velY;

        this.body.setLinearVelocity(velXRequired, velYRequired);
    }


}
