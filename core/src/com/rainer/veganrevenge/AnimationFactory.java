package com.rainer.veganrevenge;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Rainer on 18/07/2016.
 */
public class AnimationFactory {

    private HashMap<String, Animation> animationHashMap = new HashMap<String, Animation>();

    private static AnimationFactory instance;
    private AnimationFactory(){

        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/knight.txt"));
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
