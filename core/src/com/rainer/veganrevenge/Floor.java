package com.rainer.veganrevenge;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by rainervieira on 21/07/2016.
 */
public class Floor extends GameObject {

    Body physicsBody;
    EdgeShape shape;
    float height;

    private float width = 0;

    public Floor(World world, float width, float height){

        this.width = width;
        this.height = height;

        BodyDef floor = new BodyDef();
        floor.type = BodyDef.BodyType.StaticBody;
        floor.position.set(0,0);

        physicsBody = world.createBody(floor);

        shape = new EdgeShape();
        shape.set(0,this.height,this.width,this.height);

        FixtureDef fixture = new FixtureDef();
        fixture.friction = 0;
        fixture.restitution = 0;
        fixture.density = 1;
        fixture.shape = shape;

        physicsBody.createFixture(fixture);
        physicsBody.setUserData(this);

    }

    public void extend(float value){

        width += value;
        //((EdgeShape)(physicsBody.getFixtureList().get(0).getShape())).set(0,this.height,this.width,this.height);
        //shape.set(0,this.height,this.width,this.height);

        //Logger.log("floor width: " + width);
    }

}