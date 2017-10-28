package com.SpaceWreck.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.SpaceWreck.game.GameConstants;
import com.SpaceWreck.sprites.Box;
import com.SpaceWreck.sprites.DiePowerUp;
import com.SpaceWreck.sprites.FollowEnemy1;
import com.SpaceWreck.sprites.FollowEnemy2;
import com.SpaceWreck.sprites.GreenEnemy;
import com.SpaceWreck.sprites.HealthPowerUp;
import com.SpaceWreck.sprites.Star;
import com.SpaceWreck.sprites.TwoXPowerUp;
import com.SpaceWreck.sprites.WallVertical;

/**
 * Created by harsh on 06/20/17.
 */

public class LevelContentManager {

    private com.SpaceWreck.levels.Level currentLevel;
    private com.SpaceWreck.screens.PlayScreen playScreen;
    private int points;

    public LevelContentManager(){

    }
    public void updateLevelContent(com.SpaceWreck.screens.PlayScreen playScreen, com.SpaceWreck.levels.Level currentLevel){
        points=0;
        this.playScreen= playScreen;
        this.currentLevel= currentLevel;
        loadWallsHorizontal();
        loadWallsVertical();
        loadFollowEnemies1();
        loadFollowEnemies2();
        loadStaticShootingEnemies();
        loadShootingEnemies();
        loadStars();
        loadFinishLine();
        loadPowerUps();
        loadWhiteEnemies();
        loadGreenEnemies();
        loadBoxes();
        currentLevel.setMaxPoints(points);
        Gdx.app.log(currentLevel.getLevelName(), points+"");


    }

    public void loadWallsHorizontal(){
        if(currentLevel.getMap().getLayers().get("WallsHorizontal") != null){
            for(MapObject object : currentLevel.getMap().getLayers().get("WallsHorizontal").getObjects().getByType(PolylineMapObject.class)){
                Polyline line= ((PolylineMapObject)object).getPolyline();
                playScreen.getWallManager().addWall(new com.SpaceWreck.sprites.WallHorizontal(playScreen, line));
            }
        }

    }
    public void loadWallsVertical(){
        if( currentLevel.getMap().getLayers().get("WallsVertical") != null){
            for(MapObject object : currentLevel.getMap().getLayers().get("WallsVertical").getObjects().getByType(PolylineMapObject.class)){
                Polyline line= ((PolylineMapObject)object).getPolyline();
                playScreen.getWallManager().addWall(new WallVertical(playScreen, line));
            }
        }

    }
    public void loadFollowEnemies1(){
        if(currentLevel.getMap().getLayers().get("FollowEnemies") != null){
            for(MapObject object : currentLevel.getMap().getLayers().get("FollowEnemies").getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect= ((RectangleMapObject)object).getRectangle();
                playScreen.getEnemyManager().addEnemy(new FollowEnemy1(playScreen, rect.x/ GameConstants.ppm, rect.y/GameConstants.ppm));
                calculateMaxPoints(FollowEnemy1.value);
            }
        }

    }
    public void loadFollowEnemies2(){
        if(currentLevel.getMap().getLayers().get("FollowEnemies2")!= null){
            for(MapObject object : currentLevel.getMap().getLayers().get("FollowEnemies2").getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect= ((RectangleMapObject)object).getRectangle();
                String str= object.getName();
                playScreen.getEnemyManager().addEnemy(new FollowEnemy2(playScreen, rect.x/ GameConstants.ppm, rect.y/GameConstants.ppm, str));
                calculateMaxPoints(FollowEnemy2.value);
            }
        }

    }
    public void loadStaticShootingEnemies(){
        if(currentLevel.getMap().getLayers().get("StaticShootingEnemies") != null){
            for(MapObject object : currentLevel.getMap().getLayers().get("StaticShootingEnemies").getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect= ((RectangleMapObject)object).getRectangle();
                playScreen.getEnemyManager().addEnemy(new com.SpaceWreck.sprites.StaticShootingEnemy(playScreen, rect.x/ GameConstants.ppm, rect.y/GameConstants.ppm));
                calculateMaxPoints(com.SpaceWreck.sprites.StaticShootingEnemy.value);
            }
        }



    }
    public void loadShootingEnemies(){
        if(currentLevel.getMap().getLayers().get("ShootingEnemies") != null){
            for(MapObject object : currentLevel.getMap().getLayers().get("ShootingEnemies").getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect= ((RectangleMapObject)object).getRectangle();
                playScreen.getEnemyManager().addEnemy(new com.SpaceWreck.sprites.ShootingEnemy(playScreen, rect.x/ GameConstants.ppm, rect.y/GameConstants.ppm));
                calculateMaxPoints(com.SpaceWreck.sprites.ShootingEnemy.value);
            }
        }


    }
    public void loadStars(){
        if(currentLevel.getMap().getLayers().get("Stars") != null){
            for(MapObject object : currentLevel.getMap().getLayers().get("Stars").getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect= ((RectangleMapObject)object).getRectangle();
                playScreen.getEntityManager().addEntity(new Star(playScreen, rect.x/ GameConstants.ppm, rect.y/GameConstants.ppm));
                calculateMaxPoints(Star.value);
            }
        }

    }

