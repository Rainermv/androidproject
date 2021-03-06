package com.rainer.veganrevenge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Rainer on 04/07/2016.
 */
public class AnimatedGameObject extends GameObject {

    protected AnimationFactory animFactoryReference;

    private Animation animation;
    TextureRegion currentFrame;

    private float stateTime;
    //private float height;
    //private float width;

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    private boolean flipped;

    float animation_scale = 1.0f;

    public void setAnimation(String animationString) {

        this.animation = animFactoryReference.getAnimation(animationString);
        this.animation_scale = animFactoryReference.getScale(animationString);
        stateTime = 0f;
    }

    private ColorTinter tinter = new ColorTinter();

    public AnimatedGameObject(Vector3 pos){

        super(pos);

        animFactoryReference = AnimationFactory.getInstance();
        this.animation = animFactoryReference.getAnimation("knight_run");

        stateTime = 0f;

        this.currentFrame = animation.getKeyFrame(stateTime);

    }

    public boolean isAnimationFinished(){
        return animation.isAnimationFinished(stateTime);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        stateTime += Gdx.graphics.getDeltaTime();

        this.currentFrame = animation.getKeyFrame(stateTime);
        float width = currentFrame.getRegionWidth() * animation_scale;
        float height = currentFrame.getRegionHeight() * animation_scale;

        float drawWidth = width;
        float drawHeigth = height;
        float drawX = this.getX() - width/2;
        float drawY = this.getY() - height/2;

        if (flipped){
            drawWidth = -drawWidth;
            drawX = this.getX() + width/2;
        }

        if (tinter.isActive()){
            tinter.step();
            batch.setColor(tinter.getColor());
        }

        batch.draw(currentFrame,drawX,drawY, drawWidth, drawHeigth);

        if (tinter.isActive()){
            batch.setColor(Color.WHITE);
        }

        tinter.endStep();
    }

    @Override
    public void dispose() {
        super.dispose();
        //this.currentFrame.getTexture().dispose();
    }

    public void flashTint(Color color, float colorIn, float colorOut){

        tinter.start(color, colorIn, colorOut);

    }

}
