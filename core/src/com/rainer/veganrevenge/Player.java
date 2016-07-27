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

    public Player(World world, Vector3 position, float spriteScale, float bodyScale) {
        super(world, position, spriteScale, bodyScale);
        this.tag = "PLAYER";

        this.health = 100;
        this.damage = 10;

        this.jumpForce = 400;
        this.moveSpeed = 5;
        this.sensorRange = 5;

        addSensor(world, this.sensorRange, "FAR");
        addSensor(world, this.getHeight() /2, "NEAR");

        this.healthBar = new Bar(new Vector3(0.05f,0.95f,0f), Color.GREEN, Color.RED, 0.6f, 0.05f, this.health,this.health);
    }

    @Override
    public void start(){

        //physicsBody.setLinearVelocity(this.speed,0);
        actionStartMoving(1);

    }

    @Override
    public void drawUI(ShapeRenderer renderer, Camera cam) {

        //healthBar.updatePosition( cam.position);
        super.drawUI(renderer, cam);
    }

    @Override
    public void onSensorEnter(String sensorTag, Character other){
        super.onSensorEnter(sensorTag, other);

        if (sensorTag == "NEAR" && other.tag == "ENEMY"){

            this.actionDamage(other);
            this.healthBar.updateValue((float)this.health);
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
