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
 * Created by Rainer on 04/07/2016.
 */
public class StaticGameObject extends GameObject{

    protected Sprite sprite;

    public StaticGameObject(Vector3 pos, Vector3 scale, Sprite sprite){

        super(sprite.getBoundingRectangle(), pos, scale);

        this.sprite = sprite;
        adjustSprite();
        //Texture texture = new Texture(Gdx.files.internal(sprite_path));
        //this.sprite = new Sprite(texture, 30,30);
    }

    // default constructor
    public StaticGameObject(){

        super();

        Texture texture = new Texture(Gdx.files.internal("sprites/medium.png"));
        this.sprite = new Sprite(texture);

        adjustSprite();
    }

    public void adjustSprite(){
        this.sprite.setX(this.getX());
        this.sprite.setY(this.getY());
        this.sprite.setSize(this.getW(),this.getH());
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        this.sprite.draw(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
        this.sprite.getTexture().dispose();
    }

}
