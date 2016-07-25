package com.rainer.veganrevenge;

/**
 * Created by rainervieira on 25/07/2016.
 */
public enum CollisionCategory {
    FLOOR, CHARACTER, SENSOR;

    public short get(){

        switch (this){

            case FLOOR :
                return 0x0001;
            case CHARACTER:
                return 0x0002;
            case SENSOR:
                return 0x0003;
            default:
                return 0x0001;
        }
    }
}
