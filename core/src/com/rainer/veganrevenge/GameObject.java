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

    public String tag ="";

    public float SCALE;

    public GameObject(Vector3 position, float scale){
        //this.rect = rectangle;
        //this.scale = scale;

        this.SCALE = scale;

        this.updatePosition(position);

        //this.scaleRect();me
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

    public void translate(float x,float y){
        this.x+=x;
        this.y+=y;
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

    public void start(){
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
