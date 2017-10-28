package com.SpaceWreck.sprites;

import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.physics.box2d.World;
import com.SpaceWreck.screens.PlayScreen;

/**
 * Created by harsh on 06/21/17.
 */

public class Wall {
    protected PlayScreen playScreen;
    protected World world;
    protected float x;
    protected float y;
    protected float halfLength;
    public Wall(PlayScreen playScreen, Polyline line){
        this.playScreen= playScreen;
        this.world= playScreen.getWorld();
        this.x= line.getX();
        this.y= line.getY();
        this.halfLength= line.getLength()/2.0f;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHalfLength() {
        return halfLength;
    }
}
