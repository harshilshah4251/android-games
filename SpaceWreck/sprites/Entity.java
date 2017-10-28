package com.SpaceWreck.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.SpaceWreck.screens.PlayScreen;

/**
 * Created by harsh on 06/20/17.
 */

public abstract class Entity extends Sprite{
    protected PlayScreen playScreen;
    protected World world;
    protected com.SpaceWreck.sprites.Player player;
    protected float x;
    protected float y;
    protected float width, height;
    protected Body body;
    protected boolean isHit;

    public  Entity(PlayScreen playScreen, float x, float y){
        this.playScreen= playScreen;
        this.world= playScreen.getWorld();
        this.player=playScreen.getPlayer();
        this.x= x;
        this.y=y;
    }
    public boolean isActive(){
        if(playScreen.getGameCam().frustum.pointInFrustum(this.body.getPosition().x, this.body.getPosition().y, 0)){
            return true;
        }
        return false;
    }
    public abstract  void update(float delta);
    public abstract  void render(float delta);
    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }
    public Body getBody(){
        return this.body;
    }


}
