package com.rainer.veganrevenge;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by rainervieira on 28/07/2016.
 */
public class ParallaxBackground extends GameObject{

    final int BACKGROUND_SIZE = 3;
    //final float SCALE_MULT = 4.5f;

    final float BACKDROP_SPEED_MULT = 0.98f;
    final float TREELINE_SPEED_MULT = 0.6f;
    final float GROUND_SPEED_MULT = 0f;

    float backdropWidth;
    float treelineWidth;
    float groundWidth;

    Array<StaticGameObject> backdropArray = new Array<StaticGameObject>();
    Array<StaticGameObject> treelineArray = new Array<StaticGameObject>();
    Array<StaticGameObject> groundArray = new Array<StaticGameObject>();

    float last_x = 0;

    public ParallaxBackground(Vector3 pos, Texture backdropTexture, Texture treelineTexture, Texture groundTexture, float scale0, float scale1, float scale2){
        super(pos, 1);

        backdropWidth = backdropTexture.getWidth() * scale0 ;
        treelineWidth = treelineTexture.getWidth() * scale1;
        groundWidth =   groundTexture.getWidth() * scale2 ;

        for (int i = 0; i < BACKGROUND_SIZE; i++){

            float backdropX = backdropWidth * (i -1);
            float treelineX = treelineWidth * (i -1);
            float groundX   = groundWidth   * (i -1);

            backdropArray.add(new StaticGameObject(new Vector3(backdropX,0,0), backdropTexture, scale0 ));
            treelineArray.add(new StaticGameObject(new Vector3(treelineX,0,0), treelineTexture, scale1));
            groundArray.add(new StaticGameObject  (new Vector3(groundX,0,0),   groundTexture,   scale2));

        }

    }

    public void adjustLayerPosY(int layer, float yPos){

        Array<StaticGameObject> array;

        switch (layer){

            case 0:
                array = backdropArray;
                break;
            case 1:
                array = treelineArray;
                break;
            case 2:
                array = groundArray;
                break;

            default:
                return;
        }

        for (StaticGameObject obj : array) {
            obj.translate(0,yPos);
        }
    }

    public void update(float pos_x){
        super.update();

        float speed = (pos_x - last_x);

        for (StaticGameObject backdropObj : backdropArray){
            float jump_point = backdropObj.x + backdropWidth + (BACKGROUND_SIZE/2 ) * backdropWidth;
            if (pos_x  > jump_point) {
                backdropObj.translate((speed * BACKDROP_SPEED_MULT) + backdropWidth * BACKGROUND_SIZE, 0);
            }
            else
                backdropObj.translate(speed * BACKDROP_SPEED_MULT, 0 );
        }
        for (StaticGameObject treelineObj : treelineArray){
            float jump_point = treelineObj.x + treelineWidth + (BACKGROUND_SIZE/2 ) * treelineWidth;
            if (pos_x  > jump_point) {
                treelineObj.translate((speed * TREELINE_SPEED_MULT) + treelineWidth * BACKGROUND_SIZE, 0);
            }
            else
                treelineObj.translate(speed * TREELINE_SPEED_MULT, 0 );
        }
        for (StaticGameObject groundObj : groundArray) {
            float jump_point = groundObj.x + groundWidth + (BACKGROUND_SIZE/2 ) * groundWidth;
            if (pos_x  > jump_point){
                groundObj.translate((speed * GROUND_SPEED_MULT) + groundWidth* BACKGROUND_SIZE,0);
            }
            else
                groundObj.translate(speed * GROUND_SPEED_MULT, 0 );
        }

        last_x = pos_x;
    }

    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);

        for (StaticGameObject backdropObj : backdropArray){
            backdropObj.draw(batch);
        }
        for (StaticGameObject treelineObj : treelineArray){
            treelineObj.draw(batch);
        }
        for (StaticGameObject groundObj : groundArray){
            groundObj.draw(batch);
        }

    }

    @Override
    public void dispose() {

        for (StaticGameObject backdropObj : backdropArray){
            backdropObj.dispose();
        }
        for (StaticGameObject treelineObj : treelineArray){
            treelineObj.dispose();
        }
        for (StaticGameObject groundObj : groundArray){
            groundObj.dispose();
        }

    }

}
