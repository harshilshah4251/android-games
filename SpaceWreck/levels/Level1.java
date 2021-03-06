package com.SpaceWreck.levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.SpaceWreck.game.GameConstants;
import com.SpaceWreck.managers.LevelManager;

/**
 * Created by harsh on 06/20/17.
 */

public class Level1 extends  Level{

    public Level1(TiledMap map){
        super(map);
        this.levelName="Elegant Earth";
        this.initX= 80.0f/ GameConstants.ppm;
        this.initY= 40.0f/GameConstants.ppm;
        this.levelNum=1;
        this.highScore= LevelManager.data.get(this.levelNum);
        this.color= Color.FOREST;
        this.locked= false;
        this.unlockScore=0;
        setThresholds();
    }
    public void setThresholds(){
        this.bestScore= 15000;
        this.goodScore=9000;
        this.badScore=7000;
        this.worstScore=0;
    }
    public String getPerformance(int score){
        if(score>=bestScore){
            this.perfColor= this.best;
            return bestPerf.get((int)(Math.random()*bestPerf.size));
        }
        else if(score>=goodScore){
            this.perfColor= this.good;
            return goodPerf.get((int)(Math.random()*goodPerf.size));
        }
        else if(score>=badScore){
            this.perfColor= this.bad;
            return badPerf.get((int)(Math.random()*badPerf.size));
        }
        else if(score>=worstScore){
            this.perfColor= this.worst;
            return worstPerf.get((int) (Math.random()*worstPerf.size));
        }

       return null;
    }
    public Color getPerfColor(){
        return this.perfColor;
    }

}
