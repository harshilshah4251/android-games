package com.SpaceWreck.sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

/**
 * Created by harsh on 06/20/17.
 */

public class Star extends  VisibleStaticEntity{
    private TextureRegion star1;
    private TextureRegion star2;
    private Array<TextureRegion> starSequence;
    private Animation<TextureRegion> animation;
    private float stateTime;
    private Sound clingSound;

    public static int value=100;



    public Star(com.SpaceWreck.screens.PlayScreen playScreen, float x, float y) {
        super(playScreen, x, y);
        this.width= 15.0f/ com.SpaceWreck.game.GameConstants.ppm;
        this.height= 15.0f/ com.SpaceWreck.game.GameConstants.ppm;
        this.isHit=false;
        clingSound= playScreen.getGame().getAssetManager().get("Sounds/cling.mp3");
        initializeTextures();
        initializeAnimations();
        defineBody();
    }
    public void initializeTextures(){
        star1= atlas.findRegion("Star1");
        star2= atlas.findRegion("Star2");
    }
    public void initializeAnimations(){
        starSequence= new Array<TextureRegion>();
        starSequence.addAll(star1, star2);
        animation= new Animation<TextureRegion>(0.50f, starSequence, Animation.PlayMode.LOOP);

    }
    public void updateKeyFrame(float delta){
        stateTime+=delta;
        this.setRegion(animation.getKeyFrame(stateTime));
        this.setSize(width, height);
        this.setPosition(this.body.getPosition().x- this.getWidth()/2.0f, this.body.getPosition().y- this.getHeight()/2.0f);
    }
    public void defineBody(){
        BodyDef bodyDef= new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type= BodyDef.BodyType.StaticBody;
        body= world.createBody(bodyDef);

        FixtureDef fixtureDef= new FixtureDef();
        PolygonShape shape= new PolygonShape();
        shape.setAsBox(width/2.0f, height/2.0f);

        fixtureDef.shape= shape;
        fixtureDef.filter.categoryBits= com.SpaceWreck.game.GameConstants.STAR_BIT;
        fixtureDef.filter.maskBits= com.SpaceWreck.game.GameConstants.WALLHORIZONTAL_BIT |
                                    com.SpaceWreck.game.GameConstants.WALLVERTICAL_BIT |
                                    com.SpaceWreck.game.GameConstants.PLAYER_BIT |
                                    com.SpaceWreck.game.GameConstants.WALL_BIT;
        fixtureDef.isSensor=true;
        body.createFixture(fixtureDef).setUserData(this);



    }
    public void playSound(){
        clingSound.play();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {
        updateKeyFrame(delta);
        this.draw(batch);
    }

    @Override
    public void setHit(boolean hit) {
        super.setHit(hit);
        clingSound.play();
    }

    public static int getValue() {
        return value;
    }

    public static void setValue(int value) {
        Star.value = value;
    }
    public static void resetValue(){
        Star.value/=2.0;
    }
}
