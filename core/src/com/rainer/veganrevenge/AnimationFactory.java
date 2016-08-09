package com.rainer.veganrevenge;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Rainer on 18/07/2016.
 */
public class AnimationFactory {

    final float CHARACTER_KNIGHT_SCALE = 0.01f;
    final float CRARACTER_ROBOT_SCALE = 0.02f;
    final float POWERUP_SCALE = 0.002f;


    private HashMap<String, Animation> animationHashMap = new HashMap<String, Animation>();
    private HashMap<String, Float> scaleHashMap = new HashMap<String, Float>();

    //private float fps = 1/15;

    private static AnimationFactory instance;
    private AnimationFactory(){

    }

    public void build(){

        //this.fps = fps;

        animationHashMap.clear();

        TextureAtlas knight = new TextureAtlas(Gdx.files.internal("spritesheets/knight.txt"));

        storeAnimation("knight_attack",     "Attack",    5,  10, knight, Animation.PlayMode.NORMAL, CHARACTER_KNIGHT_SCALE, 1f/15f);
        storeAnimation("knight_run",        "Run",       1,   9, knight, Animation.PlayMode.LOOP, CHARACTER_KNIGHT_SCALE, 1f/15f);
        storeAnimation("knight_dead",       "Dead",      1,  10, knight, Animation.PlayMode.NORMAL, CHARACTER_KNIGHT_SCALE, 1f/15f);
        storeAnimation("knight_jump",       "Jump",      1,  5, knight, Animation.PlayMode.NORMAL, CHARACTER_KNIGHT_SCALE, 1f/15f);
        storeAnimation("knight_jumpAttack", "JumpAttack",5, 10, knight, Animation.PlayMode.NORMAL, CHARACTER_KNIGHT_SCALE, 1f/15f);

        TextureAtlas robot = new TextureAtlas(Gdx.files.internal("spritesheets/robot1.txt"));

        storeAnimation("robot_attack",     "Melee",    1,  8, robot, Animation.PlayMode.NORMAL, CRARACTER_ROBOT_SCALE, 1f/15f);
        storeAnimation("robot_run",        "Run",       1,   8, robot, Animation.PlayMode.LOOP, CRARACTER_ROBOT_SCALE, 1f/15f);
        storeAnimation("robot_dead",       "Dead",      1,  10, robot, Animation.PlayMode.NORMAL, CRARACTER_ROBOT_SCALE, 1f/15f);
        storeAnimation("robot_jump",       "Jump",      1,  10, robot, Animation.PlayMode.NORMAL, CRARACTER_ROBOT_SCALE, 1f/15f);
        storeAnimation("robot_jumpAttack", "JumpMelee",1, 8, robot, Animation.PlayMode.NORMAL, CRARACTER_ROBOT_SCALE, 1f/15f);

        TextureAtlas coin = new TextureAtlas(Gdx.files.internal("spritesheets/coin.txt"));
        storeAnimation("coin_default",     "Coin",    1,  10, coin, Animation.PlayMode.LOOP, POWERUP_SCALE, 1f/5f);

        TextureAtlas health = new TextureAtlas(Gdx.files.internal("spritesheets/health.txt"));
        storeAnimation("health_default",     "Health",    1,  8, health, Animation.PlayMode.LOOP, POWERUP_SCALE, 1f/5f);

        //knight.dispose();
        //robot.dispose();
        //coin.dispose();
        //health.dispose();

    }


    private void storeAnimation(String animationKey, String name, int first, int last, TextureAtlas textureAtlas, Animation.PlayMode mode,float scale, float fps){

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = first; i <= last; i++){
            String frameName = name + " (" + i + ")";
            //Logger.log(frameName);

            frames.add(textureAtlas.findRegion(frameName));
        }

        Animation anim = new Animation(fps, frames);
        anim.setPlayMode(mode);

        scaleHashMap.put(animationKey, scale);
        animationHashMap.put(animationKey, anim);

    }

    public static AnimationFactory getInstance(){
        if (instance == null){
            instance = new AnimationFactory();
        }

        return instance;
    }

    public float getScale(String key){

        Logger.log("GET SCALE " + scaleHashMap.get(key));

        return scaleHashMap.get(key);
    }


    public Animation getAnimation(String key){

        return animationHashMap.get(key);
    }


}
