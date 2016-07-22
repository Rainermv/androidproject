package com.rainer.veganrevenge;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by rainervieira on 22/07/2016.
 */
public class InputHandler implements InputProcessor {

    private VeganGame game = null;
    private Camera cam = null;

    private Vector3 touch_position = new Vector3();

    private static InputHandler instance;
    private InputHandler(){

    }

    public static InputHandler getInstance(){

        if (instance == null){
            instance = new InputHandler();
        }

        return instance;
    }

    public void set(VeganGame game, Camera cam){
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

        Logger.log("touched");

        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        cam.unproject(touch_position.set(screenX, screenY, 0));

        for (GameObject obj : game.foreground) {
            obj.onScreenTouch(touch_position);
        }

        //dragging = true;
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
