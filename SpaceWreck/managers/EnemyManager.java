package com.SpaceWreck.managers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.SpaceWreck.screens.PlayScreen;
import com.SpaceWreck.sprites.Enemy;
import com.SpaceWreck.sprites.Player;

/**
 * Created by harsh on 06/20/17.
 */

public class EnemyManager {
    private Array<Enemy> enemies;
    private PlayScreen playScreen;
    private World world;
    private Player player;
    private int activeEnemies;
    private Music spaceAlarm;
    private float stateTime=0.0f;

    public EnemyManager(PlayScreen playScreen){
        enemies= new Array<Enemy>();
        this.playScreen= playScreen;
        this.world= playScreen.getWorld();
        this.player= playScreen.getPlayer();
        this.activeEnemies=0;
        this.spaceAlarm= playScreen.getGame().getAssetManager().get("Sounds/spaceAlarm.mp3");
        this.spaceAlarm.setLooping(true);

    }

    public void update(float delta){
        activeEnemies=0;
        for(int i=0; i< enemies.size; i++){
            if(enemies.get(i).isActive() && !enemies.get(i).isDead() && !player.isDead()){
                enemies.get(i).update(delta);
                activeEnemies++;
            }
            if(enemies.get(i).isDead()){
                player.updateScore(enemies.get(i));
                enemies.get(i).setToBeDestroyed(true);
//                world.destroyBody(enemies.get(i).getBody());
//                enemies.removeIndex(i);
            }


        }
        for(int i=0; i< enemies.size; i++){
            if(enemies.get(i).getToBeDestroyed()){
                enemies.get(i).destroyBody();
                enemies.removeIndex(i);
            }
        }
        if(activeEnemies>0 && (!player.isDead() || !player.isHasFinishedLevel())){
            this.playSound(true);
        }
        else{
            this.playSound(false);
        }
    }
    public void render(float delta){
        for(int i=0; i< enemies.size; i++){
            if(enemies.get(i).isActive() && !enemies.get(i).isDead() && !player.isDead()){
                enemies.get(i).render(delta);
                //enemies.get(i).playSound(true);
            }
            else if(!enemies.get(i).isActive() && !enemies.get(i).isDead() && !player.isDead()){
                //enemies.get(i).playSound(false);
            }
            else if(enemies.get(i).isDead()){
                player.updateScore(enemies.get(i));
                enemies.get(i).setToBeDestroyed(true);
//                world.destroyBody(enemies.get(i).getBody());
//                enemies.removeIndex(i);
               // enemies.get(i).playSound(false);
            }



        }
        for(int i=0; i< enemies.size; i++){
            if(enemies.get(i).getToBeDestroyed()){
                enemies.get(i).destroyBody();
                enemies.removeIndex(i);
            }
        }

    }
    public void playSound(boolean play){
        if(play){
            this.spaceAlarm.play();
        }
        else{
            this.spaceAlarm.stop();
        }

    }

    public void addEnemy(Enemy enemy){
        enemies.add(enemy);
    }

}
