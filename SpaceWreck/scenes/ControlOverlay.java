package com.SpaceWreck.scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.SpaceWreck.screens.PlayScreen;
import com.SpaceWreck.sprites.Player;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by harsh on 06/20/17.
 */

public class ControlOverlay {

    private OrthographicCamera overlayCam;
    private Viewport overlayViewPort;
    public Stage stage;
    private Table controlTable;
    private Table shootTable;
    private Player player;
    private PlayScreen playScreen;
    private SpriteBatch batch;
    private TextureAtlas atlas;

    private ImageButton up;
    private ImageButton down;
    private ImageButton left;
    private ImageButton right;
    private ImageButton shoot;


    public ControlOverlay(PlayScreen playScreen){
        this.playScreen= playScreen;
        this.player=playScreen.getPlayer();
        this.batch= playScreen.getGame().batch;
        this.atlas= playScreen.getGame().getAssetManager().get("Images/Player/PlayerAtlas.txt");
        overlayCam= new OrthographicCamera();
        overlayViewPort= new FitViewport(GameConstants.viewPortWidth, GameConstants.viewPortHeight, overlayCam);
        stage= new Stage(overlayViewPort, batch);

        initializeButtons();
        addEventListeners();
        initializeTable();
        stage.addActor(controlTable);
        stage.addActor(shootTable);


    }

    public void initializeButtons(){
        up = new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("upUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("upDown"))));
        down = new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("downUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("downDown"))));
        left = new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("leftUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("leftDown"))));
        right = new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("rightUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("rightDown"))));
        shoot = new ImageButton(new TextureRegionDrawable(new TextureRegion(atlas.findRegion("shootUp"))),
                new TextureRegionDrawable(new TextureRegion(atlas.findRegion("shootDown"))));
    }
    public void addEventListeners(){
        up.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                player.setUpMove(true);
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }

        });
        down.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                player.setDownMove(true);
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }

        });
        left.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                player.setLeftMove(true);
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }

        });
        right.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                player.setRightMove(true);
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){


            }

        });
        shoot.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                player.setShooting(true);
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button){
                player.setShooting(false);

            }

        });
    }
    public void initializeTable(){
        controlTable = new Table();

        controlTable.add();
        controlTable.add(up).width(100.0f).height(100.0f).pad(10.0f);
        controlTable.add();
        controlTable.row();
        controlTable.add(left).width(100.0f).height(100.0f).pad(10.0f);
        controlTable.add(down).width(100.0f).height(100.0f).pad(10.0f);
        //controlTable.add().width(20.0f).height(20.0f);
        controlTable.add(right).width(100.0f).height(100.0f).pad(10.0f).padRight(20.0f);
//        controlTable.row();
//        controlTable.add();
//        controlTable.add(down).width(100.0f).height(100.0f);
//        controlTable.add();
        controlTable.pack();
        controlTable.setPosition(0,0);
        controlTable.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo((overlayViewPort.getWorldWidth()- controlTable.getWidth()),
                                0,1.0f, Interpolation.swing))));

        shootTable= new Table();
        shootTable.add(shoot).width(120.0f).height(150.0f).pad(30.0f).padLeft(50.0f);
        shootTable.pack();
        shootTable.setPosition(overlayViewPort.getWorldWidth(), 0);
        shootTable.addAction(sequence(alpha(0),
                parallel(fadeIn(0.5f, Interpolation.pow2),
                        moveTo(0,0,1.0f, Interpolation.swing))));

        //controlTable.setDebug(true);

//shootTable.setDebug(true);
    }
    public void render(float delta){
        stage.draw();
    }
    public void update(float delta){
        stage.act();
    }

    public void dispose(){
        stage.dispose();
        atlas.dispose();
    }

}
