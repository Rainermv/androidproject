package com.rainer.veganrevenge;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by rainervieira on 18/07/2016.
 */
public class Player extends Character  {

    final float BODY_RADIUS = 0.8f;

    final float NEAR_SENSOR_RADIUS = 1f;
    final float FAR_SENSOR_RADIUS = 4f;

    final float JUMP_FORCE = 450;
    final float MOVE_SPEED = 5;

    final int DAMAGE = 5;

    final int HEALTH = 100;

    public Player(World world, Vector3 position, ScreenGameplay screenGameplay) {
        super(position, screenGameplay);

        this.tag = "PLAYER";

        this.health = HEALTH;
        this.max_health = HEALTH;
        this.damage = DAMAGE;

        this.jumpForce = JUMP_FORCE;
        this.moveSpeed = MOVE_SPEED;
        //this.sensorRange = 5;

        this.createBody(BODY_RADIUS, world);

        addSensor(world, FAR_SENSOR_RADIUS, "FAR");
        addSensor(world, NEAR_SENSOR_RADIUS, "NEAR");

        setAnimationKeys("knight_attack", "knight_run", "knight_dead", "knight_jump", "knight_jumpAttack");
    }

    @Override
    public void addHealth(int damage) {
        super.addHealth(damage);
        //this.healthBar.updateValue((float)this.getHealth());
    }

    @Override
    public void start(){

        //physicsBody.setLinearVelocity(this.speed,0);
        actionStartMoving(1);

    }

    @Override
    public void update(){
        super.update();

        if (this.health <= 0){
            //this.flagDelete = true;
            actionDie();
        }

        if (animState == "DEAD" && isAnimationFinished()){

        }
    }

    @Override
    public void drawUI(ShapeRenderer renderer) {

        //healthBar.updatePosition( cam.position);
        super.drawUI(renderer);
    }

    @Override
    public void onSensorEnter(String sensorTag, Character other){
        super.onSensorEnter(sensorTag, other);

        if (sensorTag == "NEAR" && other.tag == "ENEMY"){

            //this.actionAttack(other);
            //this.healthBar.updateValue((float)this.health);
        }

        if (sensorTag == "FAR" && other.tag == "ENEMY"){
        }
    }

    @Override
    public void onSensorExit(String sensorTag, Character other){
        super.onSensorExit(sensorTag, other);

        if (sensorTag == "NEAR" && other.tag == "ENEMY"){

        }

        if (sensorTag == "FAR" && other.tag == "ENEMY"){
        }


    }
}
