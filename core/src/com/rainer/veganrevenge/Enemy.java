package com.rainer.veganrevenge;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by rainervieira on 25/07/2016.
 */
public class Enemy extends Character {

    final float BODY_RADIUS = 1.5f;

    final float SENSOR_RADIUS = 5f;
    final float TOUCH_RADIUS = 2.3f;

    final int HEALTH = 5;
    final int DAMAGE = 5;

    final float JUMP_FORCE = 500;
    final float MOVE_SPEED = 2;

    public Enemy(World world, Vector3 position, ScreenGameplay screenGameplay) {
        super(position, screenGameplay);
        this.tag = "ENEMY";

        this.health = HEALTH;
        this.damage = DAMAGE;

        this.jumpForce = JUMP_FORCE;
        this.moveSpeed = MOVE_SPEED;
        //this.sensorRange = 8;

        this.createBody(BODY_RADIUS, world);

        addSensor(world, SENSOR_RADIUS, "SENSOR");
        addSensor(world, TOUCH_RADIUS, "ENEMY_TOUCH");

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

        if (this.health <= 0 && !flagDead){
            //this.flagDelete = true;
            actionDie();

            int POWERUP = MathUtils.random(0, 1);

            screenGameplayReference.AddObject(new Powerup(worldReference, new Vector3(this.x,this.y,0), POWERUP));
            this.disposeSensors();
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

    @Override
    public void dispose() {




        super.dispose();


    }


}
