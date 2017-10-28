package com.SpaceWreck.managers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.SpaceWreck.screens.PlayScreen;
import com.SpaceWreck.sprites.FinishLine;
import com.SpaceWreck.sprites.Star;
import com.SpaceWreck.sprites.TwoXPowerUp;

/**
 * Created by harsh on 06/20/17.
 */

public class StaticEntityManager {
    private Array<com.SpaceWreck.sprites.Entity> entities;   //everything except boxes
    private static Array<TextureRegion> boxTextures;
    private com.SpaceWreck.screens.PlayScreen playScreen;
    private World world;
    private float discoTime =0;
    private float shootEverywhereTime=0;
    private boolean shootEverywhere;
    private TextureAtlas atlas;

    private boolean discoScreen=false;

    public StaticEntityManager(PlayScreen playScreen){
        this.playScreen= playScreen;
        this.world= playScreen.getWorld();
        this.shootEverywhere=false;
        atlas= playScreen.getGame().getAssetManager().get("Images/Player/BoxAtlas.txt", TextureAtlas.class);
        entities= new Array<com.SpaceWreck.sprites.Entity>();
        initializeBoxArray();
    }
    public void initializeBoxArray(){
        boxTextures= new Array<TextureRegion>();
        boxTextures.addAll(atlas.findRegion("abandoned"),
                atlas.findRegion("asphyxiated"),
                atlas.findRegion("black"),
                atlas.findRegion("blanked"),
                atlas.findRegion("boxed"),
                atlas.findRegion("breathless"),
                atlas.findRegion("broken"),
                atlas.findRegion("conquered"),
                atlas.findRegion("cursed"),
                atlas.findRegion("death"),
                atlas.findRegion("destruction"),
                atlas.findRegion("exile"),
                atlas.findRegion("fate"),
                atlas.findRegion("hopeless"),
                atlas.findRegion("intimidating"),
                atlas.findRegion("lost"),
                atlas.findRegion("miracle"),
                atlas.findRegion("shattered"),
                atlas.findRegion("traumatic"),
                atlas.findRegion("unexpected"),
                atlas.findRegion("violence"),
                atlas.findRegion("waiting"));
    }
    public void update(float delta){
        for (int i=0; i<entities.size; i++) {
            if(!(entities.get(i) instanceof FinishLine)){
                if(entities.get(i).isActive() && !entities.get(i).isHit()){
                    entities.get(i).update(delta);
                }
                else if(entities.get(i).isHit() ){
                    if(entities.get(i) instanceof TwoXPowerUp){
                        discoScreen=true;
                        TwoXPowerUp.playSound(true);

                    }
                    if(entities.get(i) instanceof com.SpaceWreck.sprites.FireAllPowerUp){
                        shootEverywhere=true;
                        TwoXPowerUp.playSound(true);
                    }
                    if(!(entities.get(i) instanceof com.SpaceWreck.sprites.Box)){
                        world.destroyBody(entities.get(i).getBody());
                        entities.removeIndex(i);
                    }

                    //Gdx.app.log("EntityManager", "isHit");
                }
            }

        }
        if(shootEverywhere && shootEverywhereTime<4.0f){
            playScreen.getPlayer().setShootEverywhere(shootEverywhere);
            shootEverywhereTime+=delta;
            if(shootEverywhereTime>4.0f){
                shootEverywhereTime=0;
                shootEverywhere=false;
                playScreen.getPlayer().setShootEverywhere(shootEverywhere);
                TwoXPowerUp.playSound(false);

            }
        }

        if(discoScreen && discoTime <5.0f){
            playScreen.triggerDiscoScreen(delta);
            playScreen.getPlayer().setNullifyHealth(true);
            discoTime +=delta;

            if(discoTime >=5.0f){
                discoTime =0;
                discoScreen=false;
                Star.resetValue();
                TwoXPowerUp.playSound(false);
                playScreen.getPlayer().setNullifyHealth(false);
            }
        }

    }
    public void render(float delta){
        for (int i=0; i<entities.size; i++) {
            if(!(entities.get(i) instanceof FinishLine)){
                if(entities.get(i).isActive() && !entities.get(i).isHit()){
                    entities.get(i).render(delta);
                }
                else if(entities.get(i).isHit() && !(entities.get(i) instanceof com.SpaceWreck.sprites.Box)){

                    world.destroyBody(entities.get(i).getBody());
                    entities.removeIndex(i);
                }

            }
            else{
                entities.get(i).render(delta);

            }
        }


    }
    public void addEntity(com.SpaceWreck.sprites.Entity entity){
        entities.add(entity);
        //Gdx.app.log(entities.size+"","");
    }

    public static Array<TextureRegion> getBoxTextures() {
        return boxTextures;
    }

    public static void setBoxTextures(Array<TextureRegion> boxTextures) {
        StaticEntityManager.boxTextures = boxTextures;
    }
}
