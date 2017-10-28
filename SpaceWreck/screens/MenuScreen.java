package com.SpaceWreck.screens;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.SpaceWreck.game.GameConstants;
import com.SpaceWreck.game.MainGame;
import com.SpaceWreck.screens.ContextScreen;
import com.SpaceWreck.screens.CreditsScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;



/**
 * Created by harsh on 06/20/17.
 */

public class MenuScreen implements Screen {

    private Stage stage;
    private Viewport menuPort;
    private OrthographicCamera menuCam;
    private Table table;
    private Table titleTable;
    private Table extrasTable;
    private ImageButton playButton;
    private ImageButton quitButton;
    private ImageButton storyButton;
    private ImageButton achievementsButton;
    private ImageButton rateMyGameButton;
    private ImageButton creditsButton;
    private TextureAtlas atlas;
    public static Music outGameMusic;
    private float stateTime;

    private Image redBlaster;
    private Image grossGreener;
    private Image whiteTrojans;
    private Image magnetoDrone;

    private Label wreck;
    private Label in;
    private Label space;
    private Label errorCode;
    private int randomNum;
    private int randomNumStore;

    private FreeTypeFontGenerator generator;
    private BitmapFont font100;
    private BitmapFont font70;
    private BitmapFont font70diff;





    private MainGame game;
    public MenuScreen(com.SpaceWreck.game.MainGame game){
        this.game= game;
        if(Gdx.app.getType() .equals(Application.ApplicationType.Android) ){

            game.playServices.signIn();
            //game.playServices.rateGame();
        }

        menuCam= new OrthographicCamera();
        menuPort= new FitViewport(GameConstants.viewPortWidth, GameConstants.viewPortHeight, menuCam);
        stage= new Stage(menuPort, game.batch);
        table= new Table();
        titleTable= new Table();
        extrasTable= new Table();
        this.stateTime=0;

        this.atlas= game.getAssetManager().get("Images/Player/PlayerAtlas.txt", TextureAtlas.class);
        playButton= new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("playUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("playDown"))));
        storyButton= new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("storyUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("storyDown"))));
        creditsButton= new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("creditsUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("creditsDown"))));
        quitButton= new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("quitUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("quitDown"))));
        rateMyGameButton= new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("rateMyGameUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("rateMyGameDown"))));
        achievementsButton= new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("showAchievementsUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("showAchievementsDown"))));


        generator= new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Captureit.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        font100 = generator.generateFont(parameter);
        parameter.size=70;
        font70= generator.generateFont(parameter);
        generator.dispose();
        generator= new FreeTypeFontGenerator(Gdx.files.internal("Fonts/ledBoard.ttf"));
        parameter.size=20;
        font70diff=generator.generateFont(parameter);

        wreck= new Label("WRECK", new Label.LabelStyle(font100, Color.GOLD));
        space= new Label("SPACE", new Label.LabelStyle(font100, Color.SCARLET));
        randomNum=MathUtils.random(10000, 99999);
        errorCode= new Label(String.format("Spaceship Error Code: %05d", randomNum), new Label.LabelStyle(font70diff, Color.GREEN));
        errorCode.pack();
        errorCode.setPosition(menuPort.getWorldWidth()/2.5f, menuPort.getWorldHeight()-errorCode.getHeight()-50.0f);

        wreck.pack();
        space.pack();


        initializeMusic();
        initializeTextures();
        addEventListeners();
        initializeTable();
        stage.addActor(table);
        stage.addActor(redBlaster);
        stage.addActor(grossGreener);
        stage.addActor(magnetoDrone);
        stage.addActor(titleTable);
        stage.addActor(errorCode);
        stage.addActor(extrasTable);
        Gdx.input.setInputProcessor(stage);

        com.SpaceWreck.game.MainGame.handler.hideBannerAd();



    }
    public void initializeMusic(){

            outGameMusic= game.getAssetManager().get("Sounds/outGameMusic.mp3", Music.class);
            outGameMusic.setLooping(true);
            outGameMusic.play();


    }
    public void initializeTable(){
        table.add(playButton).width(200).padRight(40.0f);
        table.row();
        table.add(storyButton).width(200).padRight(40.0f);
        table.row();
        table.add(creditsButton).width(200).padRight(40.0f);
        table.row();
        table.add(quitButton).width(200).padRight(40.0f);
        table.center();
        table.pack();
        table.setPosition(table.getX(), menuPort.getWorldHeight()/2.0f - table.getHeight()/2.0f);

       // table.setDebug(true);
        titleTable.add(space).center();
        titleTable.row();
        titleTable.add(wreck).center();


        titleTable.pack();
        titleTable.setPosition(0, menuPort.getWorldHeight());


        extrasTable.add(achievementsButton).pad(10.0f);
        extrasTable.add(rateMyGameButton).pad(10.0f);
        extrasTable.pack();
        extrasTable.setPosition(menuPort.getWorldWidth(),menuPort.getWorldHeight()/3.0f);

    }
    public void initializeTextures(){
        redBlaster= new Image(atlas.findRegion("ShootingEnemy4"));
        //redBlaster.setWidth(50.0f);
        //redBlaster.setHeight(40.0f);
        redBlaster.setPosition(menuPort.getWorldWidth(), menuPort.getWorldHeight());
        grossGreener= new Image(atlas.findRegion("GreenEnemy4"));
        //grossGreener.setWidth(50.0f);
        //grossGreener.setHeight(40.0f);
        grossGreener.setPosition(0,menuPort.getWorldHeight());
        whiteTrojans= new Image(atlas.findRegion("WeirdEnemy4"));
        whiteTrojans.setWidth(50.0f);
        whiteTrojans.setHeight(40.0f);
        whiteTrojans.setPosition(0, menuPort.getWorldHeight());
        magnetoDrone = new Image(atlas.findRegion("FollowEnemy4"));
       // magnetoDrone.setWidth(70.0f);
       // magnetoDrone.setHeight(30.0f);
        magnetoDrone.setPosition(menuPort.getWorldWidth(),0);
    }
    public void addEventListeners(){
        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new com.SpaceWreck.screens.LevelScreen(game));
                return true;
           }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }

        });
        storyButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new ContextScreen(game));
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }

        });
        creditsButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new CreditsScreen(game));
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }

        });
        quitButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                outGameMusic.stop();
                if(Gdx.app.getType() .equals(Application.ApplicationType.Android)){
                    game.playServices.signOut();
                }

                Gdx.app.exit();
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }

        });
        achievementsButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){

                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                if(Gdx.app.getType() .equals(Application.ApplicationType.Android)){
                    game.playServices.showAchievement();
                }

            }

        });
        rateMyGameButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                if(Gdx.app.getType() .equals(Application.ApplicationType.Android)){
                    game.playServices.rateGame();
                }

            }

        });
        redBlaster.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                redBlaster.addAction(rotateBy(360.0f, 1.0f, Interpolation.pow2));
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }

        });
        grossGreener.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                grossGreener.addAction(sequence(scaleBy(0.2f,0.2f,0.3f,Interpolation.swing), scaleTo(1.5f, 1.5f, 0.3f, Interpolation.swing)));
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }

        });
        magnetoDrone.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                magnetoDrone.addAction(sequence(moveBy(20.0f,0,0.5f,Interpolation.swing), moveBy(-20.0f,0, 0.5f,Interpolation.swing)));
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }

        });


    }
    public void update(float delta){
        stateTime+=delta;
        if(stateTime>2.0){
            randomNumStore=MathUtils.random(10000, 99999);

            stateTime=0;
        }
        randomNum= (int)(MathUtils.lerp(randomNum, randomNumStore, 0.1f));
        errorCode.setText(String.format("Error Code: %05d", randomNum));
        stage.act();
    }
    @Override
    public void show() {
        table.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo(menuPort.getWorldWidth()-table.getWidth(), table.getY(),1.0f, Interpolation.swing))));
        extrasTable.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo(0,menuPort.getWorldHeight()/3.0f,1.0f, Interpolation.swing))));

        redBlaster.addAction(sequence(alpha(0),scaleTo(0.1f, 0.1f),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        scaleBy(1.0f, 1.0f, 1.5f, Interpolation.pow5),
                        rotateBy(1080, 2.5f, Interpolation.pow2),
                        moveTo(menuPort.getWorldWidth()/1.5f - redBlaster.getWidth()/2.0f, menuPort.getWorldHeight()/3.0f,1.5f, Interpolation.swingIn))));
        grossGreener.addAction(sequence(alpha(0),scaleTo(0.1f, 0.1f), delay(1.0f),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        scaleBy(1.5f, 1.5f, 2.5f, Interpolation.pow5),
                        rotateBy(1080, 2.5f, Interpolation.pow2),
                        moveTo(menuPort.getWorldWidth()/3.75f, menuPort.getWorldHeight()/4.5f,1.5f, Interpolation.swing))));
        magnetoDrone.addAction(sequence(alpha(0), delay(1.0f),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        scaleBy(1.0f,1.0f,1.0f,Interpolation.pow2),
                        moveTo(0, 0,1.5f, Interpolation.circleIn))));

        titleTable.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo(menuPort.getWorldWidth()/20.0f, menuPort.getWorldHeight()-titleTable.getHeight() -40.0f,1.5f, Interpolation.swing))));

        errorCode.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2))));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        menuPort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        generator.dispose();
        atlas.dispose();
        font70diff.dispose();
        font70.dispose();
        font100.dispose();

    }
}
