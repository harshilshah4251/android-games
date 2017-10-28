package com.SpaceWreck.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by harsh on 06/20/17.
 */

public class HUD implements Disposable {
    private OrthographicCamera hudCam;
    public Stage stage;
    private Viewport hudPort;
    private Table scoreTable;
    private Table infoTable;


    private static Integer score;
    private static Label scoreLabel;
    private static Label scoreText;

    private static Label highScoreLabel;
    private static Label highScoreText;
    private static Integer highScore;

    private static Label levelName;

    private SpriteBatch batch;
    private com.SpaceWreck.screens.PlayScreen playScreen;
    private com.SpaceWreck.sprites.Player player;
    private ShapeRenderer renderer;
    private HealthBar healthBar;
    private TextureAtlas atlas;
    private FreeTypeFontGenerator generator;

    private com.SpaceWreck.screens.PauseScreen pauseScreen;
    private float lerp;
    private ImageButton pause;
    private float temp;

    public HUD(com.SpaceWreck.screens.PlayScreen playScreen){
        this.playScreen= playScreen;
        this.player= playScreen.getPlayer();
        this.batch= playScreen.getGame().batch;
        this.atlas= playScreen.getGame().getAssetManager().get("Images/Player/PlayerAtlas.txt");
        hudCam= new OrthographicCamera();
        hudPort= new FitViewport(com.SpaceWreck.game.GameConstants.viewPortWidth, com.SpaceWreck.game.GameConstants.viewPortHeight, hudCam);
        stage= new Stage(hudPort, batch);
        scoreTable = new Table();
        infoTable=new Table();
        healthBar= new HealthBar(playScreen);


        scoreTable.top().pad(10.0f);
        infoTable.top().pad(10.0f);
        //scoreTable.setFillParent(true);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Captureit.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        //parameter.color= Color.GOLD;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        BitmapFont font12 = generator.generateFont(parameter);

        // font size 12 pixels
        //font12.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        scoreLabel =new Label(String.format("%06d", score), new Label.LabelStyle(font12, Color.GOLD));
        scoreText= new Label("SCORE", new Label.LabelStyle(font12, Color.WHITE));

        highScore= playScreen.getCurrentLevel().getHighScore();
        highScoreLabel =new Label(String.format("%05d", highScore), new Label.LabelStyle(font12, Color.GOLD));
        highScoreText= new Label("HI SCORE", new Label.LabelStyle(font12, Color.WHITE));

        levelName= new Label(playScreen.getCurrentLevel().getLevelName(), new Label.LabelStyle(font12, playScreen.getCurrentLevel().getColor()));



        pause= new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("pauseUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("pauseDown"))));
        addEventListeners();

        scoreTable.add(scoreText);
        scoreTable.row();
        scoreTable.add(scoreLabel);
        scoreTable.pack();
        scoreTable.setPosition(hudPort.getWorldWidth()/2.0f- scoreTable.getWidth()/2.0f,0);
        scoreTable.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo((hudPort.getWorldWidth()/2.0f- scoreTable.getWidth()/2.0f),
                                (hudPort.getWorldHeight()- scoreTable.getHeight()),1.0f, Interpolation.swing))));

        stage.addActor(scoreTable);

        infoTable.add(levelName).colspan(2);
        infoTable.row();
        infoTable.add(highScoreText).spaceRight(20.0f);
        infoTable.add(highScoreLabel);
        infoTable.pack();
        infoTable.setPosition(0, hudPort.getWorldHeight());
        infoTable.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo(0,
                                (hudPort.getWorldHeight()- infoTable.getHeight()),1.0f, Interpolation.swing))));

        stage.addActor(infoTable);
        pause.setSize(10.0f, 10.0f);
        pause.pack();

        pause.setPosition(hudPort.getWorldWidth(), hudPort.getWorldHeight());
        pause.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo((hudPort.getWorldWidth()-pause.getWidth() -15.0f), (hudPort.getWorldHeight()-pause.getHeight()-15.0f),1.0f, Interpolation.swing))));
        stage.addActor(pause);
        pauseScreen= new com.SpaceWreck.screens.PauseScreen(playScreen);
        score=0;
        temp=0;
        lerp=0.1f;

    }
    public void addEventListeners(){
        pause.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){

                    playScreen.setPaused(true);

                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
            }
        });
    }
    public void updateScore(){
        //Gdx.app.log(score+"", player.getScore()+"");
          temp=(player.getScore()-score) * lerp;
        if(temp>0 && temp<1){
            temp=1;
        }
            score+= (int)temp;  //interpolation
            //score= player.getScore();
            scoreLabel.setText(score + "");
    }
    public void update(float delta){
        updateScore();
        healthBar.update(delta);
        stage.act();
    }
    public void render(float delta){
        healthBar.render(delta);
        stage.draw();
    }
    @Override
    public void dispose() {
        generator.dispose();// don't forget to dispose to avoid memory leaks!
        stage.dispose();
        healthBar.dispose();
    }
    public com.SpaceWreck.screens.PauseScreen getPauseScreen(){
        return this.pauseScreen;
    }
}
