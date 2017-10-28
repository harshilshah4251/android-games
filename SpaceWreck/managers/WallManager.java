package com.SpaceWreck.managers;

import com.badlogic.gdx.utils.Array;

/**
 * Created by harsh on 06/20/17.
 */

public class WallManager {
    private Array<com.SpaceWreck.sprites.Wall> walls;
    public WallManager(){
        walls= new Array<com.SpaceWreck.sprites.Wall>();
    }
    public void addWall(com.SpaceWreck.sprites.Wall wall){
        walls.add(wall);
        //Gdx.app.log(wall.getX()+":" + wall.getY(),wall.getHalfLength()+"halfLength");
    }
}
