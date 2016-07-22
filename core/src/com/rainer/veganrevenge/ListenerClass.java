package com.rainer.veganrevenge;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by rainervieira on 21/07/2016.
 */
public class ListenerClass  implements ContactListener {

    private static ListenerClass instance;
    private ListenerClass(){

    }

    public static ListenerClass getInstance(){

        if (instance == null){
            instance = new ListenerClass();
        }

        return instance;
    }

    @Override
    public void beginContact(Contact contact) {

        GameObject obja = (GameObject)contact.getFixtureA().getBody().getUserData();
        GameObject objb = (GameObject)contact.getFixtureB().getBody().getUserData();

        Logger.log("CONTACT | A: " + obja.tag + " | B: " + objb.tag);

        if (obja.tag == "PLAYER" && objb.tag == "FLOOR"){
            ((Character)obja).setContact(true);
        }
        else if (obja.tag == "FLOOR" && objb.tag == "PLAYER"){
            ((Character)objb).setContact(true);
        }

    }

    @Override
    public void endContact(Contact contact) {

        GameObject obja = (GameObject)contact.getFixtureA().getBody().getUserData();
        GameObject objb = (GameObject)contact.getFixtureB().getBody().getUserData();

        Logger.log("E CONTACT | A: " + obja.tag + " | B: " + objb.tag);

        if (obja.tag == "PLAYER" && objb.tag == "FLOOR"){
            ((Character)obja).setContact(false);
        }
        else if (obja.tag == "FLOOR" && objb.tag == "PLAYER"){
            ((Character)objb).setContact(false);
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }



}
