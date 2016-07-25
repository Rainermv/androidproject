package com.rainer.veganrevenge;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by rainervieira on 25/07/2016.
 */
public class ColorTinter {

    private float tintNanoseconds = 0;

    private float n_colorIn = 800000000;
    private float n_colorOut = 200000000;

    private Color targetColor = Color.WHITE;

    private float coef = 0f;

    private boolean active = false;

    public ColorTinter(){
    }

    public void start(Color targetColor, float colorIn, float colorOut){

        tintNanoseconds = TimeUtils.nanoTime();

        this.n_colorIn = colorIn * 1000000000;
        this.n_colorOut = colorOut * 1000000000;
        this.targetColor = targetColor;

        active = true;

    }

    public void step(){

        if (TimeUtils.nanoTime() - tintNanoseconds < n_colorIn) { //if we are "rising"
            coef = (float) (TimeUtils.nanoTime() - tintNanoseconds) / (float) n_colorIn;
            //interpolate based on elapsed and total times
        }
        else if (TimeUtils.nanoTime() - tintNanoseconds - n_colorIn < n_colorOut){
            //if we are "falling"

            coef = 1f - (float) (TimeUtils.nanoTime() - tintNanoseconds - n_colorIn) /
                    (float) n_colorOut; //interpolate again, but this time going from 1 down to 0

            if (coef <= 0){
                coef = 0;
                this.active = false;
            }
        }
    }

    public Color getColor(){

        return Color.WHITE.cpy().lerp(targetColor, coef);
    }

    public boolean isActive(){
        return active;
    }

    public void stop(){
        active = false;
    }

}
