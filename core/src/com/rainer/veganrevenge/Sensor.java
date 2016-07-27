package com.rainer.veganrevenge;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by rainervieira on 25/07/2016.
 */
public class Sensor extends GameObject {

    Body sensorBody;
    Character parent;

    float range = 0;

    float bodyScale = 0;

    private World worldReference;

    public Sensor(Vector3 position, float scale, float range, float bodyScale, World world, Character parent, String tag) {
        super(position, scale);

        //this.tag = "SENSOR";
        this.tag = tag;
        this.parent = parent;
        this.range = range;

        this.worldReference = world;

        setUpSensor(world, bodyScale, range);

    }

    private void setUpSensor( World world, float bodyScale,  float range){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(getX(), getY());

        sensorBody = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();;
        shape.setRadius(range * bodyScale);

        FixtureDef fixture = new FixtureDef();

        fixture.isSensor = true;
        //fixture.filter.categoryBits = 3; // cat 3 = sensores
        //fixture.filter.maskBits  = 2; // colide apenas com personagens (cat 2)
        fixture.filter.categoryBits = CollisionCategory.SENSOR.get();
        fixture.filter.maskBits = CollisionCategory.CHARACTER.get();

        fixture.shape = shape;

        sensorBody.createFixture(fixture);
        sensorBody.setUserData(this);

        shape.dispose();

    }

    public void updateSensor(Vector3 pos){
        super.updatePosition(pos);
        if (sensorBody != null) {
            sensorBody.setTransform(pos.x, pos.y, 0);
        }
    }

    public void enter(Character other){
        parent.onSensorEnter(this.tag, other);
    }

    public void exit(Character other){
        parent.onSensorExit(this.tag, other);
    }

    @Override
    public void dispose() {
        super.dispose();

        if (this.sensorBody != null) {
            worldReference.destroyBody(this.sensorBody);
            this.sensorBody = null;
        }
    }
}
