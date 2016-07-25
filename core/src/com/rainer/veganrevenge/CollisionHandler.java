package com.rainer.veganrevenge;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by rainervieira on 21/07/2016.
 */
public class CollisionHandler implements ContactListener {

    private static CollisionHandler instance;
    private CollisionHandler(){

    }

    public static CollisionHandler getInstance(){

        if (instance == null){
            instance = new CollisionHandler();
        }

        return instance;
    }

    @Override
    public void beginContact(Contact contact) {

        GameObject obja = (GameObject)contact.getFixtureA().getBody().getUserData();
        GameObject objb = (GameObject)contact.getFixtureB().getBody().getUserData();

        if (obja.tag == "PLAYER" && objb.tag == "FLOOR"){
            ((Character)obja).setContact(true);
        }
        else if (obja.tag == "FLOOR" && objb.tag == "PLAYER"){
            ((Character)objb).setContact(true);
        }
        if (obja instanceof Sensor && objb instanceof Character){
            ((Sensor)obja).enter((Character)objb);
        }
        if (objb instanceof Sensor && obja instanceof Character){
            ((Sensor)objb).enter((Character)obja);
        }
    }

    @Override
    public void endContact(Contact contact) {

        GameObject obja = (GameObject)contact.getFixtureA().getBody().getUserData();
        GameObject objb = (GameObject)contact.getFixtureB().getBody().getUserData();

        if (obja.tag == "PLAYER" && objb.tag == "FLOOR"){
            ((Character)obja).setContact(false);
        }
        else if (obja.tag == "FLOOR" && objb.tag == "PLAYER"){
            ((Character)objb).setContact(false);
        }
        if (obja instanceof Sensor && objb instanceof Character){
            ((Sensor)obja).exit((Character)objb);
        }
        if (objb instanceof Sensor && obja instanceof Character){
            ((Sensor)objb).exit((Character)obja);
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {


    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }



}
