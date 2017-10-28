package com.SpaceWreck.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.SpaceWreck.screens.PlayScreen;

/**
 * Created by harsh on 06/20/17.
 */

public abstract class VisibleStaticEntity extends Entity {
    protected TextureAtlas atlas;
    protected SpriteBatch batch;

    public VisibleStaticEntity(PlayScreen playScreen, float x, float y){
        super(playScreen, x, y);
        this.atlas =  playScreen.getGame().getAssetManager().get("Images/Player/PlayerAtlas.txt");
        this.batch= playScreen.getGame().batch;
    }

    public abstract  void update(float delta);
    public abstract  void render(float delta);


}
