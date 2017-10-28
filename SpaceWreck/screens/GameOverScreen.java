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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.SpaceWreck.game.GameConstants;
import com.SpaceWreck.game.MainGame;
import com.SpaceWreck.managers.LevelManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by harsh on 06/20/17.
 */

public class GameOverScreen implements Screen{
    private LevelScreen levelScreen;
    private MainGame game;
    private com.SpaceWreck.screens.PlayScreen playScreen;
    private TextureAtlas atlas;

    private OrthographicCamera gameOverCam;
    public Stage stage;
    private Viewport gameOverPort;
    private Table table;
    private Table buttonTable;

    private static Label levelName;
    private static Label performanceLabel;

    private static Integer score;
    private static Label scoreLabel;
    private static Label scoreText;


    private static Label highScoreLabel;
    private static Label highScoreText;
    private static int highScore;

    private static Label totalPointsLabel;
    private static Label totalPointsText;
    private static int totalPoints;

    public static Music outGameMusic;

    private boolean flag=true;

    private float temp1;
    private float temp2;
    private float temp3;
    private float lerp;





    private SpriteBatch batch;
    private com.SpaceWreck.sprites.Player player;

    private ShapeRenderer shapeRenderer;
    private FreeTypeFontGenerator generator;
    private BitmapFont font;

    private ImageButton levelButton;
    private ImageButton menuButton;
    private ImageButton nextLevelButton;
    private ImageButton retryButton;
    private ImageButton achievementsButton;
    private ImageButton leaderBoardButton;

    private Image crySmiley;
    private Image goodSmiley;
    private  Image placeHolder;

    private boolean adShown;



    public GameOverScreen(LevelScreen levelScreen, com.SpaceWreck.screens.PlayScreen playScreen){
        this.levelScreen= levelScreen;
        this.playScreen= playScreen;
        this.game= playScreen.getGame();
        this.player= playScreen.getPlayer();
        this.batch= playScreen.getGame().batch;
        this.atlas= playScreen.getGame().getAssetManager().get("Images/Player/PlayerAtlas.txt", TextureAtlas.class);
        gameOverCam= new OrthographicCamera();
        gameOverPort= new FitViewport(GameConstants.viewPortWidth, GameConstants.viewPortHeight);
        stage= new Stage(gameOverPort, batch);
        shapeRenderer= new ShapeRenderer();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Captureit.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        font = generator.generateFont(parameter);
        crySmiley= new Image(atlas.findRegion("crySmiley"));
        goodSmiley= new Image(atlas.findRegion("thumbsUpSmiley"));
        placeHolder= new Image(atlas.findRegion("thumbsUpSmiley"));
        initializeMusic();
        initializeLabels();
        initializeButtons();
        initializeTable();
        addEventListeners();



        stage.addActor(table);
        stage.addActor(buttonTable);

        score=0;
        highScore=0;
        totalPoints=0;
        temp1=0;
        temp2=0;
        temp3=0;
        lerp=0.05f;

        this.adShown=false;

    }
    public void initializeMusic(){
        outGameMusic= playScreen.getGame().getAssetManager().get("Sounds/outGameMusic.mp3", Music.class);
        outGameMusic.setLooping(true);
    }
    public void initializeLabels(){
        levelName= new Label(playScreen.getCurrentLevel().getLevelName(),new Label.LabelStyle(font, playScreen.getCurrentLevel().getColor()) );
        performanceLabel= new Label("",new Label.LabelStyle(font, Color.GOLD) );

        scoreText= new Label("SCORE", new Label.LabelStyle(font, Color.WHITE));
        scoreLabel=new Label(String.format("%06d", score), new Label.LabelStyle(font, Color.GOLD));

        highScoreText= new Label("HIGHSCORE", new Label.LabelStyle(font, Color.WHITE));
        highScoreLabel=new Label(String.format("%06d", highScore), new Label.LabelStyle(font, Color.GOLD));

        totalPointsText= new Label("TOTAL POINTS", new Label.LabelStyle(font, Color.WHITE));
        totalPointsLabel=new Label(String.format("%06d", totalPoints), new Label.LabelStyle(font, Color.GOLD));
    }
    public void initializeButtons(){

        retryButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("retryUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("retryDown"))));
        menuButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("menuUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("menuDown"))));
        levelButton=new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("levelsUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("levelsDown"))));
        nextLevelButton=new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("nextLevelUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("nextLevelDown"))));
        achievementsButton=new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("achievementsUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("achievementsDown"))));
        leaderBoardButton =new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("leaderBoardUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("leaderBoardDown"))));
    }
    public void initializeTable(){
        table= new Table();


        table.add(scoreText).pad(10.0f);
        table.add(scoreLabel).pad(10.0f);
        table.row();
        table.add(highScoreText).pad(10.0f);
        table.add(highScoreLabel).pad(10.0f);
        table.row();
        table.add(totalPointsText).pad(10.0f);
        table.add(totalPointsLabel).pad(10.0f);
        table.row();
        table.add(levelName).pad(10.0f).expandX().colspan(2);
        table.row();
        table.add(performanceLabel).pad(10.0f).expandX().colspan(2);
        table.row();
        table.add(placeHolder).center().colspan(2);
        table.pack();
        table.setPosition(gameOverPort.getWorldWidth(), gameOverPort.getWorldHeight());
        table.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo((gameOverPort.getWorldWidth()/2.0f-table.getWidth()/2.0f),
                                ( 2.0f*gameOverPort.getWorldHeight()/3.0f-table.getHeight()/2.0f),1.0f, Interpolation.swing))));

        buttonTable= new Table();
        buttonTable.add(retryButton).pad(10.0f);
        buttonTable.add(nextLevelButton).pad(10.0f);
        buttonTable.add(levelButton).pad(10.0f);
        buttonTable.add(menuButton).pad(10.0f);
        buttonTable.add(achievementsButton).pad(10.0f);
        buttonTable.add(leaderBoardButton).pad(10.0f);
        buttonTable.pack();

        buttonTable.setPosition(0, 0);
        buttonTable.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo((gameOverPort.getWorldWidth()/2.0f-buttonTable.getWidth()/2.0f),
                                (gameOverPort.getWorldHeight()/4.0f-buttonTable.getHeight()/2.0f),1.0f, Interpolation.swing))));

       // table.setDebug(true);
    }

