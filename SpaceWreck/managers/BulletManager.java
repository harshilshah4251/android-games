package com.SpaceWreck.managers;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by harsh on 06/20/17.
 */

public class BulletManager {
    private Array<com.SpaceWreck.sprites.Bullet> bullets;
    private com.SpaceWreck.screens.PlayScreen playScreen;
    private World  world;
    public BulletManager(com.SpaceWreck.screens.PlayScreen playScreen){
        bullets= new Array<com.SpaceWreck.sprites.Bullet>();
        this.playScreen= playScreen;
        this.world= playScreen.getWorld();
    }

    public void update(float delta){
        for (int i=0; i<bullets.size; i++) {
            if(bullets.get(i).isActive() && !bullets.get(i).isDead()){
                bullets.get(i).update(delta);
            }
            else if(!bullets.get(i).isActive() || bullets.get(i).isDead() ){
                world.destroyBody(bullets.get(i).getBody());
                bullets.removeIndex(i);
            }
            if(bullets.size>20){
                while(bullets.size>20){
                    world.destroyBody(bullets.get(0).getBody());
                    bullets.removeIndex(0);
                }
            }
        }
        //Gdx.app.log(bullets.size+"","");
    }
    public void render(float delta){
        for (int i=0; i<bullets.size; i++) {
            if(bullets.get(i).isActive() && !bullets.get(i).isDead()){
                bullets.get(i).render(delta);
            }
            else if(!bullets.get(i).isActive() || bullets.get(i).isDead()){
                world.destroyBody(bullets.get(i).getBody());
                bullets.removeIndex(i);
            }
            if(bullets.size>20){
                while(bullets.size>20){
                    world.destroyBody(bullets.get(0).getBody());
                    bullets.removeIndex(0);
                }
            }
        }
    }
    public void addBullet(com.SpaceWreck.sprites.Bullet bullet){
        bullets.add(bullet);
        //Gdx.app.log("BulletAdded", "Manager");
    }


}
