package com.SpaceWreck.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.SpaceWreck.managers.BulletManager;
import com.SpaceWreck.managers.WallManager;

/**
 * Created by harsh on 06/20/17.
 */

public class PlayScreen implements Screen {

    private com.SpaceWreck.game.MainGame game;
    private LevelScreen levelScreen;
    private GameOverScreen gameOverScreen;
    private com.SpaceWreck.screens.DeadScreen deadScreen;
    private com.SpaceWreck.levels.Level currentLevel;
    private com.SpaceWreck.sprites.Player player;

    private Viewport gamePort;
    private OrthographicCamera gameCam;
    private com.SpaceWreck.scenes.HUD hud;
    private com.SpaceWreck.scenes.ControlOverlay overlay;

    private TmxMapLoader mapLoader;
    private OrthogonalTiledMapRenderer renderer;
    private TiledMap map;

    private World world;
    private Box2DDebugRenderer b2dr;

    private WallManager wallManager;
    private com.SpaceWreck.input.InputHandler inputHandler;
    private com.SpaceWreck.managers.StaticEntityManager entityManager;
    private BulletManager bulletManager;
    private com.SpaceWreck.managers.EnemyManager enemyManager;
    private com.SpaceWreck.utilities.WorldContactListener contactListener;

    private InputMultiplexer multiplexer;
    private ShapeRenderer shapeRenderer;
    private float statetime;
    private Array<Color> colors;
    public static Music inGameMusic;

    private boolean paused;



    public PlayScreen(com.SpaceWreck.game.MainGame game, com.SpaceWreck.levels.Level currentLevel, LevelScreen levelScreen){
        this.game= game;
        this.currentLevel= currentLevel;
        this.levelScreen= levelScreen;
        gameCam= new OrthographicCamera(400.0f/ com.SpaceWreck.game.GameConstants.ppm, 250.0f/ com.SpaceWreck.game.GameConstants.ppm);
        gamePort= new FitViewport(400.0f/ com.SpaceWreck.game.GameConstants.ppm, 250.0f/ com.SpaceWreck.game.GameConstants.ppm, gameCam);
        gameCam.position.set(currentLevel.getInitX(), currentLevel.getInitY(), 0.0f);
        multiplexer= new InputMultiplexer();



        this.map= currentLevel.getMap();
        renderer= new OrthogonalTiledMapRenderer(map, 1/ com.SpaceWreck.game.GameConstants.ppm);

        world= new World(new Vector2(0,0.0f), false);
        b2dr= new Box2DDebugRenderer();
        contactListener= new com.SpaceWreck.utilities.WorldContactListener();
        world.setContactListener(contactListener);

        player= new com.SpaceWreck.sprites.Player(this);
        hud= new com.SpaceWreck.scenes.HUD(this);
        overlay= new com.SpaceWreck.scenes.ControlOverlay(this);
        gameOverScreen= new GameOverScreen(levelScreen, this);
        deadScreen= new com.SpaceWreck.screens.DeadScreen(levelScreen, this);
        //Gdx.app.log("PlayScreen", "Created");

        //Managers
        wallManager= new WallManager();
        entityManager= new com.SpaceWreck.managers.StaticEntityManager(this);
        bulletManager= new com.SpaceWreck.managers.BulletManager(this);
        inputHandler= new com.SpaceWreck.input.InputHandler(this);
        enemyManager= new com.SpaceWreck.managers.EnemyManager(this);

        if(Gdx.app.getType().equals(Application.ApplicationType.Android)){
            multiplexer.addProcessor(0,overlay.stage);
            multiplexer.addProcessor(1, hud.stage);
            Gdx.input.setInputProcessor(multiplexer);
        }
        else if(Gdx.app.getType().equals(Application.ApplicationType.Desktop)){
            multiplexer.addProcessor(0,inputHandler);
            multiplexer.addProcessor(1,hud.stage);
            Gdx.input.setInputProcessor(multiplexer);


        }

        this.paused=false;


        //Music
        inGameMusic= game.getAssetManager().get("Sounds/inGameMusic.mp3", Music.class);
        inGameMusic.setLooping(true);
        inGameMusic.play();

        colors= new Array<Color>();
        colors.addAll(Color.CORAL, Color.BLUE, Color.RED, Color.GOLD, Color.FOREST, Color.LIME, Color.MAGENTA);

        com.SpaceWreck.game.MainGame.handler.hideBannerAd();
    }

    public void doPhysicsStep(float delta){

        float deltaTime= Math.min(1/60.0f, 0.1f);

        world.step(deltaTime, 6, 2);    //requires improvisation


    }

