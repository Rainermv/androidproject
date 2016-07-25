package com.rainer.veganrevenge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by rainervieira on 25/07/2016.
 */
public class Bar extends GameObject {

    float width = 10;
    float height = 2;

    float value = 0;
    float maxValue = 0;

    Color bgColor = Color.BLACK;
    
    Color colorEmpty = Color.WHITE;
    Color colorFull = Color.WHITE;

    Color drawColor = Color.WHITE;

    //Ninepatch patch = new NinePatch()

    public Bar(Vector3 position, Color colorFull, Color colorEmpty, float width, float height, float maxValue, float initValue) {
        super(position, 1);

        this.colorFull = colorFull;
        this.colorEmpty = colorEmpty;
        this.width = width;
        this.height = height;
        this.value = initValue;
        this.maxValue = maxValue;

        updateColor();
    }

    public void updateValue(float value){
        this.value = value;
        updateColor();
    }

    private void updateColor(){

        if (this.value / this.maxValue > 0.3){
            drawColor = colorFull;
        }
        else{
            drawColor = colorEmpty;
        }
        //drawColor = colorEmpty.lerp(colorFull, this.value / this.maxValue);
    }

    @Override
    public void drawUI(ShapeRenderer renderer, Camera cam){

        float viewWidth = Gdx.app.getGraphics().getWidth();
        float viewHeight = Gdx.app.getGraphics().getHeight();

        float mult = this.value / this.maxValue;

        //Logger.log(viewHeight + " * "+ y + "=" + y *viewHeight);

        renderer.setColor(bgColor);
        renderer.rect(x *viewWidth, y *viewHeight, width *viewWidth, height *viewHeight );

        if (mult >= 0) {
            renderer.setColor(drawColor);
            renderer.rect(x * viewWidth, y * viewHeight, width * viewWidth * mult, height * viewHeight);
        }


    }


}