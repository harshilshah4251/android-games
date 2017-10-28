package com.SpaceWreck.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

/**
 * Created by harsh on 06/20/17.
 */

public class FollowEnemy1 extends com.SpaceWreck.sprites.Enemy {

    private TextureRegion followEnemy1;
    private TextureRegion followEnemy2;
    private TextureRegion followEnemy3;
    private TextureRegion followEnemy4;

    private Array<TextureRegion> followEnemySequence;
    private Animation<TextureRegion> animation;
    private float stateTime=0.0f; //for animation

    public static int value=100;

    private float velXRequired, velYRequired;

    public FollowEnemy1(com.SpaceWreck.screens.PlayScreen playScreen, float x, float y) {
        super(playScreen, x, y);
        this.width=35.0f/ com.SpaceWreck.game.GameConstants.ppm;
        this.height= 15.0f/ com.SpaceWreck.game.GameConstants.ppm;
        this.isShooting= false;
        this.velX= 5.0f;
        this.velY=5.0f;
        this.health=100.0f;
        this.velXRequired=0;
        this.velYRequired=0;
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
        fixtureDef.filter.categoryBits= com.SpaceWreck.game.GameConstants.ENEMY_BIT;
        fixtureDef.filter.maskBits= com.SpaceWreck.game.GameConstants.WALLHORIZONTAL_BIT |
                com.SpaceWreck.game.GameConstants.PLAYER_BIT |
                com.SpaceWreck.game.GameConstants.PLAYERBULLET_BIT |
                com.SpaceWreck.game.GameConstants.WALLVERTICAL_BIT |
                    com.SpaceWreck.game.GameConstants.ENEMY_BIT |
        com.SpaceWreck.game.GameConstants.WALL_BIT|
                com.SpaceWreck.game.GameConstants.BOX_BIT;;
        body.createFixture(fixtureDef).setUserData(this);

    }
    public void updateVelocity(){
        float xDistance= (float) ((float)(player.getBody().getPosition().x - this.body.getPosition().x));
        float yDistance= (float) ((float)(player.getBody().getPosition().y - this.body.getPosition().y));
        float distance= (float) (Math.sqrt(Math.pow(xDistance, 2.0)+Math.pow(yDistance, 2.0)));

        float costheta= xDistance/distance;
        float sintheta= yDistance/distance;

         velXRequired= MathUtils.lerp(velXRequired, velX * costheta, 0.1f);
         velYRequired= MathUtils.lerp(velYRequired, velY * sintheta, 0.1f);

        //this.body.applyLinearImpulse(new Vector2(velXRequired,velYRequired ), this.body.getWorldCenter(), true);
        this.body.setLinearVelocity(velXRequired, velYRequired);
    }
    public void initializeTextures(){
        followEnemy1= atlas.findRegion("FollowEnemy1");
        followEnemy2= atlas.findRegion("FollowEnemy2");
        followEnemy3= atlas.findRegion("FollowEnemy3");
        followEnemy4= atlas.findRegion("FollowEnemy4");

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
