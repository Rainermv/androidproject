package com.rainer.veganrevenge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Rainer on 09/06/2016.
 */
public class Character extends GameObject {

    Animator characterAnimator;

    public Character(){

        characterAnimator = new Animator();
        /*
        this.rect = new Rectangle();
        this.rect.x = 0;
        this.rect.y = 0;
        this.rect.width = 32;
        this.rect.height = 32;
        */
        Texture texture = new Texture(Gdx.files.internal("sprites/better.png"));
        this.sprite = new Sprite(texture);
        //this.sprite.setScale(0.5f,0.5f);
        //this.texture = new Texture(Gdx.files.internal("sprites/knight_idle_1.png"));
    }

    public Character (Sprite sprite){
        this.sprite = sprite;
    }

    public Character (TextureAtlas atlas){
        this.sprite = atlas.createSprite("Attack (1)");
    }

    @Override
    public void draw(SpriteBatch batch) {

        characterAnimator.draw(batch);

    }

    @Override
    public void onScreenTouch(Vector3 touch_position) {

        super.onScreenTouch(touch_position);

        this.rect.x = touch_position.x - this.rect.width /2;

    }
}
