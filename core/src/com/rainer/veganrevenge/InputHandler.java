package com.rainer.veganrevenge;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

import java.util.ArrayList;

/**
 * Created by rainervieira on 22/07/2016.
 */
public class InputHandler implements InputProcessor {

    private Game game = null;
    private Camera cam = null;

    private PlayerController PC = PlayerController.getInstance();

    private ArrayList<GameObject> touchedObjects = new ArrayList<GameObject>();

    private Vector3 touch_position = new Vector3();

    float boxSize = 0.5f;

    private static InputHandler instance;
    private InputHandler(){

    }

    public static InputHandler getInstance(){

        if (instance == null){
            instance = new InputHandler();
        }

        return instance;
    }

    public void set(Game game, Camera cam){
        this.game = game;
        this.cam = cam;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (button != Input.Buttons.LEFT || pointer > 0) return false;

        cam.unproject(touch_position.set(screenX, screenY, 0));

        float x1 = touch_position.x  -boxSize;
        float y1 = touch_position.y -boxSize;

        float x2 = touch_position.x  +boxSize;
        float y2 = touch_position.y   +boxSize;

        touchedObjects.clear();

        QueryCallback queryCallback = new QueryCallback() {

            @Override
            public boolean reportFixture(Fixture fixture) {

                if (fixture == null || fixture.getBody() == null)
                    return true;

                if (fixture.getBody().getUserData() instanceof GameObject){
                    GameObject obj = (GameObject)fixture.getBody().getUserData();
                    touchedObjects.add(obj);
                }

                return true;
            }

        };

        game.world.QueryAABB(queryCallback, x1, y1, x2, y2);

        PC.touch(touchedObjects);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
