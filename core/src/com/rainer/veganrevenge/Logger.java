package com.rainer.veganrevenge;

import com.badlogic.gdx.Gdx;

/**
 * Created by Rainer on 19/07/2016.
 */
public class Logger {

    public static void log(float F){
        Gdx.app.log("Log", ""+F);
    }
    public static void log(int I){
        Gdx.app.log("Log", ""+I);
    }
    public static void log(String s){
        Gdx.app.log("Log", s);
    }

}
