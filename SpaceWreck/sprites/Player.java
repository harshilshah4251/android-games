package com.SpaceWreck.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.SpaceWreck.game.GameConstants;
import com.SpaceWreck.screens.PlayScreen;
import com.SpaceWreck.sprites.FollowEnemy1;

/**
 * Created by harsh on 06/20/17.
 */

public class Player extends Sprite {
    private PlayScreen playScreen;
    private World world;
    private TextureAtlas atlas;
    private SpriteBatch batch;

    private boolean upMove =false;
    private boolean downMove = false;
    private boolean leftMove= false;
    private boolean rightMove=false;
    private boolean isStatic= true;
    private boolean isDead;

    private TextureRegion rocketUp1;
    private TextureRegion rocketUp2;
    private TextureRegion rocketUp3;
    private TextureRegion rocketUp4;

    private TextureRegion rocketDown1;
    private TextureRegion rocketDown2;
    private TextureRegion rocketDown3;
    private TextureRegion rocketDown4;

    private TextureRegion rocketLeft1;
    private TextureRegion rocketLeft2;
    private TextureRegion rocketLeft3;
    private TextureRegion rocketLeft4;

    private TextureRegion rocketRight1;
    private TextureRegion rocketRight2;
    private TextureRegion rocketRight3;
    private TextureRegion rocketRight4;

    private TextureRegion rocketStationary1;
    private TextureRegion rocketStationary2;
    private TextureRegion rocketStationary3;
    private TextureRegion rocketStationary4;

    private Animation<TextureRegion> movingRight;
    private Animation<TextureRegion> movingLeft;
    private Animation<TextureRegion> movingUp;
    private Animation<TextureRegion> movingDown;
    private Animation<TextureRegion> stationary;
    private Array<TextureRegion> mode;

    private float stateTime= 0; //for animation
    private float width;
    private float height;
    private float initX;
    private float initY;
    private float maxVelX;
    private float maxVelY;
    private float bulletVel;
    private boolean isShooting;
    private boolean shootEverywhere;
    private float gapTime=0; //for bullets
    private MassData mass;

    private Body body;
    private float health;

    private int score;

    private Sound shootingSound;
    private Sound dieSound;

    private boolean hasFinishedLevel;
    private float deadDelay=0.0f;
    private boolean nullifyPoints;
    private boolean nullifyHealth;


    public Player(PlayScreen playScreen){
        this.playScreen= playScreen;
        this.world= playScreen.getWorld();
        this.atlas= playScreen.getGame().getAssetManager().get("Images/Player/PlayerAtlas.txt", TextureAtlas.class);
        batch= playScreen.getGame().batch;
        this.width= 40.0f/ GameConstants.ppm;
        this.height=15.0f/GameConstants.ppm;
        this.initX= playScreen.getCurrentLevel().getInitX();
        this.initY= playScreen.getCurrentLevel().getInitY();
        this.maxVelX = 5.0f;
        this.maxVelY = 5.0f;
        this.isDead=false;
        this.health=100.0f;
        this.score=0;
        this.bulletVel=10.0f;
        this.hasFinishedLevel=false;
        this.nullifyPoints=false;
        this.nullifyHealth=false;
        this.isShooting=false;
        this.shootEverywhere=false;
        initializeTextures();
        initializeAnimations();
        initializeSounds();
        defineBody();




    }

    public void initializeTextures(){
        rocketUp1= new TextureRegion(atlas.findRegion("RocketUp1"));
        rocketUp2= atlas.findRegion("RocketUp2");
        rocketUp3= atlas.findRegion("RocketUp3");
        rocketUp4= atlas.findRegion("RocketUp4");

        rocketDown1= atlas.findRegion("RocketDown1");
        rocketDown2= atlas.findRegion("RocketDown2");
        rocketDown3= atlas.findRegion("RocketDown3");
        rocketDown4= atlas.findRegion("RocketDown4");

        rocketLeft1= atlas.findRegion("RocketLeft1");
        rocketLeft2= atlas.findRegion("RocketLeft2");
        rocketLeft3= atlas.findRegion("RocketLeft3");
        rocketLeft4= atlas.findRegion("RocketLeft4");

        rocketRight1= atlas.findRegion("RocketRight1");
        rocketRight2= atlas.findRegion("RocketRight2");
        rocketRight3= atlas.findRegion("RocketRight3");
        rocketRight4= atlas.findRegion("RocketRight4");

        rocketStationary1= new TextureRegion(atlas.findRegion("Stationary1"));
        rocketStationary2= new TextureRegion(atlas.findRegion("Stationary2"));
        rocketStationary3= new TextureRegion(atlas.findRegion("Stationary3"));

    }

