package com.rainer.veganrevenge;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.HashMap;

public class VeganGame extends ApplicationAdapter {

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private ExtendViewport viewport;

	final float VIEWPORT_WIDTH = 40;//80;//40;
	final float VIEWPORT_HEIGHT = 25;//50;//25;

	final int BACKGROUND_IMAGES = 4;

	ListenerClass contactListener;

	private Vector3 touch_position;
	//Texture img;

	final HashMap<String, Texture> static_textures = new HashMap<String, Texture>();

	private Array<StaticGameObject> background = new Array<StaticGameObject>();
	private Array<GameObject> foreground = new Array<GameObject>();

	private Floor theFloor;
	final float floor_height = 10f;

	World world;
	Box2DDebugRenderer debugRenderer;
	static final float STEP_TIME = 1f / 60f;
	static final int VELOCITY_ITERATIONS = 6;
	static final int POSITION_ITERATIONS = 2;
	float accumulator = 0;

	Player thePlayer;

	//StaticGameObject bg1 = null;
	//StaticGameObject bg2 = null;
	
	@Override
	public void create () {


		setUpBox2D();

		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		batch = new SpriteBatch();
		touch_position = new Vector3();

		camera = new OrthographicCamera();
		//camera.setToOrtho(false, 800, 480);

		//viewport = new ExtendViewport(800, 600, camera);
		viewport = new ExtendViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);

		//addSprites("spritesheets/knight.txt"); // add background
		static_textures.put("background", new Texture("sprites/bg.png"));
		//addSprites("sprites/bg.png");
		//foreground.add(new GameObject());

		thePlayer = new Player(world, new Vector3(5,floor_height,0), 0.8f);

		buildBackground();

		foreground.add(thePlayer);

		world.setContactListener(contactListener);

		for (GameObject obj : background) {
			obj.start();
		}
		for (GameObject obj : foreground) {
			obj.start();
		}
	}

	private void buildBackground(){

		for (int i = 0; i < BACKGROUND_IMAGES; i++){
			background.add(new StaticGameObject(new Vector3(0,0,0),static_textures.get("background"), 0.05f));
		}

		float terrain_width = background.get(0).getWidth();
		for (int i = 0; i < BACKGROUND_IMAGES; i++){

			background.get(i).updatePosition(new Vector3(terrain_width * (i -1),0,0));

		}
	}

	private void cameraFollow(GameObject target){

		camera.position.x = target.getX();

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
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();

		theFloor = new Floor(world, 10000000000000000f, floor_height);

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

		cameraFollow(thePlayer);
		camera.update();

		if(Gdx.input.isTouched()) {
			//Vector3 touchPos = new Vector3();
			this.touch_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(this.touch_position);

			for (GameObject obj : foreground) {
				obj.onScreenTouch(touch_position);
			}
		}

		stepWorld();

		Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		updateBackground(thePlayer);
		//
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (GameObject obj : background){
			obj.draw(batch);
		}
		for (GameObject obj : foreground){
			obj.draw(batch);
		}
		batch.end();

		debugRenderer.render(world, camera.combined);

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
		for (GameObject obj : foreground) {
			obj.dispose();
		}

		world.dispose();
		batch.dispose();
		debugRenderer.dispose();
	}

	private void stepWorld() {
		float delta = Gdx.graphics.getDeltaTime();

		accumulator += Math.min(delta, 0.25f);

		if (accumulator >= STEP_TIME) {
			accumulator -= STEP_TIME;

			world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		}
	}
}
