package com.SpaceWreck.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by harsh on 06/22/17.
 */

public abstract class Enemy extends Sprite {
    protected com.SpaceWreck.screens.PlayScreen playScreen;
    protected World world;
    protected Player player;
    protected TextureAtlas atlas;
    protected SpriteBatch batch;
    protected float x, y;
    protected float width, height;
    protected float velX, velY;
    protected Body body;
    protected boolean isShooting;
    protected float gapTime;
    protected MassData mass;
    protected float health;
    protected boolean isDead;
    protected boolean toBeDestroyed;


    public Enemy(com.SpaceWreck.screens.PlayScreen playScreen, float x, float y){
        this.playScreen= playScreen;
        this.world= playScreen.getWorld();
        this.player= playScreen.getPlayer();
        this.atlas= playScreen.getGame().getAssetManager().get("Images/Player/PlayerAtlas.txt");
        this.batch= playScreen.getGame().batch;
        this.isDead=false;
        this.toBeDestroyed=false;
        this.x= x;
        this.y=y;

    }

    public boolean isActive(){
        if(playScreen.getGameCam().frustum.pointInFrustum(this.body.getPosition().x, this.body.getPosition().y, 0)){
            return true;
        }
        return false;
    }
    public Body getBody(){
        return this.body;
    }

    public boolean isShooting() {
        return isShooting;
    }

    public void setShooting(boolean shooting) {
        isShooting = shooting;
    }

    public float getHealth() {
        return health;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setHealth(float health) {
        this.health = health;
    }
    public abstract void updateHealth();
    public abstract void update(float delta);
    public abstract void render(float delta);

    public void setToBeDestroyed(boolean flag){
        this.toBeDestroyed=flag;
    }
    public void destroyBody(){
        world.destroyBody(this.body);
    }
    public boolean getToBeDestroyed(){
        return this.toBeDestroyed;
    }

}
