package com.rainer.veganrevenge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Rainer on 09/06/2016.
 */
public class Character extends AnimatedGameObject {

    Body physicsBody;

    public Character (World world){

        super();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX(), getY());

        physicsBody = world.createBody(bodyDef);

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(getH(),getW());

        //FixtureDef bodyFixture = new FixtureDef();
        //bodyFixture.shape = bodyShape;
        //bodyFixture.density = 1;

        physicsBody.createFixture(bodyShape, 1);
        //physicsBodies.createBody("banana", world, bodyDef, SCALE, SCALE);
        //body.setTransform(10, 10, 0);
    }

    @Override
    public void draw(SpriteBatch batch) {
        Vector2 pos = physicsBody.getPosition();
        this.updatePosition(new Vector3(pos, 0));
        super.draw(batch);

    }

    @Override
    public void onScreenTouch(Vector3 touch_position) {

        super.onScreenTouch(touch_position);
        physicsBody.applyForceToCenter(0f,10000f,true);
        //this.updatePosition(touch_position);
        //this.rect.x = touch_position.x - this.rect.width /2;

    }
}
