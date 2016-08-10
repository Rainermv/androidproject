package com.rainer.veganrevenge;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Created by rainervieira on 27/07/2016.
 */
public class PlayerController {

    public int getGold() {
        return GOLD;
    }

    private int GOLD = 0;

    private Player playerAvatar;

    private static PlayerController ourInstance = new PlayerController();

    public static PlayerController getInstance() {
        return ourInstance;
    }

    private PlayerController() {

    }

    public Player getPlayer() {
        return playerAvatar;
    }

    public void setPlayer(Player playerAvatar) {
        this.playerAvatar = playerAvatar;

    }

    public boolean isDead() {

        return (playerAvatar.flagDead && playerAvatar.isAnimationFinished());

    }

    public void reset(){
        GOLD = 0;
    }

    public void touch( ArrayList<GameObject> touchedObjects){

        Enemy enemyTouched = null;
        Sensor closestSensor = null;

        for (GameObject touchedObject : touchedObjects){

            if (touchedObject instanceof Powerup && !touchedObject.flagDelete){

                Powerup powerup = (Powerup)touchedObject;

                playerAvatar.addHealth(powerup.bonusHealth);

                GOLD += powerup.bonusGold;

                powerup.flagDelete = true;
                return;
            }

            if (touchedObject.tag == "ENEMY_TOUCH") {
                enemyTouched = (Enemy) ((Sensor)touchedObject).parent;
            }
            else if (touchedObject instanceof Sensor && ((Sensor)touchedObject).parent.tag == "PLAYER"){

                Sensor touchedSensor = (Sensor)touchedObject;

                if (closestSensor == null || touchedSensor.range < closestSensor.range){
                    closestSensor = touchedSensor;
                }
            }

        }





        if (enemyTouched != null && closestSensor != null){
            playerAvatar.actionAttack(enemyTouched);
            //enemyTouched.actionAttack(this.playerAvatar);
        }
        else{
            if (playerAvatar.floorContact == true) {
                playerAvatar.actionJump();
            }
        }

    }


}
