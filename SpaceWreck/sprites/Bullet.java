package com.SpaceWreck.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.SpaceWreck.screens.PlayScreen;

/**
 * Created by harsh on 06/20/17.
 */

public abstract class Bullet extends Sprite {
    protected PlayScreen playScreen;
    protected World world;
    protected com.SpaceWreck.sprites.Player player;
    protected SpriteBatch batch;
    protected TextureAtlas atlas;
    protected float velX;
    protected float velY;
    protected float x,y;
    protected float width, height;
    protected Body body;
    protected MassData mass;
    protected boolean isDead;


    public Bullet(PlayScreen playScreen, float x, float y){
        this.playScreen= playScreen;
        this.world= playScreen.getWorld();
        this.player= playScreen.getPlayer();
        this.atlas= playScreen.getGame().getAssetManager().get("Images/Player/PlayerAtlas.txt", TextureAtlas.class);
        this.batch= playScreen.getGame().batch;
        mass= new MassData();
        mass.mass=0.001f;
        this.x= x;
        this.y= y;
        this.isDead=false;
    }
    public boolean isActive(){
        if(playScreen.getGameCam().frustum.pointInFrustum(this.body.getPosition().x, this.body.getPosition().y, 0)){
            return true;
        }
        return false;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public Body getBody(){
        return this.body;
    }

    public abstract void update(float delta);
    public abstract void render(float delta);
    public abstract void setDirection();

}