    public void initializeAnimations(){
        mode= new Array<TextureRegion>();
        mode.addAll(rocketDown1, rocketDown2, rocketDown3, rocketDown4);
        movingDown= new Animation<TextureRegion>(0.25f, mode, Animation.PlayMode.LOOP_PINGPONG);
        mode.clear();
        mode.addAll(rocketUp1, rocketUp2, rocketUp3, rocketUp4);
        movingUp= new Animation<TextureRegion>(0.25f, mode, Animation.PlayMode.LOOP_PINGPONG);
        mode.clear();
        mode.addAll(rocketLeft1, rocketLeft2, rocketLeft3, rocketLeft4);
        movingLeft= new Animation<TextureRegion>(0.25f, mode, Animation.PlayMode.LOOP_PINGPONG);
        mode.clear();
        mode.addAll(rocketRight1, rocketRight2, rocketRight3, rocketRight4);
        movingRight= new Animation<TextureRegion>(0.25f, mode, Animation.PlayMode.LOOP_PINGPONG);
        mode.clear();
        mode.addAll(rocketStationary1, rocketStationary2, rocketStationary3);
        stationary= new Animation<TextureRegion>(0.166f, mode, Animation.PlayMode.LOOP_PINGPONG);
        mode.clear();

    }
    public void initializeSounds(){
        shootingSound= playScreen.getGame().getAssetManager().get("Sounds/laserShot.wav", Sound.class);
        dieSound=playScreen.getGame().getAssetManager().get("Sounds/wickedwitchlaugh.wav", Sound.class);
    }
    public void defineBody(){
        BodyDef bodyDef= new BodyDef();
        bodyDef.position.set(initX, initY);
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        body= world.createBody(bodyDef);

        FixtureDef fixtureDef= new FixtureDef();
        PolygonShape shape= new PolygonShape();
        shape.setAsBox(width/2.0f, height/2.0f);

        fixtureDef.shape= shape;
        mass= new MassData();
        mass.mass=15.0f;
        fixtureDef.restitution=0.5f;
        fixtureDef.friction=0.0f;
        fixtureDef.filter.categoryBits= GameConstants.PLAYER_BIT;
        fixtureDef.filter.maskBits= GameConstants.WALLHORIZONTAL_BIT |
                com.SpaceWreck.game.GameConstants.ENEMY_BIT |
                GameConstants.ENEMYBULLET_BIT |
                GameConstants.STAR_BIT |
                GameConstants.FINISHLINE_BIT |
                GameConstants.WALLVERTICAL_BIT |
                GameConstants.WALLHORIZONTAL_BIT |
                GameConstants.WALL_BIT |
                com.SpaceWreck.game.GameConstants.POWERUP_BIT |
        GameConstants.BOX_BIT;
        body.setBullet(true);
        body.createFixture(fixtureDef).setUserData(this);

    }
    public void update(float delta){
        if(!isDead) {
            handleInput();
            healthCheck();
            updateSpriteSize();
            updatePosition();
            updateDirection();
            updateShooting(delta);
            playSound();
        }

        //Gdx.app.log(this.score+"","");



       // Gdx.app.log(this.getX()+":"+this.getY(), this.getWidth()+":"+this.getHeight());

    }
    public void render(float delta){

        if(!this.isDead) {
            // Gdx.app.log(this.body.getPosition().x+":"+this.body.getPosition().y, this.getWidth()+"+"+this.getHeight());
            updateKeyFrame(delta);
            this.draw(batch);

        }    /*this.getTexture(), this.body.getPosition().x- this.getWidth()/2.0f, this.body.getPosition().y- this.getHeight()/2.0f, this.getWidth() * GameConstants.ppm, this.getHeight()*GameConstants.ppm*/



    }
    public void playSound(){
        if(this.isShooting && !this.isDead){
            shootingSound.play();
        }
//        else{
//            shootingSound.stop();
//        }
    }
    public void handleInput(){
        if(upMove && this.body.getLinearVelocity().y< maxVelY){
            this.body.setLinearVelocity(0, this.body.getLinearVelocity().y);
            this.body.applyLinearImpulse(new Vector2(0.0f, 0.2f), this.body.getWorldCenter(), true);
            if(Gdx.input.getAccelerometerY() > 0){
                this.body.applyForce(new Vector2(Gdx.input.getAccelerometerY()*10, 0),this.body.getWorldCenter(), true);
            }
            if(Gdx.input.getAccelerometerY() < 0){
                this.body.applyForce(new Vector2(Gdx.input.getAccelerometerY()*10, 0), this.body.getWorldCenter(), true);
            }

            //Gdx.app.log("input", "up");
        }
        else if(downMove && this.body.getLinearVelocity().y > -maxVelY){
            this.body.setLinearVelocity(0, this.body.getLinearVelocity().y);
            this.body.applyLinearImpulse(new Vector2(0.0f, -0.2f), this.body.getWorldCenter(), true);
            if(Gdx.input.getAccelerometerY() > 0){
                this.body.applyForce(new Vector2(Gdx.input.getAccelerometerY()*10, 0),this.body.getWorldCenter(),  true);
            }
            if(Gdx.input.getAccelerometerY() < 0){
                this.body.applyForce(new Vector2(Gdx.input.getAccelerometerY()*10, 0),this.body.getWorldCenter(), true);
            }
           // Gdx.app.log("input", "down");
        }
        else if(leftMove && this.body.getLinearVelocity().x > -maxVelX ){
            this.body.setLinearVelocity(this.body.getLinearVelocity().x, 0);
            this.body.applyLinearImpulse(new Vector2(-0.2f, 0), this.body.getWorldCenter(), true);
            if(Gdx.input.getAccelerometerX() > 0){
                this.body.applyForce(new Vector2(0, -Gdx.input.getAccelerometerX()*10), this.body.getWorldCenter(),true);
            }
            if(Gdx.input.getAccelerometerX() < 0){
                this.body.applyForce(new Vector2(0, -Gdx.input.getAccelerometerX()*10),this.body.getWorldCenter(), true);
            }
            //Gdx.app.log("input", "left");
        }
        else if(rightMove && this.body.getLinearVelocity().x< maxVelX){
            this.body.setLinearVelocity(this.body.getLinearVelocity().x, 0);
            this.body.applyLinearImpulse(new Vector2(0.2f, 0), this.body.getWorldCenter(), true);
            if(Gdx.input.getAccelerometerX() > 0){
                this.body.applyForce(new Vector2(0, -Gdx.input.getAccelerometerX()*10), this.body.getWorldCenter(),true);
            }
            if(Gdx.input.getAccelerometerX() < 0){
                this.body.applyForce(new Vector2(0, -Gdx.input.getAccelerometerX()*10),this.body.getWorldCenter(), true);
            }
            //Gdx.app.log("input", "right");
        }
        else if(isStatic){
            this.body.setLinearVelocity(0, 0);
        }

    }
    public void updateScore(Object object){
        if(!nullifyPoints) {
            if (object instanceof com.SpaceWreck.sprites.Star) {
                this.score += com.SpaceWreck.sprites.Star.value;
            } else if (object instanceof com.SpaceWreck.sprites.FollowEnemy1) {
                this.score += FollowEnemy1.value;
            } else if (object instanceof FollowEnemy2) {
                this.score += FollowEnemy2.value;
            } else if (object instanceof StaticShootingEnemy) {
                this.score += StaticShootingEnemy.value;
            } else if (object instanceof ShootingEnemy) {
                this.score += ShootingEnemy.value;
            } else if(object instanceof WhiteEnemy){
                this.score+= WhiteEnemy.value;
            } else if(object instanceof GreenEnemy){
                this.score+=GreenEnemy.value;
            }
            //Gdx.app.log("Update Score", "invoked");
        }
    }
    public void updateKeyFrame(float delta){
        stateTime+=delta;
        if(upMove){
            this.setRegion(movingUp.getKeyFrame(stateTime));
        }
        else if(downMove){
            this.setRegion(movingDown.getKeyFrame(stateTime));
        }
        else if(leftMove){
            this.setRegion(movingLeft.getKeyFrame(stateTime));
        }
        else if(rightMove){
            this.setRegion(movingRight.getKeyFrame(stateTime));
        }
        else if (isStatic){
            this.setRegion(stationary.getKeyFrame(stateTime));
        }
    }
    public void updatePosition(){
      //  if(leftMove || rightMove){
        this.setX(this.body.getPosition().x- this.getWidth()/2.0f);
        this.setY(this.body.getPosition().y- this.getHeight()/2.0f);
        //}
       // else if(upMove || downMove){
         //   this.setX(this.body.getPosition().x- this.)
        //}
    }
    public void healthCheck(){
        if(health<=0){
            this.isDead=true;
            this.body.setAwake(false);
            dieSound.play();
            //Gdx.app.log("Killed", "Player");
        }
    }
    public void updateHealth(){
        if(!nullifyHealth){
            health-=1;
        }

    }
    public void setToDestroy(boolean flag){
        deadDelay+=Gdx.graphics.getDeltaTime();
        nullifyPoints=true;
        this.isShooting=false;
        if(flag && deadDelay>0.5f){
            this.body.setAwake(false);
        }
    }
    public void updateSpriteSize(){
        if(leftMove || rightMove){
            this.setSize(width, height);
        }
        else if(upMove || downMove){
            this.setSize(height, width);
        }
        else if(isStatic){
            this.setSize(width, height);
        }
    }
    public void updateDirection(){
        if(leftMove){
            this.body.setTransform(this.body.getPosition(), (float)(2.0* Math.PI));
        }
        else if(rightMove){
            this.body.setTransform(this.body.getPosition(), (float)(0));
        }
        else if(downMove){
            this.body.setTransform(this.body.getPosition(), (float)(3.0* Math.PI/2.0));
        }
        else if(upMove){
            this.body.setTransform(this.body.getPosition(), (float)(Math.PI/2.0));
        }
        else if(isStatic){
            this.body.setTransform(this.body.getPosition(), (float)(0));
        }
    }

