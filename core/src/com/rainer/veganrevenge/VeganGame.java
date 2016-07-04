package com.rainer.veganrevenge;

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
	
	@Override
	public void create () {

		batch = new SpriteBatch();
		touch_position = new Vector3();

		camera = new OrthographicCamera();
		//camera.setToOrtho(false, 800, 480);

		viewport = new ExtendViewport(800, 600, camera);

		addSprites("spritesheets/knight.txt");
		//gameObjects.add(new GameObject());
		gameObjects.add(new Character());
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

		batch.dispose();
	}
}
