package com.SpaceWreck.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by harsh on 06/20/17.
 */

public class DeadScreen implements Screen{
    private LevelScreen levelScreen;
    private com.SpaceWreck.game.MainGame game;
    private PlayScreen playScreen;
    private TextureAtlas atlas;

    private OrthographicCamera deadCam;
    public Stage stage;
    private Viewport deadPort;
    private Table table;

    private static Label levelName;
    private static Label levelNameLabel;

    private static Label performanceLabel;

    private static Integer score=0;
    private static Label scoreLabel;
    private static Label scoreText;


    private static Label highScoreLabel;
    private static Label highScoreText;
    private static int highScore;

    private static Label totalPointsLabel;
    private static Label totalPointsText;
    private static int totalPoints;

    public static Music outGameMusic;

    private float temp;
    private float lerp;
    private boolean flag;






    private SpriteBatch batch;
    private com.SpaceWreck.sprites.Player player;

    private ShapeRenderer shapeRenderer;
    private FreeTypeFontGenerator generator;
    private BitmapFont font;

    private Table buttonTable;
    private ImageButton retryButton;
    private ImageButton menuButton;
    private ImageButton levelButton;

    private boolean adShown;


    public DeadScreen(LevelScreen levelScreen, PlayScreen playScreen){
        this.levelScreen= levelScreen;
        this.playScreen= playScreen;
        this.game= playScreen.getGame();
        this.player= playScreen.getPlayer();
        this.batch= playScreen.getGame().batch;
        this.atlas= playScreen.getGame().getAssetManager().get("Images/Player/PlayerAtlas.txt", TextureAtlas.class);
        deadCam= new OrthographicCamera();
        deadPort= new FitViewport(com.SpaceWreck.game.GameConstants.viewPortWidth, com.SpaceWreck.game.GameConstants.viewPortHeight);
        stage= new Stage(deadPort, batch);
        shapeRenderer= new ShapeRenderer();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Captureit.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        font = generator.generateFont(parameter);

        initializeMusic();
        initializeLabels();
        initializeButtons();
        initializeTable();
        addEventListeners();

        stage.addActor(buttonTable);
        stage.addActor(table);

        temp=0;
        lerp=0.05f;
        this.flag=true;
        this.adShown=false;
    }
    public void initializeMusic(){
        outGameMusic= playScreen.getGame().getAssetManager().get("Sounds/outGameMusic.mp3", Music.class);
        outGameMusic.setLooping(true);
    }
    public void initializeLabels(){
        levelName= new Label(playScreen.getCurrentLevel().getLevelName(),new Label.LabelStyle(font, playScreen.getCurrentLevel().getColor()) );
        scoreLabel= new Label(String.format("%06d", score), new Label.LabelStyle(font, Color.GOLD));
        scoreText= new Label("SCORE", new Label.LabelStyle(font, Color.WHITE) );
        levelNameLabel= new Label("LEVEL",new Label.LabelStyle(font, Color.WHITE) );



    }
    public void initializeButtons(){
        retryButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("retryUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("retryDown"))));
        menuButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("menuUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("menuDown"))));
        levelButton=new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("levelsUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("levelsDown"))));
    }
    public void initializeTable(){
        table= new Table();


        table.add(scoreText).pad(20.0f);
        table.add(scoreLabel).pad(20.0f);
        table.row();
//        table.add(highScoreText).pad(10.0f).expandX();
//        table.add(highScoreLabel).pad(10.0f).expandX();
//        table.row();
//        table.add(totalPointsText).pad(10.0f).expandX();
//        table.add(totalPointsLabel).pad(10.0f).expandX();
//        table.row();
        table.add(levelNameLabel).pad(20.0f);
        table.add(levelName).pad(20.0f);
        table.add();
//        table.row();
//        table.add();
//        table.add(performanceLabel).pad(10.0f).expandX().center();
        table.row();
//        table.add(retryButton).pad(10.0f).width(100.0f).height(100.0f);
//        table.add(levelButton).pad(10.0f).width(100.0f).height(100.0f);
//        table.add(menuButton).pad(10.0f).width(100.0f).height(100.0f);

        table.pack();
        table.setPosition(deadPort.getWorldWidth()/2.0f - table.getWidth()/2.0f, 0);
        table.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo((deadPort.getWorldWidth()/2.0f-table.getWidth()/2.0f),
                                (2.0f*deadPort.getWorldHeight()/3.0f-table.getHeight()/2.0f),1.0f, Interpolation.swing))));
        // table.setDebug(true);

        buttonTable= new Table();
        buttonTable.add(retryButton).pad(10.0f).width(100.0f).height(100.0f);
        buttonTable.add(levelButton).pad(10.0f).width(100.0f).height(100.0f);
        buttonTable.add(menuButton).pad(10.0f).width(100.0f).height(100.0f);
        buttonTable.pack();

        buttonTable.setPosition(deadPort.getWorldWidth()/2.0f - buttonTable.getWidth()/2.0f, deadPort.getWorldHeight());
        buttonTable.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo((deadPort.getWorldWidth()/2.0f-buttonTable.getWidth()/2.0f),
                                (deadPort.getWorldHeight()/4.0f-buttonTable.getHeight()/2.0f),1.0f, Interpolation.swing)),delay(0.3f)));
        //table.setDebug(true);
        //buttonTable.setDebug(true);
    }

    public void addEventListeners(){
        retryButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                PlayScreen playScreen= new PlayScreen(game, levelScreen.getLevelManager().getCurrentLevel(), levelScreen);
                game.setScreen(playScreen);
                levelScreen.getLevelManager().updateLevel(playScreen, playScreen.getCurrentLevel().getLevelNum());
                outGameMusic.stop();
                com.SpaceWreck.game.MainGame.handler.hideBannerAd();
                //Gdx.app.log("LevelSwitch", "pressed");
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }

        });

        menuButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new MenuScreen(game));
                com.SpaceWreck.game.MainGame.handler.hideBannerAd();
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }

        });
        levelButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new LevelScreen(game));

                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }

        });

    }

    public void setScores(){

        temp=(player.getScore()-score) * lerp;
        if(temp>0 && temp<1){
            temp=1;
        }
        score+= (int)temp;  //interpolation
        scoreLabel.setText(score+"");
        if(PlayScreen.inGameMusic.isPlaying()){
            PlayScreen.inGameMusic.stop();
        }
        if(!outGameMusic.isPlaying()){
            outGameMusic.play();
        }



    }





    public void update(float delta){

        if(flag){
            Gdx.input.setInputProcessor(this.stage);
            if(!adShown){
                com.SpaceWreck.game.MainGame.handler.showInterstitialAd(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
            adShown=true;
            com.SpaceWreck.game.MainGame.handler.showBannerAd();
            flag=false;
        }





        if(adShown){
            setScores();
            stage.act();
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        update(delta);
        shapeRenderer.begin(ShapeType.Filled);
//        shapeRenderer.setColor(playScreen.getCurrentLevel().getColor().r,
//                playScreen.getCurrentLevel().getColor().g,
//                playScreen.getCurrentLevel().getColor().a, 0.3f);
        shapeRenderer.setColor(0,0,0,0.6f);
        shapeRenderer.rect(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        deadPort.update(width, height);
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
        shapeRenderer.dispose();
        font.dispose();
    }
}