    public boolean isUpMove() {
        return upMove;
    }

    public void setUpMove(boolean upMove) {
        if(downMove){
            this.body.setLinearVelocity(0,0);
        }
        this.upMove = upMove;
        this.downMove= false;
        this.leftMove= false;
        this.rightMove= false;
        this.isStatic= false;
    }

    public boolean isDownMove() {
        return downMove;
    }

    public void setDownMove(boolean downMove) {
        if(upMove){
            this.body.setLinearVelocity(0,0);
        }
        this.downMove = downMove;
        this.upMove = false;
        this.leftMove= false;
        this.rightMove= false;
        this.isStatic= false;
    }

    public boolean isLeftMove() {
        return leftMove;
    }

    public void setLeftMove(boolean leftMove) {
        if(rightMove){
            this.body.setLinearVelocity(0,0);
        }
        this.leftMove = leftMove;
        this.upMove = false;
        this.downMove= false;
        this.rightMove= false;
        this.isStatic= false;
    }

    public boolean isRightMove() {
        return rightMove;
    }

    public void setRightMove(boolean rightMove) {
        if(leftMove){
            this.body.setLinearVelocity(0,0);
        }
        this.rightMove = rightMove;
        this.upMove = false;
        this.downMove= false;
        this.leftMove= false;
        this.isStatic= false;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
        this.upMove= false;
        this.downMove= false;
        this.leftMove= false;
        this.rightMove= false;
    }
    public void setShooting(boolean flag){
        isShooting=flag;
        //Gdx.app.log("isShooting" , isShooting+"");
    }