    public void update(float delta){
        doPhysicsStep(delta);

        player.update(delta);
        entityManager.update(delta);
        enemyManager.update(delta); //??????????????
        bulletManager.update(delta);
        hud.update(delta);
        overlay.update(delta);


        recalculate();
        renderer.setView(gameCam);

    }
    public void updateInputProcessor(){
        if(Gdx.input.getInputProcessor() != this.multiplexer){
            Gdx.input.setInputProcessor(multiplexer);
        }
    }
    public void recalculate(){
        float lerp= 0.1f;
        if(player.isRightMove()) {
            gameCam.position.x = gameCam.position.x + ((player.getBody().getPosition().x + gamePort.getWorldWidth() / 4.0f) - gameCam.position.x) * lerp;
            gameCam.position.y=gameCam.position.y+((player.getBody().getPosition().y-gameCam.position.y)*lerp);
        }if(player.isUpMove()) {
            gameCam.position.y = gameCam.position.y + ((player.getBody().getPosition().y + gamePort.getWorldHeight() / 4.0f) - gameCam.position.y) * lerp;
            gameCam.position.x=gameCam.position.x+((player.getBody().getPosition().x-gameCam.position.x)*lerp);
        }if(player.isLeftMove()) {
            gameCam.position.x = gameCam.position.x + ((player.getBody().getPosition().x - gamePort.getWorldWidth() / 4.0f) - gameCam.position.x) * lerp;
            gameCam.position.y=gameCam.position.y+((player.getBody().getPosition().y-gameCam.position.y)*lerp);
        }if(player.isDownMove()) {
            gameCam.position.y = gameCam.position.y + ((player.getBody().getPosition().y - gamePort.getWorldHeight() / 4.0f) - gameCam.position.y) * lerp;
            gameCam.position.x=gameCam.position.x+((player.getBody().getPosition().x-gameCam.position.x)*lerp);
        }
        gameCam.update();

    }
    public com.SpaceWreck.game.MainGame getGame(){
        return this.game;
    }
    public World getWorld(){
        return this.world;
    }
    public WallManager getWallManager(){
        return this.wallManager;
    }
    public com.SpaceWreck.sprites.Player getPlayer(){return  this.player;}
    public com.SpaceWreck.levels.Level getCurrentLevel() {
        return currentLevel;
    }
    public com.SpaceWreck.managers.StaticEntityManager getEntityManager() {
        return entityManager;
    }
    public OrthographicCamera getGameCam() {
        return gameCam;
    }
    public BulletManager getBulletManager() {
        return bulletManager;
    }
//    public LevelScreen getLevelScreen() {
//        return levelScreen;
//    }
    public com.SpaceWreck.managers.EnemyManager getEnemyManager() {
        return enemyManager;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


       // b2dr.render(world, gameCam.combined);

        renderer.render();
        game.batch.setProjectionMatrix(gameCam.combined);  //goes in order
        game.batch.begin();
        entityManager.render(delta);
        bulletManager.render(delta);
        enemyManager.render(delta);
        player.render(delta);
        game.batch.end();
        if(!this.isPaused()){
            update(delta);
            if(!player.isHasFinishedLevel() && !player.isDead()){
                game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
                hud.render(delta);
                overlay.render(delta);
            }
        }

        else if(this.isPaused()){
            hud.getPauseScreen().render(delta);

        }
                if(player.isDead()){
                    inGameMusic.stop();
                    player.setDead(true);
                    deadScreen.render(delta);
                }
                else if(player.isHasFinishedLevel()){
                    player.setToDestroy(true);
                    //player.setDead(true);
                    inGameMusic.stop();
                    gameOverScreen.render(delta);
                }
//            else if(!player.isHasFinishedLevel() && !this.isPaused()) {
//                game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
//                hud.render(delta);
//                overlay.render(delta);
//            }
    }
    public void triggerDiscoScreen(float delta){
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        statetime+=delta;
        if(statetime>0.5f){
            colors.shuffle();
            statetime=0;
        }
        Color color=colors.get(0);
        shapeRenderer= new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color.r, color.g, color.b, 0.6f);
        shapeRenderer.rect(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();



        //Gdx.app.log("DiscoScreen", "playScreen");
    }
    public GameOverScreen getGameOverScreen() {
        return gameOverScreen;
    }

    public Viewport getGamePort() {
        return gamePort;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
        inGameMusic.stop();
        enemyManager.playSound(false);
    }

    public LevelScreen getLevelScreen() {
        return levelScreen;
    }

    public void setLevelScreen(LevelScreen levelScreen) {
        this.levelScreen = levelScreen;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        world.dispose();
        renderer.dispose();
        currentLevel.dispose();
        hud.dispose();
        overlay.dispose();
    }
}
