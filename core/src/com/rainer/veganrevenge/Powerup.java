package com.rainer.veganrevenge;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by rainervieira on 06/08/2016.
 */
public class Powerup extends AnimatedGameObject {

    Body physicsBody;

    protected float bodyScale = 0;
    protected float bodyRadius = 0;

    protected float bonusHealth = 0;
    protected float bonusAttack = 0;
    protected float bonusGold = 0;

    private World worldReference;

    public Powerup(World world, Vector3 pos, float spriteScale, float bodyScale){
        super(pos, spriteScale);
    }
}
