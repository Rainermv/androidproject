package com.rainer.veganrevenge;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Rainer on 10/06/2016.
 */
public class Animator {

    Animation animation;          // #3

    float stateTime;                                        // #8

    public Animator(){
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
    }

    public void draw(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();           // #15
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);  // #16
        batch.draw(currentFrame, 50, 50);             // #17
    }

    public void dispose() {


    }

}