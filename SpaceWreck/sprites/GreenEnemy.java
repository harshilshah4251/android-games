package com.SpaceWreck.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.SpaceWreck.game.GameConstants;
import com.SpaceWreck.screens.PlayScreen;
import com.SpaceWreck.sprites.GreenEnemyBullet;

/**
 * Created by harsh on 06/20/17.
 */

public class GreenEnemy extends com.SpaceWreck.sprites.Enemy {

    private TextureRegion greenEnemy1;
    private TextureRegion greenEnemy2;
    private TextureRegion greenEnemy3;
    private TextureRegion greenEnemy4;

    private Array<TextureRegion> greenEnemySequence;
    private Animation<TextureRegion> animation;
    private float stateTime=0.0f; //for animation
    public static int value=400;

    private float velXRequired;
    private float velYRequired;

    private float bulletVel;
    private float bulletAngle;

    private float gapTime;
    public GreenEnemy(PlayScreen playScreen, float x, float y) {
        super(playScreen, x, y);
        this.width=44.0f/ GameConstants.ppm;
        this.height= 37.0f/ com.SpaceWreck.game.GameConstants.ppm;
        this.isShooting= true;
        this.velX= 1.0f;
        this.velY=1.0f;
        this.velXRequired=0;
        this.velYRequired=0;
        this.bulletVel=10.0f;
        defineBody();
        this.health=100.0f;
        this.gapTime=0;
        this.bulletAngle=0;
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
        shape.setAsBox((width)/2.0f, (height)/2.0f);

        fixtureDef.shape= shape;
        fixtureDef.friction=0.0f;
        mass=new MassData();
        mass.mass=1.0f;
        //fixtureDef.restitution=0.5f;
        fixtureDef.filter.categoryBits= com.SpaceWreck.game.GameConstants.ENEMY_BIT;
        fixtureDef.filter.maskBits= GameConstants.WALLHORIZONTAL_BIT |
                com.SpaceWreck.game.GameConstants.PLAYER_BIT |
                GameConstants.PLAYERBULLET_BIT |
                GameConstants.WALLVERTICAL_BIT |
                com.SpaceWreck.game.GameConstants.WALLHORIZONTAL_BIT |
                com.SpaceWreck.game.GameConstants.ENEMY_BIT |
                com.SpaceWreck.game.GameConstants.WALL_BIT|
                GameConstants.BOX_BIT;;
        body.createFixture(fixtureDef).setUserData(this);

    }

    public void initializeTextures(){
        greenEnemy1= atlas.findRegion("GreenEnemy1");
        greenEnemy2= atlas.findRegion("GreenEnemy2");
        greenEnemy3= atlas.findRegion("GreenEnemy3");
        greenEnemy4= atlas.findRegion("GreenEnemy4");

        // Gdx.app.log("TexturesLoaded", "ShootingEnemy");

    }
    public void initializeAnimations(){
        greenEnemySequence=new Array<TextureRegion>();
        greenEnemySequence.addAll(greenEnemy1, greenEnemy2, greenEnemy3, greenEnemy4);
        animation= new Animation<TextureRegion>(0.25f, greenEnemySequence, Animation.PlayMode.LOOP_PINGPONG);
    }
    public void updateKeyFrame(float delta){
        stateTime+=delta;
        this.setRegion(animation.getKeyFrame(stateTime));
        this.setSize(this.width, this.height);
        //Gdx.app.log(this.getRegionWidth()+"", this.getRegionHeight()+"");
        this.setPosition(this.body.getPosition().x-this.getWidth()/2.0f, this.body.getPosition().y-this.getHeight()/2.0f);

    }
    public void activateShooting(float delta){

        gapTime+=delta;
        if(gapTime>0.4f){
            bulletAngle+=45.0f;
            if(bulletAngle> 360.0f){
                bulletAngle=45.0f;
            }
            playScreen.getBulletManager().addBullet(new GreenEnemyBullet(playScreen, this.body.getPosition().x, this.body.getPosition().y, (float)(bulletVel * Math.cos(bulletAngle)) , (float)(bulletVel * Math.sin(bulletAngle))));

        }

    }
    public void updateVelocity(float delta){
        float xDistance= (float) ((float)(player.getBody().getPosition().x - this.body.getPosition().x));
        float yDistance= (float) ((float)(player.getBody().getPosition().y - this.body.getPosition().y));
        float distance= (float) (Math.sqrt(Math.pow(xDistance, 2.0)+Math.pow(yDistance, 2.0)));

        float costheta= xDistance/distance;
        float sintheta= yDistance/distance;

        velXRequired= MathUtils.lerp(velXRequired, velX * costheta, 0.1f);
        velYRequired= MathUtils.lerp(velYRequired, velY * sintheta, 0.1f);

        this.body.setLinearVelocity(velXRequired, velYRequired);
    }

    @Override
    public void update(float delta) {
        updateVelocity(delta);
        if(isShooting){
            activateShooting(delta);
        }

        // Gdx.app.log("Enemy", "update");
    }


    @Override
    public void render(float delta) {

        updateKeyFrame(delta);
        this.draw(batch);
    }

    @Override
    public void updateHealth() {
        this.setHealth(health-=10);
        if(this.health<=0){
            this.isDead=true;
        }
    }
}