    public void updateShooting(float delta){
        if(isShooting) {
            gapTime += delta;
            if (gapTime >= 0.1f) {
                if(!shootEverywhere){
                    playScreen.getBulletManager().addBullet(new PlayerBullet(playScreen, this.body.getPosition().x, this.body.getPosition().y));
                }
                else if(shootEverywhere){
                    playScreen.getBulletManager().addBullet(new PlayerBullet(playScreen, this.body.getPosition().x + this.width/2.0f, this.body.getPosition().y, (float)(bulletVel * Math.cos(0)) , (float)(bulletVel * Math.sin(0))));
                    playScreen.getBulletManager().addBullet(new PlayerBullet(playScreen, this.body.getPosition().x + this.width/4.0f, this.body.getPosition().y + this.height/4.0f, (float)(bulletVel * Math.cos(45)) , (float)(bulletVel * Math.sin(45))));
                    playScreen.getBulletManager().addBullet(new PlayerBullet(playScreen, this.body.getPosition().x, this.body.getPosition().y+this.height/2.0f, (float)(bulletVel * Math.cos(90)) , (float)(bulletVel * Math.sin(90))));
                    playScreen.getBulletManager().addBullet(new PlayerBullet(playScreen, this.body.getPosition().x-this.width/4.0f, this.body.getPosition().y+this.height/4.0f, (float)(bulletVel * Math.cos(135)) , (float)(bulletVel * Math.sin(135))));
                    playScreen.getBulletManager().addBullet(new PlayerBullet(playScreen, this.body.getPosition().x-this.width/2.0f, this.body.getPosition().y, (float)(bulletVel * Math.cos(180)) , (float)(bulletVel * Math.sin(180))));
                    playScreen.getBulletManager().addBullet(new PlayerBullet(playScreen, this.body.getPosition().x-this.width/4.0f, this.body.getPosition().y-this.height/4.0f, (float)(bulletVel * Math.cos(225)) , (float)(bulletVel * Math.sin(225))));
                    playScreen.getBulletManager().addBullet(new PlayerBullet(playScreen, this.body.getPosition().x, this.body.getPosition().y-this.height/2.0f, (float)(bulletVel * Math.cos(270)) , (float)(bulletVel * Math.sin(270))));
                    playScreen.getBulletManager().addBullet(new PlayerBullet(playScreen, this.body.getPosition().x+this.width+4.0f, this.body.getPosition().y-this.height/4.0f, (float)(bulletVel * Math.cos(315)) , (float)(bulletVel * Math.sin(315))));
                }

                gapTime=0;
            }
        }
    }
    public boolean isActive(){
        if(playScreen.getGameCam().frustum.pointInFrustum(this.body.getPosition().x, this.body.getPosition().y, 0)){
            return true;
        }
        return false;

    }

//    @Override
//    public float getWidth() {
//        return width;
//    }

    public void setWidth(float width) {
        this.width = width;
    }

//    @Override
//    public float getHeight() {
//        return height;
//    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getMaxVelX() {
        return maxVelX;
    }

    public void setMaxVelX(float maxVelX) {
        this.maxVelX = maxVelX;
    }

    public float getMaxVelY() {
        return maxVelY;
    }

    public void setMaxVelY(float maxVelY) {
        this.maxVelY = maxVelY;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isHasFinishedLevel() {
        return hasFinishedLevel;
    }

    public void setHasFinishedLevel(boolean hasFinishedLevel) {
        this.hasFinishedLevel = hasFinishedLevel;
    }

    public void setDead(boolean dead) {
        this.isDead=dead;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public boolean isShootEverywhere() {
        return shootEverywhere;
    }

    public void setShootEverywhere(boolean shootEverywhere) {
        this.shootEverywhere = shootEverywhere;
    }

    public boolean isNullifyHealth() {
        return nullifyHealth;
    }

    public void setNullifyHealth(boolean nullifyHealth) {
        this.nullifyHealth = nullifyHealth;
    }


}
