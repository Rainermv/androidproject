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

        this.health = 50;
        this.damage = 15;

        this.jumpForce = 400;
        this.moveSpeed = 2;
        this.sensorRange = 8;

        addSensor(world, this.sensorRange, "SENSOR");
        addSensor(world, this.getHeight() * 1.2f, "ENEMY_TOUCH");
    }

    @Override
    public void start(){
        super.start();

        actionStartMoving(-1);
    }

    @Override
    public void update(){
        super.update();

        if (this.health <= 0){
            this.flagDelete = true;
        }
    }

    @Override
    public void onSensorEnter(String sensorTag, Character other){
        super.onSensorEnter(sensorTag, other);

        if (other.tag == "PLAYER"){
            //Logger.log("PLAYER DETECTED");

            if (other.getY() > y && this.floorContact){
                actionJump();
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
