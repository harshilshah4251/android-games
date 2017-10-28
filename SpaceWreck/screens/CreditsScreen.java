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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

public class CreditsScreen implements Screen {

    private BitmapFont font;
    private FreeTypeFontGenerator generator;

    private Table infoTable;
    private Stage stage;
    private OrthographicCamera creditsCam;
    private Viewport creditsViewPort;
    private SpriteBatch batch;
    private com.SpaceWreck.game.MainGame game;
    private ImageButton menu;
    private TextureAtlas atlas;


    public CreditsScreen(com.SpaceWreck.game.MainGame game){
        this.game= game;
        this.batch= game.batch;
        this.atlas= game.getAssetManager().get("Images/Player/PlayerAtlas.txt");
        creditsCam= new OrthographicCamera();
        creditsViewPort= new FitViewport(com.SpaceWreck.game.GameConstants.viewPortWidth, com.SpaceWreck.game.GameConstants.viewPortHeight, creditsCam);

        stage= new Stage(creditsViewPort, batch);

        infoTable = new Table();
        menu= new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("menuUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("menuDown"))));
        menu.pack();
        menu.setPosition(creditsViewPort.getWorldWidth(), creditsViewPort.getWorldHeight());
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/CreditsFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size=25;
        font= generator.generateFont(parameter);
        addEventListeners();
        initializeTable();


        stage.addActor(menu);
        stage.addActor(infoTable);

        Gdx.input.setInputProcessor(this.stage);
        com.SpaceWreck.game.MainGame.handler.showBannerAd();

    }
    public void initializeTable(){
        infoTable.add(new Label("SPECIAL CREDITS \n             TO:", new Label.LabelStyle(font, Color.GOLDENROD))).colspan(2).center();
        infoTable.row();
        infoTable.add(new Label(" Idea \n Design \n Development \n & Graphics ", new Label.LabelStyle(font, Color.WHITE))).left().spaceRight(Gdx.graphics.getWidth()/6.0f);
        infoTable.add(new Label(" Harshil Shah ", new Label.LabelStyle(font, Color.WHITE))).left();
        infoTable.row();
        infoTable.add();
        infoTable.row();
        infoTable.add(new Label(" Sound Assets", new Label.LabelStyle(font, Color.GOLD))).left().spaceRight(Gdx.graphics.getWidth()/6.0f);
        infoTable.add(new Label(" DL Sounds \n SoundGator \n PurplePlanet",new Label.LabelStyle(font, Color.GOLD) )).left();
        infoTable.row();
        infoTable.add(new Label(" Fonts", new Label.LabelStyle(font, Color.FIREBRICK))).left().spaceRight(Gdx.graphics.getWidth()/6.0f);
        infoTable.add(new Label(" FontSquirrel \n UrbanFonts",new Label.LabelStyle(font, Color.FIREBRICK) )).left();
        infoTable.row();
        infoTable.add(new Label("And a heartily thanks to all of my audience :) :)", new Label.LabelStyle(font, Color.GOLDENROD))).colspan(2).center();
        infoTable.pack();
        infoTable.setPosition(creditsViewPort.getWorldWidth()/2.0f- infoTable.getWidth()/2.0f, creditsViewPort.getWorldHeight());
        infoTable.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo(creditsViewPort.getWorldWidth()/2.0f- infoTable.getWidth()/2.0f,
                                (creditsViewPort.getWorldHeight()/2.0f- infoTable.getHeight()/2.0f),1.0f, Interpolation.swing))));
        menu.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo(creditsViewPort.getWorldWidth()- menu.getWidth()-40.0f,
                                (creditsViewPort.getWorldHeight()- menu.getHeight()-40.0f),1.0f, Interpolation.swing))));


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
        creditsViewPort.update(width, height);
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
