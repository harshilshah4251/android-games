package com.SpaceWreck.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

/**
 * Created by harsh on 06/20/17.
 */

public class Box extends  VisibleStaticEntity{
    private TextureRegion star1;
    private TextureRegion star2;
    private Array<TextureRegion> starSequence;
    private Animation<TextureRegion> animation;




    public Box(com.SpaceWreck.screens.PlayScreen playScreen, TextureRegion textureRegion, float x, float y) {
        super(playScreen, x, y);
        this.width= 25.0f/ com.SpaceWreck.game.GameConstants.ppm;
        this.height= 25.0f/ com.SpaceWreck.game.GameConstants.ppm;
        this.setSize(width, height);
        defineBody();
        this.setRegion(textureRegion);
        this.setPosition(this.body.getPosition().x- this.getWidth()/2.0f, this.body.getPosition().y- this.getHeight()/2.0f);

    }
    public void defineBody(){
        BodyDef bodyDef= new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        body= world.createBody(bodyDef);

        FixtureDef fixtureDef= new FixtureDef();
        PolygonShape shape= new PolygonShape();
        shape.setAsBox(width/2.0f, height/2.0f);

        fixtureDef.shape= shape;
        fixtureDef.restitution=0.2f;
        fixtureDef.filter.categoryBits= com.SpaceWreck.game.GameConstants.BOX_BIT;
        fixtureDef.filter.maskBits= com.SpaceWreck.game.GameConstants.WALLHORIZONTAL_BIT |
                com.SpaceWreck.game.GameConstants.WALLVERTICAL_BIT |
                com.SpaceWreck.game.GameConstants.PLAYER_BIT |
                com.SpaceWreck.game.GameConstants.WALL_BIT |
                com.SpaceWreck.game.GameConstants.PLAYER_BIT |
                com.SpaceWreck.game.GameConstants.PLAYERBULLET_BIT |
                com.SpaceWreck.game.GameConstants.ENEMY_BIT |
                com.SpaceWreck.game.GameConstants.ENEMYBULLET_BIT |
                com.SpaceWreck.game.GameConstants.BOX_BIT;
        body.createFixture(fixtureDef).setUserData(this);
        this.body.applyForceToCenter((float)Math.random(), (float) Math.random(), true);
    }


    @Override
    public void update(float delta) {
        this.setPosition(this.body.getPosition().x- this.getWidth()/2.0f, this.body.getPosition().y- this.getHeight()/2.0f);
    }

    @Override
    public void render(float delta) {

        update(delta);
        this.draw(batch);
    }



}
