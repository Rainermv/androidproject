package com.rainer.veganrevenge;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by rainervieira on 27/07/2016.
 */
public abstract class AnimationState {

    //private String animationString = "";
    private Animation animation = null;

    public AnimationState(String animationString){
        animation = AnimationFactory.getInstance().getAnimation(animationString);
        //this.animationString = animationString;

    }

    public Animation getAnimation(){
        return animation;
    }

}
