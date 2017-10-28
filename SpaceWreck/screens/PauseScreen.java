package com.SpaceWreck.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.SpaceWreck.game.GameConstants;
import com.SpaceWreck.game.MainGame;
import com.SpaceWreck.screens.LevelScreen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by harsh on 06/27/17.
 */

public class PauseScreen implements Screen {

    private com.SpaceWreck.screens.PlayScreen playScreen;
    public Stage stage;
    private SpriteBatch batch;
    private OrthographicCamera pauseCam;
    private Viewport pausePort;
    private boolean paused;
    private ShapeRenderer shapeRenderer;
    private FreeTypeFontGenerator generator;
    private BitmapFont font;
    private ImageButton playButton;
    private ImageButton retryButton;
    private ImageButton levelButton;
    private ImageButton menuButton;

    private TextureAtlas atlas;
    private MainGame game;
    private com.SpaceWreck.screens.LevelScreen levelScreen;
    private com.SpaceWreck.levels.Level currentLevel;
    private Table table;

    private boolean adShown;
    public PauseScreen(com.SpaceWreck.screens.PlayScreen playScreen){
        this.playScreen=playScreen;
        this.game= playScreen.getGame();
        this.currentLevel= playScreen.getCurrentLevel();
        this.levelScreen= playScreen.getLevelScreen();
        this.batch= playScreen.getGame().batch;
        this.pauseCam=new OrthographicCamera();
        this.pausePort= new FitViewport(GameConstants.viewPortWidth, com.SpaceWreck.game.GameConstants.viewPortHeight, pauseCam);
        stage= new Stage(pausePort, batch);
        this.paused= playScreen.isPaused();
        this.atlas= playScreen.getGame().getAssetManager().get("Images/Player/PlayerAtlas.txt", TextureAtlas.class);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Captureit.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size =70;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        font = generator.generateFont(parameter);
        table= new Table();


        playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("resumeUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("resumeDown"))));
        retryButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("retryUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("retryDown"))));
        menuButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("menuUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("menuDown"))));
        levelButton=new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("levelsUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("levelsDown"))));

        table.add(playButton).pad(20.0f);
        table.add(retryButton).pad(20.0f);
        table.add(levelButton).pad(20.0f);
        table.add(menuButton).pad(20.0f);
        table.pack();
       // playButton.setPosition(pausePort.getWorldWidth()/2.0f- playButton.getWidth()/2.0f, pausePort.getWorldHeight());
        table.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo((pausePort.getWorldWidth()/2.0f- table.getWidth()/2.0f),
                                (pausePort.getWorldHeight()/2.0f- table.getHeight()/2.0f),1.0f, Interpolation.swing))));
        shapeRenderer=  new ShapeRenderer();
        addEventListeners();
        stage.addActor(table);

        this.adShown =false;

    }

    public void addEventListeners(){
        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                playScreen.setPaused(false);
                playScreen.updateInputProcessor();
                MainGame.handler.hideBannerAd();
                adShown=false;
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }
        });

        retryButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                com.SpaceWreck.screens.PlayScreen playScreen= new com.SpaceWreck.screens.PlayScreen(game, currentLevel, levelScreen);
                game.setScreen(playScreen);
                levelScreen.getLevelManager().updateLevel(playScreen, playScreen.getCurrentLevel().getLevelNum());
                adShown=false;

                //Gdx.app.log("LevelSwitch", "pressed");
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
                adShown=false;
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }

        });
    }

    public void update(float delta){

        if(Gdx.input.getInputProcessor() != this.stage){
            Gdx.input.setInputProcessor(this.stage);
        }
        stage.act();

        if(!adShown){
            MainGame.handler.showBannerAd();
            adShown=true;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        update(delta);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,0.6f);
        shapeRenderer.rect(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        pausePort.update(width, height);
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
        shapeRenderer.dispose();
    }
}
