package com.rainer.veganrevenge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Rainer on 09/06/2016.
 */
public class Character extends AnimatedGameObject{

    Body physicsBody;

    boolean floorContact = true;

    public Character (World world, Vector3 position, float spriteScale, float bodyScale){

        super(position, spriteScale);

        this.tag = "PLAYER";

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(getX(), getY());

        physicsBody = world.createBody(bodyDef);

        //PolygonShape bodyShape = new PolygonShape();
        //bodyShape.setAsBox(getWidth()/2 * body_scale.x,getHeight()/2 * body_scale.y);
        CircleShape shape = new CircleShape();
        shape.setRadius(getHeight()/2 * bodyScale);

        FixtureDef fixture = new FixtureDef();
        fixture.friction = 0;
        fixture.restitution = 0;
        fixture.density = 0.33f;
        fixture.shape = shape;

        physicsBody.createFixture(fixture);

        //physicsBody.getFixtureList().get(0).getShape().

        shape.dispose();

        physicsBody.setUserData(this);

    }

    @Override
    public void draw(SpriteBatch batch) {

        Vector2 pos = physicsBody.getPosition();
        this.updatePosition(new Vector3(pos, 0));
        super.draw(batch);

    }

    @Override
    public void onScreenTouch(Vector3 touch_position) {


    }

    public void setContact(boolean contact){
        this.floorContact = contact;

        Logger.log("floor contact: " + this.floorContact);
    }

}
