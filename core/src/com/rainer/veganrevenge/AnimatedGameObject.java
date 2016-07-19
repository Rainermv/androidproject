package com.rainer.veganrevenge;

import com.badlogic.gdx.Gdx;
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

    final float SCALE = 0.025f;


    private float stateTime;

    private float height;
    private float width;

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public AnimatedGameObject(Vector3 pos){

        super(pos);

        animFactoryReference = AnimationFactory.getInstance();
        this.animation = animFactoryReference.getAnimation("knight_attack");

        stateTime = 0f;

        updateAnimation();



    }

    // default constructor
    public AnimatedGameObject(){

        super();

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

        batch.draw(currentFrame,this.getX()  - width/2,this.getY() - height/2, width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        this.currentFrame.getTexture().dispose();
    }

}
