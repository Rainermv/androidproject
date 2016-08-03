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
import com.badlogic.gdx.Game;

import java.util.HashMap;
import java.util.Iterator;


public class GameMain extends Game{

	ScreenGameplay screenGameplay;
	ScreenMenu screenMenu;


	
	@Override
	public void create () {

		screenMenu = new ScreenMenu(this);

		setScreen(screenMenu);
	}

	public void startGame(){

		screenGameplay = new ScreenGameplay(this);
		setScreen(screenGameplay);

	}

	@Override
	public void render () {

		super.render();

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		/*
		viewport.update(width, height, true);

		batch.setProjectionMatrix(camera.combined);
		camera.update();
		*/
	}
	/*
	private void loopUpdate(){

		parallaxBackground.update(PC.getPlayer().getX());

		for (GameObject obj : characters) {
			obj.update();
		}

	}

	private void loopFixedUpdate() {

		float delta = Gdx.graphics.getDeltaTime();
		accumulator += Math.min(delta, 0.25f);

		if (accumulator >= STEP_TIME) {
			accumulator -= STEP_TIME;

			world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

			for (GameObject obj : characters) {
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

		if (DEBUG_DRAW)
			debugRenderer.render(world, camera.combined);

	}

	private void loopCleanUp(){

		Iterator<Character> i = characters.iterator();
		while (i.hasNext()) {
			Character character = i.next();

			if (character.flagDelete || character.getX() < camera.position.x - VIEWPORT_WIDTH){
				if (character.tag == "PLAYER") continue;
				character.dispose();
				i.remove();
				continue;
			}

			if (character.flagDead){
				character.disposeSensors();
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
	*/
	@Override
	public void dispose() {
		super.dispose();
		/*
		for (GameObject obj : characters) {
			obj.dispose();
		}

		parallaxBackground.dispose();
		world.dispose();
		batch.dispose();
		debugRenderer.dispose();
		*/
	}
}
