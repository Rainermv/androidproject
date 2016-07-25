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

    protected Animation animation;
    TextureRegion currentFrame;

    private float stateTime;
    private float height;
    private float width;

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    private boolean flipped;

    public float getHeight() {
        return height;
    }
    public float getWidth() {
        return width;
    }

    private ColorTinter tinter = new ColorTinter();

    public AnimatedGameObject(Vector3 pos, float scale){

        super(pos, scale);

        animFactoryReference = AnimationFactory.getInstance();
        this.animation = animFactoryReference.getAnimation("knight_attack");

        stateTime = 0f;

        updateAnimation();

    }

    public void updateAnimation(){
        this.currentFrame = animation.getKeyFrame(stateTime, true);
        width = currentFrame.getRegionWidth() * SCALE;
        height = currentFrame.getRegionHeight() * SCALE;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        stateTime += Gdx.graphics.getDeltaTime();
        updateAnimation();

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
    }

    @Override
    public void dispose() {
        super.dispose();
        this.currentFrame.getTexture().dispose();
    }

    public void flashTint(Color color, float colorIn, float colorOut){

        tinter.start(color, colorIn, colorOut);

    }

}
