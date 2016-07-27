package com.rainer.veganrevenge;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.HashMap;
import java.util.Iterator;

public class VeganGame extends ApplicationAdapter{

	private SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;
	private ExtendViewport viewport;

	final float VIEWPORT_WIDTH = 20;//80;//40;
	final float VIEWPORT_HEIGHT = 12;//50;//25;

	final float CAMERA_OFFSET = 7;

	final float CHARSCALE = 0.01f;
	final float BODYSCALE = 0.8f;

	final float GRAVITY = -15;

	final int BACKGROUND_IMAGES = 4;

	final float SPAWN_OFFSET = 18;
	float SPAWN_TIMER_START = 2;
	float SPAWN_TIMER_END = 5;

	Timer enemySpawner;

	CollisionHandler contactListener = CollisionHandler.getInstance();
	InputHandler inputHandler = InputHandler.getInstance();
	//Texture img;

	final HashMap<String, Texture> static_textures = new HashMap<String, Texture>();
	private Floor theFloor;
	final float FLOOR_HEIGHT = 2f;

	World world;
	Box2DDebugRenderer debugRenderer;
	static final float STEP_TIME = 1f / 60f;
	static final int VELOCITY_ITERATIONS = 6;
	static final int POSITION_ITERATIONS = 2;
	float accumulator = 0;

	public PlayerController PC;

	public Array<StaticGameObject> background = new Array<StaticGameObject>();
	public Array<Character> characters = new Array<Character>();

	//StaticGameObject bg1 = null;
	//StaticGameObject bg2 = null;
	
	@Override
	public void create () {

		PC = PlayerController.getInstance();

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
		static_textures.put("background", new Texture("sprites/bg.png"));
		//addSprites("sprites/bg.png");

		PC.setPlayer(new Player(world, new Vector3(5, FLOOR_HEIGHT,0), CHARSCALE, BODYSCALE));

		setUpBackground();
		scheduleSpawnTimer();

		AddCharacter(PC.getPlayer());

		world.setContactListener(contactListener);

		inputHandler.set(this, camera);
		Gdx.input.setInputProcessor(inputHandler);
	}

	private void setUpBackground(){

		for (int i = 0; i < BACKGROUND_IMAGES; i++){
			background.add(new StaticGameObject(new Vector3(0,0,0),static_textures.get("background"), CHARSCALE * 2));
		}

		float terrain_width = background.get(0).getWidth();
		for (int i = 0; i < BACKGROUND_IMAGES; i++){

			background.get(i).updatePosition(new Vector3(terrain_width * (i -1),0,0));

		}
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

	private void updateBackground(GameObject target){

		float x = target.getX();

		for (GameObject obj : background) {

			StaticGameObject bg =(StaticGameObject)obj;
			float translate_point = bg.x + bg.getWidth() + (background.size/2 ) * bg.getWidth();

			if (x  > translate_point){
				bg.translate(bg.getWidth()* background.size,0);

				Logger.log("player pos: " + x);

				theFloor.extend(bg.getWidth());

				break;
			}
		}
	}

	private void setUpBox2D(){

		// Inicializa Box2D e cria novo "world"
		Box2D.init();
		world = new World(new Vector2(0, GRAVITY), true);
		debugRenderer = new Box2DDebugRenderer();

		theFloor = new Floor(world, 10000000000000000f, FLOOR_HEIGHT, CHARSCALE);

	}

	/*
	private void addSprites(String path) {
		TextureAtlas textureAtlas = new TextureAtlas(path);
		Array<AtlasRegion> regions = textureAtlas.getRegions();

		for (AtlasRegion region : regions) {
			Sprite sprite = textureAtlas.createSprite(region.name);

			sprites.put(region.name, sprite);
		}
	}
	*/



	@Override
	public void render () {

		cameraFollow(PC.getPlayer());
		camera.update();

		this.loopWorld();
		this.loopUpdate();
		this.loopRender();
		this.loopCleanUp();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		//camera.viewportWidth=width;
		//camera.viewportHeight=height;
		viewport.update(width, height, true);

		batch.setProjectionMatrix(camera.combined);
		camera.update();
	}


	@Override
	public void dispose() {

		for (StaticGameObject obj : background) {
			obj.dispose();
		}
		for (GameObject obj : characters) {
			obj.dispose();
		}

		world.dispose();
		batch.dispose();
		debugRenderer.dispose();
	}


	private void loopUpdate(){

		updateBackground(PC.getPlayer());

		for (GameObject obj : characters) {
			obj.update();
		}

	}

	private void loopWorld() {
		float delta = Gdx.graphics.getDeltaTime();

		accumulator += Math.min(delta, 0.25f);

		if (accumulator >= STEP_TIME) {
			accumulator -= STEP_TIME;

			world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		}
	}

	private void loopRender(){

		Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (GameObject obj : background){
			obj.draw(batch);
		}
		for (GameObject obj : characters){
			obj.draw(batch);
		}
		batch.end();

		batch.setProjectionMatrix(camera.projection);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		for (GameObject obj : characters){
			obj.drawUI(shapeRenderer, camera);
		}
		shapeRenderer.end();

		debugRenderer.render(world, camera.combined);

	}

	private void loopCleanUp(){

		Iterator<Character> i = characters.iterator();
		while (i.hasNext()) {
			Character character = i.next();

			if (character.flagDelete){
				character.dispose();
				i.remove();
			}
		}
	}



	private void spawnEnemy(){

		Vector3 position = new Vector3(PC.getPlayer().getX() + SPAWN_OFFSET, FLOOR_HEIGHT, 0);
		AddCharacter(new Enemy(world, position, CHARSCALE, BODYSCALE));

	}

	private void AddCharacter(Character c){
		c.start();
		characters.add(c);
	}
}
