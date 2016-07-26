package com.rainer.veganrevenge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

/**
 * Created by Rainer on 09/06/2016.
 */
public class Character extends AnimatedGameObject  {

    Body physicsBody;

    Bar healthBar = null;

    ArrayList<Sensor> sensorArray = new ArrayList<Sensor>();

    boolean floorContact = true;

    float bodyScale = 0;

    protected int health = 0;
    protected int damage = 0;

    protected float moveSpeed = 0;
    protected float jumpForce = 0;
    protected float sensorRange = 0;

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public Character (World world, Vector3 position, float spriteScale, float bodyScale){

        super(position, spriteScale);

        this.tag = "PLAYER";
        this.bodyScale = bodyScale;

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

        //fixture.filter.categoryBits = 2; // cat 2 = personagens
        //fixture.filter.maskBits = 1; // colide apenas com terreno (categoria 1)

        fixture.filter.categoryBits = CollisionCategory.CHARACTER.get();
        fixture.filter.maskBits = CollisionCategory.FLOOR.get();

        physicsBody.createFixture(fixture);

        //physicsBody.getFixtureList().get(0).getShape().

        shape.dispose();

        physicsBody.setUserData(this);

    }

    protected void addSensor(World world, float range, String tag){

        sensorArray.add(new Sensor(new Vector3(this.x, this.y, 0),this.SCALE, range, this.bodyScale, world, this, tag));
    }

    @Override
    public void draw(SpriteBatch batch) {

        setFlipped (physicsBody.getLinearVelocity().x < 0);

        Vector2 pos = physicsBody.getPosition();

        this.updatePosition(new Vector3(pos, 0));
        super.draw(batch);

        for (Sensor sensor : sensorArray){
            sensor.updateSensor(new Vector3(pos, 0));
            //sensor.draw(batch);
        }

    }
    @Override
    public void drawUI(ShapeRenderer renderer, Camera cam) {
        super.drawUI(renderer, cam);

        if (healthBar != null) {
            healthBar.drawUI(renderer, cam);
        }
    }


    @Override
    public void onScreenTouch(Vector3 touch_position) {


    }

    public void setContact(boolean contact){
        this.floorContact = contact;

        Logger.log("floor contact: " + this.floorContact);
    }

    protected void jump(){
        physicsBody.applyForceToCenter(0f, this.jumpForce, true);
    }

    protected void startMoving(float mult){
        physicsBody.setLinearVelocity(mult * this.moveSpeed, 0);
    }

    public void onSensorEnter(String sensorTag, Character other){
        // VIRTUAL
    }

    public void onSensorExit(String sensorTag, Character other){
        // VIRTUAL
    }

    public void isTouched(ArrayList<Character> touchedCharacters){

    }


}
