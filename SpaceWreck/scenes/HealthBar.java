package com.SpaceWreck.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.SpaceWreck.screens.PlayScreen;
import com.SpaceWreck.sprites.Player;

/**
 * Created by harsh on 06/26/17.
 */

public class HealthBar {
    private PlayScreen playScreen;
    private Player player;
    private float playerHealth;
    private ShapeRenderer renderer;
    private float width, height;
    private Color safe;
    private Color neutral;
    private Color risk;
    private Color state;

    public HealthBar(PlayScreen playScreen){
        this.playScreen= playScreen;
        this.player= playScreen.getPlayer();
        this.playerHealth= player.getHealth();
        renderer= new ShapeRenderer();

        safe=Color.GREEN;
        neutral= Color.ORANGE;
        risk= Color.RED;

        state= safe;

        width= Gdx.graphics.getWidth();
        height= 3.0f;
//        width= 1.0f;
//        height= Gdx.graphics.getHeight();

    }

    public void update(float delta){

        this.playerHealth= player.getHealth();
        this.width= MathUtils.lerp(this.width,(playerHealth/100.0f)* Gdx.graphics.getWidth() , 0.1f);
        //this.height= MathUtils.lerp(this.height,(playerHealth/100.0f)* Gdx.graphics.getHeight() , 0.1f);

        if(playerHealth>70.0f){
            state=safe;
        }
        else if(playerHealth>40.0f){
            state= neutral;
        }
        else if(playerHealth>=0.0f){
            state= risk;
        }


    }
    public void render(float delta){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(state);
        renderer.rect(0, Gdx.graphics.getHeight()- this.height, this.width, this.height);
        //renderer.rect(0, 0, this.width, this.height);
        renderer.end();
    }

    public void dispose(){
        renderer.dispose();
    }
}
