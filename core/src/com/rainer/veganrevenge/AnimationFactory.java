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

    private HashMap<String, Animation> animationHashMap = new HashMap<String, Animation>();

    private float fps = 1/15;

    private static AnimationFactory instance;
    private AnimationFactory(){

        /*
        TextureRegion[] animFrames = new TextureRegion[10];

        animationHashMap.put("knight_attack",new Animation(1/15f,
                (textureAtlas.findRegion("Attack (1)")),
                (textureAtlas.findRegion("Attack (2)")),
                (textureAtlas.findRegion("Attack (3)")),
                (textureAtlas.findRegion("Attack (4)")),
                (textureAtlas.findRegion("Attack (5)")),
                (textureAtlas.findRegion("Attack (6)")),
                (textureAtlas.findRegion("Attack (7)")),
                (textureAtlas.findRegion("Attack (8)")),
                (textureAtlas.findRegion("Attack (9)")),
                (textureAtlas.findRegion("Attack (10)"))));
        */

    }

    public void build(float fps){

        this.fps = fps;

        animationHashMap.clear();

        TextureAtlas knight = new TextureAtlas(Gdx.files.internal("spritesheets/knight.txt"));

        storeAnimation("knight_attack",     "Attack",    5,  10, knight, Animation.PlayMode.NORMAL);
        storeAnimation("knight_run",        "Run",       1,   9, knight, Animation.PlayMode.LOOP);
        storeAnimation("knight_dead",       "Dead",      1,  10, knight, Animation.PlayMode.NORMAL);
        storeAnimation("knight_jump",       "Jump",      1,  5, knight, Animation.PlayMode.NORMAL);
        storeAnimation("knight_jumpAttack", "JumpAttack",5, 10, knight, Animation.PlayMode.NORMAL);

    }

    private void storeAnimation(String animationKey, String name, int first, int last, TextureAtlas textureAtlas, Animation.PlayMode mode){

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (int i = first; i <= last; i++){
            String frameName = name + " (" + i + ")";
            Logger.log(frameName);
            frames.add(textureAtlas.findRegion(frameName));
        }

        Animation anim = new Animation(fps, frames);
        anim.setPlayMode(mode);

        animationHashMap.put(animationKey, anim);

    }

    public static AnimationFactory getInstance(){
        if (instance == null){
            instance = new AnimationFactory();
        }

        return instance;
    }

    public Animation getAnimation(String key){
        return animationHashMap.get(key);
    }


}
