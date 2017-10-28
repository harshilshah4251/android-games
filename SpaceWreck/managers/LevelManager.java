package com.SpaceWreck.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.SpaceWreck.levels.Level3;
import com.SpaceWreck.levels.Level4;

import java.io.IOException;

/**
 * Created by harsh on 06/20/17.
 */

public class LevelManager {


    private TmxMapLoader mapLoader;
    private com.SpaceWreck.levels.Level currentLevel;
    private com.SpaceWreck.levels.Level1 level1;
    private com.SpaceWreck.levels.Level2 level2;
    private Level3 level3;
    private Level4 level4;
    private com.SpaceWreck.levels.Level5 level5;
    private Array<com.SpaceWreck.levels.Level> levels;

    private LevelContentManager contentManager;
    public static FileHandle dataFile = Gdx.files.local("DataFile.txt");
    public static Array<Integer> data;

    private static int totalPoints;


    public LevelManager(){

        mapLoader= new TmxMapLoader();
        levels= new Array<com.SpaceWreck.levels.Level>();




        data= new Array<Integer>();
        if(!dataFile.exists()){
            try {
                dataFile.file().createNewFile();
                dataFile.writeString("0\n",true);
                dataFile.writeString("0\n",true);
                dataFile.writeString("0\n",true);
                dataFile.writeString("0\n",true);
                dataFile.writeString("0\n",true);
                dataFile.writeString("0\n",true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(String str: dataFile.readString().split("\n")){
            data.add(Integer.parseInt(str));
        }
        //Gdx.app.log(data.toString(), "");
        initializeLevels();
        setLockStatus();
        contentManager= new LevelContentManager();


    }
    public void initializeLevels(){
        level1= new com.SpaceWreck.levels.Level1(mapLoader.load("Images/Level1.tmx"));
        level2= new com.SpaceWreck.levels.Level2(mapLoader.load("Images/Level2.tmx"));
        level3= new com.SpaceWreck.levels.Level3(mapLoader.load("Images/Level3.tmx"));
        level4= new com.SpaceWreck.levels.Level4(mapLoader.load("Images/Level4.tmx"));
        level5= new com.SpaceWreck.levels.Level5(mapLoader.load("Images/Level5.tmx"));
        levels.addAll(null, level1, level2, level3, level4, level5);   //to make stuff easier

        totalPoints= data.get(0);
        //totalPoints= 99999;

       // Gdx.app.log("LevelManager", "LevelsLoaded");
    }
    public void setLockStatus(){
        if(totalPoints>level2.getUnlockScore()){
            level2.setLocked(false);
        }
        if (totalPoints>level3.getUnlockScore()){
            level3.setLocked(false);
        }
        if(totalPoints>level4.getUnlockScore()){
            level4.setLocked(false);
        }
        if(totalPoints>level5.getUnlockScore()){
            level5.setLocked(false);
        }
    }
    public void updateLevel(com.SpaceWreck.screens.PlayScreen playScreen, int levelNum){

            currentLevel= levels.get(levelNum);
            contentManager.updateLevelContent(playScreen, currentLevel);

    }
    public static void recalculate(){
        totalPoints=0;
        for (int i=1; i<data.size; i++) {
            totalPoints+=data.get(i);
        }
        data.set(0, totalPoints);
    }



    //GETTERS AND SETTERS


    public com.SpaceWreck.levels.Level1 getLevel1() {
        return level1;
    }

    public com.SpaceWreck.levels.Level2 getLevel2() {
        return level2;
    }

    public com.SpaceWreck.levels.Level3 getLevel3() {
        return level3;
    }

    public com.SpaceWreck.levels.Level4 getLevel4() {
        return level4;
    }

    public com.SpaceWreck.levels.Level5 getLevel5() {
        return level5;
    }

    public static int getTotalPoints() {
        return totalPoints;
    }

    public static  void setTotalPoints(int totalPoints) {
        totalPoints = totalPoints;
    }

    public Array<com.SpaceWreck.levels.Level> getLevels() {
        return levels;
    }
    public com.SpaceWreck.levels.Level getNextLevel(){
        return levels.get(currentLevel.getLevelNum()+1);
    }

    public com.SpaceWreck.levels.Level getCurrentLevel(){
        return this.currentLevel;
    }


}
