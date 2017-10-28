package com.SpaceWreck.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.SpaceWreck.screens.PlayScreen;

/**
 * Created by harsh on 06/20/17.
 */

public class FinishLine extends VisibleStaticEntity {

    private TextureRegion finishLine;

    public FinishLine(PlayScreen playScreen, float x, float y) {
        super(playScreen, x, y);
        finishLine= atlas.findRegion("finishLine");
        this.isHit=false;
        this.width=15.0f/ com.SpaceWreck.game.GameConstants.ppm;
        this.height=115.0f/ com.SpaceWreck.game.GameConstants.ppm;
        defineBody();
        this.setRegion(finishLine);
        this.setSize(width, height);
        this.setPosition(this.body.getPosition().x- this.getWidth()/2.0f, this.body.getPosition().y- this.getHeight()/2.0f);


    }
    public void defineBody(){
        BodyDef bodyDef= new BodyDef();
        bodyDef.position.set(x+(this.width/2.0f), y+(this.height/2.0f));
        bodyDef.type= BodyDef.BodyType.StaticBody;
        body= world.createBody(bodyDef);

        FixtureDef fixtureDef= new FixtureDef();
        PolygonShape shape= new PolygonShape();
        shape.setAsBox(width/2.0f, height/2.0f);

        fixtureDef.shape= shape;
        fixtureDef.filter.categoryBits= com.SpaceWreck.game.GameConstants.FINISHLINE_BIT;
        fixtureDef.filter.maskBits= com.SpaceWreck.game.GameConstants.PLAYER_BIT;
        fixtureDef.isSensor=true;
        body.createFixture(fixtureDef).setUserData(this);
    }



    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {
        this.draw(batch);

    }

    @Override
    public void setHit(boolean hit) {
        super.setHit(hit);

    }
}
