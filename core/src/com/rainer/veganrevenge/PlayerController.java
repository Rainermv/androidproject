package com.rainer.veganrevenge;

import java.util.ArrayList;

/**
 * Created by rainervieira on 27/07/2016.
 */
public class PlayerController {

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

    public void touch( ArrayList<GameObject> touchedObjects){

        Enemy enemyTouched = null;
        Sensor closestSensor = null;

        for (GameObject touchedObject : touchedObjects){
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
            enemyTouched.actionDamage(this.playerAvatar);
        }
        else{
            if (playerAvatar.floorContact == true) {
                playerAvatar.actionJump();
            }
        }

    }


}