    public void loadFinishLine(){
        if(currentLevel.getMap().getLayers().get("GameEnd") != null){
            for(MapObject object : currentLevel.getMap().getLayers().get("GameEnd").getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect= ((RectangleMapObject)object).getRectangle();
                playScreen.getEntityManager().addEntity(new com.SpaceWreck.sprites.FinishLine(playScreen, rect.x/ GameConstants.ppm, rect.y/GameConstants.ppm));
            }
        }


    }
    public void loadPowerUps(){
        if(currentLevel.getMap().getLayers().get("PowerUps") != null){
            for(MapObject object : currentLevel.getMap().getLayers().get("PowerUps").getObjects().getByType(RectangleMapObject.class)){
                if(object.getName().equals("chargeUp")){
                    Rectangle rect= ((RectangleMapObject)object).getRectangle();
                    playScreen.getEntityManager().addEntity(new HealthPowerUp(playScreen, rect.x/ GameConstants.ppm, rect.y/GameConstants.ppm));
                }
                if(object.getName().equals("2x")){
                    Rectangle rect= ((RectangleMapObject)object).getRectangle();
                    playScreen.getEntityManager().addEntity(new TwoXPowerUp(playScreen, rect.x/ GameConstants.ppm, rect.y/GameConstants.ppm));
                }
                if(object.getName().equals("die")){
                    Rectangle rect= ((RectangleMapObject)object).getRectangle();
                    playScreen.getEntityManager().addEntity(new DiePowerUp(playScreen, rect.x/ GameConstants.ppm, rect.y/GameConstants.ppm));
                }
                if(object.getName().equals("fireAll")){
                    Rectangle rect= ((RectangleMapObject)object).getRectangle();
                    playScreen.getEntityManager().addEntity(new com.SpaceWreck.sprites.FireAllPowerUp(playScreen, rect.x/ GameConstants.ppm, rect.y/GameConstants.ppm));
                }
                if(object.getName().equals("treasureBox")){
                    Rectangle rect= ((RectangleMapObject)object).getRectangle();
                    playScreen.getEntityManager().addEntity(new com.SpaceWreck.sprites.TreasureBoxPowerUp(playScreen, rect.x/ GameConstants.ppm, rect.y/GameConstants.ppm));
                    calculateMaxPoints(com.SpaceWreck.sprites.TreasureBoxPowerUp.value);
                }


            }
        }
    }
    public void loadWhiteEnemies(){
        if(currentLevel.getMap().getLayers().get("WhiteEnemies") != null){
            for(MapObject object : currentLevel.getMap().getLayers().get("WhiteEnemies").getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect= ((RectangleMapObject)object).getRectangle();
                playScreen.getEnemyManager().addEnemy(new com.SpaceWreck.sprites.WhiteEnemy(playScreen, rect.x/ GameConstants.ppm, rect.y/GameConstants.ppm));
                calculateMaxPoints(com.SpaceWreck.sprites.WhiteEnemy.value);
            }
        }
    }
    public void loadGreenEnemies(){
        if(currentLevel.getMap().getLayers().get("GreenEnemies") != null){
            for(MapObject object : currentLevel.getMap().getLayers().get("GreenEnemies").getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect= ((RectangleMapObject)object).getRectangle();
                playScreen.getEnemyManager().addEnemy(new GreenEnemy(playScreen, rect.x/ GameConstants.ppm, rect.y/GameConstants.ppm));
                calculateMaxPoints(com.SpaceWreck.sprites.GreenEnemy.value);
            }
        }
    }
    public void loadBoxes(){
        if(currentLevel.getMap().getLayers().get("Boxes") != null){
            for(MapObject object : currentLevel.getMap().getLayers().get("Boxes").getObjects().getByType(RectangleMapObject.class)){
                Rectangle rect= ((RectangleMapObject)object).getRectangle();
                TextureRegion textureRegion= StaticEntityManager.getBoxTextures().get((int)(Math.random() * StaticEntityManager.getBoxTextures().size));
                playScreen.getEntityManager().addEntity(new Box(playScreen, textureRegion, rect.x/ GameConstants.ppm, rect.y/GameConstants.ppm));

            }
        }

    }
    public void calculateMaxPoints(int num){
        points+=num;
    }
}
