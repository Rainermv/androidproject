package com.rainer.veganrevenge;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

/**
 * Created by rainervieira on 22/07/2016.
 */
public class InputHandler implements InputProcessor {

    private VeganGame game = null;
    private Camera cam = null;
    private RayCastHandler rayCastHandler = RayCastHandler.getInstance();

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

        if (button != Input.Buttons.LEFT || pointer > 0) return false;

        //touch_position.set(screenX, screenY);

        //Vector3 touchProjected = new Vector3(touch_position, 0);
        cam.unproject(touch_position.set(screenX, screenY, 0));


        float x1 = touch_position.x  -boxSize;
        float y1 = touch_position.y -boxSize;

        float x2 = touch_position.x  +boxSize;
        float y2 = touch_position.y   +boxSize;

        Logger.log("Touching: " + x1 + "," + y1 + "  " + x2 + "," + y2 );


        QueryCallback queryCallback = new QueryCallback() {

            @Override
            public boolean reportFixture(Fixture fixture) {

                //fixture.getBody().setUserData();

                if (fixture.getBody().getUserData() instanceof GameObject){
                    //Logger.log("derp");
                    GameObject obj = (GameObject)fixture.getBody().getUserData();

                    Logger.log("touched: " + obj.tag);

                }

                return true;
            }

        };

        game.world.QueryAABB(queryCallback, x1, y1, x2, y2);

        /*

        for (GameObject obj : game.characters) {
            obj.onScreenTouch(tpv3);
        }
        */

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
