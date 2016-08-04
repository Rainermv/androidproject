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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rainer on 09/06/2016.
 */
public class Character extends AnimatedGameObject  {

    Body physicsBody;

    //Bar healthBar = null;

    ArrayList<Sensor> sensorArray = new ArrayList<Sensor>();

    boolean floorContact = true;

    protected float bodyScale = 0;
    protected float bodyRadius = 0;

    protected int health = 0;
    protected int damage = 0;

    protected float moveSpeed = 0;
    protected float jumpForce = 0;
    protected float sensorRange = 0;

    private World worldReference;

    private HashMap<String, String> animationKeys = new HashMap<String, String>();
    private String animState = "";

    public boolean flagDead = false;

    public int getHealth() {
        return health;
    }
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void addHealth(int health) {
        if (health < 0){
            this.flashTint(Color.RED, 0.1f, 0.1f);
        }
        this.health += health;
    }

    public Character (World world, Vector3 position, float spriteScale, float bodyScale){

        super(position, spriteScale);

        this.tag = "PLAYER";
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

    public void setFloorContact(boolean contact){

        if (flagDead) return;

        if (contact == false){
            setAnimation("JUMP");
        }
        else{
            setAnimation("RUN");
        }
        this.floorContact = contact;
    }

    @Override
    public void update(){

        if (flagDead) return;

        if (animState == "ATTACK" && isAnimationFinished()){
            setAnimation("RUN");
        }
    }

    @Override
    public void draw(SpriteBatch batch) {

        if (physicsBody.getLinearVelocity().x < 0)
            setFlipped(true);
        else if (physicsBody.getLinearVelocity().x > 0){
            setFlipped(false);
        }

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

        /*
        if (healthBar != null) {
            healthBar.drawUI(renderer, cam);
        }
        */
    }

    protected void addSensor(World world, float range, String tag){

        sensorArray.add(new Sensor(new Vector3(this.x, this.y, 0),this.SCALE, range, this.bodyScale, world, this, tag));
    }

    public void actionAttack(Character other){
        if (flagDead) return;

        other.addHealth(-getDamage());

        if (this.floorContact){
            setAnimation("ATTACK");
        }
        else{
            setAnimation("JUMP_ATTACK");
        }
    }

    protected void actionJump(){
        if (flagDead) return;

        physicsBody.applyForceToCenter(0f, this.jumpForce, true);
    }

    protected void actionStartMoving(float mult){
        if (flagDead) return;

        physicsBody.setLinearVelocity(mult * this.moveSpeed, 0);
        setAnimation("RUN");
    }

    protected void actionDie(){
        if (flagDead) return;

        flagDead = true;
        physicsBody.setLinearVelocity(0,0);
        /*
        worldReference.destroyBody(physicsBody);
        for (Sensor sensor : sensorArray){
            sensor.dispose();
        }
        */
        setAnimation("DEAD");
    }


    protected void setAnimationKeys(String attack, String run, String dead, String jump, String jumpAttack){

        animationKeys.clear();

        animationKeys.put("ATTACK", attack);
        animationKeys.put("RUN", run);
        animationKeys.put("DEAD", dead);
        animationKeys.put("JUMP", jump);
        animationKeys.put("JUMP_ATTACK", jumpAttack);

    }

    @Override
    public void setAnimation(String key){

        String animationKey = animationKeys.get(key);
        if (animationKey != null){
            super.setAnimation(animationKey);
            animState = key;
        }

    }

    public void onSensorEnter(String sensorTag, Character other){
        // VIRTUAL
    }

    public void onSensorExit(String sensorTag, Character other){
        // VIRTUAL
    }


    @Override
    public void dispose() {
        super.dispose();

        for (Sensor sensor : sensorArray){
            sensor.dispose();
        }

        worldReference.destroyBody(this.physicsBody);

    }


    public void disposeSensors() {
        for (Sensor sensor : sensorArray){
            sensor.dispose();
        }
    }
}
