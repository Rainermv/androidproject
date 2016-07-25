package com.rainer.veganrevenge;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by rainervieira on 25/07/2016.
 */
public class Enemy extends Character {

    public Enemy(World world, Vector3 position, float spriteScale, float bodyScale) {
        super(world, position, spriteScale, bodyScale);
        this.tag = "ENEMY";

        this.health = 10;
        this.damage = 15;

        this.jumpForce = 400;
        this.moveSpeed = 2;
        this.sensorRange = 8;

        addSensor(world, this.sensorRange, "SENSOR");
        addSensor(world, this.getHeight() * 1.2f, "HIT");
    }

    @Override
    public void start(){

        startMoving(-1);
        //physicsBody.setLinearVelocity(-this.speed,0);

    }

    @Override
    public void onSensorEnter(String sensorTag, Character other){
        super.onSensorEnter(sensorTag, other);

        if (other.tag == "PLAYER"){
            //Logger.log("PLAYER DETECTED");

            if (other.getY() > y && this.floorContact){
                jump();
            }
        }
    }

    @Override
    public void onSensorExit(String sensorTag, Character other){
        super.onSensorExit(sensorTag, other);

        if (other.tag == "PLAYER"){
            //Logger.log("PLAYER UNDETECTED");
        }
    }
}
