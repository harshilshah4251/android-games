package com.SpaceWreck.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by harsh on 06/30/17.
 */

public class ContextScreen implements Screen {

    private BitmapFont font;
    private FreeTypeFontGenerator generator;

    private Table scrollPaneContainer;
    private Table tableContainer;
    private Table infoTable;
    private ScrollPane infoScrollPane;
    private Stage stage;
    private OrthographicCamera contextCam;
    private Viewport contextViewPort;
    private SpriteBatch batch;
    private com.SpaceWreck.game.MainGame game;

    private Image keplusStunner;
    private Image clawDrones;
    private Image magnetoDrones;
    private Image redBlasters;
    private Image grossGreeners;
    private Image whiteTrojans;
    private Image health;
    private Image fireAll;
    private Image treasureBox;
    private Image skeleton;
    private Image twoX;


    private ImageButton menu;

    private TextureAtlas atlas;

    public ContextScreen(com.SpaceWreck.game.MainGame game){
        this.game= game;
        this.batch= game.batch;
        this.atlas= game.getAssetManager().get("Images/Player/PlayerAtlas.txt", TextureAtlas.class);
        contextCam= new OrthographicCamera();
        contextViewPort= new FitViewport(com.SpaceWreck.game.GameConstants.viewPortWidth, com.SpaceWreck.game.GameConstants.viewPortHeight, contextCam);

        stage= new Stage(contextViewPort, batch);


        tableContainer= new Table();
        scrollPaneContainer= new Table();
        infoTable = new Table();
        infoScrollPane= new ScrollPane(tableContainer);
        infoScrollPane.setScrollingDisabled(true, false);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/CreditsFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size=17;
        font= generator.generateFont(parameter);
        menu= new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("menuUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("menuDown"))));
        menu.pack();
        menu.setPosition(contextViewPort.getWorldWidth(), contextViewPort.getWorldHeight());
        addEventListeners();
        initializeTable();


        scrollPaneContainer.add(infoScrollPane);
        scrollPaneContainer.setFillParent(true);
        stage.addActor(scrollPaneContainer);
        stage.addActor(menu);
        Gdx.input.setInputProcessor(this.stage);

    }

    public void initializeTable(){
        tableContainer.add(new Label("The Story goes down like this...", new Label.LabelStyle(font, Color.ORANGE))).colspan(2).center();
        tableContainer.row();
        infoTable.add(new Label(String.format("Major destruction and asphyxiating acquistion\n" +
                "were written in the fate of mankind and the Planet Earth.\n" +
                " The mankind,   that   was  striving to find Xtraterrestrial life,\n" +
                "has been consumed by its own destructive   visions.\n" +
                "However, you, Dr. LBert Newton, have evaded the invasion\n" +
                "by properly  nullifying and neutralizing the attacks of the ETs.\n" +
                "Therefore, I am assigning you the  duty to save rest of your fellow people\n" +
                " to escape the hold of the ETs. I will run you down through your goal, your\n" +
                "powers, and your enemies." ),new Label.LabelStyle(font, Color.WHITE))).left().space(20.0f);

        infoTable.row();
        infoTable.add(new Label(
                "So, here it goes,\n" +
                "You have been assigned ultrasonic shielded vehicle called the \n" +
                "KeplusStunner (inspired from Johannes Kepler). The vehicle travels at the \n" +
                "speed of light and has excessive durability and fuel efficiency. However, its \n" +
                "light weight body, designed for easy maneuvers at high speed, can cause \n" +
                "unstablity under enemy attacks.",new Label.LabelStyle(font, Color.FOREST))).right().space(20.0f);

        infoTable.row();
        infoTable.add(new Label("You will be provided with fuel cells mid-way in space to regain KeplusStunner's \n" +
                "lost health. The 2x and TreasureBox powerup will help you earn more points. \n" +
                "The multishooter gun powerup will help you fight dangerous enemies.\n" +
                "Warning: Be aware of the skeleton powerup-insufficient information.", new Label.LabelStyle(font, Color.FIREBRICK))).left().space(20.0f);;

        infoTable.row();
        infoTable.add(new Label("Enemies RunDown,\n"+
                "1) ClawDrones     : Move linearly and reverse their velocities upon colliding.\n" +
                "2) MagnetoDrones  : Use the limited magnetic fields to detect intruders in their vicinities.\n" +
                "3) RedBlasters    : Use more powerful radioactive detection system to track intruders. \n" +
                "4) GrossGreeners  : Insufficient Information\n" +
                "5) WhiteTrojans   : Insufficient Information\n" +
                "6) Static Shooters: Insufficient Information", new Label.LabelStyle(font, Color.ORANGE))).right().space(20.0f);
        infoTable.row();
        infoTable.add(new Label("Few more points:\n" +
                "Each planet is unlocked only if you gain sufficient total points.\n" +
                "Boxes of Emotions can be used to defend or attack the ETs.\n" +
                "PowerUps can help you earn xtra points.\n" +
                "And finally, rate it and review it if you love it.", new Label.LabelStyle(font, Color.ROYAL))).left().space(20.0f);
        infoTable.row();
        infoTable.add(new Label("Lots of love \n" +
                "Happy Wreckin' \n" +
                "GoodLuck !!!", new Label.LabelStyle(font, Color.SCARLET))).right().space(20.0f);


        infoTable.pack();



        tableContainer.add(infoTable);
        menu.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo(contextViewPort.getWorldWidth()- menu.getWidth()-40.0f,
                                (contextViewPort.getWorldHeight()- menu.getHeight()-40.0f),1.0f, Interpolation.swing))));
        //picScrollPane.setPosition(Gdx.graphics.getWidth()/2.0f, 0);
        // infoTable.setDebug(true);
        //picScrollPane.setDebug(true);
        //picTable.setDebug(true);
        //infoScrollPane.setDebug(true);
        //picTable.pack();
        //picScrollPane.pack();
    }
    public void addEventListeners(){
        menu.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y){

                game.setScreen(new MenuScreen(game));

            }
        });
    }
    public void update(float delta){
        stage.act();
    }

    @Override
    public void show() {

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
        contextViewPort.update(width, height);
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
        this.stage.dispose();
        generator.dispose();
        atlas.dispose();
        font.dispose();
    }
}
