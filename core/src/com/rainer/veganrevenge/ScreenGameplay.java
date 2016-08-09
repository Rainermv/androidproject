package com.rainer.veganrevenge;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by rainervieira on 02/08/2016.
 */
public class ScreenGameplay implements Screen {

    GameMain gameMain;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    final float VIEWPORT_WIDTH = 15;//20;//80;//40;
    final float VIEWPORT_HEIGHT = 9;//12;//50;//25;

    final float CAMERA_OFFSET = 7;

    //final float CHARSCALE = 0.01f;
    //final float BODYSCALE = 0.8f;


    final float GRAVITY = -15;

    //final float SPAWN_OFFSET = 10;
    float SPAWN_TIMER_START = 2f;
    float SPAWN_TIMER_END = 5f;

    final boolean DEBUG_DRAW = true;

    Timer enemySpawner;

    CollisionHandler contactListener = CollisionHandler.getInstance();
    InputHandler inputHandler = InputHandler.getInstance();
    //Texture img;

    final HashMap<String, Texture> static_textures = new HashMap<String, Texture>();
    private Floor theFloor;
    final float FLOOR_HEIGHT = 1.5f;

    World world;
    Box2DDebugRenderer debugRenderer;
    static final float STEP_TIME = 1f / 60f;
    static final int VELOCITY_ITERATIONS = 6;
    static final int POSITION_ITERATIONS = 2;
    float accumulator = 0;

    public PlayerController PC;

    //public Array<StaticGameObject> background = new Array<StaticGameObject>();
    public Array<GameObject> objects = new Array<GameObject>();

    public ParallaxBackground parallaxBackground = null;

    final float ANIMATION_FPS = 1 / 15f;
    private Bar healthBar;

    public ScreenGameplay(GameMain gameMain){
        this.gameMain = gameMain;
    }

    @Override
    public void show() {

        PC = PlayerController.getInstance();

        AnimationFactory.getInstance().build();

        setUpBox2D();

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera();
        enemySpawner = new Timer();
        //camera.setToOrtho(false, 800, 480);

        //viewport = new ExtendViewport(800, 600, camera);
        viewport = new ExtendViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);

        //addSprites("spritesheets/knight.txt"); // add background
        //static_textures.put("background", new Texture("sprites/bg.png"));
        static_textures.put("ground", new Texture("sprites/ground.png"));
        static_textures.put("treeline", new Texture("sprites/trees.png"));
        static_textures.put("backdrop", new Texture("sprites/background.png"));
        //addSprites("sprites/bg.png");

        PC.setPlayer(new Player(world, new Vector3(VIEWPORT_WIDTH * 0.2f, FLOOR_HEIGHT,0), this));
        AddObject(PC.getPlayer());

        parallaxBackground = new ParallaxBackground(new Vector3(0,0,0),
                static_textures.get("backdrop"),
                static_textures.get("treeline"),
                static_textures.get("ground"));

        parallaxBackground.adjustLayerPosY(0,-8.5f);
        parallaxBackground.adjustLayerPosY(1,0.5f);
        parallaxBackground.adjustLayerPosY(2,FLOOR_HEIGHT -2.5f);

        scheduleSpawnTimer();

        world.setContactListener(contactListener);

        inputHandler.set(this, camera);
        Gdx.input.setInputProcessor(inputHandler);

        this.healthBar = new Bar(new Vector3(0.02f,0.93f,0f), 0.6f, 0.05f,
                Color.GREEN, Color.RED, PC.getPlayer().health,PC.getPlayer().health);

    }

    private void cameraFollow(GameObject target){

        camera.position.x = target.getX() + CAMERA_OFFSET;

    }

    private void scheduleSpawnTimer(){

        final float spawnTime = MathUtils.random(SPAWN_TIMER_START, SPAWN_TIMER_END);

        enemySpawner.scheduleTask(new Timer.Task(){

            @Override
            public void run(){
                spawnEnemy();
                scheduleSpawnTimer();
            }
        }, spawnTime);

    }

    private void setUpBox2D(){

        // Inicializa Box2D e cria novo "world"
        Box2D.init();
        world = new World(new Vector2(0, GRAVITY), true);
        debugRenderer = new Box2DDebugRenderer();

        theFloor = new Floor(world, 10000000000000000f, FLOOR_HEIGHT);

    }

    @Override
    public void render (float delta) {

        cameraFollow(PC.getPlayer());
        camera.update();

        this.loopFixedUpdate();
        this.loopUpdate();

        this.loopDraw();
        this.loopCleanUp();

    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height, true);

        batch.setProjectionMatrix(camera.combined);
        camera.update();
    }

    private void loopUpdate(){

        parallaxBackground.update(PC.getPlayer().getX());

        for (GameObject obj : objects) {
            obj.update();
        }

        healthBar.updateValue(PC.getPlayer().health);

    }

    private void loopFixedUpdate() {

        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;

            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

            for (GameObject obj : objects) {
                obj.fixedUpdate();
            }
        }
    }

    private void loopDraw(){

        Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        parallaxBackground.draw(batch);

        for (GameObject obj : objects){
            obj.draw(batch);
        }
        batch.end();

        batch.setProjectionMatrix(camera.projection);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (GameObject obj : objects){
            obj.drawUI(shapeRenderer);
        }
        healthBar.drawUI(shapeRenderer);
        shapeRenderer.end();

        if (DEBUG_DRAW)
            debugRenderer.render(world, camera.combined);

    }

    private void loopCleanUp(){

        Iterator<GameObject> i = objects.iterator();
        while (i.hasNext()) {
            GameObject object = i.next();

            if (object.flagDelete || object.getX() < camera.position.x - VIEWPORT_WIDTH){

                if (object.tag == "PLAYER") continue;

                object.dispose();
                i.remove();
                continue;
            }

            /*
            if ((object instanceof Character) && ((Character)object).flagDead){


                if (object.tag =="ENEMY"){

                }
            }
            */
        }
    }

    public void spawnEnemy(){

        Vector3 position = new Vector3(PC.getPlayer().getX() + VIEWPORT_WIDTH * 1.1f, FLOOR_HEIGHT, 0);
        AddObject(new Enemy(world, position, this));

    }

    public void AddObject(GameObject go){
        go.start();
        objects.add(go);
    }

    @Override
    public void dispose() {

        for (GameObject obj : objects) {
            obj.dispose();
        }

        parallaxBackground.dispose();
        world.dispose();
        batch.dispose();
        debugRenderer.dispose();
    }

    public void endGame(){
        gameMain.startScreen();
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

}
