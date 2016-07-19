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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VeganGame extends ApplicationAdapter {

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private ExtendViewport viewport;

	private Vector3 touch_position;
	//Texture img;

	final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

	private Array<GameObject> gameObjects = new Array<GameObject>();

	World world;
	Box2DDebugRenderer debugRenderer;
	static final float STEP_TIME = 1f / 60f;
	static final int VELOCITY_ITERATIONS = 6;
	static final int POSITION_ITERATIONS = 2;
	float accumulator = 0;

	Player thePlayer;
	
	@Override
	public void create () {

		setUpBox2D();

		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		batch = new SpriteBatch();
		touch_position = new Vector3();

		camera = new OrthographicCamera();
		//camera.setToOrtho(false, 800, 480);

		//viewport = new ExtendViewport(800, 600, camera);
		viewport = new ExtendViewport(40, 25, camera);

		//addSprites("spritesheets/knight.txt"); // add background
		sprites.put("background", new Sprite(new Texture("sprites/bg.png")));
		//addSprites("sprites/bg.png");
		//gameObjects.add(new GameObject());

		thePlayer = new Player(world, new Vector3(5,10,0), 0.8f);

		gameObjects.add(new StaticGameObject(new Vector3(0,0,0),sprites.get("background"), 0.05f));
		gameObjects.add(thePlayer);


	}

	private void cameraFollow(GameObject target){

		camera.position.x = target.getX();
		//camera.translate(0.5f,0);
		//Gdx.app.log("cam", "x: "+camera.position.x);

	}

	private void setUpBox2D(){

		// Inicializa Box2D e cria novo "world"
		Box2D.init();
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();

		BodyDef floor = new BodyDef();
		floor.type = BodyDef.BodyType.StaticBody;
		floor.position.set(0, 0);

		Body floorBody = world.createBody(floor);

		EdgeShape floorShape = new EdgeShape();
		floorShape.set(0,5,1000,5);

		FixtureDef fixture = new FixtureDef();
		fixture.friction = 0;
		fixture.restitution = 0;
		fixture.density = 1;
		fixture.shape = floorShape;

		floorBody.createFixture(fixture);

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

			for (GameObject obj : gameObjects) {
				obj.onScreenTouch(touch_position);
			}
		}

		stepWorld();

		Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		//
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (GameObject obj : gameObjects){
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

		for (GameObject obj : gameObjects) {
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
