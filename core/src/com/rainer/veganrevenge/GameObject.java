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

    //public String sprite_file = "sprites/bg.png";

    //protected Texture texture;
    protected Sprite sprite;
    protected Rectangle rect; // remover

    public GameObject(String sprite_path, Rectangle rect){

        this.rect = rect;
        Texture texture = new Texture(Gdx.files.internal(sprite_path));
        this.sprite = new Sprite(texture, 30,30);

    }

    public GameObject(){
        this.rect = new Rectangle();
        this.rect.x = 0;
        this.rect.y = 0;
        this.rect.width = 32;
        this.rect.height = 32;
        //this.texture = new Texture(Gdx.files.internal("sprites/bg.png"));
    }

    public void setPosition(float x,float y){
        this.sprite.setX(x);
        this.sprite.setY(y);
    }

    public void draw(SpriteBatch batch){

        this.sprite.draw(batch);
        //batch.draw(this.texture, this.rect.x, this.rect.y );

    }

    public void onScreenTouch(Vector3 touch_position){


    }

    @Override
    public void dispose() {
        //sprite.dispose();
    }

}
