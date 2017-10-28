package com.SpaceWreck.sprites;

import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by harsh on 06/20/17.
 */

public class WallVertical extends Wall {
    private Body body;
    private float hy;  //halfheight

    public WallVertical(com.SpaceWreck.screens.PlayScreen playScreen, Polyline line){
        super(playScreen,line);
        defineBody();
    }
    public void defineBody(){
        BodyDef bodyDef= new BodyDef();
        bodyDef.position.set(x/ com.SpaceWreck.game.GameConstants.ppm, (y+halfLength)/ com.SpaceWreck.game.GameConstants.ppm);
        bodyDef.type= BodyDef.BodyType.StaticBody;
        body= world.createBody(bodyDef);

        FixtureDef fixtureDef= new FixtureDef();
        PolygonShape shape= new PolygonShape();
        shape.setAsBox(0.0f, halfLength/ com.SpaceWreck.game.GameConstants.ppm);

        fixtureDef.shape= shape;
        fixtureDef.filter.categoryBits=  com.SpaceWreck.game.GameConstants.WALL_BIT;
        fixtureDef.filter.maskBits= com.SpaceWreck.game.GameConstants.PLAYER_BIT |
                com.SpaceWreck.game.GameConstants.ENEMY_BIT |
                com.SpaceWreck.game.GameConstants.PLAYERBULLET_BIT |
                com.SpaceWreck.game.GameConstants.ENEMYBULLET_BIT |
                com.SpaceWreck.game.GameConstants.STAR_BIT|
                com.SpaceWreck.game.GameConstants.BOX_BIT;;
        body.createFixture(fixtureDef).setUserData(this);

    }
}
