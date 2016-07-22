package com.rainer.veganrevenge;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by rainervieira on 18/07/2016.
 */
public class Player extends Character  {

    private float speed = 25;//5;
    private float jump_force = 8000;

    public boolean floor_contact = true;

    public Player(World world, Vector3 position, float body_scale) {
        super(world, position, body_scale);
    }

    @Override
    public void onScreenTouch(Vector3 touch_position) {

        super.onScreenTouch(touch_position);
        physicsBody.applyForceToCenter(0f,this.jump_force,true);

        physicsBody.setLinearVelocity(this.speed,0);
        //this.updatePosition(touch_position);
        //this.rect.x = touch_position.x - this.rect.width /2;

    }

    @Override
    public void start(){

        physicsBody.setLinearVelocity(this.speed,0);

    }
}
