package com.SpaceWreck.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.SpaceWreck.game.GameConstants;
import com.SpaceWreck.screens.PlayScreen;

/**
 * Created by harsh on 06/20/17.
 */

public class FollowEnemy2 extends com.SpaceWreck.sprites.Enemy {

    private TextureRegion followEnemy1;
    private TextureRegion followEnemy2;
    private TextureRegion followEnemy3;
    private TextureRegion followEnemy4;

    private Array<TextureRegion> followEnemySequence;
    private Animation<TextureRegion> animation;
    private float stateTime=0.0f; //for animation
    private String identity;
    public static int value=80;

    public FollowEnemy2(PlayScreen playScreen, float x, float y, String identity) {
        super(playScreen, x, y);
        this.width=35.0f/ GameConstants.ppm;
        this.height= 15.0f/GameConstants.ppm;
        this.isShooting= false;
        if(identity.equals("horizontal")){
            this.velX= 5.0f;
            this.velY=0;
        }
        if(identity.equals("vertical")){
            this.velX= 0.0f;
            this.velY=5.0f;
        }

        this.health=100.0f;
        this.identity= identity;
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
        shape.setAsBox((width)/2.0f, (height)/2.0f);

        fixtureDef.shape= shape;
        fixtureDef.friction=0.0f;
        mass=new MassData();
        mass.mass=1.0f;
        //fixtureDef.restitution=0.5f;
        fixtureDef.filter.categoryBits= GameConstants.ENEMY_BIT;
        fixtureDef.filter.maskBits= com.SpaceWreck.game.GameConstants.WALLHORIZONTAL_BIT |
                GameConstants.PLAYER_BIT |
                GameConstants.PLAYERBULLET_BIT |
                GameConstants.WALLVERTICAL_BIT |
                GameConstants.ENEMY_BIT|
        GameConstants.WALL_BIT|
                com.SpaceWreck.game.GameConstants.BOX_BIT;;
        body.createFixture(fixtureDef).setUserData(this);

    }
    public void updateVelocity(){

        if(this.identity.equals("horizontal")){
            this.body.setLinearVelocity(velX, velY);
        }
        else if(this.identity.equals("vertical")){
            this.body.setLinearVelocity(velX, velY);
        }

        //this.body.applyLinearImpulse(new Vector2(velXRequired,velYRequired ), this.body.getWorldCenter(), true);

    }
    public void initializeTextures(){
        followEnemy1= atlas.findRegion("GreenFollow1");
        followEnemy2= atlas.findRegion("GreenFollow2");
        followEnemy3= atlas.findRegion("GreenFollow3");
        followEnemy4= atlas.findRegion("GreenFollow4");

        // Gdx.app.log("TexturesLoaded", "ShootingEnemy");

    }
    public void initializeAnimations(){
        followEnemySequence=new Array<TextureRegion>();
        followEnemySequence.addAll(followEnemy1, followEnemy2, followEnemy3, followEnemy4);
        animation= new Animation<TextureRegion>(0.25f, followEnemySequence, Animation.PlayMode.LOOP_PINGPONG);
    }
    public void updateKeyFrame(float delta){
        stateTime+=delta;
        this.setRegion(animation.getKeyFrame(stateTime));
        this.setSize(this.width, this.height);
        this.setPosition(this.body.getPosition().x-this.getWidth()/2.0f, this.body.getPosition().y-this.getHeight()/2.0f);
    }
    public void reverseVelocity(){
        this.velY=-velY;
        this.velX=-velX;
        this.body.setLinearVelocity(velX, velY);
    }

    @Override
    public void update(float delta) {
        updateVelocity();
    }


    @Override
    public void render(float delta) {
        updateKeyFrame(delta);
        this.draw(batch);
    }

    @Override
    public void updateHealth() {
        this.setHealth(health-=20);
        if(this.health<=0){
            this.isDead=true;
        }
    }
}
