package com.SpaceWreck.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.SpaceWreck.game.GameConstants;
import com.SpaceWreck.game.MainGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by harsh on 06/20/17.
 */

public class LevelScreen implements Screen {

    private com.SpaceWreck.game.MainGame game;
    private Stage stage;
    private Viewport levelPort;
    private OrthographicCamera levelCam;
    private Table table;
    private Table infoTable;
    private ScrollPane scrollPane;
    private Table container;

    private TextureAtlas atlas;

    private ImageButton earth;
    private ImageButton mars;
    private ImageButton jupiter;
    private ImageButton uranus;
    private ImageButton alienPlanet;

    private ImageButton marsLocked;
    private ImageButton jupiterLocked;
    private ImageButton uranusLocked;
    private ImageButton alienPlanetLocked;
    private Label lackPoints;
    private Label totalPointsText;
    private Label totalPointsLabel;
    private int totalPoints;

    private ImageButton lock2;
    private ImageButton lock3;
    private ImageButton lock4;
    private ImageButton lock5;


    private com.SpaceWreck.managers.LevelManager levelManager;

    private boolean earthHit=false;
    private boolean marsHit=false;
    private boolean jupiterHit=false;
    private boolean uranusHit=false;
    private boolean alienPlanetHit=false;

    private ImageButton menu;

    private FreeTypeFontGenerator generator;
    private BitmapFont font60;
    private BitmapFont font40;

    private boolean adShown=false;


