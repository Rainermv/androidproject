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

    protected Animation animation;
    TextureRegion currentFrame;

    private float stateTime;

    public AnimatedGameObject(Vector3 pos, Vector3 scale, Animation animation){

        super(new Rectangle(0,0,64,64), pos, scale);

        this.animation = animation;
        stateTime = 0f;
        updateAnimation();

        //Rectangle rect = new Rectangle(0,0, currentFrame.getTexture().getWidth(),currentFrame.getTexture().getHeight());

        //currentFrame.getTexture().getWidth();

    }

    // default constructor
    public AnimatedGameObject(){

        super(new Rectangle(0,0,64,64), new Vector3(0,0,0), new Vector3(2,2,1));

        TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/knight.txt"));
        TextureRegion[] animFrames = new TextureRegion[10];

        animation = new Animation(1/15f,
                (textureAtlas.findRegion("Attack (1)")),
                (textureAtlas.findRegion("Attack (2)")),
                (textureAtlas.findRegion("Attack (3)")),
                (textureAtlas.findRegion("Attack (4)")),
                (textureAtlas.findRegion("Attack (5)")),
                (textureAtlas.findRegion("Attack (6)")),
                (textureAtlas.findRegion("Attack (7)")),
                (textureAtlas.findRegion("Attack (8)")),
                (textureAtlas.findRegion("Attack (9)")),
                (textureAtlas.findRegion("Attack (10)")));

        //animation = new Animation(1/15f, textureAtlas.getRegions());
        stateTime = 0f;

        updateAnimation();
    }

    public void updateAnimation(){
        this.currentFrame = animation.getKeyFrame(stateTime, true);


        this.setRectSize(currentFrame.getRegionWidth(),currentFrame.getRegionHeight());


    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        stateTime += Gdx.graphics.getDeltaTime();
        updateAnimation();
        batch.draw(currentFrame,this.getX(),this.getY(), this.getW(), this.getH());
    }

    @Override
    public void dispose() {
        super.dispose();
        this.currentFrame.getTexture().dispose();
    }

}
