package com.rainer.veganrevenge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    //private Rectangle rect; // remover
    protected float x;
    protected float y;


    public GameObject(Vector3 position){
        //this.rect = rectangle;
        //this.scale = scale;

        this.updatePosition(position);

        //this.scaleRect();
    }

    public GameObject(){
        this.x = 0;
        this.y = 0;;

        //this.rect = new Rectangle();
        //this.rect.x = 0;
        //this.rect.y = 0;
        //this.rect.width = 64;
        //this.rect.height = 64;



        //this.scale = 0.05f;

        //this.scaleRect();
    }

    /*
    private void scaleRect(){
        this.rect.setSize(rect.width*scale.x, rect.height*scale.y);
    }
    */

    public void updatePosition(Vector3 position){
        this.x = position.x;
        this.y = position.y;
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
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
