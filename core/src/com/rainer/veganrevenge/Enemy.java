package com.rainer.veganrevenge;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by rainervieira on 25/07/2016.
 */
public class Enemy extends Character {

    public Enemy(World world, Vector3 position, float spriteScale, float bodyScale) {
        super(world, position, spriteScale * 1.5f, bodyScale);
        this.tag = "ENEMY";

        this.health = 30;
        this.damage = 100;

        this.jumpForce = 800;
        this.moveSpeed = 2;
        this.sensorRange = 8;

        addSensor(world, this.sensorRange, "SENSOR");
        addSensor(world, this.bodyRadius * 1.8f, "ENEMY_TOUCH");

        setAnimationKeys("robot_attack", "robot_run", "robot_dead", "robot_jump", "robot_jumpAttack");
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
            //this.flagDelete = true;
            actionDie();
        }
    }

    @Override
    public void onSensorEnter(String sensorTag, Character other){
        super.onSensorEnter(sensorTag, other);

        if (sensorTag == "SENSOR" && other.tag == "PLAYER"){
            if (other.getY() > y && this.floorContact){
                actionJump();
            }
        }

        if (sensorTag == "ENEMY_TOUCH" && other.tag == "PLAYER") {
            actionAttack(other);
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
