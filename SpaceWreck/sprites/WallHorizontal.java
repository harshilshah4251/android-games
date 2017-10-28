package com.SpaceWreck.sprites;

import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.SpaceWreck.game.GameConstants;

/**
 * Created by harsh on 06/20/17.
 */

public class WallHorizontal  extends com.SpaceWreck.sprites.Wall {

    private Body body;
    private float hx;  //halfwidth

    public WallHorizontal(com.SpaceWreck.screens.PlayScreen playScreen, Polyline line){
        super(playScreen, line);
        defineBody();
    }
    public void defineBody(){
        BodyDef bodyDef= new BodyDef();
        bodyDef.position.set((x+halfLength)/ GameConstants.ppm, y/GameConstants.ppm);
        bodyDef.type= BodyDef.BodyType.StaticBody;
        body= world.createBody(bodyDef);

        FixtureDef fixtureDef= new FixtureDef();
        PolygonShape shape= new PolygonShape();
        shape.setAsBox(halfLength/GameConstants.ppm, 0.0f);

        fixtureDef.shape= shape;
        fixtureDef.filter.categoryBits=  com.SpaceWreck.game.GameConstants.WALL_BIT;
        fixtureDef.filter.maskBits= com.SpaceWreck.game.GameConstants.PLAYER_BIT |
                                    GameConstants.ENEMY_BIT |
                                    GameConstants.PLAYERBULLET_BIT |
                                    GameConstants.ENEMYBULLET_BIT |
                                    com.SpaceWreck.game.GameConstants.STAR_BIT|
                GameConstants.BOX_BIT;;
        body.createFixture(fixtureDef).setUserData(this);

    }
}