    public LevelScreen(com.SpaceWreck.game.MainGame game){
        this.game= game;
        levelCam= new OrthographicCamera();
        levelPort= new FitViewport(GameConstants.viewPortWidth, GameConstants.viewPortHeight, levelCam);
        levelManager= new com.SpaceWreck.managers.LevelManager();
        stage= new Stage(levelPort, game.batch);
        this.atlas= this.game.getAssetManager().get("Images/Player/PlayerAtlas.txt", TextureAtlas.class);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Captureit.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        font60 = generator.generateFont(parameter);
        parameter.size=40;
        font40= generator.generateFont(parameter);

        initializeTextures();
        initializeStage();
        addEventListeners();
        Gdx.input.setInputProcessor(stage);
    }
    public void initializeTextures(){
        earth= new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/Earth.png", Texture.class))),
                new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/Earth.png", Texture.class))));
        mars= new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/Mars.png", Texture.class))),
                new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/Mars.png", Texture.class))));
        jupiter= new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/Jupiter.png", Texture.class))),
                new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/Jupiter.png", Texture.class))));
        uranus= new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/Uranus.png", Texture.class))),
                new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/Uranus.png", Texture.class))));
        alienPlanet= new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/AlienPlanet.png", Texture.class))),
                new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/AlienPlanet.png", Texture.class))));
        marsLocked= new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/marsLocked.png", Texture.class))),
                new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/marsLocked.png", Texture.class))));
        jupiterLocked= new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/jupiterLocked.png", Texture.class))),
                new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/jupiterLocked.png", Texture.class))));
        uranusLocked= new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/uranusLocked.png", Texture.class))),
                new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/uranusLocked.png", Texture.class))));
        alienPlanetLocked= new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/alienPlanetLocked.png", Texture.class))),
                new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/alienPlanetLocked.png", Texture.class))));

        lock2 = new ImageButton(new TextureRegionDrawable(atlas.findRegion("lockUp")),
                new TextureRegionDrawable(atlas.findRegion("lockDown")));
        lock3 = new ImageButton(new TextureRegionDrawable(atlas.findRegion("lockUp")),
                new TextureRegionDrawable(atlas.findRegion("lockDown")));
        lock4 = new ImageButton(new TextureRegionDrawable(atlas.findRegion("lockUp")),
                new TextureRegionDrawable(atlas.findRegion("lockDown")));
        lock5 = new ImageButton(new TextureRegionDrawable(atlas.findRegion("lockUp")),
                new TextureRegionDrawable(atlas.findRegion("lockDown")));

        //lackPoints= new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/lackPoints.png", Texture.class))),
          //      new TextureRegionDrawable(new TextureRegion(game.getAssetManager().get("Images/Player/lackPoints.png", Texture.class))));
        lackPoints= new Label(String.format("You need %05d more points to unlock \n %s", 000000, "yo"),new Label.LabelStyle(font60, Color.WHITE) );
        totalPoints= com.SpaceWreck.managers.LevelManager.getTotalPoints();
        totalPointsLabel= new Label(String.format("%06d", totalPoints), new Label.LabelStyle(font40, Color.GOLD));
        totalPointsText= new Label("TOTAL POINTS : ", new Label.LabelStyle(font40, Color.WHITE));

        menu= new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("menuUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("menuDown"))));

    }
    public void initializeStage(){
        container= new Table();
        table= new Table();
        infoTable= new Table();
        scrollPane= new ScrollPane(table);
        scrollPane.setScrollingDisabled(false, true);
        container.add(scrollPane);


        scrollPane.setSmoothScrolling(true);

        if(!levelManager.getLevel1().isLocked()){
            table.add(earth).width(levelPort.getWorldWidth()).height(levelPort.getWorldHeight()/3.0f).padLeft(5.0f);
        }
        if(!levelManager.getLevel2().isLocked()){
            table.add(mars).width(levelPort.getWorldWidth()).height(levelPort.getWorldHeight()/3.0f).padLeft(5.0f);
        }
        else if(levelManager.getLevel2().isLocked()){
            table.add(marsLocked).width(levelPort.getWorldWidth()).height(levelPort.getWorldHeight()/3.0f).padLeft(5.0f);
           // lock2.pack();
            //lock2.setPosition(100.0f, 100.0f);
           // lock2.setPosition(levelPort.getWorldWidth() + ((1/2.0f)*(levelPort.getWorldWidth())) - lock2.getWidth()/2.0f, levelPort.getWorldHeight()/2.0f- lock2.getHeight()/2.0f);
        }
        if(!levelManager.getLevel3().isLocked()){
            table.add(jupiter).width(levelPort.getWorldWidth()).height(levelPort.getWorldHeight()/3.0f).padLeft(5.0f);
        }
        else if(levelManager.getLevel3().isLocked()){
            table.add(jupiterLocked).width(levelPort.getWorldWidth()).height(levelPort.getWorldHeight()/3.0f).padLeft(5.0f);
            //lock3.pack();
            //lock3.setPosition((2.0f*levelPort.getWorldWidth()) + ((1/2.0f)*(levelPort.getWorldWidth())) - lock3.getWidth()/2.0f, levelPort.getWorldHeight()/2.0f- lock3.getHeight()/2.0f);
        }
        if(!levelManager.getLevel4().isLocked()){
            table.add(uranus).width(levelPort.getWorldWidth()).height(levelPort.getWorldHeight()/3.0f).padLeft(5.0f);
        }
        else if(levelManager.getLevel4().isLocked()){
            table.add(uranusLocked).width(levelPort.getWorldWidth()).height(levelPort.getWorldHeight()/3.0f).padLeft(5.0f);
            //lock4.pack();
            //lock4.setPosition((3.0f*levelPort.getWorldWidth()) + ((1/2.0f)*(levelPort.getWorldWidth())) - lock4.getWidth()/2.0f, levelPort.getWorldHeight()/2.0f- lock4.getHeight()/2.0f);
        }
        if(!levelManager.getLevel5().isLocked()){
            table.add(alienPlanet).width(levelPort.getWorldWidth()).height(levelPort.getWorldHeight()/3.0f).padLeft(5.0f);
        }
        else if(levelManager.getLevel5().isLocked()){
            table.add(alienPlanetLocked).width(levelPort.getWorldWidth()).height(levelPort.getWorldHeight()/3.0f).padLeft(5.0f);
            //lock5.pack();
            //lock5.setPosition((4.0f*levelPort.getWorldWidth()) + ((1/2.0f)*(levelPort.getWorldWidth())) - lock5.getWidth()/2.0f, levelPort.getWorldHeight()/2.0f- lock5.getHeight()/2.0f);
        }
//        table.add(earth).width(levelPort.getWorldWidth()).height(levelPort.getWorldHeight()/3.0f).padLeft(5.0f);
//        table.add(mars).width(levelPort.getWorldWidth()).height(levelPort.getWorldHeight()/3.0f).padLeft(5.0f);
//        table.add(jupiter).width(levelPort.getWorldWidth()).height(levelPort.getWorldHeight()/3.0f).padLeft(5.0f);
//        table.add(uranus).width(levelPort.getWorldWidth()).height(levelPort.getWorldHeight()/3.0f).padLeft(5.0f);
//        table.add(alienPlanet).width(levelPort.getWorldWidth()).height(levelPort.getWorldHeight()/3.0f).padLeft(5.0f);
        table.pack();
        table.setPosition(0, levelPort.getWorldHeight()/2.0f - table.getHeight()/2.0f);
        container.setFillParent(true);
        lackPoints.pack();
        lackPoints.setPosition(0,levelPort.getWorldHeight());

        infoTable.add(totalPointsText);
        infoTable.add(totalPointsLabel);
        infoTable.pack();
        infoTable.setPosition(levelPort.getWorldWidth(), levelPort.getWorldHeight());
        infoTable.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo((infoTable.getWidth()/4.0f),
                                (levelPort.getWorldHeight()-infoTable.getHeight()-infoTable.getHeight()/2.0f),1.0f, Interpolation.swing))));  //a mistake but looks good

        menu.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo((levelPort.getWorldWidth()-menu.getWidth() -45.0f), (levelPort.getWorldHeight()-menu.getHeight()-40.0f),1.0f, Interpolation.swing))));
        stage.addActor(container);
        //stage.addActor(lock2);
        //stage.addActor(lock3);
        //stage.addActor(lock4);
        //stage.addActor(lock5);
        stage.addActor(lackPoints);
        stage.addActor(infoTable);
        stage.addActor(menu);
        // table.setDebug(true);
        // scrollPane.setDebug(true);
        // container.setDebug(true);
    }
    public void lackPointsAction(com.SpaceWreck.levels.Level level){
        lackPoints.setColor(level.getColor());
        lackPoints.setText(String.format("%05d more points required to unlock \n                    %s", level.getUnlockScore()-levelManager.getTotalPoints(), level.getLevelName() ));
        lackPoints.addAction(sequence(alpha(0),
                            parallel(fadeIn(0.5f, Interpolation.pow2),
                                    moveTo((levelPort.getWorldWidth()/2.0f - lackPoints.getWidth()/2.0f),
                                           (levelPort.getWorldHeight()/2.0f-lackPoints.getHeight()/2.0f),1.0f, Interpolation.swing))));
    }
    public void addEventListeners(){

        earth.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                earthHit=true;




            }
        });
        mars.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){



                    marsHit=true;


            }
        });
        jupiter.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){


                    jupiterHit=true;

            }

        });
        uranus.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){




                    uranusHit=true;

            }
        });
        alienPlanet.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                    alienPlanetHit=true;

            }
        });
        marsLocked.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                lackPointsAction(levelManager.getLevel2());
            }
        });
        jupiterLocked.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                lackPointsAction(levelManager.getLevel3());
            }

        });
        uranusLocked.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                lackPointsAction(levelManager.getLevel4());
            }
        });
        alienPlanetLocked.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){
                lackPointsAction(levelManager.getLevel5());
            }
        });

        lackPoints.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){

                    lackPoints.addAction(sequence(alpha(0),
                            parallel(fadeOut(1.0f, Interpolation.pow2),
                                    moveTo((0),
                                            (levelPort.getWorldHeight()),1.0f, Interpolation.swing))));

            }
        });
        menu.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){

                game.setScreen(new MenuScreen(game));

            }
        });
    }

    public void update(float delta){
        stage.act();
        if(!adShown){
            MainGame.handler.showBannerAd();
            adShown=true;
        }
        if(earthHit){
            com.SpaceWreck.screens.PlayScreen playScreen= new com.SpaceWreck.screens.PlayScreen(game, levelManager.getLevel1(), this);
            game.setScreen(playScreen);
            levelManager.updateLevel(playScreen, 1);
            MenuScreen.outGameMusic.stop();
            earthHit=false;
        }
        else if(marsHit){
            com.SpaceWreck.screens.PlayScreen playScreen= new com.SpaceWreck.screens.PlayScreen(game, levelManager.getLevel2(), this);
            game.setScreen(playScreen);
            levelManager.updateLevel(playScreen, 2);
            MenuScreen.outGameMusic.stop();
            marsHit=false;
        }
        else if(jupiterHit){
            com.SpaceWreck.screens.PlayScreen playScreen= new com.SpaceWreck.screens.PlayScreen(game, levelManager.getLevel3(), this);
            game.setScreen(playScreen);
            levelManager.updateLevel(playScreen, 3);
            MenuScreen.outGameMusic.stop();
            jupiterHit=false;
        }
        else if(uranusHit){
            com.SpaceWreck.screens.PlayScreen playScreen= new com.SpaceWreck.screens.PlayScreen(game, levelManager.getLevel4(), this);
            game.setScreen(playScreen);
            levelManager.updateLevel(playScreen, 4);
            MenuScreen.outGameMusic.stop();
            uranusHit=false;
        }
        else if(alienPlanetHit){
            com.SpaceWreck.screens.PlayScreen playScreen= new com.SpaceWreck.screens.PlayScreen(game, levelManager.getLevel5(), this);
            game.setScreen(playScreen);
            levelManager.updateLevel(playScreen, 5);
            MenuScreen.outGameMusic.stop();
            alienPlanetHit=false;
        }
        //Gdx.app.log("called", "stage.act");
    }
    @Override
    public void show() {
        table.addAction(sequence(alpha(0),
                parallel(fadeIn(1.0f, Interpolation.pow2))));
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
        levelPort.update(width, height);
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
        font40.dispose();
        font60.dispose();
    }

    public com.SpaceWreck.managers.LevelManager getLevelManager() {
        return levelManager;
    }
}

