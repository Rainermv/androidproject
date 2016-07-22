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

    BitmapFont debug_font = new BitmapFont(); //or use alex answer to use custom font

    Body physicsBody;


    public Character (World world, Vector3 position, float body_scale){

        super(position);

        debug_font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        debug_font.getData().setScale(0.1f);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(getX(), getY());

        physicsBody = world.createBody(bodyDef);

        //PolygonShape bodyShape = new PolygonShape();
        //bodyShape.setAsBox(getWidth()/2 * body_scale.x,getHeight()/2 * body_scale.y);
        CircleShape shape = new CircleShape();
        shape.setRadius(getHeight()/2 * body_scale);

        FixtureDef fixture = new FixtureDef();
        fixture.friction = 0;
        fixture.restitution = 0;
        fixture.density = 1;
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

        super.onScreenTouch(touch_position);
        physicsBody.applyForceToCenter(0f,100f,true);

        physicsBody.setLinearVelocity(5,0);
        //this.updatePosition(touch_position);
        //this.rect.x = touch_position.x - this.rect.width /2;

    }

}
