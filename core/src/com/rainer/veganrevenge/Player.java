package com.rainer.veganrevenge;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by rainervieira on 18/07/2016.
 */
public class Player extends Character  {

    private float speed = 5;//5;
    private float jump_force = 400;

    public Player(World world, Vector3 position, float spriteScale, float bodyScale) {
        super(world, position, spriteScale, bodyScale);
        this.tag = "PLAYER";

        Logger.log("pos: " + position);
    }

    @Override
    public void onScreenTouch(Vector3 touch_position) {

        super.onScreenTouch(touch_position);

        if (this.floorContact == true) {
            physicsBody.applyForceToCenter(0f, this.jump_force, true);

            physicsBody.setLinearVelocity(this.speed, 0);
        }

        //this.updatePosition(touch_position);
        //this.rect.x = touch_position.x - this.rect.width /2;

    }

    @Override
    public void start(){

        physicsBody.setLinearVelocity(this.speed,0);

    }
}
