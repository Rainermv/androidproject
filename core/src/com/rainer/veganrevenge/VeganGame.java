package com.rainer.veganrevenge;

import com.badlogic.gdx.math.Vector2;
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
	
	@Override
	public void create () {

		// Inicializa Box2D e cria novo "world"
		Box2D.init();
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();

		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		batch = new SpriteBatch();
		touch_position = new Vector3();

		camera = new OrthographicCamera();
		//camera.setToOrtho(false, 800, 480);

		//viewport = new ExtendViewport(800, 600, camera);
		viewport = new ExtendViewport(80, 45, camera);

		addSprites("spritesheets/knight.txt");
		//gameObjects.add(new GameObject());
		gameObjects.add(new Character(world));
		//gameObjects.add(new Character(sprites.get("Attack (1)")));

		//GameObject obj2 = new Character(sprites.get("Attack (1)"));
		//gameObjects.add(obj2);
		//obj2.setPosition(100,0);
		//img = new Texture("badlogic.jpg");
	}

	private void addSprites(String path) {
		TextureAtlas textureAtlas = new TextureAtlas(path);
		Array<AtlasRegion> regions = textureAtlas.getRegions();

		for (AtlasRegion region : regions) {
			Sprite sprite = textureAtlas.createSprite(region.name);

			sprites.put(region.name, sprite);
		}
	}

	@Override
	public void render () {

		stepWorld();

		if(Gdx.input.isTouched()) {
			//Vector3 touchPos = new Vector3();
			this.touch_position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(this.touch_position);

			for (GameObject obj : gameObjects) {
				obj.onScreenTouch(touch_position);
			}
		}

		Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
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
