package com.SpaceWreck.levels;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Created by harsh on 06/20/17.
 */

public class Level4 extends  Level {

    public Level4(TiledMap map){
        super(map);
        this.levelName="Ultra Uranus";
        this.initX= 80.0f/ com.SpaceWreck.game.GameConstants.ppm;
        this.initY= 920.0f/ com.SpaceWreck.game.GameConstants.ppm;
        this.levelNum=4;
        this.color= Color.CYAN;
        this.locked= true;
        this.unlockScore= 45000;
        this.highScore= com.SpaceWreck.managers.LevelManager.data.get(this.levelNum);

        setThresholds();
    }
    public void setThresholds(){
        this.bestScore= 10000;
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
