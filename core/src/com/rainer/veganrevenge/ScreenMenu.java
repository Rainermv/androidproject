package com.rainer.veganrevenge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by rainervieira on 02/08/2016.
 */
public class ScreenMenu implements Screen {

    GameMain game;

    SpriteBatch spriteBatch;
    private Texture splash;

    public ScreenMenu(GameMain game){
        this.game = game;
    }

    @Override
    public void show() {

        spriteBatch = new SpriteBatch();
        splash = new Texture(Gdx.files.internal("sprites/bg.png"));

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(splash, 0, 0);
        spriteBatch.end();

        if(Gdx.input.justTouched())
            game.startGame();

    }

    @Override
    public void resize(int width, int height) {

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

    }
}