    public void addEventListeners(){
        retryButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                com.SpaceWreck.screens.PlayScreen playScreen= new com.SpaceWreck.screens.PlayScreen(game, levelScreen.getLevelManager().getCurrentLevel(), levelScreen);
                game.setScreen(playScreen);
                levelScreen.getLevelManager().updateLevel(playScreen, playScreen.getCurrentLevel().getLevelNum());
                outGameMusic.stop();
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }

        });

        nextLevelButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                levelScreen.getLevelManager().setLockStatus();
                if(!levelScreen.getLevelManager().getNextLevel().isLocked() && levelScreen.getLevelManager().getNextLevel() != null){
                    com.SpaceWreck.screens.PlayScreen playScreen= new com.SpaceWreck.screens.PlayScreen(game, levelScreen.getLevelManager().getNextLevel(), levelScreen);
                    game.setScreen(playScreen);
                    levelScreen.getLevelManager().updateLevel(playScreen, playScreen.getCurrentLevel().getLevelNum());
                    outGameMusic.stop();
                }


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
        menuButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new MenuScreen(game));
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
                if(Gdx.app.getType() .equals(Application.ApplicationType.Android)) {
                    MainGame.playServices.showAchievement();
                }
            }

        });
        leaderBoardButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                if(Gdx.app.getType() .equals(Application.ApplicationType.Android)) {
                    MainGame.playServices.showScore();
                }
            }

        });

    }

    public void setScores(){

        if(flag){
            if(player.getScore()> playScreen.getCurrentLevel().getHighScore()){
                LevelManager.data.set(playScreen.getCurrentLevel().getLevelNum(), player.getScore());
                LevelManager.recalculate();
                game.playServices.submitScore(LevelManager.data.get(0));
                playScreen.getCurrentLevel().setHighScore(player.getScore());
                writeToFile();
            }
            gauzePerformance();
            com.SpaceWreck.screens.PlayScreen.inGameMusic.stop();

            if(player.getScore()>=playScreen.getCurrentLevel().getGoodScore()){
                placeHolder.setDrawable(goodSmiley.getDrawable());
            }
            else if(player.getScore()<playScreen.getCurrentLevel().getGoodScore()){
                placeHolder.setDrawable(crySmiley.getDrawable());
            }
            Gdx.input.setInputProcessor(this.stage);
            MainGame.handler.showInterstitialAd(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Interstitial app closed");
                }
            });
            adShown= true;
            MainGame.handler.showBannerAd();
            flag=false;
        }

        temp1=(player.getScore()-score) * lerp;
        if(temp1>0 && temp1<1){
            temp1=1;
        }
        score+=(int)temp1;
        temp2=(LevelManager.getTotalPoints()-totalPoints) * lerp;
        if(temp2>0 && temp2<1){
            temp2=1;
        }
        totalPoints+=(int) temp2;
        temp3=(playScreen.getCurrentLevel().getHighScore()-highScore) * lerp;
        if(temp3>0 && temp3<1){
            temp3=1;
        }
        highScore+= (int)temp3;
        totalPointsLabel.setText(totalPoints+"");
        highScoreLabel.setText(highScore+"");
        scoreLabel.setText(score+"");


    }
    public void gauzePerformance(){
        performanceLabel.setText(playScreen.getCurrentLevel().getPerformance(player.getScore()));
        if(player.getScore()>playScreen.getCurrentLevel().getBestScore()){
            if(playScreen.getCurrentLevel().getLevelNum()==1){
                game.playServices.unlockAchievement(game.playServices.getAchievement1());
            }
            if(playScreen.getCurrentLevel().getLevelNum()==2){
                game.playServices.unlockAchievement(game.playServices.getAchievement2());
            }
            if(playScreen.getCurrentLevel().getLevelNum()==3){
                game.playServices.unlockAchievement(game.playServices.getAchievement3());
            }
            if(playScreen.getCurrentLevel().getLevelNum()==4){
                game.playServices.unlockAchievement(game.playServices.getAchievement4());
            }
            if(playScreen.getCurrentLevel().getLevelNum()==5){
                game.playServices.unlockAchievement(game.playServices.getAchievement5());
            }

        }
        performanceLabel.setColor(playScreen.getCurrentLevel().getPerfColor());



    }


    public void writeToFile(){
        LevelManager.dataFile.writeString("", false);
        for(Integer integer: LevelManager.data){
            LevelManager.dataFile.writeString(integer+"\n", true);
        }
    }

    public void update(float delta){
        setScores();

        if(adShown){
            stage.act();
            if(!outGameMusic.isPlaying()){
                outGameMusic.play();
            }
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
        gameOverPort.update(width, height);
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
        font.dispose();


    }
}
