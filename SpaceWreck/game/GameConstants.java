package com.SpaceWreck.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by harsh on 06/20/17.
 */

public class GameConstants {

    public static final float ppm= 100.0f;
    public static final short PLAYER_BIT=1;
    public static final short ENEMY_BIT=2;
    public static final short PLAYERBULLET_BIT=4;
    public static final short ENEMYBULLET_BIT=8;
    public static final short STAR_BIT=16;
    public static final short WALLHORIZONTAL_BIT=32;
    public static final short WALLVERTICAL_BIT=64;
    public static final short FINISHLINE_BIT=128;
    public static final short WALL_BIT=256;
    public static final short POWERUP_BIT=512;
    public static final short BOX_BIT=1024;

    public static final float viewPortHeight=720;
    public static final float viewPortWidth=1280;

    public static final float aspectRatio= (float)(Gdx.graphics.getHeight()/(Gdx.graphics.getWidth() * 1.0));



}
