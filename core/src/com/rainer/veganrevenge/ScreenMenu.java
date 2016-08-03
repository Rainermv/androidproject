package com.rainer.veganrevenge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by rainervieira on 02/08/2016.
 */
public class ScreenMenu implements Screen {

    GameMain game;

    final float VIEWPORT_WIDTH = 800;
    final float VIEWPORT_HEIGHT = 600;

    //final float BUTTON_WIDTH = 350;
    //final float BUTTON_HEIGHT = 165;

    private OrthographicCamera camera;
    //private ExtendViewport viewport;
    private SpriteBatch batch;

    private SpriteBatch spriteBatch;
    private Stage stage;

    private TextureAtlas buttonAtlas;
    private BitmapFont font;

    private Skin buttonSkin;
    private TextButton buttonStartGame;

    public ScreenMenu(GameMain game){
        this.game = game;
    }

    @Override
    public void show() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT); //** w/h ratio = 1.66 **//
        //viewport = new ExtendViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);

        stage = new Stage(new ExtendViewport(VIEWPORT_WIDTH,VIEWPORT_HEIGHT ));        //** window is stage **//
        stage.clear();
        Gdx.input.setInputProcessor(stage); //** stage is responsive **//

        batch = new SpriteBatch();

        spriteBatch = new SpriteBatch();

        Image background = new Image(new Texture(Gdx.files.internal("sprites/bg.png")));
        Image window = new Image(new Texture(Gdx.files.internal("sprites/Window.png")));

        window.setHeight(window.getHeight()* 0.7f); //** Button Height **//
        window.setWidth(window.getWidth()* 0.7f); //** Button Width **//
        window.setPosition(VIEWPORT_WIDTH/2 - window.getWidth()/2 , VIEWPORT_HEIGHT/2f - (window.getHeight()/2)); //** Button location **//

        buttonAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/buttons.txt"));

        buttonSkin = new Skin();
        buttonSkin.addRegions(buttonAtlas); //** skins for on and off **//

        font = new BitmapFont();
        font.getData().setScale(2, 2);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); //** Button properties **//
        style.up = buttonSkin.getDrawable("ButtonBig (4)");
        style.down = buttonSkin.getDrawable("ButtonBig (2)");
        style.font = font;

        float height = buttonAtlas.findRegion("ButtonBig (4)").getTexture().getHeight()/2;
        float width = buttonAtlas.findRegion("ButtonBig (4)").getTexture().getWidth()/2;

        buttonStartGame = new TextButton("NEW GAME", style); //** Button text and style **//
        buttonStartGame.setPosition(VIEWPORT_WIDTH/2 - width/2 , VIEWPORT_HEIGHT/2f - height/2); //** Button location **//
        buttonStartGame.setHeight(height); //** Button Height **//
        buttonStartGame.setWidth(width); //** Button Width **//

        buttonStartGame.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.startGame();
            }
        });

        stage.addActor(background);
        stage.addActor(window);
        stage.addActor(buttonStartGame);



    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        //batch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();

        stage.draw();

        spriteBatch.end();


    }

    @Override
    public void resize(int width, int height) {

        stage.getViewport().update(width, height, true);

        batch.setProjectionMatrix(camera.combined);
        camera.update();

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

        batch.dispose();
        buttonSkin.dispose();
        buttonAtlas.dispose();
        font.dispose();
        stage.dispose();

    }
}
