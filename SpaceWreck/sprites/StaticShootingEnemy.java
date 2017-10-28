package com.SpaceWreck.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.SpaceWreck.game.GameConstants;
import com.SpaceWreck.screens.PlayScreen;
import com.SpaceWreck.sprites.RedEnemyBullet;

/**
 * Created by harsh on 06/20/17.
 */

public class StaticShootingEnemy extends com.SpaceWreck.sprites.Enemy {

    private TextureRegion shootingEnemy1;
    private TextureRegion shootingEnemy2;
    private TextureRegion shootingEnemy3;
    private TextureRegion shootingEnemy4;

    private Array<TextureRegion> shootingEnemySequence;
    private Animation<TextureRegion> animation;
    private float stateTime=0.0f; //for animation
    private float pauseTime=0.0f; //for pause between shoots
    private float shootTime=0.0f;
    public static int value=200;

    public StaticShootingEnemy(PlayScreen playScreen, float x, float y) {
        super(playScreen, x, y);
        this.width=35.0f/ GameConstants.ppm;
        this.height= 30.0f/GameConstants.ppm;
        this.isShooting= false;
        this.velX= 2.0f;
        this.velY=2.0f;
        defineBody();
        this.health=100.0f;
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
        fixtureDef.restitution=0.5f;
        fixtureDef.filter.categoryBits= GameConstants.ENEMY_BIT;
        fixtureDef.filter.maskBits= GameConstants.WALLHORIZONTAL_BIT |
                GameConstants.PLAYER_BIT |
                GameConstants.PLAYERBULLET_BIT |
                GameConstants.WALLVERTICAL_BIT |
                GameConstants.WALLHORIZONTAL_BIT |
                GameConstants.ENEMY_BIT |
                GameConstants.WALL_BIT|
                GameConstants.BOX_BIT;;
        body.createFixture(fixtureDef).setUserData(this);

    }

    public void initializeTextures(){
        shootingEnemy1= atlas.findRegion("ShootingEnemy1");
        shootingEnemy2= atlas.findRegion("ShootingEnemy2");
        shootingEnemy3= atlas.findRegion("ShootingEnemy3");
        shootingEnemy4= atlas.findRegion("ShootingEnemy4");

        // Gdx.app.log("TexturesLoaded", "ShootingEnemy");

    }
    public void initializeAnimations(){
        shootingEnemySequence=new Array<TextureRegion>();
        shootingEnemySequence.addAll(shootingEnemy1, shootingEnemy2, shootingEnemy3, shootingEnemy4);
        animation= new Animation<TextureRegion>(0.25f, shootingEnemySequence, Animation.PlayMode.LOOP_PINGPONG);
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
        if(gapTime>0.1){
            playScreen.getBulletManager().addBullet(new RedEnemyBullet(playScreen, this.body.getPosition().x, this.body.getPosition().y));
            gapTime=0;
        }
    }

    @Override
    public void update(float delta) {
        updateShootingStatus(delta);
        if(isShooting){
            activateShooting(delta);
        }
    }
    public void updateShootingStatus(float delta){
        pauseTime+=delta;
        if(pauseTime>1.0f && shootTime<2.0){
            isShooting=true;
            shootTime+=delta;
        }
        if(shootTime>2.0) {
            pauseTime = 0.0f;
            shootTime = 0.0f;
            isShooting = false;
            Gdx.app.log("shooting", "false");
        }
    }

    @Override
    public void updateHealth() {
        this.setHealth(health-=5);
        if(this.health<=0){
            this.isDead=true;
        }
    }

    @Override
    public void render(float delta) {

        updateKeyFrame(delta);
        this.draw(batch);
    }
}
