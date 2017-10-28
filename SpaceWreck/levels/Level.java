package com.SpaceWreck.levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;

/**
 * Created by harsh on 06/20/17.
 */

public class Level  {

    protected TiledMap map;
    protected float initX;
    protected float initY;
    protected int highScore;
    protected String levelName;
    protected int levelNum;
    protected Color color;



    private boolean temp=true;

    protected Array<String> bestPerf;
    protected Array<String> goodPerf;
    protected Array<String> badPerf;
    protected Array<String> worstPerf;


    protected int bestScore;
    protected int goodScore;
    protected int badScore;
    protected int worstScore;

    public  Color best= Color.GOLDENROD;
    public  Color good= Color.ROYAL;
    public  Color bad= Color.GREEN;
    public  Color worst= Color.RED;

    protected Color perfColor;

    protected boolean locked;
    protected int unlockScore;
    protected static int maxPoints;


    public Level(TiledMap map){
        this.map= map;
        initializeArrays();

    }
    public void initializeArrays(){
        bestPerf= new Array<String>();
        bestPerf.addAll("FANTASTIC", "AMAZING", "FABULOUS", "GORGEOUS", "BEAUTIFUL", "KILLIn It", "DAREDEVIL", "HERCULEAN", "INVINCIBLE", "UNSTOPPABLE", "Im BACK");
        goodPerf= new Array<String>();
        goodPerf.addAll("OKkKkK", "Goody", "Lazy", "Cool", "Blasty", "Chilly");
        badPerf= new Array<String>();
        badPerf.addAll("lOl", "WOW", "GiveUp", "BLNT", "OMG");
        worstPerf= new Array<String>();
        worstPerf.addAll("ULTIM8 GAMER", "UR THE BEST", "HAHAHA", "ROFL", "NO COMMENTS");
    }
    public String getPerformance(int score){
        return null;
    }

    public int getLevelNum() {
        return levelNum;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public String getLevelName() {
        return levelName;
    }

    public TiledMap getMap(){
        return this.map;
    }

    public float getInitX() {
        return initX;
    }

    public float getInitY() {
        return initY;
    }

    public void dispose(){
        this.getMap().dispose();
    }

    public Color getColor() {
        return color;
    }
    public Color getPerfColor(){
        return null;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public int getUnlockScore() {
        return unlockScore;
    }

    public void setUnlockScore(int unlockScore) {
        this.unlockScore = unlockScore;
    }

    public int getBestScore() {
        return bestScore;
    }

    public int getGoodScore() {
        return goodScore;
    }

    public int getBadScore() {
        return badScore;
    }

    public int getWorstScore() {
        return worstScore;
    }

    public static int getMaxPoints() {
        return maxPoints;
    }

    public static void setMaxPoints(int maxPoints) {
        Level.maxPoints = maxPoints;
    }
}
