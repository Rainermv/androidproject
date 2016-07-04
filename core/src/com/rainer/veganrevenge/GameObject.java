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


    private Rectangle rect; // remover
    protected Vector3 scale;

    public GameObject(Rectangle rectangle, Vector3 position, Vector3 scale){
        this.rect = rectangle;
        this.scale = scale;

        this.updatePosition(position);
        this.scaleRect();
    }

    public GameObject(){
        this.rect = new Rectangle();
        this.rect.x = 0;
        this.rect.y = 0;
        this.rect.width = 64;
        this.rect.height = 64;

        this.scale = new Vector3(1,1,1);

        this.scaleRect();
    }

    private void scaleRect(){
        this.rect.setSize(rect.width*scale.x, rect.height*scale.y);
    }

    public void updatePosition(Vector3 position){
        this.rect.setX(position.x);
        this.rect.setY(position.y);
    }

    public void setRectSize(float w, float h){
        this.rect.setWidth(w);
        this.rect.setHeight(h);
        scaleRect();
    }

    public float getX(){
        return this.rect.getX();
    }

    public float getY(){
        return this.rect.getY();
    }

    public float getW(){
        return this.rect.getWidth();
    }

    public float getH(){
        return this.rect.getHeight();
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
