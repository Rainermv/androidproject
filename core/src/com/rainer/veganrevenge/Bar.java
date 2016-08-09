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

    public Bar(Vector3 positionMult, float widthMult, float heightMult, Color colorFull, Color colorEmpty, float maxValue, float initValue) {
        super(new Vector3(positionMult.x * Gdx.app.getGraphics().getWidth(), positionMult.y * Gdx.app.getGraphics().getHeight(),0));

        this.colorFull = colorFull;
        this.colorEmpty = colorEmpty;
        this.width = widthMult * Gdx.app.getGraphics().getWidth();
        this.height = heightMult  * Gdx.app.getGraphics().getHeight();
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
    public void drawUI(ShapeRenderer renderer){

        //float viewWidth = Gdx.app.getGraphics().getWidth();
        //float viewHeight = Gdx.app.getGraphics().getHeight();

        float mult = this.value / this.maxValue;

        //Logger.log(viewHeight + " * "+ y + "=" + y *viewHeight);

        //this.x = 0;
        //this.y = 0;
        //this.width = 10;
        //this.height = 10;

        renderer.setColor(bgColor);
        renderer.rect(x, y, width, height );


        //renderer.rect( *viewWidth, y *viewHeight, width *viewWidth, height *viewHeight );
        //renderer.rect(0, 0,viewWidth, 10 );

        if (mult >= 0) {
            renderer.setColor(drawColor);
            renderer.rect(x, y, width * mult, height );
            //renderer.rect(x * viewWidth, y * viewHeight, width * viewWidth * mult, height * viewHeight);
            //renderer.rect(x * viewWidth, y * viewHeight, width * viewWidth * mult, height * viewHeight);
        }


    }


}
