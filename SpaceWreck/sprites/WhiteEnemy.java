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
import com.SpaceWreck.sprites.WhiteEnemyBullet;

/**
 * Created by harsh on 06/20/17.
 */

public class WhiteEnemy extends com.SpaceWreck.sprites.Enemy {

    private TextureRegion whiteEnemy1;
    private TextureRegion whiteEnemy2;
    private TextureRegion whiteEnemy3;
    private TextureRegion whiteEnemy4;

    private Array<TextureRegion> whiteEnemySequence;
    private Animation<TextureRegion> animation;
    private float stateTime=0.0f; //for animation
    public static int value=400;

    private float velXRequired;
    private float velYRequired;

    private float bulletVel;

    private float moveTime;
    private float stayTime;

    public WhiteEnemy(PlayScreen playScreen, float x, float y) {
        super(playScreen, x, y);
        this.width=44.0f/ GameConstants.ppm;
        this.height= 37.0f/GameConstants.ppm;
        this.isShooting= false;
        this.velX= 2.0f;
        this.velY=2.0f;
        this.velXRequired=0;
        this.velYRequired=0;
        this.bulletVel=10.0f;
        defineBody();
        this.health=100.0f;
        this.moveTime=0;
        this.stayTime=0;
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
        fixtureDef.filter.categoryBits= GameConstants.ENEMY_BIT;
        fixtureDef.filter.maskBits= GameConstants.WALLHORIZONTAL_BIT |
                GameConstants.PLAYER_BIT |
                GameConstants.PLAYERBULLET_BIT |
                GameConstants.WALLVERTICAL_BIT |
                GameConstants.WALLHORIZONTAL_BIT |
                GameConstants.ENEMY_BIT |
                GameConstants.WALL_BIT|
                GameConstants.BOX_BIT;
        body.createFixture(fixtureDef).setUserData(this);

    }

    public void initializeTextures(){
        whiteEnemy1= atlas.findRegion("WeirdEnemy1");
        whiteEnemy2= atlas.findRegion("WeirdEnemy2");
        whiteEnemy3= atlas.findRegion("WeirdEnemy3");
        whiteEnemy4= atlas.findRegion("WeirdEnemy4");

        // Gdx.app.log("TexturesLoaded", "ShootingEnemy");

    }
    public void initializeAnimations(){
        whiteEnemySequence=new Array<TextureRegion>();
        whiteEnemySequence.addAll(whiteEnemy1, whiteEnemy2, whiteEnemy3, whiteEnemy4);
        animation= new Animation<TextureRegion>(0.25f, whiteEnemySequence, Animation.PlayMode.LOOP_PINGPONG);
    }
    public void updateKeyFrame(float delta){
        stateTime+=delta;
        this.setRegion(animation.getKeyFrame(stateTime));
        this.setSize(this.width, this.height);
        //Gdx.app.log(this.getRegionWidth()+"", this.getRegionHeight()+"");
        this.setPosition(this.body.getPosition().x-this.getWidth()/2.0f, this.body.getPosition().y-this.getHeight()/2.0f);

    }
    public void activateShooting(float delta){
        playScreen.getBulletManager().addBullet(new com.SpaceWreck.sprites.WhiteEnemyBullet(playScreen, this.body.getPosition().x + this.width/2.0f, this.body.getPosition().y, (float)(bulletVel * Math.cos(0)) , (float)(bulletVel * Math.sin(0))));
        playScreen.getBulletManager().addBullet(new com.SpaceWreck.sprites.WhiteEnemyBullet(playScreen, this.body.getPosition().x + this.width/4.0f, this.body.getPosition().y + this.height/4.0f, (float)(bulletVel * Math.cos(45)) , (float)(bulletVel * Math.sin(45))));
        playScreen.getBulletManager().addBullet(new com.SpaceWreck.sprites.WhiteEnemyBullet(playScreen, this.body.getPosition().x, this.body.getPosition().y+this.height/2.0f, (float)(bulletVel * Math.cos(90)) , (float)(bulletVel * Math.sin(90))));
        playScreen.getBulletManager().addBullet(new com.SpaceWreck.sprites.WhiteEnemyBullet(playScreen, this.body.getPosition().x-this.width/4.0f, this.body.getPosition().y+this.height/4.0f, (float)(bulletVel * Math.cos(135)) , (float)(bulletVel * Math.sin(135))));
        playScreen.getBulletManager().addBullet(new com.SpaceWreck.sprites.WhiteEnemyBullet(playScreen, this.body.getPosition().x-this.width/2.0f, this.body.getPosition().y, (float)(bulletVel * Math.cos(180)) , (float)(bulletVel * Math.sin(180))));
        playScreen.getBulletManager().addBullet(new com.SpaceWreck.sprites.WhiteEnemyBullet(playScreen, this.body.getPosition().x-this.width/4.0f, this.body.getPosition().y-this.height/4.0f, (float)(bulletVel * Math.cos(225)) , (float)(bulletVel * Math.sin(225))));
        playScreen.getBulletManager().addBullet(new com.SpaceWreck.sprites.WhiteEnemyBullet(playScreen, this.body.getPosition().x, this.body.getPosition().y-this.height/2.0f, (float)(bulletVel * Math.cos(270)) , (float)(bulletVel * Math.sin(270))));
        playScreen.getBulletManager().addBullet(new WhiteEnemyBullet(playScreen, this.body.getPosition().x+this.width+4.0f, this.body.getPosition().y-this.height/4.0f, (float)(bulletVel * Math.cos(315)) , (float)(bulletVel * Math.sin(315))));

    }
    public void updateVelocity(float delta){
        float xDistance= (float) ((float)(player.getBody().getPosition().x - this.body.getPosition().x));
        float yDistance= (float) ((float)(player.getBody().getPosition().y - this.body.getPosition().y));
        float distance= (float) (Math.sqrt(Math.pow(xDistance, 2.0)+Math.pow(yDistance, 2.0)));

        float costheta= xDistance/distance;
        float sintheta= yDistance/distance;

        velXRequired= MathUtils.lerp(velXRequired, velX * costheta, 0.1f);
        velYRequired= MathUtils.lerp(velYRequired, velY * sintheta, 0.1f);

        stayTime+=delta;
        if(stayTime>0.5f && moveTime<1.0){
            this.body.setLinearVelocity(velXRequired, velYRequired);
            moveTime+=delta;
            isShooting=true;
        }
        if(moveTime>1.0) {
            stayTime = 0.0f;
            moveTime = 0.0f;
            this.body.setLinearVelocity(0, 0);
            if(isShooting){
                activateShooting(delta);
                isShooting=false;
            }
        }





    }

    @Override
    public void update(float delta) {
        updateVelocity(delta);


        // Gdx.app.log("Enemy", "update");
    }


    @Override
    public void render(float delta) {

        updateKeyFrame(delta);
        this.draw(batch);
    }

    @Override
    public void updateHealth() {
        this.setHealth(health-=5);
        if(this.health<=0){
            this.isDead=true;
        }
    }
}
