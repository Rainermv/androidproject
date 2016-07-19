package com.rainer.veganrevenge;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by rainervieira on 18/07/2016.
 */
public class Player extends Character {
    public Player(World world, Vector3 position, float body_scale) {
        super(world, position, body_scale);
    }

    @Override
    public void onScreenTouch(Vector3 touch_position) {

        super.onScreenTouch(touch_position);
        physicsBody.applyForceToCenter(0f,100f,true);

        physicsBody.setLinearVelocity(5,0);
        //this.updatePosition(touch_position);
        //this.rect.x = touch_position.x - this.rect.width /2;

    }
}
