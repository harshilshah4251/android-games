package com.SpaceWreck.sprites;

import com.SpaceWreck.screens.PlayScreen;

/**
 * Created by harsh on 06/29/17.
 */

public abstract class PowerUps extends VisibleStaticEntity {


    public PowerUps(PlayScreen playScreen, float x, float y){
        super(playScreen, x, y);
    }
    public abstract  void update(float delta);
    public abstract  void render(float delta);
    public boolean isHit() {
        return isHit;
    }

}
