package com.rainer.veganrevenge;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by rainervieira on 06/08/2016.
 */
public class Powerup extends AnimatedGameObject {

    Body physicsBody;

    protected float bodyScale = 0;
    protected float bodyRadius = 0;

    protected int bonusHealth = 0;
    protected int bonusAttack = 0;
    protected int bonusGold = 0;

    private World worldReference;

    public Powerup(World world, Vector3 pos, float spriteScale, float bodyScale, int type){
        super(pos, spriteScale);

        switch (type){

            case 0:
                bonusHealth = 20;
                break;

            case 1:
                bonusAttack = 1;
                break;

            case 2:
                bonusGold = 1;
                break;

        }

        this.tag = "POWERUP";
        this.bodyScale = bodyScale;
        this.worldReference = world;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(getX(), getY());

        physicsBody = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();

        this.bodyRadius = getHeight()/2 * bodyScale;
        shape.setRadius(this.bodyRadius);

        FixtureDef fixture = new FixtureDef();
        fixture.friction = 0;
        fixture.restitution = 0;
        fixture.density = 0.33f;
        fixture.shape = shape;

        fixture.filter.categoryBits = CollisionCategory.CHARACTER.get();
        fixture.filter.maskBits = CollisionCategory.FLOOR.get();

        physicsBody.createFixture(fixture);

        shape.dispose();

        physicsBody.setUserData(this);
    }
}
