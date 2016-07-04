package com.rainer.veganrevenge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Rainer on 09/06/2016.
 */
public class Character extends AnimatedGameObject {

    public Character (){
        super();
    }

    @Override
    public void draw(SpriteBatch batch) {

        super.draw(batch);

    }

    @Override
    public void onScreenTouch(Vector3 touch_position) {

        super.onScreenTouch(touch_position);
        this.updatePosition(touch_position);
        //this.rect.x = touch_position.x - this.rect.width /2;

    }
}
