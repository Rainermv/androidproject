package com.rainer.veganrevenge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Rainer on 08/06/2016.
 */
public class GameObject implements Disposable {


    protected Rectangle rect; // remover

    public GameObject(Rectangle rect){
        this.rect = rect;
    }

    public GameObject(){
        this.rect = new Rectangle();
        this.rect.x = 0;
        this.rect.y = 0;
        this.rect.width = 128;
        this.rect.height = 128;
    }



    public void draw(SpriteBatch batch){
        // VIRTUAL
    }

    public void onScreenTouch(Vector3 touch_position){
        // VIRTUAL
    }

    @Override
    public void dispose() {
        // VIRTUAL
    }

}