//}
//lock2.addListener(new ClickListener(){
//@Override
//public void clicked (InputEvent event, float x, float y){
////                if(levelManager.getLevel2().isLocked()){
////                    lackPoints.addAction(sequence(alpha(0),
////                            parallel(fadeIn(0.5f, Interpolation.pow2),
////                                    moveTo((lock2.getX()+lock2.getWidth()/2.0f - lackPoints.getWidth()/2.0f),
////                                            (levelPort.getWorldHeight()/2.0f-lackPoints.getHeight()/2.0f),1.0f, Interpolation.swing))));
////                }
//        }
//        });
//        lock3.addListener(new ClickListener(){
//@Override
//public void clicked (InputEvent event, float x, float y){
////                if(levelManager.getLevel3().isLocked()){
////                    lackPoints.addAction(sequence(alpha(0),
////                            parallel(fadeIn(0.5f, Interpolation.pow2),
////                                    moveTo((lock3.getX()+lock3.getWidth()/2.0f - lackPoints.getWidth()/2.0f),
////                                            (levelPort.getWorldHeight()/2.0f-lackPoints.getHeight()/2.0f),1.0f, Interpolation.swing))));
////                }
//        }
//        });
//        lock4.addListener(new ClickListener(){
//@Override
//public void clicked (InputEvent event, float x, float y){
////                if(levelManager.getLevel4().isLocked()){
////                    lackPoints.addAction(sequence(alpha(0),
////                            parallel(fadeIn(0.5f, Interpolation.pow2),
////                                    moveTo((lock4.getX()+lock4.getWidth()/2.0f - lackPoints.getWidth()/2.0f),
////                                            (levelPort.getWorldHeight()/2.0f-lackPoints.getHeight()/2.0f),1.0f, Interpolation.swing))));
////                }
//        }
//        });
//        lock5.addListener(new ClickListener(){
//@Override
//public void clicked (InputEvent event, float x, float y){
////                if(levelManager.getLevel5().isLocked()){
////                    lackPoints.addAction(sequence(alpha(0),
////                            parallel(fadeIn(0.5f, Interpolation.pow2),
////                                    moveTo((lock5.getX()+lock5.getWidth()/2.0f - lackPoints.getWidth()/2.0f),
////                                            (levelPort.getWorldHeight()/2.0f-lackPoints.getHeight()/2.0f),1.0f, Interpolation.swing))));
////                }
//        }
//        });